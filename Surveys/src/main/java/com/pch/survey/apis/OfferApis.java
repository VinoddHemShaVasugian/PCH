package com.pch.survey.apis;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.WebServiceClient;

public class OfferApis {

	private static WebServiceClient client = new WebServiceClient();
	private static String endPointUrl = ConfigurationReader.getOfferSurveyEndApi();
	private static String env = ConfigurationReader.getEnvironment().toLowerCase();
	private static String requestBody = "{ \"mode\": \"custom\",\"environment\": \"<env>\", \"endStatus\": \"<endStatus>\",\"url\": \"<url>\"}";


	
	
	
	public static String getSurveyEndPointUrl(String status) {
	 		String json = null;
		if(status.equalsIgnoreCase("complete"))
		   json = requestBody.replace("<endStatus>", "complete");
		else
		   json = requestBody.replace("<endStatus>", "incomplete");
		json = json.replace("<env>", env);
		json = json.replace("<url>", SurveyMainPage.surveyLandingPageUrl);
		client.sendPostJsonRequest(endPointUrl, json, null);
		JSONObject responseJson = new JSONObject(client.getJSONResponse());
		return responseJson.getString("data");
	}
}
