package bean;

import java.util.ArrayList;
import java.util.Date;

public class Project{
	private String username,title,description,readme,license,project_url,_private;
    //private long downvotes, viewcount,upvotes;
   	private ArrayList<String> images,video_url,zip_file,contributors,tags,downvotes,viewby,project_link;
	private ArrayList<Comment> comments;
    private Date date = new Date();
    private Long upvotecount,downvotecount;
   ArrayList<String> upvotes;
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
public String getReadme() {
	return readme;
}
public void setReadme(String readme) {
	this.readme = readme;
}
public String getLicense() {
	return license;
}
public void setLicense(String license) {
	this.license = license;
}
public String getProject_url() {
	return project_url;
}
public void setProject_url(String project_url) {
	this.project_url = project_url;
}
public String get_private() {
	return _private;
}
public void set_private(String _private) {
	this._private = _private;
}
public ArrayList<String> getImages() {
	return images;
}
public void setImages(ArrayList<String> images) {
	this.images = images;
}
public ArrayList<String> getVideo_url() {
	return video_url;
}
public void setVideo_url(ArrayList<String> video_url) {
	this.video_url = video_url;
}
public ArrayList<String> getZip_file() {
	return zip_file;
}
public void setZip_file(ArrayList<String> zip_file) {
	this.zip_file = zip_file;
}
public ArrayList<String> getContributors() {
	return contributors;
}
public void setContributors(ArrayList<String> contributors) {
	this.contributors = contributors;
}
public ArrayList<String> getTags() {
	return tags;
}
public void setTags(ArrayList<String> tags) {
	this.tags = tags;
}
public ArrayList<String> getDownvotes() {
	return downvotes;
}
public void setDownvotes(ArrayList<String> downvotes) {
	this.downvotes = downvotes;
}
public ArrayList<String> getViewby() {
	return viewby;
}
public void setViewby(ArrayList<String> viewby) {
	this.viewby = viewby;
}
public ArrayList<String> getProject_link() {
	return project_link;
}
public void setProject_link(ArrayList<String> project_link) {
	this.project_link = project_link;
}
public ArrayList<Comment> getComments() {
	return comments;
}
public void setComments(ArrayList<Comment> comments) {
	this.comments = comments;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Long getUpvotecount() {
	return upvotecount;
}
public void setUpvotecount(Long upvotecount) {
	this.upvotecount = upvotecount;
}
public Long getDownvotecount() {
	return downvotecount;
}
public void setDownvotecount(Long downvotecount) {
	this.downvotecount = downvotecount;
}
public ArrayList<String> getUpvotes() {
	return upvotes;
}
public void setUpvotes(ArrayList<String> upvotes) {
	this.upvotes = upvotes;
}
@Override
public String toString() {
	return "Project [username=" + username + ", title=" + title + ", description=" + description + ", readme=" + readme
			+ ", license=" + license + ", project_url=" + project_url + ", _private=" + _private + ", images=" + images
			+ ", video_url=" + video_url + ", zip_file=" + zip_file + ", contributors=" + contributors + ", tags="
			+ tags + ", downvotes=" + downvotes + ", viewby=" + viewby + ", project_link=" + project_link
			+ ", comments=" + comments + ", date=" + date + ", upvotecount=" + upvotecount + ", downvotecount="
			+ downvotecount + ", upvotes=" + upvotes + "]";
}
public Project(String username, String title, String description, String readme, String license, String project_url,
		String _private, ArrayList<String> images, ArrayList<String> video_url, ArrayList<String> zip_file,
		ArrayList<String> contributors, ArrayList<String> tags, ArrayList<String> downvotes, ArrayList<String> viewby,
		ArrayList<String> project_link, ArrayList<Comment> comments, Date date, Long upvotecount, Long downvotecount,
		ArrayList<String> upvotes) {
	super();
	this.username = username;
	this.title = title;
	this.description = description;
	this.readme = readme;
	this.license = license;
	this.project_url = project_url;
	this._private = _private;
	this.images = images;
	this.video_url = video_url;
	this.zip_file = zip_file;
	this.contributors = contributors;
	this.tags = tags;
	this.downvotes = downvotes;
	this.viewby = viewby;
	this.project_link = project_link;
	this.comments = comments;
	this.date = date;
	this.upvotecount = upvotecount;
	this.downvotecount = downvotecount;
	this.upvotes = upvotes;
}
public Project() {
	super();
	// TODO Auto-generated constructor stub
}
   
}
