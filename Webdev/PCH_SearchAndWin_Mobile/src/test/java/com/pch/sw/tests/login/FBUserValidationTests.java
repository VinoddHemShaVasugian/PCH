package com.pch.sw.tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class FBUserValidationTests extends BaseTest {
	User randomUser_1,randomUser_2, randomUser_3;
	CentralServicesPage csPage;
	HomePage homePage;
	WebHeaderPage webHeaderPage;
	RegistrationPage webRegistrationPage;
	AdminBasePage joomlaPage;
	TokenHistoryPage tokenHistoryPage;
	
	@Test(groups={GroupNames.Mobile}, description="Test case Id= 23344")
	public void testUnrecognized_FBUser_registration() throws InterruptedException{
		csPage.createFBUser(randomUser_1);
		homePage.load();
		loginToSearch(randomUser_1);

		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains("User Registration on PCHSearch&Win"));	
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="Test case Id= 23343")
	public void testRecognized_FBUser_registration() throws InterruptedException{
		String registrationURL=csPage.createFBUser(randomUser_2);
		homePage.load(registrationURL);
		
		webHeaderPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Landed on Registration page.");
		
		Assert.assertTrue(webRegistrationPage.isFieldDisabled("Email"));
		webRegistrationPage.enterFBUserDetails(randomUser_2);
		webRegistrationPage.clickSubmitButton();
		
		homePage.closeUserLevelLightBox();
		
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains("User Registration on PCHSearch&Win"));
	}
	
}
