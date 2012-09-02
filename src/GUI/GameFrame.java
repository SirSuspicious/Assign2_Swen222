package GUI;

import gameObjects.Card;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import board.Position;

public class GameFrame extends JFrame {
	
	private BoardCanvas canvas;
	private final double canvasPropX = 1.0;
	private final double canvasPropY = 10.0/14.0;
	
	private InfoPanel iPanel;
	
	public GameFrame(){
		super();
		this.setPreferredSize(new Dimension(600, 700));
		this.setLayout(new BorderLayout());
		
		canvas = new BoardCanvas(canvasPropX, canvasPropY);
		this.add(canvas, BorderLayout.CENTER);
		
		this.setJMenuBar(new Menu());
		
		iPanel = new InfoPanel(canvasPropX, 1.0 - canvasPropY);
		this.add(iPanel, BorderLayout.SOUTH);
		
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	public Graphics getCanvasGfx(){
		return canvas.getImgGfx();
	}

	public void showDice(int dice1, int dice2){
		iPanel.showDice(dice1, dice2);
	}

	public void displayHand(ArrayList<Card> cards){
		iPanel.displayHand(cards);

	}
	
	public void setTurnButtonListener(ActionListener a){
		iPanel.setTurnButtonListener(a);
	}
	public void setButtonEnabled(boolean b){
		iPanel.setButtonEnabled(b);
	}
	
	/**
	 * Sets the parameter m to be the mouse listener for the board
	 * @param m
	 */
	public void addCanvasListener(MouseListener m){
		canvas.addMouseListener(m);
		
	}
	
	/**
	 * given an x and y coordinate on the board, this method will return the position
	 * of the tile that those coordinates are on.
	 * @param x
	 * @param y
	 * @return
	 */
	public Position findSquarePos(int x, int y){
		return canvas.findSquarePos(x, y);
	}
	
	/**
	 * Given a tile coordinate, this will return the position of the tile on the board in pixels.
	 * @param x
	 * @param y
	 * @return
	 */
	public Position findCoordinatePos(int x, int y){
		return canvas.findCoordinatePos(x, y);
	}
	
	
	
	/**
	 * Appends the specified text to the text area.
	 * @param text
	 */
	public void addText(String text){
		iPanel.addText(text);
	}

}
