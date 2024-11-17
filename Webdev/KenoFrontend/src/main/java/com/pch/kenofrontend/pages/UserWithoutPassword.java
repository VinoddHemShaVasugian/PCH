package com.pch.kenofrontend.pages;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.WaitHelper;
import com.pchengineering.registrations.RegistrationRequestGenerator;
import com.pchengineering.registrations.RequestDefaultsOverride;

import groovy.ui.SystemOutputInterceptor;
import net.serenitybdd.core.pages.PageObject;

/**
 * @author atiwari Sep 4, 2018
 *
 */

public class UserWithoutPassword extends PageObject {
	private static final int sz = 0;
	HomePage homePage;
	Choice_SFL csfl;
	RegistrationPage rp;

	private By uwopwd_lightbox = new By.ByXPath("//p[@class='create-password']");
	private By close_uwopwd_lightbox = new By.ByXPath("//div[@class='modal fade sso-create-password-lightbox-modal in']//span[@aria-hidden='true'][contains(text(),'×')]");
	private By Playnowbtn_frame = new By.ByXPath("//span[text()='Play Now!']"); 
	private By RevealAll_btn = new By.ByXPath("//a[@id='reveal-all']");	
	private By forfeit_link = new By.ByXPath("//a[@class='no-thanks']");
	private By forfeit_gos_sc = new By.ByXPath("//div[contains (text(),'forfeit my tokens')]");
	private By GameCount = new By.ByXPath("//div[@class='pathgame__game-progress-numbers']");
	private By createPasswordTextbox = new By.ByCssSelector("#sso-create-password-lightbox-create-password");
	private By confirmPasswordTextbox = new By.ByCssSelector("#sso-create-password-lightbox-confirm-password");
	private By tokenHistory_link = new By.ByLinkText("Token History");
	private By createPasswordSCGOS = new By.ByCssSelector("div.no-password--wrapper > input.lbpassword");
	private By confirmPasswordSCGOS = new By.ByCssSelector("div.no-password--wrapper > input.lbconfirmPassword");
	private By queuedTokensSCGOS = new By.ByCssSelector("div.gos__win__text");
	private By dailyTokenBonus_button = new By.ByCssSelector("button.btn--gold");
	private By VIP_close_btn = new By.ByCssSelector(".vip-message__close");
	private By OPTIN_light_box = new By.ByCssSelector("div.emailoptin > div.modal-dialog > div>.modal-content > div.modal-header > button.close");
	
	//Collect Bonus Reward
	static String email;
	String queuedTokens;	
	
	public UserWithoutPassword(WebDriver driver){
		super(driver);
	}

	//RegistrationRequestGenerator rs = new RegistrationRequestGenerator();
	
		RequestDefaultsOverride defaultOverride = new RequestDefaultsOverride();
		RegistrationRequestGenerator rs = new RegistrationRequestGenerator();
		
		public String create_user_withoutpassword()
		{
			System.out.println("Echo: Creating the URL for User without password");
			//rs.generateSilverUserInQA();
			rp.uniqueReg();
			defaultOverride.setEmail(RegistrationPage.uni_email);
			rs.generateSilverUserInSTG(defaultOverride);
			
			//rs.generateSilverUserInSTG();
			email= rs.getEmail();
			String gmt= rs.getGmt();
			
			String kenoUrl=  PropertiesReader.getInstance().getData("KenoUrl");
			String url= kenoUrl+"?em="+email+"&gmt="+gmt;
	        
			System.out.println(email);
			System.out.println(gmt);
			System.out.println(url);
			getDriver().navigate().refresh();
			return url;

		}

	public void navigate_user_withoutpassword() {
		String url=create_user_withoutpassword();
		getDriver().navigate().to(url);
		getDriver().manage().window().maximize();
		getDriver().navigate().refresh();
		//homePage.vipPresent();
	}



