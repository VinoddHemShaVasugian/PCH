package com.pch.automation.stepdefinitions.fp;

import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.fp.RewardsUninavSteps;

import net.thucydides.core.annotations.Steps;

public class RewardsUninavScenarioSteps {
	@Steps
	RewardsUninavSteps rewardstep;

	@Then("Click on redeem tokens icon")
	public void verifyRedeemTokensFunctionality() {
		rewardstep.onclickRedeemToken();
	}

	@Then("click on levelup icon playnow button")
	public void verifyLevelupPlaynowButtonFunctionality() {
		rewardstep.onclickLevelupicon();
	}
}