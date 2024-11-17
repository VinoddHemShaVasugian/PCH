package com.search;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SearchTests extends BaseClass {

	private final HomePage home = HomePage.getInstance();
	private final SERPage results = SERPage.getInstance();
	private final JoomlaConfigPage admin = JoomlaConfigPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final HamburgerMenuPage ham_menu_instance = HamburgerMenuPage.getInstance();
	private final AccountsRegisterPage account_register_instance = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String serp_message_article_name = "Frontpage Messaging";
	private final String search_config_article_name = "SearchBox";
	private final String submit_action_value = "/search";
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@testId(test_id = "32467")
	@testCaseName(test_case_name = "Frontpage redesign-Searchbox integration")
	@Test(priority = 1, groups = { "DESKTOP",
			"TABLET" }, testName = "32467:Frontpage redesign-Searchbox integration", description = "Verify the Search Box redirection URL by altering in the Joomla admin")
	public void verify_search_box_redirection() throws Exception {

		String search_term = "Jeans";
		try {
			// Step 1
			test_step_details(1, "Navigate to Frontpage and search with valid term");
			lb_instance.close_gs_overlay();
			home.search(search_term);
			assertTrue(results.verify_SERP_Completely());
			assertIsStringContainsIgnoreCase(getCurrentUrl(),
					xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);

			// Step 2
			test_step_details(2, "Login to Joomla and change the redirection url on Submit Action");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(search_config_article_name);
			admin.set_submit_action_value("");
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			home.search(search_term);
			assertIsStringContainsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL") + "?q=" + search_term);

			// Step 3
			test_step_details(3, "Revert back the admin changes");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.goToArticlePage();
			admin.search_for_article(search_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			assertTrue(admin.publish_article());

			// Step 4
			test_step_details(4, "Verify by unpublishing the configuration article searchbox");
			admin.search_for_article(search_config_article_name);
			admin.unpublish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertFalse(home.verify_search_box());

			// Step 5
			test_step_details(5, "Revert back the Admin changes");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.goToArticlePage();
			admin.search_for_article(search_config_article_name);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			// Step 6
			log.error("Error while modifying the SearchBox configuration file :" + e.getLocalizedMessage());
			test_step_details(6, "Revert back the admin changes");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(search_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			test_step_details(6, "Revert back the admin changes");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(search_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "32524")
	@testCaseName(test_case_name = "frontpage redesign-SERP Integration")
	@Test(priority = 2, groups = { "DESKTOP",
			"TABLET" }, testName = "32524:frontpage redesign-SERP Integration", description = "Verify the Serp page Pagination links, Shopping carousel and In-line shopping Ads")
	public void verify_serp_pagination_carousel_shopping() throws Exception {

		test_step_details(1, "Navigate to frontpage and search with valid term");
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		String search_term = "Jeans";
		home.search(search_term);
		switchToNewTab();
		assertTrue(results.verify_SERP_Completely());

		test_step_details(2, "Verify pagination links and their functionality");
		assertTrue(results.verify_paginationFunctionality(search_term));
	}

	@testId(test_id = "33001")
	@testCaseName(test_case_name = "[D] FrontPage-Redesign D-08030  Display In House Ad on SERP when no results are returned")
	@Test(priority = 3, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the In House Ads on SERP page", testName = "33001:[D] FrontPage-Redesign D-08030  Display In House Ad on SERP when no results are returned")
	public void verify_inhouse_ads() throws Exception {
		String search_string = "jeans";
		String expected_guest_serp_msg = "Register Now";
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		home.click_sign_in();
		home.click_register();
		account_register_instance.register_full_user_without_optin();
		lb_instance.close_welcome_optin_lb();
		assertTrue(home.verify_Home());

		// Step 2
		test_step_details(2, "Validate the SERP");
		home.search(search_string);
		switchToNewTab();
		assertTrue(results.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Validate the token amount for the Search");
		int total_token_value = home.get_token_amount_from_uni_nav();
		assertTrue(total_token_value > 0);

		// Step 4
		// Commented the In-House Ad's verification. Since its removed from the
		// application.
		test_step_details(4, "Validate the token amount for the Search");
		results.search(generateRandomString(8));
		lb_instance.close_level_up_lb();
		// assertTrue(results.verify_serp_inhouse_ad());
		assertTrue(home.get_token_amount_from_uni_nav() > total_token_value);
		total_token_value = home.get_token_amount_from_uni_nav();

		// Step 5
		test_step_details(5, "Validate the tokens for the same Search word");
		results.search(search_string);
		assertTrue(home.get_token_amount_from_uni_nav() > total_token_value);

		// Step 6
		test_step_details(6, "Logged out the user and do a Search as Guest user");
		ham_menu_instance.click_sign_out();
		results.search(search_string);
		String guest_serp_message = results.get_serp_message();
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(serp_message_article_name);
		assertEquals(guest_serp_message, expected_guest_serp_msg);

		// Step 7
		// Commented the In-House Ad's verification. Since its removed from the
		// application.
		test_step_details(7, "Validate the In House Ad for random search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home.search(randomString(5, 6) + Date());
		switchToNewTab();
		// assertTrue(results.verify_serp_inhouse_ad());
		switchToMainTab();
	}

	@testId(test_id = "29839")
	@testCaseName(test_case_name = "[D] - Frontpage Desktop Redesign- Front-end Implementation - Phase 2 - Search Bar")
	@Test(priority = 4, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Token amount for consecutive search for the same keyword", testName = "29839:[D] - Frontpage Desktop Redesign- Front-end Implementation - Phase 2 - Search Bar")
	public void verify_tokens_for_consecutive_search_for_same_keyword() throws Exception {
		String search_string = generateRandomString(5);

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		home.click_sign_in();
		home.click_register();
		String user_email = account_register_instance.register_full_user_without_optin();
		lb_instance.close_welcome_optin_lb();
		assertTrue(home.verify_Home());

		// Step 2
		test_step_details(2, "Validate the SERP and the token amount for the Search");
		home.search(search_string);
		switchToNewTab();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_string);
		int total_token_value = home.get_token_amount_from_uni_nav();
		assertTrue(total_token_value > 0);

		// Step 3
		test_step_details(3, "Validate the token amount for the same keyword Search");
		results.search(search_string);
		assertEqualsInt(home.get_token_amount_from_uni_nav(), total_token_value);

		// Step 4
		test_step_details(4, "Validate the tokens for the 26th Search");
		db_instance.updateDailySearchCount(user_email, 25);
		results.search(generateRandomString(6));
		assertEqualsInt(home.get_token_amount_from_uni_nav(), total_token_value);
	}

	@testId(test_id = "34488")
	@testCaseName(test_case_name = "Change Background Color to White on SERP [M]")
	@Test(priority = 5, groups = {
			"MOBILE" }, description = "Verify the App SERP page", testName = "34488:Change Background Color to White on SERP [M]")
	public void verify_appsearch_page() throws Exception {
		String search_term = generateRandomString(5);

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		home.click_sign_in();
		home.click_register();
		account_register_instance.register_full_user_without_optin();
		lb_instance.close_welcome_optin_lb();
		assertTrue(home.verify_Home());

		// Step 2
		test_step_details(2, "Validate the SERP and the token amount for the Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "appsearch?q=" + search_term);
		assertTrue(results.verify_SERP_Completely());
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "appsearch?q=" + search_term);

		// Step 3
		test_step_details(3, "Validate the SERP page sections");
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(results.verify_serp_page_ad_results_section());
		assertTrue(results.verify_serp_page_main_results_section());
	}
}
