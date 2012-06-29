package anika;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SaveGameFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		if (f.getName().endsWith(".bsf"))
			return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "Spielstände";
	}

}
