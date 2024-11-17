package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.steps.fp.SocialRegSteps;

import net.thucydides.core.annotations.Steps;

public class SocialRegScenarioSteps {

	@Steps
	SocialRegSteps socialReg;
	HeaderAndUninavPage com;

	@When("Complete registration to become full reg user from social reg user")
	@Then("Complete registration to become full reg user from social reg user")
	public void socialRegtoFullreg() {
		socialReg.fullReg();
	}

	@Then("Verify the presence of the complete reg light box for social user")
	public void verifyPresenceOfCompleteregLb() {
		assertTrue("Optin complete reg light box is failed to display", socialReg.verifyCompleteregLb());
	}

	@Then("Do complete reg from complete reg Lightbox to become fullreg user")
	public void doCompleteRegFromcomregLB() {
		socialReg.CompleteRegLbtofullReg();
	}
}