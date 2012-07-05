package axel;

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
		this.layoutController.drawField(board, heigth, width);

	}
	/**
	 *   redrawBoard
	 * 
	 *        redraws the board, depends on the chosen Layout
	 *        
	 *        @param Feld [][] board	 
	 */
	
	public void redrawBoard(Feld[][] board) {
		this.layoutController.redrawField(board);

	}
	
	/**
	 *   drawBomb
	 * 
	 *        draws the standard bomb at a given position
	 *        
	 *        @param int[] position	 
	 */

	public void drawBomb(int[] position) {
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
		this.layoutController.explodePlayer(id);
	}
	
	/**
	 *   setAcitveLayout
	 * 
	 *        setting up the correct Layout
	 *        @param int activeLayout
	 *            
	 */		 
	public void setAcitveLayout(int activeLayout)
	{
		this.layoutController.setLayout(activeLayout);
	}
}
