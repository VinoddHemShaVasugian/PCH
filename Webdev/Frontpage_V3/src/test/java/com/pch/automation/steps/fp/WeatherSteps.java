package com.pch.automation.steps.fp;

import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.pages.fp.WeatherPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class WeatherSteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	WeatherPage weatherPage;
	HomePage homepage;

	@Step
	public void hoverWeatherMenu() {
		weatherPage.hoverWeatherMenu();
	}

	@Step
	public boolean claimTokens() {
		homepage.clickClaimButton();
		homepage.clickWeatherMenu();
		if (homepage.verifyClaimedButton()) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public String getCityName() {
		return weatherPage.getCityName();
	}

	@Step
	public String getCityNameFromWeatherHoverScreen() {
		weatherPage.hoverWeatherMenu();
		return weatherPage.getCityFromHoverScreen();
	}

	@Step
	public boolean verifyEditIconOnHoverScreen() {
		return weatherPage.verifyEditIconOnHoverScreen();
	}

	@Step
	public boolean verifyCityNameOnHoverScreen() {
		return weatherPage.verifyCityNameOnHoverScreen();
	}

	@Step
	public boolean verifyFullForecastIconOnHoverScreen() {
		return weatherPage.verifyFullForecastIconOnHoverScreen();
	}

	@Step
	public boolean verifyTempDewPointOnHoverScreen() {
		return weatherPage.verifyTempDewPointOnHoverScreen();
	}

	@Step
	public boolean verifyTempDateTimeOnHoverScreen() {
		return weatherPage.verifyTempDateTimeOnHoverScreen();
	}

	@Step
	public boolean verifyTempImageOnHoverScreen() {
		return weatherPage.verifyTempImageOnHoverScreen();
	}

	@Step
	public boolean verifyTempOnHoverScreen() {
		return weatherPage.verifyTempOnHoverScreen();
	}

	@Step
	public boolean verifyTempHumidityOnHoverScreen() {
		return weatherPage.verifyTempHumidityOnHoverScreen();
	}

	@Step
	public boolean verifyTempWindsOnHoverScreen() {
		return weatherPage.verifyTempWindsOnHoverScreen();
	}

	@Step
	public void clickWeatherMenu() {
		homepage.clickWeatherMenu();
	}

	@Step
	public void clickEditZipCode() {
		weatherPage.hoverWeatherMenu();
		weatherPage.clickEditZipcodeOnWeatherHover();
	}

	@Step
	public boolean verifyPageTitle() {
		if (getDriver().getTitle().trim().equalsIgnoreCase("Weather")) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public void clickFullForecastLink() {
		weatherPage.hoverWeatherMenu();
		weatherPage.clickFullForecastWeatherHover();
	}

	@Step
	public String getHumidityFromWeatherHoverScreen() {
		return weatherPage.getHumidityFromHoverScreen();
	}

	@Step
	public String getHumidity() {
		return weatherPage.getCityHumidity();
	}

	@Step
	public String getDewFromWeatherHoverScreen() {
		return weatherPage.getTempDewPointFroHoverScreen();
	}

	@Step
	public String getDewValue() {
		return weatherPage.getCityDewPoints();
	}

	@Step
	public String getTemperatureFromWeatherHoverScreen() {
		return weatherPage.getTempFromHoverScreen();
	}

	@Step
	public String getTemperature() {
		return weatherPage.getCityTemp();
	}

	@Step
	public String getTempImageFromWeatherHoverScreen() {
		return weatherPage.getTempImageFromHoverScreen();
	}

	@Step
	public String getTempImage() {
		return weatherPage.getTempImageSrcDetails();
	}

	@Step
	public String getTempWindsFromWeatherHoverScreen() {
		return weatherPage.getTempWindsFromHoverScreen();
	}

	@Step
	public String getTempWinds() {
		return weatherPage.getCityWindDetails();
	}

	@Step
	public boolean verifyHourlyForecastSection() {
		return weatherPage.verifyHourlyForecastSection();
	}

	@Step
	public boolean verifyDailyForecastSection() {
		return weatherPage.verifyDailyForecastSection();
	}

	@Step
	public boolean verifyClimateHistorySection() {
		return weatherPage.verifyClimateHistorySection();
	}

	@Step
	public boolean verifyWeatherAdSection() {
		return weatherPage.verifyWeatherAdSection();
	}

	@Step
	public String getHeaderImage() {
		return weatherPage.getHeaderImageSrc();
	}

	@Step
	public String getFooterImage() {
		return weatherPage.getFooterImageSrc();
	}

	@Step
	public void changeCity(String zipCode) {
		weatherPage.enterZipCode(zipCode);
		weatherPage.clickZipcodeGoButton();
	}

	@Step
	public boolean verifyHourlyForecastExpandStatus(String status) {
		weatherPage.clickViewLessDetails();
		if (weatherPage.getHourlyForecastStatus().equalsIgnoreCase(status)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyHourlyForecastCollapsedStatus(String status) {
		weatherPage.clickViewMoreDetails();
		if (weatherPage.getHourlyForecastStatus().equalsIgnoreCase(status)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public String[] getPageAdGoogleQueryId() {
		return weatherPage.getPageAdGoogleQueryDd();
	}

	@Step
	public String getInlineAdGoogleQueryId() {
		return weatherPage.getInlineAdGoogleQueryId();
	}

	@Step
	public String getRightRailAdOneGoogleQueryId() {
		return weatherPage.getRightRailAd1GoogleQueryId();
	}

	@Step
	public String getRightRailAdTwoGoogleQueryId() {
		return weatherPage.getRightRailAd2GoogleQueryId();
	}

	@Step
	public void clickFiveDayForecastSection() {
		weatherPage.click5DayForecast();
	}

	@Step
	public boolean verifyFiveDayForecastSection() {
		return weatherPage.verify5DayForecastSection();
	}

	@Step
	public void clickThreeDayForecastSection() {
		weatherPage.click3DayForecast();
	}

	@Step
	public boolean verifyThreeDayForecastSection() {
		return weatherPage.verify3DayForecastSection();
	}

	@Step
	public void clickSevenDayForecastSection() {
		weatherPage.click7DayForecast();
	}

	@Step
	public boolean verifySevenDayForecastSection() {
		return weatherPage.verify7DayForecastSection();
	}

	@Step
	public String getSelectedMonthClimateHistory() {
		return weatherPage.getSelectedMonthOfClimateHistory();
	}

	@Step
	public String changeClimateHistoryMonth() {
		String monthToSelect = "December";
		weatherPage.selectClimateHistoryMonth(monthToSelect);
		return monthToSelect;
	}
}