package com.pch.kenofrontend.pages;

import java.util.Iterator;
import java.util.List;
import java.lang.Integer;

import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import net.serenitybdd.core.pages.PageObject;


public class MyAccountPage extends PageObject {
	
	// Initialize the Page Objects using By Class	
	private By tokenEntry = new By.ByCssSelector("section#th_list > article");
	private By tokenEntryDescription = new By.ByCssSelector("section#th_list > article > div.th_details > div.th_desc");
	private By tokenEntryAmount = new By.ByCssSelector("section#th_list > article > div.th_details > div.date_tokens > span.tokens");
	
	// Initialize Variables
	List<WebElement> tokenEntries;
	List<WebElement> tokenDescription;
	List<WebElement> tokenAmount;	
	WebElement e1;
	WebElement e2;
	Iterator <WebElement> i1;
	Iterator <WebElement> i2;
	
		
	public void verifyTokensInTokenHistory(ExamplesTable tokenEnrtyData)
	{
		System.out.println("MyAccountPage");
		Integer x = 0;					
		tokenEntries= getDriver().findElements(tokenEntry);					
		x = tokenEntries.size();	
		
		if (x >= 5)
		{
			Integer y = 0;
			//Try click via webdriver; Try in IE
			Actions actions = new Actions(getDriver());	
			actions.moveToElement(tokenEntries.get(0));
			actions.click();		
			while (x != y)
			{			
				tokenEntries= getDriver().findElements(tokenEntry);
				//tokenEntries = findAll(tokenEntry);
				
				x = tokenEntries.size();
				System.out.println("Token History size before Page Down is  " + x);
			
				actions.sendKeys(Keys.PAGE_DOWN);
				actions.build().perform();
				actions.sendKeys(Keys.PAGE_DOWN);
				actions.build().perform();
			
				System.out.println("Page Down pressed twice");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tokenEntries= getDriver().findElements(tokenEntry);
				y = tokenEntries.size();
				System.out.println("Token History size after Page Down is  " + y);
			}
		}
		
		tokenEntries= getDriver().findElements(tokenEntry);
		tokenDescription= getDriver().findElements(tokenEntryDescription);
		tokenAmount= getDriver().findElements(tokenEntryAmount);
		
		i1 = tokenDescription.iterator();
		i2 = tokenAmount.iterator();
		
		Integer tokenSize = tokenEntries.size();
		System.out.println("Token History size is  " + tokenSize);		
		boolean flag = false;		
		//while(i1.hasNext() && (i2.hasNext()))
		for (int i=0; i<tokenSize; i++)	
			{
				e1 = tokenDescription.get(i);
				e2 = tokenAmount.get(i);
					
				System.out.println("Token Entry Description - "+e1.getText());
				System.out.println("Token Entry Amount - "+e2.getText());
				
				if(e1.getText().contains(tokenEnrtyData.getRow(0).get("token_entry")) && e2.getText().contains(tokenEnrtyData.getRow(0).get("token_amount")))
				{
					//Assert.assertEquals("PCHkeno User Registration",e1.getText());
					//Assert.assertTrue(e1.getText().contains("PCHkeno User Registration"));
					System.out.println(e2.getText() + " tokens awarded for '" + e1.getText() + "'");
					System.out.println("Tokens Validated!!!");
					Assert.assertTrue(true);
					flag = true;
					break;									
				}
			}
		
		if (!flag)
			Assert.assertFalse("Token Entry not found!!", true);
		
	}

}
