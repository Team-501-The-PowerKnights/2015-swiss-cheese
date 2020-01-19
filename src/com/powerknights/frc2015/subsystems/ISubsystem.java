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


/**
 * @author first.stu
 **/
public interface ISubsystem
{

   /**
    * Resets all state and ensures that any active components (e..g, PWMs) are
    * set to their 'off' state.
    **/
   public void reset();

}
