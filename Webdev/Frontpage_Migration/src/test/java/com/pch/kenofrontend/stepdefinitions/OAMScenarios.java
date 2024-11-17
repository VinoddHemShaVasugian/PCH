package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.steps.OAMSteps;

import net.thucydides.core.annotations.Steps;

public class OAMScenarios {

	@Steps
	OAMSteps oams; 
	
	@Given("User Logs into OAM page")
	public void userLogsIntoOAMPage() 
	{
		oams.loginOAM();
	}
	
	@Given("clicks on Search Menu")
	public void clicksOnSearchMenu()
	{
		oams.clickSearchMenu();
	}
	
	@Given("clicks on Legacy Contest Entry Tab")
	public void clicksOnLegacyContestEntryTab()
	{
		oams.clickLegacyContestEntryTab();
	}
	
	@Given("clicks on Subscriptions Events Tab")
	public void clicksOnSubscriptionsEventTab()
	{
		oams.clicksOnSubscriptionsEventTab();
	}
	
	@When("user enters user without password email from previous testcase")
	public void userEntersUserWithoutPasswordEmail() throws InterruptedException
	{
		oams.enterUserWithoutPwdEmail();
	}
	
	@When("user enters user with password email from previous testcase")
	public void userEntersUserWithPasswordEmail() throws InterruptedException
	{
		oams.enterUserWithPwdEmail();
	}
	
	
	@Then("records with configured contest keys are found")
	public void sameContestKeyRecord() throws InterruptedException
	{
		oams.verifyContestEntryRecord();
	}

	@Then("record with Keno optin is found")
	public void recordWithKenoOptinIsFound() throws InterruptedException
	{
		oams.verifyKenoOptinRecord();
	}
	
	@Then ("the optins should be properly captured under OAM tools")
	public void verifyOptinsPreReg()
	{
		oams.verifyOptinsPreReg();
	}
}
