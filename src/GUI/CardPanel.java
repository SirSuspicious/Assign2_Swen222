package GUI;
import gameObjects.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;





public class CardPanel extends Component {
	
	private final double propX;
	private final double propY;
	
	private BufferedImage hand;
	
	private final int cardResizeX = 200;
	private final int cardResizeY = 300;
	
	private int numCards = 1;
	
	public CardPanel(double propX, double propY) {
		super();
		
		
		this.propX = propX;
		this.propY = propY;
	}
	
	/**
	 * displays the cards contained within the "cards" ArrayList
	 * in this component. 
	 * @param cards
	 */
	public void displayHand(ArrayList<Card> cards){
	/*
	 * Resize every card to 200*300, as they are not all the same dimension,
	 * then paint them all to a new image, and resize that to fit the component.	
	 */
		
		hand = new BufferedImage(cardResizeX*cards.size(), cardResizeY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = hand.getGraphics();
		
		numCards = cards.size();
		
		for(int i = 0; i < numCards; i++){
			BufferedImage rs = BoardCanvas.scaleImage(cards.get(i).getImg(), cardResizeX, cardResizeY);
			g.drawImage(rs, i*cardResizeX, 0, cardResizeX, cardResizeY, null);
		}
		
	}
	@Override
	public void paint(Graphics g){
		
		// x = 2/3 y
		int newW = 0;
		int newH = 0;
		
		if(((double)getParent().getWidth()/(double)numCards) < (double)getParent().getHeight()){
			newW = (int)(((double)this.getParent().getWidth())*propX);
			newH = (int)(((double)newW)/((double)numCards) *3.0/2.0);
		}else{
			newH = (int)(((double)this.getParent().getHeight())*propY);
			newW = (int)(((double)newH)*((double)numCards) *2.0/3.0);
		}
		
	
		if(newH < 1){
			newH = 1;
		}
		if(newW < 1){
			newW = 1;
		}
		BufferedImage rs = BoardCanvas.scaleImage(hand, newW, newH);
		g.drawImage(rs, 0, 0, newW, newH, null);
	}
	
	@Override
	public Dimension getPreferredSize(){
		int y = (int)(((double)this.getParent().getHeight())*propY);
		int x = (int)(((double)this.getParent().getWidth())*propX);
		return new Dimension(x,y);
	}
	
}
