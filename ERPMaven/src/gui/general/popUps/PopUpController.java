package gui.general.popUps;

import java.io.IOException;

import domain.shared.Beschaffungsart;
import domain.shared.Mengeneinheit;
import domain.shared.Teileart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopUpController {

	@FXML
	private Button ok;

	@FXML
	private TextArea textArea;

	private String message;

	public PopUpController(String msg) {
		message = msg;

	}

	@FXML
	private void initialize() {
		textArea.setText(message);
		textArea.setEditable(false);
		if (message.contains("Erfolgreich")) {
			textArea.setStyle("-fx-text-inner-color: green;");
		}

	}

	public void userOk(ActionEvent event) throws IOException {
		Stage stage = (Stage) ok.getScene().getWindow();
		stage.close();
	}

}
