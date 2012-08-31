package GUI;

import gameObjects.Card;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private BoardCanvas canvas;
	private final double canvasPropX = 1.0;
	private final double canvasPropY = 10.0/14.0;
	
	private InfoPanel bPanel;
	
	public GameFrame(){
		super();
		this.setPreferredSize(new Dimension(600, 700));
		this.setLayout(new BorderLayout());
		
		canvas = new BoardCanvas(canvasPropX, canvasPropY);
		this.add(canvas, BorderLayout.CENTER);
		
		this.setJMenuBar(new Menu());
		
		bPanel = new InfoPanel(canvasPropX, 1.0 - canvasPropY);
		this.add(bPanel, BorderLayout.SOUTH);
		
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	public Graphics getCanvasGfx(){
		return canvas.getImgGfx();
	}

	public void showDice(int dice1, int dice2){
		bPanel.showDice(dice1, dice2);
	}

	public void displayHand(ArrayList<Card> cards){
		bPanel.displayHand(cards);

	}

}
