[33mcommit 4801ccfd9bdd9322e9ae2dd6a9da282bf496b9cc[m
Author: Axel Honka <axel.honka@uni-duesseldorf.de>
Date:   Mon Jun 25 17:30:12 2012 +0200

    Ausgang wird jetzt auch gezeichnet

[1mdiff --git a/src/Alex/Gameplay.java b/src/Alex/Gameplay.java[m
[1mindex 7730090..ed1dc9f 100644[m
[1m--- a/src/Alex/Gameplay.java[m
[1m+++ b/src/Alex/Gameplay.java[m
[36m@@ -121,7 +121,7 @@[m [mpublic class Gameplay {[m
 	 */[m
 	public void gameWon(int id) {[m
 		// ?[m
[31m-		control.print("And the winner is: Player " + id);[m
[32m+[m		[32mSystem.out.println("And the winner is: Player " + id);[m
 		gameOver();[m
 		//System.exit(0);[m
 	}[m
[1mdiff --git a/src/axel/LayoutController.java b/src/axel/LayoutController.java[m
[1mindex 15e1d03..b79662c 100644[m
[1m--- a/src/axel/LayoutController.java[m
[1m+++ b/src/axel/LayoutController.java[m
[36m@@ -1,5 +1,7 @@[m
 package axel;[m
 [m
[32m+[m[41m[m
[32m+[m[41m[m
 import java.awt.image.BufferedImage;[m
 import java.io.File;[m
 import java.io.IOException;[m
[36m@@ -17,7 +19,7 @@[m [mimport upietz.Feld;[m
 [m
 public class LayoutController {[m
 [m
[31m-	private JLabel[][] imageField = null;[m
[32m+[m	[32mprivate JLabel[][] staticField = null;[m[41m[m
 	[m
 	private JLabel[][] envField = null;[m
 	[m
[36m@@ -67,7 +69,7 @@[m [mpublic class LayoutController {[m
 	[m
 	private void init(int heigth, int width)[m
 	{[m
[31m-		this.imageField = new JLabel[heigth][width];[m
[32m+[m		[32mthis.staticField = new JLabel[heigth][width];[m[41m[m
 		this.envField = new JLabel[heigth][width];[m
 		this.heigth = heigth;[m
 		this.width = width;[m
[36m@@ -89,12 +91,12 @@[m [mpublic class LayoutController {[m
 		for(int i=0;i< this.heigth; i++)[m
 			for(int j=0; j< this.width;j++)[m
 			{[m
[31m-				this.imageField[i][j] = new JLabel();[m
[31m-				this.imageField[i][j].setSize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH);[m
[31m-				this.imageField[i][j].setLocation(i*LayoutController.ADAPT_PANEL_WIDTH, j*LayoutController.ADAPT_PANEL_HEIGTH);[m
[31m-				this.imageField[i][j].setVisible(true);[m
[31m-				this.imageField[i][j].setBorder(javax.swing.BorderFactory.createEmptyBorder());[m
[31m-				this.gameArea.add(this.imageField[i][j],1);[m
[32m+[m				[32mthis.staticField[i][j] = new JLabel();[m[41m[m
[32m+[m				[32mthis.staticField[i][j].setSize(LayoutController.ADAPT_PANEL_WIDTH, LayoutController.ADAPT_PANEL_HEIGTH);[m[41m[m
[32m+[m				[32mthis.staticField[i][j].setLocation(i*LayoutController.ADAPT_PANEL_WIDTH, j*LayoutController.ADAPT_PANEL_HEIGTH);[m[41m[m
[32m+[m				[32mthis.staticField[i][j].setVisible(true);[m[41m[m
[32m+[m				[32mthis.staticField[i][j].setBorder(javax.swing.BorderFactory.createEmptyBorder());[m[41m[m
[32m+[m				[32mthis.gameArea.add(this.staticField[i][j],2);[m[41m[m
 			}[m
 		[m
 		// We need to layers to display the field one layer for the static environment and one for objects that maybe blown up or interacted with[m
[36m@@ -106,7 +108,8 @@[m [mpublic class LayoutController {[m
 				this.envField[i][j].setLocation(i*LayoutController.ADAPT_PANEL_WIDTH, j*LayoutController.ADAPT_PANEL_HEIGTH);[m
 				this.envField[i][j].setVisible(true);[m
 				this.envField[i][j].setBorder(javax.swing.BorderFactory.createEmptyBorder());[m
[31m-				this.gameArea.add(this.imageField[i][j],1);[m
[32m+[m				[32mthis.gameArea.add(this.envField[i][j],1);[m[41m[m
[32m+[m				[32mthis.gameArea.moveToFront(this.envField[i][j]);[m[41m[m
 			}[m
 	}[m
 	//Preload and resize images[m
[36m@@ -149,20 +152,20 @@[m [mpublic class LayoutController {[m
 				if(board[i][j].typ == Constants.FLOOR)[m
 				{[m
 				[m
[31m-					this.imageField[i][j].setIcon(new ImageIcon(this.floorIMG)); [m
[32m+[m					[32mthis.staticField[i][j].setIcon(new ImageIcon(this.floorIMG));[m[41m [m
 				}else if(board[i][j].typ == Constants.SOLID_WALL)[m
 				{[m
[31m-					this.imageField[i][j].setIcon(new ImageIcon(this.solidWallIMG));[m
[32m+[m					[32mthis.staticField[i][j].setIcon(new ImageIcon(this.solidWallIMG));[m[41m[m
 				}[m
 				//If we there are env objects on the panel we still need to init the static layer under it[m
 				if(board[i][j].typ == Constants.BREAKABLE_WALL)[m
 				{[m
 					this.envField[i][j].setIcon(new ImageIcon(this.boxIMG));[m
[31m-					this.imageField[i][j].setIcon(new ImageIcon(this.floorIMG));[m
[31m-				}else if(board[i][j].typ == Constants.EXIT)[m
[32m+[m					[32mthis.staticField[i][j].setIcon(new ImageIcon(this.floorIMG));[m[41m[m
[32m+[m				[32m}else if(board[i][j].typ == Constants.EXIT || board[i][j].isExit)[m[41m[m
 				{[m
 					this.envField[i][j].setIcon(new ImageIcon(this.doorIMG));[m
[31m-					this.imageField[i][j].setIcon(new ImageIcon(this.floorIMG));[m
[32m+[m					[32mthis.staticField[i][j].setIcon(new ImageIcon(this.floorIMG));[m[41m[m
 				}[m
 			}[m
 	}[m
[36m@@ -234,5 +237,6 @@[m [mpublic class LayoutController {[m
 		this.gameArea.add(temp);[m
 		this.gameArea.moveToFront(temp);[m
 	}[m
[31m-	[m
 }[m
[41m+		[m
[41m+		[m
\ No newline at end of file[m
