package axel;

import java.util.*;
import upietz.*;
import static upietz.Constants.*;

public class Draw {
     public void drawBoard(Spielfeld.Feld[][] board)
     {
    	 System.out.println("Feld malen");
     }
     
     public void drawBomb( int[] position)
     {
    	 System.out.println("Bombe malen an " + position[X_KOORD] + "," + position[Y_KOORD]);
     }
     
     public void explodeTile(int x, int y)
     {
    	 System.out.println("Tile explodieren an " + x + "," + y);
     }
     
     public void movePlayer(int id, int[] vonKoord, int[] nachKoord)
     {
    	 System.out.println("Bewege Player " + id + " von "
    			 + vonKoord[X_KOORD] + "," + vonKoord[Y_KOORD]
    			 + " nach " + nachKoord[X_KOORD] + "," + nachKoord[Y_KOORD]
    			 );
     }
     
     public void drawPlayer( int id, int[] position)
     {
    	 System.out.println("Male Player " + id + " an Position " + position[X_KOORD] + "," + position[Y_KOORD]);
     }
     
     public void explodePlayer( int id, int[] position)
     {
    	 System.out.println("Player " + id + " explodiert an " + position[X_KOORD] + "," + position[Y_KOORD]);
     }
}
