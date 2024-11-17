package com.nfsp;

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

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class NFSPSegmentTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final CentralServices_Page cs_instnace = CentralServices_Page.getInstance();
	private final String infospace_article_name = "config-Infospace";
	private final String nfsp_segment_field_name = "nfspAccessIds";
	private final String algo_only_segment = "algo_only_segment";
	private String[] algo_only_segments = null;
	private String nfsp_segment_json = null;
	private String algo_nfsp_source_desktop = "mx_d";
	private String algo_nfsp_source_tablet = "mx_t";
	private String[] segmented_nfsp_source = { "fpnew", "fps1test" };
	private String user_segment = "SST";
	private String nfsp_source = "s1fp8";
	private String default_nfsp_source = "portv2";

	/**
	 * Reading nfsp source&segment and storing for execution
	 * 
	 * @author vsankar
	 */
	@BeforeClass
	public void beforeClass() {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(infospace_article_name);
			String temp = admin_instance.get_input_field_element_by_key_name(algo_only_segment).getAttribute("value");
			algo_only_segments = temp.replace("\"", "").replace("[", "").replace("]", "").split(",");
			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
					.getAttribute("value");
			admin_instance.close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the Before Class: " + e.getLocalizedMessage());
			System.out.println("Error in the Before Class: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Common method to Do a search and verify the source&segment
	 * 
	 * @return True
	 * @param segment
	 * @param source
	 * 
	 * @author vsankar
	 */

	public boolean searchAndVerifyNFSP(String segment, String source) {
		try {
			homepage_instance.search(randomString(5, 6));
			switchToNewTab();
			assertTrue(serp_instance.verify_SERP_Completely());
			assertEquals(get_nfsp_segment_from_page_source(1), segment);
			assertEquals(get_nfsp_source_from_page_source(1), source);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 1, description = "Verify NFSP value for algo only segment ([\"SES\",\"ESS\",\"SN\"]) Gold users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_for_algo_only_segment_gold_user() throws Exception {
		String expected_nfsp_segment_desktop = null;
		String expected_nfsp_segment_tablet = null;
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_tablet);
		}

		test_Method_details(1, "Verify the NFSP value for algo only segment Gold user");
		// Step 1
		test_step_details(1, "Create a gold user and assign algo only segment");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], algo_only_segments[0]);
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and login");
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Launch URL with different NFSP and Verify the algo NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 2, description = "Verify NFSP value for segment ([\"SST\"]) Gold users", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_specific_segment_for_gold_user() throws Exception {
		String expected_nfsp_segment_desktop_first_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[0]);
		String expected_nfsp_segment_desktop_second_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[1]);
		test_Method_details(2, "Verify the NFSP value for segment user");
		// Step 1
		test_step_details(1, "Create a gold user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		System.out.println(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_first_search, segmented_nfsp_source[0]));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_second_search, segmented_nfsp_source[1]));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 3, description = "Verify NFSP value for users redirected with nfsp for Gold users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_in_url_for_gold_user() throws Exception {
		String expected_url_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, nfsp_source);
		String expected_default_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		test_Method_details(3, "Verify the NFSP value for segment user");
		// Step 1
		test_step_details(1, "Create a gold user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Launch URL with different NFSP and Verify NFSP value in browser console");
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Login and Verify the default NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 4, description = "Verify NFSP value for algo only segment ([\"SES\",\"ESS\",\"SN\"]) Silver users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_for_algo_only_segment_silver_user() throws Exception {
		String expected_nfsp_segment_desktop = null;
		String expected_nfsp_segment_tablet = null;
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_tablet);
		}

		test_Method_details(4, "Verify the NFSP value for algo only segment Silver user");
		// Step 1
		test_step_details(1, "Create a silver user and assign algo only segment");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSilverUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], algo_only_segments[0]);
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and login");
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertFalse(homepage_instance.verify_Home());
		homepage_instance.click_complete_registration();
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Launch URL with different NFSP and Verify the algo NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 5, description = "Verify NFSP value for segment ([\"SST\"]) silver users", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_specific_segment_for_silver_user() throws Exception {
		String expected_nfsp_segment_desktop_first_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[0]);
		String expected_nfsp_segment_desktop_second_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[1]);
		test_Method_details(5, "Verify the NFSP value for segment silver user");
		// Step 1
		test_step_details(1, "Create a silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSilverUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		assertFalse(homepage_instance.verify_Home());
		homepage_instance.click_complete_registration();
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_first_search, segmented_nfsp_source[0]));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_second_search, segmented_nfsp_source[1]));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 6, description = "Verify NFSP value for users redirected with nfsp for silver users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_in_url_for_silver_user() throws Exception {
		String expected_url_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, nfsp_source);
		String expected_default_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		test_Method_details(6, "Verify the NFSP value for segment silver user");
		// Step 1
		test_step_details(1, "Create a silver user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSilverUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Launch URL with different NFSP and Verify NFSP value in browser console");
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		assertFalse(homepage_instance.verify_Home());
		homepage_instance.click_complete_registration();
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Login and Verify the default NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 7, description = "Verify NFSP value for algo only segment ([\"SES\",\"ESS\",\"SN\"]) Mini register users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_for_algo_only_segment_mini_reg_user() throws Exception {
		String expected_nfsp_segment_desktop = null;
		String expected_nfsp_segment_tablet = null;
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_tablet);
		}

		test_Method_details(7, "Verify the NFSP value for algo only segment mini register user");
		// Step 1
		test_step_details(1, "Create a mini reg user and assign algo only segment");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createMiniReguser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], algo_only_segments[0]);
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and Complete the Registration");
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Launch URL with different NFSP and Verify the algo NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 8, description = "Verify NFSP value for segment ([\"SST\"]) mini reg users", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_specific_segment_for_mini_reg_user() throws Exception {
		String expected_nfsp_segment_desktop_first_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[0]);
		String expected_nfsp_segment_desktop_second_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[1]);
		test_Method_details(8, "Verify the NFSP value for segment mini reg user");
		// Step 1
		test_step_details(1, "Create a mini reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createMiniReguser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_first_search, segmented_nfsp_source[0]));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_second_search, segmented_nfsp_source[1]));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 9, description = "Verify NFSP value for users redirected with nfsp for Mini reg users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_in_url_for_mini_reg_user() throws Exception {
		String expected_url_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, nfsp_source);
		String expected_default_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		test_Method_details(9, "Verify the NFSP value for segment silver user");
		// Step 1
		test_step_details(1, "Create a Mini reg user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createMiniReguser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Launch URL with different NFSP and Verify NFSP value in browser console");
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Login and Verify the default NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 10, description = "Verify NFSP value for algo only segment ([\"SES\",\"ESS\",\"SN\"]) social users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_for_algo_only_segment_social_user() throws Exception {
		String expected_nfsp_segment_desktop = null;
		String expected_nfsp_segment_tablet = null;
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			expected_nfsp_segment_desktop = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_desktop);
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			expected_nfsp_segment_tablet = get_nfsp_segment_from_json(nfsp_segment_json, algo_nfsp_source_tablet);
		}

		test_Method_details(10, "Verify the NFSP value for algo only segment social user");
		// Step 1
		test_step_details(1, "Create a social user and assign algo only segment");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSocialUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], algo_only_segments[0]);
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and complete the registration");
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify the algo NFSP value");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Launch URL with different NFSP and Verify the algo NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop, algo_nfsp_source_desktop));
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
			Assert.assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_tablet, algo_nfsp_source_tablet));
		}
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 11, description = "Verify NFSP value for segment ([\"SST\"]) social users", groups = { "DESKTOP",
			"TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_specific_segment_for_social_user() throws Exception {
		String expected_nfsp_segment_desktop_first_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[0]);
		String expected_nfsp_segment_desktop_second_search = get_nfsp_segment_from_json(nfsp_segment_json,
				segmented_nfsp_source[1]);
		test_Method_details(11, "Verify the NFSP value for segment social user");
		// Step 1
		test_step_details(1, "Create a social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSocialUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Assign specific segment to user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], user_segment);
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_first_search, segmented_nfsp_source[0]));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value for assigned segment");
		assertTrue(searchAndVerifyNFSP(expected_nfsp_segment_desktop_second_search, segmented_nfsp_source[1]));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);
	}

	@testId(test_id = "RT-04216")
	@testCaseName(test_case_name = "FP: NFSP Validation")
	@Test(priority = 12, description = "Verify NFSP value for users redirected with nfsp for Social users", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04216: [D/T/M] FP: NFSP Validation")
	public void verify_nfsp_in_url_for_social_user() throws Exception {
		String expected_url_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, nfsp_source);
		String expected_default_nfsp_segment = get_nfsp_segment_from_json(nfsp_segment_json, default_nfsp_source);
		test_Method_details(12, "Verify the NFSP value for segment Social user");
		// Step 1
		test_step_details(1, "Create a Social user and login");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSocialUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Launch URL with different NFSP and Verify NFSP value in browser console");
		invokeBrowser(user_details[1] + "&nfsp=" + nfsp_source);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a first search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Do a second search and Verify NFSP value");
		assertTrue(searchAndVerifyNFSP(expected_url_nfsp_segment, nfsp_source));
		homepage_instance.click_SignOut();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Login and Verify the default NFSP value");
		db_instance.updateDailySearchCount(user_details[0], 0);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(user_details[0], xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		assertTrue(searchAndVerifyNFSP(expected_default_nfsp_segment, default_nfsp_source));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(5, true);
	}
}