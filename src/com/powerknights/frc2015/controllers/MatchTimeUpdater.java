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


import com.powerknights.frc2015.config.DelayTimeConfig;
import com.powerknights.frc2015.config.FRCConfig;
import com.powerknights.frc2015.managers.DriverStationManager;
import com.powerknights.frc2015.managers.LCDManager;
import com.powerknights.frc2015.utils.Controller;
import com.powerknights.frc2015.utils.StringUtils;

import edu.wpi.first.wpilibj.DriverStation;


/**
 * @author first.liz
 **/
public class MatchTimeUpdater
   extends Controller
{

   /** Singleton instance of class for all to use **/
   private static MatchTimeUpdater ourInstance;

   /** Utility wrapper to DriverStation **/
   private final DriverStationManager dsManager;
   /** Utility wrapper to DriverStation LCDManager **/
   private final LCDManager lcdManager;
   /** **/
   private final DriverStation driverStation;


   private MatchTimeUpdater()
   {
      super( "MatchTime Updater", DelayTimeConfig.getMatchTimeUpdaterDelay() );

      dsManager = DriverStationManager.getInstance();
      lcdManager = LCDManager.getInstance();
      driverStation = DriverStation.getInstance();
   }


   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException(
            "MatchTimeUpdater Already Constructed" );
      }
      ourInstance = new MatchTimeUpdater();
   }


   public static MatchTimeUpdater getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException(
            "MatchTimeUpdater Not Constructed Yet" );
      }
      return ourInstance;
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2013.utils.Controller#doIt()
    */
   @Override
   public boolean doIt()
   {
      if ( !driverStation.isEnabled() )
      {
         lcdManager.resetTime();
         // Keep on going
         return false;
      }

      // This is additive on whole match; not sub-modes
      double secondsDuration;
      if ( driverStation.isAutonomous() )
      {
         secondsDuration = FRCConfig.getAutonomousDuration();
      }
      else if ( driverStation.isOperatorControl() )
      {
         secondsDuration = FRCConfig.getMatchDuration();
      }
      else
      {
         lcdManager.resetTime();
         // Keep on going
         return false;
      }
      double secondsLeft = secondsDuration - dsManager.getMatchTime();
      // round down to handle update rate
      secondsLeft -= 0.5;

      // Send new data set to the LCDManager
      lcdManager.setTime( StringUtils.format( ( secondsLeft - 0.5 ), 0 ) );

      // Keep on going
      return false;
   }

}
