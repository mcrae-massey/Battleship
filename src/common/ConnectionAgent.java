package common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import server.Game;

public class ConnectionAgent extends MessageSource implements Runnable {
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
	private Game game;

	/**
	 * 
	 */
	private PrintStream out;

	/**
	 * 
	 */
	private boolean connected;

	/**
	 * Constructor.
	 */
	public ConnectionAgent(Socket socket, Game game) {
		this.game = game;
		this.socket = socket;
		this.connected = false;
		try {
			in = new Scanner(new InputStreamReader(socket.getInputStream()));
			// set auto flush true
			out = new PrintStream(socket.getOutputStream(), true);
			connected = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void sendMessage(String message) {
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
		try {
			this.socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		closeMessageSource();
	}

	/**
	 * 
	 */
	public void run() {
		String msg;
		try {
			while ((msg = in.nextLine()) != null && connected) {
				this.notifyReceipt(msg);
			}
		} catch (NoSuchElementException nsee) {
			this.close();
		}
		connected = false;
		try {
			this.socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.closeMessageSource();
	}
}
