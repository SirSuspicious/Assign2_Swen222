package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class DiceDisplay extends Component {
	
	private final double propX;
	private final double propY;
	private int diceD = 200;
	private int diceSpacing = 4;
	
	private BufferedImage one = null, two = null, three =null, four = null, five = null, six = null;
	
	private BufferedImage dice;
	
	
	
	public DiceDisplay(double propX, double propY) {
		super();
		
		
		
		this.propX = propX;
		this.propY = propY;
		
		dice = new BufferedImage((diceD*2)+diceSpacing, diceD, BufferedImage.TYPE_INT_ARGB);
		
		try{
			one = ImageIO.read(new File("DieImages\\DiceOne.jpg"));
			two = ImageIO.read(new File("DieImages\\DiceTwo.jpg"));
			three = ImageIO.read(new File("DieImages\\DiceThree.jpg"));
			four = ImageIO.read(new File("DieImages\\DiceFour.jpg"));
			five = ImageIO.read(new File("DieImages\\DiceFive.jpg"));
			six = ImageIO.read(new File("DieImages\\DiceSix.jpg"));
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	
	public void paint(Graphics g){
		int newW = (int)(((double)this.getParent().getWidth())*propX);
		int newH = (int)(((double)this.getParent().getHeight())*propY);
	
		if(newH < 1){
			newH = 1;
		}
		if(newW < 1){
			newW = 1;
		}
		
		BufferedImage i = BoardCanvas.scaleImage(dice, newW, newH);
	
		g.drawImage(i, 0, 0, newW, newH, null);
	}
	
	public void showDice(int dice1, int dice2){
		BufferedImage d1 = getImage(dice1);
		BufferedImage d2 = getImage(dice2);
		Graphics g = dice.getGraphics();
		g.drawImage(d1, 0, 0, null);
		g.drawImage(d2, diceD+diceSpacing, 0, null);
		
	}
	
	/**
	 * returns the Dice face corresponding to the int parameter.
	 * @param i
	 * @return
	 */
	private BufferedImage getImage(int i){
		
		switch(i){
		case 1: return one;
		case 2: return two;
		case 3: return three;
		case 4: return four;
		case 5: return five;
		case 6: return six;
		}
		return null;
	}
	
}
