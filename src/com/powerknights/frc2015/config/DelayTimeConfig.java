/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.config;


/**
 * @author first.stu
 **/
public class DelayTimeConfig
{

   /*
    *
    */
   public static final double minLoopPercent = 0.10; // 10%

   // All delays are in msec; getters convert appropriately

   /*
    * Driver Update Rate (for driver changes)
    */
   private static final long driverController = 10; // 25


   public static long getDriverControllerDelay()
   {
      return driverController;
   }


   public static double getDriverControllerDelaySec()
   {
      return driverController / 1000;
   }

   /*
    * Operator Update Rate (for operator changes)
    */
   private static final long operatorController = 10; // 25


   public static long getOperatorControllerDelay()
   {
      return operatorController;
   }


   public static double getOperatorControllerDelaySec()
   {
      return operatorController / 1000;
   }

   /*
    * Dashboard Update Rate (for triggering updates)
    */
   private static final long dashboardUpdater = 200;


   public static long getDashboardUpdaterDelay()
   {
      return dashboardUpdater;
   }


   public static double getDashboardUpdaterDelaySec()
   {
      return dashboardUpdater / 1000;
   }

   /*
    * Sensor Update Rate (for triggering updates)
    */
   private static final long sensorUpdater = 200;


   public static long getSensorUpaterDelay()
   {
      return sensorUpdater;
   }


   public static double getSensorUpaterDelaySec()
   {
      return sensorUpdater / 1000;
   }

   /*
    * Match Time Update Rate (for time count down)
    */
   private static final long matchTimeUpdater = 1000;


   public static long getMatchTimeUpdaterDelay()
   {
      return matchTimeUpdater;
   }


   public static double getMatchTimeUpdaterDelaySec()
   {
      return matchTimeUpdater / 1000;
   }

   /*
    * History Collect Rate (for triggering collects)
    */
   private static final long historyCollector = 200;


   public static long getHistoryCollectorDelay()
   {
      return historyCollector;
   }


   public static double getHistoryCollectorDelaySec()
   {
      return historyCollector / 1000;
   }

   /*
    * History Collection Size (I know it's not a time)
    */
   private static final int historySize = 100;


   public static int getHistoryCollectionSize()
   {
      return historySize;
   }

}
