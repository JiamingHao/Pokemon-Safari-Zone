package View;

import java.util.Observable;
import java.util.Observer;

import controller.MapRegionTransitionAnimation;
import controller.MovementAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import model.Game;
import model.GameMap;
import model.MapRegion;
import model.Player;
import model.Player.PlayerModelChangeMsg;
import model.Terrain;


/**
 * GUI view for {@link GameMap}
 *
 */
public class MapView extends Canvas implements Observer, GUIView {

	private final static int Width = 640, Height = 480;
	private Game theGame;
	private Player player;
    private GraphicsContext gc;

    private final int MaxNumGridInWidth = 18, MaxNumGridInHeight = 16;
    /**
     * Whether the view rect moves
     */
    private boolean viewRectMove;
    private int topLeftGridX, topLeftGridY;

	private Timeline movementTimeline, transitionTimeline;
	private MovementAnimation movementAnimation;
	private MapRegionTransitionAnimation transitionAnimation;

	/**
	 * Constructor
	 * @param theGame Game
	 * @param player Main Player of the MapView
	 */
    public MapView(Game theGame, Player player, MovementAnimation movementAnimation, MapRegionTransitionAnimation transitionAnimation) {

    	super(Width, Height);
    	this.gc = this.getGraphicsContext2D();

    	this.theGame = theGame;
    	this.player = player;

    	this.topLeftGridX = this.player.getX() - this.MaxNumGridInWidth / 2;
    	this.topLeftGridY = this.player.getY() - this.MaxNumGridInHeight / 2;
    	this.topLeftGridX = this.topLeftGridX > 0 ? this.topLeftGridX : 0;
    	this.topLeftGridY = this.topLeftGridY > 0 ? this.topLeftGridY : 0;

    	// Observe the Player
    	this.player.addObserver(this);

    	this.setFocusTraversable(true);

    	// Character Movement Animation
    	this.movementAnimation = movementAnimation;
		movementTimeline = new Timeline(new KeyFrame(Duration.millis(50), this.movementAnimation));
		movementTimeline.setCycleCount(this.movementAnimation.getTicPerAnimationCycle());

    	// MapRegion Transition Animation
		this.transitionAnimation = transitionAnimation;
		this.transitionTimeline = new Timeline(new KeyFrame(Duration.millis(50), this.transitionAnimation));
		this.transitionTimeline.setCycleCount(this.transitionAnimation.getTicPerAnimationCycle());

		// Draw Map
		this.drawMap();
		// Draw Character
		this.drawCharacter();
    }

    /**
     * Start the walking animation
     */
    public void startAnimation() {
    	this.movementTimeline.play();
    }

    /**
     * Whether the Animation is running or not
     * @return True if Animation is running, False otherwise
     */
    public boolean isAnimationRunning() {
    	return this.movementAnimation.isRunning() || this.transitionAnimation.isRunning();
    }

	@Override
	/**
	 * Update method, implement from {@link Observer} interface
	 */
	public void update(Observable arg0, Object arg1) {

		this.player = (Player)arg0;
		if(arg1 == null) return;

		// If only change Direction (not moving, just change facing direction)
		if((PlayerModelChangeMsg)arg1 ==  PlayerModelChangeMsg.ChangeDirection) {
			System.out.println("MapView: changeDirOnly");
			this.drawMap();
			this.drawCharacter();
		}
		// Move or Teleport
		else {

			int tempX = this.topLeftGridX;
			int tempY = this.topLeftGridY;

			this.topLeftGridX = this.player.getX() - this.MaxNumGridInWidth / 2;
			this.topLeftGridY = this.player.getY() - this.MaxNumGridInHeight / 2;
			this.topLeftGridX = this.topLeftGridX > 0 ? this.topLeftGridX : 0;
			this.topLeftGridY = this.topLeftGridY > 0 ? this.topLeftGridY : 0;

			this.viewRectMove = !(tempX == this.topLeftGridX && tempY == this.topLeftGridY);
			System.out.printf("TopLeft: %d, %d\n", this.topLeftGridX, this.topLeftGridY);
		}
		// Teleport
		if((PlayerModelChangeMsg)arg1 == PlayerModelChangeMsg.ChangeRegion) {
			System.out.println("MapView: teleport");
			System.out.printf("Region Index: %d\n", this.player.getActiveMapRegionIndex());
			System.out.printf("%d, %d\n", this.player.getX(), this.player.getY());
			this.movementTimeline.stop();
			this.movementAnimation.synchronizePosition();

			this.transitionTimeline.play();
		}

	}

	public void drawMap() {
		GameMap map = this.theGame.getMap();
		MapRegion region = map.getRegion(this.player.getActiveMapRegionIndex());

		// Draw Map
		gc.clearRect(0, 0, MapView.Width, MapView.Height);

		for(int x = 0; x < region.getWidth(); x++) {
			for(int y = 0; y < region.getHeight(); y++) {
				region.getGrid(x, y).getTerrain().draw(gc,
						(x - this.topLeftGridX) * Terrain.TileWidth,
						(y - this.topLeftGridY) * Terrain.TileHeight);
			}
		}

		// Inventory Text
		this.gc.setFill(Color.WHITE);
		this.gc.fillRect(this.Width - 210, this.Height - 50, 150, 40);
		this.gc.setFont(new Font("Serif", 25));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText("(I)nventory", this.Width - 200, this.Height - 25);

		// Step Text
		this.gc.setFill(Color.WHITE);
		//this.gc.fillRect(this.Width - 210, this.Height - 50, 150, 40);
		this.gc.setFont(new Font("Serif", 20));
		this.gc.setFill(Color.BLACK);
		this.gc.fillText("Step: " + this.player.getSteps(), 50, 25);
	}

	public void drawCharacter() {

		int xOnScreen = (this.player.getX() - this.topLeftGridX) * Terrain.TileWidth;
		int yOnScreen = (this.player.getY()+1 - this.topLeftGridY) * Terrain.TileHeight;
		System.out.printf("MapView: drawCharacter() %d, %d\n", xOnScreen, yOnScreen);
		this.player.draw(gc, 1, xOnScreen, yOnScreen);
	}

	/**
	 * Getter
	 * @return AnimateStarter that perform the Animation
	 */
	public MovementAnimation getAnimateStarter() {
		return movementAnimation;
	}

	@Override
	/**
	 * Init code to enable the view, called when switch between views
	 */
	public void enable() {
	}

	public int getTopLeftGridX() {
		return topLeftGridX;
	}

	public int getTopLeftGridY() {
		return topLeftGridY;
	}

	public boolean isViewRectMove() {
		return viewRectMove;
	}

}
