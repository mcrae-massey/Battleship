package client;

import java.net.InetAddress;
import java.util.Scanner;
/**
 * BattleShipDriver contains the main() method for the server. It parses command line options, instantiates a
 * BattleServer, and calls its listen() method. This takes two command line arguments, the port number for
 * the server and the size of the board (if the size is left off, default to size 10 x 10). We are assuming square
 * arrays.
 *
 * @author CJ Bland and McRae Massey
 * @version December 8, 2017
 */
public class BattleDriver {

	public static void main(String[] args){
		if(args.length == 3){
            try{
                InetAddress host = InetAddress.getByName(args[0]);
                int port = Integer.parseInt(args[1]);
                BattleClient bc = new BattleClient(host, port, args[2]);
                
                boolean keepGoing = true;
                Scanner input = new Scanner(System.in);

                while(keepGoing){
                    String sendLine = input.nextLine();
                    bc.send(sendLine);
                    if(sendLine.equals("/quit")){
                        keepGoing = false;
                    }
                }            
                bc.sourceClosed(null);

            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Invalid params, must be <hostname, portnumber," + 
            " and user nickname>");
        }
        /*for(int i = 0; i< args.length; i++){
			System.out.println(args[i]);
		}*/
	}

	
}
