/**
 * 
 */
package upietz;

import static upietz.Constants.*;

import java.util.Hashtable;

import Alex.Gameplay;
import axel.Draw;
import controller.Controller;

/**
 * @author Stefan Upietz
 * 
 *         Spielfeld
 * 
 *         Die Klasse Spielfeld ist die Verwaltung eines Arrays, das die
 *         Informationen über die Karte des Spiels enthält. Für jedes Feld
 *         wird gespeichert, was sich in ihm befindet (Figur, Bombe, Item).
 * 
 */
public class Spielfeld {
	/**
	 * Klasse Feld
	 * 
	 * Ersatz für ein struct Feld. Hier werden pro Feld Informationen zu
	 * Feldtyp und aktueller Belegung gespeichert.
	 */
	public class Feld {

		public int typ = UNDEFINED;

		public int belegt = EMPTY;

		public boolean hasBomb = false;
	}

	/* Das Spielfeld */
	private Feld[][] board;
	/* Ein Array mit allen Startpositionen */
	private int[][] startPositionen;
	/* Spielfelddimensionen */
	private int width;
	private int height;
	/* Zugehöriges Gameplay */
	private Gameplay master;
	/* Die Darstellung */
	private Draw screen;
	/* Der Controller */
	private Controller control;

	/**
	 * Konstruktor
	 * 
	 * Initialisiert das Array mit dimHeight x dimWidth Spielfeldern. Ist
	 * initialSetup NULL, wird eine Standardkarte generiert.
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
		/* Erstellen des eigentlichen Spielfeldes */
		try {
			this.board = createBoard(dimHeight, dimWidth, initialSetup,
					startFelder);
			this.width = dimWidth;
			this.height = dimHeight;
			this.master = master;
			this.screen = screen;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		// Stelle das Spielfeld dar
		this.screen.drawBoard(this.board);
	}

