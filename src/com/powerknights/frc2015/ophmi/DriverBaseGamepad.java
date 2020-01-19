/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.ophmi;


import com.powerknights.frc2015.config.hw.GamepadModels;
import com.powerknights.frc2015.config.hw.LogitechDualActionGamepad;
import com.powerknights.frc2015.config.hw.Xbox360Gamepad;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.stu
 **/
public abstract class DriverBaseGamepad
   extends Gamepad
{

   private final GamepadModels model;


   protected DriverBaseGamepad( Joystick joystick, GamepadModels model )
   {
      super( joystick );

      this.model = model;
   }


   public int getStrafeAxis()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getLeftStickX();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getLeftStickX();
      default:
         return Xbox360Gamepad.getLeftStickX();
      }
   }


   public int getStrafeLeftAxis()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getLeftTrigger();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getLeftTrigger();
      default:
         return Xbox360Gamepad.getLeftTrigger();
      }
   }


   public int getStrafeRightAxis()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getRightTrigger();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getRightTrigger();
      default:
         return Xbox360Gamepad.getRightTrigger();
      }
   }


   public int getSpeedAxis()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getLeftStickY();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getLeftStickY();
      default:
         return Xbox360Gamepad.getLeftStickY();
      }
   }


   public int getTurnAxis()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getRightStickX();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getRightStickX();
      default:
         return Xbox360Gamepad.getRightStickX();
      }
   }


   public int getTurboEnableButton()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getRightBumper();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getRightBumper();
      default:
         return Xbox360Gamepad.getRightBumper();
      }
   }


   public int getFeederToteButton()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getBlueButton();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getBlueButton();
      default:
         return Xbox360Gamepad.getBlueButton();
      }
   }

}
