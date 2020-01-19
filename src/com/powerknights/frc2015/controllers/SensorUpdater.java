/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.controllers;


import java.util.Vector;

import com.powerknights.frc2015.config.DelayTimeConfig;
import com.powerknights.frc2015.utils.Controller;


/**
 * @author first.stu
 **/
public class SensorUpdater
extends Controller
{

   /** Singleton instance of class for all to use **/
   private static SensorUpdater ourInstance;

   /** List of all the sensors to update **/
   private final Vector< ISensorUpdater > sensors;


   private SensorUpdater()
   {
      super( "Sensor Updater", DelayTimeConfig.getSensorUpaterDelay() );

      sensors = new Vector< ISensorUpdater >();
   }


   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException( "SensorUpdater Already Constructed" );
      }
      ourInstance = new SensorUpdater();
   }


   public static SensorUpdater getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException( "SensorUpdater Not Constructed Yet" );
      }
      return ourInstance;
   }


   public void addSensor( ISensorUpdater sensor )
   {
      sensors.addElement( sensor );
   }


   public void removeSensor( ISensorUpdater sensor )
   {
      sensors.removeElement( sensor );
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2013.utils.Controller#doIt()
    */
   @Override
   public boolean doIt()
   {
      for ( ISensorUpdater sensor : sensors )
      {
         try
         {
            sensor.update();
         }
         catch ( Exception ex )
         {
            System.err.println( "ISensorUpdater failed; removing from list" );
            sensors.removeElement( sensor );
            break;
         }
      }

      // Keep on going ...
      return false;
   }

}
