package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.TabHomePage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class TabHomepageSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	HeaderAndUninavPage uninav;
	TabHomePage homePage;

	@Step
	public boolean verifyTabHomePage() {
		return homePage.verifyTabHomePage();
	}
}
