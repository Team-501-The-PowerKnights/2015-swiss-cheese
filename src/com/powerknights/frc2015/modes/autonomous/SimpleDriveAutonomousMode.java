/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;


/**
 * @author first.stu
 **/
public class SimpleDriveAutonomousMode
   extends AutonomousMode
{

   /** Handle to the drive **/
   private final DriveTrain driveTrain;


   /**
    * @param robot
    **/
   public SimpleDriveAutonomousMode( RobotBase robot )
   {
      super( robot );

      driveTrain = DriveTrain.getInstance();
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.modes.ModeImplementer#runIt()
    */
   @Override
   public void runIt()
   {
      System.out.println( "Autonomous with simple drive forward" );
      lcdManager.logMessage( "Autonomous - DriveOnly" );

      /*
       *
       */
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         return;
      }

      /*
       * Drive forward
       */
      driveTrain.robotDrive( 0.30, 0.0, 0.0 );
      Timer.delay( 3.0 );

      /*
       * Stop the moving parts
       */
      driveTrain.robotDrive( -0.10, 0.0, 0.0 );
      driveTrain.stop();

      lcdManager.logMessage( "Autonomous - Complete" );
   }

}
