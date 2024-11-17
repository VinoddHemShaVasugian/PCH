package com.pch.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.automation.pages.fp.ArticlesPage;

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
	private final By optinSubmitBtn = By.cssSelector("div.fp_lb_button_holder");
	private final By fpOptinCheckBox = By.xpath("//span[contains(text(),'PCHFrontpage')]");
	private final By pchcomOptinCheckBox = By.xpath("//span[contains(text(),'pch.com')]");
	private final By rapidHRSubmitBtn = By.cssSelector("div.fp_lb_button");
	private final By closeHRLightBox = By.cssSelector("button.close_lb");
	private final By maxSearchHRSubmitBtn = By.cssSelector("div.fp_lb_button");
	private final By fancyLbAccept = By.cssSelector("div.fancybox-skin");
	private final By fancyLbClose = By.cssSelector("a.fancybox-close");
	private final By scratchcardLBSubmitButton = By.id("scratchLightboxImage");
	private final By scratchcardLBCloseIcon = By.id("dismiss_lb");
	private final By completeRegContinueBtn = By.cssSelector("button.continue");
	private final By closeCompelteReglb = By.cssSelector("button.close-x");
	private final By passwordSilveruser = By.cssSelector("input.lbpassword");
	private final By cnfmpasswordSilveruser = By.cssSelector("input.lbconfirmPassword");
	private final By submitSilveruser = By.cssSelector("button.submit");
	private final By completeRegcontinueButton = By.cssSelector("div.fp_lb_button_holder");
	private final By closeOptinlb = By.cssSelector("section.optin_lb.modal-dialog > button.close_lb");
	private final By welcomeLBClose = By.cssSelector("section.frontpage_lightbox.welcome_lightbox > button.close_lb");
	private final By guestSigregLb = By.cssSelector("section.signin_lightbox");
	private final By uninavLogo = By.cssSelector("a.uninav__logo");
	ArticlesPage article;

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
	 * Close welcome light box
	 * 
	 * @return boolean
	 */
	public void closeWelcomeLightbox() {
		waitFor(1);
		if (isElementVisible(welcomeLBClose)) {
//			closeEvergagePopup();
			try {
				clickOn(element(welcomeLBClose).waitUntilClickable());
			} catch (Exception e) {
				e.getLocalizedMessage();
				getDriver().navigate().refresh();
			}
		}
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
		if (isElementVisible(scratchcardLBCloseIcon)) {
			clickOn(element(scratchcardLBCloseIcon));
		}
	}

	/**
	 * Verify the display of the HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyRapidHRLightbox() {
		try {
			waitForRenderedElementsToBePresent(rapidHRSubmitBtn);
			return isElementVisible(rapidHRSubmitBtn);
		} catch (Exception e) {
			return isElementVisible(rapidHRSubmitBtn);
		}
	}

	/**
	 * Verify the display of the Max Search HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyMaxSearchHRLightbox() {
		waitABit(7);
		waitForRenderedElementsToBePresent(maxSearchHRSubmitBtn);
		return isElementVisible(maxSearchHRSubmitBtn);
	}

	/**
	 * Verify the display of the Daily Limit Search HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyDailyLimitSearchHRLightbox() {
		return isElementVisible(maxSearchHRSubmitBtn);
	}

	/**
	 * Verify the display of the Disable Search HR light box
	 * 
	 * @return boolean
	 */
	public boolean verifyDisableSearchHRLightbox() {
		waitABit(5000);
		waitForRenderedElementsToBePresent(fancyLbAccept);
		return isElementVisible(fancyLbAccept);
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
		return (isElementVisible(fpOptinCheckBox)
				|| isElementVisible(pchcomOptinCheckBox) && isElementVisible(optinSubmitBtn));
	}

	/**
	 * Close the optin lightbox
	 */
	public void closeOptinLightbox() {
		waitFor(1);
		closeWelcomeLightbox();
		if (isElementVisible(optinSubmitBtn)) {
//			closeEvergagePopup();
			try {
				clickOn(element(closeOptinlb).waitUntilClickable());
				getDriver().navigate().refresh();
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
		waitFor(3);
		waitForRenderedElementsToBePresent(completeRegcontinueButton);
		return isElementVisible(completeRegcontinueButton);
	}

	public void clickContinueBtnCompleteRegLb() {
		if (verifyOptinCompleteRegLBforUsertypes()) {
			clickOn(element(completeRegcontinueButton));
		}
	}

	public void closeHRFancyLightbox() {
		clickOn(element(fancyLbClose));
	}

	public void closeGuestUserLb() {
		if (verifyGuestuserLB()) {
			clickOn(element(closeCompelteReglb));
		}
	}

	public boolean verifyGuestuserLB() {

		waitFor(3);
		waitForRenderedElementsToBePresent(guestSigregLb);
		return isElementVisible(guestSigregLb);
	}

	public void verifyGuestUserLB() {
		clickOn(element(uninavLogo));
		article.clickAnyArticle();
		waitFor(3);
		if (verifyGuestuserLB()) {
			clickOn(element(closeHRLightBox));
		} else {
			article.clickNextArticle();
		}

	}

}