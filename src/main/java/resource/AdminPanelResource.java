package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
