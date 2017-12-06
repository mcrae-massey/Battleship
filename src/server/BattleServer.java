package server;

import java.net.ServerSocket;

import common.MessageSource;

public class BattleServer {
	/**
	 * 
	 */
	private ServerSocket serverSocket;
	
	/**
	 * 
	 */
	private int current;
	
	/**
	 * 
	 */
	private Game game;
	
	/**
	 * Constructor.
	 */
	public BattleServer(int port) {
	}
	
	/**
	 * 
	 */
	public void listen() {
	}
	
	/**
	 * 
	 */
	public void broadcast(String message) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 */
	public void messageReceived(String message, MessageSource source) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 */
	public void sourceClosed(MessageSource source) {
		// TODO Auto-generated method stub
	}
	
	
}
