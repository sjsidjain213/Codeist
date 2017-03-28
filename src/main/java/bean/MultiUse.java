package bean;

public class MultiUse {
private String username,message;

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
	return "MultiUse [username=" + username + ", message=" + message + "]";
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}
}
