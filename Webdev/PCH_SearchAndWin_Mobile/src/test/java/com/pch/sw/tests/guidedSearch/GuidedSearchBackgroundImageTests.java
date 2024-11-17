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

public class GuidedSearchBackgroundImageTests extends BaseTest {
	
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	GuidedSearchPage gsPage;
	User randomUser_1;
	
	@Test(groups={GroupNames.Mobile}, description="Verify GS_Backgound image for Recognised user - 29924")
	public void unRecBGimage(){
		
		CustomLogger.log("############### Validating Background image for UnRecognised users ###############");
		homePage.load();
		CustomLogger.log("Validating Default open_X");
		Assert.assertTrue(gsPage.banner().isDisplayed(), "Wea re not seeing OpenX");
		CustomLogger.log("We are seeing expected openx. . .");
		Assert.assertTrue(gsPage.giveAwayDetails().isDisplayed(), "Wea re not seeing Give Away Details");
		CustomLogger.log("We are seeing Gve Away details. . .");
		
		/*//select a background image and enter valid openx ID and verify Background image
		homePage.loadGuidedSearch("Automation-L1_Background_Unrec_Openx_Enabled");
		CustomLogger.log("Validating open_X for selected background image");
		Assert.assertFalse(gsPage.validateBackGroundimage("ac1ad40782127145144f6245dc0f6d30"), "We are seeing Background image. . .");
		CustomLogger.log("We are seeing default Backgroud image as of open_X ID . . .");
		
		//select a background image and make openx ID as blank and verify Background image 
		homePage.loadGuidedSearch("Automation-L1_Background");
		CustomLogger.log("Validating open_X for selected background image");
		Assert.assertTrue(gsPage.validateBackGroundimage("ac1ad40782127145144f6245dc0f6d30"), "We are not seeing expected Background image. . .");
		CustomLogger.log("We are seeing selected Backgroud image as open_X. . .");*/
		
		CustomLogger.log("##################################################################################");
	}
	
	@Test(groups={GroupNames.Mobile}, description="Verify GS_Backgound image for Recognised user - 29924")
	public void recBGimage(){
		
		CustomLogger.log("################ Validating Background image for Recognised users ################");
		homePage.load();
		headerPage.loginToSearch("vkatam0001@pchmail.com", "testing");
		CustomLogger.log("Validating Default open_X");Assert.assertTrue(gsPage.banner().isDisplayed(), "Wea re not seeing OpenX");
		CustomLogger.log("We are seeing expected openx. . .");
		Assert.assertTrue(gsPage.giveAwayDetails().isDisplayed(), "Wea re not seeing Give Away Details");
		CustomLogger.log("We are seeing Gve Away details. . .");
		/*
		//select a background image and enter valid openx ID and verify Background image
		homePage.loadGuidedSearch("Automation-L1_Background_rec");
		CustomLogger.log("Validating open_X for selected background image - with open-X ID enabled");
		Assert.assertFalse(gsPage.validateBackGroundimage("ac1ad40782127145144f6245dc0f6d30"), "We are seeing Background image. . .");
		CustomLogger.log("We are seeing default Backgroud image as of open_X ID . . .");
		
		//select a background image and make openx ID as blank and verify Background image 
		homePage.loadGuidedSearch("Automation-L1_Background_rec_Openx_Disabled");
		CustomLogger.log("Validating open_X for selected background image");
		Assert.assertTrue(gsPage.validateBackGroundimage("ac1ad40782127145144f6245dc0f6d30"), "We are not seeing expected Background image. . .");
		CustomLogger.log("We are seeing selected Backgroud image as open_X. . .");
		*/
		CustomLogger.log("##################################################################################");
	}

}
