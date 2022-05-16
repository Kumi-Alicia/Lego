package modeles;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChoisirSauvegarde {
	public static String display () {
		String nom = "";
		Stage window = new Stage();
		window.setMinWidth(250);
		Label label = new Label();
		label.setText("Veuillez choisir la sauvegarde");
		Button closeButton = new Button("Valider le nom");
		closeButton.setOnAction(e->
		window.close()
		);
		ComboBox sauv = new ComboBox();
		File directory = new File("src/save");
		File[] subfiles = directory.listFiles();
		for(int i=0 ; i<subfiles.length; i++){
			sauv.getItems().add(subfiles[i].getName());
		}
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,sauv,closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return sauv.getSelectionModel().getSelectedItem().toString();
	}
}
