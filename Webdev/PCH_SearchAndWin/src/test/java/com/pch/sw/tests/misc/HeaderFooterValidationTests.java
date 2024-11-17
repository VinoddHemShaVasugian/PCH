package com.pch.sw.tests.misc;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebFooterPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

//Footer QC TC = 14876

public class HeaderFooterValidationTests extends BaseTest {

	private WebHeaderPage webHeaderPage;
	private WebFooterPage webFooterPage;
	private HomePage homePage;
	private GuidedSearchPage gsPage;
	RegistrationPage webRegistrationPage;
	User randomUser,randomUser_1,randomUser_2,randomUser_3, randomUser_4;
	
	String searchKeyword = "winter";
	/**
	 * 
	 * Validate Header links
	 * 
	 */
	@Test
	public void testHeaderLinks() {
		/*CustomLogger.log(Environment.getAppName() + " | "
				+ Environment.getEnvironment().toUpperCase()
				+ ": Validate Header Links");*/
		
		homePage.load();
		Assert.assertNotNull(webHeaderPage.getWelcomeUserLinkText());
		CustomLogger.log("Welcome to PCH Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getaboutPCHSearchAndWin());
		CustomLogger.log("About PCH Search&Win Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.gethowToSearch());
		CustomLogger.log("How To Search Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.gethelp());
		CustomLogger.log("Help Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		/*Assert.assertNotNull(webHeaderPage.getContestDetailLink());
		CustomLogger.log("Contact Details Link is present in header.");*/
		
		Assert.assertNotNull(webHeaderPage.getSignInButton());
		CustomLogger.log("SignIn link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getRegisterButton());
		CustomLogger.log("Register Link is present in header.");
		
	//Check Header Links after Search
		webHeaderPage.search(searchKeyword, false);
		Assert.assertNotNull(webHeaderPage.getWelcomeUserLinkText());
		CustomLogger.log("Welcome to PCH Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getaboutPCHSearchAndWin());
		CustomLogger.log("About PCH Search&Win Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.gethowToSearch());
		CustomLogger.log("How To Search Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.gethelp());
		CustomLogger.log("Help Link is present in header.");
				
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getContestDetailLink());
		CustomLogger.log("Contact Details Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getSignInButton());
		CustomLogger.log("SignIn link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getRegisterButton());
		CustomLogger.log("Register Link is present in header.");
		
	//Check Header Links after SignIN
		loginToSearch(randomUser);
		webHeaderPage.search(searchKeyword, false);
		Assert.assertNotNull(webHeaderPage.getWelcomeUserLinkText());
		CustomLogger.log("Welcome to PCH Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getaboutPCHSearchAndWin());
		CustomLogger.log("About PCH Search&Win Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.gethowToSearch());
		CustomLogger.log("How To Search Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.gethelp());
		CustomLogger.log("Help Link is present in header.");
				
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getSignOutLink());
		CustomLogger.log("Sign-Out link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getMyAccountLink());
		CustomLogger.log("My Account Link is present in header.");		
			
	}
	
	/**
	 * 
	 * Validate Footer links
	 * 
	 */
	@Test
	public void testFooterLinks() {
		CustomLogger.log(Environment.getAppName() + " | "
				+ Environment.getEnvironment().toUpperCase()
				+ ": Validate Footer Links");
		
		// Validate presence of About PCH link & link text
		homePage.load();
		
		Assert.assertNotNull(webFooterPage.getRecentWinnersLink());
		CustomLogger.log("Recent Winners Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getDosAndDontsLink());
		CustomLogger.log("Do's and Don't Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getBlogLink());
		CustomLogger.log("Blog Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getFacebookLink());
		CustomLogger.log("Facebook Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getTwitterLink());
		CustomLogger.log("Twitter Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getAboutPchLink());
		CustomLogger.log("About PCH Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getContestDetailsLink());
		CustomLogger.log("Contest Details Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getCustomerServiceLink());	
		CustomLogger.log("Customer Service Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getPrivacyPolicyLink());
		CustomLogger.log("Privacy policy Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getTermsOfUseLink());
		CustomLogger.log("Terms of Use Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getadvertiseWithUsLink());		
		CustomLogger.log("Advertse with us Link is present in footer.");
	
	//Validate Footer part After login And Search
		loginToSearch(randomUser_1);
		
		webHeaderPage.search(searchKeyword, false);
		
		Assert.assertNotNull(webFooterPage.getRecentWinnersLink());
		CustomLogger.log("Recent Winners Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getDosAndDontsLink());
		CustomLogger.log("Do's and Don't Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getBlogLink());
		CustomLogger.log("Blog Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getFacebookLink());
		CustomLogger.log("Facebook Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getTwitterLink());
		CustomLogger.log("Twitter Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getAboutPchLink());
		CustomLogger.log("About PCH Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getContestDetailsLink());
		CustomLogger.log("Contest Details Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getCustomerServiceLink());	
		CustomLogger.log("Customer Service Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getPrivacyPolicyLink());
		CustomLogger.log("Privacy policy Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getTermsOfUseLink());
		CustomLogger.log("Terms of Use Link is present in footer.");
		
		Assert.assertNotNull(webFooterPage.getadvertiseWithUsLink());		
		CustomLogger.log("Advertse with us Link is present in footer.");		
	}
	
	/**
	 * 
	 * Validate Copyright information in the footer
	 * 
	 */
	@Test
	public void testCopyrightContent() {
		/*CustomLogger.log(Environment.getAppName() + " | "
				+ Environment.getEnvironment().toUpperCase()
				+ ": Validate Copyright information in the footer");*/
		homePage.load();
		
		String copyrightText = webFooterPage.getcopyrightText();
		CustomLogger.log("Actual Copyright Content - ");
		CustomLogger.log(copyrightText);
		
		Assert.assertTrue(copyrightText.contains("Copyright © 2004-2017 Publishers Clearing House"));
		Assert.assertTrue(copyrightText.contains("All trademarks and registered trademarks appearing on this site are the property of their respective owners."));
		Assert.assertTrue(copyrightText.contains("Said owners do not endorse nor are they affiliated with Publishers Clearing House or its promotions."));
		CustomLogger.log("Copyright content validation passed");
		
		//Login, Search and Verify the Copyright info
		
		loginToSearch(randomUser_2);
		webHeaderPage.search(searchKeyword, false);
		
		String copyrightTextOnSignPage = webFooterPage.getcopyrightText();
		CustomLogger.log("Actual Copyright Content - ");
		CustomLogger.log(copyrightTextOnSignPage);
		
		Assert.assertTrue(copyrightText.contains("Copyright © 2004-2017 Publishers Clearing House"));
		Assert.assertTrue(copyrightText.contains("All trademarks and registered trademarks appearing on this site are the property of their respective owners."));
		Assert.assertTrue(copyrightText.contains("Said owners do not endorse nor are they affiliated with Publishers Clearing House or its promotions."));
		CustomLogger.log("Copyright content validation passed");
	}
	
	@Test(description = "Test case ID 27390")
	public void verifyBBBlogoForGuestUser() {

		homePage.load();
		homePage.waitTillPageLoads();
		if(homePage.currentURL().contains("http://search."+Environment.getEnvironment()+".pch.com/guidedsearch")){
			gsPage.switchToFrame();
		}
		Assert.assertTrue(webFooterPage.isBBBlogoPresent(),
				"Didn't found BBB Logo in Home page.");

		homePage.waitFor(2);
		// Verifying for BBB Logo in Home page..
		homePage.clickBBBandVerifyTitle(webFooterPage.logoBBB());

		homePage.waitFor(2);

	}

	@Test(description = "Test case ID 27390")
	public void verifyBBBlogoForSignedInUser() {

		homePage.load();
		// Go to Registration page
		webHeaderPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information
		webRegistrationPage.enterUserDetails(randomUser_3);

		// Click Submit button
		webRegistrationPage.clickSubmitButton();
		CustomLogger.log("Registration form submitted.");

		homePage.waitTillPageLoads();

		try{
			CustomLogger.log("Close LevelLigh Box");
			homePage.closeUserLevelLightBox();
		}catch(Exception e){
			CustomLogger.log("Didn't find LevelLigh Box");
		}

		Assert.assertTrue(webFooterPage.isBBBlogoPresent(),
				"Didn't found BBB Logo in Home page.");

		homePage.waitFor(2);
		// Verifying for BBB Logo in Home page..
		homePage.clickBBBandVerifyTitle(webFooterPage.logoBBB());
	}
	
	/**
	 * 
	 * Validate Universal Navigation links for a registered user
	 * 
	 */
	@Test
	public void testUniversalNavLinksRegisteredUser() {
		
		homePage.load();
		Assert.assertNotNull(webHeaderPage.getWelcomeUserLinkText());
		CustomLogger.log("Welcome to PCH Link is present in header.");
		
		// Login and Validate if UniNav is present
		loginToSearch(randomUser_4);
		Assert.assertTrue(webHeaderPage.isUniNavDisplayed());
		webHeaderPage.getAllUniNavLinksAndValidate();
		
		// Search and validate if the UniNav is present
		webHeaderPage.search("abc", false);
		Assert.assertFalse(webHeaderPage.isUniNavDisplayed());
		CustomLogger.log("Universal Navigation is not present on SERP page as expected.");
		
		// Navigate to About S&W page and validate if UniNav is present
		webHeaderPage.aboutPCHSearchAndWin().click();
//		homePage.closeUserLevelLightBox();
		Assert.assertTrue(webHeaderPage.isUniNavDisplayed());
		webHeaderPage.getAllUniNavLinksAndValidate();
		
		// Navigate to How to Search page and validate if UniNav is present
		webHeaderPage.howToSearch().click();
		Assert.assertTrue(webHeaderPage.isUniNavDisplayed());
		webHeaderPage.getAllUniNavLinksAndValidate();
		
		// Navigate to Help page and validate if UniNav is present
		webHeaderPage.help().click();
		Assert.assertTrue(webHeaderPage.isUniNavDisplayed());
		webHeaderPage.getAllUniNavLinksAndValidate();
		
		// Navigate to Recent Winners page and validate if UniNav is present
		webFooterPage.recentWinnersLink().click();
		Assert.assertTrue(webHeaderPage.isUniNavDisplayed());
		webHeaderPage.getAllUniNavLinksAndValidate();
		
		// Navigate to Dos & Donts page and validate if UniNav is present
		webFooterPage.dosAndDontsLink().click();
		Assert.assertTrue(webHeaderPage.isUniNavDisplayed());
		webHeaderPage.getAllUniNavLinksAndValidate();
		
			
	}
	
	/**
	 * 
	 * Validate Universal Navigation links for a Guest user
	 * 
	 */
	@Test
	public void testUniversalNavLinksGuestUser() {
		
		homePage.load();
		Assert.assertNotNull(webHeaderPage.getWelcomeUserLinkText());
		CustomLogger.log("Welcome to PCH Link is present in header.");
		
		// For guest users UniNav is not present 
		Assert.assertFalse(webHeaderPage.isUniNavDisplayed());
		
		// Navigate to About S&W page and validate if UniNav is present
		webHeaderPage.aboutPCHSearchAndWin().click();
		Assert.assertFalse(webHeaderPage.isUniNavDisplayed());
		
	}
	
	@Test(description = "Validating header links URL of Registration page - 16225")
	public void headerLinksValidationOnRegistrationPage(){
		
		homePage.load();
		//Navigate to Registration Page
		webHeaderPage.registerButton().click();
		homePage.waitTillPageLoads();
		
		//Click on Header links in registration page and verifying URL
		CustomLogger.log("Verifying header link URL's of Registration page");
		Assert.assertTrue(webHeaderPage.verifyHeaderLinksURLonRegistrartionPage());
		
	}
	

}