package model;

import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * A Collection of available {@link PokemonSpecie} in this game
 *
 */
public class AvailablePokemonSpecieList extends ArrayList<PokemonSpecie> {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 6914718148245124117L;

	private static AvailablePokemonSpecieList instance;

	/**
	 * protected access to prevent instantiation from outside of class
	 */
	protected AvailablePokemonSpecieList() {
		super();
		this.initializeList();
	}

	/**
	 * Get an instance of the list
	 * @return An instance of the {@link AvailablePokemonSpecieList}
	 */
	public static AvailablePokemonSpecieList getInstance() {
		if(instance == null) {
			instance = new AvailablePokemonSpecieList();
		}
		return instance;
	}

	/**
	 * Hard coded {@link PokemonSpecie}
	 */
	private void initializeList() {

		this.add(new PokemonSpecie("Nidoran", PokemonType.Poison, 22, 250, 0, "Nidoran"));
		this.add(new PokemonSpecie("Paras", PokemonType.Grass, 27, 50, 0, "Paras"));
		this.add(new PokemonSpecie("Chansey", PokemonType.Normal, 23, 10, 0, "Chansey"));
		this.add(new PokemonSpecie("Cubone", PokemonType.Ground, 19, 100, 0, "Cubone"));
		this.add(new PokemonSpecie("Exeggcute", PokemonType.Psychic, 25, 200, 0, "Exeggcute"));
		this.add(new PokemonSpecie("Scythere", PokemonType.Flying, 23, 40, 0, "Scyther"));
		this.add(new PokemonSpecie("Rhyhorn", PokemonType.Rock, 30, 250, 0, "Rhyhorn"));
		this.add(new PokemonSpecie("Tauros", PokemonType.Normal, 28, 10, 0, "Tauros"));
		this.add(new PokemonSpecie("Kangaskhan", PokemonType.Normal, 28, 10, 0, "Kangaskhan"));
		this.add(new PokemonSpecie("Doduo", PokemonType.Flying, 26, 200, 0, "Doduo"));
		this.add(new PokemonSpecie("Pikachu", PokemonType.Electric, 27, 50, 0, "Pikachu"));
		this.add(new PokemonSpecie("Oddish", PokemonType.Poison, 27, 400, 0, "Oddish"));

		this.add(new PokemonSpecie("Magikarp", PokemonType.Water, 10, 700, 0, "Magikarp"));
		this.add(new PokemonSpecie("Psyduck", PokemonType.Water, 15, 250, 0, "Psyduck"));
		this.add(new PokemonSpecie("Slowpoke", PokemonType.Psychic, 15, 250, 0, "Slowpoke"));
		this.add(new PokemonSpecie("Krabby", PokemonType.Water, 15, 250, 0, "Krabby"));
		this.add(new PokemonSpecie("Dratini", PokemonType.Dragon, 15, 250, 0, "Dratini"));
	}

	/**
	 * Search through the list to find {@link PokemonSpecie} with the same name
	 * @param name Name to be searched
	 * @return Return null if PokemonSpecie not found, or an {@link PokemonSpecie} object with the same name if found
	 */
	public static PokemonSpecie search(String name) {

		for(PokemonSpecie ps : AvailablePokemonSpecieList.getInstance()) {
			if(ps.getName().compareTo(name) == 0) {
				return ps;
			}
		}
		return null;
	}

	/**
	 * Load images for all the PokemonSpecie
	 */
	public static void loadImages() {

		try {
			for(PokemonSpecie ps : AvailablePokemonSpecieList.getInstance()) {
				ps.loadImage();
			}
		} catch (Exception e) {
			System.err.println("Fail to load Pokemon Images");
			System.exit(-1);
		}
	}

}
