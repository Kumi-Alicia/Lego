package main;
	
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Camera;
import javafx.scene.Group;
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
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;


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
		SmartGroup group = new SmartGroup();	
		group.getChildren().add(box);
		group.getChildren().add(cylindre);
		group.getChildren().add(cylindre2);
		box.translateYProperty().set(10);
		cylindre.translateXProperty().set(25);
		cylindre.translateYProperty().set(-8);
		cylindre2.translateXProperty().set(-25);
		cylindre2.translateYProperty().set(-8);
		Camera camera = new PerspectiveCamera();
		SubScene subScene3D = new SubScene(group, WIDTH, HEIGHT,true,null);
		subScene3D.setCamera(camera);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vues/VuePageJeu.fxml"));
		AnchorPane pane = loader.load();
		pane.getChildren().add(subScene3D);
		Scene scene = new Scene(pane);
		group.translateXProperty().set(WIDTH/2);
		group.translateYProperty().set(HEIGHT/2);
		group.translateZProperty().set(-1200);

		initMouseControl(group, subScene3D, primaryStage);
		
		primaryStage.setTitle("Genuine Coder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Box prepareBox() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.ROYALBLUE);
		Box box = new Box(100,20,50);
		box.setMaterial(material);
		return box;
	}
	private Cylinder prepareCylinder() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.ROYALBLUE);
		Cylinder cylindre = new Cylinder(15,16,10);
		cylindre.setMaterial(material);
		return cylindre;
	}
	
	private void initMouseControl(SmartGroup group, SubScene subScene3D, Stage stage) {
		Rotate xRotate;
		Rotate yRotate;
		group.getTransforms().addAll(xRotate = new Rotate(0,Rotate.X_AXIS), yRotate = new Rotate(0,Rotate.Y_AXIS));
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
			group.translateZProperty().set(group.getTranslateZ() + delta);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	 
}
