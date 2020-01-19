/**
 * /** Copyright (c) Team 501 Power Knights 2015. All Rights Reserved. Open
 * Source Software - May be modified and shared by FRC teams. The code must be
 * accompanied by the FIRST BSD license file in the root directory of the
 * project. You may also obtain a copy of it from the following:
 * http://www.opensource.org/licenses/bsd-license.php.
 *
 * See (Git) repository metadata for author and revision history for this file.
 **/

package com.powerknights.frc2015.roborealm;


/**
 * @author first.connor
 **/
public class RoboRealmServer
   extends Thread // used to be StoppableThread
{

   /** Singleton instance of class for all to use **/
   private static RoboRealmServer ourInstance;

   // /** Server we create to accept incoming connections / data **/
   // protected ServerSocketConnection server = null;
   //
   // /** Utility wrapper to DriverStation LCD **/
   // private final LCDManager lcdManager;
   //
   /** Flag for whether new data has arrived **/
   private boolean newData;
   /** Flag for whether new data is valid or not **/
   private boolean validUpdate;
   /** Number of valid updates **/
   private final long numValidUpdates;
   /** Number of invalid updates **/
   private final long numInvalidUpdates;
   /** Decimated count of updates (for feedback) **/
   private final long updateCount;


   private RoboRealmServer()
   {
      super( "RoboRealm Server" );

      // lcdManager = LCDManager.getInstance();
      //
      // createServerSocket( NetworkConfig.getRoboRealmPort() );
      //
      // No data has arrived yet (may never)
      newData = false;
      validUpdate = false;
      numValidUpdates = 0;
      numInvalidUpdates = 0;
      updateCount = 0;
   }


   public static void constructInstance()
   {
      if ( ourInstance != null )
      {
         throw new IllegalStateException(
            "RoboRealm Server Already Constructed" );
      }
      ourInstance = new RoboRealmServer();
   }


   public static synchronized RoboRealmServer getInstance()
   {
      if ( ourInstance == null )
      {
         throw new IllegalStateException(
            "RoboRealm Server Not Constructed Yet" );
      }
      return ourInstance;
   }


   private void setValidUpdate( boolean validUpdate )
   {
      this.validUpdate = validUpdate;
   }


   public boolean isValidUpdate()
   {
      return validUpdate;
   }


   /**
    * @return the newData
    **/
   public boolean hasNewData()
   {
      return newData;
   }


   /**
    * @param newData the newData to set
    **/
   private void setNewData( boolean newData )
   {
      this.newData = newData;
   }


   private void createServerSocket( int port )
   {
      // try
      // {
      // server = (ServerSocketConnection) Connector.open( "socket://:" + port
      // );
      // lcdManager.setRoboRealmStarted();
      // }
      // catch ( IOException ex )
      // {
      // System.err.println( "Failed to create server socket on port " + port );
      // ex.printStackTrace();
      // server = null;
      // }
   }


   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run()
   {
      // if ( server == null )
      // {
      // lcdManager.resetRoboRealm();
      // return;
      // }
      //
      // while ( !isStopped() )
      // {
      // try
      // {
      // SocketConnection client = acceptClient();
      // Runnable handler = new ClientHandler( client );
      // new Thread( handler ).start();
      // lcdManager.setRoboRealmConnected();
      // }
      // catch ( Exception ex )
      // {
      // System.err.println( "Error in client accept handling" );
      // break;
      // }
      // }
   }

   // private SocketConnection acceptClient()
   // throws Exception
   // {
   // SocketConnection client = null;
   // boolean exception = false;
   // try
   // {
   // client = (SocketConnection) server.acceptAndOpen();
   // client.setSocketOption( SocketConnection.LINGER, 0 );
   // System.out.println( "RoboRealm::acceptClient()" );
   // }
   // catch ( Exception ex )
   // {
   // System.err.println( "Problem in client socket accept: " + ex );
   // exception = true;
   // }
   // finally
   // {
   // if ( exception )
   // {
   // System.err.println( "Client socket failed" );
   // if ( client != null )
   // {
   // client.close();
   // }
   // throw new Exception( "Client socket failed" );
   // }
   // }
   // return client;
   // }
   //
   // private class ClientHandler
   // implements Runnable
   // {
   //
   // private final InputStream is;
   //
   //
   // public ClientHandler( SocketConnection socket )
   // throws IOException
   // {
   // is = socket.openInputStream();
   // }
   //
   //
   // /*
   // * (non-Javadoc)
   // *
   // * @see java.lang.Runnable#run()
   // */
   // @Override
   // public void run()
   // {
   // // StringBuffer logBuf = new StringBuffer( );
   //
   // StringBuffer buf = new StringBuffer();
   //
   // String valueString;
   //
   // boolean firstData = true;
   // boolean done = false;
   // while ( !done )
   // {
   // // logBuf.setLength( 0 );
   // try
   // {
   // char c;
   // boolean bValue;
   // int iValue;
   // double dValue;
   //
   // // frame start delimiter
   // buf.setLength( 0 );
   // while ( ( c = (char) is.read() ) != '<' )
   // {
   // }
   // if ( firstData )
   // {
   // lcdManager.setRoboRealmUpdating();
   // firstData = false;
   // }
   // // logBuf.append( "Time => " ).append(
   // // System.currentTimeMillis( ) / 1000 );
   //
   // // validity
   // buf.setLength( 0 );
   // while ( ( c = (char) is.read() ) != ',' )
   // {
   // buf.append( c );
   // }
   // valueString = buf.toString();
   // bValue = valueString.equalsIgnoreCase( "true" );
   // setValidUpdate( bValue );
   // // logBuf.append( ", Valid = " ).append( validUpdate );
   // boolean updateLCD = false;
   // if ( !validUpdate )
   // {
   // numInvalidUpdates++;
   // updateLCD = true;
   // }
   // else
   // {
   // numValidUpdates++;
   // if ( ( numValidUpdates % 10 ) == 0 )
   // {
   // updateCount++;
   // updateLCD = true;
   // }
   // }
   // if ( updateLCD )
   // {
   // lcdManager.updateRoboRealm( updateCount, numInvalidUpdates );
   // }
   //
   // // offset
   // buf.setLength( 0 );
   // while ( ( c = (char) is.read() ) != ',' )
   // {
   // buf.append( c );
   // }
   // valueString = buf.toString();
   // iValue = Integer.parseInt( valueString );
   // setOffset( iValue );
   // // logBuf.append( ", Offset = " ).append( offset );
   //
   // // distance (vertical)
   // buf.setLength( 0 );
   // while ( ( c = (char) is.read() ) != ',' )
   // {
   // buf.append( c );
   // }
   // valueString = buf.toString();
   // dValue = Double.parseDouble( valueString );
   // setVerticalDistance( dValue );
   // // logBuf.append( ", V_Distance = " ).append( vertDistance
   // // );
   //
   // // distance (horizontal)
   // buf.setLength( 0 );
   // while ( ( c = (char) is.read() ) != '>' )
   // {
   // buf.append( c );
   // }
   // valueString = buf.toString();
   // dValue = Double.parseDouble( valueString );
   // setHorizontalDistance( dValue );
   // // logBuf.append( ", H_Distance = " ).append( horizDistance
   // // );
   //
   // // System.out.println( logBuf.toString( ) );
   // }
   // catch ( IOException ex )
   // {
   // System.err.println( "RoboRealm socket error" );
   // ex.printStackTrace();
   // done = true;
   // }
   // catch ( NumberFormatException ex )
   // {
   // System.err.println( "RoboRealm number format error" );
   // ex.printStackTrace();
   // }
   // }
   //
   // lcdManager.setRoboRealmStarted();
   // }
   // }

}
