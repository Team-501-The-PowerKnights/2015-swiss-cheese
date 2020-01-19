/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.subsystems.pids;


/**
 * @author first.stu
 **/
public interface IPID
{

   /**
    * Starts the PID, which includes initializing the set point to the current
    * location.
    **/
   public void start();


   /**
    * Sets the current value of the set point to be the current location of the
    * input source. Because we are working from internal use of the PID input,
    * the values (including any reversing) is automatically handled.
    **/
   public void setSetPoint();


   /**
    * Sets the current value of the set point to be the value passed in. This
    * assumes a positive value regardless of the sign, and handles the negation
    * via the reversing control.
    *
    * @param newSetPoint - new set point value to use
    **/
   public void setSetPoint( double newSetPoint );


   /**
    * Gets the current value of the set point.
    *
    * @return current value of the set point
    **/
   public double getSetPoint();


   /**
    * Stops the PID, and resets all the state information associated with the
    * run that just completed.
    **/
   public void stop();

}
