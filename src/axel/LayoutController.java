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

public class LayoutController {

	private JLabel[][] staticField = null;

	private JLabel[][] envField = null;

	// Prebuffered Images
	private BufferedImage floorIMG = null;
	private BufferedImage solidWallIMG = null;
	private BufferedImage boxIMG = null;
	private BufferedImage doorIMG = null;
	private BufferedImage bombIMG = null;

	private BufferedImage explo1IMG = null;
	private BufferedImage explo2IMG = null;
	private BufferedImage explo3IMG = null;

	// Duplicate from bomb but not reachable otherwise
	private static final int LIFETIME_BOMB = 3000;
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

	// For convenience only
	private JLayeredPane gameArea = null;

	public LayoutController() {
		this.preloadImages();
	}

	public LayoutController(int heigth, int width) {
		this.init(heigth, width);
		this.preloadImages();
	}

	private void init(int heigth, int width) {
		this.staticField = new JLabel[heigth][width];
		this.envField = new JLabel[heigth][width];
		this.heigth = heigth;
		this.width = width;

		this.gameArea = Bomberman.getGameArea();
		this.initField();
		this.initPlayers();
	}

	private void initPlayers() {
		this.players = new PlayerDisplayController[2];
		this.players[0] = new PlayerDisplayController(0);
		this.players[1] = new PlayerDisplayController(1);
	}

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

		// We need to layers to display the field one layer for the static
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
	}

	// Preload and resize images
	private void preloadImages() {
		try {
			this.floorIMG = ImageIO.read(new File(System
					.getProperty("user.dir") + "/src/graphics/floor.png"));
			this.floorIMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.floorIMG);
			this.solidWallIMG = ImageIO.read(new File(System
					.getProperty("user.dir") + "/src/graphics/solidwall.jpg"));
			this.solidWallIMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.solidWallIMG);
			this.boxIMG = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/src/graphics/box.png"));
			this.boxIMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.boxIMG);
			this.doorIMG = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/src/graphics/door.png"));
			this.doorIMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.doorIMG);
			this.bombIMG = ImageIO.read(new File(System.getProperty("user.dir")
					+ "/src/graphics/bomb.png"));
			this.bombIMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.bombIMG);
			this.explo1IMG = ImageUtilities.applyWhiteTransparency(ImageIO
					.read(new File(System.getProperty("user.dir")
							+ "/src/graphics/explosion1.png")));
			this.explo1IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.explo1IMG);
			this.explo2IMG = ImageUtilities.applyWhiteTransparency(ImageIO
					.read(new File(System.getProperty("user.dir")
							+ "/src/graphics/explosion2.png")));
			this.explo2IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.explo2IMG);
			this.explo3IMG = ImageUtilities.applyWhiteTransparency(ImageIO
					.read(new File(System.getProperty("user.dir")
							+ "/src/graphics/explosion3.png")));
			this.explo3IMG = ImageUtilities.resize(ADAPT_PANEL_WIDTH,
					ADAPT_PANEL_HEIGTH, this.explo3IMG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}

	// Draw the field initialize it when needed
	public void drawField(Feld[][] board, int heigth, int width) {
		if (this.heigth == 0 && this.width == 0) {
			this.init(heigth, width);
		}

		for (int i = 0; i < this.heigth; i++)
			for (int j = 0; j < this.width; j++) {
				if (board[i][j].typ == Constants.FLOOR) {

					this.staticField[i][j]
							.setIcon(new ImageIcon(this.floorIMG));
				} else if (board[i][j].typ == Constants.SOLID_WALL) {
					this.staticField[i][j].setIcon(new ImageIcon(
							this.solidWallIMG));
				}
				// If we there are env objects on the panel we still need to
				// init the static layer under it
				if (board[i][j].typ == Constants.BREAKABLE_WALL) {
					this.envField[i][j].setIcon(new ImageIcon(this.boxIMG));
					this.staticField[i][j]
							.setIcon(new ImageIcon(this.floorIMG));
				} else if (board[i][j].typ == Constants.EXIT
						|| board[i][j].isExit) {
					this.envField[i][j].setIcon(new ImageIcon(this.doorIMG));
					this.staticField[i][j]
							.setIcon(new ImageIcon(this.floorIMG));
				}
			}
	}

	// Draws a player and adds him to the field if hes not already there
	public void drawPlayer(int id, int[] pos) {
		if (!this.players[id].getLabel().isAncestorOf(this.gameArea))
			this.players[id].addToScene(this.gameArea);

		this.players[id].draw(pos[0], pos[1]);

		this.gameArea.moveToFront(this.players[id].getLabel());
	}

	// Move all players to the top
	private void playerMoveForeground() {
		this.players[0].moveUp();
		this.players[1].moveUp();
	}

	// Moves a player from startPos to endPos. Includes smoothed panel
	// transition.
	public void movePlayer(int id, int[] startPos, int[] endPos) {
		this.players[id].move(startPos[0], startPos[1], endPos[0], endPos[1]);
	}

	// Player explodes with a centered explosion sprite
	public void explodePlayer(int id) {
		this.players[id].explode();
	}

	// Uses the TempOverlay JLabel to display a bomb that disappears after
	// LIFETIME_BOMB
	public void drawBomb(int x, int y) {
		TempOverlay temp = new TempOverlay(x, y, this.bombIMG);
		temp.deleteAfter(LayoutController.LIFETIME_BOMB);
		this.gameArea.add(temp);
		this.gameArea.moveToFront(temp);
		this.playerMoveForeground();
	}

	// Draws an explosion on the tile based on the orientation of the explosion.
	// This depends on the location of the explosion center.
	// Orientation has to be distinguished between center, vertical and
	// horizontal
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
		this.gameArea.add(temp);
		this.gameArea.moveToFront(temp);
	}
}
