package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class BoardCanvas extends Component {
	
	//The x and y proportion for the canvas with respect to the frame it is in.
	private final double propX;
	private final double propY;
	
	private BufferedImage boardImg;

	/**
	 * 
	 * @param proportionX The horizontal proportion this canvas will take up in the parent.
	 * @param proportionY The vertical proportion this canvas will take up in the parent.
	 */
	public BoardCanvas(double proportionX, double proportionY){
		super();
		//The cluedo.jpeg file is a 1600x1600 image.
		boardImg =  new BufferedImage(1600, 1600, BufferedImage.TYPE_INT_ARGB);
		
		propX = proportionX;
		propY = proportionY;
		
	}
	

	@Override
	public void paint(Graphics g){
		
		int newW = (int)(((double)this.getParent().getWidth())*propX);
		int newH = (int)(((double)this.getParent().getHeight())*propY);
		
		if(newH < 1){
			newH = 1;
		}
		if(newW < 1){
			newW = 1;
		}
		
		BufferedImage i = scaleImage(newW, newH);
	
		g.drawImage(i, 0, 0, newW, newH, null);
		
	}
	
	/**
	 * gets the graphics of the image that will be resized and drawn to the canvas.
	 * @return
	 */
	public Graphics getImgGfx(){
		
		return boardImg.getGraphics();
		
		
	}
	
	/**
	 * Scales the boardImg to the specified width and height.
	 * @param width
	 * @param height
	 * @return
	 */
	private BufferedImage scaleImage(int width, int height){
		
		BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = scaled.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		g.drawImage(boardImg, 0, 0, width, height, null);
		
		
		return scaled;
	}
}
