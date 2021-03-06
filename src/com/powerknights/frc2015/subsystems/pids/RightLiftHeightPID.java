/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.subsystems.pids;


import com.powerknights.frc2015.sensors.LiftHeight;

import edu.wpi.first.wpilibj.SpeedController;


/**
 * @author first.stu
 **/
public class RightLiftHeightPID
   extends LiftHeightPID
{

   /*
    * Override the defaults from base class here
    */

   private static final double P = 0.0350; // 0.0250; // 0.0200; // 0.0150;

   private static final double minOutput = -0.95; // -0.75; // -0.50; // -0.40;
   private static final double maxOutput = 0.95; // 1.00; // +0.85;

   private static final PIDParams pidParams;

   static
   {
      pidParams = new PIDParams();

      pidParams.P = P;
      pidParams.I = defaultI;
      pidParams.D = defaultD;
      pidParams.period = defaultPeriod;

      pidParams.minInput = defaultMinInput;
      pidParams.maxInput = defaultMaxInput;

      pidParams.minOutput = minOutput;
      pidParams.maxOutput = maxOutput;
   }


   /**
    * @param liftHeight
    * @param lift
    * @param reverse
    **/
   public RightLiftHeightPID( LiftHeight liftHeight, SpeedController lift,
      boolean reverse )
   {
      super( liftHeight, lift, pidParams, reverse );
   }

}
