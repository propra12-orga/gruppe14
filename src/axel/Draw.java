package axel;

import static upietz.Constants.X_KOORD;
import static upietz.Constants.Y_KOORD;
import upietz.Spielfeld;
import upietz.Feld;
import controller.Controller;

public class Draw {

	private Controller control;

	public Draw(Controller control) {
		this.control = control;
	}

	public void drawBoard(Feld[][] board) {
		control.print("Feld malen");
	}

	public void drawBomb(int[] position) {
		control.print("Bombe malen an " + position[X_KOORD] + ","
				+ position[Y_KOORD]);
	}

	public void explodeTile(int x, int y) {
		control.print("Tile explodieren an " + x + "," + y);
	}

	public void movePlayer(int id, int[] vonKoord, int[] nachKoord) {
		control.print("Bewege Player " + id + " von " + vonKoord[X_KOORD] + ","
				+ vonKoord[Y_KOORD] + " nach " + nachKoord[X_KOORD] + ","
				+ nachKoord[Y_KOORD]);
	}

	public void drawPlayer(int id, int[] position) {
		control.print("Male Player " + id + " an Position " + position[X_KOORD]
				+ "," + position[Y_KOORD]);
	}

	public void explodePlayer(int id, int[] position) {
		control.print("Player " + id + " explodiert an " + position[X_KOORD]
				+ "," + position[Y_KOORD]);
	}
}
