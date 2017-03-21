package service;
import java.util.*;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import bean.Acknowledgement;
import bean.Tag;
import bean.Tile;
import interfaces.service.AtoSCon;
import interfaces.service.StoACon;
import interfaces.service.StoAcknowCon;

public class GeneralServices{
static final HashMap<Object,Tile> hm = new HashMap<Object,Tile>();
public String atosmethod(AtoSCon<String> ats,ArrayList<String> al)
{
return ats.convertAtoS(al);
}

public Acknowledgement stoacknowmethod(StoAcknowCon stacknow,String acknow)
{
return stacknow.convertStoAcknow(acknow);
}

@SuppressWarnings("unchecked")
public boolean match(Tag tags,ArrayList<String> tag)
{
	try{
	for (String temp: tags.getTags()){
        if(tag.contains(temp)){
        	return true;
        }
	}}
	catch(Exception e){
		e.printStackTrace();
	}
	return false;
}

public Tile returnTile(Document d,String source)
{
	Tile tl = new Tile();
	    tl.set_id(d.get("_id"));
	    ArrayList<String> altwo = new ArrayList<String>();
	    altwo.add(source);
	    tl.setSource(altwo);
		tl.setTitle(d.getString("title"));
		tl.setDescription(d.getString("description"));
		tl.setTags((ArrayList<String>)d.get("tags"));
		tl.setUsername(d.getString("username"));
	    tl.setPositioncount(1);
		d = (Document) d.get("info");
		tl.setUpvotes(d.getDouble("upvotes").intValue());
		tl.setDownvotes(d.getDouble("downvotes").intValue());
		tl.setViewcount(d.getDouble("viewcount").intValue());
	return tl;	
}


}
