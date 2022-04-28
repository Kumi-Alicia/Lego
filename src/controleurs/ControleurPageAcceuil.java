package controleurs;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class ControleurPageAcceuil {
	public Stage stage;
	public Scene scene;
	public Parent root;

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
	private SmartGroup ensembleGroup = new SmartGroup();
	private final DoubleProperty angleX = new SimpleDoubleProperty(0);
	private final DoubleProperty angleY = new SimpleDoubleProperty(0);

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
		subScene3D.setOnMouseClicked(event -> {
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
			SmartGroup nveauGroup = new SmartGroup();
			Box nvlleBox = prepareBox();
			Cylinder nveauCylindre = prepareCylinder();
			Cylinder nveauCylindre2 = prepareCylinder();
			nveauGroup.getChildren().add(nvlleBox);
			nveauGroup.getChildren().add(nveauCylindre);
			nveauGroup.getChildren().add(nveauCylindre2);
			ensembleGroup.getChildren().add(nveauGroup);
			nvlleBox.translateYProperty().set(10);
			nveauCylindre.translateXProperty().set(25);
			nveauCylindre.translateYProperty().set(-8);
			nveauCylindre2.translateXProperty().set(-25);
			nveauCylindre2.translateYProperty().set(-8);
			ensembleGroup.getChildren().get(1).setOnMouseClicked(event1 -> {
				System.out.println("clic");
			});
			ensembleGroup.getChildren().get(0).setOnMouseClicked(event2 -> {
				System.out.println("clac");
			});
			nveauGroup.translateXProperty().set(ensembleGroup.getChildren().get(ensembleGroup.getChildren().size()-3).getTranslateX());
			nveauGroup.translateYProperty().set(ensembleGroup.getChildren().get(ensembleGroup.getChildren().size()-3).getTranslateY()-20);
			nveauGroup.translateZProperty().set(-800);
			initMouseControl(nveauGroup, subScene3D, stage);
			
			
			
		});
		subScene3D.setOnMouseDragged(event -> {
			//angleX.set(anchorAngleX - anchorY - event.getSceneY());
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
		});	
		stage.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			node.translateZProperty().set(node.getTranslateZ() - delta);
		});
	}

	public void switchToPageJeu(ActionEvent event) throws IOException{
		Cylinder cylindre = prepareCylinder();
		Cylinder cylindre2 = prepareCylinder();
		Box box = prepareBox();
		Cylinder cylindre3 = prepareCylinder();
		Cylinder cylindre4 = prepareCylinder();
		Box box2 = prepareBox();
		SmartGroup group = new SmartGroup();
		SmartGroup group2 = new SmartGroup();
		group.getChildren().add(box);
		group.getChildren().add(cylindre);
		group.getChildren().add(cylindre2);
		group2.getChildren().add(box2);
		group2.getChildren().add(cylindre3);
		group2.getChildren().add(cylindre4);
		ensembleGroup.getChildren().add(group);
		ensembleGroup.getChildren().add(group2);
		box.translateYProperty().set(10);
		cylindre.translateXProperty().set(25);
		cylindre.translateYProperty().set(-8);
		cylindre2.translateXProperty().set(-25);
		cylindre2.translateYProperty().set(-8);
		box2.translateYProperty().set(10);
		cylindre3.translateXProperty().set(25);
		cylindre3.translateYProperty().set(-8);
		cylindre4.translateXProperty().set(-25);
		cylindre4.translateYProperty().set(-8);
		Camera camera = new PerspectiveCamera();
		SubScene subScene3D = new SubScene(ensembleGroup, WIDTH, HEIGHT,true,null);
		subScene3D.setCamera(camera);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vues/VuePageJeu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		AnchorPane pane = loader.load();
		pane.getChildren().add(subScene3D);
		Scene scene = new Scene(pane);
		group.translateXProperty().set(WIDTH/2);
		group.translateYProperty().set(HEIGHT/1.5);
		group.translateZProperty().set(-800);
		group2.translateXProperty().set(WIDTH/1.5);
		group2.translateYProperty().set(HEIGHT/1.5);
		group2.translateZProperty().set(-800);
		for (int i=0;i<ensembleGroup.getChildren().size();i++) {
			initMouseControl(ensembleGroup.getChildren().get(i), subScene3D, stage);
		}

		stage.setTitle("Page jeu");
		stage.setScene(scene);
		stage.show();
	}
	public void fermerApplication(ActionEvent event) throws IOException{
		 System.out.println("L'application va se fermer!");
		 System.exit(0);
	}
}
