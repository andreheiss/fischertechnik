package gui;
	
import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import domain.artificialClock.IClockService;
import gui.general.LogInController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static Injector injector;
	private static Stage stg;
	
	public void start(Stage primaryStage) {
	
			stg = primaryStage;
			
			FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("general/LogInView.fxml"));
		  
		    loader.setControllerFactory(new Callback<Class<?>, Object>(){
		    	//wenn Factory eine bestimmtes Objekt benötigt, wird dieses durch Callback durch Injektor bereitgestellt!
		    	public Object call(Class<?>klasse) {
		    		
		    		return injector.getInstance(klasse);
		    	}
		    });
		    	
		//    }injector.getInstance(null)); //FX-Callback
		    try {
		    	Scene scene;
		        Parent root = loader.load();
		        scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setResizable(false);
				primaryStage.setTitle("KESS");
				primaryStage.setScene(scene);
				primaryStage.show();
			 } catch (IOException e) {
			        throw new IllegalStateException(e);
			    }

	}
	
	public void changeScene(String fxml,int x,int y) throws IOException { //fxml File wird hier übergeben, zu welchem Fenster man wechselt um nicht hardcodiert das file anzugeben
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
	    loader.setControllerFactory(injector::getInstance); //FX-Callback
		Parent pane = loader.load();
		stg.setHeight(y);
		stg.setWidth(x);
		stg.getScene().setRoot(pane); // macht das selbe wie primaryStage.setScene(scene)
		stg.setX(0);
		stg.setY(0);
		
	}

	
	
	public static void startGui(Injector inj) {
		injector = inj;
		Main.launch();

	}
	
	public void stop() {
		injector.getInstance(IClockService.class).stop();
	}
	


}
