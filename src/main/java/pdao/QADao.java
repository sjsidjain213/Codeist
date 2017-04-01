package pdao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import bean.Answer;
import bean.Question;
import service.DatabaseServices;

public class QADao {

	MongoCollection<Document> tc = new DatabaseServices().getDb().getCollection("qa");
	MongoCollection<Document> tcuser = new DatabaseServices().getDb().getCollection("userdata");

	@SuppressWarnings("unchecked")
	public ArrayList<Question> getProblem(String username)
	{
		//ObjectId id1=new ObjectId(id.toString());
		FindIterable<Document> dd = (FindIterable<Document>)tc.find(eq("username",username));
		ArrayList<Answer> alansw = new ArrayList<Answer>();
		ArrayList<Question> quesList = new ArrayList<Question>();
		for(Document doc : dd){
			System.out.println(doc);
	if(doc!=null)
	{Question quest = new Question();
	quest.setUsername(doc.getString("username"));
	quest.setQuestion(doc.getString("question"));
	quest.setDescription(doc.getString("description"));
	quest.setState(doc.getString("region_state"));
	quest.setCity(doc.getString("region_city"));
	quest.setDate(doc.getLong("date"));
	//quest.setLast_updated(doc.getLong("last_updated"));
	quest.setTags((ArrayList<String>)doc.get("tags"));
	quest.setQuestion_url(doc.getString("url"));
	Document document=(Document) doc.get("info");
	quest.setDownvotes((ArrayList<String>)document.get("downvotes"));
	quest.setUpvotes((ArrayList<String>)document.get("upvotes"));
	//quest.setFeatured_points(doc.getLong("featured_points"));
    ArrayList<Document> aldo = (ArrayList<Document>) doc.get("answers");
    if(aldo != null){
	for(Document d:aldo)
	{	Answer answer =  new Answer();
		answer.setUsername(d.getString("username"));
		answer.setAnswer(d.getString("answer"));
		answer.setDate(d.getLong("date"));
		answer.setLast_updated(d.getLong("last_updated"));
		Document in=(Document) d.get("info");
		answer.setDownvotes((ArrayList<String>)in.get("downvotes"));
		answer.setUpvotes((ArrayList<String>)in.get("upvotes"));
		answer.setFeatured_points(d.getLong("featured_points"));
		alansw.add(answer);
	}
	quest.setAnswers(alansw);
    }
    System.out.println("got"+quest);
	quesList.add(quest);
	}
		}
	
		
		return quesList;
	
	}}
