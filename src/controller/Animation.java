package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class Animation implements EventHandler<ActionEvent> {

	protected final int ticPerAnimationCycle;
	protected int tic = 0;
	protected boolean animationRunning;

	public Animation() {
		this.ticPerAnimationCycle = 0;
		this.tic = 0;
		this.animationRunning = false;
	}

	public Animation(int numTic) {
		this.ticPerAnimationCycle = numTic;
		this.tic = 0;
		this.animationRunning = false;
	}

	public synchronized boolean isRunning() {
		return this.animationRunning;
	}

	protected synchronized void started() {
		this.animationRunning = true;
	}
	protected synchronized void finished() {
		this.animationRunning = false;
	}

	public int getTicPerAnimationCycle() {
		return this.ticPerAnimationCycle;
	}
}
