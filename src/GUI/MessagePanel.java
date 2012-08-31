package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class MessagePanel extends JPanel {
	
	private final double propX;
	private final double propY;
	
	private JTextArea textArea;
	private JScrollPane scrollPane;
	public MessagePanel(double propX, double propY) {
		super();
		this.propX = propX;
		this.propY = propY;
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		
		
		this.add(scrollPane);
	}
	
	public void addText(String text){
		textArea.append(text);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	
	@Override
	public Dimension getPreferredSize(){
		int y = (int)(((double)this.getParent().getHeight())*propY);
		int x = (int)(((double)this.getParent().getWidth())*propX);
		return new Dimension(x,y);
	}
	
}
