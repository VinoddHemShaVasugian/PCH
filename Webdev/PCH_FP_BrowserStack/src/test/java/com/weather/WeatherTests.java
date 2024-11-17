package com.weather;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.WeatherPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class WeatherTests extends BaseClass {

	private final AccountsRegisterPage account_register_isntance = AccountsRegisterPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final WeatherPage weather_instance = WeatherPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();

	private final String page_title = "Weather";
	private final String valid_zip_code = "02801";
	private final String invalid_zip_code = "15000";
	private final String valid_city_name = "ADAMSVILLE, RI";
	private final String default_city_name = "PORT WASHINGTON, NY";
	private final String zipcode_placeholder = "ENTER ZIP CODE";
/*
	@testId(test_id = "RT-04232")
	@testCaseName(test_case_name = "Weather Page [D/T/M]")
	@Test(priority = 1, description = "Verify the Weather Hover screen details with Main page details", groups = {
			"DESKTOP", "TABLET" }, testName = "Weather Page [D/T/M]")
	public void verify_weather_hover_screen() throws Exception {
		test_Method_details(1, "Verify the Weather Hover screen details with Main page details");
		// Step 1
		test_step_details(1, "Create a full reg user with current date as DOB from Frontpage application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		account_register_isntance.register_FullUser(getCurrentDate("dd"), getCurrentMonth("MMMM"),
				getYearWithOffset(-15, "YYYY"));
		lb_instance.close_welcome_optin_lb();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Hover the Weather Menu");
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		scrollToTopOfPage();
		weather_instance.click_evergage_top_banner();
		weather_instance.hover_weather_menu();
		String hover_city_name = weather_instance.get_city_from_hover_screen();
		System.out.println("Step 2 -> hover_city_name: " + hover_city_name);
		String hover_humidity_value = weather_instance.get_humidity_from_hover_screen();
		System.out.println("Step 2 -> hover_humidity_value: " + hover_humidity_value);
		String hover_dew_point_value = weather_instance.get_temp_dew_point_from_hover_screen();
		System.out.println("Step 2 -> hover_dew_point_value: " + hover_dew_point_value);
		String hover_temp_value = weather_instance.get_temp_from_hover_screen();
		System.out.println("Step 2 -> hover_temp_value: " + hover_temp_value);
		String hover_temp_image_src = weather_instance.get_temp_image_from_hover_screen();
		System.out.println("Step 2 -> hover_temp_image_src: " + hover_temp_image_src);
		String hover_temp_winds = weather_instance.get_temp_winds_from_hover_screen();
		System.out.println("Step 2 -> hover_temp_winds: " + hover_temp_winds);
		assertTrue(weather_instance.verify_edit_icon_on_hover_screen());
		assertTrue(weather_instance.verify_city_name_on_hover_screen());
		assertTrue(weather_instance.verify_full_forecast_icon_on_hover_screen());
		assertTrue(weather_instance.verify_temp_date_time_on_hover_screen());
		assertTrue(weather_instance.verify_temp_dew_point_on_hover_screen());
		assertTrue(weather_instance.verify_temp_image_on_hover_screen());
		assertTrue(weather_instance.verify_temp_humidity_on_hover_screen());
		assertTrue(weather_instance.verify_temp_on_hover_screen());
		assertTrue(weather_instance.verify_temp_winds_on_hover_screen());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Click on Edit icon on Hover screen");
		weather_instance.click_edit_zipcode_on_weather_hover();
		sleepFor(5);
		assertEquals(getTitle(), page_title);
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Enter valid zip code");
		assertEquals(weather_instance.get_zip_code_field_placeholder_text(), zipcode_placeholder);
		weather_instance.enter_zip_code(valid_zip_code);
		weather_instance.click_zipcode_go_button();

		// Step 5
		test_step_details(5, "Click on Full Forecast link on Hover screen");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		weather_instance.click_evergage_top_banner();
		scrollToTopOfPage();
		weather_instance.hover_weather_menu();
		weather_instance.click_full_forecast_weather__hover();
		sleepFor(5);
		assertEquals(getTitle(), page_title);
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Validate the Hover details with the main page details");
		doRefresh();
		scrollToTopOfPage();
		weather_instance.click_evergage_top_banner();
		weather_instance.hover_weather_menu();
		hover_city_name = weather_instance.get_city_from_hover_screen();
		System.out.println("Step 4 -> hover_city_name1: " + hover_city_name);
		hover_humidity_value = weather_instance.get_humidity_from_hover_screen();
		hover_dew_point_value = weather_instance.get_temp_dew_point_from_hover_screen();
		hover_temp_value = weather_instance.get_temp_from_hover_screen();
		hover_temp_image_src = weather_instance.get_temp_image_from_hover_screen();
		hover_temp_winds = weather_instance.get_temp_winds_from_hover_screen();
		assertEquals(weather_instance.get_city_name(), valid_city_name);
		String city_name = weather_instance.get_city_name().split(",")[0].toLowerCase();
		String humidity_value = weather_instance.get_city_humidity();
		String dew_point_value = weather_instance.get_city_dew_points();
		String temp_value = weather_instance.get_city_temp();
		String temp_image_src = weather_instance.get_temp_image_src_details();
		String temp_winds = weather_instance.get_city_wind_details();
		assertIsStringContains(hover_city_name, city_name);
		assertEquals(hover_humidity_value, humidity_value);
		assertIsStringContains(dew_point_value, hover_dew_point_value);
		assertIsStringContains(hover_temp_value, temp_value);
		assertIsStringEndsWith(hover_temp_image_src, temp_image_src);
		assertIsStringContains(hover_temp_winds, temp_winds);
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Click on Claim button");
		assertTrue(homepage_instance.click_claim_button());
		homepage_instance.click_weather_menu();
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the Claimed status of the user and the Progres bar");
		assertTrue(homepage_instance.verify_claimed_button());
		doRefresh();
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		step_validator(8, true);

		// Step 9
		test_step_details(9, "Verify the Daily, Hourly and Climate Hisotry sections");
		assertTrue(weather_instance.verify_hourly_forecast_section());
		assertTrue(weather_instance.verify_daily_forecast_section());
		assertTrue(weather_instance.verify_weather_video_section());
		assertTrue(weather_instance.verify_climate_history_section());
		assertTrue(weather_instance.verify_weather_ad_section());
		step_validator(9, true);

		// Step 10
		test_step_details(10, "Verify the iamge of Header and Footer menu");
		assertEquals(weather_instance.get_header_image_src(), weather_instance.get_footerr_image_src());
		step_validator(10, true);

		// Step 11
		test_step_details(11, "Enter invalid zip code");
		weather_instance.enter_zip_code(invalid_zip_code);
		weather_instance.click_zipcode_go_button();
		assertEquals(weather_instance.get_city_name(), default_city_name);
		step_validator(11, true);
	}
*/
	@testId(test_id = "RT-04232")
	@testCaseName(test_case_name = "Weather Page [D/T/M]")
	@Test(priority = 2, description = "Verify the Hourly Forecast section details with Ad refresh", groups = {
			"DESKTOP", "TABLET" }, testName = "Weather Page [D/T/M]")
	public void verify_hourly_forecast_details() throws Exception {
		test_Method_details(2, "Verify the Hourly Forecast section details with Ad refresh");

		// Step 1
		test_step_details(1, "Verify the presence of Hourly Forecast section");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		homepage_instance.close_openx_banner();
		weather_instance.click_evergage_top_banner();
		scrollToTopOfPage();
		weather_instance.click_weather_menu();
		assertTrue(weather_instance.verify_hourly_forecast_section());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the View More details link of Hourly Forecast section");
		String page_ad_google_ids[] = weather_instance.get_page_ad_google_query_id();
		weather_instance.click_view_less_details();
		String table_status = weather_instance.get_hourly_forecast_expand_status();
		assertEquals(table_status, "");
		assertNotEquals(page_ad_google_ids[0], weather_instance.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], weather_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], weather_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = weather_instance.get_page_ad_google_query_id();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the View Less details link of Hourly Forecast section");
		weather_instance.click_view_more_details();
		table_status = weather_instance.get_hourly_forecast_expand_status();
		assertEquals(table_status, "expanded");
		assertNotEquals(page_ad_google_ids[0], weather_instance.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], weather_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], weather_instance.get_right_rail_ad_2_google_query_id());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04232")
	@testCaseName(test_case_name = "Weather Page [D/T/M]")
	@Test(priority = 3, description = "Verify the Daily Forecast section details with Ad refresh", groups = { "DESKTOP",
			"TABLET" }, testName = "Weather Page [D/T/M]")
	public void verify_daily_forecast_details() throws Exception {
		test_Method_details(3, "Verify the Daily Forecast section details with Ad refresh");
		// Step 1
		test_step_details(1, "Verify the presence of Daily Forecast section");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		homepage_instance.close_openx_banner();
		weather_instance.click_evergage_top_banner();
		scrollToTopOfPage();
		weather_instance.click_weather_menu();
		assertTrue(weather_instance.verify_daily_forecast_section());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the 5 Day Forecast link of Daily Forecast section");
		String page_ad_google_ids[] = weather_instance.get_page_ad_google_query_id();
		weather_instance.click_5_day_forecast();
		sleepFor(5);
		assertTrue(weather_instance.verify_5_day_forecast_section());
		assertNotEquals(page_ad_google_ids[0], weather_instance.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], weather_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], weather_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = weather_instance.get_page_ad_google_query_id();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the 7 Day Forecast link of Daily Forecast section");
		weather_instance.click_7_day_forecast();
		sleepFor(5);
		assertTrue(weather_instance.verify_7_day_forecast_section());
		assertNotEquals(page_ad_google_ids[0], weather_instance.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], weather_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], weather_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = weather_instance.get_page_ad_google_query_id();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the 3 Day Forecast link of Daily Forecast section");
		weather_instance.click_3_day_forecast();
		sleepFor(5);
		assertTrue(weather_instance.verify_3_day_forecast_section());
		assertNotEquals(page_ad_google_ids[0], weather_instance.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], weather_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], weather_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = weather_instance.get_page_ad_google_query_id();
		step_validator(4, true);
	}

	@testId(test_id = "RT-04232")
	@testCaseName(test_case_name = "Weather Page [D/T/M]")
	@Test(priority = 4, description = "Verify the Weather Climate history", groups = { "DESKTOP",
			"TABLET" }, testName = "Weather Page [D/T/M]")
	public void weather_climate_history() throws Exception {
		test_Method_details(4, "Verify the Weather Climate history");
		// Step 1
		test_step_details(1, "Verify the presence of Weather Climate History section");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		homepage_instance.close_openx_banner();
		weather_instance.click_evergage_top_banner();
		scrollToTopOfPage();
		weather_instance.click_weather_menu();
		assertTrue(weather_instance.verify_climate_history_section());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the default selected month");
		// String expected_default_month = getMonthWithOffset(-1, "MMMM");
		String expected_default_month = "April";
		assertEqualsIgnoreCase(weather_instance.get_selected_month_of_climate_history(), expected_default_month);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Modify month to verify the Ad refresh");
		String page_ad_google_ids[] = weather_instance.get_page_ad_google_query_id();
		weather_instance.select_climate_history_month(getMonthWithOffset(-2, "MMMM"));
		expected_default_month = getMonthWithOffset(-2, "MMMM");
		assertEqualsIgnoreCase(weather_instance.get_selected_month_of_climate_history(), expected_default_month);
		assertNotEquals(page_ad_google_ids[0], weather_instance.get_inline_ad_google_query_id());
		assertNotEquals(page_ad_google_ids[1], weather_instance.get_right_rail_ad_1_google_query_id());
		assertNotEquals(page_ad_google_ids[2], weather_instance.get_right_rail_ad_2_google_query_id());
		page_ad_google_ids = weather_instance.get_page_ad_google_query_id();
		step_validator(3, true);
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 5, description = "Verify display of Weather page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_weather_page() throws Exception {
		test_Method_details(5, "Verify display of Weather page Ad's");
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_width = "300";
		String right_rail_ad_2_height = "250";
		String inline_ad_width = "728";
		String inline_ad_height = "90";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		weather_instance.click_evergage_top_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the display of Inline Ad's and the Size");
		homepage_instance.click_weather_menu();
		sleepFor(5);
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
	}
}
