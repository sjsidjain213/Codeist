package dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Institute;
import bean.Notifications;
import service.DatabaseServices;
import service.GeneralServices;

public class AdminDao {
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("institute");
	public Acknowledgement insertinstitute(Institute institute){
	
		
		Document doc=new Document().append("username", institute.getUsername())
				.append("password", institute.getPassword())
				.append("university_name", institute.getUniversity_name())
				.append("college_name", institute.getCollege_name())
				.append("state", institute.getState())
				.append("city", institute.getCity())
				.append("email_id",institute.getEmail_id())
				.append("phone_no", institute.getPhone_no())
				.append("address", institute.getAddress())
				.append("departments",institute.getDepartments())
				.append("tags", institute.getTags())
				.append("project_id", new ArrayList<String>())
				.append("question_id", new ArrayList<String>())
				.append("question_asked", new ArrayList<String>());
			tc.insertOne(doc);	
		return new GeneralServices().response(Notifications.PROJECTINSERTED);
	}
	
@SuppressWarnings("unchecked")
public Acknowledgement updateinstitute(String id,Institute institute){
	 ObjectId oid = new ObjectId(id.toString());
	 Document document = tc.find(eq("_id",oid)).first();
	 
	 
		Document doc=new Document().append("username", institute.getUsername())
				.append("password", document.getString("password"))
				.append("university_name", institute.getUniversity_name())
				.append("college_name", institute.getCollege_name())
				.append("state", institute.getState())
				.append("city", institute.getCity())
				.append("email_id",institute.getEmail_id())
				.append("phone_no", institute.getPhone_no())
				.append("address", institute.getAddress())
				.append("departments",institute.getDepartments())
				.append("tags", institute.getTags())
				.append("project_id", (ArrayList<String>)document.get("project_id"))
				.append("question_id",(ArrayList<String>)document.get("question_id"))
				.append("question_asked",(ArrayList<String>)document.get("question_asked"));
		tc.updateOne(eq("_id",oid),new Document("$set",doc));
		return new GeneralServices().response(Notifications.PROJECTINSERTED);
	}

public void getHighlyRated()
{
	
FindIterable <Document> doc  = tc.find().sort(new Document("rating",-1));

}
	
}
