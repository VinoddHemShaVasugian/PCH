package com.pch.kenofrontend.pages;

//import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import net.serenitybdd.core.pages.PageObject;
import toolbox.JavaScriptReusablePage;

public class Choice_SFL extends PageObject{
	private By canvas = new By.ByCssSelector("div canvas"); //new By.ByXPath("//*[@id='game-holder']/canvas"); 
	private By oops = new By.ByCssSelector("div canvas");
	private By gameLogo = new By.ByClassName("gos__game-logo");
	private By congratsText = new By.ByCssSelector("[class*='gos__texts__body']");
	private By tokenEarnedLabel = new By.ByClassName("gos__win__label");
	private By tokenWinText = new By.ByClassName("gos__win__text");
	private By td_screen = new By.ByCssSelector("[class*='td']");
	private By oops_text = new By.ByCssSelector(".gos__top-text");
	private By oops_bottom_text = new By.ByCssSelector(".gos__bottom-text");
	JavaScriptReusablePage exeJS = JavaScriptReusablePage.getInstance();
	
	
	public void clickOnSFLPlayNow() throws FindFailed, InterruptedException 
	{
		((JavascriptExecutor) getDriver()).executeScript("scroll(0,250)");
	
		Thread.sleep(3000); //must to load
		Screen s=new Screen();
		Pattern pattern1 = new Pattern("\\images\\SFL_PlayNow.png");
		s.wait(pattern1,100);
		if(s.exists(pattern1) != null){
			s.hover();
			s.click(pattern1);
			}	
	}
	
		public void clickOnRevealNowButton() throws FindFailed {
			Screen s=new Screen();
			Pattern pattern = new Pattern("\\images\\SFL_reveal_all.png");
			s.wait(pattern, 60);
			if(s.exists(pattern) != null){
				s.hover();
				s.click(pattern);
			}
			else if(element(oops).isPresent()) {
				System.out.println("TD occured");
			}
			else {
				System.out.println("Reveal All not present");
			}
		}

		
		/*public void verifyGOSScreen() {
			if(element(oops).isPresent()){
				System.out.println("Technical Difficulty");
				verifyTDScreen();
			}
			else{
				element(gameLogo).waitUntilVisible();
				element(gameLogo).shouldBeVisible();
				System.out.println("Firstname:"+exeJS.getUsername());
				element(congratsText).shouldContainText(exeJS.getUsername());
				element(tokenEarnedLabel).shouldBeVisible();
				element(tokenWinText).shouldBeVisible();			
			}
		}
		
		
		public void verifyTDScreen(){
			WebElement oops_screen = element(oops);
			new Actions(getDriver()).moveToElement(getDriver().findElement(By.cssSelector("div canvas"))).build().perform();
			assertTrue(element(td_screen).isPresent());
	    String topText = element(oops_text).getText();
	    String bottomText = element(oops_bottom_text).getText();
	    assertTrue(topText.equalsIgnoreCase("Oops!"));
	    assertTrue(bottomText.equalsIgnoreCase("We are experiencing technical difficulties. Please try again!"));		
		}*/


}
