package server;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

import common.ConnectionAgent;
import common.MessageSource;
import common.MessageListener;

public class BattleServer implements MessageListener{
	/**
	 * 
	 */
	private ServerSocket serverSocket;
	
    private Socket socket;
	/**
	 * 
	 */
	//private int current;
	
    private int port;

    private ArrayList<ConnectionAgent> players;

    private boolean isListening;
	/**
	 * 
	 */
	private Game game;
	
    private int size;
	/**
	 * Constructor.
	 */
	public BattleServer(int port, int size) {
        this.port = port;
        players = new ArrayList<ConnectionAgent>();
        isListening = true;
        game = new Game();
        game.setSize(size);
        try{
        serverSocket = new ServerSocket(port);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
	}
	
	/**
	 * 
	 */
	public void listen() {
        try{
            while(this.isListening){
                //System.out.println("Trying to listen");
                //serverSocket =  new ServerSocket(port);
                socket = serverSocket.accept();
                try{
                    ConnectionAgent ca = new ConnectionAgent(socket);
                    Thread thread = new Thread(ca);
                    ca.addMessageListener(this);
                    players.add(ca);
//                    System.out.println("Trying to start");
                    thread.start();
                }catch(Exception e){
                    //e.printStackTrace();
                    //ignore meeeeee
                }
            }            
        }catch(IOException ioe){
            shutDown();
        }
	}

    public void shutDown(){
        if(this.isListening){
            for(ConnectionAgent i: players){
                i.close();
            }
            this.isListening = false;
            System.exit(0);
        }
    }
	
	/**
	 * 
	 */
	public void broadcast(String message) {
   //     System.out.println("Trying to broadcast");
		for(ConnectionAgent i: players){
            i.sendMessage(message);
        }
	}
	
	/**
	 * 
	 */
	public void messageReceived(String message, MessageSource source) {
	 //   System.out.println("Trying to print messageReceived message: " + message);
    
     boolean quit = false;
     String s = game.message(message + " " + source.hashCode(), source);
     if(!s.isEmpty()){
         System.out.println(s);
         if(s.contains("GAME")){
            quit = true;            
         }
         broadcast(s);
     }
     //broadcast(s);
     if(quit){
        this.shutDown();
     }
        

	}
	
	/**
	 * 
	 */
	public void sourceClosed(MessageSource source) {
		//closeMessageSource();
        this.players.remove(source);
	}
	
	
}
