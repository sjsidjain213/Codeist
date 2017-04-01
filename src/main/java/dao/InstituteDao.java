package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

import service.DatabaseServices;

public class InstituteDao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("institute");
    //id of institute
	public void getAllQuestions(String username)
	{
     Document doc = tc.find(eq("username",username)).first();
     ArrayList<String> alquestion = (ArrayList<String>) doc.get("question_id");
     for(String s : alquestion)
     { 
    	 new QADao().getQuestion(s);		
     }
     
	}
}
