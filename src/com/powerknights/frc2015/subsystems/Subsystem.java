/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.subsystems;


import com.powerknights.frc2015.managers.DriverStationManager;
import com.powerknights.frc2015.managers.LCDManager;
import com.powerknights.frc2015.managers.LiveWindowManager;
import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;


/**
 * @author first.stu
 **/
abstract class Subsystem
   implements ISubsystem
{

   /** Handle to driver station to get inputs **/
   protected final DriverStationManager dsManager;
   /** Handle to LCD manager to display status **/
   protected final LCDManager lcdManager;
   /** Handle to smart dashboard **/
   protected final SmartDashboardManager smartDashboard;
   /** Handle to Preferences Manager **/
   protected final PreferencesManager prefsManager;

   /** Handle to live window **/
   protected final LiveWindowManager liveWindow;


   protected Subsystem()
   {
      dsManager = DriverStationManager.getInstance();
      lcdManager = LCDManager.getInstance();
      smartDashboard = SmartDashboardManager.getInstance();
      prefsManager = PreferencesManager.getInstance();

      liveWindow = LiveWindowManager.getInstance();
   }

}
