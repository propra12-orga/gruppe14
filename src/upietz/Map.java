package upietz;

/* Bomberman-Konstanten */
import static upietz.Constants.*;
import upietz.Feld;

/* XML-Libs */
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/* Weitere Java-Libs */
import java.util.*;
import java.io.*;

/**
 * Map
 * 
 * Liefert über die einzig öffentliche Methode ein zweidimensionales Array,
 * das das Spielfeld zu Bomberman darstellt. Wird kein Dateiname übergeben,
 * wird ein Standardfeld generiert, ansonsten wird versucht aus dem übergebenen
 * String einen Dateinamen zu generieren und XML-Daten umzuwandeln.
 * 
 * @author upietz
 *
 */
public class Map {
	/**
	 * Diese Klasse wurde grundlegend übernommen von
	 * http://java.sun.com/webservices/reference/tutorials/jaxp/html/sax.html
	 * 
	 * Klasse, die aus einer Datei eine Map einliest
	 * 
	 */
	public class SAX extends DefaultHandler {
		
		private Feld[][] spielfeld;
		private SAXParser saxParser;
		
		/**
		 * Konstruktor
		 * 
		 * Erzeugen aller nötigen SAX-klassen.
		 * 
		 * @param filename 
		 * @throws Exception
		 */
	    public SAX() throws Exception {
	        /* Initialisieren des SAX-Parsings */
	        SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setNamespaceAware(false);
	        this.saxParser = spf.newSAXParser();
	    }
	    
	    /**
	     * init
	     * 
	     * Übergibt den Dateinamen und startet das parsen
	     * 
	     * @param	String 	filename
	     * @return  Feld[][]
	     * @throws 	Exception
	     */
	    public void init(String filename) throws Exception
	    {
	        XMLReader xmlReader = this.saxParser.getXMLReader();
	        xmlReader.setContentHandler(new SAX());
	        xmlReader.parse(convertToFileURL(filename));
		}
		
		private String convertToFileURL(String filename)
		{
	        String path = new File(filename).getAbsolutePath();
	        if (File.separatorChar != '/') {
	            path = path.replace(File.separatorChar, '/');
	        }
	        if (!path.startsWith("/")) {
	            path = "/" + path;
	        }
	        return "file:" + path;
	    }		

	    public void startDocument() throws SAXException
	    {
	    	/* Dummy */
	    }
	    
	    public void endDocument() throws SAXException
	    {
	    	/* Dummy */
	    }
	    
	    public void startElement(String namespaceURI, String localName,
	            String qName, Attributes atts)
	            throws SAXException
	    {
	    	String key = localName;
	    	int attrCount = atts.getLength();
	    	
	    	for( int i = 0; i < attrCount; i++ )
	    	{
	    		String attrName = atts.getLocalName(i);
	    		
	    		try
	    		{
	    			int attrValue = Integer.parseInt(atts.getValue(i));
	    		}
	    		catch( NumberFormatException e ) 
	    		{
	    			System.out.println("Ungültiger Wert");
	    			System.exit(1);
	    		}
	    	}
	    }	    
	}
	
	/* Hier beginnt die eigentliche Klasse Map */
	private SAX configReader;
	
	/**
	 * Konstruktor
	 * 
	 * Dummy.
	 */
	public Map() 
	{
		
	}
	
	/**
	 * readFile
	 * 
	 * Ein Dateiname wird übergeben und der Parser initiiert
	 * ToDo: Exception sollte besser abgefangen werden!
	 * 
	 * @param	String 		filename
	 * @return  Feld[][]	Spielfeld
	 */
	public Feld[][] readFile( String filename )
	{
		try
		{
			this.configReader = new SAX();
			this.configReader.init(filename);
		}
		catch( Exception e )
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		return this.configReader.spielfeld;
	}
}
