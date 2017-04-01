package bean;

import java.util.Date;

public class Signup {
private String username,password,emailid;
private Date date;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
@Override
public String toString() {
	return "Signup [username=" + username + ", password=" + password + ", emailid=" + emailid + ", date=" + date + "]";
}
public Signup(String username, String password, String emailid, Date date) {
	super();
	this.username = username;
	this.password = password;
	this.emailid = emailid;
	this.date = date;
}
public Signup() {
	super();
	// TODO Auto-generated constructor stub
}
}
