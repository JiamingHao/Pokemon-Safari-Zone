package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Account;
import model.AccountList;
import model.Game;
import model.GameMap;
import model.MultiPlayerGame;
import model.Player;
import model.SinglePlayerGame;

public class ServerData {

	/**
	 * List of User Account
	 */
	private AccountList accountList; 

	/**
	 * List of Games that are available to join
	 */
	private List<Game> openedGameList;

	/**
	 * Saved Game List
	 */
	private List<Game> savedGameList;

	public ServerData() {
	}

	public boolean login(String username, String password) {
		return this.accountList.checkPassword(username, password);
	}

	public boolean login(Account account) {
		return this.accountList.checkPassword(account.getUsername(), account.getPassword());
	}

	public List<Game> listOfSavedGame(Account account) {

		ArrayList<Game> list = new ArrayList<Game>();

		for(Integer id : account.getGameIDs()) {
			for(Game game : this.savedGameList) {
				if(game.getGameID() == id) {
					list.add(game);
					break;
				}
			}
		}
		return list;
	}

	public void startNewSinglePlayerGame(Game game) {
		throw new RuntimeException("Not implemented");
	}

	public void startNewMultiPlayerGame(MultiPlayerGame game, Player player) {
		this.openedGameList.add(game);
	}

	public void updatePlayerInGame(int gameID, Player player) {
		for(Game game : this.openedGameList) {
			if(game.getGameID() == gameID) {
				for(Player p: game.getPlayers()) {
					if(p.getName().compareTo(player.getName()) == 0) {
						p.setPos(player.getX(), player.getY());
					}
				}
			}
		}
	}


}
