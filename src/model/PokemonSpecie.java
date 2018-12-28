package model;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Hold Info about a Pokemon Specie.
 * Factory for {@link Pokemon} class
 * @author JohnXu
 *
 */
public class PokemonSpecie implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 7372340335852883702L;

	private String name;
	private PokemonType type;
	private int levelMin;
	private int levelMax;

	private String imageFileName;
	private transient Image image;
	private transient Image largeImage;

	/**
	 * Indicate how rare a specie is.
	 * Use to calculate which PokemonSpecie Player will encounter.
	 */
	private final double encounterFactor;
	private double catchRate;

	/**
	 * 
	 * @param name PokemonSpecie name
	 * @param type Pokemon type
	 * @param level Medium of the level range
	 * @param encounterFactor Encounter Factor
	 * @param catchRate Catch Rate
	 * @param imageFileName Filename of the Image
	 */
	public PokemonSpecie(String name, PokemonType type, int level, double encounterFactor, double catchRate, String imageFileName) {

		this.name = name;
		this.type = type;
		this.levelMax = level+5;
		this.levelMin = level > 5 ? level-5 : 0;
		this.encounterFactor = encounterFactor;
		this.catchRate = catchRate;
		this.imageFileName = imageFileName;
	}

	/**
	 * Create a {@link Pokemon} object that is of this PokemonSpecie
	 * @return {@link Pokemon} object or null if not encountered
	 */
	public Pokemon createPokemon() {

		Random rand = new Random();
		return new Pokemon(this, rand.nextInt(this.levelMax - this.levelMin) + this.levelMin);
	}

	/**
	 * Draw image of PokemonSpecie
	 * @param gc GraphicsContext
	 * @param x X coord on Screen
	 * @param y Y coord on Screen
	 */
	public void draw(GraphicsContext gc, double x, double y) {

		gc.drawImage(this.largeImage, x, y);
	}

	/**
	 * Draw large image of Pokemon Specie
	 * @param gc GraphicsContext
	 * @param x X coord on Screen
	 * @param y Y coord on Screen
	 */
	public void drawLargeImage(GraphicsContext gc, double x, double y) {

		assert(this.largeImage != null);
		gc.drawImage(this.largeImage, x, y, 200, 200);
	}

	/**
	 * Getter
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter
	 * @return Type
	 */
	public PokemonType getType() {
		return type;
	}

	/**
	 * Getter
	 * @return Encounter Factor
	 */
	public double getEncounterFactor() {
		return encounterFactor;
	}

	/**
	 * Getter
	 * @return Catch Rate
	 */
	public double getCatchRate() {
		return catchRate;
	}
	
	public String toString() {
		return name+" "+type;
	}

	/**
	 * Getter
	 * @return Filename of the Image
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * Getter
	 * @return Image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Load Image
	 */
	public void loadImage() {

		this.image = new Image("file:src/Images/Pokemon/" + this.imageFileName + ".png", false);
		this.largeImage = new Image("file:src/Images/Pokemon/" + this.imageFileName + "-large.png", false);
	}

}
