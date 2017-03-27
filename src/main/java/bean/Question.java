package bean;

import java.util.ArrayList;
import java.util.Date;

public class Question {

	private String username,question,question_url,description;
	private Date date = new Date();
	private ArrayList<String> tags;
	private long featured_points;
	private ArrayList<String> upvotes,downvotes;
	private long upvotecount,downvotecount;
	private ArrayList<Answer> answers = new ArrayList<Answer>();
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
	public String getQuestion_url() {
		return question_url;
	}
	public void setQuestion_url(String question_url) {
		this.question_url = question_url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public long getFeatured_points() {
		return featured_points;
	}
	public void setFeatured_points(long featured_points) {
		this.featured_points = featured_points;
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
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	@Override
	public String toString() {
		return "Question [username=" + username + ", question=" + question + ", question_url=" + question_url
				+ ", description=" + description + ", date=" + date + ", tags=" + tags + ", featured_points="
				+ featured_points + ", upvotes=" + upvotes + ", downvotes=" + downvotes + ", upvotecount=" + upvotecount
				+ ", downvotecount=" + downvotecount + ", answers=" + answers + "]";
	}
	public Question(String username, String question, String question_url, String description, Date date,
			ArrayList<String> tags, long featured_points, ArrayList<String> upvotes, ArrayList<String> downvotes,
			long upvotecount, long downvotecount, ArrayList<Answer> answers) {
		super();
		this.username = username;
		this.question = question;
		this.question_url = question_url;
		this.description = description;
		this.date = date;
		this.tags = tags;
		this.featured_points = featured_points;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
		this.upvotecount = upvotecount;
		this.downvotecount = downvotecount;
		this.answers = answers;
	}
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}	

}
