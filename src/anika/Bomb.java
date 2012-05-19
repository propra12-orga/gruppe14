package anika;

public class Bomb implements Runnable {

	public int x;
	public int y;
	public int radius;
	public int damage;
	public int timer;

	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getRadius() {
		return this.radius;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getTimer() {
		return this.timer;
	}

	@Override
	public void run() {
		// this loop will handle the counting down of the timers
		while (timer > 0) {
			try {
				Thread.sleep(1000); // waits for a second
			} catch (InterruptedException e) {
				// can bombs be moved, picked up or stopped?
				// if so, interrupt this thread and handle event here
				// in this case, there should be some flags to indicate this
			}
			timer--; // decrement timer by one
		}
	}
}
