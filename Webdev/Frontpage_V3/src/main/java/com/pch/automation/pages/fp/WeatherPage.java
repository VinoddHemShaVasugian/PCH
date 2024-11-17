package com.pch.automation.pages.fp;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.interactions.Actions;

import net.serenitybdd.core.pages.PageObject;

public class WeatherPage extends PageObject {
	HomePage homepage = new HomePage();
	private final By weatherMenu = By.cssSelector("div#header li.menu__item--weather a");
	private final By hoverWeatherMenu = By.cssSelector("div#header li.menu__item--weather");
	private final By weatherHoverEditIcon = By.cssSelector("div#header div.weather-dropdown__edit a");
	private final By weatherHoverFullForecastIcon = By.cssSelector("div#header article.weather-dropdown__link");
	private final By weatherHoverTempDateTime = By.cssSelector("div#header span.weather-data__time");
	private final By weatherHoverTempImage = By.cssSelector("div#header span.weather-data__image img");
	private final By weatherHoverTempValue = By.cssSelector("div#header span.weather-data__temp");
	private final By weatherHoverTempHumidity = By
			.xpath("//div[@id='header']//div[text()='Humidity']/ancestor::span/div[2]");
	private final By weatherHoverTempDewpoint = By
			.xpath("//div[@id='header']//div[text()='Dew Point']/ancestor::span/div[2]");
	private final By weatherHoverTempWinds = By.xpath("//div[@id='header']//div[text()='Winds']/ancestor::span/div[2]");
	private final By weatherHoverCityName = By.cssSelector("div#header div.weather-dropdown__citystate");
	private final By cityName = By.cssSelector("div.weather-locationAndZip__citystate");
	private final By editZipcode = By.name("zipcode");
	private final By cityTemperature = By.cssSelector("div.weather-temperature.weather-temperature--main");
	private final By cityTempImageSrc = By.cssSelector("div.weather-details--main > img");
	private final By cityTempHumidity = By.xpath("//div[contains(text(),'humidity')]/following-sibling::div");
	private final By cityTempDewPoints = By.xpath("//div[contains(text(),'dew point')]/following-sibling::div[2]");
	private final By cityTempWind = By.xpath("//div[contains(text(),'wind')]/following-sibling::div[2]");
	private final By hourlyForecastSection = By.cssSelector("section.weather-hourly");
	private final By dailyForecastSection = By.cssSelector("section.weather-daily");
	private final By climateHistorySection = By.cssSelector("section.weather-history");
	private final By weatherAdSection = By.cssSelector("section.weather-ad");
	private final By weatherVideoSection = By.xpath("//h3[contains(text(),'Videos')]");
	private final By weatherImageHeader = By.xpath("//div[@id='header']//a[@href='/weather']/img");
	private final By weatherImageFooter = By.xpath("//footer[@class='footer']//a[@href='/weather']/img");
	private final By zipGoButton = By.cssSelector("input.weather-locationAndZip__zip_submit");
	private final By viewMoreDetailsLink = By.xpath("//div[contains(text(),'view more details')]");
	private final By viewLessDetailsLink = By.xpath("//div[contains(text(),'view less details')]");
	private final By hourlyForecastExpandAttribute = By.cssSelector("table[summary='Hourly weather forecast.']");
	private final By weatherInlineAd = By.cssSelector("div#div-gpt-ad-bottom");
	private final By weatherRightRailAd1 = By.cssSelector("div#div-gpt-ad-multiple");
	private final By weatherRightRailAd2 = By.cssSelector("div#div-gpt-ad-box");
	private final By enbledThreeDayForecastLink = By.cssSelector("div.three_day_sel");
	private final By enbledFiveDayForecastLink = By.cssSelector("div.five_day_sel");
	private final By enbledSevenDayForecastLink = By.cssSelector("div.seven_day_sel");
	private final By disbledThreeDayForecastLink = By.xpath("//div[contains(text(),'3-day forecast')]");
	private final By disbledFiveDayForecastLink = By.xpath("//div[contains(text(),'5-day forecast')]");
	private final By disbledSevenDayForecastLink = By.xpath("//div[contains(text(),'7-day forecast')]");
	private final By weatherClimateMonthDropdown = By.cssSelector("select.history_month_select");
	private final By menuHoverSection = By.cssSelector("div#header section.weather-dropdown");

