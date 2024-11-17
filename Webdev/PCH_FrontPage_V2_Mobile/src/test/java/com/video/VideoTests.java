package com.video;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServicesPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class VideoTests extends BaseClass {
	private final HomePage homepage_instance = HomePage.getInstance();
	private final VideoPage videopage_instance = VideoPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final CentralServicesPage cs_instance = CentralServicesPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final String token_video_article_name = "Tokens / Video Claim Tokens";

	@SuppressWarnings("unused")
	@testId(test_id = "34311")
	@testCaseName(test_case_name = "B-54213 [M] Redesign- Video Page Integration")
	@Test(priority = 1, description = "Verify Video page for the Recognised user", testName = "34311:B-54213 [M] Redesign- Video Page Integration")
	public void featured_video_page() throws Exception {

		// Step 1
		test_step_details(1, "Login and go to Video page");
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		homepage_instance.click_video_menu();
		assertEqualsIgnoreCase(videopage_instance.get_video_category_name(), "Featured");
		assertTrue(videopage_instance.verify_video_player());
		assertTrue(videopage_instance.verify_back_to_home_featured());

		// Step 2
		test_step_details(2,
				"Verify the Category video sections and its Next and Previous arrows on Featured Video page");
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> category_names = videopage_instance.get_category_menu_name();
		for (String name : category_names) {
			assertTrue(videopage_instance.verify_next_arrow_enable_status(name));
			assertTrue(videopage_instance.verify_previous_arrow_disable_status(name));
			videopage_instance.click_next_arrow(name);
			videopage_instance.click_next_arrow(name);
			assertTrue(videopage_instance.verify_previous_arrow_enable_status(name));
			assertTrue(videopage_instance.verify_next_arrow_enable_status(name));
			videopage_instance.click_previous_arrow(name);
			assertTrue(videopage_instance.verify_previous_arrow_enable_status(name));
			assertTrue(videopage_instance.verify_next_arrow_enable_status(name));
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 3
		test_step_details(3, "Verify the Category videos section ");
		for (int count = 0; count < category_names.size(); count++) {
			String video_title = videopage_instance
					.get_visible_video_title_from_category_menu(category_names.get(count));
			videopage_instance.click_visible_video_from_category_menu(category_names.get(count));
			assertTrue(videopage_instance.verify_back_to_home_featured());
			assertEqualsIgnoreCase(videopage_instance.get_video_category_name(), category_names.get(count));
			assertIsStringContains(videopage_instance.get_video_title_from_video_player(), video_title);
			assertEquals(videopage_instance.get_video_title_from_video_player(),
					videopage_instance.get_video_title_from_bottom_of_video_player());
			assertIsStringContains(videopage_instance.get_video_title_from_video_player(),
					videopage_instance.get_selected_video_title_from_bottom_playlist());
			homepage_instance.click_video_menu();
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		String current_category_page_url = null;
		String current_category_page_name = null;
		// Step 4
		test_step_details(4, "Verify the Sub-Category videos section on all Category video page");
		for (int count = 0; count < category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(category_names.get(count));
			assertTrue(videopage_instance.verify_back_to_home_featured());
			assertTrue(videopage_instance.verify_category_list());
			current_category_page_url = getCurrentUrl();
			current_category_page_name = category_names.get(count);
			homepage_instance.click_video_menu();
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the category
			// pages.
			break;
		}

		// Step 5
		test_step_details(5,
				"Verify the Next and Previous arrows of all the Sub-Category video sections of the Category video page's");
		invokeBrowser(current_category_page_url);
		ArrayList<String> sub_category_names = videopage_instance.get_category_menu_name();
		for (String name : sub_category_names) {
			assertTrue(videopage_instance.verify_next_arrow_enable_status(name));
			assertTrue(videopage_instance.verify_previous_arrow_disable_status(name));
			videopage_instance.click_next_arrow(name);
			videopage_instance.click_next_arrow(name);
			assertTrue(videopage_instance.verify_previous_arrow_enable_status(name));
			assertTrue(videopage_instance.verify_next_arrow_enable_status(name));
			videopage_instance.click_previous_arrow(name);
			assertTrue(videopage_instance.verify_previous_arrow_enable_status(name));
			assertTrue(videopage_instance.verify_next_arrow_enable_status(name));
			// Break statement used to stop the verification on one Sub Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the
			// sub-categories.
			break;
		}

		// Step 6
		// We are not verifying the sub-category page of all the category page.
		// We are verifying the category page where application presents after
		// the Step-5 is executed.
		test_step_details(6, "Verify the Sub-Category video section and its page");
		for (int count = 0; count < sub_category_names.size(); count++) {
			String video_title = videopage_instance
					.get_visible_video_title_from_category_menu(sub_category_names.get(count));
			videopage_instance.click_visible_video_from_category_menu(sub_category_names.get(count));
			assertTrue(videopage_instance.verify_back_to_home_featured());
			assertEqualsIgnoreCase(videopage_instance.get_video_category_name(), sub_category_names.get(count));
			assertIsStringContains(videopage_instance.get_video_title_from_video_player(), video_title);
			assertEquals(videopage_instance.get_video_title_from_video_player(),
					videopage_instance.get_video_title_from_bottom_of_video_player());
			assertIsStringContains(videopage_instance.get_video_title_from_video_player(),
					videopage_instance.get_selected_video_title_from_bottom_playlist());
			// Verify the Category section on the Sub-Category page
			assertTrue(videopage_instance.get_category_menu_name().contains(current_category_page_name));
			invokeBrowser(current_category_page_url);
			sub_category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Sub-Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the sub-categories
			// page.
			break;
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 2, description = "Verify the Video log scenario for the Full Registered user")
	public void verify_video_details_on_video_log_for_full_reg() throws Exception {
		String token_amount_value = String.valueOf(rand(0, 2000));
		// Step 1
		test_step_details(1, "Register Full user");
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.click_register();
		String user_email = register_instance.register_full_user_with_optin();
		assertTrue(homepage_instance.verify_token_icon_uni_nav());
		assertTrue(homepage_instance.verify_status_icon_uni_nav());

		// Step 2
		test_step_details(2, "Modify the Token amount value on the Joomla Admin");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_video_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 3
		test_step_details(3, "Goto Video page and verify the Token Claimed status");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		videopage_instance.verify_video_player();
		videopage_instance.verify_video_claimed_status();
		String category_name = videopage_instance.get_video_category_name();
		String video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
		assertEqualsIgnoreCase(videopage_instance.get_claimed_token_amount(), token_amount_value);
		assertIsStringContainsIgnoreCase(homepage_instance.get_unis_message(),
				msg_property_file_reader("video_token_activity_message", token_amount_value));

		// Step 4
		test_step_details(4, "Verify the Video log details for the Featured video");
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_email);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "1");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), token_amount_value);

		// Step 5
		String current_category_url = "";
		test_step_details(5, "Verify Video log details for the Category videos");
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> category_names = videopage_instance.get_category_menu_name();
		for (int count = 0; count < category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			assertEqualsIgnoreCase(videopage_instance.get_claimed_token_amount(), token_amount_value);
			assertIsStringContainsIgnoreCase(homepage_instance.get_unis_message(),
					msg_property_file_reader("video_token_activity_message", token_amount_value));
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_email);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "1");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), token_amount_value);
			// Navigate to Video Menu
			current_category_url = getCurrentUrl();
			homepage_instance.click_video_menu();
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 6
		test_step_details(6, "Verify Video log details for the Sub-Category videos");
		invokeBrowser(current_category_url);
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> sub_category_names = videopage_instance.get_category_menu_name();
		for (int count = 1; count < sub_category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(sub_category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			assertEqualsIgnoreCase(videopage_instance.get_claimed_token_amount(), token_amount_value);
			assertIsStringContainsIgnoreCase(homepage_instance.get_unis_message(),
					msg_property_file_reader("video_token_activity_message", token_amount_value));
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_email);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "1");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), token_amount_value);
			// Navigate to Video Menu
			invokeBrowser(current_category_url);
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 3, description = "Verify the Video log scenario for the Mini Registered user")
	public void verify_video_details_on_video_log_for_mini_reg() throws Exception {
		String token_amount_value = String.valueOf(rand(0, 2000));
		// Step 1
		test_step_details(1, "Modify the Token amount value on the Joomla Admin");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_video_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Register Mini Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.create_mini_reg_user();

		// Step 3
		test_step_details(3, "Goto Video page and verify the Complete Registration text on Video player");
		invokeBrowser(user_details[1]);
		lb_instance.close_gs_overlay();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());
		homepage_instance.click_first_video_link();
		videopage_instance.verify_video_player();
		videopage_instance.verify_default_token_icon();
		videopage_instance.verify_play_circle();
		videopage_instance.verify_complete_reg_on_video_player();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());
		String category_name = videopage_instance.get_video_category_name();
		String video_title = videopage_instance.get_video_title_from_bottom_of_video_player();

		// Step 4
		test_step_details(4, "Verify the Video log details for the Featured video");
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "0");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), "0");

		// Step 5
		String current_category_url = "";
		test_step_details(5, "Verify Video log details for the Category videos");
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> category_names = videopage_instance.get_category_menu_name();
		for (int count = 0; count < category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_details[0]);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "0");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), "0");
			// Navigate to Video Menu
			current_category_url = getCurrentUrl();
			homepage_instance.click_video_menu();
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 6
		test_step_details(6, "Verify Video log details for the Sub-Category videos");
		invokeBrowser(current_category_url);
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> sub_category_names = videopage_instance.get_category_menu_name();
		for (int count = 1; count < sub_category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(sub_category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_details[0], video_title);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "0");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), "0");
			// Navigate to Video Menu
			invokeBrowser(current_category_url);
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 7
		test_step_details(7, "Complete the Registration and verify the Video log details");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_complete_reg_link_uni_nav();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_registration_for_mini_reg_user();
		homepage_instance.click_first_video_link();
		category_name = videopage_instance.get_video_category_name();
		video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
		videopage_instance.verify_video_claimed_status();
		// Verify the Video log details
		log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "1");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), token_amount_value);
	}

	@SuppressWarnings("unused")
	@Test(priority = 4, description = "Verify the Video log scenario for the Social user")
	public void verify_video_details_on_video_log_for_social_reg() throws Exception {
		String token_amount_value = String.valueOf(rand(0, 2000));
		// Step 1
		test_step_details(1, "Modify the Token amount value on the Joomla Admin");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_video_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Register Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.create_social_user();

		// Step 3
		test_step_details(3, "Goto Video page and verify the Complete Registration text on Video player");
		invokeBrowser(user_details[1]);
		lb_instance.close_gs_overlay();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());
		homepage_instance.click_first_video_link();
		videopage_instance.verify_video_player();
		videopage_instance.verify_default_token_icon();
		videopage_instance.verify_play_circle();
		videopage_instance.verify_complete_reg_on_video_player();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());
		String category_name = videopage_instance.get_video_category_name();
		String video_title = videopage_instance.get_video_title_from_bottom_of_video_player();

		// Step 4
		test_step_details(4, "Verify the Video log details for the Featured video");
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "0");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), "0");

		// Step 5
		String current_category_url = "";
		test_step_details(5, "Verify Video log details for the Category videos");
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> category_names = videopage_instance.get_category_menu_name();
		for (int count = 0; count < category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_details[0]);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "0");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), "0");
			// Navigate to Video Menu
			current_category_url = getCurrentUrl();
			homepage_instance.click_video_menu();
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 6
		test_step_details(6, "Verify Video log details for the Sub-Category videos");
		invokeBrowser(current_category_url);
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> sub_category_names = videopage_instance.get_category_menu_name();
		for (int count = 1; count < sub_category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(sub_category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_details[0]);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "0");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), "0");
			// Navigate to Video Menu
			invokeBrowser(current_category_url);
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 7
		test_step_details(7, "Complete the Registration and verify the Video log details");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_complete_reg_link_uni_nav();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_registration_for_social_user();
		homepage_instance.click_first_video_link();
		category_name = videopage_instance.get_video_category_name();
		video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
		videopage_instance.verify_video_claimed_status();
		// Verify the Video log details
		log_details = db_instance.get_video_log_details(user_details[0], video_title);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "1");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), token_amount_value);
	}

	@SuppressWarnings("unused")
	@Test(priority = 5, description = "Verify the Video log scenario for the Silver user")
	public void verify_video_details_on_video_log_for_silver_reg() throws Exception {
		String token_amount_value = String.valueOf(rand(0, 2000));
		// Step 1
		test_step_details(1, "Modify the Token amount value on the Joomla Admin");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_video_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Register Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.create_silver_user();
		invokeBrowser(user_details[1]);
		lb_instance.close_gs_overlay();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());

		// Step 3
		test_step_details(3, "Goto Video page and verify the Complete Registration text on Video player");
		homepage_instance.click_first_video_link();
		videopage_instance.verify_video_player();
		videopage_instance.verify_default_token_icon();
		videopage_instance.verify_play_circle();
		videopage_instance.verify_complete_reg_on_video_player();
		assertTrue(homepage_instance.verify_complete_registration_link_uni_nav());
		String category_name = videopage_instance.get_video_category_name();
		String video_title = videopage_instance.get_video_title_from_bottom_of_video_player();

		// Step 4
		test_step_details(4, "Verify the Video log details for the Featured video");
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "0");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), "0");

		// Step 5
		String current_category_url = "";
		test_step_details(5, "Verify Video log details for the Category videos");
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> category_names = videopage_instance.get_category_menu_name();
		for (int count = 0; count < category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_details[0]);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "0");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), "0");
			// Navigate to Video Menu
			current_category_url = getCurrentUrl();
			homepage_instance.click_video_menu();
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 6
		test_step_details(6, "Verify Video log details for the Sub-Category videos");
		invokeBrowser(current_category_url);
		assertTrue(videopage_instance.verify_category_list());
		ArrayList<String> sub_category_names = videopage_instance.get_category_menu_name();
		for (int count = 1; count < sub_category_names.size(); count++) {
			videopage_instance.click_visible_video_from_category_menu(sub_category_names.get(count));
			videopage_instance.verify_video_player();
			videopage_instance.verify_video_claimed_status();
			category_name = videopage_instance.get_video_category_name();
			video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
			// Verify the Video log details
			log_details = db_instance.get_video_log_details(user_details[0]);
			assertEquals(log_details.get("video_title"), video_title);
			assertEquals(log_details.get("claimed"), "0");
			assertEquals(log_details.get("device"), "M");
			assertEquals(log_details.get("category"), category_name);
			assertEquals(log_details.get("tokens"), "0");
			// Navigate to Video Menu
			invokeBrowser(current_category_url);
			category_names = videopage_instance.get_category_menu_name();
			count = count + 1;
			// Break statement used to stop the verification on one Category
			// alone to reduce the execution time. Removal of the Break, will
			// make the script to verify the function for all the categories.
			break;
		}

		// Step 7
		test_step_details(7, "Complete the Registration and verify the Video log details");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_complete_reg_link_uni_nav();
		register_instance.complete_registration_for_silver_user();
		homepage_instance.click_first_video_link();
		category_name = videopage_instance.get_video_category_name();
		video_title = videopage_instance.get_video_title_from_bottom_of_video_player();
		videopage_instance.verify_video_claimed_status();
		// Verify the Video log details
		log_details = db_instance.get_video_log_details(user_details[0], video_title);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("claimed"), "1");
		assertEquals(log_details.get("device"), "M");
		assertEquals(log_details.get("category"), category_name);
		assertEquals(log_details.get("tokens"), token_amount_value);
	}

	@testId(test_id = "25891")
	@testCaseName(test_case_name = "B-27443 Center copy and Claim Tokens button below Vidible Player")
	@Test(priority = 6, description = "Verify the Token already claimed status for Full Registered user", testName = "25891:B-27443 Center copy and Claim Tokens button below Vidible Player")
	public void verify_token_already_claimed_status() throws Exception {
		// Step 1
		test_step_details(1, "Register Full user");
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.click_register();
		register_instance.register_full_user_with_optin();
		assertTrue(homepage_instance.verify_token_icon_uni_nav());
		assertTrue(homepage_instance.verify_status_icon_uni_nav());

		// Step 2
		test_step_details(2, "Goto Video page and verify the Token Claimed status");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		videopage_instance.verify_video_player();
		videopage_instance.verify_video_claimed_status();

		// Step 3
		test_step_details(3, "Verify the Token Already claimed status and icon");
		doRefresh();
		videopage_instance.verify_video_already_claimed_status();
		// assertIsStringContainsIgnoreCase(homepage_instance.get_unis_message(),
		// msg_property_file_reader("video_already_token_claimed_message"));

		// Step 4
		test_step_details(4, "Verify the Progress bar status");
		assertEqualsInt(homepage_instance.get_daily_bonus_game_check_count(), 1);
	}

	@Test(priority = 7, description = "Verify the Ad's are getting refreshed after a new Video playes")
	public void verify_ad_refresh() throws Exception {
		// Step 1
		test_step_details(1, "Register Full user");
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.click_register();
		register_instance.register_full_user_with_optin();
		assertTrue(homepage_instance.verify_token_icon_uni_nav());
		assertTrue(homepage_instance.verify_status_icon_uni_nav());

		// Step 2
		test_step_details(2, "Goto Video page and verify the Token Claimed status");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		videopage_instance.verify_video_player();
		videopage_instance.verify_video_claimed_status();
	}
}
