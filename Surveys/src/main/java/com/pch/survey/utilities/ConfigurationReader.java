package com.pch.survey.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TreeMap;

public class ConfigurationReader {

	private static ConfigurationReader instance;
	private static Properties properties;
	private static TreeMap<String, String> configReader = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

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
	private static String gridHubUrl = null;

	// database
	private static String offersHost = null;
	private static String offersPort = null;
	private static String offersPW = null;

	// logfiles
	private static String surveyLogsUrl = null;
	private static String offerLogsUrl = null;

	// Offers admin
	private static String offersAdminUrl = null;
	private static String offersAdminUserName = null;
	private static String offersAdminPassword = null;
	private static String offersSurveyendToolUrl = null;

	private static String offerSurveyEndApi = null;
	private static String offerSurveyEndApiGRL = null;
	private static String profileQuestionAnswerApi = null;

	private static String tokenBankUrl = null;
	private static String tokenBankKeyUrl = null;
	private static String eventTrackerUrl = null;

	// Crons
	private static String processAmzGcQueue = null;
	private static String processMissionCompletes = null;

	// Disclaimer
	private static String disclaimerTitle = null;
	private static String disclaimerDesc = null;

	// QOTD
	private static String qotdModuleDesc = null;
	private static String qotdInitialStateDesc = null;
	private static String qotdIncompleteStateDesc = null;
	private static String qotdModuleToken = null;
	private static String qotdModuleOfficialRules = null;
	private static String qotdModuleSweepstakeFacts = null;
	private static String qotdModulePrivacyPolicy = null;
	private static String tokenDescriptionForQOTD = null;
	private static String tokenDescriptionForQAPI = null;
	private static String qapiExitPopupDesc = null;

	// Badges URL
	private static String influencerLocked = null;
	private static String influencerActive = null;
	private static String influencerProLocked = null;
	private static String influencerProActive = null;
	private static String influencerProPlusLocked = null;
	private static String influencerProPlusActive = null;
	private static String tokenTitanLocked = null;
	private static String tokenTitanActive = null;
	private static String tokenTitanGold = null;

	// Badges Info Text
	private static String influencerInfoText = null;
	private static String influencerProInfoText = null;
	private static String influencerProPlusInfoText = null;

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

		offerSurveyEndApi = properties.getProperty("offerSurveyEndApi");
		offerSurveyEndApiGRL = properties.getProperty("offerSurveyEndApiGRL");

		tokenBankKeyUrl = properties.getProperty("tokenBankKeyUrl");
		tokenBankUrl = properties.getProperty("tokenBankUrl");

		profileQuestionAnswerApi = properties.getProperty("profileQuestionAnswerApi");
		eventTrackerUrl = properties.getProperty("eventTrackerUrl");

		// offers admin
		offersAdminUrl = properties.getProperty("offersAdminUrl");
		offersSurveyendToolUrl = properties.getProperty("offersSurveyendToolUrl");
		offersAdminUserName = properties.getProperty("offersAdminUserName");
		offersAdminPassword = properties.getProperty("offersAdminPassword");

		// Crons
		processAmzGcQueue = properties.getProperty("process-amz-gc-queue");
		processMissionCompletes = properties.getProperty("process-mission-completes");

		// disclaimer
		disclaimerTitle = properties.getProperty("disclaimerTitle");
		disclaimerDesc = properties.getProperty("disclaimerDesc");

		// QOTD
		qotdModuleDesc = properties.getProperty("qotdModuleDesc");
		qotdInitialStateDesc = properties.getProperty("qotdInitialStateDesc");
		qotdIncompleteStateDesc = properties.getProperty("qotdIncompleteStateDesc");
		qotdModuleToken = properties.getProperty("qotdModuleToken");
		qotdModuleOfficialRules = properties.getProperty("qotdModuleOfficialRules");
		qotdModuleSweepstakeFacts = properties.getProperty("qotdModuleSweepstakeFacts");
		qotdModulePrivacyPolicy = properties.getProperty("qotdModulePrivacyPolicy");
		tokenDescriptionForQOTD = properties.getProperty("tokenDescriptionForQOTD");
		tokenDescriptionForQAPI = properties.getProperty("tokenDescriptionForQAPI");
		qapiExitPopupDesc = properties.getProperty("qapiExitPopupDesc");
		configReader.put("qotdTokenAwardDescription", properties.getProperty("qotdTokenAwardDescription"));
		configReader.put("qapiTokenAwardDescription", properties.getProperty("qapiTokenAwardDescription"));
		configReader.put("qotdModuleToken", properties.getProperty("qotdModuleToken"));
		configReader.put("qotdCreatePwdLbDesc", properties.getProperty("qotdCreatePwdLbDesc"));
		configReader.put("qotdCreatePwdLbExtraTokenDesc", properties.getProperty("qotdCreatePwdLbExtraTokenDesc"));
		configReader.put("qotdCreatePwdLbSuccessMsg", properties.getProperty("qotdCreatePwdLbSuccessMsg"));
		configReader.put("createPasswordQotdTokenDesc", properties.getProperty("createPasswordQotdTokenDesc"));

