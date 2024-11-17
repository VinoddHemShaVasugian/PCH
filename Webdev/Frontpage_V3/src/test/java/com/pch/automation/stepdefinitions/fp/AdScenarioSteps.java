package com.pch.automation.stepdefinitions.fp;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.ArticlesSteps;
import com.pch.automation.steps.fp.AdSteps;

import net.thucydides.core.annotations.Steps;

public class AdScenarioSteps {

	@Steps
	AdSteps adSteps;
	@Steps
	ArticlesSteps articleSteps;

	@Then("Verify right rail multiple ad '$width1','$height1','$width2','$height2'")
	public void verifyRightRailMultipleAd(String width1, String height1, String width2, String height2) {
		Assert.assertTrue("Right Rail multiple Ad is not displayed.",
				adSteps.verifyRightRailMultipleAd(width1, height1, width2, height2));
	}

	@When("Verify right rail ad '$width','$height'")
	@Then("Verify right rail ad '$width','$height'")
	public void verifyRightRailAd(String width, String height) {
		Assert.assertTrue("Right Rail Ad is not displayed.", adSteps.verifyRightRailAd(width, height));
	}

	@Then("Verify inline ad '$width','$height'")
	public void verifyInlineAd(String width, String height) {
		Assert.assertTrue("Inline Ad is not displayed.", adSteps.verifyInlineAd(width, height));
	}
	
	@Then("Verify inline tile ad '$width','$height'")
	public void verifyInlineTileAd(String width, String height) {
		Assert.assertTrue("Inline Tile Ad is not displayed.", adSteps.verifyInlineTileAd(width, height));
	}

	@Then("Verify ad on Top stories '$width','$height'")
	public void verifyTopStoriesAd(String width, String height) {
		Assert.assertTrue("Top stories ad is not displayed.", adSteps.verifyTopStoriesAd(width, height));
	}

	@Then("Verify Trending now ad '$width','$height'")
	public void verifyTrendingNowAd(String width, String height) {
		Assert.assertTrue("Trending now ad is not displayed.", adSteps.verifyTrendingNowAd(width, height));
	}

	@Then("Verify ourpicks ad '$width','$height'")
	public void verifyOurpicksAd(String width, String height) {
		Assert.assertTrue("Ourpicks Ad is not displayed.", adSteps.verifyOurpicksAd(width, height));
	}

	@Then("Verify sponsored ads '$width','$height'")
	public void verifySponsoredAd(String width, String height) {
		Assert.assertTrue("Sponsored Ads is not displayed.", adSteps.verifySponsoredAd(width, height));
	}

	@Then("Verify bottom ad under article section '$width','$height'")
	public void verifyBottomAd(String width, String height) {
		Assert.assertTrue("Sponsored Ads is not displayed.", adSteps.verifyBottomAd(width, height));
	}

	@Then("Verify the presence of interstitial ads")
	public void verifyInterstitialad() {
		articleSteps.navigateToArticlePage();
		articleSteps.clickNextArticle();
		articleSteps.clickNextArticle();
		articleSteps.clickNextArticle();
		adSteps.clickNextArticleOnInterstitialAd();
	}
}