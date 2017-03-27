package bean;

import java.util.ArrayList;
import java.util.Date;

public class Tile{	
//url for project/question url use of url is mandatory
//title for project/question title
//description for project/question description 	
private String username,title,description,url;
private Date date;
private ArrayList<String> source,upvote,downvote;
// upvotecount and downvotecount can be used directly used for sorting on basis of upvote and downvote
private long upvotecount,downvotecount,viewcount;
private ArrayList<String> tags= new ArrayList<String>();

//match count can also be used with position count
private int positioncount;
private Object _id;
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
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public ArrayList<String> getSource() {
	return source;
}
public void setSource(ArrayList<String> source) {
	this.source = source;
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
public long getUpvotecount() {
	return upvotecount;
}
public void setUpvotecount(long upvotecount) {
	this.upvotecount = upvotecount;
}
public long getDownvotecount() {
	return downvotecount;
}
public void setDownvotecount(long downvotecount) {
	this.downvotecount = downvotecount;
}
public long getViewcount() {
	return viewcount;
}
public void setViewcount(long viewcount) {
	this.viewcount = viewcount;
}
public ArrayList<String> getTags() {
	return tags;
}
public void setTags(ArrayList<String> tags) {
	this.tags = tags;
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
@Override
public String toString() {
	return "Tile [username=" + username + ", title=" + title + ", description=" + description + ", url=" + url
			+ ", date=" + date + ", source=" + source + ", upvote=" + upvote + ", downvote=" + downvote
			+ ", upvotecount=" + upvotecount + ", downvotecount=" + downvotecount + ", viewcount=" + viewcount
			+ ", tags=" + tags + ", positioncount=" + positioncount + ", _id=" + _id + "]";
}
public Tile(String username, String title, String description, String url, Date date, ArrayList<String> source,
		ArrayList<String> upvote, ArrayList<String> downvote, long upvotecount, long downvotecount, long viewcount,
		ArrayList<String> tags, int positioncount, Object _id) {
	super();
	this.username = username;
	this.title = title;
	this.description = description;
	this.url = url;
	this.date = date;
	this.source = source;
	this.upvote = upvote;
	this.downvote = downvote;
	this.upvotecount = upvotecount;
	this.downvotecount = downvotecount;
	this.viewcount = viewcount;
	this.tags = tags;
	this.positioncount = positioncount;
	this._id = _id;
}
public Tile() {
	super();
	// TODO Auto-generated constructor stub
}
}
