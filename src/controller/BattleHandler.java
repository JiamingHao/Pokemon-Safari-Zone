package controller;

import java.util.Random;

import controller.ViewChange.GUIViewType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import mediaplayer.BGM;
import model.AvailableItemList;
import model.Item;
import model.Player;
import model.Pokemon;

/**
 * Angry makes Pokemon more likely to escape and more likely to be captured.
 * Full makes Pokemon less likely to escape and less likely to be captured.
 * @author JohnXu
 *
 */
public class BattleHandler {

	private final int maxRoundBeforeEscape = 30;
	private final int maxEatingRound = 5;
	private final double baseEscapeRate = 0.05;

	private Player player;
	private Pokemon pokemon;

	private BattleRoundStage roundStage;

	private int roundIndex;
	private int angriness;
	private boolean eating;
	private int fullness;
	private int eatingRoundCount;

	private ViewChange viewChange;
	private InventoryEventHandler invenHandler;

	private enum BattleRoundStage {
		SelectAction, BrowseInventory, ValidateAction, PerformAction, ApplyEffect, BattleEnded
	};

	public enum BattleAction {
		UsePokeBall, ThrowRock, ThrowBait, UseItem, Nothing, RunAway;
	}

	public BattleHandler(ViewChange gui) {

		this.viewChange = gui;

		this.player = null;
		this.pokemon = null;
		this.roundStage = BattleRoundStage.SelectAction;

		this.angriness = 0;
		this.fullness = 0;
		this.eating = false;
		this.eatingRoundCount = 0;
	}

	public void init(InventoryEventHandler invenHandler) {
		this.invenHandler = invenHandler;
	}


	/**
	 * Start a new battle
	 * @param player Player in the battle
	 * @param pokemon Pokemon in the battle
	 */
	public void startBattle(Player player, Pokemon pokemon) {

		this.player = player;
		if(this.pokemon == null) this.pokemon = pokemon;
		this.roundIndex = 0;
		this.roundStage = BattleRoundStage.SelectAction;
		this.angriness = 0;
		this.fullness = 0;
		this.eating = false;
		this.eatingRoundCount = 0;
	}

	/**
	 * Show the View of Inventory, allow user to use Item
	 */
	private void browseInventory() {

		this.viewChange.switchView(GUIViewType.GUIInvenView, true);
	}

	/**
	 * Player perform action in a round
	 * @param action Action to be performed
	 */
	public void roundAction(BattleAction action) {

		while(true) {
			if(! this.singleRoundAction(action)) break;
		}
	}

	private boolean singleRoundAction(BattleAction action) {

		System.out.println("BattleHandler: roundAction: " + action);
		System.out.println("BattleHandler: roundAction: " + this.roundStage);

		//if(this.roundStage == BattleRoundStage.BattleEnded) return false;

		switch(this.roundStage) {
			case SelectAction:
				if(action == BattleAction.UseItem) {
					this.roundStage = BattleRoundStage.BrowseInventory;
				}
				else {
					this.roundStage = BattleRoundStage.ValidateAction;
				}
				// Go to next stage
				return true;
			case BrowseInventory:
				this.roundStage = BattleRoundStage.ValidateAction;
				this.browseInventory();
				break;
			case ValidateAction:
				if(this.validAction(action)) {
					System.out.println("validAction: true");
					this.roundStage = BattleRoundStage.PerformAction;
					// Go to next stage
					return true;
				}
				else {
					this.roundStage = BattleRoundStage.SelectAction;
				}
				break;
			case PerformAction:
				this.roundStage = BattleRoundStage.ApplyEffect;
				this.performAction(action);
				// Go to next stage
				return true;
			case ApplyEffect:
				this.roundStage = BattleRoundStage.SelectAction;
				this.roundEnd();
				break;
			case BattleEnded:
			default:
				break;
		}
		return false;
	}

	/**
	 * Check if an action is able to be performed
	 * @param action
	 * @return
	 */
	public boolean validAction(BattleAction action) {

		boolean result = false;

		switch(action) {
		case UsePokeBall:
			result = this.checkItem("Poke Ball");
			break;
		case ThrowRock:
			result = this.checkItem("Rock");
			break;
		case ThrowBait:
			result = this.checkItem("Bait");
			break;
		case UseItem:
			//this.player.getInventory().useItem(this.itemTobeUsed);
		case Nothing:
			Item item = this.invenHandler.getItemSelected();
			// If no item is selected
			if(item == null) {
				result = false;
			}
			else {
				result = this.checkItem(item);
			}
		case RunAway:
			result = true;
			break;
		}
		return result;
	}

