package gui.teileverwaltung.stueckliste;

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
import domain.teileverwaltung.core.InvalidTeilException;
import domain.teileverwaltung.core.Stuecklistenelement;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

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
	private Button suchenBack;

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

	private Eigenfertigungsteil suchErg;

	@FXML
	private Text betriebszeit;
	@FXML
	private Button pause;
	@FXML
	private Button play;

	@FXML
	private Text foundElement;

	@FXML
	private VBox vBox;

	@FXML
	private Label labelBezeichnung;

	@FXML
	private Button hinzufuegen;
	@FXML
	private Button entfernen;
	@FXML
	private Button speichern;

	@FXML
	private TableView<TeileMenge> listView;

	private IClockService uhrzeit;
	@FXML
	private TableColumn<TeileMenge, Integer> teilenummer;
	@FXML
	private TableColumn<TeileMenge, String> bezeichnung;
	@FXML
	private TableColumn<TeileMenge, Integer> menge;

	@FXML
	private javafx.scene.layout.AnchorPane anchor;

	private Teil stueck;

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
		suchenBack.setVisible(false);
		vBox.setVisible(false);
		listView.setVisible(false);

		// Setzen der Initial Werte
		farbe.setItems(farbBox);
		planungsArt.setItems(planungsArtBox);
		teileArt.setItems(teileArtBox);
		mengenEinheit.setItems(mengenEinheitBox);

		// Uhrzeit anzeigen
		betriebszeit.setText(uhrzeit.getTime());
		anchor.setVisible(false);

	}

	/**
	 * User Drueckt auf den Suchen-Button und loest die Suche aus
	 */
	public void userSuchen(ActionEvent event) throws IOException {
		List<Teil> trefferListe = new LinkedList<>();
		suchErg = fillTeil(); // Mit null initialsieren, damit beim Verlassen durch Exit null geliefert wird
		List<Teil> filterList = teileService.getTeil(suchErg);
		for (Teil t : filterList) {
			if (t.getGueltigBis().compareTo(LocalDate.now()) >= 0) {
				trefferListe.add(t);
			}
		}
		if (trefferListe.size() == 1) { // Nur ein Element existiert -> sofort anzeigen
			suchErg = (Eigenfertigungsteil) trefferListe.get(0);
			elementGefunden(suchErg);
		} else {
			if (trefferListe.size() == 0) { // Kein Element existiert
				// Feedback PopUp, mit Individueller Nachricht
				feedbackPopUp("Keine Treffer gefunden");
				clearFelder();
			} else { // Mehrere Treffer
				// MehrfachAuswahl PopUp, das uns Teil als Such ergebniss zurueck gibt
				PopUpManager pop = new PopUpManager(new Stage(), root);
				suchErg = (Eigenfertigungsteil) pop.mehrfachAuswahlPopUp(trefferListe, suchen);
				clearFelder();
				if (suchErg != null) { // Der Fall: Zurueck druecken ohne Auswahl
					elementGefunden(suchErg);
				}
			}
		}
	}

	/**
	 * User Drueckt auf den Suchen-Back-Button und loest aus, das die urspruengliche
	 * View, mit Suche und derer Funktionen wieder aktiv ist
	 */
	public void userSuchenBack(ActionEvent event) throws IOException {
		labelUeberschrift.setText("Teil suchen");
		clearFelder();
		suchenBack.setVisible(false);
		suchen.setVisible(true);
		back.setVisible(true);
		vBox.setVisible(false);
		hinzufuegen.setVisible(false);
		speichern.setVisible(false);
		removeFelder(true);// felder wieder aktiv Schalten
		suchErg = fillTeil();
		anchor.setVisible(false);
		// Buttons aktivieren
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
		farbe.setValue(t.getFarbe());
		teileArt.setValue(t.getTeileart());
		mengenEinheit.setValue(t.getMengeneinheit());
		planungsArt.setValue(t.getPlanart());
		teileGueltigVon.setValue(t.getGueltigVon());
		teileGueltigBis.setValue(t.getGueltigBis());
	}

	/**
	 * Leere Felder fuer die Suche setzen
	 */
	private void clearFelder() {
		teileBezeichnung.setText("");
		farbe.setValue(null);
		teileArt.setValue(null);
		mengenEinheit.setValue(null);
		planungsArt.setValue(null);
		teileGueltigVon.setValue(null);
		teileGueltigBis.setValue(null);
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
		teileGueltigVon.setDisable(false);
		teileGueltigBis.setDisable(false);
		// Werte laden
		setFelder(t);
	}

	/**
	 * Wenn ein Teil T gefunden wird, dann wird in die "Teile Anzeige" Maske
	 * gesprungen
	 */
	public void elementGefunden(Eigenfertigungsteil t) {
		labelUeberschrift.setText("Stückliste pflegen");
		suchen.setVisible(false); // SuchButton & AnlegeButton verschwinden
		removeFelder(false); // Felder werden inaktiv geschalten
		activateFelder(t);
		// setAbgrenzenUndAendern(true); // Neue Auswahl an Knoepfen -> Aendern und
		// Abgrenzen

	}

	/**
	 * Pop Up aufrufen, das ein Feedback mit der passenden @param msg Nachricht
	 * befüllt wird
	 */
	public void feedbackPopUp(String msg) {
		PopUpManager pop = new PopUpManager(new Stage(), root);
		pop.feedbackPopUp("PopUpView.fxml", suchen, msg);
	}

	/**
	 * Such felder werden Geladen
	 * 
	 * @return Teil mit Werten aus der Suche
	 */
	public Eigenfertigungsteil fillTeil() {
		Eigenfertigungsteil neu = new Eigenfertigungsteil();
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

	/**
	 * Felder anzeigen oder ausblenden
	 * 
	 * @param bool: True -> Felder anzeigen False -> Felder ausblenden
	 */
	public void removeFelder(Boolean bool) {
		teileBezeichnung.setVisible(bool);
		farbe.setVisible(bool);
		teileArt.setVisible(bool);
		mengenEinheit.setVisible(bool);
		planungsArt.setVisible(bool);
		teileGueltigVon.setVisible(bool);
		teileGueltigBis.setVisible(bool);
		labelBezeichnung.setVisible(bool);
	}

	/**
	 * Die Liste wird aktiv geschalten, genau so wie die Knoepfe für diese Liste
	 * Falls Inhalt enthalten, wird dieser ebenfalls geladen.
	 * 
	 * @param t
	 */
	public void activateFelder(Eigenfertigungsteil t) {
		clearTable();
		vBox.setVisible(true);
		foundElement.setText("ID: " + t.getTeilenummer() + "     " + t.getBezeichnung());
		listView.setVisible(true);
		suchenBack.setVisible(true);
		TeileMenge tm;
		// Spaltenbefuellung ueber Namensgleichheit
		teilenummer.setCellValueFactory(new PropertyValueFactory<TeileMenge, Integer>("teilenummer"));
		bezeichnung.setCellValueFactory(new PropertyValueFactory<TeileMenge, String>("bezeichnung"));
		menge.setCellValueFactory(new PropertyValueFactory<TeileMenge, Integer>("menge"));
		for (Stuecklistenelement st : t.getBaukastenstueckliste()) {
			tm = new TeileMenge(st.getTeil());
			tm.setMenge(st.getMenge());
			listView.getItems().add(tm); // Liste fuellen
		}
		listView.refresh();
		listView.setEditable(true);
		menge.setEditable(true);
		hinzufuegen.setVisible(true);
		speichern.setVisible(true);
		anchor.setVisible(true);

	}

	/**
	 * User drückt auf Hinzufügen Pop Up zum Suchen und anlegen von Teilen wird
	 * gerufen Das ausgewaehlte/angelegte Teil wird dann in die Liste geladen Wobei
	 * nur Id, Bezeichnung und Menge wird in die Listegeladen
	 */
	public void userHinzufuegen() {
		PopUpManager pop = new PopUpManager(new Stage(), root);
		stueck = pop.teileAnlegenPopUp(teileService, suchErg, suchen);
		if (stueck != null) {
			TeileMenge tm = new TeileMenge(stueck);
			// Spaltenbefuellung ueber Namensgleichheit
			teilenummer.setCellValueFactory(new PropertyValueFactory<TeileMenge, Integer>("teilenummer"));
			bezeichnung.setCellValueFactory(new PropertyValueFactory<TeileMenge, String>("bezeichnung"));
			menge.setCellValueFactory(new PropertyValueFactory<TeileMenge, Integer>("menge"));
			menge.setCellFactory(TextFieldTableCell.<TeileMenge, Integer>forTableColumn(new IntegerStringConverter()));
			listView.getItems().add(tm); // Liste fuellen
			listView.refresh();
		}
	}

	/**
	 * User wählt eine Reihe aus und drükt auf entfernen Element aus der Liste wird
	 * entfernt
	 */
	public void userEntfernen() {
		try {
			TablePosition pos = (TablePosition) listView.getSelectionModel().getSelectedCells().get(0);
			int index = pos.getRow();
			listView.getItems().remove(index);
			listView.refresh();
		} catch (IndexOutOfBoundsException e) {
			if (listView.getItems().size() == 0) {
				feedbackPopUp("Liste ist leer");
			} else {
				feedbackPopUp("Element auswählen");
			}
		}
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

	public void userListView() {

	}

	/**
	 * User drueckt auf Speichern Listen Inhalt wird gelesen und das Teil bekommt
	 * diesen Inhalt als Baukastenstueckliste gesichert
	 */
	public void userSpeichern() {
		listView.getItems().size();
		List<Stuecklistenelement> sListe = new LinkedList<>();
		Stuecklistenelement stueckListenEl;
		for (int i = 0; i < listView.getItems().size(); i++) {
			stueckListenEl = new Stuecklistenelement();
			stueckListenEl.setTeil(listView.getItems().get(i).getTeil());
			stueckListenEl.setMenge(listView.getItems().get(i).getMenge());
			sListe.add(stueckListenEl);
		}
		suchErg.replaceBaukastenstueckliste(sListe);
		try {

			if (teileService.changeTeil(suchErg)) {
				feedbackPopUp("Erfolgreich gesichert");
			} else {
				feedbackPopUp("Misserfolg");
			}

		} catch (InvalidTeilException it) {
			feedbackPopUp(it.getMessage());
		}

	}

	public void userEditMenge(TableColumn.CellEditEvent<TeileMenge, Integer> e) throws IOException {
		e.getRowValue().setMenge(e.getNewValue());
	}

	/**
	 * Tabellen Cache wird geleert.
	 */
	public void clearTable() {
		listView.getItems().clear();
	}

}
