package controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Game;
import model.Item;
import model.Pokemon;

/**
 * Perform data persistence.
 * Permanent data are stored on the server side.
 * Client side only hold some Game Data when running.
 * 
 * Client Data: model.Game (including {@link model.Player},  {@link model.GameMap})
 */
public class Persistence {

	private static ObjectInputStream ois;
	private static ObjectOutputStream oos;

	public Persistence() {
	}

	public static void saveToFile(Game game) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_yyyy_MM_dd_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();

		String filename = game.getPlayers().get(0).getName() + dtf.format(now) + ".data";

		try {
			oos = new ObjectOutputStream(new FileOutputStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			oos.writeObject(game);

		} catch (IOException e) {

			e.printStackTrace();
			System.err.println("Unable to write to file");
		}
	}

	public static Game restoreFromFile(File file) {

		Game game = null;

		try {
			ois = new ObjectInputStream(new FileInputStream(file.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while(true) {
				Object obj = ois.readObject();
				if(obj == null) break;

				System.out.println("PersistentData Read: " + obj.getClass().toString());
				if(!(obj instanceof Game)) continue;

				game = (Game)obj;
				/*
				System.out.println("PersistentData: Set");
				System.out.printf("PersistentData: Player: %d, %d\n", this.game.getPlayers().get(0).getX(), this.game.getPlayers().get(0).getY());
				for(Pokemon pokemon : this.game.getPlayers().get(0).getInventory().getPokemons()) {
					System.out.println(pokemon.getSpecie().getName());
				}
				System.out.println("================");
				*/
			}

		} catch (EOFException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return game;
	}

}
