package service;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseServices {
	static private MongoClientURI uri  = new MongoClientURI("mongodb://sjsidjain:sjsidjain@ds145359.mlab.com:45359/testhack"); 
    static private MongoClient client = new MongoClient(uri);
    static private MongoDatabase db = client.getDatabase(uri.getDatabase());
    MongoCollection <Document> tc = db.getCollection("testcol");
	
    public MongoClientURI getUri() {
		return uri;
	}
	public MongoClient getClient() {
		return client;
	}
	public MongoCollection<Document> getTcUserData() {
		return tc;
	}
	public void setTc(MongoCollection<Document> tc) {
		this.tc = tc;
	}
	public MongoDatabase getDb() {
		return db;
	}
	
	
}
