package dao;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import bean.Acknowledgement;
import bean.Comment;
import bean.Notifications;
import bean.Project;
import bean.Tag;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
public class ProjectInsert
{
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("project");
	
//inserting a new project to database
public String insertProject(Project project)
{ Date date = new Date();
	    Document doc = new Document()
	    		 .append("username", project.getUsername())
	    		 .append("title",project.getTitle())	
	    		 .append("date",project.getDate())
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
	    	 return "Inserted";
}

public List<Project> getProjectBrief(String username)
{
  List<Project> project = new ArrayList<Project>();
  Project pro=null;
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
         pro= new Project();
    	 pro.setTitle(d.getString("title"));   
         pro.setDescription(d.getString("description"));
         project.add(pro); 
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
                  .append("date",new Date());
String acknow= tc.updateOne(and(eq("username",username),eq("title",projectname)),new Document("$push",new Document("comments",doc))).toString();
Acknowledgement acknowledge = new GeneralServices().stoacknowmethod(s ->{
    Acknowledgement ac2 = new Acknowledgement();
    String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
       ac2.setMatchedCount(sa[0]);
       ac2.setModifiedCount(sa[1]);
       ac2.setUpsertedId(sa[2]);
    return ac2;}, acknow);
new NotificationService().commentNotification(username,projectname,comment.getUsername(),comment.getComment(),Notifications.COMMENT);
return acknowledge;
}

@SuppressWarnings("unchecked")
public ArrayList<Comment> getAllComments(String username,String projectname)
{   Document document = tc.find(and(eq("username",username),eq("title",projectname))).first();
	Comment comment = new Comment();
	ArrayList<Comment> alcomment = new ArrayList<Comment>();
    	    ArrayList<Document> al =  (ArrayList<Document>) document.get("comments");
		    for(Document din : al)
		    {
		    	comment.setUsername(din.getString("username"));
		    	comment.setComment(din.getString("comment"));
		    	comment.setDate(din.getDate("date"));
	            alcomment.add(comment);
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
