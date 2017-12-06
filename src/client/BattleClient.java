package client;

import java.net.InetAddress;

import common.MessageListener;
import common.MessageSource;

public class BattleClient extends MessageSource implements MessageListener {

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
	public BattleClient(InetAddress host, int port, String username) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
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
