package net.dtracktor.bot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * @todo
 * 
 * 
 */

public class ApacheHttpClientGet implements IHTTPClient{

    

    @Override
    public String execute() {
        // TODO Implement this method
        return null;
    }

    @Override
    public String getResponseDataAsString() {
        // TODO Implement this method
        return null;
    }

    @Override
    public void setDataAsByteArray(byte[] body) {
        // TODO Implement this method
    }

    @Override
    public void setURL(String url) {
        // TODO Implement this method
    }

    @Override
    public void setHeader(String id, String value) {
        // TODO Implement this method

    }

    public static void main(String[] args) {
	  try {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
			"http://localhost:8080/RESTfulExample/json/product/get");
		getRequest.addHeader("accept", "application/json");

		HttpResponse response = httpClient.execute(getRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		httpClient.getConnectionManager().shutdown();

	  } catch (ClientProtocolException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();
	  }

	}

}