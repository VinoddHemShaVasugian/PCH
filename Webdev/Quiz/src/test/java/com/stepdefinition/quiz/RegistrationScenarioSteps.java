package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.steps.quiz.HomepageSteps;
import com.steps.quiz.NavigationSteps;
import com.steps.quiz.RegistrationSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Registration Scenario Steps
 * 
 * @author vsankar
 *
 */
public class RegistrationScenarioSteps {

	@Steps
	RegistrationSteps regSteps;

	@Steps
	NavigationSteps navigationSteps;

	@Steps
	HomepageSteps homepageSteps;

	/**
	 * Register the user by selecting the Optins
	 */
	@Given("Register a user with the Optins")
	@When("Register a user with the Optins")
	public void registerUserWithOptins() {
		regSteps.registerUserWithOptins();
	}

	/**
	 * Register the user by un selecting the Optins
	 */
	@Given("Register a user without the Optins")
	public void registerUserWithoutOptins() {
		regSteps.registerUserWithoutOptins();
	}

	/**
	 * Register the different user types from RF and login to site
	 */
	@Given("Create '<userType>' user and login to quiz tab")
	public void registerUsersFromRFAndLoginToSite(String userType) {
		if (userType.equalsIgnoreCase("fully registered")) {
			regSteps.registerFullyRegFromRFAndLoginToSite();
		} else if (userType.equalsIgnoreCase("mini registered")) {
			regSteps.registerMiniRegFromRFAndLoginToSite();
		} else if (userType.equalsIgnoreCase("silver")) {
			regSteps.registerSilverRegFromRFAndLoginToSite();
		} else if (userType.equalsIgnoreCase("social")) {
			regSteps.registerSocialRegFromRFAndLoginToSite();
		} else if (userType.equalsIgnoreCase("guest")) {
			navigationSteps.goToQuizApplication();
		}else {
			Assert.assertTrue("Registration failed", false);
		}
	}

	/**
	 * Register the Fully registered user from RF
	 */
	@Given("Register a fully registered user through RF and login to the site")
	public void registerFullyRegFromRFAndLoginToSite() {
		regSteps.registerFullyRegFromRFAndLoginToSite();
	}

	/**
	 * Register the Fully registered user from RF
	 */
	@Given("Register a fully registered user through RF")
	public void registerFullyRegFromRF() {
		regSteps.registerFullyRegFromRF();
	}

	/**
	 * Register the Mini registered user from RF
	 */
	@Given("Register a mini registered user through RF and login to the site")
	public void registerMiniRegFromRFAndLoginToSite() {
		regSteps.registerMiniRegFromRFAndLoginToSite();
	}

	/**
	 * Register the Mini registered user from RF
	 */
	@Given("Register a mini registered user through RF")
	public void registerMiniRegFromRF() {
		regSteps.registerMiniRegFromRF();
	}

	/**
	 * Register the Silver user from RF
	 */
	@Given("Register a silver user through RF and login to the site")
	public void registerSilverRegFromRFAndLoginToSite() {
		regSteps.registerSilverRegFromRFAndLoginToSite();
	}

	/**
	 * Register the Silver user from RF
	 */
	@Given("Register a silver user through RF")
	public void registerSilverRegFromRF() {
		regSteps.registerSilverRegFromRF();
	}

	/**
	 * Register the Social registered user from RF
	 */
	@Given("Register a social user through RF and login to the site")
	public void registerSocialRegFromRFAndLoginToSite() {
		regSteps.registerSocialRegFromRFAndLoginToSite();
	}

	/**
	 * Register the Social registered user from RF
	 */
	@Given("Register a social user through RF")
	public void registerSocialRegFromRF() {
		regSteps.registerSocialRegFromRF();
	}

	/**
	 * To verify registration page
	 */
	@Then("Verify the registration page")
	public void verifyRegistrationPage() {
		regSteps.clickRegister();
		Assert.assertTrue("Registration page is not displayed", regSteps.verifyRegistrationPage());
	}
}