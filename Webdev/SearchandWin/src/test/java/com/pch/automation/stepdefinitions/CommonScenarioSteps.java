package com.pch.automation.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.driver.BrowserStackSerenityDriver;
import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.TokensSteps;
import com.pch.automation.steps.cs.SegmentationSteps;
import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.steps.sw.CVSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Steps;

public class CommonScenarioSteps {

	@Steps
	NavigationSteps navigationSteps;
	@Steps
	SegmentationSteps segmentationSteps;
	@Steps
	TokensSteps tokensSteps;
	@Steps
	CVSteps cvSteps;
	@Steps
	HomepageSteps homepageSteps;
	HeaderAndUninavPage com;
	RegistrationPage regPage;
	private AppConfigLoader configProp = AppConfigLoader.getInstance();

	/**
	 * Before Scenarios.
	 */
	@BeforeScenario
	public void beforeScenario() {
		navigationSteps.goToSWApplication();
		navigationSteps.maximizeWindow();
	}

	/**
	 * Will clear up the Joomla admin info. before each story
	 */
	@BeforeStory
	public void beforeStory() {
		SearchAdminSteps.getArticleDetails().clear();
	}

	/**
	 * Will kill the running browser stack local process.
	 */
	@AfterStories
	public void afterAllStories() {
		try {
			if (BrowserStackSerenityDriver.getLocalInstance() != null
					&& BrowserStackSerenityDriver.getLocalInstance().isRunning()) {
				BrowserStackSerenityDriver.getLocalInstance().stop();
			}
		} catch (Exception e) {
			System.out.println("Exception in the Browser Stack local instance killing process");
			e.printStackTrace();
		}
	}

	/**
	 * Go to Front Page Site
	 */
	@Given("Go to Frontpage site")
	public void navigateToFPApplication() {
		navigationSteps.navigateToFPApplication();
	}

	/**
	 * Go to Front Page Site
	 */
	@Given("Verify the user lands on FP site")
	public void verifyFPSite() {
		navigationSteps.navigateToFPApplication();
	}

	/**
	 * Go to Search&Win Site as guest user
	 */
	@Given("Goto Search and Win Site")
	public void goToSWApplication() {
		navigationSteps.navigateToSWApplication();
	}

	/**
	 * Go to Search & Win Site
	 */
	@When("User redirect to the Search application")
	@Then("User redirect to the Search application")
	public void navigateToSWApplication() {
		navigationSteps.navigateToSWApplication();
	}

	/**
	 * Go to current URL and append parameter
	 */
	@Then("User redirect to the current URL '$queryStrings'")
	public void navigateToCurrentURl(String query) {
		if (SearchAdminSteps.getArticleDetails().get(query) != null) {
			navigationSteps.navigateToCurrentURL(SearchAdminSteps.getArticleDetails().get(query));
		} else if (!configProp.getEnvironmentProperty(query).isEmpty()) {
			navigationSteps.navigateToCurrentURL(configProp.getEnvironmentProperty(query));
		} else {
			navigationSteps.navigateToCurrentURL(query);
		}
	}

	/**
	 * Go to Search & Win Site and append parameter along with base URL
	 * 
	 * @param queryString
	 * @param condition   (If passing condition=1 from story file, will retrive
	 *                    queryString from admin. If passing condition=0, will use
	 *                    same parameter as querystring)
	 */
	@Given("User redirect to the Search application '$queryStrings'")
	@Then("User redirect to the Search application '$queryStrings'")
	@When("User redirect to the Search application '$queryStrings'")
	public void navigateToSWApplicationAppender(String query) {
		if (SearchAdminSteps.getArticleDetails().get(query) != null
				&& !SearchAdminSteps.getArticleDetails().get(query).isEmpty()) {
			navigationSteps.navigateToSWApplication(SearchAdminSteps.getArticleDetails().get(query));
		} else if (!configProp.getEnvironmentProperty(query).isEmpty()) {
			navigationSteps.navigateToSWApplication(configProp.getEnvironmentProperty(query));
		} else {
			navigationSteps.navigateToSWApplication(query);
		}
	}

	/**
	 * Login to the Search and Win Site as recent user
	 * 
	 */
	@Given("Login to the SW Application as recent user")
	@Then("Login to the SW Application as recent user")
	public void loginToSearchAndWinSiteAsRecentUser() {
		navigationSteps.loginToSWApplicationAsRecentUser();
	}

