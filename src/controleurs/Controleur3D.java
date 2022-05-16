package controleurs;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Observable;

import modeles.ChoisirSauvegarde;
import modeles.DemandeSauvegarde;
import modeles.LegoConstruc;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.GestureEvent;
import javafx.scene.input.KeyEvent;
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

@SuppressWarnings("deprecation")
public class Controleur3D extends Observable {
	public static Stage stage;
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

	public static double anchorX;
	public int cote = 900;
	public double valeur_rotate = 90;
	public static SmartGroup plateau_jeu = new SmartGroup();
	Camera camera = new PerspectiveCamera();
	static public LinkedList<LinkedList<LegoConstruc>> constructions = new LinkedList<LinkedList<LegoConstruc>>();
	static public LinkedList<LinkedList<Node>> plateau_jeu_liste=new LinkedList<LinkedList<Node>>();
	public static final DoubleProperty angleY = new SimpleDoubleProperty(0);
	public static double anchorAngleY;
	public static String selecNom="rectangle bleu 1";
	public static Color selecCouleur=Color.ROYALBLUE;
	public static int selecTaille=1;
	public static String selecTexture=null;
	
	public Controleur3D() {
		
	}

	public Box prepareBox(Color couleur, String texture, int taille, String nom) {
		PhongMaterial material = new PhongMaterial();
		if(texture!=null) {
			material.setDiffuseMap(new Image(getClass().getResourceAsStream(texture)));
		}
		else {
			material.setDiffuseColor(couleur);
		}
		if (nom.toLowerCase().contains("carre")) {
			Box box = new Box(50*taille,20,50*taille);
			box.setMaterial(material);
			return box;
		}
		else {
			if (valeur_rotate == 0) {
				Box box = new Box(50+50*taille,20,50);
				box.setMaterial(material);
				return box;
			}
			else {
				Box box = new Box(50,20,50+50*taille);
				box.setMaterial(material);
				return box;
			}
		}
		

	}
	public Cylinder prepareCylinder(Color couleur, String texture) {
		PhongMaterial material = new PhongMaterial();
		if(texture!=null) {
			material.setDiffuseMap(new Image(getClass().getResourceAsStream(texture)));
		}
		else {
			material.setDiffuseColor(couleur);
		}
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
				if (event1.isShiftDown()) {
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
								LegoConstruc nvlle_piece = null;
								if (selecTexture == null) {
									nvlle_piece = new LegoConstruc(selecNom, selecTaille, nveauGroup.getTranslateX(), nveauGroup.getTranslateY(), nveauGroup.getTranslateZ(), selecCouleur.toString(), null);
								}
								else {
									nvlle_piece = new LegoConstruc(selecNom, selecTaille, nveauGroup.getTranslateX(), nveauGroup.getTranslateY(), nveauGroup.getTranslateZ(), null, selecTexture);
								}	
								constructions.get(j).add(nvlle_piece);
								initMouseControl(nveauGroup, subScene3D, stage);
								break;
							}
						}
					}
				}
				if (event1.isControlDown()) {
					for (int j=0; j<plateau_jeu_liste.size(); j++) {
						if ((plateau_jeu_liste.get(j)).get(0).getTranslateX() == X  && (plateau_jeu_liste.get(j)).get(0).getTranslateZ() == Z ) {
							plateau_jeu_liste.get(j).pop();
							constructions.get(j).pop();
							if (plateau_jeu_liste.get(j).isEmpty()) {
								plateau_jeu_liste.remove(j);
								constructions.remove(j);
							}
							plateau_jeu.getChildren().remove(lego);
							
							break;
						}
					}
				}


			});
		}
		plateau_jeu.getChildren().get(0).setOnMouseClicked(event2 ->{
			double X = 700;
			double Z = -800;
			if (event2.isAltDown()) {
				if (selecNom.toLowerCase().contains("carre") && selecTaille == 2) {
					if (event2.getX()<25 && event2.getX()>-25) {
						X = 700;
					}
					if (event2.getX()<=-25 && event2.getX()>=-425) {
						X = 700 + (int)((event2.getX() - 25)/25)/2 * 50;
					}
					if (event2.getX()<-425) {
						X = 700 + (int)((-425)/25)/2*50;
					}
					
					if (event2.getX()>=25 && event2.getX()<=375) {
						X = 700 + (int)((event2.getX() + 25)/25)/2 * 50;
					}
					if (event2.getX()>375) {
						X = 700 + (int)((375)/25)/2*50;
					}
					
					if (event2.getZ()<25 && event2.getZ()>-25) {
						Z = -800;
					}
					if (event2.getZ()>=25) {
						Z = -800 + (int) (event2.getZ()/25)/2 * 50;
					}
					if (event2.getZ()<=-25 && event2.getZ()>=-400) {
						Z = -800 + (int) (event2.getZ()/25)/2 * 50;
					}
					if (event2.getZ()<-400 ) {
						Z = -800 + (int) (-375/25)/2 * 50;
					}
					
					
				}
				else {
					if (valeur_rotate == 0) {
						if (event2.getX()<25 && event2.getX()>-25) {
							X = 700;
						}
						if (event2.getX()<=-25 && event2.getX()>-425 + selecTaille * 50 - 50) {
							X = 700 + (int)((event2.getX() - 25)/25)/2 * 50;
						}
						if (event2.getX()<=-425 + selecTaille * 50 - 50 && event2.getX()>-451) {
							X = 700 + (-425/25)/2 * 50 + selecTaille * 25 - 25;
						}
						if (event2.getX()>=25 && event2.getX()<425 - selecTaille * 50 + 50) {
							X = 700 + (int)((event2.getX() + 25)/25)/2 * 50;
						}
						if (event2.getX()>=425 - selecTaille* 50 + 50 && event2.getX()<451) {
							X = 700 + (425/25)/2 * 50 - selecTaille * 25 + 25;
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
					}
					else {
						if (event2.getX()<25 && event2.getX()>-25) {
							X = 700;
						}
						if (event2.getX()>=25) {
							X = 700 + (int) (event2.getX()/25)/2 * 50;
						}
						if (event2.getX()<=-25) {
							X = 650 + (int) (event2.getX()/25)/2 * 50;
						}
						if (event2.getZ()<25 && event2.getZ()>-25) {
							Z = -800;
						}
						if (event2.getZ()<=-25 && event2.getZ()>-375 + selecTaille * 50 - 50) {
							Z = -800 + (int)((event2.getZ() - 25)/25)/2 * 50;
						}
						if (event2.getZ()<=-375 + selecTaille * 50 - 50 && event2.getZ()>=-426) {
							Z = -800 + (int)((-375)/25)/2 * 50 + selecTaille * 25 - 25;
						}
						if (event2.getZ()>=25 && event2.getZ()<375 - selecTaille * 50 + 50) {
							Z = -800 + (int)((event2.getZ() + 25)/25)/2 * 50;
						}
						if (event2.getZ()>=375 - selecTaille * 50 + 50 && event2.getZ()<=426) {
							Z = -800 + (int)((400)/25)/2 * 50 - selecTaille * 25 + 25;
						}
					}
				}
				
				SmartGroup lego_nn_pose = nouveauLego();
				lego_nn_pose.setRotationAxis(Rotate.Y_AXIS);
				lego_nn_pose.setRotate((plateau_jeu.getChildren().get(0).getRotate()));
				LinkedList<Node> pile_vierge = new LinkedList();
				plateau_jeu_liste.add(pile_vierge);
				pile_vierge.add(lego_nn_pose);
				lego_nn_pose.translateYProperty().set(plateau_jeu.getChildren().get(0).getTranslateY() - 30);
				if (selecNom.toLowerCase().contains("carre") && selecTaille == 2) {
					lego_nn_pose.translateXProperty().set(X);
					lego_nn_pose.translateZProperty().set(Z + plateau_jeu.getChildren().get(0).getTranslateZ() + 800);
				}
				if (valeur_rotate == 0) {
					lego_nn_pose.translateXProperty().set(X);
					lego_nn_pose.translateZProperty().set(Z + plateau_jeu.getChildren().get(0).getTranslateZ() + 800);
				}
				else {
					lego_nn_pose.translateXProperty().set(X + 25);
					lego_nn_pose.translateZProperty().set(Z + plateau_jeu.getChildren().get(0).getTranslateZ() + 775);
				}
				LinkedList<LegoConstruc> nvlle_constructions = new LinkedList();
				LegoConstruc nvlle_piece = null;
				if (selecTexture == null) {
					nvlle_piece = new LegoConstruc(selecNom, selecTaille, lego_nn_pose.getTranslateX(), lego_nn_pose.getTranslateY(), lego_nn_pose.getTranslateZ(), selecCouleur.toString(), null);
				}
				else {
					nvlle_piece = new LegoConstruc(selecNom, selecTaille, lego_nn_pose.getTranslateX(), lego_nn_pose.getTranslateY(), lego_nn_pose.getTranslateZ(), null, selecTexture);
				}
				constructions.add(nvlle_constructions);
				nvlle_constructions.add(nvlle_piece);
				
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
		stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case W:
				valeur_rotate = 90;
				break;
			case S:
				valeur_rotate = 0;
				break;
			}
		});
	}
	public SmartGroup nouveauLego() {
		Cylinder cylindre = prepareCylinder(selecCouleur, selecTexture);
		Cylinder cylindre2 = prepareCylinder(selecCouleur, selecTexture);
		Cylinder cylindre3 = prepareCylinder(selecCouleur, selecTexture);
		Cylinder cylindre4 = prepareCylinder(selecCouleur, selecTexture);
		Box box = prepareBox(selecCouleur, selecTexture, selecTaille, selecNom);
		SmartGroup group = new SmartGroup();
		group.getChildren().add(box);
		group.getChildren().add(cylindre);
		if (selecNom.toLowerCase().contains("carre")) {
			if (selecTaille == 1) {
				cylindre.translateXProperty().set(0);
				cylindre.translateZProperty().set(0);
			}
			if (selecTaille == 2) {
				group.getChildren().add(cylindre2);
				group.getChildren().add(cylindre3);
				group.getChildren().add(cylindre4);
				cylindre.translateXProperty().set(25);
				cylindre.translateZProperty().set(25);
				cylindre2.translateXProperty().set(25);
				cylindre2.translateZProperty().set(-25);
				cylindre3.translateXProperty().set(-25);
				cylindre3.translateZProperty().set(25);
				cylindre4.translateXProperty().set(-25);
				cylindre4.translateZProperty().set(-25);
				cylindre2.translateYProperty().set(-8);
				cylindre3.translateYProperty().set(-8);
				cylindre4.translateYProperty().set(-8);
			}
			
		}
		else {
			if (valeur_rotate == 0) {
				if (selecTaille == 1) {
					group.getChildren().add(cylindre2);
					cylindre.translateXProperty().set(25);
					cylindre2.translateXProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
				}
				if (selecTaille == 2) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					cylindre.translateXProperty().set(50);
					cylindre2.translateXProperty().set(-50);
					cylindre3.translateXProperty().set(0);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
				}
				if (selecTaille == 3) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					group.getChildren().add(cylindre4);
					cylindre.translateXProperty().set(75);
					cylindre2.translateXProperty().set(-75);
					cylindre3.translateXProperty().set(25);
					cylindre4.translateXProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
					cylindre4.translateYProperty().set(-8);
				}
				
			}
			else {
				if (selecTaille == 1) {
					group.getChildren().add(cylindre2);
					cylindre.translateZProperty().set(25);
					cylindre2.translateZProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
				}
				if (selecTaille == 2) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					cylindre.translateZProperty().set(50);
					cylindre2.translateZProperty().set(-50);
					cylindre3.translateZProperty().set(0);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
				}
				if (selecTaille == 3) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					group.getChildren().add(cylindre4);
					cylindre.translateZProperty().set(75);
					cylindre2.translateZProperty().set(-75);
					cylindre3.translateZProperty().set(25);
					cylindre4.translateZProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
					cylindre4.translateYProperty().set(-8);
				}
			}
		}
		
		plateau_jeu.getChildren().add(group);
		box.translateYProperty().set(10);
		cylindre.translateYProperty().set(-8);
		return group;
	}
	public SmartGroup nouveauLego1(String type, int taille, double X, double Y, double Z, Color couleur, String texture) {
		Cylinder cylindre = prepareCylinder(couleur, texture);
		Cylinder cylindre2 = prepareCylinder(couleur, texture);
		Cylinder cylindre3 = prepareCylinder(couleur, texture);
		Cylinder cylindre4 = prepareCylinder(couleur, texture);
		Box box = prepareBox(couleur, texture, taille, type);
		SmartGroup group = new SmartGroup();
		group.getChildren().add(box);
		group.getChildren().add(cylindre);
		if (type.toLowerCase().contains("carre")) {
			if (taille == 1) {
				cylindre.translateXProperty().set(0);
				cylindre.translateZProperty().set(0);
			}
			if (taille == 2) {
				group.getChildren().add(cylindre2);
				group.getChildren().add(cylindre3);
				group.getChildren().add(cylindre4);
				cylindre.translateXProperty().set(25);
				cylindre.translateZProperty().set(25);
				cylindre2.translateXProperty().set(25);
				cylindre2.translateZProperty().set(-25);
				cylindre3.translateXProperty().set(-25);
				cylindre3.translateZProperty().set(25);
				cylindre4.translateXProperty().set(-25);
				cylindre4.translateZProperty().set(-25);
				cylindre2.translateYProperty().set(-8);
				cylindre3.translateYProperty().set(-8);
				cylindre4.translateYProperty().set(-8);
			}
			
		}
		else {
			if (valeur_rotate == 0) {
				if (taille == 1) {
					group.getChildren().add(cylindre2);
					cylindre.translateXProperty().set(25);
					cylindre2.translateXProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
				}
				if (taille == 2) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					cylindre.translateXProperty().set(50);
					cylindre2.translateXProperty().set(-50);
					cylindre3.translateXProperty().set(0);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
				}
				if (taille == 3) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					group.getChildren().add(cylindre4);
					cylindre.translateXProperty().set(75);
					cylindre2.translateXProperty().set(-75);
					cylindre3.translateXProperty().set(25);
					cylindre4.translateXProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
					cylindre4.translateYProperty().set(-8);
				}
				
			}
			else {
				if (taille == 1) {
					group.getChildren().add(cylindre2);
					cylindre.translateZProperty().set(25);
					cylindre2.translateZProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
				}
				if (taille == 2) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					cylindre.translateZProperty().set(50);
					cylindre2.translateZProperty().set(-50);
					cylindre3.translateZProperty().set(0);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
				}
				if (taille == 3) {
					group.getChildren().add(cylindre2);
					group.getChildren().add(cylindre3);
					group.getChildren().add(cylindre4);
					cylindre.translateZProperty().set(75);
					cylindre2.translateZProperty().set(-75);
					cylindre3.translateZProperty().set(25);
					cylindre4.translateZProperty().set(-25);
					cylindre2.translateYProperty().set(-8);
					cylindre3.translateYProperty().set(-8);
					cylindre4.translateYProperty().set(-8);
				}
			}
		}
		group.setTranslateX(X);
		group.setTranslateY(Y);
		group.setTranslateZ(Z);
		plateau_jeu.getChildren().add(group);
		box.translateYProperty().set(10);
		cylindre.translateYProperty().set(-8);
		return group;
	}

	public void initialisation(ActionEvent event) throws IOException {
		PhongMaterial material1 = new PhongMaterial();
		material1.setDiffuseColor(Color.GREY);
		Box sol = new Box(cote - 1,20,cote - 50);
		sol.setMaterial(material1);
		SmartGroup plateau = new SmartGroup();
		plateau.getChildren().add(sol);
		plateau_jeu.getChildren().add(plateau);
		camera.translateZProperty().set(-1.2 * cote);
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
		plateau.translateXProperty().set(WIDTH/2);
		plateau.translateYProperty().set((HEIGHT/1.5) + 30);
		plateau.translateZProperty().set(-800);
		
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
	 void Tutoriel(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vues/VuePageTuto.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));  
		stage.showAndWait();
	 }

	@FXML
    public void sauvegarder(ActionEvent event) {
		String nom = DemandeSauvegarde.display();
		System.out.println(nom);
		try {
			FileOutputStream fos = new FileOutputStream(new File("src/save/"+nom+".xml"));
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			XMLEncoder encoder = new XMLEncoder(bos);
			encoder.writeObject(this.constructions);
			encoder.flush();
			encoder.close();
			bos.close();
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException("Impossible d'écrire les données");
		}
    }
	
	@FXML
	public void charger(ActionEvent event) throws IOException, ClassNotFoundException {
		String sauvegarde = ChoisirSauvegarde.display();
		plateau_jeu_liste = new LinkedList<LinkedList<Node>>();
		int taille = plateau_jeu.getChildren().size();
		for (int i=0;i<taille-1;i++) {
			plateau_jeu.getChildren().remove(1);
		}
		FileInputStream fis = new FileInputStream(new File("src/save/"+sauvegarde));
		BufferedInputStream bis = new BufferedInputStream(fis);
		XMLDecoder decoder = new XMLDecoder(bis);
		this.constructions = (LinkedList<LinkedList<LegoConstruc>>)decoder.readObject();
		decoder.close();
		bis.close();
		fis.close();
		for (int j=0;j<constructions.size();j++) {
			LinkedList<Node> nvlle_pile = new LinkedList<Node>();
			plateau_jeu_liste.add(nvlle_pile);
			for (int k=0; k<constructions.get(j).size();k++) {
				LegoConstruc piece_utilisation = constructions.get(j).get(k);
				SmartGroup nvlle_piece = null;
				if ( piece_utilisation.getTexture() == null) {
					System.out.println(piece_utilisation.couleur);
					nvlle_piece = nouveauLego1(piece_utilisation.getType(),piece_utilisation.getTaille(),piece_utilisation.getX(),piece_utilisation.getY(),piece_utilisation.getZ(),Color.web(piece_utilisation.getCouleur()),null);
				}
				else {
					nvlle_piece = nouveauLego1(piece_utilisation.getType(),piece_utilisation.getTaille(),piece_utilisation.getX(),piece_utilisation.getY(),piece_utilisation.getZ(),Color.ROYALBLUE,piece_utilisation.getTexture());
				}
				initMouseControl(nvlle_piece,subScene3D, stage);
				nvlle_piece.setRotationAxis(Rotate.Y_AXIS);
				nvlle_piece.setRotate(plateau_jeu.getChildren().get(0).getRotate());
				nvlle_pile.add(nvlle_piece);			
			}
		}
	}

	@FXML
	public void switchToCatalego(ActionEvent event)  throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../vues/VueCatalegoPiece.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));  
		stage.showAndWait();
		ControleurCatalegoPiece catalego = fxmlLoader.getController();
		this.selecNom=catalego.choixlegonom;
		this.selecCouleur=catalego.choixlegocouleur;
		this.selecTaille=catalego.choixlegotaille;
		this.selecTexture=catalego.choixtexture;
	}
}
