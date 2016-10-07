package net.dtracktor.bot.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * 
 * 
 */

public class ApacheHttpClientPost implements IHTTPClient {

    TreeMap  <String, String> options = new TreeMap  <String, String> ();  
    String url; 
    BufferedReader br = null;
    byte[] data;
    String sdata;
    
    @Override
    public String execute() {
        
        HttpResponse response = null;
        try {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(url);


        String ct = options.get("Content-Type");
        String auth = options.get("Authorization");

        HttpEntity ENTITY = null;
            postRequest.setHeader("Content-Type", ct);       
            postRequest.setHeader("Authorization", auth);       

            if (sdata != null) {        
            
            StringEntity input = new StringEntity(sdata);
            input.setContentType(ct);
            ENTITY=input;
  
            } else {

            ByteArrayEntity input = new ByteArrayEntity(data);  
            ENTITY=input;

            }
            
        postRequest.setEntity(ENTITY);

        response = httpClient.execute(postRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
        }

        br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));


        httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

        e.printStackTrace();

        } catch (IOException e) {

        e.printStackTrace();

        }
        return response.getStatusLine().toString();
    }

    @Override
    public String getResponseDataAsString() {
        String output;
        System.out.println("Output from Server .... \n");
        try {
        while ((output = br.readLine()) != null) {
                System.out.println(output);
        }
        } catch (Throwable ee) {
          ee.printStackTrace();  
        }
        return null;
    }

    @Override
    public void setDataAsByteArray(byte[] body) {
           data = body; 
    }

    @Override
    public void setHeader(String id, String value) {
           options.put (id, value);
    }

    @Override
    public void setURL(String url) {
           this.url  = url;
    }

    public static void main(String[] args) {




    }

}