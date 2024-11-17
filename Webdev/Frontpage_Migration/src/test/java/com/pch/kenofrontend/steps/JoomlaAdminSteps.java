package com.pch.kenofrontend.steps;

import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.pages.JoomlaAdminPage;
import com.pch.kenofrontend.utilities.PropertiesReader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class JoomlaAdminSteps {
	JoomlaAdminPage jap;
	
//	@Steps
//	BrowserSteps_old browserSteps;
	
	@Step
	public void OpenKenoAdminPage()
	{
		jap.OpenKenoAdminPage();
				
    }
	
	
	@Step
	public void enterAdminCredentials(ExamplesTable credentials)
	{
		jap.login(credentials);
		
    }
	
	@Step
	public void clickArticles()
	{
		jap.clickArticles();
	}
	
	@Step
	public void selectCategory(String category) throws InterruptedException
	{
		switch (category)
		{
			case "Contest Entries":
				{
					jap.selectCategory("Contest Entries");
				}
				break;
			case "Optin Lightbox":
				{
					jap.selectCategory("Optin Lightbox");
				}
				break;
			case "Daily Token Bonus":
			{
				jap.selectCategory("Daily Token Bonus");
			}
			break;
					
			default:
				System.out.println("no category selected");
		}
	}
	
	@Step
	public void selecttitle(String title)
	{
		switch (title)
		{
			case "Contest Entries":
				{
					jap.selecttitle("Contest Entries");
				}
				break;
			case "Optin Lightbox":
				{
					jap.selecttitle("Optin Lightbox");
				}
				break;
			case "Daily Token Bonus":
			{
				jap.selecttitle("Daily Token Bonus");
			}
			break;
			default:
				System.out.println("no title selected");
		}
		
	}
	
		
	@Step
	public void readContestKey()
	{
		jap.readContestKey();
	}
	
	@Step
	public void readContestMessage()
	{
		jap.readContestMessage();
	}
	
	@Step
	public void readOptinTokens()
	{
		jap.readOptinTokens();
	}


	public void readDailyTokenBonusTokens() 
	{
		jap.readDailyBonusTokens();
		
	}
}
