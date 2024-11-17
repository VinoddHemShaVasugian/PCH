package com.pch.survey.stepdefinitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.dtos.GaTagInfoDto;
import com.pch.survey.pages.GaTrackingPage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonStepDefinitions {

	public static String featureName;
	public static String scenarioName;

	private static String url = ConfigurationReader.getInstance().getApplicationUrl();
	private static String offersUrl = ConfigurationReader.getInstance().getOffersUrl();
	private static String surveyUrl = ConfigurationReader.getInstance().getSurveyUrl();
	private static String badgesUrl = ConfigurationReader.getInstance().getBadgesUrl();

	private static String envir = ConfigurationReader.getInstance().getEnvironment();

	private static SurveyMainPage surveyPage;
	private static GaTrackingPage gatracking;

	@Given("I land on the Surveys Page as a guest user")
	public void givenimOnSurveysPage() {
		WebDriver driver = WebdriverBuilder.getDriver();
		driver.get(url);

		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the Surveys Page as a {string} user")
	public void givenNewUsersLandingOnSurveyPage(String userType) {
		WebDriver driver = WebdriverBuilder.getDriver();
		if (userType.equalsIgnoreCase("full reg")) {
			Registrations.createGoldUser();
		} else if (userType.equalsIgnoreCase("mini reg")) {
			Registrations.createMiniRegUser();
		} else if (userType.equalsIgnoreCase("silver")) {
			Registrations.createSilverUser();
		} else if (userType.equalsIgnoreCase("social")) {
			Registrations.createSocialUser();
		} else if (userType.equalsIgnoreCase("guest")) {
			driver.get(url);
		} else {
			Assert.assertTrue("Registration failed", false);
		}
		String urlNew = url;
		if (!userType.equalsIgnoreCase("guest")) {
			urlNew = url + "/?em=" + Registrations.getEmail() + "&e=" + Registrations.getGmt();
			System.out.println(urlNew);
			driver.get(urlNew);
		}
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the Surveys Page as a recognized silver user")
	public void givenimOnSurveysRecognized() {
		WebDriver driver = WebdriverBuilder.getDriver();
		Registrations.createSilverUser();
		String urlNew = url + "/?em=" + Registrations.getEmail() + "&e=" + Registrations.getGmt();
		System.out.println(urlNew);
		driver.get(urlNew);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the Surveys Page as a recognized full reg user")
	public void givenimOnSurveysRecognizedGold() {
		WebDriver driver = WebdriverBuilder.getDriver();
		Registrations.createGoldUser();
		String urlNew = url + "/?em=" + Registrations.getEmail() + "&e=" + Registrations.getGmt();
		System.out.println(urlNew);
		driver.get(urlNew);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the Surveys Page as a recognized social user")
	public void givenimOnSurveysSocialRecognized() {
		WebDriver driver = WebdriverBuilder.getDriver();
		Registrations.createSocialUser();
		String urlNew = url + "/?em=" + Registrations.getEmail() + "&e=" + Registrations.getGmt();
		System.out.println(urlNew);
		driver.get(urlNew);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the Surveys Page as a recognized mini reg user")
	public void givenimOnSurveysMiniRegRecognized() {
		WebDriver driver = WebdriverBuilder.getDriver();
		Registrations.createMiniRegUser();
		String urlNew = url + "/?em=" + Registrations.getEmail() + "&e=" + Registrations.getGmt();
		System.out.println(urlNew);
		driver.get(urlNew);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on badges page as a guest user")
	public void i_land_on_badges_page() {
		WebDriver driver = WebdriverBuilder.getDriver();
		driver.get(badgesUrl);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the Surveys Page as a existing user with email {string} and gmt {string}")
	public void givenImOnSurveysPredefinedUser(String emailParm, String gmtParm) {
		Registrations.setEmail(emailParm);
		Registrations.setGmt(gmtParm);
		WebDriver driver = WebdriverBuilder.getDriver();
		String urlNew = url + "/?em=" + emailParm + "&e=" + gmtParm;
		System.out.println(urlNew);
		driver.get(urlNew);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Given("I land on the handraiser page where mid equals {string}")
	public void givenImOnHandraiserPage(String mid) {
		WebDriver driver = WebdriverBuilder.getDriver();
		String urlNew = offersUrl + "?mid=" + mid;
		System.out.println(urlNew);
		driver.get(urlNew);
	}

	@Given("I switch to the Surveys Page")
	public void givenISwithToSurveysPage() {
		WebDriver driver = WebdriverBuilder.getDriver();

		driver.get(url);
		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
		// driver.manage().window().maximize();
	}

	@Given("I switch to Lucid surveys page")
	public void givenImOnLucidSurveysPage() {
		WebDriver driver = WebdriverBuilder.getDriver();

		String urlNew = surveyUrl + "/?mid=lucid";
		System.out.println(urlNew);
		driver.get(urlNew);
	}

	@Given("I close the Take a Tour popup")
	public void givenIClosetakeATour() {
		WebDriver driver = WebdriverBuilder.getDriver();

		new SurveyMainPage(driver).clickSurveyTourCloseButton();
	}

	@Given("I close the Privacy popup")
	public void givenIClosePrivacy() {
		WebDriver driver = WebdriverBuilder.getDriver();

		new SurveyMainPage(driver).clickPrivacyPolicyCloseButton();
	}

	@Then("Verify All GA Tags$")
	public void verifyAllGATags(DataTable table) {
		List<GaTagInfoDto> actualGATagList = new GaTrackingPage(WebdriverBuilder.getDriver()).getGATags();
		List<List<String>> expectedGATagList = table.asLists();
		for (int i = 1; i < expectedGATagList.size(); i++) {
			List<String> tag = expectedGATagList.get(i);
			String eventCategory = tag.get(0);
			String eventAction = tag.get(1);
			String eventlabel = tag.get(2);
			if (eventlabel == null)
				eventlabel = "";
			boolean tagFound = false;
			for (int x = 0; x < actualGATagList.size(); x++) {
				if (actualGATagList.get(x).getEventCategory().equals(eventCategory)
						&& actualGATagList.get(x).getEventAction().equals(eventAction)
						&& actualGATagList.get(x).getEventLabel().equals(eventlabel)) {
					tagFound = true;
					break;

				}
			}
			if (!tagFound)
				Assert.fail("Could not find GA tag " + eventCategory + eventAction + eventlabel);

		}
	}

	@When("I pause execution for {} seconds")
	public void givenIPauseexecution(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public String getEmail() {
		return Registrations.getEmail();
	}

	public String getGmt() {
		return Registrations.getGmt();
	}

	public String getPassword() {
		return Registrations.getPassword();
	}

	public String getFirstName() {
		if (Registrations.getFirstName() == null)
			return "";
		return Registrations.getFirstName();
	}

	@After
	public void afterScenario() {
		System.out.println("******************  Closing driver");
		WebdriverBuilder.closeDriver();
	}

	@Before
	public void before(Scenario scenario) {
		int lastIndex = scenario.getUri().toString().split("/").length - 1;
		featureName = scenario.getUri().toString().split("/")[lastIndex];
		scenarioName = scenario.getName();
		System.out.println("Tests Running in feature : " + featureName);
		System.out.println("Test Start for Scenario : " + scenarioName);
	}

}
