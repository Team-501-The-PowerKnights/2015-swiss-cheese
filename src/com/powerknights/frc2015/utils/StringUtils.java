/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.utils;


/**
 * @author first.stu
 **/
public class StringUtils
{

   static public String format( double value, int digits )
   {
      String valueString = Double.toString( value );
      int dotLoc = valueString.indexOf( '.' );
      if ( dotLoc < 0 )
      {
         return valueString;
      }
      int numDigits = valueString.length() - dotLoc - 1;
      if ( numDigits < digits )
      {
         StringBuffer buf = new StringBuffer( valueString );
         for ( int i = 0; i < ( digits - numDigits ); i++ )
         {
            buf.append( "0" );
         }
         return buf.toString();
      }
      else if ( numDigits == digits )
      {
         return valueString;
      }
      else
      {
         return valueString.substring( 0, ( dotLoc + digits + 1 ) );
      }
   }

}
