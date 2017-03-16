package bean;

public class Project {
	private String title,description,access,readme,licence,keywords,zipfile,images,URL,tags,like,comment,contibutor;

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getZipfile() {
		return zipfile;
	}

	public void setZipfile(String zipfile) {
		this.zipfile = zipfile;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
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

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getContibutor() {
		return contibutor;
	}

	public void setContibutor(String contibutor) {
		this.contibutor = contibutor;
	}

	public Project(String title, String description, String access, String readme, String licence, String keywords,
			String zipfile, String images, String uRL, String tags, String like, String comment, String contibutor) {
		super();
		this.title = title;
		this.description = description;
		this.access = access;
		this.readme = readme;
		this.licence = licence;
		this.keywords = keywords;
		this.zipfile = zipfile;
		this.images = images;
		URL = uRL;
		this.tags = tags;
		this.like = like;
		this.comment = comment;
		this.contibutor = contibutor;
	}

	@Override
	public String toString() {
		return "Project [title=" + title + ", description=" + description + ", access=" + access + ", readme=" + readme
				+ ", licence=" + licence + ", keywords=" + keywords + ", zipfile=" + zipfile + ", images=" + images
				+ ", URL=" + URL + ", tags=" + tags + ", like=" + like + ", comment=" + comment + ", contibutor="
				+ contibutor + "]";
	}

}
