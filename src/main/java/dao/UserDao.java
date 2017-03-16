package dao;

import org.bson.Document;
import bean.User;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

public class UserDao {
	  MongoClientURI uri  = new MongoClientURI("mongodb://sjsidjain:sjsidjain@ds145359.mlab.com:45359/testhack"); 
      MongoClient client = new MongoClient(uri);
      MongoDatabase db = client.getDatabase(uri.getDatabase());
      MongoCollection <Document> tc = db.getCollection("testcol");
      public User getUserDetails(String username)
      {   User user = new User();
    	  FindIterable <Document> fi = tc.find(eq("username",username));
    	  for(Document d : fi)
    	  {
              user.setBio(d.getString("Bio"));
              user.setName(d.getString("Name"));
              user.setUsername(d.getString("username"));
              
              //nested document access
              Document innerdoc = (Document) d.get("Contact_Information");
              user.setCity(innerdoc.getString("City"));
              user.setCountry(innerdoc.getString("Country"));
    	      user.setState(innerdoc.getString("State"));
    	      user.setEmailid(innerdoc.getString("Email id"));
    	      user.setPhoneno(innerdoc.getString("Phone no"));
    	      user.setZipcode(String.valueOf(innerdoc.get("Zipcode")));
    	  } 
    	  return user;
      }
      
      public void updateUserDetails(User user)
      {  Document outdoc = new Document("Name",user.getName())
    			  .append("Bio",user.getBio());
        String result = tc.updateOne(eq("username", user.getUsername()),new Document("$set",outdoc)).toString();   	
          Document doc=new Document("Phone no",user.getPhoneno());
    	  doc.append("Email id",user.getEmailid());
    	  doc.append("Country",user.getCountry());
    	  doc.append("City",user.getCity());
    	  doc.append("State",user.getState());
    	 String result1= tc.updateOne(eq("username",user.getUsername()),new Document("$set",new Document("Contact_Information",doc))).toString();
         //Desing a function to convert string into json using either beans or jsonparser and jsonobject
      }
}
