package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessagePanel extends JPanel {
	
	private final double propX;
	private final double propY;
	
	private JTextArea textArea;
	public MessagePanel(double propX, double propY) {
		super();
		this.propX = propX;
		this.propY = propY;
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		textArea = new JTextArea();
		this.add(textArea);
	}
	
	
	@Override
	public Dimension getPreferredSize(){
		int y = (int)(((double)this.getParent().getHeight())*propY);
		int x = (int)(((double)this.getParent().getWidth())*propX);
		return new Dimension(x,y);
	}
	
}
