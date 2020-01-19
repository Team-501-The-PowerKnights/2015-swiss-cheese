/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.managers;


import edu.wpi.first.wpilibj.DriverStation;


/**
 * @author first.stu
 */
public class DriverStationManager
{

   /** Singleton instance of class for all to use **/
   private static DriverStationManager ourInstance;

   /** Handle to the WPILib Driver Station **/
   private final DriverStation driverStation;


   private DriverStationManager()
   {
      driverStation = DriverStation.getInstance();
   }


   /**
    * Constructs the singleton instance of the DriverStation manager. Assumed to
    * be called before any use of the manager; and verifies only called once.
    *
    * @throws IllegalStateException
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException(
            "DriverStationManager Already Constructed" );
      }
      ourInstance = new DriverStationManager();
   }


   /**
    * Returns the singleton instance of the DriverStation manager. Verifies the
    * manager has been successfully created prior to use.
    *
    * @return singleton instance of DriverStation manager
    * @throws IllegalStateException
    **/
   public static DriverStationManager getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException(
            "DriverStationManager Not Constructed Yet" );
      }
      return ourInstance;
   }


   /**
    * Return the approximate match time in seconds.
    *
    * @return Match time in seconds since the beginning of autonomous
    * @see edu.wpi.first.wpilibj.DriverStation#getMatchTime
    */
   public double getMatchTime()
   {
      return driverStation.getMatchTime();
   }

}
