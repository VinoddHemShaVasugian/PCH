package com.pch.survey.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.pch.survey.pages.PageObject;

public class LogReader {

	private static WebServiceClient client = new WebServiceClient();
	private static PageObject pageObject = new PageObject();

	private static String today = DateUtilities.getCurrentDateAsString();
	private static String offerLogsUrl = ConfigurationReader.getInstance().getOfferLogsUrl();
	private static String surveyLogsUrl = ConfigurationReader.getInstance().getSurveyLogsUrl();

	private static String vendorLog = "";

	private ArrayList<String> getLogFile(String vendor, String gmt) {
		String logUrl = "";
		pageObject.waitSeconds(30);
		switch (vendor.toUpperCase()) {
		case "PURESPECTRUM-EVENTS":
			logUrl = offerLogsUrl + "PureSpectrumFusionEventsLogger-" + today + ".log";
			break;
		case "PURESPECTRUMAPI-EVENTS":
			logUrl = offerLogsUrl + "PureSpectrumApiEventsLogger-" + today + ".log";
			break;
		case "LUCID-EVENTS":
			logUrl = offerLogsUrl + "LucidEventsLogger-" + today + ".log";
			break;
		case "LUCIDAPI-EVENTS":
			logUrl = offerLogsUrl + "LucidApiEventsLogger-" + today + ".log";
			break;
		case "QMEE-EVENTS":
			logUrl = offerLogsUrl + "QmeeEventsLogger-" + today + ".log";
			break;
		case "JEBBIT-EVENTS":
			logUrl = offerLogsUrl + "JebbitEventsLogger-" + today + ".log";
			break;

		case "GRL-EVENTS":
			logUrl = offerLogsUrl + "GrlEventsLogger-" + today + ".log";
			break;
		case "CONTEST_ENTRY":
			logUrl = offerLogsUrl + "contest-entry-api-client-" + today + ".log";
			break;
		case "GRL_PREQUAL":
			logUrl = offerLogsUrl + "SurveyLogger-" + today + ".log";
			break;

		case "LUCID_PREQUAL":
			logUrl = offerLogsUrl + "LucidPrequalsLogger-" + today + ".log";
			break;
		case "GA3":
			logUrl = offerLogsUrl + "GoogleAnalyticsLogger-" + today + ".log";
			break;
		case "GA4":
			logUrl = offerLogsUrl + "ga4-" + today + ".log";
			break;
		case "LISTENERS-EVENTS":
			logUrl = offerLogsUrl + "ListenersLogger-" + today + ".log";
//			logUrl = offerLogsUrl + "ListenersLogger-2023-08-25.log";
			break;
		default:
		}
		vendorLog = vendor.toUpperCase();
		return client.getLogFileViaHttp(logUrl, gmt);
	}

	private List<String> getAllSurveyStartsByGmt(ArrayList<String> eventList) {
		List<String> startsArray = new ArrayList<String>();
		if (eventList == null)
			return null;
		for (String event : eventList) {
			int index = event.indexOf("SurveyStart");
			if (index != -1) {
				String jsonStr = null;
				jsonStr = event.substring(index + 14);
				startsArray.add(jsonStr);
			}

			index = event.indexOf("SurveyMissed_opportunity"); // for lucidapi starts
			if (index != -1) {
				String jsonStr = null;
				jsonStr = event.substring(index + 27);
				startsArray.add(jsonStr);
			}

		}
		return startsArray;
	}

	private List<String> getAllSurveyEndsByGmt(ArrayList<String> eventList) {
		List<String> startsArray = new ArrayList<String>();
		if (eventList == null)
			return null;
		for (String event : eventList) {
			int index = event.indexOf("SurveyEnd");
			if (index != -1) {
				String jsonStr = null;
				jsonStr = event.substring(index + 12);
				startsArray.add(jsonStr);
			}
		}
		return startsArray;
	}

	private List<String> getAllSurveyPrequalsByGmt(ArrayList<String> eventList) {
		List<String> startsArray = new ArrayList<String>();
		if (eventList == null)
			return null;
		for (String event : eventList) {
			int index = event.indexOf("PREQUALS - {");
			if (index != -1) {
				String jsonStr = null;
				jsonStr = event.substring(index + 11);
				startsArray.add(jsonStr);
			}
		}
		return startsArray;
	}

