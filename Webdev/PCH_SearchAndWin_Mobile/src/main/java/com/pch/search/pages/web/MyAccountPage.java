package com.pch.search.pages.web;

import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Sleeper;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.User;

public class MyAccountPage extends Action {
	
	String device=Environment.getDevice();
	
	public enum MyAccountTab{
		My_Info("My Info"),
		Token_History("Token History"),
		Rewards("Rewards"),
		All_Time_Token_LeaderBoard("All-Time Token Leaderboard");

		private String tabName;

		MyAccountTab(String tabName) {
			this.tabName=tabName;
		}

		public String getTabName(){
			return tabName;
		}

	}

	public void waitForMyAccountPageToLoad(){
		try{
			CustomLogger.log("Waiting for MyAccount Page to load...");			
			driver.findElement(By.xpath("//div[@class='account-box']")).waitForVisible();
			driver.findElement(By.xpath("//div[@class='main-block']")).waitForVisible();
			driver.findElement(By.xpath("//div[@class='main-block']//div")).waitForVisible();			
		}catch(Exception e){
			CustomLogger.log(e.getMessage());
		}
	}
	private HtmlElement getTabElement(String tabName){
		String xpath = String.format("//span[text()='%s']",tabName);
		return driver.findElement(By.xpath(xpath));
	}
	public void tokenDescription(String text){
		
		driver.findElement(By.xpath("//a[contains(@href,'token-history')]")).equals(text);
		
	}

	public void navigateToTab(MyAccountTab tab) {
		Common.sleepFor(1000);
		driver.findElement(By.xpath("//a[contains(@href,'token-history')]")).click();
		driver.waitForBrowserToLoadCompletely();
	}

	public String getSelectedTab() {
		HtmlElement selectedTab = driver.findElement(By.xpath("//a[@class='tab active']"));
		String tabName = selectedTab.getAttribute("textContent");
		CustomLogger.log("Selected Tab on My account page " + tabName);
		return tabName;
	}

	public void updateUserInfo(User user){
		CustomLogger.log("Updating user info");
		setTitle(user.getTitle());
		setFirstName(user.getFirstname());
		setLastName(user.getLastname());
		setStreetAddress(user.getStreet());
		setCity(user.getCity());
		setState(user.getState());
		setZip(user.getZip());
		setDOB_Month(user.getDob_Month());
		setDOB_Day(Integer.parseInt(user.getDob_Day()));
		setDOB_Year(user.getDob_Year());
		clickUpdate();
		successElement().waitForVisible();

	}

	public void navigateToMainParentSite(){
		HtmlElement parentWebSiteLogo = driver.findElement(By.xpath("//div[@class='uni-nav-bar']//a"));
		parentWebSiteLogo.click();
		driver.waitForBrowserToLoadCompletely();		
	}

	private HtmlElement successElement(){
		return driver.findElement(By.xpath("//*[(contains(text(),'Account Updated Successfully!') and contains(@class,'message')) or contains(@id,'success_message')]"));
	}

	private void clickUpdate(){
		driver.findElement(By.xpath(".//*[contains(@id,'sub-myaccount-btn') or contains(text(),'Update')]")).click();		
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

	private void setDOB_Month(String month){
		driver.findSelectList(By.id("MN")).selectByVisibleText(month);
	}

	private void setDOB_Day(int day){
		DecimalFormat formatter = new DecimalFormat("00");		
		driver.findSelectList(By.id("DY")).selectByVisibleText(formatter.format(day));
	}


	private void setDOB_Year(String year){
		driver.findSelectList(By.id("YR")).selectByVisibleText(year);
	}	

}
