package gui.betriebsmittelverwaltung.betriebsmittel;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.google.inject.Inject;

import domain.artificialClock.IClockService;
import domain.artificialClock.IObserver;
import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.betriebsmittelverwaltung.core.InvalidBetriebsmittelException;
import domain.betriebsverwaltung.core.Werk;
import domain.betriebsverwaltung.provided.IWerkService;
import domain.shared.Betriebsmittelart;
import domain.teileverwaltung.core.InvalidTeilException;
import domain.betriebsmittelverwaltung.provided.IBetriebsmittelService;
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

	private IBetriebsmittelService BetriebsmittelService;
	private IWerkService werkservice;

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
	private TextField Betriebsmittelbezeichnung;

	@FXML
	private ChoiceBox<Betriebsmittelart> betriebsmittelart;
	@FXML
	private ChoiceBox<Integer> werk; //hier evtl String fuer Werksbezeichnung
	@FXML
	private DatePicker bmGueltigVon;
	@FXML
	private DatePicker bmGueltigBis;

	private Betriebsmittel suchErg;

	@FXML
	private Text betriebszeit;
	@FXML
	private Button pause;
	@FXML
	private Button play;

	private IClockService uhrzeit;

	// Erstellen der Choice Boxen und Befuellen
	ObservableList<Integer> WerkBox = FXCollections.observableArrayList(1);
	ObservableList<Betriebsmittelart> BmArtBox = FXCollections.observableArrayList(Betriebsmittelart.values());

	@Inject
	public Controller(IBetriebsmittelService bms, IClockService uhr, IWerkService ws) {
		BetriebsmittelService = bms;
		werkservice = ws;
		uhrzeit = uhr;
		uhrzeit.anmelden(this);
	}

	@FXML
	private void initialize() {
		// Unbenoetigte Felder erstmal unsichtbar machen
		abgrenzen.setVisible(false);
		aendern.setVisible(false);
		aendernBestaetigen.setVisible(false);
		abgrenzenBestaetigen.setVisible(false);
		anlegenBestaetigen.setVisible(false);
		anlegenBack.setVisible(false);

		// Setzen der Initial Werte
		betriebsmittelart.setItems(BmArtBox);
		
		werk.setItems(WerkBox);

		betriebszeit.setText(uhrzeit.getTime());
	}

	/**
	 * User Drueckt auf den Suchen-Button und loest die Suche aus
	 */
	public void userSuchen(ActionEvent event) throws IOException {
		List<Betriebsmittel> trefferListe;
		suchErg = fillBm(); // Mit null initialsieren, damit beim Verlassen durch Exit null geliefert wird
		trefferListe = BetriebsmittelService.getBetriebsmittel(suchErg);
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
				suchErg = pop.bmMehrfachauswahlPopUp(trefferListe, abgrenzen);
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
		labelUeberschrift.setText("Betriebsmittel anlegen"); // Label anpassen
		setSuchenUndAnlegen(false); // disable Felder zum Suchen und Anlegen
		anlegenBack.setVisible(true); // neuer zurueck Knopf der die Werte auf Suchen setzt
		back.setVisible(false);
		anlegenBestaetigen.setVisible(true); // Felder Aktiv Schalten & bestaeetigen Knopf einrichten
		loadFelder();
	}

	/**
	 * User Drueckt auf den Anlegen-Bestaetigen-Button und loest aus, das das
	 * gewuenschte Betriebsmittel angelegt wird Wir bleiben in der selben Ansicht,
	 * nach dem anlegen.
	 */
	public void userAnlegenBestaetigen(ActionEvent e) throws IOException {

		Betriebsmittel bm = new Betriebsmittel();
		bm.setBezeichnung(Betriebsmittelbezeichnung.getText());
		
		// Ersetzen sobald Repository vorhanden
		bm.setWerksnummer(1); 			
		
		bm.setArt((Betriebsmittelart) betriebsmittelart.getValue());
		bm.setGueltigVon(bmGueltigVon.getValue());
		bm.setGueltigBis(bmGueltigBis.getValue());
		try {
			if (BetriebsmittelService.createBetriebsmittel(bm)) {
				feedbackPopUp(bm.toString() + "\n" + "Erfolgreich angelegt");
				loadFelder();
			} else
				feedbackPopUp("Fehler beim Anlegen!");
		} catch (InvalidBetriebsmittelException ef) {
			feedbackPopUp(ef.getMessage());
		}
	}

	/**
	 * User Drueckt auf den Anlegen-Back-Button und loest aus, das die
	 * urspruengliche View, mit Suche und derer Funktionen wieder aktiv ist
	 */
	public void userAnlegenBack(ActionEvent event) throws IOException {
		labelUeberschrift.setText("Betriebsmittel suchen");
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
		labelUeberschrift.setText("Betriebsmittel ändern");
		loadBm(suchErg); // Suchergebnis wird mit dem richtigen Teil befuellt
		setAbgrenzenUndAendern(false); // entfernen der Knoepfe Aendern und Abgrenzen
		aendernBestaetigen.setVisible(true); // Aktiviere Button Bestaetigen
	}

	/**
	 * User Drueckt auf den Aendern-Bestaetigen-Button und loest aus, das das
	 * gewuenschte Teil geaendert wird.
	 */
	public void userAendernBestaetigen() throws IOException {

		Betriebsmittel bm = new Betriebsmittel();
		bm.setBetriebsmittelnummer(suchErg.getBetriebsmittelnummer());
		bm.setBezeichnung(Betriebsmittelbezeichnung.getText());
		
		// Ersetzen sobald Repository vorhanden
		bm.setWerksnummer(1); 	
		
		bm.setArt(betriebsmittelart.getValue());
		bm.setGueltigVon(bmGueltigVon.getValue());
		bm.setGueltigBis(bmGueltigBis.getValue());
		try {
			if (BetriebsmittelService.changeBetriebsmittel(bm)) { // aendert das alteTeil(SuchErgebniss) mit neuem Teil
				feedbackPopUp(suchErg.toString() + "\n" + "Erfolgreich geaendert zu" + "\n" + bm.toString());
				loadFelder();
			}
		} catch (NullPointerException ef) {
			feedbackPopUp(ef.getMessage());
		}

		loadFelder();

	}

	/**
	 * User Drueckt auf den Abgrenzen--Button und loest aus, das das gewuenschte
	 * Teil abgegrenzt werden soll.
	 */
	public void userAbgrenzen() throws IOException {
		labelUeberschrift.setText("Betriebsmittel abgrenzen");
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
		if(BetriebsmittelService.changeBetriebsmittel(suchErg)) {
			feedbackPopUp("Erfolgreich abgegrenzt");
		}else {
			feedbackPopUp("Misserfolg!");
		}
		}catch(NullPointerException it) {
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
		m.changeScene("general/AfterLogInView.fxml", 1280, 800);
	}

