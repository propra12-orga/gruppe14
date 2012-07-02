/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Alex.Gameplay;
import Jan.Bomberman;
import anika.FileIO;
import anika.SaveGameFilter;

/**
 * @author anika
 * 
 */
public class Controller implements ActionListener, KeyListener {

	Bomberman b;

	Gameplay gameplay;

	/**
	 * Creates new instance of this controller and links it to the view.
	 * 
	 * @param view
	 *            The view which uses this controller
	 */
	public Controller(Bomberman view) {
		this.b = view;
		gameplay = null;
	}

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

			else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				String key = "bomb";
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
		}

		// if key input is 'p', ENTER or ESC do not send to gameplay;
		// instead treat internally
		if (event.getKeyCode() == KeyEvent.VK_P) {
			JOptionPane.showMessageDialog(null,
					"Pause, um weiterzuspielen OK dr�cken!", "Pause",
					JOptionPane.OK_CANCEL_OPTION);
		}

		else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// What to do here?
		}

		else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			// exit();
		}
		// What to do here?
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

	@Override
	public void actionPerformed(ActionEvent ae) {
		// check whether the calling object is an instance of JMenuItem
		if (ae.getSource() instanceof JMenuItem) {
			// if so, check the item's displayed text
			// (may be changed to check for reference later)
			if (((JMenuItem) ae.getSource()).getText().equals("Neues Spiel")) {
				this.initializeGame();
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
				try {
					this.gameplay = FileIO.loadGame(f, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (((JMenuItem) ae.getSource()).getText().equals("Server starten"))
			{
				initializeGameServer();
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
	public void initializeGame() {
		this.gameplay = new Gameplay(2, this);
	}

	/**
	 * initializeGameServer
	 * 
	 * Open a socket, wait for a client to connect and start a game
	 * 
	 */
	public void initializeGameServer()
	{
		// May throw an IOException, so try-catch
		try {
			ServerSocket socket = new ServerSocket(11111);
			// Wait for client to connect
			System.out.println("Bound to: " + socket.getInetAddress());
			socket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
