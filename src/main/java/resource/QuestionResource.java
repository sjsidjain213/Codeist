package resource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import bean.Super;
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
	@Path("/{id}/updatequestion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement updateQuestion(Question question,@Context HttpServletRequest req,@PathParam("id")String id,@HeaderParam("sess") String sess)
	{
		//return new QADao().updateQuestion(req, question,id);
		//return (new SessionService().sessionVerifier(req))?new QADao().insertQuestion(question,req):new GeneralServices().response(null);
		return (new SessionService().sessionVerifier(sess))?new QADao().updateQuestion(sess, question,id):new GeneralServices().response(null);
}
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement insertQuestion(Question question,@Context HttpServletRequest req,@PathParam("username") String username,@HeaderParam("sess") String sess)
	{
	//return new QADao().insertQuestion(question,username,req);	
	return (new SessionService().sessionVerifier(sess))?new QADao().insertQuestion(question,sess,req):new GeneralServices().response(null);

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
	public ArrayList<Question> getAllQuestion(@PathParam("username")String username,@HeaderParam("sess") String sess)
	{
		return new QADao().getAllQuestions(username);
	}
	
	
	
	@POST
	@Path("/{id}/answer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Answer insertAnswer(@PathParam("id") String id,@PathParam("username") String username,Answer answer,@Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
		//return new QADao().insertAnswer(id,answer,username,req);
	//	return (new SessionService().sessionVerifier(req))?new QADao().insertAnswer(id,answer,req):new GeneralServices().response(null);
	return (new SessionService().sessionVerifier(sess))?new QADao().insertAnswer(id,answer,sess):new Answer(false);


	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Question getQuestion(@PathParam("id")String question,@HeaderParam("sess") String sess)
	{
		return new QADao().getQuestion(question);
	}
	
	@POST
	@Path("/forum/{question_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Super getQuestions(@PathParam("question_id") String question_id,@HeaderParam("sess") String sess)
	{
	
	//get questions tags from database
		Super s = new Super();
		ArrayList<Tile> altile = new HomePage().getRelatedProject(question_id);
		s.setData(altile);
		s.setLoggedin(true);
    	return s;
	//return new HomePage().getRelatedProject(question_id);
		//return new HomePage().getQuestions(username);
      //return new HomePage().getQuestions(req.getSession().getAttribute("username").toString());

	}
	
	@PUT
	@Path("/{id}/upvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse upquestion(@PathParam("id")String id,@Context HttpServletRequest req,@PathParam("username") String username,@HeaderParam("sess") String sess)
	{
		//return new QADao().upQuestion(id,username);
		return (new SessionService().sessionVerifier(sess))?new QADao().upQuestion(id,sess):new MultiUse(false);

//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().upQuestion(id,req.getSession().getAttribute("username").toString()):obj;
	//return (new SessionService().sessionVerifier(req))?new QADao().upQuestion(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/downvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse downquestion(@PathParam("id")String id,@Context HttpServletRequest req,@PathParam("username") String username,@HeaderParam("sess") String sess)
	{
		//return new QADao().downQuestion(id,username);
		return (new SessionService().sessionVerifier(sess))?new QADao().downQuestion(id,sess):new MultiUse(false);

//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().downQuestion(id,req.getSession().getAttribute("username").toString()):obj;
//	//return (new SessionService().sessionVerifier(req))?new QADao().downQuestion(id,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
}
	@PUT
	@Path("/{id}/{username}/upvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse upanswer(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req,@PathParam("user")String user,@HeaderParam("sess") String sess)
	{//username:whose answer user: who is upvoting
		//return new QADao().upanswer(id,username,user);
		return (new SessionService().sessionVerifier(sess))?new QADao().upanswer(id,username,sess):new MultiUse(false);

//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().upanswer(id,username,req.getSession().getAttribute("username").toString()):obj;
	//return (new SessionService().sessionVerifier(req))?new QADao().upanswer(id,username,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	@PUT
	@Path("/{id}/{username}/downvote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MultiUse downanswer(@PathParam("id")String id,@PathParam("username")String username,@Context HttpServletRequest req,@PathParam("user")String user,@HeaderParam("sess") String sess)
	{
		//return new QADao().downanswer(id,username,user);
		return (new SessionService().sessionVerifier(sess))?new QADao().downanswer(id,username,sess):new MultiUse(false);

//		MultiUse obj= new MultiUse();
//		obj.setMessage(Notifications.SESSIONDONOTEXSIT.getMsg());
//	return (new SessionService().sessionVerifier(req))?new QADao().downanswer(id,username,req.getSession().getAttribute("username").toString()):obj;
	//return (new SessionService().sessionVerifier(req))?new QADao().downanswer(id,username,req.getSession().getAttribute("username").toString()):new ArrayList<String>();
	}
	
	@POST
	@Path("/{id}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement deleteQuestion(@PathParam("id") String id, @Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
		 //return new QADao().deleteQuestion(req, id);
		 return (new SessionService().sessionVerifier(sess))?new QADao().deleteQuestion(sess, id):new GeneralServices().response(null);

	}

	
	
	@POST
	@Path("/{id}/{username}/deleteans")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement deleteanswer(@PathParam("id") String id,Answer ans, @Context HttpServletRequest req,@PathParam("username")String username,@HeaderParam("sess") String sess)
	{
		return (new SessionService().sessionVerifier(sess))?new QADao().deleteanswer(sess, username,id):new GeneralServices().response(null);

		// return new QADao().deleteanswer(req, username,id);
	}
	
	@POST
	@Path("/{id}/updateans")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement updateanswer(@PathParam("id") String id,Answer ans, @Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
		 //return new QADao().updateanswer(req, ans,id);
		 return (new SessionService().sessionVerifier(sess))?new QADao().updateanswer(sess, ans,id):new GeneralServices().response(null);

	}
	
	@POST
	@Path("/{id}/updateques")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement updateQuestion(@PathParam("id") String id,Question ques, @Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
		// return new QADao().updateQuestion(req, ques,id);
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/asked/{username}")
	//@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Question> getProblems_asked(@PathParam("username") String email)
	{
		 return new QADao().getProblem(email);
	}*/
		return (new SessionService().sessionVerifier(sess))?new QADao().updateQuestion(sess, ques,id):new GeneralServices().response(null);

	
}
	
}
