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


import com.powerknights.frc2015.config.ChassisConfig;
import com.powerknights.frc2015.sensors.RobotOrientation;
import com.powerknights.frc2015.sensors.ToteDistance;


/**
 * @author first.stu
 **/
public class Chassis
extends PWMSubsystem
{

   public enum DriveMode
   {
      Stop, Straight, Rotate, Drive, Unknown
   };

   /** State of the chassis subsystem **/
   private DriveMode driveMode;

   /** Singleton instance of class for all to use **/
   private static Chassis ourInstance;
   /** Name of our subsystem **/
   private static final String myName = "Chassis";

   /** Handle to drive subsystem **/
   private final DriveTrain drive;

   /** Range finder for distance **/
   private final ToteDistance toteDistance;
   /** Gyro for orientation **/
   private final RobotOrientation orientation;

   private boolean driving;
   private boolean started;


   private Chassis()
   {
      System.out.println( "Chassis::constructor()" );

      // Assumes that drive is constructed first
      drive = DriveTrain.getInstance();

      toteDistance = constructToteDistance();
      orientation = constructRobotOrientation();
   }


   /**
    * Constructs/initializes the distance ultrasonic sensor
    *
    * @return constructed and initialized sensor
    */
   private ToteDistance constructToteDistance()
   {
      final int channelIn = ChassisConfig.getUltrasonicRangeChannelIn();
      final int channelOut = ChassisConfig.getUltrasonicRangeChannelOut();
      final ToteDistance distance = new ToteDistance( channelIn, channelOut );

      final String component =
         "toteDistance" + "[" + channelIn + "," + channelOut + "]";
      liveWindow.addSensor( myName, component, distance );
      return distance;
   }


   /**
    * Constructs/initializes the robot / chassis orientation sensor
    *
    * @return constructed and initialized sensor
    */
   private RobotOrientation constructRobotOrientation()
   {
      final int channel = ChassisConfig.getGyroChannel();
      final RobotOrientation orientation = new RobotOrientation( channel );

      final String component = "chassisOrient" + "[" + channel + "]";
      liveWindow.addSensor( myName, component, orientation );
      return orientation;
   }


   /**
    * Constructs instance of the chassis subsystem. Assumed to be called before
    * any usage of the subsystem; and verifies only called once. Allows
    * controlled startup sequencing of the robot and all it's subsystems.
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException( "Chassis Already Constructed" );
      }
      ourInstance = new Chassis();
   }


   /**
    * Returns the singleton instance of the chassis subsystem. If it hasn't been
    * constructed yet, throws an <code>IllegalStateException</code>.
    *
    * @return singleton instance of chassis
    **/
   public static Chassis getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException( "Chassis Not Constructed Yet" );
      }
      return ourInstance;
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2013.subsystems.ISubsystem#reset()
    */
   @Override
   public void reset()
   {
      stopDriving();
   }


   /**
    *
    * @return
    **/
   public double getDistanceToTote()
   {
      final double distance = toteDistance.get();
      smartDashboard.putNumber( "toteDistance", distance );
      return distance;
   }


   /**
    * @return
    **/
   public double getOrientation()
   {
      final double angle = orientation.get();
      smartDashboard.putNumber( "chassisOrient", angle );
      return angle;
   }


   /*
    * State Management Section
    */

   private void setDriveMode( DriveMode mode )
   {
      if ( driveMode != mode )
      {
         driveMode = mode;
         smartDashboard.putString( "driveMode", driveMode.name() );
      }
   }


   private void stop()
   {
      drive.stop();
   }


   public synchronized void startDriving()
   {
      setDriving( true );
      setStarted( false );
   }


   public boolean isDriving()
   {
      return driving;
   }


   private void setDriving( boolean driving )
   {
      this.driving = driving;
      smartDashboard.putBoolean( "driving", driving );
   }


   private boolean isStarted()
   {
      return started;
   }


   private void setStarted( boolean started )
   {
      this.started = started;
      smartDashboard.putBoolean( "started", started );
   }


   public synchronized void stopDriving()
   {
      stop();
      setDriving( false );
      setStarted( false );
   }


   /**
    * Sets the individual speeds (left and right) on the underlying
    * <code>DriveTrain</code> itself. Assumes the relative values and signs have
    * been correctly determined to conform to the <code>DriveTrain</code>
    * conventions. This is currently "positive" values going forward, and
    * "negative" values going backwards.
    *
    * @param leftSpeed
    * @param rightSpeed
    **/
   private void setDriveSpeeds( double leftSpeed, double rightSpeed )
   {
      // drive.drive( leftSpeed, rightSpeed );
      drive.robotDrive( leftSpeed, 0.0, 0.0 );
   }


   /**
    * Drives the robot in an 'arc', with a combination of forward direction and
    * rotation. Convention for input is "positive" values go forward, and
    * "negative" values go backwards.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param leftSpeed
    * @param rightSpeed
    **/
   public void drive( double leftSpeed, double rightSpeed )
   {
      setDriveSpeeds( leftSpeed, rightSpeed );
   }


   /**
    * Drive the robot in a 'straight line', with no turn. Convention for input
    * is "positive" values go forward, and "negative" values go backwards.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void drive( double speed )
   {
      if ( speed >= 0.0 )
      {
         driveForward( speed );
      }
      else
      {
         driveBackward( speed );
      }
   }


   /**
    * Drive the robot forward, in a 'straight line' with no turn. Negative
    * values, which would normally imply driving backwards, are normalized to a
    * positive value first - so the robot will always go forward.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void driveForward( double speed )
   {
      speed = ensurePositiveSpeed( speed );

      driveStraight( speed );
   }


   /**
    * Drive the robot backward, in a 'straight line' with no turn. Positive
    * values, which would normally imply driving forwards, are normalized to a
    * negative value first - so the robot will always go backward.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void driveBackward( double speed )
   {
      speed = ensureNegativeSpeed( speed );

      driveStraight( speed );
   }


   /**
    * Drive the robot in a straight line, where the left and right speeds are
    * locked together. Convention for input is "positive" values go forward, and
    * "negative" values go backwards.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   private void driveStraight( double speed )
   {
      if ( !isDriving() )
      {
         // System.err.println( "Chassis: driving not started / enabled" );
         return;
      }

      if ( !isStarted() )
      {
         setStarted( true );
         setDriveMode( DriveMode.Straight );
      }
      setDriveSpeeds( speed, speed );
   }


   /**
    * Rotate the robot about the centerpoint, by turning in-place. The direction
    * of rotation is counter-clockwise as viewed from above. The same speed is
    * used to drive both sides, just in opposite direction of travel.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void rotateCCW( double speed )
   {
      setDriveMode( DriveMode.Rotate );

      // left backward, right forward
      setDriveSpeeds( ensureNegativeSpeed( speed ), ensurePositiveSpeed( speed ) );
   }


   /**
    * Rotate the robot about the centerpoint, by turning in-place. The direction
    * of rotation is clockwise as viewed from above. The same speed is used to
    * drive both sides, just in opposite direction of travel.
    *
    * Stopping the robot is not handled by this method, unless by chance.
    *
    * @param speed
    **/
   public void rotateCW( double speed )
   {
      setDriveMode( DriveMode.Rotate );

      // left forward, right backward
      setDriveSpeeds( ensurePositiveSpeed( speed ), ensureNegativeSpeed( speed ) );
   }


   public void goForward( double speed, double distance )
   {
      setDriveMode( DriveMode.Straight );

      speed = ensurePositiveSpeed( speed );
      // TODO - Implement goForward() in DriveTrain
      System.err.println( "goForward not implemented" );
   }


   public void goBackward( double speed, double distance )
   {
      setDriveMode( DriveMode.Straight );

      speed = ensureNegativeSpeed( speed );
      // TODO - Implement goBackward() in DriveTrain
      System.err.println( "goBackward not implemented" );
   }

}
