package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.steps.quiz.AdSteps;

import net.thucydides.core.annotations.Steps;

public class AdScenarioSteps {

	@Steps
	AdSteps adSteps;

	@Then("Verify right rail multiple ad '$width1','$height1','$width2','$height2','$width3','$height3'")
	public void verifyRightRailMultipleAd(String width1, String height1, String width2, String height2, String width3,
			String height3) {
		Assert.assertTrue("Right Rail multiple Ad is not displayed.",
				adSteps.verifyRightRailMultipleAd(width1, height1, width2, height2, width3, height3));
	}

	@Then("Verify right rail ad '$width','$height'")
	public void verifyRightRailAd(String width, String height) {
		Assert.assertTrue("Right Rail Ad is not displayed.", adSteps.verifyRightRailAd(width, height));
	}

	@When("Verify left rail ad '$width','$height'")
	@Then("Verify left rail ad '$width','$height'")
	public void verifyLeftRailAd(String width, String height) {
		Assert.assertTrue("Left Rail Ad is not displayed.", adSteps.verifyLeftRailAd(width, height));
	}

	@Then("Verify inline ad '$width','$height'")
	public void verifyInlineAd(String width, String height) {
		Assert.assertTrue("Inline Ad is not displayed.", adSteps.verifyInlineAd(width, height));
	}

	@Then("Verify inline ad in category page '$width','$height'")
	public void verifyInlineAdCategoryPage(String width, String height) {
		Assert.assertTrue("Inline Ad is not displayed.", adSteps.verifyInlineAdCategoryPage(width, height));
	}

	@Then("Verify right rail bottom ad '$width','$height'")
	public void verifyRightRailBottomAd(String width, String height) {
		Assert.assertTrue("Right Rail Bottom Ad is not displayed.", adSteps.verifyBottomAd(width, height));
	}

	@Then("Verify playbuzz right rail ad")
	public void verifyPlaybuzzStickyRightRailAd() {
		Assert.assertTrue("Playbuzz right rail ad is not displayed.", adSteps.verifyPlaybuzzStickyRightRailAd());
		adSteps.closePlaybuzzStickyRightRailAd();
	}

	@Then("Verify playbuzz bottom ad")
	public void verifyPlaybuzzStickyBottomAd() {
		Assert.assertTrue("Playbuzz bottom ad is not displayed.", adSteps.verifyPlaybuzzStickyBottomAd());
	}

	@Then("Play quiz and verify inline ad '$width','$height'")
	public void verifyInlineTileAd(String width, String height) {
		Assert.assertTrue("Inline Tile Ad is not displayed between questions.",
				adSteps.verifyInlineAdBetweenQuestions(width, height));
	}

	@Then("Verify page tagged status at adFrame '$adFrame'")
	public void verifyPageTaggingStatus(String adFrame) {
		Assert.assertTrue("Page tagged incorrectly.", adSteps.verifyPageTaggingStatus(Integer.parseInt(adFrame)));
	}
}