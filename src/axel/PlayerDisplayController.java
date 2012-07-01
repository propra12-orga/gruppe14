package axel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class PlayerDisplayController {
	
	//Label that is responsible for displaying the player
	private JLabel playerLabel;
	
	//Id of the player
	private int playerID;
	
	//Preloaded player sprites for all orientations
	BufferedImage[] playerImages = null;
	
	//Centered Explosion sprite for player explosion
	BufferedImage playerExplode = null;
	
	//Constants to determine orientation
	public static final int ORIENT_NORTH = 0;
	public static final int ORIENT_EAST = 1;
	public static final int ORIENT_SOUTH = 2;
	public static final int ORIENT_WEST = 3;
	
	//Player orientation
	int orientation = ORIENT_EAST;
	
	//Sequence step of the movement phase
	int sequenceStep = 0;
	
	//For convenience again
	JLayeredPane gameArea = null;
	
	//Constructor initializes player by assigning id and preloading images, position is not yet assigned. Dummy position is assumed.
	public PlayerDisplayController(int id)
	{
		
		this.playerID = id;	
		
		this.preloadImages();
		
		this.playerLabel = new JLabel();
		this.playerLabel.setSize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH);
		this.playerLabel.setLocation(0, 0);
		this.playerLabel.setVisible(true);
		this.playerLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		this.updateImage();
	}
	
	//Initializes player with appropriate orientation
	public PlayerDisplayController(int id, int orientation)
	{
		this(id);
		this.orientation = orientation;	
		this.updateImage();
	}
	
	//Preloads and resizes all needed images for the player
	private void preloadImages()
	{
		if(this.playerID==0)
			this.preloadPlayerOneImages();
		else
			this.preloadPlayerTwoImages();
		
		try {
			this.playerExplode = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/explosion3.png"));
			this.playerExplode = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerExplode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.resizePlayerImages();
	}
	
	//resizes the images of the player to the chosen format in the LayoutController
	private void resizePlayerImages()
	{
		//NORTH
		playerImages[0] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[0]);
		playerImages[1] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[1]);
		playerImages[2] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[2]);
		//EAST
		playerImages[3] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[3]);
		playerImages[4] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[4]);
		playerImages[5] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[5]);
		//SOUTH
		playerImages[6] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[6]);
		playerImages[7] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[7]);
		playerImages[8] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[8]);
		//WEST
		playerImages[9] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[9]);
		playerImages[10] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[10]);
		playerImages[11] = ImageUtilities.resize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH, this.playerImages[11]);
	}
	
	//Preloads the needed images for player one.
	private void preloadPlayerOneImages()
	{
		this.playerImages = new BufferedImage[12];
		
		try {
			//NORTH
			playerImages[0] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/back1.png"));
			playerImages[1] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/back2.png"));
			playerImages[2] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/back3.png"));	
			//EAST
			playerImages[3] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/right1.png"));
			playerImages[4] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/right2.png"));
			playerImages[5] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/right3.png"));		
			//SOUTH
			playerImages[6] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/front1.png"));
			playerImages[7] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/front2.png"));
			playerImages[8] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/front3.png"));
			//WEST
			playerImages[9] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/left1.png"));
			playerImages[10] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/left2.png"));
			playerImages[11] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/left3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Preloads the needed images for player two.
	private void preloadPlayerTwoImages()
	{
		this.playerImages = new BufferedImage[12];
		
		try {
			//NORTH
			playerImages[0] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2back1.png"));
			playerImages[1] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2back2.png"));
			playerImages[2] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2back3.png"));	
			//EAST
			playerImages[3] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2right1.png"));
			playerImages[4] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2right2.png"));
			playerImages[5] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2right3.png"));		
			//SOUTH
			playerImages[6] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2front1.png"));
			playerImages[7] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2front2.png"));
			playerImages[8] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2front3.png"));
			//WEST
			playerImages[9] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2left1.png"));
			playerImages[10] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2left2.png"));
			playerImages[11] = ImageIO.read(new File(System.getProperty("user.dir") + "/src/graphics/2left3.png"));		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Updates the image that should be assigned to the player. Depends on the orientation and the current sequence step.
	private void updateImage()
	{
		this.playerLabel.setIcon(new ImageIcon(this.playerImages[this.orientation*3 + this.sequenceStep]));
		this.playerLabel.repaint(); //Is this really needed?
	}
	
	//Perform a sequence step

	public void walk()
	{
		this.updateImage();
		this.sequenceStep = (this.sequenceStep + 1) % 3;
	}
	
	//Getter for the player label
	public JLabel getLabel()
	{
		return this.playerLabel;
	}
	
	//Adds the player to a JComponent also is a getter for the gameArea.
	public void addToScene(JComponent scene)
	{
		this.gameArea = (JLayeredPane) scene;
		this.gameArea.add(this.playerLabel,0);
	}
	
	//Determines if an orientation change is needed based on the step that is about to be performed. Returns the orientation after the step.
	private int determineOrientationChange(int x1, int y1, int x2, int y2)
	{
		if(x1>x2)
			return PlayerDisplayController.ORIENT_WEST;
		if(x1<x2)
			return PlayerDisplayController.ORIENT_EAST;
		if(y1>y2)
			return PlayerDisplayController.ORIENT_NORTH;
		if(y1<y2)
			return PlayerDisplayController.ORIENT_SOUTH;

		//Never get here
		return 0;
	}
	
	//Updates the Panel of the game in the region that has been updated by the step.
	private void updatePanelOnOrientation(int x1, int y1, int x2, int y2)
	{
		switch(this.orientation)
		{
			case PlayerDisplayController.ORIENT_NORTH:
				this.gameArea.paintImmediately(x2, y2, LayoutController.ADAPT_PANEL_WIDTH,2*LayoutController.ADAPT_PANEL_HEIGTH);
				break;
			case PlayerDisplayController.ORIENT_EAST:
				this.gameArea.paintImmediately(x1, y1, 2*LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH);
				break;
			case PlayerDisplayController.ORIENT_SOUTH:
				this.gameArea.paintImmediately(x1, y1, LayoutController.ADAPT_PANEL_WIDTH, 2*LayoutController.ADAPT_PANEL_HEIGTH);
				break;
			case PlayerDisplayController.ORIENT_WEST:
				this.gameArea.paintImmediately(x2, y2, 2*LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH);
				break;
		}
		
	}
	
	//Moves the player from (x1,y1) to (x2,y2) Also smoothes the transition from one panel to another.
	public void move(int x1, int y1, int x2, int y2)
	{
		int newOrientation = determineOrientationChange(x1,y1,x2,y2);
		int stepCounter =0;
		
		
		if(newOrientation!=this.orientation)
		{
			this.orientation = newOrientation;
			this.sequenceStep = 0;
			this.updateImage();
		}
		
		//Smooth the step by performing multiple very small steps. It might be a good idea to do this in a TimerTask.
		
		int x_distance = x2*LayoutController.ADAPT_PANEL_WIDTH - this.playerLabel.getX();
		int x_sign = x_distance!=0?x_distance/Math.abs(x_distance):1;
		int y_distance = y2*LayoutController.ADAPT_PANEL_HEIGTH -  this.playerLabel.getY();
		int y_sign = y_distance!=0?y_distance/Math.abs(y_distance):1;
		
		
		for(int i=0;i<=Math.abs(x_distance);i++)
			for(int j=0;j<=Math.abs(y_distance);j++)
			{
				int x_updated = x1*LayoutController.ADAPT_PANEL_WIDTH+i*x_sign;
				int y_updated = y1*LayoutController.ADAPT_PANEL_HEIGTH + j*y_sign;
				this.playerLabel.setLocation(x_updated, y_updated);
				this.gameArea.moveToFront(this.playerLabel);
				this.updatePanelOnOrientation(x1*LayoutController.ADAPT_PANEL_WIDTH, y1*LayoutController.ADAPT_PANEL_HEIGTH, x_updated, y_updated);
				stepCounter++;
				if(stepCounter % (LayoutController.ADAPT_PANEL_HEIGTH/4)==0)
					this.walk();
				try {
					Thread.sleep(5); //Sleep time might be adapted for better performance
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		//Make sure we are where we want to be, when immediate steps are wanted only use this.
		this.draw(x2, y2);
		
	}
	
	//Draws the player at the given position
	public void draw(int x, int y)
	{
		this.playerLabel.setLocation(x*LayoutController.ADAPT_PANEL_WIDTH, y*LayoutController.ADAPT_PANEL_HEIGTH);
		
	}
	
	//Explodes the player at the given position
	public void explode()
	{
		this.playerLabel.setIcon(new ImageIcon(ImageUtilities.applyWhiteTransparency(this.playerExplode)));
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.playerLabel.setVisible(false);
	}
	
	//Moves the player panel to the foreground.
	public void moveUp()
	{
		this.gameArea.moveToFront(this.playerLabel);
	}
	
	
}
