package anika;

import static upietz.Constants.X_KOORD;
import static upietz.Constants.Y_KOORD;
import upietz.Spielfeld;
import Alex.Gameplay;
import axel.Draw;

/**
 * Player
 * 
 * Player-instance. Controls all inputs, communicates with Spielfeld and view.
 * 
 * @author upietz
 * 
 */
public class Player {

	private int id; // ID attached from gameplay
	private Spielfeld board; // Spielfeld on which player is located
	private Draw screen; // View
	private Gameplay master; // responsible Gameplay
	private int[] position = new int[2]; // coordinates attached from Spielfeld
	private boolean dead;
	private int score = 0;

	/**
	 * Constructor
	 * 
	 * Receives id and Spielfeld object.
	 * 
	 * @param int id
	 * @param Spielfeld
	 *            board
	 */
	public Player(int id, Spielfeld board, Draw screen, Gameplay master) {
		// saving main data
		this.id = id;
		this.board = board;
		this.master = master;
		this.screen = screen;

		// Register on Spielfeld and receiving of start coordinate
		this.position = this.board.registerPlayer(this.id);

		// View
		this.screen.drawPlayer(this.id, this.position);
	}

	public Player(int id, Spielfeld board, Draw screen, Gameplay master,
			int[] coordinates) {
		this.id = id;
		this.board = board;
		this.master = master;
		this.screen = screen;

		this.position = coordinates;
	}

	/**
	 * moveLeft
	 * 
	 * Checks if move to the left is possible. If yes, new coordinate is set and
	 * request to visualize the move is send to screen object. Returns true if
	 * move is valid and false if not.
	 * 
	 * @return boolean
	 */
	public boolean moveLeft() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD] - 1; // nach links
		newPosition[Y_KOORD] = this.position[Y_KOORD];

		boolean valid = this.board.moveFigur(this.id, this.position,
				newPosition);
		if (valid) {
			this.screen.movePlayer(this.id, this.position, newPosition);
			// Setting the new coordinates
			this.position = newPosition;
		}
		return valid;
	}

	/**
	 * moveRight
	 * 
	 * Checks if move to the right is possible. If yes, new coordinate is set
	 * and request to visualize the move is send to screen object.Returns true
	 * if move is valid and false if not.
	 * 
	 * @return boolean
	 */
	public boolean moveRight() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD] + 1; // nach rechts
		newPosition[Y_KOORD] = this.position[Y_KOORD];

		boolean valid = this.board.moveFigur(this.id, this.position,
				newPosition);
		if (valid) {
			this.screen.movePlayer(this.id, this.position, newPosition);
			// Setting the new coordinates
			this.position = newPosition;
		}
		return valid;
	}

	/**
	 * moveUp
	 * 
	 * Checks if move upwards is possible. If yes, new coordinate is set and
	 * request to visualize the move is send to screen object.Returns true if
	 * move is valid and false if not.
	 * 
	 * @return boolean
	 */
	public boolean moveUp() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD];
		newPosition[Y_KOORD] = this.position[Y_KOORD] - 1; // nach oben

		boolean valid = this.board.moveFigur(this.id, this.position,
				newPosition);
		if (valid) {
			this.screen.movePlayer(this.id, this.position, newPosition);
			// Setting the new coordinates
			this.position = newPosition;
		}
		return valid;
	}

	/**
	 * moveDown
	 * 
	 * Checks if move downwards is possible. If yes, new coordinate is set and
	 * request to visualize the move is send to screen object.Returns true if
	 * move is valid and false if not.
	 * 
	 * @return boolean
	 */
	public boolean moveDown() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD];
		newPosition[Y_KOORD] = this.position[Y_KOORD] + 1; // nach unten

		boolean valid = this.board.moveFigur(this.id, this.position,
				newPosition);
		if (valid) {
			this.screen.movePlayer(this.id, this.position, newPosition);
			// Setting the new coordinates
			this.position = newPosition;
		}
		return valid;
	}

	/**
	 * dropBomb
	 * 
	 * A new bomb is dropped and started
	 * 
	 * @return boolean
	 */
	public boolean dropBomb() {
		// Coordinates are commited, so the bomb can know where it is located
		new Bomb(this.position, this.board, this);
		return true;
	}

	public boolean dropBomb2() {
		// Coordinates are commited, so the bomb can know where it is located
		new Bomb_2(this.position, this.board, this);
		return true;
	}

	/**
	 * die
	 * 
	 * If player is on a field which expldes, this method is called by gameplay.
	 * This object asks view to remove player.
	 * 
	 * @return boolean
	 */
	public boolean die() {
		this.screen.explodePlayer(this.id, this.position);
		dead = true;
		return dead;
	}

	/**
	 * Checks if the player is dead
	 * 
	 * @return true if the player is dead, false otherwise
	 */
	public boolean isDead() {
		return this.dead;
	}

	/**
	 * Gets the array with the player's coordinates
	 * 
	 * @return The array containing the player's coordinates
	 */
	public int[] coordinates() {
		return this.position;
	}

	/**
	 * Returns the player's id
	 * 
	 * @return The id of the player
	 */
	public int getId() {
		return id;
	}

	/**
	 * The current score of this player
	 * 
	 * @return The player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Adjusts the score
	 * 
	 * @param adjustBy
	 *            The amount by which the score has to be adjusted
	 */
	public void adjustScore(int adjustBy) {
		score += adjustBy;
	}

}
