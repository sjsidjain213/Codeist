package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import bean.Question;
import bean.Tile;
import bean.User;
import service.DatabaseServices;
import service.GeneralServices;

public class HomePage {
    HashMap<Object,Tile> hm = new HashMap<Object,Tile>();
 	@SuppressWarnings("unchecked")
 	public ArrayList<Tile> getProjects(String username)
 	{
 		ArrayList<Tile> project = new ArrayList<Tile>();
 	      project =  getTrendingProject(project);
 		  project = getInterestProject(project, username);
 	return project;
 	}
 	
 	public ArrayList<Tile> getQuestions(String username)
 	{
 		ArrayList<Tile> question = new ArrayList<Tile>();
 	      question =  trendingQuestion();
 		  question = getInterestQuestion(question, username);
 		  return question;
 	}
 	
 	public ArrayList<Tile> getInterestProject(ArrayList<Tile> project, String username)
 	{ //project
	      //id
 		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
 		Document doc = tc.find(eq("username",username)).first();
 	     
 		ArrayList<String> alcontributing = (ArrayList<String>) doc.get("contributing");
 		ArrayList<String> albookmark = (ArrayList<String>) doc.get("project_bookmark");
 		ArrayList<String> alfollowing = (ArrayList<String>) doc.get("following");
 		ArrayList<String> alfavtags = (ArrayList<String>) doc.get("favourite_tag");
	   
 		// in tags
 		doc = (Document) doc.get("history");
 		ArrayList<String> alviewedtags = (ArrayList<String>) doc.get("tag_view");
 		ArrayList<String> alviewedproject = (ArrayList<String>) doc.get("project_view");
 		ArrayList<String> alvieweduser = (ArrayList<String>) doc.get("user_view");
    
 		tc =new DatabaseServices().getDb().getCollection("project");
 		FindIterable <Document> docfavtags =  tc.find(in("tags",alfavtags));
 		FindIterable <Document> docviewedtags =  tc.find(in("tags",alviewedtags));
 		FindIterable <Document> docvieweduser =  tc.find(in("username",alvieweduser));
 		FindIterable <Document> docfollowing =  tc.find(in("username",alfollowing));
 		FindIterable <Document> docviewedproject =  tc.find(in("title",alviewedproject));
 		FindIterable <Document> doccontributing = null;
 		for(String s : alcontributing){
 			ObjectId id = new ObjectId(s);
 			doccontributing = tc.find(in("_id",id));
 		}
 		FindIterable <Document> docbookmark = null;
 		for(String s : albookmark){
 			ObjectId id = new ObjectId(s);
 			docbookmark = tc.find(in("_id",id));
 		}
	  
 		if(doccontributing != null)
 			getHistory(project,doccontributing,"interesting","project");
 		if(docbookmark != null)
 			getHistory(project,docbookmark,"interesting","project");
 		if(docfavtags != null)
 			getHistory(project,docfavtags,"interesting","project");
 		if(docviewedtags != null)
 			getHistory(project,docviewedtags,"interesting","project");
 		if(docvieweduser != null)
 			getHistory(project,docvieweduser,"interesting","project");
 		if(docviewedproject != null)
 			getHistory(project,docviewedproject,"interesting","project");
 		if(docfollowing != null)
 			getHistory(project,docfollowing,"interesting","project");
    
 		if(project != null)
 	    	  Collections.sort(project, Tile.homesort);
 	      System.out.println("project"+project);
 		
 		return project;
 	}
 	
