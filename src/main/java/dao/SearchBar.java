package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;

import bean.SearchBean;
import bean.Tag;
import service.DatabaseServices;

public class SearchBar {

	
	public ArrayList<SearchBean> doSearch(Tag tag)
	{
		MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("testuserdata");
	    MongoCollection<Document>tctwo = new DatabaseServices().getDb().getCollection("project");
		MongoCollection <Document> tcques = new DatabaseServices().getDb().getCollection("qa");
        HashMap<Object,SearchBean> hm = new HashMap<Object,SearchBean>();
	    //HashMap<Object,SearchBean> hmproject = new HashMap<Object,SearchBean>();
	    //HashMap<Object,SearchBean> hmtags = new HashMap<Object,SearchBean>();
	    //HashMap<Object,SearchBean> hmques = new HashMap<Object,SearchBean>();
	    ArrayList<SearchBean> alsearch =new ArrayList<SearchBean>();
        ArrayList <String> al = tag.getTags();
	
        al.forEach( s-> {
        	//search on basis of user
        	FindIterable <Document> fi = tc.find(or(eq("username",Pattern.compile("(?i:.*"+s+".*)")),eq("name",Pattern.compile("(?i:.*"+s+".*)"))));	
            fi.forEach((Block<Document>) doc -> {
        	if(!hm.containsKey(doc.get("_id"))){
            SearchBean user = new SearchBean();
        	    user.setSource("name");
        	    user.setUsername(doc.getString("username"));
        	    user.setBio(doc.getString("bio"));
        	    user.setName(doc.getString("name"));
        	    user.setRating(doc.getLong("rating"));
        	    user.setId(doc.get("_id").toString());
        	    user.setMatchedcount(1);
        	    boolean bool =(doc.getString("username").equals(s)||doc.getString("name").equals(s))?user.setPriority("z"):user.setPriority("b");    
       	        alsearch.add(user);   
                hm.put(doc.get("_id"),user);
        }else{
        SearchBean sb=hm.get(doc.get("_id"));
        if(sb!=null)
        {
        	sb.setMatchedcount((sb.getMatchedcount()+1));
            String source = sb.getSource();
           // source = source+":name";
            sb.setSource(source);
        	hm.put(doc.get("_id"),sb);
        }
        }
        });

        //search on basis of project
        fi = tctwo.find(or(eq("title",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(doc.getString("title").matches("(?i:.*"+s+".*)")&&!hm.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("title");
  	             project.setTitle(doc.getString("title"));
  	             project.setDescription(doc.getString("description"));
  	             project.setId(doc.get("_id").toString());
         	     project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
//  	             Document innerd = (Document)doc.get("info");
//  	             if(innerd != null){
//  	            	 project.setUpvotecount(doc.getLong("upvotecount"));
//  	            	 //project.setUpvoteCount(innerd.getLong("upvotes"));
//  	            	 project.setDownvotecount(innerd.getLong("downvotecount"));
//  	             }
            	 project.setUpvotecount(doc.getLong("upvotecount"));
            	 project.setDownvotecount(doc.getLong("downvotecount"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("title").matches("(?i:"+s+")"))?project.setPriority("z"):project.setPriority("b");    
        	     alsearch.add(project);
                 hm.put(doc.get("_id"),project);
        	     }else{
                    SearchBean sb=hm.get(doc.get("_id"));
        	         if(sb!=null)
        	         {
        	         	sb.setMatchedcount((sb.getMatchedcount()+1));
        	            String source = sb.getSource();
        	      //      source = source+ ":title";
        	            sb.setSource(source);
        	         	hm.put(doc.get("_id"),sb);
        	         }	    	 
        	     }
        });
       
        //search on basis of project description
        fi = tctwo.find(or(eq("description",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hm.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("description");
  	             project.setTitle(doc.getString("title"));
  	             project.setDescription(doc.getString("description"));
  	             project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setId(doc.get("_id").toString());
       	         project.setUrl(doc.getString("project_url"));
//  	             Document innerd = (Document)doc.get("info");
//	             if(innerd != null){
//	            	 project.setUpvotecount(innerd.getLong("upvotecount"));
//	            	 project.setDownvotecount(innerd.getLong("downvotecount"));
//	             }
            	 project.setUpvotecount(doc.getLong("upvotecount"));
            	 project.setDownvotecount(doc.getLong("downvotecount"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("description").matches("(?i:"+s+")"))?project.setPriority("b"):project.setPriority("a");    
        	     alsearch.add(project);
                 hm.put(doc.get("_id"),project);
        	     }else{
                     SearchBean sb=hm.get(doc.get("_id"));
         	         if(sb!=null)
         	         {
         	         	sb.setMatchedcount((sb.getMatchedcount()+1));
         	            String source = sb.getSource();
         	    //       source = source+ ":description";
       	                sb.setSource(source);
         	         	hm.put(doc.get("_id"),sb);
         	         }	    	 
         	     }
        });
       
        //search on basis of region in tags
        fi = tctwo.find(or(eq("region_state",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hm.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("region");
  	             project.setTitle(doc.getString("title"));
  	             project.setDescription(doc.getString("description"));
  	             project.setId(doc.get("_id").toString());
       	         project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
//  	             Document innerd = (Document)doc.get("info");
//	             if(innerd != null){
//	            	 project.setUpvotecount(innerd.getLong("upvotecount"));
//	            	 project.setDownvotecount(innerd.getLong("downvotecount"));
//	             }
            	 project.setUpvotecount(doc.getLong("upvotecount"));
            	 project.setDownvotecount(doc.getLong("downvotecount"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("description").matches("(?i:"+s+")"))?project.setPriority("b"):project.setPriority("a");    
        	     alsearch.add(project);
                 hm.put(doc.get("_id"),project);
        	     }else{
                     SearchBean sb=hm.get(doc.get("_id"));
         	         if(sb!=null)
         	         {
         	         	sb.setMatchedcount((sb.getMatchedcount()+1));
         	            String source = sb.getSource();
         	    //       source = source+ ":description";
       	                sb.setSource(source);
         	         	hm.put(doc.get("_id"),sb);
         	         }	    	 
         	     }
        });
       
        //search on basis of region in tags
         fi = tctwo.find(or(eq("region_city",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hm.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("region");
  	             project.setTitle(doc.getString("title"));
  	             System.out.println(doc.get("_id")+"::"+(ObjectId)doc.get("_id"));
  	             project.setDescription(doc.getString("description"));
  	             project.setId(doc.get("_id").toString());
       	         project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
//  	             Document innerd = (Document)doc.get("info");
//	             if(innerd != null){
//	            	 project.setUpvotecount(innerd.getLong("upvotes"));
//	            	 project.setDownvotecount(innerd.getLong("downvotes"));
//	             }
            	 project.setUpvotecount(doc.getLong("upvotecount"));
            	 project.setDownvotecount(doc.getLong("downvotecount"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("description").matches("(?i:"+s+")"))?project.setPriority("b"):project.setPriority("a");    
        	     alsearch.add(project);
                 hm.put(doc.get("_id"),project);
        	     }else{
                     SearchBean sb=hm.get(doc.get("_id"));
         	         if(sb!=null)
         	         {
         	         	sb.setMatchedcount((sb.getMatchedcount()+1));
         	            String source = sb.getSource();
         	    //       source = source+ ":description";
       	                sb.setSource(source);
         	         	hm.put(doc.get("_id"),sb);
         	         }	    	 
         	     }
        });
        
        //search projects on basis of tags
        fi = tctwo.find();
        Pattern pattern = Pattern.compile("(?i:.*"+s+".*)");
        fi.forEach((Block<Document>)  doc-> {
        ArrayList<String> altags = (ArrayList<String>)doc.get("tags");
        System.out.println("tags size"+altags.size()+":::"+altags);
        System.out.println("hmtags size"+hm.size());
        altags.forEach(st -> {
        	  if(st.matches("(?i:.*"+s+".*)")&&!hm.containsKey(doc.get("_id")))
        	  {  SearchBean project = new SearchBean();
                 project.setSource("tags");
	             project.setTitle(doc.getString("title"));
	             project.setId(doc.get("_id").toString());
	             project.setDescription(doc.getString("description"));
	             project.setTags((ArrayList<String>)doc.get("tags"));
	             project.setUsername(doc.getString("username"));
	             project.setUrl(doc.getString("project_url"));
//	             Document innerd = (Document)doc.get("info");
//  	             if(innerd != null){
//  	          	 project.setUpvotecount(innerd.getLong("upvotecount"));
//            	 project.setDownvotecount(innerd.getLong("downvotecount"));
//             }
            	 project.setUpvotecount(doc.getLong("upvotecount"));
            	 project.setDownvotecount(doc.getLong("downvotecount"));
	             project.setMatchedcount(1);
	             boolean bool =(st.matches("(?:"+s+")"))?project.setPriority("z"):project.setPriority("b");    
      	         alsearch.add(project);
      	         hm.put(doc.get("_id"),project);
      	         System.out.println("hmtags updated size"+hm.size()+"::"+st);
        	  }else if(st.matches("(?i:.*"+s+".*)")){
                  SearchBean sb=hm.get(doc.get("_id"));
                  System.out.println("else");
                 if(sb!=null)
      	         { 
           	    if(st.matches("(?:"+s+")"))
           	    {
           	    	if(sb.getPriority().equals("b") || sb.getPriority().equals("a"))
           	    	{
           	    		sb.setPriority("z");
           	    	}
           	    }
                long count = sb.getMatchedcount()+1;	
      	         System.out.println("else id"+doc.get("_id")+st+"::"+count);
      	         sb.setMatchedcount((count));
      	            String source = sb.getSource();
      	            sb.setSource(source);
      	         	hm.put(doc.get("_id"),sb);
      	         }	    	 
      	     }
          });
        });
        

        //search on basis of region in tags
       // search bean project or question is same
        
        fi = tcques.find(or(eq("city",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	     if(!hm.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("region");
  	             project.setTitle(doc.getString("title"));
  	             project.setId(doc.get("_id").toString());
  	             project.setDescription(doc.getString("description"));
  	             project.setId(doc.get("_id").toString());
       	         project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
//  	             Document innerd = (Document)doc.get("info");
//	             if(innerd != null){
//	            	 project.setUpvotecount(innerd.getLong("upvotecount"));
//	            	 project.setDownvotecount(innerd.getLong("downvotecount"));
//	                 }
            	 project.setUpvotecount(doc.getLong("upvotecount"));
            	 project.setDownvotecount(doc.getLong("downvotecount"));
  	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("description").matches("(?i:"+s+")"))?project.setPriority("b"):project.setPriority("a");    
        	     alsearch.add(project);
                 hm.put(doc.get("_id"),project);
        	     }else{
                     SearchBean sb=hm.get(doc.get("_id"));
         	         if(sb!=null)
         	         {
         	         	sb.setMatchedcount((sb.getMatchedcount()+1));
         	            String source = sb.getSource();
         	    //       source = source+ ":description";
       	                sb.setSource(source);
         	         	hm.put(doc.get("_id"),sb);
         	         }	    	 
         	     }
        });
        

        //search on basis of region in question
         fi = tcques.find(or(eq("state",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        		System.out.println("**"+doc);
        	     if(!hm.containsKey(doc.get("_id"))){
        	     SearchBean project = new SearchBean();
                 project.setSource("region");
  	             project.setTitle(doc.getString("title"));
  	             project.setId(doc.get("_id").toString());
  	             project.setDescription(doc.getString("description"));
  	             project.setId(doc.get("_id").toString());
       	         project.setTags((ArrayList<String>)doc.get("tags"));
  	             project.setUrl(doc.getString("project_url"));
  	             project.setUpvotecount(doc.getLong("upvotecount"));
	             project.setDownvotecount(doc.getLong("downvotecount"));
	             project.setMatchedcount(1);
  	             boolean bool =(doc.getString("description").matches("(?i:"+s+")"))?project.setPriority("b"):project.setPriority("a");    
        	     alsearch.add(project);
                 hm.put(doc.get("_id"),project);
        	     }else{
                     SearchBean sb=hm.get(doc.get("_id"));
         	         if(sb!=null)
         	         {
         	         	sb.setMatchedcount((sb.getMatchedcount()+1));
         	            String source = sb.getSource();
         	    //       source = source+ ":description";
       	                sb.setSource(source);
         	         	hm.put(doc.get("_id"),sb);
         	         }	    	 
         	     }
        });
        
        //search questions on basis of tags
        fi = tcques .find();
        pattern = Pattern.compile("(?i:.*"+s+".*)");
        fi.forEach((Block<Document>)  doc-> {
        ArrayList<String> altags = (ArrayList<String>)doc.get("tags");
        System.out.println("tags size"+altags.size()+":::"+altags);
        System.out.println("hmtags size"+hm.size());
        altags.forEach(st -> {
        	  if(st.matches("(?i:.*"+s+".*)")&&!hm.containsKey(doc.get("_id")))
        	  {  
        		  SearchBean ques = new SearchBean();
        	  	  ques.setSource("question");
        	  	  ques.setTitle(doc.getString("question"));
        	      ques.setId(doc.get("_id").toString());
        	  	  ques.setDescription(doc.getString("description"));
        	  	  ques.setUsername(doc.getString("username"));
        	  	  ques.setUpvotecount(doc.getLong("upvotecount"));
        	  	  ques.setDownvotecount(doc.getLong("downvotecount"));
        	  	  ques.setMatchedcount(1);
	             boolean bool =(st.matches("(?:"+s+")"))?ques.setPriority("z"):ques.setPriority("b");    
      	         alsearch.add(ques);
      	         hm.put(doc.get("_id"),ques);
      	         System.out.println("hmtags updated size"+hm.size()+"::"+st);
        	  }else if(st.matches("(?i:.*"+s+".*)")){
                  SearchBean sb=hm.get(doc.get("_id"));
                  System.out.println("else");
                 if(sb!=null)
      	         { 
           	    if(st.matches("(?:"+s+")"))
           	    {
           	    	if(sb.getPriority().equals("b") || sb.getPriority().equals("a"))
           	    	{
           	    		sb.setPriority("z");
           	    	}
           	    }
                long count = sb.getMatchedcount()+1;	
      	         System.out.println("else id"+doc.get("_id")+st+"::"+count);
      	         sb.setMatchedcount((count));
      	            String source = sb.getSource();
      	            sb.setSource(source);
      	         	hm.put(doc.get("_id"),sb);
      	         }	    	 
      	     }
          });
        });
        
        
        //search on basis of question
        fi = tcques.find(or(eq("question",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	    if(!hm.containsKey(doc.get("_id"))){
        	    	 System.out.println("inside if");
        	    	 SearchBean ques = new SearchBean();
        	    	 ques.setSource("question");
        	    	 ques.setTitle(doc.getString("question"));
        	    	 ques.setId(doc.get("_id").toString());
        	    	 ques.setDescription(doc.getString("description"));
        	    	 ques.setUsername(doc.getString("username"));
        	    	 ques.setUpvotecount(doc.getLong("upvotecount"));
        	    	 ques.setDownvotecount(doc.getLong("downvotecount"));
        	    	 ques.setMatchedcount(1);
        	    	 boolean bool =(doc.getString("question").matches("(?i:"+s+")"))?ques.setPriority("z"):ques.setPriority("b");
        	    	 System.out.println(bool);
        	    	 alsearch.add(ques);
        	    	 hm.put(doc.get("_id"),ques);
        	     }
        	    else if(doc.getString("question").matches("(?i:.*"+s+".*)")){
                    SearchBean sb=hm.get(doc.get("_id"));
                    System.out.println("else");
                   if(sb!=null)
        	         { 
             	    if(doc.getString("question").matches("(?:"+s+")"))
             	    {
             	    	if(sb.getPriority().equals("b") || sb.getPriority().equals("a"))
             	    	{
             	    		sb.setPriority("z");
             	    	}
             	    }
                  long count = sb.getMatchedcount()+1;	
        	         //System.out.println("else id"+doc.get("_id")+st+"::"+count);
        	         sb.setMatchedcount((count));
        	            String source = sb.getSource();
        	            sb.setSource(source);
        	         	hm.put(doc.get("_id"),sb);
        	         }	    	 
        	     }
        });
        
        //search on basis of description
        fi = tcques.find(or(eq("description",Pattern.compile("(?i:.*"+s+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        	    if(!hm.containsKey(doc.get("_id"))){
        	    	 System.out.println("inside if");
        	    	 SearchBean ques = new SearchBean();
        	    	 ques.setSource("question");
        	    	 ques.setTitle(doc.getString("question"));
        	    	 ques.setId(doc.get("_id").toString());
        	    	 ques.setDescription(doc.getString("description"));
        	    	 ques.setUsername(doc.getString("username"));
        	    	 ques.setUpvotecount(doc.getLong("upvotecount"));
        	    	 ques.setDownvotecount(doc.getLong("downvotecount"));
        	    	 ques.setMatchedcount(1);
        	    	 boolean bool =(doc.getString("question").matches("(?i:"+s+")"))?ques.setPriority("z"):ques.setPriority("b");
        	    	 System.out.println(bool);
        	    	 alsearch.add(ques);
        	    	 hm.put(doc.get("_id"),ques);
        	     }
        	    else if(doc.getString("description").matches("(?i:.*"+s+".*)")){
                    SearchBean sb=hm.get(doc.get("_id"));
                    System.out.println("else");
                   if(sb!=null)
        	         { 
             	    if(doc.getString("question").matches("(?:"+s+")"))
             	    {
             	    	if(sb.getPriority().equals("b") || sb.getPriority().equals("a"))
             	    	{
             	    		sb.setPriority("z");
             	    	}
             	    }
                  long count = sb.getMatchedcount()+1;	
        	         //System.out.println("else id"+doc.get("_id")+st+"::"+count);
        	         sb.setMatchedcount((count));
        	            String source = sb.getSource();
        	            sb.setSource(source);
        	         	hm.put(doc.get("_id"),sb);
        	         }	    	 
        	     }
        });
        });
             
        Collections.sort(alsearch, SearchBean.searchsort);
        System.out.println("alsearch"+alsearch);
        ArrayList<SearchBean> tempList = new ArrayList<SearchBean>();
        ArrayList<SearchBean> finalList = new ArrayList<SearchBean>();
        //int flag = 0;
        for(int i=0;i<alsearch.size();i++){
        	
        		if(i>0){
        			System.out.println("22"+alsearch.get(i).getUpvotecount());
        			if(alsearch.get(i).getMatchedcount() == alsearch.get(i-1).getMatchedcount()){
        				System.out.println("equal+++++");
        				//flag = 0;
        				tempList.add(alsearch.get(i));
        			}
        			else{
        				System.out.println("never-------");
        				//flag = 1;
        				Collections.sort(tempList, SearchBean.upvoteSort);
        				
        				//for(SearchBean sb : tempList)
        					finalList.addAll(tempList);
        					System.out.println("inside final" + finalList);
        				tempList = new ArrayList<SearchBean>();
        				tempList.add(alsearch.get(i));
        			}
        		}
        		else{
        			//flag = 2;
        			tempList.add(alsearch.get(i));
        		}
        		//if(flag==0)
        			
        		
        }
        Collections.sort(tempList, SearchBean.upvoteSort);
        //Collections.sort(tempList, SearchBean.downvoteSort);
        finalList.addAll(tempList);
        System.out.println("final = "+finalList);
        
        tempList = new ArrayList<SearchBean>();
        ArrayList<SearchBean> returnList = new ArrayList<SearchBean>();
        for(int i=0;i<finalList.size();i++){
        	
    		if(i>0){
    			
    			if(finalList.get(i).getUpvotecount() == finalList.get(i-1).getUpvotecount()){
    				System.out.println("equal+++++");
    				//flag = 0;
    				tempList.add(finalList.get(i));
    			}
    			else{
    				
    				Collections.sort(tempList, SearchBean.downvoteSort);
    				//for(SearchBean sb : tempList)
    					returnList.addAll(tempList);
    					System.out.println("inside final" + returnList);
    				tempList = new ArrayList<SearchBean>();
    				tempList.add(finalList.get(i));
    			}
    		}
    		else{
    			//flag = 2;
    			tempList.add(finalList.get(i));
    		}
    		//if(flag==0)
    			
    		
    }
    Collections.sort(tempList, SearchBean.downvoteSort);
    //Collections.sort(tempList, SearchBean.downvoteSort);
    returnList.addAll(tempList);
    System.out.println("final = "+returnList);

        
         return returnList;
	}
}
