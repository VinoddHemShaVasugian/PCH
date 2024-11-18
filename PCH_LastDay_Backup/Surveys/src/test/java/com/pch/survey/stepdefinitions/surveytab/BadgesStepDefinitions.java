package com.pch.survey.stepdefinitions.surveytab;

import org.junit.Assert;

import com.pch.survey.pages.surveytab.BadgesPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BadgesStepDefinitions {

	private BadgesPage badgesPage = new BadgesPage(WebdriverBuilder.getDriver());

	@When("I click on Back to Surveys link")
	public void clickBackToSurveysLink() {
		badgesPage.backToSurveysLink();
	}

	@When("I click on Program Terms link")
	public void clickProgramTermsLink() {
		badgesPage.programTermsLink();
	}

	@Then("I verify {string} badge is unlocked")
	public void verifyBadgeUnlockedStatus(String badgeName) {
		switch (badgeName) {
		case "Influencer":
			Assert.assertTrue("Failed to displayed " + badgeName + " active badge on badges page.",
					badgesPage.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerActive()));
			break;
		case "Influencer Pro":
			Assert.assertTrue("Failed to displayed " + badgeName + " active badge on badges page.", badgesPage
					.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerProActive()));
			break;
		case "Influencer Pro Plus":
			Assert.assertTrue("Failed to displayed " + badgeName + " active badge on badges page.", badgesPage
					.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerProPlusActive()));
			break;
		case "Token Titan":
			Assert.assertTrue("Failed to displayed " + badgeName + " active badge on badges page.",
					badgesPage.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getTokenTitanActive()));
			break;
		case "Token Titan Gold":
			Assert.assertTrue("Failed to displayed " + badgeName + " Gold badge on badges page.",
					badgesPage.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getTokenTitanGold()));
			break;
		default:
			break;
		}

	}

	@Then("I verify {string} badge is locked")
	public void verifyBadgeLockedStatus(String badgeName) {
		switch (badgeName) {
		case "Influencer":
			Assert.assertTrue("Failed to displayed " + badgeName + " inactive badge on badges page.",
					badgesPage.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerLocked()));
			break;
		case "Influencer Pro":
			Assert.assertTrue("Failed to displayed " + badgeName + " inactive badge on badges page.", badgesPage
					.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerProLocked()));
			break;
		case "Influencer Pro Plus":
			Assert.assertTrue("Failed to displayed " + badgeName + " inactive badge on badges page.", badgesPage
					.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerProPlusLocked()));
			break;
		case "Token Titan":
			Assert.assertTrue("Failed to displayed " + badgeName + " inactive badge on badges page.",
					badgesPage.getBadgeImgSrc(badgeName).equalsIgnoreCase(ConfigurationReader.getTokenTitanLocked()));
			break;
		default:
			Assert.assertTrue(false);
			break;
		}

	}

	@Then("I verify info text on {string} badge")
	public void verifyBadgesInfoText(String badgeName) {
		switch (badgeName) {
		case "Influencer":
			badgesPage.clickInfoIcon(badgeName);
			Assert.assertTrue("Failed to displayed info text on " + badgeName + " in badges page.", badgesPage
					.getBadgeInfoText(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerInfoText()));
			break;
		case "Influencer Pro":
			badgesPage.clickInfoIcon(badgeName);
			Assert.assertTrue("Failed to displayed info text on " + badgeName + " in badges page.", badgesPage
					.getBadgeInfoText(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerProInfoText()));
			break;
		case "Influencer Pro Plus":
			badgesPage.clickInfoIcon(badgeName);
			Assert.assertTrue("Failed to displayed info text on " + badgeName + " in badges page.", badgesPage
					.getBadgeInfoText(badgeName).equalsIgnoreCase(ConfigurationReader.getInfluencerProPlusInfoText()));
			break;
		default:
			Assert.assertTrue(false);
			break;
		}

	}

}