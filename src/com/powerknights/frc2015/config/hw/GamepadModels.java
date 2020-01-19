/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.config.hw;


/**
 * @author first.stu
 **/
public enum GamepadModels
{
   // @formatter:off
   Default( "Xbox360" ),
   LogitechDualAction( "LogitechDualAction" ),
   LogitechF310("LogitechF310"),
   Xbox360( "Xbox360" );
   // @formatter:on

   private final String model;


   private GamepadModels( String model )
   {
      this.model = model;
   }


   public String getModel()
   {
      return model;
   }

}
