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
import bean.MultiUse;
import bean.Notifications;
import bean.Question;
import bean.Tile;
import dao.HomePage;
import dao.ProjectInsert;
//import dao.QADao;
import dao.QADao;
import service.GeneralServices;
import service.SessionService;

@Path("/questions")
public class QuestionResource {
	
	@POST
	@Path("/updatequestion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateQuestion(Question question,@Context HttpServletRequest req)
	{
		new QADao().updateQuestion(req, question, "58ddf9a7c48fc91a78acefc3");
		//return (new SessionService().sessionVerifier(req))?new QADao().insertQuestion(question,req):new GeneralServices().response(null);
	}
	@POST
	@Path("/insert/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement insertQuestion(Question question,@Context HttpServletRequest req,@PathParam("username") String username)
	{
	return new QADao().insertQuestion(question,username,req);	
	//	return (new SessionService().sessionVerifier(req))?:new GeneralServices().response(null);
	}
	
	//implement this api to get related project to an question
	/*
	@GET
	@Path("/forum/{username}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getQuestion(@PathParam("username")String username,@PathParam("question_id") String question_id)
	{
	get questions tags from database
	return new HomePage().getRelatedProject(username,question_id,altags)
		//return new HomePage().getQuestions(username);
      //return new HomePage().getQuestions(req.getSession().getAttribute("username").toString());
	}*/
	
	@GET
	@Path("/allquestions/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Question> getAllQuestion(@PathParam("username")String username)
	{
		return new QADao().getAllQuestions(username);
	}
	
	
	@POST
	@Path("/{id}/answer/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Answer insertAnswer(@PathParam("id") String id,@PathParam("username") String username,Answer answer,@Context HttpServletRequest req)
	{return new QADao().insertAnswer(id,answer,username,req);
	//	return (new SessionService().sessionVerifier(req))?new QADao().insertAnswer(id,answer,req):new GeneralServices().response(null);
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Question getQuestion(@PathParam("id")String question)
	{
		return new QADao().getQuestion(question);
	}
	
	@POST
	@Path("/forum/{question_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getQuestions(@PathParam("question_id") String question_id)
	{
	
	//get questions tags from database
	return new HomePage().getRelatedProject(question_id);
		//return new HomePage().getQuestions(username);
      //return new HomePage().getQuestions(req.getSession().getAttribute("username").toString());

	}
	
	@PUT
	@Path("/{id}/upvote/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse upquestion(@PathParam("id")String id,@Context HttpServletRequest req,@PathParam("username") String username)
	{
		return new QADao().upQuestion(id,username);
//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().upQuestion(id,req.getSession().getAttribute("username").toString()):obj;
	//return (new SessionService().sessionVerifier(req))?new QADao().upQuestion(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/downvote/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse downquestion(@PathParam("id")String id,@Context HttpServletRequest req,@PathParam("username") String username)
	{
		return new QADao().downQuestion(id,username);
//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().downQuestion(id,req.getSession().getAttribute("username").toString()):obj;
//	//return (new SessionService().sessionVerifier(req))?new QADao().downQuestion(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
}
	@PUT
	@Path("/{id}/{username}/{user}/upvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse upanswer(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req,@PathParam("user")String user)
	{//username:whose answer user: who is upvoting
		return new QADao().upanswer(id,username,user);
//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().upanswer(id,username,req.getSession().getAttribute("username").toString()):obj;
	//return (new SessionService().sessionVerifier(req))?new QADao().upanswer(id,username,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/{username}/{user}/downvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse downanswer(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req,@PathParam("user")String user)
	{
		return new QADao().downanswer(id,username,user);
//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().downanswer(id,username,req.getSession().getAttribute("username").toString()):obj;
	//return (new SessionService().sessionVerifier(req))?new QADao().downanswer(id,username,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	
	@POST
	@Path("/{id}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement deleteQuestion(@PathParam("id") String id, @Context HttpServletRequest req)
	{
		 return new QADao().deleteQuestion(req, id);
	}

	
	
	@POST
	@Path("/{id}/{username}/deleteans")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement deleteanswer(@PathParam("id") String id,Answer ans, @Context HttpServletRequest req,@PathParam("username")String username)
	{
		 return new QADao().deleteanswer(req, username,id);
	}
	
	@POST
	@Path("/{id}/updateans")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement updateanswer(@PathParam("id") String id,Answer ans, @Context HttpServletRequest req)
	{
		 return new QADao().updateanswer(req, ans,id);
	}
	
	@POST
	@Path("/{id}/updateques")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement updateQuestion(@PathParam("id") String id,Question ques, @Context HttpServletRequest req)
	{
		 return new QADao().updateQuestion(req, ques,id);
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/asked/{username}")
	//@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Question> getProblems_asked(@PathParam("username") String email)
	{
		 return new QADao().getProblem(email);
	}*/
	
}
	
}
