package com.pch.automation.steps.fp;

import com.pch.automation.pages.fp.HomePage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class HoroscopeSteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	HomePage homePage;

	@Step
	public void clickHoroscopeMenu() {
		homePage.clickHoroscopeMenu();
	}

	@Step
	public void claimTokens() {
		homePage.clickClaimButton();
	}
}