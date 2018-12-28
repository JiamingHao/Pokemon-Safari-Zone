package model;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represent Terrain of a MapGrid
 * @author JohnXu
 *
 */
public class Terrain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5814482864008571554L;

	/**
	 * Enum of Terrain type
	 * @author JohnXu
	 *
	 */
	public enum TerrainType {
		Grass, Water, Ground, Floor, Sand, Fence, Wall,
		// Teleport to another region
		InnerGate, OutterGate;
	}
	private TerrainType type;

	/**
	 * Width and Height of the Tile Image
	 */
	public static final int TileWidth = 40, TileHeight = 40;

	/**
	 * Specify the designated {@link MapRegion} to Teleport
	 */
	private int targetedRegionIndex;

	/**
	 * Specify the position of the designated portal in the designated {@link MapRegion} to Teleport
	 */
	private int targetedPortalX, targetedPortalY;

	private static Image tileSet[];

	/**
	 * Constructor
	 */
	public Terrain() {
		this.targetedRegionIndex = 0;
	}

	/**
	 * 
	 * Constructor
	 * @param type Type of Terrain
	 */
	public Terrain(TerrainType type) {
		this.targetedRegionIndex = 0;
		this.type = type;
	}

	/**
	 * Load Image of all the Terrain
	 */
	public static void loadImages() {

		try {
			Terrain.tileSet = new Image[10];
			Terrain.tileSet[0] = new Image("file:src/Images/Grass.png",false);
			Terrain.tileSet[1] = new Image("file:src/Images/Water.png",false);
			Terrain.tileSet[2] = new Image("file:src/Images/Ground.png",false);
			Terrain.tileSet[3] = new Image("file:src/Images/Floor.png",false);
			Terrain.tileSet[4] = new Image("file:src/Images/Fence.png",false);
			Terrain.tileSet[5] = new Image("file:src/Images/Wall.png",false);
			Terrain.tileSet[6] = new Image("file:src/Images/InnerGate.png",false);
			Terrain.tileSet[7] = new Image("file:src/Images/OutterGate.png",false);
			Terrain.tileSet[8] = new Image("file:src/Images/Sand.png",false);
		} catch(Exception e) {
			System.err.println("Fail to load Terrain Images");
			System.exit(-1);
		}
	}

	/**
	 * Draw the Terrain
	 * @param gc GraphicsContext
	 * @param x X coord on Screen
	 * @param y Y coord on Screen
	 */
	public void draw(GraphicsContext gc, int x, int y) {
		switch(this.type) {
		case Grass:
			gc.drawImage(tileSet[0], x, y);;
			break;
		case Water:
			gc.drawImage(tileSet[1], x, y);;
			break;
		case Ground:
			gc.drawImage(tileSet[2], x, y);;
			break;
		case Floor:
			gc.drawImage(tileSet[3], x, y);;
			break;
		case Fence:
			gc.drawImage(tileSet[4], x, y);;
			break;
		case Wall:
			gc.drawImage(tileSet[5], x, y);;
			break;
		case InnerGate:
			gc.drawImage(tileSet[6], x, y);;
			break;
		case OutterGate:
			gc.drawImage(tileSet[7], x, y);;
			break;
		case Sand:
			gc.drawImage(tileSet[8], x, y);;
			break;
		}
	}

	/**
	 * @return Whether the Terrain is an Obstruction (Player cannot walk through/step on)
	 */
	public boolean obstructed() {

		switch(this.type) {
		case Wall:
		case Water:
		case Fence:
			return true;
		default:
			return false;
		}
	}

	/**
	 * @return Whether the Terrain is an Portal (Player will be teleport to another {@link MapRegion} when step on it)
	 */
	public boolean isPortal() {

		switch(this.type) {
		case OutterGate:
		case InnerGate:
			return true;
		default:
			return false;
		}
	}

	/**
	 * toString
	 */
	public String toString() {

		switch(this.type) {
		case Grass:
			return "Grass";
		case Ground:
			return "Ground";
		case Water:
			return "Water";
		case Floor:
			return "Floor";
		case Fence:
			return "Fence";
		case Wall:
			return "Wall";
		case InnerGate:
			return "InnerGate";
		case OutterGate:
			return "OutterGate";
		case Sand:
			return "OutterGate";
		default:
			return "";
		}
	}

	/**
	 * Getter
	 * @return Index of the Designated MapRegion of the Portal Terrain
	 */
	public int getTargetedRegionIndex() {
		return this.targetedRegionIndex;
	}

	/**
	 * Setter
	 * @param index Index of the Designated MapRegion of the Portal Terrain
	 */
	public void setTargetedRegionIndex(int index) {
		this.targetedRegionIndex = index;
	}

	/**
	 * Getter
	 * @return X coord of the landing Position in the Designated MapRegion of the Portal Terrain
	 */
	public int getTargetedPortalX() {
		return targetedPortalX;
	}

	/**
	 * Getter
	 * @return Y coord of the landing Position in the Designated MapRegion of the Portal Terrain
	 */
	public int getTargetedPortalY() {
		return targetedPortalY;
	}

	/**
	 * Setter
	 * @param x X coord of the landing Position in the Designated MapRegion of the Portal Terrain
	 * @param y Y coord of the landing Position in the Designated MapRegion of the Portal Terrain
	 */
	public void setTargetedPos(int x, int y) {
		this.targetedPortalX = x;
		this.targetedPortalY = y;
	}

	/**
	 * Getter
	 * @return The type of the Terrain in enum constant
	 */
	public TerrainType getType() {
		return type;
	}

	/**
	 * Setter
	 * @param type The type of the Terrain in enum constant
	 */
	public void setType(TerrainType type) {
		this.type = type;
	}


}
