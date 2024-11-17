package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.LightboxPage;
import com.pages.quiz.RegistrationPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

public class RegistrationSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	public static String recentURL;

	@Steps
	NavigationSteps navigationSteps;
	
	RegistrationPage regPage;
	HeaderAndUninavPage headerUninavPage;
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
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(regPage.registerFullyRegUserFromRF());
		lbPage.closeOptinLb();
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
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(regPage.registerMiniRegUserFromRF());
		lbPage.closeOptinLb();
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
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(regPage.registerSilverRegUserFromRF());
		lbPage.closeOptinLb();
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
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(regPage.registerSocialUserFromRF());
//		lbPage.closeOptinLb();
		getDriver().navigate().refresh();
	}

	@Step
	public void registerSocialRegFromRF() {
		regPage.registerSocialUserFromRF();
	}

	@Step
	public void clickRegister() {
		headerUninavPage.clickRegister();
	}

	@Step
	public boolean verifyRegistrationPage() {
			return headerUninavPage.verifySignin();
	}
}