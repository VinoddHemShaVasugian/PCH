package com.search;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SearchTests extends BaseClass {

	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final SERPage results = SERPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final AccountsRegisterPage account_register_isntance = AccountsRegisterPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private final String desktop_search_box_field_name = "desktop_searchbox_enabled";
	private final String tablet_search_box_field_name = "tablet_searchbox_enabled";
	private final String search_config_article_name = "Config-EveryDayLife";
	private String big_fish_search_term = "3080";
	private String valid_infospace_search_term = "Shoes";

	@testId(test_id = "35068")
	@testCaseName(test_case_name = "B-59235 EDL SERP - D&T")
	@Test(priority = 1, groups = { "DESKTOP",
			"TABLET" }, testName = "35068:B-59235 EDL SERP - D&T", description = "Verify the EDL SERP page")
	public void verify_search_box_redirection() throws Exception {
		try {
			// Step 1
			test_step_details(1, "Create a Full Registered user");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			edl_home_instance.click_register();
			account_register_isntance.register_FullUser();
			assertTrue(edl_home_instance.verify_home());

			// Step 2
			test_step_details(2, "Navigate to EDL and search with valid term");
			edl_home_instance.search(valid_infospace_search_term);
			switchToNewTab();
			lb_instance.close_level_up_lb();
			edl_home_instance.verify_edl_title("Search");
			edl_home_instance.verfiy_date_on_page();
			assertIsStringContainsIgnoreCase(getCurrentUrl(), valid_infospace_search_term);
			
			//assertTrue(results.verify_search_results_url(valid_infospace_search_term));

			// Step 3
			test_step_details(3, "Search a word and verify the token and message");
			int token_amount = edl_home_instance.get_token_amount_from_uninav();
			edl_home_instance.search(randomString(0, 6));
			assertTrue(edl_home_instance.get_token_amount_from_uninav() > token_amount);
			assertIsStringContains(msg_property_file_reader("serp_token_message"),
					edl_home_instance.get_latest_entry_activity_message().split("<br>")[1]);

			// Step 4 - This is totally dependent on info space if its not coming then its not an issue.
			/*test_step_details(4, "Verify In-line Shopping Ad carousel");
			edl_home_instance.search(valid_infospace_search_term);
			assertTrue(results.verify_inline_shopping_ads_carousel());
			assertEquals(results.get_shopping_ads_title(), "Shopping Ads");
			String product_prize = results.get_prize_of_first_shopping_product();
			results.click_first_shopping_product();
			switchToNewTab();
			assertIsStringContains(get_page_source(), product_prize);
			switchToMainTab();
			results.click_more_shopping_results();
			assertTrue(results.verifiy_shopping_sorting_results(valid_infospace_search_term));*/

			// Step 5
			/*test_step_details(5, "Verify the SERP Pagination functionality");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			edl_home_instance.search(valid_infospace_search_term);
			assertTrue(results.verify_pagination_functionality(valid_infospace_search_term));*/

			// Step 6
			test_step_details(6, "Verify the Big Fish & Super Prize Ad's");
			edl_home_instance.search(big_fish_search_term);
			assertTrue(results.verify_serp_inhouse_ad());			

			// Step 8
			test_step_details(8, "Verify the display of Search box");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(edl_home_instance.verify_search_box());

			// Step 9
			test_step_details(9, "Revert back the admin changes");
			switchToMainTab();
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(search_config_article_name);
			admin_instance.enter_input_field_element_by_key_name(desktop_search_box_field_name, "1");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 10
			test_step_details(10, "Verify the display of Search box");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(edl_home_instance.verify_search_box());
		} catch (Exception e) {
			log.error("Error while modifying the SearchBox configuration file :" + e.getLocalizedMessage());
			test_step_details(6, "Revert back the admin changes");
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(search_config_article_name);
			admin_instance.enter_input_field_element_by_key_name(desktop_search_box_field_name, "1");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			navigateTo(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(search_config_article_name);
			admin_instance.enter_input_field_element_by_key_name(desktop_search_box_field_name, "1");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@Test(priority = 2, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Token amount for consecutive search for the same keyword")
	public void verify_tokens_for_consecutive_search_for_same_keyword() throws Exception {
		String search_string = generateRandomString(5);

		// Step 1
		test_step_details(1, "Navigate to EDL and sign-in with valid credentials");
		edl_home_instance.click_register();
		String user_email = account_register_isntance.register_FullUser();
		assertTrue(edl_home_instance.verify_home());

		// Step 2
		test_step_details(2, "Validate the SERP and the token amount for the Search");
		edl_home_instance.search(search_string);
		lb_instance.close_level_up_lb();
		assertTrue(results.verify_search_results_url(search_string));
		int total_token_value = edl_home_instance.get_token_amount_from_uninav();
		assertTrue(total_token_value > 0);

		// Step 3
		test_step_details(3, "Validate the token amount for the same keyword Search");
		edl_home_instance.search(search_string);
		assertEqualsInt(edl_home_instance.get_token_amount_from_uninav(), total_token_value);

		// Step 4
		test_step_details(4, "Validate the tokens for the 26th Search");
		db_instance.updateDailySearchCount(user_email, 25);
		edl_home_instance.search(generateRandomString(6));
		assertEqualsInt(edl_home_instance.get_token_amount_from_uninav(), total_token_value);
	}
}
