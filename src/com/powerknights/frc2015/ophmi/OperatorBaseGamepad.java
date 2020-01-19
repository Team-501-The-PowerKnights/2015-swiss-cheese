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


import com.powerknights.frc2015.config.hw.GamepadModels;
import com.powerknights.frc2015.config.hw.LogitechDualActionGamepad;
import com.powerknights.frc2015.config.hw.Xbox360Gamepad;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.stu
 **/
public abstract class OperatorBaseGamepad
   extends Gamepad
{

   private final GamepadModels model;


   protected OperatorBaseGamepad( Joystick joystick, GamepadModels model )
   {
      super( joystick );

      this.model = model;
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


   public int getTotePositionPOV()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getPOV();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getPOV();
      default:
         return Xbox360Gamepad.getPOV();
      }
   }


   public int getFloorButton()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getGreenButton();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getGreenButton();
      default:
         return Xbox360Gamepad.getGreenButton();
      }
   }


   public int getTote1Button()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getRedButton();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getRedButton();
      default:
         return Xbox360Gamepad.getRedButton();
      }
   }


   public int getMaxToteButton()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getYellowButton();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getYellowButton();
      default:
         return Xbox360Gamepad.getYellowButton();
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


   public int getLifterResetButton()
   {
      switch ( model )
      {
      case Xbox360:
         return Xbox360Gamepad.getLeftBumper();
      case LogitechDualAction:
         return LogitechDualActionGamepad.getLeftBumper();
      default:
         return Xbox360Gamepad.getLeftBumper();
      }
   }

}
