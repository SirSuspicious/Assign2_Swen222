package main;

import gameObjects.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import board.RoomTile;
import board.*;


public class Player {
	private ArrayList<Card> hand;
	private String name;
	private Player nextPlayer;
	private Player prevPlayer;
	private Tile on;
	private Room inRoom = null;
	private Person playingAs;
	
	
	
	public Player(Tile start, String name, Person p){
		hand = new ArrayList<Card>();
		playingAs = p;
		on = start;
		this.name = name;
	}
	
	/**
	 * Used only in the test suite.
	 * @param c
	 * @return
	 */
	public boolean containsSuggestion(Suggestion c){
		for(Card card : hand){
			if(c.contains(card)){
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean refutes(Player p, Suggestion c){
		if(p == this){
			System.out.println("Suggestion: "+ c.toString() +" was not refuted.");
			return false;
		}
		for(Card card : hand){
			if(c.contains(card)){
				System.out.println(name+" refutes "+p.getName()+"'s suggestion: "+c.toString());
				return true;
			}
		}
		return nextPlayer.refutes(p, c);
		
	}
	
	/**
	 * performs the turn for this player.
	 * @param sc - Scanner to be used for receiving input
	 * @param board - the game board
	 * @param solution - the game Solution
	 * @return
	 */
	public int doTurn(Scanner sc, Board board, Solution solution){
		System.out.println("=================================");
		System.out.println(name + "'s turn.");
	
		if(inRoom == null){
			System.out.println("Your Position: "+ on.toString());
		}else{
			System.out.println("You are in the "+ inRoom.getName());
		}
	
		


		if(inRoom != null){
			 if(askYesNo("Make suggestion?", sc)){
				makeSuggestion(sc);
				return 0;
				
			}else if(inRoom instanceof CornerRoom && askYesNo("Move to opposite corener room?", sc)){
				inRoom = board.getRoom(((CornerRoom)inRoom).getTo());
				System.out.println("You may make a suggestion.");
				makeSuggestion(sc);
				return 0;
				
			}else if(inRoom.getName() == RoomType.SWIMMING_POOL && askYesNo("Make accusation?", sc)){
				
				RoomType r = selectRoom(sc);
				Person p = selectPerson(sc);
				Weapon w = selectWeapon(sc);
				Suggestion s = new Suggestion(r, p, w);
				if(solution.is(s)){
					return 1;//wins the game.
				}else{
					return 2;//eliminated from the game.
				}


			}else{
			
				System.out.println("Select tile to exit to (Enter an integer):");
				on = selectTile(inRoom.getAdjacentTo(), sc);
				inRoom = null;
				return moveOptions(sc, board, solution, 1);
				
			}

		}

		return moveOptions(sc, board, solution, 0);

	}
	
	/**
	 * constructs a suggestion and then checks to see if the suggestion is refuted
	 * by the other players.
	 * @param sc
	 */
	private void makeSuggestion(Scanner sc){
		if(askYesNo("Print cards in your hand? (Enter an integer)", sc)){
			System.out.println(handToString());
		}
		
		RoomType r = inRoom.getName();
		Person p = selectPerson(sc);
		Weapon w = selectWeapon(sc);
		
		Suggestion s = new Suggestion(r, p, w);
		nextPlayer.refutes(this, s);
	}
	
	/**
	 * Provides the movement options for when a player is not in a room.
	 * @param sc
	 * @param board
	 * @param solution
	 * @return
	 */
	private int moveOptions(Scanner sc, Board board, Solution solution, int exited){
		int dRoll = Cluedo.randomInt(1, 6)-exited;
		System.out.println("Dice Roll: "+dRoll);
		
		if(askYesNo("Print room coordinates?", sc)){
			System.out.println(board.roomCoords());
		}

		for(int i = dRoll; i > 0; i--){
			if(on instanceof RoomTile && askYesNo("Enter the "+((RoomTile)on).getTo().getName()+"?", sc)){
				RoomTile r = (RoomTile)on;
				inRoom = r.getTo();
				if(inRoom.getName() == RoomType.SWIMMING_POOL && askYesNo("Make accusation?", sc)){
					RoomType rt = selectRoom(sc);
					Person p = selectPerson(sc);
					Weapon w = selectWeapon(sc);
					Suggestion s = new Suggestion(rt, p, w);
					if(solution.is(s)){
						return 1;//wins the game.
					}else{
						return 2;//eliminated from the game.
					}
				}else{
					System.out.println("You may make a suggestion.");
					makeSuggestion(sc);
					return 0;
				}
				
				
			}else{
				System.out.println(i+"Moves remaining.");
				ArrayList<Tile> adj = board.getAdjTiles(on);
				System.out.println("Select a tile to move to (Enter an Integer)");
				on = selectTile(adj, sc);
			}
		}
		return 0;
	}
	
	
	/**
	 * Helper method.
	 * asks player to select a tile to move to.
	 * @param tiles
	 * @param sc
	 * @return
	 */
	private Tile selectTile(ArrayList<Tile> tiles, Scanner sc){
		HashMap<Integer, Tile> adj = new HashMap<Integer, Tile>();
		int i = 1;
		for(Tile r : tiles){
			adj.put(i++, r);
		}
		for(int j = 1; j < i; j++){
			System.out.println(j+": "+adj.get(j).toString());
		}
	
		return adj.get(Cluedo.inputInt(sc, 1, i-1));
	}

	/**
	 * Helper method;
	 * asks player to select a weapon. 
	 * @param sc
	 * @return
	 */
	private Weapon selectWeapon(Scanner sc){
		
		HashMap<Integer, Weapon> weaps = new HashMap<Integer, Weapon>();
		int i = 1;
		for(Weapon w : Weapon.values()){
			weaps.put(i, w);
			i++;
		}
		System.out.println("Select Weapon (Enter an integer):");
		for(int j = 1; j < i; j++){
			System.out.println(j+": "+weaps.get(j));
		}
		return weaps.get(Cluedo.inputInt(sc, 1, i-1));
		
	}
	
	/**
	 * Helper method;
	 * asks player to select a Person. 
	 * @param sc
	 * @return
	 */
	private Person selectPerson(Scanner sc){
		HashMap<Integer, Person> chars = new HashMap<Integer, Person>();
		int i = 1;
		for(Person w : Person.values()){
			chars.put(i, w);
			i++;
		}
		System.out.println("Select Character (Enter an integer):");
		for(int j = 1; j < i; j++){
			System.out.println(j+": "+chars.get(j));
		}
		return chars.get(Cluedo.inputInt(sc, 1, i-1));
		
	}
	
	/**
	 * Helper method;
	 * asks player to select a room.
	 * @param sc
	 * @return
	 */
	private RoomType selectRoom(Scanner sc){
		HashMap<Integer, RoomType> rs = new HashMap<Integer, RoomType>();
		int i = 1;
		for(RoomType w : RoomType.values()){
			rs.put(i, w);
			i++;
		}
		System.out.println("Select Room (Enter an integer):");
		for(int j = 1; j < i; j++){
			System.out.println(j+": "+rs.get(j));
		}
		return rs.get(Cluedo.inputInt(sc, 1, i-1));
	}
	
	/**
	 * 
	 * @param question Asks user this question
	 * @param sc 
	 * @return returns true/false for a yes/no question
	 */
	public boolean askYesNo(String question, Scanner sc){
		System.out.println(question+" (enter an Integer)");
		System.out.println("1: Yes");
		System.out.println("2: No");

		int in = Cluedo.inputInt(sc, 1, 2);
		if(in == 1){
			return true;
		}
		return false;
	}
	
	/**
	 * adds a crad to the players hand.
	 * @param card
	 */
	public void addCard(Card card){
		hand.add(card);
	}
	
	
	public boolean inRoom(){
		if(inRoom == null){
			return false;
		}
		return true;
		
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public Player getPrevPlayer() {
		return prevPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public void setPrevPlayer(Player prevPlayer) {
		this.prevPlayer = prevPlayer;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * returns a string representation of the players hand
	 * @return
	 */
	public String handToString(){
		String s = "";
		for(Card c : hand){
			s = s + c.toString()+"\n";
		}
		return s;
				
	}

	
	
	
	/**
	 * The following methods are used in testing
	 * */
	public Tile getOn() {
		return on;
	}

	public Room getInRoom() {
		return inRoom;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void setOn(Tile on) {
		this.on = on;
	}

	public void setInRoom(Room inRoom) {
		this.inRoom = inRoom;
	}

	
	
	
}
