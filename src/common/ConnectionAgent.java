package common;

import java.io.PrintStream;
import java.net.Socket;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ConnectionAgent extends MessageSource implements Runnable{
	/**
	 * 
	 */
	private Socket socket;
	
	/**
	 * 
	 */
	private Scanner in;
    
    /**
     *
     */
//    private Game game;	

	/**
	 * 
	 */
	private PrintStream out;
	
	/**
	 * 
	 */
//	private Thread thread;
	
    /**
     *
     */
    private boolean connected;

	/**
	 * Constructor.
	 */
	public ConnectionAgent(Socket socket) {		
        this.socket = socket;
		connected = true;
		try {
			in = new Scanner(new InputStreamReader(socket.getInputStream()));			
			out = new PrintStream(socket.getOutputStream(), true);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}		
	}
	
	
	/**
	 * 
	 */
	public void sendMessage(String message) {
//        System.out.println("Trying to send message " + message);        
		out.println(message);
    }
	
	/**
	 * 
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * 
	 */
	public void close() {
		try{
            this.socket.close();
            connected = false;
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        closeMessageSource();
		
	}
	
	/**
	 * 
	 */
	public void run() {
  //      System.out.println("Trying to run");
	    String msg;
        try{
            while((msg = in.nextLine()) != null && connected){
    //            System.out.println("trying to notifyReceipt");
                this.notifyReceipt(msg);
            }
        }catch(NoSuchElementException nsee){
            this.close();
        }
        connected = false;		       
        close();        
	}
}

