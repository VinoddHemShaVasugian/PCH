package com.nfsp;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.EDLHomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.DB_Connector;

public class NFSPTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();

	private final String infospace_article_name = "Infospace";
	private final String nfsp_source_field_name = "nfspDefaults";
	private final String nfsp_segment_field_name = "nfspAccessIds";
	private final String application_name = "frontpage";
	private String default_nfsp_source = "edlsearch";
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
			/*String default_nfsp_source_json = admin_instance.get_input_field_element_by_key_name(nfsp_source_field_name)
					.getAttribute("value");
			default_nfsp_source = DEVICE.equalsIgnoreCase("Desktop")
					? json_parser(default_nfsp_source_json, "DESKTOP", application_name)
					: json_parser(default_nfsp_source_json, "TABLET", application_name);*/
			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
					.getAttribute("value");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the NFSP value retrieving: " + e.getLocalizedMessage());
		}
	}

	@Test(priority = 1, description = "Verify the NFSP value for UnRecognised User after 6 search", groups = {
			"DESKTOP", "TABLET", "SANITY" })
	public void verify_nfsp_after_5_search_for_unrec_user() throws Exception {

		// Step 1
		test_step_details(1, "Search a keyword and Verify the SERP");
		edl_home_instance.search(randomString(5, 6));
		switchToNewTab();
		lb_instance.close_level_up_lb();
		assertTrue(edl_home_instance.verfiy_date_on_page());
		assertTrue(edl_home_instance.verify_edl_title("Search"));

		// Step 2
		test_step_details(2, "Verify the Defautl NFSP value");
		String expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), default_nfsp_source);

		// Step 3
		test_step_details(3, "Verify the NFSP value after 6 searches for Unrecognised user");
		for (int loop = 0; loop < 6; loop++) {
			edl_home_instance.search(randomString(5, 6));
		}
		// We don't have an option to store the UnRecgonised user details in
		// DB. NFSP will be same for all the searches. Script will be reworked
		// once it is implemented.
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), default_nfsp_source);
		switchToMainTab();
	}

	@Test(priority = 2, description = "Verify the default NFSP value for Recognised User", groups = { "DESKTOP",
			"TABLET", "SANITY" })
	public void verify_default_nfsp_for_rec_user() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to EDL and create a Full Reg user");
		edl_home_instance.click_register();
		register_instance.register_FullUser();
		assertTrue(edl_home_instance.verify_home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		edl_home_instance.search(randomString(5, 6));
		switchToNewTab();
		lb_instance.close_level_up_lb();
		assertTrue(edl_home_instance.verfiy_date_on_page());
		assertTrue(edl_home_instance.verify_edl_title("Search"));

		// Step 3
		test_step_details(3, "Verify the Default NFSP value");
		String expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), default_nfsp_source);
	}

	@Test(priority = 3, description = "Verify the NFSP for Recognised User after 27th search", groups = { "DESKTOP",
			"TABLET", "SANITY" })
	public void verify_nfsp_for_rec_user_after_27_search() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to FEDL and sign-in with valid credentials");
		edl_home_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(edl_home_instance.verify_home());

		// Step 2
		test_step_details(2, "Search a keyword and Verify the SERP");
		edl_home_instance.search(randomString(5, 6));
		switchToNewTab();
		lb_instance.close_level_up_lb();
		assertTrue(edl_home_instance.verfiy_date_on_page());
		assertTrue(edl_home_instance.verify_edl_title("Search"));

		// Step 3
		test_step_details(5, "Verify the ALGO only results after 27th search for Rec. user via NFSP value");
		db_instance.updateDailySearchCount(xmlReader(ENVIRONMENT, "ValidUserName1"), 27);
		edl_home_instance.search(randomString(5, 6));
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

	@Test(priority = 4, description = "Verify the NFSP prioritied by assigning user and algo segments", groups = {
			"DESKTOP", "TABLET" })
	public void nfsp_priority_over_segmentation() throws Exception {
		String user_segment = "SST";
		String algo_segment = "SES";
		String url_nfsp_1 = "fptest1";
		String url_nfsp_2 = "fptest2";

		// Step 1
		test_step_details(1, "Create a Full Reg user from RF and assign user segment to it");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createGoldUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instance.set_segment_to_user(user_details[0], user_segment);

		// Step 2
		test_step_details(2, "Login to EDL with the segmented user and verify the NFSP details");
		delete_session_cookies();
		invokeBrowser(user_details[1]);
		edl_home_instance.search(generateRandomString(6));
		lb_instance.close_level_up_lb();
		String expected_nfsp_source_desktop = "fpnew";
		String expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json,
				expected_nfsp_source_desktop);
		String expected_nfsp_source_tablet = "fpnew_t";
		String expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json,
				expected_nfsp_source_tablet);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
			assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_tablet);
			assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source_tablet);
		}

		// Step 3
		test_step_details(3, "Create a Full Reg user from RF and assign algo segment to it");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		user_details = cs_instance.createGoldUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instance.set_segment_to_user(user_details[0], algo_segment);

		// Step 4
		test_step_details(4, "Login to EDL with the segmented user and verify the NFSP details");
		delete_session_cookies();
		invokeBrowser(user_details[1]);
		edl_home_instance.search(generateRandomString(6));
		lb_instance.close_level_up_lb();
		expected_nfsp_source_desktop = "mx_d";
		expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, expected_nfsp_source_desktop);
		expected_nfsp_source_tablet = "mx_t";
		expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json, expected_nfsp_source_tablet);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
			assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_tablet);
			assertEquals(get_nfsp_source_from_page_source(1), expected_nfsp_source_tablet);
		}

		// Step 5
		test_step_details(5, "Create a Full Reg user from RF");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		user_details = cs_instance.createGoldUser();
		user_details[1] = user_details[1].replace("everydaylife/?", "everydaylife/search?q="+generateRandomString(6)+"&");
		String url = user_details[1] + "&nfsp=" + url_nfsp_1;	
		invokeBrowser(url);		
		lb_instance.close_level_up_lb();
		expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp_1);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), url_nfsp_1);
		// Passing the another NFSP in the URL
		delete_session_cookies();
		invokeBrowser(url + "&nfsp=" + url_nfsp_2);
		expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, url_nfsp_2);
		assertEquals(get_nfsp_segment_from_page_source(1), expected_nfsp_segment_desktop);
		assertEquals(get_nfsp_source_from_page_source(1), url_nfsp_2);
	}
}
