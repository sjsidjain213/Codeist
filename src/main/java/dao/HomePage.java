package dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import bean.Tile;
import service.DatabaseServices;
import service.GeneralServices;

public class HomePage {
	  static HashMap<Object,Tile> hm = new HashMap<Object,Tile>();
	    
	@SuppressWarnings("unchecked")
	public ArrayList<Tile> getHistory(String username)
	{
	   MongoCollection<Document> tc =new DatabaseServices().getDb().getCollection("userdata");
	   Document doc = tc.find(eq("username",username)).first();
       
	   ArrayList<String> alfavtags = (ArrayList<String>) doc.get("favourite_tags");
       doc = (Document) doc.get("history");
       ArrayList<String> alviewedtags = (ArrayList<String>) doc.get("tags_viewed");
       ArrayList<String> alvieweduser = (ArrayList<String>) doc.get("user_viewed");
       ArrayList<String> alviewedproject = (ArrayList<String>) doc.get("project_viewed");

        tc =new DatabaseServices().getDb().getCollection("project");
       FindIterable <Document> docfavtags =  tc.find(in("tags",alfavtags));
       FindIterable <Document> docviewedtags =  tc.find(in("tags",alviewedtags));
       FindIterable <Document> docvieweduser =  tc.find(in("username",alvieweduser));
       FindIterable <Document> docviewedproject =  tc.find(in("title",alviewedproject));
       
       ArrayList<Tile> altl = new ArrayList<Tile>();
       GeneralServices gs = new GeneralServices();
       gs.getHistory(hm, altl, docfavtags);
       gs.getHistory(hm, altl, docviewedtags);
       gs.getHistory(hm, altl, docvieweduser);
       gs.getHistory(hm, altl,docviewedproject);
       return altl;   
	}
	
}
