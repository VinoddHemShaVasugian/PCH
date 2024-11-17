package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.MyAccountPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AccountSteps extends ScenarioSteps {
	private static final long serialVersionUID = 1L;

	/** To access My Account page. */
	HeaderAndUninavPage headerAndUninavPage;
	MyAccountPage accountPage;

	@Step
	public int verifyTokenTransactionsDetails(String description, String quizCompleteTokens, int position) {
		return accountPage.verifyTokenTransactionsDetails(description, quizCompleteTokens, position);
	}

	@Step
	public int getExtensionTokenAmount() {
		return headerAndUninavPage.getTokens();
	}
}