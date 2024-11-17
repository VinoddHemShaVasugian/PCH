package com.pch.survey.stepdefinitions;

import com.pch.survey.apis.OfferApis;
import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.pages.surveytab.SurveyCompletePage;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.When;

public class OffersApiSurveyEndStepDefinitions {

	private String surveyCompleteUrl = "";
	private String surveyEndStatus = "";
	private SurveyCompletePage surveyCompletePage = new SurveyCompletePage();;
	private MpoRegistrationPage mpoRegPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());

	@When("I generate the survey endpoint url with {string} status")
	public void whenGeneratedSurveyURL(String status) {
		surveyEndStatus = status;
		surveyCompleteUrl = OfferApis.getSurveyEndPointUrl(status);
	}

	@When("I call the survey endpoint url")
	public void whenCompleteSurveyUsingCompleteURl() {
		WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
		if (WebdriverBuilder.getDriver().getCurrentUrl().contains("https://mpo.qa.pch.com/")) {
			mpoRegPage.CreateFullRegUserForm();
		}
		if (surveyEndStatus.equalsIgnoreCase("complete"))
			surveyCompletePage.completeSurvey();
	}

	@When("I completed the survey using survey complete url by creating password for NoPassword user")
	public void whenCompleteSurveyUsingCompleteURlForNoPwdUser() {
		WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
		surveyCompletePage.completeSurveyForSilverUser();
	}

	@When("I completed the survey using survey complete url without creating password for NoPassword user")
	public void whenCompleteSurveyUsingCompleteURlAndSkipForNoPasswordUser() {
		WebdriverBuilder.getDriver().navigate().to(surveyCompleteUrl);
		surveyCompletePage.completeSurveyAndSkipForSilverUser();
	}
}