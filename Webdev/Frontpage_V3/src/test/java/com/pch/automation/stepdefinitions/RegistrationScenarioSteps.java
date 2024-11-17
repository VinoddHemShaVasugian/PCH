package com.pch.automation.stepdefinitions;

import org.jbehave.core.annotations.Given;

import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.RegistrationSteps;
import com.pch.automation.steps.fp.OptinSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Registration Scenario Steps
 * 
 * @author mperumal
 *
 */
public class RegistrationScenarioSteps {

	@Steps
	RegistrationSteps regSteps;

	@Steps
	NavigationSteps navigationSteps;

	@Steps
	OptinSteps optinSteps;

	/**
	 * Register the user by selecting the Optins
	 */
	@Given("Register a user with the Optins")
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
}