package controller;

import java.util.Observable;
import java.util.Observer;

import View.MapView;
import controller.ViewChange.GUIViewType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.AvailablePokemonSpecieList;
import model.Game;
import model.GameMap;
import model.MapGrid;
import model.Player;
import model.Pokemon;
import model.PokemonGenerator;
import model.Terrain;
import model.Player.PlayerModelChangeMsg;

/**
 * Handle Teleport and Pokemon Encounter
 * 
 * Observe all Player instance
 * 
 * @author JohnXu
 *
 */
public class MapEventHandler implements Observer {

	private Game theGame;
	private PokemonGenerator pokemonGen;
	private ViewChange viewChange;

	private static boolean teleported = false;

	public MapEventHandler(Game theGame, ViewChange gui) {

		this.theGame = theGame;

		this.viewChange = gui;

		this.pokemonGen = new PokemonGenerator(AvailablePokemonSpecieList.getInstance());

		// Observe all the player
		for(Player player : this.theGame.getPlayers()) {
			player.addObserver(this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		// If only Direction is changed, ignore
		if((PlayerModelChangeMsg)arg == PlayerModelChangeMsg.ChangeDirection) return;

		GameMap map = this.theGame.getMap();
		Player player = (Player) o;

		MapGrid grid = map.getRegion(player.getActiveMapRegionIndex()).getGrid(player.getX(), player.getY());

		// Portal
		if(grid.getTerrain().isPortal()) {

			System.out.println("MapEventHandler: portal");
			if(teleported) {

				teleported = false;
				return;
			}
			teleported = true;
			player.teleport(grid.getTerrain().getTargetedRegionIndex(),
					grid.getTerrain().getTargetedPortalX(), grid.getTerrain().getTargetedPortalY());
		}
		// Grass
		else if(grid.getTerrain().getType() == Terrain.TerrainType.Grass) {

			// Generate Pokemon
			Pokemon pokemon = this.pokemonGen.generate();

			// Check if encounter Pokemon or not
			if(pokemon == null) return;
			System.out.println("MapEventHandler: Pokemon Encountered");
			System.out.println("MapEventHandler: " + pokemon.getSpecie().getName() + pokemon.toString());

			this.viewChange.switchView(GUIViewType.GUIBattleView, pokemon);
		}
		// Check lost
		if(this.theGame.lose()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Pokemon");
			alert.setHeaderText("You Lost!");
			alert.setContentText("");
			alert.show();

			// Terminate GUI 
			this.viewChange.switchView(null, null);
		}
	}

}
