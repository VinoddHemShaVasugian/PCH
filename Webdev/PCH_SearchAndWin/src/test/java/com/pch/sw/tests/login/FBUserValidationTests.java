package com.pch.sw.tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.User;

public class FBUserValidationTests extends BaseTest {
	User randomUser_1,randomUser_2, randomUser_3;
	CentralServicesPage csPage;
	HomePage webBasePage;
	WebHeaderPage webHeaderPage;
	RegistrationPage webRegistrationPage;
	AdminBasePage joomlaPage;
	
	@Test(description="Test case Id= 23344")
	public void testUnrecognized_FBUser_registration(){
		csPage.createFBUser(randomUser_1);
		webBasePage.load();
		webHeaderPage.clickRegisterBtn();
		//Change the password
		randomUser_1.setPassword(randomUser_1.getPassword()+"X");
		webRegistrationPage.enterUserDetails(randomUser_1);
		webRegistrationPage.clickSubmitButton();
		
		webBasePage.closeUserLevelLightBox();
		
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(welcomeText.startsWith("Welcome "+randomUser_1.getFirstname()+" "+randomUser_1.getLastname()),welcomeText);

		String latestActivity=webHeaderPage.getLatestActivityFromStatus();				 
		String latestActivityName = Common.subString(latestActivity,Common.REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY);				
		Assert.assertEquals(latestActivityName, "For Completing Registration!");
		String tokensAtRegistration = Common.subString(latestActivity,Common.REGEX_PARSING_TOKENS_FROM_ACTIVITY);
		Assert.assertEquals(tokensAtRegistration,"1000");		
	}
	
	@Test(description="Test case Id= 23343")
	public void testRecognized_FBUser_registration(){
		String registrationURL=csPage.createFBUser(randomUser_2);
		webBasePage.load(registrationURL);
		
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(welcomeText.contains(randomUser_2.getEmail().toLowerCase()),welcomeText);
		
		webHeaderPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Landed on Registration page.");
		
		Assert.assertTrue(webRegistrationPage.isFieldDisabled("Email"));
		webRegistrationPage.enterFBUserDetails(randomUser_2);
		webRegistrationPage.clickSubmitButton();
		
		webBasePage.closeUserLevelLightBox();
		
		welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(welcomeText.startsWith("Welcome "+randomUser_2.getFirstname()+" "+randomUser_2.getLastname()),welcomeText);

		String latestActivity=webHeaderPage.getLatestActivityFromStatus();				 
		String latestActivityName = Common.subString(latestActivity,Common.REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY);				
		Assert.assertEquals(latestActivityName, "For Completing Registration!");
		String tokensAtRegistration = Common.subString(latestActivity,Common.REGEX_PARSING_TOKENS_FROM_ACTIVITY);
		Assert.assertEquals(tokensAtRegistration,"1000");
	}
	
}
