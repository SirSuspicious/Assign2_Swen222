package main;

import gameObjects.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import GUI.GameFrame;
import board.*;


public class Player {
	private ArrayList<Card> hand;
	private String name;
	private Player nextPlayer;
	private Player prevPlayer;
	private Tile on;
	private Room inRoom = null;
	public final Person playingAs;
	
	private int movement;
	
	private GameFrame frame = null;
	
	private int markerD = 50;
	
	private Cluedo c;
	
	private int status = 0;//0 for still in game, 1 for win, 2 for eliminated.
	
	public Player(Tile start, String name, Person p){
		hand = new ArrayList<Card>();
		playingAs = p;
		on = start;
		this.name = name;
	}
	
	public Player(Tile start, String name, Person p, GameFrame gameFrame, Cluedo cluedo){
		hand = new ArrayList<Card>();
		playingAs = p;
		on = start;
		this.name = name;
		frame = gameFrame;
		c = cluedo;
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
	
	
	/**
	 * this has been adapted for the GUI.
	 * @param p
	 * @param c
	 * @return
	 */
	public boolean refutes(Player p, Suggestion c){
		if(p == this){
			//System.out.println("Suggestion: "+ c.toString() +" was not refuted.");
			frame.addText("Suggestion: "+ c.toString() +" was not refuted.\n");
			return false;
		}
		for(Card card : hand){
			if(c.contains(card)){
				//System.out.println(name+" refutes "+p.getName()+"'s suggestion: "+c.toString());
				frame.addText(name+" refutes "+p.getName()+"'s suggestion: "+c.toString()+"\n");
				return true;
			}
		}
		return nextPlayer.refutes(p, c);
		
	}
	
	
	/**
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(playingAs.color());
		int x = on.getPosition().getX();
		int y = on.getPosition().getY();
		Position pos = frame.findCoordinatePos(x, y);
		
		g.fillOval(pos.getX(), pos.getY(), markerD, markerD);
		g.setColor(Color.black);
		g.drawOval(pos.getX(), pos.getY(), markerD, markerD);
	}
	
	/**
	 * Executes a players turn using the GUI.
	 * @param board
	 * @param solution
	 * @return
	 */
	public int doTurnGUI(final Board board, Solution solution, final ArrayList<Player> players){
	
		int d1 = Cluedo.randomInt(1, 6);
		int d2 = Cluedo.randomInt(1, 6);
		frame.showDice(d1, d2);
		frame.repaint();
		movement = d1+d2;
		
		if(inRoom != null){
			if(inRoom.getName() == RoomType.SWIMMING_POOL){
				int o = JOptionPane.showConfirmDialog(frame, "Would you like to make an accusation?");
				if(o == 0){
					
					makeAccusation(solution);
					return status;
				}
			}else{
				int o = JOptionPane.showConfirmDialog(frame, "Would you like to make a suggestion?");
				if(o == 0){
					
					makeSuggGUI();
					return 0;
				}
			}
		}
	
		MouseListener listener = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				
				move(e.getX(), e.getY(), board, players);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
		
		frame.addCanvasListener(listener);
		
		while(true){
			try{
				wait(10);
			}catch(Exception e){}
			if(movement <= 0){
				break;
			}
		}
		listener = null;
		return 0;
	}
	
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param board
	 */
	private void move(int x, int y, Board board, ArrayList<Player> players){
		Position p = frame.findSquarePos(x, y);
		if(p == null){
			System.out.println("null position");
			return;
		}
		Tile to = board.getTile(p);
		for(Player player : players){
			if(player.on == to){
				return;
			}
		}
		
		int i = board.isViableMove(on, to, movement);
		
		if(inRoom instanceof CornerRoom){
			Room other = board.getRoom(board.inRoom(p));
			if(other instanceof CornerRoom){
				if(((CornerRoom) inRoom).getTo() == other.getName()){
					inRoom = other;
					on = to;
					movement = 0;
					c.redraw();
					return;
					
				}
			}
		}
		
		if(inRoom  != null){
			if(board.getRoom(board.inRoom(p)) == null){
				
				ArrayList<Tile> exits = inRoom.getAdjacentTo();
				ArrayList<Integer> costs = new ArrayList<Integer>();

				for(Tile t : exits){
					
					costs.add(board.isViableMove(t, to, movement));
				}
				int minValue = 100;

				for(int j = 0; j < exits.size(); j++){
					if(costs.get(j) < minValue && costs.get(j)>= 0){
						minValue = costs.get(j);
					}
				}
				 
				 if(movement - minValue - 1 >=0){
					 movement = movement - minValue-1;
					 on = to;
					 c.redraw();
					
					 inRoom = null;
					 
				 }
				 return;
			}
		}
		
		
		if(i < 0){
			 if(inRoom == board.getRoom(board.inRoom(p))){
				 return;
			 }

			 Room r = board.getRoom(board.inRoom(p));
			 if(r != null){
				 ArrayList<Tile> enterFrom = r.getAdjacentTo();
				 ArrayList<Integer> costs = new ArrayList<Integer>();

				 for(Tile t : enterFrom){
					 costs.add(board.isViableMove(on, t, movement));
				 }

				
				 int minValue = 100;
				 for(int j = 0; j < enterFrom.size(); j++){
					 if(costs.get(j) < minValue && costs.get(j)>= 0){
						 minValue = costs.get(j);
				
					 }
				 }
				 if(!(movement - minValue >0)){
					 return;
				 }
				 movement = 0;
				 inRoom = board.getRoom(board.inRoom(p));
				 on = to;
				 c.redraw();
				 return;
			 }
			

		}else{
			on = to;
			movement -= i;
			c.redraw();
		}
	
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
	 * Gets the player to construct there suggestion using a dialog box with radio buttons 
	 * for the possible selections.
	 */
	private void makeSuggGUI(){
		
		ButtonGroup personGroup = new ButtonGroup();
		final HashMap<Person, JRadioButton> personButtons = personButtons();
		
		for(Person p : personButtons.keySet()){
			personGroup.add(personButtons.get(p));
		}
		
		
		ButtonGroup weapGroup = new ButtonGroup();
		final HashMap<Weapon, JRadioButton> weapButtons = weaponButtons();
		
		for(Weapon p : weapButtons.keySet()){
			weapGroup.add(weapButtons.get(p));
		}
		
	
		final JDialog d = new JDialog();
		d.setModal(true);

		d.setLayout(new BorderLayout());

		JLabel label = new JLabel(name+" make a selection:");
		d.add(label, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		for(Person p: Person.values()){
			JRadioButton b = personButtons.get(p);
			b.setAlignmentX(Component.LEFT_ALIGNMENT);
			leftPanel.add(b);
		}
		for(Weapon p: Weapon.values()){
			JRadioButton b = weapButtons.get(p);
			b.setAlignmentX(Component.LEFT_ALIGNMENT);
			rightPanel.add(b);
		}
		
		
		
		d.add(leftPanel);
		d.add(rightPanel, BorderLayout.EAST);
		
		
		
		JButton button = new JButton("Ok");
		
		
		final ButtonGroup PG = personGroup;
		final ButtonGroup WG = weapGroup;
		final RoomType r = inRoom.getName();
		final Player pl = this;
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Person p :personButtons.keySet()){
					if(personButtons.get(p).getModel() == PG.getSelection()){
						for(Weapon w : weapButtons.keySet()){
							if(weapButtons.get(w).getModel() == WG.getSelection()){
								Suggestion s = new Suggestion(r, p, w);
								d.setVisible(false);
								
								nextPlayer.refutes(pl, s);
								
							}
						}
						
					
					
					}
				}
			}
		});
		
