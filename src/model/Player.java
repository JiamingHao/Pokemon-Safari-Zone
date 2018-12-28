package model;

import java.io.Serializable;
import java.util.Observable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Player data model
 *
 * Notify Observer with an {@link PlayerModelChangeMsg} object to indicate what types of movement player is performing
 */
public class Player extends Observable implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 6041567580728483888L;

	/**
	 * Indicate what types of movement player is performing.
	 * Used by {@link Player} to communicate with Observer of the {@link Player} class
	 * @author JohnXu
	 *
	 */
	public static enum PlayerModelChangeMsg {
		ChangeDirection, Move, ChangeRegion;
	};

	/**
	 * Name of the Player
	 */
	private String name;

	/**
	 * Number of steps Player has moved
	 */
	private int steps;

	/**
	 * Inventory holds items, and Pokemon captured
	 */
	private Inventory inventory;

	/**
	 * Direction the Player currently facing
	 */
	private Direction dir;

	/**
	 * Index of the Region that player is currently in
	 */
	private int activeMapRegionIndex;

	/**
	 * Position of the player inside a Region
	 */
	private int x, y;

	private double extraEncounterRate;
	private double extraCaptureRate;

	private static Image characterTileSet;
	private static Image largeImage;

	/**
	 * Base Encounter Rate
	 */
	public final static double baseEncounterRate = 0.3;

	/**
	 * Base Capture Rate
	 */
	public final static double baseCaptureRate = 0.3;

	/**
	 * Constructor
	 * @param name Name of the Player
	 * @param x X coord of the player
	 * @param y Y coord of the player
	 */
	public Player(String name, int x, int y) {

		this.name = name;
		this.dir = Direction.SOUTH;
		this.activeMapRegionIndex = 0;
		this.x = x;
		this.y = y;
		this.steps = 0;
		this.inventory = new Inventory();
		this.inventory.initializeInventory();

	}

	/**
	 * Load Image of Player
	 */
	public static void loadImages() {

		try {
			Player.characterTileSet = new Image("file:src/Images/character.png",false);
			Player.largeImage = new Image("file:src/Images/character-large.png",false);
		} catch(Exception e) {
			System.err.println("Fail to load Player Images");
			System.exit(-1);
		}

	}

	/**
	 * Draw Player Image
	 * @param gc GraphicsContext
	 * @param frame Frame of the animate
	 * @param x X coord on screen (top-left coord)
	 * @param y Y corrd on screen (top-left coord)
	 */
	public void draw(GraphicsContext gc, int frame, double x, double y) {

		assert(frame <= 2);
		int direction = 0;

		switch(this.dir) {
			case NORTH:
				direction = 3;
				break;
			case SOUTH:
				direction = 0;
				break;
			case WEST:
				direction = 2;
				break;
			case EAST:
				direction = 1;
				break;
		}
		gc.drawImage(Player.characterTileSet, 27 * frame, 30 * direction, 20, 24, x, y - Terrain.TileHeight * 24 / 20, Terrain.TileWidth, Terrain.TileHeight * 24 / 20);
	}

	/**
	 * Draw Player Large Image (used in {@link View.BattleView})
	 * @param gc GraphicsContext
	 * @param x X coord on screen (top-left coord)
	 * @param y Y corrd on screen (top-left coord)
	 */
	public void drawLargeImage(GraphicsContext gc, double x, double y) {

		assert(Player.largeImage != null);
		gc.drawImage(Player.largeImage, x, y, 200, 200);
	}

	/**
	 * Get the index of the {@link MapRegion} that Player is currently in
	 * @return Index
	 */
	public int getActiveMapRegionIndex() {
		return activeMapRegionIndex;
	}

	/**
	 * Set the index of the {@link MapRegion} that Player is currently in.
	 * In other words, teleport Player to another {@link MapRegion}
	 * @param activeMapRegionIndex Index
	 */
	public void setActiveMapRegionIndex(int activeMapRegionIndex) {
		this.activeMapRegionIndex = activeMapRegionIndex;
		this.setChanged();
		this.notifyObservers(PlayerModelChangeMsg.ChangeRegion);
	}

	/**
	 * Teleport Player to another {@link MapRegion}, land at the given x-y coord in the designated {@link MapRegion}
	 * @param activeMapRegionIndex Index
	 * @param x X coord of the landing position
	 * @param y Y coord of the landing position
	 */
	public void teleport(int activeMapRegionIndex, int x, int y) {
		this.activeMapRegionIndex = activeMapRegionIndex;
		this.x = x;
		this.y = y;
		this.setChanged();
		this.notifyObservers(PlayerModelChangeMsg.ChangeRegion);
	}

	/**
	 * Getter
	 * @return X coord in the unit of Number of {@link MapGrid}
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter
	 * @return Y coord in the unit of Number of {@link MapGrid}
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter
	 * @param x X coord to be set
	 * @param y Y coord to be set
	 */
	public void setPos(int x, int y) {
		System.out.println("SetPos");
		this.x = x;
		this.y = y;
		this.steps++;
		this.setChanged();
		this.notifyObservers(PlayerModelChangeMsg.Move);
	}

	/**
	 * Move player in the given {@link Direction}
	 * @param dir Direction
	 */
	public void move(Direction dir) {

		this.dir = dir;

		switch(this.dir) {
		case SOUTH:
			this.y += 1;
			break;
		case NORTH:
			this.y -= 1;
			break;
		case WEST:
			this.x -= 1;
			break;
		case EAST:
			this.x += 1;
			break;
		}
		this.steps++;
		this.setChanged();
		this.notifyObservers(PlayerModelChangeMsg.Move);
	}

	/**
	 * Getter
	 * @return Player Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter
	 * @return Number of steps {@link Player} has walked
	 */
	public int getSteps() {
		return steps;
	}
	
	public void minusSteps() {
		steps--;
	}

	/**
	 * Getter
	 * @return {@link Direction} of the player
	 */
	public Direction getDirection() {
		//System.out.println(this.dir);
		assert(this.dir != null);
		return this.dir;
	}

	/**
	 * Setter
	 * @param dir {@link Direction} to be set
	 */
	public void setDirection(Direction dir) {

		//System.out.println(this.dir);
		assert(dir != null);

		this.dir = dir;
		this.setChanged();
		this.notifyObservers(PlayerModelChangeMsg.ChangeDirection);
	}

	/**
	 * Setter. set Direction without notify the Observers
	 * @param dir {@link Direction} to be set
	 */
	public void setDirectionUnnotify(Direction dir) {

		assert(dir != null);

		this.dir = dir;
	}

	/**
	 * Getter
	 * @return {@link Inventory} of the Player
	 */
	public Inventory getInventory() {
		return this.inventory;
	}

	/**
	 * Getter
	 * @return Extra Encounter Rate
	 */
	public double getExtraEncounterRate() {
		return extraEncounterRate;
	}

	/**
	 * Setter
	 * @param extraEncounterRate Extra Encounter Rate
	 */
	public void setExtraEncounterRate(double extraEncounterRate) {
		this.extraEncounterRate = extraEncounterRate;
	}

	/**
	 * Getter
	 * @return Extra Capture Rate
	 */
	public double getExtraCaptureRate() {
		return extraCaptureRate;
	}

	/**
	 * Setter
	 * @param extraCaptureRate Extra Capture Rate
	 */
	public void setExtraCaptureRate(double extraCaptureRate) {
		this.extraCaptureRate = extraCaptureRate;
	}

}
