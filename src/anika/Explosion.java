package anika;

public class Explosion {
	public int x;
	public int y;
	public long timer;



public Explosion(int x, int y) {
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

public void setTimer (long timer) {
	this.timer = System.currentTimeMillis();
}


public long getTimer () {
	return this.timer;
}
}