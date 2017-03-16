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
import bean.*;
import dao.*;
import service.*;
@Path("/user")
public class UserResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/profile/{username}")
	public User getUserDetails(@PathParam("username")String username)
	{
		return new UserDao().getUserDetails(username);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateuser")
	public void setUserName(User user)
	{
		new UserDao().updateUserDetails(user);
	}
	
	@POST
	@Path("/setData")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setUserDetails(User user)
	{
		System.out.println(user.getName());
	}
	
	
	// Only for reference 
	/*
	@POST
	@Path("/setDataOld")
	@Consumes(MediaType.APPLICATION_JSON)
	public void setUserDetailsOldTime(@Context HttpServletRequest req)
	{
		System.out.println(req.getParameter("name"));
	}*/
}
