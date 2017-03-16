package resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import dao.ProjectInsert;
import bean.Project;
@Path("/project")
public class ProjectResource {

@POST
@Path("/insert")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public String insertProject(Project project)
{
return new ProjectInsert().insertProject(project, "sid");
}

@GET
@Path("/retrieve/{username}")
@Produces(MediaType.APPLICATION_JSON)
public List<Project> getBriefProject(@PathParam("username")String username)
{
return new ProjectInsert().getProjectBrief(username);
}
}
