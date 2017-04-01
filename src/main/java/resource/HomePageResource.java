package resource;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bean.Tile;
import bean.User;
import bean.test;
import dao.HomePage;
import service.GeneralServices;
import service.objectupload;

@Path("/homepage")
public class HomePageResource {

	/*@GET
	@Path("/history/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getUserHistory(@PathParam("username") String username)
	{
    	return new HomePage().getHistory(username);
	}*/
	
//	@GET
//	@Path("/project/{username}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public ArrayList<Tile> getProjects(@PathParam("username") String username)
//	{
//		return new HomePage().getProjects(username);
//	}
	
	@GET
	@Path("/forum")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getQuestion(@Context HttpServletRequest req)
	{
		return new HomePage().getQuestions(req.getSession().getAttribute("username").toString());
	}
	
	@GET
	@Path("/projects-feed")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getProject(@Context HttpServletRequest req)
	{
		//System.out.println(req.getSession().getAttribute("username").toString());
    	return new HomePage().getProjects(req.getSession().getAttribute("username").toString());//req.getSession().getAttribute("username").toString());
	}
	
	
	@GET
	@Path("/topuser")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getTopUser()
	{
    	return new HomePage().topUsers();
	}
	@POST
	@Path("/img")
	@Produces(MediaType.APPLICATION_JSON)
	public void img(test test)
	{
    	try {
			new objectupload().upload(test.getImg());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
