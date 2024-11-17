/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;

/**
 * The Class WebServiceClient.
 */
public class WebServiceClient {

	/** The http status response. */
	private HttpResponse httpStatusResponse;

	/** The json response. */
	private String jsonResponse;

	/** The http client. */
	private HttpClient httpClient = HttpClientBuilder.create().build();
	private final AppConfigLoader configLoaderInstance = AppConfigLoader.getInstance();
	private static WebServiceClient webserviceInstance = null;

	/**
	 * Submit http post request.
	 *
	 * @param serviceUrl  the service url
	 * @param requestJSON the request JSON
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void submitHttpPostRequest(String serviceUrl, String requestJSON) throws IOException {
		submitHttpPostRequest(serviceUrl, requestJSON, null);
	}

	/**
	 * Submit http post request.
	 *
	 * @param serviceUrl     the service url
	 * @param requestJSON    the request JSON
	 * @param requestHeaders the request headers
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void submitHttpPostRequest(String serviceUrl, String requestJSON, HashMap<String, String> requestHeaders)
			throws IOException {

		HttpPost postRequest = null;
		BufferedReader bufferedReader = null;
		CloseableHttpClient client = null;
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {

				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}

			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			client = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
			httpStatusResponse = null;
			jsonResponse = "";

			System.out.println("ServiceUrl = " + serviceUrl);
			System.out.println("RequestJSON = " + requestJSON);

			postRequest = new HttpPost(serviceUrl);
			postRequest.addHeader("content-type", "application/json");
			postRequest.addHeader("accept", "application/json");

			if (requestHeaders != null) {
				Iterator<Entry<String, String>> it = requestHeaders.entrySet().iterator();
				while (it.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry) it.next();
					postRequest.addHeader((String) pair.getKey(), (String) pair.getValue());
					it.remove();
				}
			}

			StringEntity params = new StringEntity(requestJSON);
			postRequest.setEntity(params);
			// client = clientBuilder.build();
			httpStatusResponse = client.execute(postRequest);

			int httpStatusCode = httpStatusResponse.getStatusLine().getStatusCode();
			System.out.println("HttpResponse is " + httpStatusCode);

			if (httpStatusCode != 500) {
				bufferedReader = new BufferedReader(
						new InputStreamReader((httpStatusResponse.getEntity().getContent())));
				String output;
				while ((output = bufferedReader.readLine()) != null) {
					jsonResponse += output;
				}
				System.out.println("JSON Response = " + jsonResponse);
				System.out.println("HttpResponse Phrase = " + httpStatusResponse.getStatusLine().getReasonPhrase());
				bufferedReader.close();
			}

		} catch (Exception ex) {
			Assert.fail("SendPostRequest failed for url  " + serviceUrl + " Reason " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (postRequest != null)
				postRequest.releaseConnection();
		}

	}

	/**
	 * Submit http get request.
	 *
	 * @param serviceUrl the service url
	 * @return true, if successful
	 */
	// Sends GET request to API and gets response plus headers as Map
	public boolean submitHttpGetRequest(String serviceUrl) {
		HttpGet getRequest = null;
		try {
			httpStatusResponse = null;
			jsonResponse = "";
			System.out.println(serviceUrl);
			getRequest = new HttpGet(serviceUrl.replace(" ", "%20"));

			httpStatusResponse = httpClient.execute(getRequest);

			if ((httpStatusResponse.getStatusLine().getStatusCode() != 200)
					&& (httpStatusResponse.getStatusLine().getStatusCode() != 202)) {
				System.out.println("Http Response :" + httpStatusResponse.getStatusLine());
				System.out.println("HttpResponse if not 200 = " + httpStatusResponse.getStatusLine().getStatusCode()
						+ " " + httpStatusResponse.getStatusLine().getReasonPhrase());

				return false;
			}
			BufferedReader br = new BufferedReader(
					new InputStreamReader((httpStatusResponse.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				jsonResponse += output;
			}
			System.out.println("Response : " + jsonResponse);
			br.close();
			return true;

		} catch (Exception ex) {
			Assert.fail("SendGetRequest failed for url  " + serviceUrl + "Reason " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			getRequest.releaseConnection();
		}

	}

	/**
	 * Retrieve the User Id of the user by using Email via Central Service and User
	 * API's.
	 * 
	 * @return User Id
	 * @author mperumal
	 * @throws IOException
	 */
	public String getUserIdFromEmail(String userEmail) throws IOException {

		String getUserIdFromApi;
		getUserIdFromApi = configLoaderInstance.getEnvironmentProperty("UserApi")
				+ getUserGMTOATFromEmail(userEmail, "OAT");
		this.submitHttpGetRequest(getUserIdFromApi);
		String userId = jsonParser(jsonResponse, "id", "data");
		System.out.println("User Id for Email- " + userEmail + "- is -" + userId);
		return userId;
	}

	/**
	 * Retrieve the GMT of the user by using Email via Central Service and User
	 * API's.
	 * 
	 * @return User Id
	 * @author mperumal
	 * @throws IOException
	 */
	public String getUserGMTOATFromEmail(String user_email, String key) throws IOException {
		this.submitHttpPostRequest(configLoaderInstance.getEnvironmentProperty("RFUserProfileApi"),
				"{\"Email\": \"" + user_email + "\", \"LoginName\": \"CustomerService\"}");
		String GMT = this.jsonParser(jsonResponse, "OAT").toLowerCase();
		System.out.println("GMT value: " + GMT);
		return GMT;
	}

	/**
	 * Parse the JSON format String response and retrieve the value for the given
	 * parameter key. To retrieve the child node, give the parent node name as third
	 * parameter
	 * 
	 * @param response_in_string
	 * @param response_key_param
	 * @param parent_node
	 * @author mperumal
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String jsonParser(String responseInString, String responseKeyParam, String... parentNode)
			throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json_node = mapper.readTree(responseInString);
		for (String node : parentNode) {
			json_node = json_node.get(node);
		}
		json_node = json_node.findValue(responseKeyParam);
		if (json_node.isArray()) {
			return json_node.toString();
		} else {
			return json_node.asText().toString();
		}
	}

	/**
	 * Gets the http status response code.
	 *
	 * @return the http status response code
	 */
	// Return Http Response Code
	public int getHttpStatusResponseCode() {
		return httpStatusResponse.getStatusLine().getStatusCode();
	}

	/**
	 * Gets the http status reponse.
	 *
	 * @return the http status reponse
	 */
	// Return Http Response
	public HttpResponse getHttpStatusReponse() {
		return httpStatusResponse;
	}

	/**
	 * Gets the JSON response.
	 *
	 * @return the JSON response
	 */
	// Return JSON response
	public String getJSONResponse() {
		return jsonResponse;
	}

	/**
	 * Gets the header value.
	 *
	 * @param headerKey the header key
	 * @return the header value
	 */
	public String getHeaderValue(String headerKey) {
		return getHttpStatusReponse().getFirstHeader(headerKey).getValue();
	}

	/**
	 * Gets the reason phrase.
	 *
	 * @return the reason phrase
	 */
	public String getReasonPhrase() {
		return httpStatusResponse.getStatusLine().getReasonPhrase();
	}

	/**
	 * Singleton method to instantiate one instance for the run
	 * 
	 * @return WebServiceClient instance
	 */
	public static WebServiceClient getInstance() {
		if (webserviceInstance == null) {
			webserviceInstance = new WebServiceClient();
		}
		return webserviceInstance;
	}
}
