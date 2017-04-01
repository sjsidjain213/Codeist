package pdao;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import bean.Tile;
import service.DatabaseServices;
import service.GeneralServices;

public class HomePage {
	HashMap<Object,Tile> hm = new HashMap<Object,Tile>();
 	
	public ArrayList<Tile> getQuestions(String username)
 	{
 		ArrayList<Tile> question = new ArrayList<Tile>();
 		  question = getInterestQuestion(question, username);
 		  return question;
 	}
 	
	
	public ArrayList<Tile> getInterestQuestion(ArrayList<Tile> question, String username)
	{
		 MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
	Document doc = tc.find(eq("username",username)).first();
	// all tags of user
	ArrayList<String> alfavtags = (ArrayList<String>) doc.get("favourite_tag");
		
	// matched questions
		 tc =new DatabaseServices().getDb().getCollection("qa");
	     FindIterable <Document> quesfavtags =  tc.find(in("tags",alfavtags));
	   
	     if(quesfavtags != null)
	//question is the arraylist in which response will be stored
	    	 getHistory(question,quesfavtags,"interesting","question");
	          
		return question;
	}

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
	/*
	public ArrayList<Tile> getInterestQuestion(ArrayList<Tile> question, String username)
 	{
 		 MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
 		   
 		   Document doc = tc.find(eq("username",username)).first();
 		   ArrayList<String> alquesbookmark = (ArrayList<String>) doc.get("question_bookmark");
 		   ArrayList<String> alquesask = (ArrayList<String>) doc.get("question_ask");
 		   ArrayList<String> alquesanswer = (ArrayList<String>) doc.get("question_answer");
 		   ArrayList<String> alfollowing = (ArrayList<String>) doc.get("following");
 		   
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
 	
	*/
}
