package dao;

import org.bson.Document;

import bean.Acknowledgement;
import bean.User;
import service.DatabaseServices;
import service.GeneralServices;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;

public class UserDao {
	 // MongoClientURI uri  = new MongoClientURI("mongodb://sjsidjain:sjsidjain@ds145359.mlab.com:45359/testhack"); 
      //MongoClient client = new MongoClient(uri);
      //MongoDatabase db = client.getDatabase(uri.getDatabase());
      // collection userdata is not for testing, for testing purpose chnage collection name to testcol
      //MongoCollection <Document> tc = db.getCollection("userdata");
      @SuppressWarnings("unchecked")
	public User getUserDetails(String username)
      { 
    	// Given below connection is established with help of mongo pool connection 
    	// send request to this method iteratively for 3 or 4 time and then comment it and again send request to this method   
        // you will feel the difference between the amount of time taken you can also observe console for that
    	  MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("userdata");
    	  User user = new User();
    	  FindIterable <Document> fi = tc.find(eq("username",username));
    	//System.out.println("enter");
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
    	      user.setZipcode(String.valueOf(innerdoc.get("zipcode")));
    	      user.setLinkedin_id(innerdoc.getString("linkedin_id"));
    	      user.setGithub_id(innerdoc.getString("github_id"));
          //System.out.println(d);
    	  } 
    	  return user;
      }
      
      public ArrayList<Acknowledgement> updateUserDetails(User user)
      { // this collection is for testing purpose uncomment this for doing test
         //MongoCollection <Document> tc = db.getCollection("testcol");
	  MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testcol");
    	  Acknowledgement ac2 = new Acknowledgement();
          ArrayList<Acknowledgement> alacknow = new ArrayList<Acknowledgement>();
    	  
    	  Document outdoc = new Document("name",user.getName())
        		  .append("followers",user.getFollowers())
        		  .append("following",user.getFollowing())
        		  .append("favourite_tags",user.getFavourite_tags())
        		  .append("contributing",user.getContributing())
        		  .append("bio",user.getBio());
          String acknow1 = tc.updateOne(eq("username", user.getUsername()),new Document("$set",outdoc)).toString();   	
          Document doc=new Document("phone_no",user.getPhone_no())
        		  .append("email_id",user.getEmail_id())
    	          .append("country",user.getCountry())
    	          .append("city",user.getCity())
    	          .append("linkedin_id", user.getLinkedin_id())
    	          .append("github_id",user.getGithub_id())
    	          .append("zipcode",user.getZipcode())
    	          .append("state",user.getState());
    	 String acknow2= tc.updateOne(eq("username",user.getUsername()),new Document("$set",new Document("contact_information",doc))).toString();
    	 
    	 Acknowledgement acknowledge1 = new GeneralServices().stoacknowmethod(s ->{
             String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
                ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
             return ac2;}, acknow1);

    	 Acknowledgement acknowledge2 = new GeneralServices().stoacknowmethod(s ->{
             String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
                ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
             return ac2;}, acknow2);
        alacknow.add(acknowledge1);alacknow.add(acknowledge2);
        
    	 //client.close();
         return alacknow;
      }
}
