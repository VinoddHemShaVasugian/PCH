package com.pch.sw.tests.shopping;

import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class ShoppingPagesTests extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage serp;
	User randomUser_1, randomUser_2, randomUser_3;
	String searchKeyword = "shoes";

	@Test(groups = { GroupNames.Regression, GroupNames.Mobile,
			GroupNames.Desktop }, description = "Guest User - Validate Shopping Carousel on SERP.")
	public void testShoppingCarousel_GuestUser() {

		homePage.load();
		headerPage.search(searchKeyword, false);

		// Validate Shopping carousel
		serp.validateShoppingCarousel();

	}

	@Test(groups = { GroupNames.Regression, GroupNames.Mobile,
			GroupNames.Desktop }, description = "Registered User - Validate Shopping Carousel on SERP.")
	public void testShoppingCarousel_RegisteredUser() {

		// Register New User and login
		loginToSearch(randomUser_1);
		headerPage.search(searchKeyword, false);
		homePage.closeUserLevelLightBox();
		// Validate Shopping carousel
		serp.validateShoppingCarousel();

	}

	@Test(groups = { GroupNames.Regression, GroupNames.Mobile,
			GroupNames.Desktop }, description = "Guest User - Validate Shopping Results page.")
	public void testShoppingResultsPage_GuestUser() {

		homePage.load();
		headerPage.search(searchKeyword, false);

		// Navigate to shopping results page and validate the shopping results
		// page
		serp.clickOnShoppingTab();

		// Validate Sorting functionality
		serp.validateSortingShoppingresults(searchKeyword);

		// Validate Pagination
		serp.validatePagination(searchKeyword);

		// Validate Left navigation
		serp.validateLeftNav();

	}

	@Test(groups = { GroupNames.Regression, GroupNames.Mobile,
			GroupNames.Desktop }, description = "Registered User - Validate Shopping Results page.")
	public void testShoppingResultsPage_RegisteredUser() {

		loginToSearch(randomUser_2);

		headerPage.search(searchKeyword, false);

		// Navigate to shopping results page and validate the shopping results
		// page
		serp.clickOnShoppingTab();

		// Validate Sorting functionality
		serp.validateSortingShoppingresults(searchKeyword);

		// Validate Pagination
		serp.validatePagination(searchKeyword);

		// Validate Left navigation
		serp.validateLeftNav();

	}

	// @Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description
	// = "Guest User - Validate Shopping/Product details page.")
	public void testShoppingDetailsPage_GuestUser() {

		homePage.load();
		headerPage.search(searchKeyword, false);

		// Validate product page by navigating from different locations
		serp.navigateToProductPageAndValidate(searchKeyword);

	}

	// @Test(groups={GroupNames.Regression,GroupNames.Mobile,GroupNames.Desktop},description
	// = "Registered User - Validate Shopping/Product details page.")
	public void testShoppingDetailsPage_RegisteredUser() {

		loginToSearch(randomUser_3);
		homePage.closeUserLevelLightBox();
		headerPage.search(searchKeyword, false);

		// Validate product page by navigating from different locations
		serp.navigateToProductPageAndValidate(searchKeyword);

	}

}
