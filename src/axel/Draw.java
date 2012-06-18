package axel;

import static upietz.Constants.BREAKABLE_WALL;
import static upietz.Constants.EXIT;
import static upietz.Constants.FLOOR;
import static upietz.Constants.SOLID_WALL;
import static upietz.Constants.X_KOORD;
import static upietz.Constants.Y_KOORD;

import javax.swing.ImageIcon;

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

	/**
	 * This method will instantiate a new ImageIcon with the appropriate image
	 * for the given tile type.
	 * 
	 * @param tileType
	 *            The type of floor tile for which an image is needed
	 * @return A new ImageIcon object appropriate to the parameter, or null if
	 *         there is none
	 */
	public static ImageIcon getTile(int tileType) {
		ImageIcon image = null;

		switch (tileType) {
		case SOLID_WALL:
			image = new ImageIcon("graphics/solidwall.jpg");
			break;
		case FLOOR:
			image = new ImageIcon("graphics/floor.png");
			break;
		case BREAKABLE_WALL:
			image = new ImageIcon("graphics/box.png");
			break;
		case EXIT:
			image = new ImageIcon("graphics/door.png");
			break;
		}
		return image;
	}
}
