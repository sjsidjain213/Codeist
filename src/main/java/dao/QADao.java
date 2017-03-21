package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Answer;
import bean.Question;
import service.DatabaseServices;
import service.GeneralServices;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
public class QADao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("qa");
    
	public Acknowledgement insertQuestion(Question question)
	{ 
		Document doc2 = tc.find(eq("question",question.getQuestion())).first();
		if(doc2==null)
		{Document doc = new Document("username",question.getUsername())
	    		.append("question",question.getQuestion())
	    		.append("date",question.getDate())
	    		.append("upvotes",question.getUpvotes())
	    		.append("downvotes", question.getDownvotes())
	    		.append("featured_points", question.getFeatured_points());
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
	public Question getQuestion(String question)
	{
	Document doc = tc.find(eq("question",question)).first();	
	Question quest = new Question();
	quest.setUsername(doc.getString("username"));
	quest.setDate(doc.getDate("date"));
	quest.setTags((ArrayList<String>)doc.get("tags"));
	quest.setDownvotes(doc.getLong("downvotes"));
	quest.setUpvotes(doc.getLong("upvotes"));
	quest.setFeatured_points(doc.getLong("featured_points"));
	ArrayList<Answer> alansw = new ArrayList<Answer>();
	ArrayList<Document> aldo = (ArrayList<Document>) doc.get("answers");
	for(Document d:aldo)
	{	Answer answer =  new Answer();
		answer.setUsername(d.getString("username"));
		answer.setAnswer(d.getString("answer"));
		answer.setDate(d.getDate("date"));
		answer.setDownvotes(d.getLong("downvotes"));
		answer.setUpvotes(d.getLong("upvotes"));
		answer.setFeatured_points(d.getLong("featured_points"));
		alansw.add(answer);
	}
	quest.setAnswer(alansw);
	return quest;
	}
	
	public Acknowledgement insertAnswer(Answer question,String quest)
	{ 
		Document doc = new Document("username",question.getUsername())
	    		.append("answer",question.getAnswer())
	    		.append("date",question.getDate())
	    		.append("upvotes",question.getUpvotes())
	    		.append("downvotes", question.getDownvotes())
	    		.append("featured_points", question.getFeatured_points());
String acknow =tc.updateOne(eq("question", quest),new Document("$push",new Document("answers",doc))).toString();
    Acknowledgement acknowledge = new GeneralServices().stoacknowmethod(s ->{
	Acknowledgement ac2 = new Acknowledgement();
    String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
    ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
    return ac2;}, acknow);
return acknowledge;
	}
	
}
