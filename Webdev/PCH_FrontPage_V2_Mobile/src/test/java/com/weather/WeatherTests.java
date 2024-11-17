package com.weather;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.WeatherPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class WeatherTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final WeatherPage weather_instance = WeatherPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String page_title = "Weather";
	private final String article_name = "Tokens / Weather";
	private final String valid_zip_code = "02801";
	private final String invalid_zip_code = "15000";
	private final String valid_city_name = "ADAMSVILLE, RI";
	private final String default_city_name = "PORT WASHINGTON, NY";

	@testId(test_id = "34487")
	@testCaseName(test_case_name = "Weather Remove the text enter city from input field")
	@Test(priority = 1, description = "Verify the Weather page for the UnRecognised user", groups = { "MOBILE",
			"SMOKE" }, testName = "34487:Weather Remove the text enter city from input field")
	public void verify_weather_page_unrecognized_user() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Weather page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		homepage_instance.click_weather_menu();

		// Step 2
		test_step_details(2, "Verify the default city");
		assertEquals(getTitle(), page_title);
		assertEquals(weather_instance.get_city_name(), default_city_name);
		assertEquals(weather_instance.get_zip_field_placeholder_msg(),
				msg_property_file_reader("weather_zip_field_placeholder_message"));

		// Step 3
		test_step_details(3, "Enter valid zip code and validate the city");
		weather_instance.enter_zip_code(valid_zip_code);
		weather_instance.click_zipcode_go_button();
		assertEquals(weather_instance.get_city_name(), valid_city_name);

		// Step 4
		test_step_details(4, "Verify the Daily, Hourly and Climate Hisotry sections");
		assertTrue(weather_instance.verify_hourly_forecast_section());
		assertTrue(weather_instance.verify_daily_forecast_section());
		assertTrue(weather_instance.verify_climate_history_section());

		// Step 5
		test_step_details(5, "Enter invalid zip code");
		weather_instance.enter_zip_code(invalid_zip_code);
		weather_instance.click_zipcode_go_button();
		assertEquals(weather_instance.get_city_name(), default_city_name);
	}

	@testId(test_id = "23382")
	@testCaseName(test_case_name = "WeatherModule_DisplayWeather_Mobile")
	@Test(priority = 2, description = "Verify the Weather Hover screen details with Main page details", groups = {
			"MOBILE" }, testName = "23382:WeatherModule_DisplayWeather_Mobile")
	public void verify_weather_page_recognized_user() throws Exception {
		String token_amount = String.valueOf(rand(100, 1000));
		String token_desc = generateRandomString(6);

		// Step 1
		test_step_details(1, "Login to the Joomla and modify the token amount");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		admin_instance.enter_text_field_element_by_label("Description", token_desc);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Register a user and navigate to weather page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		sign_in_instance.click_register();
		register_instance.register_full_user_with_optin();
		lb_instance.close_gs_overlay();
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_weather_menu();

		// Step 3
		test_step_details(3, "Verify the default city name");
		assertEquals(getTitle(), page_title);
		assertEquals(weather_instance.get_city_name(), default_city_name);
		assertEquals(weather_instance.get_zip_field_placeholder_msg(),
				msg_property_file_reader("weather_zip_field_placeholder_message"));

		// Step 4
		test_step_details(4, "Enter valid zip code and validate the city");
		weather_instance.enter_zip_code(valid_zip_code);
		weather_instance.click_zipcode_go_button();
		assertEquals(weather_instance.get_city_name(), valid_city_name);

		// Step 5
		test_step_details(5, "Verify the Daily, Hourly and Climate Hisotry sections");
		assertTrue(weather_instance.verify_hourly_forecast_section());
		assertTrue(weather_instance.verify_daily_forecast_section());
		assertTrue(weather_instance.verify_climate_history_section());

		// Step 6
		test_step_details(6, "Enter invalid zip code");
		weather_instance.enter_zip_code(invalid_zip_code);
		weather_instance.click_zipcode_go_button();
		assertEquals(weather_instance.get_city_name(), default_city_name);

		// Step 7
		test_step_details(7, "Verify the current statistics report of the city");
		assertTrue(weather_instance.verify_current_weather_stats());
		assertTrue(weather_instance.verify_current_weather_sunrise_stats());
		weather_instance.collapse_current_weather_stats();

		// Step 8
		test_step_details(8, "Verify the Claim token amount and the Claim button");
		assertEquals(homepage_instance.get_unclaimed_token_amount(), token_amount);
		assertTrue(homepage_instance.verify_claim_button());
		homepage_instance.click_claim_button();

		// Step 9
		test_step_details(9, "Verify the Claimed status of the user and the Progres bar");
		assertTrue(homepage_instance.verify_claimed_button());
		assertIsStringContainsIgnoreCase(homepage_instance.get_unis_message(),
				msg_property_file_reader("weather_activity_message", token_amount) + " " + token_desc);
		assertEqualsInt(homepage_instance.get_daily_bonus_game_check_count(), 1);
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 3, description = "Verify the Weather Climate history and the Ad refresh", testName = "")
	public void verify_ad_refresh_on_weather_climate_history_section() throws Exception {
		// Step 1
		test_step_details(1, "Verify the presence of Weather Climate History section");
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		weather_instance.click_weather_menu();
		assertTrue(weather_instance.verify_climate_history_section());

		// Step 2
		test_step_details(2, "Verify the default selected month");
		String expected_default_month = "April";
		assertEqualsIgnoreCase(weather_instance.get_selected_month_of_climate_history(), expected_default_month);

		// Step 3
		test_step_details(3, "Modify month to verify the Ad refresh");
		String ad_google_id = weather_instance.get_native_ad_google_query_id();
		weather_instance.select_climate_history_month(getMonthWithOffset(-2, "MMMM"));
		expected_default_month = getMonthWithOffset(-2, "MMMM");
		assertEqualsIgnoreCase(weather_instance.get_selected_month_of_climate_history(), expected_default_month);
		sleepFor(3);
		assertNotEquals(ad_google_id, weather_instance.get_native_ad_google_query_id());
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 4, description = "Verify the Daily forecast section and the Ad refresh", testName = "")
	public void verify_ad_refresh_on_daily_forecast_section() throws Exception {
		// Step 1
		test_step_details(1, "Verify the presence of Daily forecast section section");
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		weather_instance.click_weather_menu();
		assertTrue(weather_instance.verify_daily_forecast_section());

		// Step 2
		test_step_details(2, "Expand to detailed section and verify the Ad refresh");
		String ad_google_id = weather_instance.get_native_ad_google_query_id();
		weather_instance.verify_detailed_daily_forecast_section();
		sleepFor(3);
		assertNotEquals(ad_google_id, weather_instance.get_native_ad_google_query_id());

		// Step 3
		test_step_details(3, "Collapse the detailed section and verify the Ad refresh");
		ad_google_id = weather_instance.get_native_ad_google_query_id();
		weather_instance.collapse_daily_forecast_details();
		sleepFor(3);
		assertNotEquals(ad_google_id, weather_instance.get_native_ad_google_query_id());
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 5, description = "Verify the Hourly forecast section and the Ad refresh", testName = "")
	public void verify_ad_refresh_on_hourly_forecast_section() throws Exception {
		// Step 1
		test_step_details(1, "Verify the presence of Hourly forecast section section");
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		weather_instance.click_weather_menu();
		assertTrue(weather_instance.verify_hourly_forecast_section());

		// Step 2
		test_step_details(2, "Expand to detailed section and verify the Ad refresh");
		String ad_google_id = weather_instance.get_native_ad_google_query_id();
		weather_instance.verify_detailed_hourly_forecast_section();
		sleepFor(3);
		assertNotEquals(ad_google_id, weather_instance.get_native_ad_google_query_id());

		// Step 3
		test_step_details(3, "Collapse the detailed section and verify the Ad refresh");
		ad_google_id = weather_instance.get_native_ad_google_query_id();
		weather_instance.collapse_hourly_forecast_details();
		sleepFor(3);
		assertNotEquals(ad_google_id, weather_instance.get_native_ad_google_query_id());
	}

}
