package com.miscellanous;

import java.util.LinkedList;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SubCategoryPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SubCategoryTests extends BaseClass {
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final SubCategoryPage sub_category_instance = SubCategoryPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();

	@testId(test_id = "34293")
	@testCaseName(test_case_name = "B-51352- [M Redesign] Subcategory Integration")
	@Test(priority = 1, description = "Verify the Sub Category page", testName = "34293:B-51352- [M Redesign] Subcategory Integration")
	public void verify_sub_cateogry_stories() throws Exception {
		String story_count = String.valueOf(rand(10, 20));
		int total_no_of_videos_on_page = 2;

		// Step 1
		test_step_details(1, "Login to the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		 homepage_instance.click_sign_in();
		 sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
		 xmlReader(ENVIRONMENT, "ValidPassword"));
		 lb_instance.close_welcome_optin_lb();

		// Step 2
		test_step_details(2, "Navigate to Sub Category page");
		LinkedList<String> category_menu_urls = homepage_instance.get_main_catagory_menu_url_list();
		for (String main_category_url : category_menu_urls) {
			// Since we doesn't have any unique identifier to point our category
			// pages we hard coded the category section below.
			if (main_category_url.endsWith("entertainments") || main_category_url.endsWith("news")
					|| main_category_url.endsWith("lifestyle")) {
				invokeBrowser(main_category_url);
				LinkedList<String> sub_category_menu_urls = homepage_instance.get_sub_catagory_menu_url_list();
				for (String sub_category_url : sub_category_menu_urls) {
					invokeBrowser(sub_category_url);
					String sub_category_type = sub_category_instance.get_sub_category_header_text();

					// Step 3
					test_step_details(3, "Login to the Joomla and modify the stories count");
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(sub_category_type);
					admin_instance.select_dropdown_field_element_by_label("Elements", story_count);
					admin_instance.publish_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

					// Step 4
					test_step_details(4, "Verify the Sub Category header and their contents");
					invokeBrowser(sub_category_url);
					assertEqualsIgnoreCase(getTitle(), sub_category_type);
					assertEqualsInt(sub_category_instance.get_total_videos_on_page(), total_no_of_videos_on_page);
					assertEqualsInt(Integer.parseInt(story_count) + total_no_of_videos_on_page,
							sub_category_instance.get_total_articles_on_page()
									+ sub_category_instance.get_total_videos_on_page());

					// Step 5
					test_step_details(5, "Verify the View More button on bottom of Sub Category page");
					assertTrue(sub_category_instance.verify_view_more_button());
					String current_url = getCurrentUrl();
					sub_category_instance.click_view_more_button();
					assertEquals(getCurrentUrl(), current_url + "?page=2");

					// Step 6
					test_step_details(6, "Verify the Full Story link of first article of the Sub Category page");
					String first_article_desc = sub_category_instance.get_desc_of_first_article();
					sub_category_instance.click_full_story_link_of_first_article();
					article_instance.verify_next_article_link();
					assertEquals(article_instance.get_article_sub_category_type(), sub_category_type);
					assertIsStringContains(article_instance.get_article_desc(), first_article_desc);

					// Step 7
					test_step_details(7, "Verify the Full Story link of first video of the Sub Category page");
					invokeBrowser(sub_category_url);
					String first_video_desc = sub_category_instance.get_desc_of_first_video();
					sub_category_instance.click_full_story_link_of_first_video();
					video_instance.verify_video_player();
					assertEquals(video_instance.get_video_category_name(), sub_category_type);
					assertIsStringContains(video_instance.get_video_desc(), first_video_desc);
					// Break statement used to prevent the execution for all the
					// sub-category pages, to save time. Removing the Break,
					// make the script to verify on all the sub-category pages.
					break;
				}
				// Break statement used to prevent the execution for all the
				// main-category pages, to save time. Removing the Break,
				// make the script to verify on all the main-category pages.
				break;
			}
		}
	}

	@testId(test_id = "34293")
	@testCaseName(test_case_name = "B-51352- [M Redesign] Subcategory Integration")
	@Test(priority = 2, description = "Verify the Sub Category page", testName = "34293:B-51352- [M Redesign] Subcategory Integration")
	public void verify_trending_now_section() throws Exception {
		String trending_elements_count = String.valueOf(rand(1, 8));
		String popular_videos_section_title = generateRandomString(8);
		int popular_video_count = 4;

		// Step 1
		test_step_details(1, "Login to the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		// homepage_instance.click_sign_in();
		// sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
		// xmlReader(ENVIRONMENT, "ValidPassword"));
		// lb_instance.close_welcome_optin_lb();

		// Step 2
		test_step_details(2, "Navigate to Sub Category page");
		LinkedList<String> category_menu_urls = homepage_instance.get_main_catagory_menu_url_list();
		for (String main_category_url : category_menu_urls) {
			// Since we doesn't have any unique identifier to point our category
			// pages we hard coded the category section below.
			if (main_category_url.endsWith("entertainments") || main_category_url.endsWith("news")) {
				invokeBrowser(main_category_url);
				LinkedList<String> sub_category_menu_urls = homepage_instance.get_sub_catagory_menu_url_list();
				for (String sub_category_url : sub_category_menu_urls) {
					invokeBrowser(sub_category_url);
					String sub_category_type = sub_category_instance.get_sub_category_header_text();

					// Step 3
					test_step_details(3, "Login to the Joomla and modify the stories count");
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(sub_category_type);
					admin_instance.check_field_based_on_label_name("Trending Now", 1, 1);
					admin_instance.select_option_dropdown_field_element_by_label("Trending Now Category",
							sub_category_type);
					admin_instance.select_dropdown_field_element_by_label("Trending Elements", trending_elements_count,
							1);
					admin_instance.enter_text_field_element_by_label("Videos Text", popular_videos_section_title, 1);
					admin_instance.select_dropdown_field_element_by_label("Playlist Category", sub_category_type, 1);
					admin_instance.publish_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

					// Step 4
					test_step_details(4, "Verify the Sub Category Trending Now section");
					invokeBrowser(sub_category_url);
					assertEqualsInt(sub_category_instance.get_trending_now_elements_count(),
							Integer.parseInt(trending_elements_count));

					// Step 5
					test_step_details(5, "Verify the Sub Category Popular Videos section");
					assertEqualsInt(sub_category_instance.get_popular_videos_count(), popular_video_count);
					assertEqualsIgnoreCase(sub_category_instance.get_popular_videos_section_title(),
							popular_videos_section_title);
					// Break statement used to prevent the execution for all the
					// sub-category pages, to save time. Removing the Break,
					// make the script to verify on all the sub-category pages.
					break;
				}
				// Break statement used to prevent the execution for all the
				// main-category pages, to save time. Removing the Break,
				// make the script to verify on all the main-category pages.
				break;
			}
		}
	}
}
