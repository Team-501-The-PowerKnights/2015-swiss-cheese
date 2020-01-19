/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.ophmi;


import com.powerknights.frc2015.managers.DriverStationManager;
import com.powerknights.frc2015.managers.LCDManager;
import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.stu
 **/
public abstract class Gamepad
   implements IHmiController
{

   /** Slop in input values from axis **/
   protected final double zeroInputValue = 0.05;

   /** Handle to operator control device **/
   protected final Joystick joystick;

   /** Handle to driver station to get inputs **/
   protected final DriverStationManager dsManager;
   /** Handle to smart dashboard **/
   protected final SmartDashboardManager smartDashboard;
   /** Handle to LCD manager to display status **/
   protected final LCDManager lcdManager;
   /** Handle to Preferences Manager **/
   protected final PreferencesManager prefsManager;


   protected Gamepad( Joystick joystick )
   {
      this.joystick = joystick;

      dsManager = DriverStationManager.getInstance();
      smartDashboard = SmartDashboardManager.getInstance();
      lcdManager = LCDManager.getInstance();
      prefsManager = PreferencesManager.getInstance();
   }


   /**
    * Determines whether input is to be considered 'zero' or not. This allows
    * for the slop in the input devices, where they don't necessarily drop to
    * zero when released, but you still want the actuator to stop.
    *
    * @param inputValue - input from device
    * @return <code>true</code> if close enough to zero, <code>false</code>
    *         otherwise
    **/
   protected boolean isZero( double inputValue )
   {
      return ( Math.abs( inputValue ) < zeroInputValue );
   }


   /**
    * Determines whether input is to be considered positive or not. Positive is
    * considered to include the value 'zero'.
    *
    * @param inputValue - input from device
    * @return <code>true</code> if >= 0, <code>false</code> otherwise
    **/
   protected boolean isPositive( double inputValue )
   {
      return ( inputValue >= 0.0 );
   }


   /**
    * Determines whether input is to be considered negative or not. Negative is
    * not considered to include the value 'zero'.
    *
    * @param inputValue - input from device
    * @return <code>true</code> if < 0, <code>false</code> otherwise
    **/
   protected boolean isNegative( double inputValue )
   {
      return ( inputValue < 0.0 );
   }

}
