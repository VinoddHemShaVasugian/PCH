package com.search;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.FooterPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.DriverManager;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SearchTests extends BaseClass {

	private final HomePage home = HomePage.getInstance();
	private final SERPage results = SERPage.getInstance();
	private final JoomlaConfigPage admin = JoomlaConfigPage.getInstance();
	private final AccountsSignInPage signin = AccountsSignInPage.getInstance();
	private final FooterPage footer_instance = FooterPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final String submit_action_value = "/search";

	private final AccountsRegisterPage account_register_isntance = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String serp_message_article_name = "Frontpage Messaging";
	private final String searchbox_config_article_name = "config-SearchBox";
	private final String search_config_article_name = "search";
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 1, groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04244:[D/T/M] FP: SERP page validation", description = "Verify the Search Box redirection URL by altering in the Joomla admin")
	public void verify_search_box_redirection() throws Exception {
		test_Method_details(1, "Frontpage redesign-Searchbox integration");

		String search_term = "Jeans";
		try {
			// Step 1
			test_step_details(1, "Navigate to Frontpage and search with valid term");
			home.search(search_term);
			switchToNewTab();
			assertTrue(results.verify_SERP_Completely());
			assertTrue(results.verify_searchresultspage(search_term));
			step_validator(1, true);
			// Step 2
			test_step_details(2, "Login to Joomla and change the redirection url on Submit Action");
			switchToMainTab();
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(searchbox_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			home.search(search_term);
			switchToNewTab();
			assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Revert back the admin changes");
			switchToMainTab();
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(searchbox_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			assertTrue(admin.publish_article());
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify by unpublishing the configuration article searchbox");
			admin.search_for_article(search_config_article_name);
			admin.unpublish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			navigateTo(xmlReader(ENVIRONMENT, "app_clear_cache"));
			navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
			home.search(search_term);
			assertFalse(home.verify_searchBox());
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Revert back the Admin changes");
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(search_config_article_name);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(5, true);
		} catch (Exception e) {

			log.error("Error while modifying the SearchBox configuration file :" + e.getLocalizedMessage());
			test_step_details(6, "Revert back the admin changes");
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(searchbox_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			admin.publish_article();
			admin.search_for_article(search_config_article_name);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"), xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin.goToArticlePage();
			admin.search_for_article(searchbox_config_article_name);
			admin.set_submit_action_value(submit_action_value);
			admin.publish_article();
			admin.search_for_article(search_config_article_name);
			admin.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 2, groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04244:[D/T/M] FP: SERP page validation", description = "Verify the Serp page Pagination links, Shopping carousel and In-line shopping Ads")
	public void verify_serp_pagination_carousel_shopping() throws Exception {
		test_Method_details(2, "frontpage redesign-SERP Integration");

		test_step_details(1, "Navigate to frontpage and search with valid term");
		navigateTo(xmlReader(ENVIRONMENT, "app_clear_cache"));
		navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
		String search_term = "Jeans";
		home.search(search_term);
		switchToNewTab();
		assertTrue(results.verify_SERP_Completely());
		step_validator(1, true);

		test_step_details(2, "Verify pagination links and their functionality");
		assertTrue(results.verify_paginationFunctionality(search_term));
		step_validator(2, true);

		test_step_details(3, "Validate the In-line Shopping Ads carousel.");
		assertTrue(results.verify_ShoppingAdsCarousel());
		step_validator(3, true);

		test_step_details(4, "Validate Sorting on shopping results page.");
		results.click_moreShoppingResults();
		assertTrue(results.verifiy_sortingShoppingResults(search_term));
		step_validator(4, true);
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 3, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the In House Ads on SERP page", testName = "RT-04244:[D/T/M] FP: SERP page validation")
	public void verify_inhouse_ads() throws Exception {
		test_Method_details(3, "Verify the In House Ads on SERP page");
		String search_string = "jeans";
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		home.click_Register();
		account_register_isntance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		assertTrue(home.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Validate the SERP");
		home.search(search_string);
		switchToNewTab();
		assertTrue(results.verify_SERP_Completely());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Validate the token amount for the Search");
		int total_token_value = home.get_Tokens();
		assertTrue(total_token_value > 0);
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Validate the token amount for the Search");
		results.search(generateRandomString(8));
		lb_instance.close_level_up_lb();
		assertTrue(home.get_Tokens() > total_token_value);
		total_token_value = home.get_Tokens();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Validate the tokens for the same Search word");
		results.search(search_string);
		assertTrue(home.get_Tokens() > total_token_value);
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Logged out the user and do a Search as Guest user");
		signin.logout();
		results.search(search_string);
		String guest_serp_message = results.get_serp_message();
		switchToMainTab();
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(serp_message_article_name);
		String expected_guest_serp_msg = getAttribute(
				admin_instance.get_text_field_element_by_label("Desktop - First Search", "2"), "value");
		assertEquals(guest_serp_message, expected_guest_serp_msg);
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Validate the In House Ad for random search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home.search(randomString(5, 6) + Date());
		switchToNewTab();
		switchToMainTab();
		step_validator(7, true);
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 4, groups = { "DESKTOP", "TABLET",
			"SANITY" }, description = "Verify the Collapse Search box on all the Site Pages for Recognised User", testName = "RT-04244:[D/T/M] FP: SERP page validation")
	public void verify_collapse_search_box_rec_user() throws Exception {
		test_Method_details(4, "Verify the Collapse Search box on all the Site Pages for Recognised User");
		String search_string = "Shoes";
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in wiith valid credentials");
		home.click_SignIn();
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(home.verify_Home());
		step_validator(1, true);

	/*	// Step 2
		test_step_details(2, "Verify the Collapsed Search box on Homepage");
		home.close_openx_banner();
		sleepFor(1);
		assertFalse(home.verify_openx_banner());
		step_validator(2, true);*/

		// Step 3
		test_step_details(3, "Verify the Collapsed Search box from all the SitePages");
		List<WebElement> footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			if (!footer_menu.get(count).getText().equalsIgnoreCase("Videos")) {
				moveToElement(footer_menu.get(count));
				footer_menu.get(count).click();
//				assertFalse(home.verify_openx_banner());
				assertTrue(home.verify_openx_banner());
				break;
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Search a keyword from the Collapsed Search box");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
//		home.search_term_on_collapse(search_string);
		home.search(search_string);
		switchToNewTab();
		assertTrue(results.verify_SERP_Completely());
		switchToMainTab();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Clear the session and verify the Search box in OpenX banner");
		DriverManager.getDriver().manage().deleteAllCookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertTrue(home.verify_openx_banner());
		step_validator(5, true);
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 5, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Collapse Search box on all the Site Pages for UnRecognised User", testName = "RT-04244:[D/T/M] FP: SERP page validation")
	public void verify_collapse_search_box_unrec_user() throws Exception {
		test_Method_details(5, "Verify the Collapse Search box on all the Site Pages for UnRecognised User");
		String search_string = "Shoes";
		// Step 1
/*		test_step_details(1, "Verify the Collapsed Search box on Homepage");
		home.close_openx_banner();
		sleepFor(1);
		assertFalse(home.verify_openx_banner());
		step_validator(1, true);*/

		// Step 2
		test_step_details(2, "Verify the Collapsed Search box from all the SitePages");
		List<WebElement> footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			if (!footer_menu.get(count).getText().equalsIgnoreCase("Videos")) {
				moveToElement(footer_menu.get(count));
				footer_menu.get(count).click();
//				assertFalse(home.verify_openx_banner());
				assertTrue(home.verify_openx_banner());
				break;
			}
		}
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Search a keyword from the Collapsed Search box");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
//		home.search_term_on_collapse(search_string);
		home.search(search_string);
		switchToNewTab();
		assertTrue(results.verify_SERP_Completely());
		switchToMainTab();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Clear the session and verify the Search box in OpenX banner");
		DriverManager.getDriver().manage().deleteAllCookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertTrue(home.verify_openx_banner());
		step_validator(4, true);
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 6, groups = { "DESKTOP", "TABLET",
			"SANITY" }, description = "Verify the OpenX banner on all the Site Pages for Recognised User", testName = "RT-04244:[D/T/M] FP: SERP page validation")
	public void verify_openx_banner_rec_user() throws Exception {
		test_Method_details(6, "Verify the OpenX banner and search box on all the Site Pages for Recognised User");

		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com  and sign-in wiith valid credentials");
		home.click_SignIn();
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(home.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the OpenX banner on all the SitePages");
		List<WebElement> footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			if (!footer_menu.get(count).getText().toLowerCase().equalsIgnoreCase("videos")
					&& !footer_menu.get(count).getText().toLowerCase().trim().equalsIgnoreCase("everyday life")) {
				moveToElement(footer_menu.get(count));
				System.out.println(footer_menu.get(count).getText());
				footer_menu.get(count).click();
				assertTrue(home.verify_search_bar_above_openx_banner());
				assertTrue(home.verify_openx_banner());
				home.search(generateRandomString(5));
				switchToNewTab();
				assertTrue(results.verify_SERP_Completely());
				switchToMainTab();
				break;
			}
		}
		step_validator(2, true);
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 7, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the OpenX banner and search box on all the Site Pages for UnRecognised User", testName = "RT-04244:[D/T/M] FP: SERP page validation")
	public void verify_openx_banner_unrec_user() throws Exception {
		test_Method_details(7, "Verify the OpenX banner on all the Site Pages for UnRecognised User");

		// Step 1
		test_step_details(1, "Verify the OpenX banner on all the SitePages");
		List<WebElement> footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			if (!footer_menu.get(count).getText().toLowerCase().equalsIgnoreCase("videos")
					&& !footer_menu.get(count).getText().toLowerCase().trim().equalsIgnoreCase("everyday life")) {
				moveToElement(footer_menu.get(count));
				footer_menu.get(count).click();
				assertTrue(home.verify_search_bar_above_openx_banner());
				assertTrue(home.verify_openx_banner());
				home.search(generateRandomString(5));
				switchToNewTab();
				assertTrue(results.verify_SERP_Completely());
				break;
			}
		}
		step_validator(1, true);
	}

	@testId(test_id = "RT-04244")
	@testCaseName(test_case_name = "[D/T/M] FP: SERP page validation")
	@Test(priority = 8, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Token amount for consecutive search for the same keyword", testName = "RT-04244:[D/T/M] FP: SERP page validation")
	public void verify_tokens_for_consecutive_search_for_same_keyword() throws Exception {
		String search_string = generateRandomString(5);
		test_Method_details(8, "Verify the Token amount for consecutive search for the same keyword");

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		home.click_Register();
		String user_email = account_register_isntance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		assertTrue(home.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Validate the SERP and the token amount for the Search");
		home.search(search_string);
		switchToNewTab();
		assertTrue(results.verify_searchresultspage(search_string));
		int total_token_value = home.get_Tokens();
		assertTrue(total_token_value > 0);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Validate the token amount for the same keyword Search");
		results.search(search_string);
		assertEqualsInt(home.get_Tokens(), total_token_value);
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Validate the tokens for the 26th Search");
		db_instance.updateDailySearchCount(user_email, 25);
		results.search(generateRandomString(6));
		assertEqualsInt(home.get_Tokens(), total_token_value);
		step_validator(4, true);
	}
}