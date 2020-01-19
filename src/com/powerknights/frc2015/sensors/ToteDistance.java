/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.sensors;


import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;


/**
 * first.stu
 **/
public class ToteDistance
   extends Sensor< Double >
{

   // How far to feeder station (ft)
   private static final double feederStationDistance = 3.76;

   private final Ultrasonic sensor;


   /**
    * @param channelIn
    * @param channelOut
    **/
   public ToteDistance( int channelIn, int channelOut )
   {
      super( new Ultrasonic( channelIn, channelOut ) );
      // Now that it's created in superclass, typecast locally
      sensor = (Ultrasonic) sendable;

      sensor.setDistanceUnits( Unit.kInches );

      sensor.setAutomaticMode( true );
      sensor.setEnabled( true );
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.sensors.Sensor#reset()
    */
   @Override
   public void reset()
   {
      // Nothing for this one
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.sensors.Sensor#get()
    */
   @Override
   public Double get()
   {
      // sensor is at back of frame to capture totes
      final double distance = sensor.getRangeInches() / 12.0;
      return distance;
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.controllers.ISensorUpdater#update()
    */
   @Override
   public void update()
   {
      smartDashboard.putNumber( "ultrasonicDistance", get() );

      smartDashboard.putBoolean( "firstToteDistance", inRange() );
   }


   public boolean inRange()
   {
      final double range = get();
      return ( range > ( feederStationDistance - 0.10 ) )
         && ( range < ( feederStationDistance + 0.10 ) );
   }

}
