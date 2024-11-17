package com.pch.survey.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.dtos.GaTagInfoDto;
import com.pch.survey.utilities.ConfigurationReader;

public class GaTrackingPage extends PageObject {

	By categories = new By.ByXPath("//tr/td[3]");
	By actions = new By.ByXPath("//tr/td[4]");
	By labels = new By.ByXPath("//tr/td[5]");

	private static String eventTrackerUrl = ConfigurationReader.getInstance().getEventTrackerUrl();

 
	
	public GaTrackingPage(WebDriver driver) {
		super(driver);
	}

	public GaTrackingPage() {
	}

	private void clearConsole() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "console.clear();";
		js.executeScript(script);
	}

	public List<GaTagInfoDto> getGATags() {
		boolean flag = false;
		int count=1, trycount=1;
		List<GaTagInfoDto> tagList = new ArrayList<GaTagInfoDto>();
		waitSeconds(2);
		driver.navigate().to(eventTrackerUrl);
		waitSeconds(5);

		List<WebElement> categoriesItems = driver.findElements(categories);
		List<WebElement> actionsItems    = driver.findElements(actions);
		List<WebElement> lablesItems    = driver.findElements(labels);
		int rowsCount = categoriesItems.size();
		System.out.println("Number of rows in Event Tracker is: "+rowsCount);
		String[][] eventsTable = new String[rowsCount][3];
		
		for(int i = 0;i<rowsCount;i++)
		{
			trycount=1;
			while(trycount<=2) {	
			try {
				eventsTable[i][0] = categoriesItems.get(i).getText().trim();
				System.out.println(eventsTable[i][0]);
				eventsTable[i][1] = actionsItems.get(i).getText().trim();
				System.out.println(eventsTable[i][1]);
				eventsTable[i][2] = lablesItems.get(i).getText().trim();
				System.out.println(eventsTable[i][2]);
				trycount=3;
				}
				catch(StaleElementReferenceException se) {
					System.out.println("There is stale element exception for the element in "+i+"th row");
					System.out.println("Lets try one more time");
					trycount++;
					categoriesItems = driver.findElements(categories);
					actionsItems = driver.findElements(actions);
					lablesItems = driver.findElements(labels);
					rowsCount = categoriesItems.size();
				}
			}
		}
		for(int i = 0; i < eventsTable.length; i++) {
			GaTagInfoDto tagInfo = new GaTagInfoDto();
			tagInfo.setEventCategory(eventsTable[i][0]);
			tagInfo.setEventAction(eventsTable[i][1]);
			tagInfo.setEventLabel(eventsTable[i][2]);
			tagList.add(tagInfo);
		}
		 
		return tagList;
	}
}
