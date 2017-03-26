package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Answer;
import bean.Notifications;
import bean.Question;
import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
import service.SessionService;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
public class QADao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("qa");
    public Acknowledgement insertQuestion(Question question)
	{ 
		Document doc2 = tc.find(eq("question",question.getQuestion())).first();
		long intial = 0;
		if(doc2==null)
		{Document doc = new Document("username",question.getUsername())
	    		.append("question",question.getQuestion())
	    		.append("date",question.getDate())
	    		.append("tags",question.getTags())
	            .append("upvotes",intial)
	            .append("downvotes",intial)
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
	quest.setFeatured_points(doc.getLong("featured_points"));
	quest.setTags((ArrayList<String>)doc.get("tags"));
	quest.setDownvotes(doc.getLong("downvotes"));
	quest.setUpvotes(doc.getLong("upvotes"));
	ArrayList<Answer> alansw = new ArrayList<Answer>();
	ArrayList<Document> aldo = (ArrayList<Document>) doc.get("answers");
	if(aldo!=null&&aldo.size()>0)
	{	for(Document d:aldo)
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
	}
	return quest;
	}
	
	public Acknowledgement insertAnswer(Answer answer, HttpServletRequest req)
	{ long intial = 0;
		Document doc = new Document("username",answer.getUsername())
	    		.append("answer",answer.getAnswer())
	    		.append("date",answer.getDate())
	    		.append("upvotes",intial)
	    		.append("downvotes", intial)
	    		.append("featured_points",intial);
    String acknow =tc.updateOne(eq("question",answer.getQuestion()),new Document("$push",new Document("answers",doc))).toString();
    Acknowledgement acknowledge = new GeneralServices().response(acknow);
    
    //public void answerNotification(String username,String question,String commitername,String commitermsg,Notifications notify)
	new NotificationService().answerNotification(answer.getUsername(),answer.getQuestion(),req.getSession().getAttribute("username").toString(),answer.getAnswer(),Notifications.QUESTIONSOLVED);
    return acknowledge;
	}
	
}
