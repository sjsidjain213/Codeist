package dao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import bean.Acknowledgement;
import bean.Comment;
import bean.MultiUse;
import bean.Notifications;
import bean.Project;
import bean.Super;

import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
public class ProjectInsert
{
	MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("project"); 
	MongoCollection <Document> tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
	
//inserting a new project to database
public Acknowledgement insertProject(Project project,String username)
{ try{
	String userfromsession = username;
	//String userfromsession = req.getSession().getAttribute("username").toString();
	Document docexsit = tc.find(and(eq("username",project.getUsername()),eq("title",project.getTitle()))).first();
   ArrayList<String> a=new ArrayList<String>();
   if(new UserDao().getAllUseri().contains(username)){
	   project.setOwner("i");
   }
   else if(new InstituteDao().getAllUserc().contains(username)){
	   project.setOwner("c");
   }
   a.add(project.getUsername());
   project.setContributors(a);
   Acknowledgement ack=new Acknowledgement();
   if(docexsit==null)
    { 
	  Document info=new Document().append("upvotes", new ArrayList<String>()).append("downvotes",new ArrayList<String>()).append("viewby",new ArrayList<String>());
      Document doc = new Document()
	    		 .append("username", username)
	    		// .append("username", project.getUsername())
	    		 .append("title",project.getTitle())	
	    		 .append("date",GeneralServices.getCurrentDate().getTime()) //
	    		 .append("last_updated",GeneralServices.getCurrentDate())
	    		 .append("owner", project.getOwner())
	    		 .append("description",project.getDescription())
	    		 .append("tags",(List<String>)project.getTags())
	    		 .append("comments",new ArrayList<Comment>())
	    		 .append("contributors",(ArrayList<String>)project.getContributors())
	    		 .append("readme", project.getReadme())
	    		 .append("license", project.getLicense())
	    		 .append("_private", project.get_private())
	    		 .append("project_link", (List<String>)project.getProject_link())
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
	    		  UpdateResult rs=tc.updateOne(and(eq("username",userfromsession),eq("title",project.getTitle())),new Document("$set",new Document("project_url",url)));		    		 
	    		  ack.setUpsertedId(projectid);
	    		  ack.setMessage(GeneralServices.spaceRemover(project.getTitle()));
	    		  return ack;
	    			}else{
	                    ack.setMessage("exist");
	                 	return ack;
                       }
}
catch(Exception e){
	e.printStackTrace();
	return new GeneralServices().response(Notifications.ERROR);

}
                    }
    @SuppressWarnings("unchecked")
   public Acknowledgement updateproject(Project project,String id){
    ObjectId oid = new ObjectId(id.toString());
    
    //Document doccheck = tc.find(and(or(eq("username",req.getSession().getAttribute("username").toString()),
    //                      in("contributors",req.getSession().getAttribute("username").toString())),
    //                      eq("_id",oid))).first();
   // if(doccheck!=null){
 
    Document document = tc.find(eq("_id",oid)).first();
      if(document!=null){
		Document info=(Document) document.get("info");
		  String title =  new GeneralServices().spaceRemover(project.getTitle());
		  String url = ""+document.getString("id")+"/"+title;
	    Document doc = new Document()
   		 .append("username", document.getString("username"))
   		 .append("title",project.getTitle())
   		 .append("owner", document.getString("owner"))
   		 .append("last_updated",new GeneralServices().getCurrentDate().getTime())
   		 .append("description",project.getDescription())
   		 .append("project_url",url)
   		 .append("project_link", (List<String>)project.getProject_link())
   		 .append("tags",(List<String>)project.getTags())
   		 .append("comments",(ArrayList<Comment>)document.get("comments"))
   		 .append("contributors",document.get("contributors"))
   		 .append("readme", project.getReadme())
   		 .append("license", project.getLicense())
   		 .append("_private", project.get_private())
   		 .append("video_url",project.getVideo_url())
   		 .append("zip_file",project.getZip_file())
   		 .append("images",(List<String>)project.getImages())
   		 .append("info",info);
	tc.updateOne(eq("_id",oid),new Document("$set",doc));
	//new NotificationService().projectUpdateNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
	ArrayList<String> contributors = project.getContributors();
	//contributors.forEach(contributor->{new NotificationService().projectUpdateNotification(contributor, req.getSession().getAttribute("username").toString(),id,project.getTitle());});
	return new GeneralServices().response(Notifications.SUCCESSFULLYINSERTED);
	}
    //}else{
	return new GeneralServices().response(null);
//}
    }



@SuppressWarnings("unchecked")
public ArrayList<Project> getProjectBrief(String username)
{System.out.println("here");
  ArrayList<Project> project = new ArrayList<Project>();
  Project pro=null;
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
	  Document d1=(Document)d.get("info");
         pro= new Project();
         pro.setId(d.get("_id").toString());
    	 pro.setTitle(d.getString("title"));   
         pro.setDescription(d.getString("description"));
         pro.setUpvotes((ArrayList<String>)d1.get("upvotes"));
         pro.setDownvotes((ArrayList<String>)d1.get("downvotes"));
         pro.setImages((ArrayList<String>) d.get("images"));
         pro.setProject_url(d.getString("project_url"));
         pro.setComments(new ProjectInsert().getAllComments(d.getString("username"), d.getString("title")));
         pro.setLogged("logged");  
         project.add(pro); 
  }
return project;
}

