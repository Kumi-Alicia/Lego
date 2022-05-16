package controleurs;

import java.io.IOException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modeles.ChoisirSauvegarde;
import modeles.SmartGroup;

public class ControleurPageAcceuil {
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	public void switchToPageJeu(ActionEvent event) throws IOException{
		Controleur3D c = new Controleur3D();
		c.initialisation(event);	
	}
	@FXML
    void tutoriel(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vues/VuePageTuto.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));  
		stage.showAndWait();
    }
	public void fermerApplication(ActionEvent event) throws IOException{
		System.exit(0);
	}
	
	@FXML
    void charger(ActionEvent event) throws ClassNotFoundException, IOException {
		Controleur3D c = new Controleur3D();
		c.initialisation(event);	
		c.charger(event);
    }
}