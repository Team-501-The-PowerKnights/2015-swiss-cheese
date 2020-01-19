/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.modes.autonomous;


import com.powerknights.frc2015.subsystems.Lifter;
import com.powerknights.frc2015.subsystems.Lifter.LiftPosition;

import edu.wpi.first.wpilibj.RobotBase;


/**
 * @author first.stu
 **/
public abstract class LifterAutonomousMode
extends AutonomousMode
{

   /** Handle to lifter **/
   protected final Lifter lifter;


   /**
    * @param robot
    **/
   protected LifterAutonomousMode( RobotBase robot )
   {
      super( robot );

      lifter = Lifter.getInstance();
   }


   /**
    * Performs busy wait to give time for the lifter to reach it's commanded
    * position.
    *
    * @param position lifter position to wait for
    * @return <code>true</code> if position reached; <code>false</code> if not
    **/
   protected boolean waitForLiftPosition( LiftPosition position )
   {
      final long waitForLiftTimeout = 5000; // msec

      if ( !lifter.isInPosition( position ) )
      {
         long time = 0;
         while ( !lifter.isInPosition( position )
            && ( time < waitForLiftTimeout ) )
         {
            sleep( 10 );
            time += 10;
         }
      }

      boolean success = lifter.isInPosition( position );
      if ( !success )
      {
         smartDashboard.putTimeout( true, "waitForLiftPosition()" );
      }

      return success;
   }

}
