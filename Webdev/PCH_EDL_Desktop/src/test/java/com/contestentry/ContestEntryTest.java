package com.contestentry;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.ContestEntryPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.OamPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ContestEntryTest extends BaseClass {

	private final EDLHomePage edl_instance = EDLHomePage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final ContestEntryPage contest_entry_page = ContestEntryPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serppage_instance = SERPage.getInstance();
	private final LightBoxPage lightbox_instance = LightBoxPage.getInstance();
	private final CentralServices_Page cs_instnace = CentralServices_Page.getInstance();
	private final OamPage oam_page = OamPage.getInstance();

	private final String registration_ck_article = "Contest Keys / Frontpage / Registration";
	private final String edl_ck_article = "EDL Default contest";
	private final String first_search_ck_article = "Contest Keys / Frontpage / FirstSearch";
	private final String application_property_name = "Front Page";
	private String desktop_registration_ck, edl_ck_activity, first_search_ck, fp_url = "FrontPageURL";

	@BeforeClass
	private void get_contest_entry_from_back_end() {

		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(registration_ck_article);

		if (DEVICE.equals("Desktop")) {
			desktop_registration_ck = admin_instance.get_text_field_element_by_label("Contest Key")
					.getAttribute("value");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(edl_ck_article);
			edl_ck_activity = admin_instance.get_text_field_element_by_label("Contest Key").getAttribute("value");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(first_search_ck_article);
			first_search_ck = admin_instance.get_text_field_element_by_label("Contest Key").getAttribute("value");
			admin_instance.save_and_close_article();
		} else if (DEVICE.contains("Tablet")) {
			desktop_registration_ck = admin_instance.get_text_field_element_by_label("Contest Key", "2")
					.getAttribute("value");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(edl_ck_article);
			edl_ck_activity = admin_instance.get_text_field_element_by_label("Contest Key", "2").getAttribute("value");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(first_search_ck_article);
			first_search_ck = admin_instance.get_text_field_element_by_label("Contest Key", "2").getAttribute("value");
			admin_instance.save_and_close_article();
		}
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_contest_keys"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@testId(test_id = "35101")
	@testCaseName(test_case_name = "B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	@Test(priority = 1, description = "Ensure each activity performs entitles user for contest entry", groups = {
			"DESKTOP",
			"TABLET" }, testName = "35101:B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	public void verify_edl_activity_contest_entry() throws Exception {

		test_step_details(1, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_register();
		String user_email = register.register_FullUser();
		assertTrue(edl_instance.verify_home());

		test_step_details(2, "Login to the OAM tool");
		oam_page.login_oam();

		// Step 3
		test_step_details(3, "Verify the Contest Entry for the Registration");

		oam_page.search_legacy_contest_entry(user_email);
		assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
		assertEquals(contest_entry_page.get_source_key_of_first_row(), desktop_registration_ck);

		test_step_details(4, "Perform an edl activity by reading an article");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_recipe_header_menu();
		article_instance.click_recipe_of_day();
		assertEquals(edl_instance.get_latest_entry_activity_message(),
				msg_property_file_reader("contest_entry_edl_activity"));

		test_step_details(5, "Verify the Contest Entry for edl activity");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_email);
		assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
		assertEquals(contest_entry_page.get_source_key_of_first_row(), edl_ck_activity);

		test_step_details(6, "Do a first search in edl page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.search_term_on_right_rail(generateRandomString(5));
		edl_instance.verify_edl_title("Search");
		oam_page.login_oam();

		test_step_details(7, "Verify the Contest Entry for first search& Edl activity is awarded");
		oam_page.search_legacy_contest_entry(user_email);
		assertEquals(contest_entry_page.get_source_key_of_first_row(), first_search_ck);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);
	}

	@testId(test_id = "35101")
	@testCaseName(test_case_name = "B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	@Test(priority = 2, description = "Ensure First search automaticaly aways edl activity contest entry and "
			+ "that additional activity or search perfomed does not account for contest entry", groups = { "DESKTOP",
					"TABLET" }, testName = "35101:B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	public void verify_first_search_on_edl_contest_entry() throws Exception {

		test_step_details(1, "Create a Full Reg user");

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		delete_session_cookies();
		edl_instance.click_register();
		String user_email = register.register_FullUser();
		assertTrue(edl_instance.verify_home());

		test_step_details(2, "Do a first search in edl page");
		edl_instance.search_term_on_right_rail(generateRandomString(5));
		edl_instance.verify_edl_title("Search");
		oam_page.login_oam();

		test_step_details(3, "Verify the Contest Entry for first search& Edl activity is awarded");
		oam_page.search_legacy_contest_entry(user_email);

		assertEquals(contest_entry_page.get_property_entry_of_first_row(), application_property_name);
		assertTrue(contest_entry_page.get_source_key_list().contains(edl_ck_activity));
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));
		assertTrue(contest_entry_page.get_source_key_list().contains(desktop_registration_ck));

		/*
		 * There are two entries one for registration and another for first
		 * search and edl activity, as fs is edl activity
		 */
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);

		test_step_details(4, "Perform another edl activity by reading an article");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_recipe_header_menu();
		article_instance.click_recipe_of_day();
		edl_instance.click_claim_button();

		test_step_details(5, "Verify the Contest Entry remains the same after this activity");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_email);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);

		test_step_details(6, "Do a first search in front page");

		invokeBrowser(xmlReader(ENVIRONMENT, fp_url));
		lightbox_instance.close_welcome_light_box();
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());

		test_step_details(7, "Ensure that the contest entry for first search is not awarded");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_email);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);

	}

	@testId(test_id = "35101")
	@testCaseName(test_case_name = "B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	@Test(priority = 3, description = "Ensure First search automaticaly aways edl activity contest entry and "
			+ "that additional activity or search perfomed does not account for contest entry", groups = { "DESKTOP",
					"TABLET" }, testName = "35101:B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	public void verify_first_search_on_fp_contest_entry() throws Exception {

		test_step_details(1, "Register a new user on EDL");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		delete_session_cookies();
		edl_instance.click_register();
		String user_email = register.register_FullUser();
		assertTrue(edl_instance.verify_home());

		test_step_details(2, "Do a first search in front page");

		invokeBrowser(xmlReader(ENVIRONMENT, fp_url));
		lightbox_instance.close_welcome_light_box();
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());

		test_step_details(3, "Ensure that the contest entry for Reg & first search is awarded");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_email);
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));
		assertTrue(contest_entry_page.get_source_key_list().contains(desktop_registration_ck));
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 2);

		test_step_details(4, "Do a first search in edl page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.search_term_on_right_rail(generateRandomString(5));
		edl_instance.verify_edl_title("Search");
		oam_page.login_oam();

		test_step_details(5, "Verify the Contest Entry for first search& Edl activity is awarded");
		oam_page.search_legacy_contest_entry(user_email);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);
		assertTrue(contest_entry_page.get_source_key_list().contains(edl_ck_activity));
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));
		assertTrue(contest_entry_page.get_source_key_list().contains(desktop_registration_ck));

		test_step_details(6, "Perform another edl activity by reading an article");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_recipe_header_menu();
		article_instance.click_recipe_of_day();
		edl_instance.click_claim_button();

		test_step_details(7, "Verify the Contest Entry remains the same after this activity");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_email);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);
	}

	@testId(test_id = "35101")
	@testCaseName(test_case_name = "B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	@Test(priority = 4, description = "Ensure promotional contest entry is awarded if user lands with ck parameter", groups = {
			"DESKTOP",
			"TABLET" }, testName = "35101:B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	public void verify_edl_activity_with_rf_promotional() throws Exception {

		final String ck = "PM001";
		// Step 1
		test_step_details(1, "Create a Full Reg. user from CS and add CK as Query String");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&ck=" + ck);
		lightbox_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertEquals(get_cookie_value("edlck"), ck);

		test_step_details(2, "Perform an edl activity by reading an article");
		edl_instance.click_recipe_header_menu();
		article_instance.click_recipe_of_day();
		assertEquals(edl_instance.get_latest_entry_activity_message(),
				msg_property_file_reader("contest_entry_edl_activity"));

		test_step_details(3, "Verify the promotional Contest Entry and edl default contest entry");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_details[0]);
		assertTrue(contest_entry_page.get_source_key_list().contains(ck));
		assertTrue(contest_entry_page.get_source_key_list().contains(edl_ck_activity));

		test_step_details(4, "Do a first search in edl page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.search_term_on_right_rail(generateRandomString(5));
		edl_instance.verify_edl_title("Search");
		oam_page.login_oam();

		test_step_details(5, "Verify the Contest Entry for first search& Edl activity is awarded");
		oam_page.search_legacy_contest_entry(user_details[0]);
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);

	}

	@testId(test_id = "35101")
	@testCaseName(test_case_name = "B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	@Test(priority = 5, description = "Ensure promotional contest entry with ck parameter for edl first search", groups = {
			"DESKTOP",
			"TABLET" }, testName = "35101:B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	public void verify_edl_activity_first_search_with_rf_promotional() throws Exception {

		final String ck = "PM001";
		// Step 1
		test_step_details(1, "Create a Full Reg. user from CS and add CK as Query String");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&ck=" + ck);
		lightbox_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertEquals(get_cookie_value("edlck"), ck);

		test_step_details(2, "Do a first search in edl page");
		edl_instance.search_term_on_right_rail(generateRandomString(5));
		edl_instance.verify_edl_title("Search");

		test_step_details(3, "Verify the promotional Contest Entry edl default and first search contest entry");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_details[0]);
		assertTrue(contest_entry_page.get_source_key_list().contains(ck));
		assertTrue(contest_entry_page.get_source_key_list().contains(edl_ck_activity));
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));

		test_step_details(4, "Do a first search in front page and perform edl activity");
		invokeBrowser(xmlReader(ENVIRONMENT, fp_url));
		lightbox_instance.close_welcome_light_box();
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());
		homepage_instance.click_edl_headermenu();
		edl_instance.click_comics_header_menu();

		test_step_details(5, "Verify the promotional Contest Entry edl default and first search contest entry");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_details[0]);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);

	}

	// fp first search
	// add tc3 with promotional
	@testId(test_id = "35101")
	@testCaseName(test_case_name = "B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	@Test(priority = 3, description = "Ensure First search on FP does not award default contest entry with promotional"
			+ "contest entry", groups = { "DESKTOP",
					"TABLET" }, testName = "35101:B-60647 EDL Award contest entry based interaction [D&T&M] [Split 1]")
	public void verify_first_search_on_fp_promotional_contest_entry() throws Exception {

		final String ck = "PM001";
		// Step 1
		test_step_details(1, "Create a Full Reg. user from CS and add CK as Query String");
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createGoldUser();
		invokeBrowser(user_details[1] + "&ck=" + ck);
		lightbox_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		assertEquals(get_cookie_value("edlck"), ck);

		test_step_details(2, "Do a first search in front page");

		edl_instance.click_pch_front_page_link();
		lightbox_instance.close_evergage_light_box();
		lightbox_instance.close_welcome_light_box();
		lightbox_instance.close_optin_light_box();
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serppage_instance.verify_SERP_Completely());

		test_step_details(3, "Ensure that the contest entry for Reg & first search is awarded");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_details[0]);
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 1);

		test_step_details(4, "Perform an edl activity by reading an article");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEquals(get_cookie_value("edlck"), ck);
		edl_instance.click_comics_header_menu();
		assertEquals(edl_instance.get_latest_entry_activity_message(),
				msg_property_file_reader("contest_entry_edl_activity"));

		test_step_details(5, "Verify the default contest entry is awarder");
		oam_page.login_oam();
		oam_page.search_legacy_contest_entry(user_details[0]);
		assertEqualsInt(contest_entry_page.get_source_key_list().size(), 3);
		assertTrue(contest_entry_page.get_source_key_list().contains(edl_ck_activity));
		assertTrue(contest_entry_page.get_source_key_list().contains(first_search_ck));
		assertTrue(contest_entry_page.get_source_key_list().contains(ck));

	}

}
