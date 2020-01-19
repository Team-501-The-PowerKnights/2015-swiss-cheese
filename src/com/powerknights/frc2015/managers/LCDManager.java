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


import com.powerknights.frc2015.wpilibif.DriverStationLCD;


/**
 * @author first.cody
 **/
public class LCDManager
{

   /** Maximum columns in LCD we can display **/
   private static final int maxColumns = DriverStationLCD.kLineLength;

   /** Singleton instance of class for all to use **/
   private static LCDManager ourInstance;

   /** Handle to the framework LCD **/
   private final DriverStationLCD userLCD;

   private final StringBuffer line1Buf;
   private final StringBuffer line2Buf;
   private final StringBuffer line3Buf;
   private final StringBuffer line4Buf;
   private final StringBuffer line5Buf;
   private final StringBuffer line6Buf;
   private final StringBuffer line7Buf;


   private LCDManager()
   {
      userLCD = DriverStationLCD.getInstance();

      line1Buf = new StringBuffer();
      line2Buf = new StringBuffer();
      line3Buf = new StringBuffer();
      line4Buf = new StringBuffer();
      line5Buf = new StringBuffer();
      line6Buf = new StringBuffer();
      line7Buf = new StringBuffer();

      init();
   }


   /**
    * Constructs the singleton instance of the LCD manager. Assumed to be called
    * before any use of the manager; and verifies only called once.
    *
    * @throws IllegalStateException
    **/
   public static synchronized void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException( "LCDManager Already Constructed" );
      }
      ourInstance = new LCDManager();
   }


   /**
    * Returns the singleton instance of the LCD manager. Verifies the manager
    * has been successfully created prior to use.
    *
    * @return singleton instance of LCD manager
    * @throws IllegalStateException
    **/
   public static LCDManager getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException( "LCDManager Not Constructed Yet" );
      }
      return ourInstance;
   }


   private void init()
   {
      resetConfig();
      resetMode();
      resetRoboRealm();
      resetTime();
      resetMessage();
      resetBuild();

      reset();
   }


   public void reset()
   {
      resetUnused();
   }


   private void resetUnused()
   {
      line4Buf.setLength( 0 );

      userLCD.updateLCD();
   }


   /**
    *
    **/
   private void trimAndFillLine( StringBuffer buf )
   {
      final int length = buf.length();
      final int numBlanks = maxColumns - length;
      if ( numBlanks < 0 )
      {
         System.err.println( "**** Exceeded line length for LCD line" );
         System.err.println( "  =>" + buf.toString() + "<=" );
         buf.setLength( maxColumns );
      }
      else
      {
         for ( int i = 0; i < numBlanks; i++ )
         {
            buf.append( " " );
         }
      }
   }


   // private void truncateLine( StringBuffer buf )
   // {
   // int length = buf.length( );
   // if ( length == 0 )
   // {
   // return;
   // }
   // int index = length - 1;
   // while ( buf.charAt( index ) == ' ' )
   // {
   // buf.deleteCharAt( index );
   // --index;
   // }
   // }

   /*
    * Line 1 - Config
    */

   public void setConfig( String config )
   {
      line1Buf.setLength( 0 );

      line1Buf.append( config );

      trimAndFillLine( line1Buf );
      userLCD.println( DriverStationLCD.Line.kUser1, 1, line1Buf.toString() );
      userLCD.updateLCD();
   }

   private int subsystemIndex = maxColumns - 1;


   public void appendSubsystem( char subsystem )
   {
      trimAndFillLine( line1Buf );
      line1Buf.setCharAt( subsystemIndex, subsystem );
      subsystemIndex--;

      trimAndFillLine( line1Buf );
      userLCD.println( DriverStationLCD.Line.kUser1, 1, line1Buf.toString() );
      userLCD.updateLCD();
   }


   private void resetConfig()
   {
      line1Buf.setLength( 0 );

      trimAndFillLine( line1Buf );
      userLCD.println( DriverStationLCD.Line.kUser1, 1, line1Buf.toString() );
      userLCD.updateLCD();
   }


   /*
    * Line 2 - Mode
    */

   public void setMode( String mode )
   {
      line2Buf.setLength( 0 );

      line2Buf.append( "MODE: " );
      line2Buf.append( mode );

      trimAndFillLine( line2Buf );
      userLCD.println( DriverStationLCD.Line.kUser2, 1, line2Buf.toString() );
      userLCD.updateLCD();
   }


   public void resetMode()
   {
      line2Buf.setLength( 0 );

      line2Buf.append( "MODE: " );

      trimAndFillLine( line2Buf );
      userLCD.println( DriverStationLCD.Line.kUser2, 1, line2Buf.toString() );
      userLCD.updateLCD();
   }


   /*
    * Line 3 - RoboRealm
    */

   public void setRoboRealmStarted()
   {
      line3Buf.setLength( 0 );

      line3Buf.append( "RRS: Started" );

      trimAndFillLine( line3Buf );
      userLCD.println( DriverStationLCD.Line.kUser3, 1, line3Buf.toString() );
      userLCD.updateLCD();
   }


   public void setRoboRealmConnected()
   {
      line3Buf.setLength( 0 );

      line3Buf.append( "RRS: Connected" );

      trimAndFillLine( line3Buf );
      userLCD.println( DriverStationLCD.Line.kUser3, 1, line3Buf.toString() );
      userLCD.updateLCD();
   }


   public void setRoboRealmUpdating()
   {
      line3Buf.setLength( 0 );

      line3Buf.append( "RRS: Updating" );

      trimAndFillLine( line3Buf );
      userLCD.println( DriverStationLCD.Line.kUser3, 1, line3Buf.toString() );
      userLCD.updateLCD();
   }


   public void updateRoboRealm( long numValid, long numInvalid )
   {
      line3Buf.setLength( 0 );

      line3Buf.append( "RRS: Update " );
      line3Buf.append( numValid );
      line3Buf.append( "[" );
      line3Buf.append( numInvalid );
      line3Buf.append( "]" );

      trimAndFillLine( line3Buf );
      userLCD.println( DriverStationLCD.Line.kUser3, 1, line3Buf.toString() );
      userLCD.updateLCD();
   }


   public void resetRoboRealm()
   {
      line3Buf.setLength( 0 );

      line3Buf.append( "RRS: " );

      trimAndFillLine( line3Buf );
      userLCD.println( DriverStationLCD.Line.kUser3, 1, line3Buf.toString() );
      userLCD.updateLCD();
   }


   /*
    * Line 4 - Unused
    */

   /*
    * Line 5 - Time
    */

   public void setTime( String time )
   {
      line5Buf.setLength( 0 );

      line5Buf.append( "TIME: " );
      line5Buf.append( time );

      trimAndFillLine( line5Buf );
      userLCD.println( DriverStationLCD.Line.kUser5, 1, line5Buf.toString() );
      userLCD.updateLCD();
   }


   public void resetTime()
   {
      line5Buf.setLength( 0 );

      line5Buf.append( "TIME: " );

      trimAndFillLine( line5Buf );
      userLCD.println( DriverStationLCD.Line.kUser5, 1, line5Buf.toString() );
      userLCD.updateLCD();
   }


   /*
    * Line 6 - message
    */

   public void logMessage( String message )
   {
      line6Buf.setLength( 0 );

      line6Buf.append( message );

      trimAndFillLine( line6Buf );
      userLCD.println( DriverStationLCD.Line.kUser6, 1, line6Buf.toString() );
      userLCD.updateLCD();
   }


   public void resetMessage()
   {
      line6Buf.setLength( 0 );

      trimAndFillLine( line6Buf );
      userLCD.println( DriverStationLCD.Line.kUser6, 1, line6Buf.toString() );
      userLCD.updateLCD();
   }


   /*
    * Line 7 - message
    */

   public void setBuild( String build )
   {
      line7Buf.setLength( 0 );

      line7Buf.append( build );

      trimAndFillLine( line7Buf );
      userLCD.println( DriverStationLCD.Line.kUser7, 1, line7Buf.toString() );
      userLCD.updateLCD();
   }


   public void resetBuild()
   {
      line7Buf.setLength( 0 );

      trimAndFillLine( line7Buf );
      userLCD.println( DriverStationLCD.Line.kUser7, 1, line7Buf.toString() );
      userLCD.updateLCD();
   }

}
