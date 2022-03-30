package modeles;

public class Lego {
	public String nom;
	public String imageLego;
	public String couleur;
	
	public Lego(String n,String img,String color) {
		this.couleur=color;
		this.imageLego=img;
		this.nom=n;
	}
	
	public void setNom(String n) {
		this.nom=n;
	}
	
	public void setImageLego(String img) {
		this.nom=img;
	}
	
	public void setCouleur(String color) {
		this.nom=color;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getImageLego() {
		return this.imageLego;
	}
	
	public String getCouleur() {
		return this.couleur;
	}
}
