package board;

import gameObjects.RoomType;


/**
 * A representation of a corner room that has a corridor to another corner room.
 * 
 *
 */
public class CornerRoom extends Room {
	private RoomType to;
	public CornerRoom(RoomType name, RoomType to) {
		super(name);
		this.to = to;
		
	}
	public RoomType getTo() {
		return to;
	}

	
	
}
