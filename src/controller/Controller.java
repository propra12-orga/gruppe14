/**
 * 
 */
package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Alex.Gameplay;
import IO.DatabaseOperator;
import IO.FileIO;
import Jan.Bomberman;
import anika.Highscore;
import anika.HighscoreEntryAdder;
import anika.HighscoreList;
import anika.MapFileFilter;
import anika.Player;
import anika.SaveGameFilter;

/**
 * @author anika
 * @author Jan
 * 
 */
public class Controller implements ActionListener, KeyListener {

	Bomberman b;

	Gameplay gameplay;

	Properties config;

	List<Highscore> highscores;

	DatabaseOperator db;

	/**
	 * Creates new instance of this controller and links it to the view.
	 * 
	 * @param view
	 *            The view which uses this controller
	 */
	public Controller(Bomberman view) {
		this.b = view;
		gameplay = null;

		config = FileIO.readConfig();

		try {
			highscores = FileIO.loadHighscores();
		} catch (IOException e) {
			System.out.println("Highscoreliste konnte nicht geladen werden!");
			e.printStackTrace();
		}

	}

	/**
	 * Synchronizes the local highscore list with the remote one
	 */
	public void synchronizeHighscore() {
		if (db == null) {
			db = new DatabaseOperator(config);
		}
		try {
			System.out.println("Schreibe lokale Punkteliste in Datenbank...");
			db.writeHighscores(highscores);

			System.out.println("Lese Datenbankliste ein...");
			this.highscores = db.readHighscores();

			System.out
					.println("Speichere aktualisierte Liste auf Dateisystem...");
			FileIO.saveHighscores(highscores);
			JOptionPane.showMessageDialog(null,
					"Synchronisation erfolgreich abgeschlossen!", "Erfolg",
					JOptionPane.OK_CANCEL_OPTION);
		} catch (SQLException e) {
			System.out.println("Datenbankprobleme: ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Probleme beim Speichern der Highscores:");
			e.printStackTrace();
		}
	}

