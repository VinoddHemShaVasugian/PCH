package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Then;
import org.junit.Assert;

import com.steps.quiz.GOSSteps;
import com.steps.quiz.NavigationSteps;
import com.steps.quiz.QuizSteps;

import net.thucydides.core.annotations.Steps;

public class QuizScenarioSteps {

	@Steps
	NavigationSteps navigationSteps;
	@Steps
	QuizSteps quizSteps;
	@Steps
	GOSSteps gosSteps;

	/**
	 * Play quiz and Verify GOS page
	 */
	@Then("Play quiz and Verify GOS page")
	public void playQuizAndVerify() {
		quizSteps.playQuiz();
		Assert.assertTrue("Video ad on GOS is not displayed", gosSteps.verifyVideoAdGos());
		Assert.assertTrue("Legacy GOS page is not displayed", gosSteps.verifyLegacyGosLayout());
	}
	
	@Then("Answer '$questionNumber' question(s)")
	public void playLimitedQuiz(String questionNumber) {
		quizSteps.playLimitedQuiz(Integer.parseInt(questionNumber));
	}
}