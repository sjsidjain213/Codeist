package pdao;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import bean.Acknowledgement;
import bean.Answer;
import bean.MultiUse;
import bean.Notifications;
import bean.Question;
import dao.UserDao;
import service.DatabaseServices;
import service.GeneralServices;
import service.NotificationService;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.servlet.http.HttpServletRequest;

public class QADao {
	
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("qa");
	MongoCollection<Document> tcuser = new DatabaseServices().getDb().getCollection("testuserdata");

	
}
