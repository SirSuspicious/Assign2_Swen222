package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class BottomPanel extends JPanel {
	
	private final double dicePropX = 1.0/3.0;
	private final double dicePropY = 1.0;
	
	private final double propX;
	private final double propY;
	
	private DicePanel dicePanel;
	private CardPanel cardPanel;
	
	public BottomPanel(double proportionX, double proportionY){
		super();
		propX = proportionX;
		propY = proportionY;
		
		this.setLayout(new BorderLayout());
		
		dicePanel = new DicePanel();
		
		cardPanel = new CardPanel();
		
		this.add(dicePanel, BorderLayout.WEST);
		this.add(cardPanel, BorderLayout.EAST);
		
		
		
		
		
		
	}
	
}
