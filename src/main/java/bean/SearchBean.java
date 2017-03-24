package bean;

import java.util.ArrayList;

public class SearchBean {
private String source,name,username,title,description,bio,priority,url;
private long matchedcount;

public long getMatchedcount() {
	return matchedcount;
}

public void setMatchedcount(long matchedcount) {
	this.matchedcount = matchedcount;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public String getPriority() {
	return priority;
}

public boolean setPriority(String priority) {
	this.priority = priority;
return (priority.equals("high"))?true:false;
}

private ArrayList<String> tags;
public ArrayList<String> getTags() {
	return tags;
}

public void setTags(ArrayList<String> tags) {
	this.tags = tags;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getBio() {
	return bio;
}

public void setBio(String bio) {
	this.bio = bio;
}

public String getSource() {
	return source;
}

public void setSource(String source) {
	this.source = source;
}
}
