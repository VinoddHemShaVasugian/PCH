package com.pch.survey.apis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.RandomDataGenerator;
import com.pch.survey.utilities.WebServiceClient;

public class OfferApis {

	private static WebServiceClient client = new WebServiceClient();
	private static String endPointUrl = ConfigurationReader.getOfferSurveyEndApi();
	private static String endPointUrlGRL = ConfigurationReader.getOfferSurveyEndApiGRL();
	private static String env = ConfigurationReader.getEnvironment().toLowerCase();
	private static String requestBody = "{ \"mode\": \"custom\",\"environment\": \"<env>\", \"endStatus\": \"<endStatus>\",\"url\": \"<url>\"}";

	public static String getSurveyEndPointUrl(String status) {
		String json = null;
		if (status.equalsIgnoreCase("complete"))
			json = requestBody.replace("<endStatus>", "complete");
		else
			json = requestBody.replace("<endStatus>", "incomplete");
		json = json.replace("<env>", env);
		json = json.replace("<url>", SurveyMainPage.surveyLandingPageUrl);
		if (SurveyMainPage.user.equalsIgnoreCase("guest")) {
			try {
				String setGmt = SurveyMainPage.surveyLandingPageUrl;
				Pattern p = Pattern.compile("&pid=(.*?)&");
				Matcher m1 = p.matcher(setGmt);
				while (m1.find()) {
					setGmt = m1.group(1);
				}
				Registrations.setGmt(setGmt);
			} catch (Exception e) {
			}
		}

		client.sendPostJsonRequest(endPointUrl, json, null);
		JSONObject responseJson = new JSONObject(client.getJSONResponse());
		return responseJson.getString("data");
	}

	public static String generateGRLEndEvent(String mid, String status) {
		if (status.equalsIgnoreCase("complete")) {
			status = "3";
		}
		if (status.equalsIgnoreCase("incomplete")) {
			status = "2";
		}
		String url = endPointUrlGRL + "?tsid=" + RandomDataGenerator.generateUUIDString() + "&oat="
				+ Registrations.getGmt() + "&mid=" + mid + "&status=" + status + "&is_test=1";
		System.out.println("GRL Endpoint get url " + url);
		client.sendGetRequest(url);
		return url;
	}

}
