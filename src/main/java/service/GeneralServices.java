package service;
import java.util.ArrayList;
import interfaces.service.AtoSCon;
public class GeneralServices{
public String atosmethod(AtoSCon<String> ats,ArrayList<String> al)
{
for(String s : al)
	System.out.println(s+":inside service");
return (String) ats.convertAtoS(al);
}
}
