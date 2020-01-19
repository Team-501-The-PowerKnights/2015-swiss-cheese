/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.config.hw;


/**
 * @author first.sam
 */
public class LogitechGamepadF310
{

   /*****************************
    * Button Inputs
    *****************************/

   public static int getAButton()
   {
      return 1;
   }


   // Green Button (A)
   public static int getGreenButton()
   {
      return getAButton();
   }


   public static int getBButton()
   {
      return 2;
   }


   // Red Button (B)
   public static int getRedButton()
   {
      return getBButton();
   }


   public static int getXButton()
   {
      return 3;
   }


   // Blue Button (X)
   public static int getBlueButton()
   {
      return getXButton();
   }


   public static int getYButton()
   {
      return 4;
   }


   // Yellow Button (Y)
   public static int getYellowButton()
   {
      return getYButton();
   }


   public static int getLeftBumper()
   {
      return 5;
   }


   public static int getRightBumper()
   {
      return 6;
   }


   public static int getBackButton()
   {
      return 7;
   }


   public static int getStartButton()
   {
      return 8;
   }


   public static int getLeftStickClick()
   {
      return 9;
   }


   public static int getRightStickClick()
   {
      return 10;
   }


   /**
    * This is not actually a button. It's something only the controller will
    * recognize.
    **/
   public static int getModeButton()
   {
      throw new IllegalStateException( "ModeButton not supported" );
   }


   /*****************************
    * Axis Inputs
    *****************************/

   // Left 0 to -1, Right 0 to +1
   public static int getLeftStickX()
   {
      return 0;
   }


   // Forward 0 to -1, Back 0 to +1
   public static int getLeftStickY()
   {
      return 1;
   }


   // Left 0 to +1
   public static int getLeftTrigger()
   {
      return 2;
   }


   // Right 0 to +1
   public static int getRightTrigger()
   {
      return 3;
   }


   // Left 0 to -1, Right 0 to +1
   public static int getRightStickX()
   {
      return 4;
   }


   // Forward 0 to -1, Back 0 to +1
   public static int getRightStickY()
   {
      return 5;
   }


   /*****************************
    * DPad Inputs (return angles)
    *****************************/

   public static int getPOV0()
   {
      return 0;
   }


   public static int getPOV()
   {
      return getPOV0();
   }

}