	public void sso_Uwopwd_LightboxPresent(){
		WaitHelper.waitForPresenceOfElement(getDriver(),uwopwd_lightbox);
		if (element(uwopwd_lightbox).isPresent())
		{
			Assert.assertEquals("Create a password to submit your numbers now. Don't risk missing out on prize funds.",element(uwopwd_lightbox).getText());
		}else {
			System.out.println("User without password lightbox is not displayed");
		}
	}

	public void close_Sso_Uwopwd_Lightbox(){
		WaitHelper.waitForPresenceOfElement(getDriver(),close_uwopwd_lightbox);
		if (element(close_uwopwd_lightbox).isPresent())
		{
			element(close_uwopwd_lightbox).click();
		}
	}

	public void forfeitLinkClick(){
		WaitHelper.waitForElementToBeClickable(getDriver(), forfeit_link);
		if (element(forfeit_link).isPresent())
		{
			element(forfeit_link).click();
		}
	}


	public void forfeitLinkSC(){
		WaitHelper.waitForElementToBeClickable(getDriver(), forfeit_gos_sc);
			if (element(forfeit_gos_sc).isPresent())
		{
			element(forfeit_gos_sc).click();
		}
	}

	public void playScratchCards_UserWoPwd() throws InterruptedException {
		try {			
			if (element(Playnowbtn_frame).isVisible()) 
			{
				element(Playnowbtn_frame).click();
			}
			element(RevealAll_btn).click();
			getDriver().switchTo().parentFrame();
			homePage.waitForAnElement(forfeit_gos_sc);
			WaitHelper.forceWait(2000);
			homePage.scrollPage();
			forfeitLinkSC();		
			((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
			homePage.HandleVideoAdvertisement();
		
		} catch (org.openqa.selenium.WebDriverException e) {
			System.out.println("Exception" + e);
		}

	}
	
	
	
	public void choiceSelection(String game) throws InterruptedException, FindFailed{
	
		List<WebElement> Choices_Parent = getDriver().findElements(By.cssSelector("div.choices__games__page > div"));
		System.out.println("Choices_Parent.size()  "+ Choices_Parent.size());
		int sz = Choices_Parent.size();
		String[] alttextarr = new String[sz];
		String alttext=null;
		
		int i;
		int selectedChoice=0;
		
		List<WebElement> Choices = getDriver().findElements(By.cssSelector("div.choices__games__page__game > div > img"));
	
		for (i=0; i<Choices_Parent.size();i++)
		{
			alttext = Choices.get(i).getAttribute("alt");
			alttextarr[i]=alttext;
			if (game.equalsIgnoreCase("Scratch Card"))
				{
					alttext="IWPATH";
					if(alttextarr[i].contains(alttext))
					{
					System.out.println("alttextarray  "+ Arrays.toString(alttextarr));
										String alttextIWPath = Choices.get(i).getAttribute("alt");
					System.out.println( "selectedChoice ALT is =   "+alttextIWPath);
					selectedChoice=i;
					System.out.println( "selectedChoice  = "+selectedChoice);
					break;
					}
				}
		
			if (game.equalsIgnoreCase("SFL"))
				{
					alttext="D-Keno set-for-life_3cards";
					 if(alttextarr[i].equals(alttext))
					 {
					selectedChoice=i;
					System.out.println( "selectedChoice   = "+selectedChoice);
					}
				}
		}
		
		System.out.println("alttextarray  "+ Arrays.toString(alttextarr));
		switch (alttext)
			{
			case "D-Keno set-for-life_3cards":
				{
					System.out.println( "selectedChoice  = "+selectedChoice);
					Choices.get(selectedChoice).click();
				}
				break;
			case "IWPATH":
				{
					System.out.println( "selectedChoice  = "+selectedChoice);
					Choices.get(selectedChoice).click();
				}
				break;
			default:
					System.out.println("default slots game");
					alttext="slots";
			}
		System.out.println("alttextarray  "+ Arrays.toString(alttextarr));
		
	}


	
	/*public int choiceSelection() throws InterruptedException, FindFailed{
		int flag = 2;
			homePage.scrollPage();
			List<WebElement> Choices_Parent = getDriver().findElements(By.cssSelector("div.choices__games__page > div"));
			System.out.println("Choices_Parent.size()  "+ Choices_Parent.size());

			for (int i=0; i<Choices_Parent.size();) {
				List<WebElement> Choices = getDriver().findElements(By.cssSelector("div.choices__games__page__game > div > img"));
								String alttext = Choices.get(i).getAttribute("alt");

				if (alttext.equals("D-Keno set-for-life_3cards")) 
	{
					
					System.out.println("identifiable SFL button playnow on Choices screen  " + Choices.get(i).isEnabled());
					System.out.println("Choices.get(i)   " + i + Choices.get(i));
									Choices.get(i).click();
					Thread.sleep(3000);
	     			int totalSFLGames=2;
					for (int j=0; j<=totalSFLGames; j++){
					csfl.clickOnSFLPlayNow();
					csfl.clickOnRevealNowButton();
					

					homePage.waitForAnElement(forfeit_gos_sc);
					WaitHelper.forceWait(2000);
					homePage.scrollPage();
					System.out.println("Clicking Forfeit SFL GOS. Element visibility "+isElementVisible(forfeit_gos_sc));
					forfeitLinkSC();
					
					
					((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -500)", "");
					homePage.HandleVideoAdvertisement();
					}
					flag=1;
					break;
				}

				else 
				{					
					System.out.println("Choices.get(i)   " + i + Choices.get(i));
					System.out.println("identifiable button playnow  on choices " + Choices.get(i).isEnabled());
					Choices.get(i).click();
					flag=0;
					break;
				}	
				
			}
			System.out.println("flag  " + flag );
			return flag;

		}
*/

	
	
	
	
	/*public void scratchpathplayAsUserWoPwd() throws Exception {

		int scratchGames = 0, totalNumberOfGames = 0;
		getDriver().switchTo().parentFrame();


		try {
			homePage.scrollPage();	
			choiceSelection();
			if (choiceSelection()==0)
			{
		
			//homePage.waitForAnElement(GameFrame);
			String[] str = element(GameCount).getText().split("of");
			totalNumberOfGames = Integer.parseInt(str[1].trim());
			for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) {
				WaitHelper.forceWait(1000);
				homePage.waitForAnElement(Playnowbtn_frame);
				System.out.println("Is Path GAME PLAY NOW visible/clickable ?  "+isElementVisible(Playnowbtn_frame));
				element(Playnowbtn_frame).click();
				WaitHelper.forceWait(5000);
				getDriver().switchTo().parentFrame();
			
				playScratchCards_UserWoPwd();				
					
			}
			Assert.assertTrue(
					"Expected Scratch Games are not played completely",
					scratchGames - 1 == totalNumberOfGames);
			}
		}catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("******Element is not present*********");
			throw (e);
		}


		catch (Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
			throw (e);
		}


	}*/
	
		
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


	
	public void enterPwdOnSCGOS() throws InterruptedException
	{
			homePage.waitForAnElement(Playnowbtn_frame);
			element(Playnowbtn_frame).click();
			WaitHelper.forceWait(2000);
			element(RevealAll_btn).click();
			WaitHelper.forceWait(2000);
		//	homePage.scrollPage();
			WaitHelper.waitForVisibliyOfElement(getDriver(), createPasswordSCGOS, 10);
			WaitHelper.waitForVisibliyOfElement(getDriver(), queuedTokensSCGOS, 10);
			if (element(createPasswordSCGOS).isPresent() && element(queuedTokensSCGOS).isPresent())
			{
				
			//homePage.waitForAnElement(createPasswordSCGOS);
			queuedTokens = element(queuedTokensSCGOS).getTextValue(); 
			System.out.println("queued Tokens             " + queuedTokens);
			element(createPasswordSCGOS).typeAndTab("pch1234");
			element(confirmPasswordSCGOS).typeAndEnter("pch1234");
					if (element(dailyTokenBonus_button).isPresent())
					{
						element(dailyTokenBonus_button).click();
					}
			}
			else {
				System.out.println("User without password SC GOS lightbox is not displayed");
			}
			((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
			homePage.HandleVideoAdvertisement();
			
	}
	
	public void createPassword(){
		//WaitHelper.waitForPresenceOfElement(getDriver(),createPasswordTextbox);
		if (element(createPasswordTextbox).isPresent())
		{
			element(createPasswordTextbox).typeAndTab("pch1234");
			element(confirmPasswordTextbox).typeAndEnter("pch1234");
		if (element(dailyTokenBonus_button).isPresent())
			{
				element(dailyTokenBonus_button).click();
			}
		}else {
			System.out.println("User without password lightbox is not displayed");
		}
		
		
	}	

	
	public void verifyThousandTokensForCreatingPassword() throws InterruptedException
		{		
		WaitHelper.forceWait(3000);
		//WaitHelper.waitForPresenceOfElement(getDriver(),tokenHistory_link);
		element(tokenHistory_link).waitUntilVisible();
		element(tokenHistory_link).click();
			List<WebElement> tokenDescription= getDriver().findElements(By.cssSelector("section#th_list > article > div.th_details > div.th_desc"));
			List<WebElement> tokenHistory= getDriver().findElements(By.cssSelector("section#th_list > article"));
			List<WebElement> creditTokens= getDriver().findElements(By.cssSelector("section#th_list > article > div.th_details > div.date_tokens > span.tokens"));
			Iterator <WebElement> i = tokenDescription.iterator();
			Iterator <WebElement> i2 = creditTokens.iterator();
				System.out.println("Token History size is  " + tokenHistory.size());
				Thread.sleep(3000);
				
				while(i.hasNext() && (i2.hasNext()))
				{
					WebElement e1=i.next();
					WebElement e2 = i2.next();
					
					System.out.println("e1    "+e1.getText());
					System.out.println("e2    "+e2.getText());
					
					if(e1.getText().contains("PCHkeno User Registration"))
					{
						{
							Assert.assertEquals("PCHkeno User Registration",e1.getText());
							Assert.assertTrue(e1.getText().contains("PCHkeno User Registration"));
						}
					}
				}
		System.out.println("Validated reg tokens");
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

	public void verifyQueuedTokens() throws InterruptedException {
		{
			WaitHelper.forceWait(3000);	
			((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
			if (element(dailyTokenBonus_button).isPresent())
			{
				element(dailyTokenBonus_button).click();
			}
			WaitHelper.waitForVisibliyOfElement(getDriver(),tokenHistory_link,3);
			element(tokenHistory_link).click();
			List<WebElement> tokenDescription= getDriver().findElements(By.cssSelector("section#th_list > article > div.th_details > div.th_desc"));
			List<WebElement> tokenHistory= getDriver().findElements(By.cssSelector("section#th_list > article"));
			List<WebElement> creditTokens= getDriver().findElements(By.cssSelector("section#th_list > article > div.th_details > div.date_tokens > span.tokens"));
			Iterator <WebElement> i = tokenDescription.iterator();
			Iterator <WebElement> i2 = creditTokens.iterator();
				System.out.println("Token History size is  " + tokenHistory.size());
				
				Thread.sleep(3000);
				
				while(i.hasNext() && (i2.hasNext()))
				{
					WebElement e1=i.next();
					WebElement e2 = i2.next();
					
					System.out.println("e1    "+e1.getText());
					System.out.println("e2    "+e2.getText());
					
					if(e1.getText().contains("Created Password & Secured Prior Token Rewards"))
					{
					Assert.assertEquals("Created Password & Secured Prior Token Rewards",e1.getText());
					System.out.println("creditTokens  "+ e2.getText().substring(1).toString());
					Assert.assertEquals(queuedTokens,  e2.getText().substring(1).toString());
					Assert.assertTrue(e1.getText().contains("Created Password & Secured Prior Token Rewards"));
					}
				}
			System.out.println("Validated Queued tokens");
		}
	}

	
}


	







