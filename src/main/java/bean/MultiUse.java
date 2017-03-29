package bean;

import java.util.ArrayList;

public class MultiUse {
private String username,message,emailid;
private ArrayList<String> upvotes,downvotes;
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
