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


import com.powerknights.frc2015.config.hw.RoboRIO;


/**
 * @author first.cody
 */
public class DriveTrainConfig
{

   /*
    * Drive Wheels (Motors)
    */

   /**
    * @return left front speed controller / motor channel
    **/
   static public int getLeftFrontMotorChannel()
   {
      return RoboRIO.getPwmChannel0();
   }


   /**
    * @return left rear speed controller / motor channel
    **/
   static public int getLeftRearMotorChannel()
   {
      return RoboRIO.getPwmChannel1();
   }


   /**
    * @return right front speed controller / motor channel
    **/
   static public int getRightFrontMotorChannel()
   {
      return RoboRIO.getPwmChannel2();
   }


   /**
    * @return right rear speed controller / motor channel
    **/
   static public int getRightRearMotorChannel()
   {
      return RoboRIO.getPwmChannel3();
   }

}
