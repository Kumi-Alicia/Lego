package controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.MyListener;
import modeles.LegoCat;

public class LegoCatalegoControleur {
	@FXML
    private ImageView image;

    @FXML
    private Label labelLeg;
    
    @FXML
    private void click(MouseEvent mouseEvent) {
    	 this.myListener.onClickListener(lego);
    }
    public LegoCat lego;
    public MyListener myListener;
    
    public void setData(LegoCat leg,MyListener myListener) {
    	this.lego=leg;
    	this.myListener = myListener;
    	
    	this.labelLeg.setText(lego.nom);
    	Image imag = new Image(getClass().getResourceAsStream(lego.imageLego));
    	this.image.setImage(imag);
    }
}
