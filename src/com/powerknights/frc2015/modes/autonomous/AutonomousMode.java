/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.modes.ModeImplementer;

import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.stu
 **/
public abstract class AutonomousMode
   extends ModeImplementer
{

   public AutonomousMode( RobotBase robot )
   {
      super( robot, "Autonomous" );
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
      return robot.isAutonomous() && robot.isEnabled();
   }

}
