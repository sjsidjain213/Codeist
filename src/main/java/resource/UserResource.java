package resource;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
	public String signup(Signup signup)
	{//@PathParam("date") String date
	   String status = new UserDao().check(signup.getUsername(), signup.getEmailid());
	if(status.equals("success"))
	{
	   return new GeneralServices().signup(signup);
	}
	else{
		return status;
	}
	}
	@GET
	@Path("/verifier/{hash1}/{hash2}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String signupverifier2(@PathParam("hash1") String hash1,@PathParam("hash2") String hash2,@Context HttpServletResponse response)
	{	String s=new EmailVerifier().test(hash1,hash2,response);
		try {
		if(s=="verified")
		response.sendRedirect("http://www.google.com");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return s;
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public Acknowledgement logout(@Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
		return new SessionService().sessionDestroy(req,sess);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert")
	public Acknowledgement insertUser(User users,@Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
	return (new SessionService().sessionVerifier(sess))?new UserDao().updateUser(users,sess):new GeneralServices().response(null);
	//return new UserDao().updateUser(users);
	}
	
	// For Demo Purpose : : User here can access his/her profile ONLY after login 
	// for login user session is required to create session use method login for session creation
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/profile/{username}")
	public User getUserDetails(@Context HttpServletRequest req,@PathParam("username") String username,@HeaderParam("sess") String sess,@Context HttpServletResponse response)
	{System.out.println(sess+"ihiwdhni");
//		if(new SessionService().tokenVerifier(auth_token,req,response)){
			//return new UserDao().getUserDetails(username);
//		}else{
//            return new User();			
		//}
	User user=new User();
	user.setLoggedin(false);
			return (new SessionService().sessionVerifier(sess))?new UserDao().getUserDetails(username):user;
			
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("profile/update")
	public Acknowledgement upsertUserDetails(User user,@Context HttpServletRequest req,@HeaderParam("sess") String sess)
	{
    return (new SessionService().sessionVerifier(sess))?new UserDao().updateUserDetails(user,sess):new GeneralServices().response(null);
	}
	//@Path("/highratinguser")
	@Override
	public void filter(ContainerRequestContext  requestContext, ContainerResponseContext cres) throws IOException {
		// TODO Auto-generated method stub
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
	      cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization,auth_token,sess");
	      cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
	      cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	      cres.getHeaders().add("Access-Control-Max-Age", "1209600");
	}
	@GET
	@Path("/contributorverifier/{hash1}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String contributor(@PathParam("hash1") String hash1,@Context HttpServletResponse response)
	{	return new EmailVerifier().contributor(hash1,response);
	}
	
	// user list and project list
	@GET
	@Path("/alluser")
	@Consumes(MediaType.APPLICATION_JSON)
	public test getAllUser(@Context HttpServletResponse response,@HeaderParam("sess") String sess)
	{	
		test s=new test();
		s.setLoggedin(false);
		return (new SessionService().sessionVerifier(sess))?new UserDao().getAllUser():s;
		//return new UserDao().getAllUser();
	}
	
	//
	@GET
	@Path("/project/allTitle/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Super getAllUserProjects(@Context HttpServletRequest req, @PathParam("username") String username,@HeaderParam("sess") String sess)
	{	 Super s=new Super();
	s.setLoggedin(false);
	return (new SessionService().sessionVerifier(sess))?new ProjectInsert().getAllTitles(username):s;
	//return new ProjectInsert().getAllTitles(username);
	}
	
	@GET 
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	public void test(@Context HttpServletRequest req){
		System.out.println(req.getRemoteAddr());
		System.out.println(req.getRemoteHost());
		System.out.println(req.getRemoteUser());
		System.out.println(req.getSession());
		System.out.println(req.getRequestedSessionId());
		System.out.println(req.getLocalAddr());
		System.out.println(req.getLocalName());
		System.out.println("-------------------------");
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
