package Jan;
import java.awt.*;
import java.awt.event.*;
import Alex.*;

// Fürht Tastaturabfrage durch und sendet bei Drücken von ESC, ENTER, SPACE, UP, DOWN, LEFT, RIGHT, P an controls in gameplay.java

public class Spielmodus implements KeyListener {

public static void main (String[] args) {  
	//KeyListener wnd = new Spielmodus(); 
	//KeyListener (new Spielmodus());
	//this.myJEditorPane.KeyListener(this);
}

public void keyPressed(KeyEvent event) {
	Spielmodus.KeyListener(this);
	
	if (event.getKeyCode() == KeyEvent.VK_LEFT)	{
		String input = "left";
		gameplay.controls(input);
		}
	
	else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
		String input = "right";
		gameplay.controls(input);
		}

	else if (event.getKeyCode() == KeyEvent.VK_UP)	{
		String input = "up";
		gameplay.controls(input);	
		}
	
	else if (event.getKeyCode() == KeyEvent.VK_DOWN)	{
		String input = "down";
		gameplay.controls(input);	
		}
	
	else if (event.getKeyCode() == KeyEvent.VK_SPACE)	{
		String input = "space";
		gameplay.controls(input);	
		}
	
	else if (event.getKeyCode() == KeyEvent.VK_P)	{
		String input = "p";
		gameplay.controls(input);	
		}
	
	else if (event.getKeyCode() == KeyEvent.VK_ESCAPE)	{
		String input = "ESC";
		gameplay.controls(input);	
		}
	
	else if (event.getKeyCode() == KeyEvent.VK_ENTER){
		String input = "enter";
		gameplay.controls(input); }
		}


private static void KeyListener(Spielmodus spielmodus) {
	KeyListener (new Spielmodus());
		}

public void keyReleased(KeyEvent event) { }

public void keyTyped(KeyEvent event) { }
}