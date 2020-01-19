/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.ophmi;


import com.powerknights.frc2015.config.hw.GamepadModels;
import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;
import com.powerknights.frc2015.subsystems.Chassis;
import com.powerknights.frc2015.subsystems.DriveTrain;
import com.powerknights.frc2015.subsystems.Lifter;
import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author Team501
 **/
public class DriverGamepad
extends DriverBaseGamepad
{

   /** Handle to the robot drive **/
   private final DriveTrain drive;
   /** Handle to the robot chassis **/
   private final Chassis chassis;

   /** Handle to the lifter **/
   private final Lifter lifter;

   /** 'Index' of the speed input device axis **/
   private final int speedAxis;
   /** 'Index' of the strafe input device axis **/
   private final int strafeAxis;
   /** **/
   private final int strafeLeftAxis;
   private final int strafeRightAxis;
   /** 'Index' of the turn input device axis **/
   private final int turnAxis;

   /** 'Index' of the turbo enable bumper **/
   private final int turboEnableButton;

   /** **/
   private double nonTurboSpeedScale; // comes from I/O on laptop
   /** **/
   private boolean flattenInputs; // comes from I/O on laptop

   /** 'Index' of the feeder station tote position button **/
   private final int feederToteButton;
   /** Flag to de-bounce the feeder tote position button **/
   private boolean lastFeederToteButton;
   /** State of feeder tote positioning **/
   private boolean doFeederPositioning;

   /** Distance from wall for feeder station tote **/
   private static final double prefsFeederToteDistance;
   static
   {
      prefsFeederToteDistance =
         PreferencesManager.getInstance().getFeederToteDistance();
      SmartDashboardManager.getInstance().putNumber( "feederToteDistance",
         prefsFeederToteDistance );
   }


   public DriverGamepad( Joystick joystick, GamepadModels model )
   {
      super( joystick, model );
      System.out.println( "DriverGamepad::contructor() for " + model );

      drive = DriveTrain.getInstance();
      chassis = Chassis.getInstance();

      lifter = Lifter.getInstance();

      speedAxis = getSpeedAxis();
      strafeAxis = getStrafeAxis();
      strafeLeftAxis = getStrafeLeftAxis();
      strafeRightAxis = getStrafeRightAxis();
      turnAxis = getTurnAxis();
      turboEnableButton = getTurboEnableButton();

      feederToteButton = getFeederToteButton();
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.ophmi.IHmiController#initForModeStart()
    */
   @Override
   public void initForModeStart()
   {
      smartDashboard.putString( "driveMode", "wpisMecanumWay" );

      initState();

      nonTurboSpeedScale =
         PreferencesManager.getInstance().getScaledDriverNonTurboSpeed();
      flattenInputs =
         PreferencesManager.getInstance().doFlattenDriverInputResponse();
   }


   private void initState()
   {
      lastFeederToteButton = false;
      setFeederPositioning( false );
   }


   private void setFeederPositioning( boolean state )
   {
      doFeederPositioning = state;
      smartDashboard.putBoolean( "doFeederPositioning", state );
   }


   private boolean isFeederPositioning()
   {
      return doFeederPositioning;
   }


   /*
    * (non-Javadoc)
    * 
    * @see
    * com.powerknights.frc2012.modes.teleoperated.IDriverControl#performUpdate
    * ()
    */
   @Override
   public void performUpdate()
   {
      // Do the speed setting based on joystick positions
      processDriving();
   }


   private void processDriving()
   {
      /*
       * Process the automated control first for feeder station
       */
      boolean toteButtonPressed;

      toteButtonPressed = getFeederToteButtonPressed();
      if ( toteButtonPressed != lastFeederToteButton )
      {
         if ( toteButtonPressed )
         {
            lifter.goTo( LiftPosition.FeederTote );

            startFeederPositioning();
         }
         else
         {
            stopFeederPositioning();
         }
         // Keep the last state to de-bounce
         lastFeederToteButton = toteButtonPressed;
      }
      if ( toteButtonPressed )
      {
         if ( isFeederPositioning() )
         {
            updateFeederPositioning();
         }
         return;
      }

      final double speed = getSpeed();
      smartDashboard.putNumber( "speed", speed );
      final double strafe = getStrafe();
      smartDashboard.putNumber( "strafe", strafe );
      final double turn = getTurn();
      smartDashboard.putNumber( "turn", turn );

      driveWPIsWay( speed, strafe, turn );
   }


   private void startFeederPositioning()
   {
      chassis.startDriving();
      setFeederPositioning( true );
   }


   private void updateFeederPositioning()
   {
      final double distance = chassis.getDistanceToTote();
      if ( distance < 0.5 )
      {
         // System.out.println( "distance: " + distance );
         // ignore - what is up with sensor?
      }
      else if ( distance > ( prefsFeederToteDistance + 0.20 ) )
      {
         chassis.driveForward( 0.25 );
      }
      else if ( distance > ( prefsFeederToteDistance + 0.10 ) )
      {
         chassis.driveForward( 0.15 );
      }
      else if ( distance < ( prefsFeederToteDistance - 0.20 ) )
      {
         chassis.driveBackward( 0.25 );
      }
      else if ( distance < ( prefsFeederToteDistance - 0.10 ) )
      {
         chassis.driveBackward( 0.15 );
      }
      else
      {
         chassis.drive( 0.0 );
         // Assume we are done so stop positioning
         setFeederPositioning( false );
      }
   }


   private void stopFeederPositioning()
   {
      chassis.stopDriving();
      // Failsafe so let up on button stops this
      setFeederPositioning( false );
   }


   private void driveWPIsWay( double speed, double strafe, double turn )
   {
      final boolean zeroSpeed = isZero( speed );
      final boolean zeroStrafe = isZero( strafe );
      final boolean zeroTurn = isZero( turn );

      if ( zeroSpeed && zeroStrafe && zeroTurn )
      {
         drive.stop();
      }
      else
      {
         drive.robotDrive( speed, strafe, turn );
      }
   }


   private double getSpeed()
   {
      final double speed = joystick.getRawAxis( speedAxis );
      smartDashboard.putNumber( "hmiSpeed", speed );
      if ( isZero( speed ) )
      {
         return 0.0;
      }
      else
      {
         return adjustSpeedInputs( speed );
      }
   }


   private double adjustSpeedInputs( double speed )
   {
      // TWEAK - Flattening response curve of joysticks
      if ( flattenInputs )
      {
         speed = InputTweaker.exponentiate( speed );
         speed = InputTweaker.square( speed );
      }

      final boolean turboEnabled = getTurboEnableButtonPressed();
      smartDashboard.putBoolean( "turboBoost", turboEnabled );
      if ( !turboEnabled )
      {
         speed *= nonTurboSpeedScale;
      }

      return adjustSpeedSign( speed );
   }


   /**
    * Ensure the sign of the speed is correct for robot convention; namely
    * "forward" is positive, and "backward" is negative.
    *
    * @param speed
    * @return
    **/
   private double adjustSpeedSign( double speed )
   {
      /*
       * Logitech Dual Action Gamepad is opposite to convention..
       */
      return -speed;
   }


   private double getJoystickStrafe()
   {
      final double strafe = joystick.getRawAxis( strafeAxis );
      return strafe;
   }


   private double getTriggersStrafe()
   {
      final double leftStrafe = joystick.getRawAxis( strafeLeftAxis );
      final double rightStrafe = joystick.getRawAxis( strafeRightAxis );
      final double strafe = -leftStrafe + rightStrafe;
      return strafe;
   }


   private double getStrafe()
   {
      // final double strafe = getJoystickStrafe();
      final double strafe = getTriggersStrafe();
      smartDashboard.putNumber( "hmiStrafe", strafe );
      if ( isZero( strafe ) )
      {
         return 0.0;
      }
      else
      {
         return adjustStrafeInputs( strafe );
      }
   }


   private double adjustStrafeInputs( double strafe )
   {
      // TWEAK - Flattening response curve of joysticks
      if ( flattenInputs )
      {
         strafe = InputTweaker.exponentiate( strafe );
         strafe = InputTweaker.square( strafe );
      }

      // strafe at full range always

      // final boolean turboEnabled = getTurboEnableButtonPressed();
      // if ( !turboEnabled )
      // {
      // strafe *= nonTurboSpeedScale;
      // }

      return adjustStrafeSign( strafe );
   }


   /**
    * Ensure the sign of the strafe is correct for robot convention; namely
    * "left" is negative, and "right" is positive.
    *
    * @param speed
    * @return
    **/
   private double adjustStrafeSign( double strafe )
   {
      /*
       * Logitech Dual Action Gamepad conforms to convention.
       */
      return strafe;
   }


   private double getTurn()
   {
      final double turn = joystick.getRawAxis( turnAxis );
      smartDashboard.putNumber( "hmiTurn", turn );
      if ( isZero( turn ) )
      {
         return 0.0;
      }
      else
      {
         return adjustTurn( turn );
      }
   }


   private double adjustTurn( double turn )
   {
      // TWEAK - Flattening response curve of thumb sticks
      if ( flattenInputs )
      {
         turn = InputTweaker.exponentiate( turn );
         turn = InputTweaker.square( turn );
      }

      // turn at capped always

      // final boolean turboEnabled = getTurboEnableButtonPressed();
      // if ( !turboEnabled )
      // {
      turn *= nonTurboSpeedScale;
      // }

      return adjustTurnSign( turn );
   }


   /**
    * Ensure the sign of the turn is correct for robot convention; namely "left"
    * is negative, and "right" is positive.
    *
    * @param speed
    * @return
    **/
   private double adjustTurnSign( double turn )
   {
      /*
       * Logitech Dual Action Gamepad conforms to convention.
       */
      return turn;
   }


   private boolean getTurboEnableButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( turboEnableButton );
      return pressed;
   }


   private boolean getFeederToteButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( feederToteButton );
      return pressed;
   }

}
