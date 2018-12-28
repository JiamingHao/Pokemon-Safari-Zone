package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView extends BorderPane implements GUIView {

	private TextField accountField;
	private PasswordField passwordField;
	private Label accountLabel;
	private Label passwordLabel;
	private Label statusLabel;
	private Button loginButton;

	public LoginView(EventHandler<ActionEvent> handler) {

		super();

		// Label and TextField
		VBox labelBox, fieldBox;
		HBox hb1;
		this.accountLabel = new Label("Account");
		this.accountLabel.setPrefWidth(80);
		this.accountField = new TextField();
		this.accountField.setPromptText("User ID");
		this.accountField.setPrefWidth(170);
		this.passwordLabel = new Label("Password");
		this.passwordLabel.setPrefWidth(80);
		this.passwordField = new PasswordField();
		this.passwordField.setPromptText("Password");
		this.passwordField.setPrefWidth(170);
		
		labelBox = new VBox();
		labelBox.getChildren().addAll(accountLabel, passwordLabel);
		labelBox.setAlignment(Pos.CENTER_LEFT);
		fieldBox = new VBox();
		fieldBox.getChildren().addAll(accountField, passwordField);
		fieldBox.setAlignment(Pos.CENTER_RIGHT);

		hb1 = new HBox();
		hb1.getChildren().addAll(labelBox, fieldBox);
		hb1.setAlignment(Pos.CENTER);
		this.setCenter(hb1);

		// Status Label, alert user if incorrect credential
		VBox vb1 = new VBox();
		this.statusLabel = new Label("Login First");

		// Login Button
		this.loginButton = new Button("Login");
		this.loginButton.setPrefWidth(100);
		this.loginButton.setOnAction(handler);

		vb1.getChildren().addAll(this.statusLabel, this.loginButton);
		this.setBottom(vb1);
		LoginView.setAlignment(vb1, Pos.CENTER);
		vb1.setAlignment(Pos.CENTER);
		
	}

	/**
	 * @return Get the content in the Username Text Field
	 */
	public String getUsername() {
		return this.accountField.getText();
	}

	/**
	 * @return Get the content in the Password Text Field
	 */
	public String getPassword() {
		return this.passwordField.getText();
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}
}
