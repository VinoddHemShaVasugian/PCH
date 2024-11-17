package com.pch.search.pages.web;

import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.User;

public class PchDotComPage extends Action {
	
	public HtmlElement registrationBtn(){
		return driver.findElement(By.xpath("//a[text()='Register']"));
	}
	public void load(){	
		CustomLogger.log("Loading PCH.com");
		driver.get("http://www."+Environment.getEnvironment()+".pch.com");
		driver.waitForBrowserToLoadCompletely();
	}
	
	public void EnterUserDetails(User user){
		setTitle(user.getTitle());
		setFirstName(user.getFirstname());
		setLastName(user.getLastname());
		setStreetAddress(user.getStreet());
		setCity(user.getCity());
		setState(user.getState());
		setZip(user.getZip());		
		setEmail(user.getEmail());
		setConfirmEmail(user.getEmail());
	}
	
	public void registerUser(User user){
		CustomLogger.log("Registering User "+user);
		if(!Environment.getDevice().equalsIgnoreCase("mobile")){
			registrationBtn().click();
			driver.waitForBrowserToLoadCompletely();
		}			
		EnterUserDetails(user);
		clickContinue();
		CustomLogger.log("User Registered");
	}
	
	public void signOut(){
		driver.findElement(By.id("hypSignOut")).click();
		driver.waitForBrowserToLoadCompletely();
	}
	
	private void clickContinue(){
		HtmlElement submit =  driver.findElement(By.id("sub-btn"));		
		submit.scrollDownAndClick();
		CustomLogger.log("Clicked continue button.");
		submit.waitTillNotPresent(60);
		driver.waitForBrowserToLoadCompletely();		
	}
	
	public void skipAds(){
		String environment = Environment.getEnvironment();
		
		CustomLogger.log("Skip ads and taking you to Homepage");
		//HtmlElement continueToNextPage = driver.findElement(By.id("mpNavNext"));
		//continueToNextPage.scrollDownAndClick();
		driver.waitForBrowserToLoadCompletely();
		
	//	driver.findElement(By.id("submitButton")).click();
	//	driver.waitForBrowserToLoadCompletely();
	//	try {
	//		Thread.sleep(5000);
	//	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		String url = driver.getCurrentUrl();
		if(!(url.contains("http://www."+environment+".pch.com/"))){
			driver.waitForBrowserToLoadCompletely();
			Assert.assertTrue(driver.getCurrentUrl().contains("http://www."+environment+".pch.com/"));
			CustomLogger.log("Welcome to PCH Home page");
		}else{
			CustomLogger.log("Welcome to PCH Home page");
		}
		driver.waitForBrowserToLoadCompletely();
	}
	
	private void setTitle(String title){
		driver.findSelectList(By.id("TI")).selectByVisibleText(title);
	}
	
	private void setFirstName(String fname){
		HtmlElement firstName = driver.findElement(By.id("FN"));
		firstName.clear();
		firstName.sendKeys(fname);
	}
	
	private void setLastName(String lname){
		HtmlElement lastName = driver.findElement(By.id("LN"));
		lastName.clear();
		lastName.sendKeys(lname);
	}
	
	private void setStreetAddress(String saddress){
		HtmlElement streetAddress = driver.findElement(By.id("A1"));
		streetAddress.clear();
		streetAddress.sendKeys(saddress);
	}
	
	private void setCity(String city){
		HtmlElement setCity = driver.findElement(By.id("CI"));
		setCity.clear();
		setCity.sendKeys(city);
	}
	
	private void setState(String state){
		driver.findSelectList(By.id("ST")).selectByVisibleText(state);
	}
	
	private void setZip(String state){
		HtmlElement zip = driver.findElement(By.id("ZI"));
		zip.clear();
		zip.sendKeys(state);
	}
	
	public void setDOB_Month(String month){
		driver.findSelectList(By.id("MN")).selectByVisibleText(month);
	}
	
