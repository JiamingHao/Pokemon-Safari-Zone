package model;

public class SinglePlayerGame extends Game {

	public SinglePlayerGame(String playerName) {
		super(playerName);
		this.type = Type.SinglePlayer;
	}

	/**
	 * Check if the player win or not
	 * @return True if Win
	 */
	public boolean win() {
		return this.listOfPlayers.get(0).getInventory().getPokemons().size() > 5;
	}

	/**
	 * Check if the player lost or not
	 * @return True if lost
	 */
	public boolean lose() {
		return this.listOfPlayers.get(0).getSteps() > this.maxStep;
	}

	/**
	 * Getter
	 * @return
	 */
	public Player getPlayer() {
		return this.listOfPlayers.get(0);
	}

}
