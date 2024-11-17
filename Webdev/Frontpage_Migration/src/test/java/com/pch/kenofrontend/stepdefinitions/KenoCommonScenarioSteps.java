package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.sikuli.script.FindFailed;

import com.pch.kenofrontend.steps.BrowserSteps_old;
import com.pch.kenofrontend.steps.CommonSteps;
import com.pch.kenofrontend.utilities.PropertiesReader;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Steps;


/**
 * @author atiwari 09/24/2018
 *
 */
public class KenoCommonScenarioSteps extends PageObject{
	
//	@Steps
//	BrowserSteps_old browserSteps;
	
	@Steps
	CommonSteps commonSteps;
	
	@Given("Go to FrontPage Home Page")
	public void givenGoToFrontpageHomePage() 
	{
//		browserSteps.OpenKenoHomePage();
		getDriver().manage().window().maximize();
		getDriver().get(PropertiesReader.getInstance().FPBaseUrl);
	}	
	
	@Given("Go to Keno Home Page")
	public void givenGoToKenoHomePage() 
	{
//		browserSteps.OpenKenoHomePage();
		getDriver().manage().window().maximize();
		getDriver().get(PropertiesReader.getInstance().kenoUrl);
	}
	
	
	@Given("user without password is logged into keno")
	public void givenGotoKenoHomePageAsNoPasswordUser()
	{
		commonSteps.LandAsUserWithoutPassword();
	}
		
	
	@Then("Create password lightbox should appear")
	public void createPasswordLightbox()
	{
		commonSteps.userWoPwd_Lightbox();
	} 
	
	@Then("clicking on x button should close the lightbox")
	public void clickUwopwdLightbox()
	{
	commonSteps.close_Sso_Uwopwd_Lightbox();
	}
	
	
	@Then("clicks on forfeit tokens link")
	public void ClickonSkipLink()
	{
		commonSteps.clickOnSkipLink();
	}
	
	@Then("forfeits tokens on Game Over Screens on SC")
	public void forfeitsTokensOnSC()
	{
		commonSteps.clickOnSCForfeitLink();
	}
	
	@Then("user without password selects a SFL choice")
	public void selectsChoicePlaysSFL() throws Exception
	{
		//commonSteps.playSFLAsUwoPwd();
		commonSteps.choiceSelection("SFL");
	}
	
	@Then("user without password selects a Scratch Card choice")
	public void selectsChoicePlaysScratchCard() throws Exception
	{
		//commonSteps.playSFLAsUwoPwd();
		commonSteps.choiceSelection("Scratch Card");
	}
	
	@Then("user without password plays few Scratch Card games")
	public void playsAFewScratchCardGames() throws FindFailed, InterruptedException
	{
		commonSteps.playFewPathGamesAsUwoPwd("Scratch Card");
	}
	
	@Then("enters password on GOS")
	public void entersPasswordOnGOS() throws InterruptedException
	{
		commonSteps.entersPasswordOnGOS();
	}
	
	@Then("user creates password on the lightbox")
	public void createsPasswordOnTheLightbox()
	{
		commonSteps.createPassword();
	}
	
	@Then("user without password plays SFL game")
	public void useWithoutPasswordPlaysSFLGame() throws FindFailed, InterruptedException
	{
		commonSteps.playPathGameAsUwoPwd("SFL");
	}
	
	@Then("user without password plays Scratch Card game")
	public void playsScratchCardGame() throws FindFailed, InterruptedException
	{
		commonSteps.playPathGameAsUwoPwd("Scratch Card");
	}
	
	@Then("Registration tokens displays in Token History")
	public void registrationTokensDisplaysInTokenHistory() throws InterruptedException
	{
		commonSteps.verifyTokensForCreatingPassword();
	}
	
	@Then("user gets Path games queued tokens")
	public void userGetsPathGamesQueuedTokens() throws InterruptedException
		{
		commonSteps.verifyQueuedTokens();
		}

	@Then("verify Daily Token Bonus module and Joomla admin have same tokens for visit")
	public void verifyDailyTokenBonusModule()
	{
		commonSteps.verifyDailyTokenBonusTokens();
	}
}
	


