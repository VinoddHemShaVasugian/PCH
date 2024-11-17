package com.pch.sw.tests.login;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class MiniRegUserValidationTests extends BaseTest {

	private HomePage webBasePage;
	private WebHeaderPage webHeaderPage;
	private RegistrationPage webRegistrationPage;
	private CentralServicesPage csPage;
	private SearchResultsPage searchResultPage;
	String complete_reg_token_message = "User Registration on PCHSearch&Win";
	private User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5, randomUser_6;

	HomePage homePage;
	String keyword = "Pen";
	WebHeaderPage headerPage;
	TokenHistoryPage tokenHistoryPage;

	String toastMessage;
	int tokensClaimed, tokensDisplayedInToastMessage, tokensEarnedInLatestActivity;
	String PCHLeaderMessage = "Click Here NOW!";

	/**
	 * 
	 * Minireg User complete registration test
	 * 
	 */
	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "B-22350: FBMiniRegUser_NewUser_Mobile")
	public void testMiniRegUserCompleteRegistration() {
		CustomLogger.log(Environment.getAppName() + " | " + Environment.getEnvironment().toUpperCase()
				+ ": Minireg User complete registration test");

		loginToSearchAsMiniregUser(randomUser_1);
		Assert.assertTrue(webHeaderPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");
		webHeaderPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Landed on Registration page.");

		webRegistrationPage.enterMiniregUserDetails(randomUser_1);
		webRegistrationPage.clickSubmitButton();

		webBasePage.closeUserLevelLightBox();

		Assert.assertFalse(webHeaderPage.isCompleteRegBtnExists(), "Complete registration button exists on the page");
	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "Test case Id= 23343")
	public void testUnrecognized_MiniRegUser_registration() {
		/*
		 * Here we are creating MiniregUser 'user1' via
		 * "REGISTRATION FOUNDATION" and we are trying to register with the same
		 * user 'user1' and expecting an error message in Registration page
		 */
		csPage.createMiniRegUser(randomUser_3);
		webBasePage.load();
		webHeaderPage.clickSignInBtn();
		webHeaderPage.clickRegisterBtn();
		String password = randomUser_3.getPassword();
		System.out.println(password);
		// Change the password
		randomUser_3.setPassword(randomUser_3.getPassword() + "X");
		webRegistrationPage.enterUserDetails(randomUser_3);
		webRegistrationPage.submit().click();
		String errorMessage = webRegistrationPage.signInErrorMessage();
		Assert.assertTrue(errorMessage.contains("email already exists"), errorMessage);

		// Step 5. Click on Sign in.
		webBasePage.load();
		webHeaderPage.loginToSearch(randomUser_3.getEmail(), password);
		Assert.assertTrue(webHeaderPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");

	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "Test case Id= 23347")
	public void testRecognignized_MiniRegUser_Search_NoRegistration() {
		String registrationURL = csPage.createMiniRegUser(randomUser_5);
		webBasePage.load(registrationURL);
		Assert.assertTrue(webHeaderPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");

		webHeaderPage.search("shoes", false);
		int searchResults = searchResultPage.getWebSearchResultCount();
		Assert.assertTrue(searchResults > 0);
	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "Test case Id= 23347")
	public void testRecognignized_MiniRegUser_Search_Register() throws InterruptedException {
		String registrationURL = csPage.createMiniRegUser(randomUser_6);
		webBasePage.load(registrationURL);
		Assert.assertTrue(webHeaderPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");

		webHeaderPage.search("shoes", false);

		Assert.assertTrue(webBasePage.getCurrentURL().contains(".com/search"));

		webHeaderPage.tokenCenterCompleteRegBtn().click();
		webHeaderPage.loginToSearchWithPreFilledEmail("testing");
		CustomLogger.log("Landed on Registration page.");

		webRegistrationPage.enterMiniregUserDetails(randomUser_6);
		webRegistrationPage.clickSubmitButton();

		webBasePage.closeUserLevelLightBox();

		Assert.assertFalse(webHeaderPage.isCompleteRegBtnExists(), "Complete registration button exists on the page");
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(complete_reg_token_message));
	}

	@Test(groups={GroupNames.Regression,GroupNames.Mobile},description = "Test case Id =23346")
	public void testRecognized_MiniRegUser_registration() {
		String registrationURL = csPage.createMiniRegUser(randomUser_4);
		webBasePage.load(registrationURL);
		Assert.assertTrue(webHeaderPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");

		webHeaderPage.tokenCenterCompleteRegBtn().click();
		webHeaderPage.loginToSearch("", "testing");
		CustomLogger.log("Landed on Registration page.");

		webRegistrationPage.enterMiniregUserDetails(randomUser_4);
		webRegistrationPage.clickSubmitButton();

		webBasePage.closeUserLevelLightBox();

		// LatestActivity string above would be of the form - "You've
		// earned1,000 Tokens!For Completing, Registration!"
		// Will parse the activity name from the latest activity, so from above
		// string we will parse
		// "For Completing, Registration!"

		Assert.assertFalse(webHeaderPage.isCompleteRegBtnExists(), "Complete registration button exists on the page");

	}

	/**
	 * 
	 * Minireg User registration page validation tests
	 * 
	 * @throws SQLException
	 * 
	 */
	@Test
	public void testMiniRegUserRegistrationPageValidations() {
		CustomLogger.log(Environment.getAppName() + " | " + Environment.getEnvironment().toUpperCase()
				+ ": Minireg User registration page validation tests");

		// Create Minireg User
		csPage.createMiniRegUser(randomUser_2);

		// Open Search & login using the minireg user credentials
		// Below line is NOW handled in signIn method of webBasePage so
		// commenting.
		webBasePage.load();
		webHeaderPage.loginToSearch(randomUser_2);

		// Click on Complete Registration button to land on Reg page
		webHeaderPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Landed on Registration page.");

		// Fill up the registration form with all information & Don't select
		// Title
		webRegistrationPage.enterMiniregUserDetails(randomUser_2);
		webRegistrationPage.selectTitle().selectByIndex(0);
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter
		// First Name
		webRegistrationPage.selectTitle().selectByIndex(1);
		webRegistrationPage.firstName().clear();
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter Last
		// Name
		webRegistrationPage.firstName().sendKeys(randomUser_2.getFirstname());
		webRegistrationPage.lastName().clear();
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter
		// Street Address
		webRegistrationPage.lastName().sendKeys(randomUser_2.getLastname());
		webRegistrationPage.streetAddress().clear();
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter City
		webRegistrationPage.streetAddress().sendKeys(randomUser_2.getStreet());
		webRegistrationPage.city().clear();
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter
		// State
		webRegistrationPage.city().sendKeys(randomUser_2.getCity());
		webRegistrationPage.stateListBox().selectByIndex(0);
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter Zip
		webRegistrationPage.stateListBox().selectByVisibleText(randomUser_2.getState());
		webRegistrationPage.zip().clear();
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter 'Day
		// and Year' for Date of Birth
		webRegistrationPage.zip().sendKeys(randomUser_2.getZip());
		webRegistrationPage.dobMonthListBox().selectByIndex(1);
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't enter
		// 'Year' for Date of Birth
		webRegistrationPage.dobMonthListBox().selectByIndex(1);
		webRegistrationPage.dobDayListBox().selectByIndex(1);
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");

		// Fill up the registration form with all information & Don't select
		// 'Month' for Date of Birth
		webRegistrationPage.dobMonthListBox().selectByIndex(0);
		webRegistrationPage.dobYearListBox().selectByVisibleText("1980");
		webRegistrationPage.submit().click();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.mobile_reg_form_error_msg().getText(),
				"Please fill in the following fields:", "Displayed invalid error message");
	}
}