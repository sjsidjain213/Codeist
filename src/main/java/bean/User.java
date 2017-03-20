package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
    @XmlRootElement
public class User extends SearchBean {
	@XmlElement
private String username,name,bio,phone_no,email_id,linkedin_id,github_id,country,state,city,zipcode,message="Log in First";
	private ArrayList<String> favourite_tags,following,followers,contributing;
	private ArrayList<String> tags_viewed, user_viewed, problem_category_viewed, project_viewed;
	private long reward_points;

	public String getUsername() {
		return username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setUsername(String username) {
		this.username = username;
	    this.message="either you are not logged in or searched user do not exsist";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getLinkedin_id() {
		return linkedin_id;
	}
	public void setLinkedin_id(String linkedin_id) {
		this.linkedin_id = linkedin_id;
	}
	public String getGithub_id() {
		return github_id;
	}
	public void setGithub_id(String github_id) {
		this.github_id = github_id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public ArrayList<String> getFavourite_tags() {
		return favourite_tags;
	}
	public void setFavourite_tags(ArrayList<String> favourite_tags) {
		this.favourite_tags = favourite_tags;
	}
	public ArrayList<String> getFollowing() {
		return following;
	}
	public void setFollowing(ArrayList<String> following) {
		this.following = following;
	}
	public ArrayList<String> getFollowers() {
		return followers;
	}
	public void setFollowers(ArrayList<String> followers) {
		this.followers = followers;
	}
	public ArrayList<String> getContributing() {
		return contributing;
	}
	public void setContributing(ArrayList<String> contributing) {
		this.contributing = contributing;
	}
	public long getReward_points(){
		return reward_points;
	}
	public void setReward_points(long points){
		this.reward_points = points;
	}
	public ArrayList<String> getTags_viewed() {
		return tags_viewed;
	}
	public void setTags_viewed(ArrayList<String> tags_viewed) {
		this.tags_viewed = tags_viewed;
	}
	public ArrayList<String> getUser_viewed() {
		return user_viewed;
	}
	public void setUser_viewed(ArrayList<String> user_viewed) {
		this.user_viewed = user_viewed;
	}
	public ArrayList<String> getProblem_category_viewed() {
		return problem_category_viewed;
	}
	public void setProblem_category_viewed(ArrayList<String> problem_category_viewed) {
		this.problem_category_viewed = problem_category_viewed;
	}
	public ArrayList<String> getProject_viewed() {
		return project_viewed;
	}
	public void setProject_viewed(ArrayList<String> project_viewed) {
		this.project_viewed = project_viewed;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", bio=" + bio + ", phone_no=" + phone_no
				+ ", email_id=" + email_id + ", linkedin_id=" + linkedin_id + ", github_id=" + github_id + ", country="
				+ country + ", state=" + state + ", city=" + city + ", zipcode=" + zipcode + ", favourite_tags="
				+ favourite_tags + ", following=" + following + ", followers=" + followers + ", contributing="
				+ contributing + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String name, String bio, String phone_no, String email_id, String linkedin_id,
			String github_id, String country, String state, String city, String zipcode,
			ArrayList<String> favourite_tags, ArrayList<String> following, ArrayList<String> followers,
			ArrayList<String> contributing) {
		super();
		this.username = username;
		this.name = name;
		this.bio = bio;
		this.phone_no = phone_no;
		this.email_id = email_id;
		this.linkedin_id = linkedin_id;
		this.github_id = github_id;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zipcode = zipcode;
		this.favourite_tags = favourite_tags;
		this.following = following;
		this.followers = followers;
		this.contributing = contributing;
	}	

    }
