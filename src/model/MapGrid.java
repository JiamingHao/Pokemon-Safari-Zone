package model;

import java.io.Serializable;

import model.Terrain.TerrainType;

/**
 * Grid in the Map.
 * @author JohnXu
 *
 */
public class MapGrid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917858104507143660L;

	Terrain terrain;

	/**
	 * Constructor
	 * @param terrain Terrain of the Grid
	 */
	public MapGrid(Terrain terrain) {

		this.terrain = terrain;
	}

	/**
	 * Constructor
	 * @param terrainType Type of Terrain of the Grid
	 */
	public MapGrid(TerrainType terrainType) {

		this.terrain = new Terrain(terrainType);
	}

	/**
	 * toString
	 */
	public String toString() {

		return this.terrain.toString();
	}

	/**
	 * Getter
	 * @return {@link Terrain}
	 */
	public Terrain getTerrain() {
		return this.terrain;
	}
}
