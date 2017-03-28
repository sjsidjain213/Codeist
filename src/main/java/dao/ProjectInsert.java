package dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import bean.Acknowledgement;
import bean.Comment;
import bean.Notifications;
import bean.Project;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
public class ProjectInsert
{
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testproject"); 
	MongoCollection <Document> tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
	
//inserting a new project to database
public Acknowledgement insertProject(Project project,HttpServletRequest req)
{  //String userfromsession = req.getSession().getAttribute("username").toString();
   Document docexsit = tc.find(and(eq("username",project.getUsername()),eq("title",project.getTitle()))).first();
  Acknowledgement ack=new Acknowledgement();
   if(docexsit==null)
    { Document info=new Document().append("upvotes", (ArrayList<String>)project.getUpvotes()).append("downvotes",(ArrayList<String>)project.getDownvotes()).append("viewby",(List<String>)project.getViewby());
      Document doc = new Document()
	    		// .append("username", req.getSession().getAttribute("username"))
	    		 .append("username", project.getUsername())
	    		 .append("title",project.getTitle())	
	    		 .append("date",GeneralServices.getCurrentDate())
	    		 .append("last_updated",GeneralServices.getCurrentDate())
	    		 .append("description",project.getDescription())
	    		 .append("tags",(List<String>)project.getTags())
	    		 //.append("comments",(ArrayList<Comment>)project.getComments())
	    		 .append("contributors",(List<String>)project.getContributors())
	    		 .append("readme", project.getReadme())
	    		 .append("license", project.getLicense())
	    		 .append("_private", project.get_private())
	    		 .append("video_url",project.getVideo_url())
	    		 .append("zip_file",project.getZip_file())
	    		 .append("images",(List<String>)project.getImages())
	    		 .append("info", info)
	    		 .append("upvotecount", new Long(0))
	    		 .append("downvotecount", new Long(0));
	    		  tc.insertOne(doc);
	    		  String projectid= tc.find(and(eq("username",project.getUsername()),eq("title",project.getTitle()))).first().get("_id").toString();
	    		  String url = GeneralServices.urlGenerator(Notifications.PROJECTMODULE, projectid, project.getTitle());
	    		  new UserDao().moduleIDAdder(Notifications.PROJECTMODULE,project.getUsername(), projectid);
	    		  UpdateResult rs=tc.updateOne(and(eq("username",project.getUsername()),eq("title",project.getTitle())),new Document("$set",new Document("project_url",url)));		    		 
	    		 ack.setUpsertedId(projectid);
	    		 ack.setMsg(GeneralServices.spaceRemover(project.getTitle()));
	    		 return ack;
	    			}else{
	                    ack.setMsg("exist");
	                 	return ack;
                       }
                    }
    @SuppressWarnings("unchecked")
   public Acknowledgement updateproject(Project project,HttpServletRequest req,String id){
    	
	
	ObjectId oid = new ObjectId(id.toString());
	Document document = tc.find(eq("_id",oid)).first();

	if(document!=null){
		System.out.println(id);
		Document info=(Document) document.get("info");
		  String title =  new GeneralServices().spaceRemover(project.getTitle());
		  String url = ""+document.getString("id")+"/"+title;
	Document doc = new Document()
   		 .append("username", document.getString("username"))
   		 .append("title",project.getTitle())	
   		.append("date",project.getDate())
   		 .append("last_updated",new GeneralServices().getCurrentDate())
   		 .append("description",project.getDescription())
   		.append("project_url",url)
   		 .append("tags",(List<String>)project.getTags())
   		 .append("comments",(ArrayList<Comment>)document.get("comments"))
   		 .append("contributors",document.get("contributors"))
   		 .append("readme", project.getReadme())
   		 .append("license", project.getLicense())
   		 .append("_private", project.get_private())
   		 .append("video_url",project.getVideo_url())
   		 .append("zip_file",project.getZip_file())
   		 .append("images",(List<String>)project.getImages())
   		 .append("info",info );
	Document doccheck = tc.find(and(eq("username",(req.getSession().getAttribute("username")).toString()),eq("title",project.getTitle()))).first();
	if(doccheck!=null){
	tc.updateOne(eq("_id",oid),new Document("$set",doc));
	return new GeneralServices().response("insert");
	}
	}
	return new GeneralServices().response(null);
}



@SuppressWarnings("unchecked")
public List<Project> getProjectBrief(String username)
{System.out.println("here");
  List<Project> project = new ArrayList<Project>();
  Project pro=null;
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
	  Document d1=(Document)d.get("info");
         pro= new Project();
    	 pro.setTitle(d.getString("title"));   
         pro.setDescription(d.getString("description"));
         pro.setUpvotes((ArrayList<String>)d1.get("upvotes"));
         pro.setDownvotes((ArrayList<String>)d1.get("downvotes"));
         pro.setProject_url(d.getString("project_url"));
         project.add(pro); 
  }
return project;
}

public List<String> gettitles(String username)
{
  List<String> project = new ArrayList<String>();
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
    	project.add(d.getString("title")); 
  }
return project;
}


