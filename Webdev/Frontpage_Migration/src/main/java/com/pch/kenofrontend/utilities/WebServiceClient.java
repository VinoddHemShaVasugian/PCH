package com.pch.kenofrontend.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class WebServiceClient {

	private static CloseableHttpResponse httpStatusResponse;
	private String jsonResponse;
	private static String httpStatusResponseString;
	private static JSONObject httpStatusResponseJson = null;

	public static String response = "";
	public static String request = "";

	// Method to submit HTTP Get request
	public Boolean submitHttpGetRequest(String serviceUrl) throws IOException{		
		return submitHttpGetRequestwithGMT(serviceUrl,"");
	}

	// Method to submit HTTP Get request with passing GMT in request header
	public Boolean submitHttpGetRequestwithGMT(String serviceUrl, String gmt) throws IOException{		

		httpStatusResponse = null;
		jsonResponse = "";		
		HttpGet getRequest =  null;
		BufferedReader bufferedReader =  null;
		CloseableHttpClient client = null;

		try {

			HttpClientBuilder clientBuilder = HttpClientBuilder.create();
			clientBuilder.useSystemProperties();
			client = clientBuilder.build();
			request = serviceUrl;
			System.out.println("RequestURL = " + serviceUrl);

			getRequest = new HttpGet(serviceUrl);
			if(!gmt.isEmpty()){
				getRequest.addHeader("GlobalMemberToken", gmt);
			}

			// Execute request and return HTTP response
			httpStatusResponse = client.execute(getRequest);			

			bufferedReader = new BufferedReader(new InputStreamReader(
					httpStatusResponse.getEntity().getContent(), "UTF-8"));
			String output;

			while ((output = bufferedReader.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse);
			response = jsonResponse;
			return true;

		} catch (Exception e) {
			Assert.fail("SendGetRequest failed for url  " + serviceUrl + "Reason " + e.getMessage());
			return false;
		}
		finally{
			if(getRequest != null)
				getRequest.releaseConnection();
			if(bufferedReader != null)
				bufferedReader.close();
		}
	}

	// Method to submit HTTP Get request
	public Boolean submitHttpGetRequestwithAuthentication(String serviceUrl) throws IOException{		

		httpStatusResponse = null;
		jsonResponse = "";		
		HttpGet getRequest =  null;
		BufferedReader bufferedReader =  null;
		CloseableHttpClient client = null;

		try {

			HttpClientBuilder clientBuilder = HttpClientBuilder.create();
			clientBuilder.useSystemProperties();
			CredentialsProvider ntlmCredentialsProvider =  CredentialProviderFactory.Get(CredentialsProviderType.CredentialProviderTypeNtlm);
			if(ntlmCredentialsProvider == null)
				throw new Exception("Could not get NTLM CredentialProvider");

			clientBuilder.setDefaultCredentialsProvider(ntlmCredentialsProvider);
			clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
			client = clientBuilder.build();

			System.out.println("serviceUrl = " + serviceUrl);

			getRequest = new HttpGet(serviceUrl);

			// Execute request and return HTTP response
			httpStatusResponse = client.execute(getRequest);

			bufferedReader = new BufferedReader(new InputStreamReader(
					httpStatusResponse.getEntity().getContent(), "UTF-8"));
			String output;

			while ((output = bufferedReader.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse);

			return true;

		} catch (Exception e) {
			Assert.fail("SendGetRequest failed for url  " + serviceUrl + "Reason " + e.getMessage());
			return false;
		}
		finally{
			if(getRequest != null)
				getRequest.releaseConnection();
			if(bufferedReader != null)
				bufferedReader.close();
		}
	}

	// Method to submit HTTP POST request
	public boolean submitHttpPostRequest(String serviceUrl, String requestJSON) throws IOException{
		return submitHttpPostRequestwithGMT(serviceUrl,requestJSON,"","");
	}

	// Method to submit HTTP POST request with passing GMT in request header
	public boolean submitHttpPostRequestwithGMT(String serviceUrl, String requestJSON,String gmt, String isTestAccount) throws IOException{

		HttpPost postRequest = null;
		BufferedReader bufferedReader = null;
		CloseableHttpClient client = null;
		try {	
			HttpClientBuilder clientBuilder = HttpClientBuilder.create();			
			request = requestJSON;
			System.out.println("serviceUrl = " + serviceUrl);
			System.out.println("requestJSON = " + requestJSON);

			httpStatusResponse = null;
			jsonResponse = "";
			httpStatusResponseString = "";

			postRequest = new HttpPost(serviceUrl);
			postRequest.addHeader("content-type", "application/json");
			postRequest.addHeader("accept", "application/json");
			if(!gmt.isEmpty()){
				postRequest.addHeader("GlobalMemberToken", gmt);			
				postRequest.addHeader("IsTestAccount", isTestAccount);
			}
			StringEntity params = new StringEntity(requestJSON);
			postRequest.setEntity(params);	
			client = clientBuilder.build();
			httpStatusResponse = client.execute(postRequest);

			if (httpStatusResponse.getStatusLine().getStatusCode() != 200) {
				System.out.println("HttpResponse if not 200 = "
						+ httpStatusResponse.getStatusLine().getStatusCode()
						+ " "
						+ httpStatusResponse.getStatusLine().getReasonPhrase());

				if(httpStatusResponse.getStatusLine().getStatusCode() != 500){

					// Code to get HttpResponse body as a string
					HttpEntity entity = httpStatusResponse.getEntity();
					httpStatusResponseString = EntityUtils.toString(entity, "UTF-8");

					// Convert String to JSON Object
					if(httpStatusResponseString.startsWith("{"))
						httpStatusResponseJson = new JSONObject(httpStatusResponseString); 
					System.out.println(httpStatusResponseJson);
				}
				return false;
			}
			bufferedReader = new BufferedReader(new InputStreamReader(
					(httpStatusResponse.getEntity().getContent())));
			String output;
			while ((output = bufferedReader.readLine()) != null) {
				jsonResponse += output;
			}
			response = jsonResponse;
			System.out.println("jsonResponse = " + jsonResponse);

			bufferedReader.close();
			return true;

		} catch (Exception ex) {
			Assert.fail("SendPostRequest failed for url  " + serviceUrl
					+ " Reason " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			if(postRequest != null)
				postRequest.releaseConnection();

			if(bufferedReader  != null)
				bufferedReader.close();
		}
	}


	// Return Http Response Body as Json
	public JSONObject getHttpStatusResponseBody(){

		return httpStatusResponseJson;
	}

	// Return Http Response
	public HttpResponse getHttpStatusResponse() {
		return httpStatusResponse;
	}

	// Return Http Response String
	public String gethttpStatusResponseString() {
		return httpStatusResponseString;
	}


	// Return JSON response
	public String getJSONResponse() {
		return jsonResponse;
	}

	// Return response
	public static String getResponse() {
		return response;
	}

	// Return request
	public static String getRequest() {
		return request;
	}
	
	public static JSONObject requestGet_IgnoringCertificateErrors(
			String endPoint, String keyToRead) {
		String jsonResponse = "";
		BufferedReader bufferedReader = null;
		JSONObject json_obj, api_response;
		String output;
		try (CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient()) {
			HttpGet httpget = new HttpGet(endPoint);
			System.out.println("Executing request " + httpget.getRequestLine());
			httpStatusResponse = httpclient.execute(httpget);
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpStatusResponse.getEntity().getContent(), "UTF-8"));
			while ((output = bufferedReader.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse.replaceAll("null", "").trim());
			api_response = new JSONObject(jsonResponse.toString());
			json_obj = new JSONObject(api_response.getJSONObject(keyToRead)
					.toString());
			System.out.println("Requested Data is: " + json_obj);
			return json_obj;
		} catch (NoSuchAlgorithmException | KeyStoreException
				| KeyManagementException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static JSONArray requestGetArray_IgnoringCertificateErrors(
			String endPoint, String keyToRead) {
		String jsonResponse = "";
		BufferedReader bufferedReader = null;
		JSONObject api_response;
		JSONArray json_array;
		
		String output;
		try (CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient()) {
			HttpGet httpget = new HttpGet(endPoint);
			System.out.println("Executing request " + httpget.getRequestLine());
			httpStatusResponse = httpclient.execute(httpget);
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpStatusResponse.getEntity().getContent(), "UTF-8"));
			while ((output = bufferedReader.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse.replaceAll("null", "").trim());
			api_response = new JSONObject(jsonResponse.toString());
			json_array = api_response.getJSONArray((keyToRead)
					.toString());
			System.out.println("Requested Data is: " + json_array);
			return json_array;
		} catch (NoSuchAlgorithmException | KeyStoreException
				| KeyManagementException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	private static CloseableHttpClient createAcceptSelfSignedCertificateClient()
			throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException {

		// use the TrustSelfSignedStrategy to allow Self Signed Certificates
		SSLContext sslContext = SSLContextBuilder.create()
				.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

		// we can optionally disable hostname verification.
		// if you don't want to further weaken the security, you don't have to
		// include this.
		HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

		// create an SSL Socket Factory to use the SSLContext with the trust
		// self signed certificate strategy
		// and allow all hosts verifier.
		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(
				sslContext, allowAllHosts);

		// finally create the HttpClient using HttpClient factory methods and
		// assign the ssl socket factory
		return HttpClients.custom().setSSLSocketFactory(connectionFactory)
				.build();
	}

}