 	public ArrayList<Tile> getInterestQuestion(ArrayList<Tile> question, String username)
 	{
 		 MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
 		   
 		   Document doc = tc.find(eq("username",username)).first();
 		   ArrayList<String> alquesbookmark = (ArrayList<String>) doc.get("question_bookmark");
 		   ArrayList<String> alquesask = (ArrayList<String>) doc.get("question_ask");
 		   ArrayList<String> alquesanswer = (ArrayList<String>) doc.get("question_answer");
 		  ArrayList<String> alfollowing = (ArrayList<String>) doc.get("following");
 		   ArrayList<String> alfavtags = (ArrayList<String>) doc.get("favourite_tag");
 		   
 	         // in tags
 		   doc = (Document) doc.get("history");
 	       ArrayList<String> alviewedtags = (ArrayList<String>) doc.get("tag_view");
 	       ArrayList<String> alvieweduser = (ArrayList<String>) doc.get("user_view");
 	       
 	      tc =new DatabaseServices().getDb().getCollection("qa");
 	       FindIterable <Document> quesfavtags =  tc.find(in("tags",alfavtags));
 	       FindIterable <Document> quesviewedtags =  tc.find(in("tags",alviewedtags));
 	       FindIterable <Document> quesvieweduser =  tc.find(in("username",alvieweduser));
 	       FindIterable <Document> quesfollowing =  tc.find(in("username",alfollowing));
 	       FindIterable <Document> quesbookmark = null;
 	       for(String s : alquesbookmark){
 	    	   ObjectId id = new ObjectId(s);
 	    	   quesbookmark = tc.find(in("_id",id));
 	       }
 	       
 	       FindIterable <Document> quesask = null;
 	       for(String s : alquesask){
 	    	   System.out.println(s+"###############################################################");
 	    	   ObjectId id = new ObjectId(s);
 	    	   quesask = tc.find(in("_id",id));
 	       }
 	       FindIterable <Document> quesanswer = null;
 	       for(String s : alquesanswer){
 	    	   ObjectId id = new ObjectId(s);
 	    	   quesanswer = tc.find(in("_id",id));
 	       }
 	       
 	      if(quesfavtags != null)
 	    	  getHistory(question,quesfavtags,"interesting","question");
 	      if(quesviewedtags != null)
 	    	  getHistory(question,quesviewedtags,"interesting","question");
 	      if(quesvieweduser != null)
 	    	  getHistory(question,quesvieweduser,"interesting","question");
 	      if(quesfollowing != null)
 	    	  getHistory(question,quesfollowing,"interesting","question");
 	      if(quesbookmark != null)
 	    	  getHistory(question,quesbookmark,"interesting","question");
 	      if(quesask != null)
 	    	  getHistory(question,quesask,"interesting","question");
 	      if(quesanswer != null)
 	      getHistory(question,quesanswer,"interesting","question");
 	      
 	      if(question != null)
 	    	  Collections.sort(question, Tile.homesort);
 	      System.out.println("alsearch"+question);
 	         return question;   
 	       
 	}
 	
 	/*public ArrayList<Tile> getHistory(String username)
	{
 		//needs to be tested, ClassCastException for long in Tile.java line 84
 	   MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
	   
	   Document doc = tc.find(eq("username",username)).first();
	   ArrayList<String> alquesbookmark = (ArrayList<String>) doc.get("question_bookmark");
	   ArrayList<String> alquesask = (ArrayList<String>) doc.get("question_ask");
	   ArrayList<String> alquesanswer = (ArrayList<String>) doc.get("question_answer");
	   
       
	   //project
	      //id
	   ArrayList<String> alcontributing = (ArrayList<String>) doc.get("contributing");
	   ArrayList<String> albookmark = (ArrayList<String>) doc.get("project_bookmark");
	   ArrayList<String> alfollowing = (ArrayList<String>) doc.get("following");
	   ArrayList<String> alfavtags = (ArrayList<String>) doc.get("favourite_tag");
	   
         // in tags
	   doc = (Document) doc.get("history");
       ArrayList<String> alviewedtags = (ArrayList<String>) doc.get("tag_view");
       ArrayList<String> alviewedproject = (ArrayList<String>) doc.get("project_view");
       ArrayList<String> alvieweduser = (ArrayList<String>) doc.get("user_view");
       
       tc =new DatabaseServices().getDb().getCollection("project");
       FindIterable <Document> docfavtags =  tc.find(in("tags",alfavtags));
       FindIterable <Document> docviewedtags =  tc.find(in("tags",alviewedtags));
       FindIterable <Document> docvieweduser =  tc.find(in("username",alvieweduser));
       FindIterable <Document> docfollowing =  tc.find(in("username",alfollowing));
       FindIterable <Document> docviewedproject =  tc.find(in("title",alviewedproject));
       FindIterable <Document> doccontributing = null;
       for(String s : alcontributing){
    	   ObjectId id = new ObjectId(s);
    	   doccontributing = tc.find(in("_id",id));
       }
      
       FindIterable <Document> docbookmark = null;
       for(String s : albookmark){
    	   ObjectId id = new ObjectId(s);
    	   docbookmark = tc.find(in("_id",id));
    	}
       
       tc =new DatabaseServices().getDb().getCollection("qa");
       FindIterable <Document> quesfavtags =  tc.find(in("tags",alfavtags));
       FindIterable <Document> quesviewedtags =  tc.find(in("tags",alviewedtags));
       FindIterable <Document> quesvieweduser =  tc.find(in("username",alvieweduser));
       FindIterable <Document> quesfollowing =  tc.find(in("username",alfollowing));
       FindIterable <Document> quesbookmark = null;
       for(String s : alquesbookmark){
    	   ObjectId id = new ObjectId(s);
    	   quesbookmark = tc.find(in("_id",id));
       }
       
       FindIterable <Document> quesask = null;
       for(String s : alquesask){
    	   System.out.println(s+"###############################################################");
    	   ObjectId id = new ObjectId(s);
    	   quesask = tc.find(in("_id",id));
       }
       FindIterable <Document> quesanswer = null;
       for(String s : alquesanswer){
    	   ObjectId id = new ObjectId(s);
    	   quesanswer = tc.find(in("_id",id));
       }
       
      ArrayList<Tile> altl = new ArrayList<Tile>();
      HashSet<Object> hs = new HashSet<Object>(); 
      if(doccontributing != null)
    	  getHistory(altl,doccontributing,"contributing","project");
      if(docbookmark != null)
    	  getHistory(altl,docbookmark,"project bookmark","project");
      if(docfavtags != null)
    	  getHistory(altl,docfavtags,"favtags","project");
      if(docviewedtags != null)
    	  getHistory(altl,docviewedtags,"viewed tags","project");
      if(docvieweduser != null)
    	  getHistory(altl,docvieweduser,"vieweduser","project");
      if(docviewedproject != null)
    	  getHistory(altl,docviewedproject,"viewedproject","project");
      if(docfollowing != null)
    	  getHistory(altl,docfollowing,"following","project");
      if(quesfavtags != null)
    	  getHistory(altl,quesfavtags,"favtags","question");
      if(quesviewedtags != null)
    	  getHistory(altl,quesviewedtags,"viewed tags","question");
      if(quesvieweduser != null)
    	  getHistory(altl,quesvieweduser,"vieweduser","question");
      if(quesfollowing != null)
    	  getHistory(altl,quesfollowing,"following","question");
      if(quesbookmark != null)
    	  getHistory(altl,quesbookmark,"question bookmark","question");
      if(quesask != null)
    	  getHistory(altl,quesask,"asked question","question");
      if(quesanswer != null)
      getHistory(altl,quesanswer,"answered question","question");
      
      if(altl != null)
    	  Collections.sort(altl, Tile.homesort);
      System.out.println("alsearch"+altl);
         return altl;   
	}*/

 	
	/*public void getProjects(String id)
	{
		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("project");
		FindIterable<Document> fi =  tc.find().sort(new Document("date",1));
	}*/
	public void getHistory(ArrayList<Tile> altl, FindIterable<Document> fi,String source, String subject)
	{
		for(Document d:fi)
	      {	if(!hm.containsKey(d.get("_id")))
		      	{   
	    	  		Tile tl = new GeneralServices().returnTile(d,source,subject);
		      	    hm.put(d.get("_id"),tl);
		      		altl.add(tl);
		      	}
		      	else{
	        	 Tile tl =    hm.get(d.get("_id"));
	        	 int i = tl.getPositioncount()+1;
	        	 ArrayList <String> al = tl.getSource();
	        	 //al.add(source);
	        	 tl.setSource(al);
	 	      	tl.setPositioncount(i);
	 	      	hm.put(d.get("_id"),tl);
	         	}
	      }
	}
	
