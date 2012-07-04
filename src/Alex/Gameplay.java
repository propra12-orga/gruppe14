package Alex;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import upietz.Feld;
import upietz.Spielfeld;
import anika.Player;
import axel.Draw;
import axel.Sound;
import controller.Client;
import controller.Controller;
import controller.Server;

/**
 * Gameplay, responsible for the communication between player, Spielfeld, view
 * and controller
 * 
 * @author Volo
 * 
 */
public class Gameplay {
	/**
	 * Instanzvariablen
	 */
	private Spielfeld board; // Board
	private int playerCount; // Amount of players
	private Player[] player; // Array with all players
	private Draw screen; // View
	private Controller control; // Controller
	static boolean game_over = false;

	// Netzwerkspiel
	private boolean isNetGame = false;
	private boolean isServer = false;
	private boolean isClient = false;
	private Server server = null;
	private Client client = null;

	/**
	 * Constructor
	 * 
	 * Creates a (standard) board and the wished amount of players.
	 * 
	 * @param int player
	 */
	public Gameplay(String mapFile, int player, Controller control, Object net) {
		// Do we have a Network Game?
		if (net != null) {
			if (net instanceof controller.Server) {
				this.server = (Server) net;
				this.isNetGame = true;
				this.isServer = true;
			} else if (net instanceof controller.Client) {
				this.client = (Client) net;
				this.isNetGame = true;
				this.isClient = true;
			}
		}

		/*
		 * System.out.println("isNetGame: " + (this.isNetGame?"true":"false") +
		 * "\n" + "isClient: " + (this.isClient?"true":"false") + "\n" +
		 * "isServer: " + (this.isServer?"true":"false") + "\n" );
		 */

		this.control = control;
		game_over = false;
		new Sound(System.getProperty("user.dir") + "/graphics/musik.wav")
				.loop();
		// Saves the amount of players
		// this.playerCount = player;
		this.playerCount = player;

		// Initialize a Draw Object
		this.screen = new Draw(control);

		// Create the board, at first with hardcoded values
		try {
			this.board = new Spielfeld(mapFile, this.playerCount,
					this.screen, this, control);
		} catch (Exception e) {
			control.print("Spielfeld erstellen gescheitert: " + e.getMessage());
		}

		// Create as many player instances as wished
		this.player = new Player[this.playerCount];
		for (int id = 0; id < this.playerCount; id++) {
			createPlayer(id);
		}

	}

	/**
	 * Constructs a Gameplay object from saved data
	 * 
	 * @param playerInfo
	 *            Strings containing the player data
	 * @param board
	 *            Strings containing the serialized board
	 * @param c
	 *            The controller
	 * @return The object representing the restored game
	 */
	public static Gameplay restore(String[] playerInfo, String[] board,
			Controller c) {
		// construct empty Gameplay object
		Gameplay gp = new Gameplay();
		// fill instance variables
		gp.control = c;
		gp.screen = new Draw(c);
		Draw d = gp.screen;

		int width = Integer.valueOf(board[0].split(",")[0]);
		int height = Integer.valueOf(board[0].split(",")[1]);
		Feld[][] feld = new Feld[width][height];
		// construct new Feld objects from the loaded game and put them into
		// feld
		for (int i = 1; i < board.length; i++) {
			int row = (int) ((i - 1) / width);
			int column = i - 1 - row * width;
			// construct empty Feld object
			Feld f = new Feld();
			String[] info = board[i].split(",");
			// read the tile's properties
			f.typ = Integer.valueOf(info[0]);
			f.belegt = Integer.valueOf(info[1]);
			f.hasBomb = (info[2].equals("1") ? true : false);
			f.isExit = (info[3].equals("1") ? true : false);
			// set the tile to it's proper position
			feld[row][column] = f;
		}

		Spielfeld gameboard = new Spielfeld(d, gp, c, feld);
		gp.board = gameboard;
		// using a list here, so the player array is the right size and has no
		// null pointers trailing as a substitute for dead players
		ArrayList<Player> playerList = new ArrayList<Player>();
		int id = 0;
		for (int i = 1; i < playerInfo.length; i++) {
			String[] info = playerInfo[i].split(",");
			// check whether the player is already dead
			if (info[2].equals("0")) {
				continue;
			}
			int[] coordinates = { Integer.valueOf(info[0]),
					Integer.valueOf(info[1]) };

			Player p = new Player(id++, gp.board, d, gp, coordinates);
			p.adjustScore(Integer.valueOf(info[3]));
			playerList.add(p);
		}
		Player[] players = new Player[0];
		players = playerList.toArray(players);
		gp.playerCount = players.length;
		gp.player = players;
		d.drawBoard(gp.board.getStructure(), height, width);
		for (Player p : players) {
			d.drawPlayer(p.getId(), p.coordinates());
		}
		return gp;
	}

