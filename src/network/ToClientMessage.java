package network;

public class ToClientMessage extends DataTransfer {

	private int gameID;

	public static DataTransfer loginResponseMessage(boolean result) {

		ToClientMessage msg = new ToClientMessage();
		msg.type = result ? Type.LoginSuccess : Type.LoginFail;
		msg.gameID = 0;

		return msg;
	}

	public static DataTransfer gameCreateSuccessMessage(int gameID) {

		ToClientMessage msg = new ToClientMessage();
		msg.type = Type.GameCreateSuccess;
		msg.gameID = gameID;

		return msg;
	}

	public static DataTransfer messageReceivedMessage() {

		ToClientMessage msg = new ToClientMessage();
		msg.type = Type.MessageReceive;
		msg.gameID = 0;

		return msg;
	}


}
