package bean;

import java.util.ArrayList;

import org.bson.Document;

public class MultiUse {
private String username,message,emailid,session_id;
private Document region;
public Document getRegion() {
	return region;
}
private Boolean loggedin;

public MultiUse(Boolean loggedin){
  this.loggedin=loggedin;
}

public Boolean getLoggedin() {
	return loggedin;
}

public void setLoggedin(Boolean loggedin) {
	this.loggedin = loggedin;
}

public void setRegion(Document region) {
	this.region = region;
}

public String getSession_id() {
	return session_id;
}

public void setSession_id(String session_id) {
	this.session_id = session_id;
}

private boolean is_valid;
public boolean isIs_valid() {
	return is_valid;
}

public void setIs_valid(boolean is_valid) {
	this.is_valid = is_valid;
}

private ArrayList<String> upvotes,downvotes,departments;

public ArrayList<String> getDepartments() {
	return departments;
}

public void setDepartments(ArrayList<String> departments) {
	this.departments = departments;
}

public ArrayList<String> getUpvotes() {
	return upvotes;
}

public void setUpvotes(ArrayList<String> upvotes) {
	this.upvotes = upvotes;
}

public ArrayList<String> getDownvotes() {
	return downvotes;
}

public void setDownvotes(ArrayList<String> downvotes) {
	this.downvotes = downvotes;
}

public String getEmailid() {
	return emailid;
}

public MultiUse(String username, String message, String emailid) {
	super();
	this.username = username;
	this.message = message;
	this.emailid = emailid;
}

public void setEmailid(String emailid) {
	this.emailid = emailid;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public MultiUse() {
	super();
	// TODO Auto-generated constructor stub
}

public MultiUse(String username) {
	super();
	this.username = username;
}

@Override
public String toString() {
	return "MultiUse [username=" + username + ", message=" + message + ", emailid=" + emailid + ", upvotes=" + upvotes
			+ ", downvotes=" + downvotes + "]";
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}
}