	/**
	 * createBoard
	 * 
	 * Mit den übergebenen Daten wird ein Array aus Feldern erstellt. ToDo: Bis
	 * jetzt kann nur eine Standardkarte erstellt werden.
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

		/* initialSetup wird erstmal ignoriert und die Standardkarte generiert. */
		return createStandardMap(height, width, startFelder);
	}

	/**
	 * createStandardMap
	 * 
	 * Erstellt die Standard-Testkarte
	 */
	private Feld[][] createStandardMap(int height, int width, int startFelder) {
		Feld[][] feld = new Feld[height][width];

		/*
		 * Das Standardfeld hat abwechselnd eine Zeile nur mit FLOOR-Feldern und
		 * eine mit abwechselnd FLOOR und SOLID_WALL-Feldern. Start ist mit
		 * einer Leerzeile.
		 */
		int everyOdd, everyEven = FLOOR;

		for (int i = 0; i < height; i++) {
			/*
			 * Ist i%2 = 1 haben wir eine ungerade Zeile, also abwechselnd FLOOR
			 * und SOLID_WALL Ist i%2=0 gibt es nur FLOOR
			 */
			everyOdd = (i % 2 > 0) ? SOLID_WALL : FLOOR;

			for (int j = 0; j < width; j++) {
				// Initialize object reference
				feld[i][j] = new Feld();
				feld[i][j].typ = (j % 2 > 0) ? everyOdd : everyEven;
			}
		}

		/* Für das temporäre Feld noch einen Ausgang finden */
		java.util.Random zufall = new java.util.Random(5); // Seed um immer den
															// selben Ausgang zu
															// haben
		int exit_w = zufall.nextInt(width);
		int exit_h = zufall.nextInt(height);
		feld[exit_w][exit_h].typ = EXIT;
		control.print("Ausgang an: " + exit_w + "," + exit_h);

		/*
		 * Die Startpositionen sind ebenfalls fix und in der Standardkarte auf 4
		 * beschränkt, jeweils in den Ecken des Feldes
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
	 * Gegeben eine Figur-ID und eine Feldkoordinate checkt diese Methode ob ein
	 * Zug auf dieses Feld gültig ist. Wenn ja wird er ausgeführt und true
	 * zurückgegeben, falls nicht wird false zurückgegeben. Das Ausführen der
	 * Bewegung beinhaltet: - Belegen des des neuen Feldes mit der Figur-ID -
	 * Leeren des alten Feldes
	 * 
	 * @param int id
	 * @param int[] vonKoord
	 * @param int[] nachKoord
	 * @return boolean
	 */
	public boolean moveFigur(int id, int[] vonKoord, int[] nachKoord) {
		// Sicherstellen, dass die Koordinaten auch im Feld sind
		if (nachKoord[X_KOORD] < 0 || nachKoord[Y_KOORD] > this.width
				|| nachKoord[Y_KOORD] < 0 || nachKoord[Y_KOORD] > this.height)
			return false;

		// Ist das neue Feld der Ausgang, rufe direkt Gameplay.gameWon auf
		// und beende das Spiel! Kein Test, da der Ausgang immer begehbar ist
		if (this.board[nachKoord[X_KOORD]][nachKoord[Y_KOORD]].typ == EXIT)
			this.master.gameWon(id);

		// Ist die Zielkoordinate begehbar?
		if (validMove(nachKoord[X_KOORD], nachKoord[Y_KOORD])) {
			// Wenn ja, setze Status der alten Positon auf nicht belegt
			this.board[vonKoord[X_KOORD]][vonKoord[Y_KOORD]].belegt = EMPTY;

			// Und setze das neue Feld auf belegt
			this.board[nachKoord[X_KOORD]][nachKoord[Y_KOORD]].belegt = id;

			// Erfolgreicher Zug, true zurück
			return true;
		} else {
			// Ist es SOLID_WALL oder belegt, kein gültiger Zug
			return false;
		}
	}

	/**
	 * validMove
	 * 
	 * Gegeben zwei Koordinaten, liefere true zurück wenn das Feld vom Typ
	 * FLOOR und nicht belegt ist
	 * 
	 * @param int x
	 * @param int y
	 * @return boolean
	 */
	private boolean validMove(int x, int y) {
		if (this.board[x][y].typ == FLOOR && this.board[x][y].belegt == EMPTY)
			return true;
		else {
			control.print("Kein gültiger Zug nach " + x + "," + y);
			return false;
		}
	}

	/**
	 * registerPlayer
	 * 
	 * Öffentliche Methode zum Aufruf durch Figuren. Wenn ein Spieler dem Spiel
	 * beitritt, ruft er diese Methode auf. Ihm wird eine Startposition aus dem
	 * Pool zugewiesen und diese als belegt markiert.
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

		// Es wird immer die erste Position genommen
		x = this.startPositionen[0][X_KOORD];
		y = this.startPositionen[0][Y_KOORD];

		// Das Feld wird mit der Player-ID belegt
		this.board[x][y].belegt = id;

		returnPosition[X_KOORD] = x;
		returnPosition[Y_KOORD] = y;

		// Entfernen der gebrauchten Startposition
		// ArrayUtils.remove(this.startPositionen, 0);

		return returnPosition;
	}

	/**
	 * dropBomb
	 * 
	 * Öffentliche Methode zum Aufruf durch Bomben. An der übergebenen
	 * Position wird eine Bombe markiert. Liegt bereits eine Bombe auf dem Feld,
	 * gib false zurück, sonst true.
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
	 * Öffentliche Methode zum Aufruf durch Bomben. An den gegebenen
	 * Koordinaten wird eine Bombe explodiert. Dazu werden die betroffenen
	 * Felder ermittelt und: - Diese werden Draw übergeben - Ist eins der
	 * Felder belegt, wird Player.die() aufgerufen - Alle Felder werden als leer
	 * gekennzeichnet
	 * 
	 * @param int[] position
	 * @param int radius
	 */
	public void explode(int[] position, int radius) {
		// Etwas übersichtlicher. x und y sind die Ausgangskoordinaten
		int x = position[X_KOORD];
		int y = position[Y_KOORD];
		int i = 0; // Iterator

		// Eine Bombe explodiert in einem Kreuz, dessen Mitte die aktuelle
		// Position ist
		// ...im Zentrum
		explodeTile(x, y); // Wenn hier eine Bombe liegt braucht man keinen Test

		/*
		 * ToDo: Das sieht so aus als wäre es alles in einer for-Schleife
		 * eleganter
		 */

		/* ToDo: Prinzipiell sollten Bomben nacheinander explodieren.
		 * Im Moment explodiert eine Bombe, sobald sie von der alten
		 * Explosion erreicht wird. Gibt es also viele Bomben in einer Reihe
		 * explodiert die ursprüngliche als letztes! Das ist in bestimmten
		 * Situationen nicht wünschenswert.
		 */
		
		// ...nach links
		while (++i <= radius) // nicht über radius hinausgehen
		{
			int b_x = x - i;
			int b_y = y;

			if (b_x >= 0 // Nicht über das Spielfeld hinausgehen
					&& this.board[b_x][b_y].typ == FLOOR
					&& this.board[b_x][b_y].typ == BREAKABLE_WALL )
			{
				explodeTile(b_x, b_y);
				// Wenn an dieser Stelle eine Bombe liegt, wird diese
				// auch gezündet = Kettenreaktion. 
				if( this.board[b_x][b_y].hasBomb )
				{
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			}
			else
				break; // ...und beendet diese
		}

		// ...nach oben
		i = 0;
		while (++i <= radius) // nicht über radius hinausgehen
		{
			int b_x = x;
			int b_y = y - i;

			if (b_y >= 0 // Nicht über das Spielfeld hinausgehen
					&& this.board[b_x][b_y].typ == FLOOR
					&& this.board[b_x][b_y].typ == BREAKABLE_WALL )
			{
				explodeTile(b_x, b_y);
				// Wenn an dieser Stelle eine Bombe liegt, wird diese
				// auch gezündet = Kettenreaktion. 
				if( this.board[b_x][b_y].hasBomb )
				{
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			}
			else
				break; // ...und beendet diese
		}

		// ...nach rechts
		i = 0;
		while (++i <= radius) // nicht über radius hinausgehen
		{
			int b_x = x + i;
			int b_y = y;

			if (b_x <= this.width // Nicht über das Spielfeld hinausgehen
					&& this.board[b_x][b_y].typ == FLOOR
					&& this.board[b_x][b_y].typ == BREAKABLE_WALL )
			{
				explodeTile(b_x, b_y);
				// Wenn an dieser Stelle eine Bombe liegt, wird diese
				// auch gezündet = Kettenreaktion. 
				if( this.board[b_x][b_y].hasBomb )
				{
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			}
			else
				break; // ...und beendet diese
		}

		// ...nach unten
		i = 0;
		while (++i <= radius) // nicht über radius hinausgehen
		{
			int b_x = x;
			int b_y = y + i;

			if (b_y <= this.height // Nicht über das Spielfeld hinausgehen
					&& this.board[b_x][b_y].typ == FLOOR
					&& this.board[b_x][b_y].typ == BREAKABLE_WALL )
			{
				explodeTile(b_x, b_y);
				// Wenn an dieser Stelle eine Bombe liegt, wird diese
				// auch gezündet = Kettenreaktion. 
				if( this.board[b_x][b_y].hasBomb )
				{
					int[] koord = { b_x, b_y };
					explode(koord, 2);
				}
			}
			else
				break; // ...beides Beendet die Explosion
		}
	}

	/**
	 * explodeTile
	 * 
	 * Übernimmt alle Schritte, ein Feld explodieren zu lassen
	 * 
	 * @param int x
	 * @param int y
	 */
	private void explodeTile(int x, int y) {
		// ToDo: Es sollte eine unterschiedliche Darstellung für die Explosion von
		// FLOOR oder BREAKABLE_WALL-Teilen geben
		
		// Teile explodieren lassen
		this.screen.explodeTile(x, y);
		// Eine mögliche Bombe entfernen
		this.board[x][y].hasBomb = false;

		// Befindet sich eine Figur auf diesem Feld?
		if (this.board[x][y].belegt != EMPTY) {
			// Dann dem Master mitteilen, dass er tot ist.
			this.master.deregisterPlayer(this.board[x][y].belegt);

		// Feld als leer markieren
		this.board[x][y].belegt = EMPTY;
		// Wenn das Feld den Typ BREAKABLE_WALL hatte, ist es nun FLOOR
		if( this.board[x][y].typ == BREAKABLE_WALL )
			this.board[x][y].typ = FLOOR;
		}
	}
}