	/**
	 * Player perform action in a round
	 * @param action Action to be performed
	 */
	public void performAction(BattleAction action) {

		System.out.println("BattleHandler: Action: " + action.toString());

		// Perform Action
		switch(action) {
		case UsePokeBall:
			this.useItem("Poke Ball");
			break;
		case ThrowRock:
			this.useItem("Rock");
			break;
		case ThrowBait:
			this.useItem("Bait");
			break;
		case RunAway:
			System.out.println("BattleHandler: You Run Away");
			// Battle Ended, Return to MapView
			this.battleEnd();
			break;
		case UseItem:
			this.useItem(this.invenHandler.getItemSelected());
			break;
		case Nothing:
			break;
		}
	}

	/**
	 * Perform neccessary action when 1 round ended
	 */
	private void roundEnd() {

		// Check if Pokemon escapes
		// After a Max number of round, Pokemon will escape no matter what
		if(this.pokemonAttemptEscape() || this.roundIndex > this.maxRoundBeforeEscape) {

			this.pokemonEscaped();
			return;
		}

		// Decrease Angriness by the end of Round
		this.angriness = this.angriness > 0 ? this.angriness - 1 : 0;

		// Eating
		if(this.eating) {
			this.fullness += 5;
			this.eatingRoundCount++;
			if(this.eatingRoundCount >= this.maxEatingRound) this.eating = false;
		}
		// Decrease Fullness by the end of Round
		else {
			this.fullness = this.fullness > 0 ? this.fullness - 1 : 0;
		}

		System.out.println("-----------------------------BattleHandler: Round End-------------------------");
		this.roundIndex++;
	}

	/**
	 * 
	 * @return Return true if Pokemon is captured
	 */
	private boolean attemptCapturePokemon() {

		Random rand = new Random();
		// Calculate Capture Rate
		double rate = Player.baseCaptureRate + this.player.getExtraCaptureRate();

		if(this.fullness > 0.0) {
			rate *= 0.75 / (double)this.fullness + 0.15;
		}
		rate += (double)this.angriness / (1.1 * this.angriness + 10);

		// Random
		double num = rand.nextDouble();
		System.out.printf("BattleHandler: capture %f / %f\n", num, rate);

		return num < rate;
	}

	/**
	 * 
	 * @return Return true if Pokemon will escape
	 */
	private boolean pokemonAttemptEscape() {

		Random rand = new Random();
		double rate = this.baseEscapeRate;
		if(this.angriness + this.fullness != 0) {
			rate *= (this.angriness - this.fullness) / (this.angriness + this.fullness) / 4;
		}
		rate = rate > 0.0 ? rate : 0.0;
		double num = rand.nextDouble();

		return num < rate;
	}


	/**
	 * Clean up after pokemon is captured
	 */
	private void pokemonCaptured() {

		// Add Pokemon to inventory
		System.out.println("BattleHandler: Pokemon Captured");
		this.player.getInventory().addPokemon(this.pokemon);

		BGM.playPokemonCapturedSound();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pokemon");
		alert.setHeaderText("Pokemon Captured!");
		alert.setContentText("You have captured a level " +this.pokemon.getLevel()
		+ " " + this.pokemon.getSpecie().getName());
		alert.show();

		// Return to MapView
		this.battleEnd();
	}

	/**
	 * Clean up after pokemon escaped
	 */
	private void pokemonEscaped() {

		System.out.println("BattleHandler: Pokemon Escape");

		BGM.playPokemonEscapedSound();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pokemon");
		alert.setHeaderText("Pokemon Escaped!");
		alert.setContentText(this.pokemon.getSpecie().getName() + " has Escaped!");
		alert.show();

		// Return to MapView
		this.battleEnd();
	}


	private boolean useItem(String itemName) {

		return this.useItem(AvailableItemList.search(itemName));
	}

	private boolean useItem(Item item) {

		assert(item != null);

		if(this.player.getInventory().getQuantity(item) == 0) {
			return false;
		}
		this.player.getInventory().useItem(item);
		switch(item.getAction()) {
		case Capture:

			if(this.attemptCapturePokemon()) {
				this.pokemonCaptured();
			}
			break;
		case Rock:
			this.angriness += 10;
			break;
		case Bait:
			this.eating = true;
			// Reset round count if new bait is thrown
			this.eatingRoundCount = 0;
			break;
		}
		return true;
	}

	private boolean checkItem(String itemName) {
		return this.player.getInventory().getQuantity(AvailableItemList.search(itemName)) != 0;
	}

	private boolean checkItem(Item item) {
		return this.player.getInventory().getQuantity(item) != 0;
	}

	private void battleEnd() {

		this.roundIndex = 0;
		this.player = null;
		this.pokemon = null;
		this.eating = false;
		this.angriness = 0;
		this.fullness = 0;
		this.viewChange.switchView(GUIViewType.GUIMapView, null);
		this.roundStage = BattleRoundStage.BattleEnded;
	}

	public int getRoundIndex() {
		return roundIndex;
	}

	public Player getPlayer() {
		return player;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public boolean isEating() {
		return eating;
	}


}
