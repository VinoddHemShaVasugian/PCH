package com.lightbox;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SignInRegisterLightboxTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();
	private final int lb_trigger_value = 2;

	@testId(test_id = "RT-04228")
	@testCaseName(test_case_name = "[D/T/M] FP : Remove signin/register")
	@Test(priority = 1, description = "Verify the Register/Sign-In LightBox for UnRecognised user", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04228:[D/T/M] FP : Remove signin/register")
	public void verify_register_lb_for_unrec_user() throws Exception {

		// Step 1
		test_step_details(1, "Verify the Register/Sign In LB after navigating 2 articles for Unrecognised user");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_presence();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article();
			article_page.verify_next_article_presence();
		}
		assertTrue(lb_instance.verfiy_register_sign_in_lb());

		// Need to add scenario for video navigation
		// Step 2
		test_step_details(2, "Click the Register button from LB for Unrecognised user");
		lb_instance.click_lb_register();
		register_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
	}

	@testId(test_id = "RT-04228")
	@testCaseName(test_case_name = "[D/T/M] FP : Remove signin/register")
	@Test(priority = 2, description = "Verify the Register/Sign-In LightBox for UnRecognised user", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04228:[D/T/M] FP : Remove signin/register")
	public void verify_signin_lb_for_unrec_user() throws Exception {
		// Step 1
		test_step_details(1, "Verify the Register/Sign In LB after navigating 2 articles for Unrecognised user");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_presence();
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			article_page.click_next_article();
			article_page.verify_next_article_presence();
		}
		assertTrue(lb_instance.verfiy_register_sign_in_lb());

		// Need to add scenario for video navigation
		// Step 2
		test_step_details(2, "Verify the Sign-In functionality from LB for Unrecognised user");
		lb_instance.click_lb_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
	}

	@testId(test_id = "RT-04228")
	@testCaseName(test_case_name = "[D/T/M] FP : Remove signin/register")
	@Test(priority = 1, description = "Verify the Register/Sign-In LightBox for UnRecognised user on SERP page", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04228:[D/T/M] FP : Remove signin/register")
	public void verify_register_signin_lb_for_unrec_user_on_serp() throws Exception {

		// Step 1
		test_step_details(1, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 2
		test_step_details(2, "Verify the Register/Sign In LB after 2 searches for Unrecognised user on SERP page");
		for (int loop = 0; loop < lb_trigger_value; loop++) {
			serp_instance.search(randomString(5, 6));
		}
		assertFalse(lb_instance.verfiy_register_sign_in_lb());
		switchToMainTab();
	}
}
