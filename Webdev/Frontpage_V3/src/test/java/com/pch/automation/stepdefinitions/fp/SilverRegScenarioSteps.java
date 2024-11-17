package com.pch.automation.stepdefinitions.fp;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.steps.fp.SilverRegSteps;

import net.thucydides.core.annotations.Steps;

public class SilverRegScenarioSteps {

	@Steps
	SilverRegSteps silveruser;
	HeaderAndUninavPage com;

	@When("Do complete registration to become fully registered user from Silver User")
	@Then("Do complete registration to become fully registered user from Silver User")
	public void silverTofullReg() {
		silveruser.fullReg();
	}

	@Then("Do complete registration to become fully registered user from Serp page")
	public void silverTofullRegFromSerp() {
		silveruser.fullRegFromCompleteRegLightbox();
	}
}