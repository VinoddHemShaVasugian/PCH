package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;
import com.util.DriverManager;

public class WeatherPage extends BaseClass {

	private static final WeatherPage weather_instance = new WeatherPage();
	private final HomePage homepage_instance = HomePage.getInstance();

	private WeatherPage() {
	}

	public static WeatherPage getInstance() {
		return weather_instance;
	}

	private final By weather_menu = By.xpath("//div[@id='header']//a//img[contains(@src,'weather')]");
	private final By weather_menu1 = By.xpath("//div[@id='header']//li[@class='menu__item  menu__item--weather']");
	private final By evergage_top = By.id("evergage-tooltip-ambyI3Dw");
	private final By weather_hover_edit_icon = By.cssSelector("div#header div.weather-dropdown__edit a");
//	private final By weather_hover_full_forecast_icon = By
//			.xpath("//div[@class='container']//nav[@id='menu']//a[text()='full forecast']");
//	private final By weather_hover_full_forecast_icon = By
//			.xpath("//div[@class='container']//a[text()='full forecast']");
	private final By weather_hover_full_forecast_icon = By.cssSelector("div#header article.weather-dropdown__link");
	private final By weather_hover_temp_date_time = By.cssSelector("div#header span.weather-data__time");
	private final By weather_hover_temp_image = By.cssSelector("div#header span.weather-data__image img");
	private final By weather_hover_temp_value = By.cssSelector("div#header span.weather-data__temp");
	private final By weather_hover_temp_humidity = By
			.xpath("//div[@id='header']//div[text()='Humidity']/ancestor::span/div[2]");
	private final By weather_hover_temp_dewpoint = By
			.xpath("//div[@id='header']//div[text()='Dew Point']/ancestor::span/div[2]");
	private final By weather_hover_temp_winds = By
			.xpath("//div[@id='header']//div[text()='Winds']/ancestor::span/div[2]");
//	private final By weather_hover_city_name = By.cssSelector("div#header nav#menu div.weather-dropdown__citystate");
	private final By weather_hover_city_name = By.xpath("//div[@class='container']//div[@class='weather-dropdown__citystate']");
	private final By city_name = By.cssSelector("div.weather-locationAndZip__citystate");
	private final By edit_zipcode = By.name("zipcode");
	private final By city_temperature = By.cssSelector("div.weather-temperature.weather-temperature--main");
	private final By city_temp_image_src = By.cssSelector("div.weather-details--main > img");
	private final By city_temp_humidity = By.xpath("//div[contains(text(),'humidity')]/following-sibling::div");
	private final By city_temp_dew_points = By.xpath("//div[contains(text(),'dew point')]/following-sibling::div[2]");
	private final By city_temp_wind = By.xpath("//div[contains(text(),'wind')]/following-sibling::div[2]");
	private final By hourly_forecast_section = By.cssSelector("section.weather-hourly");
	private final By daily_forecast_section = By.cssSelector("section.weather-daily");
	private final By climate_history_section = By.cssSelector("section.weather-history");
	private final By weather_ad_section = By.cssSelector("section.weather-ad");
	private final By weather_video_section = By.xpath("//h3[contains(text(),'Videos')]");
	private final By weather_image_header = By.xpath("//div[@id='header']//a[@href='/weather']/img");
	private final By weather_image_footer = By.xpath("//footer[@class='footer']//a[@href='/weather']/img");
	private final By zip_go_button = By.cssSelector("input.weather-locationAndZip__zip_submit");
	private final By view_more_details_link = By.xpath("//div[contains(text(),'view more details')]");
	private final By view_less_details_link = By.xpath("//div[contains(text(),'view less details')]");
	private final By hourly_forecast_expand_attribute = By.cssSelector("table[summary='Hourly weather forecast.']");
	private final By weather_inline_ad = By.cssSelector("div#div-gpt-ad-bottom");
	private final By weather_right_rail_ad1 = By.cssSelector("div#div-gpt-ad-multiple");
	private final By weather_right_rail_ad2 = By.cssSelector("div#div-gpt-ad-box");
	private final By enbled_three_day_forecast_link = By.cssSelector("div.three_day_sel");
	private final By enbled_five_day_forecast_link = By.cssSelector("div.five_day_sel");
	private final By enbled_seven_day_forecast_link = By.cssSelector("div.seven_day_sel");
	private final By disbled_three_day_forecast_link = By.xpath("//div[contains(text(),'3-day forecast')]");
	private final By disbled_five_day_forecast_link = By.xpath("//div[contains(text(),'5-day forecast')]");
	private final By disbled_seven_day_forecast_link = By.xpath("//div[contains(text(),'7-day forecast')]");
	private final By weather_climate_month_dropdown = By.cssSelector("select.history_month_select");

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
	 * Cliam tokens and verify vip level up message
	 * @return true
	 */
	public boolean validating_vip_level_up(String firstName) throws Exception {
		homepage_instance.click_weather_menu();
		homepage_instance.click_claim_button();
		assertTrue(homepage_instance.verify_claimed_button());
		try {
			String vip_c1_msg = "Hi, "+firstName+""+msg_property_file_reader("vip_c1_msg_header")+"\n"+msg_property_file_reader("vip_c1_msg_body");
			System.out.println("vip_c1_msg: "+vip_c1_msg);
			homepage_instance.clic_new_VIP_logo(5);
			String afterActivity= homepage_instance.get_vip_msg();
			assertNotEquals(afterActivity, vip_c1_msg);
		return true;
			}
			catch(Exception e) {
				return false;
			}
	}
	
