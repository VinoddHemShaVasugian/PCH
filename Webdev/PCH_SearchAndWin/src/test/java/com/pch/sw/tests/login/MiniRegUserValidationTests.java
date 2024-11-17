package com.pch.sw.tests.login;

import java.sql.SQLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.lightBox.OptinLightBox;
import com.pch.search.pages.lightBox.RegStepAwayLightBox;
import com.pch.search.pages.lightBox.SignInLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class MiniRegUserValidationTests extends BaseTest {

	private TokenHistoryPage tokenHistoryPage;
	private HomePage webBasePage;
	private WebHeaderPage webHeaderPage;
	private RegistrationPage webRegistrationPage;
	private CentralServicesPage csPage;
	private SearchResultsPage searchResultPage;
	private OptinLightBox optinLB;
	private String timeZone = "America/New_York";

	/*
	 * private String email= Common.generateRandomID("QaAuto") + "@pchmail.com";
	 * private String email2= Common.generateRandomID("QaAuto") +
	 * "@pchmail.com"; private String email3= Common.generateRandomID("QaAuto")
	 * + "@pchmail.com"; private String firstName=
	 * Common.getRandomUserName("FN-"); private String lastName=
	 * Common.getRandomUserName("LN-");
	 */
	private User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5, randomUser_6, randomUser_8;

	HomePage homePage;
	String keyword = "Pen";
	WebHeaderPage headerPage;
	String toastMessage;
	int tokensClaimed, tokensDisplayedInToastMessage, tokensEarnedInLatestActivity;
	String PCHLeaderMessage = "Click Here NOW!";

	/**
	 * 
	 * Minireg User complete registration test
	 * 
	 */
	@Test
	public void testMiniRegUserCompleteRegistration() {
		CustomLogger.log(Environment.getAppName() + " | " + Environment.getEnvironment().toUpperCase()
				+ ": Minireg User complete registration test");

		loginToSearchAsMiniregUser(randomUser_1);
		Assert.assertTrue(webHeaderPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");
		webHeaderPage.tokenCenterCompleteRegBtn().scrollUpAndClick();
		CustomLogger.log("Landed on Registration page.");

		webRegistrationPage.enterMiniregUserDetails(randomUser_1);
		webRegistrationPage.clickSubmitButton();
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(
				welcomeText.startsWith("Welcome " + randomUser_1.getFirstname() + " " + randomUser_1.getLastname()));
		Assert.assertFalse(webHeaderPage.isCompleteRegBtnExists(), "Complete registration button exists on the page");

		// webBasePage.openArticle(false);
		// Claim token button should be present now.
		// Assert.assertEquals(articlePage.isClaimTokenButtonPresent(),1);
	}

	@Test(description = "Test case Id= 23343")
	public void testUnrecognized_MiniRegUser_registration() {
		/*
		 * Here we are creating MiniregUser 'user1' via
		 * "REGISTRATION FOUNDATION" and we are trying to register with the same
		 * user 'user1' and expecting an error message in Registration page
		 */
		csPage.createMiniRegUser(randomUser_3);
		webBasePage.load();
		webHeaderPage.clickRegisterBtn();
		String password = randomUser_3.getPassword();
		System.out.println(password);
		// Change the password
		randomUser_3.setPassword(randomUser_3.getPassword() + "X");
		webRegistrationPage.enterUserDetails(randomUser_3);
		webRegistrationPage.clickSubmitButton();
		String errorMessage = webRegistrationPage.errorMessage();
		Assert.assertTrue(errorMessage.contains("email already exists"), errorMessage);

		// vamsi Dec-11-2015
		// Step 5. Click on Sign in.
		webBasePage.load();
		// webHeaderPage.clickSignInBtn(); this step includes in the below
		// performing line.

		webHeaderPage.loginToSearch(randomUser_3, password);
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		// Assert.assertTrue(welcomeText.startsWith("Welcome
		// "+randomUser_1.getFirstname()+"
		// "+randomUser_1.getLastname()),welcomeText);
		Assert.assertTrue(welcomeText.contains(randomUser_3.getEmail().toLowerCase()), welcomeText);

	}

	@Test(description = "Test case Id= 23347")
	public void testRecognignized_MiniRegUser_Search_NoRegistration() {
		String registrationURL = csPage.createMiniRegUser(randomUser_5);
		webBasePage.load(registrationURL);
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(welcomeText.contains(randomUser_5.getEmail().toLowerCase()), welcomeText);
		webHeaderPage.search("PCH", false);
		RegStepAwayLightBox lb = searchResultPage.getRegStepAwayLightBox();
		lb.dismissLightBox();
		int searchResults = searchResultPage.getWebSearchResultCount();
		Assert.assertTrue(searchResults > 0);
	}

	@Test(description = "Test case Id= 23347")
	public void testRecognignized_MiniRegUser_Search_Register() {
		String registrationURL = csPage.createMiniRegUser(randomUser_6);
		webBasePage.load(registrationURL);
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(welcomeText.contains(randomUser_6.getEmail().toLowerCase()), welcomeText);
		webHeaderPage.search("PCH", false);
		RegStepAwayLightBox lb = searchResultPage.getRegStepAwayLightBox();
		lb.continueRegistration();

		SignInLightBox signInLB = webRegistrationPage.signInLightBox();
		Assert.assertTrue(signInLB.isEmailReadOnly());
		signInLB.enterPassword(randomUser_6.getPassword());
		signInLB.clickSignIn();

		webRegistrationPage.enterMiniregUserDetails(randomUser_6);
		webRegistrationPage.clickSubmitButton();

		welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(
				welcomeText.startsWith("Welcome " + randomUser_6.getFirstname() + " " + randomUser_6.getLastname()),
				welcomeText);

//		String latestActivity = webHeaderPage.getLatestActivityFromStatus();

		webHeaderPage.clickTokenHistory();
		List<String> desc = tokenHistoryPage.getContentOfVisibleRows();

		for (String temp : desc) {
			if (temp.contains("First Search")) {
				Assert.assertEquals(temp, "For Your First Search of the Day!");
			}
		}

		// LatestActivity string above would be of the form - "You've
		// earned1,000 Tokens!For Completing, Registration!"
		// Will parse the activity name from the latest activity, so from above
		// string we will parse
		// "For Completing, Registration!"

		// String latestActivityName =
		// Common.subString(latestActivity,Common.REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY);
		// Assert.assertEquals(latestActivityName, "For Your First Search of the
		// Day!");
		// String tokensAtRegistration =
		// Common.subString(latestActivity,Common.REGEX_PARSING_TOKENS_FROM_ACTIVITY);
		String tokensAtRegistration = tokenHistoryPage.tokensAtRegistration();
		Assert.assertEquals(tokensAtRegistration, "1,000");

	}

	@Test(description = "Test case Id =23346")
	public void testRecognized_MiniRegUser_registration() {
		String registrationURL = csPage.createMiniRegUser(randomUser_4);
		webBasePage.load(registrationURL);
		String welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(welcomeText.contains(randomUser_4.getEmail().toLowerCase()), welcomeText);

		webHeaderPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Landed on Registration page.");
		SignInLightBox signInLB = webRegistrationPage.signInLightBox();
		Assert.assertTrue(signInLB.isEmailReadOnly());
		signInLB.enterPassword(randomUser_4.getPassword());
		signInLB.clickSignIn();

		webRegistrationPage.enterMiniregUserDetails(randomUser_4);
		webRegistrationPage.clickSubmitButton();

		webBasePage.closeUserLevelLightBox();
		welcomeText = webHeaderPage.getWelcomeUserLinkText();
		Assert.assertTrue(
				welcomeText.startsWith("Welcome " + randomUser_4.getFirstname() + " " + randomUser_4.getLastname()),
				welcomeText);

		String latestActivity = webHeaderPage.getLatestActivityFromStatus();

		// LatestActivity string above would be of the form - "You've
		// earned1,000 Tokens!For Completing, Registration!"
		// Will parse the activity name from the latest activity, so from above
		// string we will parse
		// "For Completing, Registration!"

		String latestActivityName = Common.subString(latestActivity, Common.REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY);
		Assert.assertEquals(latestActivityName, "For Completing Registration!");
		String tokensAtRegistration = Common.subString(latestActivity, Common.REGEX_PARSING_TOKENS_FROM_ACTIVITY);
		Assert.assertEquals(tokensAtRegistration, "1000");
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
		// driver.get(Common.getAppUrl(Environment.getAppName()));
		webBasePage.load();
		webHeaderPage.loginToSearch(randomUser_2);

		// webBasePage.openArticle(false);
		// Claim token button should not be present yet
		// Assert.assertEquals(articlePage.isClaimTokenButtonPresent(),0);

		// Click on Complete Registration button to land on Reg page
		webHeaderPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Landed on Registration page.");

		// Fill up the registration form with all information & Don't select
		// Title
		webRegistrationPage.enterMiniregUserDetails(randomUser_2);
		webRegistrationPage.selectTitle().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Title");

		// Fill up the registration form with all information & Don't enter
		// First Name
		webRegistrationPage.selectTitle().selectByIndex(1);
		webRegistrationPage.firstName().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "First Name");

		// Fill up the registration form with all information & Don't enter Last
		// Name
		webRegistrationPage.firstName().sendKeys(randomUser_2.getFirstname());
		webRegistrationPage.lastName().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Last Name");

		// Fill up the registration form with all information & Don't enter
		// Street Address
		webRegistrationPage.lastName().sendKeys(randomUser_2.getLastname());
		webRegistrationPage.streetAddress().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Street Address");

		// Fill up the registration form with all information & Don't enter City
		webRegistrationPage.streetAddress().sendKeys(randomUser_2.getStreet());
		webRegistrationPage.city().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "City");

		// Fill up the registration form with all information & Don't enter
		// State
		webRegistrationPage.city().sendKeys(randomUser_2.getCity());
		webRegistrationPage.stateListBox().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "State");

		// Fill up the registration form with all information & Don't enter Zip
		webRegistrationPage.stateListBox().selectByVisibleText(randomUser_2.getState());
		webRegistrationPage.zip().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Zip Code");

		// Fill up the registration form with all information & Don't enter 'Day
		// and Year' for Date of Birth
		webRegistrationPage.zip().sendKeys(randomUser_2.getZip());
		webRegistrationPage.dobMonthListBox().selectByIndex(1);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Date of Birth");

		// Fill up the registration form with all information & Don't enter
		// 'Year' for Date of Birth
		webRegistrationPage.dobMonthListBox().selectByIndex(1);
		webRegistrationPage.dobDayListBox().selectByIndex(1);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Date of Birth");

		// Fill up the registration form with all information & Don't select
		// 'Month' for Date of Birth
		webRegistrationPage.dobMonthListBox().selectByIndex(0);
		webRegistrationPage.dobYearListBox().selectByVisibleText("1980");
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Date of Birth");

	}

	@Test(description = "Test case Id = 24098, Optin Lightbox should not be displayed when user checked For Subscribe in optin Light box check box ")
	public void testReDisplayOfOptinMessage() throws SQLException {

		// Create Minireg User
		csPage.createMiniRegUser(randomUser_8);

		// Open Search & login using the minireg user credentials

		webBasePage.load();
		webHeaderPage.loginToSearch(randomUser_8);

		optinLB = webBasePage.getOptinLightBox();
		Assert.assertTrue(optinLB.isLightBoxPresent());
		optinLB.checkBothCheckBox();
		// optinLB.dismissLightBox();
		optinLB.submitOptin();
		headerPage.signOut();

		// Check for last week.
		String dateFormat = "MM/dd/yyyy";
		String curentDate = Common.getCurrentDate(timeZone, dateFormat);
		String lastWeekDateSTr = Common.addDaysToDate(curentDate, dateFormat, -8);
		long lastWeekEpochTime = Common.getEpochTimeStamp(lastWeekDateSTr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUser_8.getEmail(), lastWeekEpochTime);
		webBasePage.removeAllCookies();
		loginToSearch(randomUser_8);
		Assert.assertFalse(optinLB.isLightBoxPresent());
	}

}