// for retrieving selected project
@SuppressWarnings("unchecked")
public Project getSelectedProject(String id,HttpServletRequest req)
{
	ObjectId id1=new ObjectId(id.toString());
	Project project=new Project();
	Document d= tc.find(eq("_id",id1)).first();
	if(d!=null){
			project.setUsername(d.getString("username"));
		   	project.setTitle(d.getString("title"));
	   		project.setDescription(d.getString("description"));
	   		project.setTags((ArrayList<String>)d.get("tags")); 
	   		project.setLicense(d.getString("license"));
	   		project.setProject_url(d.getString("project_link"));
	   		project.setContributors((ArrayList<String>)d.get("contributors"));
	   		project.setReadme(d.getString("readme"));
	   		project.set_private(d.getString("_private"));
	   		project.setVideo_url((ArrayList<String>)d.get("video_url"));
	   		project.setZip_file((ArrayList<String>)d.get("zip_file"));
	   		project.setImages((ArrayList<String>)d.get("images"));
	   		Document innerdoc = (Document)d.get("info");
	   		if(innerdoc!=null){
	   		project.setUpvotes((ArrayList<String>)innerdoc.get("upvotes"));
	   		project.setDownvotes((ArrayList<String>)innerdoc.get("downvotes"));
	   		project.setViewby((ArrayList<String>)innerdoc.get("viewby"));
	   		}
	   		project.setComments(new ProjectInsert().getAllComments(d.getString("username"), d.getString("title")));
	   		if(req.getSession().getAttribute("username")!=null)
	   		view(d.getString("username"),d.getString("title"), req.getSession().getAttribute("username").toString());
	}
	return project;
}

public Acknowledgement insertComment(Comment comment,String id,HttpServletRequest req)
{ObjectId id1=new ObjectId(id.toString());
	Document project = tc.find(eq("_id",id1)).first();
Document doc = new Document("username",req.getSession().getAttribute("username"))
                  .append("comment", comment.getComment())
                  .append("date",GeneralServices.getCurrentDate());
String acknow= tc.updateOne(eq("_id",id1),new Document("$push",new Document("comments",doc))).toString();
new NotificationService().commentNotification(project.getString("username"),project.getString("title"),id,req.getSession().getAttribute("username").toString(),comment.getComment(),Notifications.COMMENT);
return new GeneralServices().response(acknow);
}

@SuppressWarnings("unchecked")
public ArrayList<Comment> getAllComments(String username,String projectname)
{   Document document = tc.find(and(eq("username",username),eq("title",projectname))).first();
	ArrayList<Comment> alcomment = new ArrayList<Comment>();
    	    ArrayList<Document> al =  (ArrayList<Document>) document.get("comments");
    	    if(al!=null){
		    for(Document din : al)
		    {   Comment comment = new Comment();
				comment.setUsername(din.getString("username"));
		    	comment.setComment(din.getString("comment"));
		    	comment.setDate(din.getDate("date"));
	            alcomment.add(comment);
		    }		
    	    }
     return alcomment;
}

@SuppressWarnings("unchecked")
public Acknowledgement up(String id,String user){
	ObjectId id1=new ObjectId(id.toString());
	Document d = tc.find(eq("_id",id1)).first();
	String username=d.getString("username");
	// access in this pattern in QADao vote method also
	Document infodetails=(Document)d.get("info");
		ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
		ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
		
		if(up!=null){
			if(!up.contains(user)){
				if(down!=null &&down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",-1)));
				}
			up.add(user);
			String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	
			tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
			tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",1)));
			//changes make in other too
			//new NotificationService().voteNotification(username,title,p_id,user,Notifications.UPVOTESQUESTION);
           	 return new GeneralServices().response(acknow2);}
			else{
				up.remove(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
				tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",-1)));
			}
		return new GeneralServices().response("already exist");
		}
		else{
			if(down!=null && down.contains(user)){
				down.remove(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",-1)));
			}
			up=new ArrayList<String>();
			up.add(user);
			 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();
			 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
			 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",1)));
		//make chnages here
			 //	 new NotificationService().voteNotification(username,title,Notifications.UPVOTESPROJECT);
			 return new GeneralServices().response(acknow2);
			
		}
}

@SuppressWarnings("unchecked")
public Acknowledgement down(String id,String user){
	ObjectId id1=new ObjectId(id.toString());
	Document d = tc.find(eq("_id",id1)).first();
	String username=d.getString("username");
	Document infodetails=(Document)d.get("info");
		ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
		ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
		
		if(down!=null){
			if(!down.contains(user)){
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",-1)));
				}
			down.add(user);
			String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
			tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
			tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",1)));
			//make changes here
			// new NotificationService().voteNotification(username,title,Notifications.DOWNVOTESPROJECT);
			 return new GeneralServices().response(acknow2);}
			else{
				down.remove(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
				tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",-1)));
			}
		return new GeneralServices().response("already exist");
		}
		else{
			if(up!=null && up.contains(user)){
				up.remove(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",up.size())));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",-1)));
			}
			down=new ArrayList<String>();
			down.add(user);
			 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	
			 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",down.size())));
			 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",1)));
			//make changes here
			 // new NotificationService().voteNotification(username,title,Notifications.DOWNVOTESPROJECT);
			 return new GeneralServices().response(acknow2);
			
		}
}


@SuppressWarnings("unchecked")
public Acknowledgement view(String username,String title,String user){
	Document d = tc.find(and(eq("username",username),eq("title",title))).first();
	Document infodetails=(Document)d.get("info");
		
		ArrayList<String> view=(ArrayList<String>)infodetails.get("viewby");
		
		if(view!=null){
			if(!view.contains(user)){
			view.add(user);
			String acknow2 = tc.updateOne(and(eq("username", username),eq("title",title)),new Document("$set",new Document("info.viewby",view))).toString();	 
			 //new NotificationService().voteNotification(username,title,Notifications.UPVOTESPROJECT);
			 return new GeneralServices().response(acknow2);
			 }
				return new GeneralServices().response("already exist");
				}
		else{
			view=new ArrayList<String>();
			view.add(user);
			 String acknow2 = tc.updateOne(and(eq("username", username),eq("title",title)),new Document("$set",new Document("info.viewby",view))).toString();	 
			 return new GeneralServices().response(acknow2);
			
		}
}

}
