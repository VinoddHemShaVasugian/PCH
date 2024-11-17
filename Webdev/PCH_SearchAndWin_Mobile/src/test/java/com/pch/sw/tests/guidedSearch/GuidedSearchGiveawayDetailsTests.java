package com.pch.sw.tests.guidedSearch;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class GuidedSearchGiveawayDetailsTests extends BaseTest {
	
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	GuidedSearchPage gsPage;
	User randomUser_1;
	
	
	@Test(groups={GroupNames.Mobile},  description="Un-recognized users GS PCH Giveaway details validation tests - 29931.")
	public void gsGiveaway_UnrecUser(){
		
		//Validating banner for Enabled Scenario
		CustomLogger.log("############### Validating PCH Giveaway details for UnRecognised users ###############");
		CustomLogger.log("As banner Enabled - Validating the presence of PCH Giveaway Details for Unrecognised user. . .");
		homePage.load();
		CustomLogger.log("Validating Giveaway Details for default Home-Page");
		Assert.assertTrue(gsPage.isGiveawayExists(), "We are not seeing PCH Giveaway Details on S&W page. . .");
		gsPage.getTitleOfGiveawayUrl("Giveaway Details");
		
		/*//validating pch for particular GS 
		CustomLogger.log("Validating Giveaway Details for for GS - AUTOMATION-L1");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		Assert.assertTrue(gsPage.isGiveawayExists(), "We are not seeing PCH Giveaway Details on S&W page. . .");
		
		//Validating PCH Giveaway Details for Disabled Scenario
		CustomLogger.log("Validating the presence of PCH Giveaway Details for Unrecognised user. . .for banner-Disabled Scenarios. . .");
		homePage.loadGuidedSearch("AUTOMATION-L1-Logo-UnrecUser");
		Assert.assertFalse(gsPage.isGiveawayExists(), "We are seeing PCH Giveaway Details on S&W page - we shouldn't see as we disabled banner on admin");
		
		//validating Giveaway URL
		CustomLogger.log("Validating Giveaway Details URL");
		homePage.loadGuidedSearch("AUTOMATION-L1-InValid-OpenX-UnrecUser");
		gsPage.getTitleOfGiveawayUrl("Facebook");
		
		//verify Default Giveaway URL
		CustomLogger.log("Validating default Giveaway Details URL");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gsPage.getTitleOfGiveawayUrl("Giveaway Details");*/
		
		CustomLogger.log("################################################################################");
		
	}
	
	@Test(groups={GroupNames.Mobile},  description="recognized users GS PCH Giveaway details validation tests - 29931.")
	public void gsGiveaway_RecUser(){
		
		//validating Giveaway Details for Enable scenario
		CustomLogger.log("############### Validating PCH Giveaway details for Recognised users ###############");
		CustomLogger.log("Validating default Banner for recognised user. . .");
		homePage.load();
		headerPage.loginToSearch("vkatam0001@pchmail.com", "testing");
		Assert.assertTrue(gsPage.isBannerExists(), "We are not seeing PCH Giveaway Details on S&W page. . .");
		gsPage.getTitleOfGiveawayUrl("Giveaway Details");
		
		/*//Validating Giveaway Details for Disabled Scenario
		CustomLogger.log("Validating the presence of Giveaway Details for recognised user. . .for banner-Disabled Scenarios. . .");
		homePage.loadGuidedSearch("AUTOMATION-L1-Logo-RecUser");
		Assert.assertFalse(gsPage.isGiveawayExists(), "We are seeing Giveaway Details on S&W page - we shouldn't see as we disabled banner on admin");
		
		//validating Giveaway Url
		CustomLogger.log("Validating Giveaway Details URL");
		homePage.loadGuidedSearch("AUTOMATION-L1-Invalid_openX-RecUser");
		gsPage.getTitleOfGiveawayUrl("Facebook");
		
		//verify Default Giveaway URL
		CustomLogger.log("Validating Giveaway Details Default URL");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gsPage.getTitleOfGiveawayUrl("Giveaway Details");
		*/
		CustomLogger.log("################################################################################");	
	}
	
}
