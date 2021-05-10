package gui.general;

import java.io.IOException;

import com.google.inject.Inject;

import domain.artificialClock.IClockService;
import domain.artificialClock.IObserver;
import gui.Main;
import gui.teileverwaltung.OverviewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class AfterLogInController implements IObserver {

	@FXML
	private Button logOut;
	@FXML
	private Button teileVerwaltung;
	@FXML
	private Button auftragsVerwaltung;
	@FXML
	private Button betriebsmittel;
	@FXML
	private Label standort;
	@FXML
	private Text betriebszeit;
	@FXML
	private Button pause;
	@FXML
	private Button play;

	private IClockService uhrzeit;

	@Inject
	public AfterLogInController(IClockService uhr) {
		uhrzeit = uhr;
		uhrzeit.anmelden(this);
	}

	public void initialize() {
		betriebszeit.setText(uhrzeit.getTime());
	}

	public void userLogOut(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("general/LogInView.fxml", 600, 400);
	}

	public void userTeileVerwaltung(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("teileverwaltung/OverviewView.fxml", 1280, 800);
	}

	public void userAuftragsVerwaltung(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("Auftragsverwaltung.fxml", 1280, 800);
	}

	public void userBetriebsmittel(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("betriebsmittelverwaltung/betriebsmittel/View.fxml", 1280, 800);
	}
	
	public void userArbeitsplan(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("arbeitsplanverwaltung/OverviewView.fxml", 1280, 800);
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
