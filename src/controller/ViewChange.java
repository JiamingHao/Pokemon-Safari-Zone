package controller;

import model.Game;
import model.Player;

/**
 * Provide ability to change the view being display to user.
 * Essentially a callback interface to {@link GUI} class
 * @author JohnXu
 *
 */
public interface ViewChange {

	/**
	 * Serve as message on View Switching, pass between controller and main {@link GUI} Application
	 * @author JohnXu
	 *
	 */
	public enum GUIViewType {
		GUILoginView, GUIStartingView, GUIMapView, GUIBattleView, GUIInvenView;
	}

	/**
	 * Start a Game
	 * @param theGame The Game object created
	 * @param player The main player display on this client
	 */
	public void startGame(Game theGame, Player player);

	/**
	 * 
	 * Load a Game
	 */
	public boolean loadGame();

	/**
	 * Change the view being display to user
	 * @param view View switch into
	 * @param obj Additional argument pass to the designated View (e.g. {@link model.Pokemon} Generated to {@link View.BattleView})
	 */
	public void switchView(GUIViewType view, Object obj);

	/**
	 * Return the previous View (the one before current, null if none exist)
	 * @return Previous view
	 */
	public GUIViewType prevView();

	/**
	 * View currently being display
	 * @return Current view
	 */
	public GUIViewType currentView();
}
