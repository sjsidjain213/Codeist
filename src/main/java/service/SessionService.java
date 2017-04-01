package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	/**
	 * @param req
	 * @param response
	 * @param username
	 * @param password
	 * @return
	 */
	public MultiUse sessionCreate(HttpServletRequest req,HttpServletResponse response,String username,String password)
	{    System.out.println(username);
		Document doc = tc.find(eq("username",username)).first();
	MultiUse user = new MultiUse();
	System.out.println(GeneralServices.get_SHA_256_SecurePassword(username, password)+"::"+req.getSession().getAttribute("username")+"::"+doc.getString("session_id"));
	if (tc.find(eq("username",username)).first()!=null&&GeneralServices.get_SHA_256_SecurePassword(username, password).equals(doc.getString("password")))
	{ System.out.println("user exsit and password match");
		if(req.getSession().getAttribute("username")==null&&doc.getString("session _id")==null)
		{
			//create session
		req.getSession().setAttribute("username", username);
	    String s_id =	req.getSession().getId();
	    System.out.println("S-id"+s_id);
	    System.out.println("new session"+tc.updateOne(eq("username", username),new Document("$set",new Document("session_id",s_id))));
		req.getSession().setAttribute("s_id",s_id);
		user.setIs_valid(true);
		user.setSession_id(s_id);
		user.setUsername(username);
		return user;
		}else if(doc.getString("session_id")!=null)
		{
		req.getSession().setAttribute("username", username);
		String s_id =	req.getSession().getId();
		System.out.println(tc.updateOne(eq("username", username),new Document("$set",new Document("session_id",s_id))));
		req.getSession().setAttribute("s_id",s_id);
		System.out.println("S-id"+s_id);
		user.setIs_valid(true);
		user.setSession_id(s_id);
		user.setUsername(username);
		user.setMessage("new session is generated");
		return user;
		}

	}
	user.setMessage("invalid users");
	return user;
		}
	
	public boolean tokenVerifier(String auth_token,HttpServletRequest req,HttpServletResponse response)
	{String s_token =null;
	if(req.getSession().getAttribute("s_id")!=null)
		{System.out.println("sessio id present"+"::"+req.getSession().getAttribute("s_id")); 
		s_token = req.getSession().getAttribute("s_id").toString();
        if(req.getSession().getAttribute("s_id")!=null&&s_token.equals(auth_token))
        {//response.setHeader("logged_in", "true");
        	System.out.println("okay");
       
        	return true;
        }
    	System.out.println("notokay");
        return false;
		}else{        	System.out.println("verunotokay");
        	return false;
        }
	}
	
	public boolean sessionVerifier(HttpServletRequest req)
	{
		
		System.out.println(req.getSession().getAttribute("username")+"::"+req.getSession().getAttribute("s_id"));
    return (req.getSession().getAttribute("username")==null&&req.getSession().getAttribute("s_id")==null)?false:true;
	}
    
	public Acknowledgement sessionDestroy(HttpServletRequest req)
	{
		System.out.println(req.getSession().getAttribute("username")+"**"+req.getSession().getAttribute("s_id"));
		String username=req.getSession().getAttribute("username").toString();
	if(req.getSession().getAttribute("username")!=null&&req.getSession().getAttribute("s_id")!=null)	
		{req.getSession().removeAttribute("username");
	    req.getSession().removeAttribute("s_id");
	    System.out.println("inavlid");
	    tc.updateOne(eq("username",username),new Document("$set",new Document("session_id",null)));
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
