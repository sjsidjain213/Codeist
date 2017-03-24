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

import javax.servlet.http.HttpServletRequest;

import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
public class ProjectInsert
{
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testproject");
	
//inserting a new project to database
public Acknowledgement insertProject(Project project)
{       Document doc = new Document()
	    		 .append("username", project.getUsername())
	    		 .append("title",project.getTitle())	
	    		 .append("date",project.getDate())
	    		 .append("description",project.getDescription())
	    		 .append("tags",(List<String>)project.getTags())
	    		 .append("comments",(List<String>)project.getComments())
	    		 .append("contributors",(List<String>)project.getContributors())
	    		 .append("readme", project.getReadme())
	    		 .append("license", project.getLicense())
	    		 .append("_private", project.get_private())
	    		 .append("video_url",project.getVideo_url())
	    		 .append("zip_file",project.getZip_file())
	    		 .append("images",(List<String>)project.getImages());
	    		  tc.insertOne(doc);
	    		  String p_id= tc.find(and(eq("username","sidda"),eq("title","unique"))).first().get("_id").toString();
	             String title =  new GeneralServices().spaceRemover(project.getTitle());
	    		  String url = "http:8080//localhost/webapi/project/"+p_id+"/retrieveselect/"+title;
	    		  tc.updateOne(and(eq("username",project.getUsername()),eq("title",project.getTitle())),new Document("$set",new Document("project_url",url)));	
	    
	    return new GeneralServices().response("insert");
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
new NotificationService().commentNotification(username,projectname,comment.getUsername(),comment.getComment(),Notifications.COMMENT);
return new GeneralServices().response(acknow);
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

public Acknowledgement changeUpvotes(String action,String projecttitle,HttpServletRequest req)
{
String user=	(String) req.getSession().getAttribute("username");
if(!(user==null))
{Document d = tc.find(and(eq("username",user),eq("title",projecttitle))).first();
Document info=  (Document) d.get("info");
long upvotes = info.getLong("upvotes");
if(action.equals("inc")){upvotes++;}else{upvotes--;}
String acknow = tc.updateOne(and(eq("username",user),eq("title",projecttitle)),new Document("$set",new Document("info.upvotes",upvotes))).toString();
new NotificationService().voteNotification("akshaykumar",projecttitle,Notifications.UPVOTESQUESTION);
return new GeneralServices().response(acknow);
}
else{
return new GeneralServices().response(null);	
}

}

public Acknowledgement changeDownvotes(String action,String projecttitle,HttpServletRequest req)
{
String user=	(String) req.getSession().getAttribute("username");
System.out.println(user);
if(!(user==null))
{Document d = tc.find(and(eq("username",user),eq("title",projecttitle))).first();
Document info=  (Document) d.get("info");
long upvotes = info.getLong("downvotes");
if(action.equals("inc")){upvotes++;}else{upvotes--;}
String s =tc.updateOne(and(eq("username",user),eq("title",projecttitle)),new Document("$set",new Document("info.downvotes",upvotes))).toString();
return new GeneralServices().response(s);
}
else{return new GeneralServices().response(null);
}
}



// getter and setter for Database
public Project getProjectUpvotes(String username, String title)
{
	  Project project = new Project();
	  FindIterable <Document> fi = tc.find(eq("username",username));
	  for(Document d : fi){
		  if(d.getString("title").equalsIgnoreCase(title)){
			    Document innerdoc = (Document)d.get("info");
		   		project.setUpvotes(innerdoc.getLong("upvotes"));
		  }
	  }
	  return project;
}

public Project getProjectDownvotes(String username, String title)
{
	  Project project = new Project();
	  FindIterable <Document> fi = tc.find(eq("username",username));
	  for(Document d : fi){
		  if(d.getString("title").equalsIgnoreCase(title)){
			    Document innerdoc = (Document)d.get("info");
		   		project.setDownvotes(innerdoc.getLong("downvotes"));
		  }
	  }
	  return project;
}

public Project getProjectViewcount(String username, String title)
{
	  Project project = new Project();
	  FindIterable <Document> fi = tc.find(eq("username",username));
	  for(Document d : fi){
		  if(d.getString("title").equalsIgnoreCase(title)){
			    Document innerdoc = (Document)d.get("info");
		   		project.setViewcount(innerdoc.getLong("viewcount"));
		  }
	  }
	  return project;
}

public Acknowledgement setProjectUpvotes(Project project, String username, String title)
{
	  Acknowledgement ac2 = new Acknowledgement();
	  FindIterable <Document> fi = tc.find(eq("username",username));
	  for(Document d : fi){
		  if(d.getString("title").equalsIgnoreCase(title)){
			  Document innerdoc = (Document)d.get("info");
			  project.setDownvotes(innerdoc.getLong("downvotes"));
			  project.setViewcount(innerdoc.getLong("viewcount"));
		  }
	  }
	  Document doc = new Document()
			  		.append("upvotes",project.getUpvotes())
	  				.append("downvotes",project.getDownvotes())
	  				.append("viewcount",project.getViewcount());
	  String acknow1 = tc.updateOne(and(eq("username", project.getUsername()),eq("title",project.getTitle())),new Document("$set",new Document("info",doc))).toString();
	  Acknowledgement acknowledge1 = new GeneralServices().stoacknowmethod(s ->{
        String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
           ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
        return ac2;}, acknow1);
	  return acknowledge1;
}

public Acknowledgement setProjectDownvotes(Project project, String username, String title)
{
	  Acknowledgement ac2 = new Acknowledgement();
	  FindIterable <Document> fi = tc.find(eq("username",username));
	  for(Document d : fi){
		  if(d.getString("title").equalsIgnoreCase(title)){
			  Document innerdoc = (Document)d.get("info");
			  project.setUpvotes(innerdoc.getLong("upvotes"));
			  project.setViewcount(innerdoc.getLong("viewcount"));
		  }
	  }
	  Document doc = new Document()
		  		.append("upvotes",project.getUpvotes())
				.append("downvotes",project.getDownvotes())
				.append("viewcount",project.getViewcount());
	  String acknow1 = tc.updateOne(and(eq("username", project.getUsername()),eq("title",project.getTitle())),new Document("$set",new Document("info",doc))).toString();
	  Acknowledgement acknowledge1 = new GeneralServices().stoacknowmethod(s ->{
        String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
           ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
        return ac2;}, acknow1);
	  return acknowledge1;
}

public Acknowledgement setProjectViewcount(Project project, String username, String title)
{
	  Acknowledgement ac2 = new Acknowledgement();
	  FindIterable <Document> fi = tc.find(eq("username",username));
	  for(Document d : fi){
		  if(d.getString("title").equalsIgnoreCase(title)){
			  Document innerdoc = (Document)d.get("info");
			  project.setUpvotes(innerdoc.getLong("upvotes"));
			  project.setDownvotes(innerdoc.getLong("downvotes"));
		  }
	  }
	  Document doc = new Document()
		  		.append("upvotes",project.getUpvotes())
				.append("downvotes",project.getDownvotes())
				.append("viewcount",project.getViewcount());
	  String acknow1 = tc.updateOne(and(eq("username", project.getUsername()),eq("title",project.getTitle())),new Document("$set",new Document("info",doc))).toString();
	  Acknowledgement acknowledge1 = new GeneralServices().stoacknowmethod(s ->{
        String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
           ac2.setMatchedCount(sa[0]);ac2.setModifiedCount(sa[1]);ac2.setUpsertedId(sa[2]);
        return ac2;}, acknow1);
	  return acknowledge1;
}

}
