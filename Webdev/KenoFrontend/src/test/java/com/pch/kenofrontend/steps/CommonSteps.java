package com.pch.kenofrontend.steps;


import org.sikuli.script.FindFailed;

import com.pch.kenofrontend.pages.UserWithoutPassword;
import com.pch.kenofrontend.pages.HomePage;
import net.thucydides.core.annotations.Step;
/**
 * @author atiwari Sep 4, 2018 
 *
 */
public class CommonSteps {
	
	HomePage homePage;
	UserWithoutPassword cuwop;
	
	@Step
	public void LandAsUserWithoutPassword() 
	{
		cuwop.navigate_user_withoutpassword();
		
	}
			
	@Step
	public void userWoPwd_Lightbox()
	{
		cuwop.sso_Uwopwd_LightboxPresent();
	}
	
	@Step
	public void close_Sso_Uwopwd_Lightbox()
	{
		cuwop.close_Sso_Uwopwd_Lightbox();
	}
	
	@Step
	public void clickOnSkipLink()
	{
		cuwop.forfeitLinkClick();
	}
	
	@Step
	public void clickOnSCForfeitLink()
	{
		cuwop.forfeitLinkSC();
	}
	
	@Step
	public void LandOnGameChoicesafterSubmission() throws InterruptedException
	{
		homePage.LandOnGameChoices();
	}
	
	@Step
	public void choiceSelection(String game) throws Exception
	{
		switch (game)
		{
			case "SFL":
				{
					cuwop.choiceSelection("SFL");
				}
				break;
			case "Scratch Card":
				{
					cuwop.choiceSelection("Scratch Card");
				}
				break;
			default:
				System.out.println("default slots game");
					
		}
	}
	
	
	
	
	@Step
	public void playPathGameAsUwoPwd(String game) throws FindFailed, InterruptedException
	{
		switch (game)
		{
			case "SFL":
				{
					cuwop.playChoiceAsUserWoPwd("SFL");
				}
				break;
			case "Scratch Card":
				{
					cuwop.playChoiceAsUserWoPwd("Scratch Card");
				}
				break;
			default:
				System.out.println("default slots game");
		}
	}
	
	@Step
	public void createPassword()
	{
		cuwop.createPassword();
	}
	
	@Step
	public void verifyTokensForCreatingPassword() throws InterruptedException
	{
		cuwop.verifyThousandTokensForCreatingPassword();
	}

	@Step
	public void playFewPathGamesAsUwoPwd(String game) throws FindFailed, InterruptedException
	{
		switch (game)
		{
			case "SFL":
				{
					cuwop.playFewPathGamesAsUwoPwd("SFL");
				}
				break;
			case "Scratch Card":
				{
					cuwop.playFewPathGamesAsUwoPwd("Scratch Card");
				}
				break;
			default:
				System.out.println("default slots game");
		}
	}

	public void verifyQueuedTokens() throws InterruptedException 
	{
		cuwop.verifyQueuedTokens();
		
	}

	public void entersPasswordOnGOS() throws InterruptedException {
		cuwop.enterPwdOnSCGOS();
		
	}

	public void verifyDailyTokenBonusTokens()
	{
		homePage.verifyDailyTokenBonusTokens();
	}



	
	
}
