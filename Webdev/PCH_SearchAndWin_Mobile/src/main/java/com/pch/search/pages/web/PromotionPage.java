package com.pch.search.pages.web;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.PageFactory;


public class PromotionPage extends Action{

	private HtmlElement openXad() {
		return driver.findElement(By.cssSelector("img.img-responsive"));
	}
	
	private HtmlElement searchBox() {
		return driver.findElement(By.id("searchField0"));
	}
	
	private HtmlElement skipBtn() {
		return driver.findElement(By.cssSelector("button.cross-promo__next"));
	}
	
	public void selectcontrol(String VisibleText1){
		driver.findSelectList(By.id("jform_params_percent_test1"))
		.selectByVisibleText(VisibleText1);
	}
	public void TestcycleRandomNumber(){
		Random random=new Random();
		int answer=random.nextInt(10)+1;
		driver.findElement(By.xpath("//span[contains(text(),'Advanced Options')]")).click();
		Common.sleepFor(2000);
		driver.findElement(By.id("jform_params_test_ver")).clear();
		driver.findElement(By.id("jform_params_test_ver")).sendKeys(Integer.toString(answer));
	}
	
	public void SelectgsTest(String VisibleText)
	{
		driver.findSelectList(By.id("jform_params_percent_control"))
		.selectByVisibleText(VisibleText);
		}
	
	public HtmlElement SearchWinLogo(){
		return driver.findElement(By.xpath(".//div[@class='gs-logo']/img"));
	}
	
	public HtmlElement GiveawayDetails(){
		return driver.findElement(By.xpath("//a[contains(text(),'PCH Giveaway Details')]"));
	}
	
	public HtmlElement Truste(){
		return driver.findElement(By.cssSelector(".truste>div>a>img"));
	}
	public HtmlElement BBBlogo(){
		return driver.findElement(By.cssSelector("#truste_bbb>div>a>img"));
	}
	
	
	
	

	public void validatePromotionPage() {
		// Validate if OpenX ad is displayed
		Assert.assertTrue(openXad().isDisplayed());
		CustomLogger.log("OpenX Ad is displayed on promotion page.");

		// Validate if search box is displayed
		Assert.assertTrue(searchBox().isDisplayed());
		CustomLogger.log("Search Box is displayed on promotion page.");

		// Validate if Skip btn is displayed on Promotion page
		Assert.assertTrue(skipBtn().isDisplayed());
		CustomLogger.log("Skip button is displayed on promotion page.");
	}
	
	public void Systemterm(String Selector, String Element){
		WebDriver drivers = PageFactory.getBrowserNDriverMap()
				.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver();
    WebElement element1=  drivers.findElement(By.xpath(Selector));
	driver.switchToFrame(element1);
	String mainwindow= driver.getWindowHandle();
 	driver.findElement(By.xpath(Element)).click();
 	if(driver.getWindowHandles().size()>1){
 	driver.switchToChildWindow(mainwindow);
 	}
 	}
	
	public void GsTestPage(){
		Assert.assertTrue(SearchWinLogo().isDisplayed());
		CustomLogger.log("Logo is displayed on Test page.");
		
		Assert.assertTrue(searchBox().isDisplayed());
		CustomLogger.log("Search Box is displayed on Test page.");
		
		Assert.assertTrue(openXad().isDisplayed());
		CustomLogger.log("OpenX Ad is displayed on Test page.");
		
		Assert.assertTrue(GiveawayDetails().isDisplayed());
		CustomLogger.log("Giveaway details is displayed on Test page.");
		
		Assert.assertTrue(Truste().isDisplayed());
		CustomLogger.log("Truste is displayed on Test page.");
		
	    Assert.assertTrue(BBBlogo().isDisplayed());
	    CustomLogger.log("BBBlogo is displayed on Test page.");
	    
	  	     }
	
}
