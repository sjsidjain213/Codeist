package resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bean.Acknowledgement;
import bean.Institute;
import dao.AdminDao;

@Path("/adminpanel")
public class AdminPanelResource {
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Acknowledgement insertinstitute(Institute institute){
		return new AdminDao().insertinstitute(institute);
	}

	@GET
	@Path("/{college}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Institute getRating(@PathParam("college") String college){
		return new AdminDao().getInstituteDetail(college);
	}


	
	@GET
	@Path("/institutes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Institute> getName(@HeaderParam("sess")String s_id){
		System.out.println("-----------------------------------------------------");
		return new AdminDao().getName();
	}

}
