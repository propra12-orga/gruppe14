package anika;

import upietz.Spielfeld;

public class Bomb implements Runnable {

	private Spielfeld board; // Current Spielfeld
	private int[] position = new int[2]; // coordinates of the bomb
	private int time2explode = 3000; // time until explosion in ms
	private int radius = 2; // radius of explosion in fields
	private Player owner;

	public Bomb(int[] position, Spielfeld board, Player owner) {
		this.position = position;
		this.board = board;
		this.owner = owner;

		// Inform Spielfeld about existing bombs
		if (this.board.dropBomb(this.position)) {
			Thread t = new Thread(this);
			t.start();
		}
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
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
		this.board.explode(this.position, this.radius, owner);
	}

}
