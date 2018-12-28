package View;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartingView extends BorderPane implements GUIView {

	private Label usernameLabel;
	private Button tutorialButton;

	private HBox row1, row2;
	private Button newGameButton;
	private Button loadGameButton;
	private Button settingButton;

	private Button newMultiplayerGameButton;
	private Button viewMultiplayerRoomButton;
	private Button loadMultiplayerGameButton;

	public StartingView(EventHandler<ActionEvent> handler) {

		this.usernameLabel = new Label();

		// 1st Row of Button
		this.row1 = new HBox(20);
		this.newGameButton = new Button("New Game");
		this.loadGameButton = new Button("Load Game");
		this.settingButton = new Button("Setting");

		this.newGameButton.setPrefSize(120, 80);
		this.loadGameButton.setPrefSize(120, 80);
		this.settingButton.setPrefSize(120, 80);

		this.newGameButton.setOnAction(handler);
		this.loadGameButton.setOnAction(handler);
		this.settingButton.setOnAction(handler);
		this.row1.getChildren().addAll(this.newGameButton, this.loadGameButton, this.settingButton);
		this.row1.setAlignment(Pos.CENTER);

		// 2nd Row of Button
		this.row2 = new HBox(20);
		this.newMultiplayerGameButton = new Button("New Multiplayer Game");
		this.viewMultiplayerRoomButton = new Button("View Available Multiplayer Game");
		this.loadMultiplayerGameButton = new Button("Load Multiplayer Game");

		this.newMultiplayerGameButton.setPrefSize(120, 80);
		this.viewMultiplayerRoomButton.setPrefSize(120, 80);
		this.loadMultiplayerGameButton.setPrefSize(120, 80);

		this.newMultiplayerGameButton.wrapTextProperty().setValue(true);
		this.viewMultiplayerRoomButton.wrapTextProperty().setValue(true);
		this.loadMultiplayerGameButton.wrapTextProperty().setValue(true);

		this.newMultiplayerGameButton.setOnAction(handler);
		this.viewMultiplayerRoomButton.setOnAction(handler);
		this.loadMultiplayerGameButton.setOnAction(handler);
		this.row2.getChildren().addAll(this.newMultiplayerGameButton, this.viewMultiplayerRoomButton, this.loadMultiplayerGameButton);
		this.row2.setAlignment(Pos.CENTER);

		this.tutorialButton = new Button("Tutorial");

		this.tutorialButton.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		        Stage stage = new Stage();
				stage.setTitle("My New Stage Title");
				stage.setScene(new Scene(new TutorialView(), 450, 450));
				stage.show();
		    }
		});

		HBox topHBox = new HBox();
		topHBox.setPadding(new Insets(10, 10, 10, 10));
		topHBox.setSpacing(480 - 150);
		topHBox.getChildren().addAll(this.usernameLabel, this.tutorialButton);

		this.setTop(topHBox);
		VBox vbox = new VBox(20);
		vbox.getChildren().addAll(this.row1, this.row2);
		this.setCenter(vbox);
	}

	public void setUsername(String username) {
		this.usernameLabel.setText("Welcome, " + username);
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

}
