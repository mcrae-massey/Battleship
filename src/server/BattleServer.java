package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.ConnectionAgent;
import common.MessageListener;
import common.MessageSource;

public class BattleServer implements MessageListener {
	/**
	 * Welcome socket.
	 */
	private ServerSocket serverSocket;

	/**
	 * client socket.
	 */
	private Socket socket;

	/**
	 * 
	 */
	private ConnectionAgent thread;

	/**
	 * 
	 */
	private int current;

	/**
	 * 
	 */
	private int port;

	/**
	 * 
	 */
	private Game game;

	/**
	 * 
	 */
	private ArrayList<ConnectionAgent> players;

	/**
	 * 
	 */
	private boolean isListening;

	/**
	 * Constructor.
	 */
	public BattleServer(int port) {
		this.port = port;
		players = new ArrayList<ConnectionAgent>();
		isListening = true;
		game = new Game();
	}

	/**
	 * 
	 */
	public void shutDown() {
		//close connections
		if (this.isListening) {
			for (ConnectionAgent i : players) {
				i.close();
			}
			this.isListening = false;
			// exit server
			System.exit(0);
		}
	}

	/**
	 * 
	 */
	public void listen() {
		try {
			while (this.isListening) {
				socket = serverSocket.accept();
				try {
					// thread = new ConnectionAgent(socket);
					new Thread(new ConnectionAgent(socket, game)).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException ioe) {
			//should we shut down here?
			shutDown();
		}

	}

	/**
	 * 
	 */
	public void broadcast(String message) {
		for (ConnectionAgent i : players) {
			i.sendMessage(message);
		}
	}

	/**
	 * 
	 */
	public void messageReceived(String message, MessageSource source) {
		game.message(message);
	}

	/**
	 * 
	 */
	public void sourceClosed(MessageSource source) {
		players.remove(source);
	}

}
