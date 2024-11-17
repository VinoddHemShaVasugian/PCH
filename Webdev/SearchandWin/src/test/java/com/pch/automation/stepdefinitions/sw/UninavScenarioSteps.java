package com.pch.automation.stepdefinitions.sw;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.sw.UninavSteps;

import net.thucydides.core.annotations.Steps;

public class UninavScenarioSteps {
	@Steps
	UninavSteps uninavStep;

	@Then("Click on redeem tokens icon")
	public void verifyRedeemTokensFunctionality() {
		uninavStep.onclickRedeemToken();
	}

	@Then("click on levelup icon playnow button")
	public void verifyLevelupPlaynowButtonFunctionality() {
		uninavStep.onclickLevelupicon();
	}

	@Then("verify the infopages")
	public void verifyinfopageNavigation() {
		uninavStep.infoPage();
	}

	@Then("Verify the uninav for guest user")
	public void verifyUninavGuestUser() {
		assertTrue("SignIn or Register button is not displayed.", uninavStep.verifySigninAndRegister());
	}
}