package com.lightbox;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class OptinLightboxTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final HamburgerMenuPage menu_instance = HamburgerMenuPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 1, description = "Verify the Optin LightBox for Full Reg. User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_optin_lb_for_full_reg_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Full Reg. user");
		homepage_instance.click_sign_in();
		signin_instance.click_register();
		String user_email = register_instance.register_full_user_without_optin();

		// Step 2
		test_step_details(2, "Verify the Optin LB");
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_welcome_light_box();
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();

		// Step 3
		test_step_details(3, "Verify the Optin LB for the same user");
		menu_instance.click_sign_out();
		menu_instance.click_sign_in();
		signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_optin_lb());

		// Step 4
		test_step_details(4, "Verify the Optin LB after modifying the DB value");
		db_instance.updateOptinShowedCount(user_email, 0);
		db_instance.updateLastLogin(user_email, 0l);
		menu_instance.click_sign_out();
		delete_session_cookies();
		menu_instance.click_sign_in();
		signin_instance.login_without_close_optin(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 2, description = "Verify the Optin LightBox for Mini Reg. User", groups = {
			"MOBILE" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_optin_lb_for_mini_reg_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Mini Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createMiniReguser();

		// Step 2
		test_step_details(2, "Verify the Optin LB");
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_welcome_light_box();
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();

		// Step 3
		test_step_details(3, "Verify the Optin LB for the same user");
		menu_instance.click_sign_out();
		menu_instance.click_sign_in();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		assertFalse(lb_instance.verify_optin_lb());

		// Step 4
		test_step_details(4, "Verify the Optin LB after modifying the DB value");
		db_instance.updateOptinShowedCount(user_details[0], 0);
		db_instance.updateLastLogin(user_details[0], 0l);
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1]);
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 3, description = "Verify the Optin LightBox for Social User", groups = {
			"MOBILE" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_optin_lb_for_social_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSocialUser();

		// Step 2
		test_step_details(2, "Verify the Optin LB");
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_welcome_light_box();
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();

		// Step 3
		test_step_details(3, "Verify the Optin LB for the same user");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1]);
		assertFalse(lb_instance.verify_optin_lb());

		// Step 4
		test_step_details(4, "Verify the Optin LB after modifying the DB value");
		db_instance.updateOptinShowedCount(user_details[0], 0);
		db_instance.updateLastLogin(user_details[0], 0l);
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1]);
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 4, description = "Verify the Optin LightBox for Silver User", groups = {
			"MOBILE" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_optin_lb_for_silver_user() throws Exception {
		// Step 1
		test_step_details(1, "Create a Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSilverUser();

		// Step 2
		test_step_details(2, "Verify the Optin LB");
		invokeBrowser(user_details[1]);
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_welcome_light_box();
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();

		// Step 3
		test_step_details(3, "Verify the Optin LB for the same user");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1]);
		assertFalse(lb_instance.verify_optin_lb());

		// Step 4
		test_step_details(4, "Verify the Optin LB after modifying the DB value");
		db_instance.updateOptinShowedCount(user_details[0], 0);
		db_instance.updateLastLogin(user_details[0], 0l);
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1]);
		assertTrue(lb_instance.verify_optin_lb());
		lb_instance.close_optin_light_box();
	}
}
