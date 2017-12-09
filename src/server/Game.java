package server;

import java.util.ArrayList;

import common.*;
/**
 * A class that contains the logic for a Battleship game. It has a Grid for each client
 *
 * @author CJ Bland and McRae Massey
 * @version December 8, 2017
 */
public class Game {

	/** A list of usernames, index matches the index of their thread id and board*/
	private ArrayList<String> usernames;
	
	/** A list of Grids, one for each user, index matches the index of their name*/
	private ArrayList<Grid> boards;

	/** List of ids, matches the name and Grid index*/
	private ArrayList<Integer> userID;
	
	/** Default size of a game board*/
	private final int DEFAULT_SIZE = 10;
	
	/** User provided size of a gameboard*/
	private int size;
	
	/** The number of the current turn, used with a mod operator to determine 
	 * which user can attack*/
	private int currentTurn;
	
	/** A bool which is true when a game is started, false all other times*/
	boolean started = false;

    private ConnectionAgent ca;
	/** A default constructor which initializes the arraylists and sets the size to the default */
	public Game(){
		usernames = new ArrayList<String>();
		userID = new ArrayList<Integer>();
		boards = new ArrayList<Grid>();
		size = DEFAULT_SIZE;       
	}

	/**
	 * The method which handles the parsing and calling of other methods, determines if the 
	 * user has sent a valid command and executes it. 
	 * @param messages will be what the user inputs, as well as the ID of the user/thread 
	 * which sent it 
	 */
	public String message(String message, MessageSource ms){
        ConnectionAgent ca = (ConnectionAgent) ms;
        String broadcast = "";
	    	String[] parsed = message.split(" ");
        
       		for(int i = 0; i < parsed.length; i++){
                	parsed[i].toLowerCase();
	            }

		//System.out.println(message + "\n");

	    	switch(parsed[0].toLowerCase()){

		//default when server starts, will send "start [int size of board]"
		case("start"):
			if(parsed.length==2){
				if(isInteger(parsed[1])){
					size = Integer.parseInt(parsed[1]);
				}
            }
			/*	else{
					//System.out.println("Invalid response, must enter valid size of board");
					size = DEFAULT_SIZE;
				}
			}
			else{
				size = DEFAULT_SIZE;
			}*/
		break;

		// ex "join username"
		case ("join"):
			if(parsed.length == 3){
				broadcast = join(parsed[1], Integer.parseInt(parsed[2]));
			}
            else{
                ca.sendMessage("Error, incorrect parameters, must be 'join username'");
            }
		break;

		// ex "/play username"
		case("/play"):
			if(!started){
				broadcast = play();
			}
			else{
				ca.sendMessage("/to user/ Game already in progress");
			}
		break;
		
		// ex "/attack user coord coord userID"
		case("/attack"):
			//don't move to next turn until valid command
			
			//if there is a game going
			if(started){
				//if this is the correct num of params and user and id exist
				if(parsed.length == 5 && isUser(parsed[1]) && isID(Integer.parseInt(parsed[4]))){
					//if the coords are integers
					if(isInteger(parsed[2]) && isInteger(parsed[3])){
						//if the coords within size of board
						if(Integer.parseInt(parsed[2]) <= size && Integer.parseInt(parsed[3]) <= size){
							//if your not attacking yourself
							if(usernames.indexOf(parsed[1]) != userID.indexOf(Integer.parseInt(parsed[4]))){
								//if its your turn
								if(currentTurn % usernames.size() == userID.indexOf(Integer.parseInt(parsed[4]))){
									broadcast = attack(parsed[1].toLowerCase(), Integer.parseInt(parsed[2]), Integer.parseInt(parsed[3]), Integer.parseInt(parsed[4]));
								}
								else{
									ca.sendMessage("/to user/ Move Failed, player turn: " + usernames.get(currentTurn%usernames.size()) + "\n");
								}
							}
							else{
								ca.sendMessage("/to user/ Cannot attack yourself\n");
							}
						}
						else{
							ca.sendMessage("/to user/ Invalid command: coordinates out of bound\n");
						}
					}
					else{
					    ca.sendMessage("/to user/ Invalid command: coordinates not numbers\n");
					}
				}
				else{
					ca.sendMessage("/to user/ Invalid command: " + parsed.toString() +"\n");
				}
			}
			else{
				ca.sendMessage("/to user/ Game not in progress\n");
			}
		break;

		// ex "/show user userID
		case("/show"):
			if(started){
				if(isUser(parsed[1]) && isUser(parsed[1])){
 					show(parsed[1], Integer.parseInt(parsed[2]), ca);
				}
				else{
					ca.sendMessage("/to user/ Invalid command: " + parsed.toString());
				}
			}
			else{
				ca.sendMessage("/to user/ Game not in progress");
			}
		break;

		// ex "/quit userID"
		case("/quit"):
			broadcast = quit(Integer.parseInt(parsed[1]));
			break;

        default :
            ca.sendMessage("Invalid command, must start with the '/' character");
		}
        //System.out.println(broadcast);
        return broadcast;

	}

