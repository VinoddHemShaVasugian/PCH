package com.login;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.ForgotPasswordPage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DriverManager;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class AccountsClientPackageIntegrationTests extends BaseClass {

	private final AccountsSignInPage signin = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final ForgotPasswordPage forgot = ForgotPasswordPage.getInstance();
	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final SERPage results = SERPage.getInstance();
	private final MyAccount myaccount = MyAccount.getInstance();
	private final HomePage home = HomePage.getInstance();
	public boolean step_status;

	@testId(test_id = "32580")
	@testCaseName(test_case_name = "B-43858 Accounts Client Package Integration")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts Client Package Integration", testName = "32580:B-43858 Accounts Client Package Integration")
	public void B_43858_AccountsClientPackageIntegration() throws Exception {
		test_Method_details(1, "B-43858 Accounts Client Package Integration");
		test_step_details(1, "Navigate to frontpage and verify");
		assertTrue(home.verify_UnRecHome());
		step_validator(1, true);

		test_step_details(2, "Verify the sign-in & register links for Guest user");
		home.click_SignIn();
		step_status = DriverManager.getDriver().getCurrentUrl().contains("login");
		navigate_back();
		home.click_Register();
		assertIsStringContains(getCurrentUrl(), "register");
		step_validator(2, true);

		test_step_details(3, "Verify by clicking signout & myaccount links");
		navigate_back();
		home.click_SignIn();
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		home.verify_Home();
		home.click_MyAccount();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		home.verify_sign_out();
		step_status = DriverManager.getDriver().getCurrentUrl().contains("my-account");
		navigateTo_WOC_Cookies(xmlReader(ENVIRONMENT, "BaseURL"));
		home.click_SignOut();
		assertTrue(step_status && home.verify_UnRecHome());
		step_validator(3, true);

		test_step_details(4, "Verify the forgot password link on sign-in page.");
		home.click_SignIn();
		assertTrue(forgot.verify_forgotPassword("Help is on the way!\ncheck your inbox for the link to create or reset your password."));
		step_validator(4, true);

		test_step_details(5, "Verify by clicking on complete registration for minireg user");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createMiniReguser()[1]);
		lb_instance.close_welcome_optin_lb();
		home.click_complete_registration();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(register.verifyEmailPasswordNotPresent() && register.verify_AccountsRegisterScreen());
		step_validator(5, true);

		test_step_details(6, "Verify by clicking on the complete registration button for silver users");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSilverUser()[1]);
		home.click_complete_registration();
		assertTrue(lb_instance.verify_create_password_lb());
		step_validator(6, true);

		test_step_details(7, "Verify the sign-in & register links on serp page who are not signed-in.");
		navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
		home.search("jeans");
		switchToNewTab();
		step_status = results.verify_searchresultspage("jeans");
		home.click_SignIn();
		step_status = DriverManager.getDriver().getCurrentUrl().contains("login");
		navigate_back();
		home.click_Register();
		assertTrue(step_status);
		assertIsStringContains(getCurrentUrl(), "register");
		switchToMainTab();
		step_validator(7, true);
	}

	@testId(test_id = "32577")
	@testCaseName(test_case_name = "B-43858 Accounts client package Integration - Forgot Password")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts client package Integration - Forgot Password", testName = "32577:B-43858 Accounts client package Integration - Forgot Password")
	public void B43858_AccountsclientpackageIntegration_ForgotPassword() throws Exception {
		test_Method_details(2, "B-43858 Accounts client package Integration - Forgot Password");

		test_step_details(1, "Navigate to frontpage and verify");
		assertTrue(home.verify_UnRecHome());
		step_validator(1, true);

		test_step_details(2,
				"click sign in button and click Forgot or need to reset your password? Click Here and verify");
		home.click_SignIn();
		assertTrue(forgot.verify_forgotPassword(
				"Help is on the way!\ncheck your inbox for the link to create or reset your password."));
		step_validator(2, true);

		test_step_details(3, "Enter invalid email address in the Email Field and click Enter");
		navigate_back();
		assertTrue(forgot.verify_forgotPassword_UnrecEmail(
				"Sorry, the email you provided is not recognized. Please 'Register' to create your own account today."));
		step_validator(3, true);
	}

	@testId(test_id = "32579")
	@testCaseName(test_case_name = "B-43858 Accounts client package integration - MyAccount")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts client package integration - MyAccount", testName = "32579:B-43858 Accounts client package integration - MyAccount")
	public void B43858_AccountsclientpackageIntegration_MyAccount() throws Exception {
		test_Method_details(3, "B-43858 Accounts client package integration - MyAccount");

		test_step_details(1, "Navigate to frontpage, sign in and navigate to my account page and verify");

		home.click_SignIn();
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		home.verify_Home();
		home.click_MyAccount();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(myaccount.verify_myAccount());
		step_validator(1, true);
	}

	@testId(test_id = "32576")
	@testCaseName(test_case_name = "B-43858 Accounts client package integration - sign in")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts client package integration - sign in", testName = "32576:B-43858 Accounts client package integration - sign in")
	public void B43858_AccountsclientpackageIntegration_signin() throws Exception {
		test_Method_details(4, "B-43858 Accounts client package integration - sign in");

		test_step_details(1, "Navigate to frontpage and on signin enter invalid credentials and verify error messages");
		home.click_SignIn();
		signin.login("jfk3@pchmail.com", xmlReader(ENVIRONMENT, "ValidPassword"));
		step_status = signin.verify_errormessage(
				"The password you entered is invalid or not found. Please try again or click forgot password.");
		signin.login("tester", xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(step_status && signin.verify_errormessage("Please enter a valid Email."));
		step_validator(1, true);

		test_step_details(2, "Login with valid credentials and verify");
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(home.verify_Home());
		step_validator(2, true);
	}

	@testId(test_id = "32578")
	@testCaseName(test_case_name = "B-43858 Accounts client package integration - sign out")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts client package integration - sign out", testName = "32578:B-43858 Accounts client package integration - sign out")
	public void B43858_AccountsclientpackageIntegrationSignout() throws Exception {
		test_Method_details(5, "B-43858 Accounts client package integration - sign out");

		test_step_details(1, "Login with valid credentials and verify");
		home.click_SignIn();
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(home.verify_Home());
		step_validator(1, true);

		test_step_details(2, "Click on Sign out button");
		home.click_SignOut();
		assertTrue(home.verify_UnRecHome());
		step_validator(2, true);
	}

	@testId(test_id = "32594")
	@testCaseName(test_case_name = "B-43858 Accounts client package integration- Register page")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts client package integration- Register page", testName = "32594:B-43858 Accounts client package integration- Register page")
	public void B43858_AccountsclientpackageIntegration_RegisterPage() throws Exception {
		test_Method_details(6, "B-43858 Accounts client package integration- Register page");

		test_step_details(1,
				"Navigate to portal, click on Register and do not fill some fields and verify the error messages");
		home.click_Register();
		step_status = register.verify_ErrorMessageHeader("PLEASE FILL IN THE FOLLOWING FIELDS:")
				&& register.verify_ErrorMessageContent("Title") && register.verify_ErrorMessageContent("First Name")
				&& register.verify_ErrorMessageContent("Last Name")
				&& register.verify_ErrorMessageContent("Street Address") && register.verify_ErrorMessageContent("City")
				&& register.verify_ErrorMessageContent("State") && register.verify_ErrorMessageContent("Zip Code")
				&& register.verify_ErrorMessageContent("Email")
				&& register.verify_ErrorMessageContent("Email Confirmation")
				&& register.verify_ErrorMessageContent("Password")
				&& register.verify_ErrorMessageContent("Password Confirmation");
		assertTrue(step_status);
		step_validator(1, true);

		test_step_details(2, "Enter all valid details and commplete the registration and verify");
		register.register_FullUser("10", "May", "1960");
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(home.verify_Home());
		step_validator(2, true);
	}

	@testId(test_id = "32580")
	@testCaseName(test_case_name = "B-43858 Accounts client pakage integration - Complete Registration")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-43858 Accounts client pakage integration - Complete Registration", testName = "32580:B-43858 Accounts client pakage integration - Complete Registration")
	public void B43858_AccountsclientpackageIntegration_CompleteRegistration() throws Exception {
		test_Method_details(7, "B-43858 Accounts client pakage integration - Complete Registration");

		test_step_details(1, "As a mini reg user, click on Complete Registration, verify and complete registraton");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createMiniReguser()[1]);
		lb_instance.close_welcome_optin_lb();
		home.click_complete_registration();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		step_status = register.verifyEmailPasswordNotPresent() && register.verify_AccountsRegisterScreen();
		register.complete_RegistrationForMiniRegUser();
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(step_status && home.verify_Home());
		step_validator(1, true);

		test_step_details(2, "As a Social user, click on Complete Registration, verify and complete registraton");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSocialUser()[1]);
		lb_instance.close_welcome_optin_lb();
		home.click_complete_registration();
		step_status = register.verify_AccountsRegisterScreen();
		register.complete_RegistrationForSocialUser();
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(step_status && home.verify_Home());
		step_validator(2, true);

		test_step_details(3, "As a Silver user, click on Complete Registration, verify and complete registraton");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSilverUser()[1]);
		lb_instance.close_welcome_optin_lb();
		home.click_complete_registration();
		step_status = lb_instance.verify_create_password_lb();
		lb_instance.enter_create_password(xmlReader(ENVIRONMENT, "ValidPassword"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_bronze_level_up_lb();
		assertTrue(step_status && home.verify_Home());
		step_validator(3, true);
	}
}