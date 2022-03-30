package controleurs;
import modeles.Lego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.MyListener;

public class ControleurCatalego implements Initializable{
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
	    
	    public ArrayList<Lego> legolist = new ArrayList<Lego>();
	    public MyListener myListener;
	    public ArrayList<Lego> getData() {
	    	ArrayList<Lego> legolist = new ArrayList<Lego>();
	    	Lego lego;
	    	lego = new Lego("Carre bleu","/images/carrebleu.png","A2F0F0");
	    	legolist.add(lego);
	    	lego = new Lego("Carre vert","/images/carrevert.png","A2F0A3");
	    	legolist.add(lego);
	    	lego = new Lego("Carre gris","/images/carregris.png","F6F6F6");
	    	legolist.add(lego);
	    	lego = new Lego("Carre jaune","/images/carrejaune.png","FFF978");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle bleu","/images/rectanglebleu.png","A2F0F0");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle jaune","/images/rectanglejaune.png","FFF978");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle jaune","/images/rectanglejaune.png","FFF978");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle rouge","/images/rectanglerouge.jpg","FD4040");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle vert","/images/rectanglevert.jpg","A2F0A3");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle vert","/images/rectanglevert.jpg","A2F0A3");
	    	legolist.add(lego);
	    	lego = new Lego("Rectangle vert","/images/rectanglevert.jpg","A2F0A3");
	    	legolist.add(lego);
	    	
	    	return legolist;
	    }
	    public void setChoixLego(Lego lego) {
	    	this.nomLegoLabel.setText(lego.getNom());
	    	Image image = new Image(getClass().getResourceAsStream(lego.getImageLego()));
	    	this.carteLegoGauche.setStyle("-fx-background-color: #"+lego.getCouleur()+";\r\n"
	    			+ "    -fx-background-radius: 30;");
	    	this.legoImage.setImage(image);
	    }
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			this.legolist.addAll(getData());
			if(this.legolist.size()>	0) {
				setChoixLego(this.legolist.get(0));
				this.myListener = new MyListener() {

					@Override
					public void onClickListener(Lego lego) {
						setChoixLego(lego);
						
					}
					
				};
			}
			int column = 0;
			int row = 1;
			try {
			for(int i=0;i<this.legolist.size();i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/vues/LegoCatalego.fxml"));
				AnchorPane anchorPane = fxmlLoader.load();
				
				LegoCatalegoControleur LegoCatControleur = fxmlLoader.getController();
				LegoCatControleur.setData(this.legolist.get(i),this.myListener);
				
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
					
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
			
}
