/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.sensors;


import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;


/**
 * first.stu
 **/
public class LiftHeight
   extends Sensor< Integer >
{

   // Assumes reset at the start
   /** Maximum height / level we want to travel **/
   public static final int maxHeight = 3500;
   /** Minimum height / level we want to travel **/
   public static final int minHeight = 0;

   /** **/
   private final Encoder sensor;
   /** **/
   private final String name;


   /**
    * @param side
    * @param channelA
    * @param channelB
    * @param reverse
    * @param type
    **/
   protected LiftHeight( final String side, final int channelA,
      final int channelB, boolean reverse, final EncodingType type )
   {
      super( new Encoder( channelA, channelB, reverse, type ) );
      // Now that it's created in superclass, typecast locally
      sensor = (Encoder) sendable;

      name = side + "LiftHeight";
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.sensors.Sensor#reset()
    */
   @Override
   public void reset()
   {
      // Set the current count to zero
      sensor.reset();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.sensors.Sensor#get()
    */
   @Override
   public Integer get()
   {
      return sensor.get();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.controllers.ISensorUpdater#update()
    */
   @Override
   public void update()
   {
      smartDashboard.putNumber( name, get() );
   }

}