		d.add(button, BorderLayout.SOUTH);
		d.pack();
		d.setVisible(true);
		
	}
	

	
	/**
	 * gets the player to construct their accusation using a dialog box and 
	 * radio buttons for the possible selections, then checks the accusation against the
	 * solution and sets the status field according to whether or not the accusation was correct.
	 * @param solution
	 */
	private void makeAccusation(final Solution solution){
		
		//Characters
		ButtonGroup personGroup = new ButtonGroup();
		final HashMap<Person, JRadioButton> personButtons = personButtons();
		
		for(Person p : personButtons.keySet()){
			personGroup.add(personButtons.get(p));
		}
		
		
		//Weapons
		ButtonGroup weapGroup = new ButtonGroup();
		final HashMap<Weapon, JRadioButton> weapButtons = weaponButtons();
		
		for(Weapon p : weapButtons.keySet()){
			weapGroup.add(weapButtons.get(p));
		}
		
		//Rooms
		ButtonGroup roomGroup = new ButtonGroup();
		final HashMap<RoomType, JRadioButton> roomButtons = roomButtons();
		
		for(RoomType p : roomButtons.keySet()){
			roomGroup.add(roomButtons.get(p));
		}
		
	
		final JDialog d = new JDialog();
		d.setModal(true);

		d.setLayout(new BorderLayout());

		JLabel label = new JLabel(name+" make a selection:");
		d.add(label, BorderLayout.NORTH);
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		
		for(Person p: Person.values()){
			JRadioButton b = personButtons.get(p);
			b.setAlignmentX(Component.LEFT_ALIGNMENT);
			middlePanel.add(b);
		}
		for(Weapon p: Weapon.values()){
			JRadioButton b = weapButtons.get(p);
			b.setAlignmentX(Component.LEFT_ALIGNMENT);
			rightPanel.add(b);
		}
		for(RoomType p: RoomType.values()){
			JRadioButton b = roomButtons.get(p);
			b.setAlignmentX(Component.LEFT_ALIGNMENT);
			leftPanel.add(b);
		}
		
		d.add(middlePanel);
		d.add(rightPanel, BorderLayout.EAST);
		d.add(leftPanel, BorderLayout.WEST);
		
		JButton button = new JButton("Ok");
		
		
		final ButtonGroup PG = personGroup;
		final ButtonGroup WG = weapGroup;
		final ButtonGroup RG = roomGroup;
		final Player pl = this;
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Person p :personButtons.keySet()){
					if(personButtons.get(p).getModel() == PG.getSelection()){
						
						for(Weapon w : weapButtons.keySet()){
							if(weapButtons.get(w).getModel() == WG.getSelection()){
								
								for(RoomType r : roomButtons.keySet()){
									if(roomButtons.get(r).getModel() == RG.getSelection()){
										
										Suggestion s = new Suggestion(r, p, w);
										d.setVisible(false);
										if(solution.is(s)){
											status = 1;
										}else{
											status = 2;
										}
									}
								}	
							}
						}
					}
				}
			}
		});
		
		d.add(button, BorderLayout.SOUTH);
		d.pack();
		d.setVisible(true);
	}
	final private HashMap<Person, JRadioButton> personButtons(){
	HashMap<Person, JRadioButton> rButtons = new HashMap<Person, JRadioButton>();
		
		for(Person p : Person.values()){
			JRadioButton b = new JRadioButton(p.toString());

			rButtons.put(p, b);
		}
		
		return rButtons;
	}
	
	
	
	
	private HashMap<Weapon, JRadioButton> weaponButtons(){
		HashMap<Weapon, JRadioButton> rButtons = new HashMap<Weapon, JRadioButton>();
			
			for(Weapon p : Weapon.values()){
				JRadioButton b = new JRadioButton(p.toString());

				rButtons.put(p, b);
			}
			
			return rButtons;
		}
	
	private HashMap<RoomType, JRadioButton> roomButtons(){
		HashMap<RoomType, JRadioButton> rButtons = new HashMap<RoomType, JRadioButton>();
			
			for(RoomType p : RoomType.values()){
				JRadioButton b = new JRadioButton(p.toString());

				rButtons.put(p, b);
			}
			
			return rButtons;
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
