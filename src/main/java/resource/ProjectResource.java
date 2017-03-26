package resource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bean.Acknowledgement;
import bean.Comment;
import dao.ProjectInsert;
import dao.UserDao;
import service.GeneralServices;
import service.SessionService;
import bean.Project;
import bean.SearchBean;
import bean.User;
@Path("/project")
public class ProjectResource {

@POST
@Path("/insert")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement insertProject(Project project,@Context HttpServletRequest req)
{
//return new ProjectInsert().insertProject(project);
return (new SessionService().sessionVerifier(req))?new ProjectInsert().insertProject(project,req):new GeneralServices().response(null);
}

@POST
@Path("/update/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement updateProject(Project project,@Context HttpServletRequest req,@PathParam("id")String id)
{
//return new ProjectInsert().insertProject(project);
return (new SessionService().sessionVerifier(req))?new ProjectInsert().updateproject(project,req,id):new GeneralServices().response(null);
}

@GET
@Path("/retrieveall")   //logged in user
@Produces(MediaType.APPLICATION_JSON)
public List<Project> getBriefProject(@Context HttpServletRequest req)
{
//return new ProjectInsert().getProjectBrief(username);
return (new SessionService().sessionVerifier(req))?new ProjectInsert().getProjectBrief(req.getSession().getAttribute("username").toString()):GeneralServices.nullProject();
}
@GET
@Path("/retrieveselect/{title}")   //for logged in user
@Produces(MediaType.APPLICATION_JSON)
public Project getSelectedProject(@Context HttpServletRequest req,@PathParam("title")String title)
{
//return new ProjectInsert().getSelectedProject(req.getSession().getAttribute("username").toString(), title);
//return new Project();
return (new SessionService().sessionVerifier(req))?new ProjectInsert().getSelectedProject(req.getSession().getAttribute("username").toString(), GeneralServices.spaceAdder(title),req):new Project();

}
@GET
@Path("/retrieveselect/{username}/{title}")
@Produces(MediaType.APPLICATION_JSON)
public Project getSelected(@PathParam("username")String username,@PathParam("title")String title,@Context HttpServletRequest req)
{
return new ProjectInsert().getSelectedProject(username, GeneralServices.spaceAdder(title),req);

//return new Project();
}

@POST
@Path("/insertcomment/{username}/{title}")	//username:project owner
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Acknowledgement insertComment(@Context HttpServletRequest req,Comment comment,@PathParam("title")String title,@PathParam("username")String username)
{
//return new ProjectInsert().insertComment(comment,username,projectname);
return (new SessionService().sessionVerifier(req))?new ProjectInsert().insertComment(comment,username,GeneralServices.spaceAdder(title),req):new GeneralServices().response(null);

}

@GET
@Path("/retrievecomments/{username}/{title}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<Comment> getComment(@PathParam("title")String title,@PathParam("username")String username)
{
	return 	new ProjectInsert().getAllComments(username, GeneralServices.spaceAdder(title));
}

//@PUT
//@Path("/upvote/{action}/{username}/{projecttitle}")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public Acknowledgement changeUpvotes(@PathParam("action")String action,@PathParam("username")String username,@PathParam("projecttitle")String projecttitle,@Context HttpServletRequest req)
//{
//return new ProjectInsert().changeUpvotes(action,username,projecttitle,req);
//}
//
//@PUT
//@Path("/downvote/{action}/{projecttitle}")
//@Consumes(MediaType.APPLICATION_JSON)
//public Acknowledgement changeDownvotes(@PathParam("action")String action,@PathParam("projecttitle")String projecttitle,@Context HttpServletRequest req)
//{
// return	new ProjectInsert().changeDownvotes(action,projecttitle,req);
//}





/**/
//@GET
//@Path("/projectupvotes/{username}/{title}")
//@Produces(MediaType.APPLICATION_JSON)
//public Project fetchProjectUpvotes(@PathParam("username")String username, @PathParam("title")String title)
//{
//return new ProjectInsert().getProjectUpvotes(username, title);
//}
//
//@GET
//@Path("/projectdownvotes/{username}/{title}")
//@Produces(MediaType.APPLICATION_JSON)
//public Project fetchProjectDownvotes(@PathParam("username")String username, @PathParam("title")String title)
//{
//return new ProjectInsert().getProjectDownvotes(username, title);
//}
//
//@GET
//@Path("/projectviewcount/{username}/{title}")
//@Produces(MediaType.APPLICATION_JSON)
//public Project fetchProjectViewcount(@PathParam("username")String username, @PathParam("title")String title)
//{
//return new ProjectInsert().getProjectViewcount(username, title);
//}
//
//@POST
//@Path("/insertupvotes/{username}/{title}")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public Acknowledgement insertProjectUpvotes(Project project,@PathParam("username")String username, @PathParam("title")String title)
//{
//return new ProjectInsert().setProjectUpvotes(project, username, title);
//}
//
//@POST
//@Path("/insertdownvotes/{username}/{title}")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public Acknowledgement insertProjectDownvotes(Project project,@PathParam("username")String username, @PathParam("title")String title)
//{
//return new ProjectInsert().setProjectDownvotes(project, username, title);
//}
//
//@POST
//@Path("/insertviewcount/{username}/{title}")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public Acknowledgement insertProjectViewcount(Project project,@PathParam("username")String username, @PathParam("title")String title)
//{
//return new ProjectInsert().setProjectViewcount(project, username, title);
//}
//
//
//--------------------------------------
@PUT
@Path("/upvote/{username}/{title}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement up(@PathParam("username")String username,@PathParam("title")String title,@Context HttpServletRequest req)
{
//return new ProjectInsert().up(username,title,req.getSession().getAttribute("username").toString());
return (new SessionService().sessionVerifier(req))?new ProjectInsert().up(username,GeneralServices.spaceAdder(title),req.getSession().getAttribute("username").toString()):new GeneralServices().response(null);
}

@PUT
@Path("/downvote/{username}/{title}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Acknowledgement down(@PathParam("username")String username,@PathParam("title")String title,@Context HttpServletRequest req)
{
//return new ProjectInsert().down(username,title,req.getSession().getAttribute("username").toString());
return (new SessionService().sessionVerifier(req))?new ProjectInsert().down(username,GeneralServices.spaceAdder(title),req.getSession().getAttribute("username").toString()):new GeneralServices().response(null);
}
}

