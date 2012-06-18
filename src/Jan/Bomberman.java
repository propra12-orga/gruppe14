package Jan;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import upietz.Feld;
import upietz.Spielfeld;
import axel.Draw;
import controller.Controller;

public class Bomberman extends JFrame {

	private static final long serialVersionUID = -278000529642944434L;

	public Bomberman() {

		setTitle("Bomberman");
		setSize(800, 600);
		setLayout(null);

		// creates a new Controller for key and action listening
		Controller l = new Controller(this);

		// register l as KeyListener
		this.addKeyListener(l);

		// Creates a menubar
		JMenuBar menuBar = new JMenuBar();

		// Adds the menubar to the frame
		setJMenuBar(menuBar);

		// Define and add drop down menu to the menubar
		JMenu fileMenu = new JMenu("Datei");
		JMenu networkMenu = new JMenu("Netzwerk");
		JMenu infoMenu = new JMenu("Info");
		menuBar.add(fileMenu);
		menuBar.add(networkMenu);
		menuBar.add(infoMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem newAction = new JMenuItem("Neues Spiel");
		newAction.addActionListener(l);
		JMenuItem pauseAction = new JMenuItem("Pause");
		JMenuItem exitAction = new JMenuItem("Beenden");

		JMenuItem netAction = new JMenuItem("Beispiel1");
		JMenuItem net2Action = new JMenuItem("Beispiel2");

		JMenuItem infoAction = new JMenuItem("Info");

		fileMenu.add(newAction);
		fileMenu.add(pauseAction);
		fileMenu.add(exitAction);

		networkMenu.add(netAction);
		networkMenu.add(net2Action);

		infoMenu.add(infoAction);

		this.requestFocus();
	}

	public static void main(String[] args) {
		Bomberman me = new Bomberman();
		me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		me.setVisible(true);

	}

	public void setupBoard(Spielfeld gameboard) {
		Feld[][] board = gameboard.getStructure();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				JLabel l = new JLabel(Draw.getTile(board[i][j].typ));
				// JLabel l = new JLabel("" + i + ", " + j);
				int width = l.getIcon().getIconWidth();
				int height = l.getIcon().getIconHeight();
				l.setBounds(width * i, height * j, width, height);
				this.add(l);
			}
		}
		this.validate();
		this.repaint();
	}

}
