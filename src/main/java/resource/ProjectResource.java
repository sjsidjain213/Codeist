package resource;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.bson.Document;

import bean.Acknowledgement;
import bean.Comment;
import bean.MultiUse;
import bean.Notifications;
import dao.ProjectInsert;
import dao.UserDao;
import service.GeneralServices;
import service.SessionService;
import bean.Project;
import bean.SearchBean;
import bean.User;
@Path("/projects")
public class ProjectResource {

@POST
@Path("/insert")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement insertProject(Project project,@HeaderParam("sess") String sess)
{
	
//return new ProjectInsert().insertProject(project);
//return	new ProjectInsert().insertProject(project,username);
return (new SessionService().sessionVerifier(sess))?new ProjectInsert().insertProject(project,sess):new GeneralServices().response(null);
}
//to update
@PUT
@Path("/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement updateProject(Project project,@PathParam("id")String id,@HeaderParam("sess") String sess)
{
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().updateproject(project,req,id):new GeneralServices().response(null);
//return new ProjectInsert().updateproject(project,id);
return (new SessionService().sessionVerifier(sess))?new ProjectInsert().updateproject(project,id,sess):new GeneralServices().response(null);

}
//all projects of a user
@GET
@Path("/user/{username}")   
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<Project> getBriefProject(@Context HttpServletRequest req,@PathParam("username")String username,@HeaderParam("sess") String sess)
{
//return new ProjectInsert().getProjectBrief(username);
return new ProjectInsert().getProjectBrief(username);
}


//@GET
//@Path("/retrieveselect/{title}")   //for logged in user
//@Produces(MediaType.APPLICATION_JSON)
//public Project getSelectedProject(@Context HttpServletRequest req,@PathParam("title")String title)
//{
////return new ProjectInsert().getSelectedProject(req.getSession().getAttribute("username").toString(), title);
////return new Project();
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().getSelectedProject(req.getSession().getAttribute("username").toString(), GeneralServices.spaceAdder(title),req):new Project();
//}



@GET
@Path("/{id}")
@Produces(MediaType.APPLICATION_JSON)
public Project getSelected(@PathParam("id")String id,@Context HttpServletRequest req,@HeaderParam("sess") String sess)
{
return new ProjectInsert().getSelectedProject(id);

//return new Project();
}

@POST
@Path("/{id}/comment")	//username:project owner
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Comment insertComment(@Context HttpServletRequest req,Comment comment,@PathParam("id")String id,@HeaderParam("sess") String sess)
{
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().insertComment(comment,id,req):new Comment();
//return new ProjectInsert().insertComment(comment,id);
return (new SessionService().sessionVerifier(sess))?new ProjectInsert().insertComment(comment,id,sess):new Comment(false);
}

@POST
@Path("/{id}/comment/delete")
@Produces(MediaType.APPLICATION_JSON)
public Comment deleteComment(@PathParam("id")String id,Comment comment,@Context HttpServletRequest req,@HeaderParam("sess") String sess){
	//return (new SessionService().sessionVerifier(req))?new ProjectInsert().deleteComment(id,comment):comm;
	//comm.setComment(Notifications.SESSIONDONOTEXSIT.getMsg());
	//Comment comm=new Comment();
	//return new ProjectInsert().deleteComment(id,comment);
	return (new SessionService().sessionVerifier(sess))?new ProjectInsert().deleteComment(id,comment,sess):new Comment(false);
}

@PUT
@Path("/{id}/upvote")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public MultiUse up(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req,@HeaderParam("sess") String sess)
{
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().up(id,req.getSession().getAttribute("username").toString()):obj;
	//obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
	//MultiUse obj= new MultiUse();
//return new ProjectInsert().up(id,username);
	System.out.println(sess);
return (new SessionService().sessionVerifier(sess))?new ProjectInsert().up(id,sess):new MultiUse(false);
}

@PUT
@Path("/{id}/downvote")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public MultiUse down(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req,@HeaderParam("sess") String sess)
{
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().down(id,req.getSession().getAttribute("username").toString()):obj;
	//obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
	//MultiUse obj= new MultiUse();
//return new ProjectInsert().down(id,username);
return (new SessionService().sessionVerifier(sess))?new ProjectInsert().down(id,sess):new MultiUse(false);
}
}

