package com.pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;

/**
 * Page objects and methods for Light boxes
 *
 * @author vsankar
 */

public class LightboxPage extends PageObject {

	/**
	 * Instantiates a Lightbox page.
	 *
	 * @param driver
	 */
	public LightboxPage(WebDriver driver) {
		super(driver);
	}

	private final By passwordSilveruser = By.cssSelector("input.lbpassword");
	private final By cnfmpasswordSilveruser = By.cssSelector("input.lbconfirmPassword");
	private final By submitSilveruser = By.cssSelector("button.submit");
	private final By noPwdUserLbContinueBtn = By.cssSelector("#no-password > a.action");
	private final By miniUserCompleteRegButton = By.cssSelector("#comp-reg > a.action");
	private final By guestUserLB = By.cssSelector("div#unrecognized");
	private final By optinClose = By.cssSelector("img.close");
	private final By optinLb = By.cssSelector("div#optin-container");
	private final By abandonLb = By.cssSelector("div#abandon-tokens");

	/**
	 * To close optin lightbox
	 * 
	 * @return None
	 */
	public void closeOptinLb() {
		if (verifyOptinLB()) {
			clickOn(element(optinClose));
		}
	}

	/**
	 * To Verify abandonand light box
	 * 
	 * @return boolean
	 */
	public boolean verifyAbandonLB() {
		waitABit(3000);
		waitForRenderedElementsToBePresent(abandonLb);
		return isElementVisible(abandonLb);
	}

	/**
	 * Verify optin light box
	 * 
	 * @return boolean
	 */
	public boolean verifyOptinLB() {
		waitABit(2000);
		waitForRenderedElementsToBePresent(optinLb);
		return isElementVisible(optinLb);
	}

	/**
	 * To completed silver user registration.
	 * 
	 * @return boolean
	 */
	public void completeSilverUser() {
		typeInto(element(passwordSilveruser), "Pch123");
		typeInto(element(cnfmpasswordSilveruser), "Pch123");
		clickOn(element(submitSilveruser).waitUntilClickable());
	}

	/**
	 * Verify complete registration light box for Mini reg user
	 * 
	 * @return boolean
	 */
	public boolean verifyCompleteRegLB() {
		waitABit(3000);
		waitForRenderedElementsToBePresent(miniUserCompleteRegButton);
		return isElementVisible(miniUserCompleteRegButton);
	}

	/**
	 * To click complete registration button from light box for mini reg user
	 * 
	 * @return None
	 */
	public void clickContinueBtnCompleteRegLb() {
		if (verifyCompleteRegLB()) {
			clickOn(element(miniUserCompleteRegButton));
		}
	}

	/**
	 * Verify complete registration light box for no password user
	 * 
	 * @return boolean
	 */
	public boolean verifyNoPwdUserCompleteRegLB() {
		waitFor(3);
		waitForRenderedElementsToBePresent(noPwdUserLbContinueBtn);
		return isElementVisible(noPwdUserLbContinueBtn);
	}

	/**
	 * To click complete registration button from light box for no password user
	 * 
	 * @return None
	 */
	public void clickContinueBtnNoPwdUserLb() {
		if (verifyNoPwdUserCompleteRegLB()) {
			clickOn(element(noPwdUserLbContinueBtn).waitUntilClickable());
		}
	}

	/**
	 * Verify registration light box for guest user
	 * 
	 * @return boolean
	 */
	public boolean verifyGuestUserLB() {
		return isElementVisible(guestUserLB);
	}
}