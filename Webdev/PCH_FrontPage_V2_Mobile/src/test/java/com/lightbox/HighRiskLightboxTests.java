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
	private final String infospace_article_name = "Infospace";
	private final String nfsp_segment_field_name = "nfspAccessIds";
	private String nfsp_segment_json = null;

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
			log.error("Error in DB handling: " + e.getLocalizedMessage());
		}
	}

	/**
	 * As pre-requisite it will collect the NFSP source and segments/access-id's
	 * from the admin.
	 * 
	 * @author mperumal
	 * @throws Exception
	 */
	@BeforeClass
	public void get_nfsp_from_admin() throws Exception {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(infospace_article_name);
			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
					.getAttribute("value");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in NFSP accedd retrieving: " + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 1, description = "Verify the Rapid Search LightBox for Recognised User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_rapid_lb_for_rec_user() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(generateRandomString(6));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the Rapid Search LB");
		for (int loop = 0; loop < 6; loop++) {
			serp_instance.click_search_button();
		}
		assertTrue(lb_instance.verify_lb_accept_button());

		// Step 4
		test_step_details(4, "Verify it redirects to homepage after accepts the Rapid Search LB");
		lb_instance.accept_lb();
		sleepFor(2);
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL"));
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 2, description = "Verify the 26th Maximum Search LightBox for Recognised User", groups = {
			"MOBILE", "SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_25th_search_lb() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(generateRandomString(5));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the 25th Maximum Search LB");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 24);
		serp_instance.search(generateRandomString(5));
		assertTrue(lb_instance.verify_lb_accept_button());
		lb_instance.close_lb();

		// Step 4
		test_step_details(4, "Verify the Tokens are not awarded after the 25th Maximum Search LB");
		int token_amount = homepage_instance.get_token_amount_from_uni_nav();
		serp_instance.search(generateRandomString(5));
		assertEqualsInt(token_amount, homepage_instance.get_token_amount_from_uni_nav());

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
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 3, description = "Verify the 40th Maximum Search LightBox for Recognised User", groups = {
			"MOBILE", "SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_40th_search_lb() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(generateRandomString(5));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the 40th Search LB");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 39);
		serp_instance.search(generateRandomString(5));
		assertTrue(lb_instance.verify_lb_accept_button());
		lb_instance.close_lb();
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 4, description = "Verify the 75th Maximum Search LightBox for Recognised User", groups = {
			"MOBILE", "SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_75th_search_lb() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(generateRandomString(5));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the 75th Search LB");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 74);
		serp_instance.search(generateRandomString(5));
		assertTrue(lb_instance.verify_lb_accept_button());
		lb_instance.close_lb();
	}

	@testId(test_id = "34330")
	@testCaseName(test_case_name = "B-54221 - [M Redesign] Lightboxes")
	@Test(priority = 5, description = "Verify the 500th Search LightBox for Recognised User", groups = { "MOBILE",
			"SANITY" }, testName = "34330:B-54221 - [M Redesign] Lightboxes")
	public void verify_500th_search_lb() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(generateRandomString(5));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the 500th Search LB");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 499);
		serp_instance.search(generateRandomString(5));
		assertTrue(lb_instance.verify_500_search_lb());
		lb_instance.click_500_ok_button();

		// Step 4
		test_step_details(4, "Verify the 500th Search LB after reseting the search count to below 500");
		serp_instance.search(generateRandomString(5));
		lb_instance.click_500_ok_button();
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 5);
		serp_instance.search(generateRandomString(5));
		assertTrue(lb_instance.verify_500_search_lb());
		lb_instance.click_500_ok_button();
		db_instance.updateSearchingEnabledStatus(xmlReader(ENVIRONMENT, "ValidUserName1"), 1);
		serp_instance.search(generateRandomString(5));
		assertFalse(lb_instance.verify_500_search_lb());
	}
}
