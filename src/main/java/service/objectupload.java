package service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class objectupload {
	private static String bucketName     = "codeist";
	private static String keyName        = "test12345";
	private static String uploadFileName = "done";
	
	
	public void upload(String base) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        try {
        	byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base);
        	ByteArrayInputStream contentsAsStream  = new ByteArrayInputStream(imageBytes);
            
            System.out.println("Uploading a new object to S3 from a file\n");
         //   File file = new File("C:/Users/dell 1/Documents/Advance java/img.png");
            ObjectMetadata  md = new ObjectMetadata();
            md.setContentType("image/png");
            md.setContentLength(imageBytes.length); 
            s3client.putObject(new PutObjectRequest(bucketName, keyName,contentsAsStream,md));
String url  = s3client.getUrl(bucketName, "codeist/"+keyName).toString();
System.out.println(url);
String urltwo = "https://s3-us-west-2.amazonaws.com/codeist/"+keyName;
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it" +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}