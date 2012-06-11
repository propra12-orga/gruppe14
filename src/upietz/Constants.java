/**
 * 
 */
package upietz;

/**
 * @author upietz
 *
 * Eine Sammlung von Konstanten, die im Package upietz benutzt werden.
 */
public final class Constants {
	/* Konstanten für Feldtypen */
	public static final int UNDEFINED = -1;
	public static final int EXIT = 0;
	public static final int SOLID_WALL = 1;
	public static final int FLOOR = 2;
	public static final int BREAKABLE_WALL = 3;	
	public static final int EMPTY = -1;
	
	/* Hilfskonstanten */
	public static final int X_KOORD = 0;
	public static final int Y_KOORD = 1;
	
	
	/* Wir fügen einen dummy-Konstruktor ein, damit diese Klasse nicht instantiiert wird.
	 * s. http://www.javapractices.com/topic/TopicAction.do?Id=2
	 */
	private Constants()
	{
		throw new AssertionError();
	}
}
