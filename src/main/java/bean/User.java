package bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
    @XmlRootElement
public class User {
	@XmlElement
    private String username,password,name,bio,phone_no,gender,email_id,country,state,city,message,category,institute;
	private ArrayList<String> favourite_tag,following,follower,contributing,project_id,project_bookmark,question_bookmark;
	private ArrayList<String> tag_view, user_view, problem_category_view, project_view,question_ask,question_answer;
	private long rating,zipcode;
	private Date date = new Date();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public ArrayList<String> getFavourite_tag() {
		return favourite_tag;
	}
	public void setFavourite_tag(ArrayList<String> favourite_tag) {
		this.favourite_tag = favourite_tag;
	}
	public ArrayList<String> getFollowing() {
		return following;
	}
	public void setFollowing(ArrayList<String> following) {
		this.following = following;
	}
	public ArrayList<String> getFollower() {
		return follower;
	}
	public void setFollower(ArrayList<String> follower) {
		this.follower = follower;
	}
	public ArrayList<String> getContributing() {
		return contributing;
	}
	public void setContributing(ArrayList<String> contributing) {
		this.contributing = contributing;
	}
	public ArrayList<String> getProject_id() {
		return project_id;
	}
	public void setProject_id(ArrayList<String> project_id) {
		this.project_id = project_id;
	}
	public ArrayList<String> getProject_bookmark() {
		return project_bookmark;
	}
	public void setProject_bookmark(ArrayList<String> project_bookmark) {
		this.project_bookmark = project_bookmark;
	}
	public ArrayList<String> getQuestion_bookmark() {
		return question_bookmark;
	}
	public void setQuestion_bookmark(ArrayList<String> question_bookmark) {
		this.question_bookmark = question_bookmark;
	}
	public ArrayList<String> getTags_view() {
		return tag_view;
	}
	public void setTags_view(ArrayList<String> tags_view) {
		this.tag_view = tags_view;
	}
	public ArrayList<String> getUser_view() {
		return user_view;
	}
	public void setUser_view(ArrayList<String> user_view) {
		this.user_view = user_view;
	}
	public ArrayList<String> getProblem_category_view() {
		return problem_category_view;
	}
	public void setProblem_category_view(ArrayList<String> problem_category_view) {
		this.problem_category_view = problem_category_view;
	}
	public ArrayList<String> getProject_view() {
		return project_view;
	}
	public void setProject_view(ArrayList<String> project_view) {
		this.project_view = project_view;
	}
	public ArrayList<String> getQuestion_ask() {
		return question_ask;
	}
	public void setQuestion_ask(ArrayList<String> question_ask) {
		this.question_ask = question_ask;
	}
	public ArrayList<String> getQuestion_answer() {
		return question_answer;
	}
	public void setQuestion_answer(ArrayList<String> question_answer) {
		this.question_answer = question_answer;
	}
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}
	public long getZipcode() {
		return zipcode;
	}
	public void setZipcode(long zipcode) {
		this.zipcode = zipcode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", bio=" + bio
				+ ", phone_no=" + phone_no + ", gender=" + gender + ", email_id=" + email_id + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", message=" + message + ", category=" + category
				+ ", institute=" + institute + ", favourite_tag=" + favourite_tag + ", following=" + following
				+ ", follower=" + follower + ", contributing=" + contributing + ", project_id=" + project_id
				+ ", project_bookmark=" + project_bookmark + ", question_bookmark=" + question_bookmark + ", tags_view="
				+ tag_view + ", user_view=" + user_view + ", problem_category_view=" + problem_category_view
				+ ", project_view=" + project_view + ", question_ask=" + question_ask + ", question_answer="
				+ question_answer + ", rating=" + rating + ", zipcode=" + zipcode + ", date=" + date + "]";
	}
	public User(String username, String password, String name, String bio, String phone_no, String gender,
			String email_id, String country, String state, String city, String message, String category,
			String institute, ArrayList<String> favourite_tag, ArrayList<String> following, ArrayList<String> follower,
			ArrayList<String> contributing, ArrayList<String> project_id, ArrayList<String> project_bookmark,
			ArrayList<String> question_bookmark, ArrayList<String> tags_view, ArrayList<String> user_view,
			ArrayList<String> problem_category_view, ArrayList<String> project_view, ArrayList<String> question_ask,
			ArrayList<String> question_answer, long rating, long zipcode, Date date) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.bio = bio;
		this.phone_no = phone_no;
		this.gender = gender;
		this.email_id = email_id;
		this.country = country;
		this.state = state;
		this.city = city;
		this.message = message;
		this.category = category;
		this.institute = institute;
		this.favourite_tag = favourite_tag;
		this.following = following;
		this.follower = follower;
		this.contributing = contributing;
		this.project_id = project_id;
		this.project_bookmark = project_bookmark;
		this.question_bookmark = question_bookmark;
		this.tag_view = tags_view;
		this.user_view = user_view;
		this.problem_category_view = problem_category_view;
		this.project_view = project_view;
		this.question_ask = question_ask;
		this.question_answer = question_answer;
		this.rating = rating;
		this.zipcode = zipcode;
		this.date = date;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	/*public static Comparator<User> ratingsort = new Comparator<User>() {

		public int compare(User s1,User s2) {

		   int rating1 = (int)s1.getRating();
		   int rating2 = (int)s2.getRating();

		   return rating2-rating1;
	  }};*/
	
    }
