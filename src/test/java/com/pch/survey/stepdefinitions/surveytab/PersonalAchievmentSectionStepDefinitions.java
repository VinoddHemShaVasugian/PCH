package com.pch.survey.stepdefinitions.surveytab;

import org.junit.Assert;

import com.pch.survey.pages.surveytab.PersonalAchievementsSectionPage;
import com.pch.survey.stepdefinitions.CommonStepDefinitions;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PersonalAchievmentSectionStepDefinitions {

	private PersonalAchievementsSectionPage badgesPage = new PersonalAchievementsSectionPage(
			WebdriverBuilder.getDriver());
	private static CommonStepDefinitions common = new CommonStepDefinitions();

	@Then("I verify surveys completed count is {string}")
	public void verifySurveyCount(String cnt) {
		Assert.assertEquals(cnt, badgesPage.getSurveysCompletedCount());
	}

	@Then("I verify badges received count is {string}")
	public void verifyBadgesReceived(String cnt) {
		Assert.assertEquals(cnt, badgesPage.getBadgesRecievedCount());
	}

	@Then("I verify badge header text is {string}")
	public void verifyBadgeHeader(String txt) {
		Assert.assertEquals(txt, badgesPage.getBadgeHeaderText(txt));
	}

	@Then("I verify badge title text is {string}")
	public void verifyBadgeTitle(String txt) {
		Assert.assertEquals(txt, badgesPage.getBadgeTitleText());
	}

	@Then("I verify badge footer text is {string}")
	public void verifyBadgeFooter(String txt) {
		txt = txt.replace("<FIRSTNAME>", common.getFirstName());
		Assert.assertTrue(badgesPage.getBadgeFooterText().contains(txt));
	}

	@Then("I navigates to Personal Achievements page")
	public void navigatesToPersonalAchievementspage() {
		badgesPage.clickSeeAllBadgesLink();
	}
	
	@When("I click on Back to Surveys link")
    public void i_click_on_back_to_surveys_link() {
		badgesPage.backToSurveysLink();
    }
	

    @When("I click on Program Terms link")
    public void i_click_on_program_terms_link()  {
    	badgesPage.programTermsLink();
    }
	
	

}