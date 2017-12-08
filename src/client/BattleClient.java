package client;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import common.MessageListener;
import common.MessageSource;

public class BattleClient extends MessageSource implements MessageListener {
	
	/**
	 * 
	 */
	private Socket socket;

	/**
	 * 
	 */
	private InetAddress host;

	/**
	 * user port
	 */
	private int port;

	/**
	 * Name of the user
	 */
	private String username;

	/**
	 * 
	 * @param host
	 * @param port
	 * @param username
	 */
	public BattleClient(InetAddress host, int port, String username) throws UnknownHostException {
		this.host = host;
		this.port = port;
		this.username = username;
		connect();
	}

	/**
	 * 
	 */
	public void connect() {

	}

	/**
	 * 
	 */
	public void messageReceived(String message, MessageSource source) {
		notifyReceipt(message);
	}

	/**
	 * 
	 */
	public void sourceClosed(MessageSource source) {

	}

	/**
	 * 
	 */
	public void send(String message) {

	}

}
