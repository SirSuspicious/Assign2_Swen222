package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private BoardCanvas canvas;
	
	public GameFrame(){
		super();
		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new BorderLayout());
		
		canvas = new BoardCanvas(this);
		this.add(canvas, BorderLayout.CENTER);
		
		
		
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
}
