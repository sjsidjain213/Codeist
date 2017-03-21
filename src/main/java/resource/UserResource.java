package resource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import bean.*;
import dao.*;
import service.DatabaseServices;
import service.SessionService;
@Path("/user")
public class UserResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/sessioncreate/{username}")
	public String login(@Context HttpServletRequest req,@PathParam("username") String username)
	{
		new SessionService().sessionCreate(req,username);
	    new DatabaseServices();
		return "Session Created";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/sessiondestroy")
	public String logout(@Context HttpServletRequest req)
	{
		new SessionService().sessionDestroy(req);
	    return "Session Destroyed";
	}
	
	// For Demo Purpose : : User here can access his/her profile ONLY after login 
	// for login user session is required to create session use method login for session creation
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/profile/{username}")
	public User getUserDetails(@PathParam("username")String username,@Context HttpServletRequest req)
	{
	return (new SessionService().sessionVerifier(req))?new UserDao().getUserDetails(username):new User();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateuser/{username}")
	public ArrayList<Acknowledgement> upsertUserDetails(User user,@PathParam("username") String username)
	{
		return new UserDao().updateUserDetails(user,username);
	}

}
