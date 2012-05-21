package upietz;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] position;
		try
		{
			Spielfeld feld = new Spielfeld( 10, 10, null, 4 );
			
			position = feld.registerPlayer();
			
			System.out.println("X: " + position[0] + ", Y: " + position[1]);
		}
		catch( Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
