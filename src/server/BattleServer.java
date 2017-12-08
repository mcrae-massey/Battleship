package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener {
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
	 * 
	 */
	private ArrayList<MessageListener> player = new ArrayList<MessageListener>();

	/**
	 * 
	 */
	private boolean isListening;

	/**
	 * Constructor.
	 */
	public BattleServer(int port) {
		isListening = false;
	}

	/**
	 * 
	 */
	public void listen() {
//		try {
//			while (this.isListening) {
//
//			}
//		} catch (IOException ioe) {
//			// stop running the server
//		}

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
		
	}

}
