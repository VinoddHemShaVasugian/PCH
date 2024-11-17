package com.miscellanous;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HomeTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final String homepage_article_name = "Homepage";

	@testId(test_id = "34008")
	@testCaseName(test_case_name = "[M Redesign] Homepage UI")
	@Test(priority = 1, description = "Verify the Home Page Top Story section based on the Joomla configuration", testName = "34008:[M Redesign] Homepage UI")
	public void verify_top_stories_section() throws Exception {
		String top_story_count = String.valueOf(rand(1, 10));
		// Step 1
		test_step_details(1, "Login to the Joomla and check the Top Stories section");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(homepage_article_name);
		admin_instance.select_option_dropdown_field_element_by_label("Top Stories Count", top_story_count, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Login to the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Verify the Top Stories section");
		assertEquals(homepage_instance.get_top_stories_count(), top_story_count);
	}

	@testId(test_id = "34008")
	@testCaseName(test_case_name = "[M Redesign] Homepage UI")
	@Test(priority = 2, description = "Verify the Home Page Our Pick section based on the Joomla configuration", testName = "34008:[M Redesign] Homepage UI")
	public void verify_our_pick_section() throws Exception {
		String our_pick_count = "6";
		// Step 1
		test_step_details(1, "Login to the Joomla and check the Our Pick section");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(homepage_article_name);
		admin_instance.check_field_based_on_label_name("Our Pick", 1, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Login to the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Verify the Our Pick section");
		assertEquals(homepage_instance.get_our_pick_count(), our_pick_count);
		assertTrue(homepage_instance.verify_our_pick_horoscope_tile());
		assertTrue(homepage_instance.verify_our_pick_lottery_tile());
		assertTrue(homepage_instance.verify_our_pick_weather_tile());

		// Step 4
		test_step_details(4, "Verify the Our Pick static tiles");
		homepage_instance.click_our_pick_horoscope_tile();
		assertEquals(getTitle(), "Horoscope");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_our_pick_lotery_tile();
		assertEquals(getTitle(), "Lottery");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_our_pick_weather_tile();
		assertEquals(getTitle(), "Weather");
	}

	@testId(test_id = "34008")
	@testCaseName(test_case_name = "[M Redesign] Homepage UI")
	@Test(priority = 3, description = "Verify the Home Page Category section based on the Joomla configuration", testName = "34008:[M Redesign] Homepage UI")
	public void verify_category_tile_section() throws Exception {
		String newly_added_category = "News";
		int category_tile_count = Integer.parseInt(homepage_instance.get_category_tile_count());
		// Step 1
		test_step_details(1, "Login to the Joomla and modify the Category Tile section");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(homepage_article_name);
		admin_instance.add_new_drop_down_section_by_label("Tile Category", newly_added_category);
		admin_instance.choose_tile_category_story_types("2");
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		try {
			// Step 2
			test_step_details(2, "Login to the application");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			lb_instance.close_gs_overlay();
			homepage_instance.click_sign_in();
			sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
			lb_instance.close_welcome_optin_lb();

			// Step 3
			test_step_details(3, "Verify the Category Tile section");
			assertEquals(homepage_instance.get_category_tile_count(), String.valueOf(category_tile_count + 1));

		} catch (Exception e) {
			log.error("Error in Homepage, Reseting the changed configuration:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(homepage_article_name);
			admin_instance.delete_last_drop_down_section_by_label("Tile Category");
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			test_step_details(4, "Reset/Delete the added the Category tile from Joomla");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(homepage_article_name);
			admin_instance.delete_last_drop_down_section_by_label("Tile Category");
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "34008")
	@testCaseName(test_case_name = "[M Redesign] Homepage UI")
	@Test(priority = 4, description = "Verify the Home Page Trending Elements section based on the Joomla configuration", testName = "34008:[M Redesign] Homepage UI")
	public void verify_trending_now_section() throws Exception {
		int popular_video_count = 4;
		String trending_elements_count = String.valueOf(rand(4, 10));
		String treding_now_popular_video_category_type = "News";
		String popular_videos_section_title = generateRandomString(8);
		// Step 1
		test_step_details(1, "Login to the Joomla and modify the Trending Elements section");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(homepage_article_name);

		admin_instance.check_field_based_on_label_name("Trending Now", 1, 1);
		admin_instance.select_dropdown_field_element_by_label("Trending Now Category",
				treding_now_popular_video_category_type);
		admin_instance.select_dropdown_field_element_by_label("Trending Elements", trending_elements_count, 1);
		admin_instance.enter_text_field_element_by_label("Videos Text", popular_videos_section_title, 1);
		admin_instance.select_dropdown_field_element_by_label("Playlist Category",
				treding_now_popular_video_category_type, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Login to the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();

		// Step 3
		test_step_details(3, "Verify the Trending Elements section");
		assertEqualsInt(homepage_instance.get_trending_now_elements_count(), Integer.parseInt(trending_elements_count));

		// Step 4
		test_step_details(4, "Verify the HomePage Popular Videos section");
		assertEqualsInt(homepage_instance.get_popular_videos_count(), popular_video_count);
		assertEqualsIgnoreCase(homepage_instance.get_popular_videos_section_title(), popular_videos_section_title);
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 5, description = "Verify the Home Page Ad's", testName = "")
	public void verify_homepage_ads() throws Exception {
		String category_native_ad_width_ = "300";
		String category_native_ad_height_1 = "250";
		String category_native_ad_height_2 = "50";
		String trending_now_native_ad_width_ = "320";
		String trending_now_native_ad_height = "180";

		// Step 1
		test_step_details(1, "Login to the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();

		// Step 2
		test_step_details(2, "Verify the display of Category Native Ad's and the Size");
		assertTrue(homepage_instance.verify_category_native_ad());
		assertEquals(homepage_instance.get_size_of_category_native_ad()[0], category_native_ad_width_);
		if (homepage_instance.get_size_of_category_native_ad()[1].equals(category_native_ad_height_1)) {
			assertEquals(homepage_instance.get_size_of_category_native_ad()[1], category_native_ad_height_1);
		} else if (homepage_instance.get_size_of_category_native_ad()[1].equals(category_native_ad_height_2)) {
			assertEquals(homepage_instance.get_size_of_category_native_ad()[1], category_native_ad_height_2);
		} else {
			assertTrue(false, "Category Native Ad is mismatched in the height");
		}

		test_step_details(3, "Verify the display of Trending Now Native Ad's and the Size");
		assertTrue(homepage_instance.verify_trending_now_ad());
		assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[0], trending_now_native_ad_width_);
		assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[1], trending_now_native_ad_height);

	}
}
