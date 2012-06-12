package upietz;

/* Bomberman-Konstanten */
import static upietz.Constants.*;

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
		
		private Hashtable tags;
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
	        tags = new Hashtable();
	    }
	    
	    public void endDocument() throws SAXException
	    {
	        Enumeration e = tags.keys();
	        while (e.hasMoreElements())
	        {
	            String tag = (String)e.nextElement();
	            int count = ((Integer)tags.get(tag)).intValue();
	            System.out.println("Local Name \"" + tag + "\" occurs " + count
	                               + " times");
	        }
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
}
