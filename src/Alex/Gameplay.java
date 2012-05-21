package Alex;

import anika.*;
import axel.*;
import Jan.*;
import upietz.*;

/**
 * Gameplay, verantowrtlich f�r die Kommunikation zwischen Figur, Spielfled, Darstellung und Steuerung
 * @author Volo
 *
 */
public class Gameplay {
	/**
	 * Instanzvariablen
	 */
	private Spielfeld board;	// Das Spielfeld
	private int playerCount;	// Anzahl der Spieler
	private Player[] player;	// Array mit allen Spielern
	
	/**
	 * Konstruktor
	 * 
	 * Erstellt ein (Standard-) Spielfeld und die gewünschte Anzahl von Playern.
	 * 
	 * @param	int		player
	 */
	public Gameplay( int player )
	{
		// Speichern der Spieleranzahl
		//this.playerCount = player;
		// Im Moment aber nur 1 Spieler erlaubt, also hardcoded!
		this.playerCount = 1;
		
		// Erstellen des Spielfelds, zunächst mit hardcoded Werten
		try
		{
			this.board = new Spielfeld(20,20,null,this.playerCount);
		}
		catch( Exception e )
		{
			System.out.println("Spielfeld erstellen gescheitert: " + e.getMessage());
		}
		
		// Nun soviele Spielerinstanzen erstellen wie gewünscht
		this.player = new Player[this.playerCount];
		for( int id = 0; id < this.playerCount; id++)
		{
			createPlayer(id);
		}
	}
	
	/**
	 * createPlayer
	 * 
	 * Erstellt eine Player-Instanz mit der übergebenen id und speichert sie im Array this.player
	 * 
	 * @param	int		id
	 */
	private void createPlayer(int id)
	{
		this.player[id] = new Player(id);
	}
	
	/**
	 * receiveKey
	 * 
	 * Nimmt alle Tasteneingaben entgegen und leitet sie an die entsprechenden Stellen weiter.
	 * 
	 * @param	String	key
	 */
	public void receiveKey( String key)
	{
		/*
		 * Hier sollte dann eine Auswertung nach Taste kommen. Im Moment aber nur ein
		 * Player, also geht alles an diesen. Restliche Eingaben werden ignoriert.
		 */
		if( key.equals("left") )
			this.player[0].moveLeft();
		else if( key.equals("right") )
			this.player[0].moveRight();
		else if( key.equals("up") )
			this.player[0].moveUp();
		else if( key.equals("down") )
			this.player[0].moveDown();
		else if( key.equals("bomb") )
			this.player[0].dropBomb();
	}
	
	/**
	 * gameWon
	 * 
	 * Bekommt als Parameter die id eines Players. Dieser hat das Spiel gewonnen.
	 * 
	 * @param	int		id
	 */
	public void gameWon( int id )
	{
		// ?
		System.out.println("And the winner is: Player " + id);
	}
	
	/**
	 * gameOver
	 * 
	 * Ist der letzte Spieler tot ist das Spiel verloren.
	 */
	public void gameOver()
	{
		// ?
		System.out.println("Game Over!");
	}
	
	/**
	 * deregisterPlayer
	 * 
	 * Ist ein Player tot meldet er sich bei dieser Methode vom Spiel ab.
	 * ToDo: Im Moment gibt es ja nur einen Spieler, also wird das Spiel sofort
	 * beendet. In Zukunft sollte hier eine Abfrage stehen die nachsieht,
	 * ob noch ein Player aktiv ist. Sind es mehr als einer geht das Spiel
	 * weiter, ist es genau einer hat dieser gewonnen, ist keiner mehr aktiv
	 * ist das Spiel vorbei.
	 * 
	 * @param	int		id
	 */
	public void deregisterPlayer( int id )
	{
		gameOver();
	}
}