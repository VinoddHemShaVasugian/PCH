package com.pch.sw.tests.misc;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.User;


public class GuidedSearchPresentationTests extends BaseTest {
	private HomePage homePage;	
	public WebHeaderPage webHeaderPage;
	public RegistrationPage registrationPage;
	private User randomUser_1;	
	private SearchResultsPage searchResultspage;
	private GuidedSearchPage guidedSearchPage;
	private AdminBasePage joomlaPage;	
	String oldSearchTitle="Desserts";
	String newSearchTitle="Test";

	
	@Test(testName="TestID=26756",priority=1,description="verify guided search terms for registered and un-registered user")
	public void test_GuidedSearchPresentationForGuestAndRegisteredUser(){
		//load home page and validate guided search terms
		homePage.load();
		Assert.assertTrue(guidedSearchPage.isGuidedSearchSectionDisplayed(), "Guided Search Section failed to display");		
		String guidedSearchTitle=guidedSearchPage.getGuidedSearchTitle();		
		guidedSearchPage.clickGuidedSearch();
		Assert.assertEquals(guidedSearchTitle.toLowerCase(), searchResultspage.getSearchBarText().toLowerCase());		
		//login with the registered user and validate guided search terms
		loginToSearch(randomUser_1);
		homePage.closeUserLevelLightBox();
		Assert.assertTrue(guidedSearchPage.isGuidedSearchSectionDisplayed(), "Guided Search Section failed to display");
		guidedSearchTitle=guidedSearchPage.getGuidedSearchTitle();
		guidedSearchPage.clickGuidedSearch();
		Assert.assertEquals(guidedSearchTitle.toLowerCase(), searchResultspage.getSearchBarText().toLowerCase());
	}

	@Test(testName="TestID=26756",priority=2,description="Edit search term from joomla admin and verify guided search terms for registered and un-registered user")
	public void test_VerifyUpdatedGuidedSearchTermForGuestAndRegisteredUser(){

		//go to joomla article and change search term and its weight
		joomlaPage.goToArticle("Guided Search Terms [D]");
		joomlaPage.changeSearchTermAndWeight(oldSearchTitle, newSearchTitle, "100");
		Assert.assertEquals(joomlaPage.saveCloseAndClearCache(),"success");
		// goto search page and validate changed search term
		homePage.load();
		Assert.assertTrue(guidedSearchPage.isGuidedSearchSectionDisplayed(), "Guided Search Section failed to display");
		Assert.assertTrue(guidedSearchPage.getGuidedSearchTitles().contains(newSearchTitle));
		//click on new search term and validate it SERP page
		guidedSearchPage.clickGuidedSearchByTitle(newSearchTitle);
		Assert.assertEquals(searchResultspage.getSearchBarText().toLowerCase(),newSearchTitle.toLowerCase());			
		//login with registered user and validate guided search terms
		loginToSearch(randomUser_1);
		homePage.closeUserLevelLightBox();
		Assert.assertTrue(guidedSearchPage.isGuidedSearchSectionDisplayed(), "Guided Search Section failed to display");
		Assert.assertTrue(guidedSearchPage.getGuidedSearchTitles().contains(newSearchTitle));
		//click on new search term and validate it SERP page
		guidedSearchPage.clickGuidedSearchByTitle(newSearchTitle);
		Assert.assertEquals(searchResultspage.getSearchBarText().toLowerCase(),newSearchTitle.toLowerCase());	
	}


	@Test(priority=3,description="change search term to its original value")
	public void ConfigureJoomlaForGuidedSearch_RevertSearchTerm(){

		//go to joomla article and change search term and its weight
		joomlaPage.goToArticle("Guided Search Terms [D]");
		joomlaPage.changeSearchTermAndWeight(newSearchTitle,oldSearchTitle, "10");
		Assert.assertEquals(joomlaPage.saveCloseAndClearCache(),"success");
		// goto search page and validate changed search term
	}

}
