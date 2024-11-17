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

public class GuidedSearchBannerTests extends BaseTest {
	
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	GuidedSearchPage gsPage;
	User randomUser_1;
	
	
	@Test(groups={GroupNames.Mobile}, description="Un-recognized users GS Banner validation tests - 29930.")
	public void gsBanner_UnrecUser(){
		
		/*
		 * 1. Disable
		 * 2. Enable
		 * 3. click
		 * 4. URL
		 * 5. invalid open-X id
		*/
		
		//Validating banner for Enabled Scenario
		CustomLogger.log("############### Validating Banner for UnRecognised users ###############");
		CustomLogger.log("As banner Enabled - Validating the presence of Banner for Unrecognised user. . .");
		homePage.load();
		CustomLogger.log("Validating banner for default Home-Page");
		Assert.assertTrue(gsPage.isBannerExists(), "We are not seeing Banner on S&W page. . .");
		gsPage.getTitleOfBannerUrl("GuidedSearch");
		
	/*	//validating Banner for particular GS 
		CustomLogger.log("Validating banner for for GS - AUTOMATION-L1");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		Assert.assertTrue(gsPage.isBannerExists(), "We are not seeing Banner on S&W page. . .");
		
		//Validating banner for Disabled Scenario
		CustomLogger.log("Validating the presence of Banner for Unrecognised user. . .for banner-Disabled Scenarios. . .");
		homePage.loadGuidedSearch("AUTOMATION-L1-Logo-UnrecUser");
		Assert.assertFalse(gsPage.isBannerExists(), "We are seeing banner on S&W page - we shouldn't see as we disabled banner on admin");
		
		//validating Invalid open-X
		CustomLogger.log("Validating for invalid openX");
		homePage.loadGuidedSearch("AUTOMATION-L1-InValid-OpenX-UnrecUser");
		//Assert.assertFalse(gsPage.isBannerExists(), "We are seeing banner on S&W page - we shouldn't see as we come up with invalid openX id");
		
		//validating open_X Url
		CustomLogger.log("Validating openX URL");
		homePage.loadGuidedSearch("AUTOMATION-L1-InValid-OpenX-UnrecUser");
		gsPage.getTitleOfBannerUrl("Gmail");
		
		//verify Default Open-X URL
		CustomLogger.log("Validating openX URL");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gsPage.getTitleOfBannerUrl("GuidedSearch");*/
		
		CustomLogger.log("######################################################################");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="recognized users GS Banner validation tests - 29930.")
	public void gsBanner_RecUser(){
		
		/*
		 * 1. Disable
		 * 2. Enable
		 * 3. click
		 * 4. URL
		 * 5. invalid open-X id
		*/
		
		//validating Banner for Enable scenario
		CustomLogger.log("############### Validating Banner for Recognised users ###############");
		CustomLogger.log("Validating default Banner for recognised user. . .");
		homePage.load();
		headerPage.loginToSearch("vkatam0001@pchmail.com", "testing");
		Assert.assertTrue(gsPage.isBannerExists(), "We are not seeing Banner on S&W page. . .");
		gsPage.getTitleOfBannerUrl("GuidedSearch");
		
		/*//Validating banner for Disabled Scenario
		CustomLogger.log("Validating the presence of Banner for recognised user. . .for banner-Disabled Scenarios. . .");
		homePage.loadGuidedSearch("AUTOMATION-L1-Logo-RecUser");
		Assert.assertFalse(gsPage.isBannerExists(), "We are seeing banner on S&W page - we shouldn't see as we disabled banner on admin");
		
		//validating Invalid open-X
		CustomLogger.log("Validating for invalid openX");
		homePage.loadGuidedSearch("AUTOMATION-L1-Invalid_openX-RecUser");
		//Assert.assertFalse(gsPage.isBannerExists(), "We are seeing banner on S&W page - we shouldn't see as we come up with invalid openX id");
		
		//validating open_X Url
		CustomLogger.log("Validating openX URL");
		homePage.loadGuidedSearch("AUTOMATION-L1-Invalid_openX-RecUser");
		gsPage.getTitleOfBannerUrl("Gmail");
		
		//verify Default Open-X URL
		CustomLogger.log("Validating openX Default URL");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gsPage.getTitleOfBannerUrl("GuidedSearch");
		 */		
		CustomLogger.log("######################################################################");	
	}
	
	
	
}
