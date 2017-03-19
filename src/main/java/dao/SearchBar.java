package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import bean.Project;
import bean.SearchBean;
import bean.Tag;
import bean.User;
import service.DatabaseServices;

public class SearchBar {

	
	public ArrayList<SearchBean> doSearch(Tag tag)
	{MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("userdata");
	    HashMap<Object,SearchBean> hmuser = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmproject = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmtags = new HashMap<Object,SearchBean>();
		
	    ArrayList <String> al = tag.getTags();
	    /*on basis of userdata collection -> username
		              project collection -> title
		              project collection -> tags
		 */
		String s = al.get(0);
       	FindIterable <Document> fi = tc.find(or(eq("username",Pattern.compile("(?i:.*"+s+".*)")),eq("name",Pattern.compile("(?i:.*"+s+".*)"))));	
        ArrayList<SearchBean> alsearch =new ArrayList<SearchBean>();
       	
        fi.forEach((Block<Document>) doc -> {
        	if(!hmuser.containsKey(doc.get("_id"))){
          SearchBean user = new SearchBean();
        	    user.setSource("username and name");
        	    user.setUsername(doc.getString("username"));
        	    user.setBio(doc.getString("bio"));
        	    user.setName(doc.getString("name"));
        	    boolean bool =(doc.getString("username").equals(s)||doc.getString("name").equals(s))?user.setPriority("high"):user.setPriority("low");    
       	        alsearch.add(user);   
                hmuser.put(doc.get("_id"),user);
        }
        });

        tc = new DatabaseServices().getDb().getCollection("project");
        fi = tc.find(or(eq("title",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hmproject.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("title");
  	             project.setTitle(doc.getString("title"));
  	             project.setDescription(doc.getString("description"));
  	             project.setTags((ArrayList<String>)doc.get("tags"));
  	             boolean bool =(doc.getString("title").matches("(?i:"+s+")"))?project.setPriority("high"):project.setPriority("low");    
        	     alsearch.add(project);
                 hmproject.put(doc.get("_id"),project);
        	     }
        });
        
        fi = tc.find();
        Pattern pattern = Pattern.compile("(?i:.*"+s+".*)");
        fi.forEach((Block<Document>)  doc-> {
        ArrayList<String> altags = (ArrayList<String>)doc.get("tags");
        altags.forEach(st -> {
        	  if(st.matches("(?i:.*"+s+".*)")&&!hmtags.containsKey(doc.get("_id")))
        	  { SearchBean project = new SearchBean();
                 project.setSource("tags");
	             project.setTitle(doc.getString("title"));
	             project.setDescription(doc.getString("description"));
	             project.setTags((ArrayList<String>)doc.get("tags"));
	         project.setPriority("high");
	         boolean bool =(st.matches("(?:"+s+")"))?project.setPriority("high"):project.setPriority("low");    
      	         alsearch.add(project);
      	          hmtags.put(doc.get("_id"),project);
        	  }
          });
        }); 
         return alsearch;
	}
}
