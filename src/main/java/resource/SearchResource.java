package resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import bean.MultiUse;
import bean.Project;
import bean.SearchBean;
import bean.Tag;
import dao.ProjectInsert;
import dao.RegionDao;
import dao.SearchBar;


@Path("/search")
public class SearchResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SearchBean> search(Tag d)
	{
		return new SearchBar().doSearch(d);
	}
}
