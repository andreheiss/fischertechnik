package gui.general.popUps;

import java.io.IOException;
import java.util.List;

import domain.betriebsmittelverwaltung.core.Betriebsmittel;
import domain.teileverwaltung.core.Stuecklistenelement;
import domain.teileverwaltung.core.Teil;
import domain.teileverwaltung.providedservices.ITeilService;
import gui.general.popUps.BMV_MultiplePopUpController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class PopUpManager {

	
	@FXML
	private Stage popUp;
	
	@FXML
	private Parent root;
	
	public PopUpManager(Stage popUp, Parent root) {
		popUp = this.popUp;
		root = this.root;
	}
	
	public void feedbackPopUp(String popUpFensterFxml, Button closeWindwoRelation, String msg) {
		popUp = new Stage();
		popUp.setResizable(false);
		popUp.setHeight(195);
		popUp.setWidth(500);
		if(msg.contains("Erfolgreich")) {
			popUp.setTitle("Erfolgreich");
		}
		try {
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource(popUpFensterFxml)); //Pop Up das wir laden wollen
//			root = FXMLLoader.load(getClass().getResource(popUpFensterFxml));  
		    loader.setController(new PopUpController(msg)); //View braucht nun keine Controller Angabe
		    root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		popUp.setScene(scene); //Das geladene Anzeigen
		popUp.initModality(Modality.APPLICATION_MODAL); //Sorgt dafür das es ein Pop Up wird
		popUp.initOwner(closeWindwoRelation.getScene().getWindow());//Wer ist vorherige Besitzer des Fokus
		popUp.showAndWait();
		
	}
	
	
	public void lieferantPopUp(Button closeWindwoRelation) {
		popUp = new Stage();
		popUp.setResizable(false);
		popUp.setHeight(400);
		popUp.setWidth(600);
		try {
			root = FXMLLoader.load(getClass().getResource("TVLieferantenAnlegen.fxml"));  //Pop Up das wir laden wollen
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		popUp.setTitle("Lieferant anlegen");
		popUp.setScene(scene); //Das geladene Anzeigen
		popUp.initModality(Modality.APPLICATION_MODAL); //Sorgt dafür das es ein Pop Up wird
		popUp.initOwner(closeWindwoRelation.getScene().getWindow());//Wer ist vorherige Besitzer des Fokus
		popUp.showAndWait();
	}
	
	public Teil mehrfachAuswahlPopUp(List<Teil> liste,Button closeWindowRelation) {
		Teil clickErg = null;
		popUp = new Stage();
		popUp.setResizable(false);
		popUp.setHeight(400);
		popUp.setWidth(600);
		TeilMultiplePopUpController auswahlPop = new TeilMultiplePopUpController(liste);
		try {
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("TeilMultChoicePopUp.fxml")); //Pop Up das wir laden wollen
//			root = FXMLLoader.load(getClass().getResource(popUpFensterFxml)); 
		    loader.setController(auswahlPop);
		    root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		popUp.setTitle("Suchergebnisse");
		popUp.setScene(scene); //Das geladene Anzeigen
		popUp.initModality(Modality.APPLICATION_MODAL); //Sorgt dafür das es ein Pop Up wird
		popUp.initOwner(closeWindowRelation.getScene().getWindow());//Wer ist vorherige Besitzer des Fokus
		popUp.showAndWait();
	    clickErg = auswahlPop.getClickErgebnis();
		return clickErg;
	}
	
	public Teil teileAnlegenPopUp(ITeilService s,Teil aktuell, Button closeWindowRelation) {
		Teil clickErg = null;
		popUp = new Stage();
		popUp.setResizable(false);
		popUp.setHeight(700);
		popUp.setWidth(800);
		TeileAnlegenPopUpController anlegenPop = new TeileAnlegenPopUpController(s,aktuell);
		try {
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("TeileAnlegenPopUp.fxml")); //Pop Up das wir laden wollen
//			root = FXMLLoader.load(getClass().getResource(popUpFensterFxml)); 
		    loader.setController(anlegenPop);
		    root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		popUp.setTitle("Teil Anlegen");
		popUp.setScene(scene); //Das geladene Anzeigen
		popUp.initModality(Modality.APPLICATION_MODAL); //Sorgt dafür das es ein Pop Up wird
		popUp.initOwner(closeWindowRelation.getScene().getWindow());//Wer ist vorherige Besitzer des Fokus
		popUp.showAndWait();
	    clickErg = anlegenPop.getErgebnis();
		return clickErg;
		
	}
	
	public Betriebsmittel bmMehrfachauswahlPopUp(List<Betriebsmittel> liste, Button closeWindowRelation) {
		Betriebsmittel clickErg = null;
		popUp = new Stage();
		popUp.setResizable(false);
		popUp.setHeight(400);
		popUp.setWidth(600);
		BMV_MultiplePopUpController auswahlPop = new BMV_MultiplePopUpController(liste);
		try {
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("BMV_MultChoicePopUp.fxml")); //Pop Up das wir laden wollen
//			root = FXMLLoader.load(getClass().getResource(popUpFensterFxml)); 
		    loader.setController(auswahlPop);
		    root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		popUp.setTitle("Suchergebnisse");
		popUp.setScene(scene); //Das geladene Anzeigen
		popUp.initModality(Modality.APPLICATION_MODAL); //Sorgt dafür das es ein Pop Up wird
		popUp.initOwner(closeWindowRelation.getScene().getWindow());//Wer ist vorherige Besitzer des Fokus
		popUp.showAndWait();
	    clickErg = auswahlPop.getClickErgebnis();
		return clickErg;
	}
	
	

}
