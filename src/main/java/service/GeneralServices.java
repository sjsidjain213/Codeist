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
static int count =0;
@SuppressWarnings("unchecked")
public HashMap<Object,Tile> getHistory(HashMap<Object,Tile> hm,ArrayList<Tile> al,FindIterable<Document> fi,String source,HashSet<Object> hs)
{   System.out.println(hm.size()+"this is size"+(++count));
	fi.forEach((Block<Document>) d -> {
      	if(hs.add(d.get("_id")))
      	{   System.out.println(d.get("_id").toString()+hm.size());       
      	    Tile tl = new Tile();
      		hs.add(d.get("_id"));
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
      		al.add(tl);
      		hm.put(d.get("_id"),tl);
      	}
      	else{
      		Tile tile = hm.get(d.get("_id"));
            System.out.println("inside");
            //alqq.add(source);
          //  tile.setSource(alqq);
      	//	tile.setPositioncount(tile.getPositioncount()+1); 
      System.out.println(hm.get(d.get("_id"))+"**");
      		al.add(tile);
      	}
      });
return hm;
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
