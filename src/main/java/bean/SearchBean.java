package bean;

import java.util.ArrayList;
import java.util.Comparator;

import org.bson.types.ObjectId;

public class SearchBean {
private String source,name,username,title,description,bio,priority,url, ques_url;
private ObjectId id;
private long matchedcount,upvotecount, rating, downvotecount;
private ArrayList<String> tags,upvotes,downvotes;


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


public void setUpvotes(ArrayList<String> upvotes) {
	this.upvotes = upvotes;
}


public void setDownvotes(ArrayList<String> downvotes) {
	this.downvotes = downvotes;
}


@Override
public String toString() {
	return "SearchBean [source=" + source + ", name=" + name + ", username=" + username + ", title=" + title
			+ ", description=" + description + ", bio=" + bio + ", priority=" + priority + ", url=" + url
			+ ", matchedcount=" + matchedcount + ", tags=" + tags + ", upvotes=" + upvotes + ", downvotes=" + downvotes 
			+ ", rating=" + rating + ", ques_url" + "ques_url" +"]";
}


public String getQues_url() {
	return ques_url;
}
public ObjectId getId() {
	return id;
}

public void setId(ObjectId id) {
	this.id = id;
}

public void setQues_url(String ques_url) {
	this.ques_url = ques_url;
}

public long getRating() {
	return rating;
}

public void setRating(long rating) {
	this.rating = rating;
}


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

public static Comparator<SearchBean> searchsort = new Comparator<SearchBean>() {

	public int compare(SearchBean s1,SearchBean s2) {

	   int match1 = (int)s1.getMatchedcount();
	   int match2 = (int)s2.getMatchedcount();

	   if(s1.getPriority().compareTo(s2.getPriority())>0)
	   {   return -1;
	   }else if(s1.getPriority().compareTo(s2.getPriority())==0)
	   {
		   return (match2-match1);
	   }
	   else {
		   return (match2-match1);
	   }
	   /*For ascending order*/

	   /*For descending order*/
	   //rollno2-rollno1;
  }};
  
  public static Comparator<SearchBean> upvoteSort = new Comparator<SearchBean>() {

		public int compare(SearchBean s1,SearchBean s2) {

		   int upvote1 = (int)s1.getUpvotecount();
		   int upvote2 = (int)s2.getUpvotecount();

		   return upvote2-upvote1;
	  }};

}
