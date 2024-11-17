package com.pch.sw.tests.searchHrRules;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SearchHighRiskRulesTests extends BaseTest {
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage serp;
	User randomUser_1, randomUser_2, randomUser_3;
	String rapidlySearchingAlertText = "You may be searching and clicking too rapidly. To prevent disruption with your account, please use PCHSearch&Win like any other search engine.";
	String maxTokensEarnedAlertText = "You have reached your maximum DAILY chances to WIN instant prizes or score tokens by searching. Remember, failure to conduct legitimate searches may result in your account privileges being revoked.";
	String additionalHrAlertsText = "*ALERT* Are you sure you want to continue? Please keep in mind that failure to conduct legitimate searches may result in your account privileges being revoked.";

	@Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description = "Rapidly Searching LB - Search for the same term and see a ligntbox on 7th search. -- Registered User")
	public void testRepidlySearchingLB_RegisteredUser() throws SQLException {

		loginToSearch(randomUser_1);
		// Search for the same term seven time to see the LB.
		for (int i = 0; i <= 6; i++) {
			System.out.print("Search count is:: " + i);
			headerPage.multipleSearch("PCH");
		}

		// First warning alert should be displayed
		Assert.assertTrue(serp.isAlertPresent());
		CustomLogger.log("First Rapidly Searching warning alert is displayed.");

		Assert.assertTrue(serp.getAlertText().contains(rapidlySearchingAlertText));

		// Dismiss alert to land on home page
		serp.dismissAlert();
		homePage.closeUserLevelLightBox();

		// Validate if navigated back to home page after closing the alert.
		Assert.assertTrue(!serp.getCurrentURL().contains(".com/search"));

		// Search for the same term seven more times to see the LB
		for (int i = 0; i <= 6; i++) {
			//headerPage.search("shoes", false);
			headerPage.multipleSearch("shoes");
		}

		// Second warning alert should be displayed
		Assert.assertTrue(serp.isAlertPresent());
		CustomLogger.log("Second Rapidly Searching warning alert is displayed.");

		Assert.assertTrue(serp.getAlertText().contains(rapidlySearchingAlertText));

		// Dismiss alert to land on home page
		serp.dismissAlert();

		// Validate if navigated back to home page after closing the alert.
		Assert.assertTrue(!serp.getCurrentURL().contains(".com/search"));

		for (int i = 0; i <= 6; i++) {
			// Search for the same term seven more times
			//headerPage.search("shoe", false);
			headerPage.multipleSearch("shoe");
		}

		// Warning alert should not be displayed after showing it for two times
		Assert.assertTrue(serp.isAlertNotPresent());
		CustomLogger.log("Rapidly Searching warnign alert is not displayed after displaying two times.");
	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description = "Rapidly Searching LB - Search for the same term and see a ligntbox on 7th search. -- Guest User")
	public void testRepidlySearchingLB_GuestUser() throws SQLException {

		homePage.load();
		// Search for the same term seven time to see the LB.
		for (int i = 0; i <= 6; i++) {
			//headerPage.search("PCH", false);
			headerPage.multipleSearch("PCH");
		}

		// First warning alert should be displayed
		Assert.assertTrue(serp.isAlertPresent());
		CustomLogger.log("First Rapidly Searching warning alert is displayed.");

		Assert.assertTrue(serp.getAlertText().contains(rapidlySearchingAlertText));

		// Dismiss alert to land on home page
		serp.dismissAlert();

		// Validate if navigated back to home page after closing the alert.
		Assert.assertTrue(!serp.getCurrentURL().contains(".com/search"));

		// Search for the same term seven more times to see the LB
		for (int i = 0; i <= 6; i++) {
			//headerPage.search("shoes", false);
			headerPage.multipleSearch("shoes");
		}

		// Second warning alert should be displayed
		Assert.assertTrue(serp.isAlertPresent());
		CustomLogger.log("Second Rapidly Searching warning alert is displayed.");
		Assert.assertTrue(serp.getAlertText().contains(rapidlySearchingAlertText));

		// Dismiss alert to land on home page
		serp.dismissAlert();

		// Validate if navigated back to home page after closing the alert.
		Assert.assertTrue(!serp.getCurrentURL().contains(".com/search"));

		for (int i = 0; i <= 6; i++) {
			// Search for the same term seven more times
			//headerPage.search("shoe", false);
			headerPage.multipleSearch("shoe");
		}

		// Warning alert should not be displayed after showing it for two times
		Assert.assertTrue(serp.isAlertNotPresent());
		CustomLogger.log("Rapidly Searching warnign alert is not displayed after displaying two times.");
	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description = "Force User to Login on 25th Search.")
	public void testForceUserToLoginOn25thSearch() throws SQLException {
        homePage.load();
		String user = headerPage.getCookieValues("pci");
		CustomLogger.log("User cookie values is : " + user);

		//headerPage.search("PCH", false);
		headerPage.multipleSearch("PCH");

		// Validate if the user is forced to login after 25 searches
		DBUtils.updateSearchCount_GuestUser(user, 25);
		headerPage.search(Common.getRandomString(5), false);

		Common.sleepFor(2000);
		Assert.assertTrue(homePage.getCurrentURL()
				.contentEquals("https://accounts." + Environment.getEnvironment() + ".pch.com/login"));
		CustomLogger.log("SERP is automatically redirected to login page on 25th search.");
		CustomLogger
				.log("Current page url is : " + "https://accounts." + Environment.getEnvironment() + ".pch.com/login");
		// Update the DailySearchCount on DB and verify it
		DBUtils.updateSearchCount_GuestUser(user, 2);
		homePage.load();
		headerPage.search(Common.getRandomString(5), false);
		Common.sleepFor(2000);
		System.out.println(homePage.getCurrentURL());
		System.out.println("https://search." + Environment.getEnvironment() + ".pch.com");
		Assert.assertTrue(
				homePage.getCurrentURL().contains("http://search." + Environment.getEnvironment() + ".pch.com"));

	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description = "Maximum tokens received alert is shown after 25 HR search count. -- Registered User")
	public void testMaxTokensEarnedForSearchingLB_RegisteredUser() throws SQLException {

		loginToSearch(randomUser_2);
		//headerPage.search("PCH", false);
		headerPage.multipleSearch("PCH");

		DBUtils.updateSearchCount(randomUser_2.getEmail(), 25);
		headerPage.multipleSearch("PCH");
		// Validate if Maximum tokens received alert is displayed
		Assert.assertTrue(serp.isAlertPresent());
		CustomLogger.log("Maximum tokens received alert is displayed.");

		Assert.assertTrue(serp.getAlertText().contains(maxTokensEarnedAlertText));

		// Dismiss alert to land on home page
		serp.dismissAlert();

		// Dismiss lightbox and do a search few times to see if the same lb is
		// not displayed again.
		for (int i = 0; i <= 3; i++) {
			//headerPage.search("shoes", false);
			headerPage.multipleSearch("shoes");
			Assert.assertFalse(serp.isAlertPresent());
		}

	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description = "Test Additional HR alertes on 40, 75, 100,.... 500 HR search count. -- Registered User")
	public void testAdditionalHrLightBoxes_RegisteredUser() throws SQLException {

		loginToSearch(randomUser_3);
		//headerPage.search("shoes", false);
		headerPage.multipleSearch("shoes");

		// Validate 40th search count LB
		DBUtils.updateSearchCount(randomUser_3.getEmail(), 39);
		headerPage.multipleSearch("shoe");

		Assert.assertTrue(serp.isAlertPresent());
		CustomLogger.log("40th Search Count alert is displayed.");
		Assert.assertTrue(serp.getAlertText().contains(additionalHrAlertsText));
		// Dismiss alert to land on home page
		serp.dismissAlert();

		// Validate additional alerts for every 25 searches from 75 to 500
		// searches.
		for (int i = 74; i <= 498; i = i + 25) {
			DBUtils.updateSearchCount(randomUser_3.getEmail(), i);
			headerPage.multipleSearch(Common.generateRandomID("abc"));

			Assert.assertTrue(serp.isAlertPresent());
			System.out.println("Search Count is ::" + i+1);
			Assert.assertTrue(serp.getAlertText().contains(additionalHrAlertsText));
			serp.dismissAlert();
			CustomLogger.log((i + 1) + "th Search Count alert is displayed.");

		}

	}

}
