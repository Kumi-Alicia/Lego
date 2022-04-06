package controleurs;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.MyListener;
import modeles.Lego;

public class ControleurCatalego {
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
	
	public void switchToCatalegoPiece(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../vues/VueCatalegoPiece.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
