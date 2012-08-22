package gameObjects;

import java.awt.Color;



public enum Person implements CardToken {
	
	Kasandra_Scarlett(Color.RED),
	Jack_Mustard(Color.YELLOW),
	Diane_White(Color.WHITE),
	Jacob_Green(Color.GREEN),
	Eleanor_Peacock(Color.BLUE),
	Victor_Plum(Color.MAGENTA);
	
	/*Added a Color field for person,
	 * I plan on simply using coloured circles to display player position.
	 * */
	private Color color;
	
	private Person(Color c){
		color = c;
	}
	
	public Color color(){
		return color;
	}
	
}
