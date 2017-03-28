package resource;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.Tile;
import bean.User;
import bean.test;
import dao.HomePage;
import service.GeneralServices;

@Path("/homepage")
public class HomePageResource {

	@GET
	@Path("/history/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getUserHistory(@PathParam("username") String username)
	{
    	return new HomePage().getHistory(username);
	}
	
	@GET
	@Path("/question-forum")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getQuestion(@PathParam("username") String username)
	{
    	return new HomePage().trendingQuestion();
	}
	
	@GET
	@Path("/project-forum")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getProject(@PathParam("username") String username)
	{
    	return new HomePage().trendingProject();
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
    	new GeneralServices().update(test.getImg());
	}
}
