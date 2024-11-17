package com.miscellaneous;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SubCategoryPage;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

/**
 * Contains the test scenarios for Sub Category pages
 * 
 * @author mperumal
 *
 */
public class SubCategoryMenuTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final SubCategoryPage sub_category_instance = SubCategoryPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final VideoLandingPage video_instance = VideoLandingPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final int no_of_hardcoded_video_count = 2;

	@testId(test_id = "RT-04238")
	@testCaseName(test_case_name = "Subcategory Page [D/T/M]")
	@Test(priority = 1, description = "Verify the Sub Category page scenarios", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04238:Subcategory Page [D/T/M]")
	public void verify_page_articles_count() throws Exception {
		test_Method_details(1, "Verify the Sub Category page scenarios");
		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to each Sub-Catagory page");
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				String article_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
				log.info("Login to Joomla and navigate to article name: " + article_name);
				invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
				admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
						xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
				admin_instance.goToArticlePage();
				admin_instance.search_for_article(article_name);
				int expected_article_count = Integer.parseInt(get_first_selected_option(
						admin_instance.get_dropdown_field_element_by_label("Elements"), "value", 10));
				int expected_native_ad_position = Integer.parseInt(get_first_selected_option(
						admin_instance.get_dropdown_field_element_by_label("Native Ad Position"), "value", 10));
				int expected_gpt_ad_position = Integer.parseInt(
						getAttribute(admin_instance.get_text_field_element_by_label("Ad Positions"), "value"));
				int expected_trending_elements_count = Integer.parseInt(get_first_selected_option(
						admin_instance.get_dropdown_field_element_by_label("Trending Elements"), "value", 10));
				String expected_popular_video_title = getAttribute(
						admin_instance.get_text_field_element_by_label("Videos Text"), "value");
				expected_popular_video_title = expected_popular_video_title.equals("") ? "POPULAR VIDEOS"
						: expected_popular_video_title;
				step_validator(2, true);

				// Step 3
				test_step_details(3, "Validate the Article, Trending Now, Ad Positions and Popular Video section");
				invokeBrowser(url);
				homepage_instance.close_openx_banner();
				assertEqualsInt(
						sub_category_instance.get_article_count() - sub_category_instance.get_count_of_videos_on_page(),
						expected_article_count);
				assertEqualsInt(sub_category_instance.get_trending_elements_count(), expected_trending_elements_count);
				assertEquals(sub_category_instance.get_popular_videos_section_title_name().toLowerCase(),
						expected_popular_video_title.toLowerCase());
				assertEqualsInt(sub_category_instance.get_position_of_native_ad_unit(), expected_native_ad_position);
				assertEqualsInt(sub_category_instance.get_position_of_gpt_ad_unit(), expected_gpt_ad_position);
				assertEqualsInt(sub_category_instance.get_count_of_videos_on_page(), no_of_hardcoded_video_count);
				assertEqualsInt(sub_category_instance.get_full_story_link_count(),
						expected_article_count + no_of_hardcoded_video_count);
				assertTrue(sub_category_instance.verify_article_without_image_presence());
				step_validator(3, true);

				// Step 4
				test_step_details(4, "Verify View More button");
				String next_page_details = sub_category_instance.get_next_page_details_from_view_more();
				sub_category_instance.click_view_more();
				assertEquals(getCurrentUrl(), next_page_details);
				step_validator(4, true);

				// Step 5
				test_step_details(5, "Verify Full Story link");
				sub_category_instance.click_full_story_link(expected_article_count - 1);
				assertTrue(article_instance.verify_next_article_presence() || video_instance.verify_fa_videosection());
				step_validator(5, true);
				// Below Break command will limit the execution to verify the
				// first sub-category page.
				// Commenting the Break will execute the above
				// entire verification for all the Sub-Category pages
				break;
			}
		}
	}

	@testId(test_id = "RT-04238,RT-04391")
	@testCaseName(test_case_name = "Subcategory Page [D/T/M],[D/T] FP: Verify ads in across the site")
	@Test(priority = 2, description = "Verify the Native Ad's are displayed in Sub Category page as per Admin configurations.", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04238:Subcategory Page [D/T/M],RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_native_ad_positioning() throws Exception {
		test_Method_details(2,
				"Verify the Native Ad's are displayed in Sub Category page as per Admin configurations.");

		// Step 1
		test_step_details(1, "Navigate to frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to each Sub-Catagory page");
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				String article_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
				log.info("Login to Joomla and navigate to article name: " + article_name);
				invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
				admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
						xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
				admin_instance.goToArticlePage();
				admin_instance.search_for_article(article_name);
				int native_ad_position = Integer.parseInt(get_first_selected_option(
						admin_instance.get_dropdown_field_element_by_label("Native Ad Position"), "value", 10));
				int gpt_ad_position = Integer.parseInt(
						getAttribute(admin_instance.get_text_field_element_by_label("Ad Positions"), "value"));
				native_ad_position = native_ad_position % 2 == 0 ? native_ad_position % 2 + 1
						: native_ad_position % 2 + 1;
				gpt_ad_position = gpt_ad_position <= 3 ? 5 : 3;
				step_validator(2, true);

				// Step 3
				test_step_details(3, "Modifying the Ad positions on Admin");
				sub_category_instance.select_native_ad_position(native_ad_position);
				sub_category_instance.enter_gpt_ad_position(gpt_ad_position);
				admin_instance.save_and_close_article();
				step_validator(3, true);

				// Step 4
				test_step_details(4, "Verify the modified Ad position based on Admin change");
				invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
				invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
				invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
				invokeBrowser(url);
				homepage_instance.close_openx_banner();
				assertEqualsInt(sub_category_instance.get_position_of_native_ad_unit(), native_ad_position);
				assertEqualsInt(sub_category_instance.get_position_of_gpt_ad_unit(), gpt_ad_position);
				step_validator(4, true);

				// Step 5
				test_step_details(5, "Verify the Sticky Search and Header menu on each category page");
				scrollToBottomOfPage();
				assertTrue(homepage_instance.verify_sticky_header_menu());
				step_validator(5, true);
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
	@Test(priority = 3, description = "Verify display of Sub Category Page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_sub_category_page() throws Exception {
		test_Method_details(3, "Verify display of Sub Category Page Ad's");
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_width = "300";
		String right_rail_ad_2_height = "250";
		String trending_now_native_ad_width = "300";
		String trending_now_native_ad_height = "90";
		String sub_category_native_ad_width = "796";
		String sub_category_native_ad_height = "170";
		String inline_ad_width = "728";
		String inline_ad_height = "90";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the display of Inline GPT Ad's and the Size");
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			invokeBrowser(url);
			assertTrue(homepage_instance.verify_inline_gpt_ad());
			assertEquals(homepage_instance.get_size_of_inline_gpt_ad()[0], inline_ad_width);
			assertEquals(homepage_instance.get_size_of_inline_gpt_ad()[1], inline_ad_height);
			step_validator(2, true);

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
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify the display of Right Rail Second Ad and the Size");
			assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[0], right_rail_ad_2_width);
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[1], right_rail_ad_2_height);
			step_validator(4, true);

			// Step 5
			test_step_details(7, "Verify the display of Sub Category Native Ad");
			assertTrue(homepage_instance.verify_sub_category_native_ad());
			assertEquals(homepage_instance.get_size_of_sub_category_native_ad()[0], sub_category_native_ad_width);
			assertEquals(homepage_instance.get_size_of_sub_category_native_ad()[1], sub_category_native_ad_height);
			step_validator(5, true);

			// Step 6
			test_step_details(5, "Verify the display of Trending Now Native Ad");
			assertTrue(homepage_instance.verify_trending_now_native_ad());
			assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[0], trending_now_native_ad_width);
			assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[1], trending_now_native_ad_height);
			step_validator(6, true);
			// Below Break command will limit the execution to verify the
			// first category page.
			// Commenting the Break will execute the above
			// entire verification for all the Category pages
			break;
		}
	}
}
