/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.subsystems.pids;


import com.powerknights.frc2015.sensors.LiftHeight;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;


/**
 * first.stu
 **/
public abstract class LiftHeightPID
   implements IPID
{

   protected static class PIDParams
   {

      public double P;
      public double I;
      public double D;
      public double period;

      public double minInput;
      public double maxInput;

      public double minOutput;
      public double maxOutput;
   }

   protected static final double defaultP = 0.0085; // 0.0075
   protected static final double defaultI = 0.000;
   protected static final double defaultD = 0.000;
   protected static final double defaultPeriod = 0.001; // msec

   // Floor is at 0, counts up to top
   protected static final double defaultMinInput = 0;
   protected static final double defaultMaxInput = 3500;

   // Slower speed lowering, faster lifing up
   protected static final double defaultMinOutput = -0.40; // -0.30;
   protected static final double defaultMaxOutput = +0.75;

   /** Height for lift position **/
   private final LiftHeight liftHeight;
   /** Speed controller for lifter **/
   @SuppressWarnings( "unused" )
   private final SpeedController lift;

   /** Whether sense of PID is reversed **/
   private final boolean reverse;

   /** PID controller **/
   protected final PIDController myPID;


   public LiftHeightPID( LiftHeight liftHeight, SpeedController lift,
      PIDParams params, boolean reverse )
   {
      this.reverse = reverse;

      PIDSource source = new LiftPIDSource( liftHeight );
      PIDOutput output = new LiftPIDOutput( lift );

      myPID =
         new PIDController( params.P, params.I, params.D, source, output,
            params.period );

      myPID.disable();
      if ( reverse )
      {
         myPID.setInputRange( -params.maxInput, -params.minInput );
         myPID.setOutputRange( -params.maxOutput, -params.minOutput );
      }
      else
      {
         myPID.setInputRange( params.minInput, params.maxInput );
         myPID.setOutputRange( params.minOutput, params.maxOutput );
      }

      // Only for debugging stuff
      this.liftHeight = liftHeight;
      this.lift = lift;
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.subsystems.pids.IPID#start()
    */
   @Override
   public void start()
   {
      setSetPoint();
      myPID.enable();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.subsystems.pids.IPID#setSetPoint()
    */
   @Override
   public void setSetPoint()
   {
      myPID.setSetpoint( liftHeight.get() );
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.subsystems.pids.IPID#setSetPoint(double)
    */
   @Override
   public void setSetPoint( double newSetPoint )
   {
      if ( reverse )
      {
         newSetPoint = -newSetPoint;
      }
      myPID.setSetpoint( newSetPoint );
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.subsystems.pids.IPID#getSetPoint()
    */
   @Override
   public double getSetPoint()
   {
      return myPID.getSetpoint();
   }


   /*
    * (non-Javadoc)
    *
    * @see com.powerknights.frc2015.subsystems.pids.IPID#stop()
    */
   @Override
   public void stop()
   {
      myPID.disable();
      myPID.reset();
   }

   /**
    *
    *
    **/
   protected class LiftPIDSource
      implements PIDSource
   {

      private final LiftHeight height;


      LiftPIDSource( LiftHeight height )
      {
         this.height = height;
      }


      /*
       * (non-Javadoc)
       *
       * @see edu.wpi.first.wpilibj.PIDSource#pidGet()
       */
      @Override
      public double pidGet()
      {
         return height.get();
      }

   }

   /**
    *
    *
    **/
   protected class LiftPIDOutput
      implements PIDOutput
   {

      private final SpeedController motor;


      LiftPIDOutput( SpeedController motor )
      {
         this.motor = motor;
      }


      /*
       * (non-Javadoc)
       *
       * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
       */
      @Override
      public void pidWrite( double output )
      {
         motor.set( output );
      }

   }

}
