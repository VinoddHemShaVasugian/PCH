package com.pch.offers.offersadmin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class MissionAdminPages extends PageObject {

	private By amzGiftCardMenu = By.xpath("//a[contains(text(),'Mission Amazon Gift Cards')]");
	private By searchField = By.cssSelector("input.w-search");
	private By missionResultsTableHeader = By.cssSelector("div.card thead >tr");
	private By missionResultsTable = By.cssSelector("div.card tbody >tr");

	private int userGmtPosition;
	private int transactionStatusPosition;
	private int gcAmountPosition;

	public MissionAdminPages(WebDriver driver) {
		super(driver);
	}

	public MissionAdminPages() {
		super();
	}

	public String getGMTofAmzGiftcard() {
		return driver.findElement(missionResultsTable).findElements(By.tagName("td")).get(userGmtPosition).getText();
	}

	public String getTransactionStatusofAmzGiftcard() {
		return driver.findElement(missionResultsTable).findElements(By.tagName("td")).get(transactionStatusPosition)
				.getText();
	}

	public String getAmountofAmzGiftcard() {
		return driver.findElement(missionResultsTable).findElements(By.tagName("td")).get(gcAmountPosition).getText();
	}

	public void retriveAmzGiftCardColumns() {
		waitUntilElementIsVisible(30, missionResultsTableHeader);
		waitSeconds(10);
		WebElement ele = driver.findElement(missionResultsTableHeader);
		waitUntilElementIsClickable(ele);
		List<WebElement> columns = ele.findElements(By.tagName("th"));
		for (int i = 0; i < columns.size(); i++) {
			String actualValue = columns.get(i).getText();
			if (actualValue.equalsIgnoreCase("GMT")) {
				userGmtPosition = i;
			}
			if (actualValue.equalsIgnoreCase("TRANSACTION STATUS")) {
				transactionStatusPosition = i;
			}
			if (actualValue.equalsIgnoreCase("AMOUNT $")) {
				gcAmountPosition = i;
			}
		}
	}

	public void clickAmzGiftCardMenu() {
		waitUntilElementIsVisible(30, amzGiftCardMenu);
		waitUntilElementIsClickable(driver.findElement(amzGiftCardMenu)).click();
	}

	public void searchAmzGcRecord(String gmt) {
		waitUntilElementIsVisible(30, searchField);
		driver.findElement(searchField).sendKeys(gmt);
	}

}
