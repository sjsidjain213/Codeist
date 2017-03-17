package dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import bean.Project;
import exception.UserNotFoundException;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import interfaces.service.AtoSCon;
import interfaces.service.StoACon;
import service.GeneralServices;
public class ProjectInsert
{
	 private MongoClientURI uri  = new MongoClientURI("mongodb://sjsidjain:sjsidjain@ds145359.mlab.com:45359/testhack"); 
     private MongoClient client = new MongoClient(uri);
     private MongoDatabase db = client.getDatabase(uri.getDatabase());
     private MongoCollection <Document> tc = db.getCollection("testcol");

public Acknowledgement insertProject(Project project,String username)
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
         String acknow =  tc.updateOne(eq("username",username),new Document("$addToSet",new Document("Projects",doc))).toString();	

            Acknowledgement acknowledge = new GeneralServices().stoacknowmethod(s ->{
	                     Acknowledgement ac2 = new Acknowledgement();
	                     String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
   	                     ac2.setMatchedCount(sa[0]);
   	                     ac2.setModifiedCount(sa[1]);
   	                     ac2.setUpsertedId(sa[2]);
	                     return ac2;}, acknow);
client.close();
return acknowledge;
}

public List<Project> getProjectBrief(String username)
{
List<Project> project = new ArrayList<Project>();
Project pro=null;
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
	  @SuppressWarnings("unchecked")
	ArrayList<Document> arrayproject = (ArrayList<Document>) d.get("Projects");
       for(Document innerd:arrayproject)
       { pro= new Project();
    	 pro.setTitle(innerd.getString("title"));   
         pro.setDescription(innerd.getString("description"));
         project.add(pro); 
       }
  }
return project;
}

// for retrieving selected project
@SuppressWarnings("unchecked")
public Project getSelectedProject(String username,String title)
{
Project project=new Project();
FindIterable <Document> fi = tc.find(eq("username",username));
Document doc = fi.first();
ArrayList<Document> docarray = (ArrayList<Document>) doc.get("Projects");
for(Document d:docarray)
{
if(d.getString("title")!=null&&d.getString("title").equals(title))
{
	   project.setTitle(d.getString("title"));
	   //project.setComment(d.get(key));
	    project.setTags(new GeneralServices().atosmethod(als -> {
    	String s = "";
    	for(String sin : als)
    		s+=sin;
    	return s;
    	},(ArrayList<String>)d.get("tags"))); 
}
}
return project;
}
}
