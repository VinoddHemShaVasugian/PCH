package com.pch.survey.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
 
 
public class WebServiceClient {

	private HttpResponse httpStatusResponse;
	private String jsonResponse;
	private HttpClient httpClient = HttpClientBuilder.create().build();

	 
	// Sends POST request to API and gets response plus headers as Map
	public boolean sendPostJsonRequest(String serviceUrl, String requestJSON, Map<String, String> headers) {
		HttpPost postRequest = null;
		try {
			System.out.println("serviceUrl = " + serviceUrl);
			System.out.println("requestJSON = " + requestJSON);
			httpStatusResponse = null;
			jsonResponse = "";
			postRequest = new HttpPost(serviceUrl);
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					postRequest.addHeader(entry.getKey(), entry.getValue());
					System.out.println(entry.getKey()+" :"+entry.getValue());
				}
			}
			postRequest.addHeader("content-type", "application/json");
			postRequest.addHeader("accept", "application/json");
			StringEntity params = new StringEntity(requestJSON);
			postRequest.setEntity(params);
			httpStatusResponse = httpClient.execute(postRequest);
			if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
					&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {
				System.out.println("HttpResponse if not 200 = "
						+ httpStatusResponse.getStatusLine().getStatusCode()
						+ " "
						+ httpStatusResponse.getStatusLine().getReasonPhrase());
				
				return false;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpStatusResponse.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("jsonResponse = " + jsonResponse);
			br.close();
			return true;
		} catch (Exception ex) {
			Assert.fail("SendPostRequest failed for url  " + serviceUrl
					+ " Reason " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			postRequest.releaseConnection();
		}

	}
	public boolean sendPostFormRequest(String serviceUrl, Map<String, String> parameters,Map<String, String> headers) {
		HttpPost postRequest = null;
		httpStatusResponse = null;
		jsonResponse = "";
		try {
			System.out.println("serviceUrl = " + serviceUrl);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
			System.out.println("key = '" + entry.getKey()+ "' Val = '" + entry.getValue()+"'");
				
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			
			postRequest = new HttpPost(serviceUrl);
			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					postRequest.addHeader(entry.getKey(), entry.getValue());
					System.out.println(entry.getKey()+" :"+entry.getValue());
				}
			}			
			postRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
 			httpStatusResponse = httpClient.execute(postRequest);
			System.out.println("Httpresponse = " + httpStatusResponse.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpStatusResponse.getEntity().getContent())));
			String output;
		 
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse);
 
			br.close();

			
			
			return true;
		} catch (Exception ex) {
			Assert.fail("SendPostRequest failed for url  " + serviceUrl
					+ " Reason " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			postRequest.releaseConnection();
		}
	}
	

	
 

	// Sends PUT request to API and gets response plus headers as Map
	public boolean sendPutRequest(String serviceUrl, String requestJSON, Map<String, String> headers){
		HttpPut putRequest = null;
		System.out.println("serviceUrl = " + serviceUrl);
		System.out.println("requestJSON = " + requestJSON);

		try {
			httpStatusResponse = null;
			jsonResponse = "";
			putRequest = new HttpPut(serviceUrl);

			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					putRequest.addHeader(entry.getKey(), entry.getValue());
					System.out.println(entry.getKey()+" :"+entry.getValue());
				}
			}

			putRequest.addHeader("content-type", "application/json");
			putRequest.addHeader("accept", "application/json");
			StringEntity params = new StringEntity(requestJSON);
			putRequest.setEntity(params);

			httpStatusResponse = httpClient.execute(putRequest);
			if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
					&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {

				System.out.println("HttpResponse if not 200 = "
						+ httpStatusResponse.getStatusLine().getStatusCode()
						+ " "
						+ httpStatusResponse.getStatusLine().getReasonPhrase());

				return false;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpStatusResponse.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}
			br.close();

			return true;
		} catch (Exception ex) {
			Assert.fail("SendPutRequest failed for url  " + serviceUrl
					+ "Reason " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			putRequest.releaseConnection();
		}
	}

	// Sends GET request to API and gets response plus headers as Map
	public boolean sendGetRequest(String serviceUrl, Map<String, String> headers) {
		HttpGet getRequest = null;
		try {
			httpStatusResponse = null;
			jsonResponse = "";

			System.out.println(serviceUrl);
			getRequest = new HttpGet(serviceUrl.replace(" ","%20"));

			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					getRequest.addHeader(entry.getKey(), entry.getValue());
					System.out.println(entry.getKey()+" :"+entry.getValue());
				}
			}

			getRequest.addHeader("content-type", "application/json");
			getRequest.addHeader("accept", "application/json");
			httpStatusResponse = httpClient.execute(getRequest);

			if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
					&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {
				System.out.println("Http Response :"
						+ httpStatusResponse.getStatusLine());
				System.out.println("HttpResponse if not 200 = "
						+ httpStatusResponse.getStatusLine().getStatusCode()
						+ " "
						+ httpStatusResponse.getStatusLine().getReasonPhrase());

				return false;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpStatusResponse.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse);

			br.close();
			return true;

		} catch (Exception ex) {
			Assert.fail("SendGetRequest failed for url  " + serviceUrl
					+ "Reason " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			getRequest.releaseConnection();
		}

	}
	
	// Sends GET request to API and gets response plus headers as Map
		public StringBuffer sendGetRequest(String serviceUrl) {
			HttpGet getRequest = null;
			try {
				httpStatusResponse = null;
				jsonResponse = "";

				System.out.println(serviceUrl);
				getRequest = new HttpGet(serviceUrl.replace(" ","%20"));

				httpStatusResponse = httpClient.execute(getRequest);

				if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
						&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {
					System.out.println("Http Response :"
							+ httpStatusResponse.getStatusLine());
					System.out.println("HttpResponse if not 200 = "
							+ httpStatusResponse.getStatusLine().getStatusCode()
							+ " "
							+ httpStatusResponse.getStatusLine().getReasonPhrase());

					return null;
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(httpStatusResponse.getEntity().getContent())));
				String output;
				StringBuffer buff = new StringBuffer(10000);
			 
				
				while ((output = br.readLine()) != null) {
					buff.append(output);
				}
				System.out.println("Response : " + buff.length());
				buff.reverse();
				br.close();
				return buff;

			} catch (Exception ex) {
				Assert.fail("SendGetRequest failed for url  " + serviceUrl
						+ "Reason " + ex.getMessage());
				ex.printStackTrace();
				return null;
			} finally {
				getRequest.releaseConnection();
			}

		}

	/*
	 * Get HTTP STATUS response
	 */
	public HttpResponse getHttpStatusResponse() {
		return httpStatusResponse;
	}

	public String getJSONResponse() {
		return jsonResponse;
	}

	// Get 1st header value from response
	public String getHeaderValue(String name) {
		String rtn = null;
		try {
			Header header = httpStatusResponse.getFirstHeader(name);
			if (header == null)
				return rtn;
			rtn = header.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}


	
	// loads response into StringBuilder faster
	public StringBuilder getLogFileViaHttp(String serviceUrl) {
		HttpGet getRequest = null;
		try {
			httpStatusResponse = null;
			jsonResponse = "";

 			System.out.println(serviceUrl);
			getRequest = new HttpGet(serviceUrl.replace(" ","%20"));

			httpStatusResponse = httpClient.execute(getRequest);

			if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
					&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {
				System.out.println("Http Response :"
						+ httpStatusResponse.getStatusLine());
				System.out.println("HttpResponse if not 200 = "
						+ httpStatusResponse.getStatusLine().getStatusCode()
						+ " "
						+ httpStatusResponse.getStatusLine().getReasonPhrase());

				return null;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpStatusResponse.getEntity().getContent())));
			String output;
			StringBuilder sBuilder = new StringBuilder(100000);
		 
			
			while ((output = br.readLine()) != null) {
				sBuilder.append(output+"^");
				System.out.println("Response : " + output+"^");
			}
 	 
			br.close();
			return sBuilder;

		} catch (Exception ex) {
			Assert.fail("SendGetRequest failed for url  " + serviceUrl
					+ "Reason " + ex.getMessage());
			ex.printStackTrace();
			return null;
		} finally {
			getRequest.releaseConnection();
		}

	}


	
	
	
	
	// loads response into StringBuilder faster
		public ArrayList<String> getLogFileViaHttp(String serviceUrl, String gmt) {
			HttpGet getRequest = null;
			ArrayList<String> resultList = new ArrayList<String>();
			
			try {
				httpStatusResponse = null;
				jsonResponse = "";

	 			System.out.println(serviceUrl);
				getRequest = new HttpGet(serviceUrl.replace(" ","%20"));

				httpStatusResponse = httpClient.execute(getRequest);

				if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
						&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {
					System.out.println("Http Response :"
							+ httpStatusResponse.getStatusLine());
					System.out.println("HttpResponse if not 200 = "
							+ httpStatusResponse.getStatusLine().getStatusCode()
							+ " "
							+ httpStatusResponse.getStatusLine().getReasonPhrase());

					return null;
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(httpStatusResponse.getEntity().getContent())));
				String output;
				
				while ((output = br.readLine()) != null) {
					if(output.contains(gmt)) {
						resultList.add(output);
					}
					}
				br.close();
				return resultList;

			} catch (Exception ex) {
				Assert.fail("SendGetRequest failed for url  " + serviceUrl
						+ "Reason " + ex.getMessage());
				ex.printStackTrace();
				return null;
			} finally {
				getRequest.releaseConnection();
			}

		}


		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		WebServiceClient client = new WebServiceClient();
		client.sendGetRequest("https://surveylogs.qa.pch.com/survey/survey-2023-03-16.log");
		
		
		
	}


}
