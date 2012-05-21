/**
 * 
 */
package upietz;
import static upietz.Constants.EXIT;
import static upietz.Constants.FLOOR;
import static upietz.Constants.SOLID_WALL;
import static upietz.Constants.UNDEFINED;
import static upietz.Constants.X_KOORD;
import static upietz.Constants.Y_KOORD;

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
	
	/* Das Spielfeld */
	private Feld[][] board;
	/* Ein Array mit allen Startpositionen */
	private int[][] startPositionen;

	/**
	 * Konstruktor
	 * 
	 * Initialisiert das Array mit dimHeight x dimWidth Spielfeldern. 
	 * Ist initialSetup NULL, wird eine Standardkarte generiert.
	 * 
	 * @param	int		dimHeight
	 * @param 	int		dimWidth
	 * @param	int[][]	initialSetup
	 * @param	int		startFelder
	 */
	public Spielfeld( int dimHeight, int dimWidth, int[][] initialSetup, int startFelder ) 
			throws Exception
	{
		/* Erstellen des eigentlichen Spielfeldes */
		try
		{
			this.board = createBoard(dimHeight, dimWidth, initialSetup, startFelder);
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
	private Feld[][] createBoard(int height, int width, int[][] initialSetup, int startFelder) throws Exception 
	{
		if( height < 4 || width < 4 )
			throw new Exception("Spielfeld ist zu klein!");
		
		/* initialSetup wird erstmal ignoriert und die Standardkarte generiert. */
		return createStandardMap(height, width, startFelder);
	}
	
	/**
	 * createStandardMap
	 * 
	 * Erstellt die Standard-Testkarte
	 */
	private Feld[][] createStandardMap(int height, int width, int startFelder)
	{
		Feld[][] feld = new Feld[height][width];
	
		/* Das Standardfeld hat abwechselnd eine Zeile nur mit FLOOR-Feldern und
		 * eine mit abwechselnd FLOOR und SOLID_WALL-Feldern. Start ist mit einer Leerzeile.
		 */
		int everyOdd, everyEven = FLOOR;
		
		for( int i = 0; i < height; i++)
		{
			/* Ist i%2 = 1 haben wir eine ungerade Zeile, also abwechselnd FLOOR und SOLID_WALL
			 * Ist i%2=0 gibt es nur FLOOR */
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
		
		/* Die Startpositionen sind ebenfalls fix und in der Standardkarte auf 4 beschränkt, jeweils in
		 * den Ecken des Feldes
		 */
		this.startPositionen = new int[4][2];
		this.startPositionen[0][X_KOORD] = 0;
		this.startPositionen[0][Y_KOORD] = 0;
		this.startPositionen[1][X_KOORD] = (width-1);
		this.startPositionen[1][Y_KOORD] = 0;
		this.startPositionen[2][X_KOORD] = (height-1);
		this.startPositionen[2][Y_KOORD] = 0;
		this.startPositionen[3][X_KOORD] = (height-1);
		this.startPositionen[3][Y_KOORD] = (width-1);
		
		return feld;
	}
	
	/**
	 * moveFigur
	 * 
	 * Gegeben eine Figur-Nr und eine Feldkoordinate checkt diese Methode ob
	 * ein Zug auf dieses Feld gültig ist. Wenn ja wird er ausgeführt und
	 * true zurückgegeben, falls nicht wird false zurückgegeben.
	 * Das Ausführen der Bewegung beinhaltet:
	 *   - Belegen des des neuen Feldes mit der Figur-ID
	 *   - Leeren des alten Feldes
	 * 
	 * @param 	int		figurNr
	 * @param 	int		vonKoordX
	 * @param	int		vonKoordY
	 * @param	int		nachKoordX
	 * @param 	int 	nachKoordY
	 * @return	boolean
	 */
	public boolean moveFigur( int figurNr, int vonKoordX, int vonKoordY, int nachKoordX, int nachKoordY )
	{
		// Ist die Zielkoordinate begehbar?
		if( validMove(nachKoordX, nachKoordY) )
		{
			// Wenn ja, setze Status der alten Positon auf nicht belegt
			this.board[vonKoordX][vonKoordY].belegt = false;
			
			// Und setze das neue Feld auf belegt
			this.board[nachKoordX][nachKoordY].belegt = true;
			
			// Erfolgreicher Zug, true zurück
			return true;
		}
		else
			// Ist es SOLID_WALL oder belegt, kein gültiger Zug
			return false;
	}
	
	/**
	 * validMove
	 * 
	 * Gegeben zwei Koordinaten, liefere true zurück wenn das Feld vom Typ FLOOR und nicht belegt ist
	 * 
	 * @param	int		x
	 * @param	int		y
	 * @return	boolean
	 */
	private boolean validMove( int x, int y)
	{
		if( this.board[x][y].typ == FLOOR && !this.board[x][y].belegt )
			return true;
		else
			return false;
	}
	
	/**
	 * registerPlayer
	 * 
	 * Öffentliche Methode zum Aufruf durch Figuren. Wenn ein Spieler dem Spiel beitritt, ruft er
	 * diese Methode auf. Ihm wird eine Startposition aus dem Pool zugewiesen und diese als belegt
	 * markiert.
	 * 
	 * @return int[]
	 */
	public int[] registerPlayer()
	{
		/* ToDo
		 * - was passiert, wenn alle Positionen aufgebraucht sind?
		 * - wenn die Startpositionen gelöscht werden, was passiert bei einem restart?
		 */
		int x,y;
		int[] returnPosition = new int[2];
		
		// Es wird immer die erste Position genommen
		x = this.startPositionen[0][X_KOORD];
		y = this.startPositionen[0][Y_KOORD];
		
		this.board[x][y].belegt = true;
		returnPosition[X_KOORD] = x;
		returnPosition[Y_KOORD] = y;
		
		// Entfernen der gebrauchten Startposition
		// ArrayUtils.remove(this.startPositionen, 0);
		
		return returnPosition;
	}
	
	/**
	 * dropBomb
	 * 
	 * Öffentliche Methode zum Aufruf durch Figuren.
	 */
	

	/**
	 * explode
	 * 
	 * Öffentliche Methode zum Aufruf durch Bomben.
	 */
}
