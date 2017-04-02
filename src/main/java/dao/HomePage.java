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
import bean.SearchBean;
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
 	
 	public ArrayList<Tile> getRelatedProject(String question_id)
 	{
 		// username of loggedin, question_id of new inserted question, altags of question 
 		ArrayList<Tile> project = new ArrayList<Tile>();
 		ObjectId oid=new ObjectId(question_id.toString());
 		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("qa");
 		Document doc = tc.find(eq("_id",oid)).first();
 		ArrayList<String> altags = (ArrayList<String>)doc.get("tags");
 		String username = doc.getString("username");
 		System.out.println(altags);
 		project = getInterestRelatedProject(project,username,altags,question_id);
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
 	// interest
 	//one more thing is required is question id which is inserted
 	@SuppressWarnings("unchecked")
	public ArrayList<Tile> getInterestRelatedProject(ArrayList<Tile> project, String username,ArrayList<String> altags,String question_id)
 	{
 		ObjectId oid=new ObjectId(question_id.toString());
 		System.out.println("in*****"+altags);
 		HashMap<Object,Tile> hm = new HashMap<Object,Tile>();
 		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("project");
 		MongoCollection<Document> tcqa =new DatabaseServices().getDb().getCollection("qa");
 		Document dd = tcqa.find(eq("_id",oid)).first();
 		ArrayList<Tile> tempList = new ArrayList<Tile>();
 		ArrayList<Tile> finalList = new ArrayList<Tile>();
 		FindIterable <Document> quesfavtags =  (FindIterable<Document>)tc.find(in("tags",altags));
 		for(Document d : quesfavtags)
 		System.out.println("***"+d);
 		tc =new DatabaseServices().getDb().getCollection("institute");
        FindIterable <Document> institute =  tc.find(in("tags",altags));
        new GeneralServices().addProblem(institute,question_id);    
    //project is the arraylist in which we want to add feed
    //public void getHistory(ArrayList<Tile> altl, FindIterable<Document> fi,String source, String subject)
    //sorted according to matched count, region and owner (if owner is institute then high priority)
 		if(quesfavtags != null) {		
	    	 finalList.addAll(getHistory(project,quesfavtags,"matched","p",oid));
 		
 		}
 		System.out.println("final**"+finalList);
               Collections.sort(finalList,Tile.matchsort);
               Collections.sort(finalList,Tile.statussort);
               System.out.println("final++++"+finalList);
 		return finalList;      
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
 	
	public ArrayList<Tile> getHistory(ArrayList<Tile> altl, FindIterable<Document> fi,String source, String subject,ObjectId oid)
	{//subject is either question or answer and source is interesting and trending
		MongoCollection<Document> tcqa =new DatabaseServices().getDb().getCollection("qa");
 		Document dd = tcqa.find(eq("_id",oid)).first();
 		HashMap<Object,Tile> hm1 = new HashMap<Object,Tile>();
 		for(Document d : fi)
 			System.out.println("8888888"+d);
		for(Document doc:fi)
	      {	if(!hm1.containsKey(doc.get("_id")))
		      	{   
	    	  		Tile tl = new GeneralServices().returnTile(doc,source,subject);
	    	  		
	    	                //Tile tile = new Tile();
	    	            	   
	    	            	    //tile.setUsername(doc.getString("username"));
	    	            	    //tile.setId(doc.get("_id").toString());
	    	            	    System.out.println("##"+doc.getString("state")+"    "+dd.getString("state")+"    "+doc);
	    	            	    System.out.println("000 "+(dd.getString("state").equals(doc.getString("state")) && dd.getString("city").equals(doc.getString("city"))));
	    	            	    //tile.setMatchedcount(1);
	    	            	    if((dd.getString("state").equals(doc.getString("state"))) && dd.getString("city").equals(doc.getString("city"))){
	    	            	    	tl.setStatus(2);
	    	            	    	System.out.println("here");
	    	            	    }
	    	            	    else if(dd.getString("state").equals(doc.getString("state"))){
	    	            	    	tl.setStatus(1);
	    	            	    	System.out.println("also");
	    	            	    }
	    	            	    else {
	    	            	    	tl.setStatus(0);
	    	            	    	System.out.println("toooo");
	    	            	    }
	    	            	    System.out.println(tl);
	    	            	   // boolean bool =(doc.getString("username").equals(s)||doc.getString("name").equals(s))?user.setPriority("z"):user.setPriority("b");    
	    	           	        altl.add(tl); 
	    	           	        System.out.println(altl);
	    	                    hm1.put(doc.get("_id"),tl);
	    	                    System.out.println("project"+altl);
	    	            }else{
	    	            Tile sb=hm1.get(doc.get("_id"));
	    	            if(sb!=null)
	    	            {
	    	            	sb.setMatchedcount((sb.getMatchedcount()+1));
	    	            	System.out.println("sb     "+sb);
	    	              hm1.put(doc.get("_id"),sb);
	    	            }
	    	            }
	    	        }
	    	   //question id is added in institute
	    	               
	    	               /*for(Document doc : institute)
	    	               {
	    	            	   if(!hm.containsKey(doc.get("_id"))){
	    	                       //Tile tile = new Tile();
	    	                   	   
	    	                   	    //tile.setUsername(doc.getString("username"));
	    	                   	    //tile.setId(doc.get("_id").toString());
	    	                   	    System.out.println("##"+doc.getString("state")+"    "+dd.getString("state"));
	    	                   	    System.out.println("$$ "+dd.getString("state").equals(doc.getString("state")));
	    	                   	    tile.setMatchedcount(1);
	    	                   	    if((dd.getString("state").equals(doc.getString("state"))) && dd.getString("city").equals(doc.getString("city")))
	    	                   	    	tile.setStatus(2);
	    	                   	    else if(dd.getString("state").equals(doc.getString("state")))
	    	                   	    	tile.setStatus(1);
	    	                   	    else 
	    	                   	    	tile.setStatus(0);
	    	                   	   // boolean bool =(doc.getString("username").equals(s)||doc.getString("name").equals(s))?user.setPriority("z"):user.setPriority("b");    
	    	                  	        project.add(tile);   
	    	                           hm.put(doc.get("_id"),tile);
	    	                   }else{
	    	                   Tile sb=hm.get(doc.get("_id"));
	    	                   if(sb!=null)
	    	                   {
	    	                   	sb.setMatchedcount((sb.getMatchedcount()+1));
	    	                     hm.put(doc.get("_id"),sb);
	    	                   }
	    	                   }
	    	               }}
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
	      }*/
		return altl;
	}
	
	public void getHistory(ArrayList<Tile> altl, FindIterable<Document> fi,String source, String subject)
	{//subject is either question or answer and source is interesting and trending
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
	
	@SuppressWarnings("unchecked")
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
		        Document innerdoc = (Document)d.get("info");
		   		if(innerdoc!=null){
		   		doc.setUpvotes((ArrayList<String>)innerdoc.get("upvotes"));
		   		doc.setDownvotes((ArrayList<String>)innerdoc.get("downvotes"));
		   		
		   		}
		        ArrayList<String> altwo = new ArrayList<String>();
		        if(altwo.size() < 1)
		        altwo.add("trending");
		        doc.setSource(altwo);
		        ques.add(doc);
		        System.out.println(ques);
		}
		return ques;
	}
	
	@SuppressWarnings("unchecked")
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
		        doc.setId(d.get("_id").toString());
		        doc.setUpvotecount(d.getLong("upvotecount"));
		        doc.setDownvotecount(d.getLong("downvotecount"));
		        Document innerdoc = (Document)d.get("info");
		   		if(innerdoc!=null){
		   		doc.setUpvotes((ArrayList<String>)innerdoc.get("upvotes"));
		   		doc.setDownvotes((ArrayList<String>)innerdoc.get("downvotes"));
		   		
		   		}
		        doc.setUrl_title(GeneralServices.spaceRemover(d.getString("title")));
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