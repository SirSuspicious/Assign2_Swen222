package gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import board.Position;

public enum Weapon implements CardToken {
	
	ROPE("WeaponImages\\Rope.jpg"),
	CANDLESTICK("WeaponImages\\Candlestick.jpg"),
	KNIFE("WeaponImages\\Knife.jpg"),
	PISTOL("WeaponImages\\Pistol.jpg"),
	BASEBALL_BAT("WeaponImages\\Bat.jpg"),
	DUMBBELL("WeaponImages\\Dumbbell.jpg"),
	TROPHY("WeaponImages\\Trophy.jpg"),
	POISON("WeaponImages\\Poison.jpg"),
	AXE("WeaponImages\\Axe.jpg");
	
	private BufferedImage img = null;
	
	private Weapon(String fname){
		try{
			img = ImageIO.read(new File(fname));
		}catch(IOException e){
			System.out.println("Failed to load weapon image: "+ e);
			System.exit(1);
		}
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
}
