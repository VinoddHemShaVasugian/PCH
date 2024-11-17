package com.pch.kenofrontend.stepdefinitions;

import java.util.List;

import org.jbehave.core.annotations.Then;

import com.pch.kenofrontend.pages.HomePage;
import com.pch.kenofrontend.steps.GameChoicesPageSteps;
import com.pch.kenofrontend.steps.HomePageSteps;

import net.thucydides.core.annotations.Steps;

public class GameChoicesPageScenarioSteps {
	
	@Steps
	HomePageSteps homePageSteps;
		
	@Steps
	GameChoicesPageSteps gamechoicesPageSteps;
	
	// This is to be used when numbers were submitted manually
	@Then("verify correct numbers are submitted as $numberstoselect")
	public void verifyselectednumbers(List<String> numberstoselect) {
		gamechoicesPageSteps.verifynumbers(numberstoselect);
	}
	
	// This is to be used when numbers were submitted via Quick Pick
	@Then ("the gameplay is successful")
	public void verifySubmittedNumbers()
	{
		System.out.println("Print submitted numbers from gameChoicesPageScenarioSteps file");
		System.out.println(HomePage.numbersSubmittedAre);
		
		gamechoicesPageSteps.verifynumbers(HomePage.numbersSubmittedAre);
	}
	
}
