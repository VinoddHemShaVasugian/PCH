package com.pch.automation.stepdefinitions.fp;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.fp.WeatherSteps;

import net.thucydides.core.annotations.Steps;

public class WeatherScenarioSteps {

	@Steps
	WeatherSteps weatherSteps;
	@Steps
	HomepageSteps homeSteps;
	@Steps
	NavigationSteps navigationSteps;

	@When("Verify weather hover menu")
	public void verifyWeatherHoverMenu() {
		weatherSteps.hoverWeatherMenu();
		Assert.assertTrue("Edit icon is not displayed on hover screen.", weatherSteps.verifyEditIconOnHoverScreen());
		Assert.assertTrue("City name is not displayed on hover screen.", weatherSteps.verifyCityNameOnHoverScreen());
		Assert.assertTrue("Full forecast icon is not displayed on hover screen.",
				weatherSteps.verifyFullForecastIconOnHoverScreen());
		Assert.assertTrue("Temperature date & time is not displayed on hover screen.",
				weatherSteps.verifyTempDateTimeOnHoverScreen());
		Assert.assertTrue("Temperature dew point is not displayed on hover screen.",
				weatherSteps.verifyTempDewPointOnHoverScreen());
		Assert.assertTrue("Temperature image is not displayed on hover screen.",
				weatherSteps.verifyTempImageOnHoverScreen());
		Assert.assertTrue("Temperature humidity is not displayed on hover screen.",
				weatherSteps.verifyTempHumidityOnHoverScreen());
		Assert.assertTrue("Temperature is not displayed on hover screen.", weatherSteps.verifyTempOnHoverScreen());
		Assert.assertTrue("Temperature winds is not displayed on hover screen.",
				weatherSteps.verifyTempWindsOnHoverScreen());
	}

	@Then("Edit zip code and verify weather page")
	public void editZipCodeAndVerifyWeatherPage() {
		weatherSteps.clickEditZipCode();
		Assert.assertTrue("Weather page is not displayed, when clicks edit icon in hover screen.",
				weatherSteps.verifyPageTitle());
	}

	@Then("Click full forecast link and verify weather page")
	public void clickFullForecastLinkAndVerifyWeatherPage() {
		weatherSteps.clickFullForecastLink();
		Assert.assertTrue("Weather page is not displayed, when clicks full forecast link in hover screen.",
				weatherSteps.verifyPageTitle());
	}

	@Then("Validate the Hover details with the main page details")
	public void validateeHoverDetailsWithMainPageDetails() {
		weatherSteps.clickWeatherMenu();
		Assert.assertEquals("City name mismatch on weather page.", weatherSteps.getCityNameFromWeatherHoverScreen(),
				weatherSteps.getCityName());
		Assert.assertEquals("Humidity value mismatch on weather page.",
				weatherSteps.getHumidityFromWeatherHoverScreen(), weatherSteps.getHumidity());
		Assert.assertEquals("Dew point value mismatch on weather page.", weatherSteps.getDewFromWeatherHoverScreen(),
				weatherSteps.getDewValue());
		Assert.assertEquals("Temperature on weather page.", weatherSteps.getTemperatureFromWeatherHoverScreen(),
				weatherSteps.getTemperature());
		Assert.assertEquals("Temperature image mismatch on weather page.",
				weatherSteps.getTempImageFromWeatherHoverScreen(), weatherSteps.getTempImage());
		Assert.assertEquals("Temperature winds mismatch on weather page.",
				weatherSteps.getTempWindsFromWeatherHoverScreen(), weatherSteps.getTempWinds());
	}

	@When("Verify the weather tokens claimed status of the user and the Progres bar")
	public void verifyTokensClaimedStatus() {
		weatherSteps.clickWeatherMenu();
		Assert.assertTrue("Token not claimed for the day", weatherSteps.claimTokens());
		Assert.assertEquals("Daily bonus game count is not equal.", 1, homeSteps.getDailyBonusGameCheckCount());
		Assert.assertTrue("Daily bonus game lock icon is not enabled.", homeSteps.verifyDailyBonusGameIconEnabled());
	}

	@Then("Verify the Daily, Hourly, Ad and Climate Hisotry sections")
	public void verifyClimateHisotrySections() {
		navigationSteps.navigateToFPApplication();
		weatherSteps.clickWeatherMenu();
		Assert.assertTrue("Hourly forecast section is not displayed", weatherSteps.verifyHourlyForecastSection());
		Assert.assertTrue("Daily forecast section is not displayed", weatherSteps.verifyDailyForecastSection());
		Assert.assertTrue("Climate section is not displayed", weatherSteps.verifyClimateHistorySection());
		Assert.assertTrue("Weather ads section is not displayed", weatherSteps.verifyWeatherAdSection());
	}

	@Then("Verify the image of Header and Footer menu")
	public void verifyWeatherImage() {
		Assert.assertEquals("Header and Footer weather image mismatched.", weatherSteps.getHeaderImage(),
				weatherSteps.getFooterImage());
	}

