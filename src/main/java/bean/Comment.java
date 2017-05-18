package bean;

import java.util.Date;

public class Comment {
private String username,comment,project_id;
private long date;
private Boolean loggedin;

public Comment(Boolean loggedin){
	this.loggedin=loggedin;
}
public Comment(Boolean loggedin,String comment){
	this.loggedin=loggedin;
	this.comment=comment;
}
public Boolean getLoggedin() {
	return loggedin;
}
public void setLoggedin(Boolean loggedin) {
	this.loggedin = loggedin;
}
public String getProject_id() {
	return project_id;
}
public void setProject_id(String project_id) {
	this.project_id = project_id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public long getDate() {
	return date;
}
public void setDate(long date) {
	this.date = date;
}
@Override
public String toString() {
	return "Comment [username=" + username + ", comment=" + comment + ", project_id=" + project_id + ", date=" + date
			+ "]";
}
public Comment(String username, String comment, long date) {
	super();
	this.username = username;
	this.comment = comment;
	this.date = date;
}
public Comment() {
	super();
	// TODO Auto-generated constructor stub
}

}
