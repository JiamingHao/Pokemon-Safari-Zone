package controller;

import View.GUIView;
import View.MapView;
import controller.ViewChange.GUIViewType;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Direction;
import model.Game;
import model.Inventory;
import model.MapRegion;
import model.Player;

/**
 * Main KeyHandler for the Appilcation.
 * Handle {@link model.Player} movement and {@link View.InventoryView} on/off
 * @author JohnXu
 *
 */
public class MapViewKeyHandler implements EventHandler<KeyEvent>, Controller {

	private boolean released = false;
	private Game theGame;
	private Player player;
	private ViewChange viewChange;
	private MapView mapView;

	/**
	 * Constructor
	 * @param theGame Core Game model
	 * @param player Main Player of this instance of Application
	 * @param mapView View of the Map
	 * @param viewChange Provide ability to change the view being display
	 */
	public MapViewKeyHandler(Game theGame, Player player, ViewChange viewChange) {

		this.theGame = theGame;
		this.player = player;
		this.viewChange = viewChange;
	}

	public void init(MapView mapView) {
		this.mapView = mapView;
	}

	@Override
	/**
	 * Handling method for Key Event, implement from EventHandler<KeyEvent>
	 */
	public void handle(KeyEvent event) {

		if(event.getCode() == KeyCode.I) {
			this.handleInventoryKeyEvent(event);
			return;
		}

		// Player Movement
		this.handlePlayerMovement(event);
	}

	private void handleInventoryKeyEvent(KeyEvent event) {

		if(event.getCode() == KeyCode.I && event.getEventType() == KeyEvent.KEY_PRESSED) {
			System.out.println("KeyHandler: I");
			if(this.viewChange.currentView() != GUIViewType.GUIInvenView) {
				this.viewChange.switchView(GUIViewType.GUIInvenView, false);
			}
			else {
				assert(this.viewChange.prevView() != null);
				this.viewChange.switchView(this.viewChange.prevView(), null);
			}
			return;
		}
	}

	private void handlePlayerMovement(KeyEvent event) {

		this.released = (event.getEventType() == KeyEvent.KEY_RELEASED);
		if(! this.released) return;

		// If the Animation is still running, ignore the KeyEvent
		if(this.mapView.isAnimationRunning()) return;

		// Arrow Key for Direction of Player 
		Direction dir = null;
		switch(event.getCode()) {
		case UP:
			dir = Direction.NORTH;
			break;
		case DOWN:
			dir = Direction.SOUTH;
			break;
		case LEFT:
			dir = Direction.WEST;
			break;
		case RIGHT:
			dir = Direction.EAST;
			break;
		default:
			return;
		}

		// Set the Direction of the Player first
		this.player.setDirection(dir);

		// Get the MapRegion that the Player is currently in
		MapRegion region = theGame.getMap().getRegion(this.player.getActiveMapRegionIndex());

		// If invalid move, then only change direction 
		if(! region.vaildMove(this.player)) return;

		// Player
		this.mapView.startAnimation();

		this.player.move(dir);
	}

	@Override
	public void init(Game theGame) {
		this.theGame = theGame;
	}

	@Override
	public void init(Player player) {
		this.player = player;
	}

	@Override
	public void init(Inventory inventory) {
	}
}