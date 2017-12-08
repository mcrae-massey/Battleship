package tests;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestDriver {

	public static void main(String[] args){
		// localhost because the Server is running on the local machine. A 'client' and
		// 'server' can run on the same host.
		try {
			// What is a port?			
			TestDriver client = new TestDriver("localhost", 1026); // What kind of port is this?					
            client.go();
		} 
		// Do not turn in code with stack traces turned on. This is for your debugging
		// purposes only!
		catch (IOException ioe ) {
			ioe.printStackTrace();
		}
	}

	/** Socket will be our connection to a server */
	Socket clientSocket;

	/**
	 * Creates client with a socket connected to a waiting server
	 *
	 * @param host hostname where the server lives.
	 * @param port port where the server is listening
	 *
	 * @throws IOException if we cannot create a socket.
	 */
	public TestDriver(String host, int port) throws IOException {
		/* 
		 * Create a Socket and initiate the TCP connection between the client and
		 * server. A DNS lookup will actually be performed before the TCP connection is
		 * initiated, but that is done for us.
		 */
		clientSocket = new Socket(host, port);
	}

	/**
	 * Get some information from the user, send it to a server, get a response, and then
	 * print out the response. Make many assumptions.  Example purposes only!
	 *
	 * @throws IOException if something goes wrong with out socket.
	 */
	public void go() throws IOException {
		// Create a 'stream' connected to the keyboard for user input.
		
        boolean keepGoing = true;
        
        //while(keepGoing){
        Scanner scanIn = new Scanner(System.in);

		// Create a 'stream' connected to the server to write data.
		DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

		// Create a 'stream' connected to the server to read data.
		Scanner clientIn = new Scanner(clientSocket.getInputStream());
        
        while(keepGoing){
		System.out.print("Enter a sentence to send to the server >\n");
		String sendLine = scanIn.nextLine();        // data from user

        // Why am I sending a newline?
		//for(int i = 0; i<5; i++){
        toServer.writeBytes(sendLine + "\n");       // write to the server
        toServer.flush();
		String recdLine = clientIn.nextLine();      // read from server
        if(!sendLine.equals("/quit")){
		System.out.println("The server sent back:");
		System.out.println("\t" + recdLine);
        }
        else{
            keepGoing = false;
        }
        }

		clientSocket.close();                       // close the TCP connection.
	}

}


