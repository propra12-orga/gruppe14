package axel;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Used to display temporary objects like bombs ore explosions on the field. Might either be deleted directly or after a given timeout is reached.

public class TempOverlay extends JLabel {

	private static final long serialVersionUID = 2202931409330827225L;
	//We save virtual coordinates because eventually we want to keep track of all temporary labels created on the field.
	
	int x_virt = 0;
	int y_virt = 0;
	
	
	//Creates a new temporary Overlay with a given virtual location and an image to display
	public TempOverlay(int x_virt, int y_virt, BufferedImage img)
	{
		super();
		
		this.x_virt = x_virt;
		this.y_virt = y_virt;
		
		this.setVisible(true);
		this.setSize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH);
		this.setLocation(x_virt *LayoutController.ADAPT_PANEL_WIDTH, y_virt*LayoutController.ADAPT_PANEL_HEIGTH);
		this.setIcon(new ImageIcon(img));
	}
	
	//TimerTask for asynchronous deletion
	private class DeletionTask extends TimerTask
	{
		private TempOverlay tempOv = null;
		
		public DeletionTask(TempOverlay tempOv)
		{
			this.tempOv = tempOv;
		}
		
		public void run() {
			this.tempOv.delete();
		}
	}
	
	//Schedules the deletion of this objects in ms from now
	public void deleteAfter(int ms)
	{
		Timer timer = new Timer();
		
		timer.schedule(new DeletionTask(this), ms);
	}
	//Deletes the object now.
	public void delete()
	{
		this.setVisible(false);
		this.removeAll();
	}
}