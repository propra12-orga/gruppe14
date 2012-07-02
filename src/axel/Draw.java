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


/**
 * 
 * 
 *         Draw
 * 
 *         Graphical representation of Bomberman such like the board, bombs, players etc. 
 * 
 */
public class Draw {

	private Controller control;
	
	private LayoutController layoutController=null;
	
	
	
	public Draw(Controller control) {
		this.control = control;
		this.layoutController = new LayoutController();
	}
	

	/**
	 *   drawBoard
	 * 
	 *        draws our board as 2 layers, a static layer and an environment layer
	 *        
	 *        @param Feld[][] board
	 *        @param int height
	 * 		  @param int width
	 */
	
	public void drawBoard(Feld[][] board, int heigth, int width) {
		control.print("Feld malen");
		System.out.println("Drawing play area: " + heigth +" "+width);
		this.layoutController.drawField(board, heigth, width);

	}
	/**
	 *   drawBomb
	 * 
	 *        draws the standard bomb at a given position
	 *        
	 *        @param int[] position	 
	 */

	public void drawBomb(int[] position) {
		System.out.println("Bombe malen an " + position[X_KOORD] + ","
				+ position[Y_KOORD]);
		this.layoutController.drawBomb(position [0], position[1]);
	}
	
	/**
	 *   drawBomb_2
	 * 
	 *        draws a second, more powerful bomb at a given position
	 *        
	 *        @param int[] position	 
	 */
	
	public void drawBomb_2(int[] position) {
		System.out.println("Bombe malen an " + position[X_KOORD] + ","
				+ position[Y_KOORD]);
		this.layoutController.drawBomb2(position [0], position[1]);
	}
	
	/**
	 *   explodeTile
	 * 
	 *        draws the explosion
	 *        
	 *        @param int x
	 *        @param int y	 
	 */
	
	public void explodeTile(int x, int y) {
		System.out.println("Tile explodieren an " + x + "," + y);
		this.layoutController.explodeTile(x, y, 0);
	}
	
	/**
	 *   movePlayer
	 * 
	 *        moves a player from the start position to end position
	 *        
	 *        @param int id	 
	 *        @param int[] vonKoord
	 *        @param int[] nachKoord
	 */

	public void movePlayer(int id, int[] vonKoord, int[] nachKoord) {
		System.out.println("Bewege Player " + id + " von " + vonKoord[X_KOORD] + ","
				+ vonKoord[Y_KOORD] + " nach " + nachKoord[X_KOORD] + ","
				+ nachKoord[Y_KOORD]);
		this.layoutController.movePlayer(id, vonKoord, nachKoord);
		
	}
	/**
	 *   drawPlayer
	 * 
	 *        draws a player at a given position
	 *        
	 *        @param int id
	 *        @param int[] position 
	 */
	public void drawPlayer(int id, int[] position) {
		System.out.println("Male Player " + id + " an Position " + position[X_KOORD]
				+ "," + position[Y_KOORD]);
		this.layoutController.drawPlayer(id, position);
	}

	/**
	 *   explodePlayer
	 * 
	 *        explodes a player
	 *        
	 *        @param int id
	 *        @param int[] position 
	 */
	
	public void explodePlayer(int id, int[] position) {
		System.out.println("Player " + id + " explodiert an " + position[X_KOORD]
				+ "," + position[Y_KOORD]);
		
		this.layoutController.explodePlayer(id);
	}
}
