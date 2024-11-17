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
	
	@Test(groups={GroupNames.Desktop}, description="Verify GS_Backgound image for Recognised user - 29924")
	public void unRecBGimage(){
		
		//String gsURL = "http://search."+Environment.getEnvironment()+".pch.com/guidedsearch";
		CustomLogger.log("############### Validating Background image for UnRecognised users ###############");
		homePage.load();
		CustomLogger.log("Validating Default open_X");
		//Assert.assertFalse(gsPage.validateBackGroundimage("70e0fe61a254d85be5278ce1a0876ff2"), "We are not seeing expected Background image. . .");
		Assert.assertTrue(gsPage.banner().isDisplayed(), "Wea re not seeing OpenX");
		CustomLogger.log("We are seeing expected openx. . .");
		Assert.assertTrue(gsPage.giveAwayDetails().isDisplayed(), "Wea re not seeing Give Away Details");
		CustomLogger.log("We are seeing Gve Away details. . .");
		
		/*//select a background image and enter valid openx ID and verify Background image
		homePage.loadGuidedSearch("SWGSL2_Bimg_Openx");
		CustomLogger.log("Validating open_X for selected background image");
		Assert.assertFalse(gsPage.validateBackGroundimage("70e0fe61a254d85be5278ce1a0876ff2"), "We are seeing Background image. . .");
		CustomLogger.log("We are seeing default Backgroud image as of open_X ID . . .");
		
		//select a background image and make openx ID as blank and verify Background image 
		homePage.loadGuidedSearch("SWGSL2_BackGrounImage");
		CustomLogger.log("Validating open_X for selected background image");
		Assert.assertTrue(gsPage.validateBackGroundimage("70e0fe61a254d85be5278ce1a0876ff2"), "We are not seeing expected Background image. . .");
		CustomLogger.log("We are seeing selected Backgroud image as open_X. . .");*/
		
		CustomLogger.log("##################################################################################");
	}
	
	@Test(groups={GroupNames.Desktop}, description="Verify GS_Backgound image for Recognised user - 29924")
	public void recBGimage(){
		
		//String gsURL = "http://search."+Environment.getEnvironment()+".pch.com/guidedsearch";
		CustomLogger.log("################ Validating Background image for Recognised users ################");
		homePage.load();
		headerPage.loginToSearch("np02@pchmail.com", "testing");
		CustomLogger.log("Validating Default open_X");
		//Assert.assertFalse(gsPage.validateBackGroundimage("70e0fe61a254d85be5278ce1a0876ff2"), "We are not seeing expected Background image. . .");
		Assert.assertTrue(gsPage.banner().isDisplayed(), "Wea re not seeing OpenX");
		CustomLogger.log("We are seeing expected openx. . .");
		Assert.assertTrue(gsPage.giveAwayDetails().isDisplayed(), "Wea re not seeing Give Away Details");
		CustomLogger.log("We are seeing Gve Away details. . .");
		
		/*//select a background image and enter valid openx ID and verify Background image
		homePage.loadGuidedSearch("SWGSL2_Bimg_Openx");
		CustomLogger.log("Validating open_X for selected background image - with open-X ID enabled");
		Assert.assertFalse(gsPage.validateBackGroundimage("70e0fe61a254d85be5278ce1a0876ff2"), "We are seeing Background image. . .");
		CustomLogger.log("We are seeing default Backgroud image as of open_X ID . . .");
		
		//select a background image and make openx ID as blank and verify Background image 
		homePage.loadGuidedSearch("SWGSL2_BackGrounImage");
		CustomLogger.log("Validating open_X for selected background image");
		Assert.assertTrue(gsPage.validateBackGroundimage("70e0fe61a254d85be5278ce1a0876ff2"), "We are not seeing expected Background image. . .");
		CustomLogger.log("We are seeing selected Backgroud image as open_X. . .");*/
		
		CustomLogger.log("##################################################################################");
	}

}
