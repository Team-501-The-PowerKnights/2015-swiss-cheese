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
public class LeftThreeToteBumpAutonomousMode
extends ThreeToteAutonomousMode
{

   // How long to let robot drive forward to get to scoring zone
   final protected long drivingTimeToZone = 6500; // msec


   /**
    * @param robot
    **/
   public LeftThreeToteBumpAutonomousMode( RobotBase robot )
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
      .println( "Autonomous with left three tote bump implementation" );
      lcdManager.logMessage( "Autonomous - Left3Totes" );

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

      // STUWASHERE - Add delay to let others get out of the way
      sleep( 1250 );

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
      if ( !pickUpLastTote( LiftPosition.Travel, false, robotRotatingSpeed ) )
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
      rotateCounterClockwise( robotRotatingSpeed );
      // driveCounterClockwiseArc( robotRotatingSpeed, drivingTimeToZone );

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
      sleep( 500 );
      driveTrain.stop();
      chassis.stopDriving();

      /*
       * Stop the moving parts
       */
      driveTrain.stop();

      lcdManager.logMessage( "Autonomous - Complete" );
   }

}
