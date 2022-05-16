package modeles;

import java.io.Serializable;

public class LegoConstruc implements Serializable {
	private static final long serialVersionUID = -3832993424892092913L;
	public String type;
	public int taille;
	public double x,y,z;
	
	public LegoConstruc() {
		
	}
	public LegoConstruc(String type, int taille, double x, double y, double z) {
		this.type=type;
		this.taille=taille;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public String toString() {
		return this.type +" "+ this.taille + " " + this.x + " " + this.y +" " + this.z;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
