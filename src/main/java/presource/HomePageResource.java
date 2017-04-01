package presource;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import bean.Tile;
import pdao.HomePage;

@Path("/phomepage")
public class HomePageResource {
	@GET
	@Path("/forum/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Tile> getQuestion(@Context HttpServletRequest req,@PathParam("username") String username)
	{
//		return new HomePage().getQuestions(req.getSession().getAttribute("username").toString());
		return new HomePage().getQuestions(username);
		
	}

//	/
//	@GET
//	@Path("/projects-feed")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Super getProject(@Context HttpServletRequest req)
//	{
//		Super s = new Super();
//		ArrayList<Tile> altile = new HomePage().getProjects(req.getSession().getAttribute("username").toString());
//		s.setAlsuper(altile);
//		s.setLogged("logged");
//    	return s; //req.getSession().getAttribute("username").toString());
//	}
//	
//	
//	@GET
//	@Path("/topuser")
//	@Produces(MediaType.APPLICATION_JSON)
//	public ArrayList<User> getTopUser()
//	{
//    	return new HomePage().topUsers();
//	}
//	@POST
//	@Path("/img")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void img(test test)
//	{
//    	try {
//			new objectupload().upload(test.getImg());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
