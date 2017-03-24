package dao;

import org.bson.Document;

import bean.Acknowledgement;
import bean.Tag;
import bean.User;
import service.DatabaseServices;
import service.GeneralServices;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

public class UserDao {
	  MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testuserdata");
  	  public Acknowledgement insertUser(User user)
	  { Document test = tc.find(eq("username",user.getUsername())).first();
		  if(test==null)
		  { 
			  System.out.println(user.getGithub_id());
		  Document contact_information = new Document("phone_no",user.getPhone_no())
				  .append("email_id",user.getEmail_id())
				  .append("linkedin_id",user.getLinkedin_id())
				  .append("github_id",user.getGithub_id())
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
				  .append("favourite_tags",user.getFavourite_tags()); 
		  tc.insertOne(doc);
		  return new GeneralServices().response("insert");
		  }
		  else{
			  return new GeneralServices().response(null);
		  }
	  }
	@SuppressWarnings("unchecked")
	public User getUserDetails(String username)
      { 
    	  User user = new User();
    	  FindIterable <Document> fi = tc.find(eq("username",username));
    	  for(Document d : fi)
    	  {
              user.setBio(d.getString("bio"));
              user.setName(d.getString("name"));
              user.setUsername(d.getString("username"));
    	      user.setContributing((ArrayList<String>)d.get("contributing"));
    	      user.setFollowers((ArrayList<String>)d.get("followers"));
    	      user.setFollowing((ArrayList<String>)d.get("following"));
    	      user.setFavourite_tags((ArrayList<String>)d.get("favourite_tags"));
              Document innerdoc = (Document) d.get("contact_information");
              user.setCity(innerdoc.getString("city"));
              user.setCountry(innerdoc.getString("country"));
    	      user.setState(innerdoc.getString("state"));
    	      user.setEmail_id(innerdoc.getString("email_id"));
    	      user.setPhone_no(innerdoc.getString("phone_no"));
    	      user.setZipcode(innerdoc.getLong("zipcode"));
    	      user.setLinkedin_id(innerdoc.getString("linkedin_id"));
    	      user.setGithub_id(innerdoc.getString("github_id"));
              innerdoc = (Document) d.get("history");
              try{
              user.setTags_viewed((ArrayList<String>)innerdoc.get("tags_viewed"));
              user.setProblem_category_viewed((ArrayList<String>)innerdoc.get("problem_category_viewed"));
              user.setUser_viewed((ArrayList<String>)innerdoc.get("user_viewed"));
              user.setProject_viewed((ArrayList<String>)innerdoc.get("project_viewed"));}
              catch(NullPointerException e){
            	  
              }
              //System.out.println(d);
    	  } 
    	  return user;
      }
      
      public Acknowledgement updateUserDetails(User user,String username)
      {   System.out.println(user.getBio()+"this is bip");
    	  Document outdoc = new Document("name",user.getName())
        		  .append("followers",user.getFollowers())
        		  .append("following",user.getFollowing())
        		  .append("favourite_tags",user.getFavourite_tags())
        		  .append("contributing",user.getContributing())
        		  .append("bio",user.getBio());
                   tc.updateOne(eq("username", user.getUsername()),new Document("$set",outdoc));   	
          Document doc=new Document("phone_no",user.getPhone_no())
        		  .append("email_id",user.getEmail_id())
    	          .append("country",user.getCountry())
    	          .append("city",user.getCity())
    	          .append("linkedin_id", user.getLinkedin_id())
    	          .append("github_id",user.getGithub_id())
    	          .append("zipcode",user.getZipcode())
    	          .append("state",user.getState());
        String acknow2= tc.updateOne(eq("username",user.getUsername()),new Document("$set",new Document("contact_information",doc))).toString();
           return 	new GeneralServices().response(acknow2);
      }
      
      @SuppressWarnings("unchecked")
	public ArrayList<User> getAllUsers()
      {
    	  ArrayList<User> aluser = new ArrayList<User>();
    	  
    	  tc.find().forEach((Block<Document>)doc -> {
    		  User user = new User();
    		  user.setName(doc.getString("name"));
    		  user.setUsername(doc.getString("username"));
    	  aluser.add(user);
    	  });
    	  return aluser;
      }
	
      
     // Database Getter and Setter 
      public User getUserRating(String username)
      {
    	  User user = new User();
    	  FindIterable <Document> fi = tc.find(eq("username",username));
    	  System.out.println(username);
    	  for(Document d : fi){
    		  user.setRating(d.getLong("rating"));
    	  }
    	  return user;
      }
      
      public Acknowledgement setUserRating(User user, String username)
      {
    	  Acknowledgement ac2 = new Acknowledgement();
    	  Document doc = new Document()
    			  		.append("rating",user.getRating());
    	  String acknow1 = tc.updateOne(eq("username", user.getUsername()),new Document("$set",doc)).toString();
    	  Acknowledgement acknowledge1 = new GeneralServices().stoacknowmethod(s ->{
              String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
                 ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
              return ac2;}, acknow1);
    	  return acknowledge1;
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

}
