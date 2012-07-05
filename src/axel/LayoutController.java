package axel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import upietz.Constants;
import upietz.Feld;
import Jan.Bomberman;

/**
 *   LayoutController
 * 
 *        Class for displaying the board as 2 layers, a static and an environment layer
 *   
 *      
 */		  

public class LayoutController {

	private JLabel[][] staticField = null;

	private JLabel[][] envField = null;
	
	private TempOverlay[][] overlayField = null;

	// Prebuffered Images
	private BufferedImage[] floorIMG = null;
	private BufferedImage[] solidWallIMG = null;
	private BufferedImage[] boxIMG = null;
	private BufferedImage[] doorIMG = null;
	
	private BufferedImage bombIMG = null;
	private BufferedImage bomb2IMG = null;

	private BufferedImage explo1IMG = null;
	private BufferedImage explo2IMG = null;
	private BufferedImage explo3IMG = null;

	// Values in order to let a bomb disappear after Lifetime
	private static final int LIFETIME_BOMB = 3000;
	private static final int LIFETIME_BOMB2 = 1000;
	private static final int LIFETIME_EXPLOSION = 750;

	// These are the original image sizes. Use them with a smaller field or a
	// bigger window. Otherwise the field will be to big to display in the given
	// JLayeredPane
	public static final int ORIG_PANEL_WIDTH = 50;
	public static final int ORIG_PANEL_HEIGHT = 50;

	// Change this values to scale down the complete field to desired size
	public static final int ADAPT_PANEL_WIDTH = 40;
	public static final int ADAPT_PANEL_HEIGTH = 40;

	// Controller for player actions
	private PlayerDisplayController[] players = null;
	// Size of the field is important for initialization
	private int heigth = 0;
	private int width = 0;
	
	private int activeLayout=0;
	
	private Feld[][] board=null;

	// For convenience only
	private JLayeredPane gameArea = null;
	
	/**
	 *   constructor
	 * 
	 *        preloading images
	 *            
	 */		 
	public LayoutController() {
		this.preloadImages();
	}
	/**
	 *   constructor
	 * 
	 *        initializing field and preloading images
	 *            
	 */		 
	public LayoutController(int heigth, int width) {
		this.init(heigth, width);
		this.preloadImages();
	}
	/**
	 *   init
	 * 
	 *        initializing the 2 layers with its Field and players
	 *        
	 *        @param int height
	 *        @param int width
	 *   
	 *      
	 */		  
	private void init(int heigth, int width) {
		this.staticField = new JLabel[heigth][width];
		this.envField = new JLabel[heigth][width];
		this.heigth = heigth;
		this.width = width;

		this.gameArea = Bomberman.getGameArea();
		this.initField();
		this.initPlayers();
	}
	
	/**
	 *   initPlayers
	 * 
	 *        initializing the 2 players
	 *            
	 */		  
	
	private void initPlayers() {
		this.players = new PlayerDisplayController[2];
		this.players[0] = new PlayerDisplayController(0);
		this.players[1] = new PlayerDisplayController(1);
	}
	/**
	 *   initField
	 * 
	 *        initializing and resizing the static and environment layer with its Jlabels
	 *            
	 */		  
	private void initField() {
		for (int i = 0; i < this.heigth; i++)
			for (int j = 0; j < this.width; j++) {
				this.staticField[i][j] = new JLabel();
				this.staticField[i][j].setSize(
						LayoutController.ADAPT_PANEL_WIDTH,
						LayoutController.ADAPT_PANEL_HEIGTH);
				this.staticField[i][j].setLocation(i
						* LayoutController.ADAPT_PANEL_WIDTH, j
						* LayoutController.ADAPT_PANEL_HEIGTH);
				this.staticField[i][j].setVisible(true);
				this.staticField[i][j].setBorder(javax.swing.BorderFactory
						.createEmptyBorder());
				this.gameArea.add(this.staticField[i][j], 2);
			}

		// We need two layers to display the field one layer for the static
		// environment and one for objects that maybe blown up or interacted
		// with
		for (int i = 0; i < this.heigth; i++)
			for (int j = 0; j < this.width; j++) {
				this.envField[i][j] = new JLabel();
				this.envField[i][j].setSize(LayoutController.ADAPT_PANEL_WIDTH,
						LayoutController.ADAPT_PANEL_HEIGTH);
				this.envField[i][j].setLocation(i
						* LayoutController.ADAPT_PANEL_WIDTH, j
						* LayoutController.ADAPT_PANEL_HEIGTH);
				this.envField[i][j].setVisible(true);
				this.envField[i][j].setBorder(javax.swing.BorderFactory
						.createEmptyBorder());
				this.gameArea.add(this.envField[i][j], 1);
				this.gameArea.moveToFront(this.envField[i][j]);
			}
		
		
		this.overlayField = new TempOverlay[this.heigth][this.width];
	}
	/**
	 *   preloadImages
	 * 
	 *        Preload and resize images
	 *            
	 */		
	
