/**
 * 
 */
package upietz;
import static upietz.Constants.*;
import Alex.*; 
import axel.*;

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

		public int typ = UNDEFINED;

		public int belegt = EMPTY;
		@SuppressWarnings("unused")
		public boolean hasBomb = false;
	}
	
	/* Das Spielfeld */
	private Feld[][] board;
	/* Ein Array mit allen Startpositionen */
	private int[][] startPositionen;
	/* Spielfelddimensionen */
	private int width;
	private int height;
	/* Zugehöriges Gameplay */
	private Gameplay master;

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
	public Spielfeld( int dimHeight, int dimWidth, int[][] initialSetup, int startFelder, Gameplay master ) 
			throws Exception
	{
		/* Erstellen des eigentlichen Spielfeldes */
		try
		{
			this.board = createBoard(dimHeight, dimWidth, initialSetup, startFelder);
			this.width = dimWidth;
			this.height = dimHeight;
			this.master = master;
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
	 * Gegeben eine Figur-ID und eine Feldkoordinate checkt diese Methode ob
	 * ein Zug auf dieses Feld gültig ist. Wenn ja wird er ausgeführt und
	 * true zurückgegeben, falls nicht wird false zurückgegeben.
	 * Das Ausführen der Bewegung beinhaltet:
	 *   - Belegen des des neuen Feldes mit der Figur-ID
	 *   - Leeren des alten Feldes
	 * 
	 * @param 	int		id
	 * @param 	int[]	vonKoord
	 * @param	int[]	nachKoord
	 * @return	boolean
	 */
	public boolean moveFigur( int id, int[] vonKoord, int[] nachKoord )
	{
		// Ist die Zielkoordinate begehbar?
		if( validMove(nachKoord[X_KOORD], nachKoord[Y_KOORD]) )
		{
			// Wenn ja, setze Status der alten Positon auf nicht belegt
			this.board[vonKoord[X_KOORD]][vonKoord[Y_KOORD]].belegt = EMPTY;
			
			// Und setze das neue Feld auf belegt
			this.board[nachKoord[X_KOORD]][nachKoord[Y_KOORD]].belegt = id;
			
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
		if( this.board[x][y].typ == FLOOR && this.board[x][y].belegt > EMPTY)
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
	 * @param 	int		id
	 * @return 	int[]
	 */
	public int[] registerPlayer( int id)
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
		
		// Das Feld wird mit der Player-ID belegt
		this.board[x][y].belegt = id;
		
		returnPosition[X_KOORD] = x;
		returnPosition[Y_KOORD] = y;
		
		// Entfernen der gebrauchten Startposition
		// ArrayUtils.remove(this.startPositionen, 0);
		
		return returnPosition;
	}
	
	/**
	 * dropBomb
	 * 
	 * Öffentliche Methode zum Aufruf durch Bomben. An der übergebenen Position wird
	 * eine Bombe markiert. Liegt bereits eine Bombe auf dem Feld, gib false zurück,
	 * sonst true.
	 * 
	 * @param	int[]	position
	 * @return  boolean
	 */
	public boolean dropBomb( int[] position )
	{
		if( !this.board[position[X_KOORD]][position[Y_KOORD]].hasBomb )
		{
			this.board[position[X_KOORD]][position[Y_KOORD]].hasBomb = true;
			Draw.bomb(position);
			return true;
		}
		else
			return false;
	}
	

	/**
	 * explode
	 * 
	 * Öffentliche Methode zum Aufruf durch Bomben. An den gegebenen Koordinaten
	 * wird eine Bombe explodiert. Dazu werden die betroffenen Felder ermittelt und:
	 * - Diese werden Draw übergeben
	 * - Ist eins der Felder belegt, wird Player.die() aufgerufen
	 * - Alle Felder werden als leer gekennzeichnet
	 * 
	 * @param	int[]	position
	 * @param	int		radius
	 */
	public void explode( int[] position, int radius )
	{
		// Startkoordinaten des Radius ist radius Felder links oben der position oder 0
		int[] start = new int[2];
		start[X_KOORD] = position[X_KOORD] - radius;
		start[Y_KOORD] = position[Y_KOORD] - radius;
		
		// Negative Werte im Array gehen nicht, deswegen sicher stellen dass mindestens 0 steht
		if( start[X_KOORD] < 0 )
			start[X_KOORD] = 0;
		if( start[Y_KOORD] < 0 )
			start[Y_KOORD] = 0;
		
		// Zielkoordinaten entsprechend
		int[] ziel = new int[2];
		ziel[X_KOORD] = position[X_KOORD] + radius;
		ziel[Y_KOORD] = position[Y_KOORD] + radius;
		
		// Zu große Werte im Array gehen nicht, deswegen sicher stellen dass max size steht
		if( ziel[X_KOORD] >= this.width )
			ziel[X_KOORD] = this.width - 1;
		if( ziel[Y_KOORD] >= this.height )
			ziel[Y_KOORD] = this.height - 1;
		
		// Jetzt zeilenweise durch das Feld laufen
		for( int x = start[X_KOORD]; x <= ziel[X_KOORD]; x++ )
		{
			for( int y = start[Y_KOORD]; y <= ziel[Y_KOORD]; y++ )
			{
				// Nur FLOOR-Teile werden betrachtet
				if( this.board[x][y].typ == FLOOR )
				{
					// FLOOR-Teile explodieren lassen
					Draw.explodeTile(x,y);
					// Und die Bombe entfernen
					this.board[x][y].hasBomb = false;
					
					// Befindet sich eine Figur auf diesem Feld?
					if( this.board[x][y].belegt != EMPTY )
					{
						// Dann dem Master mitteilen, dass er tot ist.
						this.master.deregisterPlayer(this.board[x][y].belegt);
						
						// Feld als leer markieren
						this.board[x][y].belegt = EMPTY;
					}
				}
			}
		}
	}
}
