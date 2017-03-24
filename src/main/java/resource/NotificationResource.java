package resource;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import bean.NotificationBean;
import service.NotificationService;

@Path("/notification")
public class NotificationResource {

@GET
@Path("/retrieveall")
@Produces(MediaType.APPLICATION_JSON)
public ArrayList<NotificationBean> getAllNotifcation(@Context HttpServletRequest req)
{
  return new NotificationService().getAllNotifications(req.getSession().getAttribute("username").toString(),req.getSession().getAttribute("s_id").toString());
}
}
