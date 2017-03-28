package resource;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bean.*;
import dao.*;
import service.DatabaseServices;
import service.EmailVerifier;
import service.GeneralServices;
import service.SendEmail;
import service.EmailVerifier;
import service.SessionService;
@Path("/user")
public class UserResource implements ContainerResponseFilter {
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public void signup(Signup signup)
	{//@PathParam("date") String date
		new GeneralServices().signup(signup);
	}
	@GET
	@Path("/verifier/{hash1}/{hash2}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String signupverifier2(@PathParam("hash1") String hash1,@PathParam("hash2") String hash2,@Context HttpServletResponse response)
	{	return new EmailVerifier().test(hash1,hash2,response);
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public MultiUse login(@Context HttpServletRequest req,@Context HttpServletResponse res,User user)
	{
		new DatabaseServices();
		return new SessionService().sessionCreate(req,res,user.getUsername(),user.getPassword());
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
	@Path("/profile/{username}")
	public User getUserDetails(@Context HttpServletRequest req,@PathParam("username") String username)
	{
	return new UserDao().getUserDetails(username);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("profile/update")
	public Acknowledgement upsertUserDetails(User user,@Context HttpServletRequest req)
	{
    return (new SessionService().sessionVerifier(req))?new UserDao().updateUserDetails(user,req.getSession().getAttribute("username").toString()):new GeneralServices().response(null);
	}
	//@Path("/highratinguser")
	@Override
	public void filter(ContainerRequestContext  requestContext, ContainerResponseContext cres) throws IOException {
		// TODO Auto-generated method stub
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
	      cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	      cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
	      cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	      cres.getHeaders().add("Access-Control-Max-Age", "1209600");
	}
	
	
	/*
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
	@Path("/userrating")
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
	}***/
}
