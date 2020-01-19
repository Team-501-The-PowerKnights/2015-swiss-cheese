/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.sensors;


import edu.wpi.first.wpilibj.CounterBase.EncodingType;


/**
 * first.stu
 **/
public class LeftLiftHeight
   extends LiftHeight
{

   /**
    * @param channelA
    * @param channelB
    * @param reverse
    * @param type
    **/
   public LeftLiftHeight( final int channelA, final int channelB,
      final boolean reverse, final EncodingType type )
   {
      super( "left", channelA, channelB, reverse, type );
   }

}
