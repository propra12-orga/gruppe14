/**
 * 
 */
package upietz;

/**
 * @author upietz
 *
 * Collection of constants which are used in package upietz
 */
public final class Constants {
	/* constants for field types */
	public static final int UNDEFINED = -1;
	public static final int EXIT = 0;
	public static final int SOLID_WALL = 1;
	public static final int FLOOR = 2;
	public static final int BREAKABLE_WALL = 3;	
	public static final int EMPTY = -1;
	public static final int START = 4;
	public static final int EXIT_HIDDEN = 5;
	
	/* helping constants */
	public static final int X_KOORD = 0;
	public static final int Y_KOORD = 1;
	
	
	/* Inserting a dummy-constructor in order to prevent the process of instantiating this class.
	 * s. http://www.javapractices.com/topic/TopicAction.do?Id=2
	 */
	private Constants()
	{
		throw new AssertionError();
	}
}
