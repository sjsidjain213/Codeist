package presource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.Question;
import pdao.QADao;

@Path("/problem")
public class ProblemResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	@Path("/asked/{username}")
	public ArrayList<Question> getProblems_asked(@PathParam("username") String email)
	{
		 return new QADao().getProblem(email);
	}
}