	/**
	 * Select the month on the Weather Climate Hisotry section
	 * 
	 * @param month
	 */
	public void selectClimateHistoryMonth(String month) {
		try {
			selectFromDropdown(element(weatherClimateMonthDropdown), month);
		} catch (StaleElementReferenceException stale) {
			waitForRenderedElementsToBePresent(weatherClimateMonthDropdown);
			selectFromDropdown(element(weatherClimateMonthDropdown), month);
		}
		waitFor(5);
	}

	/**
	 * Return the selected option of the Weather Climate History drop down
	 * 
	 * @return
	 */
	public String getSelectedMonthOfClimateHistory() {
		return getSelectedValueFrom(element(weatherClimateMonthDropdown));
	}

	/**
	 * Click on 3 Day Forecast section
	 */
	public void click3DayForecast() {
		waitForRenderedElementsToBePresent(disbledThreeDayForecastLink);
		try {
			clickOn(element(disbledThreeDayForecastLink));
		} catch (Exception e) {
			homepage.jsClick(disbledThreeDayForecastLink);
		}
		waitForRenderedElementsToBePresent(enbledThreeDayForecastLink);
	}

	/**
	 * Click on 5 Day Forecast section
	 */
	public void click5DayForecast() {
		waitForRenderedElementsToBePresent(disbledFiveDayForecastLink);
		try {
			clickOn(element(disbledFiveDayForecastLink));
		} catch (Exception e) {
			homepage.jsClick(disbledFiveDayForecastLink);
		}
		waitForRenderedElementsToBePresent(enbledFiveDayForecastLink);
	}

	/**
	 * Click on 7 Day Forecast section
	 */
	public void click7DayForecast() {
		waitForRenderedElementsToBePresent(disbledSevenDayForecastLink);
		try {
			clickOn(element(disbledSevenDayForecastLink));
		} catch (Exception e) {
			homepage.jsClick(disbledSevenDayForecastLink);
		}
		waitForRenderedElementsToBePresent(enbledSevenDayForecastLink);
	}

	/**
	 * @return True if the 3 Days section details alone are displayed
	 */
	public boolean verify3DayForecastSection() {
		return isElementVisible(enbledThreeDayForecastLink);
	}

	/**
	 * @return True if the 5 Days section details alone are displayed
	 */
	public boolean verify5DayForecastSection() {
		return isElementVisible(enbledFiveDayForecastLink);
	}

	/**
	 * @return True if the 7 Days section details alone are displayed
	 */
	public boolean verify7DayForecastSection() {
		return isElementVisible(enbledSevenDayForecastLink);
	}