	/**
	 * Helper method to determine if a string is an integer
	 * @param s The string to be evaluated
	 * @return true if it is an integer, false if not
	 */
	public boolean isInteger(String s) {
		boolean bool = true;

		try { 
			Integer.parseInt(s); 
		} 
		catch(NumberFormatException e) { 
			bool = false; 
		} 
		catch(NullPointerException e) {
			bool= false;
		}

		return bool;
	}

   public void setSize(int size){
        this.size = size;
   }

	/** 
	 * Helper method to determine if a given string is a username alread in use
	 * @param name The username to be evaluated
	 * @return true if it is a taken username, false if it is free
	 */
	public boolean isUser(String name){
		boolean answer = false;
		for(int i = 0; i < usernames.size(); i++){
			if(name.equals(usernames.get(i))){
				answer = true;
			}
		}
		return answer;
	}
	
	/** 
	 * Determines if a given ID is already used. (Should always return false, no two threads 
	 * should have the same id
	 * @param id the id to be evaluated 
	 * @return true if it is an id in use, false if it is unused
	 */
	public boolean isID(int id){
		boolean answer = false;
		for(int i = 0; i < userID.size(); i++){
			if(userID.get(i).equals(id)){
				answer = true;
			}
		}
		return answer;
	}
	
	/** 
	 * Command of the game, checks to see if the username is taken, if free, registers them in 
	 * usernames and userID
	 *
	 * @param username The username given by a user
	 * @param id the id number of the client thread
	 */
	public String join(String username, int id){
		//done automatically, username and id added to list, dupes not allowed
		boolean valid = true;
        String broadcast = "";
		String user = username.toLowerCase();
		for(int i = 0; i<usernames.size(); i++){
			if(user.equals(usernames.get(i))){
				valid = false;
			}
		}

		if(valid){
			usernames.add(user);
			userID.add(id);
			broadcast += ("/broadcast/ !!!" + username + " has joined\n");
		}
		else{
			ca.sendMessage("/to user/Username already in use, please choose another\n");
		}

        return broadcast;
        //System.out.println(usernames.size());
	}

	/** 
	 * Command of the game, if two players have joined, start the game, after that ignore any
	 * other play commands until the game is over	  
	 */
	public String play(){
        String broadcast = "";
		if(usernames.size()>=2){
			for(int i = 0; i<usernames.size(); i++){
				//System.out.println(i);
				Grid g = new Grid(size);
				g.makeGrid();
				boards.add(g);
			}
			started = true;
			broadcast += ("/broadcast/ The game begins\n");
			currentTurn = 0;
			broadcast += (usernames.get(currentTurn % usernames.size()) + " it is your turn\n");
		}	
		else{			
			ca.sendMessage("/to user/ Not enough players to play the game\n");
		}
        return broadcast;
	}