	@Then("Verify default city for invalid zipcode")
	public void verifyDefaultCity() {
		weatherSteps.changeCity("15000");
		Assert.assertEquals("Default city name is not displayed, when entered invalid zip code",
				weatherSteps.getCityName(), "port washington");
	}

	@When("Verify the presence of Hourly Forecast section")
	public void verifyHourlyForecast() {
		weatherSteps.clickWeatherMenu();
		Assert.assertTrue("Hourly forecast section is not displayed", weatherSteps.verifyHourlyForecastSection());
	}

	@Then("Verify ads and weather content in expanded section")
	public void verifyAdsOnWhenExpandedHourlySection() {
		String pageAdGoogleIds[] = weatherSteps.getPageAdGoogleQueryId();
		Assert.assertTrue("Hourly forcast section is not expanding.",
				weatherSteps.verifyHourlyForecastExpandStatus(""));
		Assert.assertNotEquals(pageAdGoogleIds[0], weatherSteps.getInlineAdGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[1], weatherSteps.getRightRailAdOneGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[2], weatherSteps.getRightRailAdTwoGoogleQueryId());
	}

	@Then("Verify ads and weather content in collapsed section")
	public void verifyAdsOnWhenCollapsedHourlySection() {
		String pageAdGoogleIds[] = weatherSteps.getPageAdGoogleQueryId();
		Assert.assertTrue("Hourly forcast section is not collapsed.",
				weatherSteps.verifyHourlyForecastCollapsedStatus("expanded"));
		Assert.assertNotEquals(pageAdGoogleIds[0], weatherSteps.getInlineAdGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[1], weatherSteps.getRightRailAdOneGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[2], weatherSteps.getRightRailAdTwoGoogleQueryId());
	}

	@When("Verify the presence of daily forecast section")
	public void verifyDailyForecastSection() {
		weatherSteps.clickWeatherMenu();
		Assert.assertTrue("Daily forecast section is not displayed", weatherSteps.verifyDailyForecastSection());
	}

	@Then("Verify the 5 day forecast section")
	public void verifyFiveDayForecastSection() {
		String pageAdGoogleIds[] = weatherSteps.getPageAdGoogleQueryId();
		weatherSteps.clickFiveDayForecastSection();
		Assert.assertTrue("5 day forcast section is not collapsed.", weatherSteps.verifyFiveDayForecastSection());
		Assert.assertNotEquals(pageAdGoogleIds[0], weatherSteps.getInlineAdGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[1], weatherSteps.getRightRailAdOneGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[2], weatherSteps.getRightRailAdTwoGoogleQueryId());
	}

	@Then("Verify the 7 day forecast section")
	public void verifySevenDayForecastSection() {
		String pageAdGoogleIds[] = weatherSteps.getPageAdGoogleQueryId();
		weatherSteps.clickSevenDayForecastSection();
		Assert.assertTrue("7 day forcast section is not collapsed.", weatherSteps.verifySevenDayForecastSection());
		Assert.assertNotEquals(pageAdGoogleIds[0], weatherSteps.getInlineAdGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[1], weatherSteps.getRightRailAdOneGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[2], weatherSteps.getRightRailAdTwoGoogleQueryId());
	}

	@Then("Verify the 3 day forecast section")
	public void verifyThreeDayForecastSection() {
		String pageAdGoogleIds[] = weatherSteps.getPageAdGoogleQueryId();
		weatherSteps.clickThreeDayForecastSection();
		Assert.assertTrue("3 day forcast section is not collapsed.", weatherSteps.verifyThreeDayForecastSection());
		Assert.assertNotEquals(pageAdGoogleIds[0], weatherSteps.getInlineAdGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[1], weatherSteps.getRightRailAdOneGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[2], weatherSteps.getRightRailAdTwoGoogleQueryId());
	}

	@When("Verify the presence of weather climate history section")
	public void verifyClimateHistorySection() {
		weatherSteps.clickWeatherMenu();
		Assert.assertTrue("Climate section is not displayed.", weatherSteps.verifyClimateHistorySection());
	}

	@Then("Verify the default selected month")
	public void verifyDefaultMonth() {
		Assert.assertEquals(
				"Default month is not selected in Month dropdown, instead selected default month as: "
						+ weatherSteps.getSelectedMonthClimateHistory(),
				"April", weatherSteps.getSelectedMonthClimateHistory());
	}

	@Then("Verify Ad refresh while modifing the month")
	public void verifyAdRefresh() {
		String pageAdGoogleIds[] = weatherSteps.getPageAdGoogleQueryId();
		Assert.assertEquals("Selected month is not displayed in Month dropdown.",
				weatherSteps.changeClimateHistoryMonth(), weatherSteps.getSelectedMonthClimateHistory());
		Assert.assertNotEquals(pageAdGoogleIds[0], weatherSteps.getInlineAdGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[1], weatherSteps.getRightRailAdOneGoogleQueryId());
		Assert.assertNotEquals(pageAdGoogleIds[2], weatherSteps.getRightRailAdTwoGoogleQueryId());
	}
}