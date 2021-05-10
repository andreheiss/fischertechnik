package gui.teileverwaltung.teil;

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
import gui.general.popUps.PopUpManager;
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

public class Controller implements IObserver {

	private ITeilService teileService;

	@FXML
	private Parent root;

	@FXML
	private Label labelUeberschrift;

	@FXML
	private Button back;

	@FXML
	private Button suchen;

	@FXML
	private Button abgrenzen;
	@FXML
	private Button abgrenzenBestaetigen;

	@FXML
	private Button aendern;
	@FXML
	private Button aendernBestaetigen;

	@FXML
	private Button anlegen;
	@FXML
	private Button anlegenBestaetigen;
	@FXML
	private Button anlegenBack;

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

	private Teil suchErg;

	@FXML
	private Text betriebszeit;
	@FXML
	private Button pause;
	@FXML
	private Button play;

	private IClockService uhrzeit;

	// Erstellen der Choice Boxen und Befuellen
	ObservableList<Beschaffungsart> beschaffungsArtBox = FXCollections.observableArrayList(Beschaffungsart.values());
	ObservableList<Farbe> farbBox = FXCollections.observableArrayList(Farbe.values());
	ObservableList<Planungsart> planungsArtBox = FXCollections.observableArrayList(Planungsart.values());
	ObservableList<Teileart> teileArtBox = FXCollections.observableArrayList(Teileart.values());
	ObservableList<Mengeneinheit> mengenEinheitBox = FXCollections.observableArrayList(Mengeneinheit.values());

	@Inject
	public Controller(ITeilService ts, IClockService uhr) {
		teileService = ts;
		uhrzeit = uhr;
		uhrzeit.anmelden(this);
	}

	@FXML
	private void initialize() {
		// Unbenoetigte Felder erstmal sichtbar machen
		abgrenzen.setVisible(false);
		aendern.setVisible(false);
		aendernBestaetigen.setVisible(false);
		abgrenzenBestaetigen.setVisible(false);
		anlegenBestaetigen.setVisible(false);
		anlegenBack.setVisible(false);

		// Setzen der Initial Werte
		beschaffungsArt.setItems(beschaffungsArtBox);
		farbe.setItems(farbBox);
		planungsArt.setItems(planungsArtBox);
		teileArt.setItems(teileArtBox);
		mengenEinheit.setItems(mengenEinheitBox);

		// Uhrzeit anzeigen
		betriebszeit.setText(uhrzeit.getTime());
	}

