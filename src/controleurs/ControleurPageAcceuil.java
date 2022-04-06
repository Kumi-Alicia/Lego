package controleurs;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleurPageAcceuil {
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	public void switchToPageJeu(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../vues/VuePageJeu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
