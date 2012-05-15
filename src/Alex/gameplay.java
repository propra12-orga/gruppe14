package Alex;

import java.io.*;
//import anika.*;
//import upietz.*;
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
		int code = 0;
		if(input.equals("left")){
			code = 1;
			System.out.println("code = " + code);
		}
		else if(input.equals("right")) {
			code = 2;
			System.out.println("code = " + code);
		}
		else if(input.equals("up")) {
			code = 3;
			System.out.println("code = " + code);
		}
		else if(input.equals("down")) {
			code = 4;
		}
		else if(input.equals("space")) {
			code = 5;
		}
		else if(input.equals("p")) {
			code = 6;
		}
		else if(input.equals("ESC")) {
			code = 7;
		}
		/**
		 * Interpretieren der Befehle mit Abfrage über movement() mithilfe von Spielfeld, ob Bewegung erlaubt ist
		 */
		switch(code){
			case(1):
				if(movement(code) == true){
					figure.move("left");
					break;
				}
			case(2):
				if(movement(code)==true){
					figure.move("right");
					break;
				}
			case(3):
				if(movement(code)==true){
					figure.move("up");
					break;
				}
			case(4):
				if(movement(code)==true){
					figure.move("down");
					break;
				}
			
			case(5):
				figure.bomb();
				break;
				
			case(6):
				//Pause --> an Spielfeld / Darstellung senden? eine Programmweite Pause
				figure.pause();
				//bomb.pause();
				break;
			
			case(7):
				//ESC
				figure.pause();
				//bomb.pause();
				break;
			
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
		int positionx = figure.position("x");//x Position der Figur
		int positiony = figure.position("y");//y Position der Figur
		boolean move = true;
		//return move = spielfeld.feld(positionx, positiony, code);//Übergabe von x y Koordinaten und die gewünschte Bewegungsrichtung
		return move;
	}
}