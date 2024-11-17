package com.nfsp;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class NFSPTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String infospace_article_name = "config-Infospace";
	private final String nfsp_source_field_name = "nfspDefaults";
	private final String nfsp_segment_field_name = "nfspAccessIds";
	private String default_nfsp_source = null;
	private String nfsp_segment_json = null;
	private final String risk_management = "Config-riskmanagement";
	private final String algo_only_searches_unrecognized = "algo_only_searches_unrecognized";
	private static String algo_only_searches_unrecognized_search_value;
	private final String algo_only_searches_recognized = "algo_only_searches_recognized";
	private static String algo_only_searches_recognized_search_value;
	private String algo_segment_max_nfsp_source_desktop = "mx_d";
	private String algo_segment_max_nfsp_source_tablet = "mx_t";
	private static String expected_max_nfsp_segment_desktop;
	private static String expected_max_nfsp_segment_tablet;

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
	 * @author vsankar
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
			String default_nfsp_source_json = admin_instance.get_input_field_element_by_key_name(nfsp_source_field_name)
					.getAttribute("value");
			default_nfsp_source = DEVICE.equalsIgnoreCase("Desktop")
					? json_parser(default_nfsp_source_json, "DESKTOP", "frontpage")
					: json_parser(default_nfsp_source_json, "TABLET", "frontpage");
			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
					.getAttribute("value");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(risk_management);
			algo_only_searches_unrecognized_search_value = admin_instance
					.get_input_field_element_by_key_name(algo_only_searches_unrecognized).getAttribute("value");
			algo_only_searches_recognized_search_value = admin_instance
					.get_input_field_element_by_key_name(algo_only_searches_recognized).getAttribute("value");
			admin_instance.close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the Before Class: " + e.getLocalizedMessage());
			System.out.println("Error in the Before Class: " + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "[D/T/M] FP: NFSP Validation")
	@Test(priority = 1, description = "Verify the NFSP value for UnRecognised User after 6 search", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_after_5_search_for_unrec_user() throws Exception {
		test_Method_details(1, "Verify the NFSP value for UnRecognised User after 6 search");

		// Step 1
		test_step_details(1, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Defautl NFSP value");
		String expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), default_nfsp_source);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the NFSP value after 6 searches for Unrecognised user");
		int search_count = Integer.parseInt(algo_only_searches_unrecognized_search_value);
		for (int loop = 0; loop < search_count; loop++) {
			serp_instance.search(randomString(5, 6));
		}
		expected_max_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json,
				algo_segment_max_nfsp_source_desktop);
		expected_max_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json,
				algo_segment_max_nfsp_source_tablet);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_max_nfsp_segment_desktop);
			assertEquals(get_nfsp_source_from_page_source(1), algo_segment_max_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_max_nfsp_segment_tablet);
			assertEquals(get_nfsp_source_from_page_source(1), algo_segment_max_nfsp_source_tablet);
		}
		switchToMainTab();
		step_validator(3, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 2, description = "Verify the default NFSP value for Recognised User", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_default_nfsp_for_rec_user() throws Exception {
		test_Method_details(2, "Verify the default NFSP value for Recognised User");
		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com  and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		homepage_instance.click_Register();
		register_instance.register_FullUser();
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
		test_step_details(3, "Verify the Defautl NFSP value");
		String expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), default_nfsp_source);
		switchToMainTab();
		step_validator(3, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 3, description = "Verify the NFSP for Recognised User after 27th search", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_for_rec_user_after_27_search() throws Exception {
		test_Method_details(3, "Verify the NFSP for Recognised User after 27th search");
		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com and sign-in with valid credentials");
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
		test_step_details(3, "Verify the ALGO only results after 27th search for Rec. user via NFSP value");
		int search_count = Integer.parseInt(algo_only_searches_recognized_search_value);
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), search_count);
		serp_instance.search(randomString(5, 6));
		String expected_max_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json,
				algo_segment_max_nfsp_source_desktop);
		String expected_max_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json,
				algo_segment_max_nfsp_source_tablet);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_max_nfsp_segment_desktop);
			assertEquals(get_nfsp_source_from_page_source(1), algo_segment_max_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_max_nfsp_segment_tablet);
			assertEquals(get_nfsp_source_from_page_source(1), algo_segment_max_nfsp_source_tablet);
		}
		switchToMainTab();
		step_validator(3, true);
	}
}
