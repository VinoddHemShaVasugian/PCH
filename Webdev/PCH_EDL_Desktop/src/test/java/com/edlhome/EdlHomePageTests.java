package com.edlhome;

import org.testng.annotations.Test;
import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class EdlHomePageTests extends BaseClass {

	private final EDLHomePage edl_instance = EDLHomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final VideoLandingPage video_page_instance = VideoLandingPage.getInstance();

	private String ltst_and_grtst_title;
	private String content_type[] = { "Video", "Sweepstake", "Recipes", "Sponsored Ad", "Article" };
	String ltst_and_grtst_category = String.valueOf(rand(51, 66)), receip_category = String.valueOf(rand(51, 58)),
			ltst_and_grtst_category_val;

	private void change_content_type_in_joomla(String content_type, String content_category) throws Exception { // re-usable

		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("everydaylife");

		ltst_and_grtst_category_val = admin_instance.input_last_uninav_section("Latest & Greatest",
				ltst_and_grtst_title, content_type, content_category);

		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

	}

	@testId(test_id = "34444")
	@testCaseName(test_case_name = "B-54956 [D & T] Home Page everyday life")
	@Test(priority = 1, description = "Home Page everyday life for unregistered User", groups = { "DESKTOP",
			"TABLET" }, testName = "34444:B-54956 [D & T] Home Page everyday life")
	public void verify_edl_home_page() throws Exception {

		String ad_asertion_text = "ads";
		String sweepstakes_asertion_text = "spectrum";
		ltst_and_grtst_title = randomString(5, 8);

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		// Step 1
		test_step_details(1, "Load the edl home page and verify its content");

		//'home' in qa 'homepage' in stg
		assertTrue(edl_instance.verify_item_highlighted_header_menu("homepage")); 
		assertTrue(edl_instance.verify_item_highlighted_footer_menu("homepage"));
		assertTrue(edl_instance.verify_edl_title("Today's Picks"));
		assertTrue(edl_instance.verify_homepage_second_title("The Latest & Greatest"));
		assertTrue(edl_instance.verfiy_date_on_page());

		test_step_details(2, "Change the Latest & Greatest section to show video");
		// parameterize in joomla and assert on front page
		change_content_type_in_joomla(content_type[0], ltst_and_grtst_category);

		test_step_details(3, "Ensure the Latest & Greatest section shows video on  EDL HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		assertEqualsIgnoreCase(ltst_and_grtst_title, edl_instance.get_ltst_and_grtst_last_title());
		assertIsStringContainsIgnoreCase(edl_instance.get_ltst_and_grtst_last_content_type(), content_type[0]);
		assertEqualsIgnoreCase(ltst_and_grtst_category_val, edl_instance.get_ltst_and_grtst_last_category_type());

		test_step_details(4, "Change the Latest & Greatest section to show Sweepstake");
		change_content_type_in_joomla(content_type[1], ltst_and_grtst_category);

		test_step_details(5, "Ensure the Latest & Greatest section shows Sweepstake on  EDL HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		assertEqualsIgnoreCase(ltst_and_grtst_title, edl_instance.get_ltst_and_grtst_last_title());
		assertIsStringContainsIgnoreCase(edl_instance.get_ltst_and_grtst_last_content_type(),
				sweepstakes_asertion_text);
		edl_instance.click_ltst_and_grtst_last_content_type();
		assertIsStringContains(getCurrentUrl(), sweepstakes_asertion_text);
		navigate_back();

		test_step_details(6, "Change the Latest & Greatest section to show Recipes");
		change_content_type_in_joomla(content_type[2], receip_category);

		test_step_details(7, "Ensure the Latest & Greatest section shows Recipes on  EDL HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		assertEqualsIgnoreCase(ltst_and_grtst_title, edl_instance.get_ltst_and_grtst_last_title());
		assertIsStringContainsIgnoreCase(edl_instance.get_ltst_and_grtst_last_content_type(), "recipe");
		assertEqualsIgnoreCase(ltst_and_grtst_category_val, edl_instance.get_ltst_and_grtst_last_category_type());

		test_step_details(8, "Change the Latest & Greatest section to show Sponsored Ad");
		change_content_type_in_joomla(content_type[3], ltst_and_grtst_category);

		test_step_details(9, "Ensure the Latest & Greatest section shows Sponsored Ad on  EDL HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		assertEqualsIgnoreCase(ltst_and_grtst_title, edl_instance.get_ltst_and_grtst_last_title());
		// some time due to ad fill the ads section is empty
		if (!edl_instance.get_ltst_and_grtst_last_content_type().isEmpty())
			assertIsStringContainsIgnoreCase(edl_instance.get_ltst_and_grtst_last_content_type(), ad_asertion_text);

	}

	@testId(test_id = "34444")
	@testCaseName(test_case_name = "B-54956 [D & T] Home Page everyday life")
	@Test(priority = 2, description = "Home Page everyday life ads verification", groups = { "DESKTOP",
			"TABLET" }, testName = "34444:B-54956 [D & T] Home Page everyday life")
	public void verify_edl_home_page_ads() throws Exception {

		test_step_details(1, "Modify the trending now text and save in Joomla");
		String trending_now_text = randomString(5, 10);

		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("everydaylife");
		admin_instance.enter_text_field_element_by_label("Trending Now Title", trending_now_text, 1);

		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		test_step_details(2, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		register.register_FullUser(getCurrentDate("dd"), getMonthWithOffset(2, "MMMM"), getYearWithOffset(-15, "YYYY"));
		assertTrue(homepage_instance.verify_Home());

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		test_step_details(3, "Verify the edl home page ads gets displayed");
		assertTrue(edl_instance.verify_inline_gpt_ad());
		assertTrue(edl_instance.verify_right_rail_gpt_ad_1());
		assertTrue(edl_instance.verify_right_rail_gpt_ad_2());

		test_step_details(4, "Verify the edl home page ads dimentions");
		assertEquals(edl_instance.get_size_of_inline_gpt_ad()[0], "728");
		assertEquals(edl_instance.get_size_of_inline_gpt_ad()[1], "90");

		assertEquals(edl_instance.get_size_of_right_rail_gpt_ad_1()[0], "300");
		if (edl_instance.get_size_of_right_rail_gpt_ad_1()[1].equals("600")
				|| edl_instance.get_size_of_right_rail_gpt_ad_1()[1].equals("250")) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}

		assertEquals(edl_instance.get_size_of_right_rail_gpt_ad_2()[0], "300");
		assertEquals(edl_instance.get_size_of_right_rail_gpt_ad_2()[1], "250");

		test_step_details(5, "Verify search box is displayed and the trending now section");
		assertTrue(edl_instance.verify_searchBox());

		test_step_details(6, "Verify the trending now section");
		assertEqualsIgnoreCase(edl_instance.get_trending_now_title(), trending_now_text);

		String trending_now_first_video_text = edl_instance.get_trending_now_first_video_title();
		edl_instance.click_first_trending_now_category_video();
		assertEqualsIgnoreCase(trending_now_first_video_text, video_page_instance.get_video_title());
		navigate_back();

	}

	@testId(test_id = "34444")
	@testCaseName(test_case_name = "B-54956 [D & T] Home Page everyday life")
	@Test(priority = 2, description = "Home Page everyday life add/delete new video from back end", groups = {
			"DESKTOP", "TABLET" }, testName = "34444:B-54956 [D & T] Home Page everyday life")
	public void add_del_section_edl_home_page() throws Exception {

		ltst_and_grtst_title = randomString(5, 8);
		int ini_latest_and_greatest_sections;
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		test_step_details(1, "Get the number of latest & greatest sections available on edl page");
		ini_latest_and_greatest_sections = edl_instance.get_size_of_ltst_and_grtst_section();

		test_step_details(2, "Delete the last vedio section from joomla");

		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("everydaylife");
		admin_instance.delete_added_uninav_field_section_by_label("Latest & Greatest");
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		test_step_details(3, "Ensure that the section is deleted");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		/* The previous count minus one after we delete */
		assertEqualsInt(ini_latest_and_greatest_sections - 1, edl_instance.get_size_of_ltst_and_grtst_section());

		test_step_details(4, "Add new video to edl homepage from joomla");

		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("everydaylife");
		admin_instance.add_new_uninav_field_section_by_label("Latest & Greatest");
		admin_instance.enter_uninav_text_field_element_by_label("Latest & Greatest", "Content Title",
				ltst_and_grtst_title, 0);
		admin_instance.select_uninav_setting_dropdown_field_by_label("Latest & Greatest", "Content Type",
				content_type[0], 0);
		admin_instance.select_uninav_setting_dropdown_field_by_label("Latest & Greatest", "Category", "Nutrition", 0);
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		test_step_details(5, "Ensure that a section is added");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		/* After adding back the initial count matches */
		assertEqualsInt(ini_latest_and_greatest_sections, edl_instance.get_size_of_ltst_and_grtst_section());

		test_step_details(6, "Ensure that the added video is displayed as expected");
		assertEqualsIgnoreCase(ltst_and_grtst_title, edl_instance.get_ltst_and_grtst_last_title());
		assertIsStringContainsIgnoreCase(edl_instance.get_ltst_and_grtst_last_content_type(), content_type[0]);

		test_step_details(7, "Ensure that the 4th vedio section is displayed across 8 columns and not 6");
		assertTrue(edl_instance.is_wide_section_displayed());
	}

}
