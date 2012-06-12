package Alex;

import upietz.Spielfeld;
import anika.Player;
import axel.Draw;
import controller.Controller;

/**
 * Gameplay, verantowrtlich für die Kommunikation zwischen Figur, Spielfled,
 * Darstellung und Steuerung
 * 
 * @author Volo
 * 
 */
public class Gameplay {
	/**
	 * Instanzvariablen
	 */
	private Spielfeld board; // Das Spielfeld
	private int playerCount; // Anzahl der Spieler
	private Player[] player; // Array mit allen Spielern
	private Draw screen; // Die Darstellung
	private Controller control; // Der Controller

	/**
	 * Konstruktor
	 * 
	 * Erstellt ein (Standard-) Spielfeld und die gewünschte Anzahl von
	 * Playern.
	 * 
	 * @param int player
	 */
	public Gameplay(int player, Controller control) {
		this.control = control;
		// Speichern der Spieleranzahl
		// this.playerCount = player;
		this.playerCount = player;

		// Darstellung initialisieren
		this.screen = new Draw(control);

		// Erstellen des Spielfelds, zunächst mit hardcoded Werten
		try {
			this.board = new Spielfeld(20, 20, null, this.playerCount,
					this.screen, this, control);
		} catch (Exception e) {
			control.print("Spielfeld erstellen gescheitert: " + e.getMessage());
		}

		// Nun soviele Spielerinstanzen erstellen wie gewünscht
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
	 * Erstellt eine Player-Instanz mit der übergebenen id und speichert sie im
	 * Array this.player
	 * 
	 * @param int id
	 */
	private void createPlayer(int id) {
		this.player[id] = new Player(id, this.board, this.screen, this);
	}

	/**
	 * controls
	 * 
	 * Nimmt alle Tasteneingaben entgegen und leitet sie an die entsprechenden
	 * Stellen weiter.
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
	 * Bekommt als Parameter die id eines Players. Dieser hat das Spiel
	 * gewonnen.
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
	 * Ist der letzte Spieler tot ist das Spiel verloren.
	 */
	public void gameOver() {
		// ?
		control.print("Game Over!");
		//System.exit(0);
	}

	/**
	 * deregisterPlayer
	 * 
	 * Ist ein Player tot meldet er sich bei dieser Methode vom Spiel ab. ToDo:
	 * Im Moment gibt es ja nur einen Spieler, also wird das Spiel sofort
	 * beendet. In Zukunft sollte hier eine Abfrage stehen die nachsieht, ob
	 * noch ein Player aktiv ist. Sind es mehr als einer geht das Spiel weiter,
	 * ist es genau einer hat dieser gewonnen, ist keiner mehr aktiv ist das
	 * Spiel vorbei.
	 * 
	 * @param int id
	 */
	public void deregisterPlayer(int id) {
		// Dem Player mitteilen, dass er tot ist
		this.player[id].die();
		gameOver();
	}
}