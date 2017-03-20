package bean;

import java.util.ArrayList;

public class Tile{
private String username,title,description;
private ArrayList<String> source = new ArrayList<String>();


public ArrayList<String> getSource() {
	return source;
}

public void setSource(ArrayList<String> source) {
	this.source = source;
}

private int upvotes,downvotes,viewcount,positioncount;
private Object _id;
public Object get_id() {
	return _id;
}

public void set_id(Object _id) {
	this._id = _id;
}

public int getPositioncount() {
	return positioncount;
}

public void setPositioncount(int positioncount) {
	this.positioncount = positioncount;
}

private ArrayList<String> tags= new ArrayList<String>();

public Tile() {
	super();
	// TODO Auto-generated constructor stub
}

public Tile(String username, String title, String description, int upvotes, int downvotes, int viewcount,
		ArrayList<String> tags) {
	super();
	this.username = username;
	this.title = title;
	this.description = description;
	this.upvotes = upvotes;
	this.downvotes = downvotes;
	this.viewcount = viewcount;
	this.tags = tags;
}

@Override
public String toString() {
	return "Tile [username=" + username + ", title=" + title + ", description=" + description + ", upvotes=" + upvotes
			+ ", downvotes=" + downvotes + ", viewcount=" + viewcount + ", tags=" + tags + "]";
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getUpvotes() {
	return upvotes;
}

public void setUpvotes(int upvotes) {
	this.upvotes = upvotes;
}

public int getDownvotes() {
	return downvotes;
}

public void setDownvotes(int downvotes) {
	this.downvotes = downvotes;
}

public int getViewcount() {
	return viewcount;
}

public void setViewcount(int viewcount) {
	this.viewcount = viewcount;
}

public ArrayList<String> getTags() {
	return tags;
}

public void setTags(ArrayList<String> tags) {
	this.tags = tags;
}

}
