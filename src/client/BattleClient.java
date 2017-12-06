package client;

import java.net.InetAddress;

import common.MessageSource;

public class BattleClient {
	/**
	 * 
	 */
	private InetAddress host;
	
	/**
	 * 
	 */
	private int port;
	
	/**
	 * 
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
	public void connect(){
		
	}
	
	/**
	 * 
	 */
	public void messageReceived(String message, MessageSource source){
		
	}
	
}
