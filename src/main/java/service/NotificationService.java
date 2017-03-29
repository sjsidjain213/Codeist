package service;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.MongoCollection;

import bean.NotificationBean;
import bean.Notifications;
//details1 -> commitername
//details2 -> commitermsg 
public class NotificationService {
    String prefixurl= "http://localhost:8080/Codeist";
    MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("testuserdata");
	
    public void updateProjectNotification(String username,String projectname,String project_id,String commitername,String commitermsg,Notifications notify)
    {
    	projectname  = new GeneralServices().spaceRemover(projectname);
    	
    	
    }
    
    public void commentNotification(String username,String projectname,String project_id,String commitername,String commitermsg,Notifications notify)
	{   projectname = new GeneralServices().spaceRemover(projectname);
        String suffixurl= "/projects/"+project_id;
		Document doc = new Document("date",GeneralServices.getCurrentDate())
    		       .append("message",notify.getMsg())
    		       .append("generator",commitername)
    		       .append("messagegenerator",commitermsg)
    		       .append("url",GeneralServices.urlGenerator(Notifications.PROJECTMODULE,project_id,projectname));
        tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	}
	
	public void answerNotification(String username,String question,String question_id,String commitername,String commitermsg,Notifications notify)
	{   Document doc = new Document("date",GeneralServices.getCurrentDate())
 		       .append("message",notify.getMsg())
 		       .append("generator",commitername)
 		       .append("messagebygenerator",commitermsg)
 		       .append("url",GeneralServices.urlGenerator(Notifications.QUESTIONMODULE,question_id,question));
	     tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	}
	//username string id will have value of either question id or project id
	public void voteNotification(String username,String pqname,String pqid,String commitername,Notifications notify)
	{
        if(notify==Notifications.UPVOTESQUESTION||notify==Notifications.DOWNVOTESQUESTION)
        {
    	Document doc = new Document("date",GeneralServices.getCurrentDate())
 		       .append("message",notify.getMsg())
 		       .append("generator",commitername)
 		       .append("messagebygenerator",notify.getMsg())
 		       .append("url",GeneralServices.urlGenerator(Notifications.QUESTIONMODULE,pqid,pqname));
	     tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	    	
        }else if(notify==Notifications.UPVOTESPROJECT||notify==Notifications.DOWNVOTESPROJECT)
        {
        	Document doc = new Document("date",GeneralServices.getCurrentDate())
      		       .append("message",notify.getMsg())
      		       .append("generator",username)
      		       .append("messagebygenerator",notify.getMsg())
      		       .append("url",GeneralServices.urlGenerator(Notifications.PROJECTMODULE,pqid,pqname));
      		 tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
        }else if(notify==Notifications.UPVOTESANSWER||notify==Notifications.DOWNVOTESANSWER)
        {
        	Document doc = new Document("date",GeneralServices.getCurrentDate())
      		       .append("message",notify.getMsg())
      		       .append("generator",username)
      		       .append("messagebygenerator",notify.getMsg())
      		       .append("url",GeneralServices.urlGenerator(Notifications.QUESTIONMODULE,pqid,pqname));
      		 tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
        }
	}
	public ArrayList<NotificationBean> getAllNotifications(String username,String s_id)
	{
		 Document doc = tc.find(eq("username",username)).first();
		 ArrayList<Document> arnotify=null;
	     try{
	    	 arnotify = (ArrayList<Document>) doc.get("notifications");
	     }
	     catch(NullPointerException e){ 
	     }
	     ArrayList<NotificationBean> aldoc = new ArrayList<NotificationBean>();
	     /*ArrayList<ArrayList<String>> day = new ArrayList<ArrayList<String>>();
	     day.forEach(hour->hour.add("coding"));*/
	     NotificationBean notify = new NotificationBean();
	     if(arnotify!=null){
	     for(Document din : arnotify)
	    {
	     notify.setDate(din.getDate("date"));
	     notify.setGenerator(din.getString("generator"));
	     notify.setMessagebygenerator(din.getString("messagebygenerator"));
	     notify.setMessage(din.getString("message"));
	     notify.setUrl(din.getString("url"));
	     notify.setS_id(s_id);
	     aldoc.add(notify); 
	    }}
	     else{
	    	  notify.setS_id(s_id);
	 	      aldoc.add(notify);  
	         }
	    return aldoc;
	    }
	
}
