package com.pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;

import com.pch.quiz.utilities.CommonLib;

import net.serenitybdd.core.pages.PageObject;

/**
 * Page objects and methods for Home Page of Quiz site.
 *
 * @author vsankar
 */
public class HomePage extends PageObject {

	/**
	 * Instantiates a Home page.
	 *
	 * @param driver
	 */
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	CommonLib commonLib = new CommonLib();

	private final By clickTrendingQuiz = By.cssSelector("article.qdetail-item >a");
	private final By featuredQuizSection = By.cssSelector("section.qcontent-block--featured");

	public void clickTrendingQuiz() {
		try {
		clickOn(element(clickTrendingQuiz));
		}
		catch(ElementClickInterceptedException ecic) {
			commonLib.jsClick(clickTrendingQuiz);
		}
	}

	/**
	 * Verify Site Home page.
	 *
	 * @return True
	 */
	public boolean verifySiteHomePage() {
		waitForRenderedElementsToBePresent(featuredQuizSection);
		return isElementVisible(featuredQuizSection);
	}

	/**
	 * To click category pages.
	 *
	 * @return None
	 */
	public void navigateToCategoryPage(String category) {
		waitForRenderedElementsToBePresent(By.xpath("//a[text()='"+ category +"']"));
		clickOn(element(By.xpath("//a[text()='"+ category +"']")));
	}
}