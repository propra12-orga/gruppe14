package axel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 *   ImageUtilities 
 * 
 *        Class for preparing images such as applying transparency or resizing 
 *        
 *        
 */

public class ImageUtilities {
	
	/**
	 *   Image applyTransparencyToColor
	 * 
	 *        Applies transparency to a given color within the image
	 *        
	 *        @param BufferedImage img
	 *        @param final Color col	
	 *        return img 
	 */
	
	
	private static Image applyTransparencyToColor(BufferedImage img, final Color col)
	{
	    ImageFilter filter = new RGBImageFilter() {

	        // the color we are looking for... Alpha bits are set to opaque
	        public int markerRGB = col.getRGB() | 0xFF000000;

	        public final int filterRGB(int x, int y, int rgb) {
	            if ((rgb | 0xFF000000) == markerRGB) {
	                // Mark the alpha bits as zero - transparent
	                return 0x00FFFFFF & rgb;
	            } else {
	                // nothing to do
	                return rgb;
	            }
	        }
	    };
	    ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}
	/**
	 *   BufferedImage imageToBufferedImage
	 * 
	 *        Converts an object of type Image to BufferedImage
	 *        
	 *        @param Image image
	 *   
	 *        @param int width	 
	 *		  @param int height	
	 *		  return BufferedImage
	 */		  
	
	private static BufferedImage imageToBufferedImage(Image image, int width, int height)
	{
		BufferedImage dest = new BufferedImage(
	    width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = dest.createGraphics();
	    g2.drawImage(image, 0, 0, null);
	    g2.dispose();
	    return dest;
	}
	
	/**
	 *   BufferedImage applyWhiteTransparency
	 * 
	 *        Applies white transparency to a given BufferedImage (needed for explosions)
	 *        
	 *        @param BufferedImage image
	 *        return Bufferedimage
	 *   
	 *      
	 */		  
	
	public static BufferedImage applyWhiteTransparency(BufferedImage image)
	{
		return imageToBufferedImage(applyTransparencyToColor(image, Color.WHITE), image.getWidth(), image.getHeight());
	}
	/**
	 *   BufferedImage resize
	 * 
	 *        Resizes a given BufferedImage object to the given dimensions
	 *        
	 *        @param int width
	 *        @param int height
	 *        @param BufferedImage img
	 *        return BufferedImage
	 *   
	 *      
	 */		  
	
	public static BufferedImage resize(int width, int height, BufferedImage img)
	{
		return imageToBufferedImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH),width,height);
	}

}
