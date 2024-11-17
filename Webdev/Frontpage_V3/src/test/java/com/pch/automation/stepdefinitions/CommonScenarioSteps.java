package com.pch.automation.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.TokensSteps;
import com.pch.automation.steps.cs.SegmentationSteps;
import com.pch.automation.steps.fp.CVSteps;
import com.pch.automation.steps.jm.FpAdminSteps;
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
		navigationSteps.goToFPApplication();
		if (System.getProperty("testing.DeviceType") != null
				&& System.getProperty("testing.DeviceType").equalsIgnoreCase("Desktop")) {
			navigationSteps.maximizeWindow();
		}
	}

	/**
	 * Will clear up the Joomla admin info. before each story
	 */
	@BeforeStory
	public void beforeStory() {
		FpAdminSteps.getArticleDetails().clear();
	}

	/**
	 * Go to Front Page Site
	 */
	@Given("Go to Frontpage site")
	public void goToFPApplication() {
		navigationSteps.navigateToFPApplication();
	}

	/**
	 * Go to Front Page Site as guest user
	 */
	@Given("Go to Frontpage site as guest user")
	public void goToFpAsGuest() {
		navigationSteps.goToFPApplication();
	}

	/**
	 * Go to Front Page Site
	 */
	@Given("Go to Search & Win site")
	public void goToSWApplication() {
		navigationSteps.navigateToSWApplication();
	}

	/**
	 * Go to Front Page Site
	 */
	@Given("Verify the user lands on FP site")
	public void verifyFPSite() {
		navigationSteps.navigateToFPApplication();
	}

	/**
	 * 
	 * 
	 * /** Go to Frontpage Site
	 */
	@When("User redirect to the Frontpage application")
	@Then("User redirect to the Frontpage application")
	public void navigateToFPApplication() {
		navigationSteps.navigateToFPApplication();
	}

	/**
	 * 
	 * 
	 * /** Go to Frontpage Site
	 */
	@When("User redirect to EDL application")
	@Then("User redirect to EDL application")
	public void navigateToEDLApplication() {
		navigationSteps.navigateToEDLApplication();
	}

	/**
	 * Go to current URL and append parameter
	 */
	@Then("User redirect to the current URL '$queryStrings'")
	public void navigateToCurrentURl(String query) {
		if (FpAdminSteps.getArticleDetails().get(query) != null) {
			navigationSteps.navigateToCurrentURL(FpAdminSteps.getArticleDetails().get(query));
		} else if (!configProp.getEnvironmentProperty(query).isEmpty()) {
			navigationSteps.navigateToCurrentURL(configProp.getEnvironmentProperty(query));
		} else {
			navigationSteps.navigateToCurrentURL(query);
		}
	}

	/**
	 * Go to Frontpage Site and append parameter along with base URL
	 * 
	 * @param queryString
	 * 
	 */
	@Given("User redirect to the Frontpage application '$queryStrings'")
	@Then("User redirect to the Frontpage application '$queryStrings'")
	@When("User redirect to the Frontpage application '$queryStrings'")
	public void navigateToFPApplicationAppender(String query) {
		if (FpAdminSteps.getArticleDetails().get(query) != null
				&& !FpAdminSteps.getArticleDetails().get(query).isEmpty()) {
			navigationSteps.navigateToFPApplication(FpAdminSteps.getArticleDetails().get(query));
		} else if (!configProp.getEnvironmentProperty(query).isEmpty()) {
			navigationSteps.navigateToFPApplication(configProp.getEnvironmentProperty(query));
		} else {
			navigationSteps.navigateToFPApplication(query);
		}
	}

	/**
	 * Login to the Search and Win Site as recent user
	 * 
	 */
	@Given("Login to the FP Application as recent user")
	@Then("Login to the FP Application as recent user")
	public void loginToFrontpageSiteAsRecentUser() {
		navigationSteps.loginToFPApplicationAsRecentUser();
	}

	/**
	 * Login to the Search and Win Site as recognized user
	 * 
	 */
	@Given("Login to the FP Application as recognized user")
	public void loginToFrontpageSiteAsRecognizedUser() {
		navigationSteps.loginToFrontpageSiteAsRecognizedUser();
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
	 * To verify the tokens claimed for the day.
	 * 
	 */
	@Then("Verify tokens are claimed")
	public void verifyTokensClaimed() {
		homepageSteps.clickClaimButton();
		assertTrue("Tokens not claimed for the day", homepageSteps.verifyTokensClaimed());
	}

	/**
	 * To verify complete registration button present on the uninav
	 * 
	 */
	@When("Verify complete registration button on the uninav")
	public void verifyPresenceOfCompleteRegButton() {
		assertTrue("Complete reg button is not present", com.verifyCompleteRegistration());
	}

	/**
	 * To verify user landed on the homepage successfully
	 */
	@Then("Verify user successfully landed on homepage")
	@Alias("Verify the user is full reg")
	public void verifyUserLandingOnHomepage() {
		assertTrue("Frontpage Homepage is not displayed", com.verifyHome());
	}

	@When("Do a search on homepage and Verify SERP page")
	public void searchOnHomepage() {
		homepageSteps.searchAndVerifySERPPage();
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
