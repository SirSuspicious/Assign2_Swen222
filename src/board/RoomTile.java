package board;


/**
 * A tile which grants access to a Room
 *
 */
public class RoomTile extends Tile {

	private Room to;
	private Position pos;
	
	
	
	public RoomTile(Position pos, Room room) {
		super(pos);
		
		to = room;
	}
	
	public String toString(){
		return super.toString();//+"  Can enter "+to.getName().toString();
	}

	public Room getTo() {
		return to;
	}

	
	
}
