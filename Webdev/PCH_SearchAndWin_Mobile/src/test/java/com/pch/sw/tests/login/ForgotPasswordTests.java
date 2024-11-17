package com.pch.sw.tests.login;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.ErrorPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.PasswordAssistancePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.ResetPasswordPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.EmailUtils;
import com.pch.search.utilities.User;

public class ForgotPasswordTests extends BaseTest {

	WebHeaderPage headerPage;
	RegistrationPage regPage;
	ResetPasswordPage resetPwdPage;
	CentralServicesPage csPage;
	GuidedSearchPage gsPage;

	String invalidFormatID = "np01@pch";
	String unregisteredEmail = "np1111@pchmail.com";
	String emailId = "nptest@pchmail.com";
	String password = "password3";
	String hostName = "imail.ny.pch.com";
	String hostname = "http://imail.ny.pch.com/IClient/Login.aspx?ReturnUrl=%2ficlient";
	String mailIDforForgetPwsd = "np85@pchmail.com";
	HomePage homePage;
	User randomUser_1;
	ErrorPage errPage;
	PasswordAssistancePage pwdAsstPage;
	String resetLink = null;
	EmailUtils emailUtil = new EmailUtils();
	Date passwordRequestedTime;

	@Test(testName = "[1]Reset_Forgot_Password_Expired - 17004", description = "Reset the expired Forget Password link")
	public void Reset_Forgot_Password_Expired() {
		CustomLogger.log(
				"########## Validating Forgot password by expiring and hardcoding the link with the help of reg foundation ##########");
		homePage.load();

		// Navigating to Sign page and click on forgot password
		CustomLogger.log("Navigating to Sign page and click on forgot password");
		headerPage.signInButton().click();
		headerPage.forgotPasswordLink().click();

		// Enter valid mail ID and click on forgot password
		CustomLogger.log("Enter valid mail ID and click on forgot password");
		resetPwdPage.enterEmailAddress().clear();
		resetPwdPage.enterEmailAddress().sendKeys(mailIDforForgetPwsd);
		resetPwdPage.submitBtnOnForgotPswdPage().click();

		// Validating password assistance page
		CustomLogger.log("Validating the message displayed");
		homePage.waitFor(3);
		String helpContent = resetPwdPage.helpIsOnTheWay().getText();
		CustomLogger.log("Validating the mesage : " + helpContent);
		Assert.assertTrue(helpContent.contains("Help is on the way!"),
				"We are seeing some other alert : " + helpContent);

		// Validating reset password expired condition with the help of reg
		// foundation
		CustomLogger.log("Validating reset password expired condition with the help of reg foundation");
		resetPwdPage.verifyPasswordExpiredRefound(mailIDforForgetPwsd);

		CustomLogger.log(
				"########## Validated Forgot password by expiring the link with the help of reg foundation ##########");
	}

	@Test(testName = "Mobile_ResetPassword - 16982", description = "Verifying reset password and confirmation page",enabled=false)
	public void Mobile_ResetPassword() {
		homePage.load();

		// Navigating to Sign page and click on forgot password
		CustomLogger.log("Navigating to Sign page and click on forgot password");
		headerPage.signInButton().click();
		headerPage.forgotPasswordLink().click();

		// Enter valid mail ID and click on forgot password
		CustomLogger.log("Enter valid mail ID and click on forgot password");
		resetPwdPage.enterEmailAddress().clear();
		resetPwdPage.enterEmailAddress().sendKeys(mailIDforForgetPwsd);
		resetPwdPage.submitBtnOnForgotPswdPage().click();

		// Validate the message displayed
		CustomLogger.log("Validating the message displayed");
		homePage.waitFor(3);
		String helpContent = resetPwdPage.helpIsOnTheWay().getText();
		CustomLogger.log("Validating the mesage : " + helpContent);
		Assert.assertTrue(helpContent.contains("Help is on the way!"),
				"We are seeing some other alert : " + helpContent);

		// validating Date from machine
		passwordRequestedTime = new Date();

		homePage.waitFor(3);

		// login to Imail and click on create password
		resetPwdPage.openMailAndValidateCreatePassword(passwordRequestedTime);
		homePage.load();
	}

	//@Test(groups = {GroupNames.Mobile }, testName = "[1]UnrecognizedUserResetsPassword_Desktop 17003,17158", description = "Reset Forgot Password as unrecognised user.")
	public void UnrecognizedUserResetsPassword_Desktop() {
		homePage.load();
		headerPage.signInButton().click();

		headerPage.forgotPasswordLink().click();
		CustomLogger.log("Validating with empty Email field");
		resetPwdPage.enterEmailAddress().clear();

		resetPwdPage.submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);

		// Validating with Empty email
		CustomLogger.log("Validating with Empty email");
		if (resetPwdPage.isErrorDisplayed()) {
			String errorContent = resetPwdPage.errorContent().getText();
			CustomLogger.log("Header : " + errorContent);
			Assert.assertTrue(errorContent.contains("Please enter a valid Email."),
					"We are seeing some other alert : " + errorContent);
		}

		// Validating with invalid format email
		CustomLogger.log("Validating with invalid format email");
		resetPwdPage.enterEmailAddress().clear();
		resetPwdPage.enterEmailAddress().sendKeys(invalidFormatID);
		resetPwdPage.submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if (resetPwdPage.isErrorDisplayed()) {
			String errorContent = resetPwdPage.errorContent().getText();
			CustomLogger.log("Header : " + errorContent);
			Assert.assertTrue(errorContent.contains("Please enter a valid Email."),
					"We are seeing some other alert : " + errorContent);
		}

		// Validating with unregistered email
		CustomLogger.log("Validating with unregistered email");
		resetPwdPage.enterEmailAddress().clear();
		resetPwdPage.enterEmailAddress().sendKeys(unregisteredEmail);
		resetPwdPage.submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if (resetPwdPage.isErrorDisplayed()) {
			String errorContent = resetPwdPage.errorContent().getText();
			CustomLogger.log("Header : " + errorContent);
			Assert.assertTrue(
					errorContent.contains(
							"Sorry, the email you provided is not recognized. Please 'Register' to create your own account today."),
					"We are seeing some other alert : " + errorContent);
		}

		// Validating with valid email
		CustomLogger.log("Validating with valid email");
		resetPwdPage.enterEmailAddress().clear();
		resetPwdPage.enterEmailAddress().sendKeys(mailIDforForgetPwsd);
		resetPwdPage.submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if (!resetPwdPage.isErrorDisplayed()) {
			String helpContent = resetPwdPage.helpIsOnTheWay().getText();
			CustomLogger.log("Header : " + helpContent);
			Assert.assertTrue(helpContent.contains("Help is on the way!"),
					"We are seeing some other alert : " + helpContent);
		}

	}
}
