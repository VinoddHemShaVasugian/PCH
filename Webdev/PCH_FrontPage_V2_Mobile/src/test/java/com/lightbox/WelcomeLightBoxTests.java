package com.lightbox;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HamburgerMenuPage;
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
	private final HamburgerMenuPage menu_instance = HamburgerMenuPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 1, description = "Verify the Welcome LightBox for Recognised User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_welcome_lb_for_rec_user() throws Exception {

		// Step 1
		test_step_details(1, "Create a Full reg user and verify the Welcome Light Box.");
		homepage_instance.click_sign_in();
		signin_instance.click_register();
		String user_email = register_instance.register_full_user_with_optin();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About Frontpage");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 2, description = "Verify the Welcome LightBox for Mini Reg User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_welcome_lb_for_mini_reg_user() throws Exception {

		// Step 1
		test_step_details(1, "Create a Mini reg user and verify the Welcome Light Box.");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About Frontpage");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 3, description = "Verify the Welcome LightBox for Silver User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_welcome_lb_for_silver_user() throws Exception {

		// Step 1
		test_step_details(1, "Create a Silver reg user and verify the Welcome Light Box.");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About Frontpage");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 4, description = "Verify the Welcome LightBox for Social User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_welcome_lb_for_social_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Social reg user and verify the Welcome Light Box.");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());

		// Step 2
		test_step_details(2, "Click on Learn More link and verify the Redirection page");
		lb_instance.click_welcome_lb_learn_more_link();
		switchToNewTab();
		assertEquals(getTitle(), "About Frontpage");
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "About_FP"));
		switchToMainTab();
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Sign Out & Sign In with the same user and verify Welcome Light Box.");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_welcome_lb_learn_more_link());
	}
}
