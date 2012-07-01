package anika;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import upietz.Feld;
import Alex.Gameplay;
import controller.Controller;

public class FileIO {

	/**
	 * This method will take a Gameplay object and a File object and serialize
	 * the game into plain text, then save it.
	 * 
	 * @param gp
	 *            The current Gameplay object
	 * @param f
	 *            The file to which to save
	 * @throws IOException
	 *             If anything happens, an exception will be thrown
	 */
	public static void saveGame(Gameplay gp, File f) throws IOException {
		// make sure the extension is .bsf
		if (!f.getName().endsWith(".bsf")) {
			f = new File(f.getAbsolutePath().concat(".bsf"));
		}
		// serialized will hold the serialized version of the ongoing game
		StringBuilder serialized = new StringBuilder();
		// saving the boards dimensions. This only works if the board is
		// rectangular.
		int x = gp.getBoard().getStructure().length;
		int y = gp.getBoard().getStructure()[0].length;
		serialized.append(x + "," + y + ";");

		// serialize every single field
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Feld field = gp.getBoard().getStructure()[i][j];
				serialized.append(field.typ + "," + field.belegt + ","
						+ (field.hasBomb ? "1," : "0,")
						+ (field.isExit ? "1;" : "0;"));
			}
		}
		serialized.append("\n");
		serialized.append(gp.getNumOfPlayers() + ";");
		for (int i = 0; i < gp.getNumOfPlayers(); i++) {
			Player p = gp.getPlayer(i);
			serialized.append(p.coordinates()[0] + "," + p.coordinates()[1]
					+ "," + (p.isDead() ? "0;" : "1;"));
		}

		saveFile(f, serialized.toString());
	}

	/**
	 * This method deserializes a formerly saved game
	 * 
	 * @param f
	 *            The file in which the gamedata resides
	 * @return Returns a Gameplay Object with game data
	 * @throws IOException
	 *             If there are IO problems, an exception will be thrown
	 */
	public static Gameplay loadGame(File f, Controller c) throws IOException {
		if (!f.exists()) {
			return null;
		}
		BufferedReader reader = createReader(f);
		String board = reader.readLine();
		String[] boardInfo = board.split(";");
		String playerString = reader.readLine();
		// deserialize player information
		String[] playerInfo = playerString.split(";");

		Gameplay gp = Gameplay.restore(playerInfo, boardInfo, c);
		reader.close();
		return gp;
	}

	/**
	 * Saves a string into a file
	 * 
	 * @param f
	 *            The file to which to save
	 * @param content
	 *            A string that will be saved
	 * @throws IOException
	 *             Throws an exception if bad things happen while saving
	 */
	private static void saveFile(File f, String content) throws IOException {
		BufferedWriter outStream = createWriter(f);
		outStream.write(content);
		outStream.flush();
		outStream.close();
	}

	/**
	 * Generates a BufferedWriter object for file writing
	 * 
	 * @param out
	 *            The file to which to write
	 * @return Returns a buffered writer for the specified file
	 * @throws IOException
	 */
	private static BufferedWriter createWriter(File out) throws IOException {
		Writer outStream = new FileWriter(out);
		BufferedWriter writer = new BufferedWriter(outStream);

		return writer;
	}

	/**
	 * Generates a BufferedReader object
	 * 
	 * @param in
	 *            The file from which to read
	 * @return Returns a buffered reader for the specified file
	 * @throws FileNotFoundException
	 */
	public static BufferedReader createReader(File in)
			throws FileNotFoundException {
		FileReader inStream = new FileReader(in);
		BufferedReader reader = new BufferedReader(inStream);

		return reader;
	}

}
