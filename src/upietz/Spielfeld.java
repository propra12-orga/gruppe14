/**
 * 
 */
package upietz;
import static upietz.Constants.*;

/**
 * @author Stefan Upietz
 * 
 * Spielfeld
 * 
 * Die Klasse Spielfeld ist die Verwaltung eines Arrays, das die Informationen über die
 * Karte des Spiels enthält. Für jedes Feld wird gespeichert, was sich in ihm befindet
 * (Figur, Bombe, Item).
 *
 */
public class Spielfeld {
	/**
	 * Klasse Feld
	 * 
	 * Ersatz für ein struct Feld. Hier werden pro Feld Informationen zu Feldtyp und
	 * aktueller Belegung gespeichert.
	 */
	private class Feld {
		@SuppressWarnings("unused")
		public int typ = UNDEFINED;
		@SuppressWarnings("unused")
		public boolean belegt = false;
		@SuppressWarnings("unused")
		public boolean hasBomb = false;
	}
	
	private Feld[][] board;

	/**
	 * Konstruktor
	 * 
	 * Initialisiert das Array mit dimHeight x dimWidth Spielfeldern. 
	 * Ist initialSetup NULL, wird eine Standardkarte generiert.
	 * 
	 * @param	int		dimHeight
	 * @param 	int		dimWidth
	 * @param	int[]	initialSetup
	 */
	public Spielfeld( int dimHeight, int dimWidth, int[][] initialSetup ) throws Exception
	{
		/* Erstellen des eigentlichen Spielfeldes */
		try
		{
			this.board = createBoard(dimHeight, dimWidth, initialSetup);
		}
		catch( Exception e)
		{
			throw new Exception(e.getMessage());
		}
		
		
	}
	
	/**
	 * createBoard
	 * 
	 * Mit den übergebenen Daten wird ein Array aus Feldern erstellt.
	 * ToDo: Bis jetzt kann nur eine Standardkarte erstellt werden.
	 * 
	 * @param	int		height
	 * @param	int 	width
	 * @param	int[][] initialSetup
	 * @return  feld[][]
	 */
	private Feld[][] createBoard(int height, int width, int[][] initialSetup) throws Exception 
	{
		if( height < 4 || width < 4 )
			throw new Exception("Spielfeld ist zu klein!");
		
		/* initialSetup wird erstmal ignoriert und die Standardkarte generiert. */
		return createStandardMap(height, width);
	}
	
	/**
	 * createStandardMap
	 * 
	 * Erstellt die Standard-Testkarte
	 */
	private Feld[][] createStandardMap(int height, int width)
	{
		Feld[][] feld = new Feld[height][width];
	
		/* Das Standardfeld hat abwechselnd eine Zeile nur mit FLOOR-Feldern und
		 * eine mit abwechselnd FLOOR und SOLID_WALL-Feldern. Start ist mit einer Leerzeile.
		 */
		int everyOdd, everyEven = FLOOR;
		
		for( int i = 0; i < height; i++)
		{
			/* Ist i%2 = 1, haben wir eine ungerade Zeile, also abwechselnd. Ist i%2=0
			 * gibt es nur FLOOR */
			everyOdd = (i % 2 > 0)?SOLID_WALL:FLOOR;
			
			for( int j = 0; j < width; j++)
			{
				// Initialize object reference
				feld[i][j] = new Feld();
				feld[i][j].typ = (j % 2 > 0 )?everyOdd:everyEven;
			}
		}
		
		/* Für das temporäre Feld noch einen Ausgang finden */
		java.util.Random zufall = new java.util.Random();
		int exit_w = zufall.nextInt(width);
		int exit_h = zufall.nextInt(height);
		feld[exit_w][exit_h].typ = EXIT;
				
		return feld;
	}
}
