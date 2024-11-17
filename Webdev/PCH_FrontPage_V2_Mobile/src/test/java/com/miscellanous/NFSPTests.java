package com.miscellanous;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class NFSPTests extends BaseClass {
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final String infospace_article_name = "Infospace";
	private final String nfsp_source_field_name = "nfspDefaults";
	private final String nfsp_segment_field_name = "nfspAccessIds";
	private String default_nfsp_source = null;
	private String nfsp_segment_json = null;

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
			String default_nfsp_source_json = admin_instance.get_input_field_element_by_key_name(nfsp_source_field_name)
					.getAttribute("value");
			default_nfsp_source = json_parser(default_nfsp_source_json, "MOBILE", "frontpage");
			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
					.getAttribute("value");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in retrieving default NFSP access id's: " + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "23497")
	@testCaseName(test_case_name = "FrontpageHighRiskRules_Desktop_Tablet_Mobile")
	@Test(priority = 1, description = "Verify the default and URL NFSP value for Recognised User", groups = { "MOBILE",
			"SANITY" }, testName = "23497:FrontpageHighRiskRules_Desktop_Tablet_Mobile")
	public void verify_nfsp_after_5_search_for_unrec_user() throws Exception {

		// Step 1
		test_step_details(1, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 2
		test_step_details(2, "Verify the Default NFSP value");
		String expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), default_nfsp_source);

		// Step 3
		test_step_details(3, "Verify the NFSP value after 6 searches for Unrecognised user");
		for (int loop = 0; loop < 6; loop++) {
			serp_instance.search(randomString(5, 6));
		}
		// We don't have an option to store the UnRecgonised user details in
		// DB. NFSP will be same for all the searches. Script will be reworked
		// once it is implemented.
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), default_nfsp_source);

		// Step 4
		test_step_details(4, "Verify the NFSP value by passing in URL");
		String url_nfsp = "portpc2v2";
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "/?nfsp=" + url_nfsp);
		serp_instance.search(generateRandomString(5));
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), url_nfsp);
	}

	@testId(test_id = "34454")
	@testCaseName(test_case_name = "new nfsp related checklist")
	@Test(priority = 2, description = "Verify the default and URL NFSP value for Recognised User", groups = { "MOBILE",
			"SANITY" }, testName = "34454:new nfsp related checklist")
	public void verify_default_nfsp_for_rec_user() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.click_register();
		register_instance.register_full_user_with_optin();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the Default NFSP value");
		String expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), default_nfsp_source);

		// Step 4
		test_step_details(4, "Verify the NFSP value by passing in URL");
		String url_nfsp = "portpc2v2";
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "/?nfsp=" + url_nfsp);
		serp_instance.search(generateRandomString(5));
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), url_nfsp);
	}

	@testId(test_id = "23497,23083")
	@testCaseName(test_case_name = "FrontpageHighRiskRules_Desktop_Tablet_Mobile,CreateTestNFSP_Desktop_Tablet_Mobile")
	@Test(priority = 3, description = "Verify the NFSP for Recognised User after 27th search", groups = { "MOBILE",
			"SANITY" }, testName = "23497:FrontpageHighRiskRules_Desktop_Tablet_Mobile,23083:CreateTestNFSP_Desktop_Tablet_Mobile")
	public void verify_nfsp_for_rec_user_after_27_search() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(5, "Verify the ALGO only results after 27th search for Rec. user via NFSP value");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 27);
		serp_instance.search(randomString(5, 6));
		String expected_nfsp_source = "mx_m";
		String expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), expected_nfsp_source);

	}

	@testId(test_id = "34457")
	@testCaseName(test_case_name = "Mobile_NFSP")
	@Test(priority = 4, description = "Verify the Mobile NFSP", groups = { "MOBILE",
			"SANITY" }, testName = "34454:new nfsp related checklist")
	public void nfsp_priority_by_url() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.click_register();
		register_instance.register_full_user_with_optin();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		homepage_instance.search(randomString(5, 6));
		assertTrue(serp_instance.verify_SERP_Completely());

		// Step 3
		test_step_details(3, "Verify the Default NFSP value");
		String expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), default_nfsp_source);

		// Step 4
		test_step_details(4, "Verify the NFSP value by passing in URL");
		String url_nfsp = "portpc2v2";
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "/?nfsp=" + url_nfsp);
		serp_instance.search(generateRandomString(5));
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), url_nfsp);

		// Step 5
		test_step_details(5, "Verify the NFSP value by passing in URL on the existing session");
		url_nfsp = "fptest1";
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "/?nfsp=" + url_nfsp);
		serp_instance.search(generateRandomString(5));
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), url_nfsp);

		// Step 6
		test_step_details(6, "Verify the NFSP segment value by passing in wrong device access id in URL");
		url_nfsp = "fptab";
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "/?nfsp=" + url_nfsp);
		serp_instance.search(generateRandomString(5));
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), default_nfsp_source);

		// Step 7
		test_step_details(7, "Verify the NFSP segment value for Popular searches");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_popular_search_word();
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, "fppopular");
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), "fppopular");
	}

	@testId(test_id = "34491")
	@testCaseName(test_case_name = "B-55856 Test new nfsp for system1[ DTM]")
	@Test(priority = 5, description = "Verify the NFSP prioritied by assigning user and algo segments", groups = {
			"MOBILE" }, testName = "34491:B-55856 Test new nfsp for system1[ DTM]")
	public void nfsp_priority_over_segmentation() throws Exception {
		String algo_segment = "SES";
		String url_nfsp_1 = "fptest1";
		String url_nfsp_2 = "fptest2";
		String search_term = generateRandomString(6);

		// Step 1
		test_step_details(1, "Create a Full Reg user from RF and assign algo segment to it");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createGoldUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instance.set_segment_to_user(user_details[0], algo_segment);

		// Step 2
		test_step_details(2, "Login to FP with the segmented user and verify the NFSP details");
		delete_session_cookies();
		invokeBrowser(user_details[1]);
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();

		String expected_nfsp_source = "mx_m";
		String expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source);
		LinkedHashMap<String, String> log_details = db_instance.get_search_activity_log_details(user_details[0]);
		// Verify the Search Activity log details
		assertEquals(log_details.get("useragenttype"), "MOBILE");
		assertEquals(log_details.get("nfsp"), expected_nfsp_source);
		assertEquals(log_details.get("search_term"), search_term);

		// Step 3
		test_step_details(3, "Create a Full Reg user from RF");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		user_details = cs_instance.createGoldUser();
		user_details[1] = user_details[1].replace("/?", "/search?q=" + search_term + "&");
		String url = user_details[1] + "&nfsp=" + url_nfsp_1;
		invokeBrowser(url);
		lb_instance.close_level_up_lb();
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp_1);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), url_nfsp_1);
		log_details = db_instance.get_search_activity_log_details(user_details[0]);
		// Verify the Search Activity log details
		assertEquals(log_details.get("useragenttype"), "MOBILE");
		assertIsStringContains(log_details.get("nfsp"), url_nfsp_1);
		assertEquals(log_details.get("search_term"), search_term);

		// Passing the another NFSP in the URL
		delete_session_cookies();
		invokeBrowser(url + "&nfsp=" + url_nfsp_2);
		expected_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp_2);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment);
		assertIsStringContains(get_nfsp_source_from_page_source(1), url_nfsp_2);
		log_details = db_instance.get_search_activity_log_details(user_details[0]);
		// Verify the Search Activity log details
		assertEquals(log_details.get("useragenttype"), "MOBILE");
		assertIsStringContains(log_details.get("nfsp"), url_nfsp_2);
		assertEquals(log_details.get("search_term"), search_term);
	}
}
