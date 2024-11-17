package com.pch.automation.pages.sw;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;

import com.pch.automation.utilities.RandomGenerator;

import net.serenitybdd.core.pages.PageObject;

/**
 * Page objects and methods for Home page
 *
 * @author vsankar
 */
public class HomePage extends PageObject {

	private final By searchBox = By.name("q");
//	private final By searchButton = By.cssSelector("input.serp-search-bar__submit");
	private final By samsBanner = By.xpath("//img[contains(@src,'https://samsadmin')]");
	private final By defaultBanner = By
			.xpath("//img[contains(@src,'http://cdn.pch.com/ui/openx-ads/fallbacks/pchsearch')]");

	/**
	 * Do the search with the given keyword
	 * 
	 * @param keyword
	 */
	public void search(String keyword) {
		typeInto(element(searchBox), keyword);
		element(searchBox).waitUntilClickable().submit();

	}

	/**
	 * Do the search with the random keyword
	 * 
	 * 
	 */
	public void search() {
		typeInto(element(searchBox), RandomGenerator.randomAlphabetic(6));
		element(searchBox).waitUntilClickable().submit();

	}

	/**
	 * To verify the presence of searchBox
	 * 
	 * @return True
	 */
	public boolean verifySearchBox() {
		return isElementVisible(searchBox);
	}

	/***
	 * Verify the display of SAMS banner
	 * 
	 * @return True
	 */
	public boolean verifySamsBanner() {
		return isElementVisible(samsBanner);
	}
	
	/***
	 * Verify the display of fallback banner
	 * 
	 * @return True
	 */
	public boolean verifyFallbackDefaultBanner() {
		return isElementVisible(defaultBanner);
	}
	
	/**
	 * To click element using javascript executor
	 * 
	 */
	public void jsClick(By locator) {
		waitForRenderedElementsToBePresent(locator);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		try 
		{
			executor.executeScript("arguments[0].click();", element(locator));
		} catch (StaleElementReferenceException stale) 
		{
			waitForRenderedElementsToBePresent(locator);
			executor.executeScript("arguments[0].click();", element(locator));
		}
	}
	
}