	/**
	 * @author Jan
	 * 
	 *         sends key events to method in gameplay.
	 * 
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		if (gameplay != null) {

			// if key input is left, right, up, down or space send to gameplay
			if (event.getKeyCode() == KeyEvent.VK_LEFT) {
				String key = "left";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
				String key = "right";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_UP) {
				String key = "up";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
				String key = "down";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_N) {
				String key = "n";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_M) {
				String key = "m";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_W) {
				String key = "w";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_A) {
				String key = "a";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_S) {
				String key = "s";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_D) {
				String key = "d";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_Y) {
				String key = "y";
				redirection(key);
			}

			else if (event.getKeyCode() == KeyEvent.VK_X) {
				String key = "x";
				redirection(key);
			}
		}

		if (event.getKeyCode() == KeyEvent.VK_P) {
			JOptionPane.showMessageDialog(null,
					"Pause, um weiterzuspielen OK dr�cken!", "Pause",
					JOptionPane.OK_CANCEL_OPTION);
		}

	}

	static String tmp = "";

	public void redirection(String key) {
		if (key != tmp) {
			gameplay.controls(key);
			tmp = key;
		} else if (key == tmp) {
		}
	}

	public static void reset() {
		tmp = "";
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * states which actions have to be performed when different menu items have
	 * been clicked.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		// check whether the calling object is an instance of JMenuItem
		if (ae.getSource() instanceof JMenuItem) {
			// if so, check the item's displayed text
			// (may be changed to check for reference later)
			if (((JMenuItem) ae.getSource()).getText().equals("Neues Spiel")) {
				String filename = chooseMapFile();
				this.initializeGame(filename);
			} else if (((JMenuItem) ae.getSource()).getText().equals("Beenden")) {
				System.exit(0);
			} else if (((JMenuItem) ae.getSource()).getText().equals(
					"Spiel speichern")) {
				if (this.b == null || this.gameplay == null)
					return;
				File f = callFileChooser(true); // getting destination file
				try {
					// try to save the file to the file system
					FileIO.saveGame(gameplay, f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (((JMenuItem) ae.getSource()).getText().equals(
					"Spiel laden")) {
				File f = callFileChooser(false);
				if (f == null)
					return;
				b.wipe();
				try {
					this.gameplay = FileIO.loadGame(f, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (((JMenuItem) ae.getSource()).getText().equals(
					"Server starten")) {
				String filename = chooseMapFile();
				initializeGameServer(filename);
			} else if (((JMenuItem) ae.getSource()).getText().equals(
					"Mit Server verbinden")) {
				initializeGameClient();
			} else if (((JMenuItem) ae.getSource()).getText().equals("Info")) {
				JOptionPane
						.showMessageDialog(
								null,
								"Bomberman\n(c) 2012 by Stefa Upietz, Anika Mehlem, \nAxel Honka, Alexander Volodarski, Jan-Niklas Tolles.",
								"Info", JOptionPane.OK_CANCEL_OPTION);
			}

			else if (((JMenuItem) ae.getSource()).getText().equals(
					"Handbuch anzeigen")) {
				Desktop desktop = Desktop.getDesktop();
				URI uri;
				try {
					uri = new URI("Bomberman_Handbuch.pdf");
					desktop.browse(uri);

				} catch (Exception oError) {
					JOptionPane
							.showMessageDialog(
									null,
									"Das Benutzerhandbuch konnte nicht geladen werden.\nBitte versuchen Sie ein manuelles �ffnen.",
									"Fehler", JOptionPane.OK_CANCEL_OPTION);
					oError.printStackTrace();
				}

				;
			} else if (((JMenuItem) ae.getSource()).getText()
					.equals("Layout 1")) {
				// System.out.println ("test1");
				if (this.gameplay != null)
					this.gameplay.setLayout(0);

			}

			else if (((JMenuItem) ae.getSource()).getText().equals("Layout 2")) {
				// System.out.println ("test2");
				if (this.gameplay != null)
					this.gameplay.setLayout(1);

			} else if (((JMenuItem) ae.getSource()).getText().equals(
					"Synchronisieren")) {
				this.synchronizeHighscore();
			} else if (((JMenuItem) ae.getSource()).getText()
					.equals("Anzeigen")) {
				new HighscoreList(b, highscores);
			}
		}

		Bomberman.getGameArea().repaint();
	}

	private File callFileChooser(boolean save) {
		JFileChooser fc = new JFileChooser();
		fc.setVisible(true);
		fc.setFileFilter(new SaveGameFilter());
		int option;
		if (save)
			option = fc.showSaveDialog(b);
		else
			option = fc.showOpenDialog(b);
		if (option == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getAbsoluteFile();
		}
		return null;
	}

	/**
	 * chooseMapFile
	 * 
	 * Open a JFileChooser to choose a map-File to be loaded. Basically taken
	 * from:
	 * http://docs.oracle.com/javase/1.4.2/docs/api/javax/swing/JFileChooser
	 * .html
	 * 
	 * @author upietz
	 * 
	 * @return String
	 */
	private String chooseMapFile() {
		String mapDir = System.getProperty("user.dir") + "/maps";
		JFileChooser mapChooser = new JFileChooser(mapDir);

		mapChooser.setVisible(true);
		mapChooser.setFileFilter(new MapFileFilter());

		int retVal = mapChooser.showOpenDialog(b);
		if (retVal == JFileChooser.APPROVE_OPTION)
			return mapChooser.getSelectedFile().getAbsolutePath();
		else
			// return Standardmap
			return mapDir + "/std.map";
	}

	/**
	 * Prints a string to the output component.
	 * 
	 * @param output
	 *            The String to be printed.
	 */
	public void print(String output) {
		b.appendLine(output);
	}

	/**
	 * Starts new game and initializes all required classes
	 */
	public void initializeGame(String mapFile) {
		// provide clean game area
		b.wipe();
		this.gameplay = new Gameplay(mapFile, 2, this, null);
	}

	/**
	 * initializeGameServer
	 * 
	 * Initialize Server instance with given values.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void initializeGameServer(String mapFile) {
		Server server = new Server();
		// check every second if a client connected
		while (server.connected == false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException iE) {
				System.out.println(iE.getMessage());
			}
		}

		
		// Once a client is connected, start the game
		this.gameplay = new Gameplay(mapFile, 2, this, server);
		
		// Tell server which map to choose
		server.setMap(mapFile);
		server.setGameplay(this.gameplay);
	}

	/**
	 * initializeGameClient
	 * 
	 * Initialize Client instance.
	 */
	public void initializeGameClient() {
		Client client = new Client();
		// provide clean game area
		b.wipe();
		// And start game
		String map;
		while( (map = client.getMap()) == null )
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.gameplay = new Gameplay(map, 2, this, client);
		client.setGameplay(this.gameplay);
	}

	/**
	 * Gets the names of all players. Gets all the player's names and saves
	 * their highscores to the local file before leaving the application.
	 * 
	 * @param players
	 *            The array of players
	 */
	public void handleHighscores(Player[] players) {
		for (Player p : players) {
			new HighscoreEntryAdder(p, highscores);
		}
		try {
			FileIO.saveHighscores(highscores);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
