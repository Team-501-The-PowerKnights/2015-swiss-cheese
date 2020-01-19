/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.ophmi;


/**
 * @author first.stu
 **/
public class InputTweaker
{

   private static double expm1Of1 = Math.expm1( 1.0 );


   //
   // input = 0.0 expm1 = 0.0
   // input = 0.1 expm1 = 0.06120702456008913
   // input = 0.2 expm1 = 0.12885124808584156
   // input = 0.3 expm1 = 0.2036096767023117
   // input = 0.4 expm1 = 0.2862305178902687
   // input = 0.5 expm1 = 0.3775406687981455
   // input = 0.6 expm1 = 0.47845399210662953
   // input = 0.7 expm1 = 0.5899804622735315
   // input = 0.8 expm1 = 0.7132362736976229
   // input = 0.9 expm1 = 0.8494550119673449
   // input = 1.0 expm1 = 0.9999999999999999
   //
   public static double exponentiate( double original )
   {
      original = limit( original );
      double retValue = Math.expm1( Math.abs( original ) ) / expm1Of1;
      if ( original < 0.0 )
      {
         retValue = -retValue;
      }
      return retValue;
   }


   public static double square( double original )
   {
      original = limit( original );
      if ( original >= 0.0 )
      {
         return ( original * original );
      }
      else
      {
         return -( original * original );
      }
   }


   private static double limit( double original )
   {
      if ( original > 1.0 )
      {
         return 1.0;
      }
      if ( original < -1.0 )
      {
         return -1.0;
      }
      return original;
   }

   // /**
   // * @param args
   // **/
   // public static void main( String[] args )
   // {
   // // testFormatFunction( );
   // // testMathFunctions( );
   // // testRealFunctions( );
   // }
   //
   // private static void testFormatFunction( )
   // {
   // Double value;
   //
   // value = 0.0;
   // System.out.println( StringUtils.format( value, 3 ) );
   // value = 0.30000000000000004;
   // System.out.println( StringUtils.format( value, 3 ) );
   // }
   //
   //
   // private static void testMathFunctions( )
   // {
   // double result;
   // double delta;
   // double percent;
   //
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.exp( input ) / MathUtils.exp( 1.0 );
   // System.out.print( "  exp   = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.expm1( input ) / MathUtils.expm1( 1.0 );
   // System.out.print( "  expm1 = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.log( input );
   // System.out.print( "  log   = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.log1p( input );
   // System.out.print( "  log1p = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.acos( input );
   // System.out.print( "  acos  = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.asin( input );
   // System.out.print( "  asin  = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // for ( double input = 0.0; input < 1.1; input += 0.1 )
   // {
   // System.out.print( "input   = " + StringUtils.format( input, 1 ) );
   // result = MathUtils.atan( input );
   // System.out.print( "  atan  = " + StringUtils.format( result, 6 ) );
   // delta = input - result;
   // System.out.print( ", delta = " + StringUtils.format( delta, 6 ) );
   // percent = ( result / input ) * 100;
   // System.out.println( ", percent = "
   // + StringUtils.format( percent, 2 ) );
   // }
   // System.out.println( "******" );
   // }
   //
   //
   // private static void testRealFunctions( )
   // {
   // double input;
   // double result;
   //
   // // Single Exponentiation
   // for ( input = -1.0; input < -0.05; input += 0.1 )
   // {
   // result = exponentiate( input );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   // for ( input = 0.0; input < 1.05; input += 0.1 )
   // {
   // result = exponentiate( input );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   //
   // // Triple Exponentiation
   // for ( input = -1.0; input < -0.05; input += 0.1 )
   // {
   // result = exponentiate( input );
   // result = exponentiate( result );
   // result = exponentiate( result );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   // for ( input = 0.0; input < 1.05; input += 0.1 )
   // {
   // result = exponentiate( input );
   // result = exponentiate( result );
   // result = exponentiate( result );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   //
   // // Single Squaring
   // for ( input = -1.0; input < -0.05; input += 0.1 )
   // {
   // result = square( input );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   // for ( input = 0.0; input < 1.05; input += 0.1 )
   // {
   // result = square( input );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   //
   // // Single Exponentation, Followed by Squaring
   // for ( input = -1.0; input < -0.05; input += 0.1 )
   // {
   // result = square( exponentiate( input ) );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   // for ( input = 0.0; input < 1.05; input += 0.1 )
   // {
   // result = square( exponentiate( input ) );
   // System.out.println( StringUtils.format( input, 2 ) + " , "
   // + StringUtils.format( result, 6 ) );
   // }
   // }

}
