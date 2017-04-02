package resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.MultiUse;
import bean.Project;
import bean.Question;
import dao.InstituteDao;
import dao.QADao;

@Path("/institute")
public class InstituteResource {
	
	@GET
	@Path("/question/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Question> getQuestion(@PathParam("username")String username)
	{
		return new InstituteDao().getAllQuestions(username);
	//	return new QADao().getQuestion(username);
	}
	@GET
	@Path("project/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Project> getProject(@PathParam("username")String username)
	{
		return new InstituteDao().getAllProjects(username);
	//	return new QADao().getQuestion(username);
	}
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Question> getOwnQuestion(@PathParam("username")String username)
	{
		return new InstituteDao().getAllOwnQuestions(username);	
		//	return new QADao().getQuestion(username);
	}
	
	@GET
	@Path("/{username}/departments")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MultiUse departments(@PathParam("username")String username)
	{
		return new InstituteDao().getdepartments(username);	
		//	return new QADao().getQuestion(username);
	}
	
}
