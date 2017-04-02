package dao;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import bean.Acknowledgement;
import bean.Comment;
import bean.MultiUse;
import bean.Notifications;
import bean.Project;
import bean.Question;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;

import service.DatabaseServices;
import service.GeneralServices;

public class InstituteDao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("institute");
    //id of institute
	// getname
	public ArrayList<Question> getAllQuestions(String username)
	{
   	 ArrayList<Question> alq = new ArrayList<Question>();
     Document doc = tc.find(eq("username",username)).first();
     ArrayList<String> alquestion = (ArrayList<String>) doc.get("question_id");
     System.out.println(alquestion);
     if(alquestion.size()>0){
     for(String s : alquestion)
     { 
    	Question quest = new QADao().getQuestion(s);
    	System.out.println(quest);
    	ArrayList<String> tags = quest.getTags();
       ArrayList<String> source =  addDepartment(username,tags);
        quest.setDepartment(source);
       alq.add(quest);
     }
     }
     return alq;
	}
	
	public ArrayList<String> addDepartment(String username,ArrayList<String> questiontags)
	{
	Document doc = tc.find(eq("username",username)).first();
	ArrayList<String> al = (ArrayList<String>) doc.get("departments");
ArrayList<String> aldepartment = new ArrayList<String>();
	for(String sout:al)
	{
        if(tc.find(in(sout,questiontags)).first()!=null)
        {
        	aldepartment.add(sout);
        }
	}
	return aldepartment;
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
	public ArrayList<String> getAllUserc()
	{
		 FindIterable <Document> fi =  tc.find();
	  ArrayList <String> alluser =  new ArrayList<String>();
		 for(Document doc : fi)
	  {		
	  alluser.add(doc.getString("username"));
	  }
		 return alluser;
		}
	public MultiUse getdepartments(String username){
		MultiUse obj = new MultiUse();
		Document doc=tc.find(eq("username",username)).first();
		obj.setDepartments((ArrayList<String>) doc.get("departments"));
		return obj;
	}
}
