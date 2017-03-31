package dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Answer;
import bean.MultiUse;
import bean.Notifications;
import bean.Question;
import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.servlet.http.HttpServletRequest;

public class QADao {
	
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("testqa");
	MongoCollection<Document> tcuser = new DatabaseServices().getDb().getCollection("testuserdata");

	public Acknowledgement insertQuestion(Question question, HttpServletRequest req)
	{   //String userfromsession ="hsharma";// req.getSession().getAttribute("username").toString();
	    Document doc2 = tc.find(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion()))).first();
		if(doc2==null)
		{
			Document info=new Document().append("upvotes",new ArrayList<String>()).append("downvotes",new ArrayList<String>());
			Document doc = new Document("username",req.getSession().getAttribute("username"))
	    		.append("question",question.getQuestion())
	    		.append("description",question.getDescription())
	    		.append("tags",(List<String>)question.getTags())
	    		.append("date",GeneralServices.getCurrentDate().getTime())
	    		.append("info",info)
	    		.append("featured_points", question.getFeatured_points());
	       tc.insertOne(doc);
	    
	    String id = tc.find(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion()))).first().get("_id").toString();

	    // for userdata 
	    new UserDao().moduleIDAdder(Notifications.QUESTIONMODULE,req.getSession().getAttribute("username").toString(), id);
	    
	    // for url
	    String url = GeneralServices.urlGenerator(Notifications.QUESTIONMODULE, id, question.getQuestion());
	    tc.updateOne(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion())),new Document("$set",new Document("url",url)));
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
	public Question getQuestion(String id)
	{
		ObjectId id1=new ObjectId(id.toString());
		Document doc = tc.find(eq("_id",id1)).first();
		ArrayList<Answer> alansw = new ArrayList<Answer>();
	if(doc!=null)
	{Question quest = new Question();
	quest.setUsername(doc.getString("username"));
	quest.setDescription(doc.getString("description"));
	quest.setDate(doc.getLong("date"));
	quest.setTags((ArrayList<String>)doc.get("tags"));
	quest.setQuestion_url(doc.getString("url"));
	Document document=(Document) doc.get("info");
	quest.setDownvotes((ArrayList<String>)document.get("downvotes"));
	quest.setUpvotes((ArrayList<String>)document.get("upvotes"));
	quest.setFeatured_points(doc.getLong("featured_points"));
    ArrayList<Document> aldo = (ArrayList<Document>) doc.get("answers");
    if(aldo != null){
	for(Document d:aldo)
	{	Answer answer =  new Answer();
		answer.setUsername(d.getString("username"));
		answer.setAnswer(d.getString("answer"));
		answer.setDate(d.getLong("date"));
		Document in=(Document) d.get("info");
		answer.setDownvotes((ArrayList<String>)in.get("downvotes"));
		answer.setUpvotes((ArrayList<String>)in.get("upvotes"));
		answer.setFeatured_points(d.getLong("featured_points"));
		alansw.add(answer);
	}
	quest.setAnswers(alansw);
    }
	return quest;
	}
	else{
		Question question = new Question();
		question.setDescription("Question Id do not exsit");
		return question;
	}
	}
	
	public Acknowledgement insertAnswer(String id,Answer answer,HttpServletRequest req)
	{   Document info=new Document().append("upvotes",new ArrayList<String>()).append("downvotes",new ArrayList<String>());	
    	//String userfromsession ="pulkit"; //req.getSession().getAttribute("username").toString();
	 ObjectId oid = new ObjectId(id.toString());
		Document d = tc.find(eq("_id",oid)).first();
		System.out.println("****"+d);
	    //String q_id = tc.find(and(eq("username",d.getString("username")),eq("question",answer.getQuestion()))).first().get("_id").toString();
	    Document doc = new Document("username",req.getSession().getAttribute("username").toString())
	    		.append("answer",answer.getAnswer())
	    		.append("date",GeneralServices.getCurrentDate().getTime())
	    		.append("info",info);
	    		//.append("featured_points", answer.getFeatured_points());
        String acknow =tc.updateOne(eq("question",answer.getQuestion()),new Document("$push",new Document("answers",doc))).toString();
        Acknowledgement acknowledge = new GeneralServices().response(Notifications.SUCCESSFULLYINSERTED);
        //answer.getUsername is owner of question
        new NotificationService().answerNotification(d.getString("username"),d.getString("question"),id,req.getSession().getAttribute("username").toString(),answer.getAnswer(),Notifications.QUESTIONSOLVED);
        return acknowledge;
	}
	
	@SuppressWarnings("unchecked")
	public MultiUse upQuestion(String id,String user){
		ObjectId id1=new ObjectId(id.toString());
		Document d = tc.find(eq("_id",id1)).first();
		MultiUse obj=new MultiUse();
		String username=d.getString("username");
			
		//username ques owner //user who votes
		//d = tc.find(eq("_id",id1)).first();
		Document infodetails=(Document)d.get("info");
			ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
			ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
			if(up!=null){
				if(!up.contains(user)){
					if(down!=null &&down.contains(user)){
						// when user had downvoted in past 
						 down.remove(user);
						 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
			 		}
				 up.add(user);
				//
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				//public void voteNotification(String username,String pqname,String pqid,String commitername,Notifications notify)
		        String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				   
				//new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.UPVOTESQUESTION);
		    	obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;}
				else{
					up.remove(user);
					//
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 
					tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
					tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(down!=null && down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
				}
				up=new ArrayList<String>();
				up.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				 //String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.UPVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}
	@SuppressWarnings("unchecked")
	public MultiUse downQuestion(String id,String user){
		ObjectId id1=new ObjectId(id.toString());
		Document d = tc.find(eq("_id",id1)).first();
		MultiUse obj=new MultiUse();
		String username=d.getString("username");
		//Document d = tc.find(and(eq("username",username),eq("question",question.getQuestion()))).first();
		Document infodetails=(Document)d.get("info");
			ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
			ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
			
			if(down!=null){
				if(!down.contains(user)){
					if(up!=null && up.contains(user)){
						up.remove(user);
						 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
					}
				down.add(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.DOWNVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;}
				else{
					down.remove(user);
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up)));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
				}
				down=new ArrayList<String>();
				down.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down)));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 
				 //String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.DOWNVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}


	@SuppressWarnings("unchecked")
	public MultiUse upanswer(String id,String username,String user){
		//username:whos answer
		//user upvote user
		ObjectId id1=new ObjectId(id.toString());
		MultiUse obj=new MultiUse();
		Document d = tc.find(eq("_id",id1)).first();
		ArrayList<String> up=null,down=null;
		int i=0;
		ArrayList<Document> aldo = (ArrayList<Document>) d.get("answers");
		for(Document d1:aldo){
			if(d1.get("username").equals(username)){
			    Document infodetails=(Document)d1.get("info");
				up=(ArrayList<String>)infodetails.get("upvotes");
				down=(ArrayList<String>)infodetails.get("downvotes");
				break;
					}
			i++;
		}
		
		String ups="answers."+i+".info.upvotes";
		String downs="answers."+i+".info.downvotes";
		String upc="answers."+i+".upvotecount";
		String downc="answers."+i+".downvotecount";
			if(up!=null){
				if(!up.contains(user)){
					if(down!=null &&down.contains(user)){
						down.remove(user);
						 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,down.size())));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
					}
				up.add(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
				tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,up.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
		//public void voteNotification(String username,String pqname,String pqid,String commitername,Notifications notify)
		String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				   
				//new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.UPVOTESANSWER);
		obj.setUpvotes(up);
       	obj.setDownvotes(down);
		return obj;}
				else{
					up.remove(user);
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,up.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(down!=null && down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,down.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
				}
				up=new ArrayList<String>();
				up.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();	 
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,up.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.UPVOTESANSWER);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}
	@SuppressWarnings("unchecked")
	public MultiUse downanswer(String id,String username,String user){
		ObjectId id1=new ObjectId(id.toString());
		Document d = tc.find(eq("_id",id1)).first();
		MultiUse obj=new MultiUse();
		ArrayList<String> up=null,down=null;
		int i=0;
		ArrayList<Document> aldo = (ArrayList<Document>) d.get("answers");
		for(Document d1:aldo){
			if(d1.get("username").equals(username)){
				 Document infodetails=(Document)d1.get("info");
			up=(ArrayList<String>)infodetails.get("upvotes");
			 down=(ArrayList<String>)infodetails.get("downvotes");
			break;
						}
				i++;
			}
		
		String ups="answers."+i+".info.upvotes";
		String downs="answers."+i+".info.downvotes";
		String upc="answers."+i+".upvotecount";
		String downc="answers."+i+".downvotecount";
			if(down!=null){
				if(!down.contains(user)){
					if(up!=null && up.contains(user)){
						up.remove(user);
						 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();	 
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,up.size())));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
					}
				down.add(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,down.size())));
				tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.DOWNVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;}
				else{
					down.remove(user);
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,down.size())));
					tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,up.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
				}
				down=new ArrayList<String>();
				down.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,down.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.DOWNVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}
	
}
