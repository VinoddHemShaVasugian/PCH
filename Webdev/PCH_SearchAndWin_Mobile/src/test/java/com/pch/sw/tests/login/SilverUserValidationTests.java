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
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.User;

public class SilverUserValidationTests extends BaseTest {

	User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5, randomUser_6;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	CentralServicesPage csPage;
	MyAccountPage myAccountPage;
	PchDotComPage pchComPage;
	AdminBasePage joomlaPage;
	TokenHistoryPage tokenHistoryPage;
	String first_search_token_message = "For Your First Search of the Day!";
	String create_password_token_message = "Creating Password for Account";
	String tokensForCreatePwdMsg = "Creating Password for Account";

	@Test(description = "Test case Id 23350")
	public void testCreatePasswordPromptOnTokenEventMobile() {

		pchComPage.load();
		pchComPage.registerUser(randomUser_1);
		homePage.load();
        headerPage.signOut();
		headerPage.clickSignInBtn();
		headerPage.clickRegisterBtn();

		regPage.enterUserDetails(randomUser_1);
		regPage.clickSubmitButton();
		CustomLogger.log("Completing registration.");
		headerPage.search("shoes", false);
		homePage.closeUserLevelLightBox();
		// Validate Member status, Total tokens Redeem Token
		// and Token History links
		Assert.assertTrue(headerPage.isTokensBadgePresent());
		Assert.assertTrue(headerPage.isUserLevelBadgePresent());
	}

	@Test(description = "Test case Id 23349")
	public void testRegistrationFromRegFoundationWithoutPassword() throws InterruptedException {

		String registrationURL = csPage.createSilverUser(randomUser_2);
		Assert.assertNotNull(registrationURL);
		CustomLogger.log("Reg. URL: " + registrationURL);

		homePage.load(registrationURL);

		headerPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Completing registration.");
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		cpl.enterPasswordandConfirm(randomUser_2.getPassword());
		homePage.closeUserLevelLightBox();
		headerPage.search("shoes", false);

		Assert.assertTrue(headerPage.isTokensBadgePresent());
		Assert.assertTrue(headerPage.isUserLevelBadgePresent());

		homePage.goToTokenHistory();
		System.out.println(tokenHistoryPage.getActivityName());
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(first_search_token_message));
	}

	@Test(description = "Silver User Password Prompt validation")
	public void testUserWithoutPasswordSearch() throws Exception {
		pchComPage.load();
		pchComPage.registerUser(randomUser_3);
		homePage.load();

		Assert.assertTrue(headerPage.tokenCenterCompleteRegBtn().isDisplayed());
		headerPage.search("shoes", false);
		Assert.assertTrue(headerPage.getCurrentURL().contains(".com/search"));
		Assert.assertTrue(headerPage.tokenCenterCompleteRegBtn().isDisplayed());
	}

	@Test(description = "Test case Id 17194")
	public void testCreatePasswordPromptOnTokenEventNegativeMobile() throws InterruptedException {

		pchComPage.load();
		pchComPage.registerUser(randomUser_4);

		homePage.load();
		headerPage.search("shoes", false);

		headerPage.search("Dolls", false);

		headerPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Completing registration.");
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		cpl.enterPasswordandConfirm(randomUser_4.getPassword());
		homePage.closeUserLevelLightBox();

		// Validate Member status, Total tokens Redeem Token
		// and Token History links
		Assert.assertTrue(headerPage.isTokensBadgePresent());
		Assert.assertTrue(headerPage.isUserLevelBadgePresent());
		homePage.goToTokenHistory();
		System.out.println(tokenHistoryPage.getActivityName());
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(first_search_token_message));
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(create_password_token_message));
	}

	@Test(description = "Test case Id 17194")
	public void testSilverUserCreatesPasswordMobile() throws InterruptedException {

		pchComPage.load();
		pchComPage.registerUser(randomUser_5);
		homePage.load();

		headerPage.search("shoes", false);
		CustomLogger.log("Completing registration.");
		headerPage.tokenCenterCompleteRegBtn().click();
		Assert.assertTrue(headerPage.forfeitTokensLink().isDisplayed(), "Forfiet Tokens link is not displayed");
		headerPage.forfeitTokensLink().click();
		Assert.assertTrue(headerPage.tokenCenterCompleteRegBtn().isDisplayed(),
				"Complete Registration link is not displayed");
		headerPage.tokenCenterCompleteRegBtn().click();
		// Click on Official Rules, SweepStake Facts and Privacy Policy link and
		// Validate the page display
		headerPage.verifyHeaderLinksURL();
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		cpl.createPasswordFormSubmitButton().click();
		Common.sleepFor(1000);
		Assert.assertEquals(cpl.passwordErrorMessage().getText(), "Please enter a valid Password.",
				"Incorrect Password Error message is displayed.");
		Assert.assertEquals(cpl.confirmPasswordErrorMessage().getText(), "Please confirm your Password.",
				"Incorrect Confirm Password Error message is displayed.");
		// Enter valid password
		cpl.enterPasswordandConfirm(randomUser_5.getPassword());
		homePage.closeUserLevelLightBox();

		// Validate Member status, Total tokens Redeem Token
		// and Token History links
		Assert.assertTrue(headerPage.isTokensBadgePresent());
		Assert.assertTrue(headerPage.isUserLevelBadgePresent());
		homePage.goToTokenHistory();
		System.out.println(tokenHistoryPage.getActivityName());
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(first_search_token_message));
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(create_password_token_message));
	}

	@Test(description = "Verify the tokens by creating paswword via the My Account page", testName = "16014: AwardTokensAfterUserCreatesPassword_Mobile")
	public void testCreatePasswordFromMyAccount() throws InterruptedException {

		// Login to Joomla to get create password tokens
//		joomlaPage.goToArticle("Tokens / User Creates Password");
		String create_pwd_token_value = "1000";
//		joomlaPage.setTextProperty("Tokens", create_pwd_token_value);
		pchComPage.load();
		pchComPage.registerUser(randomUser_6);
		homePage.load();
		homePage.goToMyAccountPage();
		CustomLogger.log("Completing registration.");
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		cpl.enterPasswordandConfirm(randomUser_6.getPassword());
		homePage.refreshPage();
		homePage.closeUserLevelLightBox();

		// Validate Member status, Total tokens Redeem Token
		// and Token History links
		Assert.assertTrue(headerPage.isTokensBadgePresent());
		Assert.assertTrue(headerPage.isUserLevelBadgePresent());
		homePage.load();
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(create_password_token_message));
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(create_password_token_message), create_pwd_token_value,
				"Complete Password token value is mismatched between Joomla and Application.");
	}
}
