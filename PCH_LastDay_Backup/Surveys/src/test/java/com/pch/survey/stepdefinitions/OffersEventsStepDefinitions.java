package com.pch.survey.stepdefinitions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pch.survey.apis.OfferApis;
import com.pch.survey.centralservices.Registrations;
import com.pch.survey.dtos.ListenersLoggerEventsDto;
import com.pch.survey.dtos.SurveyEventsDto;
import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.pages.surveytab.SurveyCompletePage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.LogReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OffersEventsStepDefinitions {

	private static String env = ConfigurationReader.getEnvironment().toLowerCase();

	private LogReader logReader = new LogReader();
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String surveyCompleteUrl = null;
	private String surveyEndStatus = "";
	private SurveyCompletePage surveyCompletePage = new SurveyCompletePage();;
	private MpoRegistrationPage mpoRegPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());
	private SurveyMainPage surveyPage = new SurveyMainPage(WebdriverBuilder.getDriver());

	@When("I generate the survey endpoint url with {string} status")
	public void whenGeneratedSurveyURL(String status) {
		surveyPage.waitForSurveypage();
		surveyEndStatus = status;
		surveyCompleteUrl = OfferApis.getSurveyEndPointUrl(status);
	}

	@When("I call the survey endpoint url")
	public void whenCompleteSurveyUsingCompleteURl() {
		surveyPage.waitUntilThePageLoads();
		WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
		if (WebdriverBuilder.getDriver().getCurrentUrl().contains("https://mpo." + env + ".pch.com/")) {
			surveyPage.closePhpdebugbar();
			mpoRegPage.CreateFullRegUserForm();
		}
		if (surveyEndStatus.equalsIgnoreCase("complete")) {
			surveyPage.closePhpdebugbar();
			surveyCompletePage.completeSurvey();
		}
		if (surveyEndStatus.equalsIgnoreCase("incomplete")) {
			surveyPage.closePhpdebugbar();
			surveyCompletePage.completeSurvey();
		}
	}

	@When("I generate the QMEE survey endpoint url with {string} status")
	public void whenGeneratedQMEESurveyURL(String status) {
		String qmeelandingPage = "https://api-profiler.qurated.ai/prescreen/check?";
		surveyPage.grabNetworkCalls(qmeelandingPage);
		surveyPage.getQmeeSurveyStartURL();
		surveyEndStatus = status;
		surveyCompleteUrl = OfferApis.getSurveyEndPointUrl(status);
	}

	@When("I generate the GRL survey endpoint url with {string} status")
	public void whenGeneratedGRLSurveyURL(String status) {
		List<String> events = getEventList("GRL", "start");
		ObjectMapper objectMapper = new ObjectMapper();
		SurveyEventsDto surveyData = null;
		System.out.println("Event json String  = " + events.get(0));
		try {
			surveyData = objectMapper.readValue(events.get(0), SurveyEventsDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not parse json");
		}

		surveyCompleteUrl = OfferApis.generateGRLEndEvent(surveyData.getMidSessionKey(), status);

	}

	@When("I completed the survey using survey complete url by creating password for NoPassword user")
	public void whenCompleteSurveyUsingCompleteURlForNoPwdUser() {
		WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
		surveyPage.closePhpdebugbar();
		surveyCompletePage.completeSurveyForSilverUser();
	}

	@When("I completed the survey using survey complete url without creating password for NoPassword user")
	public void whenCompleteSurveyUsingCompleteURlAndSkipForNoPasswordUser() {
		WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
		surveyPage.closePhpdebugbar();
		surveyCompletePage.completeSurveyAndSkipForSilverUser();
	}

	@Then("I verify {string} survey start event json contains the following elements")
	public void verifyJsonElements(String provider, DataTable table) {
		List<String> eventLilst = getEventList(provider, "start");
		validateEventJson(eventLilst, table);
	}

	@Then("I verify {string} survey end event json contains the following elements")
	public void verifyJsonEndElements(String provider, DataTable table) {
		List<String> surveyEnds = getEventList(provider, "end");
		validateEventJson(surveyEnds, table);
	}

	private List<String> getEventList(String provider, String eventType) {
		List<String> surveyEvents = null;
		switch (provider.toUpperCase()) {
		case "PURESPECTRUM":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllPureSpectrumStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllPureSpectrumEndsByGmt(Registrations.getGmt());
			break;
		case "PURESPECTRUMAPI":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllPureSpectrumApiStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllPureSpectrumApiEndsByGmt(Registrations.getGmt());
			break;
		case "LUCID":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllLucidSurveyStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllLucidSurveyEndsByGmt(Registrations.getGmt());
			break;
		case "LUCIDAPI":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllLucidApiSurveyStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllLucidApiSurveyEndsByGmt(Registrations.getGmt());
			break;
		case "QMEE":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllQmeeSurveyStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllQmeeSurveyEndsByGmt(Registrations.getGmt());

			break;
		case "JEBBIT":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllJebbitSurveyStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllJebbitSurveyEndsByGmt(Registrations.getGmt());
			break;
		case "GRL":
			if (eventType.equalsIgnoreCase("start"))
				surveyEvents = logReader.getAllGRLSurveyStartsByGmt(Registrations.getGmt());
			else
				surveyEvents = logReader.getAllGRLSurveyEndsByGmt(Registrations.getGmt());
			break;
		default:
			Assert.fail("Supplier names not found");
		}

		return surveyEvents;

	}

	private void validateEventJson(List<String> surveyEventList, DataTable table) {
		ObjectMapper objectMapper = new ObjectMapper();
		SurveyEventsDto surveyData = null;
		if (surveyEventList.size() == 0)
			Assert.fail("No events found in log");
		try {
			System.out.println("Event json String  = " + surveyEventList.get(0));

			surveyData = objectMapper.readValue(surveyEventList.get(0), SurveyEventsDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not parse json");
		}

		List<Map<String, String>> expectedLogData = table.asMaps(String.class, String.class);
		for (Map<String, String> columns : expectedLogData) {

			Assert.assertEquals(columns.get("CBL"), surveyData.getCbl());

			if (columns.get("OAT").equals("fromReg"))
				Assert.assertEquals(Registrations.getGmt(), surveyData.getOat());
			else
				Assert.assertEquals(columns.get("OAT"), Registrations.getGmt());

			if (columns.get("GMT").equals("fromReg"))
				Assert.assertEquals(Registrations.getGmt(), surveyData.getGmt());
			else
				Assert.assertEquals(columns.get("GMT"), Registrations.getGmt());

			if (columns.get("Duration").equalsIgnoreCase("<= loi"))
				Assert.assertTrue(
						surveyData.getDuration().compareTo(new BigDecimal(surveyData.getLengthInMinutes())) <= 0);
			else if (columns.get("Duration").equalsIgnoreCase("notnull"))
				Assert.assertNotNull(surveyData.getDuration());
			else
				Assert.assertEquals(new BigDecimal(columns.get("Duration")), surveyData.getDuration());

			Assert.assertEquals(columns.get("LengthInMinutes"), surveyData.getLengthInMinutes());
			if (columns.get("MinimumGrossCPI").equalsIgnoreCase("empty"))
				Assert.assertEquals("", surveyData.getMinimumGrossCPI());
			else
				Assert.assertEquals(columns.get("MinimumGrossCPI"), surveyData.getMinimumGrossCPI());

			if (columns.get("Payout").equalsIgnoreCase(">=cpi"))
				Assert.assertTrue(
						surveyData.getPayout().compareTo(new BigDecimal(surveyData.getMinimumGrossCPI())) >= 0);
			else if (columns.get("Payout").equalsIgnoreCase("notnull"))
				Assert.assertNotNull(surveyData.getPayout());
			else
				Assert.assertEquals(new BigDecimal(columns.get("Payout")), surveyData.getPayout());

			Assert.assertEquals(columns.get("PlacementId"), surveyData.getPlacementId());

			if (columns.get("Status").equalsIgnoreCase("empty"))
				Assert.assertEquals(surveyData.getStatus(), "");
			else
				Assert.assertEquals(columns.get("Status"), surveyData.getStatus());
			Assert.assertEquals(columns.get("SurveyType"), surveyData.getSurveyType());

			String[] entriesGranted = columns.get("ContestEntriesGranted").split(",");
			if (entriesGranted[0].equalsIgnoreCase("null")) {
				Assert.assertNull(surveyData.getContestEntriesGranted().getAmount());
				Assert.assertNull(surveyData.getContestEntriesGranted().getKey());
			} else {
				Assert.assertEquals(Integer.valueOf(entriesGranted[0]),
						surveyData.getContestEntriesGranted().getAmount());
				if (entriesGranted[1].equalsIgnoreCase("empty"))
					Assert.assertEquals(surveyData.getContestEntriesGranted().getKey(), "");
				else
					Assert.assertEquals(entriesGranted[1], surveyData.getContestEntriesGranted().getKey());

			}

			String[] tokensGranted = columns.get("TokensGranted").split(",");
			if (tokensGranted[0].equalsIgnoreCase("null")) {
				Assert.assertNull(surveyData.getTokensGranted().getAmount());
				Assert.assertNull(surveyData.getTokensGranted().getDescription());
			} else {
				Assert.assertEquals(Integer.valueOf(tokensGranted[0]), surveyData.getTokensGranted().getAmount());
				if (tokensGranted[1].equalsIgnoreCase("empty"))
					Assert.assertEquals(surveyData.getTokensGranted().getDescription(), "");
				else
					Assert.assertEquals(tokensGranted[1], surveyData.getTokensGranted().getDescription());
			}

			String[] surveyTokensComplete = columns.get("SurveyTokensComplete").split(",");
			if (surveyTokensComplete[0].equalsIgnoreCase("null")) {
				Assert.assertNull(surveyData.getSurveyReward().getTokens().getComplete().getAmount());
				Assert.assertNull(surveyData.getSurveyReward().getTokens().getComplete().getDescription());
			} else {
				Assert.assertEquals(Integer.valueOf(surveyTokensComplete[0]),
						surveyData.getSurveyReward().getTokens().getComplete().getAmount());
				if (surveyTokensComplete[1].equalsIgnoreCase("empty"))
					Assert.assertEquals(surveyData.getSurveyReward().getTokens().getComplete().getDescription(), "");
				else
					Assert.assertEquals(surveyTokensComplete[1].toUpperCase(),
							surveyData.getSurveyReward().getTokens().getComplete().getDescription().toUpperCase());
			}

			String[] surveyTokensInComplete = columns.get("SurveyTokensInComplete").split(",");
			if (surveyTokensInComplete[0].equalsIgnoreCase("null")) {
				Assert.assertNull(surveyData.getSurveyReward().getTokens().getIncomplete().getAmount());
				Assert.assertNull(surveyData.getSurveyReward().getTokens().getIncomplete().getDescription());
			} else {
				Assert.assertEquals(Integer.valueOf(surveyTokensInComplete[0]),
						surveyData.getSurveyReward().getTokens().getIncomplete().getAmount());
				if (surveyTokensInComplete[1].equalsIgnoreCase("empty"))
					Assert.assertEquals(surveyData.getSurveyReward().getTokens().getIncomplete().getDescription(), "");
				else
					Assert.assertEquals(surveyTokensInComplete[1],
							surveyData.getSurveyReward().getTokens().getIncomplete().getDescription());
			}

			String[] surveyEntriesComplete = columns.get("SurveyEntriesComplete").split(",");
			if (surveyEntriesComplete[0].equalsIgnoreCase("null")) {
				Assert.assertNull(surveyData.getSurveyReward().getEntries().getComplete().getAmount());
				Assert.assertNull(surveyData.getSurveyReward().getEntries().getComplete().getKey());
			} else {
				Assert.assertEquals(Integer.valueOf(surveyEntriesComplete[0]),
						surveyData.getSurveyReward().getEntries().getComplete().getAmount());
				if (surveyEntriesComplete[1].equalsIgnoreCase("empty"))
					Assert.assertEquals(surveyData.getSurveyReward().getEntries().getComplete().getKey(), "");
				else
					Assert.assertEquals(surveyEntriesComplete[1],
							surveyData.getSurveyReward().getEntries().getComplete().getKey());
			}

			String[] surveyEntriesInComplete = columns.get("SurveyEntriesInComplete").split(",");
			if (surveyEntriesInComplete[0].equalsIgnoreCase("null")) {
				Assert.assertNull(surveyData.getSurveyReward().getEntries().getIncomplete().getAmount());
				Assert.assertNull(surveyData.getSurveyReward().getEntries().getIncomplete().getKey());
			} else {
				Assert.assertEquals(Integer.valueOf(surveyEntriesInComplete[0]),
						surveyData.getSurveyReward().getEntries().getIncomplete().getAmount());
				if (surveyEntriesInComplete[1].equalsIgnoreCase("empty"))
					Assert.assertEquals(surveyData.getSurveyReward().getEntries().getIncomplete().getKey(), "");
				else
					Assert.assertEquals(surveyEntriesInComplete[1],
							surveyData.getSurveyReward().getEntries().getIncomplete().getKey());
			}
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
		List<Map<String, String>> expectedLogData = table.asMaps(String.class, String.class);
		List<String> eventsToInclude = new ArrayList<String>();

		for (Map<String, String> columns : expectedLogData) {
			eventsToInclude.add(columns.get("Type"));
		}
		ArrayList<ListenersLoggerEventsDto> actualEventList = new ArrayList<ListenersLoggerEventsDto>();

		List<String> logEventList = logReader.getAllListenersEventsByGmt(Registrations.getGmt());
		for (String event : logEventList) {
			event = event.substring(event.indexOf("MessageBody") + 14);
			event = event.substring(0, event.indexOf("QueueUrl") - 3);
			event = event.replace("\\", "");
			System.out.println(event);
		}

		logEventList = logReader.scrubEventList(logEventList, eventsToInclude);

		for (String event : logEventList) {
			event = event.substring(event.indexOf("MessageBody") + 14);
			event = event.substring(0, event.indexOf("QueueUrl") - 3);
			event = event.replace("\\", "");
			System.out.println(event);
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

				try {
					eventObj.getSession().setGaSessionId(jsonNode.get("Session").get("GaSessionId").asText());
				} catch (NullPointerException npe) {
				}
				if (jsonNode.get("Session").get("MidSessionKey") != null)
					eventObj.getSession().setMidSessionKey(jsonNode.get("Session").get("MidSessionKey").asText());

				if (jsonNode.get("Session").get("ParentMidSessionKey") != null)
					eventObj.getSession()
							.setParentMidSessionKey(jsonNode.get("Session").get("ParentMidSessionKey").asText());

				try {
					if (jsonNode.get("Data").get("Request") != null) {
						eventObj.getData().setGMT(jsonNode.get("Data").get("Request").get("gmt").asText());
						eventObj.getData().setOAT(jsonNode.get("Data").get("Request").get("oat").asText());
					} else {
						eventObj.getData().setGMT(jsonNode.get("Data").get("GMT").asText());
						eventObj.getData().setOAT(jsonNode.get("Data").get("OAT").asText());
					}
				} catch (NullPointerException nullExecption) {
					eventObj.getData().setGMT(jsonNode.get("Data").get("GMT").asText());
					eventObj.getData().setOAT(jsonNode.get("Data").get("OAT").asText());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (jsonNode.get("Data").get("PlacementId") != null)
					eventObj.getData().setPlacementId(jsonNode.get("Data").get("PlacementId").asText());
				if (jsonNode.get("Data").get("MidKey") != null)
					eventObj.getData().setPlacementId(jsonNode.get("Data").get("MidKey").asText());
				if (jsonNode.get("Data").get("pchSurveyId") != null)
					eventObj.getData().setPchSurveyId(jsonNode.get("Data").get("pchSurveyId").asText());

				if (jsonNode.get("Data").get("RefererURL") != null)
					eventObj.getData().setRefererURL(jsonNode.get("Data").get("RefererURL").asText());

				if (jsonNode.get("Data").get("Suppression") != null)
					eventObj.getData().setSuppression(jsonNode.get("Data").get("Suppression").asText());

				if (jsonNode.get("Data").get("DNC") != null)
					eventObj.getData().setDNC(jsonNode.get("Data").get("DNC").asInt());

				if (jsonNode.get("Data").get("RTL") != null)
					eventObj.getData().setRTL(jsonNode.get("Data").get("RTL").asInt());

				if (jsonNode.get("Data").get("Page") != null)
					eventObj.getData().setPage(jsonNode.get("Data").get("Page").asText());

				if (jsonNode.get("Data").get("Consent") != null)
					eventObj.getData().setConsent(jsonNode.get("Data").get("Consent").asText());

				if (jsonNode.get("Data").get("ContestEntriesGranted") != null) {
					eventObj.getData().getContestEntriesGranted()
							.setAmount(jsonNode.get("Data").get("ContestEntriesGranted").get("amount").asInt());
					eventObj.getData().getContestEntriesGranted()
							.setKey(jsonNode.get("Data").get("ContestEntriesGranted").get("key").asText());
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
					eventObj.getData().getTokensGranted()
							.setAmount(jsonNode.get("Data").get("TokensGranted").get("amount").asInt());
					eventObj.getData().getTokensGranted()
							.setDescription(jsonNode.get("Data").get("TokensGranted").get("description").asText());
				}

				if (jsonNode.get("Data").get("SurveyId") != null)
					eventObj.getData().setSurveyId(jsonNode.get("Data").get("SurveyId").asInt());

				if (jsonNode.get("Data").get("SurveyReward") != null) {
					eventObj.getData().getSurveyReward().getTokens().getComplete().setAmount(jsonNode.get("Data")
							.get("SurveyReward").get("tokens").get("complete").get("amount").asInt());
					eventObj.getData().getSurveyReward().getTokens().getComplete().setDescription(jsonNode.get("Data")
							.get("SurveyReward").get("tokens").get("complete").get("description").asText());
					eventObj.getData().getSurveyReward().getTokens().getIncomplete().setAmount(jsonNode.get("Data")
							.get("SurveyReward").get("tokens").get("incomplete").get("amount").asInt());
					eventObj.getData().getSurveyReward().getTokens().getIncomplete().setDescription(jsonNode.get("Data")
							.get("SurveyReward").get("tokens").get("incomplete").get("description").asText());

					eventObj.getData().getSurveyReward().getEntries().getComplete().setAmount(jsonNode.get("Data")
							.get("SurveyReward").get("entries").get("complete").get("amount").asInt());
					eventObj.getData().getSurveyReward().getEntries().getComplete().setKey(jsonNode.get("Data")
							.get("SurveyReward").get("entries").get("complete").get("key").asText());
					eventObj.getData().getSurveyReward().getEntries().getIncomplete().setAmount(jsonNode.get("Data")
							.get("SurveyReward").get("entries").get("incomplete").get("amount").asInt());
					eventObj.getData().getSurveyReward().getEntries().getIncomplete().setKey(jsonNode.get("Data")
							.get("SurveyReward").get("entries").get("incomplete").get("key").asText());

				}
				if (jsonNode.get("Data").get("Consent") != null)
					eventObj.getData().setConsent(jsonNode.get("Data").get("Consent").asText());

				if (jsonNode.get("Data").get("IsMission") != null)
					eventObj.getData().setIsMission(jsonNode.get("Data").get("IsMission").asText());

				if (jsonNode.get("Data").get("IsPath") != null)
					eventObj.getData().setIsPath(jsonNode.get("Data").get("IsPath").asText());

				if (jsonNode.get("Data").get("MidOrder") != null)
					eventObj.getData().setMidOrder(jsonNode.get("Data").get("MidOrder").asText());

				if (jsonNode.get("Data").get("MissionResetType") != null)
					eventObj.getData().setMissionResetType(jsonNode.get("Data").get("MissionResetType").asText());

				actualEventList.add(eventObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, String> compareToPreviousEventValue = new HashMap<>();
		List<String> verifiedTransactionId = new ArrayList<String>();
		HashMap<String, Integer> eventsMap = new HashMap<>();
		int cnt = 0;
		boolean eventCapturedFromLog = false;
		for (Map<String, String> columns : expectedLogData) {
			for (int i = 0; i < actualEventList.size(); i++) {
				if (columns.get("Type").equalsIgnoreCase(actualEventList.get(i).getType())
						&& !verifiedTransactionId.contains(actualEventList.get(i).getTransactionId())) {
					if (eventsMap.containsKey(columns.get("Type"))) {
						eventsMap.put(columns.get("Type"), eventsMap.get(columns.get("Type")) + 1);
					} else {
						eventsMap.put(columns.get("Type"), 1);
					}
					boolean sessionTokenStatus = actualEventList.get(i).getSession().getSessionToken()
							.equalsIgnoreCase(compareToPreviousEventValue.get("Session.SessionToken"));
					if ((columns.get("Session.SessionToken").equals("new")) && sessionTokenStatus) {
						continue;
					} else if ((columns.get("Session.SessionToken").equals("previous")) && sessionTokenStatus
							&& eventsMap.get(columns.get("Type")) > 1) {
						eventsMap.remove(columns.get("Type"));
						continue;
					} else {
						cnt = i;
						eventCapturedFromLog = true;
						break;
					}
				}
			}

			Assert.assertTrue(columns.get("Type") + " event is not retrieved or not available in logs",
					eventCapturedFromLog);
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
				if (actualEventList.get(cnt).getType().equalsIgnoreCase("OFFERS_USER_STATUS"))
					verifiedTransactionId.add(actualEventList.get(cnt).getTransactionId());
			}

			if (columns.get("Client.DeviceType") != null)
				Assert.assertEquals(columns.get("Client.DeviceType"),
						actualEventList.get(cnt).getClient().getDeviceType());

			Assert.assertNotNull(actualEventList.get(cnt).getClient().getIpAddress());
			Assert.assertNotNull(actualEventList.get(cnt).getClient().getUserAgent());
			Assert.assertNotNull(actualEventList.get(cnt).getClient().getUserAgentId());

			if (columns.get("Source.OriginatingUrl") != null)
				Assert.assertTrue(actualEventList.get(cnt).getSource().getOriginatingUrl()
						.contains(columns.get("Source.OriginatingUrl")));

			if (columns.get("Source.CBL") != null)
				Assert.assertEquals(columns.get("Source.CBL"), actualEventList.get(cnt).getSource().getCBL());

			if (columns.get("Source.TrackingToken") != null) {
				if (columns.get("Source.TrackingToken").equals("empty"))
					Assert.assertEquals("", actualEventList.get(cnt).getSource().getTrackingToken());
			}

			if (columns.get("Session.SessionToken") != null) {
				if (columns.get("Session.SessionToken").equals("notnull")) {
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getSessionToken());
					compareToPreviousEventValue.put("Session.SessionToken",
							actualEventList.get(cnt).getSession().getSessionToken());
				}
				if (columns.get("Session.SessionToken").equals("previous"))
					Assert.assertEquals(compareToPreviousEventValue.get("Session.SessionToken"),
							actualEventList.get(cnt).getSession().getSessionToken());
				if (columns.get("Session.SessionToken").equals("new")) {
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getSessionToken());
					Assert.assertNotEquals(compareToPreviousEventValue.get("Session.SessionToken"),
							actualEventList.get(cnt).getSession().getSessionToken());
				}

			}

			if (columns.get("Session.MidSessionKey") != null) {
				if (columns.get("Session.MidSessionKey").equals("notnull")) {
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getMidSessionKey());
					compareToPreviousEventValue.put("Session.MidSessionKey",
							actualEventList.get(cnt).getSession().getMidSessionKey());
				}
				if (columns.get("Session.MidSessionKey").equals("previous"))
					Assert.assertEquals(compareToPreviousEventValue.get("Session.MidSessionKey"),
							actualEventList.get(cnt).getSession().getMidSessionKey());
				if (columns.get("Session.MidSessionKey").equals("new"))
					Assert.assertNotEquals(compareToPreviousEventValue.get("Session.MidSessionKey"),
							actualEventList.get(cnt).getSession().getMidSessionKey());
			}

			if (columns.get("Session.GaSessionId") != null) {
				if (columns.get("Session.GaSessionId").equals("notnull")) {
					Assert.assertNotNull(actualEventList.get(cnt).getSession().getGaSessionId());
					compareToPreviousEventValue.put("Session.GaSessionId",
							actualEventList.get(cnt).getSession().getGaSessionId());
				}
				if (columns.get("Session.GaSessionId").equals("previous"))
					Assert.assertEquals(compareToPreviousEventValue.get("Session.GaSessionId"),
							actualEventList.get(cnt).getSession().getGaSessionId());
				if (columns.get("Session.GaSessionId").equals("new"))
					Assert.assertNotEquals(compareToPreviousEventValue.get("Session.GaSessionId"),
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

			if (columns.get("Data.RefererURL") != null) {
				Assert.assertTrue(
						actualEventList.get(cnt).getData().getRefererURL().contains(columns.get("Data.RefererURL")));
			}

			if (columns.get("Data.Page") != null) {
				switch (columns.get("Data.Page")) {

				case "notnull":
					Assert.assertNotNull(actualEventList.get(cnt).getData().getPage());
					compareToPreviousEventValue.put("Data.Page", actualEventList.get(cnt).getData().getPage());
					break;
				case "new":
					Assert.assertNotNull(actualEventList.get(cnt).getData().getPage());
					Assert.assertNotEquals(compareToPreviousEventValue.get("Data.Page"),
							actualEventList.get(cnt).getData().getPage());
					break;
				case "previous":
					Assert.assertEquals(compareToPreviousEventValue.get("Data.Page"),
							actualEventList.get(cnt).getData().getPage());
					break;
				default:
					boolean status = false;
					String[] values = columns.get("Data.Page").split("/");
					for (String val : values) {
						status = actualEventList.get(cnt).getData().getPage().contains(val);
						if (status) {
							compareToPreviousEventValue.put("Data.Page", actualEventList.get(cnt).getData().getPage());
							break;
						}
					}
					Assert.assertTrue("Data.Page value should be present.", status);
				}
			}

			if (columns.get("Data.Suppression") != null) {
				switch (columns.get("Data.Suppression")) {

				case "notnull":
					Assert.assertNotNull(actualEventList.get(cnt).getData().getSuppression());
					compareToPreviousEventValue.put("Data.Suppression",
							actualEventList.get(cnt).getData().getSuppression());
					break;
				case "new":
					Assert.assertNotNull(actualEventList.get(cnt).getData().getSuppression());
					Assert.assertNotEquals(compareToPreviousEventValue.get("Data.Suppression"),
							actualEventList.get(cnt).getData().getSuppression());
					break;
				case "previous":
					Assert.assertEquals(compareToPreviousEventValue.get("Data.Suppression"),
							actualEventList.get(cnt).getData().getSuppression());
					break;
				default:
					boolean status = false;
					String[] values = columns.get("Data.Suppression").split("/");
					for (String val : values) {
						status = actualEventList.get(cnt).getData().getSuppression().contains(val);
						if (status) {
							compareToPreviousEventValue.put("Data.Suppression",
									actualEventList.get(cnt).getData().getSuppression());
							break;
						}
					}
					Assert.assertTrue("Data.Suppression value should be present.", status);
				}
			}

			if (columns.get("Data.Consent") != null) {
				if (columns.get("Data.Consent").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getData().getConsent());
				if (!columns.get("Data.Consent").isEmpty())
					Assert.assertEquals(columns.get("Data.Consent"), actualEventList.get(cnt).getData().getConsent());
			}

			if (columns.get("Data.DNC") != null)
				Assert.assertEquals(Integer.parseInt(columns.get("Data.DNC")),
						actualEventList.get(cnt).getData().getDNC());

			if (columns.get("Data.RTL") != null)
				Assert.assertEquals(Integer.parseInt(columns.get("Data.RTL")),
						actualEventList.get(cnt).getData().getRTL());

			if (columns.get("Data.ContestEntriesGranted") != null) {
				String[] values = columns.get("Data.ContestEntriesGranted").split(",");
				values[1] = replaceString(values[1]);
				Assert.assertEquals(Integer.parseInt(values[0]),
						actualEventList.get(cnt).getData().getContestEntriesGranted().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getContestEntriesGranted().getKey());
			}

			if (columns.get("Data.Tokens.Complete") != null) {
				String[] values = columns.get("Data.Tokens.Complete").split(",");
				values[1] = replaceString(values[1]);
				Assert.assertEquals(Integer.parseInt(values[0]),
						actualEventList.get(cnt).getData().getSurveyReward().getTokens().getComplete().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getSurveyReward().getTokens()
						.getComplete().getDescription());
			}

			if (columns.get("Data.Tokens.Incomplete") != null) {
				String[] values = columns.get("Data.Tokens.Incomplete").split(",");
				values[1] = replaceString(values[1]);
				Assert.assertEquals(Integer.parseInt(values[0]),
						actualEventList.get(cnt).getData().getSurveyReward().getTokens().getIncomplete().getAmount());
				Assert.assertEquals(values[1], actualEventList.get(cnt).getData().getSurveyReward().getTokens()
						.getIncomplete().getDescription());

			}

			if (columns.get("Data.Entries.Complete") != null) {
				String[] values = columns.get("Data.Entries.Complete").split(",");
				values[0] = replaceString(values[0]);
				values[1] = replaceString(values[1]);

				if (values[0] == null)
					Assert.assertNull(actualEventList.get(cnt).getData().getSurveyReward().getEntries().getComplete()
							.getAmount());
				else if (!values[0].equals(""))
					Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData()
							.getSurveyReward().getEntries().getComplete().getAmount());

				if (values[1] == null)
					Assert.assertTrue(actualEventList.get(cnt).getData().getSurveyReward().getEntries().getComplete()
							.getKey().equalsIgnoreCase("null"));
				else if (!values[1].equals(""))
					Assert.assertEquals(values[1],
							actualEventList.get(cnt).getData().getSurveyReward().getEntries().getComplete().getKey());

			}

			if (columns.get("Data.Entries.Incomplete") != null) {
				String[] values = columns.get("Data.Entries.Incomplete").split(",");
				values[0] = replaceString(values[0]);
				values[1] = replaceString(values[1]);
				if (values[0] == null)
					Assert.assertNull(actualEventList.get(cnt).getData().getSurveyReward().getEntries().getIncomplete()
							.getAmount());
				else if (!values[0].equalsIgnoreCase(""))
					Assert.assertEquals(Integer.parseInt(values[0]), actualEventList.get(cnt).getData()
							.getSurveyReward().getEntries().getIncomplete().getAmount());

				if (values[1] == null)
					Assert.assertTrue(actualEventList.get(cnt).getData().getSurveyReward().getEntries().getIncomplete()
							.getKey().equalsIgnoreCase("null"));
				else if (!values[1].equalsIgnoreCase(""))
					Assert.assertEquals(values[1],
							actualEventList.get(cnt).getData().getSurveyReward().getEntries().getIncomplete().getKey());
			}

			if (columns.get("Data.Duration") != null)
				Assert.assertEquals(columns.get("Data.Duration"), actualEventList.get(cnt).getData().getDuration());

			if (columns.get("Data.LengthInMinutes") != null) {
				if (columns.get("Data.SurveyType").equalsIgnoreCase("purespectrumfusion")) {
					int actual = Integer.parseInt(actualEventList.get(cnt).getData().getLengthInMinutes());
					int expected = Integer.parseInt(columns.get("Data.LengthInMinutes"));
					boolean status = false;
					if (actual <= expected) {
						status = true;
					}
					Assert.assertTrue("LengthInMinutes value should <= to "
							+ Integer.parseInt(columns.get("Data.LengthInMinutes")), status);
				} else {
					Assert.assertEquals(columns.get("Data.LengthInMinutes"),
							actualEventList.get(cnt).getData().getLengthInMinutes());
				}
			}

			if (columns.get("Data.MinimumGrossCPI") != null)
				Assert.assertEquals(columns.get("Data.MinimumGrossCPI"),
						actualEventList.get(cnt).getData().getMinimumGrossCPI());

			if (columns.get("Data.Payout") != null)
				if (columns.get("Data.Payout").equals("notnull"))
					Assert.assertNotNull(actualEventList.get(cnt).getData().getPayout());
				else
					Assert.assertEquals(columns.get("Data.Payout"), actualEventList.get(cnt).getData().getPayout());

			if (columns.get("Data.PlacementId") != null)
				Assert.assertEquals(columns.get("Data.PlacementId"),
						actualEventList.get(cnt).getData().getPlacementId());

			if (columns.get("Data.Status") != null)
				if (columns.get("Data.Status").equals("empty"))
					Assert.assertEquals("", actualEventList.get(cnt).getData().getStatus());
				else
					Assert.assertEquals(columns.get("Data.Status"), actualEventList.get(cnt).getData().getStatus());

			if (columns.get("Data.Consent") != null)
				Assert.assertEquals(columns.get("Data.Consent"), actualEventList.get(cnt).getData().getConsent());

			if (columns.get("Data.IsMission") != null)
				Assert.assertEquals(columns.get("Data.IsMission"), actualEventList.get(cnt).getData().getIsMission());

			if (columns.get("Data.IsPath") != null)
				Assert.assertEquals(columns.get("Data.IsPath"), actualEventList.get(cnt).getData().getIsPath());

			if (columns.get("Data.MidOrder") != null)
				Assert.assertEquals(columns.get("Data.MidOrder"), actualEventList.get(cnt).getData().getMidOrder());

			if (columns.get("Data.MissionResetType") != null)
				Assert.assertEquals(columns.get("Data.MissionResetType"),
						actualEventList.get(cnt).getData().getMissionResetType());

			cnt++;
		}

	}

	private String replaceString(String value) {
		if (value == null)
			return value;
		if (value.equalsIgnoreCase("empty"))
			return "";
		if (value.equalsIgnoreCase("null"))
			return null;
		return value;
	}

}