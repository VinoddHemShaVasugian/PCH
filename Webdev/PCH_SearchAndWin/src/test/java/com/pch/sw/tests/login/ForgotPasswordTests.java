package com.pch.sw.tests.login;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.lightBox.ResetPasswordLightBox;
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
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class ForgotPasswordTests extends BaseTest {

	WebHeaderPage headerPage;
	RegistrationPage regPage;
	ResetPasswordPage resetPwdPage;
	CentralServicesPage csPage;

	String emailId = "nptest@pchmail.com";
	String password = "Pch2003";
	String hostName = "imail.ny.pch.com";
	String hostname = "http://imail.ny.pch.com/IClient/Login.aspx?ReturnUrl=%2ficlient";
	// String regFound =
	// "http://centralservices."+Environment.getEnvironment()+".pch.com/RFApi_v8/Svc/testpage.aspx#";
	String mailIDforForgetPwsd = "np85@pchmail.com";
	HomePage homePage;
	User randomUser_1;
	ErrorPage errPage;
	PasswordAssistancePage pwdAsstPage;
	String resetLink = null;
	EmailUtils emailUtil = new EmailUtils();
	Date passwordRequestedTime;

	/*
	 * @Test public void getDate() throws java.text.ParseException,
	 * ParseException{ DateFormat format = new SimpleDateFormat(
	 * "E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH); Date date; date = (Date)
	 * format.parse("Wed Jun 15 20:41:06 IST 2016"); DateFormat dateFormat = new
	 * SimpleDateFormat("MM/dd/YYYY hh:mm:ss a", Locale.ENGLISH); String dd =
	 * dateFormat.format(date); System.out.println(dd); }
	 */

	@Test(groups = { GroupNames.Desktop }, testName = "[1]Reset_Forgot_Password_Expired - 17004")
	public void Reset_Forgot_Password_Expired() {
		CustomLogger.log(
				"########## Validating Forgot password by expiring and hardcoding the link with the help of reg foundation ##########");
		homePage.load();

		// Navigating to Sign page and click on forgot password
		CustomLogger.log("Navigating to Sign page and click on forgot password");
		headerPage.clickForgotPassword();
		ResetPasswordLightBox rplb = homePage.resetPasswordLightBox();
		rplb.enterEmail(mailIDforForgetPwsd);
		passwordRequestedTime = rplb.clickEnter();

		// Validating password assistance page
		CustomLogger.log("Validating password assistance page");
		Assert.assertTrue(resetPwdPage.helpIsOnTheWay().getText().contains("Help is on the way!"),
				"Message Displayed when we tried for the second time is incorrect");

		// Validating reset password expired condition with the help of reg
		// foundation
		CustomLogger.log("Validating reset password expired condition with the help of reg foundation");
		resetPwdPage.verifyPasswordExpiredRefound(mailIDforForgetPwsd);

		CustomLogger.log(
				"########## Validated Forgot password by expiring the link with the help of reg foundation ##########");
	}

	@Test(groups = {
			GroupNames.Desktop }, testName = "Test case Id 17003,17158", description = "Reset Password Again, Reset Forgot Password.")
	public void resetForgotPassword() {
		homePage.load();

		// Navigating to Sign page and click on forgot password
		CustomLogger.log("Navigating to Sign page and click on forgot password");
		headerPage.clickForgotPassword();
		ResetPasswordLightBox rplb = homePage.resetPasswordLightBox();
		rplb.enterEmail(mailIDforForgetPwsd);
		passwordRequestedTime = rplb.clickEnter();
		Assert.assertNotNull(passwordRequestedTime);
		CustomLogger.log("Password requested at " + passwordRequestedTime);

		// login to Imail and click on create password
		resetPwdPage.openMailAndValidateCreatePassword(passwordRequestedTime);
		homePage.load();
		Assert.assertTrue(homePage.welcomeMesg().contains("Welcome"));
	}

	@Test(groups = {
			GroupNames.Desktop }, testName = "[1]UnrecognizedUserResetsPassword_Desktop 17003,17158", description = "Reset Forgot Password as unrecognised user.")
	public void UnrecognizedUserResetsPassword_Desktop() {
		homePage.load();
		headerPage.clickForgotPassword();
		resetPwdPage.verifyWithInvalidIDs();
		ResetPasswordLightBox rplb = homePage.resetPasswordLightBox();
		rplb.enterEmail(mailIDforForgetPwsd);
		passwordRequestedTime = rplb.clickEnter();
		String messageDisplayed = resetPwdPage.helpIsOnTheWay().getText();
		Assert.assertTrue(messageDisplayed.contains("Help is on the way!"),
				"We are seeing some other message : " + messageDisplayed);
	}

	/*
	 * @BeforeClass(alwaysRun=true) public void testMailBoxConnectivity(){
	 * String result=emailUtil.connectToMailServer(hostName, emailId, password);
	 * Assert.assertEquals(result, "success"); }
	 * 
	 * @Test(description=
	 * "Test case Id 23383, Forgot password functionality - ForgotPassword on FrontPage"
	 * ) public void testResetPasswordLinkFromMailBox(){ homePage.load();
	 * //headerPage.clickSignInBtn(); headerPage.clickForgotPassword();
	 * ResetPasswordLightBox rplb = homePage.resetPasswordLightBox();
	 * rplb.enterEmail(mailIDforForgetPwsd); passwordRequestedTime =
	 * rplb.clickEnter(); Assert.assertNotNull(passwordRequestedTime);
	 * CustomLogger.log("Password requested at "+passwordRequestedTime);
	 * resetLink = emailUtil.getPasswordResetLink(passwordRequestedTime);
	 * 
	 * Assert.assertNotNull(resetLink,"Reset Link not found in mail box.");
	 * Assert.assertEquals(resetPwdPage.resetPasswordUsingLink(resetLink,
	 * "Auto1234$$"),"success"); resetPwdPage.clickContinueButton();
	 * CustomLogger.log("Clicked Continue button");
	 * Assert.assertTrue(homePage.isPageLoaded(),
	 * "Home Page is not loaded upon clicking continue button"); }
	 * 
	 * 
	 * @Test(description="Test case Id = 23404 ResetPassWordAgain"
	 * ,dependsOnMethods="testResetPasswordLinkFromMailBox") public void
	 * testResetPasswordEmailAgain(){
	 * 
	 * //Try to reset password with already used Reset Link
	 * 
	 * Assert.assertFalse(resetPwdPage.resetPasswordUsingLink(resetLink,
	 * "Auto1234$$").equals("success"));
	 * Assert.assertTrue(errPage.isTextDisplayed(
	 * "password reset link has already been used"));
	 * Assert.assertTrue(errPage.isLinkPresent("CONTINUE"),
	 * "CONTINUE link not present.");
	 * 
	 * errPage.clickOnLink("CONTINUE"); ResetPasswordLightBox rplb =
	 * homePage.resetPasswordLightBox(); rplb.enterEmail(emailId);
	 * passwordRequestedTime=rplb.clickEnter();
	 * //Assert.assertTrue(pwdAsstPage.isTextDisplayed("Help is on the way!"));
	 * CustomLogger.log("Password requested at "+passwordRequestedTime);
	 * resetLink = emailUtil.getPasswordResetLink(passwordRequestedTime);
	 * Assert.assertNotNull(resetLink,"Reset Link not found in mail box.");
	 * Assert.assertEquals(resetPwdPage.resetPasswordUsingLink(resetLink,
	 * "Auto1234$$"),"success");
	 * 
	 * resetPwdPage.clickContinueButton();
	 * Assert.assertTrue(homePage.isPageLoaded(),
	 * "Home Page is not loaded upon clicking continue button");
	 * 
	 * }
	 * 
	 * @Test(description="Test case id 23403, Expired password functionality"
	 * ,dependsOnMethods="testResetPasswordLinkFromMailBox") public void
	 * testExpiredPasswordLink(){ homePage.load();
	 * //headerPage.clickSignInBtn(); headerPage.clickForgotPassword();
	 * ResetPasswordLightBox rplb = homePage.resetPasswordLightBox();
	 * rplb.enterEmail(emailId); passwordRequestedTime=rplb.clickEnter();
	 * Assert.assertNotNull(passwordRequestedTime);
	 * //Assert.assertTrue(rplb.isTextDisplayed("Help is on the way!"));
	 * CustomLogger.log("Password requested at "+passwordRequestedTime); String
	 * resetLinkToBeExpired =
	 * emailUtil.getPasswordResetLink(passwordRequestedTime);
	 * Assert.assertNotNull(resetLinkToBeExpired,
	 * "Reset Link not found in mail box.");
	 * 
	 * //Expire password from Reg Foundation
	 * Assert.assertTrue(csPage.expirePasswordForUser(emailId));
	 * 
	 * Assert.assertFalse(resetPwdPage.resetPasswordUsingLink(
	 * resetLinkToBeExpired,"Auto1234$$").equals("success"));
	 * Assert.assertTrue(errPage.isTextDisplayed(
	 * "password reset link has expired"));
	 * Assert.assertTrue(errPage.isLinkPresent("CONTINUE"),
	 * "CONTINUE link not present.");
	 * 
	 * errPage.clickOnLink("CONTINUE"); rplb.enterEmail(emailId);
	 * passwordRequestedTime=rplb.clickEnter();
	 * //pwdAsstPage.enterEmail(emailId); //System.out.println("Old "+
	 * passwordRequestedTime); //passwordRequestedTime =
	 * pwdAsstPage.clickSubmit();
	 * //Assert.assertTrue(pwdAsstPage.isTextDisplayed("Help is on the way!"));
	 * 
	 * CustomLogger.log("Password requested at "+passwordRequestedTime);
	 * resetLinkToBeExpired =
	 * emailUtil.getPasswordResetLink(passwordRequestedTime);
	 * Assert.assertNotNull(resetLinkToBeExpired,
	 * "Reset Link not found in mail box.");
	 * Assert.assertEquals(resetPwdPage.resetPasswordUsingLink(
	 * resetLinkToBeExpired,"Auto1234$$"),"success");
	 * 
	 * resetPwdPage.clickContinueButton(); CustomLogger.log(
	 * "Clicked Continue Button"); Assert.assertTrue(homePage.isPageLoaded(),
	 * "Home Page is not loaded upon clicking continue button"); }
	 * 
	 * 
	 * 
	 * @Test (description="Session expire test Test case id = 22805") public
	 * void testSessionExpiration(){ loginToSearch(randomUser_1);
	 * homePage.removeCookie("QaSSOa"); headerPage.navigateToMyAccountPage();
	 * SignInLightBox signInLB = regPage.signInLightBox();
	 * Assert.assertTrue(signInLB.isLightBoxPresent()); }
	 * 
	 * @AfterClass(alwaysRun=true) public void closeMailBoxConnectivity() throws
	 * MessagingException{ emailUtil.closeEmailConnection();
	 * 
	 * }
	 */
}
