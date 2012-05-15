package Alex;
/**
 * Testweise erzeugte Klasse Figur
 * @author Volo
 *
 */
public class figure {

	/**
	 * @param args
	 */
	public static void move(String s){
		System.out.println("move: " + s);
	}
	public static void pause() {
		// TODO Auto-generated method stub
		System.out.println("Pause!");
	}
	public static void bomb() {
		// TODO Auto-generated method stub
		System.out.println("Bombe!");
	}
	public static int position(String string) {
		// TODO Auto-generated method stub
		int x = 1;
		int y = 2;
		int tmp = 0;
		
		if(string == "x"){
			tmp = x;
		}
		if(string == "y"){
			tmp = y;
		}
		return tmp;
	}
}
