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
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;

import javax.management.Notification;
import javax.servlet.http.HttpServletRequest;

public class QADao {
	
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("testqa");
    public Acknowledgement insertQuestion(Question question, HttpServletRequest req)
	{   String userfromsession = req.getSession().getAttribute("username").toString();
	    Document doc2 = tc.find(and(eq("username",userfromsession),eq("question",question.getQuestion()))).first();
		if(doc2==null)
		{Document info=new Document().append("upvotes", (ArrayList<String>)question.getUpvotes()).append("downvotes",(ArrayList<String>)question.getDownvotes());
			Document doc = new Document("username",question.getUsername())
	    		.append("question",question.getQuestion())
	    		.append("date",question.getDate())
	    		.append("info",info)
	    		.append("featured_points", question.getFeatured_points());
	       tc.insertOne(doc);
	    
	    String id = tc.find(and(eq("username",userfromsession),eq("question",question.getQuestion()))).first().get("_id").toString();

	    // for userdata 
	    new UserDao().moduleIDAdder(Notifications.QUESTIONMODULE,userfromsession, id);
	    
	    // for url
	    String url = GeneralServices.urlGenerator(Notifications.QUESTIONMODULE, id, question.getQuestion());
	    tc.updateOne(and(eq("username",userfromsession),eq("question",question.getQuestion())),new Document("$set",new Document("url",url)));
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
	
	@SuppressWarnings("unchecked")
	public Question getQuestion(String question)
	{
	Document doc = tc.find(eq("question",question)).first();	
	Question quest = new Question();
	quest.setUsername(doc.getString("username"));
	quest.setDate(doc.getDate("date"));
	quest.setTags((ArrayList<String>)doc.get("tags"));
	quest.setUrl(doc.getString("url"));
	Document document=(Document) doc.get("info");
	
	quest.setDownvotes((ArrayList<String>)document.get("downvotes"));
	quest.setUpvotes((ArrayList<String>)document.get("upvotes"));
	quest.setFeatured_points(doc.getLong("featured_points"));
	ArrayList<Answer> alansw = new ArrayList<Answer>();
	ArrayList<Document> aldo = (ArrayList<Document>) doc.get("answers");
	for(Document d:aldo)
	{	Answer answer =  new Answer();
		answer.setUsername(d.getString("username"));
		answer.setAnswer(d.getString("answer"));
		answer.setDate(d.getDate("date"));
		Document in=(Document) d.get("info");
		answer.setDownvotes((ArrayList<String>)in.get("downvotes"));
		answer.setUpvotes((ArrayList<String>)in.get("upvotes"));
		answer.setFeatured_points(d.getLong("featured_points"));
		alansw.add(answer);
	}
	quest.setAnswer(alansw);
	return quest;
	}
	
	public Acknowledgement insertAnswer(Answer answer,HttpServletRequest req)
	{ Document info=new Document().append("upvotes", (ArrayList<String>)answer.getUpvotes()).append("downvotes",(ArrayList<String>)answer.getDownvotes());	
	  Document doc = new Document("username",req.getSession().getAttribute("username"))
	    		.append("answer",answer.getAnswer())
	    		.append("date",answer.getDate())
	    		.append("info",info)
	    		.append("featured_points", answer.getFeatured_points());
String acknow =tc.updateOne(eq("question",answer.getQuestion()),new Document("$push",new Document("answers",doc))).toString();
    Acknowledgement acknowledge = new GeneralServices().stoacknowmethod(s ->{
	Acknowledgement ac2 = new Acknowledgement();
    String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
    ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
    return ac2;}, acknow);
    new NotificationService().answerNotification(answer.getUsername(),answer.getQuestion(),req.getSession().getAttribute("username").toString(),answer.getAnswer(),Notifications.QUESTIONSOLVED);
    return acknowledge;
	}
	
	@SuppressWarnings("unchecked")
	public Acknowledgement upques(String username,Question question,String user){
		Document d = tc.find(and(eq("username",username),eq("question",question.getQuestion()))).first();
		Document infodetails=(Document)d.get("info");
			ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
			ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
			
			if(up!=null){
				if(!up.contains(user)){
					if(down!=null &&down.contains(user)){
						down.remove(user);
						 String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.downvotes",down))).toString();	 
					}
				up.add(user);
				String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.upvotes",up))).toString();	 
				 new NotificationService().voteNotification(username,question.getQuestion(),Notifications.UPVOTESQUESTION);
				 return new GeneralServices().response(acknow2);}
				else{
					up.remove(user);
					String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.upvotes",up))).toString();	 	 
				}
			return new GeneralServices().response("already exist");
			}
			else{
				if(down!=null && down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.downvotes",down))).toString();	 
				}
				up=new ArrayList<String>();
				up.add(user);
				 String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.upvotes",up))).toString();	 
				 new NotificationService().voteNotification(username,question.getQuestion(),Notifications.UPVOTESQUESTION);
				 return new GeneralServices().response(acknow2);
				
			}
	}
	@SuppressWarnings("unchecked")
	public Acknowledgement downques(String username,Question question,String user){
		Document d = tc.find(and(eq("username",username),eq("question",question.getQuestion()))).first();
		Document infodetails=(Document)d.get("info");
			ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
			ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
			
			if(down!=null){
				if(!down.contains(user)){
					if(up!=null && up.contains(user)){
						up.remove(user);
						 String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.upvotes",up))).toString();	 
					}
				down.add(user);
				String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.downvotes",down))).toString();	 
				 new NotificationService().voteNotification(username,question.getQuestion(),Notifications.DOWNVOTESPROJECT);
				 return new GeneralServices().response(acknow2);}
				else{
					down.remove(user);
					String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.downvotes",down))).toString();	 	 
				}
			return new GeneralServices().response("already exist");
			}
			else{
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.upvotes",up))).toString();	 
				}
				down=new ArrayList<String>();
				down.add(user);
				 String acknow2 = tc.updateOne(and(eq("username", username),eq("question",question.getQuestion())),new Document("$set",new Document("info.downvotes",down))).toString();	 
				 new NotificationService().voteNotification(username,question.getQuestion(),Notifications.DOWNVOTESPROJECT);
				 return new GeneralServices().response(acknow2);
				
			}
	}


}
