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
	public void commentNotification(String username,String projectname,String commitername,String commitermsg,Notifications notify)
	{
        String suffixurl= "/project/retrieveselect/"+username+"/"+projectname;
		Document doc = new Document("date",new Date())
    		       .append("message",notify.getMsg())
    		       .append("detail1",commitername)
    		       .append("details2",commitermsg)
    		       .append("url",prefixurl+suffixurl);
        tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	}
	
	public void answerNotification(String username,String question,String commitername,String commitermsg,Notifications notify)
	{   String suffixurl="/question/retrieve/"+username+"/"+question;
		Document doc = new Document("date",new Date())
 		       .append("message",notify.getMsg())
 		       .append("detail1",commitername)
 		       .append("details2",commitermsg)
 		       .append("url",prefixurl+suffixurl);
	     tc.updateOne(eq("username",username),new Document("$addToSet",new Document("notifications",doc)));
	}
	
	public ArrayList<NotificationBean> getAllNotifications(String username)
	{
		Document doc = tc.find(eq("username",username)).first();
	    ArrayList<Document> arnotify = (ArrayList<Document>) doc.get("notifications");
	    ArrayList<NotificationBean> aldoc = new ArrayList<NotificationBean>();
	    for(Document din : arnotify)
	    {NotificationBean notify = new NotificationBean();
	     notify.setDate(din.getDate("date"));
	     notify.setDetail1(din.getString("detail1"));
	     notify.setDetail2(din.getString("detail2"));
	     notify.setMessage(din.getString("message"));
	     notify.setUrl(din.getString("url"));
	     aldoc.add(notify); 
	    }
	    return aldoc;
	    }
}