	private void preloadImages() {
		try {
			this.floorIMG = new BufferedImage[2];
			this.floorIMG[0] = ImageIO.read(new File(System
					.getProperty("user.dir") + "/graphics/floor.png"));
			this.floorIMG[0] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.floorIMG[0]);
			this.floorIMG[1] = ImageIO.read(new File(System
					.getProperty("user.dir") + "/graphics/desert_floor.png"));
			this.floorIMG[1] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.floorIMG[1]);
			this.solidWallIMG = new BufferedImage[2];
			this.solidWallIMG[0] = ImageIO.read(new File(System
					.getProperty("user.dir") + "/graphics/solidwall.jpg"));
			this.solidWallIMG[0] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.solidWallIMG[0]);
			this.solidWallIMG[1] = ImageIO.read(new File(System
					.getProperty("user.dir") + "/graphics/desert_solidwall.png"));
			this.solidWallIMG[1] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.solidWallIMG[1]);
			this.boxIMG = new BufferedImage[2];
			this.boxIMG[0] = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/graphics/box.png"));
			this.boxIMG[0] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.boxIMG[0]);
			this.boxIMG[1] = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/graphics/desert_breakablewall.png"));
			this.boxIMG[1] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.boxIMG[1]);
			this.doorIMG = new BufferedImage[2];
			this.doorIMG[0] = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/graphics/door.png"));
			this.doorIMG[0] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.doorIMG[0]);
			this.doorIMG[1] = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/graphics/desert_door.png"));
			this.doorIMG[1] = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.doorIMG[1]);
			this.bombIMG = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/graphics/bomb.png"));
			this.bombIMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.bombIMG);
			this.bomb2IMG = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/graphics/bomb2.png"));
			this.bomb2IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.bomb2IMG);
			this.explo1IMG = ImageUtilities.applyWhiteTransparency(ImageIO
					.read(new File(System.getProperty("user.dir")
							+ "/graphics/explosion1.png")));
			this.explo1IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.explo1IMG);
			this.explo2IMG = ImageUtilities.applyWhiteTransparency(ImageIO
					.read(new File(System.getProperty("user.dir")
							+ "/graphics/explosion2.png")));
			this.explo2IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.explo2IMG);
			this.explo3IMG = ImageUtilities.applyWhiteTransparency(ImageIO
					.read(new File(System.getProperty("user.dir")
							+ "/graphics/explosion3.png")));
			this.explo3IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.explo3IMG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}	
	/**
	 *   redrawField
	 * 
	 *        redraws the field with the correct images 
	 *        @param Feld [][] board
	 *            
	 */		 
	
	public void redrawField(Feld[][] board)
	{
		if(this.heigth!=0 && this.width!=0)
			this.drawField(board, this.heigth, this.width);
	}
	
	/**
	 *   drawField
	 * 
	 *        Draw the field 
	 *        @param Feld[][] board
	 *        @param int height
	 *        @param int width
	 *            
	 */		
	
	public void drawField(Feld[][] board, int heigth, int width) {
		if (this.heigth == 0 && this.width == 0) {
			this.init(heigth, width);
		}
		
		this.board = board;

		for (int i = 0; i < this.heigth; i++)
			for (int j = 0; j < this.width; j++) {
				if (board[i][j].typ == Constants.FLOOR) {

					this.staticField[i][j]
							.setIcon(new ImageIcon(this.floorIMG[activeLayout]));
					
				} else if (board[i][j].typ == Constants.SOLID_WALL) {
					this.staticField[i][j].setIcon(new ImageIcon(
							this.solidWallIMG[activeLayout]));
				}
				// If there are env. objects on the panel we still need to
				// initialize the static layer under it
				if (board[i][j].typ == Constants.BREAKABLE_WALL) {
					this.envField[i][j].setIcon(new ImageIcon(this.boxIMG[activeLayout]));
					this.staticField[i][j]
							.setIcon(new ImageIcon(this.floorIMG[activeLayout]));
				} else if (board[i][j].typ == Constants.EXIT
						|| board[i][j].isExit) {
					this.envField[i][j].setIcon(new ImageIcon(this.doorIMG[activeLayout]));
					this.staticField[i][j]
							.setIcon(new ImageIcon(this.floorIMG[activeLayout]));
				}
			}
	}
	/**
	 *   drawPlayer
	 * 
	 *        Draws a player and adds him to the field if hes not already there
	 *        
	 *        @param int id
	 *        @param int[] pos 
	 */
	
	public void drawPlayer(int id, int[] pos) {
		if (!this.players[id].getLabel().isAncestorOf(this.gameArea))
			this.players[id].addToScene(this.gameArea);

		this.players[id].draw(pos[0], pos[1]);

		this.gameArea.moveToFront(this.players[id].getLabel());
	}
	/**
	 *   playerMoveForeground
	 * 
	 *        Move all players to the top
	 *        
	 *      
	 */
	
	private void playerMoveForeground() {
		this.players[0].moveUp();
		this.players[1].moveUp();
	}

	/**
	 *   movePlayer
	 * 
	 *        moves a player from the start position to end position
	 *        
	 *        @param int id	 
	 *        @param startPos
	 *        @param int[] endPos
	 */
	public void movePlayer(int id, int[] startPos, int[] endPos) {
		this.players[id].move(startPos[0], startPos[1], endPos[0], endPos[1]);
	}

	/**
	 *   explodePlayer
	 * 
	 *        explodes a player
	 *        
	 *        @param int id
	 *        
	 */
	public void explodePlayer(int id) {
		this.players[id].explode();
	}
	
	/**
	 *   drawBomb
	 * 
	 *        Uses the TempOverlay JLabel to display a bomb that disappears after
 	 *	      LIFETIME_BOMB
	 *        
	 *        @param int x
	 *        @param int y
	 *        
	 */
	
	public void drawBomb(int x, int y) {
		TempOverlay temp = new TempOverlay(x, y, this.bombIMG);
		temp.deleteAfter(LayoutController.LIFETIME_BOMB);
		if(this.overlayField[x][y]!=null)
			this.overlayField[x][y].delete();
		this.overlayField[x][y] = temp;
		this.gameArea.add(temp);
		this.gameArea.moveToFront(temp);
		this.playerMoveForeground();
	}
	
	/**
	 *   drawBomb2
	 * 
	 *        Uses the TempOverlay JLabel to display the second bomb that disappears after
 	 *	      LIFETIME_BOMB
	 *        
	 *        @param int x
	 *        @param int y
	 *        
	 */
	
	public void drawBomb2(int x, int y) {
		TempOverlay temp = new TempOverlay(x, y, this.bomb2IMG);
		temp.deleteAfter(LayoutController.LIFETIME_BOMB2);
		if(this.overlayField[x][y]!=null)
			this.overlayField[x][y].delete();
		this.overlayField[x][y] = temp;
		this.gameArea.add(temp);
		this.gameArea.moveToFront(temp);
		this.playerMoveForeground();
	}
	
	/**
	 *   explodeTile
	 * 
	 *        Draws an explosion on the tile based on the orientation of the explosion.        
	 *        @param int x
	 *        @param int y
	 *        @param int orientation
	 *        
	 */
	
	public void explodeTile(int x, int y, int orientation) {
		final int CENTER = 0;
		final int VERTICAL = 1;
		final int HORIZONTAL = 2;

		BufferedImage explosion = null;

		switch (orientation) {
		case CENTER:
			explosion = this.explo3IMG;
			break;
		case VERTICAL:
			explosion = this.explo1IMG;
			break;
		case HORIZONTAL:
			explosion = this.explo2IMG;
			break;
		}
		TempOverlay temp = new TempOverlay(x, y, explosion);
		temp.deleteAfter(LayoutController.LIFETIME_EXPLOSION);
		
		if(this.overlayField[x][y]!=null)
			this.overlayField[x][y].delete();
		
		this.overlayField[x][y] = temp;
		
		
		this.gameArea.add(temp);
		this.gameArea.moveToFront(temp);
		
		if(this.board[x][y].typ == Constants.BREAKABLE_WALL)
			this.envField[x][y].setIcon(null);
		
		if(this.board[x][y].isExit)
			this.envField[x][y].setIcon(new ImageIcon(this.doorIMG[activeLayout]));
	}
	/**
	 *   setLayout
	 * 
	 *        setting up the correct Layout
	 *        @param int newLayout
	 *            
	 */		 
	public void setLayout(int newLayout)
	{
		if((newLayout==1 || newLayout==0) && newLayout!=this.activeLayout)
			this.activeLayout = newLayout;
	}
}
