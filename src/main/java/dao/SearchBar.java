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
	 MongoCollection<Document>tctwo = new DatabaseServices().getDb().getCollection("project");
        HashMap<Object,SearchBean> hmuser = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmproject = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmtags = new HashMap<Object,SearchBean>();
	    ArrayList<SearchBean> alsearch =new ArrayList<SearchBean>();
        ArrayList <String> al = tag.getTags();
	
        al.forEach( s-> {
        	FindIterable <Document> fi = tc.find(or(eq("username",Pattern.compile("(?i:.*"+s+".*)")),eq("name",Pattern.compile("(?i:.*"+s+".*)"))));	
            fi.forEach((Block<Document>) doc -> {
        	if(!hmuser.containsKey(doc.get("_id"))){
            SearchBean user = new SearchBean();
        	    user.setSource("name");
        	    user.setUsername(doc.getString("username"));
        	    user.setBio(doc.getString("bio"));
        	    user.setName(doc.getString("name"));
        	    user.setMatchedcount(1);
        	    boolean bool =(doc.getString("username").equals(s)||doc.getString("name").equals(s))?user.setPriority("high"):user.setPriority("low");    
       	        alsearch.add(user);   
                hmuser.put(doc.get("_id"),user);
        }else{
        SearchBean sb=hmuser.get(doc.get("_id"));
        if(sb!=null)
        {
        	sb.setMatchedcount((sb.getMatchedcount()+1));
            String source = sb.getSource();
           // source = source+":name";
            sb.setSource(source);
        	hmuser.put(doc.get("_id"),sb);
        }
        }
        });

        fi = tctwo.find(or(eq("title",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hmproject.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("title");
  	             project.setTitle(doc.getString("title"));
  	             project.setDescription(doc.getString("description"));
  	             project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("title").matches("(?i:"+s+")"))?project.setPriority("high"):project.setPriority("low");    
        	     alsearch.add(project);
                 hmproject.put(doc.get("_id"),project);
        	     }else{
                    SearchBean sb=hmproject.get(doc.get("_id"));
        	         if(sb!=null)
        	         {
        	         	sb.setMatchedcount((sb.getMatchedcount()+1));
        	            String source = sb.getSource();
        	      //      source = source+ ":title";
        	            sb.setSource(source);
        	         	hmproject.put(doc.get("_id"),sb);
        	         }	    	 
        	     }
        });
       
        fi = tctwo.find(or(eq("description",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hmproject.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("description");
  	             project.setTitle(doc.getString("title"));
  	             project.setDescription(doc.getString("description"));
  	             project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("title").matches("(?i:"+s+")"))?project.setPriority("low"):project.setPriority("lowest");    
        	     alsearch.add(project);
                 hmproject.put(doc.get("_id"),project);
        	     }else{
                     SearchBean sb=hmproject.get(doc.get("_id"));
         	         if(sb!=null)
         	         {
         	         	sb.setMatchedcount((sb.getMatchedcount()+1));
         	            String source = sb.getSource();
         	    //       source = source+ ":description";
       	                sb.setSource(source);
         	         	hmproject.put(doc.get("_id"),sb);
         	         }	    	 
         	     }
        });
       
        
        fi = tctwo.find();
        Pattern pattern = Pattern.compile("(?i:.*"+s+".*)");
        fi.forEach((Block<Document>)  doc-> {
        ArrayList<String> altags = (ArrayList<String>)doc.get("tags");
        System.out.println("tags size"+altags.size()+":::"+altags);
        System.out.println("hmtags size"+hmtags.size());
        altags.forEach(st -> {
        	  if(st.matches("(?i:.*"+s+".*)")&&!hmtags.containsKey(doc.get("_id")))
        	  {  SearchBean project = new SearchBean();
                 project.setSource("tags");
	             project.setTitle(doc.getString("title"));
	             project.setDescription(doc.getString("description"));
	             project.setTags((ArrayList<String>)doc.get("tags"));
	             project.setUsername(doc.getString("username"));
	             project.setUrl(doc.getString("project_url"));
	             project.setMatchedcount(1);
	             boolean bool =(st.matches("(?:"+s+")"))?project.setPriority("high"):project.setPriority("low");    
      	         alsearch.add(project);
      	         hmtags.put(doc.get("_id"),project);
      	         System.out.println("hmtags updated size"+hmtags.size()+"::"+st);
        	  }else if(st.matches("(?i:.*"+s+".*)")){
                  SearchBean sb=hmtags.get(doc.get("_id"));
                  System.out.println("else");
                 if(sb!=null)
      	         { 
           	    if(st.matches("(?:"+s+")"))
           	    {
           	    	if(sb.getPriority().equals("low"))
           	    	{
           	    		sb.setPriority("high");
           	    	}
           	    }
                long count = sb.getMatchedcount()+1;	
      	         System.out.println("else id"+doc.get("_id")+st+"::"+count);
      	         sb.setMatchedcount((count));
      	            String source = sb.getSource();
      	            sb.setSource(source);
      	         	hmtags.put(doc.get("_id"),sb);
      	         }	    	 
      	     }
          });
        });}); 
         return alsearch;
	}
}
