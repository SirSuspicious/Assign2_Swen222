package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class BoardCanvas extends Component {
	
	
	
	
	
	private Image boardImg;

	
	
	//use  component.getBounds() to get current frame size
	private GameFrame frame;
	
	public BoardCanvas(GameFrame f){
		super();
		frame = f;
		
		
	}
	
	private void something(){
		
		this.getParent();
	}
	
	
	@Override
	public void paint(Graphics g){
		
		Image i = scaleImage();
		Graphics gr = i.getGraphics();
		gr.setColor(Color.BLACK);
		gr.fillRect(this.getParent().getWidth()/4, this.getParent().getHeight()/4, this.getParent().getWidth()/4, this.getParent().getHeight()/4);
		
		Graphics2D gfx = (Graphics2D)g;
		
		gfx.drawImage(i, 0, 0, getParent().getWidth(), getParent().getHeight(), this);
		
		
	}
	
	
	public Graphics getGraphics(){
		
		return boardImg.getGraphics();
		
		
	}
	
	
	private Image scaleImage(){
		//TODO scale boardImg to the right size for the frame.
		return new BufferedImage(getParent().getWidth(), getParent().getHeight(), BufferedImage.TYPE_INT_ARGB);
	}
	
}
