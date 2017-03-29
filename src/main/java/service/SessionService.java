package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.MultiUse;
import bean.NotificationBean;
import bean.Notifications;
import bean.User;

import static com.mongodb.client.model.Filters.*;

import java.io.IOException;
import java.util.ArrayList;
public class SessionService {
MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("userdata");

	public MultiUse sessionCreate(HttpServletRequest req,HttpServletResponse response,String username,String password)
	{    System.out.println(username);
		Document doc = tc.find(eq("username",username)).first();		
		if(req.getSession().getAttribute("username")==null&&doc!=null &&(GeneralServices.get_SHA_256_SecurePassword(username, password)).equals(doc.getString("password")))
	{	req.getSession().setAttribute("username",username);
	    String s_id = req.getSession().getId();
	    req.getSession().setAttribute("s_id",s_id);
	    System.out.println(username+"::"+s_id);
	    MultiUse user = new MultiUse();
	    user.setUsername(username);
	    return user;
	}
		else if(req.getSession().getAttribute("username")!=null&&req.getSession().getAttribute("username").toString().equals(username)){
		    MultiUse user = new MultiUse();
		    user.setUsername(username);
		    return user;
		}
	else {
		ArrayList<NotificationBean> nb = new ArrayList<NotificationBean>();
		NotificationBean notify = new NotificationBean();
		notify.setMsg(Notifications.USERISNOTLOGGEDIN.getMsg()+"or"+ Notifications.SESSIONISALREADYACTIVE.getMsg());
		notify.setS_id(Notifications.SESSIONDONOTEXSIT.getMsg());
		nb.add(notify);
	    MultiUse user = new MultiUse();
	    user.setMessage("invalid username or password");
	    return user;
	}
		}
	
	public boolean sessionVerifier(HttpServletRequest req)
	{
		System.out.println(req.getSession().getAttribute("username")+"::"+req.getSession().getAttribute("s_id"));
    return (req.getSession().getAttribute("username")==null&&req.getSession().getAttribute("s_id")==null)?false:true;
	}
    
	public Acknowledgement sessionDestroy(HttpServletRequest req)
	{
	if(req.getSession().getAttribute("username")!=null&&req.getSession().getAttribute("s_id")!=null)	
		{req.getSession().removeAttribute("username");
	    req.getSession().removeAttribute("s_id");
	    Acknowledgement acknow = new Acknowledgement();
        acknow.setMessage(Notifications.USERLOGGEDOUT.getMsg());
	    return acknow; 
		}
	else{
		 Acknowledgement acknow = new Acknowledgement();
	        acknow.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
		    return acknow; 
	}
	
	}
}
