package gui.general;


import java.io.IOException;

import gui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {

	@FXML
	private Button logIn;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField username;
	
	@FXML
	private Label errorLogIn;
	

	public void userLogIn(ActionEvent logIn) throws IOException {
		checkLogIn();
	}

	private void checkLogIn() throws IOException {
		Main m = new Main();
		if(username.getText().equals("Admin") && password.getText().toString().equals("123")) {
			 //nach erfolgreichem Log in wechseln wir zu dem neuen Fenster -> design in afterLogIn.xml
		m.changeScene("general/AfterLogInView.fxml",1280,800);
		}
		
		else if(username.getText().isEmpty() && password.getText().isEmpty()) {
			errorLogIn.setText("Fill in Data");
		}
		
		else  errorLogIn.setText("Wrong password or username");
	}
	
}


