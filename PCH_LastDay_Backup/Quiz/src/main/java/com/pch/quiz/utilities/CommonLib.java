package com.pch.quiz.utilities;

/**
 * Common libraries for quizzes scripts development.
 * 
 * @author vsankar
 */

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;

public class CommonLib extends PageObject {
	private By apphealthPageRegFoundation = new By.ByXPath("//pre[contains(text(),'Registration Foundation: UP')]");
	private final String CASEvent = "CAS EVENT";
	private final String regFoundation = "Registration Foundation";
	private final String tokenBank = "Token Bank";
	private final String deviceAnalyzer = "Device Analyzer";
	private final String contestEntryApi = "Contest Entry API";
	private final String userApi = "User API";
	private final String prizeMachine = "Prizemachine";
	private final String segmentation = "Segmentation";
	private final String databaseJoomla = "Database Joomla";

	/**
	 * To click element using javascript executor
	 * 
	 * @parameter Locator
	 */
	public void jsClick(By locator) {
		waitForRenderedElementsToBePresent(locator);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		try {
			executor.executeScript("arguments[0].click();", element(locator));
		} catch (StaleElementReferenceException stale) {
			waitForRenderedElementsToBePresent(locator);
			executor.executeScript("arguments[0].click();", element(locator));
		}
	}

	/**
	 * Navigate to new window and close it then back to parent window.
	 */
	public void closeCurrentlyFocusedTab() {
		WebDriver driver = getDriver();
		String parentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(parentHandle).close();
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	/**
	 * Verify quizzes apphealth
	 * 
	 * @auther rkhan
	 */

	public void verifyQuizzesAppHealthPage() {
		String text = element(apphealthPageRegFoundation).getText();
		Assert.assertEquals(true, element(apphealthPageRegFoundation).isDisplayed());
		String[] appHealthResponseLength = text.split("[\\r?\\n]");
		for (String appHealthResponse : appHealthResponseLength) {
			System.out.println(appHealthResponse);
			if (appHealthResponse.contains(CASEvent))
				Assert.assertEquals("CAS EVENT: NO", appHealthResponse);
			else if (appHealthResponse.contains(regFoundation))
				Assert.assertEquals("Registration Foundation: UP", appHealthResponse);
			else if (appHealthResponse.contains(tokenBank))
				Assert.assertEquals("Token Bank: UP", appHealthResponse);
			else if (appHealthResponse.contains(deviceAnalyzer))
				Assert.assertEquals("Device Analyzer: UP", appHealthResponse);
			else if (appHealthResponse.contains(contestEntryApi))
				Assert.assertEquals("Contest Entry API: UP", appHealthResponse);
			else if (appHealthResponse.contains(userApi))
				Assert.assertEquals("User API: UP", appHealthResponse);
			else if (appHealthResponse.contains(prizeMachine))
				Assert.assertEquals("Prizemachine: UP", appHealthResponse);
			else if (appHealthResponse.contains(segmentation))
				Assert.assertEquals("Segmentation: UP", appHealthResponse);
			else if (appHealthResponse.contains(databaseJoomla))
				Assert.assertEquals("Database Joomla: UP", appHealthResponse);

		}
	}

	/**
	 * Navigate to new window and close it then back to parent window.
	 */
	public void switchToNewlyOpenedTab() {
		WebDriver driver = getDriver();
		String parentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}
}