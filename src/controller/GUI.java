package controller;

import View.MapView;
import View.StartingView;
import controller.InventoryEventHandler.InventoryViewMode;

import java.io.File;
import java.util.Optional;

import View.BattleView;
import View.InventoryView;
import View.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import mediaplayer.BGM;
import javafx.stage.Stage;
import model.AccountList;
import model.AvailableItemList;
import model.AvailablePokemonSpecieList;
import model.Game;
import model.Player;
import model.Pokemon;
import model.SinglePlayerGame;
import model.Terrain;


/**
 * GUI Application, entry point for the Game
 *
 */
public class GUI extends Application implements ViewChange {

	public Game theGame;
	private Player mainPlayer;

	// Controller
	private LoginViewHandler loginHandler;
	private StartingViewHandler startingViewHandler;
	@SuppressWarnings("unused")
	private MapEventHandler mapHandler;
	private BattleHandler battleHandler;
	private InventoryEventHandler invenController;
	private MapViewKeyHandler mapKeyHandler;

	// Animation
	private MovementAnimation movementAnimation;
	private MapRegionTransitionAnimation transitionAnimation;

	// Stage and Scene
	private Stage primaryStage;
	private Scene loginScene;
	private Scene startingScene;
	private Scene mapScene;
	private Scene battleScene;
	private Scene invenScene;
	private BorderPane mapPane;

	// View
	private LoginView loginView;
	private StartingView startingView;
	private MapView mapView;
	private BattleView battleView;
	private InventoryView inventoryView;

	private GUIViewType prevView;

	@Override
	/**
	 * start method for GUI App
	 */
	public void start(Stage arg0) throws Exception {

		this.primaryStage = arg0;

		AccountList accountList = new AccountList();

		// HardCoded Player Names
		String[] playerNames = new String[2];
		playerNames[0] = "Player 1";
		playerNames[1] = "Player 2";

		// Game Model
		/*
		this.theGame = new Game(playerNames);
		this.mainPlayer = this.theGame.getPlayers().get(0);
		*/

	    // Controller
		this.loginHandler = new LoginViewHandler(this, accountList);
		this.startingViewHandler = new StartingViewHandler(this);
		/*
		this.mapHandler = new MapEventHandler(theGame, this);
		this.battleHandler = new BattleHandler(this);
		this.mapKeyHandler = new MapViewKeyHandler(this.theGame, this.mainPlayer, this);
		this.invenController = new InventoryEventHandler(this.mainPlayer.getInventory(), this);
		*/

		// Login Scene
		this.loginView = new LoginView(this.loginHandler);
		this.loginScene = new Scene(this.loginView, 320, 150);

		// Starting Scene
		this.startingView = new StartingView(this.startingViewHandler);
		this.startingScene = new Scene(this.startingView, 480, 250);

	    // Controller init
	    this.loginHandler.init(this.loginView, this.startingView);

		this.primaryStage.setTitle("Pokemon Game - Safari Zone");
		this.primaryStage.setScene(this.loginScene);

		this.primaryStage.show();
	}

	@Override
	/**
	 * Stop Method for the GUI application
	 * Called before application is closed
	 */
	public void stop() {
		// System Persistence
		if(this.theGame == null) return;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Save Confirmation");
		alert.setHeaderText("Save Confirmation");
		alert.setContentText("Do you want to save the game?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Persistence.saveToFile(this.theGame);
		} else {
		}

	}