// Es folgen Methoden, die Mehrfach vorkommen, oder zur Uebersicht aausgelagert wurden

	/**
	 * Gefundenes Betriebsmittel bm wird in die Felder eingefuegt
	 */
	private void setFelder(Betriebsmittel bm) {
		Betriebsmittelbezeichnung.setText(bm.getBezeichnung());
		werk.setValue(1); // Ersetzen sobald Repository vorhanden
		betriebsmittelart.setValue(bm.getArt());
		bmGueltigVon.setValue(bm.getGueltigVon());
		bmGueltigBis.setValue(bm.getGueltigBis());
	}

	/**
	 * Felder im Aendern und Anlegen Bereich werden mit Standard Werten befuellt
	 */
	private void loadFelder() {

		Betriebsmittelbezeichnung.setText("");
		werk.setValue(1); // Ersetzen sobald Repository vorhanden
		betriebsmittelart.setValue(Betriebsmittelart.Maschine);
		bmGueltigVon.setValue(null);
		bmGueltigBis.setValue(null);

	}

	/**
	 * Leere Felder fuer die Suche setzen
	 */
	private void clearFelder() {
		Betriebsmittelbezeichnung.setText("");
		werk.setValue(null);
		betriebsmittelart.setValue(null);
		bmGueltigVon.setValue(null);
		bmGueltigBis.setValue(null);
	}

	/**
	 * Beim Finden eines Elements, sollen die Felder nicht veraendert werden
	 * koennen, disabeld werden
	 * 
	 * @return void
	 */
	private void disableFelder() {
		Betriebsmittelbezeichnung.setDisable(true);
		Betriebsmittelbezeichnung.setStyle("-fx-opacity: 1.0;");

		werk.setDisable(true);
		werk.setStyle("-fx-opacity: 1.0;");

		betriebsmittelart.setDisable(true);
		betriebsmittelart.setStyle("-fx-opacity: 1.0;");

		bmGueltigVon.setDisable(true);
		bmGueltigVon.setStyle("-fx-opacity: 1.0;");

		bmGueltigBis.setDisable(true);
		bmGueltigBis.setStyle("-fx-opacity: 1.0;");
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
	public void loadBm(Betriebsmittel bm) {
		// Die aenderbaren Felder sichtbar machen
		Betriebsmittelbezeichnung.setDisable(false);
		werk.setDisable(false);
		betriebsmittelart.setDisable(false);
		bmGueltigVon.setDisable(false);
		bmGueltigBis.setDisable(false);
		// Werte laden
		setFelder(bm);
	}

	/**
	 * Wenn ein Teil T gefunden wird, dann wird in die "Teile Anzeige" Maske
	 * gesprungen
	 */
	public void elementGefunden(Betriebsmittel bm) {
		setFelder(bm);
		labelUeberschrift.setText("Betriebsmittel anzeigen");
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
	public Betriebsmittel fillBm() {
		Betriebsmittel neu = new Betriebsmittel();
		if (Betriebsmittelbezeichnung.getText().length() != 0) {
			neu.setBezeichnung(Betriebsmittelbezeichnung.getText());
		}

		if (betriebsmittelart.getValue() != null)
			neu.setArt(betriebsmittelart.getValue());

		if (werk.getValue() != null) {
			
			// Ersetzen sobald Repository vorhanden
			neu.setWerksnummer(1);	
		}
		

		if (bmGueltigVon.getValue() != null) {
			neu.setGueltigVon(bmGueltigVon.getValue());
		}
		if (bmGueltigBis.getValue() != null) {
			neu.setGueltigVon(bmGueltigBis.getValue());
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
