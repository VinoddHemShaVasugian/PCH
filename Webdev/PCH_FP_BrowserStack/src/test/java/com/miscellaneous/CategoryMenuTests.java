package com.miscellaneous;

import java.util.LinkedList;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CategoryPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.pageobjects.SubCategoryPage;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

/**
 * Contains the test scenarios for Category pages. On Joomla, the articles comes
 * under Site Category section are all consider as Category pages. Home page
 * also includes as part of it. Specific scenario will be covered in separate
 * classes if needs.
 * 
 * @author mperumal
 *
 */
public class CategoryMenuTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final SERPage serpage_instance = SERPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final VideoLandingPage video_instance = VideoLandingPage.getInstance();
	private final CategoryPage category_instance = CategoryPage.getInstance();
	private final SubCategoryPage sub_category_instance = SubCategoryPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
/*
	@testId(test_id = "31938,32143")
	@testCaseName(test_case_name = "Frontpage Redesign Integration-Category page [Split],FPContentToMakeConfigurable--SitePages(categories)")
	@Test(priority = 1, description = "Verify the re-direction of sub Category page from Category page", groups = {
			"DESKTOP", "TABLET" }, testName = "Frontpage Redesign Integration-Category page [Split]")
	public void verify_page_redirection() throws Exception {
		String sub_category_section_label_name = "Tile Category";

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		String category_name = null;
		test_step_details(2, "Navigate to each Catagory page on Joomla admin to do changes");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		try {
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					category_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
					log.info("Login to Joomla and navigate to article name: " + category_name);
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(category_name);
					LinkedList<String> expected_sub_category_list = admin_instance
							.get_sub_category_list_from_category_page(sub_category_section_label_name);
					int expected_trending_elements_count = Integer.parseInt(get_first_selected_option(
							admin_instance.get_dropdown_field_element_by_label("Trending Elements"), "value", 10));
					String expected_popular_video_title = getAttribute(
							admin_instance.get_text_field_element_by_label("Videos Text"), "value");
					expected_popular_video_title = expected_popular_video_title.equals("") ? "POPULAR VIDEOS"
							: expected_popular_video_title;
					int expected_top_stories_count = Integer.parseInt(get_first_selected_option(
							admin_instance.get_dropdown_field_element_by_label("Top Stories Count"), "value", 10));
					admin_instance.click_field_element_by_label("SearchBox");
					admin_instance.save_and_close_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

					// Step 3
					invokeBrowser(url);
					test_step_details(3,
							"Verify the Openx Banner, Top Stories, Trending Elements count and Popular Video Text");
					assertEqualsInt(sub_category_instance.get_trending_elements_count(),
							expected_trending_elements_count);
					assertEquals(sub_category_instance.get_popular_videos_section_title_name().toLowerCase(),
							expected_popular_video_title.toLowerCase());
					assertEqualsInt(homepage_instance.get_top_stories_count(), expected_top_stories_count);
					assertFalse(homepage_instance.verify_openx_banner());

					// Step 4
					test_step_details(4, "Navigate to each Sub Catagory page from the Category page");
					for (String sub_category_name : expected_sub_category_list) {
						video_instance.navigate_sub_category_page_from_label(sub_category_name);
						assertEqualsIgnoreCase(getTitle().trim(), sub_category_name.trim());
						invokeBrowser(url);
					}

					// Step 5
					test_step_details(5, "Reset the Joomla changes");
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(category_name);
					admin_instance.click_field_element_by_label("SearchBox");
					admin_instance.save_and_close_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					// Below Break command will limit the execution to verify
					// the
					// first category page.
					// Commenting the Break will execute the above
					// entire verification for all the Category pages
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error while modifying the Category pages: " + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(category_name);
			admin_instance.click_field_element_by_label("SearchBox");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}*/

	@testId(test_id = "RT-04238")
	@testCaseName(test_case_name = "Subcategory Page [D/T/M]")
	@Test(priority = 2, description = "Verify the newly added sub Category listed on the Category page", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04238:Subcategory Page [D/T/M]")
	public void verfiy_newly_added_sub_category() throws Exception {
		String sub_category_section_name = "News";
		String sub_category_section_label_name = "Tile Category";
		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to each Catagory page on Joomla admin to add a new Sub category list");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		String category_name = null;
		try {
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					category_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
					log.info("Login to Joomla and navigate to article name: " + category_name);
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(category_name);
					admin_instance.add_new_drop_down_section_by_label(sub_category_section_label_name,
							sub_category_section_name);
					admin_instance.save_and_close_article();
					step_validator(2, true);

					// Step 3
					test_step_details(3, "Navigate to newly added Sub Catagory page from the Category page");
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					invokeBrowser(url);
					video_instance.navigate_sub_category_page_from_label(sub_category_section_name);
					assertEqualsIgnoreCase(getTitle(), sub_category_section_name);
					step_validator(3, true);

					// Step 4
					test_step_details(4, "Delete the newly added section from Joomla Admin");
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(category_name);
					admin_instance.delete_last_drop_down_section_by_label(sub_category_section_label_name);
					admin_instance.save_and_close_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					step_validator(4, true);

					// Step 5
					invokeBrowser(url);
					test_step_details(5, "Verify the Sticky Search and Header menu on each category page");
					scrollToBottomOfPage();
					assertTrue(homepage_instance.verify_sticky_header_menu());
					homepage_instance.hover_search(generateRandomString(5));
					switchToNewTab();
					assertTrue(serpage_instance.verify_SERP_Completely());
					switchToMainTab();
					step_validator(5, true);
					// Below Break command will limit the execution to verify
					// the first category page.
					// Commenting the Break will execute the above
					// entire verification for all the Category pages
					break;
				}
			}
		} catch (Exception e) {
			log.error("Exception handling block for adding Sub Category section :" + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(category_name);
			admin_instance.delete_last_drop_down_section_by_label(sub_category_section_label_name);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
/*
	@testId(test_id = "31938")
	@testCaseName(test_case_name = "Frontpage Redesign Integration-Category page [Split]")
	@Test(priority = 3, description = "Verify the Hover header menu on sub Category listed on the Category page", groups = {
			"DESKTOP", "TABLET" }, testName = "Frontpage Redesign Integration-Category page [Split]")
	public void verfiy_hover_header_menu() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the Sticky Search and Header menu on each category page");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more")) {
				invokeBrowser(url);
				scrollToBottomOfPage();
				assertTrue(homepage_instance.verify_sticky_header_menu());
				serpage_instance.search(generateRandomString(5));
				switchToNewTab();
				assertTrue(serpage_instance.verify_SERP_Completely());
				switchToMainTab();
				// Below Break command will limit the execution to verify
				// the first category page.
				// Commenting the Break will execute the above
				// entire verification for all the Category pages
				break;
			}
		}
	}*/

	@testId(test_id = "34003")
	@testCaseName(test_case_name = "RT-04391:[D/T] FP: Verify ads in across the site")
	@Test(priority = 4, description = "Verify the GPT Ad's are displayed in Category page as per Admin configurations.", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_native_gpt_ad_positioning() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Navigate to each Catagory page");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			String category_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
			if (!category_name.equalsIgnoreCase("Entertainment")) {
				log.info("Login to Joomla and navigate to article name: " + category_name);
				invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
				admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
						xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
				admin_instance.goToArticlePage();
				admin_instance.search_for_article(category_name);
				int native_ad_position = Integer.parseInt(get_first_selected_option(
						admin_instance.get_dropdown_field_element_by_label("Tile Sponsored Ad Position"), "value", 10));
				int gpt_ad_position = Integer.parseInt(
						getAttribute(admin_instance.get_dropdown_field_element_by_label("Tile Ad Position"), "value"));
				native_ad_position = native_ad_position % 2 == 0 ? native_ad_position % 2 + 1
						: native_ad_position % 2 + 1;
				gpt_ad_position = gpt_ad_position <= 3 ? 4 : 3;

				// Step 3
				test_step_details(3, "Modifying the Ad positions on Admin");
				category_instance.select_native_ad_position(native_ad_position);
				category_instance.select_gpt_ad_position(gpt_ad_position);
				admin_instance.save_and_close_article();

				// Step 4
				test_step_details(4, "Verify the modified Ad position based on Admin change");
				invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
				invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
				invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
				sleepFor(60);
				invokeBrowser(url);
				homepage_instance.close_openx_banner();
				assertEqualsInt(category_instance.get_position_of_native_ad_unit(), native_ad_position);
				assertEqualsInt(category_instance.get_position_of_gpt_ad_unit(), gpt_ad_position);
				// Below Break command will limit the execution to verify the
				// first sub-category page.
				// Commenting the Break will execute the above
				// entire verification for all the Sub-Category pages
				break;
			}
		}
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 5, description = "Verify the Native Ad's are displayed in Category page as per Admin configurations.", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_top_story_native_ad() throws Exception {

		// Step 1
		test_step_details(1, "Navigate to FP and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		homepage_instance.close_openx_banner();

		// Step 2
		test_step_details(2, "Verify the Top Story Native Ad in all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			invokeBrowser(url);
			assertTrue(category_instance.verify_top_story_native_ad());
		}
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 6, description = "Verify display of Category Page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_category_page() throws Exception {
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_width = "300";
		String right_rail_ad_2_height = "250";
		String inline_ad_width = "728";
		String inline_ad_height = "90";
		String sponsored_native_ad_width = "720";
		String sponsored_native_ad_height = "192";
		String trending_now_native_ad_width = "300";
		String trending_now_native_ad_height = "90";
		String top_story_native_ad_width = "368";
		String top_story_native_ad_height = "97";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the display of Inline GPT Ad's and the Size");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.toLowerCase().endsWith("entertainment")) {
				invokeBrowser(url);
				assertTrue(homepage_instance.verify_inline_gpt_ad());
				assertEquals(homepage_instance.get_size_of_inline_gpt_ad()[0], inline_ad_width);
				assertEquals(homepage_instance.get_size_of_inline_gpt_ad()[1], inline_ad_height);

				// Step 3
				test_step_details(3, "Verify the display of Right Rail First Ad and the Size");
				assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
				assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[0], right_rail_ad_1_width);
				if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_1_height)) {
					assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_1_height);
				} else if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_2_height)) {
					assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_2_height);
				} else {
					assertTrue(false, "Right Rail First ad is mismatched in the height");
				}

				// Step 4
				test_step_details(4, "Verify the display of Right Rail Second Ad and the Size");
				assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());
				assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[0], right_rail_ad_2_width);
				assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[1], right_rail_ad_2_height);

				// Step 5
				test_step_details(5, "Verify the display of Top Story Native Ad");
				assertTrue(homepage_instance.verify_top_stories_native_ad());
				assertEquals(homepage_instance.get_size_of_top_stories_native_ad()[0], top_story_native_ad_width);
				assertEquals(homepage_instance.get_size_of_top_stories_native_ad()[1], top_story_native_ad_height);

				// Step 6
				test_step_details(6, "Verify the display of Trending Now Native Ad");
				assertTrue(homepage_instance.verify_trending_now_native_ad());
				assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[0], trending_now_native_ad_width);
				assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[1], trending_now_native_ad_height);

				// Step 7
				test_step_details(7, "Verify the display of Sponsored Native Ad");
				assertTrue(homepage_instance.verify_sponsored_native_ad());
				assertEquals(homepage_instance.get_size_of_sponsored_native_ad()[0], sponsored_native_ad_width);
				assertEquals(homepage_instance.get_size_of_sponsored_native_ad()[1], sponsored_native_ad_height);

				// Below Break command will limit the execution to verify the
				// first category page.
				// Commenting the Break will execute the above
				// entire verification for all the Category pages
				break;
			}
		}
	}
