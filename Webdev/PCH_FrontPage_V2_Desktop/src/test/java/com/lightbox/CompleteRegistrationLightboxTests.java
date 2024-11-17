package com.lightbox;

import org.testng.annotations.Test;
import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.InterstitialPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class CompleteRegistrationLightboxTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final InterstitialPage inetrstitial_instance = InterstitialPage.getInstance();
	private final int lb_trigger_value = 2;

	@testId(test_id = "RT-04210")
	@testCaseName(test_case_name = "Mini Reg user test scenario [D/T]")
	@Test(priority = 1, description = "Verify the Complete Registration LightBox for Mini Reg User", groups = {
			"DESKTOP",
			"TABLET" }, testName = "RT-04210:Mini Reg user test scenario [D/T]")
	public void verify_complete_registration_lb_for_mini_reg() throws Exception {
		test_Method_details(1, "Verify the Complete Registration LightBox for Mini Reg User");

		// Step 1
		test_step_details(1, "Create a Mini Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to articles continously and verify the Complete registration LB");
		homepage_instance.close_openx_banner();
		sleepFor(2);
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_presence();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article();
			if (getTitle().contains("Interstitial")) {
				inetrstitial_instance.click_next_article_button();
			} else {
				article_page.verify_next_article_presence();
			}
		}
		assertTrue(lb_instance.verify_complete_registraion_lb());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Complete the registration process from Complete registration LB");
		lb_instance.click_complete_registraion_lb();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04212")
	@testCaseName(test_case_name = "Silver user test scenario [D/T]")
	@Test(priority = 2, description = "Verify the Complete Registration LightBox for Silver User", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04212:Silver user test scenario [D/T]")
	public void verify_complete_registration_lb_for_silver_user() throws Exception {
		test_Method_details(2, "Verify the Complete Registration LightBox for Silver User");

		// Step 1
		test_step_details(1, "Create a Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to articles continously and verify the Complete registration LB");
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_presence();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article();
			if (getTitle().contains("Interstitial")) {
				inetrstitial_instance.click_next_article_button();
			} else {
				article_page.verify_next_article_presence();
			}
		}
		assertTrue(lb_instance.verify_complete_registraion_lb());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Complete the registration process from Complete registration LB");
		lb_instance.click_complete_registraion_lb();
		assertTrue(lb_instance.verify_create_password_lb());
		lb_instance.enter_create_password(xmlReader(ENVIRONMENT, "ValidPassword"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04214")
	@testCaseName(test_case_name = "Social user test scenario [D/T]")
	@Test(priority = 3, description = "Verify the Complete Registration LightBox for Social User", groups = { "DESKTOP",
			"TABLET" }, testName = "Frontpage redesign-implement lightbox skin (complete registration, sign-in & register,create password)")
	public void verify_complete_registration_lb_for_social_user() throws Exception {
		test_Method_details(3, "Verify the Complete Registration LightBox for Social User");

		// Step 1
		test_step_details(1, "Create a Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to articles continously and verify the Complete registration LB");
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_presence();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article();
			if (getTitle().contains("Interstitial")) {
				inetrstitial_instance.click_next_article_button();
			} else {
				article_page.verify_next_article_presence();
			}
		}
		assertTrue(lb_instance.verify_complete_registraion_lb());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Complete the registration process from Complete registration LB");
		lb_instance.click_complete_registraion_lb();
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(3, true);
	}
}
