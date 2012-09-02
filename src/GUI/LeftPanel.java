package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LeftPanel extends JPanel {
	
	private final double propX;
	private final double propY;
	
	private final double messagePropX = 1.0;
	private final double messagePropY = 2.0/5.0;
	
	
	private DiceDisplay diceD;
	private MessagePanel messageP;
	
	private JButton nextTurn;
	
	public LeftPanel(double propX, double propY) {
		super();
		this.propX = propX;
		this.propY = propY;
		
		this.setLayout(new BorderLayout());
		
		diceD = new DiceDisplay(messagePropX, messagePropY);
		messageP = new MessagePanel(messagePropX, messagePropY);
		nextTurn = new JButton("Proceed");
		this.add(diceD, BorderLayout.CENTER);
		this.add(messageP, BorderLayout.NORTH);
		this.add(nextTurn, BorderLayout.SOUTH);
		
		
		
	}
	
	public void showDice(int dice1, int dice2){
		diceD.showDice(dice1, dice2);
	}
	
	public void addText(String text){
		messageP.addText(text);
	}
	
	public void setTurnButtonListener(ActionListener a){
		nextTurn.addActionListener(a);
	}
	
	public void setButtonEnabled(boolean b){
		nextTurn.setEnabled(b);
	}
	
	@Override
	public Dimension getPreferredSize(){
		int y = (int)(((double)this.getParent().getHeight())*propY);
		int x = (int)(((double)this.getParent().getWidth())*propX);
		return new Dimension(x,y);
	}
	
	
}
