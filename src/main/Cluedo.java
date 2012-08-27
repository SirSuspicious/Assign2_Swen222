package main;



import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import GUI.GameFrame;
import board.*;
import gameObjects.*;


public class Cluedo {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	
	//collections of Cards
	private  ArrayList<WeaponCard> weapons = new ArrayList<WeaponCard>();
	private  ArrayList<CharacterCard> characters = new ArrayList<CharacterCard>();
	private  ArrayList<RoomCard> rooms = new ArrayList<RoomCard>();

	private  ArrayList<Card> allCards = new ArrayList<Card>();
	

	private  Board board;
	
	private  Solution solution;
	
	
	private Scanner input;
	
	
	public static void main(String[] args){
		
		//Cluedo m = new Cluedo(new Scanner(System.in));
		
		//m.gameLoop();
		GameFrame g = new GameFrame();

		
		
		
		//System.exit(0);
		
		
	}

	/**
	 * 
	 * @param in The Scanner to be used for input
	 */
	public Cluedo(Scanner in){
		players = new ArrayList<Player>();
		weapons = new ArrayList<WeaponCard>();
		characters = new ArrayList<CharacterCard>();
		rooms = new ArrayList<RoomCard>();

		allCards = new ArrayList<Card>();
		
		input = in;
		board = new Board("board.txt");

		
		System.out.println("To exit at any time, type \"exit\".");
		System.out.println();
		
		startUp(input);

		dealCards();
		
	}
	
	/**
	 * cycles through each player tells them to take their turn.
	 */
	public void gameLoop(){
	
		int curPlayer = 0;
		while(true){
			
			if(players.size() == 1){
				System.out.println(players.get(0).getName()+ " wins!");
				break;
			}
			if(curPlayer >= players.size()){
				curPlayer = 0;
			}
			int i = players.get(curPlayer).doTurn(input, board, solution);
			if(i == 1){
				System.out.println(players.get(curPlayer).getName()+" wins!");
				break;
				
			}else if(i == 2){
				System.out.println("Accusation incorrect, "+players.get(curPlayer).getName()+" eliminated!");
				Player elim = players.get(curPlayer);
				Player prev = elim.getPrevPlayer();
				Player next = elim.getNextPlayer();
				prev.setNextPlayer(next);
				next.setPrevPlayer(prev);
				players.remove(elim);
				
				int c = 0;
				for(Card card : elim.getHand()){
					if(c >= players.size()){
						c = 0;
					}
					players.get(c++).addCard(card);
					
				}
				
			}else{
				curPlayer++;
			}
		}
		
	}
	
	/**
	 * for testing.
	 * @return
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Solution getSolution() {
		return solution;
	}

	/**
	 * Selects the cards that will be the Solution
	 * and then distributes the remaining cards among the players.
	 */
	private void dealCards(){
		
		//populate weapons, characters, and rooms.
		for(Weapon w : Weapon.values()){
			weapons.add(new WeaponCard(w));
		}
		for(Person c : Person.values()){
			characters.add(new CharacterCard(c));
		}
		for(RoomType r : RoomType.values()){
			rooms.add(new RoomCard(r));
		}
		
		//generate the "solution".
		WeaponCard w = weapons.get(randomInt(0, weapons.size()-1));
		CharacterCard c = characters.get(randomInt(0, characters.size()-1));
		RoomCard r = rooms.get(randomInt(0, rooms.size()-1));
		
		rooms.remove(r);
		characters.remove(c);
		weapons.remove(w);
		
		solution = new Solution(r, c, w);
		
		Collections.shuffle(rooms);
		Collections.shuffle(characters);
		Collections.shuffle(weapons);
		allCards.addAll(rooms);
		allCards.addAll(characters);
		allCards.addAll(weapons);
		
		//deal the remaining cards.
		int playerCount = 0;
		for(Card card : allCards){
			if(playerCount >= players.size()){
				playerCount = 0;
			}
			players.get(playerCount).addCard(card);
			playerCount++;
		}
		
	}
	
	/**
	 * Establishes how many players are going to play, and asks who will play 
	 * as which character.
	 * The Player objects constructed here and linked together.
	 */
	private void startUp(Scanner in){
		int numPlayers = 0;
		
		System.out.print("Enter number of players (3 - 6):");
		numPlayers = inputInt(in, 3, 6);
			
		System.out.println();
		
		HashMap<Integer, Person> characters = new HashMap<Integer, Person>();
		int c = 1;
		for(Person p : Person.values()){
			characters.put(c, p);
			c++;
		}
		
		for(int i = 1; i <= numPlayers; i++){
			System.out.println("Player "+i+":");
			System.out.println("Enter the number of the character you wish to play as:");
			int per ;
			while(true){
				
				for(int j = 1; j <= 6; j++){
					if(characters.containsKey(j)){
						System.out.printf("%d: %s\n",j , characters.get(j).toString());
					}
				}
				
					per = inputInt(in, 1, 6);
					if(!characters.containsKey(per)){
						System.out.println("Character has already been taken.");
						continue;
					}else
						break;
					
				
				
			}
			Person p = characters.get(per);
			players.add(new Player(board.getStartTile(p), "Player "+i));
			characters.remove(per);
			System.out.println();
			
		}
		
		for(int i = 0; i < numPlayers; i++){
			Player p = players.get(i);
			if(i == 0){
				p.setNextPlayer(players.get(i+1));
				p.setPrevPlayer(players.get(numPlayers-1));
			}else if(i == numPlayers-1){
				p.setNextPlayer(players.get(0));
				p.setPrevPlayer(players.get(i-1));
			}else{
				p.setNextPlayer(players.get(i+1));
				p.setPrevPlayer(players.get(i-1));
			}
		}
		
	}
	
	
	/**
	 * Helper method.
	 * Generates a random integer between "start" and "end" (inclusive)
	 * @param start
	 * @param end
	 * @return
	 */
	public static int randomInt(int start, int end){
		return ((int)(Math.random()*((end+1)-start)))+start;
	}
	
	
	/**
	 * Helper method.
	 * Scans for input that is an integer between "start" and "end" (inclusive),
	 * if an integer in the required range is not scanned, an InvalidInputException is thrown.
	 * @param start 
	 * @param end
	 * @return
	 * @throws InvalidInputException
	 */
	public static int inputInt(Scanner sc, int start, int end){
		while(true){
			if(sc.hasNextInt()){
				int t = sc.nextInt();
				if(t >= start && t <= end){
					return t;
				}
				System.out.println("Invalid input. Try again.");
			}else{
				if(sc.next().equalsIgnoreCase("exit")){
					System.exit(0);
				}
				System.out.println("Invalid input. Try again.");
			}
		}
	}

	public Board getBoard() {
		return board;
	}


	
	
}
