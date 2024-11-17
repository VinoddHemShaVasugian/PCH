package com.contestentry;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.ContestEntryPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ContestEntryTests extends BaseClass {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final ContestEntryPage contest_entry_page = ContestEntryPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final SERPage serppage_instance = SERPage.getInstance();
	private final CentralServices_Page cs_instnace = CentralServices_Page.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final AccountsSignInPage signin_instnace = AccountsSignInPage.getInstance();
	private String expected_Message;
	private final String application_property_name = "Front Page";
	private final String registration_ck_article = "Contest Keys / Frontpage / Registration";
	private final String first_search_ck_article = "Contest Keys / Frontpage / FirstSearch";
	private final String contest_entry_config_article = "config-contest-entry-api-client";
	private final String bmh_config_article = "Config-BillMeHandler";
	private final String config_frontpage = "Config-Frontpage";

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "Integrate New Contest Entry System [D& T]")
	@Test(priority = 1, description = "Verify the Contest Entries for Registration and First Search for Full Reg user", groups = {
			"DESKTOP", "TABLET", "SANITY" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_full_reg() throws Exception {
		test_Method_details(1, "Verify the Contest Entries for Registration and First Search for Full Reg user");
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(registration_ck_article);
		String desktop_registration_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Full Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		String user_email = register_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Login to the OAM tool to verify Registration contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length()).trim();
		oam_url = ("http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url).trim();
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Contest Entry for the Registration");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_email);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_registration_ck);
		} else {
			System.out.println("Registration contest entires not updated in OAM for Gold user: " + user_email);
			log.info("Registration contest entires not updated in OAM for Gold user: " + user_email);
		}
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the Contest Entry for the First Search");
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_email);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_first_search_ck);
		} else {
			System.out.println("First search contest entires not updated in OAM for Gold user: " + user_email);
			log.info("First search contest entires not updated in OAM for Gold user: " + user_email);
		}
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verify the Contest Entry for the First Search in the DB");
		assertEquals(db_instance.verify_first_search_contest_key_on_userdata_table(user_email, desktop_first_search_ck),
				"1");
		step_validator(7, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "Integrate New Contest Entry System [D& T]")
	@Test(priority = 2, description = "Verify the Contest Entries for Registration and First Search for Mini Reg user", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_mini_reg() throws Exception {
		test_Method_details(2, "Verify the Contest Entries for Registration and First Search for Mini Reg user");
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(registration_ck_article);
		String desktop_registration_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Mini Reg. user and complete the registration");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Complete the Registration");
		homepage_instance.click_complete_registration();
		signin_instnace.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Login to the OAM tool to verify Registration contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length());
		oam_url = "http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url;
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the Contest Entry for the Registration");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_registration_ck);
		} else {
			System.out.println("Registration contest entires not updated in OAM for Mini Reg user: " + user_details[0]);
			log.info("Registration contest entires not updated in OAM for Mini Reg user: " + user_details[0]);
		}
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verify the Contest Entry for the First Search");
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_first_search_ck);
		} else {
			System.out.println("First search contest entires not updated in OAM for Mini Reg user: " + user_details[0]);
			log.info("First search contest entires not updated in OAM for Mini Reg user: " + user_details[0]);
		}
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the Contest Entry for the First Search in the DB");
		assertEquals(
				db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], desktop_first_search_ck),
				"1");
		step_validator(8, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "Integrate New Contest Entry System [D& T]")
	@Test(priority = 3, description = "Verify the Contest Entries for Registration and First Search for Silver Reg user", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_silver_user() throws Exception {
		test_Method_details(3, "Verify the Contest Entries for Registration and First Search for Silver Reg user");
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(registration_ck_article);
		String desktop_registration_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Silver user and complete the registration");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertFalse(homepage_instance.verify_Home());
		homepage_instance.click_complete_registration();
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Login to the OAM tool to verify Registration contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length());
		oam_url = "http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url;
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Contest Entry for the Registration");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_registration_ck);
		} else {
			System.out.println("Registration contest entires not updated in OAM for Silver user: " + user_details[0]);
			log.info("Registration contest entires not updated in OAM for Silver user: " + user_details[0]);
		}
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the Contest Entry for the First Search");
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_first_search_ck);
		} else {
			System.out.println("First search contest entires not updated in OAM for Silver user: " + user_details[0]);
			log.info("First search contest entires not updated in OAM for Silver user: " + user_details[0]);
		}
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verify the Contest Entry for the First Search in the DB");
		assertEquals(
				db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], desktop_first_search_ck),
				"1");
		step_validator(7, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "Integrate New Contest Entry System [D& T]")
	@Test(priority = 4, description = "Verify the Contest Entries for Registration and First Search for Social user", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_social_user() throws Exception {
		test_Method_details(4, "Verify the Contest Entries for Registration and First Search for Social user");
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(registration_ck_article);
		String desktop_registration_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Mini Reg. user and complete the registration");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Complete the Registration");
		homepage_instance.click_complete_registration();
		signin_instnace.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Login to the OAM tool to verify Registration contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length());
		oam_url = "http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url;
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the Contest Entry for the Registration");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_registration_ck);
		} else {
			System.out.println("Registration contest entires not updated in OAM for Social user: " + user_details[0]);
			log.info("Registration contest entires not updated in OAM for Social user: " + user_details[0]);
		}
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verify the Contest Entry for the First Search");
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_first_search_ck);
		} else {
			System.out.println("First search contest entires not updated in OAM for social user: " + user_details[0]);
			log.info("First search contest entires not updated in OAM for social user: " + user_details[0]);
		}
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the Contest Entry for the First Search in the DB");
		assertEquals(
				db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], desktop_first_search_ck),
				"1");
		step_validator(8, true);
	}

	@testId(test_id = "RT-04245,RT-04220")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry,[D T M] FP:Support TC&V on query string")
	@Test(priority = 5, description = "Verify the Contest Entries for First Search by having TC & V and not CK in query string", groups = {
			"DESKTOP",
			"TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry,RT-04220:[D T M] FP:Support TC&V on query string")
	public void verify_contest_entry_with_tc_v_without_ck() throws Exception {
		test_Method_details(5,
				"Verify the Contest Entries for First Search by having TC & V and not CK in query string");
		final String tc = "206WS7743P";
		final String v = "20153111";
//		final String expected_Message ="You clicked an expired email. Check today’s email to enter to win";

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(config_frontpage);
		expected_Message = admin_instance.get_input_field_element_by_key_name("tc_v_not_supported_message")
				.getAttribute("value");
		admin_instance.close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Full Reg. user from CS and add TC & V value as Query string parameter");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&tc=" + tc + "&v=" + v);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		String actual_Message = homepage_instance.get_latest_entry_activity_message();
		assertEqualsIgnoreCase(actual_Message, expected_Message);
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Contest Entry for the First Search in the DB for both BMH and CK");
		assertEquals(
				db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], desktop_first_search_ck),
				"1");
