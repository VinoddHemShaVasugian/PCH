package com.pch.kenofrontend.steps;

import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.pages.MyAccountPage;

import net.thucydides.core.annotations.Step;

public class MyAccountPageSteps {
	
	
	MyAccountPage myAccountPage;
	
	@Step
	public void verifyTokensInTokenHistory(ExamplesTable tokenEnrtyData)
	{
		System.out.println("MyAccountPageSteps");
		myAccountPage.verifyTokensInTokenHistory(tokenEnrtyData);
	}

}
