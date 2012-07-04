package upietz;

/* Bomberman-Konstanten */
import static upietz.Constants.*;
import upietz.Feld;

/* Weitere Java-Libs */
import java.io.*;

/**
 * Map
 * 
 * Liefert über die einzig öffentliche Methode ein zweidimensionales Array,
 * das das Spielfeld zu Bomberman darstellt. Wird kein Dateiname übergeben,
 * wird ein Standardfeld generiert, ansonsten wird versucht aus dem übergebenen
 * String einen Dateinamen zu generieren und die Datei auszulesen.
 * 
 * Das Format einer Map-Datei ist einfacher Text, bestehend aus Zahlen. Die Zahlen
 * entsprechen den Konstanten aus upietz.Constants und deren Zuweisung zu 
 * verschiedenen Feldtypen. 
 *  
 * @author upietz
 *
 */
public class Map
{
	/* Instanzvariablen */
	// Stream to read the file from
	//private BufferedReader fileBuffer;
	private File mapFile;
	// Map to deliver
	private Feld[][] map;
	// Dimensionsof the map
	private int height, width;
	// There are max 4 Starting positions with two coordinates each
	private int[][] startPos = new int[4][2];
	// This is used to keep trackof the delivered starting positions
	private int startPosCount = 0;
	private int startPosDelivered = 0;
	
	/**
	 * Map
	 * 
	 * Constructor. Takes a String filename as argument. The file is checked
	 * for existence and readability and then passed further to be parsed.
	 * 
	 * @param	String		
	 * @throws IOException
	 */
	public Map(String filename)
	{
		try{
		// Instantiate File object
		this.mapFile = new File(filename);
		
		//Run some sanity tests on the file
		if( !mapFile.exists() )
			throw new IOException();
		
		if( !mapFile.canRead() )
			throw new IOException();
		
		// Create a stream to parse from later
		//this.fileBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(mapFile)));
		//this.in = new FileInputStream(mapFile);
        //this.reader = new InputStreamReader(in);
        // buffer for efficiency
        //this.fileBuffer = new BufferedReader(reader);
        BufferedReader fileBuffer = new BufferedReader(new FileReader(mapFile));
        
		// Pass on to parsing
		//try
		//{
			parseMap();
			fileBuffer.close();
			//in.close();
			//reader.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		

	}
	
	/**
	 * getMap
	 * 
	 * Delivers the map.
	 * 
	 * @return	Field[][]
	 */
	public Feld[][] getMap()
	{
		return this.map;
	}
	
	/**
	 * getHeight
	 * 
	 * Deliver the height
	 * 
	 * @return	int
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * getWidth
	 * 
	 * Deliver the width
	 * 
	 * @return	int
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * getNextStartPos
	 * 
	 * delivers the next starting position or null if all were delivered
	 * 
	 * return	int[]
	 */
	public int[] getNextStartPos()
	{
		if( this.startPosDelivered < 4 && this.startPosCount > 0 )
			return this.startPos[this.startPosDelivered++];
		else
			return null;
	}
	
	/**
	 * parseMap
	 * 
	 * Walk through the file char by char and fill an array of Field.
	 * @throws IOException 
	 * @throws InterruptedException 
	 *  
	 */
	private void parseMap() throws IOException
	{
		// Indexes to fill the array
		int x, maxX, maxY = 0;
		
		// Since we'll be filling an array, determine the dimensions
		maxX = getXfromFile();
		maxY = getYfromFile();
		
		this.map = new Feld[maxX][maxY];
		
		// Now that we have the dimensions, parse the file according
		// to those.
		
		// we know there are maxYlines
		String line;
		//for( y = 0; y < maxY; y++ )
		int y = 0;
		BufferedReader fileBuffer = new BufferedReader(new FileReader(this.mapFile));
		
		while( (line = fileBuffer.readLine()) != null )
		{
			// get line or stop at EOF
			//if( (line = this.fileBuffer.readLine()) == null )
				//break;
			//line = this.fileBuffer.readLine();
			
			// make sure it is of the right length
			line = checkLine(line, maxX);
			//now read char by char and assign to field
			for( x = 0; x < maxX; x++ )
			{
				// TODO we should make sure type is valid
				int type = Integer.parseInt(line.substring(x,x+1));
				this.map[x][y] = new Feld();
				this.map[x][y].typ = type;
				
				// recognizespecial field types
				if( type == EXIT )
				{
					// Exit needs to be FLOOR
					this.map[x][y].typ = FLOOR;
					this.map[x][y].isExit = true;
				}
				else
					if( type == EXIT_HIDDEN )
					{
						// Exit needs to be breakable
						this.map[x][y].typ = BREAKABLE_WALL;
						this.map[x][y].isExit = true;
					}
					else
						if( type == START && startPosCount < 4 )
						{
							// Starting position needs to be FLOOR
							this.map[x][y].typ = FLOOR;
							this.startPos[this.startPosCount][X_KOORD] = x;
							this.startPos[this.startPosCount++][Y_KOORD] = y;
						}
			}
			y++;
		}
		
	}
	
	/**
	 * checkLine
	 * 
	 * Given an int as length, check if the line consists of exactly that many characters.
	 * If not, either pad with FLOOR or trim.
	 * 
	 * @param	String		line
	 * @param	int			length
	 * @return 	String
	 */
	private String checkLine(String line, int length)
	{
		// If it is of the right length, return immediately
		if( line == null)
		{
			System.out.println("line is null!");
			System.exit(1);
		}
		
		if( line.length() == length )
			return line;
		else
			// pad with FLOOR until length
			if( line.length() < length )
			{
				char[] padded = new char[length];
				for( int i = 0; i < length; i++ )
					padded[i] = Character.forDigit(FLOOR,10);
				
				line.getChars(0, line.length(), padded, 0);
				return String.valueOf(padded);
			}
			else
				// Cut to length
				//if( line.length() > length )
					return line.substring(0, length );
	}
	
	/**
	 * getXfromFile
	 * 
	 * Count the elements in the first line. This will determine the width of the map.
	 * 
	 * @return	int
	 * @throwInputStream in = new FileInputStream(file);
             Reader reader = new InputStreamReader(in, encoding);
             // buffer for efficiency
             Reader buffer = new BufferedReader(reader))s IOException 
	 */
	private int getXfromFile() throws IOException
	{
		int c, x = 0;
		BufferedReader fileBuffer = new BufferedReader(new FileReader(this.mapFile));
		// Read until a newline
		while( (c = fileBuffer.read()) != '\n')
		{
			x++;
		}
		
		this.width = x;
		return x;
	}
	
	/**
	 * getYfromFile
	 * 
	 * Count the lines in the file. This will determine the width of the map.
	 * 
	 * @return	int
	 * @throws IOException 
	 */
	private int getYfromFile() throws IOException
	{
		String line;
		int y = 0;
		BufferedReader fileBuffer = new BufferedReader(new FileReader(this.mapFile));
		// Read until EOF
		while( (line = fileBuffer.readLine()) != null)
		{
			y++;
		}
		
		this.height = y;
		return y;
	}
}
