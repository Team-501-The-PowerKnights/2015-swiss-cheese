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


/**
 * @author first.stu
 **/
public abstract class PWMSubsystem
   extends Subsystem
{

   /** Delta in input values to be considered 'zero' **/
   protected final double zeroInputValue = 0.01;


   protected PWMSubsystem()
   {
   }


   protected double capSpeed( double speed )
   {
      if ( speed > 1.0 )
      {
         speed = 1.0;
      }
      else if ( speed < -1.0 )
      {
         speed = -1.0;
      }
      return speed;
   }


   protected double ensurePositiveSpeed( double speed )
   {
      return Math.abs( speed );
   }


   protected double ensureNegativeSpeed( double speed )
   {
      return -Math.abs( speed );
   }


   /**
    * Determines whether input is to be considered 'zero' or not. For the
    * purposes of the various speed controllers, any value close to zero will
    * effectively be zero.
    *
    * @param inputValue - input to evaluate
    * @return <code>true</code> if close enough to zero, <code>false</code>
    *         otherwise
    **/
   protected boolean isZero( double inputValue )
   {
      return ( Math.abs( inputValue ) < zeroInputValue );
   }


   /**
    * Determines whether the input is positive or not.
    *
    * @param inputValue - input to evaluate
    * @return <code>true</code> if greater than 0, <code>false</code> otherwise
    **/
   protected boolean isPositive( double inputValue )
   {
      return ( inputValue > 0.0 );
   }


   /**
    * Determines whether the input is negative or not.
    *
    * @param inputValue - input to evaluate
    * @return <code>true</code> if less than 0, <code>false</code> otherwise
    **/
   protected boolean isNegative( double inputValue )
   {
      return ( inputValue < 0.0 );
   }

}
