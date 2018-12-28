package View;

import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TutorialView extends TextFlow implements GUIView {

	private Text title;
	private Text content;
	private final String tutorialStr;

	public TutorialView() {
		
		this.tutorialStr = "Welcome to Pokemon Game - Safari Zone.\n"
				+ "- Control\n"
				+ "Use Arrow Key to move around\n"
				+ "Press \'I\' to open/close Inventory\n"
				+ "- Rule\n"
				+ "You will only encounter Pokemon in the tall grass and near water\n"
				+ "Unlike normal Pokemon game, You have limit action when engaging battle with wild Pokemon you encounter\n";

		this.title = new Text("Tutorial\n");
		this.title.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		this.content = new Text(this.tutorialStr);
		this.content.setFont(Font.font("Serif", 20));
		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(this.title, this.content);
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}
}
