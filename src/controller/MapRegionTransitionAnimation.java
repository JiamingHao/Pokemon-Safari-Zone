package controller;

import View.MapView;
import javafx.event.ActionEvent;

import javafx.scene.paint.Color;

public class MapRegionTransitionAnimation extends Animation {

	private MapView mapView;
	
	public MapRegionTransitionAnimation() {

		super(6);
	}

	public void init(MapView mapView) {
		this.mapView = mapView;
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		this.started();

		// Before Last tic
		if(tic < this.ticPerAnimationCycle-1) {
			this.mapView.getGraphicsContext2D().setFill(
					Color.rgb(tic / this.ticPerAnimationCycle, tic / this.ticPerAnimationCycle, tic / this.ticPerAnimationCycle));
			this.mapView.getGraphicsContext2D().fillRect(0, 0, 720, 480);
			this.tic++;
		}
		// Last tic
		else {
			System.out.println("MapRegionTransitionAnimation: animation finished");
			this.mapView.drawMap();
			this.mapView.drawCharacter();
			this.tic = 0;
			this.finished();
		}
	}

}
