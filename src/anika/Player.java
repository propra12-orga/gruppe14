package anika;

import axel.*;
import upietz.*;
import Alex.*;
import static upietz.Constants.*;

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

	/**
	 * moveLeft
	 * 
	 * Checks if move to the left is possible. If yes, new coordinate is set
	 * and request to visualize the move is send to screen object.
	 */
	public void moveLeft() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD] - 1; // nach links
		newPosition[Y_KOORD] = this.position[Y_KOORD];

		if (this.board.moveFigur(this.id, this.position, newPosition)) {
			this.screen.movePlayer(this.id, this.position, newPosition);

			// Setting the new coordinates
			this.position = newPosition;
		}
	}

	/**
	 * moveRight
	 * 
	 * Checks if move to the right is possible. If yes, new coordinate is set
	 * and request to visualize the move is send to screen object.
	 */
	public void moveRight() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD] + 1; // nach rechts
		newPosition[Y_KOORD] = this.position[Y_KOORD];

		if (this.board.moveFigur(this.id, this.position, newPosition)) {
			this.screen.movePlayer(this.id, this.position, newPosition);

			// Setting the new coordinates
			this.position = newPosition;
		}
	}

	/**
	 * moveUp
	 * 
	 * Checks if move upwards is possible. If yes, new coordinate is set
	 * and request to visualize the move is send to screen object.
	 */
	public void moveUp() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD];
		newPosition[Y_KOORD] = this.position[Y_KOORD] - 1; // nach oben

		if (this.board.moveFigur(this.id, this.position, newPosition)) {
			this.screen.movePlayer(this.id, this.position, newPosition);

			// Setting the new coordinates
			this.position = newPosition;
		}
	}

	/**
	 * moveDown
	 * 
	 * Checks if move downwards is possible. If yes, new coordinate is set
	 * and request to visualize the move is send to screen object..
	 */
	public void moveDown() {
		// Calculating new position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD];
		newPosition[Y_KOORD] = this.position[Y_KOORD] + 1; // nach unten

		if (this.board.moveFigur(this.id, this.position, newPosition)) {
			this.screen.movePlayer(this.id, this.position, newPosition);

			// Setting the new coordinates
			this.position = newPosition;
		}
		;
	}

	/**
	 * dropBomb
	 * 
	 * A new bomb is dropped and started
	 */
	public void dropBomb() {
		// Coordinates are commited, so the bomb can know where it is located
		Bomb b = new Bomb(this.position, this.board);
	}

	/**
	 * die
	 * 
	 * If player is on a field which expldes, this method is called by gameplay.
	 * This object asks view to remove player.
	 */
	public void die() {
		this.screen.explodePlayer(this.id, this.position);
	}

}
