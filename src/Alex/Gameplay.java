package Alex;

import upietz.Spielfeld;
import anika.Player;
import axel.Draw;
import controller.Controller;

/**
 * Gameplay, responsible for the communication between player, Spielfeld, view and controller
 *  
 * @author Volo
 * 
 */
public class Gameplay {
	/**
	 * Instanzvariablen
	 */
	private Spielfeld board; // Board
	private int playerCount; // Amount of players
	private Player[] player; // Array with all players
	private Draw screen; // View
	private Controller control; // Controller

	/**
	 * Constructor
	 * 
	 * Creates a (standard) board and the wished amount of players.
	 * 
	 * @param int player
	 */
	public Gameplay(int player, Controller control) {
		this.control = control;
		// Saves the amount of players
		// this.playerCount = player;
		this.playerCount = player;

		// Initialize a Draw Object
		this.screen = new Draw(control);

		// Create the board, at first with hardcoded values
		try {
			this.board = new Spielfeld(20, 20, null, this.playerCount,
					this.screen, this, control);
		} catch (Exception e) {
			control.print("Spielfeld erstellen gescheitert: " + e.getMessage());
		}

		// Create as many player instances as wished
		this.player = new Player[this.playerCount];
		for (int id = 0; id < this.playerCount; id++) {
			createPlayer(id);
		}

	}

	/**
	 * Constructor without integer parameter initializes game for 1 player as
	 * default
	 */
	public Gameplay(Controller control) {
		this(1, control);
	}

	/**
	 * createPlayer
	 * 
	 * Creates instance of Player with transmitted it und saves it in array 
	 * this.player
	 * 
	 * @param int id
	 */
	private void createPlayer(int id) {
		this.player[id] = new Player(id, this.board, this.screen, this);
	}

	/**
	 * controls
	 * 
	 * Receives key inputs and sends them to the relevant spaces
	 * 
	 * @param String
	 *            key
	 */
	public void controls(String key) {
		/*
		 * Hier sollte dann eine Auswertung nach Taste kommen. Im Moment aber
		 * nur ein Player, also geht alles an diesen. Restliche Eingaben werden
		 * ignoriert.
		 */
		if (key.equals("left")) {
			this.player[0].moveLeft();
		} else if (key.equals("right"))
			this.player[0].moveRight();
		else if (key.equals("up"))
			this.player[0].moveUp();
		else if (key.equals("down"))
			this.player[0].moveDown();
		else if (key.equals("bomb"))
			this.player[0].dropBomb();

		if (key.equals("a"))
			this.player[1].moveLeft();
		else if (key.equals("d"))
			this.player[1].moveRight();
		else if (key.equals("w"))
			this.player[1].moveUp();
		else if (key.equals("s"))
			this.player[1].moveDown();
		else if (key.equals("y"))
			this.player[1].dropBomb();

	}

	/**
	 * gameWon
	 * 
	 * Gets player's id as parameter. This one has won the game.
	 * 
	 * @param int id
	 */
	public void gameWon(int id) {
		// ?
		control.print("And the winner is: Player " + id);
		//System.exit(0);
	}

	/**
	 * gameOver
	 * 
	 * If every player is dead, game is lost.
	 */
	public void gameOver() {
		// ?
		control.print("Game Over!");
		//System.exit(0);
	}

	/**
	 * deregisterPlayer
	 * 
	 * If a player is dead, he deregisters with this method.
	 * 
	 * ToDo:
	 * Im Moment gibt es ja nur einen Spieler, also wird das Spiel sofort
	 * beendet. In Zukunft sollte hier eine Abfrage stehen die nachsieht, ob
	 * noch ein Player aktiv ist. Sind es mehr als einer geht das Spiel weiter,
	 * ist es genau einer hat dieser gewonnen, ist keiner mehr aktiv ist das
	 * Spiel vorbei.
	 * @param int id
	 */
	public void deregisterPlayer(int id) {
		// Informs player, that he is dead.
		this.player[id].die();
		gameOver();
	}
}