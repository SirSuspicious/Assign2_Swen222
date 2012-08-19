package board;

import java.util.ArrayList;

import gameObjects.RoomType;

public class Room {
	private RoomType name;
	private ArrayList<Tile> adjacentTo;
	public Room(RoomType name) {

		this.name = name;
		adjacentTo = new ArrayList<Tile>();
	}
	
	public void addAdjacent(Tile tile){
		adjacentTo.add(tile);
	}
	
	/**
	 * returns a string representation of the position of every tile from which 
	 * a player can enter this room.
	 * @return
	 */
	public String adjToString(){
		String s = "";
		for(Tile t : adjacentTo)
			s = s+t.toString()+" || ";
		return s;
	}

	public RoomType getName() {
		return name;
	}

	public ArrayList<Tile> getAdjacentTo() {
		return adjacentTo;
	}

	


	
	
}
