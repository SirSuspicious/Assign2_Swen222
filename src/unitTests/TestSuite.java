package unitTests;

import static org.junit.Assert.*;
import gameObjects.*;

import java.util.ArrayList;
import java.util.Scanner;
import main.*;
import org.junit.Test;

import board.*;


/**
 * I found trying to write test cases somewhat frustrating.
 * Unfortunately, due to how I wrote most of the program, it turned out to be
 * very impractical to test. I think this is largely due to many of the components of the program,
 * particularly those in the Player and Cluedo class's, being far to dependent on other
 * parts of the program, which is clearly a fault in the design.
 * */
public class TestSuite {
	
	
	/**
	 * Tests the randomInt method in Cluedo class,
	 * this was more for myself, to ensure it was doing what I wanted,
	 * but I'll leave it here anyway.
	 */
	@Test
	public void testRandomInt(){
		
		int from = 21;
		int to = 50;
		for(int i = 0; i < 1000; i ++){
			int random = Cluedo.randomInt(from, to);
			assertTrue(random >= from);
			assertTrue(random <= to);
		}
		
		from = -50;
		to = 50;
		for(int i = 0; i < 1000; i ++){
			int random = Cluedo.randomInt(from, to);
			assertTrue(random >= from);
			assertTrue(random <= to);
		}
	}
	
	/**
	 * Tests that none of the players have any of the solution cards
	 * in their hand.
	 * and that only one of each card exists.
	 */
	@Test
	public void testCardsAndSolution(){
		for(int i = 0; i < 100; i++){
			Scanner scan = new Scanner("4 1 2 3 4");

			Cluedo m = new Cluedo(scan);
			ArrayList<Player> players = m.getPlayers();
			Solution s = m.getSolution();
			Suggestion c = new Suggestion((RoomType)s.getRoom().getName(), (Person)s.getCharacter().getName(), (Weapon)s.getWeapon().getName());
		
	
			for(Player p : players){
				assertFalse(p.containsSuggestion(c));
				for(Player p2 : players){
					if(!p.equals(p2)){
						assertFalse(p.getHand().equals(p2.getHand()));
					}
				}
			}
		}
		for(int i = 0; i < 100; i++){
			Scanner scan = new Scanner("6 1 2 3 4 5 6");

			Cluedo m = new Cluedo(scan);
			ArrayList<Player> players = m.getPlayers();
			Solution s = m.getSolution();
			Suggestion c = new Suggestion((RoomType)s.getRoom().getName(), (Person)s.getCharacter().getName(), (Weapon)s.getWeapon().getName());
		

			for(Player p : players){
				assertFalse(p.containsSuggestion(c));
				for(Player p2 : players){
					if(!p.equals(p2)){
						assertFalse(p.getHand().equals(p2.getHand()));
					}
				}
			}
		}

	}
	
	/**
	 * tests the exiting of rooms.
	 * */
	@Test
	public void testRoomToTile(){
		Scanner scan = new Scanner("3 1 2 3 ");

		Cluedo m = new Cluedo(scan);
		ArrayList<Player> players = m.getPlayers();
		Room r = m.getBoard().getRoom(RoomType.SWIMMING_POOL);
		players.get(1).setInRoom(r);
		players.get(1).doTurn(new Scanner("2 2 1 2 2 1 1 1 1 1"), m.getBoard(), m.getSolution());
		assertTrue(players.get(1).getInRoom() == null);
		
		
		players.get(1).setInRoom(m.getBoard().getRoom(RoomType.PATIO));
		players.get(1).doTurn(new Scanner("2 1 2 2 1 1 1 1 1 1 1 1 1 1 1 "), m.getBoard(), m.getSolution());
		assertTrue(players.get(1).getInRoom() == null);
		
		
		players.get(1).setInRoom(m.getBoard().getRoom(RoomType.GUEST_HOUSE));
		players.get(1).doTurn(new Scanner("2 2 1 2 2 1 1 1 1 1 1 1 "), m.getBoard(), m.getSolution());
		assertTrue(players.get(1).getInRoom() == null);
	}
	
	
	/**
	 * tests the exiting of rooms.
	 * */
	@Test
	public void testAccusationFail(){
		Scanner scan = new Scanner("3 1 2 3 ");

		Cluedo m = new Cluedo(scan);
		ArrayList<Player> players = m.getPlayers();
		Room r = m.getBoard().getRoom(RoomType.SWIMMING_POOL);
		players.get(0).setInRoom(r);
		Player p = players.get(0);
		int i = players.get(0).doTurn(new Scanner("2 1 1 1 1 1 1 1 1 1"), m.getBoard(), m.getSolution());
		
		if(i == 1){
			i = players.get(0).doTurn(new Scanner("2 1 2 2 2 2 1 1 1 1"), m.getBoard(), m.getSolution());
		}
		assertTrue(i == 2);//indicates the player is eliminated.
		
	}

}
