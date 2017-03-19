package service;
import java.util.*;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import bean.Acknowledgement;
import bean.Tile;
import interfaces.service.AtoSCon;
import interfaces.service.StoACon;
import interfaces.service.StoAcknowCon;

public class GeneralServices{

public String atosmethod(AtoSCon<String> ats,ArrayList<String> al)
{
return ats.convertAtoS(al);
}

public Acknowledgement stoacknowmethod(StoAcknowCon stacknow,String acknow)
{
return stacknow.convertStoAcknow(acknow);
}

public void search(String data)
{
// Write logic here for using search bar change signature of method according to requirments
	
}

public void getHistory(HashMap<Object,Tile> hm,ArrayList<Tile> al,FindIterable<Document> fi)
{
	fi.forEach((Block<Document>) d -> {
      	if(!hm.containsKey(d.get("_id")))
      	{ 
    	Tile tl = new Tile();
      		tl.set_id(d.get("_id"));
      		tl.setTitle(d.getString("title"));
      		tl.setDescription(d.getString("description"));
      		tl.setTags((ArrayList<String>)d.get("tags"));
      		tl.setUsername(d.getString("username"));
      	    //tl.setPositioncount(1);
      		d = (Document) d.get("info");
      		tl.setUpvotes(d.getDouble("upvotes").intValue());
      		tl.setDownvotes(d.getDouble("downvotes").intValue());
      		tl.setViewcount(d.getDouble("viewcount").intValue());
      		al.add(tl);
      		hm.put(d.get("_id"),tl);
      	}
      });

}
}
