package dao;


import org.bson.Document;

import bean.Acknowledgement;
import bean.MultiUse;
import bean.Notifications;
import bean.Tag;
import bean.User;
import service.DatabaseServices;
import service.GeneralServices;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class UserDao {
	
	
	  MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testuserdata");
	
	  public void signupUser(String username,String password,String emailid,Date date)
	  {MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("unverifieduserdata");
	   Document doc = new Document("username",username)
				  .append("password",password)
				  .append("emailid",emailid)
				  .append("date",date)
				  .append("verified","n");
		           tc.insertOne(doc);
	  }
	  public String userVerifier(String username,String emailid,Date date,HttpServletResponse response)
	  {MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("unverifieduserdata");
	  System.out.println(date+"<< data");System.out.println(emailid);System.out.println(username);
	  Document doc= tc.find(and(eq("username",username),eq("emailid",emailid),eq("date",date))).first();
		 if(doc!=null)
		 {   String password = tc.find(and(eq("username",username),eq("emailid",emailid),eq("date",date))).first().getString("password");
		    	new UserDao().insertNewUser(username,emailid,date,password,doc);
		   // 	response.sendRedirect("localhost");
			 return "verified";
		 }
		 else{
			 return "unverified";
		 }
	  }
	  public void insertNewUser(String username,String emailid,Date date,String password,Document unverified){

		  Document unverify  = new Document("username",username)
				  .append("emailid", emailid)
				  .append("verified","n")
				  .append("date", date)
				  .append(password, password);
		  
		  Document contact_information = new Document("phone_no","")
				  .append("email_id",emailid)
				  .append("country", "")
				  .append("state","")
				  .append("city","")
				  .append("zipcode", new Long(0));
		  
		  Document history = new Document("tag_view",new ArrayList<String>())
				              .append("problem_category_view",new ArrayList<String>())
				              .append("project_view",new ArrayList<String>())
				              .append("user_view",new ArrayList<String>());

		  Document doc = new Document("name","")
					  .append("password",password)
					  .append("username",username)
					  .append("bio","")
					  .append("date",date.getTime())
					  .append("profile_url","")
					  .append("gender","")
					  .append("category","")
					  .append("institute","")
					  .append("following",new ArrayList<String>())
					  .append("follower", new ArrayList<String>())
					  .append("contributing",new ArrayList<String>())
					  .append("project_id", new ArrayList<String>())
					  .append("project_bookmark", new ArrayList<String>())
					  .append("question_bookmark", new ArrayList<String>())
					  .append("rating",new Long(0))
					  .append("project_upvote",new Long(0))
					  .append("project_downvote",new Long(0))
					  .append("qa_upvote",new Long(0))
					  .append("qa_downvote",new Long(0))
					  .append("question_ask", new ArrayList<String>())
					  .append("question_answer", new ArrayList<String>())
					  .append("contact_information",contact_information)
					  .append("history",history)
					  .append("favourite_tag",new ArrayList<String>()); 
                  		  tc.insertOne(doc);
      MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("unverifieduserdata");
	  tc.deleteOne(unverify);
	  }
	  
	  public Acknowledgement updateUser(User user)
	  { Document test = tc.find(eq("username",user.getUsername())).first();
		  if(test==null)
		  { 
			  Document contact_information = new Document("phone_no",user.getPhone_no())
					  .append("email_id",user.getEmail_id())
					  .append("country", user.getCountry().toLowerCase())
					  .append("state",user.getState().toLowerCase())
					  .append("city",user.getCity())
					  .append("zipcode", Long.valueOf(user.getZipcode()));
			  
		    	  Document history = new Document("tag_view",new ArrayList<String>())
					              .append("problem_category_view",new ArrayList<String>())
					              .append("project_view",new ArrayList<String>())
					              .append("user_view",new ArrayList<String>());
String profile_url="";
if(user.getProfile_url()!=""){profile_url=user.getProfile_url();}
profile_url = (user.getGender().equals("m")&&user.getProfile_url()=="")?"https://s3-us-west-2.amazonaws.com/codeist/male_user.png":"https://s3-us-west-2.amazonaws.com/codeist/female_user.png"; 	  
		    	  
                  Document doc = new Document("username",user.getUsername())
				  .append("password",GeneralServices.get_SHA_256_SecurePassword(user.getUsername(),user.getPassword()))
				  .append("name",user.getName())
				  .append("bio",user.getBio())
				  .append("date",GeneralServices.getCurrentDate().getTime())
				  .append("gender",user.getGender())
			      .append("profile_url",profile_url)
				  .append("category",user.getCategory())
				  .append("institute",user.getInstitute())
				  .append("following",new ArrayList<String>())
				  .append("follower", new ArrayList<String>())
				  .append("contributing",new ArrayList<String>())
				  .append("project_id", new ArrayList<String>())
				  .append("project_bookmark", new ArrayList<String>())
				  .append("question_bookmark", new ArrayList<String>())
				  .append("rating",new Long(0))
				  .append("question_ask", new ArrayList<String>())
				  .append("question_answer", new ArrayList<String>())
				  .append("contact_information",contact_information)
				  .append("history",history)
				  .append("favourite_tag",user.getFavourite_tag()); 
		  tc.insertOne(doc);
		  return new GeneralServices().response(Notifications.SUCCESSFULLYINSERTED);
		  }
		  else{
			  return new GeneralServices().response(null);
		  }
	  }
	@SuppressWarnings("unchecked")
	public User getUserDetails(String username)
      { //username from session
    	System.out.println("received");
		User user = new User();
    	  FindIterable <Document> fi = tc.find(eq("username",username));
    	
    	  for(Document d : fi)
    	  {
              user.setBio(d.getString("bio"));
              user.setName(d.getString("name"));
              user.setUsername(d.getString("username"));
              user.setGender(d.getString("gender"));
              user.setCategory(d.getString("category"));
              user.setInstitute(d.getString("institute"));
    	      user.setContributing((ArrayList<String>)d.get("contributing"));
    	      user.setFollower((ArrayList<String>)d.get("followers"));
    	      user.setFollowing((ArrayList<String>)d.get("following"));
    	      user.setFavourite_tag((ArrayList<String>)d.get("favourite_tags"));
              Document innerdoc = (Document) d.get("contact_information");
              user.setCity(innerdoc.getString("city"));
              user.setCountry(innerdoc.getString("country"));
    	      user.setState(innerdoc.getString("state"));
    	      user.setEmail_id(innerdoc.getString("email_id"));
    	      user.setPhone_no(innerdoc.getString("phone_no"));
    	      user.setZipcode(innerdoc.getLong("zipcode"));
    	      innerdoc = (Document) d.get("history");
              try{
              user.setTags_view((ArrayList<String>)innerdoc.get("tags_viewed"));
              user.setProblem_category_view((ArrayList<String>)innerdoc.get("problem_category_viewed"));
              user.setUser_view((ArrayList<String>)innerdoc.get("user_viewed"));
              user.setProject_view((ArrayList<String>)innerdoc.get("project_viewed"));}
              catch(NullPointerException e){
            	System.out.println(e.getMessage()+"error");  
              }
              //System.out.println(d);
    	  } 
    	  return user;
      }
      
      public Acknowledgement updateUserDetails(User user,String username)
      {   System.out.println(user.getBio()+"this is bip");
    	  Document outdoc = new Document("name",user.getName())
    			  .append("gender", user.getGender())
    			  .append("category",user.getCategory())
    			  .append("institute", user.getInstitute())
        		  .append("followers",user.getFollower())
        		  .append("following",user.getFollowing())
        		  .append("favourite_tags",user.getFavourite_tag())
        		  
        		  .append("contributing",user.getContributing())
        		  .append("bio",user.getBio());
                   tc.updateOne(eq("username", user.getUsername()),new Document("$set",outdoc));   	
          Document doc=new Document("phone_no",user.getPhone_no())
        		  .append("email_id",user.getEmail_id())
    	          .append("country",user.getCountry())
    	          .append("city",user.getCity())
    	          .append("zipcode",Long.valueOf(user.getZipcode()))
    	          .append("state",user.getState());
tc.updateOne(eq("username",user.getUsername()),new Document("$set",new Document("contact_information",doc)));
           return 	new GeneralServices().response(Notifications.SUCCESSFULLYINSERTED);
      }
      
      public void moduleIDAdder(Notifications notify,String username,String variableid)
  	   {
        if(notify.equals(Notifications.QUESTIONMODULE))
        {
       	  tc.updateOne(eq("username",username),new Document("$addToSet",new Document("question_ask",variableid)));
        }
        else if(notify.equals(Notifications.PROJECTMODULE))
        {
       	  tc.updateOne(eq("username",username),new Document("$addToSet",new Document("project_ask",variableid)));
        }
  	   }

      
      public Acknowledgement updateFavTags(Tag favTags,String username){
          tc.updateOne(eq("username",username),new Document("$set", new Document("favourite_tags",favTags.getTags())));
    //??????????????????????
    	  Acknowledgement acknowledge1 = new GeneralServices().response(Notifications.SUCCESSFULLYINSERTED);
    	  return acknowledge1;
      }
      public Acknowledgement updateTagsViewed(String username,Tag tagsViewed){
    	  MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testcol");
    	  String acknow = null;
    	  Acknowledgement ac2 = new Acknowledgement();
    	  for(String i:tagsViewed.getTags())
    	  { acknow = tc.updateOne(eq("username",username),new Document("$addToSet",new Document("history.tags_viewed",i))).toString();
    	  }
    	  Acknowledgement acknowledge1 = new GeneralServices().stoacknowmethod(s ->{
              String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
                 ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
              return ac2;}, acknow);
    	  return acknowledge1;
      }
      
      public void ratingUpdate(String username,Notifications notify)
      {
      long changerating = Long.valueOf(notify.getMsg());
      long currentrating = tc.find(eq("username",username)).first().getLong("rating");
      currentrating = currentrating+changerating;
      if(currentrating<0)
      tc.updateOne(eq("username",username),new Document("$set",new Document("rating",currentrating)));
      }
      
      public Long rating(String username){
    	  Document document=tc.find(eq("username",username)).first();
    	  Long l=(document.getLong("project_upvote")*20)+(document.getLong("project_downvote")*(-10))+(document.getLong("qa_upvote")*10)+(document.getLong("qa_upvote")*(-5));
		return l;
      }
      
      public ArrayList<MultiUse> getAllUser()
      {
    	 FindIterable <Document> fi =  tc.find();
        ArrayList <MultiUse> alluser =  new ArrayList<MultiUse>();
    	 for(Document doc : fi)
        {	 MultiUse use = new MultiUse();
             use.setUsername(doc.getString("username"));
             use.setEmailid(doc.getString("email_id"));  	
        alluser.add(use);
        }
    	 return alluser;
    	}
 
          public String check(String username,String emailid){
    		           UserDao obj1=new UserDao();
    		           ArrayList<MultiUse> obj=obj1.getAllUser();
    		           for(MultiUse a:obj){
    		               if(username.equals(a.getUsername())){
    		                   return "username present";
    		               }else if(emailid.equals(a.getEmailid()))
    		               {
    		            	   return "emailid present";
    		               }
    		               
    		           }
    		           return "success";
    		       }
          
          public ArrayList<String> getAllUseri()
          {
          	 FindIterable <Document> fi =  tc.find();
            ArrayList <String> alluser =  new ArrayList<String>();
          	 for(Document doc : fi)
            {		
            alluser.add(doc.getString("username"));
            System.out.println(doc.getString("username"));
            }
          	 return alluser;
          	}
}
