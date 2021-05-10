package gui.general.popUps;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import domain.shared.Farbe;
import domain.shared.Mengeneinheit;
import domain.shared.Planungsart;
import domain.shared.Teileart;
import domain.teileverwaltung.core.Eigenfertigungsteil;
import domain.teileverwaltung.core.Stuecklistenelement;
import domain.teileverwaltung.core.Teil;
import gui.teileverwaltung.stueckliste.TeileMenge;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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

public class TeilMultiplePopUpController {

	@FXML
	private TableView<Teil> tableView;
	@FXML
	private TableColumn<Teil, Integer> teilenummer;
	@FXML
	private TableColumn<Teil, String> bezeichnung;
	@FXML
	private TableColumn<Teil, Teileart> teileart;
	@FXML
	private TableColumn<Teil, Farbe> farbe;
	@FXML
	private TableColumn<Teil, Planungsart> planart;
	@FXML
	private TableColumn<Teil, Mengeneinheit> mengeneinheit;

	@FXML
	private Label ueberschrift;

	private List<Teil> suchErgebnisse;
	private Teil clickErgebnis;

	private String message;
	private ObservableList l;

	public TeilMultiplePopUpController(List<Teil> liste) {
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
				clickErgebnis = (Teil) tableView.getItems().get(index);
				Stage stage = (Stage) tableView.getScene().getWindow();
				stage.close();
			} catch (IndexOutOfBoundsException er) {
				//Hier könnte ihr Text stehen
			}

		}
	}

	public void userTableView() {

	}

	public void activateFelder(List<Teil> t) {
		// Spaltenbefuellung ueber Namensgleichheit
		teilenummer.setCellValueFactory(new PropertyValueFactory<Teil, Integer>("teilenummer"));
		bezeichnung.setCellValueFactory(new PropertyValueFactory<Teil, String>("bezeichnung"));
		planart.setCellValueFactory(new PropertyValueFactory<Teil, Planungsart>("planart"));
		farbe.setCellValueFactory(new PropertyValueFactory<Teil, Farbe>("farbe"));
		teileart.setCellValueFactory(new PropertyValueFactory<Teil, Teileart>("teileart"));
		mengeneinheit.setCellValueFactory(new PropertyValueFactory<Teil, Mengeneinheit>("mengeneinheit"));
		for (Teil st : suchErgebnisse) {
			tableView.getItems().add(st);
		}
		tableView.refresh();
	}

	// Spuckt das ergebnis des Geklickten Feldes aus
	public Teil getClickErgebnis() {
		return clickErgebnis;
	}

	
}
