package com.pch.automation.steps.jm;

import com.pch.automation.pages.jm.LoginPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class SamsAdminSteps extends ScenarioSteps {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LoginPage loginPage;
	
	@Step
	public void loginSamsAdmin() {
		loginPage.loginSamsAdmin();
	}

}
