package com.pch.sw.tests.misc;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

//Footer QC TC = 14876, 16222, 16221, 16881, 16148, 16137

public class HeaderFooterValidationTests extends BaseTest {

	private WebHeaderPage webHeaderPage;
	private HomePage homePage;
	RegistrationPage webRegistrationPage;
	User randomUser,randomUser_1,randomUser_2,randomUser_3, randomUser_4;
	
	String searchKeyword = "winter";
	
	@Test(groups={GroupNames.Mobile},testName = "test case = AboutPCHSerch&WinContentPage_Mobile")
	public void testAboutPCHSearchContent(){
		homePage.load();
		
		homePage.verifyAboutSearchWin();
		CustomLogger.log("Verified About PCH Search and WIN URL");
		
		homePage.verify_VideoLinkURL();
		CustomLogger.log("Verified Youtube link URL on AboutPCHSearch&Win Content page");
		
		webHeaderPage.loginToSearch("np03@pchmail.com", "testing");
		Assert.assertTrue(homePage.getCurrentURL().contains("about"));
		CustomLogger.log("Verified Sign In functionality on AboutPCHSearch&Win Content page");
	}
	
	@Test(groups={GroupNames.Mobile},testName = "test case = Verifyiing Mobile Menus")
	public void testMobileMenu(){
		homePage.load();
		
		// Click on About Seawrch&Win and verify if page is redirected.
		homePage.verifyAboutSearchWin();
		CustomLogger.log("Verifying About Search&Win link and its redirection");
		
		// Click on Home and verify if page is redirected
		homePage.verifyHome();
		CustomLogger.log("Verifying Home link and its redirection");
		
		// Verify How to Search item in the menu is navigating to right page.
		homePage.verifyHowToSearch();
		CustomLogger.log("Verifying How to Search link and its redirection");
		
		// Verify Dos and Don'ts item in the menu is navigating to right page.
		homePage.verifyDoAnddonts();
		CustomLogger.log("Verifying Do and Donots link and its redirection");
		
		// Verify About SuperPrize item in the menu is navigating to the right page.
		homePage.verifyAboutSuperPrize();
		CustomLogger.log("Verifying About Super Prize link and its redirection");
		
		// Verify Blog item in the menu is landing on the right page.
		homePage.verifyBlog();
		CustomLogger.log("Verifying Blog link and its redirection");
		
		// Verify About PCH is navigating to the right page.
		homePage.verifyAboutPCH();
		CustomLogger.log("Verifying About PCH link and its redirection");
		
		// Verify Contact Us item in the menu is navigating to the right page.
		homePage.verifyContactUs();
		CustomLogger.log("Verifying Contact US link and its redirection");
		
	}
	
	@Test(groups={GroupNames.Mobile},testName = "test case = Verifyiing Truste and BBB logos")
	public void testFooterContent(){
		homePage.load();
		// Verifying Truste  and BBB logos for unrec user
		Assert.assertNotNull(webHeaderPage.getTrustelogo());
		Assert.assertNotNull(webHeaderPage.getBBBlogo());
		CustomLogger.log("Trueste logo is present for unrec user");
		
		loginToSearch(randomUser_1);
		// Verifying Truste and BBB logos for Recognized user
		Assert.assertNotNull(webHeaderPage.getTrustelogo());
		Assert.assertNotNull(webHeaderPage.getBBBlogo());
		CustomLogger.log("Trueste logo is present for Rec user");
		
		// Verifying Truste and BBB logos for Recognized user on landscape mode
		webHeaderPage.switchToLandscapeMode(620, 420);
		Assert.assertNotNull(webHeaderPage.getTrustelogo());
		Assert.assertNotNull(webHeaderPage.getBBBlogo());
		CustomLogger.log("Trueste logo is present for Rec user in Landscape mode too");
		
	}
	
	@Test(groups={GroupNames.Mobile},testName = "test case = [1]RulesAndFacts_Mobile, 16913[M]")
	public void testHeaderLinksGuestUser(){
		
		homePage.load();
		
		//Validating Header links - official Rules, Sweeps facts, Privacy Policy
		CustomLogger.log("Validating Header links - official Rules, Sweeps facts, Privacy Policy");
		
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
		
		
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getPrivacyPolicyLink());
		CustomLogger.log("Privacy policy Link is present in header.");
		
		Assert.assertTrue(webHeaderPage.verifyHeaderLinksURL(), "Some thing went wrong in header links");
		
		webHeaderPage.search(searchKeyword, false);
		
		//Validating Header links - official Rules, Sweeps facts, Privacy Policy on SERP page
		CustomLogger.log("Validating Header links - official Rules, Sweeps facts, Privacy Policy on SERP page");
		
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
				
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getPrivacyPolicyLink());
		CustomLogger.log("Privacy policy Link is present in header.");
		
		Assert.assertTrue(webHeaderPage.verifyHeaderLinksURL(), "Some thing went wrong in header links");
		
	}
	
	@Test(groups={GroupNames.Mobile}, testName = "test case = [1]RulesAndFacts_Mobile, 16913[M]")
	public void testHeaderLinksAfterSearchRecognisedUser(){
		
		homePage.load();
		loginToSearch(randomUser_1);
		homePage.closeUserLevelLightBox();
		
		//Validating Officila rules links on the top of the page
		CustomLogger.log("Validating Officila rules links on the top of the page");
		
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getPrivacyPolicyLink());
		CustomLogger.log("Privacy policy Link is present in header.");
		
		Assert.assertTrue(webHeaderPage.verifyHeaderLinksURL(), "Some thing went wrong in header links");
				
		webHeaderPage.search(searchKeyword, false);
		
		//Validating Header links - official Rules, Sweeps facts, Privacy Policy
		CustomLogger.log("Validating Header links - official Rules, Sweeps facts, Privacy Policy");
		
		Assert.assertNotNull(webHeaderPage.getofficialRulesLink());
		CustomLogger.log("Official Rules Link is present in header.");
				
		Assert.assertNotNull(webHeaderPage.getSweepStakeFactLink());
		CustomLogger.log("Sweepstakes Facts Link is present in header.");
		
		Assert.assertNotNull(webHeaderPage.getPrivacyPolicyLink());
		CustomLogger.log("Privacy policy Link is present in header.");
		
		Assert.assertTrue(webHeaderPage.verifyHeaderLinksURL(), "Some thing went wrong in header links");
		
	}

}