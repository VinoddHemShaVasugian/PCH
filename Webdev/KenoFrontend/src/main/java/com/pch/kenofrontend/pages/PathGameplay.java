package com.pch.kenofrontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;

import com.pch.kenofrontend.utilities.WaitHelper;
import com.pchengineering.registrations.RegistrationRequestGenerator;

import net.serenitybdd.core.pages.PageObject;
/**
 * @author atiwari Feb 6, 2019
 *
 */
public class PathGameplay extends PageObject{

	Choice_SFL csfl;
	HomePage homePage = new HomePage(getDriver());
	
	UserWithoutPassword cuwop;
	
	
	
	private By Keepgoing_btn = new By.ByXPath("//button[contains(text(),'Keep Going!')]");
	private By Playnowbtn_frame = new By.ByXPath("//div[@class='play_now_button']//a[@href='#']");
	private By GameCount = new By.ByXPath("//div[@class='pathgame__game-progress-numbers']");
	private By RevealAll_btn = new By.ByXPath("//a[@id='reveal-all']");
	private By forfeit_gos_sc = new By.ByXPath("//div[contains (text(),'forfeit my tokens')]");
	
	
	public PathGameplay(WebDriver driver) {
		super(driver);
	}
	
	public void playChoiceAsUserWPwd(String game) throws FindFailed, InterruptedException 
	{
		switch (game)
		{
		case "SFL":
			{
				Thread.sleep(3000);
				int totalSFLGames=2;
				for (int selectedChoice=0; selectedChoice<=totalSFLGames; selectedChoice++)
				{
				csfl.clickOnSFLPlayNow();
				if (homePage.element(Keepgoing_btn).isPresent()) //support for Tech Diff when TD GOS shows before Reveal all
				{
					homePage.scrollPage();
					System.out.println("Handling Technical difficulty "+isElementVisible(Keepgoing_btn));
					
					WaitHelper.waitForElementToBeClickable(homePage.getDriver(), Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent())
				{
					element(Keepgoing_btn).click();
				}
					
					((JavascriptExecutor) homePage.getDriver()).executeScript("window.scrollBy(0, -500)", "");
					homePage.HandleVideoAdvertisement();
				} 
				else
				{
					csfl.clickOnRevealNowButton();
					homePage.waitForAnElement(Keepgoing_btn);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					WaitHelper.waitForElementToBeClickable(getDriver(), Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent())
				{
					element(Keepgoing_btn).click();
				}					

					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -500)", "");
					homePage.HandleVideoAdvertisement();
				}
				}
			}
			break;
		case "Scratch Card":
			{				
				int scratchGames = 0, totalNumberOfGames = 0;
				homePage.scrollPage();
				String[] str = element(GameCount).getText().split("of");
				totalNumberOfGames = Integer.parseInt(str[1].trim());
				for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) 
				{
					WaitHelper.forceWait(1000);
					homePage.waitForAnElement(Playnowbtn_frame);
					element(Playnowbtn_frame).click();
					WaitHelper.forceWait(1000);
					if (element(Keepgoing_btn).isPresent()) //support for Tech Diff when TD GOS shows before Reveal all
					{
						homePage.waitForAnElement(Keepgoing_btn);
						WaitHelper.forceWait(2000);
						homePage.scrollPage();
						System.out.println("Handling Technical difficulty "+isElementVisible(Keepgoing_btn));
					
						WaitHelper.waitForElementToBeClickable(getDriver(), Keepgoing_btn);
						if (element(Keepgoing_btn).isPresent())
					{
						element(Keepgoing_btn).click();
					}
						
						((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
						homePage.HandleVideoAdvertisement();
					} 
					else
					{
						if (element(RevealAll_btn).isPresent()) 
						{
							element(RevealAll_btn).click();
						}
						else									//support for Play now frame not going away upon clicking
						{
							WaitHelper.forceWait(1000);
							homePage.getDriver().navigate().refresh();
							WaitHelper.forceWait(1000);
							element(Playnowbtn_frame).click();
							WaitHelper.forceWait(1000);
							element(RevealAll_btn).click();
							homePage.waitForAnElement(Keepgoing_btn);
							WaitHelper.forceWait(2000);
							homePage.scrollPage();
							
							WaitHelper.waitForElementToBeClickable(getDriver(), Keepgoing_btn);
							if (element(Keepgoing_btn).isPresent())
						{
							element(Keepgoing_btn).click();
						}
							
							((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
							homePage.HandleVideoAdvertisement();
						}
						
						homePage.waitForAnElement(Keepgoing_btn);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					
					WaitHelper.waitForElementToBeClickable(getDriver(), Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent())
				{
					element(Keepgoing_btn).click();
				}
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
					homePage.HandleVideoAdvertisement();

					}				
				}
			}
			break;
			default:
			{
				System.out.println("Playing default slots game");
			}
			
		}
			
	}
	
	
	
	public void playFewPathGamesAsUwoPwd(String game) throws FindFailed, InterruptedException 
	{
		switch (game)
		{
		case "SFL":
			{
				Thread.sleep(3000);
				((JavascriptExecutor) getDriver()).executeScript("scroll(0,350)");
				String[] str = element(GameCount).getText().split("of");
				int totalSFLGames = Integer.parseInt(str[1].trim());
				for (int selectedChoice=0; selectedChoice<=totalSFLGames; selectedChoice++)
				{
					csfl.clickOnSFLPlayNow();
					csfl.clickOnRevealNowButton();
					homePage.waitForAnElement(forfeit_gos_sc);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					forfeitLinkSC();
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -500)", "");
					homePage.HandleVideoAdvertisement();
				}
			}
			break;
		case "Scratch Card":
			{
				int scratchGames = 0, totalNumberOfGames = 0;
				homePage.scrollPage();
				String[] str = element(GameCount).getText().split("of");
				totalNumberOfGames = Integer.parseInt(str[1].trim());
				System.out.println("FewNumberOfGames =  "+ (totalNumberOfGames-3));
				for (scratchGames = 1; scratchGames <=(totalNumberOfGames-3); scratchGames++) 
				{
					WaitHelper.forceWait(1000);
					homePage.waitForAnElement(Playnowbtn_frame);
					element(Playnowbtn_frame).click();
					WaitHelper.forceWait(2000);
					element(RevealAll_btn).click();
					WaitHelper.forceWait(2000);
					homePage.waitForAnElement(forfeit_gos_sc);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					forfeitLinkSC();		
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
					homePage.HandleVideoAdvertisement();
					}
			}
			
			break;
			default:
			{
				System.out.println("Playing default slots game");
			}
			
		}
}

	
	
	public void playChoiceAsUserWoPwd(String game) throws FindFailed, InterruptedException 
	{
		switch (game)
		{
		case "SFL":
			{
				Thread.sleep(3000);
				int totalSFLGames=2;
				for (int selectedChoice=0; selectedChoice<=totalSFLGames; selectedChoice++)
				{
				csfl.clickOnSFLPlayNow();
				if (element(forfeit_gos_sc).isPresent()) //support for Tech Diff when TD GOS shows before Reveal all
				{
					homePage.scrollPage();
					System.out.println("Handling Technical difficulty "+isElementVisible(forfeit_gos_sc));
					forfeitLinkSC();
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -500)", "");
					homePage.HandleVideoAdvertisement();
				} 
				else
				{
					csfl.clickOnRevealNowButton();
					homePage.waitForAnElement(forfeit_gos_sc);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					forfeitLinkSC();
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -500)", "");
					homePage.HandleVideoAdvertisement();
				}
				}
			}
			break;
		case "Scratch Card":
			{				
				int scratchGames = 0, totalNumberOfGames = 0;
				homePage.scrollPage();
				String[] str = element(GameCount).getText().split("of");
				totalNumberOfGames = Integer.parseInt(str[1].trim());
				for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) 
				{
					WaitHelper.forceWait(1000);
					homePage.waitForAnElement(Playnowbtn_frame);
					element(Playnowbtn_frame).click();
					WaitHelper.forceWait(1000);
					if (element(forfeit_gos_sc).isPresent()) //support for Tech Diff when TD GOS shows before Reveal all
					{
						homePage.waitForAnElement(forfeit_gos_sc);
						WaitHelper.forceWait(2000);
						homePage.scrollPage();
						System.out.println("Handling Technical difficulty "+isElementVisible(forfeit_gos_sc));
						forfeitLinkSC();
						((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
						homePage.HandleVideoAdvertisement();
					} 
					else
					{
						if (element(RevealAll_btn).isPresent()) 
						{
							element(RevealAll_btn).click();
						}
						else									//support for Play now frame not going away upon clicking
						{
							WaitHelper.forceWait(1000);
							getDriver().navigate().refresh();
							WaitHelper.forceWait(1000);
							element(Playnowbtn_frame).click();
							WaitHelper.forceWait(1000);
							element(RevealAll_btn).click();
							homePage.waitForAnElement(forfeit_gos_sc);
							WaitHelper.forceWait(2000);
							homePage.scrollPage();
							forfeitLinkSC();		
							((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
							homePage.HandleVideoAdvertisement();
						}
						
					homePage.waitForAnElement(forfeit_gos_sc);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					forfeitLinkSC();		
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
					homePage.HandleVideoAdvertisement();

					}				
				}
			}
			break;
			default:
			{
				System.out.println("Playing default slots game");
			}
			
		}
			
	}
	
	public void forfeitLinkSC(){
		WaitHelper.waitForElementToBeClickable(getDriver(), forfeit_gos_sc);
			if (element(forfeit_gos_sc).isPresent())
		{
			element(forfeit_gos_sc).click();
		}
	}
	

}
