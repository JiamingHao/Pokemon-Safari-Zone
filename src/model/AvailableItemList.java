package model;

import java.util.ArrayList;

/**
 * A Collection of available {@link Item} in this game
 *
 */
public class AvailableItemList extends ArrayList<Item> {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -1515453573112669349L;

	private static AvailableItemList instance = null;

	/**
	 * protected access to prevent instantiation from outside of class
	 */
	protected AvailableItemList() {
		super();
		this.initializeList();
	}

	/**
	 * Get an instance of the list
	 * @return An instance of the {@link AvailableItemList}
	 */
	public static AvailableItemList getInstance() {
		if(instance == null) {
			instance = new AvailableItemList();
		}
		return instance;
	}

	/**
	 * Hard coded {@link Item}
	 */
	private void initializeList() {

		this.add(new Item("Poke Ball", ItemAction.Capture, "SafariBall", true, true, false));
		this.add(new Item("Rock", ItemAction.Rock, "Rock", true, true, false));
		this.add(new Item("Bait", ItemAction.Bait, "Bait", true, true, false));
		this.add(new Item("Potion of Luck", PlayerAttribute.CaptureRate, 10, "PotionOfLuck-Capture", true, false, true));
		this.add(new Item("Pokemon Incense", PlayerAttribute.EncounterRate, 10, "PokemonIncense", true, false, true));
	}

	/**
	 * Search through the list to find {@link Item} with the same name
	 * @param name Name to be searched
	 * @return Return null if Item not found, or an {@link Item} object with the same name if found
	 */
	public static Item search(String name) {

		for(Item item : AvailableItemList.getInstance()) {
			if(item.getName().compareTo(name) == 0) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Load Images for all the items
	 */
	public static void loadImages() {

		try {
			for(Item item : AvailableItemList.getInstance()) {
				item.loadImage();
			}
		} catch (Exception e) {
			System.err.println("Fail to load Item Images");
			System.exit(-1);
		}
	}
}