/*
	@testId(test_id = "34796")
	@testCaseName(test_case_name = "[D&T] Popular Videos in right rail to be made configurable [Split 4]")
	@Test(priority = 1, description = "Verify the UniNav property configuration", groups = { "DESKTOP",
			"TABLET" }, testName = "34796:[D&T] Popular Videos in right rail to be made configurable [Split 4]")
	public void verify_popular_videos_position() throws Exception {
		String popular_video_position_label_name = "Videos Position";
		TreeMap<Integer, String> videos_position_name_on_admin = new TreeMap<Integer, String>();
		videos_position_name_on_admin.put(1, "Top ");
		videos_position_name_on_admin.put(2, "After Sidebar Popular Search ");
		videos_position_name_on_admin.put(3, "After Ads ");
		videos_position_name_on_admin.put(4, "Bottom(After Trending Now) ");
		TreeMap<Integer, Integer> videos_position_on_page = new TreeMap<Integer, Integer>();
		videos_position_on_page.put(1, 0);
		videos_position_on_page.put(2, 2);
		videos_position_on_page.put(3, 6);
		videos_position_on_page.put(4, 7);
		int video_position = rand(1, 4);

		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		String category_name = null;
		test_step_details(2, "Navigate to each Catagory page on Joomla admin to do changes");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more") && !url.toLowerCase().endsWith("entertainment")) {
				category_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
				log.info("Login to Joomla and navigate to article name: " + category_name);
				invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
				admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
						xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
				admin_instance.goToArticlePage();
				admin_instance.search_for_article(category_name);
				String popular_video_title = getAttribute(admin_instance.get_text_field_element_by_label("Videos Text"),
						"value");
				admin_instance.select_option_dropdown_field_element_by_label(popular_video_position_label_name,
						videos_position_name_on_admin.get(video_position));
				admin_instance.save_and_close_article();
				invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
				invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

				// Step 3
				invokeBrowser(url);
				test_step_details(3, "Verify the Popular Video Position");
				assertEqualsInt(videos_position_on_page.get(video_position),
						category_instance.find_popular_video_position(popular_video_title));
				// Below Break command will limit the execution to verify
				// the first category page.
				// Commenting the Break will execute the above
				// entire verification for all the Category pages
				break;
			}
		}
	}*/
}
