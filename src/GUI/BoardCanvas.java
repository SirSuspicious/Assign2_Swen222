package GUI;

import java.awt.Component;
import java.awt.Image;


public class BoardCanvas extends Component {
	
	
	
	private Image boardImg;
	
	//use  component.getBounds() to get current frame size
	private GameFrame frame;
	
	public BoardCanvas(GameFrame f){
		super();
		frame = f;
		
	}
	
}
