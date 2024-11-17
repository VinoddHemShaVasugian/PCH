package com.pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;

/**
 * Page objects and methods for GOS page
 *
 * @author vsankar
 */
public class GosPage extends PageObject {

	/**
	 * Instantiates a GOS page.
	 *
	 * @param driver
	 */
	public GosPage(WebDriver driver) {
		super(driver);
	}

	private final By legacyGosLayout = By.cssSelector("div.quiz-gos-blue");
	private final By LegacyTokenAndMsg = By.cssSelector("div.quiz-gos-blue__tokens");
	private final By nextQuizBtn = By.cssSelector("a.quiz-gos-blue__next-button");
	private final By playNextQuizLink = By.cssSelector("a.quiz-gos-blue__back-home");
	private final By videoAdGos = By.cssSelector("div.jw-media");
	private final By greatJobMsg = By.cssSelector("div.quiz-gos-blue__congrats");

	/**
	 * Verify legacy GOS layout.
	 *
	 * @param none
	 * @return boolean
	 * @author vsankar
	 */
	public boolean verifyLegacyGosLayout() {
		waitABit(10000);
		waitForRenderedElementsToBePresent(legacyGosLayout);
		return isElementVisible(legacyGosLayout);
	}

	/**
	 * Verify congrats message - Great Job!.
	 *
	 * @param none
	 * @return boolean
	 * @author vsankar
	 */
	public boolean verifyCongratsMsg() {
		waitForRenderedElementsToBePresent(greatJobMsg);
		return isElementVisible(greatJobMsg);
	}

	/**
	 * Verify video ad on GOS.
	 *
	 * @param none
	 * @return boolean
	 * @author vsankar
	 */
	public boolean verifyVideoAdGos() {
		waitForRenderedElementsToBePresent(videoAdGos);
		return isElementVisible(videoAdGos);
	}

	/**
	 * Retrieve Token amount from GOS.
	 *
	 * @param none
	 * @return String
	 * @author vsankar
	 */
	public String getTokenAmtGos() {
		return element(LegacyTokenAndMsg).getText().replaceAll("[^0-9]", "");
	}

	/**
	 * To verify token amount on GOS
	 *
	 * @author vsankar
	 */
	public boolean verifyTokenAmtGos() {
		waitForRenderedElementsToBePresent(LegacyTokenAndMsg);
		return isElementVisible(LegacyTokenAndMsg);
	}

	/**
	 * To click play next link on GOS
	 *
	 * @author vsankar
	 */
	public void clickPlayNextQuizLink() {
		verifyLegacyGosLayout();
		clickOn(element(playNextQuizLink).waitUntilClickable());
	}

	/**
	 * To verify play next link on GOS
	 *
	 * @author vsankar
	 */
	public boolean verifyPlayNextQuizLink() {
		waitForRenderedElementsToBePresent(playNextQuizLink);
		return isElementVisible(playNextQuizLink);
	}

	/**
	 * To click next quiz on GOS
	 *
	 * @author vsankar
	 */
	public void clickNextQuizBtn() {
		verifyLegacyGosLayout();
		clickOn(element(nextQuizBtn).waitUntilClickable());
	}

	/**
	 * To verify next quiz on GOS
	 *
	 * @author vsankar
	 */
	public boolean verifyNextQuizBtn() {
		waitForRenderedElementsToBePresent(nextQuizBtn);
		return isElementVisible(nextQuizBtn);
	}
}