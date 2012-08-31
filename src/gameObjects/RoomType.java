package gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum RoomType implements CardToken {
	
	SPA("RoomImages\\Spa.jpg"),
	THEATER("RoomImages\\Theater.jpg"),
	LIVING_ROOM("RoomImages\\LivingRoom.jpg"),
	OBSERVATORY("RoomImages\\Observatory.jpg"),
	PATIO("RoomImages\\Patio.jpg"),
	SWIMMING_POOL("RoomImages\\Spa.jpg"),
	HALL("RoomImages\\Hall.jpg"),
	KITCHEN("RoomImages\\Kitchen.jpg"),
	DINING_ROOM("RoomImages\\DiningRoom.jpg"),
	GUEST_HOUSE("RoomImages\\GuestHouse.jpg");
	
private BufferedImage img = null;
	
	private RoomType(String fname){
		try{
			img = ImageIO.read(new File(fname));
		}catch(IOException e){
			System.out.println("Failed to load room image: "+ e);
		}
	}
	
	public BufferedImage getImage(){
		return img;
	}
}
