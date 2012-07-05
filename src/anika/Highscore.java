package anika;

public class Highscore implements Comparable<Highscore> {

	String user;

	int score;

	boolean local;

	/**
	 * Constructs a new Highscore object
	 * 
	 * @param score
	 *            The score for this highscore entry
	 * @param user
	 *            The user who made this score
	 * @param local
	 *            Whether the highscore is local, or persisted in database
	 */
	public Highscore(int score, String user, boolean local) {
		this.user = user;
		this.score = score;
		this.local = local;
	}

	@Override
	public String toString() {
		return user + ": " + score;
	}

	public boolean isLocal() {
		return this.local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Highscore arg) {
		if (this.score < arg.score)
			return 1;
		if (this.score > arg.score) {
			return -1;
		} else
			return this.user.toUpperCase().compareTo(arg.user.toUpperCase());
	}
}
