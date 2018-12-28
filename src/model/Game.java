package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import controller.PersistentData;

/**
 * Core model for the pokemon game
 *
 */
public abstract class Game extends Observable implements PersistentData<Game> {

	/**
	 * SerialVersionUID
	 */
	protected static final long serialVersionUID = 6342713372636188227L;

	/**
	 * Max number of steps
	 */
	public final static int maxStep = 500;

	public enum Type {
		SinglePlayer, MultiPlayer
	}
	protected Type type;
	protected int gameID;
	protected GameMap map;
	protected List<Player> listOfPlayers;
	
	/**
	 * Start a game with only variable-number of players
	 * @param playerNames Array of Names
	 */
	public Game(String[] playerNames)
	{
		this.map = new GameMap(2);
		this.map.initMap();

		this.listOfPlayers = new ArrayList<Player>();

		for(int i = 0; i < playerNames.length; i++) {

			// init starting pos of players
			// Players will start at the center of the hub(region 0)
			int x = GameMap.HubRegionWidth / 2 - playerNames.length / 2;
			int y = GameMap.HubRegionHeight / 4 * 3;
			Player player = new Player(playerNames[i], x + i, y);
			this.listOfPlayers.add(player);
		}
	}

	/**
	 * Start a game with only 1 player
	 * @param playerName
	 */
	public Game(String playerName)
	{
		this.map = new GameMap(2);
		this.map.initMap();

		this.listOfPlayers = new ArrayList<Player>();

		// init starting pos of players
		// Players will start at the center of the hub(region 0)
		int x = GameMap.HubRegionWidth / 2;
		int y = GameMap.HubRegionHeight / 4 * 3;
		Player player = new Player(playerName, x, y);
		this.listOfPlayers.add(player);
	}

	/**
	 * Getter
	 * @return Game ID
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * Setter
	 * @param gameID Game ID
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * Getter
	 * @return {@link GameMap}
	 */
	public GameMap getMap() {
		return this.map;
	}

	/**
	 * Getter
	 * @return List of Players
	 */
	public List<Player> getPlayers() {
		return this.listOfPlayers;
	}
	
	/**
	 * Check if the player win or not
	 * @return True if Win
	 */
	public boolean win() {
		return ! (this.listOfPlayers.get(0).getSteps() > this.maxStep);
	}

	/**
	 * Check if the player lost or not
	 * @return True if lost
	 */
	public boolean lose() {
		return this.listOfPlayers.get(0).getSteps() > this.maxStep;
	}

	@Override
	public void restore(Game data) {
		this.map = data.getMap();
		this.listOfPlayers.clear();
		for(Player player : data.listOfPlayers) {
			this.listOfPlayers.add(player);
		}
	}
}
