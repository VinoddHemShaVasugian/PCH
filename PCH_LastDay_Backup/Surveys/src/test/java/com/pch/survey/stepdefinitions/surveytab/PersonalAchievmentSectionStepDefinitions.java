package com.pch.survey.stepdefinitions.surveytab;

import org.junit.Assert;

import com.pch.survey.pages.surveytab.LightboxPage;
import com.pch.survey.pages.surveytab.PersonalAchievementsSectionPage;
import com.pch.survey.stepdefinitions.CommonStepDefinitions;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PersonalAchievmentSectionStepDefinitions {

	private PersonalAchievementsSectionPage personalAchievementsSection = new PersonalAchievementsSectionPage(
			WebdriverBuilder.getDriver());
	private LightboxPage lightboxPage = new LightboxPage(WebdriverBuilder.getDriver());

	private static CommonStepDefinitions common = new CommonStepDefinitions();

	@Then("I verify surveys completed count is {string}")
	public void verifySurveyCount(String cnt) {
		lightboxPage.closeBonusGameLB();
		Assert.assertEquals(cnt, personalAchievementsSection.getSurveysCompletedCount());
	}

	@Then("I verify badges received count is {string}")
	public void verifyBadgesReceived(String cnt) {
		Assert.assertEquals(cnt, personalAchievementsSection.getBadgesRecievedCount());
	}

	@Then("I verify badge header text is {string}")
	public void verifyBadgeHeader(String txt) {
//		WebdriverBuilder.getDriver().navigate().refresh(); // Vinoth - Temp fix to check awarded badges.
		lightboxPage.closeBonusGameLB();
		Assert.assertEquals(txt, personalAchievementsSection.getBadgeHeaderText(txt));
	}

	@Then("I verify badge title text is {string}")
	public void verifyBadgeTitle(String txt) {
		Assert.assertEquals(txt, personalAchievementsSection.getBadgeTitleText());
	}

	@Then("I verify badge footer text is {string}")
	public void verifyBadgeFooter(String txt) {
		txt = txt.replace("<FIRSTNAME>", common.getFirstName());
		Assert.assertTrue(personalAchievementsSection.getBadgeFooterText().contains(txt));
	}

	@When("I navigates to Badges page")
	public void navigatesToPersonalAchievementspage() {
		lightboxPage.closeBonusGameLB();
		personalAchievementsSection.clickSeeAllBadgesLink();
	}
}