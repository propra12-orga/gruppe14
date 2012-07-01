package upietz;

import static upietz.Constants.*;

/**
 * Class Feld
 * 
 * Replacement for a struct Feld. Saves information of field type and current
 * occupancy per field.
 */
public class Feld {

	public int typ = UNDEFINED;

	public int belegt = EMPTY;

	public boolean hasBomb = false;
	public boolean isExit = false;
}
