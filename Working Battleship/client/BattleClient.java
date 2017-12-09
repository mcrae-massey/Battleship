package client;

import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;


import common.MessageListener;
import common.ConnectionAgent;
import common.MessageSource;

public class BattleClient extends MessageSource implements MessageListener{
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

    private Thread t;

    /**
     *
     */
    private ConnectionAgent ca;

    /**
     *
     */
    private Socket clientSocket;

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
        connect();
	}
	
	/**
	 * 
	 */
	public void connect(){
	    
        try{
            clientSocket = new Socket(host, port);
            ca = new ConnectionAgent(clientSocket);
            ca.addMessageListener(new PrintStreamMessageListener(System.out));
            this.addMessageListener(new PrintStreamMessageListener(System.out));            
            t = new Thread(ca);
            t.start();
            //System.out.println("trying to join");
            ca.sendMessage("join " +  username);
            
        }
        catch(IOException e){
            System.out.print(e.getMessage());
        }
	}
	
	/**
	 * 
	 */
	public void messageReceived(String message, MessageSource source){
		notifyReceipt(message);
        //ca.sendMessage(message);
	}
	

    public void sourceClosed(MessageSource source){
        //notifyReceipt("Closed");
        //closeMessageSource();       
        try{
            clientSocket.close();
        }
        catch(IOException ioe){
            //do nothing, already closed
        }

        ca.close();
        System.exit(0);
    }

    public void send(String message){
        ca.sendMessage(message);
    }
}
