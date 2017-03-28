package bean;

public class MultiUse {
private String username,message,emailid;

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
	return "MultiUse [username=" + username + ", message=" + message + ", emailid=" + emailid + "]";
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}
}
