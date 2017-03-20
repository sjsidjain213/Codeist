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
	
	//we have established connection with mongoDB in DatabaseService so here we are directly accessing the collection
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("project");
	
//inserting a new project to database
public String insertProject(Project project,String username)
{ 
	    Document doc = new Document()
	    		 .append("username", project.getUsername())
	    		 .append("title",project.getTitle())		  
	    		 .append("description",project.getDescription())
	    		 .append("project_url",project.getProject_url())
	    		 .append("tags",(List<String>)project.getTags())
	    		 .append("comments",(List<String>)project.getComments())
	    		 .append("contributors",(List<String>)project.getContributors())
	    		 .append("readme", project.getReadme())
	    		 .append("license", project.getLicense())
	    		 .append("_private", project.get_private())
	    		 .append("video_url",project.getVideo_url())
	    		 .append("zip_file",project.getZip_file())
	    		 .append("images",(List<String>)project.getImages())
	    		 .append("info", new Document("upvotes",project.getUpvotes())
	    				 	.append("downvotes",project.getDownvotes())
	    				 	.append("viewcount",project.getViewcount()));
		          tc.insertOne(doc);
	    	 //System.out.println(project.getUpvotes()+"::"+project.getDownvotes());
		       
	    	 return "Inserted";
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
	for(Document d:fi)
	{
		if(d.getString("title").equalsIgnoreCase(title))
		{
	   		project.setTitle(d.getString("title"));
	   		project.setDescription(d.getString("description"));
	   		project.setTags((ArrayList<String>)d.get("tags")); 
	   		project.setLicense(d.getString("license"));
	   		project.setProject_url(d.getString("project_url"));
	   		project.setContributors((ArrayList<String>)d.get("contributors"));
	   		project.setReadme(d.getString("readme"));
	   		project.set_private(d.getString("_private"));
	   		project.setVideo_url((ArrayList<String>)d.get("video_url"));
	   		project.setZip_file((ArrayList<String>)d.get("zip_file"));
	   		project.setImages((ArrayList<String>)d.get("images"));
	   		Document innerdoc = (Document)d.get("info");
	   		project.setUpvotes(innerdoc.getLong("upvotes"));
	   		project.setDownvotes(innerdoc.getLong("downvotes"));
	   		project.setViewcount(innerdoc.getLong("viewcount"));
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
