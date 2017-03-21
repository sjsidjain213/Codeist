package bean;

import java.util.ArrayList;
import java.util.Date;

public class Question {

	private String username,question;
	private Date date = new Date();
	private ArrayList<String> tags;
	private long upvotes,downvotes,featured_points;
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Question(String username, String question, Date date, ArrayList<String> tags, long upvotes, long downvotes,
			long featured_points) {
		super();
		this.username = username;
		this.question = question;
		this.date = date;
		this.tags = tags;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
		this.featured_points = featured_points;
	}
	@Override
	public String toString() {
		return "Question [username=" + username + ", question=" + question + ", date=" + date + ", tags=" + tags
				+ ", upvotes=" + upvotes + ", downvotes=" + downvotes + ", featured_points=" + featured_points + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public long getUpvotes() {
		return upvotes;
	}
	public void setUpvotes(long upvotes) {
		this.upvotes = upvotes;
	}
	public long getDownvotes() {
		return downvotes;
	}
	public void setDownvotes(long downvotes) {
		this.downvotes = downvotes;
	}
	public long getFeatured_points() {
		return featured_points;
	}
	public void setFeatured_points(long featured_points) {
		this.featured_points = featured_points;
	}
}
