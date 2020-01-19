/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.sensors;


import edu.wpi.first.wpilibj.AnalogInput;


/**
 * @author first.stu
 **/
public class AutonomousSelector
extends Sensor< Double >
{

   private final AnalogInput sensor;

   public enum AutonomousSelection
   {
      // @formatter:off
      BarrelOnlyBump( 0.179 ),
      BarrelOnlyNoBump( 0.437 ),
      LeftThreeToteBump( 0.830 ),
      RightThreeToteNoBump( 1.433 ),
      DoNothing( 1.666 ),
      Unknown( 0.0 );
      // @formatter:on

      private final double setting;


      private AutonomousSelection( double setting )
      {
         this.setting = setting;
      }


      public double getSetting()
      {
         return setting;
      }


      private static AutonomousSelection getSelection( double setting )
      {
         double midpoint;

         midpoint =
            BarrelOnlyBump.setting
            + ( ( BarrelOnlyNoBump.setting - BarrelOnlyBump.setting ) / 2 );
         if ( setting < midpoint )
         {
            return BarrelOnlyBump;
         }
         midpoint =
            BarrelOnlyNoBump.setting
               + ( ( LeftThreeToteBump.setting - BarrelOnlyNoBump.setting ) / 2 );
         if ( setting < midpoint )
         {
            return BarrelOnlyNoBump;
         }
         midpoint =
            LeftThreeToteBump.setting
            + ( ( RightThreeToteNoBump.setting - LeftThreeToteBump.setting ) / 2 );
         if ( setting < midpoint )
         {
            return LeftThreeToteBump;
         }
         midpoint =
            RightThreeToteNoBump.setting
            + ( ( DoNothing.setting - RightThreeToteNoBump.setting ) / 2 );
         if ( setting < midpoint )
         {
            return RightThreeToteNoBump;
         }
         return DoNothing;
      }

   }


   /**
    * @param channel
    **/
   public AutonomousSelector( int channel )
   {
      super( new AnalogInput( channel ) );
      // Now that it's created in superclass, typecase locally
      sensor = (AnalogInput) sendable;
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.sensors.Sensor#reset()
    */
   @Override
   public void reset()
   {
      // Nothing to do for this one
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.sensors.Sensor#get()
    */
   @Override
   public Double get()
   {
      return sensor.getAverageVoltage();
   }


   /**
    * Returns the enumeration for which autonomous mode was selected.
    *
    * @return
    **/
   public AutonomousSelection getSelection()
   {
      return AutonomousSelection.getSelection( get() );
   }


   /*
    * (non-Javadoc)
    * 
    * @see com.powerknights.frc2015.controllers.ISensorUpdater#update()
    */
   @Override
   public void update()
   {
      smartDashboard.putNumber( "autoSelector", get() );
   }

}
