package board;

public class SearchNode implements Comparable<SearchNode>{

	
	public final int toHere;
	public final int heur;
	public final Position pos;
	
	public SearchNode(int heuristic, int toHere, Position pos){
		this.toHere = toHere;
		heur = heuristic;
		this.pos = pos;
	}

	@Override
	public int compareTo(SearchNode o) {
		
		return toHere + heur - o.toHere - o.heur;
	}
}
