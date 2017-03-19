package dao;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import bean.Acknowledgement;
import bean.Comment;
import bean.Project;
import bean.Tag;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import service.DatabaseServices;
import service.GeneralServices;
public class ProjectInsert
{
	// private MongoClientURI uri  = new MongoClientURI("mongodb://sjsidjain:sjsidjain@ds145359.mlab.com:45359/testhack"); 
     //private MongoClient client = new MongoClient(uri);
     //private MongoDatabase db = client.getDatabase(uri.getDatabase());
     //private MongoCollection <Document> tc = db.getCollection("userdata");
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("userdata");
	
@SuppressWarnings("unchecked")
public Acknowledgement insertProject(Project project,String username)
{ 
	Document d = tc.find(eq("username",username)).first();
	ArrayList<Document>  alproject = (ArrayList<Document>) d.get("projects");
	int index = alproject.size()+1;
	Document doc = new Document()
	    	  .append("title",project.getTitle())		  
	    	  .append("description",project.getDescription())
	    	  .append("project_url",project.getProject_url())
	    	  .append("index",index)
	    	  .append("tags",project.getTags())
	    	  .append("likes",project.getLike())
	    	  .append("comments",project.getComment())
	    	  .append("contributors",project.getContributors())
	    	  .append("readme", project.getReadme())
	    	  .append("private", project.get_private())
	    	  .append("zip_file",project.getZipfile())
	    	  .append("images",project.getImages());
         String acknow =  tc.updateOne(eq("username",username),new Document("$addToSet",new Document("projects",doc))).toString();	

            Acknowledgement acknowledge = new GeneralServices().stoacknowmethod(s ->{
	                     Acknowledgement ac2 = new Acknowledgement();
	                     String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
   	                     ac2.setMatchedCount(sa[0]);
   	                     ac2.setModifiedCount(sa[1]);
   	                     ac2.setUpsertedId(sa[2]);
	                     return ac2;}, acknow);
//client.close();
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
	   ArrayList<Document> arrayproject = (ArrayList<Document>) d.get("projects");
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
System.out.println(doc);
ArrayList<Document> docarray = (ArrayList<Document>) doc.get("projects");
for(Document d:docarray)
{
if(d.getString("title")!=null&&d.getString("title").equals(title))
{
	   project.setTitle(d.getString("title"));
	   project.setDescription(d.getString("description"));
	   project.setTags((ArrayList<String>)d.get("tags")); 
	   project.setLicence(d.getString("license"));
	   project.setLike(Double.parseDouble(d.get("likes").toString()));
	   project.setProject_url(d.getString("project_url"));
	   project.setContributors((ArrayList<String>)d.get("contributors"));
	   project.setReadme(d.getString("readme"));
	   project.set_private(d.getString("private"));
	   project.setVideourl((ArrayList<String>)d.get("video_url"));
	   project.setZipfile((ArrayList<String>)d.get("zip_file"));
	   project.setImages((ArrayList<String>)d.get("images"));
}
}
return project;
}

public Acknowledgement insertComment(Comment comment,String username,String projectname)
{
Document doc = new Document("username",comment.getUsername())
                  .append("comment", comment.getComment())
                  .append("EPOCH_TIME",new Date());
String acknow= tc.updateOne(eq("username","pjain"),new Document("$push",new Document("projects.0.comments",doc))).toString();

Acknowledgement acknowledge = new GeneralServices().stoacknowmethod(s ->{
    Acknowledgement ac2 = new Acknowledgement();
    String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
       ac2.setMatchedCount(sa[0]);
       ac2.setModifiedCount(sa[1]);
       ac2.setUpsertedId(sa[2]);
    return ac2;}, acknow);

return acknowledge;
}

@SuppressWarnings("unchecked")
public ArrayList<Comment> getAllComments(String username,String projectname)
{   System.out.println("hello");
	Document document = tc.find(eq("username",username)).first();
	ArrayList<Document> alproject = (ArrayList<Document>) document.get("projects");
	Comment comment = new Comment();
	ArrayList<Comment> alcomment = new ArrayList<Comment>();
    
	for(Document d : alproject)
	{
		if(d.getString("title").equals(projectname))
		{
		    ArrayList<Document> al =  (ArrayList<Document>) d.get("comments");
		    for(Document din : al)
		    {
		    	comment.setUsername(din.getString("username"));
		    	comment.setComment(din.getString("comment"));
		    	comment.setDate(din.getDate("EPOCH_TIME"));
	            alcomment.add(comment);
		    }		
		}
	}
    
return alcomment;
}
@SuppressWarnings("unchecked")
public List<Project> searchProject(Tag tags)
{
  List<Project> project = new ArrayList<Project>();
  Project pro=null;
  FindIterable <Document> fi = tc.find();
  for(Document d: fi)
  {
	 //@SuppressWarnings("unchecked")
	   ArrayList<Document> arrayproject = (ArrayList<Document>) d.get("projects");
       for(Document innerd:arrayproject)
       { 
    	 if(new GeneralServices().match(tags,(ArrayList<String>)innerd.get("tags"))){  
	    	 pro= new Project();
	    	 pro.setTitle(innerd.getString("title"));   
	         pro.setDescription(innerd.getString("description"));
	         project.add(pro);
    	 }
       }
  }
return project;
}
}
