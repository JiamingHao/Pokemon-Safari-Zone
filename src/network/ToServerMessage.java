package network;

import model.Account;
import model.Game;
import model.Player;

public class ToServerMessage extends DataTransfer {

	private Account account;
	private Game game;
	private int gameID;
	private Player player;

	public static DataTransfer loginMessage(Account account) {

		ToServerMessage msg = new ToServerMessage();
		msg.type = Type.Login;
		msg.account = account;
		msg.game = null;
		msg.gameID = 0;
		msg.player = null;

		return msg;
	}

	public static DataTransfer newGameMessage(Game game) {

		ToServerMessage msg = new ToServerMessage();
		msg.type = Type.Login;
		msg.account = null;
		msg.game = game;
		msg.gameID = 0;
		msg.player = null;

		return msg;
	}

	public static DataTransfer playerUpdateMessage(int gameID, Player player) {

		ToServerMessage msg = new ToServerMessage();
		msg.type = Type.Login;
		msg.account = null;
		msg.game = null;
		msg.gameID = gameID;
		msg.player = player;

		return msg;
	}

	public Account getAccount() {
		return account;
	}

	public Game getGame() {
		return game;
	}

	public int getGameID() {
		return gameID;
	}

	public Player getPlayer() {
		return player;
	}



}
