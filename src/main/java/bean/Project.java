package bean;

import java.util.ArrayList;

public class Project extends SearchBean{
	private String username,title,description,readme,license,project_url,_private;
    	private long downvotes, viewcount;
	private long upvotes;
   	private ArrayList<String> images = new ArrayList<String>();
    	private ArrayList<String> video_url = new ArrayList<String>();
    	private ArrayList<String> zip_file = new ArrayList<String>();
    	private ArrayList<String> contributors = new ArrayList<String>();
    	private ArrayList<String> tags = new ArrayList<String>();
    	private ArrayList<String> comments = new ArrayList<String>();
    
    
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Project(String title, String description, String readme, String license, String project_url,
			String _private,long upvotes, long downvotes, long viewcount, ArrayList<String> images,
			ArrayList<String> video_url, ArrayList<String> zip_file, ArrayList<String> contributors,
			ArrayList<String> tags, ArrayList<String> comments) {
		super();
		this.title = title;
		this.description = description;
		this.readme = readme;
		this.license = license;
		//this.keywords = keywords;
		this.project_url = project_url;
		this.comments = comments;
		this._private = _private;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
		this.viewcount = viewcount;
		//this.index = index;
		//this.like = like;
		this.images = images;
		this.video_url = video_url;
		this.zip_file = zip_file;
		this.contributors = contributors;
		this.tags = tags;
		
	}
	@Override
	public String toString() {
		return "Project [title=" + title + ", description=" + description + ", readme=" + readme + ", license="
						+ license + ", project_url=" + project_url + ", _private=" + _private + ", upvotes=" + upvotes 
						+ ", downvotes=" + downvotes + ", viewcount=" + viewcount + ", images=" + images + ", video_url=" + video_url + ","
						+ " zip_file=" + zip_file + ", contributors=" + contributors + ", tags=" + tags + ", comments=" + comments + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public long getViewcount() {
		return viewcount;
	}
	public void setViewcount(long viewcount) {
		this.viewcount = viewcount;
	}
	public ArrayList<String> getComments() {
		return comments;
	}
	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
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

    
	
}
