package bean;

import java.util.ArrayList;

public class Super {
	private String logged;
public ArrayList<Tile> getAlsuper() {
		return alsuper;
	}

	public void setAlsuper(ArrayList<Tile> alsuper) {
		this.alsuper = alsuper;
	}

private ArrayList<Tile> alsuper;
	public Super() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Super(String logged) {
		super();
		this.logged = logged;
	}

	@Override
	public String toString() {
		return "Super [logged=" + logged + "]";
	}

	public String getLogged() {
		return logged;
	}

	public void setLogged(String logged) {
		this.logged = logged;
	} 
}
