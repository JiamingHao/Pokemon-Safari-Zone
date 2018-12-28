package model;

import java.io.Serializable;
import java.util.Random;

/**
 * A Region/part of the entire map
 * @author JohnXu
 *
 */
public class MapRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1194408115706456751L;

	private final int width, height;
	private MapGrid[][] map;

	/**
	 * Array of Position/Coordinate for Exit.
	 * e.g. exitPositions[0][0] is the x pos of the 1st Exit.
	 * e.g. exitPositions[0][1] is the y pos of the 1st Exit.
	 */
	private int portalPositions[][];

	/**
	 * Constructor
	 * @param width Width of the region, in the unit number of grid
	 * @param height Height of the region, in the unit number of grid
	 */
	public MapRegion(int width, int height) {

		this.width = width;
		this.height = height;
		this.map = new MapGrid[width][height];
	}

	/**
	 * Constructor
	 * @param width Width of the region, in the unit number of grid
	 * @param height Height of the region, in the unit number of grid
	 * @param numPortal Number of portal in this MapRegion
	 */
	public MapRegion(int width, int height, int numPortal) {

		this.width = width;
		this.height = height;
		this.map = new MapGrid[width][height];
		this.portalPositions = new int[numPortal][2];
	}

	/**
	 * Generate a map of HUB, as a starting point for players
	 * @return MapGrid that is the Exit of the hub
	 */
	public MapGrid generateHub() {

		this.portalPositions = new int[1][2];

		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				this.map[i][j] = null;
			}
		}

		// Wall on 4 sides
		for(int i = 0; i < this.width; i++) {

			this.map[i][0] = new MapGrid(Terrain.TerrainType.Wall);

			if( i != this.width / 2) {
				this.map[i][this.height - 1] = new MapGrid(Terrain.TerrainType.Wall);
			}
			else {
				this.portalPositions[0][0] = i;
				this.portalPositions[0][1] = this.height - 1;
				this.map[i][this.height - 1] = new MapGrid(Terrain.TerrainType.InnerGate);
			}
		}
		for(int j = 1; j < this.height - 1; j++) {
			this.map[0][j] = new MapGrid(Terrain.TerrainType.Wall);
			this.map[this.width - 1][j] = new MapGrid(Terrain.TerrainType.Wall);
		}

		// Floor in the middle
		for(int i = 1; i < this.width - 1; i++) {
			for(int j = 1; j < this.height - 1; j++) {

				this.map[i][j] = new MapGrid(Terrain.TerrainType.Floor);
			}
		}
		return this.map[this.width / 2][this.height - 1];
	}

	/**
	 * Generate a wild region (which player can encounter Pokemon)
	 */
	public void generateRegion() {

		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				this.map[i][j] = null;
			}
		}

		// Wall on 4 sides
		for(int i = 0; i < this.width; i++) {

			this.map[i][0] = new MapGrid(Terrain.TerrainType.Wall);
			this.map[i][this.height - 1] = new MapGrid(Terrain.TerrainType.Wall);
		}
		for(int j = 1; j < this.height - 1; j++) {
			this.map[0][j] = new MapGrid(Terrain.TerrainType.Wall);
			this.map[this.width - 1][j] = new MapGrid(Terrain.TerrainType.Wall);
		}

		Random rand = new Random();

		// Random Grass or Ground in the middle
		for(int i = 1; i < this.width - 1; i++) {
			for(int j = 1; j < this.height - 1; j++) {

				int r = rand.nextInt(2);
				this.map[i][j] = new MapGrid(r == 0 ? Terrain.TerrainType.Grass: Terrain.TerrainType.Ground);
			}
		}

		// Random 5 water pond
		for(int num = 0; num < 8; num++) {
			int pondWidth = rand.nextInt(this.width / 10) + 2;
			int pondHeight = rand.nextInt(this.height / 10) + 2;
			int pondX = rand.nextInt(this.width - pondWidth);
			int pondY = rand.nextInt(this.height - pondHeight);

			for(int i = 0; i < pondWidth; i++) {
				for(int j = 0; j < pondHeight; j++) {
					this.map[pondX + i][pondY + j] = new MapGrid(Terrain.TerrainType.Water);
				}
			}
		}

	}

	/**
	 * Add a portal to the MapRegion 
	 * @param x X coord of the Portal
	 * @param y Y coord of the Portal
	 * @param targetedRegionIndex Index of the region the portal will teleport player to
	 * @param targetedX X coord in the designated region which player will landed on after teleport
	 * @param targetedY Y coord in the designated region which player will landed on after teleport
	 */
	public void addPortal(int x, int y, int targetedRegionIndex, int targetedX, int targetedY) {

		this.map[x][y] = new MapGrid(Terrain.TerrainType.OutterGate);
		this.map[x][y].getTerrain().setTargetedRegionIndex(targetedRegionIndex);
		this.map[x][y].getTerrain().setTargetedPos(targetedX, targetedY);

		this.map[x-1][y] = new MapGrid(Terrain.TerrainType.Ground);
		this.map[x+1][y] = new MapGrid(Terrain.TerrainType.Ground);
		this.map[x][y-1] = new MapGrid(Terrain.TerrainType.Ground);
		this.map[x][y+1] = new MapGrid(Terrain.TerrainType.Ground);

		this.map[x-1][y-1] = new MapGrid(Terrain.TerrainType.Ground);
		this.map[x+1][y+1] = new MapGrid(Terrain.TerrainType.Ground);
		this.map[x-1][y+1] = new MapGrid(Terrain.TerrainType.Ground);
		this.map[x+1][y-1] = new MapGrid(Terrain.TerrainType.Ground);

	}

	/**
	 * Check if a move in the {@link Direction} of the {@link Player} is valid in terms of {@link Terrain}
	 * @param player Player to be checked
	 * @return True if move is valid, False otherwise
	 */
	public boolean vaildMove(Player player) {

		int x = player.getX();
		int y = player.getY();
		Direction dir = player.getDirection();

		switch(dir) {
		case NORTH:
			y--;
			break;
		case SOUTH:
			y++;
			break;
		case WEST:
			x--;
			break;
		case EAST:
			x++;
			break;
		}
		if(x < 0 || x >= this.width || y < 0 || y >= this.height) return false;

		return ! this.map[x][y].terrain.obstructed();
	}

	/**
	 * Getter
	 * @param x X coord of the Grid in the region
	 * @param y Y coord of the Grid in the region
	 * @throws java.lang.ArrayIndexOutOfBoundsException if x or y are out of bound
	 * @return {@link MapGrid} at the given coord
	 */
	public MapGrid getGrid(int x, int y) {
		return this.map[x][y];
	}

	/**
	 * Getter
	 * @return Width of the region
	 */
	public int getWidth() {
		return this.map.length;
	}

	/**
	 * Getter
	 * @return Height of the region
	 */
	public int getHeight() {
		return this.map[0].length;
	}

	/**
	 * Getter
	 * @param index Index of the portal to be looked up
	 * @return int array represent x-y coord of the portal, array[0] is the x-coord, array[1] is the y-coord
	 */
	public int[] getPortalPosition(int index) {
		return portalPositions[0];
	}

	/**
	 * toString
	 */
	public String toString() {

		String str = "";

		for(int i = 0; i < this.width; i++) {
			for(int j = 0; j < this.height; j++) {
				str += this.map[i][j].toString() + "\t";
			}
			str += "\n";
		}
		return str;
	}

}