		// Badges URL
		influencerLocked = properties.getProperty("influencerLocked");
		influencerActive = properties.getProperty("influencerActive");
		influencerProLocked = properties.getProperty("influencerProLocked");
		influencerProActive = properties.getProperty("influencerProActive");
		influencerProPlusLocked = properties.getProperty("influencerProPlusLocked");
		influencerProPlusActive = properties.getProperty("influencerProPlusActive");
		tokenTitanLocked = properties.getProperty("tokenTitanLocked");
		tokenTitanActive = properties.getProperty("tokenTitanActive");
		tokenTitanGold = properties.getProperty("tokenTitanGold");

		// Badges Info Text
		influencerInfoText = properties.getProperty("influencerInfoText");
		influencerProInfoText = properties.getProperty("influencerProInfoText");
		influencerProPlusInfoText = properties.getProperty("influencerProPlusInfoText");

		// Tokens
		configReader.put("welcomeTokenDefaultUserLevel", properties.getProperty("welcomeTokenDefaultUserLevel"));
		configReader.put("welcomeTokenDefaultUserLevelDesc",
				properties.getProperty("welcomeTokenDefaultUserLevelDesc"));
		configReader.put("createPasswordTokens", properties.getProperty("createPasswordTokens"));
		configReader.put("createPasswordDescription", properties.getProperty("createPasswordDescription"));
		configReader.put("handraiserToken", properties.getProperty("handraiserToken"));
		configReader.put("handraiserTokenDesc", properties.getProperty("handraiserTokenDesc"));
		configReader.put("titanTokenBadgeTokenAmt", properties.getProperty("titanTokenBadgeTokenAmt"));
		configReader.put("titanTokenGoldBadgeTokenAmt", properties.getProperty("titanTokenGoldBadgeTokenAmt"));
		configReader.put("influencerProPlusLevelUpgradeTokenDesc",
				properties.getProperty("influencerProPlusLevelUpgradeTokenDesc"));
		configReader.put("influencerProPlusLevelUpgradeTokenAmt",
				properties.getProperty("influencerProPlusLevelUpgradeTokenAmt"));
		configReader.put("influencerProLevelUpgradeTokenDesc",
				properties.getProperty("influencerProLevelUpgradeTokenDesc"));
		configReader.put("influencerProLevelUpgradeTokenAmt",
				properties.getProperty("influencerProLevelUpgradeTokenAmt"));
		configReader.put("influencerLevelUpgradeTokenDesc", properties.getProperty("influencerLevelUpgradeTokenDesc"));
		configReader.put("influencerLevelUpgradeTokenAmt", properties.getProperty("influencerLevelUpgradeTokenAmt"));

		// Automation mission module
		configReader.put("automationMissionModuleCompleteContestkey",
				properties.getProperty("automationMissionModuleCompleteContestkey"));
		configReader.put("automationMissionStepCompleteTokenAmt",
				properties.getProperty("automationMissionStepCompleteTokenAmt"));
		configReader.put("automationMissionModuleCompleteTokenAmt",
				properties.getProperty("automationMissionModuleCompleteTokenAmt"));
		configReader.put("automationMissionIncompleteDescriptionText",
				properties.getProperty("automationMissionIncompleteDescriptionText"));
		configReader.put("automationMissionCompleteDescriptionText",
				properties.getProperty("automationMissionCompleteDescriptionText"));

