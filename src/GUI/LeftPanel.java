package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class LeftPanel extends JPanel {
	
	private final double propX;
	private final double propY;
	
	private final double messagePropX = 1.0;
	private final double messagePropY = 2.0/5.0;
	
	private DiceDisplay diceD;
	private MessagePanel messageP;
	
	public LeftPanel(double propX, double propY) {
		super();
		this.propX = propX;
		this.propY = propY;
		
		this.setLayout(new BorderLayout());
		
		diceD = new DiceDisplay(messagePropX, 1.0 - messagePropY);
		messageP = new MessagePanel(messagePropX, messagePropY);
		
		this.add(diceD, BorderLayout.CENTER);
		this.add(messageP, BorderLayout.NORTH);
		
		
	}
	
	public void showDice(int dice1, int dice2){
		diceD.showDice(dice1, dice2);
	}
	
	@Override
	public Dimension getPreferredSize(){
		int y = (int)(((double)this.getParent().getHeight())*propY);
		int x = (int)(((double)this.getParent().getWidth())*propX);
		return new Dimension(x,y);
	}
	
	
}
