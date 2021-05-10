package gui.general.popUps;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import domain.artificialClock.IClockService;
import domain.artificialClock.IObserver;
import domain.shared.Beschaffungsart;
import domain.shared.Farbe;
import domain.shared.Mengeneinheit;
import domain.shared.Planungsart;
import domain.shared.Teileart;
import domain.teileverwaltung.core.Eigenfertigungsteil;
import domain.teileverwaltung.core.Fremdbezugsteil;
import domain.teileverwaltung.core.InvalidTeilException;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.providedservices.ITeilService;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TeileAnlegenPopUpController{

	private ITeilService teileService;

	@FXML
	private Parent root;

	@FXML
	private Label labelUeberschrift;
	
	@FXML
	private Button anlegen;
	@FXML
	private Button anlegenBestaetigen;
	@FXML
	private Button back;
	
	@FXML
	private Button suchen;

	@FXML
	private TextField teileBezeichnung;
	@FXML
	private ChoiceBox<Farbe> farbe;
	@FXML
	private ChoiceBox<Beschaffungsart> beschaffungsArt;
	@FXML
	private ChoiceBox<Planungsart> planungsArt;
	@FXML
	private ChoiceBox<Teileart> teileArt;
	@FXML
	private ChoiceBox<Mengeneinheit> mengenEinheit;
	@FXML
	private DatePicker teileGueltigVon;
	@FXML
	private DatePicker teileGueltigBis;

	
	private Teil erg;
	private Teil uebergebenes;

	
	// Erstellen der Choice Boxen und Befuellen
	ObservableList<Beschaffungsart> beschaffungsArtBox = FXCollections.observableArrayList(Beschaffungsart.values());
	ObservableList<Farbe> farbBox = FXCollections.observableArrayList(Farbe.values());
	ObservableList<Planungsart> planungsArtBox = FXCollections.observableArrayList(Planungsart.values());
	ObservableList<Teileart> teileArtBox = FXCollections.observableArrayList(Teileart.values());
	ObservableList<Mengeneinheit> mengenEinheitBox = FXCollections.observableArrayList(Mengeneinheit.values());

	@Inject
	public TeileAnlegenPopUpController(ITeilService ts, Teil akt) {
		teileService = ts;
		uebergebenes = akt;
	}

	@FXML
	private void initialize() {
		// Setzen der Initial Werte
		beschaffungsArt.setItems(beschaffungsArtBox);
		farbe.setItems(farbBox);
		planungsArt.setItems(planungsArtBox);
		teileArt.setItems(teileArtBox);
		mengenEinheit.setItems(mengenEinheitBox);
		anlegenBestaetigen.setVisible(false);
		back.setVisible(false);
		clearFelder();
		teileBezeichnung.setEditable(true);
		
	}
	
	/**
	 * User Drueckt auf den Suchen-Button und loest die Suche aus
	 */
	public void userSuchen(ActionEvent event) throws IOException {
		List<Teil> trefferListe = new LinkedList<>();
		erg = fillTeil(); // Mit null initialsieren, damit beim Verlassen durch Exit null geliefert wird
		List<Teil> filterList = teileService.getTeil(erg);
		for(Teil t: filterList) {
			if(t.getGueltigBis().compareTo(LocalDate.now()) >= 0) {
				trefferListe.add(t);
			}
		}
		trefferListe.remove(uebergebenes);
		if (trefferListe.size() == 1) { // Nur ein Element existiert -> sofort anzeigen
			erg = trefferListe.get(0);
			Stage stage = (Stage) anlegen.getScene().getWindow();
			stage.close();
		} else {
			if (trefferListe.size() == 0 || trefferListe.size() == 1) { // Kein Element existiert
				// Feedback PopUp, mit Individueller Nachricht
				feedbackPopUp("Keine Treffer gefunden");
			} else { // Mehrere Treffer
				// MehrfachAuswahl PopUp, das uns Teil als Such ergebniss zurueck gibt
				PopUpManager pop = new PopUpManager(new Stage(), root);
				erg = pop.mehrfachAuswahlPopUp(trefferListe, anlegen);
				if (erg != null) { // Der Fall: Zurueck druecken ohne Auswahl
					Stage stage = (Stage) anlegen.getScene().getWindow();
					stage.close();
				}
			}
		}
	}
	
	/**
	 * User Drueckt auf den Anlegen-Button und loest aus, das die zum Anlegen
	 * noetigen Buttons geladen werden und die anderen verschwinden
	 */
	public void userAnlegen() throws IOException {
		labelUeberschrift.setText("Teil anlegen"); // Label anpassen
		suchen.setVisible(false);
		anlegen.setVisible(false);// disable Felder zum Suchen und Anlegen
		back.setVisible(true); // neuer zurueck Knopf der die Werte auf Suchen setzt
		anlegenBestaetigen.setVisible(true); // Felder Aktiv Schalten & bestaeetigen Knopf einrichten
		loadFelder();
	}

	/**
	 * User Drueckt auf den Anlegen-Besttaetigen-Button und loest aus, das das
	 * gewuenschte Teil angelegt wird Wir bleiben in der selben Ansicht, nach dem
	 * anlegen.
	 */
	public void userAnlegenBestaetigen(ActionEvent e) throws IOException {
		Teil t;
		if (beschaffungsArt.getValue().equals(Beschaffungsart.Eigenfertigung)) {
			t = new Eigenfertigungsteil();
		} else {
			t = new Fremdbezugsteil();
		}
		t.setBezeichnung(teileBezeichnung.getText());
		t.setFarbe(farbe.getValue());
		t.setGueltigVon(teileGueltigVon.getValue());
		t.setGueltigBis(teileGueltigBis.getValue());
		t.setPlanart(planungsArt.getValue());
		t.setMengeneinheit(mengenEinheit.getValue());
		t.setTeileart(teileArt.getValue());
		try {
		if (teileService.createTeil(t)) {
			erg = t;
			feedbackPopUp(t.toString() + "\n" + "Erfolgreich angelegt");
			Stage stage = (Stage) anlegen.getScene().getWindow();
			stage.close();
		} else
			feedbackPopUp("Fehler beim Anlegen!");
		}catch(InvalidTeilException te) {
			feedbackPopUp(te.getMessage());
		}
		
		}
	
	/**
	 * User Drueckt auf den Anlegen-Back-Button und loest aus, das die
	 * urspruengliche View, mit Suche und derer Funktionen wieder aktiv ist
	 */
	public void userBack(ActionEvent event) throws IOException {
		labelUeberschrift.setText("Teil suchen");
		clearFelder();
		anlegenBestaetigen.setVisible(false);
		back.setVisible(false);
		back.setVisible(false);
		suchen.setVisible(true); // Buttons aktivieren
		anlegen.setVisible(true);
	}

	/**
	 * Felder im Anlegen werden mit Standard Werten befuellt
	 */
	private void loadFelder() {
		teileBezeichnung.setText("");
		farbe.setValue(Farbe.Weiss);
		teileArt.setValue(Teileart.Rohstoff);
		mengenEinheit.setValue(Mengeneinheit.Stueck);
		beschaffungsArt.setValue(Beschaffungsart.Eigenfertigung);
		planungsArt.setValue(Planungsart.Plangesteuert);
		teileGueltigVon.setValue(null);
		teileGueltigBis.setValue(null);
	}

	/**
	 * Such felder werden Geladen
	 * @return Teil mit Werten aus der Suche
	 */
	public Teil fillTeil() {
		Teil neu;
		if (beschaffungsArt.getValue() == Beschaffungsart.Fremdbeschaffung) {
			neu = new Fremdbezugsteil();
		} else if (beschaffungsArt.getValue() == Beschaffungsart.Eigenfertigung) {
			neu = new Eigenfertigungsteil();
		} else
			neu = new Teil();
		if (teileBezeichnung.getText().length() != 0) {
			neu.setBezeichnung(teileBezeichnung.getText());
		}
		if(farbe.getValue() != null) {
			neu.setFarbe(farbe.getValue());
		}
		if (teileArt.getValue() != null) {
			neu.setTeileart(teileArt.getValue());
		}
		if (mengenEinheit.getValue() != null) {
			neu.setMengeneinheit(mengenEinheit.getValue());
		}
		if (planungsArt.getValue() != null) {
			neu.setPlanart(planungsArt.getValue());
		}
		if (teileGueltigVon.getValue() != null) {
			neu.setGueltigVon(teileGueltigVon.getValue());
		}
		if (teileGueltigBis.getValue() != null) {
			neu.setGueltigVon(teileGueltigBis.getValue());
		}
		return neu;

	}
	
	/**
	 * Leere Felder fuer die Suche setzen
	 */
	private void clearFelder() {
		teileBezeichnung.setText("");
		farbe.setValue(null);
		teileArt.setValue(null);
		mengenEinheit.setValue(null);
		beschaffungsArt.setValue(null);
		planungsArt.setValue(null);
		teileGueltigVon.setValue(null);
		teileGueltigBis.setValue(null);
	}
	
	/**
	 * Pop Up aufrufen, das ein Feedback mit der passenden @param msg Nachricht
	 * befüllt wird
	 */
	public void feedbackPopUp(String msg) {
		PopUpManager pop = new PopUpManager(new Stage(), root);
		pop.feedbackPopUp("PopUpView.fxml", anlegen, msg);
	}
	
	
	public Teil getErgebnis() {
		return erg;
	}

}
