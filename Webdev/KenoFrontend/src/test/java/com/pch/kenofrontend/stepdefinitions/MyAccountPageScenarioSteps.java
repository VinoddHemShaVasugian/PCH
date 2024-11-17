package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.steps.MyAccountPageSteps;

import net.thucydides.core.annotations.Steps;


public class MyAccountPageScenarioSteps {
	
	@Steps
	MyAccountPageSteps myAccountPageSteps;
	
	@Then ("user should be awarded below tokens which is displayed in token history $tokenEnrtyData")
	public void verifytokensForReg(ExamplesTable tokenEnrtyData)
	{
		System.out.println("MyAccountPageScenarioSteps");
		myAccountPageSteps.verifyTokensInTokenHistory(tokenEnrtyData);
	}

}
