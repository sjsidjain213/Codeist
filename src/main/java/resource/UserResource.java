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

import bean.*;
import dao.*;
import service.DatabaseServices;
import service.GeneralServices;
import service.SessionService;
@Path("/user")
public class UserResource {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public ArrayList<NotificationBean> login(@Context HttpServletRequest req,User user)
	{
		new DatabaseServices();
		return new SessionService().sessionCreate(req,user.getUsername(),user.getPassword());
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public Acknowledgement logout(@Context HttpServletRequest req)
	{
		return new SessionService().sessionDestroy(req);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert")
	public Acknowledgement insertUser(User users,@Context HttpServletRequest req)
	{
	//return (new SessionService().sessionVerifier(req))?new UserDao().insertUser(users):new GeneralServices().response(null);
	return new UserDao().insertUser(users);
	}
	
	// For Demo Purpose : : User here can access his/her profile ONLY after login 
	// for login user session is required to create session use method login for session creation
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/profile")
	public User getUserDetails(@Context HttpServletRequest req)
	{
	return (new SessionService().sessionVerifier(req))?new UserDao().getUserDetails(req.getSession().getAttribute("username").toString()):new User();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateuser")
	public Acknowledgement upsertUserDetails(User user,@Context HttpServletRequest req)
	{
    return (new SessionService().sessionVerifier(req))?new UserDao().updateUserDetails(user,req.getSession().getAttribute("username").toString()):new GeneralServices().response(null);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/retrievealluser")
	public ArrayList<User> getAllUser(@Context HttpServletRequest req)
	{
	return (new SessionService().sessionVerifier(req))?new UserDao().getAllUsers():new ArrayList<User>();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/userrating/{username}")
	public User getUserRating(@PathParam("username") String username,@Context HttpServletRequest req)
	{
	return (new SessionService().sessionVerifier(req))?new UserDao().getUserRating(req.getSession().getAttribute("username").toString()):new User();
	}
	
	@POST
	@Path("/insertrating")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Acknowledgement insertUserRating(User user,@Context HttpServletRequest req)
	{
	return (new SessionService().sessionVerifier(req))?new UserDao().setUserRating(user,req.getSession().getAttribute("username").toString()):new GeneralServices().response(null);
	}
	
	@POST
	@Path("/updatefavtags")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement updateFavTags(@PathParam("username")String username,Tag favTags,@Context HttpServletRequest req){
	return (new SessionService().sessionVerifier(req))?new UserDao().updateFavTags(favTags,req.getSession().getAttribute("username").toString()):new GeneralServices().response(null);
	}
	
	@POST
	@Path("/updatetagsviewed")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement updateTagsViewed(Tag tagsViewed,@Context HttpServletRequest req){
	return (new SessionService().sessionVerifier(req))?new UserDao().updateTagsViewed(req.getSession().getAttribute("username").toString(), tagsViewed):new GeneralServices().response(null);
	}
}
