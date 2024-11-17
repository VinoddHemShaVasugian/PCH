package com.pch.survey.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

	private static ConfigurationReader instance;
	private static Properties properties;

	private static String environment = null;
	private static String browser = null;
	private static String deviceType = null;
	private static String driverType = null;
	private static String url = null;
	private static String offersUrl = null;
	private static String surveyUrl = null;
	private static String badgesUrl = null;
	private static String programTermsUrl = null;
	private static String bonusGameUrl = null;

	// browserstack specific
	private static String browserStackUserName = null;
	private static String browserStackKey = null;
	private static String browserStackUrl = null;
	private static String os = null;
	private static String osVersion = null;
	private static String deviceName = null;

	// grid
	private static String gridHubUrl=null;
	
	
	// database
	private static String offersHost = null;
	private static String offersPort = null;
	private static String offersPW = null;

	// logfiles
	private static String surveyLogsUrl = null;
	private static String offerLogsUrl = null;

	
	private static String offerSurveyEndApi = null;
	private static String profileQuestionAnswerApi = null;

	
 


	private static String eventTrackerUrl = null;
	
	
	public static ConfigurationReader getInstance() {
		if (instance == null) {
			instance = new ConfigurationReader();
		}
		return instance;
	}

	private ConfigurationReader() {

		try {

			if (System.getProperty("environment") != null) {
				environment = System.getProperty("environment");
				System.out.println("********'ENVIRONMENT Details taken from Runtime'********");
				System.out.println("********'" + System.getProperty("environment") + " ENVIRONMENT'********");
			} else {
				Properties appConfigPropertySet = new Properties();
				InputStream appConfigPropertyStream = ConfigurationReader.class
						.getResourceAsStream("/configuration/baseAppConfig.properties");
				appConfigPropertySet.load(appConfigPropertyStream);
				environment = appConfigPropertySet.getProperty("CurrentEnvironment");
				System.out.println("********'ENVIRONMENT Details taken from Baseapp config property file'********");
				System.out.println("********'" + environment + " ENVIRONMENT'********");
			}
		} catch (IOException e) {
			// Error reading from the file - ignore
		} catch (IllegalArgumentException e) {
			// The input stream contained malformed characters - ignore
		}
		properties = new Properties();
		InputStream in = ConfigurationReader.class
				.getResourceAsStream("/configuration/" + environment + "/appConfig.properties");
		System.out.println("********'Tests running in " + environment + " ENVIRONMENT '********");

		if (in != null) {
			try {
				properties.load(in);
			} catch (IOException e) {
				// Error reading from the file - ignore
			} catch (IllegalArgumentException e) {
				// The input stream contained malformed characters - ignore
			}
		}

		browser = System.getProperty("browser");
		deviceType = System.getProperty("device_type");
		driverType = System.getProperty("driver_type");
		os = System.getProperty("os");
		osVersion = System.getProperty("osversion");
		deviceName = System.getProperty("device_name");

		gridHubUrl = System.getProperty("grid_hub_url");

		
		
		System.out.println("from system BROWSER   " + browser);
		System.out.println("from system DEVICE_TYPE    " + deviceType);
		System.out.println("from system DRIVER_TYPE    " + driverType);
		System.out.println("from system OS   " + os);
		System.out.println("from system OS_VERSION   " + osVersion);
		System.out.println("from system DEVICE_NAME   " + deviceName);

		if (browser == null)
			browser = properties.getProperty("browser");
		if (deviceType == null)
			deviceType = properties.getProperty("deviceType");
		if (driverType == null)
			driverType = properties.getProperty("driverType");
		if (os == null)
			os = properties.getProperty("os");
		if (osVersion == null)
			osVersion = properties.getProperty("osVersion");
		if (deviceName == null)
			deviceName = properties.getProperty("deviceName");
		if (gridHubUrl == null)
			gridHubUrl = properties.getProperty("gridHubUrl");

		browserStackUserName = System.getProperty("BROWSERSTACK_USER");
		browserStackKey = System.getProperty("BROWSERSTACK_KEY");
		if (browserStackUserName == null)
			browserStackUserName = properties.getProperty("browserstack.user");
		if (browserStackKey == null)
			browserStackKey = properties.getProperty("browserstack.key");
		browserStackUrl = "https://" + browserStackUserName + ":" + browserStackKey
				+ "@hub-cloud.browserstack.com/wd/hub";

		offersUrl = properties.getProperty("offersUrl");

		url = properties.getProperty("url");
		badgesUrl = properties.getProperty("badgesURL");
		surveyUrl = properties.getProperty("surveyUrl");
		programTermsUrl = properties.getProperty("programTermsUrl");
		bonusGameUrl = properties.getProperty("bonusGameUrl");

		// Redis DB
		offersHost = properties.getProperty("offersHost");
		offersPort = properties.getProperty("offersPort");
		offersPW = properties.getProperty("offersPW");
		offersPW = AESUtil.decrypt(offersPW);

		// logs
		surveyLogsUrl = properties.getProperty("surveysLogsUrl");
		offerLogsUrl = properties.getProperty("offersLogsUrl");

		offerSurveyEndApi= properties.getProperty("offerSurveyEndApi");
	
		profileQuestionAnswerApi = properties.getProperty("profileQuestionAnswerApi");
		eventTrackerUrl  = properties.getProperty("eventTrackerUrl");
		
		
 
	}

	public static Properties getProperties() {
		return properties;
	}

	public static String getUrl() {
		return url;
	}

	public static String getSurveyLogsUrl() {
		return surveyLogsUrl;
	}

	public static String getOfferLogsUrl() {
		return offerLogsUrl;
	}

	public static String getBrowser() {
		return browser;
	}

	public static String getDriverType() {
		return driverType;
	}

	public static String getDeviceType() {
		return deviceType;
	}

	public static String getApplicationUrl() {
		return url;
	}

	public static String getSurveyUrl() {
		return surveyUrl;
	}

	public static String getBadgesUrl() {
		return badgesUrl;
	}
	
	public static String getProgramTermsUrl() {
		return programTermsUrl;
	}
	
	public static String getBonusGameUrl() {
		return bonusGameUrl;
	}
	
	public static String getOffersUrl() {
		return offersUrl;
	}

	public static String getEnvironment() {
		return environment;

	}

	public static String getBrowserStackUserName() {
		return browserStackUserName;
	}

	public static String getBrowserStackKey() {
		return browserStackKey;
	}

	public static String getBrowserStackUrl() {
		return browserStackUrl;
	}

	public static String getOs() {
		return os;
	}

	public static String getOsVersion() {
		return osVersion;
	}

	public static String getDeviceName() {
		return deviceName;
	}

	public static String getOffersHost() {
		return offersHost;
	}

	public static String getOffersPort() {
		return offersPort;
	}

	public static String getOffersPW() {
		return offersPW;
	}


	
	public static String getEventTrackerUrl() {
		return eventTrackerUrl;
	}
	

	public static String getOfferSurveyEndApi() {
		return offerSurveyEndApi;
	}

	
	public static String getGriHubUrl() {
		return gridHubUrl;
	}
	public static String getProfileQuestionAnswerApi() {
		return profileQuestionAnswerApi;
	}

	
	
	
}