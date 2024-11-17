package com.lightbox;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.InterstitialPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class CompleteRegistrationLightboxTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final InterstitialPage inetrstitial_instance = InterstitialPage.getInstance();
	private final int lb_trigger_value = 2;

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 1, description = "Verify the Complete Registration LightBox for Mini Reg User", groups = {
			"MOBILE" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_complete_registration_lb_for_mini_reg() throws Exception {

		// Step 1
		test_step_details(1, "Create a Mini Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_gs_overlay();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());

		// Step 2
		test_step_details(2, "Do search and verify the Complete registration LB");
		homepage_instance.search(randomString(5, 6));
		serp_instance.verify_SERP_Completely();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			serp_instance.search(randomString(5, 6));
		}
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertTrue(lb_instance.verify_complete_registraion_lb());
		lb_instance.close_lb();

		// Step 3
		test_step_details(3, "Navigate to articles continously and verify the Complete registration LB");
		delete_session_cookies();
		invokeBrowser(user_details[1]);
		lb_instance.close_gs_overlay();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_link();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article_link();
			if (getTitle().contains("Publishers Clearing House")) {
				inetrstitial_instance.click_skip_btn();
			} else {
				article_page.verify_next_article_link();
			}
		}
		assertTrue(lb_instance.verify_complete_registraion_lb());

		// Need to do verification for Video navigation
		// Step 4
		test_step_details(4, "Complete the registration process from Complete registration LB");
		lb_instance.click_complete_registraion_lb();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_registration_for_mini_reg_user();
		if (getTitle().contains("Publishers Clearing House ")) {
			inetrstitial_instance.click_skip_btn();
		}
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 2, description = "Verify the Complete Registration LightBox for Silver User", groups = {
			"MOBILE" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_complete_registration_lb_for_silver_user() throws Exception {

		// Step 1
		test_step_details(1, "Create a Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_gs_overlay();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());

		// Step 2
		test_step_details(2, "Do search and verify the Complete registration LB");
		homepage_instance.search(randomString(5, 6));
		serp_instance.verify_SERP_Completely();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			serp_instance.search(randomString(5, 6));
		}
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertTrue(lb_instance.verify_complete_registraion_lb());
		lb_instance.close_lb();

		// Step 3
		test_step_details(3, "Navigate to articles continously and verify the Complete registration LB");
		delete_session_cookies();
		invokeBrowser(user_details[1]);
		lb_instance.close_gs_overlay();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_link();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article_link();
			if (getTitle().contains("Publishers Clearing House")) {
				inetrstitial_instance.click_skip_btn();
			} else {
				article_page.verify_next_article_link();
			}
		}
		assertTrue(lb_instance.verify_complete_registraion_lb());

		// Need to do verification for Video navigation
		// Step 4
		test_step_details(4, "Complete the registration process from Complete registration LB");
		lb_instance.click_complete_registraion_lb();
		assertTrue(lb_instance.verify_create_password_lb());
		lb_instance.enter_create_password(xmlReader(ENVIRONMENT, "ValidPassword"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		if (getTitle().contains("Publishers Clearing House")) {
			inetrstitial_instance.click_skip_btn();
		}
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 3, description = "Verify the Complete Registration LightBox for Social User", groups = {
			"MOBILE" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_complete_registration_lb_for_social_user() throws Exception {

		// Step 1
		test_step_details(1, "Create a Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());

		// Step 2
		test_step_details(3, "Do search and verify the Complete registration LB");
		homepage_instance.search(randomString(5, 6));
		serp_instance.verify_SERP_Completely();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			serp_instance.search(randomString(5, 6));
		}
		// assertTrue(lb_instance.verify_complete_registraion_lb());
		// lb_instance.close_lb();

		// Step 3
		test_step_details(3, "Navigate to articles continously and verify the Complete registration LB");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		delete_session_cookies();
		invokeBrowser(user_details[1]);
		lb_instance.close_gs_overlay();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_link();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article_link();
			if (getTitle().contains("Publishers Clearing House")) {
				inetrstitial_instance.click_skip_btn();
			} else {
				article_page.verify_next_article_link();
			}
		}
		assertTrue(lb_instance.verify_complete_registraion_lb());

		// Need to do verification for Video navigation
		// Step 4
		test_step_details(4, "Complete the registration process from Complete registration LB");
		lb_instance.click_complete_registraion_lb();
		register_instance.complete_registration_for_social_user();
		if (getTitle().contains("Publishers Clearing House")) {
			inetrstitial_instance.click_skip_btn();
		}
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
	}
}