	@Override
	/**
	 * Init GUI to start a new Game, and Switch the GUIMapView, implement from @link{ViewChange} interface
	 */
	public void startGame(Game theGame, Player player) {

		this.theGame = theGame;
		this.mainPlayer = player;

	    // Controller
		this.mapHandler = new MapEventHandler(theGame, this);
		this.battleHandler = new BattleHandler(this);
		this.mapKeyHandler = new MapViewKeyHandler(this.theGame, this.mainPlayer, this);
		this.invenController = new InventoryEventHandler(this.mainPlayer.getInventory(), this);

		// Animation
		this.movementAnimation = new MovementAnimation(this.mainPlayer);
		this.transitionAnimation = new MapRegionTransitionAnimation();

		// MapView / Primary Scene
	    this.mapView = new MapView(this.theGame, this.mainPlayer, this.movementAnimation, this.transitionAnimation);
		this.mapView.setOnKeyPressed(this.mapKeyHandler);
		this.mapView.setOnKeyReleased(this.mapKeyHandler);
	    mapPane = new BorderPane();
		mapPane.setCenter(mapView);
		mapScene = new Scene(mapPane, 640, 480);

	    // Battle Scene
	    this.battleView = new BattleView(this.battleHandler);
	    this.battleScene = new Scene(this.battleView, 640, 480);

	    // Inventory Scene
	    this.inventoryView = new InventoryView(this.mainPlayer.getInventory(), this.invenController);
	    BorderPane invenPane = new BorderPane();
	    invenPane.setCenter(this.inventoryView);
	    this.invenScene = new Scene(invenPane, 640, 480);

	    // Controller init
		this.mapKeyHandler.init(this.mapView);
		this.battleHandler.init(this.invenController);
		this.invenController.init(this.battleHandler);

		this.movementAnimation.init(mapView);
		this.transitionAnimation.init(mapView);
	}

	@Override
	public boolean loadGame() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("SavedFile", "*.data"),
				new ExtensionFilter("All Files", "*.*"));
		File selectedFile = fileChooser.showOpenDialog(this.primaryStage);

		if(selectedFile == null) return false;

		Game game = Persistence.restoreFromFile(selectedFile);

		if(game == null) return false;
		if(!(game instanceof SinglePlayerGame)) return false;

		this.startGame(game, game.getPlayers().get(0));
		return true;

	}

	@Override
	/**
	 * Switch the view displaying to user, implement from @link{ViewChange} interface
	 */
	public void switchView(GUIViewType view, Object obj) {

		if(view == null) {
			this.mapView.setDisable(false);
			this.battleView.setDisable(false);
			this.inventoryView.setDisable(false);
			this.mapView.setOnKeyPressed(null);
			this.mapView.setOnKeyReleased(null);
			return;
		}

		// Store previous view before switching
		this.prevView = this.currentView();

		switch(view) {

		case GUILoginView:
			// Disable and Enable view
			this.loginView.enable();

			this.primaryStage.setScene(this.loginScene);
			break;
		case GUIStartingView:
			// Disable and Enable view
			this.startingView.enable();

			this.primaryStage.setScene(this.startingScene);
			break;
		case GUIMapView:
			// Disable and Enable view
			this.battleView.disable();
			this.mapView.enable();

			this.primaryStage.setScene(this.mapScene);
			break;
		case GUIBattleView:
			// Start a new battle (When switched from MapView)
			if(this.currentView() == GUIViewType.GUIMapView) {
				this.battleHandler.startBattle(this.mainPlayer, (Pokemon)obj);
			}

			// Disable and Enable view
			this.battleView.enable();
			this.primaryStage.setScene(this.battleScene);
			break;
		case GUIInvenView:
			// Disable and Enable view
			this.inventoryView.enable();

			// Determine which mode of InventoryView switching into
			if(this.currentView() == GUIViewType.GUIBattleView) {
				this.invenController.switchMode(InventoryViewMode.InBattleMode);
			}
			else if(this.currentView() == GUIViewType.GUIMapView) {
				this.invenController.switchMode(InventoryViewMode.OnMapMode);
			}

			this.primaryStage.setScene(this.invenScene);
			break;
		}
	}

	@Override
	/**
	 * implement from @link{ViewChange} interface
	 */
	public GUIViewType prevView() {

		return this.prevView;
	}


	@Override
	/**
	 * Get which view is currently displaying, implement from @link{ViewChange} interface
	 */
	public GUIViewType currentView() {
		if(this.primaryStage.getScene().equals(this.mapScene)) {
			return GUIViewType.GUIMapView;
		}
		else if(this.primaryStage.getScene().equals(this.battleScene)) {
			return GUIViewType.GUIBattleView;
		}
		else if(this.primaryStage.getScene().equals(this.invenScene)) {
			return GUIViewType.GUIInvenView;
		}
		return null;
	}

	/**
	 * Main method
	 * @param args cmd line argument
	 */
	public static void main(String[] args) {

		// Image Loading
    	Terrain.loadImages();
    	Player.loadImages();
    	AvailablePokemonSpecieList.loadImages();
    	AvailableItemList.loadImages();
    	BGM.loadSounds();

    	// Launch GUI
		GUI.launch(args);
	}




}
