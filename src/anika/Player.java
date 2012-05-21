package anika;

import upietz.*;
import static upietz.Constants.*;

/**
 * Player
 * 
 * Eine Spielerinstanz. Regelt alle Eingaben, kommuniziert mit dem Spielfeld und der Darstellung.
 * 
 * @author upietz
 *
 */
public class Player {

	private int id;				// Vom Gameplay zugewiesene ID
	private Spielfeld board;	// Das Spielfeld auf dem sich die Figur befindet
	private int[] position = new int[2];				// Vom Spielfeld zugewiesene Koordinaten

	/**
	 * Konstruktor
	 * 
	 * Empfängt eine id und ein Spielfeldobjekt.
	 * 
	 * @param 	int			id
	 * @param 	Spielfeld	board
	 */
	public Player(int id, Spielfeld board) {
		// Grunddaten sichern
		this.id = id;
		this.board = board;

		// Registrieren auf dem Spielfeld und beziehen der Startkoordinaten
		this.position = this.board.registerPlayer(this.id); 
	}

	/**
	 * moveLeft
	 * 
	 * Prüft, ob ein Zug nach links möglich ist. Falls ja wird die neue Koordinate gesetzt und
	 * die Darstellung aufgefordert, den Zug zu visualisieren.
	 */
	public void moveLeft()
	{
		// Berechnen der neuen Position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD] - 1;	// nach links
		newPosition[Y_KOORD] = this.position[Y_KOORD];
		
		if( this.board.moveFigur(this.id, this.position, newPosition) )
		{
			Draw.movePlayer(this.id, this.position, newPosition);
			
			// Setzen der neuen Koordinaten
			this.position = newPosition;
		}
	}
	
	/**
	 * moveRight
	 * 
	 * Prüft, ob ein Zug nach rechts möglich ist. Falls ja wird die neue Koordinate gesetzt und
	 * die Darstellung aufgefordert, den Zug zu visualisieren.
	 */
	public void moveRight()
	{
		// Berechnen der neuen Position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD] + 1;	// nach rechts
		newPosition[Y_KOORD] = this.position[Y_KOORD];
		
		if( this.board.moveFigur(this.id, this.position, newPosition) )
		{
			Draw.movePlayer(this.id, this.position, newPosition);
			
			// Setzen der neuen Koordinaten
			this.position = newPosition;
		}
	}
	
	/**
	 * moveUp
	 * 
	 * Prüft, ob ein Zug nach oben möglich ist. Falls ja wird die neue Koordinate gesetzt und
	 * die Darstellung aufgefordert, den Zug zu visualisieren.
	 */
	public void moveUp()
	{
		// Berechnen der neuen Position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD];
		newPosition[Y_KOORD] = this.position[Y_KOORD] - 1;	// nach oben
		
		if( this.board.moveFigur(this.id, this.position, newPosition) )
		{
			Draw.movePlayer(this.id, this.position, newPosition);
			
			// Setzen der neuen Koordinaten
			this.position = newPosition;
		}
	}
	
	/**
	 * moveDown
	 * 
	 * Prüft, ob ein Zug nach unten möglich ist. Falls ja wird die neue Koordinate gesetzt und
	 * die Darstellung aufgefordert, den Zug zu visualisieren.
	 */
	public void moveDown()
	{
		// Berechnen der neuen Position
		int[] newPosition = new int[2];
		newPosition[X_KOORD] = this.position[X_KOORD];
		newPosition[Y_KOORD] = this.position[Y_KOORD] + 1;	// nach unten
		
		if( this.board.moveFigur(this.id, this.position, newPosition) )
		{
			Draw.movePlayer(this.id, this.position, newPosition);
			
			// Setzen der neuen Koordinaten
			this.position = newPosition;
		}
	}
	
	/**
	 * dropBomb
	 * 
	 * Eine neue Bombe wird gelegt und gestartet
	 */
	public void dropBomb()
	{
		// Koordinaten werden übergeben, damit die Bombe weiss wo sie ist
		Bomb b = new Bomb(this.position, this.board);
		b.run();
	}
	
	/**
	 * die
	 * 
	 * Steht der Player auf einem Feld, das explodiert, wird diese Methode vom Gameplay aufgerufen.
	 * Dieses Objekt bittet die Darstellung, den Player zu entfernen.
	 */
	public void die()
	{
		Draw.explodePlayer(this.id, this.position);
	}
	
}