		// LucidSurvey
		configReader.put("lucidMid", properties.getProperty("lucidMid"));
		configReader.put("lucidSurveyDesktopCompleteToken", properties.getProperty("lucidSurveyDesktopCompleteToken"));
		configReader.put("lucidSurveyCompleteDescription", properties.getProperty("lucidSurveyCompleteDescription"));
		configReader.put("lucidSurveyDesktopCompleteContestkey",
				properties.getProperty("lucidSurveyDesktopCompleteContestkey"));
		configReader.put("lucidSurveyDesktopIncompleteToken",
				properties.getProperty("lucidSurveyDesktopIncompleteToken"));
		configReader.put("lucidSurveyIncompleteDescription",
				properties.getProperty("lucidSurveyIncompleteDescription"));
		configReader.put("lucidSurveyDesktopIncompleteContestkey",
				properties.getProperty("lucidSurveyDesktopIncompleteContestkey"));
		configReader.put("lucidSurveyMobileCompleteToken", properties.getProperty("lucidSurveyMobileCompleteToken"));
		configReader.put("lucidSurveyMobileCompleteContestkey",
				properties.getProperty("lucidSurveyMobileCompleteContestkey"));
		configReader.put("lucidSurveyMobileIncompleteToken",
				properties.getProperty("lucidSurveyMobileIncompleteToken"));
		configReader.put("lucidSurveyMobileIncompleteToken",
				properties.getProperty("lucidSurveyMobileIncompleteToken"));
	}

	public static String getOfferSurveyEndApiGRL() {
		return offerSurveyEndApiGRL;
	}

	public static void setOfferSurveyEndApiGRL(String offerSurveyEndApiGRL) {
		ConfigurationReader.offerSurveyEndApiGRL = offerSurveyEndApiGRL;
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

	public static String getTokenBankUrl() {
		return tokenBankUrl;
	}

	public static String getTokenBankKeyUrl() {
		return tokenBankKeyUrl;
	}

	// Retriving offers admin url and credentials
	public static String getOffersSurveyendToolUrl() {
		return offersSurveyendToolUrl;
	}

	public static String getOffersAdminUrl() {
		return offersAdminUrl;
	}

	public static String getOffersAdminUserName() {
		return offersAdminUserName;
	}

	public static String getOffersAdminPassword() {
		return offersAdminPassword;
	}

	// Retriving cron URL's
	public static String getProcessAmzGcQueue() {
		return processAmzGcQueue;
	}

	public static String getProcessMissionCompletes() {
		return processMissionCompletes;
	}

	// Static content validation for survey tab
	public static String getDisclaimerTitle() {
		return disclaimerTitle;
	}

	public static String getDisclaimerDesc() {
		return disclaimerDesc;
	}

	// QOTD
	public static String getQotdModuleDesc() {
		return qotdModuleDesc;
	}

	public static String getQotdInitialStateDesc() {
		return qotdInitialStateDesc;
	}

	public static String getQotdIncompleteStateDesc() {
		return qotdIncompleteStateDesc;
	}

	public static String getQotdModuleToken() {
		return qotdModuleToken;
	}

	public static String getQotdModuleOfficialRules() {
		return qotdModuleOfficialRules;
	}

	public static String getQotdModuleSweepstakeFacts() {
		return qotdModuleSweepstakeFacts;
	}

	public static String getQotdModulePrivacyPolicy() {
		return qotdModulePrivacyPolicy;
	}

	public static String getTokenDescriptionForQOTD() {
		return tokenDescriptionForQOTD;
	}

	public static String getTokenDescriptionForQAPI() {
		return tokenDescriptionForQAPI;
	}

	public static String getQapiExitPopupDesc() {
		return qapiExitPopupDesc;
	}

//	Badges URL
	public static String getInfluencerLocked() {
		return influencerLocked;
	}

	public static String getInfluencerActive() {
		return influencerActive;
	}

	public static String getInfluencerProLocked() {
		return influencerProLocked;
	}

	public static String getInfluencerProActive() {
		return influencerProActive;
	}

	public static String getInfluencerProPlusLocked() {
		return influencerProPlusLocked;
	}

	public static String getInfluencerProPlusActive() {
		return influencerProPlusActive;
	}

	public static String getTokenTitanLocked() {
		return tokenTitanLocked;
	}

	public static String getTokenTitanActive() {
		return tokenTitanActive;
	}

	public static String getTokenTitanGold() {
		return tokenTitanGold;
	}

	// Badges Info Text
	public static String getInfluencerInfoText() {
		return influencerInfoText;
	}

	public static String getInfluencerProInfoText() {
		return influencerProInfoText;
	}

	public static String getInfluencerProPlusInfoText() {
		return influencerProPlusInfoText;
	}

	public static TreeMap<String, String> getAppConfigProperty() {
		return configReader;
	}

}