/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.managers;


import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.liz
 **/
public class RobotManager
{

   /** Singleton instance of class for all to use **/
   private static RobotManager ourInstance;

   /** Handle to the WPILib base class for Robot **/
   private final RobotBase robot;


   private RobotManager( RobotBase robot )
   {
      this.robot = robot;
   }


   /**
    * Constructs the singleton instance of the Robot manager. Assumed to be
    * called before any use of the manager; and verifies only called once.
    *
    * @throws IllegalStateException
    **/
   public static synchronized void constructInstance( RobotBase robot )
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException( "RobotManager Already Constructed" );
      }
      ourInstance = new RobotManager( robot );
   }


   /**
    * Returns the singleton instance of the Robot manager. Verifies the manager
    * has been successfully created prior to use.
    *
    * @return singleton instance of Robot manager
    * @throws IllegalStateException
    **/
   public static RobotManager getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException( "RobotManager Not Constructed Yet" );
      }
      return ourInstance;
   }


   public boolean isDisabled()
   {
      return robot.isDisabled();
   }


   /**
    * Convenience function for whether robot is enabled.
    *
    * @return
    **/
   public boolean isEnabled()
   {
      return !robot.isDisabled();
   }


   public boolean isAutonomous()
   {
      return robot.isAutonomous();
   }


   public boolean isTest()
   {
      return robot.isTest();
   }


   public boolean isOperatorControl()
   {
      return robot.isOperatorControl();
   }


   public boolean isNewDataAvailable()
   {
      return robot.isNewDataAvailable();
   }

}
