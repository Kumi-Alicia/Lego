package controleurs;

import java.io.IOException;
import java.util.LinkedList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import modeles.SmartGroup;

public class Controleur3D {
	public Stage stage;
	public Scene scene;
	public Parent root;
	
	@FXML
	private SubScene subScene3D;
	private Button btnx;
	private Button btncatalogue;
	private Button btnsauvegarder;
	private Button btnrecharger;
	
	public static final float WIDTH = 1400;
	public static final float HEIGHT = 800;

	public double anchorX;
	public double anchorAngleY = 0;
	public int cote = 900;
	
	Camera camera = new PerspectiveCamera();
	LinkedList<Node> pile1 = new LinkedList();
	public static LinkedList<LinkedList<Node>> plateau_jeu_liste=new LinkedList<LinkedList<Node>>();
	public static SmartGroup plateau_jeu = new SmartGroup();
	public final DoubleProperty angleY = new SimpleDoubleProperty(0);
	public static String selec="test";
	public Controleur3D() {

	}
	
	public Box prepareBox() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.ROYALBLUE);
		Box box = new Box(100,20,50);
		box.setMaterial(material);
		return box;
	}
	public Cylinder prepareCylinder() {
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.ROYALBLUE);
		Cylinder cylindre = new Cylinder(15,16,10);
		cylindre.setMaterial(material);
		return cylindre;
	}


	public void initMouseControl(Node node, SubScene subScene3D, Stage stage) {
		Rotate yRotate;
		node.getTransforms().addAll(yRotate = new Rotate(0,Rotate.Y_AXIS));
		yRotate.angleProperty().bind(angleY);

		subScene3D.setOnMousePressed(event -> {
			anchorX = event.getSceneX();
			anchorAngleY = angleY.get();
		});
		for (int i=1; i<plateau_jeu.getChildren().size();i++) {
			Node lego = plateau_jeu.getChildren().get(i);
			double X = plateau_jeu.getChildren().get(i).getTranslateX();
			double Z = plateau_jeu.getChildren().get(i).getTranslateZ();
			plateau_jeu.getChildren().get(i).setOnMouseClicked(event1 ->{
				for (int j=0; j<plateau_jeu_liste.size(); j++) {
					for (int k=0; k<plateau_jeu_liste.get(j).size(); k++) {
						if ((plateau_jeu_liste.get(j)).get(0).getTranslateX() == X  && (plateau_jeu_liste.get(j)).get(0).getTranslateZ() == Z ) {
							SmartGroup nveauGroup = nouveauLego();
							plateau_jeu_liste.get(j).add(lego);
							nveauGroup.setRotationAxis(Rotate.Y_AXIS);
							nveauGroup.setRotate((plateau_jeu_liste.get(j)).get(0).getRotate());
							nveauGroup.translateXProperty().set(X);
							nveauGroup.translateYProperty().set(plateau_jeu_liste.get(j).get(plateau_jeu_liste.get(j).size()-1).getTranslateY()-20);
							nveauGroup.translateZProperty().set(Z);
							initMouseControl(nveauGroup, subScene3D, stage);
							break;
						}

					}
				}

			});
		}
		plateau_jeu.getChildren().get(0).setOnMouseClicked(event2 ->{
			double X = 0;
			double Z = 0;
			if (event2.isAltDown()) {
				if (event2.getX()<50 && event2.getX()>-50) {
					X = 700;
				}
				if (event2.getX()<=-50) {
					X = 700 + (int)((event2.getX() - 50)/50)/2 * 100;
				}
				if (event2.getX()>=50) {
					X = 700 + (int)((event2.getX() + 50)/50)/2 * 100;
				}
				if (event2.getZ()<25 && event2.getZ()>-25) {
					Z = -800;
				}
				if (event2.getZ()>=25) {
					Z = -800 + (int) (event2.getZ()/25)/2 * 50;
				}
				if (event2.getZ()<=-25) {
					Z = -800 + (int) (event2.getZ()/25)/2 * 50;
				}
				System.out.println((int) (event2.getZ()/25)/2 * 50);
				SmartGroup lego_nn_pose = nouveauLego();
				lego_nn_pose.setRotationAxis(Rotate.Y_AXIS);
				lego_nn_pose.setRotate((plateau_jeu_liste.get(0)).get(0).getRotate());
				LinkedList<Node> pile_vierge = new LinkedList();
				plateau_jeu_liste.add(pile_vierge);
				pile_vierge.add(lego_nn_pose);
				lego_nn_pose.translateXProperty().set(X);
				lego_nn_pose.translateYProperty().set(plateau_jeu.getChildren().get(0).getTranslateY() - 30);
				lego_nn_pose.translateZProperty().set(Z + plateau_jeu.getChildren().get(0).getTranslateZ() + 800);
				initMouseControl(lego_nn_pose, subScene3D, stage);

			}

		});
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
		System.out.println(this.selec);
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
	
	public void initialisation(ActionEvent event) throws IOException {
		plateau_jeu_liste.add(pile1);
		PhongMaterial material1 = new PhongMaterial();
		material1.setDiffuseColor(Color.GRAY);
		Box sol = new Box(cote - 1,20,cote - 50);
		sol.setMaterial(material1);
		SmartGroup plateau = new SmartGroup();
		plateau.getChildren().add(sol);
		plateau_jeu.getChildren().add(plateau);
		SmartGroup group = nouveauLego();
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
		plateau.translateXProperty().set(WIDTH/2);
		plateau.translateYProperty().set((HEIGHT/1.5) + 30);
		plateau.translateZProperty().set(-800);
		pile1.add(plateau_jeu.getChildren().get(1));
		for (int i=0;i<plateau_jeu.getChildren().size();i++) {
			initMouseControl(plateau_jeu.getChildren().get(i), subScene3D, stage);
		}
		stage.setTitle("Page jeu");
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToPageAcceuil(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../vues/VuePageAcceuil.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
    @FXML
    public void switchToCatalego(MouseEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vues/VueCatalegoPiece.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));  
		stage.showAndWait();
		ControleurCatalegoPiece catalego = fxmlLoader.getController();
		this.selec=catalego.choixlego;
    }
}
