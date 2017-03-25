package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

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
		MongoCollection <Document> tc = new DatabaseServices().getDb().getCollection("userdata");
	 MongoCollection<Document>tctwo = new DatabaseServices().getDb().getCollection("project");
		MongoCollection <Document> tcques = new DatabaseServices().getDb().getCollection("testqa");
        HashMap<Object,SearchBean> hmuser = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmproject = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmtags = new HashMap<Object,SearchBean>();
	    HashMap<Object,SearchBean> hmques = new HashMap<Object,SearchBean>();
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
        	    boolean bool =(doc.getString("username").equals(s)||doc.getString("name").equals(s))?user.setPriority("z"):user.setPriority("b");    
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
  	             boolean bool =(doc.getString("title").matches("(?i:"+s+")"))?project.setPriority("z"):project.setPriority("b");    
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
  	             boolean bool =(doc.getString("title").matches("(?i:"+s+")"))?project.setPriority("b"):project.setPriority("a");    
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
	             boolean bool =(st.matches("(?:"+s+")"))?project.setPriority("z"):project.setPriority("b");    
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
        });
        
        String input, temp;
        Pattern ptn = Pattern.compile("\\s+");
        Matcher mtch = ptn.matcher(s);
        temp = mtch.replaceAll(" ");
        input = temp.trim();
        Pattern p = Pattern.compile("[\\w]+");
        Matcher m = p.matcher(input);
        ArrayList<String> split = new ArrayList<String>();
        while ( m.find() ) {
            split.add(input.substring(m.start(), m.end()));
        	System.out.println(input.substring(m.start(), m.end()));
        }
        fi = tcques.find(or(eq("question",Pattern.compile("(?i:.*"+input+".*)"))));	
        fi.forEach((Block<Document>) doc -> { 
        		System.out.println("I am here");
        		System.out.println(">>>>>>>"+doc.get("_id"));
        	    if(!hmques.containsKey(doc.get("_id"))){
        	    	 System.out.println("inside if");
        	    	 SearchBean ques = new SearchBean();
        	    	 ques.setSource("question");
        	    	 ques.setTitle(doc.getString("question"));
        	    	 ques.setDescription(doc.getString("description"));
        	    	 ques.setUsername(doc.getString("username"));
        	    	 ques.setUpvotes(doc.getInteger("upvotes"));
        	    	 ques.setMatchedcount(1);
        	    	 boolean bool =(doc.getString("question").matches("(?i:"+input+")"))?ques.setPriority("z"):ques.setPriority("m");
        	    	 System.out.println(bool);
        	    	 alsearch.add(ques);
        	    	 hmques.put(doc.get("_id"),ques);
        	     }
        	     else{
                    SearchBean sb=hmques.get(doc.get("_id"));
        	         if(sb!=null)
        	         {
        	         	sb.setMatchedcount((sb.getMatchedcount()+1));
        	            String source = sb.getSource();
        	      //      source = source+ ":title";
        	            sb.setSource(source);
        	         	hmques.put(doc.get("_id"),sb);
        	         }	    	 
        	     }
        });
        System.out.println("before"+split+", ");
        for(String str : split){
        	System.out.println(!str.equalsIgnoreCase("how"));
        	if(!str.equalsIgnoreCase("How") && !str.equalsIgnoreCase("What") && !str.equalsIgnoreCase("why") &&
        		!str.equalsIgnoreCase("is") && !str.equalsIgnoreCase("the") &&	!str.equalsIgnoreCase("that") &&
        		!str.equalsIgnoreCase("they") && !str.equalsIgnoreCase("a") && !str.equalsIgnoreCase("an") &&
        		!str.equalsIgnoreCase("who") && !str.equalsIgnoreCase("do"))
        	{
        		System.out.println("********"+str);
        		fi = tcques.find(or(eq("question",Pattern.compile("(?i:.*"+str+".*)"))));	
        		fi.forEach((Block<Document>) doc -> { 
   	     		if(!hmques.containsKey(doc.get("_id"))){
   	     			SearchBean ques = new SearchBean();
   	     			ques.setSource("question");
   	     			ques.setTitle(doc.getString("question"));
   	     			ques.setUsername(doc.getString("username"));
   	     			ques.setDescription(doc.getString("description"));
   	     			ques.setUpvotes(doc.getInteger("upvotes"));
   	     			ques.setMatchedcount(1);
   	     			boolean bool =(doc.getString("question").matches("(?i:"+s+")"))?ques.setPriority("m"):ques.setPriority("b");
   	     			System.out.println(bool);
   	     			alsearch.add(ques);
   	     			hmques.put(doc.get("_id"),ques);
   	     		}
   	     		else{
   	     			SearchBean sb=hmques.get(doc.get("_id"));
   	     			if(sb!=null)
   	     			{
   	     				sb.setMatchedcount((sb.getMatchedcount()+1));
   	     				String source = sb.getSource();
   	     				//      source = source+ ":title";
   	     				sb.setSource(source);
   	     				hmques.put(doc.get("_id"),sb);
   	     			}	    	 
   	     		}
        	});
        	}
       }
        
        

        });
        
        
        
        Collections.sort(alsearch, SearchBean.searchsort);
        System.out.println("alsearch"+alsearch);
        ArrayList<SearchBean> tempList = new ArrayList<SearchBean>();
        ArrayList<SearchBean> finalList = new ArrayList<SearchBean>();
        //int flag = 0;
        for(int i=0;i<alsearch.size();i++){
        	System.out.println("11");
        		if(i>0){
        			System.out.println("22"+alsearch.get(i).getUpvotes());
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
        finalList.addAll(tempList);
        System.out.println("final = "+finalList);
         return finalList;
	}
}
