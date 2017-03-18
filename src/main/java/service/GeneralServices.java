package service;
import java.util.*;

import bean.Acknowledgement;
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
}
