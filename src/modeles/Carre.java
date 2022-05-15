package modeles;

import javafx.scene.paint.Color;

public class Carre extends Lego {
	public Carre(String n,String img, String cCat,int t, Color cLeg) {
		this.couleurLeg=cLeg;
		this.couleurCat=cCat;
		this.nom=n;
		this.taille=t;
		this.imageLego=img;
	}
	public Carre(String n,String img, String cCat,int t, String texture) {
		this.texture=texture;
		this.couleurCat=cCat;
		this.nom=n;
		this.taille=t;
		this.imageLego=img;
	}
}
