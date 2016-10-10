package net.dtracktor.bot;

/**
 * 
 *   Google DRIVE API Helper class    
 *   @author rustemnasibullin@yahoo.com
 */
import java.io.FileInputStream;

import net.dtracktor.bot.utils.ApacheHttpClientPost;

import org.apache.camel.spring.SpringCamelContext;

import org.apache.commons.io.IOUtils;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 *  Camel context starter-class
 *
 *  @author rustemnasibullin@yahoo.com
 */
public class GoogleDRIVEApi {

   

    public void upload_file(String fs, String OAuthToken) throws Exception {
        byte[] body = null;
        ApacheHttpClientPost cln = new ApacheHttpClientPost ();
        try {
            
        FileInputStream in = new FileInputStream  (fs);
        body = IOUtils.toByteArray(in);    
    
        } catch (Throwable ee) {
           ee.printStackTrace(); 
        }
        
        
        cln.setURL ("https://www.googleapis.com/upload/drive/v3/files?uploadType=media");
        cln.setHeader("Content-Type","image/jpeg");
        cln.setHeader("Accept", "application/json");
        cln.setHeader ("Authorization",OAuthToken);
        cln.setDataAsByteArray(body);
        
        String response = cln.execute();        
        String data = cln.getResponseDataAsString();
        System.out.println (response);
        System.out.println (data);
            
        

    };

    
 

 


}