public ArrayList<String> getAllTitles(String username)
{
  ArrayList<String> project = new ArrayList<String>();
  FindIterable <Document> fi = tc.find(eq("username",username));
  for(Document d: fi)
  {
    	project.add(d.getString("title")); 
  }
return project;
}


// for retrieving selected project
@SuppressWarnings("unchecked")
public Project getSelectedProject(String id)
{ 
	ObjectId id1=new ObjectId(id.toString());
	Project project=new Project();
	Document d= tc.find(eq("_id",id1)).first();
	if(d!=null){
			project.setId(d.get("_id").toString());
			project.setUsername(d.getString("username"));
		   	project.setTitle(d.getString("title"));
		   	project.setOwner(d.getString("owner"));
		   	project.setUrl_title(GeneralServices.spaceRemover(d.getString("title")));
	   		project.setDescription(d.getString("description"));
	   		project.setDate(d.getLong("date"));
	   		//project.setLast_updated(d.getLong("last_updated"));
	   		project.setTags((ArrayList<String>)d.get("tags")); 
	   		project.setLicense(d.getString("license"));
	   		project.setProject_link((ArrayList<String>)d.get("project_link"));
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
	   		//if(req.getSession().getAttribute("username")!=null)
	   		//view(d.getString("username"),d.getString("title"), req.getSession().getAttribute("username").toString());
	}
	return project;
}

public Comment insertComment(Comment comment,String id)
{   ObjectId id1=new ObjectId(id.toString());
	Document project = tc.find(eq("_id",id1)).first();
	System.out.println(project);
	try{
//Document doc = new Document("username",req.getSession().getAttribute("username").toString())
		Document doc = new Document("username","pulkit")
                  .append("comment", comment.getComment())
                  .append("date",GeneralServices.getCurrentDate().getTime());
	
comment.setUsername("pulkit");//req.getSession().getAttribute("username").toString());
comment.setDate(doc.getLong("date"));
String acknow= tc.updateOne(eq("_id",id1),new Document("$push",new Document("comments",doc))).toString();
//new NotificationService().commentNotification(project.getString("username"),project.getString("title"),id,req.getSession().getAttribute("username").toString(),comment.getComment(),Notifications.COMMENT);
//new NotificationService().commentNotification(project.getString("username"),project.getString("title"),id,"pulkit",comment.getComment(),Notifications.COMMENT);

return comment;}
	catch(Exception e){
		e.printStackTrace();
		Comment a=new Comment();
		a.setComment("error");
		return a;
	}
}

public  Comment deleteComment(String project_id,Comment comment){
	
	ObjectId id1=new ObjectId(project_id);
    long dateLong	= Long.valueOf(comment.getDate());
	Comment comment1=new Comment();
//	ArrayList<String> con=(ArrayList<String>) project.get("contributors");
    try{
	Document doc = new Document("username",comment.getUsername())
      .append("comment", comment.getComment())
      .append("date", dateLong);
System.out.println(doc);
	tc.updateOne(eq("_id",id1), new Document("$pull",new Document("comments",doc)));
return comment;}
    catch(Exception e){
    	e.printStackTrace();
    	Comment a=new Comment();
		a.setComment("error");
    	return a;
    }
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
		    	comment.setDate(din.getLong("date"));
	            alcomment.add(comment);
		    }		
    	    }
     return alcomment;
}

