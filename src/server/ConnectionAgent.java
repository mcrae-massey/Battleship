package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import common.MessageSource;

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
	private PrintStream out;
	
	/**
	 * 
	 */
	private Thread thread;
	
	/**
	 * Constructor.
	 */
	public ConnectionAgent(Socket socket) {
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * 
	 */
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 */
	public boolean isConnected() {
		return false;
	}

	/**
	 * 
	 */
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

