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
				.append("password", institute.getPassword());
				
		return new GeneralServices().response(null);
	}
}
