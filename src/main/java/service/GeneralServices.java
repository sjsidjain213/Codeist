package service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

public Acknowledgement response(String s)
{
	if(s!=null&&s.equals("insert"))
	{
		Acknowledgement ac2 = new Acknowledgement();
	       ac2.setMatchedCount("1");
	       ac2.setModifiedCount("1");
	       ac2.setUpsertedId("0");
	    return ac2;
	}
	else if(s!=null)
	{ Acknowledgement ac2 = new Acknowledgement();
    String sa [] = s.substring(s.indexOf("{")+1,s.indexOf("}")).split(",");
       ac2.setMatchedCount(sa[0]);
       ac2.setModifiedCount(sa[1]);
       ac2.setUpsertedId(sa[2]);
    return ac2;
	}
	else {
		Acknowledgement ac2 = new Acknowledgement();
		ac2.setMatchedCount("0");
		ac2.setModifiedCount("0");
		ac2.setUpsertedId("0");
		ac2.setMsg("user is not logged in");
		return ac2;
	}
}

<<<<<<< HEAD
=======

>>>>>>> origin/master
public String spaceRemover(String s)
{
s=	s.replaceAll(" ", "-");
return s;	
}
public static String get_SHA_256_SecurePassword(String username,String passwordToHash)
{
	byte[] salt =username.getBytes();
	String generatedPassword = null;
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
    } 
    catch (NoSuchAlgorithmException e) 
    {
        e.printStackTrace();
    }
    return generatedPassword;
}

}
