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
		System.out.println("figure.move: " + s);
	}
	public static void pause() {
		System.out.println("figure.Pause!");
	}
	public static void bomb() {
		System.out.println("figure.Bombe!");
	}
	public static int position(String string) {
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
