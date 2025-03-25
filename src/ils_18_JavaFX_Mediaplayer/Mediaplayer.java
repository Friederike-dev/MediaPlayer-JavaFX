package ils_18_JavaFX_Mediaplayer;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Mediaplayer extends Application {
	
	@Override
	public void start(Stage meineStage) throws Exception {
		//eine Instanz von FXMLLoader erzeugen
		FXMLLoader meinLoader = new FXMLLoader(getClass().getResource("sb_miniplayer.fxml"));
		//die Datei laden
		Parent root = meinLoader.load();
		//den Conroller beschaffen
		FXMLController meinController = meinLoader.getController();
		//und die Bühne übergeben
		meinController.setMeineStage(meineStage);
		
		//die Szene erzeugen
		//an den Konstruktor werden der oberste Knoten und die Größe übergeben
		Scene meineScene = new Scene (root, 800, 600);
		//den Titel über Stage setzen
		meineStage.setTitle("JavaFX Multimedia-Player F.Hemsen");
		
		//die Szene setzen
		meineStage.setScene(meineScene);
		
		//im Vollbild darstellen
		meineStage.setMaximized(true);
		
		//Programm-Symbol hinzufügen
		File bilddatei = new File ("icons/icon.gif");
		Image bild = new Image (bilddatei.toURI().toString());
		meineStage.getIcons().add(bild);
		
		//und anzeigen
		meineStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//der Start
		launch(args);
	}

}
