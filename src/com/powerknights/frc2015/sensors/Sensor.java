/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.sensors;


import com.powerknights.frc2015.controllers.ISensorUpdater;
import com.powerknights.frc2015.controllers.SensorUpdater;
import com.powerknights.frc2015.managers.DriverStationManager;
import com.powerknights.frc2015.managers.LCDManager;
import com.powerknights.frc2015.managers.LiveWindowManager;
import com.powerknights.frc2015.managers.SmartDashboardManager;

import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;


/**
 * first.stu
 **/
abstract public class Sensor< T >
   implements ISensorUpdater, LiveWindowSendable
{

   /** Handle to driver station to get inputs **/
   protected final DriverStationManager dsManager;
   /** Handle to LCD manager to display status **/
   protected final LCDManager lcdManager;
   /** Handle to smart dashboard **/
   protected final SmartDashboardManager smartDashboard;

   /** Handle to live window **/
   protected final LiveWindowManager liveWindow;

   /** Handle to the sensor we are wrapping **/
   protected final LiveWindowSendable sendable;


   protected Sensor( LiveWindowSendable sensor )
   {
      sendable = sensor;

      dsManager = DriverStationManager.getInstance();
      lcdManager = LCDManager.getInstance();
      smartDashboard = SmartDashboardManager.getInstance();

      liveWindow = LiveWindowManager.getInstance();

      SensorUpdater.getInstance().addSensor( this );
   }


   /**
    * Resets the sensor to some known state.
    **/
   public abstract void reset();


   /**
    * Get the value of the sensor (in whatever form it takes as the primary
    * public interface). This is used for the value in the dashboard.
    *
    * @return current value of sensor
    **/
   public abstract T get();


   /*
    * (non-Javadoc)
    * 
    * @see
    * edu.wpi.first.wpilibj.Sendable#initTable(edu.wpi.first.wpilibj.tables.
    * ITable)
    */
   @Override
   public void initTable( ITable subtable )
   {
      sendable.initTable( subtable );
   }


   /*
    * (non-Javadoc)
    * 
    * @see edu.wpi.first.wpilibj.Sendable#getTable()
    */
   @Override
   public ITable getTable()
   {
      return sendable.getTable();
   }


   /*
    * (non-Javadoc)
    * 
    * @see edu.wpi.first.wpilibj.Sendable#getSmartDashboardType()
    */
   @Override
   public String getSmartDashboardType()
   {
      return sendable.getSmartDashboardType();
   }


   /*
    * (non-Javadoc)
    * 
    * @see edu.wpi.first.wpilibj.livewindow.LiveWindowSendable#updateTable()
    */
   @Override
   public void updateTable()
   {
      sendable.updateTable();
   }


   /*
    * (non-Javadoc)
    * 
    * @see
    * edu.wpi.first.wpilibj.livewindow.LiveWindowSendable#startLiveWindowMode()
    */
   @Override
   public void startLiveWindowMode()
   {
      sendable.startLiveWindowMode();
   }


   /*
    * (non-Javadoc)
    * 
    * @see
    * edu.wpi.first.wpilibj.livewindow.LiveWindowSendable#stopLiveWindowMode()
    */
   @Override
   public void stopLiveWindowMode()
   {
      sendable.stopLiveWindowMode();
   }

}
