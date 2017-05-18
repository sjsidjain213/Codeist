package bean;

import java.util.ArrayList;

public class Super {
	private Boolean loggedin;
public ArrayList<Tile> getData() {
		return data;
	}

	public void setData(ArrayList<Tile> data) {
		this.data = data;
	}

private ArrayList<Tile> data;

private ArrayList<Project> projects;

	public ArrayList<Project> getProjects() {
	return projects;
}

public void setProjects(ArrayList<Project> projects) {
	this.projects = projects;
}



	public Super() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Super(Boolean loggedin) {
		super();
		this.loggedin = loggedin;
	}

	@Override
	public String toString() {
		return "Super [logged=" + loggedin + "]";
	}

	public Boolean getLoggedin() {
		return loggedin;
	}

	public void setLoggedin(Boolean loggedin) {
		this.loggedin = loggedin;
	}

	 
}
