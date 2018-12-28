package model;

import java.util.Map;
import java.util.Observable;
import java.util.function.BiFunction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Inventory for Player.
 * Observed by {@link View.ItemInventoryTab} and {@link View.PokemonInventoryTab} 
 * @author JohnXu
 *
 */
public class Inventory extends Observable implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -2227485368320477985L;

	private List<Pokemon> pokemons;

	/**
	 * Key is the Item, Value is the Count 
	 */
	private Map<Item, Integer> items;

	/**
	 * Constructor
	 */
	public Inventory() {

		this.pokemons = new ArrayList<Pokemon>();
		this.items = new HashMap<Item, Integer>();
	}

	/**
	 * Give player the initial game item, including Pokeball, Rock, Bait
	 */
	public void initializeInventory() {

		this.items.put(AvailableItemList.search("Poke Ball"), 30);
		this.items.put(AvailableItemList.search("Rock"), 10);
		this.items.put(AvailableItemList.search("Bait"), 10);
		this.items.put(AvailableItemList.search("Potion of Luck"), 1);
	}

	/**
	 * Add a {@link Pokemon} to inventory
	 * @param p {@link Pokemon} to be added
	 */
	public void addPokemon(Pokemon p) {

		System.out.println("Inventory: add pokemon");
		this.pokemons.add(p);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Remove a {@link Pokemon} from inventory
	 * @param p {@link Pokemon} to be removed
	 */
	public void deletePokemon(Pokemon p) {

		this.pokemons.remove(p);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Add a {@link Item} to inventory
	 * @param item {@link Item} to be added
	 */
	public void addItem(Item item) {

		BiFunction<Integer, Integer, Integer> incrementCount = (x, y) -> {      
			return x + y;
		};
		this.items.merge(item, 1, incrementCount);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * 
	 * @param item Item to be used
	 * @return whether or not the item exist in Inventory
	 */
	public boolean useItem(Item item) {

		// If Item do not exist, Do Nothing
		if(! this.items.containsKey(item)) return false;

		int oldCount = this.items.get(item);

		if(oldCount > 1) {
			this.items.put(item, oldCount-1);
		}
		// If only 1 instance of Item left, remove it
		else {
			this.items.remove(item);
		}
		this.setChanged();
		this.notifyObservers();
		return true;
	}

	/**
	 * Return the quantity of the item in the Inventory
	 * @return Quantity of the item or 0 if item not exist in Inventory
	 */
	public int getQuantity(Item item) {

		// If Item do not exist, Do Nothing
		if(! this.items.containsKey(item)) return 0;

		return this.items.get(item);
	}

	/**
	 * Remove a {@link Item} from inventory
	 * @param item {@link Item} to be removed
	 */
	public void deleteItem(Item item) {

		this.items.remove(item);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Get list of {@link Pokemon} in Inventory
	 * @return list of {@link Pokemon}
	 */
	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	/**
	 * Get the collection of all of {@link Item} in Inventory
	 * @return Map object, Key is the Item, Value is the Count 
	 */
	public Map<Item, Integer> getItems() {
		return items;
	}


}
