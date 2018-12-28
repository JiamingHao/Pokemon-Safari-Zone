package model;

import java.util.List;
import java.util.Random;

/**
 * Use to generate {@link Pokemon} (for player to encounter) when player make a moves in certain Terrain
 * @author JohnXu
 * @author haotianyuan
 *
 */
public class PokemonGenerator {

	private Random rand;
	private List<PokemonSpecie> list;

	/**
	 * Take in a list of all available Pokemon
	 * @param list List of all available Pokemon
	 */
	public PokemonGenerator(List<PokemonSpecie> list) {

		this.list = list;
	}

	/**
	 * Generate a Pokemon (or none) for Player to battle with when in certain Terrain(e.g. Grass)
	 * @return null when no Pokemon Generated/Encountered
	 */
	public Pokemon generate() {

		this.rand = new Random();
		if(rand.nextDouble() > Player.baseEncounterRate) {
			return null;
		}

		double encounterFactorSum = 0;
		for(PokemonSpecie specie : this.list) {
			encounterFactorSum += specie.getEncounterFactor();
		}
		int index = rand.nextInt((int) (encounterFactorSum * 100));

		encounterFactorSum = 0;
		for(PokemonSpecie specie : this.list) {
			encounterFactorSum += specie.getEncounterFactor();
			if(encounterFactorSum * 100 > index) return specie.createPokemon();
		}

		return list.get(list.size() - 1).createPokemon();
	}
	
}
