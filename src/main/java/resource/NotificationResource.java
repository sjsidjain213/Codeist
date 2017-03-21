package resource;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import bean.NotificationBean;
import service.NotificationService;

@Path("/notification")
public class NotificationResource {

@GET
@Path("/retrieveall/{username}")
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<NotificationBean> getAllNotifcation(@PathParam("username") String username)
{
  return new NotificationService().getAllNotifications(username);
}
}
