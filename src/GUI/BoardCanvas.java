package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import board.*;


public class BoardCanvas extends Component {
	
	//The x and y proportion for the canvas with respect to the frame it is in.
	private final double propX;
	private final double propY;
	
	private BufferedImage boardImg;
	
	//The cluedo.jpeg file is a 1600x1600 image.
	private int origX = 1600;
	private int origY = 1600;
	
	private int scaledX = 0;
	private int scaledY = 0;

	/**
	 * 
	 * @param proportionX The horizontal proportion this canvas will take up in the parent.
	 * @param proportionY The vertical proportion this canvas will take up in the parent.
	 */
	public BoardCanvas(double proportionX, double proportionY){
		super();
		
		boardImg =  new BufferedImage(origX, origY, BufferedImage.TYPE_INT_ARGB);
		
		propX = proportionX;
		propY = proportionY;
		
	}
	

	@Override
	public void paint(Graphics g){
		
		scaledX = (int)(((double)this.getParent().getWidth())*propX);
		scaledY = (int)(((double)this.getParent().getHeight())*propY);
		
		if(scaledY < 1){
			scaledY = 1;
		}
		if(scaledX < 1){
			scaledX = 1;
		}
		
		BufferedImage i = scaleImage(boardImg, scaledX, scaledY);
	
		g.drawImage(i, 0, 0, scaledX, scaledY, null);
		
	}
	
	/*
	 * On the Cluedo board image, the first 39 tiles must be skipped along the x axis to 
	 * reach the first tile.
	 * Similarly the first 38 pixels must be skipped along the y axis.
	 * each tile is 50 by 50 pixels, with a 1 pixel wide gap between each tile and its neighbour.
	 * upon further inspection, i have found that the height of a tile on the cluedo board varies between
	 * 52 and 53 pixels.  -_-
	*/
	private int toFirstXTile = 39;
	private int toFirstYTile = 38;
	private int tileDimX = 52;
	private int tileDimY = 52;
	
	
	public Position findSquarePos(int x, int y){
		
		
		
		double xFac = ((double)origX) / ((double)scaledX);
		double yFac = ((double)origY) / ((double)scaledY);
		
		int newX = (int)((double)x*xFac);
		int newY = (int)((double)y*yFac);
		
		if(newX > toFirstXTile + (tileDimX*24) || newY > toFirstYTile + (tileDimY*29)){
			return null;
		}
		
		newX = newX - toFirstXTile;
		newY = newY - toFirstYTile;
		
		newX = newX/tileDimX;
		newY = newY/tileDimY;
		
		
		return new Position(newX, newY);
	}
	
	public Position findCoordinatePos(int x, int y){
		return new Position(toFirstXTile + x*tileDimX, toFirstYTile+y*tileDimY+(y/2)+1);
	}
	
	/**
	 * 
	 * gets the graphics of the image that will be resized and drawn to the canvas.
	 * @return
	 */
	public Graphics getImgGfx(){
		
		return boardImg.getGraphics();
		
		
	}
	
	/**
	 * Scales the img parameter to the specified width and height.
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage scaleImage(BufferedImage img, int width, int height){
		
		BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = scaled.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		g.drawImage(img, 0, 0, width, height, null);
		
		
		return scaled;
	}
}
