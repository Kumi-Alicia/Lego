package modeles;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DemandeSauvegarde {
	public static String display () {
		String nom = "";
		Stage window = new Stage();
		window.setMinWidth(250);
		Label label = new Label();
		label.setText("Veuillez choisir le nom de la sauvegarde");
		Button closeButton = new Button("Valider le nom");
		closeButton.setOnAction(e->
		window.close()
		);
		TextField text = new TextField();
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,text,closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return text.getText();
	}
}
