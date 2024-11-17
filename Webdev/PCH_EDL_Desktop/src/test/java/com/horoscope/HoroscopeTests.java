package com.horoscope;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HoroscopePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HoroscopeTests extends BaseClass {

	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final HoroscopePage horoscope_instance = HoroscopePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private String article_name = "Tokens / Horoscope";
	private final DB_Connector db_instance = DB_Connector.getInstance();
	String cbl = "everydaylife";

	@testId(test_id = "32352, 34580")
	@testCaseName(test_case_name = "FP Redesign-Horoscope, B-59727[D,T] Re-Skin Horoscope Page for Everyday Life")
	@Test(priority = 1, description = "Verify Horoscope page tokens and subsections - Recognized user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "32352:FP Redesign-Horoscope, 34580:B-59727[D,T] Re-Skin Horoscope Page for Everyday Life")
	public void verify_horoscope_page_recognized_user() throws Exception {
		String horoscope_tokens = String.valueOf(rand(0, 1000));
		String horoscope_desc = "For Horoscope! " + generateRandomString(6);

		// Step 1
		test_step_details(1, "Modify the horoscope page tokens and save in Joomla");
		log.info("Login to Joomla and navigate to article name: " + article_name);
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", horoscope_tokens);
		admin_instance.enter_text_field_element_by_label("Description", horoscope_desc);
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_home_instance.click_register();
		register.register_FullUser(getCurrentDate("dd"), getMonthWithOffset(2, "MMMM"), getYearWithOffset(-15, "YYYY"));
		assertTrue(edl_home_instance.verify_home());

		// Step 3
		test_step_details(3, "Claim tokens in horoscope page and verify the Progress bar");
		int token_amount = edl_home_instance.get_token_amount_from_uninav();
		edl_home_instance.click_horoscope_menu();
		assertEqualsInt(edl_home_instance.get_unclaim_token_value(), Integer.parseInt(horoscope_tokens));
		edl_home_instance.click_claim_button();
		assertTrue(edl_home_instance.get_token_amount_from_uninav() > token_amount);
		doRefresh();
		int daily_bouns_game_count = edl_home_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(edl_home_instance.verify_daily_bonus_game_lock_icon());

		// Step 4
		test_step_details(4, "Verify the Horoscope page links and the given month details");
		assertIsStringContainsIgnoreCase(horoscope_instance.get_horoscope_month(),
				getMonthWithOffset(2, "MMMM").substring(0, 3));
		assertTrue(horoscope_instance.verify_today_label());
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));
		horoscope_instance.click_yesterday_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("yesterday"));
		horoscope_instance.click_tommorow_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("tomorrow"));
		horoscope_instance.click_outlook_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("2018 Outlook"));
		horoscope_instance.click_today_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));

		// Step 5
		test_step_details(5, "Verify the Horoscope page subsections");
		assertTrue(horoscope_instance.getSubsectionsList().size() == 5);
		horoscope_instance.click_gemini();
		assertTrue(horoscope_instance.verify_overview_text().equalsIgnoreCase("Gemini"));

		// Step 6
		test_step_details(5, "Verify the Horoscope claimed tokens in the Token History page");
		edl_home_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		my_account_instance.verify_token_transactions_details(horoscope_desc, String.valueOf(token_amount), 1);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
	}

	@testId(test_id = "32352, 34580")
	@testCaseName(test_case_name = "FP Redesign-Horoscope, B-59727[D,T] Re-Skin Horoscope Page for Everyday Life")
	@Test(priority = 2, description = "Verify Horoscope page tokens and subsections - Unrecognized user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "32352:FP Redesign-Horoscope, 34580:B-59727[D,T] Re-Skin Horoscope Page for Everyday Life")
	public void verify_horoscope_page_unrecognized_user() throws Exception {
		// Step 1
		test_step_details(1, "Verify the Claim button on Horoscope page for Un Recognised user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		edl_home_instance.click_horoscope_menu();
		assertFalse(edl_home_instance.verify_unclaimed_button());
		assertFalse(edl_home_instance.verify_claimed_button());

		// Step 2
		test_step_details(2, "Verify the Horoscope page links for Un Recognised user");
		assertTrue(horoscope_instance.verify_today_label());
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));
		horoscope_instance.click_yesterday_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("yesterday"));
		horoscope_instance.click_tommorow_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("tomorrow"));
		horoscope_instance.click_outlook_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("2018 Outlook"));
		horoscope_instance.click_today_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));

		// Step 3
		test_step_details(3, "Verify the Horoscope page subsections");
		assertTrue(horoscope_instance.getSubsectionsList().size() == 5);
		horoscope_instance.click_gemini();
		assertTrue(horoscope_instance.verify_overview_text().equalsIgnoreCase("Gemini"));
	}

	@testId(test_id = "32683,34580")
	@testCaseName(test_case_name = "B-44512 Frontpage Redesign-Ad Tags, B-59727[D,T] Re-Skin Horoscope Page for Everyday Life")
	@Test(priority = 3, description = "Verify display of Horoscope page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "32683:B-44512 Frontpage Redesign-Ad Tags, 34580:B-59727[D,T] Re-Skin Horoscope Page for Everyday Life")
	public void verify_ads_on_horoscope_page() {
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_height = "250";
		String inline_tile_ad_width = "728";
		String inline_tile_ad_height = "90";
		String bottom_native_ad_width = "770";
		String bottom_native_ad_height = "320";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		edl_home_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());

		// Step 2
		test_step_details(2, "Verify the display of Right Rail Ad's and the Size");
		edl_home_instance.click_horoscope_menu();
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
		test_step_details(4, "Verify the display of bottom Native Ad");
		assertTrue(edl_home_instance.verify_bottom_native_ad());
		assertEquals(edl_home_instance.get_size_of_bottom_native_ad()[0], bottom_native_ad_width);
		assertEquals(edl_home_instance.get_size_of_bottom_native_ad()[1], bottom_native_ad_height);

		// Step 5
		test_step_details(5, "Verify the Ad refresh after click on the Yesterday horoscope link");
		String page_ad_google_ids[] = edl_home_instance.get_page_ad_google_query_id_with_bottom_ad();
		horoscope_instance.click_yesterday_link();
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_home_instance.get_bottom_native_ad_google_query_id());
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_with_bottom_ad();

		// Step 6
		test_step_details(6, "Verify the Ad refresh after click on the Today horoscope link");
		horoscope_instance.click_today_link();
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_home_instance.get_bottom_native_ad_google_query_id());
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_with_bottom_ad();

		// Step 7
		test_step_details(7, "Verify the Ad refresh after click on the Tomorrow horoscope link");
		horoscope_instance.click_tommorow_link();
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_home_instance.get_bottom_native_ad_google_query_id());
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_with_bottom_ad();

		// Step 8
		test_step_details(8, "Verify the Ad refresh after click on the any horoscope sign link");
		horoscope_instance.click_gemini();
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
		assertNotEquals(page_ad_google_ids[3], edl_home_instance.get_bottom_native_ad_google_query_id());
		page_ad_google_ids = edl_home_instance.get_page_ad_google_query_id_with_bottom_ad();

		// Step 9 - this page does not have [3] bottom native ad
		test_step_details(9, "Verify the Ad refresh after click on the Outlook horoscope link");
		horoscope_instance.click_outlook_link();
		assertNotEquals(page_ad_google_ids[0], edl_home_instance.get_inline_gpt_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], edl_home_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], edl_home_instance.get_right_rail_ad_2_google_query_id());
	}

	@testId(test_id = "35195")
	@testCaseName(test_case_name = "B-60664 [D T] Central Business Location to track EDL activity")
	@Test
	public void verify_story_log() throws Exception {
		edl_home_instance.click_register();
		String user_email = register.register_FullUser();
		lb_instance.close_bronze_level_up_lb();
		edl_home_instance.click_horoscope_menu();
		String token_amount = horoscope_instance.get_unclaim_token_value();
		String category_type = edl_home_instance.get_main_category_type();
		LinkedHashMap<String, String> log_details1 = db_instance.get_story_log_details(user_email);
		assertEquals(log_details1.get("tokens"), token_amount);
		assertEquals(log_details1.get("claimed"), "0");
		assertEquals(log_details1.get("cbl"), cbl);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(log_details1.get("device"), "D");
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(log_details1.get("device"), "T");
		}
		assertEquals(log_details1.get("category"), category_type);

	}
}