	/**
	 * Returns the Google unique id for the Inline Ad
	 * 
	 * @return
	 */
	public String getInlineAdGoogleQueryId() {
		waitForRenderedElementsToBePresent(weatherInlineAd);
		return element(weatherInlineAd).getAttribute("data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad One
	 * 
	 * @return
	 */
	public String getRightRailAd1GoogleQueryId() {
		waitForRenderedElementsToBePresent(weatherRightRailAd1);
		return element(weatherRightRailAd1).getAttribute("data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad Two
	 * 
	 * @return
	 */
	public String getRightRailAd2GoogleQueryId() {
		waitForRenderedElementsToBePresent(weatherRightRailAd2);
		return element(weatherRightRailAd2).getAttribute("data-google-query-id");
	}

	/**
	 * Return the total page Ad google id's
	 * 
	 * @return
	 */
	public String[] getPageAdGoogleQueryDd() {
		String adGoogleId[] = new String[3];
		adGoogleId[0] = getInlineAdGoogleQueryId();
		adGoogleId[1] = getRightRailAd1GoogleQueryId();
		adGoogleId[2] = getRightRailAd2GoogleQueryId();
		getDriver().navigate().refresh();
		return adGoogleId;
	}

	/**
	 * Click on View More details link on Hourly forecast section
	 */
	public void clickViewMoreDetails() {
		waitABit(30000);
		waitForRenderedElementsToBePresent(viewMoreDetailsLink);
		try {
			clickOn(element(viewMoreDetailsLink));
		} catch (Exception e) {
			homepage.jsClick(viewMoreDetailsLink);
		}
		waitForRenderedElementsToBePresent(viewLessDetailsLink);
	}

	/**
	 * Click on View Less details link on Hourly forecast section
	 */
	public void clickViewLessDetails() {
		waitForRenderedElementsToBePresent(viewLessDetailsLink);
		try {
			clickOn(element(viewLessDetailsLink));
		} catch (Exception e) {
			homepage.jsClick(viewLessDetailsLink);
		}
		waitForRenderedElementsToBePresent(viewMoreDetailsLink);
	}

	/**
	 * Returns the Hourly forecast table status. Return as expanded for more details
	 * and empty for less details
	 * 
	 * @return
	 */
	public String getHourlyForecastStatus() {
		return element(hourlyForecastExpandAttribute).getAttribute("class");
	}

	/**
	 * Retrieve the Header Weather image source
	 * 
	 * @return
	 */
	public String getHeaderImageSrc() {
		return element(weatherImageHeader).getAttribute("src");
	}

	/**
	 * Retrieve the Footer Weather image source
	 * 
	 * @return
	 */
	public String getFooterImageSrc() {
		return element(weatherImageFooter).getAttribute("src");
	}

	/**
	 * Display status of Hourly forecast section
	 * 
	 * @return
	 */
	public boolean verifyHourlyForecastSection() {
		waitForRenderedElementsToBePresent(hourlyForecastSection);
		return isElementVisible(hourlyForecastSection);
	}

	/**
	 * Display status of Weather video section
	 * 
	 * @return
	 */
	public boolean verifyWeatherVideoSection() {
		return isElementVisible(weatherVideoSection);
	}

	/**
	 * Display status of Daily forecast section
	 * 
	 * @return
	 */
	public boolean verifyDailyForecastSection() {
		return isElementVisible(dailyForecastSection);
	}

	/**
	 * Display status of Weather Ad section
	 * 
	 * @return
	 */
	public boolean verifyWeatherAdSection() {
		return isElementVisible(weatherAdSection);
	}

	/**
	 * Display status of Climate History section
	 * 
	 * @return
	 */
	public boolean verifyClimateHistorySection() {
		return isElementVisible(climateHistorySection);
	}

	/**
	 * Hover the Weather Menu used
	 */
	public void hoverWeatherMenu() {
		mouseHover(hoverWeatherMenu, menuHoverSection);
		waitForRenderedElementsToBePresent(weatherHoverCityName);
	}

	/**
	 * Go to Weather Page
	 */
	public void clickWeatherMenu() {
		try {
			clickOn(element(weatherMenu));
		} catch (ElementClickInterceptedException eci) {
			waitForRenderedElementsToBePresent(weatherMenu);
			homepage.jsClick(weatherMenu);
		} catch (StaleElementReferenceException stale) {
			clickOn(element(weatherMenu));
		}
		waitForRenderedElementsToBePresent(climateHistorySection);
	}

	/**
	 * Click on Edit icon on the Weather Hover image
	 */
	public void clickEditZipcodeOnWeatherHover() {
		clickOn(element(weatherHoverEditIcon));
		waitForRenderedElementsToBePresent(editZipcode);
	}

	/**
	 * Click on Full Forecast icon on the Weather Hover image
	 */
	public void clickFullForecastWeatherHover() {
		hoverWeatherMenu();
		try {
			waitForRenderedElementsToBePresent(weatherHoverFullForecastIcon);
			clickOn(element(weatherHoverFullForecastIcon));
		} catch (ElementClickInterceptedException e) {
			homepage.jsClick(weatherHoverFullForecastIcon);
		}
		waitForRenderedElementsToBePresent(editZipcode);
	}

	/**
	 * Entering the Zip to get Weather details
	 * 
	 * @param city_zip
	 */
	public void enterZipCode(String cityZip) {
		typeInto(element(editZipcode), cityZip);
	}

	/**
	 * Click on Zipcode submit button
	 */
	public void clickZipcodeGoButton() {
		clickOn(element(zipGoButton));
		waitForRenderedElementsToBePresent(cityName);
	}

	/**
	 * 
	 * @return the PlaceHolder text of the ZipCode field
	 */
	public String getZipCodeFieldPlaceholderText() {
		return element(editZipcode).getAttribute("placeholder");
	}

	/**
	 * Verify the Edit icon on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyEditIconOnHoverScreen() {
		hoverWeatherMenu();
		return isElementVisible(weatherHoverEditIcon);
	}

	/**
	 * Verify the Full Forecast link on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyFullForecastIconOnHoverScreen() {
		return isElementVisible(weatherHoverFullForecastIcon);
	}

	/**
	 * Verify the City Name on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyCityNameOnHoverScreen() {
		return isElementVisible(weatherHoverCityName);
	}

	/**
	 * Verify the Temperature time on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyTempDateTimeOnHoverScreen() {
		return isElementVisible(weatherHoverTempDateTime);
	}

	/**
	 * Verify the Temperature Dew point value on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyTempDewPointOnHoverScreen() {
		return isElementVisible(weatherHoverTempDewpoint);
	}

	/**
	 * Verify the Temperature humidity value on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyTempHumidityOnHoverScreen() {
		return isElementVisible(weatherHoverTempHumidity);
	}

	/**
	 * Verify the Temperature image on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyTempImageOnHoverScreen() {
		return isElementVisible(weatherHoverTempImage);
	}

	/**
	 * Verify the Temperature wind value on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyTempWindsOnHoverScreen() {
		return isElementVisible(weatherHoverTempWinds);
	}

	/**
	 * Verify the Temperature on Weather Hover screen
	 * 
	 * @return
	 */
	public boolean verifyTempOnHoverScreen() {
		return isElementVisible(weatherHoverTempValue);
	}

	/**
	 * Retrieve the City name from Weather Hover screen
	 * 
	 * @return
	 */
	public String getCityFromHoverScreen() {
		hoverWeatherMenu();
		waitForRenderedElementsToBePresent(weatherHoverCityName);
		return element(weatherHoverCityName).getText().split("\n")[0].trim().toLowerCase();
	}

	/**
	 * Retrieve the Humidity from Weather Hover screen
	 * 
	 * @return
	 */
	public String getHumidityFromHoverScreen() {
		return element(weatherHoverTempHumidity).getText().trim().toLowerCase();
	}

	/**
	 * Retrieve the Temperature time from Weather Hover screen
	 * 
	 * @return
	 */
	public String getTempTimeFromHoverScreen() {
		return element(weatherHoverTempDateTime).getText().replace("br", "").trim().toLowerCase();
	}

	/**
	 * Retrieve the Temperature Dew Point from Weather Hover screen
	 * 
	 * @return
	 */
	public String getTempDewPointFroHoverScreen() {
		return element(weatherHoverTempDewpoint).getText().replace(" ", "").trim().toLowerCase();
	}

	/**
	 * Retrieve the Temperature image source link from Weather Hover screen
	 * 
	 * @return
	 */
	public String getTempImageFromHoverScreen() {
		String urlSplit[] = element(weatherHoverTempImage).getAttribute("src").split("/");
		return urlSplit[urlSplit.length - 1].trim().toLowerCase();
	}

	/**
	 * Retrieve the Temperature from Weather Hover screen
	 * 
	 * @return
	 */
	public String getTempFromHoverScreen() {
		String temp = element(weatherHoverTempValue).getText();
		return temp.substring(0, temp.length() - 1);
	}

	/**
	 * Retrieve the Temperature Winds value from Weather Hover screen
	 * 
	 * @return
	 */
	public String getTempWindsFromHoverScreen() {
		return element(weatherHoverTempWinds).getText().trim().toLowerCase();
	}

	/**
	 * Retrieve the City name from Weather page
	 * 
	 * @return
	 */
	public String getCityName() {
		waitForRenderedElementsToBePresent(cityName);
		return element(cityName).getText().split(",")[0].trim().toLowerCase();
	}

	/**
	 * Retrieve the City temp from Weather page
	 * 
	 * @return
	 */
	public String getCityTemp() {
		return element(cityTemperature).getText().trim().toLowerCase();
	}

	/**
	 * Retrieve the City humidity from Weather page
	 * 
	 * @return
	 */
	public String getCityHumidity() {
		return element(cityTempHumidity).getText().trim().toLowerCase();
	}

	/**
	 * Retrieve the City Dew Points from Weather page
	 * 
	 * @return
	 */
	public String getCityDewPoints() {
		return element(cityTempDewPoints).getText().trim().toLowerCase();
	}

	/**
	 * Retrieve the City Wind details from Weather page
	 * 
	 * @return
	 */
	public String getCityWindDetails() {
		return element(cityTempWind).getText().trim().toLowerCase();
	}

	/**
	 * Retrieve the City Temperature image source url details from Weather page
	 * 
	 * @return
	 */
	public String getTempImageSrcDetails() {
		String urlSplit[] = element(cityTempImageSrc).getAttribute("src").split("/");
		return urlSplit[urlSplit.length - 1].trim().toLowerCase();
	}

	/**
	 * Mouse hover to the specified element
	 * 
	 * @param locators
	 */
	public void mouseHover(By menu, By subMenu) {
		Actions builder = new Actions(getDriver());
		builder.moveToElement(element(menu)).moveToElement(element(subMenu)).build().perform();
	}
}