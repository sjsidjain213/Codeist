package bean;

import java.util.ArrayList;
import java.util.Date;

public class Institute {
	private String username,password,university_name,college_name,state,city,email_id,phone_no,address;
	private ArrayList<ArrayList<String>> departments;
	private ArrayList<String> depart;
	public ArrayList<String> getDepart() {
		return depart;
	}
	public void setDepart(ArrayList<String> depart) {
		this.depart = depart;
	}
	private ArrayList<String> tags,project_id,question_id,question_asked;
	private Long project_upvote,project_downvote,qa_upvote,qa_downvote,rating;
	
	public Long getRating() {
		return rating;
	}
	public void setRating(Long rating) {
		this.rating = rating;
	}
	public Long getProject_upvote() {
		return project_upvote;
	}
	public void setProject_upvote(Long project_upvote) {
		this.project_upvote = project_upvote;
	}
	public Long getProject_downvote() {
		return project_downvote;
	}
	public void setProject_downvote(Long project_downvote) {
		this.project_downvote = project_downvote;
	}
	public Long getQa_upvote() {
		return qa_upvote;
	}
	public void setQa_upvote(Long qa_upvote) {
		this.qa_upvote = qa_upvote;
	}
	public Long getQa_downvote() {
		return qa_downvote;
	}
	public void setQa_downvote(Long qa_downvote) {
		this.qa_downvote = qa_downvote;
	}
	public ArrayList<String> getProject_id() {
		return project_id;
	}
	public void setProject_id(ArrayList<String> project_id) {
		this.project_id = project_id;
	}
	public ArrayList<String> getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(ArrayList<String> question_id) {
		this.question_id = question_id;
	}
	public ArrayList<String> getQuestion_asked() {
		return question_asked;
	}
	public void setQuestion_asked(ArrayList<String> question_asked) {
		this.question_asked = question_asked;
	}
	private Long date;
	
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
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ArrayList<ArrayList<String>> getDepartments() {
		return departments;
	}
	public void setDepartments(ArrayList<ArrayList<String>> departments) {
		this.departments = departments;
	}
	public Institute(String username, String password, String university_name, String college_name, String state,
			String city, String email_id, String phone_no, String address, ArrayList<ArrayList<String>> departments,
			ArrayList<String> tags, Long date) {
		super();
		this.username = username;
		this.password = password;
		this.university_name = university_name;
		this.college_name = college_name;
		this.state = state;
		this.city = city;
		this.email_id = email_id;
		this.phone_no = phone_no;
		this.address = address;
		this.departments = departments;
		this.tags = tags;
		this.date = date;
	}
	public Institute() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Institute [username=" + username + ", password=" + password + ", university_name=" + university_name
				+ ", college_name=" + college_name + ", state=" + state + ", city=" + city + ", email_id=" + email_id
				+ ", phone_no=" + phone_no + ", address=" + address + ", departments=" + departments + ", tags=" + tags
				+ ", date=" + date + "]";
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	
}
