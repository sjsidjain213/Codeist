package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
    @XmlRootElement
public class User {
	@XmlElement
private String username,name,bio,phoneno,emailid,country,state,city,zipcode;	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
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

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String name, String bio, String phoneno, String emailid, String country, String state,
			String city, String zipcode) {
		super();
		this.username = username;
		this.name = name;
		this.bio = bio;
		this.phoneno = phoneno;
		this.emailid = emailid;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", bio=" + bio + ", phoneno=" + phoneno + ", emailid="
				+ emailid + ", country=" + country + ", state=" + state + ", city=" + city + ", zipcode=" + zipcode
				+ "]";
	}
}
