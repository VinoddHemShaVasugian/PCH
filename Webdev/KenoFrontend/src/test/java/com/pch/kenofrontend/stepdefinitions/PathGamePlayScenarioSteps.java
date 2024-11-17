package com.pch.kenofrontend.stepdefinitions;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.sikuli.script.FindFailed;

import com.pch.kenofrontend.steps.CommonSteps;
import com.pch.kenofrontend.steps.HomePageSteps;
import com.pch.kenofrontend.steps.PathGamePlaySteps;
import com.pch.kenofrontend.steps.RegistrationSteps;
import com.pch.kenofrontend.steps.SignInPageSteps;

import net.thucydides.core.annotations.Steps;

public class PathGamePlayScenarioSteps {
	PathGamePlaySteps pathGamePlaySteps;

	@Then("user with password plays SFL game")
	public void useWithPasswordPlaysSFLGame() throws FindFailed, InterruptedException
		{
		pathGamePlaySteps.playPathGameAsUWPwd("SFL");
		}
	
	@Then("user without password plays SFL game")
	public void useWithoutPasswordPlaysSFLGame() throws FindFailed, InterruptedException
	{
		pathGamePlaySteps.playPathGameAsUwoPwd("SFL");
	}
	
	@Then("user without password plays Scratch Card game")
	public void playsScratchCardGame() throws FindFailed, InterruptedException
	{
		pathGamePlaySteps.playPathGameAsUwoPwd("Scratch Card");
	}
	
	@Then("user without password plays few Scratch Card games")
	public void playsAFewScratchCardGames() throws FindFailed, InterruptedException
	{
		pathGamePlaySteps.playFewPathGamesAsUwoPwd("Scratch Card");
	}
}
