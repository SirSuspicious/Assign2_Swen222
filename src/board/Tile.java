package board;

public class Tile {
	protected Position pos;

	
	public Tile(Position pos) {
		this.pos = pos;
	}


	public Position getPosition() {
		return pos;
	}


	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public String toString(){
		return pos.toString();
	}
	
	
}
