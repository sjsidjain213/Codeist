package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import dao.UserDao;

public class EmailVerifier extends SendEmail{
	
public String test(String hash1,String hash2,HttpServletResponse response)
{
System.out.println(hash1);
System.out.println(hash2);
String username = GeneralServices.linkDecrypter(Base64.decodeBase64(hash1));
String emailid = GeneralServices.linkDecrypter(Base64.decodeBase64(hash2));
System.out.println(username);
username = username.substring(0,username.indexOf("|"));
String dat = emailid.substring((emailid.indexOf("|")+1),emailid.length());
System.out.println(dat);
emailid = emailid.substring(0,emailid.indexOf("|"));
long date= Long.valueOf(dat);
Date dateone =null;
Date datetwo = new Date(date);
SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
String strDate = sdfDate.format(datetwo);
SimpleDateFormat sdf1 = new SimpleDateFormat();
sdf1.applyPattern("yyyy-MM-dd HH:mm:ss.SSS");
try {
	dateone = sdf1.parse(strDate);
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
String s = new UserDao().userVerifier(username,emailid,dateone,response);
return s;
}

public String contributor(String hash1,HttpServletResponse response)
{
System.out.println(hash1);
String link = GeneralServices.linkDecrypter(Base64.decodeBase64(hash1));
//System.out.println(yes);
String resp = link.substring(0,link.indexOf("*"));
if(resp.equals("y")){
	String contributorname = link.substring(1,link.indexOf("|"));
	String projectid=link.substring((link.indexOf("|")+1),link.length());
   // new UserDao().contributorVerifier(resp,contributorname,projectid);
System.out.println(contributorname+"::"+projectid);
}
else{
	String contributorname = link.substring(1,link.indexOf("|"));
	String projectid=link.substring((link.indexOf("|")+1),link.length());
  // new UserDao().contributorVerifier(resp,contributorname,projectid);
}
/*
System.out.println(dat);
email = email.substring(0,email.indexOf("|"));
long date= Long.valueOf(dat);
Date dateone =null;
Date datetwo = new Date(date);
SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
String strDate = sdfDate.format(datetwo);
SimpleDateFormat sdf1 = new SimpleDateFormat();
sdf1.applyPattern("yyyy-MM-dd HH:mm:ss.SSS");
try {
	dateone = sdf1.parse(strDate);
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
String s = new UserDao().userVerifier(name,email,dateone,response);*/
return "sa";
}
}
