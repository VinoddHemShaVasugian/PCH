package com.interstitial;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.InterstitialPage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class InterstiatialTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final InterstitialPage inetrstitial_instance = InterstitialPage.getInstance();
	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage reg_instance = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String interstitial_article_name = "Config-Interstitial";
	private String[] email_url = {};

	@AfterClass
	public void turn_off_interstitial() {
		try {
			System.out.println("Running After class");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(interstitial_article_name);
			admin_instance.enter_text_field_element_by_label("Value", "false", 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the After Class: " + e.getLocalizedMessage());
		}
	}

	@BeforeClass
	public void turn_on_interstitial() {
		try {
			System.out.println("Running Before class");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(interstitial_article_name);
			admin_instance.enter_text_field_element_by_label("Value", "true", 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the Before Class: " + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "32558,32899")
	@testCaseName(test_case_name = "Frontpage Redesign-Interstitial between Articles in PCH Frontpage-updates,[D][T] B_45936_JW Video Ad Player")
	@Test(priority = 1, description = "Verify the Interstitial ads configuration unrecognized user", groups = {
			"DESKTOP", "TABLET" })
	public void verify_interstitial_page_guest_user() throws Exception {
		test_Method_details(1, "Verify the Interstitial ads configuration unrecognized user");
		// Step 1
		test_step_details(1, "Navigate to home page and click article links till Interstitial page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		assertTrue(article_instance.verify_text_login_to_earn_tokens());
		article_instance.click_next_article();
		article_instance.click_next_article();
		lb_instance.close_lb();
		article_instance.click_next_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Interstitial page and its elements");
		assertIsStringContains(inetrstitial_instance.getCurrentUrl(), "interstitial");
		assertIsStringContains(inetrstitial_instance.get_page_source(), "content.jwplatform.com/libraries/6IpjMhDP.js");
		assertIsStringContains(getTitle(), "Interstitial");
		if (inetrstitial_instance.verify_next_article())
			inetrstitial_instance.click_next_article_button();
		assertEquals(article_instance.getTitle(), "PCH Frontpage > Article");
		step_validator(2, true);
	}

	@testId(test_id = "32558")
	@testCaseName(test_case_name = "Frontpage Redesign-Interstitial between Articles in PCH Frontpage-updates")
	@Test(priority = 2, description = "Verify the Interstitial ads configuration - FB User", groups = { "DESKTOP",
			"TABLET" })
	public void verify_interstitial_page_social_user() throws Exception {
		test_Method_details(2, "Verify the Interstitial ads configuration - FB User");
		// step 1
		test_step_details(1,
				"Create new Mini Reguser and go to FP and verify the article page text - Complete Registration now to earn tokens for enjoying articles!");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSocialUser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		assertTrue(article_instance.verify_text_complete_reg_earn_tokens());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to home page and click article links till Interstitial page");
		article_instance.click_next_article();
		article_instance.click_next_article();
		lb_instance.close_lb();
		article_instance.click_next_article();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Interstitial page and its elements");
		assertIsStringContains(inetrstitial_instance.getCurrentUrl(), "interstitial");
		assertIsStringContains(inetrstitial_instance.get_page_source(), "content.jwplatform.com/libraries/6IpjMhDP.js");
		assertIsStringContains(getTitle(), "Interstitial");
		if (inetrstitial_instance.verify_next_article())
			inetrstitial_instance.click_next_article_button();
		assertEquals(article_instance.getTitle(), "PCH Frontpage > Article");
		step_validator(3, true);
	}

	@testId(test_id = "32558")
	@testCaseName(test_case_name = "Frontpage Redesign-Interstitial between Articles in PCH Frontpage-updates")
	@Test(priority = 3, description = "Verify the Interstitial ads configuration - Mini Reg User", groups = { "DESKTOP",
			"TABLET" })
	public void verify_interstitial_page_minireg_user() throws Exception {
		test_Method_details(3, "Verify the Interstitial ads configuration - Mini Reg User");

		// step 1
		test_step_details(1,
				"Create new Mini Reguser and go to FP and verify the article page text - Complete Registration now to earn tokens for enjoying articles!");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createMiniReguser()[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		assertTrue(article_instance.verify_text_complete_reg_earn_tokens());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to home page and click article links till Interstitial page");
		article_instance.click_next_article();
		article_instance.click_next_article();
		lb_instance.close_lb();
		article_instance.click_next_article();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Interstitial page and its elements");
		assertIsStringContains(inetrstitial_instance.getCurrentUrl(), "interstitial");
		assertIsStringContains(inetrstitial_instance.get_page_source(), "content.jwplatform.com/libraries/6IpjMhDP.js");
		assertIsStringContains(getTitle(), "Interstitial");
		if (inetrstitial_instance.verify_next_article())
			inetrstitial_instance.click_next_article_button();
		assertEquals(article_instance.getTitle(), "PCH Frontpage > Article");
		step_validator(3, true);
	}

	@testId(test_id = "32558")
	@testCaseName(test_case_name = "Frontpage Redesign-Interstitial between Articles in PCH Frontpage-updates")
	@Test(priority = 4, description = "Verify the Interstitial ads configuration - Silver User", groups = { "DESKTOP",
			"TABLET" })
	public void verify_interstitial_page_silver_user() throws Exception {
		test_Method_details(4, "Verify the Interstitial ads configuration - Silver User");

		// Step 1
		test_step_details(1, "Create new Silver user and go to FP and verify the article page");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		email_url = centralpage.createSilverUser();
		navigateTo(email_url[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		assertTrue(article_instance.verify_text_complete_reg_earn_tokens());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to home page and click article links till Interstitial page");
		article_instance.click_next_article();
		article_instance.click_next_article();
		lb_instance.close_lb();
		article_instance.click_next_article();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Interstitial page and its elements");
		assertIsStringContains(inetrstitial_instance.getCurrentUrl(), "interstitial");
		assertIsStringContains(inetrstitial_instance.get_page_source(), "content.jwplatform.com/libraries/6IpjMhDP.js");
		assertIsStringContains(getTitle(), "Interstitial");
		if (inetrstitial_instance.verify_next_article())
			inetrstitial_instance.click_next_article_button();
		assertEquals(article_instance.getTitle(), "PCH Frontpage > Article");
		step_validator(3, true);
	}

	@testId(test_id = "32558")
	@testCaseName(test_case_name = "Frontpage Redesign-Interstitial between Articles in PCH Frontpage-updates")
	@Test(priority = 5, description = "Verify the Interstitial ads configuration - full registered User", groups = {
			"DESKTOP", "TABLET", "SANITY" })
	public void verify_interstitial_page_recognised_user() throws Exception {
		test_Method_details(5, "Verify the Interstitial ads configuration - full registered User");

		// Step 1
		test_step_details(1, "Create new full reg and go to FP and verify the article page");
		homepage_instance.click_Register();
		reg_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to home page and click article links till Interstitial page");
		article_instance.click_next_article();
		article_instance.click_next_article();
		article_instance.click_next_article();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Interstitial page and its elements");
		assertIsStringContains(inetrstitial_instance.getCurrentUrl(), "interstitial");
		assertIsStringContains(inetrstitial_instance.get_page_source(), "content.jwplatform.com/libraries/6IpjMhDP.js");
		assertIsStringContains(getTitle(), "Interstitial");
		if (inetrstitial_instance.verify_next_article())
			inetrstitial_instance.click_next_article_button();
		assertEquals(article_instance.getTitle(), "PCH Frontpage > Article");
		step_validator(3, true);
	}
}
