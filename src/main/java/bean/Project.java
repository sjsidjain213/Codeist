package bean;

import java.util.ArrayList;

public class Project {
	

	private String title,description,access,readme,licence,keywords,project_url,tags,comment;
    private int like,index;
    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> zipfile = new ArrayList<String>();
    private ArrayList<String> contributors = new ArrayList<String>();
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Project(String title, String description, String access, String readme, String licence, String keywords,
			String project_url, String tags, String comment, int like, int index, ArrayList<String> images,
			ArrayList<String> zipfile, ArrayList<String> contributors) {
		super();
		this.title = title;
		this.description = description;
		this.access = access;
		this.readme = readme;
		this.licence = licence;
		this.keywords = keywords;
		this.project_url = project_url;
		this.tags = tags;
		this.comment = comment;
		this.like = like;
		this.index = index;
		this.images = images;
		this.zipfile = zipfile;
		this.contributors = contributors;
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
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getReadme() {
		return readme;
	}
	public void setReadme(String readme) {
		this.readme = readme;
	}
	public String getLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getProject_url() {
		return project_url;
	}
	public void setProject_url(String project_url) {
		this.project_url = project_url;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
	public ArrayList<String> getZipfile() {
		return zipfile;
	}
	public void setZipfile(ArrayList<String> zipfile) {
		this.zipfile = zipfile;
	}
	public ArrayList<String> getContributors() {
		return contributors;
	}
	public void setContributors(ArrayList<String> contributors) {
		this.contributors = contributors;
	}
	@Override
	public String toString() {
		return "Project [title=" + title + ", description=" + description + ", access=" + access + ", readme=" + readme
				+ ", licence=" + licence + ", keywords=" + keywords + ", project_url=" + project_url + ", tags=" + tags
				+ ", comment=" + comment + ", like=" + like + ", index=" + index + ", images=" + images + ", zipfile="
				+ zipfile + ", contributors=" + contributors + "]";
	}
    
    
}
