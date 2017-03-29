package resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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

import bean.Acknowledgement;
import bean.Comment;
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
public Acknowledgement insertProject(Project project,@Context HttpServletRequest req)
{
//return new ProjectInsert().insertProject(project);
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().insertProject(project,req):new GeneralServices().response(null);
return	new ProjectInsert().insertProject(project,req);
}
//to update
@PUT
@Path("/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement updateProject(Project project,@Context HttpServletRequest req,@PathParam("id")String id)
{
return new ProjectInsert().updateproject(project,req,id);
//return (new SessionService().sessionVerifier(req))?new ProjectInsert().updateproject(project,req,id):new GeneralServices().response(null);
}
//all projects of a user
@GET
@Path("/user/{username}")   
@Produces(MediaType.APPLICATION_JSON)
public List<Project> getBriefProject(@Context HttpServletRequest req,@PathParam("username")String username)
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
public Project getSelected(@PathParam("id")String id,@Context HttpServletRequest req)
{
return new ProjectInsert().getSelectedProject(id,req);

//return new Project();
}

@POST
@Path("/{id}/comment")	//username:project owner
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Comment insertComment(@Context HttpServletRequest req,Comment comment,@PathParam("id")String id)
{
//return new ProjectInsert().insertComment(comment,username,projectname);
return (new SessionService().sessionVerifier(req))?new ProjectInsert().insertComment(comment,id,req):new Comment();

}

@DELETE
@Path("/{id}/comment/{username}/{date}")
@Produces(MediaType.APPLICATION_JSON)
public Comment deleteComment(@PathParam("id")String id,@PathParam("username")String username,@PathParam("date")String date,@Context HttpServletRequest req){
	return new ProjectInsert().deleteComment(id, username, date,req);
	//return (new SessionService().sessionVerifier(req))?new ProjectInsert().deleteComment(id, username, date ,req):new Comment();
	
}

@PUT
@Path("/{id}/upvote")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<String> up(@PathParam("id")String id,@Context HttpServletRequest req)
{
//return new ProjectInsert().up(username,title,req.getSession().getAttribute("username").toString());
return (new SessionService().sessionVerifier(req))?new ProjectInsert().up(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
}

@PUT
@Path("/{id}/downvote")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<String> down(@PathParam("id")String id,@Context HttpServletRequest req)
{
//return new ProjectInsert().down(username,title,req.getSession().getAttribute("username").toString());
return (new SessionService().sessionVerifier(req))?new ProjectInsert().down(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
}
}

