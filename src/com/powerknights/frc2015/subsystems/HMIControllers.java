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


import com.powerknights.frc2015.config.OperatorConsoleConfig;

import edu.wpi.first.wpilibj.Joystick;


/**
 * @author first.stu
 **/
public class HMIControllers
{

   /** Singleton instance of class for all to use **/
   private static HMIControllers ourInstance;

   /** Controller for robot driver **/
   private final Joystick driverJoystick;
   /** Controller for robot operator **/
   private final Joystick operatorJoystick;


   private HMIControllers()
   {
      System.out.println( "HMIControllers::constructor()" );

      /*
       * Gamepads
       */
      driverJoystick = constructDriverGamepad();
      operatorJoystick = constructOperatorGamepad();
   }


   private Joystick constructDriverGamepad()
   {
      return new Joystick( OperatorConsoleConfig.getDriverGamepad() );
   }


   private Joystick constructOperatorGamepad()
   {
      return new Joystick( OperatorConsoleConfig.getOperatorGamepad() );
   }


   /**
    * Constructs instance of the HMI controllers subsystem. Assumed to be called
    * before any usage of the subsystem; and verifies only called once. Allows
    * controlled startup sequencing of the robot and all it's subsystems.
    **/
   public static synchronized void construct()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException( "HMIControllers Already Constructed" );
      }
      ourInstance = new HMIControllers();
   }


   /**
    * Returns the singleton instance of the HMI controllers subsystem. If it
    * hasn't been constructed yet, throws an <code>IllegalStateException</code>
    * .
    *
    * @return singleton instance of HMI controllers
    **/
   public static HMIControllers getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException( "HMIControllers Not Constructed Yet" );
      }
      return ourInstance;
   }


   public Joystick getDriverController()
   {
      return driverJoystick;
   }


   public Joystick getOperatorController()
   {
      return operatorJoystick;
   }

}
