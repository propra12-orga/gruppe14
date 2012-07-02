package Jan;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import controller.Controller;


/**
 * @author Jan Tolles
 * 
 *         Bomberman
 * 
 *         contains the methods which create the JFrame, the menu bar and its menu items.
 * 
 */

public class Bomberman extends JFrame {

	private static final long serialVersionUID = -278000529642944434L;

	private JTextArea outputConsole;

	private static JLayeredPane gameArea;

	public Bomberman() {
		timer();

		setTitle("Bomberman");
		setSize(620, 665);

		// creates a new Controller for key and action listening
		Controller l = new Controller(this);

		// register l as KeyListener
		this.addKeyListener(l);

		// Creates a menubar
		JMenuBar menuBar = new JMenuBar();

		// Adds the menubar to the frame
		setJMenuBar(menuBar);

		// Define and add drop down menu to the menubar
		JMenu fileMenu = new JMenu("Menü");
		JMenu helpMenu = new JMenu("Hilfe");
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem newAction = new JMenuItem("Neues Spiel");
		newAction.addActionListener(l);
		JMenuItem serverAction = new JMenuItem("Server starten");
		serverAction.addActionListener(l);
		JMenuItem clientAction = new JMenuItem("Mit Server verbinden");
		clientAction.addActionListener(l);
		JMenuItem pauseAction = new JMenuItem("Pause");
		JMenuItem saveAction = new JMenuItem("Spiel speichern");
		saveAction.addActionListener(l);
		JMenuItem loadAction = new JMenuItem("Spiel laden");
		loadAction.addActionListener(l);
		JMenuItem exitAction = new JMenuItem("Beenden");
		exitAction.addActionListener(l);

		
		JMenuItem infoAction = new JMenuItem("Info");
		infoAction.addActionListener(l);
		JMenuItem helpAction = new JMenuItem("Handbuch anzeigen");
		helpAction.addActionListener(l);

		
		fileMenu.add(newAction);
		fileMenu.add(serverAction);
		fileMenu.add(clientAction);
		fileMenu.add(pauseAction);
		fileMenu.add(saveAction);
		fileMenu.add(loadAction);
		fileMenu.add(exitAction);

	
		helpMenu.add(helpAction);
		helpMenu.add(infoAction);
		
		// create new text area within a scroll pane container
		outputConsole = new JTextArea();
		outputConsole.setLineWrap(true);
		outputConsole.setSize(400, 400);
		outputConsole.setEditable(false);
		outputConsole.setFocusable(false);

		this.gameArea = new JLayeredPane();
		this.gameArea.setSize(400, 400);
		this.gameArea.setEnabled(false);
		this.gameArea.setVisible(true);
		this.gameArea.setLayout(null);
		add(this.gameArea);

		this.requestFocus();
	}
	
	
	/**
	 *
	 * 
	 *         method
	 * 
	 *         creates console output.
	 * 
	 */

	
	public void appendLine(String line) {
		// get the current text in the output
		String currentText = outputConsole.getText();
		// append new text and linebreak
		currentText += line + "\n";
		// update the console with the new text
		outputConsole.setText(currentText);
	}

	public static JLayeredPane getGameArea() {
		return Bomberman.gameArea;
	}

	public static void main(String[] args) {
		newBomberman();
	}
	
	public static void newBomberman(){
		Bomberman me = new Bomberman();
		//new Sound(System.getProperty("user.dir") +"/graphics/musik.wav").loop();
		me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		me.setVisible(true);
	}
	
	public void timer() {
		Reset reset;
		reset = new Reset();
		reset.start();
	}

  public void wipe() {
    gameArea.removeAll();
    this.repaint();
  }
	
	class Reset extends Thread {
		public void run() {
			while (true) {
				try {
					Controller.reset();
					sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
