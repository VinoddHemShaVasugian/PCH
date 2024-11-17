package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.steps.fp.MiniRegSteps;

import net.thucydides.core.annotations.Steps;

public class MiniRegScenarioSteps {

	@Steps
	MiniRegSteps mini;

	@When("Do complete registration to become full reg user from Mini Reg user")
	@Then("Do complete registration to become full reg user from Mini Reg user")
	public void fullRegsuccessfull() {
		mini.miniRegToFullReg();
	}

	@Then("Verify the presence of the complete reg light box")
	public void verifyPresenceOfCompleteregLb() {
		assertTrue("Optin complete reg light box is failed to display", mini.verifyCompleteregLb());
	}

	@Then("Do complete reg from complete reg Lightbox")
	public void doCompleteRegFromcomregLB() {
		mini.CompleteRegLbtofullReg();
	}
}