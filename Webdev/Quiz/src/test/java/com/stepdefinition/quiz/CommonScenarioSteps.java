package com.stepdefinition.quiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.RegistrationPage;
import com.pch.quiz.utilities.AppConfigLoader;
import com.steps.quiz.AccountSteps;
import com.steps.quiz.AdminSteps;
import com.steps.quiz.CommonSteps;
import com.steps.quiz.NavigationSteps;
import com.steps.quiz.TokensSteps;

import net.thucydides.core.annotations.Steps;

public class CommonScenarioSteps {
	@Steps
	NavigationSteps navigationSteps;
	@Steps
	AdminSteps adminSteps;
	@Steps
	AccountSteps accSteps;
	@Steps
	TokensSteps tokensSteps;
	@Steps
	CommonSteps commonSteps;

	HeaderAndUninavPage com;
	RegistrationPage regPage;

	private AppConfigLoader configProp = AppConfigLoader.getInstance();

	/**
	 * Before Scenarios.
	 */
	@BeforeScenario
	public void beforeScenario() {
		navigationSteps.goToQuizApplication();
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
		AdminSteps.getArticleDetails().clear();
	}

	/**
	 * Go to Quiz Site
	 */
	@Given("Go to Quiz site")
	public void goToQuizApplication() {
		navigationSteps.goToQuizApplication();
	}

	/**
	 * Go to Quizzes Apphealth page
	 */
	@When("land on quizzes app health Page")
	public void goToQuizessAppHealthPage() throws InterruptedException {
		navigationSteps.navigateToQuizesappHealth();
	}

	/**
	 * verify Quizzes Apphealth page
	 */
	@Then("verify quizzes apphealth")
	public void verifyQuizzesAppHealth() throws URISyntaxException, InterruptedException {
		commonSteps.QuizzesAppHealth();
	}

	/**
	 * Go to Quiz Site as guest user
	 */
	@Given("Go to Quiz site as guest user")
	public void goToQuizAsGuest() {
		navigationSteps.goToQuizApplication();
	}

	/**
	 * Go to Quiz Site and append parameter along with base URL
	 * 
	 * @param queryString
	 * 
	 */
	@Given("User redirect to the quiz application '$queryStrings'")
	@Then("User redirect to the quiz application '$queryStrings'")
	@When("User redirect to the quiz application '$queryStrings'")
	public void navigateToQuizApplicationAppender(String query) {
		if (AdminSteps.getArticleDetails().get(query) != null && !AdminSteps.getArticleDetails().get(query).isEmpty()) {
			navigationSteps.navigateToQuizApplication(AdminSteps.getArticleDetails().get(query));
		} else if (!configProp.getEnvironmentProperty(query).isEmpty()) {
			navigationSteps.navigateToQuizApplication(configProp.getEnvironmentProperty(query));
		} else {
			navigationSteps.navigateToQuizApplication(query);
		}
	}

	/**
	 * Navigate to Quiz Site
	 */
	@When("User redirect to the quiz application")
	@Then("User redirect to the quiz application")
	public void navigateToQuizApplication() {
		navigationSteps.navigateToQuizApplication();
	}

	/**
	 * Navigate to Quiz tab
	 */
	@When("User redirect to the quiz tab")
	@Then("User redirect to the quiz tab")
	public void navigateToQuizTab() {
		navigationSteps.navigateToQuizTab();
	}

	/**
	 * Go to Quiz tab
	 */
	@Given("Go to Quiz tab")
	@When("Go to Quiz tab")
	public void goToQuizTab() {
		navigationSteps.goToQuizTab();
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

	/**
	 * To verify complete registration button present on the uninav
	 * 
	 */
	@When("Verify complete registration button on the uninav")
	@Then("Verify complete registration button on the uninav")
	public void verifyPresenceOfCompleteRegButton() {
		assertTrue("Complete reg button is not present", com.verifyCompleteRegistration());
	}

	/**
	 * To verify user landed on the homepage successfully
	 */
	@Then("Verify user successfully landed on homepage")
	@Alias("Verify the user is full reg")
	public void verifyUserLandingOnHomepage() {
		assertTrue("Quiz Homepage is not displayed", com.verifyHome());
	}

	/**
	 * Login to the Search and Win Site as recognized user
	 * 
	 */
	@Given("Login to the Quiz Application as recognized user")
	public void loginToQuizSiteAsRecognizedUser() {
		navigationSteps.loginToQuizSiteAsRecognizedUser();
	}
	
	/**
	 * Go to current URL and append parameter
	 */
	@Then("User redirect to the current URL '$queryStrings'")
	public void navigateToCurrentURl(String query) {
		if (AdminSteps.getArticleDetails().get(query) != null) {
			navigationSteps.navigateToCurrentURL(AdminSteps.getArticleDetails().get(query));
		} else if (!configProp.getEnvironmentProperty(query).isEmpty()) {
			navigationSteps.navigateToCurrentURL(configProp.getEnvironmentProperty(query));
		} else {
			navigationSteps.navigateToCurrentURL(query);
		}
	}
}