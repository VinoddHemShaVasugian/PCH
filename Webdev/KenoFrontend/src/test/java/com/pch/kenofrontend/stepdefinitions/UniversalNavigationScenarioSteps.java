package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Then;
import com.pch.kenofrontend.steps.UniversalNavigationSteps;
import net.thucydides.core.annotations.Steps;

public class UniversalNavigationScenarioSteps {
	
	@Steps
	UniversalNavigationSteps UnivNavSteps;
	
	
	@Then("Universal Nav Bar is displayed on the site")
	public void thenUnivNavDisplayed() {
		UnivNavSteps.univnav();
	}

	@Then("individual tabs should be clickable for navigating onto other sites")
	public void validateUnivNavTabs(){
		UnivNavSteps.tabsinunivnav();	
	}
	
	@Then ("user is able to scroll right and left onto the Uni Nav Bar")
	public void validateScrolling(){
		UnivNavSteps.scrollunivnav();
	}
}
