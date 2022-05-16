package controleurs;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControleurTuto {
	public Stage stage;
	public Scene scene;
	public Parent root;

    @FXML
    void Q1(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("../vues/VueQ1.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void Q2(ActionEvent event) {
    	
    }

    @FXML
    void Q3(ActionEvent event) {

    }

    @FXML
    void Q4(ActionEvent event) {

    }

    @FXML
    void Q5(ActionEvent event) {

    }

    @FXML
    void quitter(ActionEvent event) {
    	this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		this.stage.close();
    }
}
