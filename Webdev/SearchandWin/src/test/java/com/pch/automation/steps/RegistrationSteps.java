package com.pch.automation.steps;

import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

public class RegistrationSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	public static String recentURL;
	RegistrationPage regPage;
	@Steps
	NavigationSteps navigationSteps;
	LightboxPage lbPage;

	/**
	 * Register the user by selecting the Optins
	 */
	@Step
	public void registerUserWithOptins() {
		regPage.registerUserWithOptins();
	}

	/**
	 * Register the user by un-selecting the Optins
	 */
	@Step
	public void registerUserWithoutOptins() {
		regPage.registerUserWithoutOptins();
	}

	@Step
	public void registerFullyRegFromRFAndLoginToSite() {
		getDriver().get(regPage.registerFullyRegUserFromRF());
		lbPage.closeOptinLightbox();
		getDriver().navigate().refresh();
	}

	@Step
	public void registerFullyRegFromRF() {
		regPage.registerFullyRegUserFromRF();
	}

	/**
	 * Register the Mini reg. user from RF
	 */
	@Step
	public void registerMiniRegFromRFAndLoginToSite() {
		getDriver().get(regPage.registerMiniRegUserFromRF());
		lbPage.closeOptinLightbox();
		getDriver().navigate().refresh();
	}

	@Step
	public void registerMiniRegFromRF() {
		regPage.registerMiniRegUserFromRF();
	}

	/**
	 * Register the Silver reg. user from RF
	 */
	@Step
	public void registerSilverRegFromRFAndLoginToSite() {
		getDriver().get(regPage.registerSilverRegUserFromRF());
		lbPage.closeOptinLightbox();
		getDriver().navigate().refresh();
	}

	@Step
	public void registerSilverRegFromRF() {
		regPage.registerSilverRegUserFromRF();

	}

	/**
	 * Register the Social reg. user from RF
	 */
	@Step
	public void registerSocialRegFromRFAndLoginToSite() {
		getDriver().get(regPage.registerSocialUserFromRF());
		lbPage.closeOptinLightbox();
		getDriver().navigate().refresh();
	}

	@Step
	public void registerSocialRegFromRF() {
		regPage.registerSocialUserFromRF();

	}
}