package model;

import java.io.Serializable;

/**
 * The Entire Map, contains multiple region/parts
 *
 */
public class GameMap implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -551337250252662948L;

	public static final int HubRegionWidth = 15;
	public static final int HubRegionHeight = 15;

	private MapRegion[] regions;

	/**
	 * Constructor
	 * @param numOfRegion Number of Region contained in this map
	 */
	public GameMap(int numOfRegion) {

		assert(numOfRegion >= 2);
		this.regions = new MapRegion[numOfRegion];
	}

	/**
	 * Init map
	 */
	public void initMap() {

		// Region 0 is the starting point for all players
		this.regions[0] = new MapRegion(GameMap.HubRegionWidth, GameMap.HubRegionHeight);
		MapGrid exit = this.regions[0].generateHub();
		exit.getTerrain().setTargetedRegionIndex(1);
		exit.getTerrain().setTargetedPos(10, 10);

		// Rest of MapRegion are randomly generated
		for(int i = 1; i < this.regions.length; i++) {
			this.regions[i] = new MapRegion(100, 100);
			this.regions[i].generateRegion();
			if(i == 1) {
				int pos[] = this.regions[0].getPortalPosition(0);
				assert(pos != null);
				this.regions[i].addPortal(10, 10, 0, pos[0], pos[1]);
			}
		}
	}

	/**
	 * Get the {@link MapRegion} with the given index
	 * @param index Index of the {@link MapRegion}
	 * @return {@link MapRegion} object
	 */
	public MapRegion getRegion(int index) {

		return this.regions[index];
	}
	

}
