package server;

import java.util.ArrayList;

public class Game {

	private ArrayList<String> usernames;
	private ArrayList<Grid> boards;
	private final int DEFAULT_SIZE = 10;
	private int size;
	private int currentTurn;
	boolean started = false;

	public Game(){
		usernames = new ArrayList<String>();
		boards = new ArrayList<Grid>();
	}

	//messages will be what the user inputs, as well as which user sent it
	public void message(String message){
		String[] parsed = message.split(" ");

		System.out.println(message + "\n");

		switch(parsed[0].toLowerCase()){

		//default when server starts, will send "start [int size of board]"
		case("start"):
			if(parsed.length==2){
				if(isInteger(parsed[1])){
					size = Integer.parseInt(parsed[1]);
				}
				else{
					System.out.println("Invalid response, must enter valid size of board");
				}
			}
			else{
				size = DEFAULT_SIZE;
			}
		break;

		// ex "/join username"
		case ("/join"):
			//if(usernames.contains(parsed[1].toLowerCase())){
				join(parsed[1]);
			//}
		break;

		// ex "/play username"
		case("/play"):
			if(!started){
				play(parsed[1]);
			}
			else{
				System.out.println("/to user/ Game already in progress");
			}
		break;
		// ex "/attack user coord coord username"
		case("/attack"):
			//don't move to next turn until valid command
			if(started){
				if(parsed.length == 5 && isUser(parsed[1]) && isUser(parsed[4])){
					if(isInteger(parsed[2]) && isInteger(parsed[3])){
						if(Integer.parseInt(parsed[2]) <= size && Integer.parseInt(parsed[3]) <= size){
							if(!parsed[1].equals(parsed[4])){
								if(currentTurn % usernames.size() == usernames.indexOf(parsed[4])){
								attack(parsed[1].toLowerCase(), Integer.parseInt(parsed[2]), Integer.parseInt(parsed[3]), parsed[4]);
								}
								else{
									System.out.println("/to user/ Move Failed, player turn: " + usernames.get(currentTurn%usernames.size()) + "\n");
								}
							}
							else{
								System.out.println("/to user/ Cannot attack yourself\n");
							}
						}
						else{
							System.out.println("/to user/ Invalid command: coordinates out of bound\n");
						}
					}
					else{
						System.out.println("/to user/ Invalid command: coordinates not numbers\n");
					}
				}
				else{
					System.out.println("/to user/ Invalid command: " + parsed.toString() +"\n");
				}
			}
			else{
				System.out.println("/to user/ Game not in progress\n");
			}
		break;

		// ex "/show user username
		case("/show"):
			if(started){
				if(isUser(parsed[1]) && isUser(parsed[1])){
					show(parsed[1], parsed[2]);
				}
				else{
					System.out.println("/to user/ Invalid command: " + parsed.toString());
				}
			}
			else{
				System.out.println("/to user/ Game not in progress");
			}
		break;

		// ex "/quit user"
		case("/quit"):
			quit(parsed[1]);
			break;
		}

	}

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

	public boolean isUser(String name){
		boolean answer = false;
		for(int i = 0; i < usernames.size(); i++){
			if(name.equals(usernames.get(i))){
				answer = true;
			}
		}
		return answer;
	}

	//dumb me, use arraylist.indexOf instead
	public int getIndexByName(String name){
		int answer = 0;
		for(int i = 0; i<usernames.size(); i++){
			if(name.equals(usernames.get(i))){
				answer = i;
			}
		}
		return answer;		
	}

	public void join(String username){
		//done automatically, username added to list, dupes not allowed
		boolean valid = true;
		String user = username.toLowerCase();
		for(int i = 0; i<usernames.size(); i++){
			if(user.equals(usernames.get(i))){
				valid = false;
			}
		}

		if(valid){
			usernames.add(user);
			//broadcast user has joined
			System.out.println("/broadcast/ !!!" + username + " has joined\n");
		}
		else{
			System.out.println("/to user/Username already in use, please choose another\n");
		}

	}

	public void play(String user){
		//if 2 or more players have joined, start game, after game started ignore this		
		if(usernames.size()>=2){
			for(int i = 0; i<usernames.size(); i++){
				//System.out.println(i);
				Grid g = new Grid(size);
				g.makeGrid();
				boards.add(g);
			}
			started = true;
			System.out.println("/broadcast/ The game begins\n");
			currentTurn = 0;
			System.out.println(usernames.get(currentTurn % usernames.size()) + " it is your turn\n");
		}	
		else{			
			System.out.println("/to user/ Not enough players to play the game\n");
		}
	}

	public void attack(String username, int coord1, int coord2, String attacker){
		//turn coord to hit or miss
		Grid grid1 = boards.get(usernames.indexOf(username));
		grid1.fire(coord1, coord2);
		System.out.println("/broadcast/ Shots fired at " +  username + " by " + attacker + "\n");
		currentTurn++;
		System.out.println(usernames.get(currentTurn % usernames.size()) + " it is your turn\n");
		//fire(coord1, coord2);
	}

	public void show(String username, String asker){
		//print grid of that user only to player who asks
		if(started){
			if(username.equals(asker)){
				//show own
				System.out.println("Show own\n");
				Grid own = boards.get(usernames.indexOf(username));
				own.gridFormatPrint();
			}
			else{
				//show others
				System.out.println("Show other\n");
			}			
		}
		else{
			System.out.println("/to user/ Game not in progress");
		}
	}

	public void quit(String quitter){
		//clean up after client, notify other players, if players remaining > 2, continue		
		//int index = getIndexByName(quitter);		
		boards.remove(usernames.indexOf(quitter));
		usernames.remove(quitter);	
		System.out.println("/broadcast/ !!! " + quitter + " has surrendered\n");
		if(usernames.size() == 1){
			System.out.println("GAME OVER: " + usernames.get(0) + " wins!\n");
		}
	}

	//testing
	public static void main(String[] args){
		Game game = new Game();
		game.message("start 7");
		game.message("/join luffy");
		game.message("/play luffy");
		game.message("/join nami");
		game.message("/play nami");
		game.message("/attack nami 0 0 luffy");
		game.message("/attack nami 0 1 luffy");
		game.message("/show nami nami");
		game.message("/quit nami");
	}
}
