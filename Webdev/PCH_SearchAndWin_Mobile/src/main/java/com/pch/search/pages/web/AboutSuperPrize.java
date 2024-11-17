package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class AboutSuperPrize extends Action{
	
	public HtmlElement giveAwayNumber(){
		return driver.findElement(By.xpath("//*[@id='disclosureBox']//p"));
	}
	
	public int giveAwayNumberCount(){
		return driver.getCountOfElementsWithXPath((".//*[@id='disclosureBox']//p"));
	}
	
	public By giveAwayNumberByContent() {
		return By.xpath(".//*[@id='disclosureBox']//p/strong/u");
	}
	
	/*This include entire -about super prize- page..
	*/
	public int aboutSuperPrizeContentCount(){
		return driver.getCountOfElementsWithXPath(".//*[@class='mainContainer']");
	}

	public HtmlElement aboutSuperPrizeContent() {
		return driver.findElement(By.xpath(".//*[@class='mainContainer']"));
	}
	
	public By aboutSuperPrizeContentByValue() {
		return By.xpath(".//*[@class='mainContainer']");
	}
	
	/*
	 * This method return a list<String> - with contents of values and conflicts page
	*/
	public List<String> contentOfValuesAndFactsPage(){
		
		String giveAwayNum2 = null;
		String awardGuarantee = null;
		String giveAwayNum1 = null;
		String disclosureText = null;
		
		CustomLogger.log("Navigating to Value and conflicts page");
		
		String url = "http://rules.pch.com/viewrulesfacts?type=searchdisclosure&nocss=1";
		driver.get(url);
		
		List<String> expectedContent = new ArrayList<String>();
		
		if(driver.getCountOfElementsWithXPath(".//*[@id='disclosureBox']/h4/strong")>0){
			
			String superPrizeEvent = driver.findElement(By.xpath(".//*[@id='disclosureBox']/h4/strong")).getText();
			expectedContent.add(superPrizeEvent);
		}
		
		if(driver.getCountOfElementsWithXPath(".//*[@id='disclosureBox']/p/strong/u")>0){
			
			giveAwayNum1 = driver.findElement(By.xpath(".//*[@id='disclosureBox']/p/strong/u")).getText();
			expectedContent.add(giveAwayNum1);
			
		}
		
		if(driver.getCountOfElementsWithXPath(".//*[@id='disclosureBox2']/h4/strong")>0){
			
			awardGuarantee = driver.findElement(By.xpath(".//*[@id='disclosureBox2']/h4/strong")).getText();
			expectedContent.add(awardGuarantee);
			
		}
		
		if(driver.getCountOfElementsWithXPath(".//*[@id='disclosureBox2']/p/span/strong/u")>0){
			giveAwayNum2 = driver.findElement(By.xpath(".//*[@id='disclosureBox2']/p/span/strong/u")).getText();
			expectedContent.add(giveAwayNum2);
		}
		
		if(driver.getCountOfElementsWithXPath(".//*[@id='disclosureBox3']/h4")>0){
			disclosureText = driver.findElement(By.xpath(".//*[@id='disclosureBox3']/h4")).getText();
			expectedContent.add(disclosureText);
		}
		
		CustomLogger.log("Gathered the content from the page");
		
		return expectedContent;
	}
	
	/*Here we are refreshing the page for i no.of times, to verify visibility of give away number in the about super prize page
	 * @param : i-for how may times we need to refresh the page
	*/
	public void waitTillGravitypresent(int i){
		try {
			for(int j = 0; j<=i; j++){
				if(driver.getCountOfElementsWithXPath((".//*[@id='disclosureBox']/p"))>0){
					break;
				}else{
					driver.navigate().refresh();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
