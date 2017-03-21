package service;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.NotificationBean;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
public class SessionService {
MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("userdata");
	public ArrayList<NotificationBean> sessionCreate(HttpServletRequest req,String username)
	{
		Document doc = tc.find(eq("username",username)).first();
	if(doc!=null)
	{	req.getSession().setAttribute("username",username);
	return new NotificationService().getAllNotifications(username);
	}
	else 
		return null;
	}
	
	public boolean sessionVerifier(HttpServletRequest req)
	{
    return (req.getSession().getAttribute("username")==null)?false:true;
	}
    
	public void sessionDestroy(HttpServletRequest req)
	{
		req.getSession().removeAttribute("username");
	}
}
