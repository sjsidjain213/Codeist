package bean;

import java.util.ArrayList;

public class Institute {
	private String loginid,password,universityname,collegename,state,city,email_id,phone_no,address;
	private ArrayList<ArrayList<String>> departments;
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUniversityname() {
		return universityname;
	}
	public void setUniversityname(String universityname) {
		this.universityname = universityname;
	}
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
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
	public Institute() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Institute(String loginid, String password, String universityname, String collegename, String state,
			String city, String email_id, String phone_no, String address, ArrayList<ArrayList<String>> departments) {
		super();
		this.loginid = loginid;
		this.password = password;
		this.universityname = universityname;
		this.collegename = collegename;
		this.state = state;
		this.city = city;
		this.email_id = email_id;
		this.phone_no = phone_no;
		this.address = address;
		this.departments = departments;
	}
	@Override
	public String toString() {
		return "Institute [loginid=" + loginid + ", password=" + password + ", universityname=" + universityname
				+ ", collegename=" + collegename + ", state=" + state + ", city=" + city + ", email_id=" + email_id
				+ ", phone_no=" + phone_no + ", address=" + address + ", departments=" + departments + "]";
	}
	

}
