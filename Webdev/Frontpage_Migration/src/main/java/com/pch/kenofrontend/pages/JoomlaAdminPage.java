package com.pch.kenofrontend.pages;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.WaitHelper;

import net.serenitybdd.core.pages.PageObject;

/**
 * @author atiwari Oct 1, 2018
 *
 */
public class JoomlaAdminPage extends PageObject {	

	
	public JoomlaAdminPage(WebDriver driver) 
	{
		super(driver);
	}
	
	private By username = new By.ById("mod-login-username");
	private By password = new By.ById("mod-login-password");
	private By login_button = new By.ByXPath("//button[@class='btn btn-primary btn-block btn-large']");
	private By articles = new By.ByXPath("//a[@href='/administrator/index.php?option=com_content']"); 
	private By searchTools = new By.ByXPath("//button[contains(text(),'Search Tools')]");
	private By searchBy = new By.ByXPath("//span[contains(text(),'- Select Category -')]");
	private By text = new By.ByXPath("//div[@id='filter_category_id_chzn']//input[@type='text']");
	private By title_contestEntry = new By.ByXPath("//a[contains(text(),'Contest Entry')]");
	private By title_optinLightbox = new By.ByXPath("//a[contains(text(),'Optin Lightbox')]");
	private By title_dailyTokenBonus = new By.ByXPath("//a[contains(text(),'Daily Token Bonus')]");
	//private By contestKey = new By.ByXPath("//input[@id='contest_entries_0_contest_key']");
	private By contestKey = new By.ByName("contest_entries[0][contest_key]");
	//private By contestKeySFL = new By.ByXPath("//input[@id='contest_entries_2_contest_key']");
	private By contestKeySFL = new By.ByName("contest_entries[2][contest_key]");
	private By contestMessage = new By.ByXPath("//input[@id='contest_entries_0_contest_message']");
	private By optin_tokens = new By.ByCssSelector("input#optin_tokens");
	private By clear_Button = new By.ByXPath("//button[contains(text(),'Clear')]");
	
	
	static String s1=null;
	static String s3=null;
	static String s4=null;
	static String s5=null;
	static String[] dailyTokenBonusArr= new String[7];

	
	String s2=null;
	
	public void login(ExamplesTable credentials)
	{
		if (element(login_button).isPresent())	
		{
			getDriver().manage().window().maximize();
			element(username).sendKeys(credentials.getRow(0).get("user_name"));
			element(password).sendKeys(credentials.getRow(0).get("password"));
			element(login_button).click();
		}
		else
		{
			return;
		}

	}
	
	
	public void clickArticles()
	{
		element(articles).click();
	}
	
	public void selectCategory(String category) throws InterruptedException
	{
		 		WaitHelper.forceWait(5000);
		 		element(clear_Button).click();
		 		element(searchTools).click();
				element(searchBy).click();
				switch (category)
		{
			case "Contest Entries":
			{
			element(text).typeAndEnter("Contest Entries");
			}
			break;
			
			case "Optin Lightbox":
			{
				element(text).typeAndEnter("Optin Lightbox");
			}
			break;
			
			case "Daily Token Bonus":
			{
				element(text).typeAndEnter("Daily Token Bonus");
			}
			break;
			default:
			{
				System.out.println("no category entered");
			}
		}
	}
	
	public void selecttitle(String title)
	{

		switch (title)
		{
			case "Contest Entries":
			{
				element(title_contestEntry).click();
			}
			break;
			case "Optin Lightbox":
			{
				element(title_optinLightbox).click();
			}	
			break;
			case "Daily Token Bonus":
			{
				element(title_dailyTokenBonus).click();
			}	
			break;
			default:
			{
				System.out.println("no title entered");
			}	
		}
}


	public void readContestKey()
	{
		s1= element(contestKey).getTextValue();
		System.out.println("contest Key "+ s1);
		s3=element(contestKeySFL).getTextValue();
		System.out.println("contest Key SFL "+ s3);
	}
	
	public void readContestMessage()
	{
		s2 = element(contestMessage).getTextValue();
		System.out.println("contest Message "+ s2);
	}

	public void OpenKenoAdminPage() {
		getDriver().get(PropertiesReader.getInstance().getData("KenoAdminUrl"));
		
	}
	
	public void readOptinTokens()
	{
		s4= element(optin_tokens).getTextValue();
		System.out.println("Read Optin Tokens "+ s4);
	}
	
	public void readDailyBonusTokens()
	{
		
		List<WebElement> numberOfItems = getDriver().findElements(By.cssSelector("div.cck-plr > div#cck1r_mainbody > div#cck1r_bonus_items > div#cck1r_form_bonus_items > div#cck1_sortable_bonus_items > div"));
		System.out.println("size  "+numberOfItems.size());
		String[] tokenText = new String[numberOfItems.size()];
		
		List<WebElement> dailyTokenBonus_tokens = getDriver().findElements(By.cssSelector("div.cck-plr > div#cck1r_mainbody > div#cck1r_bonus_items > div#cck1r_form_bonus_items > div#cck1_sortable_bonus_items > div.cck_form_group_x > div.cck_cgx_form > div.cck_token_bonus > div.cck_form_text > input"));
		for (int j=0; j<numberOfItems.size();j++)
		{
			tokenText[j] = dailyTokenBonus_tokens.get(j).getAttribute("value");
		}
		
		System.out.println("tokenText   " +Arrays.toString(tokenText));
		dailyTokenBonusArr = tokenText;
		System.out.println("dailyTokenBonusArr   " +Arrays.toString(dailyTokenBonusArr));	
	}

}


	
