package com.pch.offers.stepdefinitions;

import com.pch.offers.offersadmin.SurveyEndToolPages;
//import com.pch.survey.pages.accounts.MpoRegistrationPage;
//import com.pch.survey.pages.surveytab.SurveyCompletePage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.stepdefinitions.OffersEventsStepDefinitions;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.When;

public class SurveyEndToolStepDefinitions {

	private static String offersSurveyendToolUrl = ConfigurationReader.getOffersSurveyendToolUrl();

	private SurveyEndToolPages surveyendToolPage = new SurveyEndToolPages();;

	@When("I navigates to survey end tool")
	public void whenNavigatesToSurveyendTool() {
		WebdriverBuilder.getDriver().navigate().to(offersSurveyendToolUrl);
	}

	@When("I generated the survey complete url using survey end tool")
	public void whenGeneratedSurveyCompleteURL() {
		OffersEventsStepDefinitions.surveyCompleteUrl = surveyendToolPage
				.generateSurveyCompleteURL(SurveyMainPage.surveyLandingPageUrl);
	}
}