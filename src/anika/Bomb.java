package anika;

import static upietz.Constants.*;
import upietz.*;


public class Bomb implements Runnable {

	private Spielfeld board;				// Spielfeld, auf dem wir uns befinden
	private int[] position = new int[2];	// Koordinate der Bombe
	private int	time2explode = 3000; 		// Zeit zur Explosion in ms
	private int radius = 1;					// Radius in Feldern der Explosion

	public Bomb(int[] position, Spielfeld board) {
		this.position = position;
		this.board = board;
		
		// Dem Spielfeld Bescheid geben, dass die Bombe existiert
		if( this.board.dropBomb(this.position) )
		{
			Thread t = new Thread(this);
			t.start();
		}
	}

	/**
	 * run
	 * 
	 * Startet einen Countdown. Am Ende des Countdowns wird dem Spielfeld
	 * Bescheid gegeben, dass die Bombe explodiert. 
	 */
	@Override
	public void run() {
		// Herunterzählen
		try {
			Thread.sleep(this.time2explode);
		} catch (InterruptedException e) {
			// can bombs be moved, picked up or stopped?
			// if so, interrupt this thread and handle event here
			// in this case, there should be some flags to indicate this
		}
		// Nach Ablauf der Zeit dem Spielfeld alle nötigen Daten übermitteln
		this.board.explode(this.position, this.radius);
	}

}
