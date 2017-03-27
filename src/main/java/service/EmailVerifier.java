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
String name = GeneralServices.linkDecrypter(Base64.decodeBase64(hash1));
String email = GeneralServices.linkDecrypter(Base64.decodeBase64(hash2));
System.out.println(name);
name = name.substring(0,name.indexOf("|"));
String dat = email.substring((email.indexOf("|")+1),email.length());
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
String s = new UserDao().userVerifier(name,email,dateone,response);
return s;
}
}
