package bean;

import java.util.Date;

public class NotificationBean {
private String message,url,detail1,detail2;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getDetail1() {
	return detail1;
}
public void setDetail1(String detail1) {
	this.detail1 = detail1;
}
public String getDetail2() {
	return detail2;
}
public void setDetail2(String detail2) {
	this.detail2 = detail2;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
private Date date;
}
