/**
 * /** Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open
 * Source Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015;


import com.powerknights.frc2015.config.SubsystemsConfig;
import com.powerknights.frc2015.controllers.HistoryCollector;
import com.powerknights.frc2015.controllers.MatchTimeUpdater;
import com.powerknights.frc2015.controllers.SensorUpdater;
import com.powerknights.frc2015.managers.DashboardManager;
import com.powerknights.frc2015.managers.DriverStationManager;
import com.powerknights.frc2015.managers.LCDManager;
import com.powerknights.frc2015.managers.LiveWindowManager;
import com.powerknights.frc2015.managers.PreferencesManager;
import com.powerknights.frc2015.managers.RobotManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;
import com.powerknights.frc2015.modes.ModeImplementer;
import com.powerknights.frc2015.modes.autonomous.BarrelOnlyBumpAutonomousMode;
import com.powerknights.frc2015.modes.autonomous.BarrelOnlyNoBumpAutonomousMode;
import com.powerknights.frc2015.modes.autonomous.BarrelUpBumpAutonomousMode;
import com.powerknights.frc2015.modes.autonomous.BarrelUpNoBumpAutonomousMode;
import com.powerknights.frc2015.modes.autonomous.EmptyAutonomousMode;
import com.powerknights.frc2015.modes.teleoperated.TeleoperatedMode;
import com.powerknights.frc2015.roborealm.RoboRealmServer;
import com.powerknights.frc2015.sensors.AutonomousSelector;
import com.powerknights.frc2015.subsystems.Chassis;
import com.powerknights.frc2015.subsystems.DriveTrain;
import com.powerknights.frc2015.subsystems.HMIControllers;
import com.powerknights.frc2015.subsystems.Lifter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SampleRobot;


/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team501Robot
   extends SampleRobot
{

   /** Name of our robot **/
   private final String myName;

   /** Handle to the driver station on operator console (laptop) **/
   @SuppressWarnings( "unused" )
   private DriverStationManager dsManager;
   /** Handle to the LCD on the driver station **/
   private LCDManager lcdManager;
   /** Handle to smart dashboard **/
   private SmartDashboardManager smartDashboard;
   /** Handle to Preferences Manager **/
   private PreferencesManager prefsManager;

   /** Which autonomous mode selected via pot on robot **/
   private AutonomousSelector.AutonomousSelection autoSelection;
   /** Implementation of autonomous mode **/
   private ModeImplementer autonomous;
   /** Implementation of teleoperated mode **/
   private ModeImplementer teleoperated;


   /**
    * Construct instance of our robot. Not much to do but disable the watchdog
    * timer and wait until driver station data starts to arrive.
    **/
   public Team501Robot()
   {
      // Get class name for more generic code
      myName = getClass().getSimpleName();

      System.out.println( myName + " constructor() enter" );

      // Wait until we get the configuration data from driver station
      waitForDriverStationData();

      System.out.println( "Team501Bot constuctor() exit" );
   }


   /**
    * Holds the constructor until we receive at least one update of the control
    * data, which holds the run-time configuration.
    **/
   private void waitForDriverStationData()
   {
      while ( !DriverStation.getInstance().isNewControlData() )
      {
         System.out.println( "Waiting for DriverStation data to arrive" );
         try
         {
            Thread.sleep( 100 );
         }
         catch ( final InterruptedException ex )
         {
            // We'll ignore it
         }
      }
   }


   /*
    * (non-Javadoc)
    *
    * @see edu.wpi.first.wpilibj.SimpleRobot#robotInit()
    */
   @Override
   protected void robotInit()
   {
      System.out.println( myName + " robotInit() method enter" );

      /*
       * Create handles to managers and external systems
       */
      constructManagers();

      /*
       * Create handles to operator console and initialize
       */
      initConsoleInterface();
      /*
       * Note we are initializing
       */
      lcdManager.setMode( "Init" );
      smartDashboard.putString( "MODE", "Init" );

      /*
       * Display some configuration info
       */
      lcdManager.setConfig( myName );

      /*
       * Create RoboRealm server and start
       */
      constructAndStartRoboRealm();

      /*
       *
       */
      // Could do sensors outside of hardware / subsystems
      constructControllers();

      /*
       * Create handles to framework and hardware for robot and initialize
       */
      initSubsystems();

      /*
       * Create handles to devices for driver and operator and initialize
       */
      initOperatorControls();

      /*
       * Initialize instances of modes to run
       */
      // We no longer initialize both modes at robotInit
      final AutonomousSelector selector = new AutonomousSelector( 3 );
      System.out.println( "selector: " + selector.get() );
      selector.update();
      autoSelection = selector.getSelection();
      smartDashboard.putString( "autoSelection", autoSelection.name() );

      /*
       *
       */
      // Could do sensors outside of hardware / subsystems
      startControllers();

      /*
       * Note we are "booted"
       */
      lcdManager.setMode( "Booted" );
      smartDashboard.putString( "MODE", "Booted" );

      /*
       * Note we have no timeouts to start
       */
      smartDashboard.putTimeout( false, "" );

      System.out.println( myName + " robotInit() method exit" );
   }


   private void constructManagers()
   {
      System.out.println( myName + " constructManagers() method enter" );

      // Robot itself (so we don't have to pass ourself around)
      RobotManager.constructInstance( this );

      // Preferences Manager on Robot (using wpilib-preferences.ini file)
      PreferencesManager.constructInstance();

      // National Instruments Dashboard on Laptop
      DashboardManager.constructInstance();

      // Driver Station on Laptop
      DriverStationManager.constructInstance();

      // LCD Display on Driver Station
      LCDManager.constructInstance();

      // Smart Dashboard on Laptop
      SmartDashboardManager.constructInstance();

      // Live Window on Laptop
      LiveWindowManager.constructInstance();

      System.out.println( myName + " constructManagers() method exit" );
   }


   private void initConsoleInterface()
   {
      System.out.println( myName + " initConsoleInterface() method enter" );

      // Driver Station
      dsManager = DriverStationManager.getInstance();

      // LCD on Driver Station
      lcdManager = LCDManager.getInstance();
      lcdManager.setBuild( CodeVersionInfo.version );

      // SmartDashboard from WPILib
      smartDashboard = SmartDashboardManager.getInstance();

      // Preferences from file on robot wpilib-preferences.ini
      prefsManager = PreferencesManager.getInstance();

      System.out.println( myName + " initConsoleInterface() method exit" );
   }


   private void constructAndStartRoboRealm()
   {
      System.out
         .println( myName + " constructAndStartRoboRealm() method enter" );

      if ( prefsManager.useRoboRealm() )
      {
         RoboRealmServer.constructInstance();
         RoboRealmServer.getInstance().start();
      }

      System.out.println( myName + " constructAndStartRoboRealm() method exit" );
   }


   private void constructControllers()
   {
      System.out.println( myName + " constructControllers() method enter" );

      // Dashboard Updater
      // TODO - Implement the NI dashboard data parser
      // DashboardUpdater.constructInstance( );

      // MatchTime Updater
      MatchTimeUpdater.constructInstance();

      // Sensors
      SensorUpdater.constructInstance();

      // History
      HistoryCollector.constructInstance();

      System.out.println( myName + " constructControllers() method exit" );
   }


   private void startControllers()
   {
      System.out.println( myName + " startControllers() method enter" );

      // Dashboard Updater
      // DashboardUpdater.getInstance( ).start( );

      // MatchTime Updater
      MatchTimeUpdater.getInstance().start();

      // Sensors
      SensorUpdater.getInstance().start();

      // History
      HistoryCollector.getInstance().start();

      System.out.println( myName + " startControllers() method exit" );
   }


   private void initSubsystems()
   {
      System.out.println( myName + " initSubsystems() method enter" );

      // Top level status
      smartDashboard.putBoolean( "SUBSYSTEMS", false );
      boolean overall = true;

      // Drive
      if ( SubsystemsConfig.enableDriveTrain() )
      {
         DriveTrain.constructInstance();
         DriveTrain.getInstance().reset();
         lcdManager.appendSubsystem( 'D' );
         smartDashboard.putBoolean( "Drive", true );
      }
      else
      {
         lcdManager.appendSubsystem( 'd' );
         smartDashboard.putBoolean( "Drive", false );
         overall = false;
      }

      // Chassis
      if ( SubsystemsConfig.enableChassis() )
      {
         Chassis.constructInstance();
         Chassis.getInstance().reset();
         lcdManager.appendSubsystem( 'C' );
         smartDashboard.putBoolean( "Chassis", true );
      }
      else
      {
         lcdManager.appendSubsystem( 'c' );
         smartDashboard.putBoolean( "Chassis", false );
         overall = false;
      }

      // Lifter
      if ( SubsystemsConfig.enableLifter() )
      {
         Lifter.constructInstance();
         Lifter.getInstance().reset();
         lcdManager.appendSubsystem( 'L' );
         smartDashboard.putBoolean( "Lifter", true );
      }
      else
      {
         lcdManager.appendSubsystem( 'l' );
         smartDashboard.putBoolean( "Lifter", false );
         overall = false;
      }

      // Top level status
      smartDashboard.putBoolean( "SUBSYSTEMS", overall );

      System.out.println( myName + " initSubsystems() method exit" );
   }


   private void resetSubsystems()
   {
      System.out.println( myName + " resetSubsystems() method enter" );

      // Chassis
      if ( SubsystemsConfig.enableChassis() )
      {
         Chassis.getInstance().reset();
      }

      // Drive
      if ( SubsystemsConfig.enableDriveTrain() )
      {
         DriveTrain.getInstance().reset();
      }

      // Chassis
      if ( SubsystemsConfig.enableLifter() )
      {
         Lifter.getInstance().reset();
      }

      System.out.println( myName + " resetSubsystems() method exit" );
   }


   private void initOperatorControls()
   {
      System.out.println( myName + " initOperatorControls() method enter" );

      // HMI Controls
      HMIControllers.construct();
      lcdManager.appendSubsystem( 'H' );
      smartDashboard.putBoolean( "HMI", true );

      System.out.println( myName + " initOperatorControls() method exit" );
   }


   private void initAutonomousMode()
   {
      System.out.println( myName + " initAutonomousMode() method enter" );

      if ( prefsManager.runAutonomous() )
      {
         switch ( autoSelection )
         {
         case BarrelOnlyBump:
            autonomous = new BarrelOnlyBumpAutonomousMode( this );
            break;
         case BarrelOnlyNoBump:
            autonomous = new BarrelOnlyNoBumpAutonomousMode( this );
            break;
         case LeftThreeToteBump:
            // autonomous = new LeftThreeToteBumpAutonomousMode( this );
            autonomous = new BarrelUpBumpAutonomousMode( this );
            break;
         case RightThreeToteNoBump:
            // autonomous = new RightThreeToteNoBumpAutonomousMode( this );
            autonomous = new BarrelUpNoBumpAutonomousMode( this );
            break;
         case DoNothing:
            autonomous = new EmptyAutonomousMode( this );
            break;
         default:
            System.err.println( "Failed to determine autonomous mode" );
            autonomous = new EmptyAutonomousMode( this );
         }
      }
      else
      {
         autonomous = new EmptyAutonomousMode( this );
      }

      System.out.println( myName + " initAutonomousMode() method exit" );
   }


   private void initTeleoperatedMode()
   {
      System.out.println( myName + " initTeleoperatedMode() method enter" );

      teleoperated = new TeleoperatedMode( this );

      System.out.println( myName + " initTeleoperatedMode() method exit" );
   }


   /*
    * (non-Javadoc)
    *
    * @see edu.wpi.first.wpilibj.SimpleRobot#disabled()
    */
   @Override
   protected void disabled()
   {
      System.out.println( myName + " disabled() method enter" );

      // Reset the driver station GUI
      lcdManager.reset();

      // Ensure all the subsystems are shut down
      resetSubsystems();

      System.out.println( myName + " disabled() method exit" );
   }


   /*
    * (non-Javadoc)
    * 
    * @see edu.wpi.first.wpilibj.SimpleRobot#autonomous()
    */
   @Override
   public void autonomous()
   {
      /**
       * This function is called once each time the robot enters autonomous
       * mode.
       */
      System.out.println( myName + " autonomous() method running" );

      if ( autonomous == null )
      {
         initAutonomousMode();
      }

      // Reset the state of all the subsystems to known and "off"
      resetSubsystems();

      // Setup
      autonomous.setUp();

      // Do it all
      autonomous.runIt();

      // Clean up
      autonomous.cleanUp();

      System.out.println( myName + " leaving autonomous() method" );
   }


   /*
    * (non-Javadoc)
    * 
    * @see edu.wpi.first.wpilibj.SimpleRobot#operatorControl()
    */
   @Override
   public void operatorControl()
   {
      /*
       * This function is called once each time the robot enters operator
       * control.
       */
      System.out.println( myName + " operatorControl() method running" );

      if ( teleoperated == null )
      {
         initTeleoperatedMode();
      }

      // Reset the state of all the subsystems to known and "off"
      resetSubsystems();

      // Setup
      teleoperated.setUp();

      // Do it all
      teleoperated.runIt();

      // Clean up
      teleoperated.cleanUp();

      System.out.println( myName + " leaving operatorControl() method" );
   }


   /*
    * (non-Javadoc)
    * 
    * @see edu.wpi.first.wpilibj.SimpleRobot#test()
    */
   @Override
   public void test()
   {
      /**
       * This function is called once each time the robot enters test mode.
       */
      System.out.println( myName + " test() method running" );

      // Reset the state of all the subsystems to known and "off"
      resetSubsystems();

      lcdManager.setMode( "Test" );
      smartDashboard.putString( "MODE", "Test" );

      while ( isTest() && isEnabled() )
      {
         try
         {
            Thread.sleep( 250 );
         }
         catch ( final InterruptedException ex )
         {
            ex.printStackTrace();
         }
      }

      lcdManager.resetMode();
      smartDashboard.putString( "MODE", "Booted" );

      System.out.println( myName + " leaving test() method" );
   }

}
