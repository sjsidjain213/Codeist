package dao;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import service.DatabaseServices;

public class RegionDao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("region");
	public ArrayList<Document> getState()
	{ArrayList<Document> al = new ArrayList<Document>();
	
Document doc = tc.find().first();	
Document document = (Document) doc.get("state");
al.add(document);
	return al;
	}
}
