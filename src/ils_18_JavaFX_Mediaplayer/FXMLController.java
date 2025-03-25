package ils_18_JavaFX_Mediaplayer;

import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FXMLController {

	//f�r die B�hne
	private Stage meineStage;

	//f�r den Player
	private MediaPlayer mediaplayer;

	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//f�r das Image auf dem PlayOrPause -Button
	private String imagePlay = "icons/play.gif";
	private String imagePause = "icons/pause.gif";



	//zur Verbindung mit der fxml-Datei
	//f�r die MediaView
	@FXML private MediaView mediaview;
	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//f�r die ImageView mit dem Symbol f�r den Lautsprecher und den Play-Oder-Pause-Button
	@FXML private ImageView symbol, playOrPauseSymbol;

	//f�r das Listenfeld; es speichert Eintr�ge vom Typ String
	@FXML private ListView<String> liste;

	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//########################################################## f�r Einsendeaufgabe 3 ###############################################
	//f�r die Buttons Play/Pause, Stop und Lautsprecher
	@FXML private Button playPauseButton, stopButton, speakerButton;


	//die Methode setzt die B�hne auf den �bergebenen Wert
	public void setMeineStage(Stage meineStage) {
		this.meineStage = meineStage;
	}

	//die Methode zum Laden; das ActionEvent wird �bergeben
	@FXML protected void ladenKlick (ActionEvent event) {
		//eine neue Instanz der Klasse FileChooser erzeugen
		FileChooser oeffnenDialog = new FileChooser();
		//den Titel f�r den Dialog setzen.
		oeffnenDialog.setTitle("Datei �ffnen");
		//den Filter setzen
		oeffnenDialog.getExtensionFilters().add(new ExtensionFilter("Audiodateien", "*.mp3"));
		oeffnenDialog.getExtensionFilters().add(new ExtensionFilter("Videodateien", "*.mp4"));
		//es soll der Programmordner angezeigt werden, weil darin auch der Media-Ordner ist.
		oeffnenDialog.setInitialDirectory(new File(System.getProperty("user.dir")));

		//den �ffnen-Dialog anzeigen und das Ergebnis beschaffen
		File datei = oeffnenDialog.showOpenDialog(meineStage);
		//wurde eine Datei ausgew�hlt
		if (datei != null) 
			//dann �ber eine eigene Methode laden
			dateiLaden(datei);

	}

	//die Methode zum Stoppen; das ActionEvent wird �bergeben
	@FXML protected void stoppKlick(ActionEvent event) {
		//gibt es �berhaupt einen MediaPlayer?
		if (mediaplayer != null)
			//dann anhalten
			mediaplayer.stop();

		//########################################################## f�r Einsendeaufgabe 2 ###############################################
		//und das Bild f�r das PlayOrPauseSymbol in der Symbolleiste aktualisieren
		playPauseImageAktualisieren(imagePlay);
	}

	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//die Methode f�r die Pause f�r das Wiedergabe-Men�; das ActionEvent wird �bergeben
	@FXML protected void pauseKlick(ActionEvent event) {
		//gibt es �berhaupt einen MediaPlayer und spielt er gerade etwas ab?
		if (mediaplayer != null && mediaplayer.getStatus() == MediaPlayer.Status.PLAYING)
			//dann unterbrechen
			mediaplayer.pause();
		//und das Bild f�r das PlayOrPauseSymbol in der Symbolleiste aktualisieren
		playPauseImageAktualisieren(imagePlay);
	}

	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//die Methode f�r Play f�r das Wiedergabe-Men�; das ActionEvent wird �bergeben
	@FXML protected void playKlick(ActionEvent event) {
		//gibt es �berhaupt einen MediaPlayer und ist er pausiert oder gestoppt?
		if (mediaplayer != null && (mediaplayer.getStatus() == MediaPlayer.Status.PAUSED || mediaplayer.getStatus() == MediaPlayer.Status.STOPPED))
			//dann abspielen
			mediaplayer.play();
		//und das Bild f�r das PlayOrPauseSymbol in der Symbolleiste aktualisieren
		playPauseImageAktualisieren(imagePause);
	}

	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//Methode zum Aktualisieren des Bildes auf dem Play-Oder-Pause-Button; der Pfad zum erw�nschten Bild wird �bergeben
	public void playPauseImageAktualisieren(String imageUebergeben) {
		//f�r die Bilddatei
		String imageDatei = imageUebergeben;

		//das Bild erzeugen und anzeigen
		File bilddatei = new File(imageDatei);
		//mit setImage der Klasse ImageView das Bild zuweisen
		playOrPauseSymbol.setImage(new Image(bilddatei.toURI().toString()));

	}

	//########################################################## f�r Einsendeaufgabe 2 ###############################################
	//die Methode f�r die Wiedergabe; das ActionEvent wird �bergeben
	@FXML protected void playOrPauseKlick(ActionEvent event) {

		//l�uft schon eine Wiedergabe und der Status ist auf play?
		if(mediaplayer != null && mediaplayer.getStatus() == MediaPlayer.Status.PLAYING) {
			//dann pausieren
			mediaplayer.pause();
			//und das Bild f�r das PlayOrPauseSymbol in der Symbolleiste aktualisieren
			playPauseImageAktualisieren(imagePlay);
		}
		//l�uft schon eine Wiedergabe und der status ist auf pausiert oder gestoppt?
		if(mediaplayer != null && (mediaplayer.getStatus() == MediaPlayer.Status.PAUSED || mediaplayer.getStatus() == MediaPlayer.Status.STOPPED)) { 
			//dann weiter abspielen
			mediaplayer.play();
			//und das Bild f�r das PlayOrPauseSymbol in der Symbolleiste aktualisieren
			playPauseImageAktualisieren(imagePause);
		}
	}

	//die Methode f�r das Ein- und Ausschalten der Lautst�rke
	@FXML protected void lautsprecherKlick(ActionEvent event) {
		String dateiname = "";
		//gibt es �berhaupt einen MediaPlayer?
		if (mediaplayer != null)
			//ist die Lautst�rke 0?
			if(mediaplayer.getVolume() == 0) {
				//dann auf 100 setzen
				mediaplayer.setVolume(100);
				//und das normale Symbol setzen
				dateiname = "icons/mute.gif";
			} else {
				//sonst auf 0 setzen
				mediaplayer.setVolume(0);
				//und das durchgestrichene Symbol setzen
				dateiname = "icons/mute_off.gif";
			}
		//das Bild erzeugen und anzeigen
		File bilddatei = new File(dateiname);
		Image bild = new Image(bilddatei.toURI().toString());
		//mit setImage der Klasse ImageView das Bild zuweisen
		symbol.setImage(bild);
	}

	//die Methode zum Beenden; das ActionEvent wird �bergeben
	@FXML protected void beendenKlick(ActionEvent event) {
		Platform.exit();
	}

	//Methode l�dt eine Datei, die als Argument �bergeben wurde
	public void dateiLaden(File datei) {

		//########################################################## f�r Einsendeaufgabe 3 ###############################################
		//die Buttons aktivieren, da eine Datei geladen wird
		playPauseButton.setDisable(false);
		stopButton.setDisable(false);
		speakerButton.setDisable(false);

		//l�uft schon eine Wiedergabe?
		if(mediaplayer != null && mediaplayer.getStatus() == MediaPlayer.Status.PLAYING) {
			//dann anhalten
			mediaplayer.stop();
		}
		//das Medium, den Mediaplayer und die MediaView erzeugen
		try {
			Media medium = new Media(datei.toURI().toString());
			//eine neue Instanz der Klasse MediaPlaye erstellen
			mediaplayer = new MediaPlayer(medium);
			mediaview.setMediaPlayer(mediaplayer);
			//die Wiedergabe starten
			mediaplayer.play();
			//########################################################## f�r Einsendeaufgabe 2 ###############################################
			//und das Bild f�r das PlayOrPauseSymbol in der Symbolleiste aktualisieren
			playPauseImageAktualisieren(imagePause);


			//########################################################## f�r Einsendeaufgabe 1 ###############################################
			//wenn der Pfad im Listenfeld ist
			if (liste.getItems().contains(datei.toString())) {
				//dann den Fokus auf diesen Eintrag setzen
				liste.getSelectionModel().select(datei.toString());
			} else {
				//den Pfad in das Listenfeld eintragen
				liste.getItems().add(datei.toString());
				//und den Fokus auf diesen Eintrag setzen
				liste.getSelectionModel().select(datei.toString());
			}
			//und die Titelleiste anpassen
			meineStage.setTitle("JavaFX Multimedia-Player " + datei.toString());

		}
		catch (Exception ex) {
			//den Dialog erzeugen und anzeigen
			Alert meinDialog = new Alert(AlertType.INFORMATION, "Beim Laden hat es ein Problem gegeben.\n" + ex.getMessage());
			//den Text setzen
			meinDialog.setHeaderText("Bitte beachten");
			meinDialog.initOwner(meineStage);
			//den Dialog anzeigen
			meinDialog.showAndWait();
		}
	}


	//########################################################## f�r Einsendeaufgabe 1 ###############################################

	//EventHandler f�r Eintr�ge in ListView geht nicht, nur aufs ganze ListView. Aber man k�nnte alternativ ListCell Instanzen erstellen und denen einen EventHandler zuweisen

	//Methode zum Ausf�hren, wenn im Feld ListView geklickt wurde; das MouseEvent wird �bergeben
	@FXML protected void listenEintragKlick(MouseEvent event) {

		//erstmal das genaue angeklickte Element ermitteln
		Node clickedNode = event.getPickResult().getIntersectedNode();

		//wir wollen die Funktion nur beim linken Mouse-Button
		if(event.getButton() == MouseButton.PRIMARY) {

			//getClickCount ist nicht n�tig, da ein Klick schon ausreicht

			// Sicherstellen, dass wir den richtigen Node innerhalb der ListView erhalten (n�mlich ein ListCell) und nicht ein untergeordnetes Element
			while (clickedNode != null && !(clickedNode instanceof ListCell)) {
				clickedNode = clickedNode.getParent();
			}

			// Wenn auf leeren Raum oder leere Zelle geklickt wurde, Auswahl aufheben
			if (clickedNode == null || ((ListCell<?>) clickedNode).isEmpty()) {  
				liste.getSelectionModel().clearSelection();
			} else {
				// Wenn also auf eine ListCell geklickt wurde, k�nnen wir sie der Variablen "auswahl" zuweisen
				//		        ListCell<?> clickedCell = (ListCell<?>) clickedNode;
				//		        String auswahl = (String) clickedCell.getItem();
				// in unserem Fall reicht diese Anweisung:
				String auswahl = liste.getSelectionModel().getSelectedItem();

				//nochmals pr�fem, dass der Eintrag auch in der Liste ist
				if (auswahl != null && liste.getItems().contains(auswahl)) {
					//den Eintrag als File in die Methode zum Laden �bergeben
					File datei = new File(auswahl);
					dateiLaden(datei);
				}
			}
		}		
	}
}
