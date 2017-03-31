package bean;

public class Super {
	private String logged;

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
