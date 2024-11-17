package com.pch.sw.tests.misc;

import org.testng.annotations.Test;

import com.pch.search.iwe.AllPlaysPage;
import com.pch.search.iwe.DevicePage;
import com.pch.search.iwe.GiveawayPage;
import com.pch.search.iwe.IWEBasePage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.WinnersLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebFooterPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SeperateContentPages extends BaseTest {
	IWEBasePage iweBasePage; //new IWEBasePage();
	DevicePage devicePage;
	GiveawayPage giveawayPage;
	AllPlaysPage allPlaysPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	WebFooterPage footerPage;
	WinnersLightBox winnersLightBox;
	CentralServicesPage centralServicesPage;
	AdminBasePage joomlaPage;
	SearchResultsPage serp;
	User randomUser_1, randomUser_2, randomUser_3, randomUser_4;
	
	@Test(groups={GroupNames.Mobile})
	public void aboutSearchWin()
	{
		//Verify by changing text on joomla for about pchsearch and it reflects on site.
		joomlaPage.goToArticle("About PCHSearch&Win");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("About PCHSearch&Win"," Single search"));
		CustomLogger.log("replacing About PCHSearch&Win with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    homePage.hamburgerMenu().click();
   	    homePage.aboutPCHSearchWin().click();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("About PCHSearch&Win");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
		}
	@Test(groups={GroupNames.Mobile})
	public void howToSearch()
	{
		joomlaPage.goToArticle("How To Search");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("How To Search"," Single search"));
		CustomLogger.log("replacing About How To Search with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    homePage.hamburgerMenu().click();
   	    homePage.howToSearch().click();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("How To Search");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
	}
	@Test(groups={GroupNames.Mobile})
	public void doAndDonts()
	{
		joomlaPage.goToArticle("Dos and Donts");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("Dos and Don'ts"," Single search"));
		CustomLogger.log("replacing Dos and Don'ts with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    homePage.hamburgerMenu().click();
   	    homePage.doAnddonts().click();
   	    //footerPage.dosAndDontsLink().click();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("Dos and Donts");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
	}
	
	@Test(groups={GroupNames.Mobile}, enabled=false)
	public void recentWinners()
	{
		joomlaPage.goToArticle("Recent Winners");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("Winners Spotlight"," Single search"));
		CustomLogger.log("replacing Recent Winners with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    footerPage.recentWinnersLink().click();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("Recent Winners");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
	}
	
	@Test(groups={GroupNames.Mobile})
	public void aboutSuperPrize()
	{
		joomlaPage.goToArticle("About SuperPrize");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("About SuperPrize&regs"," Single search"));
		CustomLogger.log("replacing About SuperPrize&regs with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    // footerPage.contestDetailsLink().click();
   	    homePage.hamburgerMenu().click();
   	    homePage.aboutSuperPrize().click();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("About SuperPrize");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
	}
	
	@Test(groups={GroupNames.Mobile})
	public void Help()
	{
		joomlaPage.goToArticle("Help");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("Need Help"," Single search"));
		CustomLogger.log("replacing Need Help with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    footerPage.Help();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("Help");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
	}
	
	@Test(groups={GroupNames.Mobile})
	public void moreWaysToWin()
	{
		joomlaPage.goToArticle("More Ways to Win");
		String content=joomlaPage.pchContent().getText();
	    String replacetext =content;
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(replacetext.replaceAll("More Ways to Win"," Single search"));
		CustomLogger.log("replacing Need Help with Single search");
		joomlaPage.saveCloseAndClearCache();
   	    homePage.load();
   	    footerPage.moreWaysToWin();
		joomlaPage.verifyContentUpdated();
		joomlaPage.goToArticle("More Ways to Win");
		joomlaPage.pchContent().clear();
		joomlaPage.pchContent().sendKeys(content);
		joomlaPage.saveCloseAndClearCache();
	}
	
}
