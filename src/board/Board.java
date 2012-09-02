package board;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import gameObjects.*;

public class Board {
	
	private Tile[][] tiles;
	private HashMap<Person, Tile> startPos;
	private HashMap<RoomType, Room> rooms;
	
	
	private final int numR = 29;//number of rows in the board grid.
	private final int numC = 24;//number of columns in the board grid.
	
	public Board(String fname){
		

		tiles = new Tile[numR][numC];
		startPos = new HashMap<Person, Tile>();
		rooms = new HashMap<RoomType, Room>();
		
		
		
		readBoard(fname);
	}
	
	

	/**
	 * gets the Tile at which that particular character starts at.
	 * @param person
	 * @return
	 */
	public Tile getStartTile(Person person){
		return startPos.get(person);
	}
	
	/**
	 * checks if the specified position is within a room, and returns the room it is in, otherwise returns null.
	 * @param pos
	 * @return
	 */
	public RoomType inRoom(Position pos){
		Tile tile = tiles[pos.getY()][pos.getX()];
		if(tile instanceof inRoomTile)
			return ((inRoomTile) tile).room();
		return null;
	}
	
	public int isViableMove(Tile on, Tile to, int length){
		
		/*
		 * fringe tile search nodes
		 * heuristic;
		 * 
		 */
		
		ArrayList<Position> visited =  new ArrayList<Position>();
		
		PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>();
		
		fringe.offer(new SearchNode(calcHeur(on.pos, to.pos), 0, on.pos));
		
		while(!fringe.isEmpty()){
			SearchNode next = fringe.poll();
			if(next.pos.equals(to.pos)){
				if(next.toHere <= length){
					
					return next.toHere;
				}
				return -1;
			}
			if(!visited.contains(next.pos)){
				visited.add(next.pos);
				
				for(Tile t : getAdjTiles(tiles[next.pos.getY()][next.pos.getX()])){
					
					if( t != null && !visited.contains(t.pos)){
						fringe.offer(new SearchNode(calcHeur(t.pos, to.pos), next.toHere+1, t.pos));
					}
				}
				
			}
		}
		
		return -1;
	}
	
	private int calcHeur(Position p1, Position p2){
		return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
	}
	
	public Tile getTile(Position p){
		int y = p.getY();
		int x = p.getX();
		return tiles[y][x];
		
	}
	
	/**
	 * Returns the tiles that can be moved to from the tile parameter.
	 * @param t Tile the that is being moved from.
	 * @return
	 */
	public ArrayList<Tile> getAdjTiles(Tile t){
		ArrayList<Tile> listTiles = new ArrayList<Tile> ();
		
		int row = t.getPosition().getY();
		int col = t.getPosition().getX();
		
		
		if(row > 0 && !(tiles[row-1][col] instanceof inRoomTile) && tiles[row-1][col] != null){
			listTiles.add(tiles[row-1][col]);
		}
		if(row < numR-1 && !(tiles[row+1][col] instanceof inRoomTile)&& tiles[row+1][col] != null){
			listTiles.add(tiles[row+1][col]);
		}
		
		if(col > 0 && !(tiles[row][col-1] instanceof inRoomTile)&& tiles[row][col-1] != null){
			listTiles.add(tiles[row][col-1]);
		}
		if(col < numC-1 && !(tiles[row][col+1] instanceof inRoomTile)&& tiles[row][col+1] != null){
			listTiles.add(tiles[row][col+1]);
		}
		
		
		return listTiles;
	}
	
	
	/**
	 * Builds a string containing the coordinates of each room entrance.
	 * @return
	 */
	public String roomCoords(){
		String s = "";
		
		for(RoomType r : rooms.keySet()){
			s = s+r.toString()+" entrances: ";
			s = s+rooms.get(r).adjToString()+"\n";
			s += "\n";
			
		}
		
		return s;
	}
	
	public Room getRoom(RoomType r){
		return rooms.get(r);
	}
	
	/**
	 * Builds the board from a text file.
	 * A character name indicates the corresponds characters start position.
	 * A room name indicates a tile from which the particular room can be entered from.
	 * A 0 indicates an area that players cannot move to.
	 * A 1 indicates a regular movement tile.
	 * @param fname file name of the text file representing the board.
	 */
	private void readBoard(String fname){
		try{
			Scanner lines = new Scanner(new File(fname));
			lines.nextLine();
			
			for(int row = 0; row < numR; row++){
				Scanner scan = new Scanner(lines.nextLine());
				
				for(int col = 0; col < numC; col++){
					
					if(scan.hasNextInt()){	
						if(scan.nextInt() == 1){
							tiles[row][col] = new Tile(new Position(col, row));
						}else{
							
							tiles[row][col] = null;
						}
					}else{
						String next = scan.next();
						
						if(next.charAt(0) == '*'){//*indicates the following string is a character name.
							next = next.substring(1);
							Tile tile = new Tile(new Position(col, row));
							//record the tile the character starts on.
							startPos.put(Person.valueOf(next), tile);
							tiles[row][col] = tile;
							
						}else if(next.charAt(0) == '#'){
							next = next.substring(1);
							
							inRoomTile tile = new inRoomTile(new Position(col, row), RoomType.valueOf(next));
							
							tiles[row][col] = tile;
						}else{
							RoomType r = RoomType.valueOf(next);
							if(!rooms.containsKey(r)){
								//make a a new room of the specified type.
								if(r == RoomType.OBSERVATORY)
									rooms.put(r, new CornerRoom(r, RoomType.KITCHEN));
								else if(r == RoomType.KITCHEN)
									rooms.put(r, new CornerRoom(r, RoomType.OBSERVATORY));
								
								else if(r == RoomType.SPA)
									rooms.put(r, new CornerRoom(r, RoomType.GUEST_HOUSE));
								else if(r == RoomType.GUEST_HOUSE)
									rooms.put(r, new CornerRoom(r, RoomType.SPA));
								else 
									rooms.put(r, new Room(r));
							}
							//Add a room tile to the board,
							RoomTile tile = new RoomTile(new Position(col, row), rooms.get(r));
							tiles[row][col] = tile;
							//and and add that room tile to the corresponding room's
							//list of adjacent rooms.
							rooms.get(r).addAdjacent(tile);
						}
					}
				}
				scan.close();
			}
			
			
			
			lines.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
		
	}
}
