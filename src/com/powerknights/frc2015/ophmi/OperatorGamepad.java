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
import com.powerknights.frc2015.subsystems.Lifter;
import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.sam
 **/
public class OperatorGamepad
extends OperatorBaseGamepad
{

   /** Handle to the lifter **/
   private final Lifter lifter;

   /** 'Index' of the speed input device axis **/
   private final int speedAxis;
   /** 'Index' of the turbo enable bumper **/
   private final int turboEnableButton;

   /** 'Index' of the tote position POV **/
   private final int totePositionPOV;
   /** Flag to de-bounce the position POV **/
   private int lastTotePositionPOV;
   /** State of the lifter position **/
   private LiftPosition totePosition;

   /** 'Index' of the Floor position button **/
   private final int floorButton;
   /** Flag to de-bounce the Floor position button **/
   private boolean lastFloorButton;
   /** 'Index' of the Tote 1 position button **/
   private final int tote1Button;
   /** Flag to de-bounce the Tote 1 position button **/
   private boolean lastTote1Button;
   /** 'Index' of the maximum tote position button **/
   private final int maxToteButton;
   /** Flag to de-bounce the maximum tote position button **/
   private boolean lastMaxToteButton;

   /** 'Index' of the feeder station tote position button **/
   private final int feederToteButton;
   /** Flag to de-bounce the feeder tote position button **/
   private boolean lastFeederToteButton;

   /** 'Index' of the manual / forced lifter position reset button **/
   private final int lifterResetButton;
   /** Flag to de-bounce the manual / forced lifter position reset button **/
   private boolean lastLifterResetButton;

   /** **/
   private double nonTurboSpeedScale; // comes from I/O on laptop
   /** **/
   private boolean flattenInputs; // comes from I/O on laptop


   public OperatorGamepad( Joystick joystick, GamepadModels model )
   {
      super( joystick, model );
      System.out.println( "OperatorGamepad::contructor() for " + model );

      lifter = Lifter.getInstance();

      speedAxis = getSpeedAxis();
      turboEnableButton = getTurboEnableButton();

      totePositionPOV = getTotePositionPOV();

      floorButton = getFloorButton();
      tote1Button = getTote1Button();
      maxToteButton = getMaxToteButton();

      feederToteButton = getFeederToteButton();

      lifterResetButton = getLifterResetButton();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2013.ophmi.IHmiController#initForModeStart()
    */
   @Override
   public void initForModeStart()
   {
      initState();

      nonTurboSpeedScale =
         PreferencesManager.getInstance().getScaledOperatorNonTurboSpeed();
      flattenInputs =
         PreferencesManager.getInstance().doFlattenDriverInputResponse();
   }


   private void initState()
   {
      setTotePosition( LiftPosition.Unknown );
      lastTotePositionPOV = -1;

      lastFloorButton = false;
      lastTote1Button = false;
      lastMaxToteButton = false;

      lastFeederToteButton = false;

      lastLifterResetButton = false;
   }


   /*
    * State Management Section
    */

   private void setTotePosition( LiftPosition position )
   {
      if ( totePosition != position )
      {
         totePosition = position;
         smartDashboard.putString( "hmiTotePosition", totePosition.name() );
      }
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2013.ophmi.IHmiController#performUpdate()
    */
   @Override
   public void performUpdate()
   {
      // Must come first to determine if we are doing it
      processLiftPosition();

      processButtons();
   }


   /**
    *
    **/
   private void processLiftPosition()
   {
      /*
       * Process the manual control first
       */

      final double speed = getSpeed();
      final boolean zeroSpeed = isZero( speed );
      if ( zeroSpeed )
      {
         lifter.hold();
      }
      else
      {
         lifter.releaseHold();
         if ( isPositive( speed ) )
         {
            lifter.up( speed );
         }
         else
         {
            lifter.down( speed );
         }

         // Don't continue with fixed points if manually driving it
         setTotePosition( LiftPosition.Unknown );
         return;
      }

      /*
       * Process the game play next
       */

      LiftPosition position = LiftPosition.Unknown;

      final int totePositionPOV = getTotePositionPOVDegrees();
      if ( totePositionPOV != lastTotePositionPOV )
      {
         if ( totePositionPOV == 0 )
         {
            position = LiftPosition.Tote2;
         }
         else if ( totePositionPOV == 180 )
         {
            position = LiftPosition.Travel;
         }
         // Keep the last state to de-bounce
         lastTotePositionPOV = totePositionPOV;
      }

      boolean toteButtonPressed;

      toteButtonPressed = getFloorButtonPressed();
      if ( toteButtonPressed != lastFloorButton )
      {
         if ( toteButtonPressed )
         {
            position = LiftPosition.Floor;
         }
         // Keep the last state to de-bounce
         lastFloorButton = toteButtonPressed;
      }
      toteButtonPressed = getTote1ButtonPressed();
      if ( toteButtonPressed != lastTote1Button )
      {
         if ( toteButtonPressed )
         {
            position = LiftPosition.Tote1;
         }
         // Keep the last state to de-bounce
         lastTote1Button = toteButtonPressed;
      }
      toteButtonPressed = getMaxToteButtonPressed();
      if ( toteButtonPressed != lastMaxToteButton )
      {
         if ( toteButtonPressed )
         {
            position = LiftPosition.Maximum;
         }
         // Keep the last state to de-bounce
         lastMaxToteButton = toteButtonPressed;
      }

      toteButtonPressed = getFeederToteButtonPressed();
      if ( toteButtonPressed != lastFeederToteButton )
      {
         if ( toteButtonPressed )
         {
            position = LiftPosition.FeederTote;
         }
         // Keep the last state to de-bounce
         lastFeederToteButton = toteButtonPressed;
      }

      if ( position != LiftPosition.Unknown )
      {
         if ( !lifter.isInPosition( position ) )
         {
            goToTotePosition( position );
         }
      }
   }


   void goToTotePosition( LiftPosition position )
   {
      lifter.goTo( position );
      setTotePosition( position );
   }


   /**
    * @return
    **/

   private final double getSpeed()
   {
      final double speed = joystick.getRawAxis( speedAxis );
      smartDashboard.putNumber( "hmiLiftSpeed", speed );
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
      smartDashboard.putBoolean( "turboLiftBoost", turboEnabled );
      if ( !turboEnabled )
      {
         speed *= nonTurboSpeedScale;
      }

      return adjustSpeedSign( speed );
   }


   /**
    * Ensure the sign of the speed is correct for robot convention; namely "up"
    * is positive, and "down" is negative.
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


   private void processButtons()
   {
      boolean buttonPressed;

      buttonPressed = getLifterResetButtonPressed();
      if ( buttonPressed != lastLifterResetButton )
      {
         if ( buttonPressed )
         {
            lifter.resetPositions();
            setTotePosition( LiftPosition.Floor );
         }
         // Keep the last state to de-bounce
         lastLifterResetButton = buttonPressed;
      }
   }


   private boolean getTurboEnableButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( turboEnableButton );
      return pressed;
   }


   private int getTotePositionPOVDegrees()
   {
      final int totePOVDegrees = joystick.getPOV( totePositionPOV );
      return totePOVDegrees;
   }


   private boolean getFloorButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( floorButton );
      return pressed;
   }


   private boolean getTote1ButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( tote1Button );
      return pressed;
   }


   private boolean getMaxToteButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( maxToteButton );
      return pressed;
   }


   private boolean getFeederToteButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( feederToteButton );
      return pressed;
   }


   private boolean getLifterResetButtonPressed()
   {
      final boolean pressed = joystick.getRawButton( lifterResetButton );
      return pressed;
   }

}
