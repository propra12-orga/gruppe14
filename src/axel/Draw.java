package axel;

import static upietz.Constants.X_KOORD;
import static upietz.Constants.Y_KOORD;

import  upietz.Constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import Jan.Bomberman;

import upietz.Spielfeld;
import upietz.Feld;
import controller.Controller;

public class Draw {

	private Controller control;
	
	private LayoutController layoutController=null;
	
	
	
	public Draw(Controller control) {
		this.control = control;
		this.layoutController = new LayoutController();
	}
	


	public void drawBoard(Feld[][] board, int heigth, int width) {
		control.print("Feld malen");
		System.out.println("Drawing play area: " + heigth +" "+width);
		this.layoutController.drawField(board, heigth, width);

	}

	public void drawBomb(int[] position) {
		System.out.println("Bombe malen an " + position[X_KOORD] + ","
				+ position[Y_KOORD]);
		this.layoutController.drawBomb(position [0], position[1]);
	}
	
	public void explodeTile(int x, int y) {
		System.out.println("Tile explodieren an " + x + "," + y);
		this.layoutController.explodeTile(x, y, 0);
	}

	public void movePlayer(int id, int[] vonKoord, int[] nachKoord) {
		System.out.println("Bewege Player " + id + " von " + vonKoord[X_KOORD] + ","
				+ vonKoord[Y_KOORD] + " nach " + nachKoord[X_KOORD] + ","
				+ nachKoord[Y_KOORD]);
		this.layoutController.movePlayer(id, vonKoord, nachKoord);
		
	}

	public void drawPlayer(int id, int[] position) {
		System.out.println("Male Player " + id + " an Position " + position[X_KOORD]
				+ "," + position[Y_KOORD]);
		this.layoutController.drawPlayer(id, position);
	}

	public void explodePlayer(int id, int[] position) {
		System.out.println("Player " + id + " explodiert an " + position[X_KOORD]
				+ "," + position[Y_KOORD]);
		
		this.layoutController.explodePlayer(id);
	}
}
