package com.pch.survey.stepdefinitions.surveytab;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.accounts.CreatePasswordPage;
import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.pages.surveytab.LightboxPage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.DevToolsNetworkTabDto;
import com.pch.survey.utilities.LogReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SurveyPageStepDefinitions {

	private SurveyMainPage surveyPage = new SurveyMainPage(WebdriverBuilder.getDriver());
	private LogReader logReader = new LogReader();
	private static String offersUrl = ConfigurationReader.getOffersUrl();
	private static ArrayList<DevToolsNetworkTabDto> networkTabData = null;
	private static String env = ConfigurationReader.getEnvironment().toLowerCase();
	private CreatePasswordPage createPasswordPage = new CreatePasswordPage(WebdriverBuilder.getDriver());
	private MpoRegistrationPage mpoRegistrationPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());
	private LightboxPage lightboxPage = new LightboxPage(WebdriverBuilder.getDriver());

	@Given("I click Profile Builder lets go button")
	public void clickLetsGo() {
		surveyPage.clickProfileBuilderLetsGoButton();
	}

	@Given("I click Sign In Button")
	public void clickSignIn() {
		surveyPage.clickSignInButton();
	}

	@Given("I click first Take Survey Button")
	public void clickFirstSurvey() {
		surveyPage.clickTakeSurvey();
	}

	@Given("I navigates to survey {string}")
	public void givenImNavigatesToMid(String mid) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(mid))
			mid = ConfigurationReader.getAppConfigProperty().get(mid);
		System.out.println("Navigating to url  " + offersUrl + "?mid=" + mid);
		WebdriverBuilder.getDriver().navigate().to(offersUrl + "?mid=" + mid);
		surveyPage.navigateToMid(mid);
	}

	@Given("I wait for survey start page to load")
	public void waitForSurveypage() {
		surveyPage.waitForSurveypage();
	}

	@Then("I verify query string parameters are not passed in url {string}")
	public void thenVerifyQueryStringParameters(String queryString) {
		String[] params = queryString.split(",");
		for (int ndx = 0; ndx < params.length; ndx++) {
			Assert.assertFalse("Verify query string param" + params[ndx] + "is not passed in url",
					surveyPage.verifySurveyMidQueryStringParameter(params[ndx]));
		}
	}

	@Then("I verify query string parameters are passed in url {string}")
	public void thenVerifyQueryStringParametersPassed(String queryString) {
		String[] params = queryString.split(",");
		for (int ndx = 0; ndx < params.length; ndx++) {
			Assert.assertTrue("Verify query string param " + params[ndx] + "is passed in url",
					surveyPage.verifySurveyMidQueryStringParameter(params[ndx]));
		}
	}

	@Given("I select a survey {string}")
	public void givenImSelectLucidSurvey(String survey) {
		surveyPage.clickTakeSurvey(survey);
	}

	@Then("I verify PchSurveys logo is present on page")
	public void thenVerifyLogo() {
		surveyPage.isPchSurveysMainLogoDisplayed();
	}

	@When("I click on {string} icon")
	public void WhenIClickmenuIcon(String txt) {

		surveyPage.clickMenuIcon(txt);
	}

	@Then("I verify menu icon {string} is present on page")
	public void thenVerifyLogo(String linkText) {
		surveyPage.isMenuIconDisplayed(linkText);
	}

	@Then("I verify page title is {string}")
	public void thenVerifyPageTitle(String title) {
		Assert.assertTrue(surveyPage.waitUntilPageTitleContains(title.replace("[pipe]", "|")));
	}

	@Then("I verify tabpage title is {string}")
	public void thenVerifyTabPageTitle(String title) {
		surveyPage.switchToLastOpenTab();
		Assert.assertTrue(surveyPage.waitUntilPageTitleContains(title.replace("[pipe]", "|")));
	}

	// mobile
	@When("I click and open Hamburger Menu")
	public void whenIOpenMenu() {
		surveyPage.clickHamburgerMenu();
	}

	@Then("I verify that new category surveys are available in featured section")
	public void thenVerifyNewCategoySurveysInFeature() {
		Assert.assertTrue("Verify new category survey count > 0",
				surveyPage.getNewCategorySurveyCountInFeatureArea() > 0);
		surveyPage.getSurveyCountInFeatureArea();
	}

	@Then("I verify that all category surveys not marked with new label in featured section")
	public void thenVerifySurveysInFeature() {
		Assert.assertTrue("Verify featured survey count",
				surveyPage.getSurveyCountInFeatureArea() > surveyPage.getNewCategorySurveyCountInFeatureArea());

	}

	@Then("I verify that new category surveys are available in main survey section")
	public void thenVerifyNewCategoySurveysInSurvey() {
		Assert.assertTrue("Verify new category survey count > 0",
				surveyPage.getNewCategorySurveyCountInSurveyArea() > 0);
	}

	@Then("I verify that all category surveys not marked with new label in main survey section")
	public void thenVerifySurveysInSurvey() {
		Assert.assertTrue("Verify main survey count",
				surveyPage.getSurveyCountInSurveyArea() > surveyPage.getNewCategorySurveyCountInSurveyArea());

	}

	@When("I click the Yes Im In button in survey handraiser section for {string}")
	public void whenIClickYesIminButton(String userType) {
		surveyPage.clickYesImInButton();
		if (userType.equalsIgnoreCase("silver")) {
			createPasswordPage.createPasswordLb();
		} else if (userType.equalsIgnoreCase("guest")) {
			if (WebdriverBuilder.getDriver().getCurrentUrl().contains("https://mpo." + env + ".pch.com/")) {
				mpoRegistrationPage.CreateFullRegUserForm();
			}
		}
	}

	@When("I click the Redeem Tokens image")
	public void whenIClickRedeenTokens() {
		surveyPage.clickRedeemTokensImage();
	}

	@Then("I should be redirected to the Surveys Tab")
	public void i_should_be_redirected_to_the_surveys_tab() {
		Assert.assertEquals("User is not redirected to Survey Tab", ConfigurationReader.getUrl(),
				surveyPage.getCurrentURL());
	}

	@Then("I should be redirected to the Program Terms page in new tab")
	public void i_should_be_redirected_to_the_program_terms_page_in_new_tab() {
		surveyPage.switchToLastOpenTab();
		Assert.assertEquals("User is not redirected to Program Terms page", ConfigurationReader.getProgramTermsUrl(),
				surveyPage.getCurrentURL());
	}

	@Then("I verify the Bonus game locked state")
	public void thenVerifyBonusGameModuleLockedState() {
		Assert.assertTrue("Failed to display locked state of Bonus game.", surveyPage.verifyBonusGameLocked());
	}

	@Then("I verify the Bonus game unlocked state")
	public void thenVerifyBonusGameUnlockedState() {
		Assert.assertTrue("Failed to display unlocked state of Bonus game.", surveyPage.verifyBonusGameIsUnlocked());
	}

	@When("I get redirected to the Bonus game page in the new tab")
	public void verifyBonusGamePageRedirection() {
		surveyPage.switchToLastOpenTab();
		Assert.assertEquals("User is not redirected to Bonus game page", ConfigurationReader.getBonusGameUrl(),
				surveyPage.getCurrentURL());

	}
	
	@Then("I do not see Play Now Button")
	public void i_do_not_see_play_now_button() {
		Assert.assertFalse(surveyPage.isPlayNowPresent());
	}

	@Then("Bonus game is locked")
	public void bonus_game_is_locked() {
		Assert.assertTrue(surveyPage.isLocked());
	}

	@Then("I see the Play Now button")
	public void i_see_the_play_now_button() {
		Assert.assertTrue(surveyPage.isPlayNowPresent());

	}

	@Then("I verify {string} user level on homepage")
	public void thenVerifyUserLevelStatus(String userLevel) {
		lightboxPage.closeBonusGameLB();
		if (userLevel.equalsIgnoreCase("Influencer"))
			Assert.assertTrue("Failed to display " + userLevel + " user status on Homepage.",
					surveyPage.verifyInfluencerUserStatus());
		if (userLevel.equalsIgnoreCase("InfluencerPro"))
			Assert.assertTrue("Failed to display " + userLevel + " user status on Homepage.",
					surveyPage.verifyInfluencerProUserStatus());
		if (userLevel.equalsIgnoreCase("InfluencerProPlus"))
			Assert.assertTrue("Failed to display " + userLevel + " user status on Homepage.",
					surveyPage.verifyInfluencerProPlusUserStatus());
	}

	@Then("I verify {string} event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier {string} and device {string}")
	public void thenGATagsInLog(String event, String supplier, String device) {
		List<String> jsonTagList = logReader.getAllGA3TagsByGmt(Registrations.getGmt());
		boolean gaTagFound = false;
		for (String jsonStr : jsonTagList) {
			JSONObject json = new JSONObject(jsonStr);
			if (json.getString("ea").equalsIgnoreCase(event)) {
				if (json.getString("ec").equalsIgnoreCase(supplier + "/" + device))
					gaTagFound = true;
				break;
			}
		}
		if (!gaTagFound)
			Assert.fail("Cannot find GA3 tag for survey start event " + event + supplier + device);
	}

	@Then("I verify {string} event GA4 tags are being recorded in Ga4 logfile for supplier {string} and device {string}")
	public void thenGA4TagsInLog(String event, String supplier, String device) {
		List<String> jsonTagList = logReader.getAllGA4TagsByGmt(Registrations.getGmt());
		boolean gaTagFound = false;
		for (String jsonStr : jsonTagList) {
			JSONObject json = new JSONObject(jsonStr);
			JSONArray eventArray = json.getJSONArray("events");
			if (eventArray.getJSONObject(0).getString("name").equalsIgnoreCase(event)) {
				if (eventArray.getJSONObject(0).getJSONObject("params").getString("supplier_device_type")
						.equalsIgnoreCase(supplier + "/" + device))
					gaTagFound = true;
				break;
			}
		}
		if (!gaTagFound)
			Assert.fail("Cannot find GA4 tag for survey event " + event + supplier + device);
	}

	@Given("I close the Take a Tour popup")
	public void givenIClosetakeATour() {
		WebDriver driver = WebdriverBuilder.getDriver();
		new SurveyMainPage(driver).clickSurveyTourCloseButton();
		surveyPage.closePhpdebugbar();
	}

	@When("I click on the Take a Tour link")
	public void clickTakeATour() {
		WebDriver driver = WebdriverBuilder.getDriver();
		new SurveyMainPage(driver).clickTakeATourLink();
	}

	@Then("I verify Take a Tour popup is {string}")
	public void isTakeATourPopupDisplayed(String displayed) {
		boolean isDisplayed = new SurveyMainPage(WebdriverBuilder.getDriver()).isTakeATourPopupDisplayed();
		if (displayed.equalsIgnoreCase("displayed"))
			Assert.assertTrue("Take a tour ppup is displayed ", isDisplayed);
		else
			Assert.assertFalse("Take a tour ppup is not displayed ", isDisplayed);
	}

	@Then("I verify Take a Tour popup message slides")
	public void verifyTakeATour() {
		WebDriver driver = WebdriverBuilder.getDriver();
		SurveyMainPage surveyPage = new SurveyMainPage(driver);
		Assert.assertTrue("Take a tour popup is displayed", surveyPage.isTakeATourPopupDisplayed());
		Assert.assertTrue("Current slide is displayed", surveyPage.isActiveTourSlideDisplayed());
		surveyPage.clickCarouselNextArrow();
		Assert.assertTrue("Current slide is displayed", surveyPage.isActiveTourSlideDisplayed());
		surveyPage.clickCarouselNextArrow();
		Assert.assertTrue("Current slide is displayed", surveyPage.isActiveTourSlideDisplayed());

	}

	@Then("I verify awarded token amount {string}")
	public void verifyAwardedTokenAmt(String tokenAmt) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(tokenAmt))
			tokenAmt = ConfigurationReader.getAppConfigProperty().get(tokenAmt);
		Assert.assertTrue(
				Integer.parseInt(surveyPage.getTokenAmtText().replace(",", "")) >= Integer.parseInt((tokenAmt)));
	}

}
