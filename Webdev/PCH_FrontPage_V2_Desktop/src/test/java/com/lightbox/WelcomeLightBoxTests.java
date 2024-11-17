package com.lightbox;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class WelcomeLightBoxTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 1, description = "Verify the Welcome LightBox for Recognised User", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_welcome_lb_for_rec_user() throws Exception {
		test_Method_details(1, "Verify the Welcome LightBox for Recognised User");
		// Step 1
		test_step_details(1, "Create a Full reg user and verify the Welcome Light Box.");
		homepage_instance.click_Register();
		String user_email = register_instance.register_FullUser();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		homepage_instance.click_SignOut();
		homepage_instance.click_SignIn();
		signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 2, description = "Verify the Welcome LightBox for Mini Reg User", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_welcome_lb_for_mini_reg_user() throws Exception {
		test_Method_details(2, "Verify the Welcome LightBox for Mini Reg User");

		// Step 1
		test_step_details(1, "Create a Mini reg user and verify the Welcome Light Box.");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 3, description = "Verify the Welcome LightBox for Silver User", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_welcome_lb_for_silver_user() throws Exception {
		test_Method_details(3, "Verify the Welcome LightBox for Silver User");

		// Step 1
		test_step_details(1, "Create a Silver reg user and verify the Welcome Light Box.");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 4, description = "Verify the Welcome LightBox for Social User", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_welcome_lb_for_social_user() throws Exception {
		test_Method_details(4, "Verify the Welcome LightBox for Social User");
		// Step 1
		test_step_details(1, "Create a Social reg user and verify the Welcome Light Box.");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
		step_validator(3, true);
	}
}