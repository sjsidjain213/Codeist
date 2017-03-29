package resource;

import java.util.ArrayList;

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
import bean.Answer;
import bean.Question;
import dao.ProjectInsert;
import dao.QADao;
import service.GeneralServices;
import service.SessionService;

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
	public Question getQuestion(@PathParam("id")String question)
	{
		return new QADao().getQuestion(question);
	}
	@PUT
	@Path("/{id}/upvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> upquestion(@PathParam("id")String id,@Context HttpServletRequest req)
	{
	//return new ProjectInsert().up(username,title,req.getSession().getAttribute("username").toString());
	return (new SessionService().sessionVerifier(req))?new QADao().upQuestion(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/downvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> downquestion(@PathParam("id")String id,@Context HttpServletRequest req)
	{
	//return new ProjectInsert().up(username,title,req.getSession().getAttribute("username").toString());
	return (new SessionService().sessionVerifier(req))?new QADao().downQuestion(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/{username}/upvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> upanswer(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req)
	{
	//return new ProjectInsert().up(username,title,req.getSession().getAttribute("username").toString());
	return (new SessionService().sessionVerifier(req))?new QADao().upanswer(id,username,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/{username}/downvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> downanswer(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req)
	{
	//return new ProjectInsert().up(username,title,req.getSession().getAttribute("username").toString());
	return (new SessionService().sessionVerifier(req))?new QADao().downanswer(id,username,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}


}