	public void setDOB_Day(int day){
		DecimalFormat formatter = new DecimalFormat("00");		
		driver.findSelectList(By.id("DY")).selectByVisibleText(formatter.format(day));
	}
	
	
	public void setDOB_Year(String year){
		driver.findSelectList(By.id("YR")).selectByVisibleText(year);
	}
	
	private void setEmail(String email){
		HtmlElement emailElement = driver.findElement(By.id("EM"));
		emailElement.clear();
		emailElement.sendKeys(email);
	}
	
	private void setConfirmEmail(String email){
		HtmlElement emailElement = driver.findElement(By.id("CE"));
		emailElement.clear();
		emailElement.sendKeys(email);
	}
	
	/*
	 * 		#To remove Tutorial Holder#
	 * When we complete registration in PCH.com site
	 * we will navigate to adds page and when we skip those ads
	 * it'll take us to PCH home page with Tutorial Holder on top of it.
	*/
	public void closeTutorialHolder() {
		try{
			if(!(driver.findElement(By.id("tutorial_holder")).getAttribute("style").contains("display: none;"))){
				driver.findElement(By.xpath(".//*[@class='closeBtn close']/img")).click();
				CustomLogger.log("Tutorial Holder got closed");
			}else{
				CustomLogger.log("Didn'f find Tutorial holder");
			}
		}catch(TimeoutException ex){
			CustomLogger.log("Didn'f find Tutorial holder");
		}
	}
	
	/*
	public String sikuliFunctionToPlayGames() throws FindFailed{
		
		Screen screen = new Screen();
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		
		js.executeScript("window.scrollBy(0, 250);");
		
		driver.findElement(By.xpath(".//*[@id='main']/div[2]/div/div/div[1]/h3")).scrollDownAndClick();
		String gameCountString = driver.findElement(By.id("gameCount")).getText();
		
		int gameCount = Integer.parseInt(gameCountString);
		
		for(int i=1; i<=gameCount; i++){
			
			screen.click("GamePathImages/playNow.PNG");
			Common.sleepFor(5000);
			screen.wait("GamePathImages/revealAll"+i+1+".PNG", 5000);
			screen.click("GamePathImages/revealAll"+i+".PNG");
			Common.sleepFor(20000);
			screen.wait("GamePathImages/continue.PNG", 10000);
			screen.click("GamePathImages/continue.PNG");
			Common.sleepFor(50000);
			
		}
		
		return driver.getCurrentUrl();
			screen.click("GamePathImages/playNow.PNG");
			Common.sleepFor(5000);
			screen.wait("GamePathImages/revealAll"+i+1+".PNG", 5000);
			screen.click("GamePathImages/revealAll2.PNG");
			Common.sleepFor(20000);
			screen.wait("GamePathImages/continue.PNG", 10000);
			screen.click("GamePathImages/continue.PNG");
			Common.sleepFor(50000);
			
			screen.click("GamePathImages/playNow.PNG");
			Common.sleepFor(5000);
			screen.wait("GamePathImages/revealAll"+i+1+".PNG", 5000);
			screen.click("GamePathImages/revealAll3.PNG");
			Common.sleepFor(20000);
			screen.wait("GamePathImages/continue.PNG", 10000);
			screen.click("GamePathImages/continue.PNG");
			Common.sleepFor(50000);
			
			screen.click("GamePathImages/playNow.PNG");
			Common.sleepFor(5000);
			screen.wait("GamePathImages/revealAll"+i+1+".PNG", 5000);
			screen.click("GamePathImages/revealAll4.PNG");
			Common.sleepFor(20000);
			screen.wait("GamePathImages/continue.PNG", 10000);
			screen.click("GamePathImages/continue.PNG");
			Common.sleepFor(50000);
			
			screen.click("GamePathImages/playNow.PNG");
			Common.sleepFor(5000);
			screen.wait("GamePathImages/revealAll"+i+1+".PNG", 5000);
			screen.click("GamePathImages/revealAll5.PNG");
			Common.sleepFor(20000);
			screen.wait("GamePathImages/continue.PNG", 10000);
			screen.click("GamePathImages/continue.PNG");
			Common.sleepFor(50000);
			
	}
	*/
}
