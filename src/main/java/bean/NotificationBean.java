package bean;

import java.util.Date;

public class NotificationBean {
private String message,url,generator,messagebygenerator,seen;
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

public String getGenerator() {
	return generator;
}
public void setGenerator(String generator) {
	this.generator = generator;
}

public String getMessagebygenerator() {
	return messagebygenerator;
}
public void setMessagebygenerator(String messagebygenerator) {
	this.messagebygenerator = messagebygenerator;
}
public String getSeen() {
	return seen;
}
public void setSeen(String seen) {
	this.seen = seen;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
private Date date;
}
