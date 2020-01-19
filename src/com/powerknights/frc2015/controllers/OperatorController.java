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
import com.powerknights.frc2015.ophmi.IHmiController;
import com.powerknights.frc2015.ophmi.OperatorGamepad;
import com.powerknights.frc2015.subsystems.HMIControllers;
import com.powerknights.frc2015.utils.Controller;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.stu
 **/
public class OperatorController
   extends Controller
{

   /** Handle to operator gamepad **/
   private final IHmiController pad;


   public OperatorController()
   {
      super( "Operator Controller", DelayTimeConfig
         .getOperatorControllerDelay() );

      PreferencesManager prefs = PreferencesManager.getInstance();
      String controller = prefs.getOperatorController();

      HMIControllers controls = HMIControllers.getInstance();
      Joystick joystick = controls.getOperatorController();

      if ( controller.equals( "LogitechDualActionGamepad" ) )
      {
         pad = new OperatorGamepad( joystick, GamepadModels.LogitechDualAction );
         setConfiguration( "operatorPad", "Logitech Dual Action" );
      }
      else if ( controller.equals( "Xbox360Gamepad" ) )
      {
         pad = new OperatorGamepad( joystick, GamepadModels.Xbox360 );
         setConfiguration( "operatorPad", "Xbox 360" );
      }
      else if ( controller.equals( "LogitechF310Gamepad" ) )
      {
         pad = new OperatorGamepad( joystick, GamepadModels.LogitechF310 );
         setConfiguration( "operatorPad", "Logitech F310" );
      }
      else
      {
         System.err.println( "Unknown config for OperatorController: "
            + controller );
         pad = new OperatorGamepad( joystick, GamepadModels.Default );
         setConfiguration( "operatorPad", "Default[" + GamepadModels.Default
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
    * @see com.powerknights.frc2012.utils.Controller#doIt()
    */
   @Override
   public boolean doIt()
   {
      // Look at current operator pad values and process them
      pad.performUpdate();

      // Keep on going
      return false;
   }

}
