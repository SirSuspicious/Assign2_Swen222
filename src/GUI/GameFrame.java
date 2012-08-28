package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private BoardCanvas canvas;
	private final double canvasPropX = 1.0;
	private final double canvasPropY = 10.0/14.0;
	
	public GameFrame(){
		super();
		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new BorderLayout());
		
		canvas = new BoardCanvas(canvasPropX, canvasPropY);
		this.add(canvas, BorderLayout.CENTER);
	
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	public Graphics getCanvasGfx(){
		return canvas.getImgGfx();
	}
	
}