	/**
	 * This constructor does simply nothing, except construct a new object for
	 * later use
	 */
	private Gameplay() {
	}

	/**
	 * Constructor without integer parameter initializes game for 1 player as
	 * default
	 */
	public Gameplay(Controller control) {
		this("maps/std.map", 1, control, null);
	}

	/**
	 * createPlayer
	 * 
	 * Creates instance of Player with transmitted it und saves it in array
	 * this.player
	 * 
	 * @param int id
	 */
	private void createPlayer(int id) {
		this.player[id] = new Player(id, this.board, this.screen, this);
	}

	/**
	 * controls
	 * 
	 * Receives key inputs and sends them to the relevant spaces
	 * 
	 * @param String
	 *            key
	 */
	public void controls(String key) {

		/*
		 * TODO Hier muss zweierlei geschehen. 1. Wenn es nur einen Spieler
		 * gibt, darf der zweite if-else-Block nicht bearbeitet werden 2. Wenn
		 * es zwei Spieler gibt und wir ein Netzwerkspiel sind: a. Sind wir
		 * Client: muss vor dem ganzen Block alles zum Server gesendet werden.
		 * Der sendet entweder die Taste als String oder null zurück, das wird
		 * dann ganz normal weiter verarbeitet b. Sind wir Server: muss direkt
		 * nach dem aufrufen einer Player.moveX-Methode die Taste an den Client
		 * zurückgesendet werden. Alle moveX-Methoden in Player senden nun
		 * booleans zurück, ob der Zug gültig ist. Ist er nicht gültig, muss
		 * null an den Client gesendet werden.
		 * 
		 * Hinweise: Die Methode im Server bzw. Client zum Senden von Strings
		 * heisst sendMessage(String)
		 */

		// if(game_over == false)
		// {
		if (isNetGame == false)
			keyCheck(key, "0"); // We can safely say "0" for now, since it
								// doesn't have a meaning in this context
		// }
		else
		/*
		 * netgame muss true sein, sonst wären wir nicht im else-Block
		 * if((isNetGame == true)&&(isClient == true))
		 */
		if (this.isClient) {
			if (key.equals("left"))
				this.client.sendMessage("left", 0);
			else if (key.equals("right"))
				this.client.sendMessage("right", 0);
			else if (key.equals("up"))
				this.client.sendMessage("up", 0);
			else if (key.equals("down"))
				this.client.sendMessage("down", 0);
			else if (key.equals("n"))
				this.client.sendMessage("n", 0);
			else if (key.equals("m"))
				this.client.sendMessage("m", 0);

			// Two player? then second key bindings
			if (this.playerCount == 2) {
				if (key.equals("a"))
					this.client.sendMessage("a", 1);
				else if (key.equals("d"))
					this.client.sendMessage("d", 1);
				else if (key.equals("w"))
					this.client.sendMessage("w", 1);
				else if (key.equals("s"))
					this.client.sendMessage("s", 1);
				else if (key.equals("y"))
					this.client.sendMessage("y", 1);
				else if (key.equals("x"))
					this.client.sendMessage("x", 1);
			}
		}
		/*
		 * isNetGame siehe oben else if((isNetGame == true)&&(isServer ==
		 * true)){
		 */
		else if (this.isServer) {
			/*
			 * nur weiterleiten an keycheck, da wir den Rückgabewert von moveX
			 * benötigen!
			 */
			if (key.equals("left"))
				keyCheck(key, "0");
			else if (key.equals("right"))
				keyCheck(key, "0");
			else if (key.equals("up"))
				keyCheck(key, "0");
			else if (key.equals("down"))
				keyCheck(key, "0");
			else if (key.equals("n"))
				keyCheck(key, "0");
			else if (key.equals("m"))
				keyCheck(key, "0");

			if (this.playerCount == 2) {
				if (key.equals("a"))
					keyCheck(key, "1");
				else if (key.equals("d"))
					keyCheck(key, "1");
				else if (key.equals("w"))
					keyCheck(key, "1");
				else if (key.equals("s"))
					keyCheck(key, "1");
				else if (key.equals("y"))
					keyCheck(key, "1");
				else if (key.equals("x"))
					keyCheck(key, "1");
			}
		}
	}

