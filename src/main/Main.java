package main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage Catalego) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../vues/VueCatalego.fxml"));
			Catalego.setTitle("Catalego");
			Catalego.setScene(new Scene(root));
			Catalego.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
