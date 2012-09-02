package board;

import gameObjects.RoomType;

public class inRoomTile extends Tile {

	private RoomType room;
	public inRoomTile(Position pos, RoomType room) {
		super(pos);
		// TODO Auto-generated constructor stub
		this.room = room;
	}
	
	public RoomType room(){
		return room;
	}
	
}
