package Jan;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.Controller;

public class Bomberman extends JFrame {

	private static final long serialVersionUID = -278000529642944434L;

	private JTextArea outputConsole;

	public Bomberman() {

		setTitle("Bomberman");
		setSize(800, 600);

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

		// create new text area within a scroll pane container
		outputConsole = new JTextArea();
		outputConsole.setLineWrap(true);
		outputConsole.setSize(400, 400);
		outputConsole.setEditable(false);
		outputConsole.setFocusable(false);
		JScrollPane scroller = new JScrollPane(outputConsole);
		// since we wrap lines, we don't need or want a horizontal scrollbar
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// so text will not move around, we will always display the vertical bar
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setSize(400, 400);
		// add the scroll pane to the content pane
		add(scroller);

		this.requestFocus();
	}

	public void appendLine(String line) {
		// get the current text in the output
		String currentText = outputConsole.getText();
		// append new text and linebreak
		currentText += line + "\n";
		// update the console with the new text
		outputConsole.setText(currentText);
	}

	public static void main(String[] args) {
		Bomberman me = new Bomberman();
		me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		me.setVisible(true);

	}

}