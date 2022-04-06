package main;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.SubScene;
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
import java.util.Random;


public class Main extends Application {
	/*
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
	}*/

	class SmartGroup extends Group{
		Rotate r;
		Transform t = new Rotate();
		
		void rotateByX(int ang) {
			r = new Rotate(ang, Rotate.X_AXIS);
			t = t.createConcatenation(r);
			this.getTransforms().clear();
			this.getTransforms().addAll(t);
		}
		void rotateByY(int ang) {
			r = new Rotate(ang, Rotate.Y_AXIS);
			t = t.createConcatenation(r);
			this.getTransforms().clear();
			this.getTransforms().addAll(t);
		}
	}
	
	private static final Color[] colors = {Color.ROYALBLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.PURPLE};
	private static final float WIDTH = 1400;
	private static final float HEIGHT = 800;
	
	private double anchorX, anchorY;
	private double anchorAngleX = 0;
	private double anchorAngleY = 0;
	private final DoubleProperty angleX = new SimpleDoubleProperty(0);
	private final DoubleProperty angleY = new SimpleDoubleProperty(0);


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Cylinder cylindre = prepareCylinder();
		Cylinder cylindre2 = prepareCylinder();
		Box box = prepareBox();
		SmartGroup lego = prepareLego(box, cylindre, cylindre2);
		SmartGroup legos = new SmartGroup();
		legos.getChildren().add(lego);
		Camera camera = new PerspectiveCamera();
		SubScene subScene3D = new SubScene(legos, WIDTH, HEIGHT,true,null);
		subScene3D.setCamera(camera);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vues/VuePageJeu.fxml"));
		AnchorPane pane = loader.load();
		pane.getChildren().add(subScene3D);
		Scene scene = new Scene(pane);
		lego.translateXProperty().set(WIDTH/2);
		lego.translateYProperty().set(HEIGHT/2);
		lego.translateZProperty().set(-1000);
		int i=0;
		for (i=0;i<legos.getChildren().size();i++) {
			initMouseControl(legos.getChildren().get(i), subScene3D, primaryStage);
		}
		
		
		
		primaryStage.setTitle("Genuine Coder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Box prepareBox() {
		Box box = new Box(100,20,50);
		return box;
	}
	private Cylinder prepareCylinder() {
		Cylinder cylindre = new Cylinder(15,16,10);
		return cylindre;
	}
	private SmartGroup prepareLego(Box box, Cylinder cylindre, Cylinder cylindre2) {
		Random r = new Random();
		SmartGroup lego = new SmartGroup();
		lego.getChildren().add(box);
		lego.getChildren().add(cylindre);
		lego.getChildren().add(cylindre2);
		box.translateYProperty().set(10);
		cylindre.translateXProperty().set(25);
		cylindre.translateYProperty().set(-8);
		cylindre2.translateXProperty().set(-25);
		cylindre2.translateYProperty().set(-8);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(colors[r.nextInt(6)]);
		box.setMaterial(material);
		cylindre.setMaterial(material);
		cylindre2.setMaterial(material);
		return lego;
	}
	
	private void initMouseControl(Node node, SubScene subScene3D, Stage stage) {
		Rotate xRotate;
		Rotate yRotate;
		node.getTransforms().addAll(xRotate = new Rotate(0,Rotate.X_AXIS), yRotate = new Rotate(0,Rotate.Y_AXIS));
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);
		
		subScene3D.setOnMousePressed(event -> {
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
		});
		
		subScene3D.setOnMouseDragged(event -> {
			angleX.set(anchorAngleX - anchorY - event.getSceneY());
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
		});
		
		stage.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			node.translateZProperty().set(node.getTranslateZ() - delta);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	 
}
