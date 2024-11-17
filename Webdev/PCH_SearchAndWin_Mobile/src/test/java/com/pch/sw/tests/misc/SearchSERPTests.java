package com.pch.sw.tests.misc;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SearchSERPTests extends BaseTest {

	private SearchResultsPage searchResultPage;
	private HomePage webBasePage;
	private WebHeaderPage headerPage;
	private GuidedSearchPage gsPage;
	RegistrationPage regPage;
	AdminBasePage joomlaPage;

	String serp_messaging_article = "SERP Messaging - Search";
	String consecutive_visit_module = "PCH Search - Consecutive Visits";
	String elementForNormalSearch = "Hair";
	String elementForShoppingSearch = "Shoes";
	String first_consecutive_visit_article = "SERP Messaging First Consecutive Visit";
	String second_consecutive_visit_article = "SERP Messaging Second Consecutive Visit";
	String third_consecutive_visit_article = "SERP Messaging Third Consecutive Visit";
	String fourth_consecutive_visit_article = "SERP Messaging Fourth Consecutive Visit";
	String fifth_consecutive_visit_article = "SERP Messaging Fifth Consecutive Visit";
	User randomuser;

	String spclChar = "!@#";
	String friendlyMessage = "PCH SearchAndWin did not find anything for";
	String messageToBeDisplayed;

	// [Mobile] Validating SERP page, first and later message, Pagination,
	// Shopping results page, Sorting. for Unrecognized user
	@Test(groups = {
			GroupNames.Mobile }, testName = "[1]SERP_Msgs_Mobile 15720[M], [1]ProductDetailPage_Mobile 22039[M]")
	public void mobileSERPMessagePaginationAndShoppingDetailsValidationUnRecTests() {

		// UnPublish the Consecutive Visits function
		gsPage.driver.maximizeWindow();
		joomlaPage.unpublishArticle(first_consecutive_visit_article);
		joomlaPage.unpublishArticle(second_consecutive_visit_article);
		joomlaPage.unpublishArticle(third_consecutive_visit_article);
		joomlaPage.unpublishArticle(fourth_consecutive_visit_article);
		joomlaPage.unpublishArticle(fifth_consecutive_visit_article);
		// joomlaPage.goToArticle(serp_messaging_article);
		List<String> serp_msg = joomlaPage.serpUnRecognizedMsgsForMobile(serp_messaging_article);

		gsPage.driver.resizeWindow(375, 667);
		webBasePage.load();

		// Verifying First and Later SERP messages

		CustomLogger.log("Making first search of the day with Guest user..");
		gsPage.searchUsingTopSearchBox(elementForNormalSearch);

		CustomLogger.log("Validating first SERP message..");
		String firstMessage = searchResultPage.serpHeaderMsg().getText();
		CustomLogger.log("Message : " + firstMessage);
		Assert.assertTrue(firstMessage.equalsIgnoreCase(serp_msg.get(0)),
				"We are seeing invalid SERP message. Expected -" + serp_msg.get(0) + " and the Actual -"
						+ firstMessage);

		// Verifying SERP page Structure
		CustomLogger.log("Validating SERP page");
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForNormalSearch));

		// Verifying Pagination
		CustomLogger.log("Validating Paginaition..");
		searchResultPage.paginationBarFunctionality(elementForNormalSearch);

		CustomLogger.log("Making second search of the day with Resistered user..");
		headerPage.search(elementForShoppingSearch);

		CustomLogger.log("Validating Later SERP message..");
		String laterMessage = searchResultPage.serpHeaderMsg().getText();
		CustomLogger.log("laterMessage : " + laterMessage);
		Assert.assertTrue(laterMessage.equalsIgnoreCase(serp_msg.get(1)),
				"We are seeing invalid SERP message. Expected -" + serp_msg.get(1) + " and the Actual -"
						+ laterMessage);

		CustomLogger.log("Validating SERP page");
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForNormalSearch));

		// Publish the Consecutive Visits function
		gsPage.driver.maximizeWindow();
		joomlaPage.publishArticle(first_consecutive_visit_article);
		joomlaPage.publishArticle(second_consecutive_visit_article);
		joomlaPage.publishArticle(third_consecutive_visit_article);
		joomlaPage.publishArticle(fourth_consecutive_visit_article);
		joomlaPage.publishArticle(fifth_consecutive_visit_article);

	}

	// [Mobile] Validating SERP page, first and later message, Pagination,
	// Shopping results page, Sorting. for Recognized user.
	@Test(groups = {
			GroupNames.Mobile }, testName = "[1]SERP_Msgs_Mobile 15720[M],[1]ProductDetailPage_Mobile 22039[M] ")
	public void mobileSERPMessagePaginationAndShoppingDetailsValidationRecTests() {

		// UnPublish the Consecutive Visits function
		gsPage.driver.maximizeWindow();
		joomlaPage.unpublishArticle(first_consecutive_visit_article);
		joomlaPage.unpublishArticle(second_consecutive_visit_article);
		joomlaPage.unpublishArticle(third_consecutive_visit_article);
		joomlaPage.unpublishArticle(fourth_consecutive_visit_article);
		joomlaPage.unpublishArticle(fifth_consecutive_visit_article);
		List<String> serp_msg = joomlaPage.serpRecognizedMsgsForMobile(serp_messaging_article);

		gsPage.driver.resizeWindow(375, 667);
		webBasePage.load();

		loginToSearch(randomuser);
		CustomLogger.log("Title of the page : " + webBasePage.titleOfPage() + "...");

		if ((webBasePage.titleOfPage().contains("GuidedSearch"))
				|| (webBasePage.titleOfPage().contains("GuidedSearch"))) {
			CustomLogger.log("You are on Search Home page..");
		} else if ((webBasePage.titleOfPage().contains("Fruitastic Fortune | PCH.com"))
				|| webBasePage.titleOfPage().contains("GamePath")) {
			CustomLogger.log("You are on --" + webBasePage.titleOfPage() + "--, lets drive you to home page..");
			webBasePage.gotoURL(
					"http://search." + Environment.getEnvironment() + ".pch.com/?returnUrl=/gameone?path=done");
		}

		CustomLogger.log("Verifying user..");
		String welcomeText = headerPage.getWelcomeText();

		if (welcomeText.contains("Guest")) {
			CustomLogger.log("Some thing went wrong, will try to login in again.. ");
			headerPage.loginToSearch("np10@pchmail.com", "testing");
		} else {
			CustomLogger.log("You are successfully logged in..");
		}

		CustomLogger.log("Making first search of the day with Resistered user..");
		gsPage.searchUsingTopSearchBox(elementForNormalSearch);

		CustomLogger.log("Validating first SERP message..");
		String firstMessage = searchResultPage.serpHeaderMsg().getText();
		CustomLogger.log("Message : " + firstMessage);
		Assert.assertTrue(firstMessage.equalsIgnoreCase(serp_msg.get(0)),
				"We are seeing invalid SERP message. Expected -" + serp_msg.get(0) + " and the Actual -"
						+ firstMessage);

		CustomLogger.log("Validating SERP page");
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForNormalSearch));

		CustomLogger.log("Validating Paginaition..");
		searchResultPage.paginationBarFunctionality(elementForShoppingSearch);

		CustomLogger.log("Making second search of the day with Resistered user..");
		headerPage.search(elementForShoppingSearch);

		CustomLogger.log("Validating Later SERP message..");
		String laterMessage = searchResultPage.serpHeaderMsg().getText();
		CustomLogger.log("laterMessage: " + laterMessage);
		Assert.assertTrue(laterMessage.equalsIgnoreCase(serp_msg.get(1)),
				"We are seeing invalid SERP message. Expected -" + serp_msg.get(1) + " and the Actual -"
						+ laterMessage);

		CustomLogger.log("Validating SERP page");
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForNormalSearch));

		// Publish the Consecutive Visits function
		gsPage.driver.maximizeWindow();
		joomlaPage.publishArticle(first_consecutive_visit_article);
		joomlaPage.publishArticle(second_consecutive_visit_article);
		joomlaPage.publishArticle(third_consecutive_visit_article);
		joomlaPage.publishArticle(fourth_consecutive_visit_article);
		joomlaPage.publishArticle(fifth_consecutive_visit_article);
	}

	@Test(groups = {
			GroupNames.Mobile }, testName = "[1]SuspiciousSearchTerm_Mobile 19994[M]", description = "Guest User - Validate header message for suspicious search terms.")
	public void mobileTestSuspiciousSearchTerm_GuestUser() {

		List<String> suspiciousTerms = new ArrayList<String>();
		suspiciousTerms.add("3080");
		suspiciousTerms.add("4749");
		suspiciousTerms.add("4650");
		suspiciousTerms.add("1400");
		suspiciousTerms.add("www.pch.com sweeps");
		suspiciousTerms.add("www.pch.com/actnow");
		suspiciousTerms.add("www.pchgames.com");

		webBasePage.load();

		// Search for the suspicious search term
		for (int i = 0; i < suspiciousTerms.size(); i++) {
			CustomLogger.log("Searching with the term - " + suspiciousTerms.get(i) + " -");
			gsPage.gotoHomePage();
			gsPage.searchUsingTopSearchBox(suspiciousTerms.get(i));
			webBasePage.waitTillPageLoads();
			CustomLogger.log("validating message displayed.");
			searchResultPage.validateSuperPrizeAd();
			Assert.assertTrue(searchResultPage.isSerpHeaderMsgPresent());

		}

	}

	@Test(groups = {
			GroupNames.Mobile }, testName = "[1]SuspiciousSearchTerm_Mobile 19994[M]", description = "Validating User Friendly message Recognised user")
	public void validateUserFriendlyMessageTests() {

		// Validating User friendly message for Guest User.
		webBasePage.load();
		String actualMsg = searchResultPage.searchWithSpecialChar(spclChar);

		CustomLogger.log("Message Displayed is : " + actualMsg);
		messageToBeDisplayed = friendlyMessage + " " + spclChar;
		Assert.assertTrue(messageToBeDisplayed.contains(actualMsg));

		// Validating User friendly message for Guest User.
		webBasePage.load();
		headerPage.loginToSearch("np02@pchmail.com", "testing");
		actualMsg = searchResultPage.searchWithSpecialChar(spclChar);

		CustomLogger.log("Message Displayed is : " + actualMsg);
		messageToBeDisplayed = friendlyMessage + " " + spclChar;
		Assert.assertTrue(messageToBeDisplayed.contains(actualMsg));
	}
}
