package dao;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import bean.Project;
import bean.Question;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import service.DatabaseServices;

public class InstituteDao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("institute");
    //id of institute
	// getname
	public ArrayList<Question> getAllQuestions(String username)
	{
   	ArrayList<Question> alq = new ArrayList<Question>();
     Document doc = tc.find(eq("username",username)).first();
     ArrayList<String> alquestion = (ArrayList<String>) doc.get("question_id");
     if(alquestion.size()>0){
     for(String s : alquestion)
     { 
    	Question quest = new QADao().getQuestion(s);		
        alq.add(quest);
     }
     }
     return alq;
	}
	
	public ArrayList<Project> getAllProjects(String username)
	{
		ArrayList<Project> alp = new ArrayList<Project>();
	     Document doc = tc.find(eq("username",username)).first();
	     ArrayList<String> alproject = (ArrayList<String>) doc.get("project_id");
	     if(alproject.size()>0){
	     for(String s : alproject)
	     { 
	    	//Question quest = new QADao().getQuestion(s);		
	        Project project = new ProjectInsert().getSelectedProject(s);
	    	 alp.add(project);
	     }
	    }
	     return alp;
	}
	public ArrayList<Question> getAllOwnQuestions(String username)
	{
		ArrayList<Question> alq = new ArrayList<Question>();
     Document doc = tc.find(eq("username",username)).first();
     ArrayList<String> alquestion = (ArrayList<String>) doc.get("question_asked");
     if(alquestion.size()>0){
     for(String s : alquestion)
     { 
    	Question quest = new QADao().getQuestion(s);		
        alq.add(quest);
     }
     }
     return alq;
	}
	
}
