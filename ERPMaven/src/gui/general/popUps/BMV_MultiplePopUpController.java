package gui.general.popUps;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.shared.Betriebsmittelart;
import domain.shared.Farbe;
import domain.shared.Mengeneinheit;
import domain.shared.Planungsart;
import domain.shared.Teileart;
import domain.teileverwaltung.core.Eigenfertigungsteil;
import domain.teileverwaltung.core.Stuecklistenelement;
import domain.teileverwaltung.core.Teil;
import gui.teileverwaltung.stueckliste.TeileMenge;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BMV_MultiplePopUpController {

	@FXML
	private TableView<Betriebsmittel> tableView;
	@FXML
	private TableColumn<Betriebsmittel, Integer> betriebsmittelnummer;
	@FXML
	private TableColumn<Betriebsmittel, String> bezeichnung;
	@FXML
	private TableColumn<Betriebsmittel, Integer> werk; 
	@FXML
	private TableColumn<Betriebsmittel, Betriebsmittelart> betriebsmittelart;

	@FXML
	private Label ueberschrift;

	private List<Betriebsmittel> suchErgebnisse;
	private Betriebsmittel clickErgebnis;

	private String message;
	private ObservableList l;

	public BMV_MultiplePopUpController(List<Betriebsmittel> liste) {
		suchErgebnisse = liste;
	}

	@FXML
	private void initialize() { // Uberschrift wird gesetzt und Teile in Felder geladen
		ueberschrift.setText(suchErgebnisse.size() + " Ergebnisse");
		activateFelder(suchErgebnisse);
		tableView.refresh();
	}

	public void userDoubleClick(MouseEvent e) throws IOException {
		if (e.getClickCount() == 2) {
			try {
				TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
				int index = pos.getRow();
				clickErgebnis = (Betriebsmittel) tableView.getItems().get(index);
				Stage stage = (Stage) tableView.getScene().getWindow();
				stage.close();
			} catch (IndexOutOfBoundsException er) {
				//Hier könnte ihr Text stehen
			}

		}
	}

	public void userTableView() {

	}

	public void activateFelder(List<Betriebsmittel> bm) {
		// Spaltenbefuellung ueber Namensgleichheit
		betriebsmittelnummer.setCellValueFactory(new PropertyValueFactory<Betriebsmittel, Integer>("betriebsmittelnummer"));
		bezeichnung.setCellValueFactory(new PropertyValueFactory<Betriebsmittel, String>("bezeichnung"));
		werk.setCellValueFactory(new PropertyValueFactory<Betriebsmittel, Integer>("werksnummer")); 
	//	werk.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(cellData.getValue().getWerk().getWerksnummer()) );
		
		
		
		betriebsmittelart.setCellValueFactory(new PropertyValueFactory<Betriebsmittel, Betriebsmittelart>("art"));
		
		
		for (Betriebsmittel st : suchErgebnisse) {
			tableView.getItems().add(st);
			
		}
		tableView.refresh();
	}

	// Spuckt das ergebnis des Geklickten Feldes aus
	public Betriebsmittel getClickErgebnis() {
		return clickErgebnis;
	}

	
}
