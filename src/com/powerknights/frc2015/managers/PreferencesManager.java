/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.managers;


import edu.wpi.first.wpilibj.Preferences;


/**
 *
 *
 **/
public class PreferencesManager
{

   /** Singleton instance of class for all to use **/
   private static PreferencesManager ourInstance;


   private PreferencesManager()
   {
   }


   /**
    * Constructs the singleton instance of the Preferences manager. Assumed to
    * be called before any use of the manager; and verifies only called once.
    *
    * @throws IllegalStateException
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException(
            "PreferencesManager Already Constructed" );
      }
      ourInstance = new PreferencesManager();
   }


   /**
    * Returns the singleton instance of the Preferences manager. Verifies the
    * manager has been successfully created prior to use.
    *
    * @return singleton instance of Preferences manager
    * @throws IllegalStateException
    **/
   public static PreferencesManager getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException(
            "PreferencesManager Not Constructed Yet" );
      }
      return ourInstance;
   }


   /** Using independent stu drive mode (true) or WPI (false) **/
   public boolean useStuDriveMode()
   {
      final String key = "useStuDriveMode";
      return getBoolean( key, false );
   }


   public boolean doFlattenDriverInputResponse()
   {
      final String key = "doFlattenDriverInput";
      return getBoolean( key, false );
   }


   public boolean doFlattenOperatorInputResponse()
   {
      final String key = "doFlattenOperatorInput";
      return getBoolean( key, false );
   }


   /** Whether to do anything in autonomous mode **/
   public boolean runAutonomous()
   {
      final String key = "runAutonomous";
      return getBoolean( key, false );
   }


   /** Whether to use PIDs to hold subsystem components **/
   public boolean usePIDHolds()
   {
      final String key = "usePIDHolds";
      return getBoolean( key, false );
   }


   /**
    * Whether to use the lifter encoder reset at the bottom
    **/
   public boolean useLifterEncoderReset()
   {
      final String key = "useLifterEncoderReset";
      return getBoolean( key, false );
   }


   /** Whether to use RoboRealm for aiming support (true) or not (false) **/
   public boolean runRoboRealm()
   {
      final String key = "runRoboRealm";
      return getBoolean( key, false );
   }


   /** Whether to use RoboRealm for aiming support (true) or not (false) **/
   public boolean useRoboRealm()
   {
      final String key = "useRoboRealm";
      return getBoolean( key, false );
   }


   /** Whether to display / use SmartDashboard (true) or not (false) **/
   public boolean useSmartDashboard()
   {
      final String key = "useSmartDashboard";
      return getBoolean( key, false );
   }


   /** Whether to display / use LiveWindow (true) or not (false) **/
   public boolean useLiveWindow()
   {
      final String key = "useLiveWindow";
      return getBoolean( key, false );
   }


   /** Whether to run thread timing prints (true) or not (false) **/
   public boolean runThreadTiming()
   {
      final String key = "runThreadTiming";
      return getBoolean( key, false );
   }


   public double getScaledDriverNonTurboSpeed()
   {
      final String key = "scaledDriverNonTurboSpeed";
      return getDouble( key, 1.0 );

   }


   public double getScaledOperatorNonTurboSpeed()
   {
      final String key = "scaledOperatorNonTurboSpeed";
      return getDouble( key, 1.0 );
   }


   public double getAutoDriveForwardTime()
   {
      final String key = "autoDriveForwardTime";
      return getDouble( key, 1.0 );
   }


   public String getDriverController()
   {
      final String key = "driverController";
      return getString( key, "Xbox360Gamepad" );
   }


   public String getOperatorController()
   {
      final String key = "operatorController";
      return getString( key, "Xbox360Gamepad" );
   }


   public double getFeederToteDistance()
   {
      final String key = "feederToteDistance";
      return getDouble( key, 3.76 );
   }


   public int getFeederToteHeight()
   {
      final String key = "feederToteHeight";
      return getInt( key, 1364 );
   }


   private boolean getBoolean( String key, boolean ifMissing )
   {
      final Preferences prefs = Preferences.getInstance();
      if ( !prefs.containsKey( key ) )
      {
         System.out.println( "Error: Preference not found: " + key );
      }
      return prefs.getBoolean( key, ifMissing );
   }


   private int getInt( String key, int ifMissing )
   {
      final Preferences prefs = Preferences.getInstance();
      if ( !prefs.containsKey( key ) )
      {
         System.out.println( "Error: Preference not found: " + key );
      }
      return prefs.getInt( key, ifMissing );
   }


   private double getDouble( String key, double ifMissing )
   {
      final Preferences prefs = Preferences.getInstance();
      if ( !prefs.containsKey( key ) )
      {
         System.out.println( "Error: Preference not found: " + key );
      }
      return prefs.getDouble( key, ifMissing );
   }


   private String getString( String key, String ifMissing )
   {
      final Preferences prefs = Preferences.getInstance();
      if ( !prefs.containsKey( key ) )
      {
         System.out.println( "Error: Preference not found: " + key );
      }
      return prefs.getString( key, ifMissing );
   }

}
