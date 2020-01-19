/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.subsystems.Chassis;
import com.powerknights.frc2015.subsystems.DriveTrain;
import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.stu
 **/
public abstract class ThreeToteAutonomousMode
extends LifterAutonomousMode
{

   // Distance from back of robot to tote end to consider "inside" to load
   final protected double toteInsideDistance = 0.5; // ft
   // Value to use for forward driving between totes
   final protected double toteDrivingSpeed = 0.40;
   // Value to use for 'coasting' at tote pickup
   final protected double toteCoastingSpeed = 0.15;
   // Value to use for driving to zone
   final protected double zoneDrivingSpeed = 0.30;

   // Value to use for rotating to face scoring zone
   final protected double robotRotatingSpeed = 0.35;
   // Value to use for timed rotation (until we get gyro)
   final protected long robotRotatingTime = 2000; // msec

   /** Handle to the drive **/
   protected final DriveTrain driveTrain;
   /** Handle to the chassis **/
   protected final Chassis chassis;


   /**
    * @param robot
    **/
   public ThreeToteAutonomousMode( RobotBase robot )
   {
      super( robot );

      driveTrain = DriveTrain.getInstance();
      chassis = Chassis.getInstance();
   }


   protected void handleFailureToComplete()
   {
      chassis.stopDriving();
      // STUWASHERE - What to do with lifter?
   }


   /**
    * Drives the robot to the next tote, as defined by the distance offset.
    *
    * @param speed speed to drive at
    * @param distanceOffset when to stop assuming close enough
    * @return
    **/
   protected boolean driveToNextTote( double speed, double distanceOffset )
   {
      System.out.println( "ThreeToteAutonomousMode::driveToNextTote()" );

      final long waitForNextToteTimeout = 4000; // msec

      chassis.startDriving();
      driveTrain.robotDrive( speed, 0.0, 0.0 );

      long time = 0;
      while ( isActive() && ( chassis.getDistanceToTote() > distanceOffset )
         && ( time < waitForNextToteTimeout ) )
      {
         sleep( 10 );
         time += 10;
      }

      // stop moving parts
      driveTrain.robotDrive( -0.10, 0.0, 0.0 );
      chassis.stopDriving();

      if ( time >= waitForNextToteTimeout )
      {
         lcdManager.logMessage( "Failed to position" );
         handleFailureToComplete();
         smartDashboard.putTimeout( true, "driveToNextTote()" );
         return false;
      }
      return true;
   }


   protected void driveForward( double speed, long driveTime )
   {
      System.out.println( "ThreeToteAutonomousMode::driveForward()" );

      driveTrain.robotDrive( speed, 0.0, 0.0 );
      sleep( driveTime );

      driveTrain.stop();
   }


   protected void rotateClockwise( double speed )
   {
      System.out.println( "ThreeToteAutonomousMode::rotateClockwise()" );

      final long waitForRotateTimeout = 3500; // msec

      System.out.println( "gyro@start: " + chassis.getOrientation() );
      driveTrain.robotDrive( 0.0, 0.0, speed );
      sleep( robotRotatingTime );
      System.out.println( "gyro@end:   " + chassis.getOrientation() );

      // double heading = chassis.getOrientation();
      // double target = heading + 90.0;
      // long time = 0;
      // while ( ( chassis.getOrientation() <= target )
      // && ( time < waitForRotateTimeout ) )
      // {
      // sleep( 10 );
      // time += 10;
      // }

      driveTrain.stop();
   }


   protected void rotateCounterClockwise( double speed )
   {
      System.out.println( "ThreeToteAutonomousMode::rotateCounterClockwise()" );

      final long waitForRotateTimeout = 3500; // msec

      System.out.println( "gyro@start: " + chassis.getOrientation() );
      driveTrain.robotDrive( 0.0, 0.0, -speed );
      sleep( robotRotatingTime );
      System.out.println( "gyro@end:   " + chassis.getOrientation() );

      // double heading = chassis.getOrientation();
      // double target = heading - 90.0;
      // long time = 0;
      // while ( ( chassis.getOrientation() >= target )
      // && ( time < waitForRotateTimeout ) )
      // {
      // sleep( 10 );
      // time += 10;
      // }

      driveTrain.stop();
   }


   protected void driveClockwiseArc( double speed, long driveTime )
   {
      System.out.println( "ThreeToteAutonomousMode::driveClockwiseArc()" );

      System.out.println( "gyro@start: " + chassis.getOrientation() );
      driveTrain.robotDrive( speed, 0.0, speed );
      sleep( driveTime );
      System.out.println( "gyro@end:   " + chassis.getOrientation() );

      driveTrain.stop();
   }


   protected void driveCounterClockwiseArc( double speed, long driveTime )
   {
      System.out
         .println( "ThreeToteAutonomousMode::driveCounterClockwiseArc()" );

      System.out.println( "gyro@start: " + chassis.getOrientation() );
      driveTrain.robotDrive( speed, 0.0, -speed );
      sleep( driveTime );
      System.out.println( "gyro@end:   " + chassis.getOrientation() );

      driveTrain.stop();
   }


   /**
    * Picks up the tote and then lifts to load position.
    *
    * @param finish the desired final position of tote lifter
    * @return <code>true</code> if tote pick up cycle completes;
    *         <code>false</code> if not
    **/
   protected boolean pickUpTote( LiftPosition finish )
   {
      System.out.println( "ThreeToteAutonomousMode::pickUpTote()" );

      lifter.goTo( LiftPosition.Floor );
      if ( !waitForLiftPosition( LiftPosition.Floor ) )
      {
         lcdManager.logMessage( "Failed to position" );
         smartDashboard.putTimeout( true, "pickUpTote()" );
         handleFailureToComplete();
         return false;
      }

      lifter.goTo( finish );
      if ( !waitForLiftPosition( finish ) )
      {
         lcdManager.logMessage( "Failed to position" );
         smartDashboard.putTimeout( true, "pickUpTote()" );
         handleFailureToComplete();
         return false;
      }

      return true;
   }


   /**
    * Picks up the tote and then lifts to load position.
    *
    * @param finish the desired final position of tote lifter
    * @return <code>true</code> if tote pick up cycle completes;
    *         <code>false</code> if not
    **/
   protected boolean pickUpLastTote( LiftPosition finish, boolean isClockwise,
      double robotRotatingSpeed )
   {
      System.out.println( "ThreeToteAutonomousMode::pickUpTote()" );

      lifter.goTo( LiftPosition.Floor );
      if ( !waitForLiftPosition( LiftPosition.Floor ) )
      {
         lcdManager.logMessage( "Failed to position" );
         smartDashboard.putTimeout( true, "pickUpTote()" );
         handleFailureToComplete();
         return false;
      }

      if ( isClockwise )
      {
         driveTrain.robotDrive( 0.0, 0.0, robotRotatingSpeed );
      }
      else
      {
         driveTrain.robotDrive( 0.0, 0.0, -robotRotatingSpeed );
      }

      lifter.goTo( finish );
      if ( !waitForLiftPosition( finish ) )
      {
         lcdManager.logMessage( "Failed to position" );
         smartDashboard.putTimeout( true, "pickUpTote()" );
         handleFailureToComplete();
         return false;
      }

      return true;
   }


   /**
    * Puts down the tote stack.
    *
    * @return <code>true</code> if tote drop cycle completes; <code>false</code>
    *         if not
    **/
   protected boolean putDownToteStack()
   {
      System.out.println( "ThreeToteAutonomousMode::putDownToteStack()" );

      lifter.goTo( LiftPosition.Floor );
      if ( !waitForLiftPosition( LiftPosition.Floor ) )
      {
         lcdManager.logMessage( "Failed to position" );
         smartDashboard.putTimeout( true, "putDownToteStack()" );
         handleFailureToComplete();
         return false;
      }

      return true;
   }

}
