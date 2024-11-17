package com.pch.sw.tests.searchHrRules;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.SearchLimitLightBox;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class SearchHighRiskRulesTests extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage searchResultsPage;
	User randomUser_1, randomUser_2, randomUser_3;

	@Test(description = "Rapidly Searching LB - Search for the same term and see a ligntbox on 7th search. -- Registered User")
	public void testRapidlySearchingLB_RegisteredUser() throws SQLException {

		loginToSearch(randomUser_1);
		headerPage.search("PCH", false);

		// Search for the same term six more times to see the LB.
		for (int i = 0; i <= 5; i++) {
			headerPage.search("PCH", false);
		}

		// First warning Light box should be displayed
		SearchLimitLightBox lb = searchResultsPage.getSearchLimitLightBox();
		Common.sleepFor(2000);
		Assert.assertTrue(lb.isLightBoxPresent());
		CustomLogger.log("First Rapidly Searching warning light box is displayed.");

		// Dismiss light box
		lb.dismissLightBox();
		homePage.closeUserLevelLightBox();

		// Search for the same term six more times to see the LB
		for (int i = 0; i <= 6; i++) {
			headerPage.search("PCH", false);
		}

		// Second warning Light box should be displayed
		Common.sleepFor(2000);
		Assert.assertTrue(lb.isLightBoxPresent());
		CustomLogger.log("Second Rapidly Searching warning light box is displayed.");

		// Dismiss light box
		lb.dismissLightBox();

		// Search for the same term six more times to see the LB
		for (int i = 0; i <= 6; i++) {
			headerPage.search("PCH", false);
		}

		// Warning light box should not be displayed after showing it for two
		// times
		Assert.assertFalse(lb.isLightBoxPresent());
		CustomLogger.log("Rapidly Searching warnign light box is not displayed after displaying two times.");
	}

	@Test(description = "Rapidly Searching LB - Search for the same term and see a ligntbox on 7th search. -- Guest User")
	public void testRapidlySearchingLB_GuestUser() throws SQLException {

		homePage.load();
		// Search for the same term seven time to see the LB.
		for (int i = 0; i <= 6; i++) {
			headerPage.search("PCH", false);
		}

		// First warning Light box should be displayed
		SearchLimitLightBox lb = searchResultsPage.getSearchLimitLightBox();
		Assert.assertTrue(lb.isLightBoxPresent());
		CustomLogger.log("First Rapidly Searching warning light box is displayed.");

		// Dismiss light box
		lb.dismissLightBox();

		// Search for the same term seven more times to see the LB
		for (int i = 0; i <= 6; i++) {
			headerPage.search("PCH", false);
		}

		// Second warning Light box should be displayed
		Common.sleepFor(2000);
		Assert.assertTrue(lb.isLightBoxPresent());
		CustomLogger.log("Second Rapidly Searching warning light box is displayed.");

		// Dismiss light box
		lb.dismissLightBox();

		for (int i = 0; i <= 6; i++) {
			// Search for the same term seven more times
			headerPage.search("PCH", false);
		}

		// Warning light box should not be displayed after showing it for two
		// times
		Assert.assertFalse(lb.isLightBoxPresent());
		CustomLogger.log("Rapidly Searching warnign light box is not displayed after displaying two times.");
	}

	@Test(description = "Force User to Login on 25th Search.")
	public void testForceUserToLoginOn25thSearch() throws SQLException {

		homePage.load();
		String user = headerPage.getCookieValues("pci");
		CustomLogger.log("User cookie values is : " + user);

		headerPage.search("PCH", false);

		// Validate if the user is forced to login after 25 searches
		DBUtils.updateSearchCount_GuestUser(user, 25);
		headerPage.search(Common.getRandomString(5), false);

		Assert.assertTrue(homePage.getCurrentURL()
				.contentEquals("https://accounts." + Environment.getEnvironment() + ".pch.com/login"));
		CustomLogger.log("SERP is automatically redirected to login page on 25th search.");
		CustomLogger
				.log("Current page url is : " + "https://accounts." + Environment.getEnvironment() + ".pch.com/login");

	}

	@Test(description = "Maximum tokens received light box is shown after 25 HR search count. -- Registered User")
	public void testMaxTokensEarnedForSearchingLB_RegisteredUser() throws SQLException {

		loginToSearch(randomUser_2);
		headerPage.search("PCH");
		homePage.closeUserLevelLightBox();

		DBUtils.updateSearchCount(randomUser_2.getEmail(), 25);
		headerPage.search(Common.getRandomString(5), false);

		// Validate if Maximum tokens received Light box is displayed
		SearchLimitLightBox lb = searchResultsPage.getSearchLimitLightBox();
		Assert.assertTrue(lb.isLightBoxPresent());
		CustomLogger.log("Maximum tokens received light box is displayed.");

		// Dismiss lightbox and do a search few times to see if the same lb is
		// not displayed again.
		lb.dismissLightBox();
		for (int i = 0; i <= 3; i++) {
			headerPage.search("PCH", false);
			Assert.assertFalse(lb.isLightBoxPresent());
		}

	}

	@Test(description = "Test Additional HR Light Boxes on 40, 75, 100,.... 500 HR search count. -- Registered User")
	public void testAdditionalHrLightBoxes_RegisteredUser() throws SQLException {

		loginToSearch(randomUser_3);
		headerPage.search("PCH", false);

		// Validate 40th search count LB
		DBUtils.updateSearchCount(randomUser_3.getEmail(), 39);
		headerPage.search("PCH", false);

		SearchLimitLightBox lb = searchResultsPage.getSearchLimitLightBox();
		Assert.assertTrue(lb.isLightBoxPresent());
		lb.dismissLightBox();
		CustomLogger.log("40th Search Count light box is displayed.");

		// Validate additional light boxes for every 25 searches from 75 to 500
		// searches.
		for (int i = 74; i <= 500; i = i + 25) {
			DBUtils.updateSearchCount(randomUser_3.getEmail(), i);
			headerPage.search(Common.generateRandomID("abc"), false);

			Assert.assertTrue(lb.isLightBoxPresent());
			lb.dismissLightBox();
			CustomLogger.log((i + 1) + "th Search Count light box is displayed.");

		}

	}

}
