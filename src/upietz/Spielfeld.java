/**
 * 
 */
package upietz;

import static upietz.Constants.BREAKABLE_WALL;
import static upietz.Constants.EMPTY;
import static upietz.Constants.FLOOR;
import static upietz.Constants.SOLID_WALL;
import static upietz.Constants.X_KOORD;
import static upietz.Constants.Y_KOORD;
import Alex.Gameplay;
import axel.Draw;
import controller.Controller;

/**
 * @author Stefan Upietz
 * 
 *         Spielfeld
 * 
 *         Class Spielfeld manages an array which containts information about
 *         the map of the game. It saves for each field, what it contains
 *         (Player, Bomb, Item)
 * 
 */
public class Spielfeld {
	/* Board */
	private Feld[][] board;
	/* An Array with all starting positions */
	private int[][] startPositionen;
	/* Dimensions of the board */
	private int width;
	private int height;
	/* Dedicated Gameplay */
	private Gameplay master;
	/* View */
	private Draw screen;
	/* Controller */
	private Controller control;
	/* registered player */
	private int registeredPlayer = 0;

	/**
	 * constructor
	 * 
	 * Initializes the array with dimHeight x dimWidth fields. Is InitialSetup
	 * NULL, a standard map is created.
	 * 
	 * @param control
	 * 
	 * @param int dimHeight
	 * @param int dimWidth
	 * @param int[][] initialSetup
	 * @param int startFelder
	 */
	public Spielfeld(int dimHeight, int dimWidth, int[][] initialSetup,
			int startFelder, Draw screen, Gameplay master, Controller control)
			throws Exception {
		this.control = control;
		/* Creating the actual board */
		try {
			this.board = createBoard(dimHeight, dimWidth, initialSetup,
					startFelder);
			this.width = dimWidth;
			this.height = dimHeight;
			this.master = master;
			this.screen = screen;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new Exception(e.getMessage());
		}

		// Show board
		this.screen.drawBoard(this.board, this.height, this.width);
	}

	public Spielfeld(int dimHeight, int dimWidth, Draw screen, Gameplay master,
			Controller control, Feld[][] board) {
		this.width = dimWidth;
		this.height = dimHeight;

		this.board = board;
		this.master = master;
		this.screen = screen;
	}

	public void setField(int x, int y, Feld field) {
		this.board[x][y] = field;
	}

	/**
	 * createBoard
	 * 
	 * An array of field is created from the transmitted data. ToDo: Bis jetzt
	 * kann nur eine Standardkarte erstellt werden.
	 * 
	 * @param int height
	 * @param int width
	 * @param int[][] initialSetup
	 * @return feld[][]
	 */
	private Feld[][] createBoard(int height, int width, int[][] initialSetup,
			int startFelder) throws Exception {
		if (height < 4 || width < 4)
			throw new Exception("Spielfeld ist zu klein!");

		/* initialSetup is ignored and standard map is created. */
		return createStandardMap(height, width, startFelder);
	}

	/**
	 * createStandardMap
	 * 
	 * Creates standard-testmap
	 */
	private Feld[][] createStandardMap(int height, int width, int startFelder) {
		Feld[][] feld = new Feld[height][width];

		/*
		 * StandardMap consists of a row with only FLOOR-fields alternating with
		 * a row with alternating FLOOR and SOLID-WALL-fields. Starts with an
		 * empty row.
		 */
		int everyOdd, everyEven = FLOOR;

		for (int i = 0; i < height; i++) {
			/*
			 * f i%2 = 1, there is an uneven row, so that there is a row with
			 * alternating FLOOR and SOLID-WALL fields. If i%2 = 0, there is
			 * only FLOOR.
			 */
			everyOdd = (i % 2 > 0) ? SOLID_WALL : FLOOR;

			for (int j = 0; j < width; j++) {
				// Initialize object reference
				feld[i][j] = new Feld();
				feld[i][j].typ = (j % 2 > 0) ? everyOdd : everyEven;
			}
		}

		/* Finding an exit for the temporary board */

		java.util.Random zufall = new java.util.Random(5); // Seed to always
															// have the same
															// exit
		int exit_w = zufall.nextInt(width);
		int exit_h = zufall.nextInt(height);
		feld[exit_w][exit_h].isExit = true;
		control.print("Ausgang an: " + exit_w + "," + exit_h);

		/*
		 * Starting positions are fixed and limited to 4 in StandardMap. Always
		 * in the corners of the fields.
		 */
		this.startPositionen = new int[4][2];
		this.startPositionen[0][X_KOORD] = 0;
		this.startPositionen[0][Y_KOORD] = 0;
		this.startPositionen[1][X_KOORD] = (width - 1);
		this.startPositionen[1][Y_KOORD] = 0;
		this.startPositionen[2][X_KOORD] = (height - 1);
		this.startPositionen[2][Y_KOORD] = 0;
		this.startPositionen[3][X_KOORD] = (height - 1);
		this.startPositionen[3][Y_KOORD] = (width - 1);

		return feld;
	}

