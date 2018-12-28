package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * User Account. 
 * Stored on server side
 */
public class Account implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -6240696465134530181L;
	private String username;
	private String password;

	/**
	 * HashMap<GameID, PlayerName>
	 */
	private HashMap<Integer, String> characterList;

	/**
	 * Constructor
	 * @param username Username
	 * @param password Password
	 */
	public Account(String username, String password) {

		this.username = username;
		this.password = password;
	}

	/**
	 * Search for Name of the {@link model.Player} that belongs to this Account in a particular Game with the given GameID
	 * @param gameID ID of the Game
	 * @return Name of the Player in the specified game
	 */
	public String getPlayerName(int gameID) {
		return this.characterList.get(gameID);
	}

	public Integer[] getGameIDs() {
		return (Integer[])this.characterList.keySet().toArray();
	}

	/**
	 * Getter
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Getter
	 * @return Password
	 */
	public String getPassword() {
		return password;
	}

}