	/** 
	 * Command of the game, tries to attack a user using the given name and coordinates
	 *
	 * @param username the user to be attacked
	 * @param coord1 the first coordinate, on the Y axis (sides)
	 * @param coord2 the second coordinate, on the X axis (top and bottom)
	 * @param attackID the thread id of the attacker
	 */
	public String attack(String username, int coord1, int coord2, int attackID){
        String broadcast = "";
		Grid grid1 = boards.get(usernames.indexOf(username));
		grid1.fire(coord1, coord2);
		String attacker = "";
		
		for(int i = 0; i < userID.size(); i++){
			if(userID.get(i).equals(attackID)){
				attacker = usernames.get(i);
			}
		}
		
		broadcast += ("Shots fired at " +  username + " by " + attacker + "\n");
        if(!grid1.allSunk()){
		    currentTurn++; 
		    broadcast += (usernames.get(currentTurn % usernames.size()) + " it is your turn\n");
        }
        else{
            broadcast += (username + " is defeated!!");
            usernames.remove(usernames.indexOf(username));
            if(usernames.size() == 1){
                broadcast +=("GAME OVER: " + usernames.get(0) + " wins!");
                started = false;
                usernames.clear();
                userID.clear();
                boards.clear();              
            }
        }
        return broadcast;
	}

	/** 
	 * Command of the game, shows current state of the board, depending on name will show 
	 * either your own board with ships, or someone elses without ships
	 *
	 * @param username the users board to be shows
	 * @param asker the id of the asker
	 */
	public void show(String username, int asker, ConnectionAgent ca1){
		//print grid of that user only to player who asks
		if(started){
            System.out.println("id index" + userID.indexOf(asker));
            System.out.println("name index" + usernames.indexOf(username)); 
		if(userID.indexOf(asker) == usernames.indexOf(username)){
				//show own
				System.out.println("Show own\n");
				Grid own = boards.get(usernames.indexOf(username));
                System.out.println(own.gridFormatPrint());
                ca1.sendMessage("showing own");
				ca1.sendMessage(own.gridFormatPrint());
			}
			else{
				//show others
				System.out.println("Show other\n");
				Grid other = boards.get(usernames.indexOf(username));
                System.out.println(other.gridFormatPrint());
                ca1.sendMessage("showing other");
				ca1.sendMessage(other.gridPrintOther());
			}			
		}
		else{
			ca1.sendMessage("/to user/ Game not in progress");
		}
	}

	/** 
	 * Command of the game, removes user from all lists and informs other players
	 *
	 * @param quitter the id of the resigning thread
	 */
	public String quit(int quitter){
		//clean up after client, notify other players, if players remaining > 2, continue		
		//int index = getIndexByName(quitter);	
        
        String broadcast = "";       
        if(userID.size() >0){
        System.out.println("size of userid" + userID.size()); 
	    System.out.println(userID.indexOf(quitter));
        String quit = usernames.get(userID.indexOf(quitter));
        broadcast += (usernames.get(0) + "has surrendered\n");
		boards.remove(userID.indexOf(quitter));
		usernames.remove(userID.indexOf(quitter));	
		userID.remove(userID.indexOf(quitter));
	    //broadcast += ("/broadcast/ !!! " + quit + " has surrendered\n");
		if(usernames.size() == 1){
			broadcast += ("GAME OVER: " + usernames.get(0) + " wins!\n");
            started = false;
            usernames.clear();
            userID.clear();
            boards.clear();
		}
        }
        else{
        //already shut down
        }
        return broadcast;
	}
	
	/** Helper method to create random numbers for coords, used in testing game*/
	/*public static int getRandom(){
		 return (int) (Math.random() * (7));
	}*/

	//testing
	/*public static void main(String[] args){
		Game game = new Game();
		game.message("start 7");
		game.message("/join luffy 0");
		game.message("/play 0");
		game.message("/join nami 1");
		game.message("/play 1");
		/*game.message("/attack nami 0 0 0");
		game.message("/attack nami 0 1 0");*/
	/*	for(int i = 0; i < 20; i++){
			int id = i%2;
			if(id == 0){
				game.message("/attack nami " + getRandom() + " " + getRandom() + " " + id);
			}
			else{
				game.message("/attack luffy " + getRandom() + " " + getRandom() + " " + id);
			}
		}
		
		game.message("/show nami 1");
		game.message("/show nami 0");
		game.message("/quit 1");
	}*/
}
