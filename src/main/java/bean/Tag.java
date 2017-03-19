package bean;

import java.util.ArrayList;

public class Tag {

	private ArrayList<String> tags = new ArrayList<String>();

	@Override
	public String toString() {
		return "Tag [tags=" + tags + "]";
	}

	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tag(ArrayList<String> tags) {
		super();
		this.tags = tags;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
}
