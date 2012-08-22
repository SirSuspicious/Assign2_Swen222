package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	
	
	public GameFrame(){
		super();
		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new BorderLayout());
		
		
		
		
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
}