	public List<String> getAllLucidPrequalsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("LUCID_PREQUAL", gmt);
		return getAllSurveyPrequalsByGmt(eventList);
	}

	public List<String> getAllPureSpectrumStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("PURESPECTRUM-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllPureSpectrumApiStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("PURESPECTRUMAPI-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllPureSpectrumEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("PURESPECTRUM-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllPureSpectrumApiEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("PURESPECTRUMAPI-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllLucidSurveyStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("LUCID-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllLucidSurveyEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("LUCID-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllLucidApiSurveyStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("LUCIDAPI-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllLucidApiSurveyEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("LUCIDAPI-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllQmeeSurveyStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("QMEE-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllQmeeSurveyEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("QMEE-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllJebbitSurveyStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("JEBBIT-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllJebbitSurveyEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("JEBBIT-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllGRLSurveyStartsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("GRL-EVENTS", gmt);
		return getAllSurveyStartsByGmt(eventList);
	}

	public List<String> getAllGRLSurveyEndsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("GRL-EVENTS", gmt);
		return getAllSurveyEndsByGmt(eventList);
	}

	public List<String> getAllContestEntriesByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("CONTEST_ENTRY", gmt);
		if (eventList == null)
			return null;
		List<String> entryList = new ArrayList<String>();
		for (String event : eventList) {
			int index = event.indexOf("Data=");
			if (index != -1) {
				String jsonStr = event.substring(index + 11);
				entryList.add(jsonStr);
			}
		}
		return entryList;
	}

	public List<String> getAllGA3TagsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("GA3", gmt);
		if (eventList == null)
			return null;
		List<String> tagList = new ArrayList<String>();
		for (String event : eventList) {
			int index = event.indexOf("[trackEvent] Queueing GA job - form_params:");
			if (index != -1) {
				String jsonStr = event.substring(index + 43);
				tagList.add(jsonStr);
			}
		}
		return tagList;
	}

	public List<String> getAllGA4TagsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("GA4", gmt);
		if (eventList == null)
			return null;
		List<String> tagList = new ArrayList<String>();
		for (String event : eventList) {
			int index = event.indexOf("[trackEvent] Queueing GA4 job - event_params:");
			if (index != -1) {
				String jsonStr = event.substring(index + 45);
				tagList.add(jsonStr);
			}
		}
		return tagList;
	}

	public List<String> getAllListenersEventsByGmt(String gmt) {
		ArrayList<String> eventList = getLogFile("LISTENERS-EVENTS", gmt);
		return eventList;

	}

	// Vinoth - Changed logic to sort the scrubedList
	public static List<String> scrubEventList(List<String> actualEventList, List<String> eventsToInclude) {
		List<String> scrubedList = new ArrayList<String>();

		String lastEventDate = actualEventList.get(actualEventList.size() - 1);
		lastEventDate = lastEventDate.substring(1, lastEventDate.indexOf("]"));

		for (String event : actualEventList) {
			String eventDate = event.substring(1, event.indexOf("]"));
			if (DateUtilities.getTimeStampDifferenceInSeconds(eventDate, lastEventDate) > 180)
				continue;
			// ignore these event types
			boolean includeEvent = false;
			for (String include : eventsToInclude) {
				if (event.contains(include)) {
					scrubedList.add(event);
					continue;
				}
			}
		}
		return scrubedList;
	}

	public static void main(String[] args) {

		BigDecimal bg1 = new BigDecimal(1.2);
		BigDecimal bg2 = new BigDecimal(1.2);

		System.out.println(bg1.compareTo(bg2));

//		// TODO Auto-generated method stub
//		LogReader lr = new LogReader();
//		List<String> startsList = lr.getAllListenersEventsByGmt("0be08be5-f34a-470b-a20e-b763cf53f392");
//		int x = 1;
//		for (String start : startsList) {
//			System.out.println("String number  " + x + start);
//		}

	}

}
