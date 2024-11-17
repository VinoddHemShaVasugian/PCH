package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.steps.MyAccountPageSteps;

import net.thucydides.core.annotations.Steps;


public class MyAccountPageScenarioSteps {
	
	@Steps
	MyAccountPageSteps myAccountPageSteps;
	
	@Then ("user should be awarded 1000 tokens which is displayed in token history $tokenEnrtyData")
	public void verify1000tokensForReg(ExamplesTable tokenEnrtyData)
	{
		System.out.println("MyAccountPageScenarioSteps");
		myAccountPageSteps.verifyTokensInTokenHistory(tokenEnrtyData);
	}

}
