package tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import server.Game;

public class Example implements Runnable{
	
	protected Socket socket;
    protected Game game;

    public Example(Socket clientSocket, Game g) {
        this.socket = clientSocket;
        this.game = g;
    }

    public void run() {
    	
    	try{
    	Scanner clientIn = new Scanner(socket.getInputStream());
        DataOutputStream clientOut = new DataOutputStream(socket.getOutputStream());
        
                
        //Game g = new Game();
        game.message("start 4");
        // read from the socket
        String clientLine = clientIn.nextLine();

        while(!clientLine.equals("/quit")){
        long modLine = Thread.currentThread().getId();
        clientOut.writeBytes(clientLine + modLine + "\n");
        game.message(clientLine + " " + modLine);
        clientOut.flush();
        clientLine = clientIn.nextLine();
        }
        clientOut.writeBytes("Goodbye");
        clientOut.flush();
         socket.close();
    	}catch(IOException ioe){
    		System.out.print("IOE");
    	}
    	
    	/*
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }*/
    	
    	
    }
}
