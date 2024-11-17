package com.lightbox;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HighRiskLightboxTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String infospace_article_name = "config-Infospace";
	private final String nfsp_segment_field_name = "nfspAccessIds";
	private String nfsp_segment_json = null;
	private final String risk_management = "Config-riskmanagement";
	private final String duplicate_search_max_rate = "duplicate_search_max_rate";
	private static String duplicate_search_max_rate_search_value;
	private final String max_chances_per_day = "max_chances_per_day";
	private static String max_chances_per_day_search_value;
	private final String search_warnings_first = "search_warnings_first";
	private static String search_warnings_first_search_value;
	private final String search_warnings_later = "search_warnings_later";
	private static String search_warnings_later_search_value;
	private final String max_searches_per_day = "max_searches_per_day";
	private static String max_searches_per_day_search_value;

	/**
	 * As pre-requisite it will collect the NFSP source and segments/access-id's
	 * from the admin.
	 * 
	 * @author mperumal
	 * @throws Exception
	 */

	/**
	 * Search limits will fetch from admin module to reset the daily search count in
	 * db and perform search actions
	 * 
	 * @author Vinoth
	 * @throws Exception
	 */

	@BeforeClass
	public void get_nfsp_from_admin() {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(infospace_article_name);
			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
					.getAttribute("value");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(risk_management);
			duplicate_search_max_rate_search_value = admin_instance
					.get_input_field_element_by_key_name(duplicate_search_max_rate).getAttribute("value");
			max_chances_per_day_search_value = admin_instance.get_input_field_element_by_key_name(max_chances_per_day)
					.getAttribute("value");
			search_warnings_first_search_value = admin_instance
					.get_input_field_element_by_key_name(search_warnings_first).getAttribute("value");
			search_warnings_later_search_value = admin_instance
					.get_input_field_element_by_key_name(search_warnings_later).getAttribute("value");
			max_searches_per_day_search_value = admin_instance.get_input_field_element_by_key_name(max_searches_per_day)
					.getAttribute("value");
			admin_instance.close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the Before Class: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Reset the HR count to desired value
	 * 
	 * @throws SQLException
	 */
	@BeforeClass
	public void reset_daily_count_of_default_user() throws SQLException {
		try {
			db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 5);
			db_instance.updateSearchingEnabledStatus(xmlReader(ENVIRONMENT, "ValidUserName1"), 1);
		} catch (Exception e) {
			log.error("Error in the Before Class: " + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 1, description = "Verify the Rapid Search LightBox for Recognised User", groups = { "DESKTOP",
			"TABLET",
			"SANITY" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_rapid_lb_for_rec_user() throws Exception {
		test_Method_details(1, "Verify the Rapid Search LightBox for Recognised User");

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Rapid Search LB");
		int search_count = Integer.parseInt(duplicate_search_max_rate_search_value);
		for (int loop = 0; loop < search_count; loop++) {
			serp_instance.click_search_button();
		}
		sleepFor(10);
		assertTrue(lb_instance.verify_lb_accept_button());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify it redirects to homepage after accepts the Rapid Search LB");
		lb_instance.accept_lb();
		sleepFor(2);
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL"));
		switchToMainTab();
		step_validator(4, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 2, description = "Verify the 26th Maximum Search LightBox for Recognised User", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_25th_search_lb() throws Exception {
		test_Method_details(2, "Verify the 26th Maximum Search LightBox for Recognised User");

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the 25th Maximum Search LB");
		int search_count = Integer.parseInt(max_chances_per_day_search_value);
		search_count = search_count - 1;
		System.out.println("Verify the 25th Maximum Search LB: " + search_count);
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), search_count);
		serp_instance.search(randomString(5, 6));
		assertTrue(lb_instance.verify_lb_accept_button());
		lb_instance.close_lb();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Tokens are not awarded after the 25th Maximum Search LB");
		int token_amount = homepage_instance.get_Tokens();
		serp_instance.search(randomString(5, 6));
		assertEqualsInt(token_amount, homepage_instance.get_Tokens());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the ALGO only results after 27th search for Rec. user via NFSP segment");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 27);
		serp_instance.search(randomString(5, 6));
		String expected_nfsp_source_desktop = "mx_d";
		String expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json,
				expected_nfsp_source_desktop);
		String expected_nfsp_source_tablet = "mx_t";
		String expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json,
				expected_nfsp_source_tablet);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
			assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_tablet);
			assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source_tablet);
		}
		switchToMainTab();
		step_validator(5, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 3, description = "Verify the 40th Maximum Search LightBox for Recognised User", groups = {
			"DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_40th_search_lb() throws Exception {
		test_Method_details(3, "Verify the 40th Maximum Search LightBox for Recognised User");

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the 40th Search LB");
		int search_count = Integer.parseInt(search_warnings_first_search_value);
		search_count = search_count - 1;
		System.out.println("Verify the 40th Search LB: " + search_count);
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), search_count);
		serp_instance.search(randomString(5, 6));
		assertTrue(lb_instance.verify_lb_accept_button());
		lb_instance.close_lb();
		switchToMainTab();
		step_validator(3, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 4, description = "Verify the 75th Maximum Search LightBox for Recognised User", groups = {
			"DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_75th_search_lb() throws Exception {
		test_Method_details(4, "Verify the 75th Maximum Search LightBox for Recognised User");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the 75th Search LB");
		int search_count = Integer.parseInt(search_warnings_later_search_value);
		search_count = search_count - 1;
		System.out.println("Verify the 75th Search LB: " + search_count);
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), search_count);
		serp_instance.search(randomString(5, 6));
		assertTrue(lb_instance.verify_lb_accept_button());
		lb_instance.close_lb();
		switchToMainTab();
		step_validator(3, true);
	}

	@testId(test_id = "RT-04217")
	@testCaseName(test_case_name = "[D/T/M]FrontpageHighriskrules_lightbox")
	@Test(priority = 5, description = "Verify the 500th Search LightBox for Recognised User", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04217:[D/T/M]FrontpageHighriskrules_lightbox")
	public void verify_500th_search_lb() throws Exception {
		test_Method_details(5, "Verify the 500th Search LightBox for Recognised User");

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the 500th Search LB");
		int search_count = Integer.parseInt(max_searches_per_day_search_value);
		search_count = search_count - 1;
		System.out.println("Verify the 500th Search LB: " + search_count);
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), search_count);
		serp_instance.search(randomString(5, 6));
		assertTrue(lb_instance.verify_500_search_lb());
		lb_instance.click_500_ok_button();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the 500th Search LB after reseting the search count to below 500");
		serp_instance.search(randomString(5, 6));
		lb_instance.click_500_ok_button();
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 5);
		serp_instance.search(randomString(5, 6));
		assertTrue(lb_instance.verify_500_search_lb());
		lb_instance.click_500_ok_button();
		db_instance.updateSearchingEnabledStatus(xmlReader(ENVIRONMENT, "ValidUserName1"), 1);
		serp_instance.search(randomString(5, 6));
		assertFalse(lb_instance.verify_500_search_lb());
		switchToMainTab();
		step_validator(4, true);
	}
}