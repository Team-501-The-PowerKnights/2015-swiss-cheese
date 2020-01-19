/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 **/

package com.powerknights.frc2015.utils;


/**
 * @author first.stu
 **/
public abstract class StoppableThread
extends Thread
{

   /** Time to sleep between work method invocations **/
   protected final long loopDelay; // msec

   /** Whether it is time to stop (exit) **/
   protected volatile boolean quit;
   /** Whether running or not **/
   protected boolean running;
   /** Whether it has finished running **/
   protected boolean hasRun;


   public StoppableThread( String name, long delay )
   {
      super( name );

      loopDelay = delay;

      // Initialize here, just in case we stop before we start
      quit = false;
      running = false;
      hasRun = false;
   }


   public void setUp()
   {
      // Default does nothing; override if desired
   }


   /**
    * This method is the 'worker' method that does the cyclic processing of the
    * thread. As long as it returns <code>false</code>, the thread will sleep
    * and then loop to invoke it again. Once it returns <code>true</code>, the
    * loop will be exited.
    *
    * @return whether done with the processing
    **/
   public abstract boolean doIt();


   public void cleanUp()
   {
      // Default does nothing; override if desired
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

      while ( !shouldQuit() )
      {
         boolean done = doIt();
         if ( done )
         {
            quit = true;
            continue;
         }

         try
         {
            Thread.sleep( loopDelay );
         }
         catch ( InterruptedException ex )
         {
            interrupt();
         }
      }

      finalizeRunningState();

      cleanUp();
   }


   protected void initializeRunningState()
   {
      running = true;
   }


   protected void finalizeRunningState()
   {
      running = false;
      hasRun = true;
   }


   public void quit()
   {
      quit = true;
      interrupt();
   }


   protected boolean shouldQuit()
   {
      return quit;
   }


   public boolean isRunning()
   {
      return running;
   }


   public boolean hasRun()
   {
      return hasRun;
   }


   /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "StoppableThread " + getName() + " [loopDelay=" + loopDelay
         + ", quit=" + quit + ", running=" + running + ", hasRun=" + hasRun
         + "]";
   }

}
