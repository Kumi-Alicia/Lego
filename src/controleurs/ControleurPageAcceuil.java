package controleurs;

import java.io.IOException;
import java.util.LinkedList;

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
import javafx.scene.shape.Circle;
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

	private double anchorX;
	private double anchorAngleY = 0;
	private int cote = 900;
	private SmartGroup plateau_jeu = new SmartGroup();
	Camera camera = new PerspectiveCamera();
	LinkedList<Node> pile1 = new LinkedList();
	LinkedList<Node> pile2 = new LinkedList();
	private LinkedList<LinkedList<Node>> plateau_jeu_liste = new LinkedList();
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
		Rotate yRotate;
		node.getTransforms().addAll(yRotate = new Rotate(0,Rotate.Y_AXIS));
		yRotate.angleProperty().bind(angleY);

		subScene3D.setOnMousePressed(event -> {
			anchorX = event.getSceneX();
			anchorAngleY = angleY.get();
		});
		plateau_jeu_liste.add(pile1);
		plateau_jeu_liste.add(pile2);
		for (int i=1; i<plateau_jeu.getChildren().size();i++) {
			Node lego = plateau_jeu.getChildren().get(i);
			double X = plateau_jeu.getChildren().get(i).getTranslateX();
			plateau_jeu.getChildren().get(i).setOnMouseClicked(event1 ->{
				for (int j=0; j<2; j++) {
					for (int k=0; k<plateau_jeu_liste.get(j).size(); k++) {
						if ((plateau_jeu_liste.get(j)).get(0).getTranslateX() == X ) {
							SmartGroup nveauGroup = nouveauLego();
							plateau_jeu_liste.get(j).add(lego);
							nveauGroup.setRotationAxis(Rotate.Y_AXIS);
							nveauGroup.setRotate((plateau_jeu_liste.get(j)).get(0).getRotate());
							nveauGroup.translateXProperty().set(X);
							nveauGroup.translateYProperty().set(plateau_jeu_liste.get(j).get(plateau_jeu_liste.get(j).size()-1).getTranslateY()-20);
							nveauGroup.translateZProperty().set(plateau_jeu.getChildren().get(plateau_jeu.getChildren().size()-3).getTranslateZ());
							initMouseControl(nveauGroup, subScene3D, stage);
							break;
						}

					}
				}

			});

		}



		subScene3D.setOnMouseDragged(event -> {
			plateau_jeu.setRotationAxis(Rotate.Y_AXIS);
			for (int l=0; l<plateau_jeu.getChildren().size();l++) {
				plateau_jeu.getChildren().get(l).setRotationAxis(Rotate.Y_AXIS);
			}
			angleY.set(anchorAngleY + anchorX - event.getSceneX());
			plateau_jeu.setRotate(anchorAngleY + anchorX - event.getSceneX());
			for (int l=0; l<plateau_jeu.getChildren().size();l++) {
				plateau_jeu.getChildren().get(l).setRotate(-(anchorAngleY + anchorX - event.getSceneX()));
			}
			
			
		});	
		stage.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			node.translateZProperty().set(node.getTranslateZ() - delta);
			node.translateYProperty().set(node.getTranslateY()-delta);
		});
	}
	public SmartGroup nouveauLego() {
		Cylinder cylindre = prepareCylinder();
		Cylinder cylindre2 = prepareCylinder();
		Box box = prepareBox();
		SmartGroup group = new SmartGroup();
		group.getChildren().add(box);
		group.getChildren().add(cylindre);
		group.getChildren().add(cylindre2);
		plateau_jeu.getChildren().add(group);
		box.translateYProperty().set(10);
		cylindre.translateXProperty().set(25);
		cylindre.translateYProperty().set(-8);
		cylindre2.translateXProperty().set(-25);
		cylindre2.translateYProperty().set(-8);
		return group;
	}

	public void switchToPageJeu(ActionEvent event) throws IOException{
		PhongMaterial material1 = new PhongMaterial();
		material1.setDiffuseColor(Color.GRAY);
		Box sol = new Box(cote,20,cote);
		sol.setMaterial(material1);
		SmartGroup plateau = new SmartGroup();
		plateau.getChildren().add(sol);
		plateau_jeu.getChildren().add(plateau);
		SmartGroup group = nouveauLego();
		SmartGroup group2 = nouveauLego();
		camera.translateZProperty().set(-1 * cote);
		camera.translateYProperty().set(-100);
		camera.setRotationAxis(Rotate.X_AXIS);
		camera.setRotate(-30);
		SubScene subScene3D = new SubScene(plateau_jeu, WIDTH, HEIGHT,true,null);
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
		plateau.translateXProperty().set(WIDTH/2);
		plateau.translateYProperty().set((HEIGHT/1.5) + 30);
		plateau.translateZProperty().set(-800);
		pile1.add(plateau_jeu.getChildren().get(1));
		pile2.add(plateau_jeu.getChildren().get(2));
		for (int i=0;i<plateau_jeu.getChildren().size();i++) {
			initMouseControl(plateau_jeu.getChildren().get(i), subScene3D, stage);
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