package dao;

import org.bson.Document;

import bean.Acknowledgement;
import bean.Notifications;
import bean.Project;
import bean.Signup;
import bean.Tag;
import bean.User;
import service.DatabaseServices;
import service.GeneralServices;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserDao {
	
	
	  MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testuserdata");
	  public void signupUser(String name,String password,String emailid,Date date)
	  {MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("unverifieduserdata");
	    Document doc = new Document("name",name)
				  .append("password", password)
				  .append("emailid",emailid)
				  .append("date",date)
				  .append("verified", "n");
		 tc.insertOne(doc);
	  }
	  public String userVerifier(String name,String email,Date date)
	  {MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("unverifieduserdata");
	  System.out.println(date+"<< data");System.out.println(email);System.out.println(name);
	  Document doc= tc.find(and(eq("name",name),eq("emailid",email),eq("date",date))).first();
		 if(doc!=null)
		 {
			 return "verified";
		 }
		 else{
			 return "unverified";
		 }
	  }
	  public Acknowledgement insertUser(User user)
	  { Document test = tc.find(eq("username",user.getUsername())).first();
		  if(test==null)
		  { 

		  Document contact_information = new Document("phone_no",user.getPhone_no())
				  .append("email_id",user.getEmail_id())
				  .append("country", user.getCountry().toLowerCase())
				  .append("state",user.getState().toLowerCase())
				  .append("city",user.getCity())
				  .append("zipcode", user.getZipcode());
		  Document doc = new Document("username",user.getUsername())
				  .append("password",GeneralServices.get_SHA_256_SecurePassword(user.getUsername(),user.getPassword()))
				  .append("name",user.getName())
				  .append("bio",user.getBio())
				  .append("date",user.getDate())
				  //.append("rating",user.getRating())
				  .append("contact_information",contact_information)
				  .append("favourite_tags",user.getFavourite_tag()); 
		  tc.insertOne(doc);
		  return new GeneralServices().response("insert");
		  }
		  else{
			  return new GeneralServices().response(null);
		  }
	  }
	@SuppressWarnings("unchecked")
	public User getUserDetails(String username)
      { //username from session
    	  User user = new User();
    	  FindIterable <Document> fi = tc.find(eq("username",username));
    	  for(Document d : fi)
    	  {
              user.setBio(d.getString("bio"));
              user.setName(d.getString("name"));
              user.setUsername(d.getString("username"));
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
            	System.out.println(e.getMessage());  
              }
              //System.out.println(d);
    	  } 
    	  return user;
      }
      
      public Acknowledgement updateUserDetails(User user,String username)
      {   System.out.println(user.getBio()+"this is bip");
    	  Document outdoc = new Document("name",user.getName())
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
    	          .append("zipcode",user.getZipcode())
    	          .append("state",user.getState());
        String acknow2= tc.updateOne(eq("username",user.getUsername()),new Document("$set",new Document("contact_information",doc))).toString();
           return 	new GeneralServices().response(acknow2);
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
    	  String acknow= tc.updateOne(eq("username",username),new Document("$set", new Document("favourite_tags",favTags.getTags()))).toString();
    	  Acknowledgement acknowledge1 = new GeneralServices().response(acknow);
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
}
