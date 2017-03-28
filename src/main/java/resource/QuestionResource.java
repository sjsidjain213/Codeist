package resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bean.Acknowledgement;
import bean.Answer;
import bean.Question;
import dao.QADao;

@Path("/question")
public class QuestionResource {
	
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement insertQuestion(Question question,@Context HttpServletRequest req)
	{
		return new QADao().insertQuestion(question,req);
	}
	@POST
	@Path("/insertanswer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement insertAnswer(Answer answer,@Context HttpServletRequest req)
	{
		return new QADao().insertAnswer(answer,req);
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Question insertAnswer(@PathParam("id")String question)
	{
		return new QADao().getQuestion(question);
	}
}
