package com.miscellaneous;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class OurPicksTests extends BaseClass {

	private final AccountsRegisterPage account_register_isntance = AccountsRegisterPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage account_signin_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String homepage_article_name = "Homepage";
/*
	@testId(test_id = "32120,33486,33485")
	@testCaseName(test_case_name = "Frontpage Redesign Integration-Homepage Phase I [Split2],[D] B46042 - Update our picks images on Homepage, [T] B46042 - Update our picks images on Homepage")
	@Test(priority = 1, description = "Verify the Our Picks section pre-defined static tiles for Recognised user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "32120:Frontpage Redesign Integration-Homepage Phase I [Split2],33486:[D] B46042 - Update our picks images on Homepage")
	public void verify_our_pick_tiles_rec_user() throws Exception {
		test_Method_details(1, "Verify the Our Picks section pre-defined static tiles for Recognised user");
		// Step 1
		test_step_details(1, "Create a full reg user from Frontpage application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		account_register_isntance.register_FullUser();
		homepage_instance.close_evergage_popup();
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Horoscope link and verify the page");
		String expected_horoscope_url = xmlReader(ENVIRONMENT, "BaseURL") + "everydaylife/horoscope";
		String expected_horoscope_title = "Horoscope";
		homepage_instance.click_horoscope_our_pick();
		String actual_horoscope_url = getCurrentUrl();
		String actual_horoscope_title = getTitle();
		assertIsStringEndsWith(actual_horoscope_url, expected_horoscope_url);
		assertEquals(actual_horoscope_title, expected_horoscope_title);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Click on Weather link and verify the page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		String expected_weather_url = xmlReader(ENVIRONMENT, "BaseURL") + "weather";
		String expected_weather_title = "Weather";
		homepage_instance.click_weather_our_pick();
		String actual_weather_url = getCurrentUrl();
		String actual_weather_title = getTitle();
		assertEquals(actual_weather_url, expected_weather_url);
		assertEquals(actual_weather_title, expected_weather_title);
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Click on Lottery link and verify the page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		String expected_lottery_title = "Lottery";
		String expected_lottery_url = xmlReader(ENVIRONMENT, "BaseURL") + "lottery";
		homepage_instance.click_lottery_our_pick();
		String actual_lottery_url = getCurrentUrl();
		String actual_lottery_title = getTitle();
		assertEquals(actual_lottery_url, expected_lottery_url);
		assertEquals(actual_lottery_title, expected_lottery_title); // Logout the user
		account_signin_instance.logout();
		step_validator(4, true);
	}

	@testId(test_id = "32120")
	@testCaseName(test_case_name = "Frontpage Redesign Integration-Homepage Phase I [Split2]")
	@Test(priority = 2, description = "Verify the Our Picks section pre-defined static tiles for UnRecognised user", groups = {
			"DESKTOP", "TABLET" }, testName = "32120:Frontpage Redesign Integration-Homepage Phase I [Split2]")
	public void verify_our_pick_tiles_unrec_user() throws Exception {
		test_Method_details(2, "Verify the Our Picks section pre-defined static tiles for UnRecognised user");

		// Step 1
		test_step_details(1, "Click on Horoscope link and verify the page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		String expected_horoscope_url = xmlReader(ENVIRONMENT, "BaseURL") + "everydaylife/horoscope";
		String expected_horoscope_title = "Horoscope";
		homepage_instance.click_horoscope_our_pick();
		String actual_horoscope_url = getCurrentUrl();
		String actual_horoscope_title = getTitle();
		assertIsStringEndsWith(actual_horoscope_url, expected_horoscope_url);
		assertEquals(actual_horoscope_title, expected_horoscope_title);
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Weather link and verify the page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		String expected_weather_url = xmlReader(ENVIRONMENT, "BaseURL") + "weather";
		String expected_weather_title = "Weather";
		homepage_instance.click_weather_our_pick();
		String actual_weather_url = getCurrentUrl();
		String actual_weather_title = getTitle();
		assertEquals(actual_weather_url, expected_weather_url);
		assertEquals(actual_weather_title, expected_weather_title);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Click on Lottery link and verify the page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		String expected_lottery_title = "Lottery";
		String expected_lottery_url = xmlReader(ENVIRONMENT, "BaseURL") + "lottery";
		homepage_instance.click_lottery_our_pick();
		String actual_lottery_url = getCurrentUrl();
		String actual_lottery_title = getTitle();
		assertEquals(actual_lottery_url, expected_lottery_url);
		assertEquals(actual_lottery_title, expected_lottery_title);
		step_validator(3, true);
	}

	@testId(test_id = "32120")
	@testCaseName(test_case_name = "Frontpage Redesign Integration-Homepage Phase I [Split2]")
	@Test(priority = 3, description = "Verify the Our Picks section story tiles for Recognised user", groups = {
			"DESKTOP", "TABLET" }, testName = "32120:Frontpage Redesign Integration-Homepage Phase I [Split2]")
	public void verify_ourpick_story_type() throws Exception {
		test_Method_details(3, "Verify the Our Picks section story tiles for Recognised user"); // Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.close_evergage_popup();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(3, "Navigate to Joomla admin and modify the OurPick story type for given tile position");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(homepage_article_name);
		boolean story_type = admin_instance.choose_ourpick_category_story_types(1);
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the modified Story type on OurPick tile");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertTrue(homepage_instance.verify_fist_ourpick_type_as_video() == story_type);
		step_validator(3, true);
	}

	@testId(test_id = "34054")
	@testCaseName(test_case_name = "Keno on Uni Nav[DT]")
	@Test(priority = 4, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the PCH Properties on Uni Nav bar for Full Reg user", testName = "34054:Keno on Uni Nav[DT]")
	public void verify_uni_nav_bar() throws Exception {
		test_Method_details(4, "Verify the PCH Properties on Uni Nav bar for Full Reg user"); // Step 1
		test_step_details(1, "Login with Full Reg.");
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.close_evergage_popup();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Validate the PCH Properties on the Uni Nav bar");
		assertTrue(homepage_instance.verify_uninav_previous_arrow_disabled_status());
		assertTrue(homepage_instance.verify_uninav_next_arrow_enabled_status());
		LinkedList<String> prop_list = homepage_instance.get_pch_property_list_names();
		assertTrue(homepage_instance.verify_uninav_previous_arrow_enabled_status());
		assertTrue(homepage_instance.verify_uninav_next_arrow_disabled_status());
		assertTrue(prop_list.size() > 0);
		step_validator(2, true);
	}*/

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 5, description = "Verify display of HomePage Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_homepage() throws Exception {
		test_Method_details(5, "Verify display of HomePage Ad's");
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_width = "300";
		String right_rail_ad_2_height = "250";
		String inline_ad_width = "728";
		String inline_ad_height = "90";
		String our_pick_native_ad_width = "187";
		String our_pick_native_ad_height = "255";
		String trending_now_native_ad_width = "300";
		String trending_now_native_ad_height = "90";
		String top_story_native_ad_width = "368";
		String top_story_native_ad_height = "97";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName6"),
				xmlReader(ENVIRONMENT, "ValidPassword1"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the display of Inline GPT Ad's and the Size");
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
		test_step_details(5, "Verify the display of Top Story Native Ad");
		assertTrue(homepage_instance.verify_top_stories_native_ad());
		assertEquals(homepage_instance.get_size_of_top_stories_native_ad()[0], top_story_native_ad_width);
		assertEquals(homepage_instance.get_size_of_top_stories_native_ad()[1], top_story_native_ad_height);
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the display of Trending Now Native Ad");
		assertTrue(homepage_instance.verify_trending_now_native_ad());
		assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[0], trending_now_native_ad_width);
		assertEquals(homepage_instance.get_size_of_trending_now_native_ad()[1], trending_now_native_ad_height);
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verify the display of Our Pick Native Ad");
		assertTrue(homepage_instance.verify_our_pick_native_ad());
		assertEquals(homepage_instance.get_size_of_our_pick_native_ad()[0], our_pick_native_ad_width);
		assertEquals(homepage_instance.get_size_of_our_pick_native_ad()[1], our_pick_native_ad_height);
		step_validator(7, true);
	}
}
