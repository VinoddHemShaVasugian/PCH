package com.comic;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.util.LinkedHashMap;
import java.util.List;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ComicsPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.MyAccount;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ComicTests extends BaseClass {

	private final ComicsPage comic_instance = ComicsPage.getInstance();
	private final EDLHomePage edl_instance = EDLHomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final VideoLandingPage video_page_instance = VideoLandingPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();

	private int param_comic_count = rand(9, 20);
	private String trending_now_text = randomString(5, 10);
	private String comic_tokens = String.valueOf(rand(0, 1000));
	private String comic_desc = "For Horoscope! " + generateRandomString(6);

	@BeforeClass
	public void set_joomla_config() throws Exception {

		test_step_details(1, "Modify the Comic page tokens,comics count, trending now text and save in Joomla");

		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("comics");
		admin_instance.select_dropdown_field_element_by_label("Previous Comics Count",
				Integer.toString(param_comic_count), 1);
		admin_instance.enter_text_field_element_by_label("Trending Now Title", trending_now_text, 1);
		admin_instance.enter_text_field_element_by_label("Tokens", comic_tokens);

		assertTrue(admin_instance.publish_article());

		admin_instance.goToArticlePage();
		admin_instance.search_for_article("Tokens / Edl Comics Claim Tokens");
		admin_instance.enter_text_field_element_by_label("Description", comic_desc);
		admin_instance.save_and_close_article();

		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		sleepFor(2);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
	}

	@testId(test_id = "35043")
	@testCaseName(test_case_name = "B-59716-[D&T] Comics Page [Split 5]")
	@Test(priority = 1, description = "Comic page functionality for unregistered User", groups = { "DESKTOP",
			"TABLET" }, testName = "35043:B-59716-[D&T] Comics Page [Split 5]")
	public void verify_comics_page() throws Exception {

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		// Step 1
		test_step_details(1, "Load the comic page and verify its content");
		edl_instance.click_comics_header_menu();
		assertTrue(edl_instance.verify_item_highlighted_header_menu("comics"));
		assertTrue(edl_instance.verify_item_highlighted_footer_menu("comics"));
		assertTrue(edl_instance.verify_edl_title("Comics"));
		assertTrue(edl_instance.verfiy_date_on_page());
		/*
		 * Ensure that when page loads the feature image and left most carosel
		 * image are same
		 */
		compareEqualStrings(comic_instance.get_first_feature_comic_image(),
				comic_instance.get_left_most_carousel_image());

		assertTrue(comic_instance.verify_more_comics_present());

		// step 2
		test_step_details(2, "Verify the Feature area by changing the comics");
		assertTrue(comic_instance.verify_top_carousel_next_arrow());
		String comic_bef_change = comic_instance.get_carosel_image();
		comic_instance.click_top_carousel_next_arrow();
		assertTrue(compareNotEqualStrings(comic_bef_change, comic_instance
				.get_carosel_image())); /* ensuring image has changed */

		// step 3
		test_step_details(3, "Verify the carosel box and its comic images, count");
		assertTrue(comic_instance.verify_bottom_carousel_next_arrow());
		List<String> first_comic_list = comic_instance.get_comics_displayed_in_carosel();
		comic_instance.click_bottom_carousel_next_arrow();
		assertTrue(compareDifferentLists(first_comic_list, comic_instance.get_comics_displayed_in_carosel()));

		assertTrue(compareInts(comic_instance.get_currenct_carosel_comics_size(), 3));
		assertTrue(compareInts(comic_instance.total_comics_count(), param_comic_count + 1));

		// step 5
		test_step_details(4, "Verify the claim token button is not available");
		assertFalse(homepage_instance.verify_unclaimed_button());
		assertFalse(homepage_instance.verify_claimed_button());

	}

	@testId(test_id = "35043")
	@testCaseName(test_case_name = "B-59716-[D&T] Comics Page [Split 5]")
	@Test(priority = 2, description = "Comic page functionality for Registered User", groups = { "DESKTOP",
			"TABLET" }, testName = "35043:B-59716-[D&T] Comics Page [Split 5]")
	public void verify_comics_page_registered_user() throws Exception {

		// Step 1
		test_step_details(1, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_register();
		register.register_FullUser(getCurrentDate("dd"), getMonthWithOffset(2, "MMMM"), getYearWithOffset(-15, "YYYY"));
		assertTrue(edl_instance.verify_home());

		// step 2
		test_step_details(2, "Claim tokens in comic page and verify the Progress bar");
		edl_instance.click_comics_header_menu();
		int token_amount = edl_instance.get_Tokens();
		assertEqualsInt(edl_instance.get_unclaim_token_value(), Integer.parseInt(comic_tokens));
		edl_instance.click_claim_button();
		assertTrue(edl_instance.get_Tokens() > token_amount);
		doRefresh();
		int daily_bouns_game_count = edl_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(edl_instance.verify_daily_bonus_game_lock_icon());

		test_step_details(3, "Verify the Comic claimed tokens in the Token History page");
		edl_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		my_account_instance.verify_token_transactions_details(comic_desc, String.valueOf(token_amount), 1);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));

		// step 3
		test_step_details(4, "Verify the comic page content");
		edl_instance.click_comics_header_menu();
		assertTrue(edl_instance.verify_item_highlighted_header_menu("comics"));
		assertTrue(edl_instance.verify_item_highlighted_footer_menu("comics"));
		assertTrue(edl_instance.verify_edl_title("Comics"));
		assertTrue(edl_instance.verfiy_date_on_page());
		compareEqualStrings(comic_instance.get_first_feature_comic_image(),
				comic_instance.get_left_most_carousel_image());
		assertTrue(comic_instance.verify_more_comics_present());

		test_step_details(5, "Verify the comic image and carosel");

		assertTrue(comic_instance.verify_top_carousel_next_arrow());
		String comic_bef_change = comic_instance.get_carosel_image();
		comic_instance.click_top_carousel_next_arrow();
		assertTrue(compareNotEqualStrings(comic_bef_change, comic_instance
				.get_carosel_image())); /* ensuring image has changed */

		List<String> first_comic_list = comic_instance.get_comics_displayed_in_carosel();
		comic_instance.click_bottom_carousel_next_arrow();
		assertTrue(compareDifferentLists(first_comic_list, comic_instance.get_comics_displayed_in_carosel()));

		assertTrue(compareInts(comic_instance.get_currenct_carosel_comics_size(), 3));
		assertTrue(compareInts(comic_instance.total_comics_count(), param_comic_count + 1));

		test_step_details(6, "Verify search box is displayed and the trending now section");
		assertTrue(edl_instance.verify_searchBox());
		test_step_details(7, "Verify the trending now section");
		assertEqualsIgnoreCase(edl_instance.get_trending_now_title(), trending_now_text);

		String trending_now_first_video_text = edl_instance.get_trending_now_first_video_title();
		edl_instance.click_first_trending_now_category_video();
		assertEqualsIgnoreCase(trending_now_first_video_text, video_page_instance.get_video_title());
		navigate_back();

	}

	@testId(test_id = "35043")
	@testCaseName(test_case_name = "B-59716-[D&T] Comics Page [Split 5]")
	@Test(priority = 3, description = "Comic page ads functionality", groups = { "DESKTOP",
			"TABLET" }, testName = "35043:B-59716-[D&T] Comics Page [Split 5]")
	public void verify_comics_page_ads() throws Exception {

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_comics_header_menu();

		test_step_details(1, "Verify the comic page ads gets refreshed on feature area next button is clicked");
		String page_ad_google_ids[] = edl_instance.get_page_ad_google_query_id_with_bottom_ad();
		comic_instance.click_top_carousel_next_arrow();
		sleepFor(2);
		/*
		 * now compare all the ads and assert they have changed - next 4 lines
		 */
		assertNotEquals(page_ad_google_ids[0], edl_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_instance.get_bottom_native_ad_google_query_id());

		test_step_details(2, "Verify the comic page ads gets refreshed when carosel area next button is clicked");
		page_ad_google_ids = edl_instance.get_page_ad_google_query_id_with_bottom_ad();
		comic_instance.click_bottom_carousel_next_arrow();
		sleepFor(2);
		/*
		 * now compare all the ads and assert they have changed - next 4 lines
		 */
		assertNotEquals(page_ad_google_ids[0], edl_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_instance.get_bottom_native_ad_google_query_id());

		test_step_details(3, "Verify the comic page ads gets refreshed when more comics area next button is clicked");
		page_ad_google_ids = edl_instance.get_page_ad_google_query_id_with_bottom_ad();
		comic_instance.click_more_comics_next_arrow();
		sleepFor(2);
		/*
		 * now compare all the ads and assert they have changed - next 4 lines
		 */
		assertNotEquals(page_ad_google_ids[0], edl_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_instance.get_bottom_native_ad_google_query_id());

		test_step_details(3, "Verify dimention and size of all the adds");
		assertEquals(edl_instance.get_size_of_inline_gpt_ad()[0], "728");
		assertEquals(edl_instance.get_size_of_inline_gpt_ad()[1], "90");

		assertEquals(edl_instance.get_size_of_bottom_native_ad()[0], "770");
		assertEquals(edl_instance.get_size_of_bottom_native_ad()[1], "320");

		assertEquals(edl_instance.get_size_of_right_rail_gpt_ad_1()[0], "300");
		if (edl_instance.get_size_of_right_rail_gpt_ad_1()[1].equals("600")
				|| edl_instance.get_size_of_right_rail_gpt_ad_1()[1].equals("250")) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}

		assertEquals(edl_instance.get_size_of_right_rail_gpt_ad_2()[0], "300");
		assertEquals(edl_instance.get_size_of_right_rail_gpt_ad_2()[1], "250");

	}

	@testId(test_id = "35195")
	@testCaseName(test_case_name = "[D /T/ M] Central Business Location to track EDL activity")
	@Test(priority = 4, description = "Central Business Location to track EDL activity", groups = { "DESKTOP",
			"TABLET" }, testName = "35195:[D /T/ M] Central Business Location to track EDL activity")
	public void verify_edl_activity() throws Exception {
		// step 1
		test_step_details(1, "Create a full reg user and click on first video link");
		edl_instance.click_register();
		String user_email = register.register_FullUser();
		edl_instance.click_comics_header_menu();		
		edl_instance.click_claim_button();
		
		test_step_details(2, "Assert the EDl activity in db");
		LinkedHashMap<String, String> log_details = db_instance.get_story_log_details(user_email);
		
		assertEquals(log_details.get("story_id"), "comic");
		assertEquals(log_details.get("tokens"), comic_tokens);
		assertEquals(log_details.get("category"), "comic");		
		assertEquals(log_details.get("claimed"), "1");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
	}

}
