package View;

import controller.InventoryEventHandler;
import controller.InventoryEventHandler.InventoryViewMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Inventory;


/**
 * GUI view of the {@link model.Inventory}
 * A TabPane.
 * Has 2 tab, 1 for Item, 1 for Pokemon captured
 * @author JohnXu
 *
 */
public class InventoryView extends TabPane implements GUIView {

	private Tab pokemonInventoryTab;
	private Tab itemInventoryTab;
	private PokemonInventoryTab pokemonInvenView;
	private ItemInventoryTab itemInvenView;

	/**
	 * Constructor
	 * @param inven Inventory
	 * @param keyHandler KeyHandler
	 */
	public InventoryView(Inventory inven, InventoryEventHandler invenHandler) {

		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		this.pokemonInvenView = new PokemonInventoryTab();
		this.itemInvenView = new ItemInventoryTab(inven, invenHandler);

		inven.addObserver(pokemonInvenView);
		inven.addObserver(itemInvenView);
		pokemonInvenView.update(inven, null);
		itemInvenView.update(inven, null);

		this.pokemonInventoryTab = new Tab();
		this.pokemonInventoryTab.setText("Pokemon");
		this.pokemonInventoryTab.setClosable(false);
		this.pokemonInventoryTab.setContent(pokemonInvenView);

		this.itemInventoryTab = new Tab();
		this.itemInventoryTab.setText("Item");
		this.itemInventoryTab.setClosable(false);
		this.itemInventoryTab.setContent(itemInvenView);

		this.getTabs().add(this.itemInventoryTab);
		this.getTabs().add(this.pokemonInventoryTab);

		this.setOnKeyPressed(invenHandler.getKeyEventHandler());
	}

	@Override
	/**
	 * Init code to enable the view, called when switch between views
	 */
	public void enable() {
	}



}
