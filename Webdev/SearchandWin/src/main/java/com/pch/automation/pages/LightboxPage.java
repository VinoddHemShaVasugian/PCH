package com.pch.automation.pages;

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

	private final By evergageTop = By.cssSelector("div.evergage-tooltip-bar");
	private final By evergagePopup = By.cssSelector("div.evergage-tooltip>a>img.eg-image");
	private final By optinSubmitBtn = By.cssSelector("button.sandw-optin__button");
	private final By swOptinCheckBox = By.cssSelector("div[data-optin-option='searchandwin']");
	private final By pchcomOptinCheckBox = By.cssSelector("div[data-optin-option='pchcom']");
	private final By closeOptinlb = By.cssSelector("button.c-button.c-button--close");
	private final By rapidHRSubmitBtn = By.cssSelector("img.rm-warning-repetitively__button");
	private final By closeHRLightBox = By.cssSelector("button.c-button.c-button--close");
	private final By maxSearchHRSubmitBtn = By.cssSelector("img.rm-max-chances__button");
	private final By dailySearchLimitHRSubmitBtn = By.cssSelector("img.rm-daily-limit__button");
	private final By disableSearchHRSubmitBtn = By.cssSelector("img.rm-permanently-disabled__button");
	private final By scratchcardLBSubmitButton = By.id("scratchLightboxImage");
	private final By scratchcardLBCloseIcon = By.id("dismiss_lb");
	private final By completeRegContinueBtn = By.cssSelector("button.continue");
	private final By closeCompelteReglb = By.cssSelector("button.close-x");
	private final By passwordSilveruser = By.cssSelector("input.lbpassword");
	private final By cnfmpasswordSilveruser = By.cssSelector("input.lbconfirmPassword");
	private final By submitSilveruser = By.cssSelector("button.submit");
	private final By completeRegcontinueButton = By.cssSelector("button.continue");

	/**
	 * Verify the display of the Silver user Complete Registration light box
	 * 
	 * @return boolean
	 */
	public boolean verifySilverCompleteRegistrationLightbox() {
		return (isElementVisible(submitSilveruser) && isElementVisible(passwordSilveruser));
	}

	public void completeSilverUser() {
		typeInto(element(passwordSilveruser), "testing");
		typeInto(element(cnfmpasswordSilveruser), "testing");
		clickOn(element(submitSilveruser).waitUntilClickable());
	}

	/**
	 * Close the Complete Registration light box
	 * 
	 * @return boolean
	 */
	public void closeCompleteRegistrationLightbox() {
		clickOn(element(closeCompelteReglb).waitUntilClickable());
	}

	/**
	 * Verify the display of the Complete Registration light box
	 * 
	 * @return boolean
	 */
	public boolean verifyCompleteRegistrationLightbox() {
		return (isElementVisible(completeRegContinueBtn) && isElementVisible(closeCompelteReglb));
	}

	/**
	 * Click the continue button on the Complete Registration light box
	 * 
	 * @return boolean
	 */
	public void clickCompleteRegContinueBtn() {
		clickOn(element(completeRegContinueBtn).waitUntilClickable());
	}

	/**
	 * Verify the display of the ScratchCard light box
	 * 
	 * @return boolean
	 */
	public boolean verifyScratchcardLightbox() {
		return isElementVisible(scratchcardLBSubmitButton);
	}

	/**
	 * Close the ScratchCard lightbox
	 */
	public void closeScratchCardLightbox() {
		if(isElementVisible(scratchcardLBCloseIcon)) {
		clickOn(element(scratchcardLBCloseIcon));
		}
	}

	/**
	 * Verify the display of the HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyRapidHRLightbox() {
		return isElementVisible(rapidHRSubmitBtn);
	}

	/**
	 * Verify the display of the Max Search HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyMaxSearchHRLightbox() {
		return isElementVisible(maxSearchHRSubmitBtn);
	}

	/**
	 * Verify the display of the Daily Limit Search HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyDailyLimitSearchHRLightbox() {
		return isElementVisible(dailySearchLimitHRSubmitBtn);
	}

	/**
	 * Verify the display of the Disable Search HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyDisableSearchHRLightbox() {
		return isElementVisible(disableSearchHRSubmitBtn);
	}

	/**
	 * Close the HR lightbox
	 */
	public void closeHRLightbox() {
		clickOn(element(closeHRLightBox));
	}

	/**
	 * Verify the display of the Optin light box
	 * 
	 * @return boolean
	 */
	public boolean verifyOptinLightbox() {
		return ((isElementVisible(swOptinCheckBox) || isElementVisible(pchcomOptinCheckBox))
				&& isElementVisible(optinSubmitBtn));
	}

	/**
	 * Close the optin lightbox
	 */
	public void closeOptinLightbox() {
		if (isElementVisible(optinSubmitBtn)) {
			closeEvergagePopup();
			try {
				clickOn(element(closeOptinlb).waitUntilClickable());
			} catch (Exception e) {
				e.getLocalizedMessage();
				getDriver().navigate().refresh();
			}
		}
	}

	/**
	 * Close Evergage popup
	 */
	public void closeEvergagePopup() {
		if (verifyEvergagePopup()) {
			clickOn(element(evergagePopup));
		}
	}

	/**
	 * Verify evergage popup
	 * 
	 * @return True
	 */
	public boolean verifyEvergagePopup() {
		return isElementVisible(evergagePopup);
	}

	/**
	 * close evergage banner, displayed on page header
	 */
	public void clickEvergageTopBanner() {
		clickOn(element(evergageTop));
	}

	/**
	 * complete reg lightbox for mini reg and social reg
	 * 
	 * @return
	 */

	public boolean verifyOptinCompleteRegLBforUsertypes() {
		return isElementVisible(completeRegcontinueButton);
	}

	public void clickContinueBtnCompleteRegLb()

	{
		if (verifyOptinCompleteRegLBforUsertypes()) {
			clickOn(element(completeRegcontinueButton));
		}
	}

}
