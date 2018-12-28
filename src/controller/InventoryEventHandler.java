package controller;

import View.GUIView;
import controller.BattleHandler.BattleAction;
import controller.ViewChange.GUIViewType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.Game;
import model.Inventory;
import model.Item;
import model.Player;
import model.Pokemon;

public class InventoryEventHandler implements Controller {

	public enum InventoryViewMode {InBattleMode, OnMapMode}; 

	private BattleHandler battleHandler;
	private ViewChange viewChange;

	private Inventory inven;
	private InventoryViewMode mode;
	private Item itemSelected;
	private Pokemon pokemonSelected;


	private InventoryActionEventHandler actionEventHandler;
	private InventoryKeyEventHandler keyEventHandler;

	public InventoryEventHandler(Inventory inven, ViewChange viewChange) {

		this.inven = inven;
		this.viewChange = viewChange;
		this.itemSelected = null;
		this.pokemonSelected = null;

		this.actionEventHandler = new InventoryActionEventHandler();
		this.keyEventHandler = new InventoryKeyEventHandler();
	}

	public void init(BattleHandler battleHandler) {
		this.battleHandler = battleHandler;
	}

	public void selectItem(Item item) {

		if(item == null) return;
		if(this.inven.getQuantity(item) == 0) return;

		this.itemSelected = item;
		System.out.println("InventoryEventHandler: selectItem: " + item.getName());
		//this.battleHandler.roundAction(BattleAction.UseItem);
		this.viewChange.switchView(GUIViewType.GUIBattleView, null);
		this.battleHandler.roundAction(BattleAction.UseItem);
	}

	public Item getItemSelected() {
		return this.itemSelected;
	}

	public void selectPokemon(Pokemon pokemon) {
		this.pokemonSelected = pokemon;
	}

	public Pokemon getPokemonSelected() {
		return this.pokemonSelected;
	}

	public void switchMode(InventoryViewMode mode) {

		assert(mode != null);

		this.mode = mode;
	}

	public InventoryViewMode getMode() {
		return this.mode;
	}

	public EventHandler<KeyEvent> getKeyEventHandler() {
		return this.keyEventHandler;
	}

	public EventHandler<ActionEvent> getActionEventHandler() {
		return this.actionEventHandler;
	}

	/**
	 * ActionEvent Handler for InventoryView
	 * @author JohnXu
	 *
	 */
	private class InventoryActionEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
		}
	}

	/**
	 * KeyEvent Handler for InventoryView
	 * @author JohnXu
	 *
	 */
	private class InventoryKeyEventHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {

			if(event.getEventType() != KeyEvent.KEY_PRESSED) return;

			switch(event.getCode()) {
			// View Switching
			case I:
				System.out.println("InvenEventHandler: I");
				InventoryEventHandler.this.itemSelected = null;

				// Switch from non-Inventory View to inventory View
				if(InventoryEventHandler.this.viewChange.currentView() != GUIViewType.GUIInvenView) {
					InventoryEventHandler.this.viewChange.switchView(GUIViewType.GUIInvenView, false);
				}
				// Switch from Inventory View to prev-Inventory-View 
				else {
					assert(InventoryEventHandler.this.viewChange.prevView() != null);
					InventoryEventHandler.this.viewChange.switchView(InventoryEventHandler.this.viewChange.prevView(), null);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void init(Game theGame) {
	}

	@Override
	public void init(Player player) {
	}

	@Override
	public void init(Inventory inventory) {
		this.inven = inventory;
	}
}