	/**
	 * moveFigur
	 * 
	 * Given a Player-ID and a field coordinate, this method checks if a move to
	 * this field is valid. If yes, it is performed and true will be returned.
	 * If not, false will be returned.
	 * 
	 * @param int id
	 * @param int[] vonKoord
	 * @param int[] nachKoord
	 * @return boolean
	 */
	public boolean moveFigur(int id, int[] vonKoord, int[] nachKoord) {
		// Make sure that coordinates are inside of the board
		if (nachKoord[X_KOORD] < 0 || nachKoord[Y_KOORD] > this.width
				|| nachKoord[Y_KOORD] < 0 || nachKoord[Y_KOORD] > this.height)
			return false;

		// If new field is exit, call Gameplay.gameWon and end game.
		if (this.board[nachKoord[X_KOORD]][nachKoord[Y_KOORD]].isExit
				&& validMove(nachKoord[X_KOORD], nachKoord[Y_KOORD]))
			this.master.gameWon(id);

		// Is ai coordinate walkable?
		if (validMove(nachKoord[X_KOORD], nachKoord[Y_KOORD])) {
			// If yes, set state of the old position to empty..
			this.board[vonKoord[X_KOORD]][vonKoord[Y_KOORD]].belegt = EMPTY;

			// .. and set new field to belegt.
			this.board[nachKoord[X_KOORD]][nachKoord[Y_KOORD]].belegt = id;

			// valid move, return true
			return true;
		} else {
			// / If it is SOLID_WALL oder belegt, no valid move
			return false;
		}
	}

	/**
	 * validMove
	 * 
	 * Given two coordinates, return true if field is FLOOR and empty
	 * 
	 * @param int x
	 * @param int y
	 * @return boolean
	 */
	private boolean validMove(int x, int y) {
		if ((this.board[x][y].typ == FLOOR || this.board[x][y].typ == BREAKABLE_WALL)
				&& this.board[x][y].belegt == EMPTY)
			return true;
		else {
			control.print("Kein gültiger Zug nach " + x + "," + y);
			return false;
		}
	}

	/**
	 * registerPlayer
	 * 
	 * public method to be called by players. If a player joins the game, this
	 * method is called. A startposition of the pool is assigned to it and
	 * marked as belegt.
	 * 
	 * @param int id
	 * @return int[]
	 */
	public int[] registerPlayer(int id) {
		/*
		 * ToDo - was passiert, wenn alle Positionen aufgebraucht sind? - wenn
		 * die Startpositionen gelöscht werden, was passiert bei einem restart?
		 */
		int x, y;
		int[] returnPosition = new int[2];

		// Current position of registeredPlayer is taken
		x = this.startPositionen[this.registeredPlayer][X_KOORD];
		y = this.startPositionen[this.registeredPlayer][Y_KOORD];

		// amount of players is increased
		this.registeredPlayer++;

		// board is occupied with player-id
		this.board[x][y].belegt = id;

		returnPosition[X_KOORD] = x;
		returnPosition[Y_KOORD] = y;

		return returnPosition;
	}

	/**
	 * dropBomb
	 * 
	 * Public method to be called by bomb objects. A bomb is marked on the
	 * transmitted position. If there already is a bomb an the field, return
	 * false, otherwise return true
	 * 
	 * @param int[] position
	 * @return boolean
	 */
	public boolean dropBomb(int[] position) {
		if (!this.board[position[X_KOORD]][position[Y_KOORD]].hasBomb) {
			this.board[position[X_KOORD]][position[Y_KOORD]].hasBomb = true;
			this.screen.drawBomb(position);
			return true;
		} else
			return false;
	}

