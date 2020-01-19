/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.sensors;


import edu.wpi.first.wpilibj.Gyro;


/**
 * @author first.stu
 **/
public class RobotOrientation
   extends Sensor< Double >
{

   private final Gyro sensor;


   /**
    * @param sensor
    **/
   public RobotOrientation( int channel )
   {
      super( new Gyro( channel ) );
      // Now that it's created in superclass, typecast locally
      sensor = (Gyro) sendable;

      sensor.initGyro();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.sensors.Sensor#reset()
    */
   @Override
   public void reset()
   {
      sensor.reset();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.sensors.Sensor#get()
    */
   @Override
   public Double get()
   {
      return sensor.getAngle();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.controllers.ISensorUpdater#update()
    */
   @Override
   public void update()
   {
      smartDashboard.putNumber( "gyro", get() );
   }

}
