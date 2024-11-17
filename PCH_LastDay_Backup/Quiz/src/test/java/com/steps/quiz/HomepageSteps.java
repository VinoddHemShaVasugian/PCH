package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.HomePage;
import com.pages.quiz.SignInPage;
import com.pch.quiz.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class HomepageSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private AppConfigLoader configInstance = AppConfigLoader.getInstance();

	HeaderAndUninavPage uninav;
	HomePage homePage;
	SignInPage signIn;

	@Step
	public void navigateToTrendingQuiz() {
		homePage.clickTrendingQuiz();
	}

	@Step
	public boolean verifySiteHomePage() {
		return homePage.verifySiteHomePage();
	}

	@Step
	public boolean verifySignInButton() {
		return uninav.verifySignin();
	}

	@Step
	public boolean verifyRegistrationButton() {
		return uninav.verifyRegister();
	}

	@Step
	public void clickSignIn() {
		uninav.clickSignIn();
	}

	@Step
	public boolean verifySignInPage() {
		return uninav.verifyRegister();
	}

	@Step
	public void signIn() {
		signIn.login(configInstance.getEnvironmentProperty("UserName"),
				configInstance.getEnvironmentProperty("Password"));
	}

	@Step
	public void verifyRedeemToken() {
		uninav.verifyRedeemTokensIcon();
	}

	@Step
	public boolean verifyPlaynowButton() {
		return uninav.verifyPlaynowButton();
	}

	@Step
	public boolean openLevelUpShelf() {
		return uninav.openLevelUpShelf();
	}

	@Step
	public boolean verifyInfoPages() {
		if (uninav.verifySignout() && uninav.verifyHome() && uninav.verifyTokenHistory() && uninav.verifyPrivacyPolicy()
				&& uninav.verifyOfficialRules() && uninav.verifySweepsFact()) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyRedeemTokenShelf() {
		return uninav.verifyDropDownReedemTokenButtonVisible();
	}
	
	@Step
	public boolean verifyLogo() {
		return uninav.verifyLogo();
	}

	@Step
	public void navigateToCategoryPages(String category) {
		homePage.navigateToCategoryPage(category);	
	}
}