//		assertEquals(db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], tc + v), "1");
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Login to the OAM tool to verify First Search contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length());
		oam_url = "http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url;
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the Contest Entry for the First Search");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_first_search_ck);
		} else {
			System.out.println("First search contest entires not updated in OAM: " + user_details[0]);
			log.info("First search contest entires not updated in OAM: " + user_details[0]);
		}
		step_validator(6, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 6, description = "Verify the Contest Entries for First Search by having CK and not TC & V in query string", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_with_ck_without_tc_v() throws Exception {
		test_Method_details(5,
				"Verify the Contest Entries for First Search by having TC & V and not CK in query string");
		final String ck = "MN123";
		// Step 1
		test_step_details(1, "Create a Full Reg. user from CS and add CK as Query Stirng");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&ck=" + ck);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();

		// Step 3
		test_step_details(3, "Verify the Contest Entry for the First Search in the DB for CK");
		assertEquals(db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], ck), "1");

		// Step 4
		test_step_details(4, "Login to the OAM tool to verify contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length());
		oam_url = "http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url;
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());

		// Step 5
		test_step_details(5, "Verify the Contest Entry for the First Search");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), ck);
		} else {
			System.out.println("First search contest entires not updated in OAM: " + user_details[0]);
			log.info("First search contest entires not updated in OAM: " + user_details[0]);
		}
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 7, description = "Verify the Contest Entries for First Search by passing multiple CK for the same user in query string", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_with_mulitple_ck() throws Exception {
		final String ck1 = "MN123";
		final String ck2 = "PE123";

		// Step 1
		test_step_details(1, "Create a Full Reg. user from CS and add a CK as Query string");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&ck=" + ck1);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Do a First Search");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Contest Entry for the First Search in the DB for first CK");
		assertEquals(db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], ck1), "1");
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Login to the OAM tool to verify contest key");
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length());
		oam_url = "http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url;
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the Contest Entry for the First Search of first CK");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), ck1);
		} else {
			System.out.println("First search contest entires not updated in OAM: " + user_details[0]);
			log.info("First search contest entires not updated in OAM: " + user_details[0]);
		}
		step_validator(5, true);

		// Step 6
		test_step_details(1, "Add an another unique CK as Query string for the same user");
		invokeBrowser(user_details[1] + "&ck=" + ck2);
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Do a Search");
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the newly added Contest Entry for the same user in the DB");
		assertEquals(db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], ck2), "1");
		step_validator(8, true);

		// Step 9
		test_step_details(9, "Login to the OAM tool to verify contest key");
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
		step_validator(9, true);

		// Step 10
		test_step_details(10, "Verify the newly added Contest Entry key for the same user");
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_details[0]);
		contest_entry_page.click_search_on_entry_page();
		if ((contest_entry_page.get_property_entry_of_first_row()) != null) {
			assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
			assertEquals(contest_entry_page.get_source_key_of_first_row(), ck2);
		} else {
			System.out.println("First search contest entires not updated in OAM: " + user_details[0]);
			log.info("First search contest entires not updated in OAM: " + user_details[0]);
		}
		step_validator(10, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 8, description = "Verify the Failed Contest Entries queue tables by making Invalid CK on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_failed_contest_entry() throws Exception {
		test_Method_details(8, "Verify the Failed Contest Entries queue tables by making Invalid CK on admin");
		final String reg_originating_url = xmlReader(ENVIRONMENT, "BaseURL");
		final String first_search_originating_url = xmlReader(ENVIRONMENT, "BaseURL") + "search";
		String desktop_registration_ck = "";
		String desktop_first_search_ck = "";
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(registration_ck_article);
			desktop_registration_ck = admin_instance.get_desktop_contest_key();
			String failed_registration_contest_key = generateRandomString(5);
			admin_instance.enter_desktop_contest_key(failed_registration_contest_key);
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(first_search_ck_article);
			desktop_first_search_ck = admin_instance.get_desktop_contest_key();
			String failed_first_search_contest_key = generateRandomString(5);
			admin_instance.enter_desktop_contest_key(failed_first_search_contest_key);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Create a Full Reg. user");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_Register();
			register_instance.register_FullUser();
			lb_instance.close_welcome_optin_lb();
			assertTrue(homepage_instance.verify_Home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Do a First Search");
			homepage_instance.search(generateRandomString(5));
			switchToNewTab();
			assertTrue(serppage_instance.verify_SERP_Completely());
			switchToMainTab();
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify the Contest Entry details in the Failed queue");
			LinkedHashMap<String, String> contest_details = db_instance
					.get_failed_contest_details(failed_registration_contest_key);// Failed contest entries not updating
																					// in contest_entries_failed table
			assertEquals(contest_details.get("app_code"), application_property_name.toUpperCase().replace(" ", ""));
			assertIsStringContains(reg_originating_url, contest_details.get("originating_url"));
			contest_details = db_instance.get_failed_contest_details(failed_first_search_contest_key);
			assertEquals(contest_details.get("app_code"), application_property_name.toUpperCase().replace(" ", ""));
			assertIsStringContains(first_search_originating_url, contest_details.get("originating_url"));
			step_validator(4, true);
		} catch (Exception e) {
			log.error("Exception in the Failed contest queue:" + e.getLocalizedMessage());
			System.out.println("Exception in the Failed contest queue:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(registration_ck_article);
			admin_instance.enter_desktop_contest_key(desktop_registration_ck);
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(first_search_ck_article);
			admin_instance.enter_desktop_contest_key(desktop_first_search_ck);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(registration_ck_article);
			admin_instance.enter_desktop_contest_key(desktop_registration_ck);
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(first_search_ck_article);
			admin_instance.enter_desktop_contest_key(desktop_first_search_ck);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 9, description = "Verify the Contest Entries by having valid CK on Query string and invalid on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_invalid_ck_on_admin_valid_on_url() throws Exception {
		test_Method_details(9, "Verify the Contest Entries by having valid CK on Query string and invalid on admin");
		String desktop_first_search_ck = "";
		final String ck = "MN123";
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(first_search_ck_article);
			desktop_first_search_ck = admin_instance.get_desktop_contest_key();
			String failed_first_search_contest_key = generateRandomString(5);
			admin_instance.enter_desktop_contest_key(failed_first_search_contest_key);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Create a Full Reg. user from CS and add a CK as Query string");
			invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
			String user_details[] = cs_instnace.createGoldUser();
			invokeBrowser(user_details[1] + "&ck=" + ck);
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Do a First Search");
			homepage_instance.search_term_on_collapse(generateRandomString(5));
			switchToNewTab();
			assertTrue(serppage_instance.verify_SERP_Completely());
			switchToMainTab();
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify the Contest Entry details in the db");
			assertEquals(db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], ck), "1");
			step_validator(4, true);
		} catch (Exception e) {
			log.error("Exception in the Failed contest queue:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(first_search_ck_article);
			admin_instance.enter_desktop_contest_key(desktop_first_search_ck);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(first_search_ck_article);
			admin_instance.enter_desktop_contest_key(desktop_first_search_ck);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 10, description = "Verify the Contest Entries by having invalid CK on Query string and valid on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_invalid_ck_on_url_valid_on_admin() throws Exception {
		test_Method_details(10, "Verify the Contest Entries by having invalid CK on Query string and valid on admin");
		final String ck = "Testing";
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Full Reg. user from CS and add a CK as Query string");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&ck=" + ck);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a First Search");
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Contest Entry details in the db");
		assertEquals(
				db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], desktop_first_search_ck),
				"1");
		step_validator(4, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 11, description = "Verify the Contest Entries by having invalid TC & V on Query string and valid CK on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_invalid_tc_on_url_valid_ck_on_admin() throws Exception {
		test_Method_details(11,
				"Verify the Contest Entries by having invalid TC & V on Query string and valid CK on admin");
		final String tc = "Testing";
		final String v = "value";
		// Step 1
		test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(first_search_ck_article);
		String desktop_first_search_ck = admin_instance.get_desktop_contest_key();
		admin_instance.save_and_close_article();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Full Reg. user from CS and add a CK as Query string");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&tc=" + tc + "&v=" + v);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Do a First Search");
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		switchToMainTab();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Contest Entry details in the db");
		assertEquals(
				db_instance.verify_first_search_contest_key_on_userdata_table(user_details[0], desktop_first_search_ck),
				"1");
		step_validator(4, true);
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 12, description = "Verify Contest Entries failed queue record by making Invalid API url on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_contest_entry_queue() throws Exception {
		test_Method_details(12, "Verify Contest Entries failed queue record by making Invalid API url on admin");
		String contest_entry_api_url = "";
		String dummy_contest_entry_api_url = generateRandomString(5);
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(contest_entry_config_article);
			contest_entry_api_url = admin_instance.get_text_field_element_by_label("Value").getAttribute("value");
			admin_instance.enter_text_field_element_by_label("Value", dummy_contest_entry_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_contest_keys_cron"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Create a Full Reg. user");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_Register();
			String user_email = register_instance.register_FullUser();
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Verify the Contest Entry details in the queue for Registration");
			LinkedHashMap<String, String> contest_details = db_instance.get_queued_contest_details();
			assertEquals(contest_details.get("app_code"), application_property_name.toUpperCase().replace(" ", ""));
			assertEquals(contest_details.get("status"), "0");
			assertIsStringContains(contest_details.get("request_data"),
					db_instance.getUserGMTOATFromEmail(user_email, "OAT"));
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Do a First Search");
			homepage_instance.search_term_on_collapse(generateRandomString(5));
			switchToNewTab();
			assertTrue(serppage_instance.verify_SERP_Completely());
			switchToMainTab();
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Verify the Contest Entry details in the queue for First Search");
			contest_details = db_instance.get_queued_contest_details();
			assertEquals(contest_details.get("app_code"), application_property_name.toUpperCase().replace(" ", ""));
			assertEquals(contest_details.get("status"), "0");
			assertIsStringContains(contest_details.get("request_data"),
					db_instance.getUserGMTOATFromEmail(user_email, "OAT"));
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Verify the Contest Entry status details in the queue after running the cron");
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_contest_keys_cron"));
			contest_details = db_instance.get_queued_contest_details();
			assertEquals(contest_details.get("status"), "1");
			step_validator(6, true);

			// Step 7
			test_step_details(7, "Reset the Joomla Admin values");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(contest_entry_config_article);
			admin_instance.enter_text_field_element_by_label("Value", contest_entry_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_contest_keys_cron"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(7, true);

			// Step 8
			test_step_details(8, "Re-Run the cron and verify the contest entry queue table");
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_contest_keys_cron"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			contest_details = db_instance.get_queued_contest_details();
			assertTrue(contest_details == null);
			step_validator(8, true);

		} catch (Exception e) {
			log.error("Exception in the contest queue:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(contest_entry_config_article);
			admin_instance.enter_text_field_element_by_label("Value", contest_entry_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(contest_entry_config_article);
			admin_instance.enter_text_field_element_by_label("Value", contest_entry_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "RT-04245")
	@testCaseName(test_case_name = "[D/T/M]: Contest Entry")
	@Test(priority = 13, description = "Verify BMH failed queue record by making Invalid API url on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04245:[D/T/M]: Contest Entry")
	public void verify_bmh_queue() throws Exception {
		test_Method_details(13, "Verify BMH failed queue record by making Invalid API url on admin");
		String bmh_api_url = "";
		String dummy_bmh_api_url = generateRandomString(5);
		final String tc = "206WS7743P";
		final String v = "20153111";

		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(bmh_config_article);
			bmh_api_url = admin_instance.get_text_field_element_by_label("Value").getAttribute("value");
			admin_instance.enter_text_field_element_by_label("Value", dummy_bmh_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Create a Full Reg. user from CS and add a TC & V as Query string");
			invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
			String user_details[] = cs_instnace.createGoldUser();
			invokeBrowser(user_details[1] + "&tc=" + tc + "&v=" + v);
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Do a First Search");
			homepage_instance.search_term_on_collapse(generateRandomString(5));
			switchToNewTab();
			assertTrue(serppage_instance.verify_SERP_Completely());
			switchToMainTab();
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify the BMH details in the queue for First Search");
			LinkedHashMap<String, String> contest_details = db_instance.get_queued_bmh_details(user_details[0]);
			try {
				assertEquals(contest_details.get("site_bmh"), application_property_name.toUpperCase().replace(" ", ""));
				assertEquals(contest_details.get("posted"), "0");
				assertEquals(contest_details.get("tc"), tc);
				assertEquals(contest_details.get("v"), v);
				assertEquals(contest_details.get("rejected"), "0");
				assertIsStringContains(contest_details.get("gmt"),
						db_instance.getUserGMTOATFromEmail(user_details[0], "OAT"));
			} catch (Exception e) {
				System.out.println("Step 4 skiped due to billmehandler_queue table deprecation");
			}
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Verify the BMH status details in the queue after running the cron");
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_bmh_cron"));
			try {
				contest_details = db_instance.get_queued_bmh_details(user_details[0]);
				assertEquals(contest_details.get("posted"), "0");
				assertEquals(contest_details.get("rejected"), "1");
			} catch (Exception e) {
				System.out.println("Step 5 skiped due to billmehandler_queue table deprecation");
			}
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Reset the Joomla Admin values");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(bmh_config_article);
			admin_instance.enter_text_field_element_by_label("Value", bmh_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(6, true);

			// Step 7
			test_step_details(7, "Re-Run the cron and verify the BMH queue table");
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_bmh_cron"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			try {
				contest_details = db_instance.get_queued_bmh_details(user_details[0]);
				assertEquals(contest_details.get("posted"), "1");
			} catch (Exception e) {
				System.out.println("Step 7 skiped due to billmehandler_queue table deprecation");
			}
			step_validator(7, true);

		} catch (Exception e) {
			log.error("Exception in the BMH queue:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(bmh_config_article);
			admin_instance.enter_text_field_element_by_label("Value", bmh_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(bmh_config_article);
			admin_instance.enter_text_field_element_by_label("Value", bmh_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
}