	public static ArrayList<Tile> trendingQuestion()
	{
// test pending
		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("qa");
		FindIterable <Document> fi =tc.find().sort(new Document("date",-1).append("upvotecount", -1).append("downvotecount",1));
		ArrayList<Tile> ques = new ArrayList<Tile>();
		for(Document d:fi)
		{Tile doc = new Tile();
		        doc.setUsername(d.getString("username"));
		        doc.setTitle(d.getString("question"));
		        doc.setUpvotecount(d.getLong("upvotecount"));
		        doc.setDownvotecount(d.getLong("downvotecount"));
		        // description might throw error check database 
		        doc.setDescription(d.getString("description"));
		        doc.setUrl(d.getString("question_url"));
		        ArrayList<String> altwo = new ArrayList<String>();
		        if(altwo.size() < 1)
		        altwo.add("trending");
		        doc.setSource(altwo);
		        ques.add(doc);
		        System.out.println(ques);
		}
		return ques;
	}
	
	public static ArrayList<Tile> getTrendingProject(ArrayList<Tile> projects)
	{
// test pending
		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("project");
		FindIterable <Document> fi =tc.find().sort(new Document("date",-1).append("upvotecount", -1).append("downvotecount",1));
		ArrayList<Tile> project = new ArrayList<Tile>();
		for(Document d:fi)
		{Tile doc = new Tile();
		        doc.setUsername(d.getString("username"));
		        doc.setTitle(d.getString("title"));
		        doc.setUpvotecount(d.getLong("upvotecount"));
		        doc.setDownvotecount(d.getLong("downvotecount"));
		        // description might throw error check database 
		        doc.setDescription(d.getString("description"));
		        doc.setUrl(d.getString("project_url"));
		        ArrayList<String> altwo = new ArrayList<String>();
		        if(altwo.size() < 1)
		        	altwo.add("trending");
		        doc.setSource(altwo);
		        project.add(doc);
		        System.out.println(project);
		}
		return project;
	}
	public ArrayList<User> topUsers(){
		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
		FindIterable<Document> fi = tc.find().sort(new Document("rating",-1)).limit(100);
		ArrayList<User> userList = new ArrayList<User>();
		for(Document d : fi){
			User user = new User();
			user.setUsername(d.getString("username"));
			user.setBio(d.getString("bio"));
			user.setName(d.getString("name"));
			user.setCategory(d.getString("category"));
			user.setInstitute(d.getString("institute"));
			user.setRating(d.getInteger("rating"));
			userList.add(user);
		}
		return userList;
	}
}
