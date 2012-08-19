package board;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
	 * Returns the tiles that can be moved to from the tile parameter.
	 * @param t Tile the that is being moved from.
	 * @return
	 */
	public ArrayList<Tile> getAdjTiles(Tile t){
		ArrayList<Tile> listTiles = new ArrayList<Tile> ();
		
		int row = t.getPosition().getY();
		int col = t.getPosition().getX();
		
		
		if(row != 0 && tiles[row-1][col] != null){
			listTiles.add(tiles[row-1][col]);
		}
		if(row != numR-1 && tiles[row+1][col] != null){
			listTiles.add(tiles[row+1][col]);
		}
		
		if(col != 0 && tiles[row][col-1] != null){
			listTiles.add(tiles[row][col-1]);
		}
		if(col != numC-1 && tiles[row][col+1] != null){
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
