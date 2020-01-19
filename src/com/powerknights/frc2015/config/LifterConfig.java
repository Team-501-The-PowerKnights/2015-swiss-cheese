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

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;


/**
 * @author first.lizz
 **/
public class LifterConfig
{

   static public int getLeftMotorChannel()
   {
      return RoboRIO.getPwmChannel4();
   }


   static public int getRightMotorChannel()
   {
      return RoboRIO.getPwmChannel5();
   }


   static public EncodingType getEncodingType()
   {
      return Encoder.EncodingType.k1X;
   }


   /**
    * @return Quad Encoder (A)
    **/
   static public int getLeftEncoderChannelA()
   {
      return RoboRIO.getDioChannel0();
   }


   /**
    * @return Quad Encoder (B)
    **/
   static public int getLeftEncoderChannelB()
   {
      return RoboRIO.getDioChannel1();
   }


   /**
    * @return true if encoder is "backwards", false otherwise
    **/
   static public boolean getLeftEncoderReversing()
   {
      return false;
   }


   /**
    * @return true if the PID is "backwards", false otherwise
    **/
   static public boolean getLeftPIDReversing()
   {
      return true;
   }


   /**
    * @return Quad Encoder (A)
    **/
   static public int getRightEncoderChannelA()
   {
      return RoboRIO.getDioChannel2();
   }


   /**
    * @return Quad Encoder (B)
    **/
   static public int getRightEncoderChannelB()
   {
      return RoboRIO.getDioChannel3();
   }


   /**
    * @return true if encoder is "backwards", false otherwise
    **/
   static public boolean getRightEncoderReversing()
   {
      return false;
   }


   /**
    * @return true if the PID is "backwards", false otherwise
    **/
   static public boolean getRightPIDReversing()
   {
      return false;
   }

}
