package com.pch.search.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class HttpUtils {
	
	public static String sendPOSTRequest(String endPoint,String payload){
		CustomLogger.log(String.format("Sending Http POST request to endpoint : %s\nPayload:%s",endPoint,payload));
		StringBuilder response=new StringBuilder();
		HttpClient client = HttpClientBuilder.create().build() ;		
		HttpPost post = new HttpPost(endPoint);
		
		post.setHeader("Content-Type", "application/xml");
		
		HttpResponse httpResponse = null;
		try {
			post.setEntity(new StringEntity(payload));
			httpResponse = client.execute(post);
			
			if(httpResponse!=null){
				response.append(httpResponse.getStatusLine().getStatusCode()+"|");
				BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
				String line = "";
				while((line=reader.readLine())!=null){
					response.append(line);
				}
				CustomLogger.log("Response-\n"+response.toString());
				return response.toString();
			}
		} catch (ClientProtocolException e) {			
			CustomLogger.logException(e);
			throw new RuntimeException();
		} catch (IOException e) {
			CustomLogger.logException(e);
			throw new RuntimeException();
		}
		return null;		
	}

}
