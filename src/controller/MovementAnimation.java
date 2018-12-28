package controller;

import View.MapView;
import javafx.event.ActionEvent;
import model.Player;
import model.Terrain;

public class MovementAnimation extends Animation {

	private final double displacementX;
	private final double displacementY;
	private double x, y;
	private int frame = 0;

	private MapView mapView;
	private Player player;

	public MovementAnimation(Player player) {

		super(6);
		this.displacementX = Terrain.TileWidth / this.ticPerAnimationCycle;
		this.displacementY = Terrain.TileHeight / this.ticPerAnimationCycle;
		this.player = player;

		this.animationRunning = false;
	}

	public void init(MapView mapView) {

		this.mapView = mapView;
		this.synchronizePosition();
	}

	public void synchronizePosition() {

		this.x = (this.player.getX() - this.mapView.getTopLeftGridX()) * Terrain.TileWidth;
		this.y = (this.player.getY()+1 - this.mapView.getTopLeftGridY()) * Terrain.TileHeight;
	}

	@Override
	// This handle method gets called every 100 ms to draw the ship on a new location
	public void handle(ActionEvent event) {

		this.started();
		tic++;

		// Draw Map/background
		this.mapView.drawMap();

		// Draw Character
		switch(this.player.getDirection()) {
		case NORTH:
			if(! this.mapView.isViewRectMove())
				this.y -= this.displacementY;
			break;
		case SOUTH:
			if(! this.mapView.isViewRectMove())
				this.y += this.displacementY;
			break;
		case WEST:
			if(! this.mapView.isViewRectMove())
				this.x -= this.displacementX;
			break;
		case EAST:
			if(! this.mapView.isViewRectMove())
				this.x += this.displacementX;
			break;
		}
		// Draw Image
		this.player.draw(this.mapView.getGraphicsContext2D(), frame, this.x, this.y);

		// Increment frame
		frame = frame >= 3-2 ? 0 : frame + 1;

		// Set the pos precisely to the destination in the last tic,
		// to avoid inaccuracy due to conversion between double and int
		if(tic == this.ticPerAnimationCycle) {
			// Reset
			tic = 0;
			this.synchronizePosition();
			System.out.printf("MapView %d, %d\n", this.player.getX(), this.player.getY());

			this.finished();
		}
	}

}
