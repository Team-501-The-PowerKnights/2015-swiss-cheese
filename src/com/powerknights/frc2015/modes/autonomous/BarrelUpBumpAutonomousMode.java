
package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.subsystems.DriveTrain;
import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;


public class BarrelUpBumpAutonomousMode
   extends LifterAutonomousMode
{

   /** Handle to the drive **/
   private final DriveTrain driveTrain;


   /**
    * @param robot
    **/
   public BarrelUpBumpAutonomousMode( RobotBase robot )
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
      System.out
      .println( "Autonomous with barrel pick up, bump implementation" );
      lcdManager.logMessage( "Autonomous - BarrelUpBump" );

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
      waitForLiftPosition( LiftPosition.Tote1 );

      /*
       * Drive forward
       */
      driveTrain.robotDrive( 0.30, 0.0, 0.0 );
      @SuppressWarnings( "unused" )
      final double delay = prefs.getAutoDriveForwardTime();
      Timer.delay( 3.0 ); // 6.5

      /*
       * Back off tote ...
       */
      driveTrain.robotDrive( -0.30, 0.0, 0.0 );
      Timer.delay( 0.5 );
      /*
       * ... and stop
       */
      driveTrain.stop();

      /*
       * Lower lift to pick up barrel
       */
      lifter.goTo( LiftPosition.Floor );
      waitForLiftPosition( LiftPosition.Floor );

      Timer.delay( 0.2 );

      /*
       * Pull forward again
       */
      driveTrain.robotDrive( 0.30, 0.0, 0.0 );
      Timer.delay( 0.5 );

      /*
       * Stop the moving parts
       */
      driveTrain.stop();

      lcdManager.logMessage( "Autonomous - Complete" );
   }

}
