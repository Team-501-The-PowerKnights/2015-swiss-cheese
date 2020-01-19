/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.subsystems.DriveTrain;
import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;


/**
 * @author first.stu
 **/
public class BarrelOnlyBumpAutonomousMode
   extends LifterAutonomousMode
{

   /** Handle to the drive **/
   private final DriveTrain driveTrain;


   /**
    * @param robot
    **/
   public BarrelOnlyBumpAutonomousMode( RobotBase robot )
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
      System.out.println( "Autonomous with barrel only, bump implementation" );
      lcdManager.logMessage( "Autonomous - BarrelBump" );

      /*
       *
       */
      if ( !isActive() )
      {
         lcdManager.logMessage( "Failed to complete" );
         return;
      }

      final PreferencesManager prefs = PreferencesManager.getInstance();

      /*
       * Lift to first tote position to knock over barrel
       */
      lifter.goTo( LiftPosition.Tote1 );
      // STUWASHERE Lifter wait vs. timer delay
      // Timer.delay( 1.0 );
      waitForLiftPosition( LiftPosition.Tote1 );

      /*
       * Drive forward
       */
      driveTrain.robotDrive( 0.30, 0.0, 0.0 );
      @SuppressWarnings( "unused" )
      final double delay = prefs.getAutoDriveForwardTime();
      Timer.delay( 6.5 );

      /*
       * Back off tote
       */
      driveTrain.robotDrive( -0.30, 0.0, 0.0 );
      Timer.delay( 0.5 );

      /*
       * Stop the moving parts
       */
      driveTrain.stop();

      lcdManager.logMessage( "Autonomous - Complete" );
   }

}
