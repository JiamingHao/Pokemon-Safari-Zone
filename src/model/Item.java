package model;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Model for in-game Item
 * @author JohnXu
 *
 */
public class Item implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 6015257253571822520L;
	private String name;
	private ItemAction action;
	private PlayerAttribute attribute; 
	private int attributeVal;

	private boolean consumable;
	private boolean inBattleUse;
	private boolean outOfBattleUse;

	private final String imageFileName;
	private transient Image image;
	private transient Image largeImage;

	/**
	 * Constructor for non-consumable
	 * @param name Name of the Item
	 * @param imageFileName Filename of the Image
	 */
	public Item(String name, String imageFileName) {

		this.name = name;
		this.action = null;
		this.attribute = null;
		this.imageFileName = imageFileName;
		this.consumable = false;
		this.inBattleUse = false;
		this.outOfBattleUse = false;
	}

	/**
	 * Constructor
	 * @param name Name of the Item
	 * @param action Action performed by the Item
	 * @param imageFileName Filename of the Image
	 * @param consumable Whether is consumable or not
	 * @param inBattleUse Whether can be use in battle
	 * @param outOfBattleUse Whether can be use out of battle
	 */
	public Item(String name, ItemAction action, String imageFileName, boolean consumable, boolean inBattleUse, boolean outOfBattleUse) {

		this.name = name;
		this.action = action;
		this.attribute = null;
		this.imageFileName = imageFileName;
		this.consumable = consumable;
		this.inBattleUse = inBattleUse;
		this.outOfBattleUse = outOfBattleUse;
	}

	/**
	 * Constructor
	 * @param name Item name
	 * @param attribute Player attribute that the Item increases
	 * @param value Amount of the attribute that the Item increases
	 * @param imageFileName Filename of the Image
	 * @param consumable Whether is consumable or not
	 * @param inBattleUse Whether can be use in battle
	 * @param outOfBattleUse Whether can be use out of battle
	 */
	public Item(String name, PlayerAttribute attribute, int value, String imageFileName, boolean consumable, boolean inBattleUse, boolean outOfBattleUse) {

		this.name = name;
		this.attribute = attribute;
		this.attributeVal = value;
		this.action = null;
		this.imageFileName = imageFileName;
		this.consumable = consumable;
		this.inBattleUse = inBattleUse;
		this.outOfBattleUse = outOfBattleUse;
	}

	/**
	 * Constructor
	 * @param name Item name
	 * @param action Action performed by the Item
	 * @param attribute Player attribute that the Item increases
	 * @param value Amount of the attribute that the Item increases
	 * @param imageFileName Filename
	 * @param consumable Whether is consumable or not
	 * @param inBattleUse Whether can be use in battle
	 * @param outOfBattleUse Whether can be use out of battle
	 */
	public Item(String name, ItemAction action, PlayerAttribute attribute, int value, String imageFileName, boolean consumable, boolean inBattleUse, boolean outOfBattleUse) {

		this.name = name;
		this.action = action;
		this.attribute = attribute;
		this.attributeVal = value;
		this.imageFileName = imageFileName;
		this.consumable = consumable;
		this.inBattleUse = inBattleUse;
		this.outOfBattleUse = outOfBattleUse;
	}

	/**
	 * Draw Image of Item
	 * @param gc GraphicsContext
	 * @param x X coord
	 * @param y Y coord
	 */
	public void draw(GraphicsContext gc, double x, double y) {

		gc.drawImage(this.largeImage, x, y);
	}

	/**
	 * Draw Large Image of Item
	 * @param gc GraphicsContext
	 * @param x X coord
	 * @param y Y coord
	 */
	public void drawLargeImage(GraphicsContext gc, double x, double y) {

		gc.drawImage(this.largeImage, x, y);
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
	 * @return {@link ItemAction}
	 */
	public ItemAction getAction() {
		return action;
	}

	/**
	 * Getter
	 * @return {@link PlayerAttribute}
	 */
	public PlayerAttribute getAttribute() {
		return attribute;
	}

	/**
	 * Getter
	 * @return Filename
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
	 * Getter
	 * @return Whether Item is consumable or not
	 */
	public boolean isConsumable() {
		return consumable;
	}

	/**
	 * Getter
	 * @return Whether or not the Item can be used during battle
	 */
	public boolean isInBattleUse() {
		return inBattleUse;
	}

	/**
	 * Getter
	 * @return Whether or not the Item can be used out of battle
	 */
	public boolean isOutOfBattleUse() {
		return outOfBattleUse;
	}

	/**
	 * Load Image
	 */
	public void loadImage() {

		this.image = new Image("file:src/Images/Item/" + this.imageFileName + ".png", false);
		this.largeImage = new Image("file:src/Images/Item/" + this.imageFileName + "-large.png", false);
	}


}
