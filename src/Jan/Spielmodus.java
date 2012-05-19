package Jan;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Alex.*;

public abstract class Spielmodus implements KeyListener {
	myJEditorPane.addKeyListener(this);


public void keyPressed(KeyEvent arg0) {
	
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
}

// public void keyReleased(KeyEvent arg0) { }

// public void keyTyped(KeyEvent arg0) { }

	
