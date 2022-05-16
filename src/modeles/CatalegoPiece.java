package modeles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import javafx.scene.paint.Color;

public class CatalegoPiece extends TreeMap<String,LegoCat>{
	public int tailleDicoInit=0;
	public void dicoInitial() throws IOException {
		super.clear();
		File fil = new File("src/modeles/Pieces.txt");
		BufferedReader br = new BufferedReader(new FileReader(fil));
		String [] lecture;
		while((br.read())!=-1)
        {	
            lecture = br.readLine().split(",");
            if (lecture[0].contains("Carre")){
            	if (!Boolean.valueOf(lecture[3])) {
            		super.put(lecture[0],new Carre(lecture[0],lecture[1],lecture[2],Integer.valueOf(lecture[4]),Color.web(lecture[5])));
            	}
            	else {
            		super.put(lecture[0],new Carre(lecture[0],lecture[1],lecture[2],Integer.valueOf(lecture[4]),lecture[5]));
            	}
            	
            }
            else if (lecture[0].contains("Rectangle")) {
            	if (!Boolean.valueOf(lecture[3])) {
            		super.put(lecture[0],new Rectangle(lecture[0],lecture[1],lecture[2],Integer.valueOf(lecture[4]),Color.web(lecture[5])));
            	}
            	else {
            		super.put(lecture[0],new Rectangle(lecture[0],lecture[1],lecture[2],Integer.valueOf(lecture[4]),lecture[6]));
            	}
            } 
        }
		this.tailleDicoInit=super.size();
	}
	
	public void dicoRecherche(String mot) throws IOException {
		dicoInitial();
		TreeMap<String,LegoCat> nouveauDico = new TreeMap<String,LegoCat>();
		for(String nom : super.keySet()) {
			if(nom.toLowerCase().contains(mot.toLowerCase())) {
				nouveauDico.put(nom, super.get(nom));
			}
		}
		super.clear();
		super.putAll(nouveauDico);
	}
}