	/**
	 * Navigate to the Token History page
	 * 
	 */
	@Then("Navigate to Token History Page")
	public void navigateToTokenHistoryPage() {
		navigationSteps.redirectToTokenHistoryPage();
	}

	/**
	 * To verify complete registration button present on the uninav
	 * 
	 */
	@When("Lands on homepage verify complete registration button on the uninav")
	public void presenceOfCompleteRegButton() {
		assertTrue("Complete reg button is not present", com.verifyCompleteRegistration());
	}

	/**
	 * To verify user landed on the homepage successfully 41
	 */
	@Then("User successfully landed on the search&win homepage")
	@Alias("Verify the user is full reg")
	public void verifyUserLandingOnHomepage() {
		assertTrue("when user doesn't land on home page", com.verifyHome());
	}

	@When("Do a search on homepage and Verify SERP page")
	@Then("Do a search on homepage and Verify SERP page")
	public void searchOnHomepage() {
		cvSteps.searchAndVerifySERPPage();
	}

	/**
	 * Verify tokens transaction details (Like Tokens, Description, etc...) based on
	 * admin configured segment/article
	 */
	@Then("Verify token transaction details based on admin configured article '$tokens','$description','$position'")
	public void verifyTokensTransactionDetails(String description, String tokens, String position) {
		assertEquals("Tokens details are failed to display on Token History tab for " + position + " times",
				Integer.parseInt(position),
				tokensSteps.verifyTokenTransactionsDetails(description, tokens, Integer.parseInt(position)));
	}

	@Then("Verify the C1 VIP message")
	@Given("Verify the C1 VIP message")
	public void verifyC1VipMessage() throws Exception {
		String vipMsg = tokensSteps.formulateExpectedVipMessage("VipC1MsgHeader", "VipC1MsgBody", "Friend");
		assertTrue("C1 VIP message is mis matched", vipMsg.contains(com.getVipMsg()));
	}

	@Then("Verify the C1 VIP message after complete registration")
	@When("Verify the C1 VIP message after complete registration")
	public void verifyC1VipMessageAfterCompleteReg() throws Exception {
		String vipMsg = tokensSteps.formulateExpectedVipMessage("VipC1MsgHeader", "VipC1MsgBody", regPage.fstName);
		assertTrue("C1 VIP message is mis matched", vipMsg.contains(com.getVipMsg()));
	}

	@Then("Verify the L1 VIP message")
	@Alias("Verify the VIP activity")
	public void verifyL1VipMessage() throws Exception {
		String vipMsg;
		if (RegistrationPage.regGenerator.getFirstName() != null
				&& !RegistrationPage.regGenerator.getFirstName().isEmpty()) {
			vipMsg = tokensSteps.formulateExpectedVipMessage("VipL1MsgHeader", "VipL1MsgBody",
					RegistrationPage.regGenerator.getFirstName());
		} else {
			vipMsg = tokensSteps.formulateExpectedVipMessage("VipL1MsgHeader", "VipL1MsgBody", regPage.fstName);
		}
		assertTrue("L1 VIP message is mis matched", vipMsg.contains(com.getVipMsg()));
	}

	@Then("Verify the N1 VIP message")
	public void verifyN1VipMessage() throws Exception {
		String vipMsg;
		if (RegistrationPage.regGenerator.getFirstName() != null
				&& !RegistrationPage.regGenerator.getFirstName().isEmpty()) {
			vipMsg = tokensSteps.formulateExpectedVipMessage("VipN1MsgHeader", "VipN1MsgBody",
					RegistrationPage.regGenerator.getFirstName());

		} else {
			vipMsg = tokensSteps.formulateExpectedVipMessage("VipN1MsgHeader", "VipN1MsgBody", regPage.fstName);
		}
		assertTrue("N1 VIP message is mis matched", vipMsg.contains(com.getVipMsg()));
	}

	@When("Verify the presence of sams banner on homepage")
	@Then("Verify the presence of sams banner on homepage")
	public void verifySamsBanner() throws Exception {
		assertTrue("SAMS banner is not displayed", homepageSteps.verifySamsBanner());
	}

	@When("Verify the absence of sams banner on homepage")
	@Then("Verify the absence of sams banner on homepage")
	public void verifyDefaultBanner() throws Exception {
		assertTrue("Default fallback banner is not displayed", homepageSteps.verifyDefaultBanner());
	}
}
