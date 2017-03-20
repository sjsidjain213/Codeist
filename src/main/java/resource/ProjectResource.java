package resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.Acknowledgement;
import bean.Comment;
import dao.ProjectInsert;
import bean.Project;
@Path("/project")
public class ProjectResource {

@POST
@Path("/insert/{username}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public String insertProject(Project project,@PathParam("username")String username)
{
return new ProjectInsert().insertProject(project, username);
}

@GET
@Path("/retrieveall/{username}")
@Produces(MediaType.APPLICATION_JSON)
public List<Project> getBriefProject(@PathParam("username")String username)
{
return new ProjectInsert().getProjectBrief(username);
}
@GET
@Path("/retrieveselect/{username}/{title}")
@Produces(MediaType.APPLICATION_JSON)
public Project getSelectedProject(@PathParam("username")String username,@PathParam("title")String title)
{
return new ProjectInsert().getSelectedProject(username, title);
//return new Project();
}

@POST
@Path("/insertcomment/{username}/{projectname}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Acknowledgement insertComment(Comment comment,@PathParam("projectname")String projectname,@PathParam("username")String username)
{
return new ProjectInsert().insertComment(comment,username,projectname);
}

@GET
@Path("/retrievecomments/{username}/{projectname}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<Comment> getComment(@PathParam("projectname")String projectname,@PathParam("username")String username)
{
	return 	new ProjectInsert().getAllComments(username, projectname);
}

}
