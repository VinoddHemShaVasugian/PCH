package com.steps.quiz;

import com.pages.quiz.CategoryPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class CategorySteps extends ScenarioSteps {
	private static final long serialVersionUID = 1L;

	CategoryPage categoryPage;

	@Step
	public boolean verifyCategoryPages(String category) {
		return categoryPage.verifyCategoryPage(category);
	}

}