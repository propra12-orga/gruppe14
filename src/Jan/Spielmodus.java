package Jan;

import java.awt.*;
import java.awt.event.*;
import Alex.*;

// Fürht Tastaturabfrage durch und sendet bei Drücken von ESC, ENTER, SPACE, UP, DOWN, LEFT, RIGHT, P an controls in Gameplay.java

public class Spielmodus implements KeyListener {

	// Das zugehörige Gameplay
	private Gameplay gameplay;
	
	public Spielmodus() {
		//KeyListener wnd = new Spielmodus();
		//KeyListener (new Spielmodus());
		//this.myJEditorPane.KeyListener(this);
		
		// neues Gameplay mit einem Spieler
		this.gameplay = new Gameplay(1);
	}

public void keyPressed(KeyEvent event) {
	Spielmodus.KeyListener(this);

	if (event.getKeyCode() == KeyEvent.VK_LEFT) {
		String input = "left";
		this.gameplay.controls(input);
	}

else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
String input = "right";
this.gameplay.controls(input);
}

else if (event.getKeyCode() == KeyEvent.VK_UP) {
String input = "up";
this.gameplay.controls(input);
}

else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
String input = "down";
this.gameplay.controls(input);
}

else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
String input = "space";
this.gameplay.controls(input);
}

else if (event.getKeyCode() == KeyEvent.VK_P) {
String input = "p";
this.gameplay.controls(input);
}

else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
String input = "ESC";
this.gameplay.controls(input);
}

else if (event.getKeyCode() == KeyEvent.VK_ENTER){
String input = "enter";
this.gameplay.controls(input); }
}


private static void KeyListener(Spielmodus spielmodus) {
KeyListener (new Spielmodus());
}

public void keyReleased(KeyEvent event) { }

public void keyTyped(KeyEvent event) { }
}
