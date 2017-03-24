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
    MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("userdata");
	public void commentNotification(String username,String projectname,String commitername,String commitermsg,Notifications notify)
	{   projectname = new GeneralServices().spaceRemover(projectname);
        String suffixurl= "/project/retrieveselect/"+username+"/"+projectname;
		Document doc = new Document("date",new Date())
    		       .append("message",notify.getMsg())
    		       .append("generator",commitername)
    		       .append("messagegenerator",commitermsg)
    		       .append("url",prefixurl+suffixurl);
        tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	}
	
	public void answerNotification(String username,String question,String commitername,String commitermsg,Notifications notify)
	{   String suffixurl="/question/retrieve/"+username+"/"+question;
		Document doc = new Document("date",new Date())
 		       .append("message",notify.getMsg())
 		       .append("generator",commitername)
 		       .append("messagebygenerator",commitermsg)
 		       .append("url",prefixurl+suffixurl);
	     tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	}
	public void voteNotification(String username,String projectitle,Notifications notify)
	{
		String suffixurl="/retrieveselect/"+username+"/"+projectitle;
		Document doc = new Document("date",new Date())
 		       .append("message",notify.getMsg())
 		       .append("generator",username)
 		       .append("messagebygenerator","vote increased")
 		       .append("url",prefixurl+suffixurl);
	     tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
		
	}
	public ArrayList<NotificationBean> getAllNotifications(String username,String s_id)
	{
		 Document doc = tc.find(eq("username",username)).first();
	     ArrayList<Document> arnotify = (ArrayList<Document>) doc.get("notifications");
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
