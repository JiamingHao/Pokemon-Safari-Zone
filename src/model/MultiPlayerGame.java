package model;

import java.util.List;

public class MultiPlayerGame extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7118259822252287178L;

	public MultiPlayerGame(String[] playerNames) {
		super(playerNames);
		this.type = Type.MultiPlayer;
	}

	/**
	 * Check if the player win or not
	 * @return True if Win, False if not-win or {@link Player} not found
	 */
	public boolean win(Player player) {

		for(Player p : this.listOfPlayers) {
			if(p.equals(player)) {
				return player.getInventory().getPokemons().size() > 5;
			}
		}
		return false;
	}

	/**
	 * Check if the player lost or not
	 * @param player
	 * @return True if lost, False if not-lose or {@link Player} not found
	 */
	public boolean lose(Player player) {
		for(Player p : this.listOfPlayers) {
			if(p.equals(player)) {
				return this.listOfPlayers.get(0).getSteps() > this.maxStep;
			}
		}
		return false;
	}

}
