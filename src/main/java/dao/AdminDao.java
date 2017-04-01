package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Institute;
import service.DatabaseServices;
import service.GeneralServices;

public class AdminDao {
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("institute");
	public Acknowledgement insertinstitute(Institute institute){
		
		
		Document doc=new Document().append("loginid", institute.getLoginid())
				.append("password", institute.getPassword())
				.append("university_name", institute.getUniversity_name())
				.append("college_name", institute.getCollege_name())
				.append("state", institute.getState())
				.append("city", institute.getCity())
				.append("email_id",institute.getEmail_id())
				.append("phone_no", institute.getPhone_no())
				.append("address", institute.getAddress());
				
		return new GeneralServices().response(null);
	}
}
