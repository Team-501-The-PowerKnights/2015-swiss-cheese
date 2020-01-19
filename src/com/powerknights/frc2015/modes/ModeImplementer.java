/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.modes;


import com.powerknights.frc2015.managers.DriverStationManager;
import com.powerknights.frc2015.managers.LCDManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;

import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.stu
 **/
public abstract class ModeImplementer
{

   /** Handle to the robot we're running on **/
   protected final RobotBase robot;
   /** Readable string with name of the mode **/
   protected final String myMode;

   /** Utility wrapper to DriverStation **/
   protected final DriverStationManager dsManager;
   /** Utility wrapper to DriverStation LCD **/
   protected final LCDManager lcdManager;
   /** Handle to smart dashboard **/
   protected final SmartDashboardManager smartDashboard;


   public ModeImplementer( RobotBase robot, String myMode )
   {
      this.robot = robot;
      this.myMode = myMode;

      /*
       * Get handles to our things
       */
      // Driver Station
      dsManager = DriverStationManager.getInstance();
      // LCD on Driver Station
      lcdManager = LCDManager.getInstance();
      // SmartDashboard from WPILib
      smartDashboard = SmartDashboardManager.getInstance();
   }


   /**
    * Called at the start of the mode to allow the implementation to setup or
    * otherwise allocate resources. A default implementation is provided with
    * some handling of the state and displays; modes should override this to
    * provide their own specifics.
    **/
   public void setUp()
   {
      lcdManager.setMode( myMode );
      smartDashboard.putString( "MODE", myMode );
   }


   /**
    * Called to actually run the mode. Assumes that will not return until either
    * done, or else the FMS has notified the robot that the mode is over.
    **/
   public abstract void runIt();


   /**
    * Called at the end of the mode to allow the implementation to cleanup or
    * otherwise release resources. A default implementation is provided with
    * some handling of the state and displays; modes should override this to
    * provide their own specifics.
    **/
   public void cleanUp()
   {
      lcdManager.setMode( "Running" );
      smartDashboard.putString( "MODE", "Running" );
      lcdManager.resetMessage();
      lcdManager.reset();
   }


   /**
    * Causes the currently executing thread to sleep (temporarily cease
    * execution) for the specified number of milliseconds, subject to the
    * precision and accuracy of system timers and schedulers. The thread does
    * not lose ownership of any monitors.
    *
    * @param millis - the length of time to sleep in milliseconds
    *
    * @throws IllegalArgumentException if the value of <code>millis</code> is
    *         negative
    **/
   protected void sleep( long millis )
   {
      try
      {
         Thread.sleep( millis );
      }
      catch ( final InterruptedException ex )
      {
         // Don't care - ignore this
      }
   }

}
