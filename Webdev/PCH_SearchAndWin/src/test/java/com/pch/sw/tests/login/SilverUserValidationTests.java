package com.pch.sw.tests.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.CreatePasswordLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.MyAccountPage;
import com.pch.search.pages.web.PchDotComPage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.pages.web.MyAccountPage.MyAccountTab;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.User;

public class SilverUserValidationTests extends BaseTest {

	User randomUser_1, randomUser_2, randomUser_3, randomUser_4;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	CentralServicesPage csPage;
	MyAccountPage myAccountPage;
	PchDotComPage pchComPage;
	AdminBasePage joomlaPage;
	TokenHistoryPage tokenHistoryPage;

	String tokensForCreatePwdMsg = "Creating Password for Account";

	@Test(description = "Test case Id 22896")
	public void testCompleteRegistrationFromPCHCOMWithoutPassword() {
		pchComPage.load();
		pchComPage.registerUser(randomUser_1);

		/*
		 * homePage.load();
		 * 
		 * try{ CustomLogger.log("Closed Option ligh box"); }catch(Exception e){
		 * CustomLogger.log("Didn't find optin light box"); }
		 * 
		 * headerPage.tokenCenterCompleteRegBtn().click(); CustomLogger.log(
		 * "Completing registration."); CreatePasswordLightBox cpl =
		 * homePage.createPasswordLightBox();
		 * cpl.enterPasswordandConfirm(randomUser_1.getPassword());
		 * cpl.submit(); homePage.closeUserLevelLightBox();
		 * 
		 * 
		 * //Validate Member status, Total tokens Redeem Token //and Token
		 * History links
		 * 
		 * Assert.assertTrue(headerPage.isRedeemTokensLinkPresent());
		 * Assert.assertTrue(headerPage.isTokenHistoryLinkPresent());
		 * 
		 * Assert.assertTrue(headerPage.getTokenCount()>0);
		 * Assert.assertEquals(headerPage.getUserStatus().toUpperCase(),"BRONZE"
		 * );
		 * 
		 * String latestActivity=headerPage.getLatestActivityFromStatus();
		 * String tokensAtRegistration = Common.subString(latestActivity,Common.
		 * REGEX_PARSING_TOKENS_FROM_ACTIVITY);
		 * Assert.assertEquals(tokensAtRegistration,"1000");
		 */
	}

	@Test(description = "Test case Id 23350")
	public void testRegistrationFromPCHCOMWithoutPassword() {
		pchComPage.load();
		pchComPage.registerUser(randomUser_1);

		homePage.load();

		headerPage.signOut();
		headerPage.clickRegisterBtn();

		regPage.enterUserDetails(randomUser_1);
		regPage.clickSubmitButton();
		headerPage.search("PCH", false);
		homePage.load();
		homePage.closeUserLevelLightBox();

		CustomLogger.log("Completing registration.");

		// Validate Member status, Total tokens Redeem Token
		// and Token History links

		Assert.assertTrue(headerPage.isRedeemTokensLinkPresent());
		Assert.assertTrue(headerPage.isTokenHistoryLinkPresent());

		Assert.assertTrue(headerPage.getTokenCount() >= 0);
		Assert.assertEquals(headerPage.getUserStatus().toUpperCase(), "SILVER");

	}

	@Test(description = "Test case Id 23349")
	public void testRegistrationFromRegFoundationWithoutPassword() {
		// String
		// registrationURL=csPage.createFullRegUserWithoutPassword(randomUser_2);

		/*
		 * vamsi Nov 26 2015 Using "registration/setmember" instead of
		 * registration/registermember
		 */
		String registrationURL = csPage.createSilverUser(randomUser_2);
		Assert.assertNotNull(registrationURL);
		System.out.println(registrationURL);

		homePage.load(registrationURL);
		// homePage.closeWelcomeToFPLightBoxAfterWait();

		headerPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Completing registration.");
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		cpl.enterPasswordandConfirm(randomUser_2.getPassword());
		homePage.closeUserLevelLightBox();
		String latestActivity = headerPage.getLatestActivityFromStatus();
		String tokensAtRegistration = Common.subString(latestActivity, Common.REGEX_PARSING_TOKENS_FROM_ACTIVITY);
		Assert.assertEquals(tokensAtRegistration, "1000");
	}

	@Test(description = "Silver User Password Prompt validation")
	public void testUserWithoutPasswordSearch() throws Exception {
		pchComPage.load();
		pchComPage.registerUser(randomUser_3);
		homePage.load();

		String welcomeText = headerPage.getWelcomeUserLinkText();
		Assert.assertTrue(
				welcomeText.startsWith("Welcome " + randomUser_3.getFirstname() + " " + randomUser_3.getLastname()),
				welcomeText);

		/*
		 * String welcomeText = headerPage.getWelcomeUserLinkText();
		 * //Assert.assertTrue(welcomeText.startsWith("Welcome "
		 * +randomUser_1.getFirstname()+" "
		 * +randomUser_1.getLastname()),welcomeText);
		 * Assert.assertTrue(welcomeText.contains(randomUser_3.getEmail().
		 * toLowerCase()),welcomeText);
		 */
		Assert.assertTrue(headerPage.tokenCenterCompleteRegBtn().isDisplayed());
		headerPage.search("PCH", false);
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		Assert.assertTrue(cpl.isLightBoxPresent());
		cpl.enterPasswordandConfirm(randomUser_3.getPassword());
		homePage.closeUserLevelLightBox();

		// Validate Member status, Total tokens Redeem Token
		// and Token History links
		Assert.assertTrue(headerPage.isRedeemTokensLinkPresent());
		Assert.assertTrue(headerPage.isTokenHistoryLinkPresent());

		Assert.assertTrue(headerPage.getTokenCount() > 0);
		Assert.assertEquals(headerPage.getUserStatus().toUpperCase(), "SILVER");

		// Go to tokens history page and validate the message
		homePage.goToMyAccountPage();
		myAccountPage.navigateToTab(MyAccountTab.Token_History);

		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(tokensForCreatePwdMsg));
	}

}
