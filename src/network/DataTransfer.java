package network;

public class DataTransfer {

	public enum Type {
		Login, CreateNewGame, PlayerUpdate, SaveGame,
		LoginSuccess, LoginFail, GameCreateSuccess, MessageReceive
	}

	protected Type type;
}
