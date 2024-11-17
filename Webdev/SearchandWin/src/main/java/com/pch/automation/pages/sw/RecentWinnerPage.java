/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation.pages.sw;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;


// TODO: Auto-generated Javadoc
/**
 * The Class RecentWinnerPage.
 */
public class RecentWinnerPage extends PageObject{
	
	/**
	 * Instantiates a new recent winner page.
	 *
	 * @param driver the driver
	 */
	public RecentWinnerPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Click recent winner link.
	 */
	public void clickRecentWinnerLink(){
		element(By.xpath("//a[.='Recent Winners']")).click();
	}
	
	/**
	 * Gets the recent winners list.
	 *
	 * @return the recent winners list
	 */
	public ArrayList<String> getRecentWinnersList(){
		ArrayList<String> winnerNames = new ArrayList<String>();
		int numberOfWinners = findAll(By.xpath("//div[@class='winners-name']")).size();
		for(int winners = 1; winners <= numberOfWinners; winners++){
			winnerNames.add(element(By.xpath("(.//div[@class='winners-name'])["+winners+"]")).getText());
		}
		return winnerNames;
	}

}
