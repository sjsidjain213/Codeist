package dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import bean.Project;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class ProjectInsert {
	 private MongoClientURI uri  = new MongoClientURI("mongodb://sjsidjain:sjsidjain@ds145359.mlab.com:45359/testhack"); 
     private MongoClient client = new MongoClient(uri);
     private MongoDatabase db = client.getDatabase(uri.getDatabase());
     private MongoCollection <Document> tc = db.getCollection("testcol");

public String insertProject(Project project,String username)
{ 
	Document doc = new Document()
	    	  .append("title",project.getTitle())		  
	    	  .append("description",project.getDescription())
	    	  .append("project_url",project.getURL())
	    	  .append("tags",project.getTags())
	    	  .append("likes",project.getLike())
	    	  .append("Comments",project.getComment())
	    	  .append("Contributors",project.getContibutor())
	    	  .append("README", project.getReadme())
	    	  .append("private", project.getAccess())
	    	  .append("zip_file",project.getZipfile())
	    	  .append("images",project.getImages());
UpdateResult ur =  tc.updateOne(eq("username","sjsidjain"),new Document("$addToSet",new Document("Projects",doc)));	
return ur.toString();
}
public List<Project> getProjectBrief(String username)
{
List<Project> project = new ArrayList<Project>();
Project pro=null;
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
	  ArrayList<Document> arrayproject = (ArrayList<Document>) d.get("Projects");
       for(Document innerd:arrayproject)
       { pro= new Project();
    	 pro.setTitle(innerd.getString("title"));   
         pro.setDescription(innerd.getString("description"));
         project.add(pro); 
       // to add functionality for tags access
       }
  }
return project;
}
}