@SuppressWarnings("unchecked")
public MultiUse  up(String id,String user){
	ObjectId id1=new ObjectId(id.toString());
	Document d = tc.find(eq("_id",id1)).first();
	MultiUse obj=new MultiUse();
	String username=d.getString("username");
	if(d.getString("owner").equals("i")){
		tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
	}
	else if(d.getString("owner").equals("c")){
		tcuser=new DatabaseServices().getDb().getCollection("institute");
	}
	// access in this pattern in QADao vote method also
	Document infodetails=(Document)d.get("info");
		ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
		ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
		
		if(up!=null){
			if(!up.contains(user)){
				if(down!=null &&down.contains(user)){
					down.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
					tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",-1)));
				}
			up.add(user);
			String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	
			tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
			tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",1)));
			//changes make in other too
			//new NotificationService().voteNotification(username,title,p_id,user,Notifications.UPVOTESQUESTION);
			//new NotificationService().projectUpvoteNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
           	obj.setUpvotes(up);
           	obj.setDownvotes(down);
			return obj;}
			else{
				up.remove(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
				tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",-1)));
			}
			//new NotificationService().projectUpvoteNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
			obj.setUpvotes(up);
           	obj.setDownvotes(down);
			return obj;
		}
		else{
			if(down!=null && down.contains(user)){
				down.remove(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",-1)));
			}
			up=new ArrayList<String>();
			up.add(user);
			 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();
			 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
			 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",1)));
		//make chnages here
			 //	 new NotificationService().voteNotification(username,title,Notifications.UPVOTESPROJECT);
			 //new NotificationService().projectUpvoteNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
			 obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			
		}
}

@SuppressWarnings("unchecked")
public MultiUse down(String id,String user){
	ObjectId id1=new ObjectId(id.toString());
	Document d = tc.find(eq("_id",id1)).first();
	String username=d.getString("username");
	if(d.getString("owner").equals("i")){
		tcuser=new DatabaseServices().getDb().getCollection("testuserdata");
	}
	else if(d.getString("owner").equals("c")){
		tcuser=new DatabaseServices().getDb().getCollection("institute");
	}
	MultiUse obj=new MultiUse();
	Document infodetails=(Document)d.get("info");
		ArrayList<String> up=(ArrayList<String>)infodetails.get("upvotes");
		ArrayList<String> down=(ArrayList<String>)infodetails.get("downvotes");
		
		if(down!=null){
			if(!down.contains(user)){
				if(up!=null && up.contains(user)){
					up.remove(user);
					 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	 
					 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
					 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",-1)));
				}
			down.add(user);
			String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
			tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
			tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",1)));
			//make changes here
			// new NotificationService().voteNotification(username,title,Notifications.DOWNVOTESPROJECT);
			//new NotificationService().projectDownvoteNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
			 obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;}
			else{
				down.remove(user);
				String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	 
				tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
				tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",-1)));
			}
			//new NotificationService().projectDownvoteNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
			 obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
		}
		else{
			if(up!=null && up.contains(user)){
				up.remove(user);
				 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.upvotes",up))).toString();	
				 tc.updateOne(eq("_id",id1),new Document("$set",new Document("upvotecount",new Long(up.size()))));
				 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_upvote",-1)));
			}
			down=new ArrayList<String>();
			down.add(user);
			 String acknow2 = tc.updateOne(eq("_id",id1),new Document("$set",new Document("info.downvotes",down))).toString();	
			tc.updateOne(eq("_id",id1),new Document("$set",new Document("downvotecount",new Long(down.size()))));
			 tcuser.updateOne(eq("username",username),new Document("$inc",new Document("project_downvote",1)));
			//make changes here
			 // new NotificationService().voteNotification(username,title,Notifications.DOWNVOTESPROJECT);
			// new NotificationService().projectDownvoteNotification(project.getUsername(), req.getSession().getAttribute("username").toString(),id,project.getTitle());
			 obj.setUpvotes(up);
	           	obj.setDownvotes(down);
				return obj;
			
		}
}

/*
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
*/
/*public ArrayList<MultiUse> getAllUserProject()
{
	tc.
}*/


}
