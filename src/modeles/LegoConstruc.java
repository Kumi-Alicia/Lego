package modeles;

import java.io.Serializable;

public class LegoConstruc implements Serializable {
	private static final long serialVersionUID = -3832993424892092913L;
	public String type;
	public int taille;
	public int x,y,z;
	
	public LegoConstruc() {
		
	}
	public LegoConstruc(String type, int taille, int x, int y, int z) {
		this.type=type;
		this.taille=taille;
		this.x=x;
		this.y=y;
		this.z=z;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
