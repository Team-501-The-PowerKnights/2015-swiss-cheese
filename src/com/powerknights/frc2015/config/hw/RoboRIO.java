/**
 * Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open Source
 * Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.config.hw;


/**
 * @author first.stu
 **/
public class RoboRIO
{

   // @formatter:off
   private static final boolean pwmChannels[] = {
      true,  // 0
      true,  // 1
      true,  // 2
      true,  // 3
      true,  // 4
      true,  // 5
      false, // 6
      false, // 7
      false, // 8
      false  // 9
   };
   private static final boolean dioChannels[] = {
      true,  // 0
      true,  // 1
      true,  // 2
      true,  // 3
      false, // 4
      false, // 5
      false, // 6
      false, // 7
      true,  // 8
      true   // 9
   };
   private static final boolean relayChannels[] = {
      false, // 0
      false, // 1
      false, // 2
      false, // 3
   };
   private static final boolean analogInChannels[] = {
      true,  // 0
      false, // 1
      false, // 2
      false, // 3
   };
   // @formatter:on

   /*
    * PWM Channels (0 - 9)
    */

   static public int getPwmChannel0()
   {
      if ( pwmChannels[ 0 ] )
      {
         return 0;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 0 not allocated" );
      }
   }


   static public int getPwmChannel1()
   {
      if ( pwmChannels[ 1 ] )
      {
         return 1;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 1 not allocated" );
      }
   }


   static public int getPwmChannel2()
   {
      if ( pwmChannels[ 2 ] )
      {
         return 2;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 2 not allocated" );
      }
   }


   static public int getPwmChannel3()
   {
      if ( pwmChannels[ 3 ] )
      {
         return 3;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 3 not allocated" );
      }
   }


   static public int getPwmChannel4()
   {
      if ( pwmChannels[ 4 ] )
      {
         return 4;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 4 not allocated" );
      }
   }


   static public int getPwmChannel5()
   {
      if ( pwmChannels[ 5 ] )
      {
         return 5;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 5 not allocated" );
      }
   }


   static public int getPwmChannel6()
   {
      if ( pwmChannels[ 6 ] )
      {
         return 6;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 6 not allocated" );
      }
   }


   static public int getPwmChannel7()
   {
      if ( pwmChannels[ 7 ] )
      {
         return 7;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 7 not allocated" );
      }
   }


   static public int getPwmChannel8()
   {
      if ( pwmChannels[ 8 ] )
      {
         return 8;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 8 not allocated" );
      }
   }


   static public int getPwmChannel9()
   {
      if ( pwmChannels[ 9 ] )
      {
         return 9;
      }
      else
      {
         throw new IllegalStateException( "PWM Channel 9 not allocated" );
      }
   }


   /*
    * DIO Channels (0 - 9)
    */

   static public int getDioChannel0()
   {
      if ( dioChannels[ 0 ] )
      {
         return 0;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 0 not allocated" );
      }
   }


   static public int getDioChannel1()
   {
      if ( dioChannels[ 1 ] )
      {
         return 1;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 1 not allocated" );
      }
   }


   static public int getDioChannel2()
   {
      if ( dioChannels[ 2 ] )
      {
         return 2;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 2 not allocated" );
      }
   }


   static public int getDioChannel3()
   {
      if ( dioChannels[ 3 ] )
      {
         return 3;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 3 not allocated" );
      }
   }


   static public int getDioChannel4()
   {
      if ( dioChannels[ 4 ] )
      {
         return 4;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 4 not allocated" );
      }
   }


   static public int getDioChannel5()
   {
      if ( dioChannels[ 5 ] )
      {
         return 5;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 5 not allocated" );
      }
   }


   static public int getDioChannel6()
   {
      if ( dioChannels[ 6 ] )
      {
         return 6;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 6 not allocated" );
      }
   }


   static public int getDioChannel7()
   {
      if ( dioChannels[ 7 ] )
      {
         return 7;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 7 not allocated" );
      }
   }


   static public int getDioChannel8()
   {
      if ( dioChannels[ 8 ] )
      {
         return 8;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 8 not allocated" );
      }
   }


   static public int getDioChannel9()
   {
      if ( dioChannels[ 9 ] )
      {
         return 9;
      }
      else
      {
         throw new IllegalStateException( "DI/O Channel 9 not allocated" );
      }
   }


   /*
    * Relay Channels (0 - 3)
    */

   static public int getRelayChannel0()
   {
      if ( relayChannels[ 0 ] )
      {
         return 0;
      }
      else
      {
         throw new IllegalStateException( "Relay Channel 0 not allocated" );
      }
   }


   static public int getRelayChannel1()
   {
      if ( relayChannels[ 1 ] )
      {
         return 1;
      }
      else
      {
         throw new IllegalStateException( "Relay Channel 1 not allocated" );
      }
   }


   static public int getRelayChannel2()
   {
      if ( relayChannels[ 2 ] )
      {
         return 2;
      }
      else
      {
         throw new IllegalStateException( "Relay Channel 2 not allocated" );
      }
   }


   static public int getRelayChannel3()
   {
      if ( relayChannels[ 3 ] )
      {
         return 3;
      }
      else
      {
         throw new IllegalStateException( "Relay Channel 3 not allocated" );
      }
   }


   /*
    * AnalogIn Channels (0 - 3)
    */

   static public int getAnalogInChannel0()
   {
      if ( analogInChannels[ 0 ] )
      {
         return 0;
      }
      else
      {
         throw new IllegalStateException( "AnalogIn Channel 0 not allocated" );
      }
   }


   static public int getAnalogInChannel1()
   {
      if ( analogInChannels[ 1 ] )
      {
         return 1;
      }
      else
      {
         throw new IllegalStateException( "AnalogIn Channel 1 not allocated" );
      }
   }


   static public int getAnalogInChannel2()
   {
      if ( analogInChannels[ 2 ] )
      {
         return 2;
      }
      else
      {
         throw new IllegalStateException( "AnalogIn Channel 2 not allocated" );
      }
   }


   static public int getAnalogInChannel3()
   {
      if ( analogInChannels[ 3 ] )
      {
         return 3;
      }
      else
      {
         throw new IllegalStateException( "AnalogIn Channel 3 not allocated" );
      }
   }

}
