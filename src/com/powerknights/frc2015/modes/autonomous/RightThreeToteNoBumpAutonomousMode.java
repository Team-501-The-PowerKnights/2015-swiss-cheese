/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.stu
 **/
public class RightThreeToteNoBumpAutonomousMode
   extends ThreeToteAutonomousMode
{

   // How long to let robot drive forward to get to scoring zone
   final protected long drivingTimeToZone = 5500; // msec


   /**
    * @param robot
    **/
   public RightThreeToteNoBumpAutonomousMode( RobotBase robot )
   {
      super( robot );
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.modes.ModeImplementer#runIt()
    */
   @Override
   public void runIt()
   {
      System.out
      .println( "Autonomous with right three tote no bump implementation" );
      lcdManager.logMessage( "Autonomous - Right3Totes" );

      /*
       * Make sure we started correctly / on-time
       */
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         return;
      }

      /*
       * No barrel, so go to floor
       */
      lifter.goTo( LiftPosition.Floor );
      if ( !waitForLiftPosition( LiftPosition.Floor ) )
      {
         lcdManager.logMessage( "Failed to position" );
         handleFailureToComplete();
         return;
      }

      // STUWASHERE - Add delay to get others out of the way
      sleep( 150 );

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Drive to first tote into load position
       */
      if ( !driveToNextTote( toteDrivingSpeed, toteInsideDistance ) )
      {
         return;
      }

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      // Continue to coast
      chassis.startDriving();
      driveTrain.robotDrive( toteCoastingSpeed, 0.0, 0.0 );

      /*
       * Pick up the tote and prepare for forward drive
       */
      if ( !pickUpTote( LiftPosition.Tote1 ) )
      {
         return;
      }

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Drive to second tote into load position
       */
      if ( !driveToNextTote( toteDrivingSpeed, toteInsideDistance ) )
      {
         return;
      }

      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      // Continue to coast
      chassis.startDriving();
      driveTrain.robotDrive( toteCoastingSpeed, 0.0, 0.0 );

      /*
       * Pick up the tote and prepare for forward drive
       */
      if ( !pickUpTote( LiftPosition.Tote1 ) )
      {
         return;
      }

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Drive to third tote into load position
       */
      if ( !driveToNextTote( toteDrivingSpeed, toteInsideDistance ) )
      {
         return;
      }

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Pick up the tote and prepare for forward drive
       */
      if ( !pickUpLastTote( LiftPosition.Travel, true, robotRotatingSpeed ) )
      {
         return;
      }

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Rotate 90 degrees towards autonomous zone
       */
      rotateClockwise( robotRotatingSpeed );
      // driveClockwiseArc( robotRotatingSpeed, drivingTimeToZone );

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Drive forward into autonomous zone
       */
      driveForward( zoneDrivingSpeed, drivingTimeToZone );

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Drop the totes to the floor
       */
      if ( !putDownToteStack() )
      {
         lcdManager.logMessage( "Failed to putDown" );
         handleFailureToComplete();
         return;
      }

      // Did we overrun time for mode?
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         handleFailureToComplete();
         return;
      }

      /*
       * Back up a tad so we aren't supporting stack
       */
      chassis.startDriving();
      driveTrain.robotDrive( -toteDrivingSpeed, 0.0, 0.0 );
      sleep( 1000 );
      driveTrain.stop();
      chassis.stopDriving();

      /*
       * Stop the moving parts
       */
      driveTrain.stop();

      lcdManager.logMessage( "Autonomous - Complete" );
   }

}
