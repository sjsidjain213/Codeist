package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import bean.Tile;
import service.DatabaseServices;
import service.GeneralServices;

public class HomePage {
    HashMap<Object,Tile> hm = new HashMap<Object,Tile>();
 	@SuppressWarnings("unchecked")
	public ArrayList<Tile> getHistory(String username)
	{MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
	   
	   Document doc = tc.find(eq("username",username)).first();
	   ArrayList<String> alfavtags = (ArrayList<String>) doc.get("favourite_tag");
       doc = (Document) doc.get("history");
       ArrayList<String> alviewedtags = (ArrayList<String>) doc.get("tag_view");
       ArrayList<String> alvieweduser = (ArrayList<String>) doc.get("user_view");
       ArrayList<String> alviewedproject = (ArrayList<String>) doc.get("project_view");

       tc =new DatabaseServices().getDb().getCollection("project");
       FindIterable <Document> docfavtags =  tc.find(in("tags",alfavtags));
       FindIterable <Document> docviewedtags =  tc.find(in("tags",alviewedtags));
       FindIterable <Document> docvieweduser =  tc.find(in("username",alvieweduser));
       FindIterable <Document> docviewedproject =  tc.find(in("title",alviewedproject));
       
      ArrayList<Tile> altl = new ArrayList<Tile>();
      HashSet<Object> hs = new HashSet<Object>(); 
      getHistory(altl,docfavtags,"favtags");
      getHistory(altl,docviewedtags,"viewed tags");
      getHistory(altl,docvieweduser,"vieweduser");
      getHistory(altl,docviewedproject,"viewedproject");
       return altl;   
	}
 	
	public void getProjects(String id)
	{
		MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("project");
		FindIterable<Document> fi =  tc.find().sort(new Document("date",1));
	}
	public void getHistory(ArrayList<Tile> altl, FindIterable<Document> fi,String source)
	{
		for(Document d:fi)
	      {	if(!hm.containsKey(d.get("_id")))
		      	{   Tile tl = new GeneralServices().returnTile(d,source);
		      	    hm.put(d.get("_id"),tl);
		      		altl.add(tl);
		      	}
		      	else{
	        	 Tile tl =    hm.get(d.get("_id"));
	        	 int i = tl.getPositioncount()+1;
	        	 ArrayList <String> al = tl.getSource();
	        	 al.add(source);
	        	 tl.setSource(al);
	 	      	tl.setPositioncount(i);
	 	      	hm.put(d.get("_id"),tl);
	         	}
	      }
	}
}
