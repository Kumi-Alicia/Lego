package controleurs;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControleurQ2 {
	public Stage stage;
	public Scene scene;
	public Parent root;

    @FXML
    void quitter(ActionEvent event) throws IOException {
    	this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.stage.close();
    }
}
