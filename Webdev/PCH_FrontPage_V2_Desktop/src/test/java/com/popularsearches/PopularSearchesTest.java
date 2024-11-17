package com.popularsearches;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.GSAdminPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class PopularSearchesTest extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final GSAdminPage gs_admin_instance = GSAdminPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();

	private final String popular_search_article_name = "Popular Searches";
	private final String sidebar_popular_search_article_name = "Sidebar Popular Searches";
	private final String popular_searches_header_name = "POPULAR SEARCHES";
	private String popularsearch_config = "Config-Frontpage";
	private String config_key = "enable_searches_below_searchbar";

	@testId(test_id = "33733")
	@testCaseName(test_case_name = "Convert trending searches to popular searches using guided search[D]")
	@Test(priority = 1, description = "Verify the Popular Search section", groups = { "DESKTOP",
			"TABLET" }, testName = "33733:Convert trending searches to popular searches using guided search[D]")
	public void verify_popular_searches() throws Exception {
		try {
			// Step 1
			test_step_details(1, "Login to Frontpage with valid user");
			homepage_instance.click_SignIn();
			sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName2"), xmlReader(ENVIRONMENT, "ValidPassword"));
			lb_instance.close_welcome_optin_lb();
			assertTrue(homepage_instance.verify_Home());

			// Step 2
			test_step_details(2, "Verify the presence of Popular Searches Terms on Homepage");
			// assertTrue(homepage_instance.verify_popular_searches_header_bar());
			assertEquals(homepage_instance.get_popular_search_header_bar_text(), popular_searches_header_name);
			assertTrue(homepage_instance.verify_popular_search_terms());

			// Step 3
			test_step_details(3, "Verify the presence of Popular Searches Terms on all Category pages");
			LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					invokeBrowser(url);
					assertTrue(homepage_instance.verify_popular_searches_header_bar());
					assertEquals(homepage_instance.get_popular_search_header_bar_text(), popular_searches_header_name);
					assertTrue(homepage_instance.verify_popular_search_terms());
					// Break statement will stop the execution by verify the
					// Popular Search details for the first category page of the
					// menu. Added to save the execution time.
					break;
				}
			}

			// Step 4
			test_step_details(4, "Navigate to Joomla admin and UnPublish the Popular Searches");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popular_search_article_name);
			assertTrue(admin_instance.unpublish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 5
			test_step_details(5, "Verify the absence of Popular Searches Terms on Homepage");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertFalse(homepage_instance.verify_popular_searches_header_bar());
			assertFalse(homepage_instance.verify_popular_search_terms());

			// Step 6
			test_step_details(6, "Verify the absence of Popular Searches Terms on all Category pages");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					invokeBrowser(url);
					assertFalse(homepage_instance.verify_popular_searches_header_bar());
					assertFalse(homepage_instance.verify_popular_search_terms());
					// Break statement will stop the execution by verify the
					// Popular Search details for the first category page of the
					// menu. Added to save the execution time.
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error on disable the Popular Search Cusotm HTML article " + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popular_search_article_name);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popular_search_article_name);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "33733")
	@testCaseName(test_case_name = "Convert trending searches to popular searches using guided search[D]")
	@Test(priority = 2, description = "Verify the Popular Search NFSP Source & Segment", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "33733:Convert trending searches to popular searches using guided search[D]")
	public void verify_nfsp_for_popular_search() throws Exception {
		String expected_nfsp_source = "fppopular";
		String expected_nfsp_segment = "fppopdesktop.fppopdesktop";

		// Step 1
		test_step_details(1, "Click Popular Search and Verify the NFSP Source & Segment for Un Recognised User");
		assertTrue(homepage_instance.verify_popular_search_terms());
		homepage_instance.click_first_popular_search_word();
		assertTrue(serp_instance.verify_SERP_Completely());
		assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);

		// Step 2
		test_step_details(2, "Login as valid user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName2"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 3
		test_step_details(3, "Click Popular Search and Verify the NFSP Source & Segment for Recognised User ");
		assertTrue(homepage_instance.verify_popular_search_terms());
		homepage_instance.click_first_popular_search_word();
		assertTrue(serp_instance.verify_SERP_Completely());
		assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
	}

	@testId(test_id = "33733")
	@testCaseName(test_case_name = "Convert trending searches to popular searches using guided search[D]")
	@Test(priority = 3, description = "Verify the count of Popular Search terms", groups = { "DESKTOP",
			"TABLET" }, testName = "33733:Convert trending searches to popular searches using guided search[D]")
	public void verify_count_of_popular_search_terms() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and get the Popular Searches GS Id");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(popular_search_article_name);
		String expected_gs_id = env_property_file_reader("device.to.launch").equals("Desktop")
				? admin_instance.get_gs_id("Desktop Content")
				: admin_instance.get_gs_id("Tablet Content");
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Navigate to Frontpage and sign-in with valid credentials");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName2"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 3
		test_step_details(3, "Verify the GS Id on application");
		assertEquals(get_gs_id(1), expected_gs_id);

		// Step 4
		test_step_details(4, "Get the GS elements count from GS Admin");
		invokeBrowser(xmlReader(ENVIRONMENT, "GSAdmin_URL"));
		gs_admin_instance.log_in(xmlReader(ENVIRONMENT, "GSAdmin_Username"),
				xmlReader(ENVIRONMENT, "GSAdmin_Password"));
		gs_admin_instance.navigate_gs_article(expected_gs_id);
		gs_admin_instance.view_gs_article();
		int expected_gs_terms_count = gs_admin_instance.get_no_of_gs_elements();

		// Step 5
		test_step_details(5, "Navigate to Frontpage and verify the Popular Searches terms count");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEqualsInt(homepage_instance.get_popular_search_terms_count(), expected_gs_terms_count);
	}

	@testId(test_id = "33733,34443")
	@testCaseName(test_case_name = "Convert trending searches to popular searches using guided search[D],B-57410 Move Popular Searches and make it configurable[D&T]")
	@Test(priority = 4, description = "Verify the Sidebar Popular Search section", groups = { "DESKTOP",
			"TABLET" }, testName = "33733:Convert trending searches to popular searches using guided search[D],34443:B-57410 Move Popular Searches and make it configurable[D&T]")
	public void verify_sidebar_popular_searches() throws Exception {
		try {
			// Step 1
			test_step_details(1, "Login to Frontpage with valid user");
			homepage_instance.click_SignIn();
			sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName2"), xmlReader(ENVIRONMENT, "ValidPassword"));
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			doRefresh();

			// Step 2
			test_step_details(2, "Verify the presence of Sidebar Popular Searches Terms on Homepage");
			// assertTrue(homepage_instance.verify_sidebar_popular_searches_header_bar());
			assertEquals(homepage_instance.get_sidebar_popular_search_header_bar_text(), popular_searches_header_name);
			assertTrue(homepage_instance.verify_sidebar_popular_search_terms());

			// Step 3
			test_step_details(3, "Verify the presence of Popular Searches Terms on all Category pages");
			LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					invokeBrowser(url);
					assertTrue(homepage_instance.verify_sidebar_popular_searches_header_bar());
					assertEquals(homepage_instance.get_sidebar_popular_search_header_bar_text(),
							popular_searches_header_name);
					assertTrue(homepage_instance.verify_sidebar_popular_search_terms());
					// Break statement will stop the execution by verify the
					// Popular Search details for the first category page of the
					// menu. Added to save the execution time.
					break;
				}
			}

			// Step 4
			test_step_details(4, "Navigate to Joomla admin and UnPublish the Popular Searches");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sidebar_popular_search_article_name);
			assertTrue(admin_instance.unpublish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 5
			test_step_details(5, "Verify the absence of Popular Searches Terms on Homepage");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertFalse(homepage_instance.verify_sidebar_popular_searches_header_bar());
			assertFalse(homepage_instance.verify_sidebar_popular_search_terms());

			// Step 6
			test_step_details(6, "Verify the absence of Popular Searches Terms on all Category pages");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					invokeBrowser(url);
					assertFalse(homepage_instance.verify_sidebar_popular_searches_header_bar());
					assertFalse(homepage_instance.verify_sidebar_popular_search_terms());
					// Break statement will stop the execution by verify the
					// Popular Search details for the first category page of the
					// menu. Added to save the execution time.
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error on disable the Popular Search Cusotm HTML article " + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sidebar_popular_search_article_name);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sidebar_popular_search_article_name);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "33733")
	@testCaseName(test_case_name = "Convert trending searches to popular searches using guided search[D]")
	@Test(priority = 5, description = "Verify the Sidebar Popular Search NFSP Source & Segment", groups = { "DESKTOP",
			"TABLET",
			"SANITY" }, testName = "33733:Convert trending searches to popular searches using guided search[D]")
	public void verify_nfsp_for_sidebar_popular_search() throws Exception {
		String expected_nfsp_source = "fppopular";
		String expected_nfsp_segment = "fppopdesktop.fppopdesktop";

		// Step 1
		test_step_details(1,
				"Click Sidebar Popular Search and Verify the NFSP Source & Segment for Un Recognised User");
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_sidebar_popular_search_terms());
		homepage_instance.click_sidebar_first_popular_search_word();
		assertTrue(serp_instance.verify_SERP_Completely());
		assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);

		// Step 2
		test_step_details(2, "Login as valid user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName2"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		doRefresh();

		// Step 3
		test_step_details(3, "Click Sidebar Popular Search and Verify the NFSP Source & Segment for Recognised User ");
		assertTrue(homepage_instance.verify_sidebar_popular_search_terms());
		homepage_instance.click_sidebar_first_popular_search_word();
		assertTrue(serp_instance.verify_SERP_Completely());
		assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
	}

	@testId(test_id = "33733")
	@testCaseName(test_case_name = "Convert trending searches to popular searches using guided search[D]")
	@Test(priority = 6, description = "Verify the count of Sidebar Popular Search terms", groups = { "DESKTOP",
			"TABLET" }, testName = "33733:Convert trending searches to popular searches using guided search[D]")
	public void verify_count_of_sidebar_popular_search_terms() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and get the Sidebar Popular Searches GS Id");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(sidebar_popular_search_article_name);
		String expected_gs_id = env_property_file_reader("device.to.launch").equals("Desktop")
				? admin_instance.get_gs_id("Desktop Content")
				: admin_instance.get_gs_id("Tablet Content");
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Navigate to Frontpage and sign-in with valid credentials");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName2"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		doRefresh();

		// Step 3
		test_step_details(3, "Verify the GS Id on application");
		assertEquals(get_gs_id(1), expected_gs_id);

		// Step 4
		test_step_details(4, "Get the GS elements count from GS Admin");
		invokeBrowser(xmlReader(ENVIRONMENT, "GSAdmin_URL"));
		gs_admin_instance.log_in(xmlReader(ENVIRONMENT, "GSAdmin_Username"),
				xmlReader(ENVIRONMENT, "GSAdmin_Password"));
		gs_admin_instance.navigate_gs_article(expected_gs_id);
		gs_admin_instance.view_gs_article();
		int expected_gs_terms_count = gs_admin_instance.get_no_of_gs_elements();

		// Step 5
		test_step_details(5, "Navigate to Frontpage and verify the Popular Searches terms count");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEqualsInt(homepage_instance.get_sidebar_popular_search_terms_count(), expected_gs_terms_count);
	}

	@testId(test_id = "34443")
	@testCaseName(test_case_name = "Move Popular Searches and make it configurable[D&T]")
	@Test(priority = 7, description = "Verify the Popular search position based on the Admin setting", groups = {
			"DESKTOP", "TABLET" }, testName = "34443:Move Popular Searches and make it configurable[D&T]")
	public void verify_popular_search_position() throws Exception {
		// Step 1
		try {
			test_step_details(1, "Navigate to admin and change the value to 0 ");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popularsearch_config);
			admin_instance.enter_input_field_element_by_key_name(config_key, "0");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 2
			test_step_details(2, "verify the popular searches it should be under the openx");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(homepage_instance.verify_openx_above_popoular_search());

			// Step 3:
			test_step_details(3, "Navigate to admin and change the value to 1 ");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popularsearch_config);
			admin_instance.enter_input_field_element_by_key_name(config_key, "1");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 4:
			test_step_details(4, "verify the popular searches it should be under the searchbar");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(homepage_instance.verify_popular_search_below_search_bar());

			// Step 5:
			test_step_details(5, "Navigate to admin and removing the value");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popularsearch_config);
			admin_instance.clear_input_field_element_by_key_name(config_key);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 6:
			test_step_details(6, "verify the popular searches it should be under the searchbar");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(homepage_instance.verify_popular_search_below_search_bar());
		} catch (Exception e) {
			log.error("Error in the Popular search position configurable :" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popularsearch_config);
			admin_instance.enter_key_value_elements_config(config_key, "0");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			log.info("Finally block to Revert the admin changes of Popular search position.");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(popularsearch_config);
			admin_instance.enter_key_value_elements_config(config_key, "0");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
}
