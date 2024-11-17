package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class WeatherPage extends BaseClass {

	private static final WeatherPage weather_instance = new WeatherPage();

	private WeatherPage() {
	}

	public static WeatherPage getInstance() {
		return weather_instance;
	}

	private final By city_name = By.cssSelector("div.weather-locationAndZip__citystate");
	private final By edit_zipcode = By.name("zipcode");
	private final By zip_go_button = By.cssSelector("input.weather-locationAndZip__zip_submit");
	private final By weather_menu = By.cssSelector("div#header-menu li>a>img");
	private final By hourly_forecast_section = By.cssSelector("section.weather-hourly");
	private final By daily_forecast_section = By.xpath("//h3[text()='daily forecast']");
	private final By climate_history_section = By.cssSelector("section.weather-history");
	private final By weather_hover_edit_icon = By.cssSelector("div#header-menu div.weather_edit>a");
	private final By weather_video_section = By.xpath("//h3[contains(text(),'Weather Videos')]");
	private final By weather_climate_month_dropdown = By.cssSelector("select.history_month_select");
	private final By weather_native_ad = By.id("div-gpt-300x250-ad-box");
	private final By weather_zip_field_placeholder_msg = By.name("zipcode");
	private final By expand_current_weather_stats = By.cssSelector("section[class$='weather-current']>button");
	private final By collapse_current_weather_stats = By
			.cssSelector("section[class$='weather-current weather-current--expanded']>button");
	private final By current_weather_stats = By.cssSelector("section.weather-current__stats");
	private final By current_weather_sunrise_stats = By.cssSelector("section.weather-current__sunriseset.hover");
	private final By expand_hourly_forcase_details = By
			.xpath("//h3[text()='hourly forecast']//div[text()='view more details']");
	private final By collapse_hourly_forcase_details = By
			.xpath("//h3[text()='hourly forecast']//div[text()='view less details']");
	private final By hourly_forecast_details = By.cssSelector("table.expanded[summary='Hourly weather forecast.']");
	private final By expand_daily_forcase_details = By
			.xpath("//h3[text()='daily forecast']//div[text()='view more details']");
	private final By collapse_daily_forcase_details = By
			.xpath("//h3[text()='daily forecast']//div[text()='view less details']");
	private final By daily_forecast_details = By
			.xpath("//h3[text()='daily forecast']/following-sibling::div/table[@class='expanded']");

	/**
	 * Expand the Hourly Forecast weather details
	 */
	public void expand_hourly_forecast_details() {
		button(expand_hourly_forcase_details, 60);
	}

	/**
	 * Collapse the Hourly Forecast weather details
	 */
	public void collapse_hourly_forecast_details() {
		button(collapse_hourly_forcase_details, 60);
	}

	/**
	 * 
	 * @return the visibility status of the detailed hourly forecast section
	 */
	public boolean verify_detailed_hourly_forecast_section() {
		expand_hourly_forecast_details();
		return elementVisibility(hourly_forecast_details);
	}

	/**
	 * Expand the Daily Forecast weather details
	 */
	public void expand_daily_forecast_details() {
		button(expand_daily_forcase_details, 60);
	}

	/**
	 * Collapse the Daily Forecast weather details
	 */
	public void collapse_daily_forecast_details() {
		button(collapse_daily_forcase_details, 60);
	}

	/**
	 * 
	 * @return the visibility status of the detailed daily forecast section
	 */
	public boolean verify_detailed_daily_forecast_section() {
		expand_daily_forecast_details();
		return elementVisibility(daily_forecast_details);
	}

	/**
	 * 
	 * @return the visibility status of the current weather stats.
	 */
	public boolean verify_current_weather_stats() {
		expand_current_weather_stats();
		return elementVisibility(current_weather_stats);
	}

	/**
	 * 
	 * @return the visibility status of the current weather sunrise stats.
	 */
	public boolean verify_current_weather_sunrise_stats() {
		expand_current_weather_stats();
		return elementVisibility(current_weather_sunrise_stats);
	}

	/**
	 * Expand the current Weather Stats. section
	 */
	public void expand_current_weather_stats() {
		button(expand_current_weather_stats, 60);
	}

	/**
	 * Collapse the current Weather Stats. section
	 */
	public void collapse_current_weather_stats() {
		button(collapse_current_weather_stats, 60);
	}

	/**
	 * Returns the Zip field place holder message
	 * 
	 * @return
	 */
	public String get_zip_field_placeholder_msg() {
		return getAttribute(weather_zip_field_placeholder_msg, "placeholder");
	}

	/**
	 * Returns the Google unique id for the Native Ad
	 * 
	 * @return
	 */
	public String get_native_ad_google_query_id() {
		return getAttribute(weather_native_ad, "data-google-query-id");
	}

	/**
	 * Select the month on the Weather Climate Hisotry section
	 * 
	 * @param month
	 */
	public void select_climate_history_month(String month) {
		selectByVisibleText(weather_climate_month_dropdown, month, 5);
		sleepFor(2);
	}

	/**
	 * Return the selected option of the Weather Climate History drop down
	 * 
	 * @return
	 */
	public String get_selected_month_of_climate_history() {
		return get_first_selected_option(weather_climate_month_dropdown, 10);
	}

	/**
	 * Display status of Hourly forecast section
	 * 
	 * @return
	 */
	public boolean verify_hourly_forecast_section() {
		return elementPresent(hourly_forecast_section);
	}

	/**
	 * Display status of Daily forecast section
	 * 
	 * @return
	 */
	public boolean verify_daily_forecast_section() {
		return elementPresent(daily_forecast_section);
	}

	/**
	 * Display status of Weather Ad section
	 * 
	 * @return
	 */
	public boolean verify_weather_ad_section() {
		return elementPresent(weather_native_ad);
	}

	/**
	 * Display status of Climate History section
	 * 
	 * @return
	 */
	public boolean verify_climate_history_section() {
		return elementPresent(climate_history_section);
	}

	/**
	 * Go to Weather Page
	 */
	public void click_weather_menu() {
		button(weather_menu, 5);
		waitForElement(zip_go_button);
	}

	/**
	 * Entering the Zip to get Weather details
	 * 
	 * @param city_zip
	 */
	public void enter_zip_code(String city_zip) {
		textbox(edit_zipcode, "Enter", city_zip, 5);
		waitForElement(city_name, 5);
	}

	/**
	 * Click on Zipcode submit button
	 */
	public void click_zipcode_go_button() {
		button(zip_go_button, 5);
		waitForElement(city_name, 5);
	}

	/**
	 * Retrieve the City name from Weather page
	 * 
	 * @return
	 */
	public String get_city_name() {
		return getText(city_name, 3);
	}

	/**
	 * Hover the Weather Menu
	 */
	public void hover_weather_menu() {
		waitForElement(weather_menu);
		mouseHover(weather_menu);
		waitForElement(weather_hover_edit_icon, 10);
	}

	/**
	 * Display status of Weather video section
	 * 
	 * @return
	 */
	public boolean verify_weather_video_section() {
		return elementPresent(weather_video_section);
	}
}
