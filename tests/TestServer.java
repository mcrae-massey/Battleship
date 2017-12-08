package tests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import server.Game;

public class TestServer {
	/** Listen for clients knocking at the door (port) */
    ServerSocket welcomeSocket;
    protected static Game g;
    /**
     * Create a Server with a listening socket to accept client connections
     *
     * @param port port where the server is listening
     *
     * @throws IOException if we cannot create a socket.
     */
    public TestServer(int port) throws IOException {
        // socket listens for incoming connections.
        welcomeSocket = new ServerSocket(port);
        g = new Game();
    }

    /**
     * Accept connections from clients, Get data they send and send it back
     * modified. Makes MANY assumptions. -- <b>For example puposes only!</b>
     *
     * @throws IOException if we have problems with our sockets.
     */
    public void go() throws IOException {

        // THIS IS NOT A VALID WAY TO WAIT FOR SOCKET CONNECTIONS!, You should
        // not have a forever loop or while(true) 
        for (; ;) {
            Socket connectionSocket = welcomeSocket.accept();            
            //g = new Game();            
            new Thread(new Example(connectionSocket, g)).start();        
        }
    }

    /**
     * Main is a method that should be fully commented!
     *
     * @param args not used.
     */
    public static void main(String[] args){
    	
        try {    
        	TestServer server = new TestServer(1026);
            server.go();        	
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
    }
}
