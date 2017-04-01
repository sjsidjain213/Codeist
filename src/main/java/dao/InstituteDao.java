package dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import service.DatabaseServices;

public class InstituteDao {
	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("institute");
	public void getQuestions(String id)
	{
tc.find();
		
	}
}
