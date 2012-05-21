    package axel;
    import java.util.*;
    import anika.Player;
    import anika.Bomb;
    import anika.Explosion;
     
    public class Draw {
     
     
            public void drawPlayer () {
                     Player player; // muss auf den Player der Gameplaymethode gesetzt werden
                     int x = player.getX ();
                     int y = player.getY ();
                     StdDraw.setPenRadius (1);
                     StdDraw.setPenColor(); // PenColor ist schwarz!
                     StdDraw.circle(x, y, 0.5);
     
     
     
     
            }
             public void drawExplosion () {
             Explosion e; 
             int x;
             int y;
             for (int i = 0; i<explosion.size (); i++) { //linked list von alex..
             x = e.getX ();
             y = e.getY ();
             StdDraw.setPenColor (StdDraw.MAGENTA);
             StdDraw.circle (x,y,0.3);  }
             }
           
           
            public void drawBomb () {
                    Bomb bomb;
                    int x;
                    int y;
                     for (int i = 0; i<bomb.size (); i++) {  //linked list von alex fehlt..
                            bomb = bombs.get(i); // auch hier fehlt die linked list
                            x = bomb.getX ();
                            y = bomb.getY ();
                            StdDraw.setPenColor(StdDraw.CYAN);
                            StdDraw.circle(x, y, 0.3);
                     }
     
                    }
           
            public void drawField () {
                    /* int x;
                     * int y;
                     * Feld feld;
                     * for (int i = 0; feld.size(); i++) {
                     *      for (int j = 0; feld.size(); j++) {
                     * feld = Spielfeld.feld [i][j];
                     * switch (feld.typ) {
                       case 0: StdDraw.setColor (StdDraw.GREEN);
                       StdDraw.Rectangle (x,y,0.5,0.5);
                       break;
                     * case 1: StdDraw.setColor(StdDraw.RED);
                     * StdDraw.Rectangle (x,y,0.5,0.5);
                     * break;
                     * case 2: StdDraw.setColor(StdDraw.YELLOW);
                     * StdDraw.Rectangle (x,y,0.5,0.5);
                     * break;  }
                     * }}
                     */
     
     
            }
     
    }
