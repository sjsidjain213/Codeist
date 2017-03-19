package resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import bean.Project;
import bean.Tag;
import dao.ProjectInsert;
import service.GeneralServices;

@Path("/search")
public class SearchResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> search(Tag d)
	{
		return new ProjectInsert().searchProject(d);
	}
	
}
