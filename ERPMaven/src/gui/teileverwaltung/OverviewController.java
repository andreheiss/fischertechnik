package gui.teileverwaltung;

import java.io.IOException;

import com.google.inject.Inject;

import domain.artificialClock.IClockService;
import domain.artificialClock.IObserver;
import gui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class OverviewController implements IObserver{
	
	@FXML
	private Label labelTeileverwaltung;
	
	@FXML
	private Text betriebszeit;
	
	@FXML
	private Button back;
	
	@FXML
	private Button stueckliste;
	
	@FXML
	private Button teil;
	
	@FXML
	private Button pause;
	@FXML
	private Button play;
	
	private IClockService uhrzeit;
	
	@Inject
	public OverviewController(IClockService uhr) {
		uhrzeit = uhr;
		uhrzeit.anmelden(this);
	}
	
	public void initialize() {
		betriebszeit.setText(uhrzeit.getTime());
	}
	
	public void setLabelTeileverwaltung() {
		labelTeileverwaltung.setText("Teileverwaltung");
	}
	
	public void userBack(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("general/AfterLogInView.fxml",  1280, 800);
	}
	
	public void userStueckliste(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("teileverwaltung/stueckliste/View.fxml",1280, 800);
	}
	
	public void userTeil(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("teileverwaltung/teil/View.fxml",1280, 800);
	}
	
	@Override
	public void aktualisieren() {
		betriebszeit.setText(uhrzeit.getTime());
	}

	public void userPause(ActionEvent event) throws IOException {
		uhrzeit.stop();
	}

	public void userPlay(ActionEvent event) throws IOException {
		uhrzeit.run();
	}

}
