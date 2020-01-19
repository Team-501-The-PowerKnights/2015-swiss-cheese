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
import com.powerknights.frc2015.config.hw.GamepadModels;
import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.ophmi.DriverGamepad;
import com.powerknights.frc2015.ophmi.IHmiController;
import com.powerknights.frc2015.subsystems.HMIControllers;
import com.powerknights.frc2015.utils.Controller;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.stu
 */
public class DriverController
   extends Controller
{

   /** Handle to driver gamepad **/
   private final IHmiController pad;


   /**
    * Constructs an instance of the driver controller
    **/
   public DriverController()
   {
      super( "Driver Controller", DelayTimeConfig.getDriverControllerDelay() );

      PreferencesManager prefs = PreferencesManager.getInstance();
      String controller = prefs.getDriverController();

      HMIControllers controls = HMIControllers.getInstance();
      Joystick joystick = controls.getDriverController();

      if ( controller.equals( "LogitechDualActionGamepad" ) )
      {
         pad = new DriverGamepad( joystick, GamepadModels.LogitechDualAction );
         setConfiguration( "driverPad", "Logitech Dual Action" );
      }
      else if ( controller.equals( "Xbox360Gamepad" ) )
      {
         pad = new DriverGamepad( joystick, GamepadModels.Xbox360 );
         setConfiguration( "driverPad", "Xbox 360" );
      }
      else if ( controller.equals( "LogitechF310Gamepad" ) )
      {
         pad = new DriverGamepad( joystick, GamepadModels.LogitechF310 );
         setConfiguration( "driverPad", "Logitech F310" );
      }
      else
      {
         System.err.println( "Unknown config for DriverController: "
            + controller );
         pad = new DriverGamepad( joystick, GamepadModels.Default );
         setConfiguration( "driverPad", "Default[" + GamepadModels.Default
            + "]" );
      }
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2014.utils.Controller#setUp()
    */
   @Override
   public void setUp()
   {
      pad.initForModeStart();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2013.utils.Controller#doIt()
    */
   @Override
   public boolean doIt()
   {
      // Look at current driver pad values and process them
      pad.performUpdate();

      // Keep on going
      return false;
   }

}
