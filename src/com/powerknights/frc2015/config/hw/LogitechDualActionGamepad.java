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
 * @author first.stu
 **/
public class LogitechDualActionGamepad
{

   /*****************************
    * Button Inputs
    *****************************/

   public static int getButton1()
   {
      return 1;
   }


   public static int getXButton()
   {
      return getButton1();
   }


   public static int getBlueButton()
   {
      return getButton1();
   }


   public static int getButton2()
   {
      return 2;
   }


   public static int getAButton()
   {
      return getButton2();
   }


   public static int getGreenButton()
   {
      return getButton2();
   }


   public static int getButton3()
   {
      return 3;
   }


   public static int getBButton()
   {
      return getButton3();
   }


   public static int getRedButton()
   {
      return getButton3();
   }


   public static int getButton4()
   {
      return 4;
   }


   public static int getYButton()
   {
      return getButton4();
   }


   public static int getYellowButton()
   {
      return getButton4();
   }


   public static int getButton5()
   {
      return 5;
   }


   public static int getLeftBumper()
   {
      return getButton5();
   }


   public static int getButton6()
   {
      return 6;
   }


   public static int getRightBumper()
   {
      return getButton6();
   }


   public static int getButton7()
   {
      return 7;
   }


   public static int getLeftTrigger()
   {
      return getButton7();
   }


   public static int getButton8()
   {
      return 8;
   }


   public static int getRightTrigger()
   {
      return getButton8();
   }


   public static int getButton9()
   {
      return 9;
   }


   public static int getBackButton()
   {
      return getButton9();
   }


   public static int getButton10()
   {
      return 10;
   }


   public static int getStartButton()
   {
      return getButton10();
   }


   public static int getButton11()
   {
      return 11;
   }


   public static int getLeftStickClick()
   {
      return getButton11();
   }


   public static int getButton12()
   {
      return 12;
   }


   public static int getRightStickClick()
   {
      return getButton12();
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

   public static int getAxis0()
   {
      return 0;
   }


   // Left 0 to -1, Right 0 to +1
   public static int getAxisX()
   {
      return getAxis0();
   }


   // Left 0 to -1, Right 0 to +1
   public static int getLeftStickX()
   {
      return getAxis0();
   }


   public static int getAxis1()
   {
      return 1;
   }


   // Forward 0 to -1, Back 0 to +1
   public static int GetAxisY()
   {
      return getAxis1();
   }


   // Forward 0 to -1, Back 0 to +1
   public static int getLeftStickY()
   {
      return getAxis1();
   }


   public static int getAxis2()
   {
      return 2;
   }


   // Left 0 to -1, Right 0 to +1
   public static int getAxisZ()
   {
      return getAxis2();
   }


   // Left 0 to -1, Right 0 to +1
   public static int getRightStickX()
   {
      return getAxis2();
   }


   public static int getAxis3()
   {
      return 3;
   }


   // Forward 0 to -1, Back 0 to +1
   public static int getAxisZRotate()
   {
      return getAxis3();
   }


   // Forward 0 to -1, Back 0 to +1
   public static int getRightStickY()
   {
      return getAxis3();
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
