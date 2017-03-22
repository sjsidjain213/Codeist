package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	public Acknowledgement insertQuestion(Question question)
	{
		return new QADao().insertQuestion(question);
	}
	@POST
	@Path("/insertanswer/{username}/{question}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement insertAnswer(Answer answer,@PathParam("question")String question,@PathParam("username") String username)
	{
		return new QADao().insertAnswer(answer, question,username);
	}
	@GET
	@Path("/retrieve/{question}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Question insertAnswer(@PathParam("question")String question)
	{
		return new QADao().getQuestion(question);
	}
}
