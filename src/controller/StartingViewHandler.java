package controller;

import java.util.Optional;

import controller.ViewChange.GUIViewType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import model.Game;
import model.SinglePlayerGame;

public class StartingViewHandler implements EventHandler<ActionEvent> {

	private ViewChange viewChange;

	public StartingViewHandler(ViewChange viewChange) {
		this.viewChange  = viewChange;
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		assert(arg0.getSource().getClass().toString().compareTo(Button.class.toString()) == 0);

		Button button = (Button) arg0.getSource();

		// New Game Button
		if(button.getText().compareTo("New Game") == 0) {
			this.handleNewGameButton();
		}
		// Load Game Button
		else if(button.getText().compareTo("Load Game") == 0) {
			this.handleLoadGameButton();
		}
		// Setting Button
		else if(button.getText().compareTo("Setting") == 0) {
			this.handleSettingButton();
		}
		// New Multiplayer Game Button
		else if(button.getText().compareTo("New Multiplayer Game") == 0) {
		}
		// View Multiplayer Game Button
		else if(button.getText().compareTo("View Available Multiplayer Game") == 0) {
		}
		// Load Multiplayer Game Button
		else if(button.getText().compareTo("Load Multiplayer Game") == 0) {
		}
	}

	/**
	 * Create a New Single-Player Game
	 */
	private void handleNewGameButton() {
		// Start
		// Launch Dialog prompt Player/Character name

		String name = "";

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New Game");
		dialog.setHeaderText(null);
		dialog.setContentText("Character Name:");

		Optional<String> s = dialog.showAndWait();
		if (s.isPresent()) {
			name = s.get();
			Game game = new SinglePlayerGame(name);

			// Init GUI with the newly created Game object
			this.viewChange.startGame(game, game.getPlayers().get(0));

			// Switch to MapView
			this.viewChange.switchView(GUIViewType.GUIMapView, null);
		}
	}

	/**
	 * Load a Saved Single-Player Game
	 */
	private void handleLoadGameButton() {

		// Init GUI with the loaded Game object
		if(this.viewChange.loadGame()) {
			// If successfully loaded, Switch to MapView
			this.viewChange.switchView(GUIViewType.GUIMapView, null);

		}
	}

	private void handleSettingButton() {
		;
	}

}
