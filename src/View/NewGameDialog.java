package View;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Dialog popped up when User wants to create new Game
 * Ask for Character Name
 *
 */
public class NewGameDialog extends Dialog<Pair<String, String>> {

	/**
	 * Constructor
	 * Initialize the GUI component
	 * Set the result of the dialog
	 */
	public NewGameDialog() {

		this.setTitle("Create Account");
		this.setHeaderText("Create Account");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Create", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("User ID");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = this.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		this.getDialogPane().setContent(grid);

		// Request focus on the UserID field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a UserID-Password-pair when the create button is clicked.
		this.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});
	}
}
