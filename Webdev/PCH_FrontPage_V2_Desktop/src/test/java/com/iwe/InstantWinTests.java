package com.iwe;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.InstantWinPage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class InstantWinTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final InstantWinPage iwe_instance = InstantWinPage.getInstance();
	private final CentralServices_Page cs_instnace = CentralServices_Page.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final String iwe_cash_win_msg_for_non_segment_user = "You have won $10 Cash!";
	private final String iwe_cash_win_msg_for_segment_user = "You have won $20 Cash!";
	private final String fp_iwe_article_name = "Prize Machine - Instant Win / SERP";
	private final String fp_segmented_serp_messaging_article_name = "Frontpage Messaging VIP";
	private final String fp_serp_messaging_article_name = "Frontpage Messaging";

	@testId(test_id = "27174,32600")
	@testCaseName(test_case_name = "B-29968 Frontpage Token Activity - SPLIT,Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	@Test(priority = 1, description = "Verify the Cash Prize scenario for Non Segmented user", groups = { "DESKTOP",
			"TABLET" }, testName = "27174:B-29968 Frontpage Token Activity - SPLIT, 32600:Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	public void verify_non_segmented_user_cash_prize() throws Exception {
		test_Method_details(1, "Verify the Cash Prize scenario for Non Segmented user");
		String non_segmented_first_search_device_id = "";
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla Admin application");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(fp_iwe_article_name);
			non_segmented_first_search_device_id = getAttribute(
					admin_instance.get_text_field_element_by_label("Device Id", "2"), "value");
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Navigate to Instant Win environment and sign-in with valid credentials");
			invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
			iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"),
					xmlReader(ENVIRONMENT, "IweLogin_Password"));
			assertTrue(iwe_instance.verify_home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Modify the First Search FP Desktop device giveaway to alive");
			iwe_instance.navigate_device_overview_page(non_segmented_first_search_device_id);
			iwe_instance.make_gwy_active();
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Create a Full Reg user from CS page");
			invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
			String user_details[] = cs_instnace.createGoldUser();
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Login to the application and verify the Cash prize");
			invokeBrowser(user_details[1]);
			lb_instance.close_welcome_optin_lb();
			homepage_instance.search("Shoes");
			switchToNewTab();
			lb_instance.close_bronze_level_up_lb();
			String actual_iwe_cash_win_msg = homepage_instance.get_iwe_cash_winner_msg();
			homepage_instance.close_iwe_cash_win_lightbox();
			assertEquals(actual_iwe_cash_win_msg, iwe_cash_win_msg_for_non_segment_user);
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Made multiple searches to ensure no prize is awarded after the cash prize");
			int last_token_amount = homepage_instance.get_Tokens();
			serp_instance.search(randomString(5, 6));
			assertEqualsInt(last_token_amount, homepage_instance.get_Tokens());
			switchToMainTab();
			step_validator(6, true);

			// Step 7
			test_step_details(7, "Modify the First Search FP Desktop device giveaway to in active state");
			invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
			iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"),
					xmlReader(ENVIRONMENT, "IweLogin_Password"));
			iwe_instance.verify_home();
			iwe_instance.navigate_device_overview_page(non_segmented_first_search_device_id);
			iwe_instance.make_gwy_inactive();
			step_validator(7, true);
		} catch (Exception e) {
			log.info("IWE Non Segmented Cash Win Scenario: " + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
			iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"),
					xmlReader(ENVIRONMENT, "IweLogin_Password"));
			iwe_instance.verify_home();
			iwe_instance.navigate_device_overview_page(non_segmented_first_search_device_id);
			iwe_instance.make_gwy_inactive();
			throw e;
		}
	}

	@testId(test_id = "32584,32600")

	@testCaseName(test_case_name = "[D][T] Frontpage Redesign - B-43846 Integration Segmentation Engine,Frontpage Redesign-SSO User Package integration ( token activity Iwe)")

	@Test(priority = 2, description = "Verify the Cash Prize scenario for Segmented user", groups = { "DESKTOP",
			"TABLET" }, testName = "32584:[D][T] Frontpage Redesign - B-43846 Integration Segmentation Engine, 32600:Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	public void verify_segmented_user_cash_prize() throws Exception {
		test_Method_details(2, "Verify the Cash Prize scenario for Segmented user");
		String segmented_first_search_device_id = "";
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla Admin application");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(fp_iwe_article_name);
			String segment_to_assign = getAttribute(
					admin_instance.get_dropdown_field_element_by_label("Segments Included"), "value");
			segmented_first_search_device_id = getAttribute(admin_instance.get_text_field_element_by_label("Device Id"),
					"value");
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Navigate to Instant Win environment and sign-in with valid credentials");
			invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
			iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"),
					xmlReader(ENVIRONMENT, "IweLogin_Password"));
			assertTrue(iwe_instance.verify_home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Modify the First Search FP Desktop device giveaway to alive");
			iwe_instance.navigate_device_overview_page(segmented_first_search_device_id);
			iwe_instance.make_gwy_active();
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Create a Full Reg user from CS page");
			invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
			String[] user_details = cs_instnace.createGoldUser();
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Segment the created user");
			invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
			cs_instnace.set_segment_to_user(user_details[0], segment_to_assign);
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Login to the application and verify the Cash prize");
			invokeBrowser(user_details[1]);
			lb_instance.close_welcome_optin_lb();
			homepage_instance.search("Shoes");
			switchToNewTab();
			lb_instance.close_bronze_level_up_lb();
			String actual_iwe_cash_win_msg = homepage_instance.get_iwe_cash_winner_msg();
			homepage_instance.close_iwe_cash_win_lightbox();
			assertEquals(actual_iwe_cash_win_msg, iwe_cash_win_msg_for_segment_user);
			step_validator(6, true);

			// Step 7
			test_step_details(7, "Made multiple searches to ensure no prize is awarded after the cash prize");
			int last_token_amount = homepage_instance.get_Tokens();
			serp_instance.search(randomString(5, 6));
			assertEqualsInt(last_token_amount, homepage_instance.get_Tokens());
			switchToMainTab();
			step_validator(7, true);

			// Step 8
			test_step_details(8, "Modify the First Search FP Desktop device giveaway to in active state");
			invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
			iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"),
					xmlReader(ENVIRONMENT, "IweLogin_Password"));
			iwe_instance.verify_home();
			iwe_instance.navigate_device_overview_page(segmented_first_search_device_id);
			iwe_instance.make_gwy_inactive();
			step_validator(8, true);
		} catch (Exception e) {
			log.info("IWE Segmented Cash Win Scenario: " + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
			iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"),
					xmlReader(ENVIRONMENT, "IweLogin_Password"));
			iwe_instance.verify_home();
			iwe_instance.navigate_device_overview_page(segmented_first_search_device_id);
			iwe_instance.make_gwy_inactive();
			throw e;
		}
	}

	@testId(test_id = "32584, 32600")
	@testCaseName(test_case_name = "[D][T] Frontpage Redesign - B-43846 Integration Segmentation Engine,Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	@Test(priority = 3, description = "Verify the Token scenario and SERP message for Segmented user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "32584:[D][T] Frontpage Redesign - B-43846 Integration Segmentation Engine,32600:Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	public void verify_segmented_user_token_prize() throws Exception {
		test_Method_details(3, "Verify the Token scenario and SERP message for Segmented user");
		String segmented_2to5_search_device_id = "";
		String segmented_6to25_search_device_id = "";
		String first_serp_msg_for_segment_user = "";
		String later_serp_msg_for_segment_user = "";

		// Step 1
		test_step_details(1, "Navigate to Joomla Admin application");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(fp_iwe_article_name);
		selectByValue(admin_instance.get_dropdown_field_element_by_label("Segments Included"), "SV", 15);
		String segment_to_assign = getAttribute(admin_instance.get_dropdown_field_element_by_label("Segments Included"),
				"value");
		segmented_2to5_search_device_id = getAttribute(admin_instance.get_text_field_element_by_label("Device Id Alt"),
				"value");
		segmented_6to25_search_device_id = getAttribute(admin_instance.get_text_field_element_by_label("Device Id Opt"),
				"value");
		admin_instance.close_article();
		admin_instance.search_for_article(fp_segmented_serp_messaging_article_name);
		if (DEVICE.equalsIgnoreCase("DESKTOP")) {
			first_serp_msg_for_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Desktop - First Search"), "value");
			later_serp_msg_for_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Desktop - Later Search"), "value");
		} else {
			first_serp_msg_for_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Tablet - First Search"), "value");
			later_serp_msg_for_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Tablet - Later Search"), "value");
		}
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to Instant Win environment and sign-in with valid credentials");
		invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
		iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"), xmlReader(ENVIRONMENT, "IweLogin_Password"));
		assertTrue(iwe_instance.verify_home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Get the 2To5 Search Token Catch all value");
		iwe_instance.navigate_device_overview_page(segmented_2to5_search_device_id);
		String two_to_five_token_catch_all_value = iwe_instance.get_prize_token_catch_all_value();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Get the 6To25 Search Token Catch all value");
		iwe_instance.navigate_device_overview_page(segmented_6to25_search_device_id);
		String six_to_twentyfive_token_catch_all_value = iwe_instance.get_prize_token_catch_all_value();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Create a Full Reg user from CS page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] user_details = cs_instnace.createGoldUser();
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Segment the created user");
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
		cs_instnace.set_segment_to_user(user_details[0], segment_to_assign);
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Login to the application and verify the Token value and SERP message");
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.search("Shoes");
		switchToNewTab();
		lb_instance.close_bronze_level_up_lb();
		String app_serp_message = serp_instance.get_serp_message();
		assertEquals(app_serp_message, first_serp_msg_for_segment_user);
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the 2To5 Token value and SERP message");
		serp_instance.search(randomString(5, 6));
		app_serp_message = serp_instance.get_serp_message();
		assertEquals(app_serp_message, later_serp_msg_for_segment_user);
		assertEquals(homepage_instance.get_latest_activity_token_amount(), two_to_five_token_catch_all_value);
		step_validator(8, true);

		// Step 9
		test_step_details(9, "Verify the 6To25 Token value and SERP message");
		db_instance.updateDailySearchCount(user_details[0], 5);
		serp_instance.search(randomString(5, 6));
		app_serp_message = serp_instance.get_serp_message();
		assertEquals(app_serp_message, later_serp_msg_for_segment_user);
		assertEquals(homepage_instance.get_latest_activity_token_amount(), six_to_twentyfive_token_catch_all_value);
		switchToMainTab();
		step_validator(9, true);
	}

	@testId(test_id = "27174,32600")
	@testCaseName(test_case_name = "B-29968 Frontpage Token Activity - SPLIT,Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	@Test(priority = 4, description = "Verify the Token scenario and SERP message for Non Segmented user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "27174:B-29968 Frontpage Token Activity - SPLIT,32600:Frontpage Redesign-SSO User Package integration ( token activity Iwe)")
	public void verify_non_segmented_user_token_prize() throws Exception {
		test_Method_details(4, "Verify the Token scenario and SERP message for Non Segmented user");
		String non_segmented_2to5_search_device_id = "";
		String non_segmented_6to25_search_device_id = "";
		String first_serp_msg_for_non_segment_user = "";
		String later_serp_msg_for_non_segment_user = "";

		// Step 1
		test_step_details(1, "Navigate to Joomla Admin application");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(fp_iwe_article_name);
		non_segmented_2to5_search_device_id = getAttribute(
				admin_instance.get_text_field_element_by_label("Device Id Alt", "2"), "value");
		non_segmented_6to25_search_device_id = getAttribute(
				admin_instance.get_text_field_element_by_label("Device Id Opt", "2"), "value");
		admin_instance.close_article();
		admin_instance.search_for_article(fp_serp_messaging_article_name);
		if (DEVICE.equalsIgnoreCase("DESKTOP")) {
			first_serp_msg_for_non_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Desktop - First Search"), "value");
			later_serp_msg_for_non_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Desktop - Later Search"), "value");
		} else {
			first_serp_msg_for_non_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Tablet - First Search"), "value");
			later_serp_msg_for_non_segment_user = getAttribute(
					admin_instance.get_text_field_element_by_label("Tablet - Later Search"), "value");
		}
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to Instant Win environment and sign-in with valid credentials");
		invokeBrowser(xmlReader(ENVIRONMENT, "IweURL"));
		iwe_instance.login(xmlReader(ENVIRONMENT, "IweLogin_Username"), xmlReader(ENVIRONMENT, "IweLogin_Password"));
		assertTrue(iwe_instance.verify_home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Get the 2To5 Search Token Catch all value");
		iwe_instance.navigate_device_overview_page(non_segmented_2to5_search_device_id);
		String two_to_five_token_catch_all_value = iwe_instance.get_prize_token_catch_all_value();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Get the 6To25 Search Token Catch all value");
		iwe_instance.navigate_device_overview_page(non_segmented_6to25_search_device_id);
		String six_to_twentyfive_token_catch_all_value = iwe_instance.get_prize_token_catch_all_value();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Create a Full Reg user from CS page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] user_details = cs_instnace.createGoldUser();
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Login to the application and verify the Token value and SERP message");
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.search("Shoes");
		switchToNewTab();
		lb_instance.close_bronze_level_up_lb();
		String app_serp_message = serp_instance.get_serp_message();
		assertEquals(app_serp_message, first_serp_msg_for_non_segment_user);
		step_validator(6, true);

		// Step7
		test_step_details(7, "Verify the 2To5 Token value and SERP message");
		serp_instance.search(generateRandomString(6));
		app_serp_message = serp_instance.get_serp_message();
		assertEquals(app_serp_message, later_serp_msg_for_non_segment_user);
		assertEquals(homepage_instance.get_latest_activity_token_amount(), two_to_five_token_catch_all_value);
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the 6To25 Token value and SERP message");
		db_instance.updateDailySearchCount(user_details[0], 5);
		serp_instance.search(generateRandomString(6));
		app_serp_message = serp_instance.get_serp_message();
		assertEquals(app_serp_message, later_serp_msg_for_non_segment_user);
		assertEquals(homepage_instance.get_latest_activity_token_amount(), six_to_twentyfive_token_catch_all_value);
		switchToMainTab();
		step_validator(8, true);
	}

}