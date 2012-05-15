package Alex;

import java.io.*;
/**
 * Gameplay, verantowrtlich für die Kommunikation zwischen Figur, Spielfled, Darstellung und Steuerung
 * @author Volo
 *
 */
public class gameplay {

	/**
	 * @param args
	 * Figur
	 * Spielfeld
	 * Steuerung
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String eingabe = null;
		try {
			System.out.print("Ihre Eingabe: ");
	         eingabe = br.readLine();
	     } catch (IOException ioe) {
	         System.out.println("IO error trying to read your code!");
	         System.exit(1);
	     }
		System.out.println(eingabe);
		controls(eingabe);
	}
	/**
	 * left = 1
	 * right = 2
	 * up = 3
	 * down = 4
	 * p = 5
	 * ESC = 6
	 * Einlesen der Befehle, die von der Steuerung kommen
	 * @param input
	 */
	public static void controls(String input){
		int code = 1;
		
		System.out.println(input + " in controls");
		
		if(input == "left") {
			code = 1;
		}
		if(input == "right") {
			code = 2;
		}
		if(input == "up") {
			code = 3;
		}
		if(input == "down") {
			code = 4;
		}
		if(input == "space") {
			code = 5;
		}
		if(input == "p") {
			code = 6;
		}
		if(input == "ESC") {
			code = 7;
		}
		
		System.out.println("code: " + code);
		/**
		 * Interpretieren der Befehle mit Abfrage über movement() mithilfe von Spielfeld, ob Bewegung erlaubt ist
		 */
		switch(code){
			case(1):
				if(movement(code))figure.move("left");
			case(2):
				if(movement(code))figure.move("right");
			case(3):
				if(movement(code))figure.move("up");
			case(4):
				if(movement(code))figure.move("down");
			
			case(5):
				figure.bomb();
				
			case(6):
				//Pause --> an Spielfeld / Darstellung senden? eine Programmweite Pause
				figure.pause();
				//bomb.pause();
			
			case(7):
				//ESC
				figure.pause();
				//bomb.pause();
			
			default:
				System.out.println("keine gültige Eingabe(controls)");
		}
	}
	
	/**
	 * Abfrage, ob sich die Figur in das entsprechende Feld bewegen kann.
	 * @param move
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean movement(int code){
		int positionx = figure.position("x");//x wert der Figur
		int positiony = figure.position("y");//y wert der Figur
		boolean move = false;
		//return move = spielfeld.feld(positionx, positiony, code);//Übergabe von x y Koordinaten und die gewünschte Bewegungsrichtung
		return move;
	}
}