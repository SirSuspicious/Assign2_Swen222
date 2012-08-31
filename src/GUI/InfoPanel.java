package GUI;

import gameObjects.Card;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	
	private final double leftPropX = 1.0/3.0;
	private final double leftPropY = 1.0;
	
	private final double propX;
	private final double propY;
	
	private LeftPanel leftPanel;
	private CardPanel cardPanel;
	
	public InfoPanel(double proportionX, double proportionY){
		super();
		propX = proportionX;
		propY = proportionY;
		
		this.setLayout(new BorderLayout());
		
		leftPanel = new LeftPanel(leftPropX, leftPropY);
		
		
		cardPanel = new CardPanel(1.0 - leftPropX, leftPropY);
		
		this.add(leftPanel, BorderLayout.CENTER);
		this.add(cardPanel, BorderLayout.EAST);

		
	}
	
	public void showDice(int dice1, int dice2){
		leftPanel.showDice(dice1, dice2);
	}

	public void displayHand(ArrayList<Card> cards){
		cardPanel.displayHand(cards);
		cardPanel.repaint();

	}
	
	@Override
	public Dimension getPreferredSize(){
		int y = (int)(((double)this.getParent().getHeight())*propY);
		int x = (int)(((double)this.getParent().getWidth())*propX);
		return new Dimension(x,y);
	}
	
}
