/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.utils;


import com.powerknights.frc2015.config.DelayTimeConfig;
import com.powerknights.frc2015.managers.PreferencesManager;


/**
 * @author first.stu
 **/
public abstract class LoggedStoppableThread
   extends StoppableThread
{

   /*
    * Note - All times are kept in microseconds (usec)
    */

   /** Time to sleep between work method invocations **/
   private final long loopDelay;
   /** How long is the minimum time we sleep for in loop **/
   private final long minSleepDelay;

   /** Start time hack for this controller thread **/
   private long startTime;
   /** Next time to run based on multiple of sleep delay from start time **/
   private long nextTime;
   /** How many times the loop has run **/
   private long loopCount;

   /** Last time checked (used for whether we meet loop timing or not **/
   private long lastTime;
   /** Current time (used for whether we meet loop timing or not **/
   private long nowTime;
   /** Buffer for logging timing data **/
   private final StringBuilder logBuf;
   /** Buffer for logging error data **/
   private final StringBuilder errorBuf;


   /**
    * @param name
    * @param delay
    **/
   public LoggedStoppableThread( String name, long delay )
   {
      super( name, delay );

      loopDelay = TimeUtils.msecToUsec( delay );
      minSleepDelay = (long) ( loopDelay * DelayTimeConfig.minLoopPercent );

      // Initialize here, just in case we stop before we start
      loopCount = 0;

      logBuf = new StringBuilder();
      errorBuf = new StringBuilder();
   }


   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Thread#run()
    */
   @Override
   public void run()
   {
      setUp();

      initializeRunningState();
      startTime = TimeUtils.microTime();
      nextTime = startTime;

      while ( !shouldQuit() )
      {
         boolean done = doIt();
         if ( done )
         {
            quit = true;
            continue;
         }

         // how many iterations
         loopCount++;
         // target time for next wakeup
         nextTime += loopDelay;
         // did we make it?
         logTimerData();

         try
         {
            // how long to delay?
            long sleepDelay = nextTime - TimeUtils.microTime();
            // did we make it? if not, log error and skip
            if ( sleepDelay > minSleepDelay )
            {
               // resolution for sleep is msec
               Thread.sleep( TimeUtils.usecToMsec( sleepDelay ) );
            }
            else
            {
               logMissedTiming( sleepDelay );
            }
         }
         catch ( InterruptedException ex )
         {
            interrupt();
         }
      }

      finalizeRunningState();

      cleanUp();
   }


   protected void logTimerData()
   {
      if ( !PreferencesManager.getInstance().runThreadTiming() )
      {
         return;
      }

      nowTime = TimeUtils.microTime();

      if ( ( loopCount > 1 ) && ( ( loopCount % 20 ) == 0 ) )
      {
         logBuf.setLength( 0 );
         logBuf.append( "***** @ " );
         logBuf.append( loopCount );
         logBuf.append( " " );
         logBuf.append( getName() );
         logBuf.append( " => Target: " );
         logBuf.append( TimeUtils.usecToMsec( loopDelay ) );
         logBuf.append( ", Real: " );
         long deltaTime = nextTime - nowTime;
         logBuf.append( TimeUtils.usecToMsec( deltaTime ) );
         deltaTime = nowTime - lastTime - loopDelay;
         logBuf.append( ", Process: " );
         logBuf.append( TimeUtils.usecToMsec( deltaTime ) );

         System.out.println( logBuf.toString() );
      }

      lastTime = nowTime;
   }


   private void logMissedTiming( long sleepDelay )
   {
      if ( !PreferencesManager.getInstance().runThreadTiming() )
      {
         return;
      }

      errorBuf.setLength( 0 );
      errorBuf.append( "ERROR @ " );
      errorBuf.append( loopCount );
      errorBuf.append( " " );
      errorBuf.append( getName() );
      errorBuf.append( " Target: " );
      errorBuf.append( TimeUtils.usecToMsec( loopDelay ) );
      errorBuf.append( ", Real: " );
      errorBuf.append( sleepDelay );

      System.err.println( errorBuf.toString() );
   }


   /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "StoppableThread " + getName() + " [delay=" + loopDelay
         + ", quit=" + quit + ", running=" + running + ", hasRun=" + hasRun
         + "]";
   }

}