	public void keyCheck(String key, String player) {
		//
		boolean valid = false;
		int playerId = Integer.parseInt(player);
		// Aus der PlayerId liesse sich dann auch ein Array mit den
		// Tastaturzuweisungen machen!

		if (key.equals("pause")) {
			System.out.println("Pause...");
		}
		// Hier muss der Rückgabewert an den client gesendet werden!
		if ((key.equals("left") && this.player[0].moveLeft())
				|| (key.equals("right") && this.player[0].moveRight())
				|| (key.equals("up") && this.player[0].moveUp())
				|| (key.equals("down") && this.player[0].moveDown())
				|| (key.equals("n") && this.player[0].dropBomb())
				|| (key.equals("m") && this.player[0].dropBomb2())) {
			valid = true;
			// Wenn wir ein Server sind, muss die Nachricht weitergesendet
			// werden!
			if (this.isServer)
				this.server.sendMessage(key, 0);
		}

		if ((key.equals("a") && this.player[1].moveLeft())
				|| (key.equals("d") && this.player[1].moveRight())
				|| (key.equals("w") && this.player[1].moveUp())
				|| (key.equals("s") && this.player[1].moveDown())
				|| (key.equals("y") && this.player[1].dropBomb())
				|| (key.equals("x") && this.player[1].dropBomb2())) {
			valid = true;
			if (this.isServer)
				this.server.sendMessage(key, 1);
		}

		/*
		 * obsolet if (key.equals("a")) this.player[1].moveLeft(); else if
		 * (key.equals("d")) this.player[1].moveRight(); else if
		 * (key.equals("w")) this.player[1].moveUp(); else if (key.equals("s"))
		 * this.player[1].moveDown(); else if (key.equals("y"))
		 * this.player[1].dropBomb(); else if (key.equals("x"))
		 * this.player[1].dropBomb2();
		 */
	}

	/**
	 * gameWon
	 * 
	 * Gets player's id as parameter. This one has won the game.
	 * 
	 * @param int id
	 */
	public void gameWon(int id) {
		// ?
		System.out.println("And the winner is: Player " + id);

		// In a netgame, tell the client
		if (this.isNetGame && this.isServer)
			this.server.sendMessage("won ", id);

		game_over = true;
		GameOver();
		// System.exit(0);
	}

	/**
	 * gameOver
	 * 
	 * If every player is dead, game is lost.
	 */
	public void GameOver() {
		// ?
		control.print("Game Over!");
		JOptionPane.showMessageDialog(null, "Das Spiel ist zu Ende!",
				"Spielstand", JOptionPane.OK_CANCEL_OPTION);
		// Bomberman.newBomberman();
		System.exit(0);
	}

	/**
	 * deregisterPlayer
	 * 
	 * If a player is dead, he deregisters with this method.
	 * 
	 * ToDo: Im Moment gibt es ja nur einen Spieler, also wird das Spiel sofort
	 * beendet. In Zukunft sollte hier eine Abfrage stehen die nachsieht, ob
	 * noch ein Player aktiv ist. Sind es mehr als einer geht das Spiel weiter,
	 * ist es genau einer hat dieser gewonnen, ist keiner mehr aktiv ist das
	 * Spiel vorbei.
	 * 
	 * @param int id
	 */
	public void deregisterPlayer(int id) {
		// Informs player, that he is dead.
		this.player[id].die();
		GameOver();
	}

	/**
	 * Provides access to the game board
	 * 
	 * @return the current game board value
	 */
	public Spielfeld getBoard() {
		return board;
	}

	/**
	 * This method allows access to the array of Player objects
	 * 
	 * @param index
	 *            the index of the desired Player object in the array
	 * @return The indicated Player object
	 */
	public Player getPlayer(int index) {
		if (index < 0 || index > player.length)
			return null;
		return player[index];
	}

	public int getNumOfPlayers() {
		return playerCount;
	}
}