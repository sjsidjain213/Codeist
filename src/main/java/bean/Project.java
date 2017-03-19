package bean;

import java.util.ArrayList;

public class Project extends SearchBean{
	

	private String title,description,readme,licence,keywords,project_url,comment,_private;
    private int index;
    private double like;
    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> videourl = new ArrayList<String>();
    private ArrayList<String> zipfile = new ArrayList<String>();
    private ArrayList<String> contributors = new ArrayList<String>();
    private ArrayList<String> tags = new ArrayList<String>();
    
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Project(String title, String description, String readme, String licence, String keywords, String project_url,
			String comment, String _private, int index, double like, ArrayList<String> images,
			ArrayList<String> videourl, ArrayList<String> zipfile, ArrayList<String> contributors,
			ArrayList<String> tags) {
		super();
		this.title = title;
		this.description = description;
		this.readme = readme;
		this.licence = licence;
		this.keywords = keywords;
		this.project_url = project_url;
		this.comment = comment;
		this._private = _private;
		this.index = index;
		this.like = like;
		this.images = images;
		this.videourl = videourl;
		this.zipfile = zipfile;
		this.contributors = contributors;
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "Project [title=" + title + ", description=" + description + ", readme=" + readme + ", licence="
				+ licence + ", keywords=" + keywords + ", project_url=" + project_url + ", comment=" + comment
				+ ", _private=" + _private + ", index=" + index + ", like=" + like + ", images=" + images
				+ ", videourl=" + videourl + ", zipfile=" + zipfile + ", contributors=" + contributors + ", tags="
				+ tags + "]";
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String get_private() {
		return _private;
	}
	public void set_private(String _private) {
		this._private = _private;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getLike() {
		return like;
	}
	public void setLike(double like) {
		this.like = like;
	}
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
	public ArrayList<String> getVideourl() {
		return videourl;
	}
	public void setVideourl(ArrayList<String> videourl) {
		this.videourl = videourl;
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
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
    
	
}
