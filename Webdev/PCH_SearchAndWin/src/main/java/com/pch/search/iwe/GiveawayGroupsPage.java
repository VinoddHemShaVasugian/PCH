package com.pch.search.iwe;

import org.openqa.selenium.By;

import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class GiveawayGroupsPage extends IWEBasePage{
	
	String createGiveawayGroupsPage=appURL+"#giveawaygroup/create";
	
	public HtmlElement setName(){
		return driver.findElement(By.name("name"));
	}
	public HtmlElement saveButton(){
		return driver.findElement(By.xpath("//*[contains(text(), 'Save')]"));
	}
	
	/**
	 * To search Giveaway Groups
	 * */
	
	public boolean searchGiveaways(String giveawayGroupName){
		boolean isFound=false;	
		try{
			search(giveawayGroupName);
			driver.findElement(By.xpath(String.format("//font[text()='%s']",giveawayGroupName)));
			isFound=true;
		}catch(Exception e){
			CustomLogger.log(e.toString());
		}
		return isFound;

	}

	public void createOrEdit(String giveawayGroupName,String giveawayName){
		if(searchGiveaways(giveawayGroupName)){
			//if giveaway group already exists ....add giveaway if not added
			driver.findElement(By.xpath(String.format("//font[text()='%s']",giveawayGroupName))).click();
			addGiveaway(giveawayName);
			saveButton().click();
			Common.sleepFor(5000);
		}else{
			//open create giveaway group page and add new giveaway group
			driver.get(createGiveawayGroupsPage);
			Common.sleepFor(3000);
			saveGiveawayGroup(giveawayGroupName,giveawayName);
		}
	}
	
	public void addGiveaway(String giveawayName){
		if(driver.getCountOfElementsWithXPath("//div[text()='SnW-Auto-$10-cash']")>0){
			//do nothing
			CustomLogger.log(giveawayName+" giveaway already added");
		}else{
			CustomLogger.log("No giveaway exists...adding giveaway: "+giveawayName);
			driver.findElement(By.name("giveawayIds")).sendKeys(giveawayName);
			Common.sleepFor(3000);
			selectExactItemFromDropdown(giveawayName);
		}
	}
	

	public void saveGiveawayGroup(String name,String giveaways)
	{
		setName().sendKeys(name);
		addGiveaway(giveaways);		
		saveButton().click();
		Common.sleepFor(5000);
}
}
