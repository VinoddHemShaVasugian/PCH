package com.pch.kenofrontend.steps;

import org.sikuli.script.FindFailed;
import com.pch.kenofrontend.pages.UserWithoutPassword;
import com.pch.kenofrontend.pages.GameChoicesPage;
import com.pch.kenofrontend.pages.HomePage;
import com.pch.kenofrontend.pages.PathGameplay;
import com.pch.kenofrontend.pages.RegistrationPage;
import org.openqa.selenium.WebDriver;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Step;

public class PathGamePlaySteps extends PageObject{

	PathGameplay pathGamePlay;
	
	
	@Step
	public void playPathGameAsUWPwd(String game) throws FindFailed, InterruptedException {
		{
			switch (game)
			{
				case "SFL":
					{
						pathGamePlay.playChoiceAsUserWPwd("SFL");
					}
					break;
				case "Scratch Card":
					{
						pathGamePlay.playChoiceAsUserWPwd("Scratch Card");
					}
					break;
				default:
					System.out.println("default slots game");
			}
		}
		
	}
	
	@Step
	public void playPathGameAsUwoPwd(String game) throws FindFailed, InterruptedException
	{
		switch (game)
		{
			case "SFL":
				{
					pathGamePlay.playChoiceAsUserWoPwd("SFL");
				}
				break;
			case "Scratch Card":
				{
					pathGamePlay.playChoiceAsUserWoPwd("Scratch Card");
				}
				break;
			default:
				System.out.println("default slots game");
		}
	}
	
	@Step
	public void playFewPathGamesAsUwoPwd(String game) throws FindFailed, InterruptedException
	{
		switch (game)
		{
			case "SFL":
				{
					pathGamePlay.playFewPathGamesAsUwoPwd("SFL");
				}
				break;
			case "Scratch Card":
				{
					pathGamePlay.playFewPathGamesAsUwoPwd("Scratch Card");
				}
				break;
			default:
				System.out.println("default slots game");
		}
	}
}
