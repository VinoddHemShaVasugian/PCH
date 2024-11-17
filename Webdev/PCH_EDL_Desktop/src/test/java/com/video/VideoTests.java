package com.video;

import java.util.LinkedHashMap;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.MyAccount;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class VideoTests extends BaseClass {

	private final EDLHomePage edl_instance = EDLHomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();

	@testId(test_id = "35100")
	@testCaseName(test_case_name = "B-59722 [D&T] Video Page [Split 1]")
	@Test(priority = 1, description = "Video page functionality for unregistered User", groups = { "DESKTOP",
			"TABLET" }, testName = "35100:B-59722 [D&T] Video Page [Split 1]")
	public void verify_video_page() throws Exception {

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		test_step_details(1, "Verify the video page opened by clicking first video on the edl home page");
		String first_video_dec = edl_instance.get_first_video_desc().replace(".", "");
		edl_instance.click_first_video();
		assertIsStringContainsIgnoreCase(getTitle(), "videos");

		test_step_details(2, "Assert the video page layout");
		assertTrue(video_instance.verify_video_player());
		assertTrue(video_instance.verify_video_overlay_title());
		assertIsStringContainsIgnoreCase(video_instance.get_video_title(), first_video_dec);
		edl_instance.verfiy_date_on_page();
		String urlPageCategory = getCurrentUrl().substring(getCurrentUrl().lastIndexOf("/") + 1).replace("-", " ");
		String edlPageCategory = video_instance.get_video_page_edl_title()
				.substring(video_instance.get_video_page_edl_title().lastIndexOf("/") + 1);
		assertIsStringContainsIgnoreCase(urlPageCategory, edlPageCategory);
		video_instance.verify_token_claim_status(msg_property_file_reader("video_player_default_message"));
		assertEquals(video_instance.get_video_title_on_right_rail_playlist(), video_instance.get_video_title());

		test_step_details(3, "Verify the links displayed on video post completion");
		assertTrue(video_instance.verify_play_circle());
		assertTrue(video_instance.verify_next_video_unrec_user());
		String next_video_title = video_instance.get_next_video_to_be_played_text();

		test_step_details(4, "On playing the next video it gets displayed");
		video_instance.click_play_circle();
		assertEqualsIgnoreCase(next_video_title, video_instance.get_video_title());
		assertEquals(video_instance.get_video_title_on_right_rail_playlist(), video_instance.get_video_title());
	}

	@testId(test_id = "35100")
	@testCaseName(test_case_name = "B-59722 [D&T] Video Page [Split 1]")
	@Test(priority = 2, description = "Verify Video page for registered user", groups = { "DESKTOP",
			"TABLET" }, testName = "35100:B-59722 [D&T] Video Page [Split 1]")
	public void verify_video_page_for_registered_user() throws Exception {

		String token_claim_desc = randomString(5, 10);
		int token_amount = rand(10, 150);
		test_step_details(1, "Modify the video page tokens in Joomla");

		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("Tokens / Edl Video Claim Tokens");
		admin_instance.enter_text_field_element_by_label("Description", token_claim_desc, 1);
		admin_instance.enter_text_field_element_by_label("Tokens", Integer.toString(token_amount));

		assertTrue(admin_instance.publish_article());

		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		test_step_details(2, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_instance.click_register();
		register.register_FullUser();
		assertTrue(homepage_instance.verify_Home());
		int ini_token_amount = edl_instance.get_token_amount_from_uninav();

		test_step_details(3, "Verify the video page opened by clicking first video on the edl home page");
		String first_video_dec = edl_instance.get_first_video_desc().replace(".", "");
		edl_instance.click_first_video();
		assertIsStringContainsIgnoreCase(getTitle(), "videos");
		assertIsStringContainsIgnoreCase(video_instance.get_video_title(), first_video_dec);

		test_step_details(4, "Verify the tokens are auto claimed and the token amount matches");
		assertTrue(video_instance.verify_tokens_claimed_button());
		int token_amount_after_video_claim = edl_instance.get_token_amount_from_uninav();
		assertEquals(video_instance.get_token_award_message(),
				msg_property_file_reader("videopage_success_message", Integer.toString(token_amount)));
		assertEquals(edl_instance.get_latest_video_activity_description(), token_claim_desc);
		assertEqualsInt(Integer.parseInt(video_instance.get_claimed_token_amount()), token_amount);
		assertEquals(edl_instance.get_latest_activity_token_amount(), Integer.toString(token_amount));

		test_step_details(5, "Verify the tokens amount is added to existing tokens and that they cannot be reclaimed");
		assertTrue(token_amount_after_video_claim == ini_token_amount + token_amount);
		doRefresh();
		assertEqualsInt(1, edl_instance.get_daily_bonus_game_check_count());
		assertTrue(edl_instance.verify_daily_bonus_game_lock_icon());
		assertTrue(video_instance.verify_token_already_claim_button());

		test_step_details(6, "Verify the claimed tokens in the Token History page");
		edl_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		my_account_instance.verify_token_transactions_details(token_claim_desc, String.valueOf(token_amount), 1);
		navigate_back();
	}

	@testId(test_id = "35100")
	@testCaseName(test_case_name = "B-59722 [D&T] Video Page [Split 1]")
	@Test(priority = 3, description = "Verify ads on Video page", groups = { "DESKTOP",
			"TABLET" }, testName = "35100:B-59722 [D&T] Video Page [Split 1]")
	public void verify_ads_on_video_page() throws Exception {

		String video_page_ads[] = new String[2];

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		test_step_details(1, "Verify the video page opened by clicking first video on the edl home page");
		String first_video_dec = edl_instance.get_first_video_desc();
		edl_instance.click_first_video();
		assertTrue(video_instance.verify_video_player());
		assertIsStringContainsIgnoreCase(getTitle(), "videos");
		assertIsStringContainsIgnoreCase(video_instance.get_video_title(), first_video_dec.replace(".", ""));

		test_step_details(2, "Get Ad Details of the page");
		assertTrue(edl_instance.verify_inline_gpt_ad());
		assertTrue(edl_instance.verify_right_rail_gpt_ad_1());
		video_page_ads[0] = edl_instance.get_inline_gpt_ad_google_query_id();
		video_page_ads[1] = edl_instance.get_right_rail_ad_1_google_query_id();

		test_step_details(3, "Click on play after video completed and check next video is played");

		video_instance.click_play_circle();
		sleepFor(10);
		assertNotEqualsIgnoreCase(first_video_dec, video_instance.get_video_title());
		assertEquals(video_instance.get_video_title_on_right_rail_playlist(), video_instance.get_video_title());

		test_step_details(4, "Ensure ads got refreshed after playing next video");
		assertNotEquals(video_page_ads[0], edl_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(video_page_ads[1], edl_instance.get_right_rail_ad_1_google_query_id());

		test_step_details(5, "Ensure ads got refreshed on selecting a video from right rail");
		video_page_ads[0] = edl_instance.get_inline_gpt_ad_google_query_id();
		video_page_ads[1] = edl_instance.get_right_rail_ad_1_google_query_id();
		video_instance.click_random_video_on_right_playlist();
		sleepFor(20);
		assertNotEquals(video_page_ads[0], edl_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(video_page_ads[1], edl_instance.get_right_rail_ad_1_google_query_id());

		test_step_details(6, "Verify search box is displayed and the trending now section");
		assertTrue(edl_instance.verify_searchBox());

		String trending_now_first_video_text = edl_instance.get_trending_now_first_video_title();
		edl_instance.click_first_trending_now_category_video();
		assertEqualsIgnoreCase(trending_now_first_video_text, video_instance.get_video_title());
		navigate_back();

	}

	@testId(test_id = "35195")
	@testCaseName(test_case_name = "[D /T/ M] Central Business Location to track EDL activity")
	@Test(priority = 3, description = "Central Business Location to track EDL activity", groups = { "DESKTOP",
			"TABLET" }, testName = "35195:[D /T/ M] Central Business Location to track EDL activity")
	public void verify_story_log() throws Exception {
		// step 1
		test_step_details(1, "Create a full reg user and click on first video link");
		edl_instance.click_register();
		String user_email = register.register_FullUser();		
		edl_instance.click_first_video();		
		
		test_step_details(2, "Get the db assertion values from front end");
		String video_title = video_instance.get_video_title();
		String category = video_instance.get_video_page_edl_title()
				.substring(video_instance.get_video_page_edl_title().lastIndexOf("/") + 1).trim();
		String video_id = getCurrentUrl().split("/")[getCurrentUrl().split("/").length - 2];
		String token_amount = video_instance.get_claimed_token_amount();

		test_step_details(3, "Assert the front end values present in db");
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_email);
		assertEquals(log_details.get("video_id"), video_id);
		assertEquals(log_details.get("video_title"), video_title);
		assertEquals(log_details.get("tokens"), token_amount);
		assertEquals(log_details.get("category"), category.toLowerCase());
		assertEquals(log_details.get("cbl"), "everydaylife");
		assertEquals(log_details.get("claimed"), "1");
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}

	}

}
