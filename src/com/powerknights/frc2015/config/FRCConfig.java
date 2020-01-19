/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.config;


/**
 * @author first.stu
 **/
public class FRCConfig
{

   public static int getAutonomousDuration()
   {
      return 15;
   }


   public static int getOperatorControlDuration()
   {
      return 120;
   }


   public static int getMatchDuration()
   {
      return getAutonomousDuration() + getOperatorControlDuration();
   }

}
