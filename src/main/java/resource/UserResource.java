package resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import bean.*;
import dao.*;
@Path("/user")
public class UserResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/profile/{username}")
	public User getUserDetails(@PathParam("username")String username)
	{
		return new UserDao().getUserDetails(username);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update")
	public ArrayList<Acknowledgement> upsertUserDetails(User user)
	{
		return new UserDao().updateUserDetails(user);
	}

}
