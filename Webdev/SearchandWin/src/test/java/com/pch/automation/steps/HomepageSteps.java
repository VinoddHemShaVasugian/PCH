package com.pch.automation.steps;

import com.pch.automation.pages.sw.HomePage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class HomepageSteps extends ScenarioSteps {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The home page. */
	HomePage homePage;

	@Step
	public boolean verifySamsBanner() {
		return homePage.verifySamsBanner();
	}
	@Step
	public boolean verifyDefaultBanner() {
		return homePage.verifyFallbackDefaultBanner();
	}
}