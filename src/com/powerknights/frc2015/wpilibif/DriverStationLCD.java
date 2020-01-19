/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package com.powerknights.frc2015.wpilibif;


import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


// import edu.wpi.first.wpilibj.communication.FRCControl;
// import edu.wpi.first.wpilibj.communication.UsageReporting;
// import edu.wpi.first.wpilibj.parsing.IInputOutput;

/**
 * Provide access to "LCD" on the Driver Station. This is the Messages box on
 * the DS Operation tab.
 *
 * Buffer the printed data locally and then send it when UpdateLCD is called.
 */
public class DriverStationLCD
extends SensorBase
{

   private static DriverStationLCD m_instance;
   /**
    * Driver station timeout in milliseconds
    */
   public static final int kSyncTimeout_ms = 20;
   /**
    * Command to display text
    */
   public static final int kFullDisplayTextCommand = 0x9FFF;
   /**
    * Maximum line length for Driver Station display
    */
   public static final int kLineLength = 25;
   /**
    * Total number of lines available
    */
   public static final int kNumLines = 10;

   /**
    * Total number of characters needed for all lines
    */
   public static final int kTextBuffer = kNumLines * kLineLength;

   public static final String LINE_KEYS[] =
   { "String 0", // "DB/String 0",
      "String 1", // "DB/String 1",
      "String 2", // "DB/String 2",
      "String 3", // "DB/String 3",
      "String 4", // "DB/String 4",
      "String 5", // "DB/String 5",
      "String 6", // "DB/String 6",
      "String 7", // "DB/String 7",
      "String 8", // "DB/String 8",
      "String 9" // "DB/String 9",
   };

   /**
    * The line number on the Driver Station LCD
    */
   public static class Line
   {

      /**
       * The integer value representing this enumeration
       */
      public final int value;
      static final int kUser1_val = 0;
      static final int kUser2_val = 1;
      static final int kUser3_val = 2;
      static final int kUser4_val = 3;
      static final int kUser5_val = 4;
      static final int kUser6_val = 5;
      static final int kUser7_val = 6;
      static final int kUser8_val = 7;
      static final int kUser9_val = 8;
      static final int kUser10_val = 9;

      /**
       * Lines on the left side of default dashboard
       */
      public static final Line kUser1 = new Line( kUser1_val );
      public static final Line kUser2 = new Line( kUser2_val );
      public static final Line kUser3 = new Line( kUser3_val );
      public static final Line kUser4 = new Line( kUser4_val );
      public static final Line kUser5 = new Line( kUser5_val );

      /**
       * Lines on right side of default dashboard
       */
      public static final Line kUser6 = new Line( kUser6_val );
      public static final Line kUser7 = new Line( kUser7_val );
      public static final Line kUser8 = new Line( kUser8_val );
      public static final Line kUser9 = new Line( kUser9_val );
      public static final Line kUser10 = new Line( kUser10_val );


      private Line( int value )
      {
         this.value = value;
      }
   }

   StringBuffer[] m_textBuffer = new StringBuffer[ kNumLines ];


   /**
    * Get an instance of the DriverStationLCD
    *
    * @return an instance of the DriverStationLCD
    */
   public static synchronized DriverStationLCD getInstance()
   {
      if ( m_instance == null )
      {
         m_instance = new DriverStationLCD();
      }
      return m_instance;
   }


   /**
    * DriverStationLCD constructor.
    *
    * This is only called once the first time GetInstance() is called
    */
   private DriverStationLCD()
   {

      for ( int i = 0; i < kNumLines; i++ )
      {
         m_textBuffer[ i ] = new StringBuffer( "                          " );
      }
   }


   /**
    * Send the text data to the Driver Station.
    */
   public synchronized void updateLCD()
   {

      for ( int i = 0; i < kNumLines; ++i )
      {
         SmartDashboard
         .putString( LINE_KEYS[ i ], m_textBuffer[ i ].toString() );
      }
   }


   /**
    * Print formatted text to the Driver Station LCD text buffer.
    *
    * Use UpdateLCD() periodically to actually send the test to the Driver
    * Station.
    *
    * @param line The line on the LCD to print to.
    * @param startingColumn The column to start printing to. This is a 1-based
    *        number.
    * @param text the text to print
    */
   public void println( Line line, int startingColumn, String text )
   {
      int start = startingColumn - 1;
      int maxLength = kLineLength - start;

      if ( ( startingColumn < 1 ) || ( startingColumn > kLineLength ) )
      {
         throw new IndexOutOfBoundsException( "Column must be between 1 and "
            + kLineLength + ", inclusive" );
      }

      int length = text.length();
      int finalLength = ( length < maxLength ? length : maxLength );
      synchronized ( this )
      {
         for ( int i = 0; i < finalLength; i++ )
         {
            m_textBuffer[ line.value ].setCharAt( ( i + start ),
               text.charAt( i ) );
         }
      }
   }


   /**
    * Print formatted text to the Driver Station LCD text buffer.
    *
    * Use UpdateLCD() periodically to actually send the test to the Driver
    * Station.
    *
    * @param line The line on the LCD to print to.
    * @param startingColumn The column to start printing to. This is a 1-based
    *        number.
    * @param text the text to print
    */
   public void println( Line line, int startingColumn, StringBuffer text )
   {
      int start = startingColumn - 1;
      int maxLength = kLineLength - start;

      if ( ( startingColumn < 1 ) || ( startingColumn > kLineLength ) )
      {
         throw new IndexOutOfBoundsException( "Column must be between 1 and "
            + kLineLength + ", inclusive" );
      }

      int length = text.length();
      int finalLength = ( length < maxLength ? length : maxLength );
      synchronized ( this )
      {
         for ( int i = 0; i < finalLength; i++ )
         {
            m_textBuffer[ line.value ].setCharAt( ( i + start ),
               text.charAt( i ) );
         }
      }
   }


   /**
    * Clear User Messages box on DS Operations Tab
    *
    * This method will clear all text currently displayed in the message box
    */
   public void clear()
   {
      synchronized ( this )
      {
         for ( int i = 0; i < kNumLines; i++ )
         {
            for ( int j = 0; j < kLineLength; j++ )
            {
               m_textBuffer[ i ].setCharAt( j, ' ' );
            }
         }
      }
      updateLCD();
   }

}
