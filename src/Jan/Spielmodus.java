package Jan;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Spielmodus implements KeyListener {
	myJEditorPane.addKeyListener(this);


public void keyPressed(KeyEvent arg0) {
	if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
		// HIER ENTER-AKTION
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
		// HIER RIGHT-AKTION
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_LEFT)	{
		// HIER LEFT-AKTION
		}

	else if (arg0.getKeyCode() == KeyEvent.VK_UP)	{
		// HIER UP-AKTION
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_DOWN)	{
		// HIER DOWN-AKTION
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_SPACE)	{
		// HIER SPACE-AKTION
		}
	
	else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)	{
		// HIER ESCAPE-AKTION
		}
	}
}
// Vor dem Meilenstein: Bis Freitag Interface-Sache klären, Bis Sonntag: Umsetzen
// Instanz v. GamePlay anlegen, um mit ihr zu kommunzieren und ihr den Key zu schicken!
// SWITCH!!
//
// Nach dem Meilenstein:
// Was brauche ich von der Darstellung? Schon mal ein kleines Menü erstellen
// public void keyReleased(KeyEvent arg0) { }

// public void keyTyped(KeyEvent arg0) { }

	
