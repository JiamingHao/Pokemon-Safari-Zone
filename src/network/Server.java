package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Observable;
import java.util.Observer;

import controller.PersistentData;
import model.Account;
import model.Game;


/**
 * Server will also verify password.
 * Server will send other player's data to Client during game.
 * @author JohnXu
 *
 */
public class Server implements Runnable, Observer {

	/**
	 * Data model that will be shared across all server thread
	 */
	private static ServerData data;
	private ObjectInputStream objInStream;
	private ObjectOutputStream objOutStream;
	private Socket socket;

	public static void main(String[] args) {

		ServerSocket server;
		try {
			server = new ServerSocket(4000);
		} catch(IOException e) {
			System.err.println("Unable to start server socket on port 4000");
			return;
		}

		// Start a new thread for each connection established
		while (true) {

			try {

				// Establish Connection
				Socket connection = server.accept();
				System.out.println("Connection Established: " + connection.toString());

				// Start New Thread
				new Thread(new Server(connection)).start();

			} catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}

		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Server(Socket socket) {

		this.socket = socket;

		try {
			this.objInStream = new ObjectInputStream(this.socket.getInputStream());
			this.objOutStream = new ObjectOutputStream(this.socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	/**
	 * Each connection will run on their own thread
	 */
	public void run() {

		try {
			PersistentData temp = null;

			while(true) {
				// Read from Client
				try {

					temp = (PersistentData)objInStream.readObject();
					if(temp == null) continue;
					System.out.printf("Receive from client(%s): %s %s \n", this.socket.toString(), temp.getClass().toString(), temp.toString());

					if(!(temp instanceof network.ToServerMessage)) continue;

					ToServerMessage msg = (ToServerMessage) temp;

					switch(msg.type) {
					// Verify Client Login
					case Login:
						this.loginResult(this.data.login((Account)temp));
						break;
					// Creating New Game
					case CreateNewGame:
						Game g = msg.getGame();
						Server.data.startNewSinglePlayerGame(g);
						break;
					// Update Player pos or Inventory
					case PlayerUpdate:
						Server.data.updatePlayerInGame(msg.getGameID(), msg.getPlayer());
						break;
					// Save Game
					case SaveGame:
						throw new RuntimeException("Not implemented");
					default:
						break;
					}

				} catch (ClassNotFoundException e) {

					e.printStackTrace();
					break;
				} catch (EOFException e) {

					break;
				}
			}
			objOutStream.close();
			this.socket.close();

		} catch (java.net.SocketException e) {
			System.out.printf("Client(%s) Disconnect\n", this.socket.toString());

		} catch (IOException e) {

			System.err.println(e);
		}
	}

	public void loginResult(boolean result) {

		try {
			this.objOutStream.writeObject(ToClientMessage.loginResponseMessage(result));

		} catch (IOException e) {

			System.err.printf("Fail to send obj to client(%s)", this.socket.toString());
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * If the data is changed(by one of the client), then notify client
	 */
	public void update(Observable o, Object arg) {

		try {
			this.objOutStream.writeObject(null);

		} catch (IOException e) {

			System.err.printf("Fail to send obj to client(%s)", this.socket.toString());
			e.printStackTrace();
		}
	}
}
