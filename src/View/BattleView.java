package View;

import controller.BattleHandler;
import controller.BattleHandler.BattleAction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * A view of the Battle between {@link model.Player} and wild {@link model.Pokemon}
 * @author JohnXu
 *
 */
public class BattleView extends BorderPane implements GUIView {

	private final int Width = 640, Height = 400;
	private BattleHandler battleHandlder;

	private Button throwPokeBallButton, throwRockButton, throwBaitButton;
	private Button useItemButton;
	private Button doNothingButton, runAwayButton;
	private Label roundIndexLabel;
	private Label playerStatusLabel;
	private Label pokemonStatusLabel;

	private Canvas canvas;
    private GraphicsContext gc;

    /**
     * Constructor
     * @param battleHandlder BattleHandler that handles the battle logic for the battle that this view is showing
     */
	public BattleView(BattleHandler battleHandlder) {
		super();

		this.battleHandlder = battleHandlder;

		this.throwPokeBallButton = new Button("Use Poke Ball");
		this.throwRockButton = new Button("Use Rock");
		this.throwBaitButton = new Button("Use Bait");
		this.useItemButton = new Button("Use Other Item");
		this.doNothingButton = new Button("Do Nothing");
		this.runAwayButton = new Button("Run Away");

		ButtonHandler bh = new ButtonHandler();
		this.throwPokeBallButton.setOnAction(bh);
		this.throwRockButton.setOnAction(bh);
		this.throwBaitButton.setOnAction(bh);
		this.useItemButton.setOnAction(bh);
		this.doNothingButton.setOnAction(bh);
		this.runAwayButton.setOnAction(bh);

		this.roundIndexLabel = new Label("Round " + 1);
		this.playerStatusLabel = new Label("");
		this.pokemonStatusLabel = new Label("");

		HBox statusLabelBox = new HBox(300);
		statusLabelBox.getChildren().addAll(this.playerStatusLabel, this.pokemonStatusLabel);

		HBox buttonBox = new HBox(20);
		buttonBox.getChildren().addAll(this.throwPokeBallButton, this.throwRockButton, this.throwBaitButton);
		buttonBox.getChildren().addAll(this.useItemButton);
		buttonBox.getChildren().addAll(this.doNothingButton, this.runAwayButton);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(statusLabelBox, buttonBox);

		this.canvas = new Canvas(this.Width, this.Height);
		this.gc = canvas.getGraphicsContext2D();

		this.setTop(this.roundIndexLabel);
		this.setCenter(canvas);
		this.setBottom(vbox);
	}

	/**
	 * Init code to enable the view, called when switch between views
	 */
	public void enable() {

		this.roundIndexLabel.setText("Round " + (this.battleHandlder.getRoundIndex() + 1));
		this.playerStatusLabel.setText("");
		this.pokemonStatusLabel.setText("");
		this.gc.clearRect(0, 0, this.Width, this.Height);
		this.drawCharacter();
		this.drawPokemon();
	}

	/**
	 * Clean up code to disable the view, called when switch between views
	 */
	public void disable() {

		this.gc.clearRect(0, 0, 720, 640);
		this.playerStatusLabel.setText("");
		this.pokemonStatusLabel.setText("");
	}

	private class ButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			Button button = (Button)event.getSource();
			BattleView.this.playerStatusLabel.setText("");

			BattleAction action = null;
			if(button.equals(BattleView.this.throwPokeBallButton)) {
				action = BattleAction.UsePokeBall;
			}
			else if(button.equals(BattleView.this.throwRockButton)) {
				action = BattleAction.ThrowRock;
			}
			else if(button.equals(BattleView.this.throwBaitButton)) {
				action = BattleAction.ThrowBait;
			}
			else if(button.equals(BattleView.this.useItemButton)) {
				action = BattleAction.UseItem;
			}
			else if(button.equals(BattleView.this.doNothingButton)) {
				action = BattleAction.Nothing;
			}
			else if(button.equals(BattleView.this.runAwayButton)) {
				action = BattleAction.RunAway;
			}

			/*
			// Check if the action is valid
			if(! BattleView.this.battleHandlder.validAction(action)) {
				BattleView.this.playerStatusLabel.setText("Invalid Action");
				return;
			}
			*/

			// Update Player Status Label according to action
			switch(action) {
			case UsePokeBall:
				BattleView.this.playerStatusLabel.setText("Use Poke Ball");
				break;
			case ThrowRock:
				BattleView.this.playerStatusLabel.setText("Throw Rock, Pokemon is more angry");
				break;
			case ThrowBait:
				BattleView.this.playerStatusLabel.setText("Throw Bait, Pokemon is eating");
				break;
			}

			// Battle Round
			/*
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			    System.err.println(ste);
			}
			*/
			BattleView.this.battleHandlder.roundAction(action);

			// Update Pokemon Status Label
			if(BattleView.this.battleHandlder.isEating()) {
				BattleView.this.pokemonStatusLabel.setText("Eating");
			}

			// Update Round Count Label
			BattleView.this.roundIndexLabel.setText("Round " + (BattleView.this.battleHandlder.getRoundIndex() + 1));
		}
	}

	/**
	 * Draw a large image of the Player character
	 */
	public void drawCharacter() {
		assert(this.battleHandlder.getPlayer() != null);
		this.battleHandlder.getPlayer().drawLargeImage(this.gc, 50, 200);
	}

	/**
	 * Draw a large image of the Pokemon
	 */
	public void drawPokemon() {
		System.out.println("BattleView: draw Pokemon");
		System.out.println("BattleView: "+ this.battleHandlder.getPokemon().getSpecie().getName());
		assert(this.battleHandlder.getPokemon() != null);
		this.gc.fillText("lv. " + this.battleHandlder.getPokemon().getLevel(), 400, 20);
		this.battleHandlder.getPokemon().getSpecie().drawLargeImage(this.gc, 400, 50);
	}

}
