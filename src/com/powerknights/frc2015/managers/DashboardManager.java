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


/**
 * @author first.stu
 */
public class DashboardManager
{

   /** Singleton instance of class for all to use **/
   private static DashboardManager ourInstance;


   protected DashboardManager()
   {
   }


   /**
    * Constructs the singleton instance of the Dashboard manager. Assumed to be
    * called before any use of the manager; and verifies only called once.
    *
    * @throws IllegalStateException
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException(
            "DashboardManager Already Constructed" );
      }
      ourInstance = new DashboardManager();
   }


   /**
    * Returns the singleton instance of the Dashboard manager. Verifies the
    * manager has been successfully created prior to use.
    *
    * @return singleton instance of Dashboard manager
    * @throws IllegalStateException
    **/
   public static DashboardManager getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException(
            "DashboardManager Not Constructed Yet" );
      }
      return ourInstance;
   }

}
