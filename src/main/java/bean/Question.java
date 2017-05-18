package bean;

import java.util.ArrayList;
import java.util.Date;

public class Question {

	private String username,question,question_url,description,state,city,university_name,college_name,owner,title,id;
	private ArrayList<String> department;
    private Boolean loggedin;
	
	

    public Question(Boolean loggedin){
    	this.loggedin=loggedin;
    }
	public Boolean getLoggedin() {
		return loggedin;
	}
	public void setLoggedin(Boolean loggedin) {
		this.loggedin = loggedin;
	}
	public String getOwner() {
		return owner;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	public ArrayList<String> getDepartment() {
		return department;
	}
	public void setDepartment(ArrayList<String> department) {
		this.department = department;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUniversity_name() {
		return university_name;
	}
	public void setUniversity_name(String university_name) {
		this.university_name = university_name;
	}
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	private long date,last_updated;
	private String email_id;
	private ArrayList<String> tags;
	private long featured_points;
	private ArrayList<String> upvotes,downvotes;
	private long upvotecount,downvotecount;
	private ArrayList<Answer> answers = new ArrayList<Answer>();
	
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email) {
		this.email_id = email;
	}
	public long getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(long last_updated) {
		this.last_updated = last_updated;
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
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
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
	
	
	
	public Question(String username, String question, String question_url, String description, long date,
			long last_updated, ArrayList<String> tags, long featured_points, ArrayList<String> upvotes,
			ArrayList<String> downvotes, long upvotecount, long downvotecount, ArrayList<Answer> answers) {
		super();
		this.username = username;
		this.question = question;
		this.question_url = question_url;
		this.description = description;
		this.date = date;
		this.last_updated = last_updated;
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
	@Override
	public String toString() {
		return "Question [username=" + username + ", question=" + question + ", question_url=" + question_url
				+ ", description=" + description + ", state=" + state + ", city=" + city + ", university_name="
				+ university_name + ", college_name=" + college_name + ", date=" + date + ", last_updated="
				+ last_updated + ", email_id=" + email_id + ", tags=" + tags + ", featured_points=" + featured_points
				+ ", upvotes=" + upvotes + ", downvotes=" + downvotes + ", upvotecount=" + upvotecount
				+ ", downvotecount=" + downvotecount + ", answers=" + answers + "]";
	}

}
