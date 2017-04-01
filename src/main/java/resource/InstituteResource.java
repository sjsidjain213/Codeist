package resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.Question;
import dao.InstituteDao;
import dao.QADao;

@Path("/institute")
public class InstituteResource {
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Question> getQuestion(@PathParam("username")String username)
	{
		return new InstituteDao().getAllOwnQuestions(username);
	//	return new QADao().getQuestion(username);
	}
}