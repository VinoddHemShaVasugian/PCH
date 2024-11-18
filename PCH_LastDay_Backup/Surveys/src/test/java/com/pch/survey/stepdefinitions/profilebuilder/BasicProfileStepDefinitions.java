package com.pch.survey.stepdefinitions.profilebuilder;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.pch.survey.apis.ProfileQuestionAnswersApi;
import com.pch.survey.pages.accounts.CreatePasswordPage;
import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.pages.profiles.BasicProfilePage;
import com.pch.survey.pages.profiles.LucidConsentFormPage;
import com.pch.survey.stepdefinitions.CommonStepDefinitions;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.database.RedisClient;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BasicProfileStepDefinitions {

	private static String env = ConfigurationReader.getEnvironment().toLowerCase();

	private BasicProfilePage profilePage = new BasicProfilePage(WebdriverBuilder.getDriver());
	private LucidConsentFormPage consentPage = new LucidConsentFormPage(WebdriverBuilder.getDriver());
	private CreatePasswordPage createPasswordPage = new CreatePasswordPage(WebdriverBuilder.getDriver());
	private MpoRegistrationPage mpoRegistrationPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());

	@When("I answer all basic profile questions")
	public void answerBasicProfileQuestions() {
		profilePage.answerAllProfileQuestions();
	}

	@When("I answer all basic profile questions as a {string} user")
	public void answerBasicProfileQuestions(String userType) {
		if (userType.equalsIgnoreCase("silver")) {
			profilePage.answerAllProfileQuestions();
			createPasswordPage.completeSurveyForSilverUser();
		} else if (userType.equalsIgnoreCase("guest")) {
			profilePage.answerAllProfileQuestions();
			if (WebdriverBuilder.getDriver().getCurrentUrl().contains("https://mpo." + env + ".pch.com/")) {
				mpoRegistrationPage.CreateFullRegUserForm();
			}
		} else {
			profilePage.answerAllProfileQuestions();
		}
	}

	@When("I answer only non-sensitive questions as a {string} user")
	public void answerNonSensitiveQuestions(String userType) {
		if (userType.equalsIgnoreCase("silver")) {
			profilePage.answerNonSensitiveQuestions();
			createPasswordPage.completeSurveyForSilverUser();
		} else if (userType.equalsIgnoreCase("guest")) {
			profilePage.answerNonSensitiveQuestions();
			mpoRegistrationPage.CreateFullRegUserForm();
		} else {
			profilePage.answerNonSensitiveQuestions();
		}
	}

	@When("I answer the current basic profile question")
	public void answerBasicProfileQuestion() {
		profilePage.answerCurrentProfileQuestion();
	}

	@Then("I verify The question completed count is {string}")
	public void thenVerifyCount(String cnt) {
		Assert.assertEquals("Verify current question number", cnt, profilePage.verifyAnsweredQuestionCount());
	}

	@Then("I verify question count {int} matches in Redis Database")
	public void thenQuestionCountInDb(int cnt) {
		String key = "s:dp:" + new CommonStepDefinitions().getGmt() + ":q";
		System.out.println(key);

		Map<String, String> questions = RedisClient.getResultAsMap(key);
		Assert.assertEquals("Answered profile questionsr", cnt, RedisClient.getResultAsMap(key).size());
	}

	@When("I insert all basic profile questions and answers into Redis DB")
	public void insertIntoRedis() {
		String gmt = new CommonStepDefinitions().getGmt();

		Map<String, String> questionMap = ProfileQuestionAnswersApi.getProfileQuestionsAndAnswers();
		RedisClient.insertData("s:dp:" + gmt + ":q", questionMap);
		RedisClient.insertData("s:pb:" + gmt + ":q", questionMap);

	}

	@Then("I verify basic profile questions and answers exist in Redis Database$")
	public void verifyAlQuestionsAndAnswers(DataTable table) {
		String searchKey = "s:dp:" + new CommonStepDefinitions().getGmt() + ":q";
		Map<String, String> actualQuestionsAndAnswersMap = RedisClient.getResultAsMap(searchKey);
		List<List<String>> expectedQuestionsAndAnswersList = table.asLists();

		Assert.assertEquals("Verify actual saved questions equal expected", expectedQuestionsAndAnswersList.size() - 1,
				actualQuestionsAndAnswersMap.size());

		for (int i = 1; i < expectedQuestionsAndAnswersList.size(); i++) {
			List<String> questionAnswerPair = expectedQuestionsAndAnswersList.get(i);
			String question = questionAnswerPair.get(0);
			String answer = questionAnswerPair.get(1);
			if (actualQuestionsAndAnswersMap.get(question) != null)
				Assert.assertEquals("Verify answer to question " + question, answer,
						actualQuestionsAndAnswersMap.get(question));
			else
				Assert.fail("Question " + question + " not found in Redis");
		}
	}

	@When("I click No button for Data Collection Consent")
	public void clickNoButton() {
		profilePage.clickNoButton();
	}

	@When("I agree to Lucent Consent form")
	public void agreeToConsent() {
		consentPage.agreeLucentConsentForm();
	}
}