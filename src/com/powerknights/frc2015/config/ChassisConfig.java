/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.config;


import com.powerknights.frc2015.config.hw.RoboRIO;


/**
 * first.stu
 **/
public class ChassisConfig
{

   /*
    * Gyro (Analog)
    */

   /**
    * @return gyro port
    **/
   public static int getGyroChannel()
   {
      return RoboRIO.getAnalogInChannel0();
   }


   /*
    * Range Finder (Digital Inputs)
    */

   /**
    * @return ultrasonic in port
    **/
   public static int getUltrasonicRangeChannelIn()
   {
      return RoboRIO.getDioChannel8();
   }


   /**
    * @return ultrasonic out port
    */
   public static int getUltrasonicRangeChannelOut()
   {
      return RoboRIO.getDioChannel9();
   }
}
