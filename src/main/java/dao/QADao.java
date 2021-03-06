package dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
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
	
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("qa");
	MongoCollection<Document> tcuser = new DatabaseServices().getDb().getCollection("testuserdata");
	MongoCollection <Document> tcsession = new DatabaseServices().getDb().getCollection("sessions");
	

	public Acknowledgement insertQuestion(Question question,String s_id,HttpServletRequest req)
	{   //String userfromsession ="hsharma";// req.getSession().getAttribute("username").toString();
		try{
			Document ss=tcsession.find(eq("session_id",s_id)).first();
			String username = ss.getString("username");
	   // Document doc2 = tc.find(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion()))).first();
//	   	if(tc.find(eq("question",question.getQuestion()))!=null)
//			{Acknowledgement acknow = new Acknowledgement();
//			acknow.setMessage("question already exsist");
//			Document doc = tc.find(eq("question",question.getQuestion())).first();
//            String q_id = doc.get("_id").toString();
//		    acknow.setUpsertedId(q_id);
//            return acknow;
//			}
			Document doc2 = tc.find(and(eq("username",question.getUsername()),eq("question",question.getQuestion()))).first();
			if(doc2==null)
		{
				 if(new UserDao().getAllUseri().contains(username)){
					   question.setOwner("i");
				   }
				   else if(new InstituteDao().getAllUserc().contains(username)){
					   question.setOwner("c");
				   }
			Document info=new Document().append("upvotes",new ArrayList<String>()).append("downvotes",new ArrayList<String>());
			Document doc = new Document("username",username)
	    		.append("question",question.getQuestion())
	    		.append("description",question.getDescription())
	    		.append("state", question.getState())
	    		.append("upvotecount",new Long(0))
	    		.append("downvotecount",new Long(0))
	    		.append("city", question.getCity())
	    		.append("tags",(List<String>)question.getTags())
	    		.append("owner", question.getOwner())
	    		.append("date",GeneralServices.getCurrentDate().getTime())
	    		.append("last_updated",GeneralServices.getCurrentDate().getTime())
	    		.append("info",info)
	    		//.append("featured_points", question.getFeatured_points())
	    		.append("answers",new ArrayList<Answer>());
	       tc.insertOne(doc);
	       //String projectid= tc.find(and(eq("username",question.getUsername()),eq("question",question.getQuestion()))).first().get("_id").toString();
  		 
	//    String id = tc.find(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion()))).first().get("_id").toString();

	    // for userdata 
//	    new UserDao().moduleIDAdder(Notifications.QUESTIONMODULE,req.getSession().getAttribute("username").toString(), id);
	    
	    // for url
   String q_id=tc.find(and(eq("username",username),eq("question",question.getQuestion()))).first().get("_id").toString();
     String url = GeneralServices.urlGenerator(Notifications.QUESTIONMODULE, q_id, question.getQuestion());

     tc.find(and(eq("username",username),eq("question",question.getQuestion()))).first();	   
	   tc.updateOne(and(eq("username",username),eq("question",question.getQuestion())),new Document("$set",new Document("question_url",url)));
     Acknowledgement acknow = new Acknowledgement();
	    acknow.setMessage(GeneralServices.spaceRemover(question.getQuestion()));
		acknow.setUpsertedId(q_id);
		acknow.setLoggedin(true);
	    return acknow;
		}
	    else{
			Acknowledgement acknow = new Acknowledgement();
		     acknow.setMessage("exist");
		    acknow.setLoggedin(true);
	    	return acknow;
	    }
		}
		catch(Exception e){
			e.printStackTrace();
			return new GeneralServices().response(Notifications.ERROR);
		}
	}
	
	public ArrayList<Question> getAllQuestions(String username)
	{
	FindIterable <Document> fi = tc.find(eq("username",username));
	ArrayList<Question> alq = new ArrayList<Question>();
	for(Document d:fi)
	{
		Question question = new Question();
		question.setQuestion(d.getString("question"));
		question.setDescription(d.getString("description"));
		question.setUpvotecount(d.getLong("upvotecount"));
		question.setDownvotecount(d.getLong("downvotecount"));
		alq.add(question);
	}
	return alq;
	}
	
	@SuppressWarnings("unchecked")
	public Question getQuestion(String id)
	{System.out.println(id+"this is id");
		ObjectId id1=new ObjectId(id);
		Document doc = tc.find(eq("_id",id1)).first();
		ArrayList<Answer> alansw = new ArrayList<Answer>();
	if(doc!=null)
	{Question quest = new Question();
	quest.setId(id);
	quest.setUsername(doc.getString("username"));
	quest.setQuestion(doc.getString("question"));
	quest.setTitle(doc.getString("question"));
	quest.setDescription(doc.getString("description"));
	quest.setDate(doc.getLong("date"));
	//quest.setLast_updated(doc.getLong("last_updated"));
	quest.setTags((ArrayList<String>)doc.get("tags"));
	quest.setQuestion_url(GeneralServices.spaceRemover(doc.getString("question")));
	quest.setCity(doc.getString("city"));
	quest.setState(doc.getString("state"));
	Document document=(Document) doc.get("info");
	quest.setDownvotes((ArrayList<String>)document.get("downvotes"));
	quest.setUpvotes((ArrayList<String>)document.get("upvotes"));
	//quest.setFeatured_points(doc.getLong("featured_points"));
    ArrayList<Document> aldo = (ArrayList<Document>) doc.get("answers");
    if(aldo != null){
	for(Document d:aldo)
	{	Answer answer =  new Answer();
		answer.setUsername(d.getString("username"));
		answer.setAnswer(d.getString("answer"));
		answer.setDate(d.getLong("date"));
		answer.setOwner(d.getString("owner"));
		answer.setLast_updated(d.getLong("last_updated"));
		Document in=(Document) d.get("info");
		answer.setDownvotes((ArrayList<String>)in.get("downvotes"));
		answer.setUpvotes((ArrayList<String>)in.get("upvotes"));
		//answer.setFeatured_points(d.getLong("featured_points"));
		alansw.add(answer);
	}
	quest.setAnswers(alansw);
    }
    quest.setLoggedin(true);
	return quest;
	}
	else{
		Question question = new Question();
		question.setDescription("Question Id do not exsit");
		question.setLoggedin(true);
		return question;
	}
	}
	
	public Answer insertAnswer(String id,Answer answer,String s_id)
	{  Document ss=tcsession.find(eq("session_id",s_id)).first();
	String username = ss.getString("username");
		Document info=new Document().append("upvotes",new ArrayList<String>()).append("downvotes",new ArrayList<String>());	
    	//String userfromsession ="pulkit"; //req.getSession().getAttribute("username").toString();
	 ObjectId oid = new ObjectId(id.toString());
		Document d = tc.find(eq("_id",oid)).first();
		System.out.println("****"+d);
		 if(new UserDao().getAllUseri().contains(username)){
			 System.out.println("insidde i");
			   answer.setOwner("i");
		   }
		   else if(new InstituteDao().getAllUserc().contains(username)){
			   System.out.println("insidde c");
			   answer.setOwner("c");
		   }
	    //String q_id = tc.find(and(eq("username",d.getString("username")),eq("question",answer.getQuestion()))).first().get("_id").toString();
	    Document doc = new Document("username",username)
	    		.append("answer",answer.getAnswer())
	    		.append("date",GeneralServices.getCurrentDate().getTime())
	    		.append("owner", answer.getOwner())//
	    		.append("last_updated",GeneralServices.getCurrentDate().getTime())
	    		.append("info",info);
	    		//.append("featured_points", answer.getFeatured_points());
	    answer.setUsername(username);
	    answer.setDate(doc.getLong("date"));
	    answer.setLast_updated(doc.getLong("last_updated"));
	    answer.setUpvotes(new ArrayList<String>());
	    answer.setDownvotes(new ArrayList<String>());
	    answer.setOwner(doc.getString("owner"));
        String acknow =tc.updateOne(eq("_id",oid),new Document("$push",new Document("answers",doc))).toString();
        Acknowledgement acknowledge = new GeneralServices().response(Notifications.SUCCESSFULLYINSERTED);
        answer.setLoggedin(true);
        //answer.getUsername is owner of question
      ///  new NotificationService().answerNotification(d.getString("username"),d.getString("question"),id,req.getSession().getAttribute("username").toString(),answer.getAnswer(),Notifications.QUESTIONSOLVED);
        return answer;
	}
	/*public void updateQuestion(HttpServletRequest req,Question question,String id)
	{  //MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("testqa");
	   
	   ObjectId oid = new ObjectId(id.toString());
       //Document doc2 = tc.find(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion()))).first();
	   Document document = tc.find(eq("_id",oid)).first();
	   ArrayList<Document> aldo = (ArrayList<Document>) document.get("answers");
		Document doc = new Document("username",req.getSession().getAttribute("username"))
	    		.append("question",question.getQuestion())
	    		.append("description",question.getDescription())
	    		.append("tags",(List<String>)question.getTags())
	    		.append("date", document.getLong("date"))
	    		.append("last_updated", GeneralServices.getCurrentDate().getTime())
	    		.append("featured_points", question.getFeatured_points())
	    		.append("answers",aldo);
		tc.updateOne(eq("_id",oid),new Document("$set",doc));
	}*/
	

	@SuppressWarnings("unchecked")
	public MultiUse upQuestion(String id,String s_id){
		Document ss=tcsession.find(eq("session_id",s_id)).first();
		String user = ss.getString("username");
	//public void questionUpvoteNotification(String receiver,String commiter,String question_id, String question_name)
    //username from database,commitername from session, question id from front end, question name from database 
		ObjectId id1=new ObjectId(id.toString());
		String owner="i";
		Document d = tc.find(eq("_id",id1)).first();
		MultiUse obj=new MultiUse();
		obj.setLoggedin(true);
		String username=d.getString("username");
		
		if(new UserDao().getAllUseri().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
			owner="i";
		}
		else if(new InstituteDao().getAllUserc().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("institute");
			owner="c";
		}
			
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
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
						 new GeneralServices().ratingupdate(owner, username);
			 		}
				 up.add(user);
				//
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
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
					tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
					tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
					new GeneralServices().ratingupdate(owner, username);
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(down!=null && down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				up=new ArrayList<String>();
				up.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
				 //String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.UPVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}
	@SuppressWarnings("unchecked")
	public MultiUse downQuestion(String id,String s_id){
		Document ss=tcsession.find(eq("session_id",s_id)).first();
		String user = ss.getString("username");
		ObjectId id1=new ObjectId(id.toString());
		Document d = tc.find(eq("_id",id1)).first();
		MultiUse obj=new MultiUse();
		obj.setLoggedin(true);
		String owner="i";
		String username=d.getString("username");
		if(new UserDao().getAllUseri().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
			owner="i";
		}
		else if(new InstituteDao().getAllUserc().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("institute");
		}
		//Document d = tc.find(and(eq("username",username),eq("question",question.getQuestion()))).first();
		Document infodetails=(Document)d.get("info");
			ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
			ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
			
			if(down!=null){
				if(!down.contains(user)){
					if(up!=null && up.contains(user)){
						up.remove(user);
						 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
						 new GeneralServices().ratingupdate(owner, username);
					}
				down.add(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.DOWNVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;}
				else{
					down.remove(user);
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				down=new ArrayList<String>();
				down.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",new Long(down.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
				 
				 //String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,question.getQuestion(),q_id,user,Notifications.DOWNVOTESQUESTION);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}


	@SuppressWarnings("unchecked")
	public MultiUse upanswer(String id,String username,String s_id){
		Document ss=tcsession.find(eq("session_id",s_id)).first();
		String user = ss.getString("username");
		//username:whos answer
		//user upvote user
		ObjectId id1=new ObjectId(id.toString());
		MultiUse obj=new MultiUse();
		obj.setLoggedin(true);
		String owner="i";
		Document d = tc.find(eq("_id",id1)).first();
		
		if(new UserDao().getAllUseri().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
			owner="i";
		}
		else if(new InstituteDao().getAllUserc().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("institute");
			owner="c";
		}
		if(user.equals(d.getString("username")))
		tcuser.updateOne(eq("_id",id1),new Document("$push",new Document("question_solved",id)));
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
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,new Long(down.size()))));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
						 new GeneralServices().ratingupdate(owner, username);
					}
				up.add(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
				tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,new Long(up.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
		//public void voteNotification(String username,String pqname,String pqid,String commitername,Notifications notify)
		String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				   
				//new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.UPVOTESANSWER);
		obj.setUpvotes(up);
       	obj.setDownvotes(down);
		return obj;}
				else{
					up.remove(user);
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,new Long(up.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(down!=null && down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,new Long(down.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				up=new ArrayList<String>();
				up.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();	 
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,new Long(up.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.UPVOTESANSWER);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}

	@SuppressWarnings("unchecked")
	public MultiUse downanswer(String id,String username,String s_id){
		Document ss=tcsession.find(eq("session_id",s_id)).first();
		String user = ss.getString("username");
		//username:whos answer
		//user upvote user
		ObjectId id1=new ObjectId(id.toString());
		MultiUse obj=new MultiUse();
		obj.setLoggedin(true);
		String owner="i";
		Document d = tc.find(eq("_id",id1)).first();
		
		if(new UserDao().getAllUseri().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
			owner="i";
		}
		else if(new InstituteDao().getAllUserc().contains(username)){
			tcuser=new DatabaseServices().getDb().getCollection("institute");
			owner="c";
		}
		if(user.equals(d.getString("username")))
		tcuser.updateOne(eq("_id",id1),new Document("$push",new Document("question_solved",id)));
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
					if(up!=null &&up.contains(user)){
						up.remove(user);
						 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
						 tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,new Long(up.size()))));
						 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
						 new GeneralServices().ratingupdate(owner, username);
					}
				down.add(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
				tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,new Long(down.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
		//public void voteNotification(String username,String pqname,String pqid,String commitername,Notifications notify)
		String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				   
				//new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.UPVOTESANSWER);
		obj.setUpvotes(up);
       	obj.setDownvotes(down);
		return obj;}
				else{
					down.remove(user);
					String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,new Long(down.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			}
			else{
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(ups,up))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document(upc,new Long(up.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_upvote",-1)));
					 new GeneralServices().ratingupdate(owner, username);
				}
				down=new ArrayList<String>();
				down.add(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document(downs,down))).toString();	 
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document(downc,new Long(down.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("qa_downvote",1)));
				 new GeneralServices().ratingupdate(owner, username);
				 String q_id = tc.find(eq("_id",id1)).first().get("_id").toString();
				 //new NotificationService().voteNotification(username,answer.getQuestion(),q_id,user,Notifications.UPVOTESANSWER);
				 obj.setUpvotes(up);
		           	obj.setDownvotes(down);
					return obj;
				
			}
	}
	
	public Acknowledgement deleteQuestion(String s_id,String id){
		Document ss=tcsession.find(eq("session_id",s_id)).first();
		String user = ss.getString("username");
	    ObjectId oid = new ObjectId(id.toString());
	    Document document = tc.find(eq("_id",oid)).first();
	      if(document!=null && document.getString("username").equals(user)){
//			Document info=(Document) document.get("info");
//			  
//			  Document doc = new Document("username",user)
//		    		.append("question",document.getString("question"))
//		    		.append("description",document.getString("description"))
//		    		.append("tags",document.get("tags"))
//		    		.append("date",GeneralServices.getCurrentDate().getTime())
//		    		.append("featured_points", document.getLong("featured_points"))
//			  		.append("info",info)
//			  		.append("url",document.getString("url"))
//			  		.append("answers",document.get("answers"));
			  
			  
			  tc.deleteOne(eq("_id",oid));
			  System.out.println("deleted");
	      }
		return new GeneralServices().response(Notifications.SUCCESSFULLYDELETED);
		
	    
	}
	
	public Acknowledgement updateQuestion(String s_id,Question question,String id)
    {  //MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("testqa");
		Document ss=tcsession.find(eq("session_id",s_id)).first();
		String username = ss.getString("username");

       ObjectId oid = new ObjectId(id.toString());
       //Document doc2 = tc.find(and(eq("username",req.getSession().getAttribute("username").toString()),eq("question",question.getQuestion()))).first();
       Document document = tc.find(eq("_id",oid)).first();
       if(document.getString("username").equals(username)){
       ArrayList<Document> aldo = (ArrayList<Document>) document.get("answers");
      
        Document doc = new Document("username",username)
                .append("question",question.getQuestion())
                .append("description",question.getDescription())
                .append("tags",(List<String>)question.getTags())
                .append("date", document.getLong("date"))
                .append("last_updated", GeneralServices.getCurrentDate().getTime())
                .append("featured_points", question.getFeatured_points())
                .append("answers",aldo);
        tc.updateOne(eq("_id",oid),new Document("$set",doc));
       }
        return new GeneralServices().response(Notifications.SUCCESSFULLYUPDATED);
    }
    
    public Acknowledgement updateanswer(String s_id,Answer answer,String id){
    	Document ss=tcsession.find(eq("session_id",s_id)).first();
		String username = ss.getString("username");
         ObjectId oid = new ObjectId(id.toString());
         Document document = tc.find(eq("_id",oid)).first();
         ArrayList<Document> aldo = (ArrayList<Document>) document.get("answers");
         if(aldo != null&&answer.getUsername().equals(username)){
                for(Document d:aldo)
                {   
                	System.out.println(d);
                    if(d.getString("username").equals(answer.getUsername())){
                    	System.out.println("111");
                        tc.updateOne(eq("_id",oid), new Document("$pull",new Document("answers",d)));
                        break;
                    }
                }
            }
         Document info=new Document().append("upvotes",answer.getUpvotes()).append("downvotes",answer.getDownvotes());  
         Document doc = new Document("username",username)
                    .append("answer",answer.getAnswer())
                    .append("date",GeneralServices.getCurrentDate().getTime())
                    .append("last_updated",GeneralServices.getCurrentDate().getTime())
                    .append("info",info);
         tc.updateOne(eq("_id",oid),new Document("$push",new Document("answers",doc)));
         return new GeneralServices().response(Notifications.SUCCESSFULLYUPDATED);
    }
	
    public Acknowledgement deleteanswer(String s_id,String username,String id){
    	Document ss=tcsession.find(eq("session_id",s_id)).first();
		String user = ss.getString("username");
         ObjectId oid = new ObjectId(id.toString());
         Document document = tc.find(eq("_id",oid)).first();
         ArrayList<Document> aldo = (ArrayList<Document>) document.get("answers");
         if(aldo != null&&(document.getString("username").equals(user) || user.equals(username))){
                for(Document d:aldo)
                {   
                    if(d.getString("username").equals(username)){
                        tc.updateOne(eq("_id",oid), new Document("$pull",new Document("answers",d)));
                        return new GeneralServices().response(Notifications.SUCCESSFULLYDELETED);
                        //break;
                    }
                }
            }
         return new GeneralServices().response(Notifications.ERROR);
    }

	
	

}
