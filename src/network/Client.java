package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import model.Account;
import model.Game;

/**
 * Client will send {@link Account} object when attempt to login.
 * Client will send {@link Game} object when creating a new {@link Game}.
 * Client will send {@link Player} data to server during Game.
 * @author JohnXu
 *
 */
public class Client implements Runnable, Observer {

	private Socket socket;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;

	/*
	 * Constructor
	 */
	public Client() throws UnknownHostException, IOException {

		this.socket = new Socket("localhost", 4000);
		this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
		this.inputFromServer = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run() {

		Object obj = null;

		while(!Thread.currentThread().isInterrupted()) {

			// Read from Server
			try {
				obj = this.inputFromServer.readObject();

			} catch (java.net.SocketException e) {
				System.out.println("Disconnected");
				break;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				break;
			}

			if(obj == null) continue;
			System.out.println("Receive from Server: " + obj.toString());
		}
	}

	public void createNewGame(Game game) {
		try {
			this.outputToServer.writeObject(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void verifyLogin(Account account) {
		try {
			this.outputToServer.writeObject(account);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		try {
			this.outputToServer.writeObject(null);

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
	}

}
