package anika;

import static upietz.Constants.*;
import upietz.*;

public class Bomb_2 implements Runnable {

	private Spielfeld board; // Current Spielfeld
	private int[] position = new int[2]; // coordinates of the bomb
	private int time2explode = 1000; // time until explosion in ms
	private int radius = 5; // radius of explosion in fields

	public Bomb_2(int[] position, Spielfeld board) {
		this.position = position;
		this.board = board;

		// Inform Spielfeld about existing bombs
		if (this.board.dropBomb2(this.position)) {
			Thread t = new Thread(this);
			t.start();
		}
	}

	/**
	 * run
	 * 
	 * Starts Countdown. When countdown is over, inform Spielfeld about
	 * exploding bomb.
	 */
	@Override
	public void run() {
		// Countdown
		try {
			Thread.sleep(this.time2explode);
		} catch (InterruptedException e) {
			// can bombs be moved, picked up or stopped?
			// if so, interrupt this thread and handle event here
			// in this case, there should be some flags to indicate this
		}
		// After expiration of time, transfer all required data to Spielfeld
		this.board.explode(this.position, this.radius);
	}

}