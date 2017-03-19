package service;
import java.util.*;



import bean.Acknowledgement;
import bean.Tag;
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
}
