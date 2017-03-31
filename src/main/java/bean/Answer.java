package bean;

import java.util.ArrayList;
import java.util.Date;

public class Answer {
private String username,answer,question;
private long upvotecount,downvotecount;
public long getUpvotecount() {
	return upvotecount;
}
public void setUpvotecount(long upvotecount) {
	this.upvotecount = upvotecount;
}
public long getDownvotecount() {
	return downvotecount;
}
public void setDownvotecount(long downvotecount) {
	this.downvotecount = downvotecount;
}
private long featured_points;
ArrayList<String> upvotes,downvotes;
private long date,last_updated;



public long getLast_updated() {
	return last_updated;
}
public void setLast_updated(long last_updated) {
	this.last_updated = last_updated;
}
public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getAnswer() {
	return answer;
}
public void setAnswer(String answer) {
	this.answer = answer;
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
public long getFeatured_points() {
	return featured_points;
}
public void setFeatured_points(long featured_points) {
	this.featured_points = featured_points;
}
public long getDate() {
	return date;
}
public void setDate(long date) {
	this.date = date;
}
@Override
public String toString() {
	return "Answer [username=" + username + ", answer=" + answer + ", upvotes=" + upvotes + ", downvotes=" + downvotes
			+ ", featured_points=" + featured_points + ", date=" + date + "]";
}
public Answer() {
	super();
	// TODO Auto-generated constructor stub
}
public Answer(String username, String answer, ArrayList<String> upvotes, ArrayList<String> downvotes, long featured_points, long date) {
	super();
	this.username = username;
	this.answer = answer;
	this.upvotes = upvotes;
	this.downvotes = downvotes;
	this.featured_points = featured_points;
	this.date = date;
}

}
