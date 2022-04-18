package modeles;

import java.util.TreeMap;

public class CatalegoPiece extends TreeMap<String,Lego>{
	public int tailleDicoInit=0;
	public void dicoInitial() {
		super.clear();
		super.put("carre bleu", new Carre("Carre bleu","/images/carrebleu.png","A2F0F0"));
		super.put("carre vert", new Carre("Carre vert","/images/carrevert.png","A2F0A3"));
		super.put("carre gris", new Carre("Carre gris","/images/carregris.png","F6F6F6"));
		super.put("carre jaune", new Carre("Carre jaune","/images/carrejaune.png","FFF978"));
		super.put("rectangle bleu", new Rectangle("Rectangle bleu","/images/rectanglebleu.png","A2F0F0"));
		super.put("rectangle jaune", new Rectangle("Rectangle jaune","/images/rectanglejaune.png","FFF978"));
		super.put("rectangle rouge", new Rectangle("Rectangle rouge","/images/rectanglerouge.jpg","FD4040"));
		super.put("rectangle vert", new Rectangle("Rectangle vert","/images/rectanglevert.jpg","A2F0A3"));
		this.tailleDicoInit=super.size();
	}
	
	public void dicoRecherche(String mot) {
		dicoInitial();
		TreeMap<String,Lego> nouveauDico = new TreeMap<String,Lego>();
		for(String nom : super.keySet()) {
			if(nom.contains(mot.toLowerCase())) {
				nouveauDico.put(nom, super.get(nom));
			}
		}
		super.clear();
		super.putAll(nouveauDico);
	}
}
