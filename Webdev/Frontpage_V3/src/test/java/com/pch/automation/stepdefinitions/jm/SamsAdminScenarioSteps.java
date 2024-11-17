package com.pch.automation.stepdefinitions.jm;

import org.jbehave.core.annotations.Given;

import com.pch.automation.steps.jm.SamsAdminSteps;

import net.thucydides.core.annotations.Steps;

public class SamsAdminScenarioSteps {
	
	@Steps
	SamsAdminSteps samsAdmin;
	
	/**
	 * Login to the SAMS Admin
	 */
	@Given("Login to the SAMS Admin")
	public void loginSamsAdmin() {
		samsAdmin.loginSamsAdmin();
	}

}
