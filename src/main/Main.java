package main;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import modeles.SmartGroup;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.LinkedList;
import java.util.Random;

import controleurs.Controleur3D;
import controleurs.ControleurCatalegoPiece;
import controleurs.ControleurPageAcceuil;



public class Main extends Application {
	
	@Override
	public void start(Stage Catalego) {
		try {
			ControleurPageAcceuil cAccueil = new ControleurPageAcceuil();
			ControleurCatalegoPiece cPiece = new ControleurCatalegoPiece();
			Controleur3D c3D = new Controleur3D();
			
			Parent root = FXMLLoader.load(getClass().getResource("../vues/VuePageAcceuil.fxml"));
			Scene scene = new Scene(root);
			Catalego.setScene(scene);
			Catalego.show();	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
		
		

}
