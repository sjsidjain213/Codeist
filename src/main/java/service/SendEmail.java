package service;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;


public class SendEmail {
static ArrayList<Long> date = new ArrayList<Long>();
    // ...
public static void main(String args[])
{//SendSimple();
System.out.println();	
}
/*public void SendEmail(String email,)
{

}*/
    public static ClientResponse SendSimple(String email,String hash1,String hash2) {

        Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic(
            "api",
            "key-2146143b75b920c2a85df48a75427f49"
        )); 
        WebTarget mgRoot = client.target("https://api.mailgun.net/v3");
        //String text = "http://codeist.mi43ujva9v.us-west-2.elasticbeanstalk.com/webapi/user/verifier/"+hash1+"/"+hash2;
        String text = "http://localhost:8080/Codeist/webapi/user/verifier/"+hash1+"/"+hash2;
        Form reqData = new Form();
        reqData.param("from", "Siddhartha <sid@javacrunch.in>");
        reqData.param("to",email);
        reqData.param("subject", "Signup Process");
        reqData.param("text", text);

        return mgRoot
            .path("/javacrunch.in/messages")
            .resolveTemplate("domain", "javacrunch.in")
            .request(MediaType.APPLICATION_FORM_URLENCODED)
            .buildPost(Entity.entity(reqData, MediaType.APPLICATION_FORM_URLENCODED))
            .invoke(ClientResponse.class);
    }
}