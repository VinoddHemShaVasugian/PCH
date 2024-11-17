package com.health;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HealthPage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HealthTests extends BaseClass {

	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final HealthPage health_instance = HealthPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final String article_name = "Health";
	private final String page_name = "Health";

	@testId(test_id = "35065")
	@testCaseName(test_case_name = "B-59730 [D&T] Health Page [Split 5]")
	@Test(priority = 1, description = "Verify the Health page", groups = { "DESKTOP",
			"TABLET" }, testName = "35065:B-59730 [D&T] Health Page [Split 5]")
	public void verify_health_page_recognized_user() throws Exception {
		String trending_now_title = generateRandomString(10);
		String trending_now_category = String.valueOf(rand(51, 66));
		String trending_now_video_count = String.valueOf(rand(0, 8));
		String tile_name_before_delete;

		// Step 1
		test_step_details(1, "Modify the health site page from Admin");
		log.info("Login to Joomla and navigate to article name: " + article_name);
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		LinkedList<String> tile_list = admin_instance.get_tile_category_dropdown_values();
		tile_name_before_delete = tile_list.getLast();
		// Deleting a Tile Category
		admin_instance.delete_tile_category_section();
		compareNotEqualLists(tile_list, admin_instance.get_tile_category_dropdown_values());
		tile_list = admin_instance.get_tile_category_dropdown_values();
		// Setting up the Trending now section
		admin_instance.enter_text_field_element_by_label("Trending Now Title", trending_now_title);
		admin_instance.select_option_dropdown_field_element_by_label_with_text("Trending Elements",
				trending_now_video_count);
		admin_instance.select_option_dropdown_field_element_by_label_with_value("Trending Now Category",
				trending_now_category);
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Full Registered user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_home_instance.click_register();
		register.register_FullUser();
		assertTrue(edl_home_instance.verify_home());

		// Step 3
		test_step_details(3, "Redirect to Health Page and verify the page headers");
		edl_home_instance.click_health_menu();
		assertTrue(edl_home_instance.verify_item_highlighted_header_menu(page_name));
		assertTrue(edl_home_instance.verify_item_highlighted_footer_menu(page_name));
		assertTrue(edl_home_instance.verify_edl_title(page_name));
		assertTrue(edl_home_instance.verfiy_date_on_page());
		compareEqualLists(tile_list, health_instance.get_tile_sub_menus());

		// Step 4
		test_step_details(4, "Verify the Tile category section arrows");
		for (String category_name : tile_list) {
			assertEqualsInt(health_instance.get_visible_video_of_section(category_name), 3);
			assertTrue(health_instance.verify_disablity_of_tile_section_backward_arrow(category_name));
			health_instance.click_tile_section_forward_arrow(category_name);
			assertEqualsInt(health_instance.get_visible_video_of_section(category_name), 6);
			assertTrue(health_instance.verify_enable_state_of_tile_section_arrows(category_name));
			while (health_instance.verify_disablity_of_tile_section_forward_arrow(category_name)) {
				health_instance.click_tile_section_forward_arrow(category_name);
			}
			// We are stopping the execution for one section to save execution
			// time.
			break;
		}

		// Step 5
		test_step_details(5, "Verify the Tile category section video");
		String video_title;
		for (String category_name : tile_list) {
			edl_home_instance.click_health_menu();
			video_title = health_instance.click_video_of_tile_category(category_name);			
			assertIsStringContains(video_instance.get_video_title(),
					video_title.substring(0, video_title.length() - 4));
			// We are stopping the execution for one section to save execution
			// time.
			break;
		}

		// Step 6
		test_step_details(6, "Add a section and verify the Health page");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		// Adding a Tile Category and Selecting a value
		admin_instance.add_tile_category_section();
		admin_instance.select_tile_category_dropdown(tile_name_before_delete);
		tile_list = admin_instance.get_tile_category_dropdown_values();
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 7
		test_step_details(7, "Verify the Health page after adding a section to it");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_home_instance.click_health_menu();
		compareEqualLists(tile_list, health_instance.get_tile_sub_menus());

		// Step 8
		test_step_details(8, "Verify the Sweep Stakes & Search box section on right rail side");
		assertTrue(edl_home_instance.verify_sweepstakes());
		assertTrue(edl_home_instance.verify_searchBox());

		// Step 9
		test_step_details(9, "Verify the Trending Now section on right rail side");
		assertEqualsIgnoreCase(edl_home_instance.get_trending_now_title(), trending_now_title);
		assertEqualsInt(edl_home_instance.get_trending_now_video_count(), Integer.parseInt(trending_now_video_count));
		video_title = edl_home_instance.get_trending_now_first_video_title();
		edl_home_instance.click_first_trending_now_category_video();
		lb_instance.close_welcome_optin_lb();
		assertIsStringContainsIgnoreCase(video_instance.get_video_title(),
				video_title.substring(0, video_title.length() - 4));
	}

	@testId(test_id = "35065")
	@testCaseName(test_case_name = "B-59730 [D&T] Health Page [Split 5]")
	@Test(priority = 2, description = "Verify the Health page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "35065:B-59730 [D&T] Health Page [Split 5]")
	public void verify_health_page_ads() throws Exception {

		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_height = "250";
		String inline_tile_ad_width = "728";
		String inline_tile_ad_height = "90";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		edl_home_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());

		// Step 2
		test_step_details(2, "Verify the display of Right Rail Ad's and the Size");
		edl_home_instance.click_health_menu();
		assertTrue(edl_home_instance.verify_right_rail_gpt_ad_1());
		assertEquals(edl_home_instance.get_size_of_right_rail_gpt_ad_1()[0], right_rail_ad_1_width);
		if (edl_home_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_1_height)) {
			assertEquals(edl_home_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_1_height);
		} else if (edl_home_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_2_height)) {
			assertEquals(edl_home_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_2_height);
		} else {
			assertTrue(false, "Right Rail First ad is mismatched in the height");
		}

		// Step 3
		test_step_details(3, "Verify the display of Inline GPT Tile Ad's and the Size");
		assertTrue(edl_home_instance.verify_inline_gpt_ad());
		assertEquals(edl_home_instance.get_size_of_inline_gpt_ad()[0], inline_tile_ad_width);
		assertEquals(edl_home_instance.get_size_of_inline_gpt_ad()[1], inline_tile_ad_height);

		// Step 4
		test_step_details(4, "Verify the Ad refresh after click on the Forward arrow");
		LinkedList<String> tile_menu = health_instance.get_tile_sub_menus();
		String page_ad_google_ids[] = edl_home_instance.get_page_ad_google_query_id_without_bottom_ad();
		health_instance.click_tile_section_forward_arrow(tile_menu.get(0));
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_without_bottom_ad();

		// Step 5
		test_step_details(5, "Verify the Ad refresh after click on the Backward arrow");
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_without_bottom_ad();
		health_instance.click_tile_section_backward_arrow(tile_menu.get(0));
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_without_bottom_ad();
	}
}