	/**
	 * User Drueckt auf den Suchen-Button und loest die Suche aus
	 */
	public void userSuchen(ActionEvent event) throws IOException {
		List<Teil> trefferListe = new LinkedList<>();
		suchErg = fillTeil(); // Mit null initialsieren, damit beim Verlassen durch Exit null geliefert wird
		List<Teil> filterList = teileService.getTeil(suchErg);
		for(Teil t: filterList) {
			if(t.getGueltigBis().compareTo(LocalDate.now()) >= 0) {
				trefferListe.add(t);
			}
		}
		if (trefferListe.size() == 1) { // Nur ein Element existiert -> sofort anzeigen
			clearFelder();
			suchErg = trefferListe.get(0);
			elementGefunden(suchErg);
		} else {
			if (trefferListe.size() == 0) { // Kein Element existiert
				// Feedback PopUp, mit Individueller Nachricht
				feedbackPopUp("Keine Treffer gefunden");
				clearFelder();
			} else { // Mehrere Treffer
				// MehrfachAuswahl PopUp, das uns Teil als Such ergebniss zurueck gibt
				PopUpManager pop = new PopUpManager(new Stage(), root);
				suchErg = pop.mehrfachAuswahlPopUp(trefferListe, abgrenzen);
				clearFelder();
				if (suchErg != null) { // Der Fall: Zurueck druecken ohne Auswahl
					elementGefunden(suchErg);
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
		setSuchenUndAnlegen(false); // disable Felder zum Suchen und Anlegen
		anlegenBack.setVisible(true); // neuer zurueck Knopf der die Werte auf Suchen setzt
		back.setVisible(false);
		anlegenBestaetigen.setVisible(true); // Felder Aktiv Schalten & bestaeetigen Knopf einrichten
		loadFelder();
	}

	/**
	 * User Drueckt auf den Anlegen-Bestaetigen-Button und loest aus, das das
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
		t.setMengeneinheit((Mengeneinheit) mengenEinheit.getValue());
		t.setTeileart((Teileart) teileArt.getValue());
		t.setPlanart(planungsArt.getValue());
		try {
			if (teileService.createTeil(t)) {
				feedbackPopUp(t.toString() + "\n" + "Erfolgreich angelegt");
				loadFelder();
			} else
				feedbackPopUp("Fehler beim Anlegen!");
		} catch (InvalidTeilException te) {
			feedbackPopUp(te.getMessage());
		}
	}

	/**
	 * User Drueckt auf den Anlegen-Back-Button und loest aus, das die
	 * urspruengliche View, mit Suche und derer Funktionen wieder aktiv ist
	 */
	public void userAnlegenBack(ActionEvent event) throws IOException {
		labelUeberschrift.setText("Teil suchen");
		clearFelder();
		anlegenBestaetigen.setVisible(false);
		anlegenBack.setVisible(false);
		back.setVisible(true);
		setSuchenUndAnlegen(true); // Buttons aktivieren
	}

	/**
	 * User Drueckt auf den Aendern-Button und loest aus, das die zum Aendern
	 * noetigen Buttons geladen werden und die anderen verschwinden
	 */
	public void userAendern() throws IOException {
		labelUeberschrift.setText("Teil ändern");
		loadTeil(suchErg); // Suchergebnis wird mit dem richtigen Teil befuellt
		setAbgrenzenUndAendern(false); // entfernen der Knoepfe Aendern und Abgrenzen
		aendernBestaetigen.setVisible(true); // Aktiviere Button Bestaetigen
	}

	/**
	 * User Drueckt auf den Aendern-Bestaetigen-Button und loest aus, das das
	 * gewuenschte Teil geaendert wird.
	 */
	public void userAendernBestaetigen() throws IOException {
		Teil t;
		if (beschaffungsArt.getValue() == Beschaffungsart.Eigenfertigung) {
			t = new Eigenfertigungsteil();
			((Eigenfertigungsteil)t).setBaukastenstueckliste( ((Eigenfertigungsteil)suchErg).getBaukastenstueckliste());
		} else {
			t = new Fremdbezugsteil();
		}
		t.setTeilenummer(suchErg.getTeilenummer());
		t.setBezeichnung(teileBezeichnung.getText());
		t.setFarbe(farbe.getValue());
		t.setGueltigVon(teileGueltigVon.getValue());
		t.setGueltigBis(teileGueltigBis.getValue());
		t.setMengeneinheit(mengenEinheit.getValue());
		t.setTeileart(teileArt.getValue());
		t.setPlanart(planungsArt.getValue());
		try {
			if (teileService.changeTeil(t)) { // aendert das alteTeil(SuchErgebniss) mit neuem Teil
				feedbackPopUp(suchErg.toString() + "\n" + "Erfolgreich geaendert zu" + "\n" + t.toString());
			} else {
				feedbackPopUp("Misserfolg!");
			}
		} catch (InvalidTeilException it) {
			feedbackPopUp(it.getMessage());
		}

		loadFelder();

	}

	

	/**
	 * User Drueckt auf den Abgrenzen--Button und loest aus, das das gewuenschte
	 * Teil abgegrenzt werden soll.
	 */
	public void userAbgrenzen() throws IOException {
		labelUeberschrift.setText("Teil abgrenzen");
		setFelder(suchErg);
		setAbgrenzenUndAendern(false);
		abgrenzenBestaetigen.setVisible(true);
	}

	/**
	 * User Drueckt auf den Abgrenzen-Bestaetigen-Button und loest aus, das das
	 * gewuenschte Teil abgegrenzt wird.
	 */
	public void userAbgrenzenBestaetigen(ActionEvent e) throws IOException {
		
		suchErg.setGueltigBis(LocalDate.now());
		try {
		if(teileService.changeTeil(suchErg)) {
			feedbackPopUp("Erfolgreich abgegrenzt");
		}else {
			feedbackPopUp("Misserfolg!");
		}
		}catch(InvalidTeilException it) {
			feedbackPopUp(it.getMessage());
		}
		feedbackPopUp(suchErg.toString() + "\n" + "Erfolgreich abgegrenzt");
		loadFelder();
	}

	/**
	 * User Drueckt auf den Zurueck-Button und loest aus, das wir auf die Ovierview
	 * View gelangen.
	 */
	public void userBack(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("teileverwaltung/OverviewView.fxml", 1280, 800);
	}

// Es folgen Methoden, die Mehrfach vorkommen, oder zur Uebersicht aausgelagert wurden

	/**
	 * Gefundenes Teil t wird in die Felder eingefuegt
	 */
	private void setFelder(Teil t) {
		teileBezeichnung.setText(t.getBezeichnung());
		if (t instanceof Fremdbezugsteil) {
			beschaffungsArt.setValue(Beschaffungsart.Fremdbeschaffung);
		} else {
			beschaffungsArt.setValue(Beschaffungsart.Eigenfertigung);
		}
		farbe.setValue(t.getFarbe());
		teileArt.setValue(t.getTeileart());
		mengenEinheit.setValue(t.getMengeneinheit());
		planungsArt.setValue(t.getPlanart());
		teileGueltigVon.setValue(t.getGueltigVon());
		teileGueltigBis.setValue(t.getGueltigBis());
	}

	/**
	 * Felder im Aendern und Anlegen Bereich werden mit Standard Werten befuellt
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
	 * Beim Finden eines Elements, sollen die Felder nicht veraendert werden
	 * koennen, disabled werden
	 * 
	 * @return void
	 */
	private void disableFelder() {
		teileBezeichnung.setDisable(true);
		teileBezeichnung.setStyle("-fx-opacity: 1.0;");

		farbe.setDisable(true);
		farbe.setStyle("-fx-opacity: 1.0;");

		teileArt.setDisable(true);
		teileArt.setStyle("-fx-opacity: 1.0;");

		beschaffungsArt.setDisable(true);
		beschaffungsArt.setStyle("-fx-opacity: 1.0;");

		mengenEinheit.setDisable(true);
		mengenEinheit.setStyle("-fx-opacity: 1.0;");

		planungsArt.setDisable(true);
		planungsArt.setStyle("-fx-opacity: 1.0;");

		teileGueltigVon.setDisable(true);
		teileGueltigVon.setStyle("-fx-opacity: 1.0;");

		teileGueltigBis.setDisable(true);
		teileGueltigBis.setStyle("-fx-opacity: 1.0;");
	}

	/**
	 * Abgrenzen und Aendern Buttons werden auf Sichtbar und Aktiv geschaltet
	 */
	public void setAbgrenzenUndAendern(Boolean bool) {
		abgrenzen.setVisible(bool);
		aendern.setVisible(bool);
	}

	/**
	 * Suchen und Anlegen wird unsichtbar und deaktiviert
	 */
	public void setSuchenUndAnlegen(Boolean bool) {
		anlegen.setVisible(bool);
		suchen.setVisible(bool);
	}

	/**
	 * 
	 * Aendern felder aktivieren und Suchergebnis mit dem richtigen @param t Teil
	 * befuellen
	 */
	public void loadTeil(Teil t) {
		// Die aenderbaren Felder sichtbar machen
		teileBezeichnung.setDisable(false);
		farbe.setDisable(false);
		teileArt.setDisable(false);
		mengenEinheit.setDisable(false);
		planungsArt.setDisable(false);
		beschaffungsArt.setDisable(false);
		teileGueltigVon.setDisable(false);
		teileGueltigBis.setDisable(false);
		// Werte laden
		setFelder(t);
	}

	/**
	 * Wenn ein Teil T gefunden wird, dann wird in die "Teile Anzeige" Maske
	 * gesprungen
	 */
	public void elementGefunden(Teil t) {
		setFelder(t);
		labelUeberschrift.setText("Teil anzeigen");
		setSuchenUndAnlegen(false); // SuchButton & AnlegeButton verschwinden
		disableFelder(); // Felder werden Deaktiviert
		setAbgrenzenUndAendern(true); // Neue Auswahl an Knoepfen -> Aendern und Abgrenzen

	}

	/**
	 * Pop Up aufrufen, das ein Feedback mit der passenden @param msg Nachricht
	 * befüllt wird
	 */
	public void feedbackPopUp(String msg) {
		PopUpManager pop = new PopUpManager(new Stage(), root);
		pop.feedbackPopUp("PopUpView.fxml", anlegenBestaetigen, msg);
	}

	/**
	 * Such felder werden Geladen
	 * 
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
		if (farbe.getValue() != null) {
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

	// Uhr Funktionen
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
