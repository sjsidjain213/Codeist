package bean;

import java.util.Date;

public class Signup {
private String name,password,emailid;
public Signup(String name, String password, String emailid, Date date) {
	super();
	this.name = name;
	this.password = password;
	this.emailid = emailid;
	this.date = date;
}
public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
private Date date;
public Signup() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "Signup [name=" + name + ", password=" + password + ", emailid=" + emailid + ", date=" + date + "]";
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
}
