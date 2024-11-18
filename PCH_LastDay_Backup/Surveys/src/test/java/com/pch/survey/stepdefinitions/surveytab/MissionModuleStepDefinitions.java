package com.pch.survey.stepdefinitions.surveytab;

import org.junit.Assert;

import com.pch.survey.apis.OfferApis;
import com.pch.survey.pages.surveytab.LightboxPage;
import com.pch.survey.pages.surveytab.MissionModulesPage;
import com.pch.survey.pages.surveytab.SurveyCompletePage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MissionModuleStepDefinitions {

	private String surveyCompleteUrl = null;
	private String[] stepCount;
	private MissionModulesPage missionPage = new MissionModulesPage(WebdriverBuilder.getDriver());
	private SurveyMainPage surveyPage = new SurveyMainPage(WebdriverBuilder.getDriver());
	private SurveyCompletePage surveyCompletePage = new SurveyCompletePage(WebdriverBuilder.getDriver());
	private LightboxPage lbPage = new LightboxPage(WebdriverBuilder.getDriver());

	@When("I expand {string} module")
	public void expandAutoamtionMissionModule(String missionModule) {
		missionPage.expandMissionModule();
	}

	@When("I collapse {string} module")
	public void collapseAutoamtionMissionModule(String missionModule) {
		Assert.assertTrue("Failed to collapse " + missionModule, missionPage.collapseAutoamtionMissionModule());
	}

	@When("I fetch the number of steps configured in {string} module")
	public void getStepsCountMissionModule(String missionModule) {
		stepCount = missionPage.getMissionStepsCount();
	}

	@When("I {string} the mission steps and verify the progress")
	public void completingMissionStepsVerifyProgress(String status) {
		for (int i = 1; i <= Integer.parseInt(stepCount[1]); i++) {
			lbPage.closeBonusGameLB();
			Assert.assertEquals("Failed to navigate mission step" + i, true, missionPage.navigateToMissionStep());
			try {
				surveyPage.waitForSurveypage();
				surveyCompleteUrl = OfferApis.getSurveyEndPointUrl(status);
				WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
				if (status.equalsIgnoreCase("complete")) {
					surveyPage.closePhpdebugbar();
					surveyCompletePage.completeSurvey();
					lbPage.closeBonusGameLB();
				}
				String[] progressBarStatus = missionPage.getMissionStepsCount();
				if (progressBarStatus[0].equalsIgnoreCase("Complete")) {
					break;
				}
				Assert.assertEquals("Progress bar is not updated", i, Integer.parseInt(progressBarStatus[0]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Then("I verify the {string} module {string} status")
	public void verifyMissionModuleCompleteStatus(String missionModule, String status) {
		String[] progressBarStatus = missionPage.getMissionStepsCount();
		Assert.assertTrue("Failed to update mission progress bar for " + missionModule + " completed status.",
				progressBarStatus[0].equalsIgnoreCase(status));
		Assert.assertTrue("Failed to display Congratulations! message for " + missionModule + " mission completion.",
				missionPage.getMissionModuleDescriptionMsg().equalsIgnoreCase("Congratulations!"));
	}

	@Then("I verify the mission module on homepage")
	public void verifyMissionModule() {
		Assert.assertTrue("Failed to display mission module.", missionPage.verifyMissionModule());
	}

	@Then("I verify an info icon on mission module")
	public void verifyInfoIconOnMissionModule() {
		Assert.assertTrue("Failed to display infoIcon on mission module.",
				missionPage.verifyInfoIconOnAutomationMissionModule());
	}

	@Then("I verify the number of mission modules as per admin config {string}")
	public void verifyNumberOfMissionModules(String missionModuleCount) {
		Assert.assertEquals("Progress bar is not updated", missionPage.getMissionModuleCount(),
				Integer.parseInt(missionModuleCount));
	}

	@Then("I verify the UI elements in mission module collapsed state")
	public void verifyUIElementsMissionModuleCollapsedState() {
		Assert.assertTrue("Failed to display mission module.", missionPage.verifyAutomationMissionProgressBar());
		Assert.assertFalse("Failed to hide mission steps.", missionPage.verifyAutomationMissionSteps());
	}

	@Then("I verify the UI elements in mission module expanded state")
	public void verifyUIElementsMissionModuleExpandedState() {
		Assert.assertTrue("Failed to display mission progress bar.", missionPage.verifyAutomationMissionProgressBar());
		Assert.assertTrue("Failed to display mission steps.", missionPage.verifyAutomationMissionSteps());
	}

	@Then("I verify the description on incomplete mission module {string}")
	public void verifyDescriptionIncompleteMissionModulesState(String description) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(description))
			description = ConfigurationReader.getAppConfigProperty().get(description);
		Assert.assertEquals("Failed to display the description on incomplete mission module state.", description,
				missionPage.getMissionModuleDescriptionMsg());
	}

	@Then("I verify the description on complete mission module {string}")
	public void verifyDescriptionCompleteMissionModulesState(String description) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(description))
			description = ConfigurationReader.getAppConfigProperty().get(description);
		Assert.assertTrue("Failed to display the description on complete mission module state.",
				missionPage.getMissionModuleDescriptionMsg().contains(description));
	}

}