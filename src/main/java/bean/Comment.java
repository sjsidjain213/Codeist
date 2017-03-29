package bean;

import java.util.Date;

public class Comment {
private String username,comment;
private Date date;
private long dat;
public long getDat() {
	return dat;
}
public void setDat(long dat) {
	this.dat = dat;
}
public Comment() {
	super();
	// TODO Auto-generated constructor stub
}
public Comment(String username, String comment, Date date) {
	super();
	this.username = username;
	this.comment = comment;
	this.date = date;
}
@Override
public String toString() {
	return "Comment [username=" + username + ", comment=" + comment + ", date=" + date + "]";
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
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}


}
