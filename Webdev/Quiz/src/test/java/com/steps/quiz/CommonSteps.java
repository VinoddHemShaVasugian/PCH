package com.steps.quiz;

import com.pch.quiz.utilities.CommonLib;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class CommonSteps extends ScenarioSteps {
	private static final long serialVersionUID = 1L;

	CommonLib commonLib;

	@Step
	public void QuizzesAppHealth() {
		commonLib.verifyQuizzesAppHealthPage();
	}
}