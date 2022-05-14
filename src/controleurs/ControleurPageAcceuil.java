package controleurs;

import java.io.IOException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modeles.SmartGroup;

public class ControleurPageAcceuil {
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	public void switchToPageJeu(ActionEvent event) throws IOException{
		Controleur3D c = new Controleur3D();
		c.initialisation(event);
	}
	public void fermerApplication(ActionEvent event) throws IOException{
		System.out.println("L'application va se fermer!");
		System.exit(0);
	}
}