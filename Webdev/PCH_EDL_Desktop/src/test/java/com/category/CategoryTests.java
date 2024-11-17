package com.category;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.beust.jcommander.internal.Lists;
import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class CategoryTests extends BaseClass {

	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final List<String> category_list = Lists.newArrayList("LifeHacks", "Pets", "Trending");
	private final String article_name = category_list.get(rand(0, category_list.size() - 1));
	private String page_name = null;

	@testId(test_id = "35094")
	@testCaseName(test_case_name = "B-59726 [D,T]Category Pages [Split 4]")
	@Test(priority = 1, description = "Verify the Category page sections", groups = { "DESKTOP",
			"TABLET" }, testName = "35094-59726 [D,T]Category Pages [Split 4]")
	public void verify_category_page_sections() throws Exception {
		String trending_now_title = generateRandomString(10);
		String trending_now_category = String.valueOf(rand(51, 66));
		String trending_now_video_count = String.valueOf(rand(0, 8));
		int no_of_tile_per_block = rand(2, 4);
		
		if (article_name.equals("LifeHacks")) page_name = "Life Hacks" ;
		else page_name = article_name;

		// Step 1
		test_step_details(1, "Modify the health site page from Admin");
		log.info("Login to Joomla and navigate to article name: " + article_name);
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		admin_instance.select_option_dropdown_field_element_by_label_with_value("Elements", String.valueOf(no_of_tile_per_block));
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
		test_step_details(3, "Redirect to Category Page and verify the page headers");
		edl_home_instance.click_header_menu(page_name);
		assertTrue(edl_home_instance.verify_item_highlighted_header_menu(page_name));
		assertTrue(edl_home_instance.verify_item_highlighted_footer_menu(page_name));
		assertTrue(edl_home_instance.verify_edl_title(article_name));
		assertTrue(edl_home_instance.verfiy_date_on_page());

		// Step 4
		test_step_details(4, "Verify the Category page sections");
		assertEqualsInt(edl_home_instance.get_count_of_category_page_sections(),
				((no_of_tile_per_block * 3) / 2) + (no_of_tile_per_block % 2) + 2);
		assertTrue(edl_home_instance.is_wide_section_displayed());

		// Step 5
		test_step_details(5, "Verify the Sweep Stakes & Search box section on right rail side");
		//assertTrue(edl_home_instance.verify_sweepstakes());
		assertTrue(edl_home_instance.verify_searchBox());

		// Step 6
		test_step_details(6, "Verify the Trending Now section on right rail side");
		assertEqualsIgnoreCase(edl_home_instance.get_trending_now_title(), trending_now_title);
		assertEqualsInt(edl_home_instance.get_trending_now_video_count(), Integer.parseInt(trending_now_video_count));
		String video_title = edl_home_instance.get_trending_now_first_video_title();
		edl_home_instance.click_first_trending_now_category_video();
		lb_instance.close_welcome_optin_lb();
		assertIsStringContainsIgnoreCase(video_instance.get_video_title(),
				video_title.substring(0, video_title.length() - 4));
	}

	@testId(test_id = "35094")
	@testCaseName(test_case_name = "B-59726 [D,T]Category Pages [Split 4]")
	@Test(priority = 2, description = "Verify the Health page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "35094-59726 [D,T]Category Pages [Split 4]")
	public void verify_category_page_ads() throws Exception {

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
		edl_home_instance.click_header_menu(page_name);
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
	}
}
