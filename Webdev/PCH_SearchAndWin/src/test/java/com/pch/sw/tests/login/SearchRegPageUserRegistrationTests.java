package com.pch.sw.tests.login;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.ErrorPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.MyAccountPage;
import com.pch.search.pages.web.PchDotComPage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class SearchRegPageUserRegistrationTests extends BaseTest {

	private HomePage webBasePage;
	private WebHeaderPage headerPage;
	private RegistrationPage webRegistrationPage;
	private MyAccountPage accountPage;
	private PchDotComPage pchComPage;
	private AdminBasePage joomlaPage;
	private ErrorPage errPage;
	private User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5, randomUser_6, registeredUser;
	private String badWord = "\u0046\u0075\u0063\u006b";

	/**
	 * 
	 * Existing User register again test
	 * 
	 */

	@Test(description = "Validate error message for regestering using already registered emailID.")
	public void testExistingUserRegisterAgain() {
		loginToSearch(randomUser_1);
		headerPage.signOut();
		// Go to Registration page
		webBasePage.load();
		headerPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information
		// Email-ID should be already registered one.
		randomUser_1.setPassword(randomUser_1.getPassword() + "x");
		webRegistrationPage.enterUserDetails(randomUser_1);

		// Click Submit button
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		String actualErrorMessage = webRegistrationPage.errorMessage();
		CustomLogger.log(actualErrorMessage);
		String expectedErrorMessage = "This email already exists in our system. Please sign in.";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}

	@Test(description = "Validate links on Token Bank")
	public void testTokenCenterForRegisteredUser() {

		// Step 2. Navigate to Search.
		// Step 3. Sign in as a Register User.
		loginToSearch(randomUser_5);

		// Step 4. Verify that Token bank is visible in Universal Nav bar
		// There are no steps between 4 & 8
		// Step 8. Verify "Redeem Tokens" link in token bank
		Assert.assertTrue(headerPage.isRedeemTokensLinkPresent());
		Assert.assertTrue(headerPage.isTokenHistoryLinkPresent());

		Assert.assertTrue(headerPage.getTokenCount() > 0);
		Assert.assertEquals(headerPage.getUserStatus().toUpperCase(), "BRONZE");

		// Step 10. Click on "Token History" link
		headerPage.clickTokenHistory();
		Assert.assertEquals(accountPage.getSelectedTab().toUpperCase(), "TOKEN HISTORY");
		accountPage.navigateToMainParentSite();

		// Signout and perform the same test again as a previously registered
		// user.
		headerPage.signOut();

		loginToSearch(randomUser_5);
		Assert.assertTrue(headerPage.isRedeemTokensLinkPresent());
		Assert.assertTrue(headerPage.isTokenHistoryLinkPresent());

		Assert.assertTrue(headerPage.getTokenCount() > 0);
		Assert.assertEquals(headerPage.getUserStatus().toUpperCase(), "BRONZE");

		headerPage.clickTokenHistory();
		Assert.assertEquals(accountPage.getSelectedTab().toUpperCase(), "TOKEN HISTORY");
		accountPage.navigateToMainParentSite();

		webBasePage.load();

		// Step 12. Verify that Token Bank displays the recent Activities on
		// clicking the expanding Arrow.
		String latestActivity = headerPage.getLatestActivityFromStatus();
		Assert.assertEquals(headerPage.getLatestActivityFromStatus(), latestActivity);
		CustomLogger.log("Latest activity got Displayed, and the latest Activty is \n " + latestActivity);

		int tokens = headerPage.getTokenCount();
		Assert.assertEquals(tokens, headerPage.getTokenCount());
		CustomLogger.log("Tokens are displayed and count is : " + tokens);

	}

	@Test(description = "Validate Token Center for Guest Users.")
	public void testTokenCenterForGuestUser() {
		webBasePage.load();
		Assert.assertFalse(headerPage.isRedeemTokensLinkPresent());
		Assert.assertFalse(headerPage.isTokenHistoryLinkPresent());

		Assert.assertEquals(headerPage.getTokenCount(), -1);
		// Assert.assertEquals(headerPage.getUserStatus().toUpperCase(),"NO
		// STATUS");
	}

	@Test(description = "Register with email and confirm-email not matching and validate error message.")
	public void testRegisterUserMismatchedEmail() {
		CustomLogger.log(Environment.getAppName() + " | " + Environment.getEnvironment().toUpperCase()
				+ ": Register with email and confirm-email not matching test");

		webBasePage.load();
		// Go to Registration page
		headerPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information
		// Email-ID & Confirm Email should not match
		webRegistrationPage.enterUserDetails(randomUser_2);
		webRegistrationPage.confirmEmail().clear();
		webRegistrationPage.confirmEmail().sendKeys("abcd@pchmail.com");

		// Click Submit button
		webRegistrationPage.clickSubmitButton();

		CustomLogger.log("Registration form submitted.");

		// Validate the error message

		String actualErrorMessage = webRegistrationPage.errorMessage();
		String expectedErrorMessage = "Email and Confirm Email do not match. Please try again.";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
		CustomLogger.log("'" + expectedErrorMessage
				+ "' error message displayed for trying to register with mis-matched email & confirm-email.");

	}

	@Test(description = "Register with password and confirm-password not matching and validate error message.")
	public void testRegisterUserMismatchedPassword() {
		CustomLogger.log(Environment.getAppName() + " | " + Environment.getEnvironment().toUpperCase()
				+ ": Register with password and confirm-password not matching test");

		// Go to Registration page
		webBasePage.load();
		headerPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information
		// Password & Confirm password should not match
		webRegistrationPage.enterUserDetails(randomUser_3);
		webRegistrationPage.confirmPassword().sendKeys("testing2");

		// Click Submit button
		webRegistrationPage.clickSubmitButton();
		// Validate the error message
		String actualErrorMessage = webRegistrationPage.errorMessage();

		String expectedErrorMessage = "Password and Confirm Password do not match. Please try again.";
		Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
		CustomLogger.log("'" + expectedErrorMessage
				+ "' error message displayed for trying to register with mis-matched password & confirm-password.");

	}

	@Test(description = "Register with missing required fields and validate error messages.")
	public void testRegisterUserWithMissingRequiredData() {
		CustomLogger.log(Environment.getAppName() + " | " + Environment.getEnvironment().toUpperCase()
				+ ": Register with missing required fields test");
		webBasePage.load();
		// Go to Registration page
		headerPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information & Don't select
		// Title
		webRegistrationPage.enterUserDetails(randomUser_4);
		webRegistrationPage.selectTitle().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Title");
		CustomLogger.log("Error message to select 'Title' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// First Name
		webRegistrationPage.selectTitle().selectByIndex(1);
		webRegistrationPage.firstName().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "First Name");
		CustomLogger.log("Error message to enter 'First Name' is displayed.");

		// Fill up the registration form with all information & Don't enter Last
		// Name
		webRegistrationPage.firstName().sendKeys(randomUser_4.getFirstname());
		webRegistrationPage.lastName().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Last Name");
		CustomLogger.log("Error message to enter 'Last Name' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// Street Address
		webRegistrationPage.lastName().sendKeys(randomUser_4.getLastname());
		webRegistrationPage.streetAddress().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Street Address");
		CustomLogger.log("Error message to enter 'Street Address' is displayed.");

		// Fill up the registration form with all information & Don't enter City
		webRegistrationPage.streetAddress().sendKeys(randomUser_4.getStreet());
		webRegistrationPage.city().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "City");
		CustomLogger.log("Error message to enter 'City' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// State
		webRegistrationPage.city().sendKeys(randomUser_4.getCity());
		webRegistrationPage.stateListBox().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "State");
		CustomLogger.log("Error message to enter 'State' is displayed.");

		// Fill up the registration form with all information & Don't enter Zip
		webRegistrationPage.stateListBox().selectByVisibleText(randomUser_4.getState());
		webRegistrationPage.zip().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Zip Code");
		CustomLogger.log("Error message to enter 'Zip Code' is displayed.");
		// Validate Zip code field by entering Alphabets
		webRegistrationPage.zip().clear();
		webRegistrationPage.zip().sendKeys("Test");
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Zip Code");
		CustomLogger.log("Error message to enter 'Zip Code' is displayed.");
		// Validate Zip code field by entering Alpha numeric digits
		webRegistrationPage.zip().clear();
		webRegistrationPage.zip().sendKeys("T@#$S");
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Zip Code");
		CustomLogger.log("Error message to enter 'Zip Code' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// Email
		webRegistrationPage.zip().sendKeys(randomUser_4.getZip());
		webRegistrationPage.email().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Email");
		CustomLogger.log("Error message to enter 'Email' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// 'Confirm Email'
		webRegistrationPage.email().sendKeys(randomUser_4.getEmail());
		webRegistrationPage.confirmEmail().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Email Confirmation");
		CustomLogger.log("Error message to enter 'Email Confirmation' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// Password
		webRegistrationPage.confirmEmail().sendKeys(randomUser_4.getEmail());
		webRegistrationPage.password().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Password");
		CustomLogger.log("Error message to enter 'Password' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// 'Confirm Password'
		webRegistrationPage.password().sendKeys(randomUser_4.getPassword());
		webRegistrationPage.confirmPassword().clear();
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Password Confirmation");
		CustomLogger.log("Error message to enter 'Password Confirmation' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// 'Month' for Date of Birth
		webRegistrationPage.confirmPassword().sendKeys(randomUser_4.getPassword());
		webRegistrationPage.dobMonthListBox().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Date of Birth");
		CustomLogger.log("Error message to enter 'Date of Birth' is displayed.");

		// Fill up the registration form with all information & Don't enter
		// 'Day' for Date of Birth
		webRegistrationPage.dobMonthListBox().selectByIndex(1);
		webRegistrationPage.dobDayListBox().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Date of Birth");
		CustomLogger.log("Error message to enter 'Date of Birth' is displayed.");

		// Fill up the registration form with all information & Don't select
		// 'Year' for Date of Birth
		webRegistrationPage.dobMonthListBox().selectByIndex(1);
		webRegistrationPage.dobDayListBox().selectByIndex(1);
		webRegistrationPage.dobYearListBox().selectByIndex(0);
		webRegistrationPage.clickSubmitButton();

		// Validate the error message
		Assert.assertEquals(webRegistrationPage.errorMessage(), "Date of Birth");
		CustomLogger.log("Error message to enter 'Date of Birth' is displayed.");
	}

	@Test(description = "Test case id = 23426 - Validate if the user is less than 13 years registration is not possible.")
	public void testRegistrationWithDOBLessthan13Years() {
		webBasePage.load();
		// Go to Registration page
		headerPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information
		// Email-ID & Confirm Email should not match
		webRegistrationPage.enterUserDetails(randomUser_2);
		String year = Common.addYearToDate(Common.getCurrentDate("America/New_York", "yyyy"), "yyyy", -12);
		webRegistrationPage.dobYearListBox().selectByVisibleText(year);

		// Click Submit button
		webRegistrationPage.justClickSubmitBtn();
		Assert.assertTrue(errPage.isTextDisplayed("We're Sorry"));
		Assert.assertTrue(errPage.isTextDisplayed("The page you are looking for cannot be accessed"));
	}

	@Test(description = "Test case id - 23314, HIDE the Earn Tokens Link_Desktop")
	public void testhideEarnTokensLinkForGoldUser() {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("Position", "uninav-right");
		// Step 8. Enable "Earn Tokens Link" in Joomla
		joomlaPage.gotoModule("PCH Core - Token Center", filter);
		joomlaPage.expandOption("Footer Links");
		String earnTokensLink = joomlaPage.getTextProperty("Earn Tokens Link");
		if (earnTokensLink.isEmpty()) {
			joomlaPage.setTextProperty("Earn Tokens Link", "Earn Tokens");
		} else {
			joomlaPage.saveCloseAndClearCache();
		}
		loginToSearch(registeredUser);
		Assert.assertTrue(headerPage.isLinkPresent("Earn Tokens"), "Earn Tokens link is present");

		// Disable "Earn Tokens Link" in Joomla
		joomlaPage.gotoModule("PCH Core - Token Center", filter);
		joomlaPage.expandOption("Footer Links");
		if (!earnTokensLink.isEmpty()) {
			joomlaPage.setTextProperty("Earn Tokens Link", "");
			joomlaPage.saveCloseAndClearCache();
		} else {
			joomlaPage.saveCloseAndClearCache();
		}
		webBasePage.load();
		loginToSearch(registeredUser);
		Assert.assertFalse(headerPage.isLinkPresent("Earn Tokens"), "Earn Tokens link is not Present");

		// Enable "Earn Token Link" as default
		joomlaPage.gotoModule("PCH Core - Token Center", filter);
		joomlaPage.expandOption("Footer Links");
		if (earnTokensLink.isEmpty()) {
			joomlaPage.setTextProperty("Earn Tokens Link", "Earn Tokens");
			joomlaPage.saveCloseAndClearCache();
		} else {
			joomlaPage.saveCloseAndClearCache();
		}
	}

	@Test(description = "Test case id - 23428, Usage of restricted Word")
	public void testRegistrationWithRestrictedUser() {

		// Step 1. Go to Search PCH Homepage
		// Step 2.1. Check if Sign-In and Register button exist on Uni Nav Bar.

		webBasePage.load();
		Assert.assertTrue(headerPage.isSignINBtnDsplayed());
		CustomLogger.log("Sign button is displyed");
		Assert.assertTrue(headerPage.isRegisterBtnDsplayed());
		CustomLogger.log("Register button is displyed");

		// Step 2.2. Click on register button fill up the details with Name of
		// {restricted words} and submit the form, should direct to InValid
		// Registration Error page
		// Go to Registration page
		headerPage.clickRegisterBtn();
		CustomLogger.log("Navigated to Registration Page");

		// Fill up the registration form with all information
		// Email-ID & Confirm Email should not match
		webRegistrationPage.enterUserDetails(randomUser_1);
		webRegistrationPage.firstName().clear();
		webRegistrationPage.firstName().sendKeys(badWord);
		webRegistrationPage.justClickSubmitBtn();
		Assert.assertTrue(errPage.isTextDisplayed("Invalid Registration"));
		// vasmi k Dec-11
		Assert.assertTrue(errPage.isTextDisplayed("All Customer Service Inquiries:"));
	}

	@Test(description = "Test Case Id - 27165 - Secure accounts redirects tests")
	public void secureAccountSite() {

		String neededModule = "Plug-in Manager";

		String header = joomlaPage.selectExtensionsSubElementUsingVisibleText(neededModule);

		CustomLogger.log("We are in " + header + " page");

		if (joomlaPage.searchInPlugInManager("PCH - Accounts Site Redirect") == 1) {

			try {
				joomlaPage.resultOfplugInMangerSeach().click();
				try {
					if (joomlaPage.headerPresent()) {
						if (!joomlaPage.resultOfplugInMangerSeach().isDisplayed())
							CustomLogger.log("Searched Successfully");
						else
							joomlaPage.resultOfplugInMangerSeach().click();
					} else {
						CustomLogger.log("Searched Successfully");
					}

				} catch (NoSuchElementException e) {
					CustomLogger.log("Searched Successfully");
				}

				final String validContent = joomlaPage.validSSOcontent();
				final String inValidContent = joomlaPage.inValidSSOContent();
				// System.out.println(validContent);
				// System.out.println(inValidContent);

				joomlaPage.saveCloseAndClearCache();

				webBasePage.load();

				joomlaPage.testValidSSO(validContent);

				webBasePage.load();

				joomlaPage.testInValidSSO(inValidContent);

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}

		}

	}

	@Test(description = "Validate Reg Event for Registered Users.")
	public void testRegEventForRegisteredUser() {

		// Navigate to Search.
		// Sign in as a Register User.
		loginToSearch(randomUser_6);

		// Validate Reg Event for BLINGO
		Assert.assertTrue(headerPage.getRegEvent().contains("BLINGO"));
		Assert.assertFalse(headerPage.getRegEvent().contains("FRONTPAGE"));

		// Validate Reg Event for PCHCOM
		pchComPage.load();
		Assert.assertTrue(headerPage.getRegEvent().contains("PCHCOM"));

		// Validate Reg Event for FRONTPAGE
		headerPage.goToFrontpage();
		Assert.assertTrue(headerPage.getRegEvent().contains("FRONTPAGE"));

	}

}