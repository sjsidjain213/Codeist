package bean;

import java.util.ArrayList;

public class Signup 
{
  
	
	private String username,emailaddress, profileurl,location;
	private ArrayList<String> following,followers;
	  //--profile image
	 
	 private int age;
	
	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getProfileurl() {
		return profileurl;
	}

	public void setProfileurl(String profileurl) {
		this.profileurl = profileurl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public int getAge() {
		return age;
	}

	public Signup(String username, String emailaddress, String profileurl, String location, ArrayList<String> following,
			ArrayList<String> followers, int age) {
		super();
		this.username = username;
		this.emailaddress = emailaddress;
		this.profileurl = profileurl;
		this.location = location;
		this.following = following;
		this.followers = followers;
		this.age = age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Signup()
	{
		
		
	super();
		
	}

}
