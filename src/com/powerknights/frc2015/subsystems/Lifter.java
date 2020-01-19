/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.subsystems;


import com.powerknights.frc2015.config.LifterConfig;
import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.managers.RobotManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;
import com.powerknights.frc2015.sensors.LeftLiftHeight;
import com.powerknights.frc2015.sensors.LiftHeight;
import com.powerknights.frc2015.sensors.RightLiftHeight;
import com.powerknights.frc2015.subsystems.pids.IPID;
import com.powerknights.frc2015.subsystems.pids.LeftLiftHeightPID;
import com.powerknights.frc2015.subsystems.pids.RightLiftHeightPID;
import com.powerknights.frc2015.utils.StoppableThread;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;


/**
 * @author first.lizz
 **/
public class Lifter
   extends PWMSubsystem
{

   /** Singleton instance of class for all to use **/
   private static Lifter ourInstance;
   /** Name of our subsystem **/
   private static final String myName = "Lifter";

   /** Speed controller for left side **/
   private final SpeedController leftMotor;
   /** Speed controller for right side **/
   private final SpeedController rightMotor;

   /** Height sensor for left side **/
   private final LiftHeight leftHeight;
   /** Height sensor for right side **/
   private final LiftHeight rightHeight;

   /** (Height) PID for left side **/
   private final IPID leftPID;
   /** (Height) PID for right side **/
   private final IPID rightPID;

   public enum LiftMode
   {
      Stop, Up, Down, PID, Unknown
   };

   /** State of the lifter mode **/
   private LiftMode liftMode;

   public enum LiftPosition
   {
      // @formatter:off
      Floor( 0 ),
      Travel( 500 ),
      Tote1( 1050 ), // 950
      FeederTote( prefsFeederToteHeight  ),
      Tote2( 1870 ),
      Tote3( 1000 ), // not used
      Tote4( 1000 ), // not used
      Maximum( 2350 ),
      NoodleLoad( 0 ),
      Unknown( 1000 );
      // @formatter:on

      private final int position;


      private LiftPosition( int position )
      {
         this.position = position;
      }


      public int getPosition()
      {
         return position;
      }

   }

   /** State of the lifter position **/
   private LiftPosition liftPosition;

   /** State of whether height PID is running **/
   private boolean heightRunning;

   /** Keeps the two sides slaved together via the PID **/
   private final StoppableThread sideSlaver;

   /** Resets the encoder at the bottom each time **/
   private Thread encoderReseter;

   /** Height of lift for feeder station tote **/
   private static final int prefsFeederToteHeight;
   static
   {
      prefsFeederToteHeight =
         PreferencesManager.getInstance().getFeederToteHeight();
      SmartDashboardManager.getInstance().putNumber( "feederToteHeight",
         prefsFeederToteHeight );
   }


   private Lifter()
   {
      System.out.println( "Lifter::constructor()" );

      /*
       * Speed Controllers
       */
      leftMotor = constructLeftMotor();
      rightMotor = constructRightMotor();

      /*
       * Height Sensors
       */
      leftHeight = constructLeftHeight();
      rightHeight = constructRightHeight();

      /*
       * PIDs for Control
       */
      leftPID = constructLeftPID();
      rightPID = constructRightPID();

      /*
       * Side Slaver
       */
      sideSlaver = constructSideSlaver();
      // Start now and just run forever (PIDs control if active or not)
      sideSlaver.start();
   }


   /**
    * Constructs/initializes the left lift speed controller
    *
    * @return constructed and initialized motor
    */
   private SpeedController constructLeftMotor()
   {
      final int channel = LifterConfig.getLeftMotorChannel();
      final Talon sc = new Talon( channel );
      final String component = "leftMotor" + "[" + channel + "]";
      liveWindow.addActuator( myName, component, sc );
      return sc;
   }


   /**
    * Constructs/initializes the right lift speed controller
    *
    * @return constructed and initialized motor
    */
   private SpeedController constructRightMotor()
   {
      final int channel = LifterConfig.getRightMotorChannel();
      final Talon sc = new Talon( channel );
      final String component = "rightMotor" + "[" + channel + "]";
      liveWindow.addActuator( myName, component, sc );
      return sc;
   }


   /**
    * Constructs/initializes the left lift encoder
    *
    * @return constructed and initialized encoder
    */
   private LiftHeight constructLeftHeight()
   {
      final int channelA = LifterConfig.getLeftEncoderChannelA();
      final int channelB = LifterConfig.getLeftEncoderChannelB();
      final boolean reverse = LifterConfig.getLeftEncoderReversing();
      final EncodingType type = LifterConfig.getEncodingType();
      final LiftHeight height =
         new LeftLiftHeight( channelA, channelB, reverse, type );
      final String component =
         "leftHeight" + "[" + channelA + "/" + channelB + "]";
      liveWindow.addSensor( myName, component, height );
      height.reset();
      return height;
   }


   /**
    * Constructs/initializes the left lift encoder
    *
    * @return constructed and initialized encoder
    */
   private LiftHeight constructRightHeight()
   {
      final int channelA = LifterConfig.getRightEncoderChannelA();
      final int channelB = LifterConfig.getRightEncoderChannelB();
      final boolean reverse = LifterConfig.getRightEncoderReversing();
      final EncodingType type = LifterConfig.getEncodingType();
      final LiftHeight height =
         new RightLiftHeight( channelA, channelB, reverse, type );
      final String component =
         "rightHeight" + "[" + channelA + "/" + channelB + "]";
      liveWindow.addSensor( myName, component, height );
      height.reset();
      return height;
   }


   /**
    * Constructs/initializes the left PID for the height control.
    *
    * @return constructed and initialized PID
    **/
   private IPID constructLeftPID()
   {
      final boolean reverse = LifterConfig.getLeftPIDReversing();
      final IPID pid = new LeftLiftHeightPID( leftHeight, leftMotor, reverse );
      return pid;
   }


   /**
    * Constructs/initializes the right PID for the height control.
    *
    * @return constructed and initialized PID
    **/
   private IPID constructRightPID()
   {
      final boolean reverse = LifterConfig.getRightPIDReversing();
      final IPID pid =
         new RightLiftHeightPID( rightHeight, rightMotor, reverse );
      return pid;
   }


   /**
    * Constructs/initializes the slaver for the two height control PIDs.
    *
    * @return constructed and initialized slaver
    **/
   private StoppableThread constructSideSlaver()
   {
      final StoppableThread sideSlaver =
         new LiftSidesSlaver( "LiftSidesSlaver", 10 );
      return sideSlaver;
   }


   /**
    * Constructs instance of the lifter subsystem. Assumed to be called before
    * any usage of the subsystem; and verifies only called once. Allows
    * controlled startup sequencing of the robot and all it's subsystems.
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException( "Lifter Already Constructed" );
      }
      ourInstance = new Lifter();
   }


   /**
    * Returns the singleton instance of the lifter subsystem. If it hasn't been
    * constructed yet, throws an <code>IllegalStateException</code>.
    *
    * @return singleton instance of lifter
    **/
   public static Lifter getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException( "Lifter Not Constructed Yet" );
      }
      return ourInstance;
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.subsystems.ISubsystem#reset()
    */
   @Override
   public void reset()
   {
      stopPID();

      resetEncoderReseter();

      stop();
   }


   /*
    * State Management Section
    */

   private void setLiftMode( LiftMode mode )
   {
      if ( liftMode != mode )
      {
         liftMode = mode;
         smartDashboard.putString( "liftMode", liftMode.name() );
      }
   }


   private void setLiftPosition( LiftPosition position )
   {
      if ( liftPosition != position )
      {
         liftPosition = position;
         smartDashboard.putString( "liftPosition", liftPosition.name() );
      }
   }


   private void setLiftPIDRunning( boolean running )
   {
      heightRunning = running;
      smartDashboard.putBoolean( "liftHeightPID", heightRunning );
      if ( running )
      {
         setLiftMode( LiftMode.PID );
      }
      else
      {
         setLiftMode( LiftMode.Unknown );
      }
      setMotorsRunning( running, running );
   }


   private boolean isLiftPIDRunning()
   {
      return heightRunning;
   }


   private boolean isLiftPIDStopped()
   {
      return !heightRunning;
   }


   private void setMotorsRunning( boolean leftMotor, boolean rightMotor )
   {
      setLeftMotorRunning( leftMotor );
      setRightMotorRunning( rightMotor );
   }


   private void setLeftMotorRunning( boolean motor )
   {
      smartDashboard.putBoolean( "leftLiftMotor", motor );
   }


   private void setRightMotorRunning( boolean motor )
   {
      smartDashboard.putBoolean( "rightLiftMotor", motor );
   }


   private void setEncoderReseter( Thread reseter )
   {
      encoderReseter = reseter;
      smartDashboard.putBoolean( "encoderReseter", true );
   }


   private void resetEncoderReseter()
   {
      encoderReseter = null;
      smartDashboard.putBoolean( "encoderReseter", false );
   }


   private boolean isEncoderReseterRunning()
   {
      return ( encoderReseter != null );
   }


   /*
    * (Height) PID Section
    */

   private void stopPID()
   {
      leftPID.stop();
      rightPID.stop();
      setLiftPIDRunning( false );
      setLiftPosition( LiftPosition.Unknown );
   }


   private void ensurePIDStopped()
   {
      if ( isLiftPIDRunning() )
      {
         stopPID();
      }
   }


   private void ensurePIDRunning()
   {
      if ( !prefsManager.usePIDHolds() )
      {
         ensurePIDStopped();
         return;
      }

      if ( isLiftPIDStopped() )
      {
         leftPID.start();
         rightPID.start();
         setLiftPIDRunning( true );
         setLiftPosition( LiftPosition.Unknown );
      }
   }


   private void ensurePIDRunning( LiftPosition position )
   {
      ensurePIDRunning( position.getPosition() );
      setLiftPosition( position );
   }


   private void ensurePIDRunning( double setPoint )
   {
      if ( !prefsManager.usePIDHolds() )
      {
         ensurePIDStopped();
         return;
      }

      if ( isLiftPIDStopped() )
      {
         leftPID.start();
         rightPID.start();
         setLiftPIDRunning( true );
         setLiftPosition( LiftPosition.Unknown );
      }

      leftPID.setSetPoint( setPoint );
      // rightPID.setSetPoint( setPoint );
   }


   /*
    *
    */

   public void hold()
   {
      ensurePIDRunning();
   }


   public void releaseHold()
   {
      ensurePIDStopped();
   }

   /*
    *
    */

   private class LiftSidesSlaver
      extends StoppableThread
   {

      /**
       * @param name
       * @param delay
       **/
      public LiftSidesSlaver( String name, long delay )
      {
         super( name, delay );
      }


      /*
       * (non-Javadoc)
       * 
       * @see com.powerknights.frc2015.utils.StoppableThread#doIt()
       */
      @Override
      public boolean doIt()
      {
         // Left & right have opposite signs
         final long height = leftHeight.get();
         rightPID.setSetPoint( -height );

         return false;
      }
   }


   /**
    * Set the height of the lift to one of the fixed, pre-defined points. The
    * PID is used to make this happen, and the lift will stay after it reaches
    * it.
    *
    * @param position
    **/
   public void goTo( LiftPosition position )
   {
      if ( position == LiftPosition.Floor )
      {
         goToFloor();
      }
      else
      {
         ensurePIDRunning( position );
      }
   }


   /**
    * Set the height of the lift to the floor position, which entails a reset of
    * the encoders in order to make sure they stay aligned during the course of
    * the match.
    **/
   private synchronized void goToFloor()
   {
      if ( !prefsManager.useLifterEncoderReset() )
      {
         ensurePIDRunning( LiftPosition.Floor );
      }
      else
      {
         if ( isEncoderReseterRunning() )
         {
            System.out.println( "We are already running; so ignore" );
            return;
         }

         encoderReseter = new Thread( new LowerAndResetEncoder() );
         setEncoderReseter( encoderReseter );
         encoderReseter.start();
      }
   }


   /**
    * Return indication of whether the lifter has reached / is currently in the
    * position specified. An approximation is used to account for the
    * differences in one side to the other from the PID slaving, as well as the
    * fact it might not settle right at the commanded height.
    *
    * @param position lift position to check
    * @return <code>true</code> if the current position matches;
    *         <code>false</code> otherwise
    **/
   public boolean isInPosition( LiftPosition position )
   {
      final int deltaToBeEqual = (int) ( 0.05 * 3500 ); // 5% of total range

      final int target = position.getPosition();

      final int leftCurrent = leftHeight.get();
      final int rightCurrent = rightHeight.get();
      final int current =
         ( Math.abs( leftCurrent ) + Math.abs( rightCurrent ) ) / 2;

      final boolean inPosition = Math.abs( target - current ) < deltaToBeEqual;
      // System.out.println( "Lifter::isInPosition() = " + inPosition );
      return inPosition;
   }


   /**
    * Return indication of whether the lifter has reached / is in the last
    * position it was commanded to go to.
    *
    * @return <code>true</code> if the current position matches;
    *         <code>false</code> otherwise.
    **/
   public boolean isInPosition()
   {
      return isInPosition( liftPosition );
   }


   /**
    * Force a reset of the encoder positions to 'zero' (so the 'floor' becomes
    * wherever the position of the arms are at this point).
    **/
   public void resetPositions()
   {
      leftHeight.reset();
      rightHeight.reset();

      ensurePIDRunning( LiftPosition.Floor );
   }

   /**
    *
    *
    **/
   private class LowerAndResetEncoder
      implements Runnable
   {

      /** Speed to send the lift down at **/
      private final double downSpeed = 0.02;
      /** Sleep time between updates **/
      private final long updateDelay = 40; // msec
      /** How long to wait before assuming it failed and bailing **/
      private final long timeOut = 5000; // msec
      /** **/
      private final double sameScaleFactor = 0.05; // %
      /** **/
      private final long sameCountToBeEqual = 3;

      /** **/
      private final RobotManager robot;


      public LowerAndResetEncoder()
      {
         robot = RobotManager.getInstance();
      }


      /*
       * (non-Javadoc)
       * 
       * @see java.lang.Runnable#run()
       */
      @Override
      public void run()
      {
         // Release any running PID so we can move it
         releaseHold();

         // Know when to stop
         boolean leftStopped = false;
         long leftSameCount = 0;
         boolean rightStopped = false;
         long rightSameCount = 0;

         // Get current position ...
         long lastLeftCount = Math.abs( leftHeight.get() );
         long lastRightCount = Math.abs( rightHeight.get() );
         // ... and start the lift going down
         down( downSpeed );

         final long processTime = System.currentTimeMillis() + timeOut;
         boolean done = false;
         while ( robot.isEnabled() && !done )
         {
            if ( !leftStopped )
            {
               final long leftCount = Math.abs( leftHeight.get() );
               if ( heightsEqual( leftCount, lastLeftCount ) )
               {
                  leftSameCount++;
               }
               if ( leftSameCount >= sameCountToBeEqual )
               {
                  stopLeftMotor();
                  leftHeight.reset();
                  leftStopped = true;
               }
               else
               {
                  lastLeftCount = leftCount;
               }
            }

            if ( !rightStopped )
            {
               final long rightCount = Math.abs( rightHeight.get() );
               if ( heightsEqual( rightCount, lastRightCount ) )
               {
                  rightSameCount++;
               }
               if ( rightSameCount >= sameCountToBeEqual )
               {
                  stopRightMotor();
                  rightHeight.reset();
                  rightStopped = true;
               }
               else
               {
                  lastRightCount = rightCount;
               }
            }

            if ( leftStopped && rightStopped )
            {
               done = true;
               continue;
            }

            try
            {
               Thread.sleep( updateDelay );
            }
            catch ( final InterruptedException ex )
            {
               // Don't care
            }

            if ( System.currentTimeMillis() > processTime )
            {
               System.out.println( "***** Failed to complete on time" );
               done = true;

               // TODO - Fix the failure conditions
               stopLeftMotor();
               stopRightMotor();
            }
         }

         ensurePIDRunning( LiftPosition.Floor );

         resetEncoderReseter();
      }


      private boolean heightsEqual( long current, long previous )
      {
         System.out.println( "previous: " + previous + ", current: " + current );
         return current < ( previous * sameScaleFactor );
      }

   }


   /**
    * Raise the totes in lift. Negative values, which would normally imply
    * lowering, are normalized to a positive value first - so the lift will
    * always go up.
    *
    * Stopping the lift is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void up( double speed )
   {
      if ( travelMaxLimitsExceeded() )
      {
         hold(); // PID lowers to max position
         return;
      }

      releaseHold();
      setLiftMode( LiftMode.Up );

      speed = ensurePositiveSpeed( speed );

      setMotorSpeeds( fixLeftPolarity( speed ), fixRightPolarity( speed ) );
   }


   /**
    * Lower the totes in lift. Positive values, which would normally imply
    * lifting, are normalized to a negative value first - so the lift will
    * always go down.
    *
    * Stopping the lift is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void down( double speed )
   {
      if ( travelMinLimitsExceeded() )
      {
         hold(); // PID raises to min position
         return;
      }

      releaseHold();
      setLiftMode( LiftMode.Down );

      speed = ensureNegativeSpeed( speed );

      setMotorSpeeds( fixLeftPolarity( speed ), fixRightPolarity( speed ) );
   }


   private boolean travelMinLimitsExceeded()
   {
      // TODO - Implement this
      return false;
   }


   private boolean travelMaxLimitsExceeded()
   {
      // TODO - Implement this
      return false;
   }


   /**
    * Fixes the polarity of the speeds used on the actual wired motors in order
    * to conform to the input convention that "+" (positive) is up and "-"
    * (negative) is down relative to lifter.
    *
    * @param speed
    * @return
    **/
   private double fixLeftPolarity( double speed )
   {
      // This is where you change the sign to fix polarity of lift if necessary
      // Left is + up and - down
      return speed;
   }


   /**
    * Fixes the polarity of the speeds used on the actual wired motors in order
    * to conform to the input convention that "+" (positive) is "up" and "-"
    * (negative) is "down" relative to lifter.
    *
    * @param speed
    * @return
    **/
   private double fixRightPolarity( double speed )
   {
      // This is where you change the sign to fix polarity of lift if necessary
      // Right is - up and + down
      return -speed;
   }


   /**
    * Stop the lifter moving.
    **/
   public void stop()
   {
      releaseHold();
      setLiftMode( LiftMode.Stop );

      // Do not use "stopMotor" method

      setMotorSpeeds( 0.0, 0.0 );
   }


   private void stopLeftMotor()
   {
      setLeftMotorSpeed( 0.0 );
   }


   private void stopRightMotor()
   {
      setRightMotorSpeed( 0.0 );
   }


   /**
    * Sets the speed / inputs to the actual hardware speed controllers for the
    * left and right lifters. Includes update to the dashboard.
    *
    * @paramleftSpeed - left side setting for speed controller(s)
    * @param rightSpeed - right side setting for speed controller(s)
    **/
   private void setMotorSpeeds( double leftSpeed, double rightSpeed )
   {
      smartDashboard.putNumber( "liftLeftSpeed", leftSpeed );
      smartDashboard.putNumber( "liftRightSpeed", rightSpeed );
      setMotorsRunning( !isZero( leftSpeed ), !isZero( rightSpeed ) );

      // HACKALERT - Switching speed sign at the last minute
      leftMotor.set( -leftSpeed );
      rightMotor.set( -rightSpeed );
   }


   private void setLeftMotorSpeed( double speed )
   {
      smartDashboard.putNumber( "liftLeftSpeed", speed );
      setLeftMotorRunning( !isZero( speed ) );
      // HACKALERT - Switching speed sign at the last minute
      leftMotor.set( -speed );
   }


   private void setRightMotorSpeed( double speed )
   {
      smartDashboard.putNumber( "liftRightSpeed", speed );
      setRightMotorRunning( !isZero( speed ) );
      // HACKALERT - Switching speed sign at the last minute
      rightMotor.set( -speed );
   }

}
