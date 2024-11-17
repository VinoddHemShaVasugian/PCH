package com.pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;

/**
 * Page objects and methods for Home Page of Quiz tab.
 *
 * @author vsankar
 */
public class TabHomePage extends PageObject {

	/**
	 * Instantiates Tab Home page.
	 *
	 * @param driver
	 */
	public TabHomePage(WebDriver driver) {
		super(driver);
	}

	private final By quizTabMenu = By.id("mainnav");

	/**
	 * Verify Tab Home page.
	 *
	 * @return True
	 */
	public boolean verifyTabHomePage() {
		waitForRenderedElementsToBePresent(quizTabMenu);
		return isElementVisible(quizTabMenu);
	}

}
