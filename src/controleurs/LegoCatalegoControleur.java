package controleurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.MyListener;
import modeles.Lego;

public class LegoCatalegoControleur {
	@FXML
    private ImageView image;

    @FXML
    private Label labelLeg;
    
    @FXML
    private void click(MouseEvent mouseEvent) {
    	 this.myListener.onClickListener(lego);
    }
    public Lego lego;
    public MyListener myListener;
    
    public void setData(Lego leg,MyListener myListener) {
    	this.lego=leg;
    	this.myListener = myListener;
    	
    	this.labelLeg.setText(lego.getNom());
    	Image imag = new Image(getClass().getResourceAsStream(lego.getImageLego()));
    	this.image.setImage(imag);
    }
}
