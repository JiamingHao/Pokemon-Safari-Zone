package model;

import java.io.Serializable;

/**
 * Instance of this class represent a Pokemon entity, rather than a Pokemon species.
 *
 */
public class Pokemon implements Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 4014748425416602618L;

	private PokemonSpecie specie;
	private int level;

	/**
	 * Constructor
	 * @param specie {@link PokemonSpecie} of the Pokemon
	 * @param level Level of the Pokemon
	 */
	public Pokemon(PokemonSpecie specie, int level) {

		this.specie = specie;
		this.level = level;
	}

	/**
	 * Getter
	 * @return {@link PokemonSpecie} of the Pokemon
	 */
	public PokemonSpecie getSpecie() {
		return specie;
	}

	/**
	 * Getter
	 * @return Level of the Pokemon
	 */
	public int getLevel() {
		return level;
	}
	
}
