package controleurs;
import modeles.Carre;
import modeles.CatalegoPiece;
import modeles.Lego;
import modeles.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MyListener;

public class ControleurCatalegoPiece implements Initializable{
	@FXML
	private VBox carteLegoGauche;

	@FXML
	private GridPane grid;

	@FXML
	private ImageView legoImage;

	@FXML
	private Label nomLegoLabel;

	@FXML
	private ScrollPane scroll;

	@FXML
	private TextField recherche;

	@FXML
	private ComboBox tri;

	public ArrayList<Lego> legoliste = new ArrayList<Lego>();
	public CatalegoPiece legolist;
	public MyListener myListener;
	public Stage stage;
	public Scene scene;
	public Parent root;

	public ControleurCatalegoPiece() {
		this.legolist=new CatalegoPiece();
		this.legolist.dicoInitial();
	}


	@FXML
	void selectiontri(ActionEvent event) {
		String s = tri.getSelectionModel().getSelectedItem().toString();
		String [] couleurs= {"bleu","jaune","gris","rouge","vert"};
		for(int i=0;i<this.legolist.tailleDicoInit;i++) {
			this.grid.getChildren().clear();
		}
		if(s=="Couleur") {
			if(this.legolist.size()!=0) {
				int column = 0;
				int row = 1;
				try {
					for(int j=0 ; j<couleurs.length;j++) {
						for(String nom : this.legolist.keySet()) {
							if(nom.contains(couleurs[j])) {
								FXMLLoader fxmlLoader = new FXMLLoader();
								fxmlLoader.setLocation(getClass().getResource("/vues/LegoCatalego.fxml"));
								AnchorPane anchorPane = fxmlLoader.load();
								LegoCatalegoControleur LegoCatControleur = fxmlLoader.getController();
								LegoCatControleur.setData(this.legolist.get(nom),this.myListener);
								if(column == 3) {
									column = 0;
									row++;
								}
								this.grid.add(anchorPane, column++, row);
								this.grid.setMinWidth(Region.USE_COMPUTED_SIZE);
								this.grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
								this.grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
								this.grid.setMinHeight(Region.USE_COMPUTED_SIZE);
								this.grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
								this.grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
								GridPane.setMargin(anchorPane,new Insets(10));
							}
						}
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			if(this.legolist.size()!=0) {
				int column = 0;
				int row = 1;
				try {
					for(String nom : this.legolist.keySet()) {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/vues/LegoCatalego.fxml"));
						AnchorPane anchorPane = fxmlLoader.load();
						LegoCatalegoControleur LegoCatControleur = fxmlLoader.getController();
						LegoCatControleur.setData(this.legolist.get(nom),this.myListener);
						if(column == 3) {
							column = 0;
							row++;
						}
						this.grid.add(anchorPane, column++, row);
						this.grid.setMinWidth(Region.USE_COMPUTED_SIZE);
						this.grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
						this.grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
						this.grid.setMinHeight(Region.USE_COMPUTED_SIZE);
						this.grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
						this.grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
						GridPane.setMargin(anchorPane,new Insets(10));
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	@FXML
	void rechercheLego(KeyEvent event) throws IOException {
		String laRecherche=this.recherche.getText();
		if(laRecherche=="") {
			this.legolist.dicoInitial();
		}
		else {
			this.legolist.dicoRecherche(laRecherche);
		}
		for(int i=0;i<this.legolist.tailleDicoInit;i++) {
			this.grid.getChildren().clear();
		}

		if(this.legolist.size()!=0) {
			setChoixLego(this.legolist.get(this.legolist.firstKey()));
			this.myListener = new MyListener() {
				@Override
				public void onClickListener(Lego lego) {
					setChoixLego(lego);
				}
			};
			int column = 0;
			int row = 1;
			try {
				for(String nom : this.legolist.keySet()) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/vues/LegoCatalego.fxml"));
					AnchorPane anchorPane = fxmlLoader.load();
					LegoCatalegoControleur LegoCatControleur = fxmlLoader.getController();
					LegoCatControleur.setData(this.legolist.get(nom),this.myListener);
					if(column == 3) {
						column = 0;
						row++;
					}
					this.grid.add(anchorPane, column++, row);
					this.grid.setMinWidth(Region.USE_COMPUTED_SIZE);
					this.grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
					this.grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

					this.grid.setMinHeight(Region.USE_COMPUTED_SIZE);
					this.grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
					this.grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
					GridPane.setMargin(anchorPane,new Insets(10));
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void setChoixLego(Lego lego) {
		this.nomLegoLabel.setText(lego.nom);
		Image image = new Image(getClass().getResourceAsStream(lego.imageLego));
		this.carteLegoGauche.setStyle("-fx-background-color: #"+lego.couleur+";\r\n"
				+ "    -fx-background-radius: 30;");
		this.legoImage.setImage(image);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.tri.getItems().addAll("Alphabetique","Couleur");
		this.tri.setValue("Alphabetique");
		setChoixLego(this.legolist.get(this.legolist.firstKey()));
		this.myListener = new MyListener() {
			@Override
			public void onClickListener(Lego lego) {
				setChoixLego(lego);

			}
		};

		int column = 0;
		int row = 1;
		try {
			for(String nom : this.legolist.keySet()) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/vues/LegoCatalego.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();

				LegoCatalegoControleur LegoCatControleur = fxmlLoader.getController();
				LegoCatControleur.setData(this.legolist.get(nom),this.myListener);

				if(column == 3) {
					column = 0;
					row++;
				}
				this.grid.add(anchorPane, column++, row);
				this.grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				this.grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				this.grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

				this.grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				this.grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				this.grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
				GridPane.setMargin(anchorPane,new Insets(10));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void switchToCatalego(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("../vues/VueCatalego.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}