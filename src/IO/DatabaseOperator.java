package IO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import anika.Highscore;

/**
 * @author anika
 * 
 */
public class DatabaseOperator {

	// the Properties object containing connection information
	private Properties connectionInfo;

	/**
	 * Constructs a new DatabaseOperator object
	 * 
	 * @param connectionInfo
	 *            The properties containing the connection info
	 */
	public DatabaseOperator(Properties connectionInfo) {
		this.connectionInfo = connectionInfo;
	}

	/**
	 * @param conn
	 *            the Database Connection to use
	 * @throws SQLException
	 *             throws an exception if something goes wrong
	 */
	private boolean setupTables(Connection conn) throws SQLException {

		// check for user table
		if (!tableExists("highscore", conn)) {
			setupHighscoreTable(conn);
		}

		return true;
	}

	/**
	 * Writes every local highscore into the database
	 * 
	 * @param highscores
	 *            A list with all saved highscores
	 * @throws SQLException
	 */
	public void writeHighscores(List<Highscore> highscores) throws SQLException {
		Connection conn = connect();
		// make sure table exists in database
		setupTables(conn);
		// write new highscores into the database
		for (Highscore hs : highscores) {
			if (hs.isLocal()) {
				String addScore = "INSERT INTO bomberman.highscore (user, score) VALUES ('"
						+ hs.getUser() + "', '" + hs.getScore() + "')";
				Statement st = conn.createStatement();
				st.executeUpdate(addScore);
			}
		}
		conn.close();
	}

	/**
	 * Reads all highscores from the remote DB
	 * 
	 * @return A list containing the remote highscores
	 */
	public List<Highscore> readHighscores() throws SQLException {
		Connection conn = connect();
		String query = "SELECT * FROM "
				+ connectionInfo.get("database.database")
				+ ".highscore ORDER BY score DESC";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(query);
		List<Highscore> scores = new LinkedList<Highscore>();
		while (result.next()) {
			scores.add(new Highscore(result.getInt("score"), result
					.getString("user"), false));
		}
		conn.close();
		return scores;
	}

	/**
	 * Sets up a table for the highscores
	 * 
	 * @param conn
	 */
	private void setupHighscoreTable(Connection conn) {
		System.out.println("Versuche, Tabelle 'highscors' anzulegen...");
		String creationString = "CREATE TABLE "
				+ connectionInfo.get("database.database") + ".highscore "
				+ "(id INT(11) NOT NULL AUTO_INCREMENT, "
				+ "user VARCHAR(40) DEFAULT NULL, "
				+ "score INT(11) DEFAULT NULL, " + "PRIMARY KEY (id))";
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(creationString);
		} catch (SQLException e) {
			System.out.println("Fehler bei der Anlage von 'highscore':");
			e.printStackTrace();
		}
		System.out.println("Tabelle angelegt");
	}

	public boolean tableExists(String tableName, Connection conn)
			throws SQLException {

		ResultSet tables = conn.getMetaData().getTables(null, null, tableName,
				null);
		boolean exists = tables.next();
		if (!exists) {
			System.out.println("Tabelle " + tableName
					+ " existiert nicht in der Datenbank");
		}
		return exists;
	}

	/**
	 * Method that connects to the database and returns the Connection object
	 * 
	 * @return A Connection to the database
	 */
	private Connection connect() throws SQLException {
		Connection conn = null;

		Properties connectionProps = new Properties();
		connectionProps.put("user", connectionInfo.get("database.user"));
		connectionProps
				.put("password", connectionInfo.get("database.password"));
		StringBuilder connectionString = new StringBuilder();
		connectionString.append("jdbc:mysql://")
				.append(connectionInfo.get("database.host")).append(":")
				.append(connectionInfo.get("database.port")).append("/")
				.append(connectionInfo.get("database.database"));
		conn = DriverManager.getConnection(connectionString.toString(),
				connectionProps);

		return conn;
	}

}
