package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

//import com.pch.kenofrontend.steps.BrowserSteps_old;
import com.pch.kenofrontend.steps.JoomlaAdminSteps;

import net.thucydides.core.annotations.Steps;

public class JoomlaAdminScenarios {

	@Steps
	JoomlaAdminSteps jas;
	ExamplesTable ExamplesTable;
//	BrowserSteps_old browserSteps;
	
	@Given("Go to Keno Admin Page")
	public void givenGoToKenoAdminPage() 
	{
		jas.OpenKenoAdminPage();
				
	}
	
	@When("user logs in with credentials $credentials")
	public void userLogsInWithCredentials(ExamplesTable credentials) 
	{
		
		jas.enterAdminCredentials(credentials);
	}
	
	@When("click on Articles")
	public void clickOnArticles()
	{
		jas.clickArticles();
	}
	
	@When("search for Contest Entries category articles")
	public void searchForContestEntriesCategoryArticles() throws InterruptedException
	{
		jas.selectCategory("Contest Entries");
	}
	
		
	@When("search for Optin Lightbox category articles")
	public void searchForOptinLightboxCategoryArticles() throws InterruptedException
	{
		jas.selectCategory("Optin Lightbox");
	}
	
	@When("search for Daily Token Bonus category articles")
	public void searchForDailyTokenBonusCategoryArticles() throws InterruptedException
	{
		jas.selectCategory("Daily Token Bonus");
	}
	
	
	@When("Search for Contest Entry title article")
	public void searchForContestEntryTitlearticle()
	{
	jas.selecttitle("Contest Entries");
	}
	
	@When("Search for Optin Lightbox title article")
	public void searchForOptinLightboxTitlearticle()
	{
	jas.selecttitle("Optin Lightbox");
	}
		
	@When("Search for Daily Token Bonus title article")
	public void searchFordailyTokenBonusTitlearticle()
	{
	jas.selecttitle("Daily Token Bonus");
	}
	
	@When("read contestkeys configured for Desktop contest entry")
	public void readContestKeyConfiguredForDesktopContestEntry()
	{
		jas.readContestKey();
	}
	
	@When ("read contestmessage configured for Desktop contest entry")
	public void readContestMessageConfiguredForDesktopContestEntry()
	{
		jas.readContestMessage();
	}
	
	@When ("read Optin Tokens configured")
	public void readOptinTokensconfigured()
	{
		jas.readOptinTokens();
	}
	
	@When ("read Daily Token Bonus Tokens configured")
	public void readDailyTokenBonusTokensConfigured()
	{
		jas.readDailyTokenBonusTokens();
	}
	
	
}
