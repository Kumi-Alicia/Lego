package controleurs;

import java.io.IOException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ControleurPageAcceuil {
	
	public void switchToPageJeu(ActionEvent event) throws IOException{
		Controleur3D c = new Controleur3D();
		c.initialisation(event);
	}
	public void fermerApplication(ActionEvent event) throws IOException{
		System.out.println("L'application va se fermer!");
		System.exit(0);
	}
}