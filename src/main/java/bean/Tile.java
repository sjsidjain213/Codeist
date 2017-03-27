package bean;

import java.util.ArrayList;

public class Tile{
private String username,title,description;
private ArrayList<String> source,upvote,downvote;
private long viewcount;
private int positioncount;
private Object _id;
private ArrayList<String> tags= new ArrayList<String>();
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
public ArrayList<String> getSource() {
	return source;
}
public void setSource(ArrayList<String> source) {
	this.source = source;
}public long getViewcount() {
	return viewcount;
}
public void setViewcount(long viewcount) {
	this.viewcount = viewcount;
}
public int getPositioncount() {
	return positioncount;
}
public void setPositioncount(int positioncount) {
	this.positioncount = positioncount;
}
public Object get_id() {
	return _id;
}
public void set_id(Object _id) {
	this._id = _id;
}
public ArrayList<String> getTags() {
	return tags;
}
public void setTags(ArrayList<String> tags) {
	this.tags = tags;
}
@Override
public String toString() {
	return "Tile [username=" + username + ", title=" + title + ", description=" + description + ", source=" + source
			+ ", upvote=" + upvote + ", downvote=" + downvote + ", viewcount=" + viewcount + ", positioncount="
			+ positioncount + ", _id=" + _id + ", tags=" + tags + "]";
}

public Tile(String username, String title, String description, ArrayList<String> source, ArrayList<String> upvote,
		ArrayList<String> downvote, long viewcount, int positioncount, Object _id, ArrayList<String> tags) {
	super();
	this.username = username;
	this.title = title;
	this.description = description;
	this.source = source;
	this.upvote = upvote;
	this.downvote = downvote;
	this.viewcount = viewcount;
	this.positioncount = positioncount;
	this._id = _id;
	this.tags = tags;
}
public ArrayList<String> getUpvote() {
	return upvote;
}
public void setUpvote(ArrayList<String> upvote) {
	this.upvote = upvote;
}
public ArrayList<String> getDownvote() {
	return downvote;
}
public void setDownvote(ArrayList<String> downvote) {
	this.downvote = downvote;
}
public Tile() {
	super();
	// TODO Auto-generated constructor stub
}
}
