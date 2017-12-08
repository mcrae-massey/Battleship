package common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
	private PrintStream out;

	/**
	 * 
	 */
	private boolean isRunning;

	/**
	 * Constructor.
	 */
	public ConnectionAgent(Socket socket) {
		this.socket = socket;
		this.isRunning = false;
		try {
			in = new Scanner(new InputStreamReader(socket.getInputStream()));
			// set auto flush true
			out = new PrintStream(socket.getOutputStream(), true);
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
		return isRunning;
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
			while ((msg = in.nextLine()) != null && isRunning) {
				this.notifyReceipt(msg);
			}
		} catch (NoSuchElementException nsee) {
			this.close();
		}
		isRunning = false;
		try {
			this.socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.closeMessageSource();
	}
}
