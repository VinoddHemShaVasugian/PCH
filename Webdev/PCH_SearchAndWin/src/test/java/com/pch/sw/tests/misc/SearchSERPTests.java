package com.pch.sw.tests.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebFooterPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.User;

/*
 * This Test Case Covers
 * [1]SERP_Recognized_Users - Done
 * [1]SERPValidations - Done
 * [1]ValidateMessagingOnSERPForSearch_Unrecognized
 * [1]SERP_Msgs_Desktop - Done
*/

public class SearchSERPTests extends BaseTest {
	
	private SearchResultsPage searchResultPage;
	private HomePage webBasePage;
	private WebHeaderPage headerPage;
	private WebFooterPage footerPage;
	private AdminBasePage joomlaPage;
	private CentralServicesPage csPage;
	RegistrationPage regPage;
	private User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5, randomUser_6, randomUser_7;
	
	String elementForNormalSearch = "Hair";
	String elementForShoppingSearch = "Shoes";
	String suspiciousTerm = "3080";
	
	
	
	
		
	//@Test(description = "Test Case ID : 23338")
	public void validatingSERPGuestUser(){
		
		
		String articleToSearch = "SERP Messaging - Search";
		
		List<String> desktopUnRecogText = joomlaPage.serpUnRecognisedMsgs(articleToSearch);
		
		//System.out.println(desktopUnRecogText);
		
		//webBasePage.waitFor(2);
		
		webBasePage.load();
		
		String welcomeText = headerPage.welcomeText().getText();
		
		if(!welcomeText.contains("Welcome to PCHSearch&Win")){
			CustomLogger.log("User was already logged in, wait for a while..");
			headerPage.signOut();
		}else{
			CustomLogger.log("You Enter as a Guest User");
		}
		
		searchResultPage.searchAndGetCount(elementForNormalSearch);
		//Here we are checking for weImg, headermsg,headerMsgRegisterNw link,top/bottom ads, search results, pagination.
		CustomLogger.log("Validating the Web results page");
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForNormalSearch));
		
		webBasePage.load();
		searchResultPage.searchAndGetCount(elementForShoppingSearch);
				
		//Here we are checking for ShoppingImg, headermsg,headerMsgRegisterNw link,top/bottom ads, search results, pagination.
		CustomLogger.log("Validating the Shopping results page.");
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForShoppingSearch));
		
		String headerMsg = searchResultPage.getTextInHeaderMsg();
		
		if(headerMsg.equalsIgnoreCase(desktopUnRecogText.get(0))){
			CustomLogger.log("Header messger belongs to UnRecognised User");
		}else{
			headerMsg.equalsIgnoreCase(desktopUnRecogText.get(1));
			CustomLogger.log("Header messger belongs to UnRecognised User");
		}
		
		//searchResultPage.clickOnCarouselProductAndVerifyProductDetails();
		
		searchResultPage.validateLeftSideBar(elementForShoppingSearch);
		CustomLogger.log("We are viewing shopping results");
		
		searchResultPage.validateShoppingResultsPage(elementForShoppingSearch);
		CustomLogger.log("Validated Shopping Results Page");
		
		CustomLogger.log("Verifying pagination functionality");
		searchResultPage.paginationBarFunctionality(elementForShoppingSearch);
		
		CustomLogger.log("Validationg Sorting results in Shopping results page");
		searchResultPage.validateSortingShoppingresults(elementForShoppingSearch);
	}
	
	//@Test(description = "Test Case ID : 23338")
	public void validatingSERPRegisteredUser(){
		
		String elementForNormalSearch = "HAIR";
		String elementForShoppingSearch = "Shoes";
		String articleToSearch = "SERP Messaging - Search";
		
		List<String> desktopRecogText = joomlaPage.serpRecognizedMsgs(articleToSearch);
		joomlaPage
		.unpublishArticle("SERP Messaging First Consecutive Visit");
		
		webBasePage.load();
		
		Assert.assertTrue(headerPage.verifyHeaderLinksURL(),"HeaderLinks URL's are not as expected");
		headerPage.search(elementForNormalSearch, false);
		Assert.assertTrue(footerPage.verifyFooterLinksURL(),"FooterLinks URL'sare not as expected");
		
		loginToSearch(randomUser_1);
		
		CustomLogger.log("Validating Uni-nav links for Recognized User");
		headerPage.getAllUniNavLinksAndValidate();
		headerPage.verifyUniNavTabURLForRecognizeduser();
		
		/*String welcomeText = headerPage.welcomeText().getText();
		
		if(welcomeText.contains("Welcome to PCHSearch")){
			CustomLogger.log("User was already logged in, wait for a while..");
			headerPage.signOut();
		}else{
			CustomLogger.log("You Enter as a Guest User");
		}*/
		
		searchResultPage.searchAndGetCount(elementForNormalSearch);
		//Here we are checking for web/ShoppingImg, headermsg,headerMsgRegisterNw link,top/bottom ads, search results, pagination.
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForNormalSearch));
		
		searchResultPage.searchAndGetCount(elementForShoppingSearch);
		webBasePage.closeUserLevelLightBox();
		
		//Here we are checking for web/ShoppingImg, headermsg,headerMsgRegisterNw link,top/bottom ads, search results, pagination.
		Assert.assertTrue(searchResultPage.checkForGuestStructure(elementForShoppingSearch));
		
		String headerMsg = searchResultPage.getTextInHeaderMsg();
		
		if(headerMsg.equalsIgnoreCase(desktopRecogText.get(0))){
			CustomLogger.log("Header messger belongs to Recognised User");
		}else{
			headerMsg.equalsIgnoreCase(desktopRecogText.get(1));
			CustomLogger.log("Header messger belongs to Recognised User");
			
		}
		
		//searchResultPage.clickOnCarouselProductAndVerifyProductDetails();
		
		searchResultPage.validateLeftSideBar(elementForShoppingSearch);
		CustomLogger.log("We are viewing shopping results");
		
		searchResultPage.validateShoppingResultsPage(elementForShoppingSearch);
		
		searchResultPage.paginationBarFunctionality(elementForShoppingSearch);
		
		searchResultPage.validateSortingShoppingresults(elementForShoppingSearch);
		joomlaPage
		.publishArticle("SERP Messaging First Consecutive Visit");
	}
	
	
	//@Test
	public void testSwitchOnPredictiveSearchFromJoomla(){
		Map<String,String> filter = new HashMap<String,String>();
		filter.put("Position", "content-top");
		joomlaPage.gotoModule("PCH Search - Search Box (Home)",filter);
		joomlaPage.selectValuesForProperty("Support Predictive Text", "Yes");
	}	
	
	//@Test(description = "[1]SERPValidations - 16131" , dependsOnMethods="testSwitchOnPredictiveSearchFromJoomla")
	public void serpValidation(){
		
		String elementToSearch = "a";
		String expectedSuugesions = "amazon";
		String friendlyMessage = "PCH SearchAndWin did not find anything for";
		String spclChar = "!@#";
		String messageToBeDisplayed;
		int num1 = 6;
		int num2 = 4;
		
		webBasePage.load();
		
		//Searching for text on Top search bar.
		List<String> suggestions = headerPage.getPredictiveSearchSuggestions(elementToSearch);
		searchResultPage.searchAndGetCount(elementToSearch);
		//Verifying amazon in the predictive suggestion
		Assert.assertTrue(suggestions.contains("amazon"));		
		CustomLogger.log("Found "+expectedSuugesions+" in search suggestions of "+elementToSearch);
		
		Assert.assertTrue(searchResultPage.webResults().isDisplayed());
		Assert.assertTrue(searchResultPage.topAds().isDisplayed());
		Assert.assertTrue(searchResultPage.bottomAds().isDisplayed());
		Assert.assertTrue(searchResultPage.winnerList().isDisplayed());
		
		//Assert.assertTrue(searchResultPage.getSearchAndWinLogoLinkURL().contains(homeUrl));
		
		searchResultPage.pchCalculator(num1, num2);
		CustomLogger.log("Verifying PCH Calculator in SERP page for "+num1+" and "+num2+".");
		
		CustomLogger.log("Verifying friendly message, when we search for special characters");
		String actualMsg = searchResultPage.searchWithSpecialChar(spclChar);
		CustomLogger.log("Message Displayed is : "+actualMsg);
		messageToBeDisplayed=friendlyMessage+" "+spclChar;
		Assert.assertTrue(messageToBeDisplayed.contains(actualMsg));
		
		//Searching for Text on bottom search bar.
		CustomLogger.log("Search for any element in Bottom search bar");
		searchResultPage.searchAndGetCountOfBottomSearchBar(elementToSearch);
		Assert.assertTrue(suggestions.contains("amazon"));
		CustomLogger.log("Found "+expectedSuugesions+" in search suggestions of "+elementToSearch);
		
	}
	
	//@Test(description = "Guest User - Validate header message for suspicious search terms.")
	public void testSuspiciousSearchTerm_GuestUser() {

		// Get SERP header message 
		String articleToSearch = "SERP Messaging - Search";
		List<String> desktopUnRecogText = joomlaPage.serpUnRecognisedMsgs(articleToSearch);
		
		webBasePage.load();
		
		// Search for the suspicious search term
		headerPage.search(suspiciousTerm, false);
		
		// Validate header message
		searchResultPage.validateSuperPrizeAd();
		
		// Validate SERP header message
		String headerMsg = searchResultPage.getTextInHeaderMsg();
		
		Assert.assertTrue(headerMsg.equalsIgnoreCase(desktopUnRecogText.get(0)));
		CustomLogger.log("Guest User SERP header message is shown.");
		CustomLogger.log("SERP Header Message is: " +headerMsg);
		
		// Search for another suspicious term & validate the header message
		headerPage.search("PCH", false);
		
		// Validate header message
		searchResultPage.validateSuperPrizeAd();
		Assert.assertFalse(searchResultPage.isSerpHeaderMsgPresent());
		
		
	}
	
   //@Test(description = "Registered User - Validate header message for suspicious search terms.")
	public void testSuspiciousSearchTerm_RegisteredUser() {

		// Get SERP header message 
		String articleToSearch = "SERP Messaging - Search";
		List<String> desktopRecogText = joomlaPage.serpRecognizedMsgs(articleToSearch);
		joomlaPage
		.unpublishArticle("SERP Messaging First Consecutive Visit");
		loginToSearch(randomUser_2);
		
		// Search for the suspicious search term
		headerPage.search(suspiciousTerm, false);
		
		// Validate header message
		searchResultPage.validateSuperPrizeAd();
		
		// Validate SERP header message
		String headerMsg = searchResultPage.getTextInHeaderMsg();
		CustomLogger.log("SERP Header Message is: " +headerMsg);
		Assert.assertTrue(headerMsg.equalsIgnoreCase(desktopRecogText.get(0)));
		CustomLogger.log("Registered User SERP header message is shown.");
		CustomLogger.log("SERP Header Message is: " +headerMsg);
		
		// Search for another suspicious term & validate the header message
		headerPage.search("PCH", false);
				
		// Validate header message
		searchResultPage.validateSuperPrizeAd();
		Assert.assertFalse(searchResultPage.isSerpHeaderMsgPresent());
		joomlaPage
		.publishArticle("SERP Messaging First Consecutive Visit");
	}
	
	//@Test(description = "Guest User - Validate if house Ad is shown if no Ads are returned from Infospace.")
	public void testHouseAdsIfNoAds_GuestUser() {

		// Get SERP header message 
		String articleToSearch = "SERP Messaging - Search";
		List<String> desktopUnRecogText = joomlaPage.serpUnRecognisedMsgs(articleToSearch);
		
		webBasePage.load();
		
		// Search for the suspicious search term
		headerPage.search(suspiciousTerm, false);
		
		// Validate header message
		searchResultPage.validateHouseAd();
		
		// Validate SERP header message
		String headerMsg = searchResultPage.getTextInHeaderMsg();
		
		Assert.assertTrue(headerMsg.equalsIgnoreCase(desktopUnRecogText.get(0)));
		CustomLogger.log("Guest User SERP header message is shown.");
		CustomLogger.log("SERP Header Message is: " +headerMsg);
		
	}
	
	@Test(description = "Registered User - Validate if house Ad is shown if no Ads are returned from Infospace.")
	public void testHouseAdsIfNoAds_RegisteredUser() {

		// Get SERP header message 
		String articleToSearch = "SERP Messaging - Search";
		List<String> desktopRecogText = joomlaPage.serpRecognizedMsgs(articleToSearch);
		joomlaPage
		.unpublishArticle("SERP Messaging First Consecutive Visit");
		webBasePage.load();
		loginToSearch(randomUser_3);
		
		// Search for the suspicious search term
		headerPage.search(suspiciousTerm, false);
		
		// Validate header message
		searchResultPage.validateHouseAd();
		
		// Validate SERP header message
		String headerMsg = searchResultPage.getTextInHeaderMsg();
		CustomLogger.log("SERP Header Message is: " +headerMsg);
		Assert.assertTrue(headerMsg.equalsIgnoreCase(desktopRecogText.get(0)));
		CustomLogger.log("Registered User SERP header message is shown.");
		CustomLogger.log("SERP Header Message is: " +headerMsg);
		joomlaPage
		.publishArticle("SERP Messaging First Consecutive Visit");
		
	}
	
	//@Test(description = "Looking for -Are you looking for?- in nfspRelated SERP page- guest User - 29464")
	public void nfspRelatedSearchesGuestUser(){
		
		String appendURL = "&nfsp=related";
		webBasePage.load();
		
		//Search with any term
		headerPage.search("Shoes", false);
		
		Common.sleepFor(5000);
				
		//append "&nfsp=related" to current SERP page URL
		String reqURl = webBasePage.getCurrentURL()+appendURL;
		webBasePage.load(reqURl);
		
		//getting nfspRelated -Are you looking for?- text and printing them in console log
		searchResultPage.getNfspRelatedText();
		
		CustomLogger.log("verified related searches for guest user.");
	}
	
	//@Test(description = "Looking for -Are you looking for?- in nfspRelated SERP page- guest User - 29464")
	public void nfspRelatedSearchesRegUser(){
		
		String appendURL = "&nfsp=related";
		webBasePage.load();
		
		loginToSearch(randomUser_4);
		
		//Search with any term
		headerPage.search("Shoes", false);
		
		Common.sleepFor(5000);
		
		//append "&nfsp=related" to current SERP page URL
		String reqURl = webBasePage.getCurrentURL()+appendURL;
		webBasePage.load(reqURl);
		
		//getting nfspRelated -Are you looking for?- text and printing them in console log
		searchResultPage.getNfspRelatedText();
		
		CustomLogger.log("verified related searches for Registered user.");
		
	}
	
	/*
	 * TC : [1]SERPRightRail_Desktop - 25724 - under this TC we've 4 @Test's as below
	 * Guest User
	 * FB User
	 * Mini Reg User
	 * Silver User
	*/
	//@Test(description = "Validating nfspRigh SERP Results page, should contain Ads on right side - 25724")
	public void nfspRighSERPSearchGuestUser(){
			
		String appendURL = "&nfsp=right";
		
		//go to homepage and search with any term 
		webBasePage.load();
		headerPage.search("shoes", false);
		
		//Append &nfsp=righ to the URL of SERP page 
		String currentSERPurl = webBasePage.getCurrentURL();		
				
		//url with web as category
		String rightWebCat = currentSERPurl+"web"+appendURL;
		//url with shopping as category
		String rightShopCat = currentSERPurl+"shopping"+appendURL;
		//url with out any category
		String rightNoCat = currentSERPurl+appendURL;
				
		//loop to validate above 3 scenarios
		for(int i=0; i<3; i++){
			if(i==0){
				webBasePage.load(rightShopCat);
				CustomLogger.log("validating right SERP results Page with shopping as category");
			}else if (i==1) {
				webBasePage.load(rightWebCat);
				CustomLogger.log("validating right SERP results Page with web as category");
			}else if (i==2) {
				webBasePage.load(rightNoCat);
				CustomLogger.log("validating right SERP results Page without any category ");	
			}
			
			//validating SERP result page - for top ads, web results, winner list
			searchResultPage.topAds().isDisplayed();
			searchResultPage.webResults().isDisplayed();
			searchResultPage.winnerList().isDisplayed();
			CustomLogger.log("Validating SERP Results page include Ad on the right rail");
			
			//verifying Top Ads count on SERP results Page - should be 4
			CustomLogger.log("Validating count of top Ads - should be 4");
			Assert.assertEquals(4, searchResultPage.getTopAdsCount());
			
			//verifying SERP search results on right side of page above open X Ads - should <=6
			Assert.assertTrue(6 >= searchResultPage.getRightAdsCount());
			CustomLogger.log("Validated count of SERP results on right side - found "+searchResultPage.getRightAdsCount()+" search results");
		
		}
		
	}
		
	
	//@Test(description = "Validating nfspRigh SERP Results page, should contain Ads on right side - 25724")
	public void nfspRighSERPSearchFBUser(){
		
	 //* Go to the below Site URL's for Nfdsp=right and check the Step 3 and Step 4 for the same :
		
		String appendURL = "&nfsp=right";
				
		//Creating FB user in Reg foundation
		String fbLoginURl = csPage.createFBUser(randomUser_5);
		webBasePage.load(fbLoginURl);
		CustomLogger.log("Validating Right Rail SERP for FB user");
		
		//go to homepage and search with any term
		headerPage.search("shoes", false);
		
		//Append &nfsp=righ to the URL of SERP page
		String currentSERPurl = webBasePage.getCurrentURL();		
				
		//url with web as category
		String rightWebCat = currentSERPurl+"web"+appendURL;
		//url with shopping as category
		String rightShopCat = currentSERPurl+"shopping"+appendURL;
		//url with out any category
		String rightNoCat = currentSERPurl+appendURL;
		
		//loop to validate above 3 scenarios
		for(int i=0; i<3; i++){
			if(i==0){
				webBasePage.load(rightShopCat);
				CustomLogger.log("validating right SERP results Page with shopping as category");
			}else if (i==1) {
				webBasePage.load(rightWebCat);
				CustomLogger.log("validating right SERP results Page with web as category");
			}else if (i==2) {
				webBasePage.load(rightNoCat);
				CustomLogger.log("validating right SERP results Page without any category ");	
			}
				
			//validating SERP result page - for top ads, web results, winner list
			searchResultPage.topAds().isDisplayed();
			searchResultPage.webResults().isDisplayed();
			searchResultPage.winnerList().isDisplayed();
			CustomLogger.log("Validating SERP Results page include Ad on the right rail");
		
			//verifying Top Ads count on SERP results Page - should be 4
			CustomLogger.log("Validating count of top Ads - should be 4");
			Assert.assertEquals(4, searchResultPage.getTopAdsCount());
			
			//verifying SERP search results on right side of page above open X Ads - should <=6
			Assert.assertTrue(6 >= searchResultPage.getRightAdsCount());
			CustomLogger.log("Validated count of SERP results on right side - found "+searchResultPage.getRightAdsCount()+" search results");
		
		}
		
	}
	
	
	
	//@Test(description = "Validating nfspRigh SERP Results page, should contain Ads on right side - 25724")
	public void nfspRighSERPSearchMiniRegUser(){
		
	//Step 2
	 //* Go to the below Site URL's for Nfdsp=right and check the Step 3 and Step 4 for the same :
		
		String appendURL = "&nfsp=right";
		
		
		//Creating FB user in Reg foundation 
		String fbLoginURl = csPage.createMiniRegUser(randomUser_6);
		webBasePage.load(fbLoginURl);
		CustomLogger.log("Validating Right Rail SERP for Mini reg user");
		
		headerPage.search("shoes", false);
		
		String currentSERPurl = webBasePage.getCurrentURL();		
				
		//url with web as category
		String rightWebCat = currentSERPurl+"web"+appendURL;
		//url with shopping as category
		String rightShopCat = currentSERPurl+"shopping"+appendURL;
		//url with out any category
		String rightNoCat = currentSERPurl+appendURL;
		
		//loop to validate above 3 scenarios
		for(int i=0; i<3; i++){
			if(i==0){
				webBasePage.load(rightShopCat);
				CustomLogger.log("validating right SERP results Page with shopping as category");
			}else if (i==1) {
				webBasePage.load(rightWebCat);
				CustomLogger.log("validating right SERP results Page with web as category");
			}else if (i==2) {
				webBasePage.load(rightNoCat);
				CustomLogger.log("validating right SERP results Page without any category ");	
			}
				
			//validating SERP result page - for top ads, web results, winner list
			searchResultPage.topAds().isDisplayed();
			searchResultPage.webResults().isDisplayed();
			searchResultPage.winnerList().isDisplayed();
			CustomLogger.log("Validating SERP Results page include Ad on the right rail");
		
			//verifying Top Ads count on SERP results Page - should be 4
			CustomLogger.log("Validating count of top Ads - should be 4");
			Assert.assertEquals(4, searchResultPage.getTopAdsCount());
			
			//verifying SERP search results on right side of page above open X Ads - should <=6
			Assert.assertTrue(6 >= searchResultPage.getRightAdsCount());
			CustomLogger.log("Validated count of SERP results on right side - found "+searchResultPage.getRightAdsCount()+" search results");
		
		}
		
	}
	
	
	//@Test(description = "Validating nfspRigh SERP Results page, should contain Ads on right side - 25724")
	public void nfspRighSERPSearchSilverRegUser(){
		
		String appendURL = "&nfsp=right";
		
		
		//Creating Silver user in Reg foundation 
		String fbLoginURl = csPage.createSilverUser(randomUser_7);
		webBasePage.load(fbLoginURl);
		CustomLogger.log("Validating Right Rail SERP for silver user");
		
		headerPage.search("shoes", false);
		
		String currentSERPurl = webBasePage.getCurrentURL();		
				
		//url with web as category
		String rightWebCat = currentSERPurl+"web"+appendURL;
		//url with shopping as category
		String rightShopCat = currentSERPurl+"shopping"+appendURL;
		//url with out any category
		String rightNoCat = currentSERPurl+appendURL;
		
		//loop to validate above 3 scenarios
		for(int i=0; i<3; i++){
			if(i==0){
				webBasePage.load(rightShopCat);
				CustomLogger.log("validating right SERP results Page with shopping as category");
			}else if (i==1) {
				webBasePage.load(rightWebCat);
				CustomLogger.log("validating right SERP results Page with web as category");
			}else if (i==2) {
				webBasePage.load(rightNoCat);
				CustomLogger.log("validating right SERP results Page without any category ");	
			}
				
			//validating SERP result page - for top ads, web results, winner list
			searchResultPage.topAds().isDisplayed();
			searchResultPage.webResults().isDisplayed();
			searchResultPage.winnerList().isDisplayed();
			CustomLogger.log("Validating SERP Results page include Ad on the right rail");
		
			//verifying Top Ads count on SERP results Page - should be 4
			CustomLogger.log("Validating count of top Ads - should be 4");
			Assert.assertEquals(4, searchResultPage.getTopAdsCount());
			
			//verifying SERP search results on right side of page above open X Ads - should <=6
			Assert.assertTrue(6 >= searchResultPage.getRightAdsCount());
			CustomLogger.log("Validated count of SERP results on right side - found "+searchResultPage.getRightAdsCount()+" search results");
		
		}
		
	}
	//@AfterMethod
	public void publishconsecutivearticle()
	{
		joomlaPage
		.publishArticle("SERP Messaging First Consecutive Visit");
	}

	@Test
	public void Areyoulookingforresults(){
		loginToSearch(randomUser_4);
		headerPage.search("hello",false);
		CustomLogger.log("Checking 'are you looking for ?'on right side and the font size ");
		searchResultPage.Areyoulookingfor();
		}
	
}
