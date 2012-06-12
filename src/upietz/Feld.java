package upietz;

import static upietz.Constants.*;

/**
 * Klasse Feld
 * 
 * Ersatz für ein struct Feld. Hier werden pro Feld Informationen zu
 * Feldtyp und aktueller Belegung gespeichert.
 */
public class Feld {

	public int typ = UNDEFINED;

	public int belegt = EMPTY;

	public boolean hasBomb = false;
	public boolean isExit = false;
}
