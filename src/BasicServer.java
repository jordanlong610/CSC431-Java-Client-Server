import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.lang.Runnable;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;

public class BasicServer
{
	/**
	 * Simple server to take in a string of text 10 characters long, reverse it's order
	 * and then send it back to the client.
	 *
	 * 
	 * @author Jordan Long, Chris Lashley
	 */


    public static void main(String[] args) throws IOException 
    {
        /**
         * A server is created listening on port 4446. Each time a new
         * client connects to the server, a new thread is created
         * for that specific client.
         */
    	
		ServerSocket server = new ServerSocket(4446);
        System.out.println("Waiting for client...");
        
        while (true)
        {
        	Socket socket = server.accept();
            new ConnectionHandler(socket);
        }
        //server.close();
    }
}

class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;

        Thread t = new Thread(this);
        t.start();
    }

    public void run()
    {
        try
        {
        	/**
			 *  Read message from Client. Prints to console for reference.
			 */

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String inputMsg = (String) input.readObject();
            System.out.println("Client: " + inputMsg);

            
            /**
             * Perform string reversal.
             */
            
            String outputMsg = new StringBuffer(inputMsg).reverse().toString();

            /**
             *  Return message to client. 
             */

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(outputMsg);
            
            /**
             * Closes all streams and finally the socket. Message appears to show client
             * connection has terminated and is ready for new connection.
             */
            input.close();
            output.close();
            socket.close();
            
            System.out.println("Waiting for client...");
        } 
        
        
        catch (IOException e)
        {
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}