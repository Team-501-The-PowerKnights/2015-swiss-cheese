/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.modes.teleoperated;


import com.powerknights.frc2015.controllers.DriverController;
import com.powerknights.frc2015.controllers.OperatorController;
import com.powerknights.frc2015.modes.ModeImplementer;
import com.powerknights.frc2015.utils.Controller;

import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.stu
 **/
public class EmptyTeleoperatedMode
   extends ModeImplementer
{

   public EmptyTeleoperatedMode( RobotBase robot )
   {
      super( robot, "TeleOperated" );
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2013.modes.ModeImplementer#runIt()
    */
   @Override
   public void runIt()
   {
      /*
       * Create the controllers (one for driver and one for operator)
       */
      Controller driverController = new DriverController();
      Controller operatorController = new OperatorController();

      /*
       * Start the controllers
       */
      driverController.start();
      operatorController.start();

      /*
       * Something just to hold us here so the threads keep running ...
       */
      while ( isActive() )
      {
         sleep( 250 );
      }

      /*
       * Stop the controllers
       */
      operatorController.quit();
      driverController.quit();
   }


   /**
    * Returns whether the mode is still active (and the robot should be
    * performing the appropriate tasks) or not (in which case it should return
    * to let the next mode / state start).
    *
    * @return <code>true</code> if and only if the mode is active;
    *         <code>false</code> otherwise.
    **/
   protected boolean isActive()
   {
      return robot.isOperatorControl() && robot.isEnabled();
   }

}
