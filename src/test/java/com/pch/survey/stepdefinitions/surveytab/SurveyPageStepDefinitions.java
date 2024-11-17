package com.pch.survey.stepdefinitions.surveytab;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pch.survey.centralservices.Registrations;
import com.pch.survey.dtos.ListenersLoggerEventsDto;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.DateUtilities;
import com.pch.survey.utilities.DevToolsNetworkTabDto;
import com.pch.survey.utilities.LogReader;
import com.pch.survey.utilities.SeleniumDevTools;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SurveyPageStepDefinitions {

	private SurveyMainPage surveyPage = new SurveyMainPage(WebdriverBuilder.getDriver());
	private LogReader logReader = new LogReader();
	private static String offersUrl = ConfigurationReader.getOffersUrl();
	private static ArrayList<DevToolsNetworkTabDto> networkTabData = null;
	private static ObjectMapper objectMapper = new ObjectMapper();

	
	
	@Given("I click Profile Builder lets go button")
	public void clickLetsGo() {
		surveyPage.clickProfileBuilderLetsGoButton();
	}

	@Given("I click Sign In Button")
	public void clickSignIn() {
		surveyPage.clickSignInButton();
	}

	@Given("I click first Take Survey Button")
	public void clickFirstSurvey() {
		surveyPage.clickTakeSurvey();
	}

	@Given("I navigates to survey {string}")
	public void givenImNavigatesToMid(String mid) {
		System.out.println("Navigating to url  " + offersUrl + "?mid=" + mid);
		WebdriverBuilder.getDriver().navigate().to(offersUrl + "?mid=" + mid);
		surveyPage.navigateToMid(mid);
	}

	@Given("I wait for survey start page to load")
	public void waitForSurveypage() {
		surveyPage.waitForSurveypage();
	}

	@Then("I verify query string parameters are not passed in url {string}")
	public void thenVerifyQueryStringParameters(String queryString) {
		String[] params = queryString.split(",");
		for (int ndx = 0; ndx < params.length; ndx++) {
			Assert.assertFalse("Verify query string param" + params[ndx] + "is not passed in url",
					surveyPage.verifySurveyMidQueryStringParameter(params[ndx]));
		}
	}

	@Then("I verify query string parameters are passed in url {string}")
	public void thenVerifyQueryStringParametersPassed(String queryString) {
		String[] params = queryString.split(",");
		for (int ndx = 0; ndx < params.length; ndx++) {
			Assert.assertTrue("Verify query string param " + params[ndx] + "is passed in url",
					surveyPage.verifySurveyMidQueryStringParameter(params[ndx]));
		}
	}

	@Given("I select a survey {string}")
	public void givenImSelectLucidSurvey(String survey) {
		surveyPage.clickTakeSurvey(survey);
	}

	@Then("I verify PchSurveys logo is present on page")
	public void thenVerifyLogo() {
		surveyPage.isPchSurveysMainLogoDisplayed();
	}

	@When("I click on {string} icon")
	public void WhenIClickmenuIcon(String txt) {

		surveyPage.clickMenuIcon(txt);
	}

	@Then("I verify menu icon {string} is present on page")
	public void thenVerifyLogo(String linkText) {
		surveyPage.isMenuIconDisplayed(linkText);
	}

	@Then("I verify page title is {string}")
	public void thenVerifyPageTitle(String title) {
		Assert.assertTrue(surveyPage.waitUntilPageTitleContains(title.replace("[pipe]", "|")));
	}

	@Then("I verify tabpage title is {string}")
	public void thenVerifyTabPageTitle(String title) {
		surveyPage.switchToLastOpenTab();
		Assert.assertTrue(surveyPage.waitUntilPageTitleContains(title.replace("[pipe]", "|")));
	}

	// mobile
	@When("I click and open Hamburger Menu")
	public void whenIOpenMenu() {
		surveyPage.clickHamburgerMenu();
	}

	@Then("I verify {string} survey start event json contains the following elements")
	public void verifyJsonElements(String provider, DataTable table) {
		List<List<String>> expectedElements = table.asLists();
		List<String> surveyStarts = null;
		switch (provider.toUpperCase()) {
		case "PURESPECTRUM":
			surveyStarts = logReader.getAllPureSpectrumStartsByGmt(Registrations.getGmt());
			break;
		case "LUCID":
			surveyStarts = logReader.getAllLucidSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "LUCIDAPI":
			surveyStarts = logReader.getAllLucidApiSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "QMEE":
			surveyStarts = logReader.getAllQmeeSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "JEBBIT":
			surveyStarts = logReader.getAllJebbitSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "GRL":
			surveyStarts = logReader.getAllGRLSurveyStartsByGmt(Registrations.getGmt());
			break;
		default:
			Assert.fail("Supplier names not found");
		}
		JSONObject json = new JSONObject(surveyStarts.get(0));
		for (int i = 1; i < expectedElements.size(); i++) {
			List<String> element = expectedElements.get(i);
			String elementName = element.get(0);
			String expectedtValue = element.get(1);
			String actualValue = null;
			// depending on log values maybe strings bigdecimals ........
			Object value = json.get(elementName);
			if (value instanceof BigDecimal)
				actualValue = json.getBigDecimal(elementName).toString();
			else if (value instanceof Integer)
				actualValue = json.getBigInteger(elementName).toString();
			else
				actualValue = json.getString(elementName);
			Assert.assertEquals(expectedtValue, actualValue);
		}
	}

	@Then("I verify survey end json is recorded in {string} EventsLogger")
	public void thenVerifySurveyEndJson(String provider) {
		List<String> surveyStarts = null;
		switch (provider.toUpperCase()) {
		case "LUCID":
			surveyStarts = logReader.getAllLucidSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "LUCIDAPI":
			surveyStarts = logReader.getAllLucidApiSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "QMEE":
			surveyStarts = logReader.getAllQmeeSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "JEBBIT":
			surveyStarts = logReader.getAllJebbitSurveyStartsByGmt(Registrations.getGmt());
			break;
		default:
			Assert.fail("Supplier names not found");
		}
		System.out.println(surveyStarts.get(0));
		Assert.assertTrue(surveyStarts != null & surveyStarts.size() > 0);
	}

	@Then("I verify {string} survey end event json contains the following elements")
	public void verifyJsonEndElements(String provider, DataTable table) {
		List<List<String>> expectedElements = table.asLists();
		List<String> surveyStarts = null;
		switch (provider.toUpperCase()) {
		case "LUCID":
			surveyStarts = logReader.getAllLucidSurveyEndsByGmt(Registrations.getGmt());
			break;
		case "LUCIDAPI":
			surveyStarts = logReader.getAllLucidApiSurveyEndsByGmt(Registrations.getGmt());
			break;
		case "QMEE":
			surveyStarts = logReader.getAllQmeeSurveyEndsByGmt(Registrations.getGmt());
			break;
		case "JEBBIT":
			surveyStarts = logReader.getAllJebbitSurveyEndsByGmt(Registrations.getGmt());
			break;
		default:
			Assert.fail("Supplier names not found");
		}
		JSONObject json = new JSONObject(surveyStarts.get(0));
		for (int i = 1; i < expectedElements.size(); i++) {
			List<String> element = expectedElements.get(i);
			String elementName = element.get(0);
			String expectedtValue = element.get(1);
			if (provider.equalsIgnoreCase("JEBBIT") || provider.equalsIgnoreCase("LUCIDAPI"))
				Assert.assertEquals(expectedtValue, json.getBigDecimal(elementName).toString());
			else
				Assert.assertEquals(expectedtValue, json.getString(elementName).toString());
		}

	}

	@Then("I verify {string} survey prequal events contain the following elements")
	public void verifyJsonPrequalElements(String provider, DataTable table) {
		List<List<String>> expectedPrequalQuestions = table.asLists();
		List<String> prequals = null;
		switch (provider.toUpperCase()) {
		case "LUCID":
			prequals = logReader.getAllLucidPrequalsByGmt(Registrations.getGmt());
			break;
		case "LUCIDAPI":
			prequals = logReader.getAllLucidApiSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "QMEE":
			prequals = logReader.getAllQmeeSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "JEBBIT":
			prequals = logReader.getAllJebbitSurveyStartsByGmt(Registrations.getGmt());
			break;
		case "GRL":
			prequals = logReader.getAllGRLSurveyStartsByGmt(Registrations.getGmt());
			break;
		default:
			Assert.fail("Supplier names not found");
		}
		Assert.assertTrue("Could not find prequal questions in " + provider + "prequalsLogger  logs",
				prequals != null & prequals.size() > 0);

		HashMap<String, String> actualPrequalQuestions = new HashMap<String, String>();
		for (String start : prequals) {
			JSONObject jsonObject = new JSONObject(start);
			JSONArray questions = jsonObject.getJSONArray("questions");
			for (Object question : questions) {
				JSONObject jsonLineItem = (JSONObject) question;
				String questionNumber = jsonLineItem.getString("questionid");
				JSONArray answers = jsonLineItem.getJSONArray("answerids");
				String answernumber = null;
				for (Object answer : answers) {
					answernumber = answernumber + answer;
					System.out.println("Answer : " + answer);
				}
				actualPrequalQuestions.put(questionNumber, answernumber);
			}

		}

		if (actualPrequalQuestions.size() != expectedPrequalQuestions.size() - 1)
			Assert.fail("Actual question count does not matech expected question count");

		for (int i = 1; i < expectedPrequalQuestions.size(); i++) {
			List<String> element = expectedPrequalQuestions.get(i);
			String elementKey = element.get(0);
			String elementValue = element.get(1);
			if (actualPrequalQuestions.containsKey(elementKey))
				Assert.assertEquals("Answer for question " + elementKey + " not as expected", elementValue,
						actualPrequalQuestions.get(elementKey));
			else
				Assert.fail("Can not find queestion id");

		}

	}

	@Then("I verify SQS events in the ListenersLogger log$")
	public void thenVerifyListenerLofFile(DataTable table) {
	 
		ArrayList<ListenersLoggerEventsDto> actualEventList = new ArrayList<ListenersLoggerEventsDto>();
		List<String> eventsToIgnore = new ArrayList<String> (); 
		eventsToIgnore.add("Lucid_Link_Redirect");
		eventsToIgnore.add("OFFERWALL_IMPRESSION");
		
		
		
		List<String> logEventList = logReader.getAllListenersEventsByGmt(Registrations.getGmt());

		logEventList = logReader.scrubEventList(logEventList,eventsToIgnore);
		
		
//		List<String> logEventList =  new ArrayList<String>();
//		logEventList.add("{\"EventTime\":\"2023-08-28 10:43:37\",\"IsTestData\":true,\"Client\":{\"DeviceType\":\"DESKTOP\",\"IpAddress\":\"10.37.1.39\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\",\"UserAgentId\":\"59954\"},\"Source\":{\"EmailVendorId\":\"\",\"ForeignSource\":\"\",\"IpAddress\":\"10.1.1.77\",\"OriginatingUrl\":\"https://offers.qa.pch.com/survey\",\"TrackingToken\":\"\",\"CBL\":\"PCHCOM\"},\"Session\":{\"SessionToken\":\"9903728e-dca0-4a77-b88f-07aa55cb5283\",\"MidSessionKey\":\"4HOIIL1K1O-8162f3\",\"GaSessionId\":\"1799600600.1693233785\"},\"Type\":\"MID_START\",\"ReferenceId\":\"\",\"TransactionId\":\"RE02Q-64ECB299-XUMAZ8\",\"Data\":{\"ExperienceDeviceType\":\"EXPDESKTOP\",\"GMT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"OAT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"ReceivedAt\":\"2023-08-28T14:43:37.276181Z\",\"RefererURL\":\"https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens\",\"TSRC\":\"\",\"TSRC2\":\"\",\"UserSignedIn\":\"1\",\"PlacementId\":\"Auto_Lucid_Tokens\",\"MidKey\":\"4HOIIL1K1O\",\"pchId\":null,\"pchSurveyId\":null}}\r\n"
//				+ "");
//		
//		logEventList.add("{\"EventTime\":\"2023-08-28 10:43:37\",\"IsTestData\":true,\"Client\":{\"DeviceType\":\"DESKTOP\",\"IpAddress\":\"10.60.133.242\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\",\"UserAgentId\":\"59954\"},\"Source\":{\"EmailVendorId\":\"\",\"ForeignSource\":\"\",\"IpAddress\":\"10.1.1.77\",\"OriginatingUrl\":\"https://offers.qa.pch.com/survey/profile/basic\",\"TrackingToken\":\"\",\"CBL\":\"PCHCOM\"},\"Session\":{\"SessionToken\":\"9903728e-dca0-4a77-b88f-07aa55cb5283\",\"MidSessionKey\":\"4HOIIL1K1O-8162f3\",\"GaSessionId\":\"1799600600.1693233785\"},\"Type\":\"BASIC_PROFILE_DISPLAY\",\"ReferenceId\":\"\",\"TransactionId\":\"IN02Q-64ECB299-6YR3XV\",\"Data\":{\"ExperienceDeviceType\":\"EXPDESKTOP\",\"GMT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"OAT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"ReceivedAt\":\"2023-08-28T14:43:37.850859Z\",\"RefererURL\":\"https://offers.qa.pch.com/survey/profile/basic?msk=4HOIIL1K1O-8162f3\",\"TSRC\":\"\",\"TSRC2\":\"\",\"UserSignedIn\":\"1\",\"PlacementId\":\"Auto_Lucid_Tokens\",\"MidKey\":\"4HOIIL1K1O\",\"Page\":\"basic\",\"ExpectConsentDisplayBox\":1}}\r\n"
//				+ "");
//		logEventList.add("{\"EventTime\":\"2023-08-28 10:44:42\",\"IsTestData\":true,\"Client\":{\"DeviceType\":\"DESKTOP\",\"IpAddress\":\"10.37.1.39\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\",\"UserAgentId\":\"59954\"},\"Source\":{\"EmailVendorId\":\"\",\"ForeignSource\":\"\",\"IpAddress\":\"10.1.1.77\",\"OriginatingUrl\":\"https://offers.qa.pch.com/profilebuilder/consent/counter\",\"TrackingToken\":\"\",\"CBL\":\"PCHCOM\"},\"Session\":{\"SessionToken\":\"9903728e-dca0-4a77-b88f-07aa55cb5283\",\"MidSessionKey\":\"4HOIIL1K1O-8162f3\",\"GaSessionId\":\"1799600600.1693233785\"},\"Type\":\"CONSENT_BOX_DISPLAY\",\"ReferenceId\":\"\",\"TransactionId\":\"RE02Q-64ECB2DA-0MPUAX\",\"Data\":{\"ExperienceDeviceType\":\"EXPDESKTOP\",\"GMT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"OAT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"ReceivedAt\":\"2023-08-28T14:44:42.494885Z\",\"RefererURL\":\"https://www.qa.pch.com/\",\"TSRC\":\"\",\"TSRC2\":\"\",\"UserSignedIn\":\"1\",\"PlacementId\":\"Auto_Lucid_Tokens\",\"MidKey\":\"4HOIIL1K1O\",\"Page\":\"basic\"}}\r\n"
//				+ "");
//		logEventList.add("{\"EventTime\":\"2023-08-28 10:44:51\",\"IsTestData\":true,\"Client\":{\"DeviceType\":\"DESKTOP\",\"IpAddress\":\"10.37.1.39\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\",\"UserAgentId\":\"59954\"},\"Source\":{\"EmailVendorId\":\"\",\"ForeignSource\":\"\",\"IpAddress\":\"10.1.1.77\",\"OriginatingUrl\":\"https://offers.qa.pch.com/survey/profile/complete/basic\",\"TrackingToken\":\"\",\"CBL\":\"PCHCOM\"},\"Session\":{\"SessionToken\":\"9903728e-dca0-4a77-b88f-07aa55cb5283\",\"MidSessionKey\":\"4HOIIL1K1O-8162f3\",\"GaSessionId\":\"1799600600.1693233785\"},\"Type\":\"PROFILE_COMPLETED\",\"ReferenceId\":\"\",\"TransactionId\":\"RE02Q-64ECB2E3-8YDRYX\",\"Data\":{\"ExperienceDeviceType\":\"EXPDESKTOP\",\"GMT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"OAT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"ReceivedAt\":\"2023-08-28T14:44:51.796390Z\",\"RefererURL\":\"https://offers.qa.pch.com/survey/profile/complete/basic\",\"TSRC\":\"\",\"TSRC2\":\"\",\"UserSignedIn\":\"1\",\"PlacementId\":\"Auto_Lucid_Tokens\",\"MidKey\":\"4HOIIL1K1O\",\"Page\":\"basic\",\"Consent\":\"Yes\"}}\r\n"
//				+ "");
//		logEventList.add("{\"EventTime\":\"2023-08-28 10:44:52\",\"IsTestData\":true,\"Client\":{\"DeviceType\":\"DESKTOP\",\"IpAddress\":\"10.37.1.39\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\",\"UserAgentId\":\"59954\"},\"Source\":{\"EmailVendorId\":\"\",\"ForeignSource\":\"\",\"IpAddress\":\"10.1.1.77\",\"OriginatingUrl\":\"https://offers.qa.pch.com/survey\",\"TrackingToken\":\"\",\"CBL\":\"SURVEYTAB\"},\"Session\":{\"SessionToken\":\"9903728e-dca0-4a77-b88f-07aa55cb5283\",\"MidSessionKey\":\"4HOIIL1K1O-8162f3\",\"ParentMidSessionKey\":\"\",\"GaSessionId\":\"1799600600.1693233785\"},\"Type\":\"SURVEY_START\",\"ReferenceId\":\"_Ef8XCwSJrLEXZs1B4DXuBVn5nA\",\"TransactionId\":\"RE02Q-64ECB2E4-GMR1H6\",\"Data\":{\"ExperienceDeviceType\":\"EXPDESKTOP\",\"GMT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"OAT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"ReceivedAt\":\"2023-08-28T14:44:52.565636Z\",\"RefererURL\":\"https://www.pch.com/survey\",\"TSRC\":\"\",\"TSRC2\":\"\",\"UserSignedIn\":\"1\",\"ContestEntriesGranted\":{\"amount\":0,\"key\":\"\"},\"Duration\":0,\"LengthInMinutes\":\"10\",\"MinimumGrossCPI\":\"0.01\",\"Payout\":0,\"PlacementId\":\"Auto_Lucid_Tokens\",\"Status\":\"\",\"TokensGranted\":{\"amount\":0,\"description\":\"\"},\"SurveyId\":0,\"SurveyReward\":{\"tokens\":{\"complete\":{\"amount\":500,\"description\":\"Tokens for Auto_Lucid_Tokens mid complete\"},\"incomplete\":{\"amount\":200,\"description\":\"token for survey Auto_Lucid_Tokens incomplete\"}},\"entries\":{\"complete\":{\"amount\":1,\"key\":\"AW123\"},\"incomplete\":{\"amount\":1,\"key\":\"AW124\"}}},\"SurveyType\":\"lucid\",\"DisplayAds\":0,\"PchSurveyId\":null,\"IntegrationMode\":\"link\",\"OriginalSupplier\":null,\"Buyer\":\"\"}}\r\n"
//				+ "");
//		logEventList.add("{\"EventTime\":\"2023-08-28 10:45:09\",\"IsTestData\":true,\"Client\":{\"DeviceType\":\"DESKTOP\",\"IpAddress\":\"10.60.133.242\",\"UserAgent\":\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36\",\"UserAgentId\":\"59954\"},\"Source\":{\"EmailVendorId\":\"\",\"ForeignSource\":\"\",\"IpAddress\":\"10.1.1.77\",\"OriginatingUrl\":\"https://offers.qa.pch.com/surveycomplete\",\"TrackingToken\":\"\",\"CBL\":\"SURVEYTAB\"},\"Session\":{\"SessionToken\":\"9903728e-dca0-4a77-b88f-07aa55cb5283\",\"MidSessionKey\":\"4HOIIL1K1O-8162f3\",\"ParentMidSessionKey\":\"\",\"GaSessionId\":\"1799600600.1693233785\"},\"Type\":\"SURVEY_END\",\"ReferenceId\":\"pKD9-BQ1jlrsryrAxEgv9VdJHrA\",\"TransactionId\":\"LU02Q-64ECB2F5-74ANTU\",\"Data\":{\"ExperienceDeviceType\":\"EXPDESKTOP\",\"GMT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"OAT\":\"0c1418c4-3d61-45b8-8d29-5689459446fc\",\"ReceivedAt\":\"2023-08-28T14:45:09.719563Z\",\"RefererURL\":\"https://www.pch.com/surveycomplete\",\"TSRC\":\"\",\"TSRC2\":\"\",\"UserSignedIn\":\"1\",\"ContestEntriesGranted\":{\"amount\":1,\"key\":\"AW123\"},\"Duration\":0,\"LengthInMinutes\":0,\"MinimumGrossCPI\":0,\"Payout\":\"0.4\",\"PlacementId\":\"Auto_Lucid_Tokens\",\"Status\":\"complete\",\"TokensGranted\":{\"amount\":500,\"description\":\"Tokens for Auto_Lucid_Tokens mid complete\"},\"SurveyId\":\"1344580\",\"SurveyReward\":{\"tokens\":{\"complete\":{\"amount\":500,\"description\":\"Tokens for Auto_Lucid_Tokens mid complete\"},\"incomplete\":{\"amount\":200,\"description\":\"token for survey Auto_Lucid_Tokens incomplete\"}},\"entries\":{\"complete\":{\"amount\":1,\"key\":\"AW123\"},\"incomplete\":{\"amount\":1,\"key\":\"AW124\"}}},\"SurveyType\":\"lucid\",\"DisplayAds\":0,\"PchSurveyId\":null,\"IntegrationMode\":\"link\",\"OriginalSupplier\":null,\"Buyer\":\"\"}}\r\n"
//				+ "");
//	
		
		
		
		
		

		for (String event : logEventList) {
			event = event.substring(event.indexOf("MessageBody") + 14);
			event = event.substring(0, event.indexOf("QueueUrl") - 3);
			event = event.replace("\\", "");
			System.out.println(event);
		}
		

		
		for (String event : logEventList) {
			event = event.substring(event.indexOf("MessageBody") + 14);
			event = event.substring(0, event.indexOf("QueueUrl") - 3);
			event = event.replace("\\", "");

			try {
			ListenersLoggerEventsDto eventObj = new ListenersLoggerEventsDto();
			JsonNode jsonNode = objectMapper.readTree(event);

			eventObj.setTestData(jsonNode.get("IsTestData").asBoolean());
			eventObj.setType(jsonNode.get("Type").asText());
			eventObj.setReferenceId(jsonNode.get("ReferenceId").asText());
			eventObj.setTransactionId(jsonNode.get("TransactionId").asText());
			eventObj.getClient().setDeviceType(jsonNode.get("Client").get("DeviceType").asText());
			eventObj.getClient().setIpAddress(jsonNode.get("Client").get("IpAddress").asText());
			eventObj.getClient().setUserAgent(jsonNode.get("Client").get("UserAgent").asText());
			eventObj.getClient().setUserAgentId(jsonNode.get("Client").get("UserAgentId").asText());

			if (jsonNode.get("Source").get("ForeignSource") != null) {
				eventObj.getSource().setForeignSource(jsonNode.get("Source").get("ForeignSource").asText());
				eventObj.getSource().setTrackingToken(jsonNode.get("Source").get("TrackingToken").asText());
				eventObj.getSource().setCBL(jsonNode.get("Source").get("CBL").asText());
				eventObj.getSource().setEmailVendorId(jsonNode.get("Source").get("EmailVendorId").asText());

			}
			eventObj.getSource().setIpAddress(jsonNode.get("Source").get("IpAddress").asText());
			eventObj.getSource().setOriginatingUrl(jsonNode.get("Source").get("OriginatingUrl").asText());

			eventObj.getSession().setSessionToken(jsonNode.get("Session").get("SessionToken").asText());
			eventObj.getSession().setGaSessionId(jsonNode.get("Session").get("GaSessionId").asText());

			if (jsonNode.get("Session").get("MidSessionKey") != null)
				eventObj.getSession().setMidSessionKey(jsonNode.get("Session").get("MidSessionKey").asText());

			if (jsonNode.get("Session").get("ParentMidSessionKey") != null)
				eventObj.getSession()
						.setParentMidSessionKey(jsonNode.get("Session").get("ParentMidSessionKey").asText());

			if (jsonNode.get("Data").get("Request") != null) {
				eventObj.getData().setGMT(jsonNode.get("Data").get("Request").get("gmt").asText());
				eventObj.getData().setOAT(jsonNode.get("Data").get("Request").get("oat").asText());
			} else {
				eventObj.getData().setGMT(jsonNode.get("Data").get("GMT").asText());
				eventObj.getData().setOAT(jsonNode.get("Data").get("OAT").asText());
			}
			if (jsonNode.get("Data").get("PlacementId") != null)
				eventObj.getData().setPlacementId(jsonNode.get("Data").get("PlacementId").asText());
			if (jsonNode.get("Data").get("MidKey") != null)
				eventObj.getData().setPlacementId(jsonNode.get("Data").get("MidKey").asText());
			if (jsonNode.get("Data").get("pchSurveyId") != null)
				eventObj.getData().setPchSurveyId(jsonNode.get("Data").get("pchSurveyId").asText());

			if (jsonNode.get("Data").get("RefererURL") != null)
				eventObj.getData().setPchSurveyId(jsonNode.get("Data").get("RefererURL").asText());

			if (jsonNode.get("Data").get("Page") != null)
				eventObj.getData().setPage(jsonNode.get("Data").get("Page").asText());

			if (jsonNode.get("Data").get("ContestEntriesGranted") != null) {
				eventObj.getData().getContestEntriesGranted().setAmount(jsonNode.get("Data").get("ContestEntriesGranted").get("amount").asInt());
				eventObj.getData().getContestEntriesGranted().setKey(jsonNode.get("Data").get("ContestEntriesGranted").get("key").asText());
			}				
			
			
			
			if (jsonNode.get("Data").get("Duration") != null)
				eventObj.getData().setDuration(jsonNode.get("Data").get("Duration").asText());
			
			if (jsonNode.get("Data").get("LengthInMinutes") != null)
				eventObj.getData().setLengthInMinutes(jsonNode.get("Data").get("LengthInMinutes").asText());

			if (jsonNode.get("Data").get("MinimumGrossCPI") != null)
				eventObj.getData().setMinimumGrossCPI(jsonNode.get("Data").get("MinimumGrossCPI").asText());

			if (jsonNode.get("Data").get("Payout") != null)
				eventObj.getData().setPayout(jsonNode.get("Data").get("Payout").asText());

			if (jsonNode.get("Data").get("PlacementId") != null)
				eventObj.getData().setPlacementId(jsonNode.get("Data").get("PlacementId").asText());

			if (jsonNode.get("Data").get("Status") != null)
				eventObj.getData().setStatus(jsonNode.get("Data").get("Status").asText());
			
			
			
			if (jsonNode.get("Data").get("TokensGranted") != null) {
				eventObj.getData().getTokensGranted().setAmount(jsonNode.get("Data").get("TokensGranted").get("amount").asInt());
				eventObj.getData().getTokensGranted().setDescription(jsonNode.get("Data").get("TokensGranted").get("description").asText());
			}				
			
			
			

			if (jsonNode.get("Data").get("SurveyId") != null)
				eventObj.getData().setSurveyId(jsonNode.get("Data").get("SurveyId").asInt());
 				
			if (jsonNode.get("Data").get("SurveyReward") != null) {
				eventObj.getData().getSurveyReward().getTokens().getComplete().setAmount(jsonNode.get("Data").get("SurveyReward").get("tokens").get("complete").get("amount").asInt());
				eventObj.getData().getSurveyReward().getTokens().getComplete().setDescription(jsonNode.get("Data").get("SurveyReward").get("tokens").get("complete").get("description").asText());
				eventObj.getData().getSurveyReward().getTokens().getIncomplete().setAmount(jsonNode.get("Data").get("SurveyReward").get("tokens").get("incomplete").get("amount").asInt());
				eventObj.getData().getSurveyReward().getTokens().getIncomplete().setDescription(jsonNode.get("Data").get("SurveyReward").get("tokens").get("incomplete").get("description").asText());

				eventObj.getData().getSurveyReward().getEntries().getComplete().setAmount(jsonNode.get("Data").get("SurveyReward").get("entries").get("complete").get("amount").asInt());
				eventObj.getData().getSurveyReward().getEntries().getComplete().setKey(jsonNode.get("Data").get("SurveyReward").get("entries").get("complete").get("key").asText());
				eventObj.getData().getSurveyReward().getEntries().getIncomplete().setAmount(jsonNode.get("Data").get("SurveyReward").get("entries").get("incomplete").get("amount").asInt());
				eventObj.getData().getSurveyReward().getEntries().getIncomplete().setKey(jsonNode.get("Data").get("SurveyReward").get("entries").get("incomplete").get("key").asText());

			}

			actualEventList.add(eventObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}

		

		List<Map<String, String>> expectedLogData = table.asMaps(String.class, String.class);
		Assert.assertEquals("Assert expected and actual log enty counts are equal", expectedLogData.size(),
				actualEventList.size());

		String midSessionKeyHold = null;
		int cnt = 0;
		for (Map<String, String> columns : expectedLogData) {

			System.out.println(" verified log row " + cnt);

			
			Assert.assertEquals(columns.get("Type"), actualEventList.get(cnt).getType());

			if (columns.get("IsTestData") != null)
				Assert.assertEquals(Boolean.valueOf(columns.get("IsTestData")), actualEventList.get(cnt).isTestData());

			if (columns.get("ReferenceId") != null) {
				if (columns.get("ReferenceId").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getReferenceId());
				if (columns.get("ReferenceId").equals("empty"))
					Assert.assertEquals("", actualEventList.get(cnt).getReferenceId());
			}

			if (columns.get("TransactionId") != null) {
				if (columns.get("TransactionId").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getTransactionId());
			}

			
			
			
			
			if (columns.get("Client.DeviceType") != null)
				Assert.assertEquals(columns.get("Client.DeviceType"),actualEventList.get(cnt).getClient().getDeviceType());

			Assert.assertNotNull(actualEventList.get(cnt).getClient().getIpAddress());
			Assert.assertNotNull(actualEventList.get(cnt).getClient().getUserAgent());
			Assert.assertNotNull(actualEventList.get(cnt).getClient().getUserAgentId());

			
			
			
			if (columns.get("Source.OriginatingUrl") != null)
				Assert.assertEquals(columns.get("Source.OriginatingUrl"),actualEventList.get(cnt).getSource().getOriginatingUrl());

			Assert.assertNotNull(actualEventList.get(cnt).getSource().getIpAddress());

			if (columns.get("Source.CBL") != null)
				Assert.assertEquals(columns.get("Source.CBL"), actualEventList.get(cnt).getSource().getCBL());

			if (columns.get("Source.TrackingToken") != null) {
				if (columns.get("Source.TrackingToken").equals("empty"))
					Assert.assertEquals("", actualEventList.get(cnt).getSource().getTrackingToken());
			}

			if (columns.get("Session.SessionToken") != null) {
				if (columns.get("Session.SessionToken").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getSessionToken());
				if (columns.get("Session.SessionToken").equals("previous"))
					Assert.assertEquals(actualEventList.get(cnt - 1).getSession().getSessionToken(),actualEventList.get(cnt).getSession().getSessionToken());
				if (columns.get("Session.SessionToken").equals("new")) {
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getSessionToken());
					Assert.assertNotEquals(actualEventList.get(cnt - 1).getSession().getSessionToken(),	actualEventList.get(cnt).getSession().getSessionToken());
				}

			}

			if (columns.get("Session.MidSessionKey") != null) {
				if (columns.get("Session.MidSessionKey").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getMidSessionKey());
				if (columns.get("Session.MidSessionKey").equals("previous"))
					Assert.assertEquals(midSessionKeyHold, actualEventList.get(cnt).getSession().getMidSessionKey());
				if (columns.get("Session.MidSessionKey").equals("new"))
					Assert.assertNotEquals(midSessionKeyHold, actualEventList.get(cnt).getSession().getMidSessionKey());

				midSessionKeyHold = actualEventList.get(cnt).getSession().getMidSessionKey();

			}

			if (columns.get("Session.GaSessionId") != null) {
				if (columns.get("Session.GaSessionId").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getGaSessionId());
				if (columns.get("Session.GaSessionId").equals("previous"))
					Assert.assertEquals(actualEventList.get(cnt - 1).getSession().getGaSessionId(),
							actualEventList.get(cnt).getSession().getGaSessionId());
			}

			if (columns.get("Session.ParentMidSessionKey") != null) {
				if (columns.get("Session.ParentMidSessionKey").equals("empty"))
					Assert.assertEquals("", actualEventList.get(cnt).getSession().getParentMidSessionKey());
			}

			if (columns.get("Data.GMT") != null && columns.get("Data.GMT").equals("fromReg"))
				Assert.assertEquals(actualEventList.get(cnt).getData().getGMT(), Registrations.getGmt());

			if (columns.get("Data.OAT") != null && columns.get("Data.OAT").equals("fromReg"))
				Assert.assertEquals(actualEventList.get(cnt).getData().getOAT(), Registrations.getGmt());

			if (columns.get("Data.RefererURL") != null)
				Assert.assertEquals(columns.get("Data.RefererUR"), actualEventList.get(cnt).getData().getRefererURL());

			
			if (columns.get("Data.Page") != null)
				Assert.assertEquals(columns.get("Data.Page"), actualEventList.get(cnt).getData().getPage());
			
			if (columns.get("Data.ContestEntriesGranted") != null) {
				String[] values = columns.get("Data.ContestEntriesGranted").split(",");
				values[1] = values[1].replace("empty", "");
				Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData().getContestEntriesGranted().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getContestEntriesGranted().getKey());
			}			
			
			
			if (columns.get("Data.Tokens.Complete") != null) {
				String[] values = columns.get("Data.Tokens.Complete").split(",");
				values[1] = values[1].replace("empty", "");
				Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData().getSurveyReward().getTokens().getComplete().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getSurveyReward().getTokens().getComplete().getDescription());
			}
			
			if (columns.get("Data.Tokens.Incomplete") != null) {
				String[] values = columns.get("Data.Tokens.Incomplete").split(",");
				values[1] = values[1].replace("empty", "");
				Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData().getSurveyReward().getTokens().getIncomplete().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getSurveyReward().getTokens().getIncomplete().getDescription());
				
			}
			
			if (columns.get("Data.Entries.Complete") != null) {
				String[] values = columns.get("Data.Entries.Complete").split(",");
				values[1] = values[1].replace("empty", "");
				Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData().getSurveyReward().getEntries().getComplete().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getSurveyReward().getEntries().getComplete().getKey());
	
			}

			if (columns.get("Data.Entries.Incomplete") != null) {
				String[] values = columns.get("Data.Entries.Incomplete").split(",");
				values[1] = values[1].replace("empty", "");
				Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData().getSurveyReward().getEntries().getIncomplete().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getSurveyReward().getEntries().getIncomplete().getKey());
				
			}
			
			if (columns.get("Data.Duration") != null)  
				Assert.assertEquals(columns.get("Data.Duration"), actualEventList.get(cnt).getData().getDuration());

			if (columns.get("Data.LengthInMinutes") != null)  
				Assert.assertEquals(columns.get("Data.LengthInMinutes"), actualEventList.get(cnt).getData().getLengthInMinutes());


			if (columns.get("Data.MinimumGrossCPI") != null)  
				Assert.assertEquals(columns.get("Data.MinimumGrossCPI"), actualEventList.get(cnt).getData().getMinimumGrossCPI());
	
			if (columns.get("Data.Payout") != null)  
				if (columns.get("Data.Payout").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getData().getPayout());
				else	
					Assert.assertEquals(columns.get("Data.Payout"), actualEventList.get(cnt).getData().getPayout());
			
			if (columns.get("Data.PlacementId") != null)  
				Assert.assertEquals(columns.get("Data.PlacementId"), actualEventList.get(cnt).getData().getPlacementId());
	
			
			if (columns.get("Data.Status") != null)  
				if(columns.get("Data.Status").equals("empty"))
					Assert.assertEquals("",actualEventList.get(cnt).getData().getStatus());
				else
					Assert.assertEquals(columns.get("Data.Status"), actualEventList.get(cnt).getData().getStatus());
			
			
			
			cnt++;

		}

	}

	@Then("I verify that new category surveys are available in featured section")
	public void thenVerifyNewCategoySurveysInFeature() {
		Assert.assertTrue("Verify new category survey count > 0",
				surveyPage.getNewCategorySurveyCountInFeatureArea() > 0);
		surveyPage.getSurveyCountInFeatureArea();
	}

	@Then("I verify that all category surveys not marked with new label in featured section")
	public void thenVerifySurveysInFeature() {
		Assert.assertTrue("Verify featured survey count",
				surveyPage.getSurveyCountInFeatureArea() > surveyPage.getNewCategorySurveyCountInFeatureArea());

	}

	@Then("I verify that new category surveys are available in main survey section")
	public void thenVerifyNewCategoySurveysInSurvey() {
		Assert.assertTrue("Verify new category survey count > 0",
				surveyPage.getNewCategorySurveyCountInSurveyArea() > 0);
	}

	@Then("I verify that all category surveys not marked with new label in main survey section")
	public void thenVerifySurveysInSurvey() {
		Assert.assertTrue("Verify main survey count",
				surveyPage.getSurveyCountInSurveyArea() > surveyPage.getNewCategorySurveyCountInSurveyArea());

	}

	@When("I click the Yes Im In button in survey handraiser section")
	public void whenIClickYesIminButton() {
		surveyPage.clickYesImInButton();
	}

	@When("I click the Redeem Tokens image")
	public void whenIClickRedeenTokens() {
		surveyPage.clickRedeemTokensImage();
	}

	@Then("I should be redirected to the Surveys Tab")
	public void i_should_be_redirected_to_the_surveys_tab() {
		Assert.assertEquals("User is not redirected to Survey Tab", ConfigurationReader.getUrl(),
				surveyPage.getCurrentURL());
	}

	@Then("I should be redirected to the Program Terms page in new tab")
	public void i_should_be_redirected_to_the_program_terms_page_in_new_tab() {
		surveyPage.switchToLastOpenTab();
		Assert.assertEquals("User is not redirected to Program Terms page", ConfigurationReader.getProgramTermsUrl(),
				surveyPage.getCurrentURL());
	}

	@When("I start chrome network session capture")
	public void whenIStartNetworkSessionCapture() {
		networkTabData = null;
		networkTabData = SeleniumDevTools.startDevToolsNetworkSession((ChromeDriver) WebdriverBuilder.getDriver());
	}

	@When("I stop chrome network session capture")
	public void whenIStopNetworkSessionCapture() {
		SeleniumDevTools.stopDevToolsNetworkSession();
		System.out.println("************network tab results ***************************************************");
		for (DevToolsNetworkTabDto dto : networkTabData) {
			System.out.println(dto.getUrl() + "      " + dto.getPostData());
		}

	}

	@Then("I do not see Play Now Button")
	public void i_do_not_see_play_now_button() {
		surveyPage.verifyPlayNowIsNotPresent();
	}

	@Then("I see the Play Now button")
	public void i_see_the_play_now_button() {
		assertTrue(WebdriverBuilder.getDriver().findElement(By.xpath("//a[@aria-label='Bonus game unlocked play now']"))
				.isDisplayed());
	}

	@And("I am able to click on Play Now button")
	public void i_am_able_to_click_on_play_now_button() {
		WebdriverBuilder.getDriver().findElement(By.xpath("//a[@aria-label='Bonus game unlocked play now']")).click();
	}

	@And("I get redirected to the Bonus game page in the new tab")
	public void i_get_redirected_to_the_bonus_game_page_in_the_new_tab() {
		surveyPage.switchToLastOpenTab();
		Assert.assertEquals("User is not redirected to Bonus game page", ConfigurationReader.getBonusGameUrl(),
				surveyPage.getCurrentURL());

	}

	@Then("I verify {string} event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier {string} and device {string}")
	public void thenGATagsInLog(String event, String supplier, String device) {
		List<String> jsonTagList = logReader.getAllGA3TagsByGmt(Registrations.getGmt());
		boolean gaTagFound = false;
		for (String jsonStr : jsonTagList) {
			JSONObject json = new JSONObject(jsonStr);
			if (json.getString("ea").equalsIgnoreCase(event)) {
				if (json.getString("ec").equalsIgnoreCase(supplier + "/" + device))
					gaTagFound = true;
				break;
			}
		}
		if (!gaTagFound)
			Assert.fail("Cannot find GA3 tag for survey start event " + event + supplier + device);
	}

	@Then("I verify {string} event GA4 tags are being recorded in Ga4 logfile for supplier {string} and device {string}")
	public void thenGA4TagsInLog(String event, String supplier, String device) {
		List<String> jsonTagList = logReader.getAllGA4TagsByGmt(Registrations.getGmt());
		boolean gaTagFound = false;
		for (String jsonStr : jsonTagList) {
			JSONObject json = new JSONObject(jsonStr);
			JSONArray eventArray = json.getJSONArray("events");
			if (eventArray.getJSONObject(0).getString("name").equalsIgnoreCase(event)) {
				if (eventArray.getJSONObject(0).getJSONObject("params").getString("supplier_device_type")
						.equalsIgnoreCase(supplier + "/" + device))
					gaTagFound = true;
				break;
			}
		}
		if (!gaTagFound)
			Assert.fail("Cannot find GA4 tag for survey event " + event + supplier + device);
	}

}
