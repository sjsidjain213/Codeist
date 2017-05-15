package bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Tile extends Super{	
//url for project/question url use of url is mandatory
//title for project/question title
//description for project/question description 	
private String username,question,title,description,url,subject,url_title,university_name,college_name,state,city;
private int matchedcount,status;

public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public int getMatchedcount() {
	return matchedcount;
}
public void setMatchedcount(int matchedCount) {
	this.matchedcount = matchedCount;
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

private int positioncount;
private String id;

private Date date;
private ArrayList<String> source,upvotes,downvotes;
// upvotecount and downvotecount can be used directly used for sorting on basis of upvote and downvote
private long upvotecount,downvotecount,viewcount;
private ArrayList<String> tags= new ArrayList<String>();


@Override
public String toString() {
	return "Tile [username=" + username + ", title=" + title + ", description=" + description + ", url=" + url
			+ ", subject=" + subject + ", url_title=" + url_title + ", positioncount=" + positioncount + ", id=" + id
			+ ", date=" + date + ", source=" + source + ", upvotes=" + upvotes + ", downvotes=" + downvotes
			+ ", upvotecount=" + upvotecount + ", downvotecount=" + downvotecount + ", viewcount=" + viewcount
			+ ", tags=" + tags + ", status" + status + "]";
}
public Tile(String username,String question, String title, String description, String url, String subject, String url_title,
		int positioncount, String id, Date date, ArrayList<String> source, ArrayList<String> upvotes,
		ArrayList<String> downvotes, long upvotecount, long downvotecount, long viewcount, ArrayList<String> tags, Integer status) {
	super();
	this.question = question;
	this.username = username;
	this.title = title;
	this.description = description;
	this.url = url;
	this.subject = subject;
	this.url_title = url_title;
	this.positioncount = positioncount;
	this.id = id;
	this.date = date;
	this.source = source;
	this.upvotes = upvotes;
	this.downvotes = downvotes;
	this.upvotecount = upvotecount;
	this.downvotecount = downvotecount;
	this.viewcount = viewcount;
	this.tags = tags;
	this.status = status;
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
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getUrl_title() {
	return url_title;
}
public void setUrl_title(String url_title) {
	this.url_title = url_title;
}
public int getPositioncount() {
	return positioncount;
}
public void setPositioncount(int positioncount) {
	this.positioncount = positioncount;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public ArrayList<String> getSource() {
	return source;
}
public void setSource(ArrayList<String> source) {
	this.source = source;
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
public long getViewcount() {
	return viewcount;
}
public void setViewcount(long viewcount) {
	this.viewcount = viewcount;
}
public ArrayList<String> getTags() {
	return tags;
}
public void setTags(ArrayList<String> tags) {
	this.tags = tags;
}
public static Comparator<Tile> getHomesort() {
	return homesort;
}
public static void setHomesort(Comparator<Tile> homesort) {
	Tile.homesort = homesort;
}
public Tile() {
	super();
	// TODO Auto-generated constructor stub
}

public static Comparator<Tile> homesort = new Comparator<Tile>() {

	public int compare(Tile s1,Tile s2) {

	   int upvote1 = (int)s1.getPositioncount();
	   int upvote2 = (int)s2.getPositioncount();

	   return upvote2-upvote1;
  }};
  
  public static Comparator<Tile> matchsort = new Comparator<Tile>() {

		public int compare(Tile s1,Tile s2) {

		   int match1 = (int)s1.getPositioncount();
		   int match2 = (int)s2.getPositioncount();

		   return match2-match1;
	  }};
	  
	  public static Comparator<Tile> statussort = new Comparator<Tile>() {

			public int compare(Tile s1,Tile s2) {

			   int status1 = (int)s1.getStatus();
			   int status2 = (int)s2.getStatus();

			   return status2-status1;
		  }};
}