	/**
	 * explode
	 * 
	 * Public method to be called by bomb objects. A bomb explodes on the given
	 * coordinates. The affected fields are found out and: - Are commited to
	 * draw - Is one of the fields is belegt, call Player.die() - All fields are
	 * set to empty
	 * 
	 * @param int[] position
	 * @param int radius
	 */
	public void explode(int[] position, int radius) {
		// More clear. x and y are source coordinates
		int x = position[X_KOORD];
		int y = position[Y_KOORD];
		int i = 0; // Iterator

		// Bomb explodes in a cross, which center is the current position
		explodeTile(x, y); // If there is a bomb, no test is required
		/*
		 * ToDo: Das sieht so aus als wäre es alles in einer for-Schleife
		 * eleganter
		 */

		/*
		 * ToDo: Prinzipiell sollten Bomben nacheinander explodieren. Im Moment
		 * explodiert eine Bombe, sobald sie von der alten Explosion erreicht
		 * wird. Gibt es also viele Bomben in einer Reihe explodiert die
		 * ursprüngliche als letztes! Das ist in bestimmten Situationen nicht
		 * wünschenswert.
		 */

		// ...to the left
		while (++i <= radius) // don't walk over radius
		{
			int b_x = x - i;
			int b_y = y;

			if (b_x >= 0 // don't walk over board
					&& (this.board[b_x][b_y].typ == FLOOR || this.board[b_x][b_y].typ == BREAKABLE_WALL)) {
				explodeTile(b_x, b_y);
				// If here is a bomb, this one is fired as well = chain reaction
				if (this.board[b_x][b_y].hasBomb) {
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			} else
				break; // ...ends this one
		}

		// ...upwards
		i = 0;
		while (++i <= radius) // don't walk over radius
		{
			int b_x = x;
			int b_y = y - i;

			if (b_y >= 0 // don't walk over board
					&& (this.board[b_x][b_y].typ == FLOOR || this.board[b_x][b_y].typ == BREAKABLE_WALL)) {
				explodeTile(b_x, b_y);
				// If here is a bomb, this one is fired as well = chain reaction
				if (this.board[b_x][b_y].hasBomb) {
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			} else
				break; // ...und beendet diese
		}

		// ...nach rechts
		i = 0;
		while (++i <= radius) // don't walk over radius
		{
			int b_x = x + i;
			int b_y = y;

			if (b_x < this.width // don't walk over board
					&& (this.board[b_x][b_y].typ == FLOOR || this.board[b_x][b_y].typ == BREAKABLE_WALL)) {
				explodeTile(b_x, b_y);
				// If here is a bomb, this one is fired as well = chain reaction
				if (this.board[b_x][b_y].hasBomb) {
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			} else
				break; // ...und beendet diese
		}

		// ...nach unten
		i = 0;
		while (++i <= radius) // don't walk over radius
		{
			int b_x = x;
			int b_y = y + i;

			if (b_y < this.height // don't walk over board
					&& (this.board[b_x][b_y].typ == FLOOR || this.board[b_x][b_y].typ == BREAKABLE_WALL)) {
				explodeTile(b_x, b_y);
				// If here is a bomb, this one is fired as well = chain reaction
				if (this.board[b_x][b_y].hasBomb) {
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			} else
				break; // .. ends explosion
		}
	}

	/**
	 * explodeTile
	 * 
	 * Undertakes all steps to let a field explode
	 * 
	 * @param int x
	 * @param int y
	 */
	private void explodeTile(int x, int y) {
		// ToDo: Es sollte eine unterschiedliche Darstellung für die Explosion
		// von
		// FLOOR oder BREAKABLE_WALL-Teilen geben

		// Let tiles explode
		this.screen.explodeTile(x, y);
		// Remove a bossible bomb
		this.board[x][y].hasBomb = false;

		// Is there a player on the tile?
		if (this.board[x][y].belegt != EMPTY) {
			// Inform master, that he is dead.
			this.master.deregisterPlayer(this.board[x][y].belegt);

			// FMark tile as empty
			this.board[x][y].belegt = EMPTY;
			// If tile had type BREAKABLE_WALL, change to FLOOR
			if (this.board[x][y].typ == BREAKABLE_WALL)
				this.board[x][y].typ = FLOOR;
		}
	}

	public Feld[][] getStructure() {
		return board;
	}
}
