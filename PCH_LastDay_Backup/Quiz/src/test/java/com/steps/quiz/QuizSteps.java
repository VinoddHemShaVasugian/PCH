package com.steps.quiz;

import com.pages.quiz.QuizPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class QuizSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	QuizPage quizPage;

	@Step
	public void playQuiz() {
		quizPage.playQuizScrollView();
	}

	@Step
	public boolean verifyQuizPage() {
		return quizPage.verifyQuizPage();
	}

	@Step
	public void playLimitedQuiz(int questionNumber) {
		quizPage.playLimitedQuizScrollView(questionNumber);		
	}

}
