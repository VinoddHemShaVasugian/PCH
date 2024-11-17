package com.pch.sw.tests.misc;

import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class CaptureAdvertiseSourceForSearchLogTests extends BaseTest {

	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage serp;
	User randomUser_1, randomUser_2, randomUser_3;

	@Test(groups = { GroupNames.Desktop,
			GroupNames.Regression }, testName = "19973 - [1]CaptureAdvertiseSourceForSerpLogging_Desktop_Tablet", description = "Validate advertising source in console log from Infospace.")
	public void testAdvertisingSource_DesktopGuestUser() {

		homePage.load();

		headerPage.search("shoes", false);
		serp.validateAdvertisingSource("&ga=");
		serp.validateAdvertisingSource("&ya=");
		serp.validateAdvertisingSource("&oa=");
		CustomLogger.log("Advertising Source on SERP console log is as expected.");
	}

	@Test(groups = { GroupNames.Desktop,
			GroupNames.Regression }, testName = "19973 - [1]CaptureAdvertiseSourceForSerpLogging_Desktop_Tablet", description = "Validate advertising source in console log from Infospace.")
	public void testAdvertisingSource_DesktopRegisteredUser() {

		String user = "kcheguri21@pchmail.com";

		homePage.load();
		headerPage.loginToSearch(user, "testing");

		headerPage.search("shoes", false);
		serp.validateAdvertisingSource("&ga=");
		serp.validateAdvertisingSource("&ya=");
		serp.validateAdvertisingSource("&oa=");

		CustomLogger.log("Advertising Source on SERP console log is as expected.");

	}

}
