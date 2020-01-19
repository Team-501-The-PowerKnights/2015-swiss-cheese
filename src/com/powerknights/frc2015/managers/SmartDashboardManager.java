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


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * @author first.stu
 **/
public class SmartDashboardManager
{

   /** Singleton instance of class for all to use **/
   private static SmartDashboardManager ourInstance;

   /** Cached flag for whether to use Smart Dashboard **/
   private final boolean enableSmartDashboard;


   private SmartDashboardManager()
   {
      enableSmartDashboard =
         PreferencesManager.getInstance().useSmartDashboard();
   }


   /**
    * Constructs the singleton instance of the SmartDashboard manager. Assumed
    * to be called before any use of the manager; and verifies only called once.
    *
    * @throws IllegalStateException
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException(
            "SmartDashboardManager Already Constructed" );
      }
      ourInstance = new SmartDashboardManager();
   }


   /**
    * Returns the singleton instance of the SmartDashboard manager. Verifies the
    * manager has been successfully created prior to use.
    *
    * @return singleton instance of SmartDashboard manager
    * @throws IllegalStateException
    **/
   public static SmartDashboardManager getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException(
            "SmartDashboardManager Not Constructed Yet" );
      }
      return ourInstance;
   }


   public void putNumber( String key, double value )
   {
      if ( enableSmartDashboard )
      {
         SmartDashboard.putNumber( key, value );
      }
   }


   public void putBoolean( String key, boolean value )
   {
      if ( enableSmartDashboard )
      {
         SmartDashboard.putBoolean( key, value );
      }
   }


   public void putString( String key, String value )
   {
      if ( enableSmartDashboard )
      {
         SmartDashboard.putString( key, value );
      }
   }


   public void putTimeout( boolean value, String message )
   {
      putBoolean( "timeOut", !value ); // false is red, we want red on true
      putString( "timeOutFail", message );
   }

}
