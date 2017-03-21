package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Question;
import service.DatabaseServices;
import static com.mongodb.client.model.Filters.*;
public class QADao {

	public Acknowledgement insertQuestion(Question question)
	{
		MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("qa");
	    if(tc.find(eq("question",question.getQuestion().toLowerCase()))==null)
		{Document doc = new Document("username",question.getUsername())
	    		.append("question",question.getQuestion().toLowerCase())
	    		.append("date",question.getDate())
	    		.append("upvotes",question.getUpvotes())
	    		.append("downvotes", question.getDownvotes())
	    		.append("featured _point", question.getDownvotes());
	    tc.insertOne(doc);
		Acknowledgement acknow = new Acknowledgement();
	     acknow.setModifiedCount("1");
	     acknow.setMatchedCount("0");
		return acknow;
		}
	    else{
			Acknowledgement acknow = new Acknowledgement();
		     acknow.setModifiedCount("0");
		     acknow.setMatchedCount("1");
	    	return acknow;
	    }
		}
	public void getQuestion(String question)
	{
		
		
	}
}
