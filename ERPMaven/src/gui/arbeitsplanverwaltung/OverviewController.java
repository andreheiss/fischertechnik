package gui.arbeitsplanverwaltung;

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
	private Label label;
	
	@FXML
	private Text betriebszeit;
	
	@FXML
	private Button back;
	
	@FXML
	private Button arbeitsplan;
	
	@FXML
	private Button arbeitsgang;
	
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
		setLabel();
	}
	
	public void setLabel() {
		label.setText("Arbeitsplanverwaltung");
	}
	
	public void userBack(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("general/AfterLogInView.fxml",  1280, 800);
	}
	
	public void userArbeitsplan(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("arbeitsplanverwaltung/arbeitsplan/View.fxml",1280, 800);
	}
	
	public void userArbeitsgang(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("arbeitsplanverwaltung/arbeitsgang/View.fxml",1280, 800);
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
