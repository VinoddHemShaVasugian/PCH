package com.pch.sw.tests.searchHrRules;

import java.sql.SQLException;

import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class SearchCountsTests extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage searchResultsPage;
	User randomUser_1, randomUser_2, randomUser_3;

	@Test(description = "Registered User - Search counts for web results.")
	public void testWebSearchCounts_RegisteredUser() throws SQLException {

		// Register New User and login
		loginToSearch(randomUser_1);
		headerPage.search("shoe", false);

		// Validate the First search counts in DB\
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 1);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 1);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 1);

		// Do another search with a new term and validate the counts
		headerPage.search("shoes", false);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 2);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 2);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 3);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 3);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 4);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 4);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 5);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 5);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 6);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 3);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 6);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// refresh page and then validate the counts
		searchResultsPage.refreshPage();
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 7);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 4);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 7);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// Click on search button by leaving the search term as it is and
		// validate the counts
		searchResultsPage.clickSearchBtn();
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "dailysearchcount", 7);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailyhrsearchcount", 5);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webdailysearchcount", 8);
		DBUtils.validateSearchCount(randomUser_1.getEmail(), "webrealsearchcount", 2);

		// Click on Shopping Tab in the laft nav
		// searchResultsPage.clickOnShoppingTab();
		// DBUtils.validateSearchCount(randomUser_1.getEmail(),
		// "dailysearchcount", 8);
		// DBUtils.validateSearchCount(randomUser_1.getEmail(),
		// "shoppingdailyhrsearchcount", 1);
		// DBUtils.validateSearchCount(randomUser_1.getEmail(),
		// "shoppingdailysearchcount", 1);
	}

	@Test(description = "Guest User - Search counts for web results.")
	public void testWebSearchCounts_GuestUser() throws SQLException {

		homePage.load();
		String user = headerPage.getCookieValues("pci");
		CustomLogger.log("User cookie values is : " + user);

		headerPage.search("shoes", false);

		// Validate the First search counts in DB\
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 1);
        searchResultsPage.clickOnWebTab();
		headerPage.search("shoe", false);
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 2);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 3);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 3);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 4);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 4);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 5);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 5);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 6);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 3);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 6);

		// refresh page and then validate the counts
		searchResultsPage.refreshPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 7);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 4);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 7);

		// Click on search button by leaving the search term as it is and
		// validate the counts
		searchResultsPage.clickSearchBtn();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 7);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 4);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 7);

	}

	@Test(description = "Registered User - Search counts for shopping results.")
	public void testShoppingSearchCounts_RegisteredUser() throws SQLException {

		// Register New User and login
		loginToSearch(randomUser_2);
		headerPage.search("shoes", false);

		// Validate the First search counts in DB
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailysearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webrealsearchcount", 1);

		
		headerPage.search("shoe", false);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 3);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 3);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 4);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 4);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 5);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 5);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 6);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 3);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 6);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// refresh page and then validate the counts
		searchResultsPage.refreshPage();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 7);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 4);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 7);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// Click on search button by leaving the search term as it is and
		// validate the counts
		searchResultsPage.clickSearchBtn();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 8);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 5);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 8);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 1);

		// Do another search with a new term and validate the counts
		headerPage.search("shoes", false);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 9);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 6);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 5);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 8);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 2);

		// Do another search with a new term and validate the counts
		headerPage.search("shoe", false);
		searchResultsPage.clickOnMoreShoppingResultsLink();
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 11);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailyhrsearchcount", 7);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailysearchcount", 10);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webrealsearchcount", 4);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 1);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingrealsearchcount", 3);

	}

	@Test(description = "Guest User - Search counts for shopping results.")
	public void testShoppingSearchCounts_GuestUser() throws SQLException {

		homePage.load();
		String user = headerPage.getCookieValues("pci");
		CustomLogger.log("User cookie values is : " + user);

		headerPage.search("shoes", false);

		// Validate the First search counts in DB\
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "webdailysearchcount", 1);
		
		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailyhrsearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailysearchcount", 1);

		// Paginate and then validate the counts
		searchResultsPage.gotoNextPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 3);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailysearchcount", 3);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 4);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 1);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailysearchcount", 4);

		// Paginate and then validate the counts
		searchResultsPage.gotoPreviousPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 5);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailysearchcount", 5);

		// refresh page and then validate the counts
		searchResultsPage.refreshPage();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 6);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 3);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailysearchcount", 6);

		// Click on search button by leaving the search term as it is and
		// validate the counts
		searchResultsPage.clickSearchBtn();
		DBUtils.validateSearchCountGuestUser(user, "dailysearchcount", 6);
		DBUtils.validateSearchCountGuestUser(user, "webdailyhrsearchcount", 3);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCountGuestUser(user, "shoppingdailysearchcount", 7);

		// Do another search with a new term and validate the counts
		headerPage.search("shoe", false);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "dailysearchcount", 8);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "webdailysearchcount", 5);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailyhrsearchcount", 2);
		DBUtils.validateSearchCount(randomUser_2.getEmail(), "shoppingdailysearchcount", 8);

	}

	
	/**
	 * This function will enable and disable shopping
	 * 
	 * @param value
	 */
	@Test(priority=1)
	public void configureShopping() {
		joomlaPage.gotoPCHConfigurationTab("Infospace");
		joomlaPage.updateInfospaceSettings("Shopping Enabled", "2");
		joomlaPage.saveCloseAndClearCache();
	}

}
