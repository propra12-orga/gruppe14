package anika;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * MapFileFilter
 * 
 * Filter for a JFileChooser to display only .map-files.
 * Basically taken from:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemo2Project/src/components/ImageFilter.java
 * 
 * @author upietz
 *
 */
public class MapFileFilter extends FileFilter {

	/**
	 * accept
	 * 
	 * Accept all directories and .map files
	 * 
	 * @param	File	file
	 * @return	boolean
	 */
	@Override
	public boolean accept(File file) {
		if( file.isDirectory() )
			return true;
		
		if( file.canRead() )
			return true;
		
		if( file.getName().endsWith(".map") )
			return true;
		
		return false;
		
	}

	/**
	 * getDescription
	 * 
	 * Describes this filter.
	 * 
	 * @return	String
	 */
	@Override
	public String getDescription() {
		return "Show only .mapfiles"; 
	}

}
