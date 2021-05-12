package gui.arbeitsplanverwaltung.arbeitsgang;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


import com.google.inject.Inject;

import domain.arbeitsplanverwaltung.core.Arbeitsgang;
import domain.arbeitsplanverwaltung.core.InvalidArbeitsgangException;
import domain.arbeitsplanverwaltung.provided.IArbeitsgangService;
import domain.artificialClock.IClockService;
import domain.artificialClock.IObserver;
import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.betriebsmittelverwaltung.provided.IBetriebsmittelService;
import gui.Main;
import gui.general.popUps.PopUpManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller implements IObserver {

	private IArbeitsgangService arbeitsgangService;
	private IBetriebsmittelService btrmService;

	@FXML
	private Parent root;

	@FXML
	private Label labelUeberschrift;
	@FXML
	private Label label;
	@FXML
	private Label betriebsmittelLabel;

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
	private TextField bezeichnung;
	@FXML
	private TextField bearbeitungszeit;
	@FXML
	private TextField ruestzeit;
	@FXML
	private TableView<Betriebsmittel> tableView;
	@FXML
	private TableColumn<Betriebsmittel, Integer> betriebsmittelnummer;
	@FXML
	private DatePicker teileGueltigVon;
	@FXML
	private DatePicker teileGueltigBis;

	private Arbeitsgang suchErg;

	@FXML
	private Text betriebszeit;
	@FXML
	private Button pause;
	@FXML
	private Button play;

	private IClockService uhrzeit;


	@Inject
	public Controller(IArbeitsgangService ts, IClockService uhr, IBetriebsmittelService bmS) {
		btrmService = bmS;
		arbeitsgangService = ts;
		uhrzeit = uhr;
		uhrzeit.anmelden(this);
	}

	@FXML
	private void initialize() {
		labelUeberschrift.setText("Arbeitsgang suchen");
		label.setText("Arbeitsplanverwaltung");
		// Unbenoetigte Felder erstmal sichtbar machen
		abgrenzen.setVisible(false);
		aendern.setVisible(false);
		aendernBestaetigen.setVisible(false);
		abgrenzenBestaetigen.setVisible(false);
		anlegenBestaetigen.setVisible(false);
		anlegenBack.setVisible(false);
		tableView.setVisible(false);
		betriebsmittelLabel.setVisible(false);

		// Uhrzeit anzeigen
		betriebszeit.setText(uhrzeit.getTime());
	}

	/**
	 * User Drueckt auf den Suchen-Button und loest die Suche aus
	 */
	public void userSuchen(ActionEvent event) throws IOException {
		List<Arbeitsgang> trefferListe = new LinkedList<>();
		suchErg = fillArbeitsgang(); // Mit null initialsieren, damit beim Verlassen durch Exit null geliefert wird
		List<Arbeitsgang> filterList = arbeitsgangService.getArbeitsgang(suchErg);
		for(Arbeitsgang t: filterList) {
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
			//	suchErg = pop.mehrfachAuswahlPopUp(trefferListe, abgrenzen);
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
		labelUeberschrift.setText("Arbeitsgang anlegen"); // Label anpassen
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
		Arbeitsgang t = new Arbeitsgang();
		t.setBezeichnung(bezeichnung.getText());
		t.setBearbeitungszeit(Integer.parseInt(bearbeitungszeit.getText()));
		t.setRuestzeit(Integer.parseInt(ruestzeit.getText()));
		t.setGueltigVon(teileGueltigVon.getValue());
		t.setGueltigBis(teileGueltigBis.getValue());
		activateFelder(t.getBetriebsmittel());
		try {
			if (arbeitsgangService.createArbeitsgang(t)) {
				feedbackPopUp(t.toString() + "\n" + "Erfolgreich angelegt");
				loadFelder();
			} else
				feedbackPopUp("Fehler beim Anlegen!");
		} catch (InvalidArbeitsgangException te) { 
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
		Arbeitsgang t = new Arbeitsgang();
		t.setArbeitsgangnummer(suchErg.getArbeitsgangnummer());
		t.setBezeichnung(bezeichnung.getText());
		t.setBearbeitungszeit(Integer.parseInt(bearbeitungszeit.getText()));
		t.setRuestzeit(Integer.parseInt(ruestzeit.getText()));
		t.setGueltigVon(teileGueltigVon.getValue());
		t.setGueltigBis(teileGueltigBis.getValue());
		activateFelder(t.getBetriebsmittel());
		try {
			if (arbeitsgangService.changeArbeitsgang(t)) { // aendert das alteTeil(SuchErgebniss) mit neuem Teil
				feedbackPopUp(suchErg.toString() + "\n" + "Erfolgreich geaendert zu" + "\n" + t.toString());
			} else {
				feedbackPopUp("Misserfolg!");
			}
		} catch (InvalidArbeitsgangException te) { 
			feedbackPopUp(te.getMessage());
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
		if(arbeitsgangService.changeArbeitsgang(suchErg)) {
			feedbackPopUp("Erfolgreich abgegrenzt");
		}else {
			feedbackPopUp("Misserfolg!");
		}
		}catch(InvalidArbeitsgangException te) { 
			feedbackPopUp(te.getMessage());
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
		m.changeScene("arbeitsplanverwaltung/OverviewView.fxml", 1280, 800);
	}

// Es folgen Methoden, die Mehrfach vorkommen, oder zur Uebersicht aausgelagert wurden

	/**
	 * Gefundenes Teil t wird in die Felder eingefuegt
	 */
	private void setFelder(Arbeitsgang t) {
		tableView.setVisible(true);
		betriebsmittelLabel.setVisible(true);
		String bz = "";
		String rz ="";
		bezeichnung.setText(t.getBezeichnung());
		bz =""+t.getBearbeitungszeit();
		bearbeitungszeit.setText(bz);
		rz =""+ t.getRuestzeit();
		ruestzeit.setText(rz);
		teileGueltigVon.setValue(t.getGueltigVon());
		teileGueltigBis.setValue(t.getGueltigBis());
		activateFelder(t.getBetriebsmittel());
	}

	/**
	 * Felder im Aendern und Anlegen Bereich werden mit Standard Werten befuellt
	 */
	private void loadFelder() {
		bezeichnung.setText("");
		ruestzeit.setText("0");
		bearbeitungszeit.setText("0");
		teileGueltigVon.setValue(null);
		teileGueltigBis.setValue(null);
		betriebsmittelLabel.setVisible(true);
		tableView.setVisible(true);
		tableView.refresh();
	}

	/**
	 * Leere Felder fuer die Suche setzen
	 */
	private void clearFelder() {
		bezeichnung.setText("");
		ruestzeit.setText("");
		bearbeitungszeit.setText("");
		teileGueltigVon.setValue(null);
		teileGueltigBis.setValue(null);
		tableView.setVisible(false);
		betriebsmittelLabel.setVisible(false);
	}

	/**
	 * Beim Finden eines Elements, sollen die Felder nicht veraendert werden
	 * koennen, disabled werden
	 * 
	 * @return void
	 */
	private void disableFelder() {
		bezeichnung.setDisable(true);
		bezeichnung.setStyle("-fx-opacity: 1.0;");

		bearbeitungszeit.setDisable(true);
		bearbeitungszeit.setStyle("-fx-opacity: 1.0;");

		ruestzeit.setDisable(true);
		ruestzeit.setStyle("-fx-opacity: 1.0;");

		tableView.setDisable(true);
		tableView.setStyle("-fx-opacity: 1.0;");

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
	public void loadTeil(Arbeitsgang t) {
		// Die aenderbaren Felder sichtbar machen
		bezeichnung.setDisable(false);
		bearbeitungszeit.setDisable(false);
		ruestzeit.setDisable(false);
		teileGueltigVon.setDisable(false);
		teileGueltigBis.setDisable(false);
		tableView.setDisable(false);
		// Werte laden
		setFelder(t);
	}

	/**
	 * Wenn ein Teil T gefunden wird, dann wird in die "Teile Anzeige" Maske
	 * gesprungen
	 */
	public void elementGefunden(Arbeitsgang t) {
		setFelder(t);
		labelUeberschrift.setText("Arbeitsgang anzeigen");
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
	public Arbeitsgang fillArbeitsgang() {
		Arbeitsgang neu = new Arbeitsgang();
		if (bezeichnung.getText().length() != 0) {
			neu.setBezeichnung(bezeichnung.getText());
		}
		if (!bearbeitungszeit.getText().isEmpty()) {
			neu.setBearbeitungszeit(Integer.parseInt(bearbeitungszeit.getText()));
		}
		if(!ruestzeit.getText().isEmpty()) {
			neu.setBearbeitungszeit(Integer.parseInt(bearbeitungszeit.getText()));
		}
		if (teileGueltigVon.getValue() != null) {
			neu.setGueltigVon(teileGueltigVon.getValue());
		}
		if (teileGueltigBis.getValue() != null) {
			neu.setGueltigVon(teileGueltigBis.getValue());
		}
		tableView.refresh();
		return neu;

	}
	

	public void userBearbeitungszeit(KeyEvent e) {
		String ce = e.getCharacter();
		if(!(ce.matches("[0-9]") || ce.isEmpty() || e.getCharacter().equals("\u007F"))) {
			feedbackPopUp("Fehler: Bitte nur Zahlen eingeben!");
			bearbeitungszeit.setText("");
		}
		
	}
	
	public void userRuestzeit(KeyEvent e) {
		String ce = e.getCharacter();
		if(!(ce.matches("[0-9]") || ce.isEmpty() || e.getCharacter().equals("\u007F"))) {
			feedbackPopUp("Fehler: Bitte nur Zahlen eingeben!");
			ruestzeit.setText("");
		}
		
	}
	
	public void activateFelder(List<Betriebsmittel> bm) {
		// Spaltenbefuellung ueber Namensgleichheit
		betriebsmittelnummer.setCellValueFactory(new PropertyValueFactory<Betriebsmittel, Integer>("betriebsmittelnummer"));	
		for (Betriebsmittel st : bm) {
			tableView.getItems().add(st);
		}
		tableView.refresh();
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
