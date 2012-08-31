package gameObjects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public enum Person implements CardToken {
	
	Kasandra_Scarlett(Color.RED, "Characters\\Scarlett.jpg"),
	Jack_Mustard(Color.YELLOW, "Characters\\Mustard.jpg"),
	Diane_White(Color.WHITE, "Characters\\White.jpg"),
	Jacob_Green(Color.GREEN, "Characters\\Green.jpg"),
	Eleanor_Peacock(Color.BLUE, "Characters\\Peacock.jpg"),
	Victor_Plum(Color.MAGENTA, "Characters\\Plum.jpg");
	
	/*Added a Color field for person,
	 * I plan on simply using coloured circles to display player position.
	 * */
	private Color color;
	private BufferedImage img = null;
	
	private Person(Color c, String fname){
		color = c;
		
		try{
			img = ImageIO.read(new File(fname));
		}catch(IOException e){
			System.out.println("Failed to load character image: "+ e);
			System.exit(1);
		}
	}
	
	public Color color(){
		return color;
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
}
