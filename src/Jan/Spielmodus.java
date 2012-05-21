package Jan;
//import java.awt.*;
import java.awt.event.*;
import Alex.*;

public abstract class Spielmodus implements KeyListener {
	//this.myJEditorPane.KeyListener(this);

public void keyPressed(KeyEvent arg0) {
	this.KeyListener(this);
	if (arg0.getKeyCode() == KeyEvent.VK_LEFT)	{
		String input = "left";
		gameplay.controls(input);
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
		String input = "right";
		gameplay.controls(input);
		}

	else if (arg0.getKeyCode() == KeyEvent.VK_UP)	{
		String input = "up";
		gameplay.controls(input);	
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_DOWN)	{
		String input = "down";
		gameplay.controls(input);	
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_SPACE)	{
		String input = "space";
		gameplay.controls(input);	
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_P)	{
		String input = "p";
		gameplay.controls(input);	
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)	{
		String input = "ESC";
		gameplay.controls(input);	
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
		String input = "enter";
		gameplay.controls(input);
		}
	}

	private void KeyListener(Spielmodus spielmodus) {
		// TODO Auto-generated method stub
		
	}
}

// public void keyReleased(KeyEvent arg0) { }

// public void keyTyped(KeyEvent arg0) { }