	/**
	 * close evergage banner, displayed on page header
	 */
	public void click_evergage_top_banner() {
		button(evergage_top, 2);
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
	 * Click on 3 Day Forecast section
	 */
	public void click_3_day_forecast() {
		button(disbled_three_day_forecast_link, 5);
		waitForElement(enbled_three_day_forecast_link, 5);
		sleepFor(3);
	}

	/**
	 * Click on 5 Day Forecast section
	 */
	public void click_5_day_forecast() {
		button(disbled_five_day_forecast_link, 5);
		waitForElement(enbled_five_day_forecast_link, 5);
	}

	/**
	 * Click on 7 Day Forecast section
	 */
	public void click_7_day_forecast() {
		button(disbled_seven_day_forecast_link, 5);
		waitForElement(enbled_seven_day_forecast_link, 5);
	}

	/**
	 * @return True if the 3 Days section details alone are displayed
	 */
	public boolean verify_3_day_forecast_section() {
		return elementPresent(enbled_three_day_forecast_link);
	}

	/**
	 * @return True if the 5 Days section details alone are displayed
	 */
	public boolean verify_5_day_forecast_section() {
		return elementPresent(enbled_five_day_forecast_link);
	}

	/**
	 * @return True if the 7 Days section details alone are displayed
	 */
	public boolean verify_7_day_forecast_section() {
		return elementPresent(enbled_seven_day_forecast_link);
	}

	/**
	 * Returns the Google unique id for the Inline Ad
	 * 
	 * @return
	 */
	public String get_inline_ad_google_query_id() {
//		sleepFor(5);
		return getAttribute(weather_inline_ad, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad One
	 * 
	 * @return
	 */
	public String get_right_rail_ad_1_google_query_id() {
		return getAttribute(weather_right_rail_ad1, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad Two
	 * 
	 * @return
	 */
	public String get_right_rail_ad_2_google_query_id() {
		return getAttribute(weather_right_rail_ad2, "data-google-query-id");
	}

	/**
	 * Return the total page Ad google id's
	 * 
	 * @return
	 */
	public String[] get_page_ad_google_query_id() {
		String ad_google_id[] = new String[3];
		ad_google_id[0] = get_inline_ad_google_query_id();
		ad_google_id[1] = get_right_rail_ad_1_google_query_id();
		ad_google_id[2] = get_right_rail_ad_2_google_query_id();
		return ad_google_id;
	}

	/**
	 * Click on View More details link on Hourly forecast section
	 */
	public void click_view_more_details() {
		button(view_more_details_link, 5);
		waitForElement(view_less_details_link, 5);
		sleepFor(1);
	}

	/**
	 * Click on View Less details link on Hourly forecast section
	 */
	public void click_view_less_details() {
		button(view_less_details_link, 5);
		waitForElement(view_more_details_link, 5);
		sleepFor(1);
	}

	/**
	 * Returns the Hourly forecast table status. Return as expanded for more details
	 * and empty for less details
	 * 
	 * @return
	 */
	public String get_hourly_forecast_expand_status() {
		return getAttribute(hourly_forecast_expand_attribute, "class");
	}

	/**
	 * Retrieve the Header Weather image source
	 * 
	 * @return
	 */
	public String get_header_image_src() {
		return getAttribute(weather_image_header, "src");
	}

	/**
	 * Retrieve the Footer Weather image source
	 * 
	 * @return
	 */
	public String get_footerr_image_src() {
		return getAttribute(weather_image_footer, "src");
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
	 * Display status of Weather video section
	 * 
	 * @return
	 */
	public boolean verify_weather_video_section() {
		return elementPresent(weather_video_section);
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
		return elementPresent(weather_ad_section);
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
	 * Hover the Weather Menu
	 */
	public void hover_weather_menu() {
		waitForElement(weather_menu);
		WebElement elm=DriverManager.getDriver().findElement(weather_menu1);
		mouseHover(weather_menu);
//		mouseHover(elm);
		waitForElement(weather_hover_edit_icon, 10);
	}

	/**
	 * Go to Weather Page
	 */
	public void click_weather_menu() {
		button(weather_menu, 5);
		waitForElement(zip_go_button);
	}

	/**
	 * Click on Edit icon on the Weather Hover image
	 */
	public void click_edit_zipcode_on_weather_hover() {
		hover_weather_menu();
		waitForElement(weather_hover_edit_icon);
		button(weather_hover_edit_icon, 5);
		waitForElement(edit_zipcode, 10);
	}

	/**
	 * Click on Full Forecast icon on the Weather Hover image
	 */
	public void click_full_forecast_weather__hover() {
//		hover_weather_menu();
		waitForElement(weather_hover_full_forecast_icon);
		button(weather_hover_full_forecast_icon, 5);
//		button(weather_menu, 5);
		waitForElement(edit_zipcode, 10);
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
	 * 
	 * @return the PlaceHolder text of the ZipCode field
	 */
	public String get_zip_code_field_placeholder_text() {
		return getAttribute(edit_zipcode, "placeholder");
	}

	/**
	 * Verify the Edit icon on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_edit_icon_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_edit_icon);
	}

	/**
	 * Verify the Full Forecast link on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_full_forecast_icon_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_full_forecast_icon);
	}

	/**
	 * Verify the City Name on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_city_name_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_city_name);
	}

	/**
	 * Verify the Temperature time on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_temp_date_time_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_temp_date_time);
	}

	/**
	 * Verify the Temperature Dew point value on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_temp_dew_point_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_temp_dewpoint);
	}

	/**
	 * Verify the Temperature humidity value on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_temp_humidity_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_temp_humidity);
	}

	/**
	 * Verify the Temperature image on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_temp_image_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_temp_image);
	}

	/**
	 * Verify the Temperature wind value on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_temp_winds_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_temp_winds);
	}

	/**
	 * Verify the Temperature on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verify_temp_on_hover_screen() {
		hover_weather_menu();
		return elementPresent(weather_hover_temp_value);
	}

	/**
	 * Retrieve the City name from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_city_from_hover_screen() {
		hover_weather_menu();
//		moveToElement(hover_weather_menu);
//		moveToElement(weather_hover_city_name);
		return getText(weather_hover_city_name, 3).replace("br", "").split(" ")[0].trim().toLowerCase();
	}

	/**
	 * Retrieve the Humidity from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_humidity_from_hover_screen() {
		hover_weather_menu();
		moveToElement(weather_hover_temp_humidity);
		return getText(weather_hover_temp_humidity, 3);
	}

	/**
	 * Retrieve the Temperature time from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_temp_time_from_hover_screen() {
		hover_weather_menu();
		return getText(weather_hover_temp_date_time, 3).replace("br", "").trim();
		// String temp_time_split[] = getText(weather_hover_temp_date_time,
		// 3).replace("br", "").trim().split(" ");
		// return temp_time_split[0] + " " + temp_time_split[1];
	}

	/**
	 * Retrieve the Temperature Dew Point from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_temp_dew_point_from_hover_screen() {
		hover_weather_menu();
		moveToElement(weather_hover_temp_dewpoint);
		return getText(weather_hover_temp_dewpoint, 3).split(" ")[0];
	}

	/**
	 * Retrieve the Temperature image source link from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_temp_image_from_hover_screen() {
		hover_weather_menu();
		String url_split[] = getAttribute(weather_hover_temp_image, "src").split("/");
		return url_split[url_split.length - 1];
	}

	/**
	 * Retrieve the Temperature from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_temp_from_hover_screen() {
		hover_weather_menu();
		return getText(weather_hover_temp_value, 0);
	}

	/**
	 * Retrieve the Temperature Winds value from Weather Hover screen
	 * 
	 * @return
	 */
	public String get_temp_winds_from_hover_screen() {
		hover_weather_menu();
		return getText(weather_hover_temp_winds, 1);
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
	 * Retrieve the City temp from Weather page
	 * 
	 * @return
	 */
	public String get_city_temp() {
		return getText(city_temperature, 3);
	}

	/**
	 * Retrieve the City humidity from Weather page
	 * 
	 * @return
	 */
	public String get_city_humidity() {
		return getText(city_temp_humidity, 3);
	}

	/**
	 * Retrieve the City Dew Points from Weather page
	 * 
	 * @return
	 */
	public String get_city_dew_points() {
		return getText(city_temp_dew_points, 3);
	}

	/**
	 * Retrieve the City Wind details from Weather page
	 * 
	 * @return
	 */
	public String get_city_wind_details() {
		return getText(city_temp_wind, 3).split(" ")[0];
	}

	/**
	 * Retrieve the City Temperature image source url details from Weather page
	 * 
	 * @return
	 */
	public String get_temp_image_src_details() {
		String url_split[] = getAttribute(city_temp_image_src, "src").split("/");
		return url_split[url_split.length - 1];
	}
}
